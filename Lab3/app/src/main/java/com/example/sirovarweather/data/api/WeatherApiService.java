package com.example.sirovarweather.data.api;

import com.example.sirovarweather.data.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("v1/forecast")
    Call<WeatherResponse> forecast(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("current") String current,
            @Query("hourly") String hourly,
            @Query("daily") String daily,
            @Query("temperature_unit") String temperatureUnit,
            @Query("wind_speed_unit") String windSpeedUnit,
            @Query("forecast_days") int forecastDays,
            @Query("timezone") String timezone);
}
