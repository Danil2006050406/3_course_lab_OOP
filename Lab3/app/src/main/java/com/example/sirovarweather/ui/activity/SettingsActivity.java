package com.example.sirovarweather.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import com.example.sirovarweather.R;
import com.example.sirovarweather.data.local.PreferencesManager;
import com.example.sirovarweather.widget.WeatherWidgetUpdater;
import com.google.android.material.materialswitch.MaterialSwitch;

import timber.log.Timber;

public class SettingsActivity extends AppCompatActivity {
    private PreferencesManager preferences;
    private boolean initializing = true;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        preferences=new PreferencesManager(this);
        ((android.widget.RadioButton)findViewById(preferences.isFahrenheit() ? R.id.unit_fahrenheit : R.id.unit_celsius)).setChecked(true);
        ((android.widget.RadioButton)findViewById(preferences.isMetersPerSecond() ? R.id.unit_ms : R.id.unit_kmh)).setChecked(true);
        ((android.widget.RadioButton)findViewById("en".equals(preferences.getLanguage()) ? R.id.language_en : R.id.language_uk)).setChecked(true);
        MaterialSwitch darkTheme=findViewById(R.id.dark_theme_switch);
        darkTheme.setChecked(preferences.isDarkTheme());
        initializing=false;
        ((android.widget.RadioGroup)findViewById(R.id.temperature_group)).setOnCheckedChangeListener((group,id)->{
            if(initializing)return; preferences.setFahrenheit(id==R.id.unit_fahrenheit); WeatherWidgetUpdater.updateAll(getApplicationContext()); Timber.i("Temperature unit setting changed");
        });
        ((android.widget.RadioGroup)findViewById(R.id.wind_group)).setOnCheckedChangeListener((group,id)->{
            if(initializing)return; preferences.setMetersPerSecond(id==R.id.unit_ms); WeatherWidgetUpdater.updateAll(getApplicationContext()); Timber.i("Wind unit setting changed");
        });
        ((android.widget.RadioGroup)findViewById(R.id.language_group)).setOnCheckedChangeListener((group,id)->{
            if(initializing)return;
            String language=id==R.id.language_en ? "en" : "uk";
            preferences.setLanguage(language);
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language));
            WeatherWidgetUpdater.updateAll(getApplicationContext());
            Timber.i("Application language changed");
        });
        darkTheme.setOnCheckedChangeListener((button,checked)->{
            preferences.setDarkTheme(checked);
            AppCompatDelegate.setDefaultNightMode(checked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            WeatherWidgetUpdater.updateAll(getApplicationContext());
            Timber.i("Theme setting changed");
        });
    }
}
