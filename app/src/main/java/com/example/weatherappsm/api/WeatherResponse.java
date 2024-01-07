package com.example.weatherappsm.api;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("current")
    private CurrentWeather current;

    @SerializedName("forecast")
    private ForecastWeather forecast;
    public CurrentWeather getCurrent() {
        return current;
    }

    public ForecastWeather getForecastWeather() {
        return forecast;
    }
}

