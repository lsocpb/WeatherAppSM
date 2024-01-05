package com.example.weatherappsm.api;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("current")
    private CurrentWeather current;


    public CurrentWeather getCurrent() {
        return current;
    }

}

