package com.example.sirovarweather.utils;

import timber.log.Timber;

public final class LoggerConfig {
    private LoggerConfig() { }
    public static void initialize(boolean debug) {
        if (debug) Timber.plant(new Timber.DebugTree());
        else Timber.plant(new Timber.Tree() {
            @Override protected void log(int priority, String tag, String message, Throwable error) {
                if (priority >= android.util.Log.WARN) android.util.Log.println(priority, tag == null ? "SirovarWeather" : tag, message);
            }
        });
    }
}
