package com.example.sirovarweather.data.model;

import java.util.List;

public class WeatherResponse {
    public String timezone;
    public Current current;
    public Hourly hourly;
    public Daily daily;

    public boolean isValid() {
        return current != null && current.temperature_2m != null && current.weather_code != null
                && hourly != null && hasValues(hourly.time, hourly.temperature_2m)
                && hasValues(hourly.time, hourly.weather_code)
                && daily != null && hasValues(daily.time, daily.temperature_2m_min)
                && hasValues(daily.time, daily.temperature_2m_max)
                && hasValues(daily.time, daily.weather_code);
    }

    private boolean hasValues(List<?> times, List<?> values) {
        if (times == null || times.isEmpty() || values == null || values.size() < times.size()) return false;
        for (int index=0; index<times.size(); index++) if (times.get(index) == null || values.get(index) == null) return false;
        return true;
    }

    public static class Current {
        public String time;
        public Double temperature_2m;
        public Double relative_humidity_2m;
        public Double apparent_temperature;
        public Integer is_day;
        public Integer weather_code;
        public Double wind_speed_10m;
    }

    public static class Hourly {
        public List<String> time;
        public List<Double> temperature_2m;
        public List<Integer> weather_code;
        public List<Integer> precipitation_probability;
        public List<Integer> is_day;
    }

    public static class Daily {
        public List<String> time;
        public List<Double> temperature_2m_max;
        public List<Double> temperature_2m_min;
        public List<Integer> weather_code;
        public List<Integer> precipitation_probability_max;
    }
}
