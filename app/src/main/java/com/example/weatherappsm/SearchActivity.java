package com.example.weatherappsm;

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

import com.example.weatherappsm.api.WeatherCondition;
import com.example.weatherappsm.api.WeatherResponse;
import com.example.weatherappsm.model.SearchHistoryEntry;
import com.example.weatherappsm.objects.CustomLocation;
import com.example.weatherappsm.objects.LocationService;
import com.example.weatherappsm.util.WeatherDataManager;
import com.example.weatherappsm.viewmodel.SearchHistoryViewModel;
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
                for (SearchHistoryEntry entry : searchHistoryEntries) {
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
                searchHistoryViewModel.insertSearchHistoryEntry(new SearchHistoryEntry(cityName, String.valueOf(new Date())));
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

        String query = location.getLatitude() + "," + location.getLongitude();

        weatherDataManager.getWeatherData(query, new WeatherDataManager.WeatherDataCallback() {
            @Override
            public void onWeatherDataReceived(WeatherResponse weatherResponse) {
                int isDay = weatherResponse.getCurrent().getIsDay();
                WeatherCondition weatherContidion = weatherResponse.getCurrent().getCondition();
                String text = weatherContidion.getText();
                String temperature = weatherResponse.getCurrent().getTemperature();
                int roundedTemperature = (int) Math.round(Double.parseDouble(temperature));
                idTVcurrentLocationTemp.setText(roundedTemperature + "°");
                idTVcurrentLocation.setText(cityName);
                idTVcurrentLocationWeatherText.setText(text);

                if (isDay == 0) {
                    Picasso.get().load("https://cdn.dribbble.com/userupload/11744357/file/original-602e2442005d88c4f84f72828f6334f0.jpg?resize=1024x586").into(idIVBackground);

                } else {
                    Picasso.get().load("https://cdn.dribbble.com/users/3091124/screenshots/6632880/sunny-watercolor-texture-background_1083-167_1x.jpg?resize=400x300&vertical=center").into(idIVBackground);
                }
            }

            @Override
            public void onWeatherDataError(String errorMessage) {
            }
        });
    }
}
