package com.example.sirovarweather;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import com.example.sirovarweather.data.local.PreferencesManager;
import com.example.sirovarweather.utils.LoggerConfig;

import timber.log.Timber;

public class SirovarWeatherApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        LoggerConfig.initialize(BuildConfig.DEBUG);
        PreferencesManager preferences = new PreferencesManager(this);
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(preferences.getLanguage()));
        AppCompatDelegate.setDefaultNightMode(preferences.isDarkTheme()
                ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        Timber.i("SirovarWeather started");
    }
}
