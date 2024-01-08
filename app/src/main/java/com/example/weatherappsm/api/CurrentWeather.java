package com.example.weatherappsm.api;

import com.google.gson.annotations.SerializedName;


public class CurrentWeather {
    @SerializedName("temp_c")
    private String temperature;

    @SerializedName("is_day")
    private int isDay;

    @SerializedName("condition")
    private WeatherCondition condition;

    @SerializedName("uv")
    private double uv;

    public String getTemperature() {
        return temperature;
    }
    public int getIsDay() {
        return isDay;
    }

    public WeatherCondition getCondition() {
        return condition;
    }

    public double getUv() {
        return uv;
    }
}
