package com.example.weatherappsm.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastDay {
    @SerializedName("date")
    private String date;

    @SerializedName("hour")
    private List<ForecastHour> hour;

    public String getDate() {
        return date;
    }

    public List<ForecastHour> getHour() {
        return hour;
    }
}
