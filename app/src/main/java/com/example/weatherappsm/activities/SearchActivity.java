package com.example.weatherappsm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherappsm.MainActivity;
import com.example.weatherappsm.R;
import com.example.weatherappsm.api.WeatherResponse;
import com.example.weatherappsm.db.model.SearchHistory;
import com.example.weatherappsm.manager.UserManager;
import com.example.weatherappsm.objects.CustomLocation;
import com.example.weatherappsm.objects.LocationService;
import com.example.weatherappsm.db.model.Settings;
import com.example.weatherappsm.db.model.User;
import com.example.weatherappsm.util.WeatherDataManager;
import com.example.weatherappsm.db.viewmodel.SearchHistoryViewModel;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private AutoCompleteTextView idACTVSearch;
    private SearchHistoryViewModel searchHistoryViewModel;
    private TextInputLayout idTILEdt;
    private ImageView idIVSearch, idIVBackground;

    private String cityName;

    private WeatherDataManager weatherDataManager = new WeatherDataManager();

    private TextView idTVcurrentLocationTemp, idTVcurrentLocation, idTVcurrentLocationWeatherText;

    private List<String> cities = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        idACTVSearch = findViewById(R.id.idACTVSearch);
        idTILEdt = findViewById(R.id.idTILEdt);
        idIVSearch = findViewById(R.id.idIVSearch);
        idIVBackground = findViewById(R.id.idIVBackground);
        idTVcurrentLocationTemp = findViewById(R.id.idTVcurrentLocationTemp);
        idTVcurrentLocation = findViewById(R.id.idTVcurrentLocation);
        idTVcurrentLocationWeatherText = findViewById(R.id.idTVcurrentLocationWeatherText);


        CustomLocation currentLocation = LocationService.getInstance().getCachedLocation();
        updateWeatherData(currentLocation);

        searchHistoryViewModel = new ViewModelProvider(this).get(SearchHistoryViewModel.class);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>());

        idACTVSearch.setAdapter(adapter);

        searchHistoryViewModel.getAllSearchHistory().observe(this, searchHistoryEntries -> {
            if (searchHistoryEntries != null) {
                for (SearchHistory entry : searchHistoryEntries) {
                    String cityName = entry.cityName;
                    if (!cities.contains(cityName)) {
                        cities.add(cityName);
                    }
                }
                adapter.clear();
                adapter.addAll(cities);
                adapter.notifyDataSetChanged();
            }
        });

        idIVSearch.setOnClickListener(this::onSearchClick);

    }

    public void onSearchClick(View view) {
        String enteredCity = idACTVSearch.getText().toString();
        if (!enteredCity.isEmpty()) {
            cityName = enteredCity;
            if (!cities.contains(cityName)) {
                searchHistoryViewModel.insertSearchHistoryEntry(new SearchHistory(cityName, String.valueOf(new Date())));
            }
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            intent.putExtra("cityName", cityName);
            startActivity(intent);
            finish();
        } else {
            idACTVSearch.setError("Enter city name");
        }
    }


    private void updateWeatherData(CustomLocation location) {
        if (location.isEmpty()) {
            //TODO: show error message
            Log.e("SearchActivity", "current location is empty");
            return;
        }

        weatherDataManager.getWeatherData(location.getLatLong(), new WeatherDataManager.WeatherDataCallback() {
            @Override
            public void onWeatherDataReceived(WeatherResponse weatherResponse) {
                User user = UserManager.getInstance().getCurrentUser();
                Settings userSettings = user.getSettings();
                Settings.TemperatureUnit userTempUnit = userSettings.getTemperatureUnit();

                WeatherResponse.CurrentWeather currentWeather = weatherResponse.getCurrent();
                WeatherResponse.WeatherCondition weatherCondition = currentWeather.getCondition();

                Picasso.get().load(currentWeather.isDay() ? getString(R.string.search_day_background_url) : getString(R.string.search_night_background_url)).into(idIVBackground);

                idTVcurrentLocationTemp.setText(currentWeather.getTemperatureFormatted(userTempUnit, true));
                idTVcurrentLocation.setText(cityName);
                idTVcurrentLocationWeatherText.setText(weatherCondition.getText());
            }

            @Override
            public void onWeatherDataError(String errorMessage) {
            }
        });
    }
}
