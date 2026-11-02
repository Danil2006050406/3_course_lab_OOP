package com.example.sirovarweather.utils;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public final class LocaleUtils {
    private LocaleUtils() { }

    public static Locale getAppLocale(Resources resources) {
        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) return configuration.getLocales().get(0);
        return configuration.locale == null ? Locale.getDefault() : configuration.locale;
    }
}
