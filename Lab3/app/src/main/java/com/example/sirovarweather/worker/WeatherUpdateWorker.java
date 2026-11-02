package com.example.sirovarweather.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sirovarweather.data.local.PreferencesManager;
import com.example.sirovarweather.data.model.City;
import com.example.sirovarweather.data.repository.WeatherRepository;
import com.example.sirovarweather.widget.WeatherWidgetUpdater;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class WeatherUpdateWorker extends Worker {
    private static final String UNIQUE_WORK_NAME = "weather_periodic_update";
    public WeatherUpdateWorker(@NonNull Context context, @NonNull WorkerParameters parameters) { super(context, parameters); }

    @NonNull @Override public Result doWork() {
        PreferencesManager preferences = new PreferencesManager(getApplicationContext());
        City city = preferences.getCity();
        if (city == null) return Result.success();
        try {
            WeatherRepository.getInstance().loadWeatherBlocking(getApplicationContext(), city);
            WeatherWidgetUpdater.updateAll(getApplicationContext());
            return Result.success();
        } catch (Exception error) {
            Timber.w(error, "Scheduled weather update failed");
            return Result.retry();
        }
    }

    public static void schedule(Context context) {
        Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(WeatherUpdateWorker.class, 6, TimeUnit.HOURS)
                .setConstraints(constraints).build();
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(UNIQUE_WORK_NAME,
                androidx.work.ExistingPeriodicWorkPolicy.KEEP, request);
    }
}
