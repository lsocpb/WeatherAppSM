package com.example.weatherappsm.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastWeather {
    @SerializedName("forecastday")
    private List<ForecastDay> forecastday;

    public List<ForecastDay> getForecastday() {
        return forecastday;
    }

}
