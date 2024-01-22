package com.example.weatherappsm.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.weatherappsm.api.WeatherApiService;
import com.example.weatherappsm.api.WeatherResponse;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDataManager {
    private static final WeatherDataManager instance = new WeatherDataManager();

    public static WeatherDataManager getInstance() {
        return instance;
    }

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
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        callback.onWeatherDataReceived(weatherResponse);
                    }
                } else {
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        try {
                            JSONObject jsonObject = new JSONObject(responseBody.string());
                            String errorMessage = jsonObject.getString("error");
                            callback.onWeatherDataError(errorMessage);
                        } catch (Exception e) {
                            Log.e("TAG", "Error getting weather data due API error: \n" + e.getMessage(), e);
                            callback.onWeatherDataError(e.getMessage());
                        }
                    }
                    Log.e("TAG", "Error getting weather data due API error: \n" + response.message());
                    callback.onWeatherDataError(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable throwable) {
                Log.e("TAG", "Error getting weather data: " + throwable.getMessage(), throwable);
                callback.onWeatherDataError(throwable.getMessage());
            }
        });
    }

    public interface WeatherDataCallback {
        void onWeatherDataReceived(WeatherResponse weatherResponse);

        void onWeatherDataError(String errorMessage);
    }


}
