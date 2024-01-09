package com.example.weatherappsm.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    //TODO nest all classes in one file
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

    public static class ForecastWeather {
        @SerializedName("forecastday")
        private List<ForecastDay> forecastday;

        public List<ForecastDay> getForecastday() {
            return forecastday;
        }

    }

    public static class CurrentWeather {
        @SerializedName("temp_c")
        private String temperature;

        @SerializedName("is_day")
        private int isDay;

        @SerializedName("condition")
        private WeatherCondition condition;

        public String getTemperature() {
            return temperature;
        }

        public int getIsDay() {
            return isDay;
        }

        public WeatherCondition getCondition() {
            return condition;
        }
    }

}

