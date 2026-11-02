package com.example.sirovarweather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.sirovarweather.R;
import com.example.sirovarweather.data.local.PreferencesManager;
import com.example.sirovarweather.data.model.City;
import com.example.sirovarweather.data.model.WeatherResponse;
import com.example.sirovarweather.ui.activity.MainActivity;
import com.example.sirovarweather.utils.WeatherCodeMapper;
import com.example.sirovarweather.utils.LocaleUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public final class WeatherWidgetUpdater {
    private WeatherWidgetUpdater() { }

    public static void updateAll(Context context) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        updateProvider(context, manager, new ComponentName(context, WeatherWidgetProvider.class), false);
        updateProvider(context, manager, new ComponentName(context, WeatherWidgetLargeProvider.class), true);
    }

    static void updateIds(Context context, AppWidgetManager manager, int[] ids, boolean large) {
        for (int id : ids) updateOne(context, manager, id, large);
    }

    private static void updateProvider(Context context, AppWidgetManager manager, ComponentName component, boolean large) {
        int[] ids = manager.getAppWidgetIds(component);
        updateIds(context, manager, ids, large);
    }

    private static void updateOne(Context context, AppWidgetManager manager, int id, boolean large) {
        PreferencesManager preferences = new PreferencesManager(context);
        City city = preferences.getCity();
        WeatherResponse weather = preferences.getWeather();
        int layout = large ? R.layout.widget_weather_large : R.layout.widget_weather_compact;
        RemoteViews views = new RemoteViews(context.getPackageName(), layout);
        views.setTextViewText(R.id.widget_city, city == null ? context.getString(R.string.choose_city) : city.getDisplayName());
        if (weather != null && weather.isValid()) {
            views.setTextViewText(R.id.widget_temperature,
                    context.getString(R.string.temperature_value, Math.round(weather.current.temperature_2m), preferences.isFahrenheit() ? "°F" : "°C"));
            views.setImageViewResource(R.id.widget_icon, WeatherCodeMapper.iconResource(weather.current.weather_code,
                    weather.current.is_day == null || weather.current.is_day == 1));
            views.setTextViewText(R.id.widget_description, context.getString(WeatherCodeMapper.descriptionResource(weather.current.weather_code)));
            if (large) {
                int min = weather.daily.temperature_2m_min == null || weather.daily.temperature_2m_min.isEmpty() ? 0 : (int) Math.round(weather.daily.temperature_2m_min.get(0));
                int max = weather.daily.temperature_2m_max == null || weather.daily.temperature_2m_max.isEmpty() ? 0 : (int) Math.round(weather.daily.temperature_2m_max.get(0));
                views.setTextViewText(R.id.widget_min_max, context.getString(R.string.min_max_temperature, min, max, preferences.isFahrenheit() ? "°F" : "°C"));
                String humidity = weather.current.relative_humidity_2m == null ? "—" : Math.round(weather.current.relative_humidity_2m) + "%";
                views.setTextViewText(R.id.widget_humidity, context.getString(R.string.widget_humidity_label, humidity));
                String windUnit = preferences.isMetersPerSecond() ? context.getString(R.string.meters_per_second) : context.getString(R.string.kilometers_per_hour);
                String wind = weather.current.wind_speed_10m == null ? "—" : Math.round(weather.current.wind_speed_10m) + " " + windUnit;
                views.setTextViewText(R.id.widget_wind, context.getString(R.string.widget_wind_label, wind));
                views.setTextViewText(R.id.widget_updated, new SimpleDateFormat("HH:mm", LocaleUtils.getAppLocale(context.getResources())).format(new Date(preferences.getWeatherSavedAt())));
            }
        } else {
            views.setTextViewText(R.id.widget_temperature, "—");
            views.setTextViewText(R.id.widget_description, context.getString(R.string.no_cached_weather));
        }
        Intent launch = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launch,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget_root, pendingIntent);
        manager.updateAppWidget(id, views);
        Timber.i("Weather widget updated");
    }
}
