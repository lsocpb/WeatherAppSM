package com.example.weatherappsm.api;

import com.google.gson.annotations.SerializedName;

public class WeatherCondition {
    @SerializedName("text")
    private String text;

    @SerializedName("icon")
    private String icon;

    public String getText() {
        return text;

    }

    public String getIcon() {
        return icon;
    }
}