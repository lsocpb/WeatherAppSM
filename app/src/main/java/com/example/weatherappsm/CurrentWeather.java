package com.example.weatherappsm;

import com.google.gson.annotations.SerializedName;


public class CurrentWeather {
    @SerializedName("temp_c")
    private String temperature;

    @SerializedName("is_day")
    private int isDay;

    @SerializedName("condition")
    private WeatherContidion condition;

    public String getTemperature() {
        return temperature;
    }
    public int getIsDay() {
        return isDay;
    }

    public WeatherContidion getCondition() {
        return condition;
    }
}
