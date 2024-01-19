package com.example.weatherappsm;

public class WeatherCV {

    private final String time;
    private final String temp;
    private final String icon;
    private final String windSpeed;

    public WeatherCV(String time, String temp, String icon, String windSpeed) {
        this.time = time;
        this.temp = temp;
        this.icon = icon;
        this.windSpeed = windSpeed;
    }

    public String getTime() {
        return time;
    }

    public String getTemp() {
        return temp;
    }

    public String getIcon() {
        return icon;
    }

    public String getWindSpeed() {
        return windSpeed;
    }
}
