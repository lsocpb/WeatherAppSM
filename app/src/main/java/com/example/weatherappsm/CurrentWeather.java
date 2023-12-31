package com.example.weatherappsm;

import com.google.gson.annotations.SerializedName;

public class CurrentWeather {
    @SerializedName("temp_c")
    private String temperature;

    public String getTemperature() {
        return temperature;
    }
}
