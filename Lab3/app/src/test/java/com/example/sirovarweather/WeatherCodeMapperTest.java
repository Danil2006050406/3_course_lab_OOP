package com.example.sirovarweather;

import static org.junit.Assert.assertEquals;

import com.example.sirovarweather.utils.WeatherCodeMapper;
import org.junit.Test;

public class WeatherCodeMapperTest {
    @Test public void mapsClearAndRainCodes() {
        assertEquals(R.string.weather_clear, WeatherCodeMapper.descriptionResource(0));
        assertEquals(R.string.weather_rain, WeatherCodeMapper.descriptionResource(61));
    }
    @Test public void mapsSnowStormAndFogIcons() {
        assertEquals(R.drawable.ic_weather_snow, WeatherCodeMapper.iconResource(71, true));
        assertEquals(R.drawable.ic_weather_storm, WeatherCodeMapper.iconResource(95, true));
        assertEquals(R.drawable.ic_weather_fog, WeatherCodeMapper.iconResource(45, true));
    }
}
