package com.example.sirovarweather.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sirovarweather.R;
import com.example.sirovarweather.data.local.PreferencesManager;
import com.example.sirovarweather.data.model.City;
import com.example.sirovarweather.data.model.DailyForecast;
import com.example.sirovarweather.data.model.HourlyForecast;
import com.example.sirovarweather.data.model.WeatherResponse;
import com.example.sirovarweather.data.repository.WeatherRepository;
import com.example.sirovarweather.ui.adapter.DailyForecastAdapter;
import com.example.sirovarweather.ui.adapter.HourlyForecastAdapter;
import com.example.sirovarweather.utils.WeatherCodeMapper;
import com.example.sirovarweather.utils.LocaleUtils;
import com.google.gson.JsonParseException;
import com.example.sirovarweather.widget.WeatherWidgetUpdater;
import com.example.sirovarweather.worker.WeatherUpdateWorker;
import com.google.android.material.snackbar.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private PreferencesManager preferences;
    private WeatherRepository repository;
    private TextView cityTitle, dateText, temperature, description, minMax, feelsLike, humidity, wind, precipitation, lastUpdated, status;
    private ImageView weatherIcon;
    private SwipeRefreshLayout swipeRefresh;
    private HourlyForecastAdapter hourlyAdapter;
    private DailyForecastAdapter dailyAdapter;
    private ActivityResultLauncher<Intent> searchLauncher;
    private ActivityResultLauncher<Intent> settingsLauncher;
    private int weatherRequestId;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = new PreferencesManager(this);
        repository = WeatherRepository.getInstance();
        bindViews();
        setupLists();
        searchLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) loadSelectedCity();
        });
        settingsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> loadSelectedCity());
        findViewById(R.id.search_button).setOnClickListener(view -> searchLauncher.launch(new Intent(this, SearchActivity.class)));
        findViewById(R.id.settings_button).setOnClickListener(view -> settingsLauncher.launch(new Intent(this, SettingsActivity.class)));
        findViewById(R.id.previous_city_button).setOnClickListener(view -> {
            preferences.restorePreviousCity();
            Timber.i("Previous city restored");
            WeatherWidgetUpdater.updateAll(getApplicationContext());
            loadSelectedCity();
        });
        swipeRefresh.setOnRefreshListener(() -> {
            if (preferences.getCity() == null) geocodeDefaultCity(); else loadSelectedCity();
        });
        dateText.setText(new SimpleDateFormat("EEEE, d MMMM", LocaleUtils.getAppLocale(getResources())).format(new Date()));
        WeatherUpdateWorker.schedule(getApplicationContext());
        City city = preferences.getCity();
        if (city == null) geocodeDefaultCity(); else loadSelectedCity();
        refreshPreviousCityButton();
    }

    private void bindViews() {
        cityTitle=findViewById(R.id.city_title); dateText=findViewById(R.id.current_date); temperature=findViewById(R.id.current_temperature);
        description=findViewById(R.id.weather_description); weatherIcon=findViewById(R.id.current_icon); minMax=findViewById(R.id.min_max_today);
        feelsLike=findViewById(R.id.feels_like_value); humidity=findViewById(R.id.humidity_value); wind=findViewById(R.id.wind_value);
        precipitation=findViewById(R.id.precipitation_value); lastUpdated=findViewById(R.id.last_updated); status=findViewById(R.id.status_message);
        swipeRefresh=findViewById(R.id.swipe_refresh);
    }

    private void setupLists() {
        String unit = preferences.isFahrenheit() ? "°F" : "°C";
        hourlyAdapter = new HourlyForecastAdapter(unit);
        dailyAdapter = new DailyForecastAdapter(unit);
        RecyclerView hourly = findViewById(R.id.hourly_list);
        hourly.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        hourly.setAdapter(hourlyAdapter);
        RecyclerView daily = findViewById(R.id.daily_list);
        daily.setLayoutManager(new LinearLayoutManager(this));
        daily.setAdapter(dailyAdapter);
    }

    private void geocodeDefaultCity() {
        showStatus(getString(R.string.loading));
        String language = preferences.getLanguage();
        repository.searchCities("Kyiv", language, new WeatherRepository.CitiesCallback() {
            @Override public void onSuccess(List<City> cities) {
                if (cities.isEmpty()) { showStatus(getString(R.string.city_not_found)); return; }
                City defaultCity = cities.get(0);
                preferences.saveCity(defaultCity);
                Timber.i("Default city resolved through geocoding");
                WeatherWidgetUpdater.updateAll(getApplicationContext());
                loadSelectedCity();
                refreshPreviousCityButton();
            }
            @Override public void onError(Throwable error) {
                Timber.w(error, "Could not resolve the initial city");
                showStatus(getString(R.string.network_error));
                Snackbar.make(findViewById(R.id.swipe_refresh), R.string.network_error, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void loadSelectedCity() {
        int requestId = ++weatherRequestId;
        City city = preferences.getCity();
        if (city == null) { showStatus(getString(R.string.no_cached_weather)); return; }
        cityTitle.setText(city.getDisplayName());
        refreshPreviousCityButton();
        WeatherResponse cached = preferences.getWeather();
        if (cached != null && cached.isValid()) renderWeather(cached, preferences.getWeatherSavedAt());
        swipeRefresh.setRefreshing(true);
        status.setVisibility(View.GONE);
        repository.loadWeather(this, city, new WeatherRepository.WeatherCallback() {
            @Override public void onSuccess(WeatherResponse response) {
                if (requestId != weatherRequestId) return;
                swipeRefresh.setRefreshing(false);
                renderWeather(response, preferences.getWeatherSavedAt());
                WeatherWidgetUpdater.updateAll(getApplicationContext());
            }
            @Override public void onError(Throwable error) {
                if (requestId != weatherRequestId) return;
                swipeRefresh.setRefreshing(false);
                Timber.w(error, "Weather refresh failed");
                WeatherResponse saved = preferences.getWeather();
                String message = saved != null && saved.isValid() ? getString(R.string.offline_cached)
                        : error instanceof IllegalStateException || error instanceof JsonParseException
                        ? getString(R.string.invalid_response) : getString(R.string.network_error);
                showStatus(message);
                if (saved == null) Snackbar.make(findViewById(R.id.swipe_refresh), message, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void renderWeather(WeatherResponse data, long savedAt) {
        if (data == null || !data.isValid()) { showStatus(getString(R.string.invalid_response)); return; }
        if (data.current.time != null && data.current.time.length() >= 10) {
            try {
                Date cityDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(data.current.time.substring(0, 10));
                if (cityDate != null) dateText.setText(new SimpleDateFormat("EEEE, d MMMM", LocaleUtils.getAppLocale(getResources())).format(cityDate));
            } catch (java.text.ParseException ignored) {
                dateText.setText(new SimpleDateFormat("EEEE, d MMMM", LocaleUtils.getAppLocale(getResources())).format(new Date()));
            }
        }
        String unit = preferences.isFahrenheit() ? "°F" : "°C";
        String windUnit = preferences.isMetersPerSecond() ? getString(R.string.meters_per_second) : getString(R.string.kilometers_per_hour);
        temperature.setText(getString(R.string.temperature_value, Math.round(data.current.temperature_2m), unit));
        description.setText(WeatherCodeMapper.descriptionResource(data.current.weather_code));
        weatherIcon.setImageResource(WeatherCodeMapper.iconResource(data.current.weather_code, data.current.is_day == null || data.current.is_day == 1));
        feelsLike.setText(data.current.apparent_temperature == null ? "—" : getString(R.string.temperature_value, Math.round(data.current.apparent_temperature), unit));
        humidity.setText(data.current.relative_humidity_2m == null ? "—" : Math.round(data.current.relative_humidity_2m) + "%");
        wind.setText(data.current.wind_speed_10m == null ? "—" : Math.round(data.current.wind_speed_10m) + " " + windUnit);
        int min = firstValue(data.daily.temperature_2m_min), max = firstValue(data.daily.temperature_2m_max);
        minMax.setText(getString(R.string.min_max_temperature, min, max, unit));
        int currentHour = findCurrentHour(data);
        List<HourlyForecast> hours = new ArrayList<>();
        if (data.hourly.time != null) for (int index=currentHour; index<data.hourly.time.size() && hours.size()<24; index++) {
            HourlyForecast hour = new HourlyForecast();
            String time = data.hourly.time.get(index);
            hour.time = time.length() >= 16 ? time.substring(11,16) : time;
            hour.temperature = valueAt(data.hourly.temperature_2m, index, 0);
            hour.weatherCode = valueAt(data.hourly.weather_code, index, 0);
            hour.precipitationProbability = nullableValueAt(data.hourly.precipitation_probability, index);
            hour.isDay = valueAt(data.hourly.is_day, index, 1) == 1;
            hours.add(hour);
        }
        hourlyAdapter.submit(hours);
        List<DailyForecast> days = new ArrayList<>();
        if (data.daily.time != null) for (int index=0; index<data.daily.time.size() && index<7; index++) {
            DailyForecast day = new DailyForecast();
            day.date=data.daily.time.get(index); day.minimum=valueAt(data.daily.temperature_2m_min,index,0);
            day.maximum=valueAt(data.daily.temperature_2m_max,index,0); day.weatherCode=valueAt(data.daily.weather_code,index,0);
            days.add(day);
        }
        dailyAdapter.submit(days);
        Integer chance = nullableValueAt(data.hourly.precipitation_probability, currentHour);
        precipitation.setText(chance == null ? "—" : getString(R.string.precipitation_percent, chance));
        lastUpdated.setText(getString(R.string.last_updated, new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(savedAt))));
        status.setVisibility(View.GONE);
    }

    private int findCurrentHour(WeatherResponse data) {
        if (data.hourly == null || data.hourly.time == null || data.hourly.time.isEmpty()) return 0;
        String currentTime = data.current.time;
        if (currentTime != null) for (int i=0; i<data.hourly.time.size(); i++) if (data.hourly.time.get(i).compareTo(currentTime) >= 0) return i;
        return 0;
    }
    private int firstValue(List<Double> values) { return values == null || values.isEmpty() ? 0 : (int) Math.round(values.get(0)); }
    private int valueAt(List<Integer> values, int index, int fallback) { return values == null || index < 0 || index >= values.size() || values.get(index) == null ? fallback : values.get(index); }
    private double valueAt(List<Double> values, int index, double fallback) { return values == null || index < 0 || index >= values.size() || values.get(index) == null ? fallback : values.get(index); }
    private Integer nullableValueAt(List<Integer> values, int index) { return values == null || index < 0 || index >= values.size() ? null : values.get(index); }
    private void showStatus(String message) { status.setText(message); status.setVisibility(View.VISIBLE); }
    private void refreshPreviousCityButton() { findViewById(R.id.previous_city_button).setVisibility(preferences.getPreviousCity() == null ? View.GONE : View.VISIBLE); }
}
