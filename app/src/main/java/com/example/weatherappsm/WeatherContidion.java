package com.example.weatherappsm;

import com.google.gson.annotations.SerializedName;

public class WeatherContidion {
    @SerializedName("text")
    private String text;

    public String getText() {
        return text;

    }
}