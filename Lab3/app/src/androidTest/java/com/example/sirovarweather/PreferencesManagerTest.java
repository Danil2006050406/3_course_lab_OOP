package com.example.sirovarweather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.sirovarweather.data.local.PreferencesManager;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PreferencesManagerTest {
    @Test public void savesSettingsAcrossManagerInstances() {
        Context context = ApplicationProvider.getApplicationContext();
        context.getSharedPreferences("sirovar_weather_preferences", Context.MODE_PRIVATE).edit().clear().commit();
        PreferencesManager first = new PreferencesManager(context);
        first.setFahrenheit(true);
        first.setMetersPerSecond(true);
        first.setLanguage("en");
        first.setDarkTheme(true);
        PreferencesManager restored = new PreferencesManager(context);
        assertTrue(restored.isFahrenheit());
        assertTrue(restored.isMetersPerSecond());
        assertTrue(restored.isDarkTheme());
        assertEquals("en", restored.getLanguage());
    }
}
