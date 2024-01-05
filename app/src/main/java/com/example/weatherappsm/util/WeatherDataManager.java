package com.example.weatherappsm.util;

import android.util.Log;

import com.example.weatherappsm.api.WeatherApiService;
import com.example.weatherappsm.api.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDataManager {

    private static final String API_KEY = "bb1ae4bd346c4643965140601233012";

    public void getWeatherData(String cityName, WeatherDataCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService apiService = retrofit.create(WeatherApiService.class);

        Call<WeatherResponse> call = apiService.getWeatherData(API_KEY, cityName, 1, "no", "no");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        callback.onWeatherDataReceived(weatherResponse);
                    }
                } else {
                    Log.e("TAG", "Error getting weather data: " + response.message());
                    callback.onWeatherDataError(response.message());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e("TAG", "Error getting weather data: " + t.getMessage());
                callback.onWeatherDataError(t.getMessage());
            }
        });
    }

    public interface WeatherDataCallback {
        void onWeatherDataReceived(WeatherResponse weatherResponse);

        void onWeatherDataError(String errorMessage);
    }


}
