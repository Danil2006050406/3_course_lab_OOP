package com.example.sirovarweather.utils;

public final class UnitConverter {
    private UnitConverter() { }
    public static double celsiusToFahrenheit(double celsius) { return celsius * 9.0 / 5.0 + 32.0; }
    public static double fahrenheitToCelsius(double fahrenheit) { return (fahrenheit - 32.0) * 5.0 / 9.0; }
    public static double kilometersPerHourToMetersPerSecond(double speed) { return speed / 3.6; }
    public static double metersPerSecondToKilometersPerHour(double speed) { return speed * 3.6; }
}
