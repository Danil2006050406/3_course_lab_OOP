package com.example.sirovarweather;

import static org.junit.Assert.assertEquals;

import com.example.sirovarweather.utils.UnitConverter;
import org.junit.Test;

public class UnitConverterTest {
    @Test public void convertsTemperatureInBothDirections() {
        assertEquals(32.0, UnitConverter.celsiusToFahrenheit(0.0), 0.0001);
        assertEquals(0.0, UnitConverter.fahrenheitToCelsius(32.0), 0.0001);
    }
    @Test public void convertsWindSpeedInBothDirections() {
        assertEquals(10.0, UnitConverter.kilometersPerHourToMetersPerSecond(36.0), 0.0001);
        assertEquals(36.0, UnitConverter.metersPerSecondToKilometersPerHour(10.0), 0.0001);
    }
}
