package com.example.weatherappsm.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastDay {
    @SerializedName("date")
    private String date;

    @SerializedName("hour")
    private List<ForecastHour> hour;

    @SerializedName("day")
    private Day day;

    public String getDate() {
        return date;
    }

    public List<ForecastHour> getHour() {
        return hour;
    }

    public Day getDay() {
        return day;
    }
}
