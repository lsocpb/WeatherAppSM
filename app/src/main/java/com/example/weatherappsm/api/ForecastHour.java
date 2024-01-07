package com.example.weatherappsm.api;

import com.google.gson.annotations.SerializedName;

public class ForecastHour {
    @SerializedName("time")
    private String time;

    @SerializedName("temp_c")
    private double tempC;

    @SerializedName("wind_kph")
    private double windKph;

    @SerializedName("condition")
    private WeatherCondition condition;
    public String getTime() {
        return time;
    }

    public double getTempC() {
        return tempC;
    }


    public double getWindKph() {
        return windKph;
    }

    public WeatherCondition getCondition() {
        return condition;
    }

}
