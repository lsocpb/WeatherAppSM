package com.example.weatherappsm.api;

import com.example.weatherappsm.api.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("v1/forecast.json")
    Call<WeatherResponse> getWeatherData(
            @Query("key") String apiKey,
            @Query("q") String cityName,
            @Query("days") int days,
            @Query("aqi") String aqi,
            @Query("alerts") String alerts
    );
}