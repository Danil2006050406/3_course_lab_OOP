package com.example.sirovarweather.data.model;

import java.io.Serializable;

public class City implements Serializable {
    public String name;
    public String country;
    public String admin1;
    public double latitude;
    public double longitude;
    public String timezone;

    public String getDisplayName() {
        StringBuilder label = new StringBuilder(name == null ? "" : name);
        if (admin1 != null && !admin1.isEmpty() && !admin1.equals(name)) label.append(", ").append(admin1);
        if (country != null && !country.isEmpty()) label.append(", ").append(country);
        return label.toString();
    }
}
