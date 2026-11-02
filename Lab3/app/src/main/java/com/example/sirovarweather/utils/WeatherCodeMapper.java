package com.example.sirovarweather.utils;

import com.example.sirovarweather.R;

public final class WeatherCodeMapper {
    private WeatherCodeMapper() { }
    public static int descriptionResource(int code) {
        if (code == 0) return R.string.weather_clear;
        if (code == 1 || code == 2) return R.string.weather_partly_cloudy;
        if (code == 3) return R.string.weather_cloudy;
        if (code == 45 || code == 48) return R.string.weather_fog;
        if (code >= 51 && code <= 67 || code >= 80 && code <= 82) return R.string.weather_rain;
        if (code >= 71 && code <= 77 || code >= 85 && code <= 86) return R.string.weather_snow;
        if (code >= 95) return R.string.weather_thunderstorm;
        return R.string.weather_unknown;
    }
    public static int iconResource(int code, boolean day) {
        if (code == 0) return day ? R.drawable.ic_weather_sun : R.drawable.ic_weather_moon;
        if (code == 1 || code == 2) return R.drawable.ic_weather_partly_cloudy;
        if (code == 3) return R.drawable.ic_weather_cloud;
        if (code == 45 || code == 48) return R.drawable.ic_weather_fog;
        if (code >= 51 && code <= 67 || code >= 80 && code <= 82) return R.drawable.ic_weather_rain;
        if (code >= 71 && code <= 77 || code >= 85 && code <= 86) return R.drawable.ic_weather_snow;
        if (code >= 95) return R.drawable.ic_weather_storm;
        return R.drawable.ic_weather_cloud;
    }
}
