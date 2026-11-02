package com.example.sirovarweather.data.repository;

import android.content.Context;

import com.example.sirovarweather.data.api.GeocodingApiService;
import com.example.sirovarweather.data.api.WeatherApiService;
import com.example.sirovarweather.data.local.PreferencesManager;
import com.example.sirovarweather.data.model.City;
import com.example.sirovarweather.data.model.GeocodingResponse;
import com.example.sirovarweather.data.model.WeatherResponse;

import java.util.Collections;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import timber.log.Timber;

public class WeatherRepository {
    public interface WeatherCallback { void onSuccess(WeatherResponse response); void onError(Throwable error); }
    public interface CitiesCallback { void onSuccess(List<City> cities); void onError(Throwable error); }

    private static final String WEATHER_URL = "https://api.open-meteo.com/";
    private static final String GEOCODING_URL = "https://geocoding-api.open-meteo.com/";
    private static WeatherRepository instance;
    private final WeatherApiService weatherApi;
    private final GeocodingApiService geocodingApi;

    private WeatherRepository() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Timber.d("HTTP %s", message));
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
        weatherApi = new Retrofit.Builder().baseUrl(WEATHER_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build().create(WeatherApiService.class);
        geocodingApi = new Retrofit.Builder().baseUrl(GEOCODING_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).build().create(GeocodingApiService.class);
    }

    public static synchronized WeatherRepository getInstance() {
        if (instance == null) instance = new WeatherRepository();
        return instance;
    }

    public void loadWeather(Context context, City city, WeatherCallback callback) {
        PreferencesManager preferences = new PreferencesManager(context);
        String temperatureUnit = preferences.isFahrenheit() ? "fahrenheit" : "celsius";
        String windUnit = preferences.isMetersPerSecond() ? "ms" : "kmh";
        Timber.i("Weather API request started");
        weatherApi.forecast(city.latitude, city.longitude,
                "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,weather_code,wind_speed_10m",
                "temperature_2m,weather_code,precipitation_probability,is_day",
                "weather_code,temperature_2m_max,temperature_2m_min,precipitation_probability_max",
                temperatureUnit, windUnit, 7, "auto").enqueue(new Callback<WeatherResponse>() {
            @Override public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (!response.isSuccessful()) {
                    Timber.w("Weather API returned HTTP %d", response.code());
                    callback.onError(new HttpException(response));
                } else if (response.body() != null && response.body().isValid()) {
                    preferences.saveWeather(response.body());
                    Timber.i("Weather received and cached");
                    callback.onSuccess(response.body());
                } else {
                    Timber.w("Weather API returned an invalid response (code %d)", response.code());
                    callback.onError(new IllegalStateException("Invalid weather response"));
                }
            }
            @Override public void onFailure(Call<WeatherResponse> call, Throwable error) {
                Timber.e(error, "Weather API request failed");
                callback.onError(error);
            }
        });
    }

    public void searchCities(String query, String language, CitiesCallback callback) {
        Timber.i("City search started");
        geocodingApi.search(query, 12, language, "json").enqueue(new Callback<GeocodingResponse>() {
            @Override public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<City> results = response.body().results == null ? Collections.emptyList() : response.body().results;
                    callback.onSuccess(results);
                } else callback.onError(new IllegalStateException("Invalid geocoding response"));
            }
            @Override public void onFailure(Call<GeocodingResponse> call, Throwable error) { callback.onError(error); }
        });
    }

    public WeatherResponse loadWeatherBlocking(Context context, City city) throws IOException {
        PreferencesManager preferences = new PreferencesManager(context);
        Response<WeatherResponse> response = weatherApi.forecast(city.latitude, city.longitude,
                "temperature_2m,relative_humidity_2m,apparent_temperature,is_day,weather_code,wind_speed_10m",
                "temperature_2m,weather_code,precipitation_probability,is_day",
                "weather_code,temperature_2m_max,temperature_2m_min,precipitation_probability_max",
                preferences.isFahrenheit() ? "fahrenheit" : "celsius",
                preferences.isMetersPerSecond() ? "ms" : "kmh", 7, "auto").execute();
        if (!response.isSuccessful() || response.body() == null || !response.body().isValid()) {
            throw new IOException("Open-Meteo returned invalid weather data");
        }
        preferences.saveWeather(response.body());
        return response.body();
    }
}
