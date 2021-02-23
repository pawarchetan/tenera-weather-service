package com.tenera.io.dto;

public enum Weather {
    THUNDERSTORM("thunderstorm"),
    DRIZZLE("Drizzle"),
    RAIN("Rain");

    private String description;

    Weather(String description) {
        this.description = description;
    }

    public static boolean shouldTakeUmbrella(String description) {
        for(Weather weather: Weather.values()) {
            if(weather.description.compareToIgnoreCase(description) == 0) {
                return true;
            }
        }
        return false;
    }
}
