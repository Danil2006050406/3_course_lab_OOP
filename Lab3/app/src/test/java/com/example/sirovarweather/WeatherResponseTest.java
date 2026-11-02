package com.example.sirovarweather;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.sirovarweather.data.model.WeatherResponse;
import com.google.gson.Gson;
import org.junit.Test;

public class WeatherResponseTest {
    @Test public void parsesValidOpenMeteoPayload() {
        String json = "{\"current\":{\"temperature_2m\":12.5,\"weather_code\":3},\"hourly\":{\"time\":[\"2026-10-30T12:00\"],\"temperature_2m\":[12.5],\"weather_code\":[3]},\"daily\":{\"time\":[\"2026-10-30\"],\"temperature_2m_min\":[8],\"temperature_2m_max\":[15],\"weather_code\":[3]}}";
        WeatherResponse result = new Gson().fromJson(json, WeatherResponse.class);
        assertTrue(result.isValid());
    }
    @Test public void rejectsMissingForecastSections() {
        WeatherResponse result = new Gson().fromJson("{\"current\":{\"temperature_2m\":12}}", WeatherResponse.class);
        assertFalse(result.isValid());
    }
}
