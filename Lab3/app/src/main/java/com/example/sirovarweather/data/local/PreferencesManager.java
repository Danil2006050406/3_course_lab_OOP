package com.example.sirovarweather.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.sirovarweather.data.model.City;
import com.example.sirovarweather.data.model.WeatherResponse;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import timber.log.Timber;

public class PreferencesManager {
    private static final String FILE_NAME = "sirovar_weather_preferences";
    private final SharedPreferences preferences;
    private final Gson gson = new Gson();

    public PreferencesManager(Context context) {
        preferences = context.getApplicationContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public City getCity() { return readCity("city"); }
    public City getPreviousCity() { return readCity("previous_city"); }

    public void saveCity(City city) {
        City current = getCity();
        if (current != null && city != null && !current.getDisplayName().equals(city.getDisplayName())) {
            preferences.edit().putString("previous_city", gson.toJson(current))
                    .remove("weather").remove("weather_saved_at").apply();
        }
        preferences.edit().putString("city", gson.toJson(city)).apply();
    }

    public void restorePreviousCity() {
        City previous = getPreviousCity();
        if (previous != null) {
            City current = getCity();
            preferences.edit().putString("city", gson.toJson(previous))
                    .putString("previous_city", current == null ? null : gson.toJson(current)).apply();
        }
    }

    public void saveWeather(WeatherResponse weather) {
        preferences.edit().putString("weather", gson.toJson(weather))
                .putLong("weather_saved_at", System.currentTimeMillis()).apply();
        Timber.i("Cached weather updated");
    }

    public WeatherResponse getWeather() {
        String json = preferences.getString("weather", null);
        if (json == null) return null;
        try { return gson.fromJson(json, WeatherResponse.class); }
        catch (JsonParseException error) {
            Timber.w(error, "Saved weather could not be parsed");
            preferences.edit().remove("weather").remove("weather_saved_at").apply();
            return null;
        }
    }

    public long getWeatherSavedAt() { return preferences.getLong("weather_saved_at", 0L); }
    public boolean isFahrenheit() { return preferences.getBoolean("fahrenheit", false); }
    public boolean isMetersPerSecond() { return preferences.getBoolean("meters_per_second", false); }
    public String getLanguage() { return preferences.getString("language", "uk"); }
    public boolean isDarkTheme() { return preferences.getBoolean("dark_theme", false); }
    public void setFahrenheit(boolean enabled) {
        preferences.edit().putBoolean("fahrenheit", enabled).remove("weather").remove("weather_saved_at").apply();
    }
    public void setMetersPerSecond(boolean enabled) {
        preferences.edit().putBoolean("meters_per_second", enabled).remove("weather").remove("weather_saved_at").apply();
    }
    public void setLanguage(String language) { preferences.edit().putString("language", language).apply(); }
    public void setDarkTheme(boolean enabled) { preferences.edit().putBoolean("dark_theme", enabled).apply(); }

    private City readCity(String key) {
        String json = preferences.getString(key, null);
        if (json == null) return null;
        try { return gson.fromJson(json, City.class); }
        catch (JsonParseException error) {
            Timber.w(error, "Saved city could not be parsed");
            preferences.edit().remove(key).apply();
            return null;
        }
    }
}
