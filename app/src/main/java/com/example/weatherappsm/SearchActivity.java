package com.example.weatherappsm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private AutoCompleteTextView idACTVSearch;
    private SearchHistoryViewModel searchHistoryViewModel;
    private TextInputLayout idTILEdt;
    private ImageView idIVSearch, idIVBackground;

    private String cityName;

    private WeatherDataManager weatherDataManager = new WeatherDataManager();

    private int PERMISSION_CODE = 1;

    private TextView idTVcurrentLocationTemp, idTVcurrentLocation, idTVcurrentLocationWeatherText;
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


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                cityName = getCityName(location.getLatitude(), location.getLongitude());
                getWeatherData(cityName);
            } else {
                Log.e("TAG", "Location is null");
            }
        }


        searchHistoryViewModel = new ViewModelProvider(this).get(SearchHistoryViewModel.class);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>());

        idACTVSearch.setAdapter(adapter);

        searchHistoryViewModel.getAllSearchHistory().observe(this, searchHistoryEntries -> {
            if (searchHistoryEntries != null) {
                List<String> cities = new ArrayList<>();
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

        idIVSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchClick(v);
            }
        });

    }

    public void onSearchClick(View view) {
        String enteredCity = idACTVSearch.getText().toString();
        if (!enteredCity.isEmpty()) {
            cityName = enteredCity;
            searchHistoryViewModel.insertSearchHistoryEntry(new SearchHistoryEntry(cityName, String.valueOf(new Date())));
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            intent.putExtra("cityName", cityName);
            startActivity(intent);
            finish();
        } else {
            idACTVSearch.setError("Enter city name");
        }
    }

    private String getCityName(double lat, double lon) {
        String cityName = "London";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(lat, lon, 10);
            for (Address adr : addresses) {
                if (adr.getLocality() != null) {
                    String city = adr.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                    } else {
                        cityName = "Not Found";
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }



    private void getWeatherData(String cityName) {
        weatherDataManager.getWeatherData(cityName, new WeatherDataManager.WeatherDataCallback() {
            @Override
            public void onWeatherDataReceived(WeatherResponse weatherResponse) {
                int isDay = weatherResponse.getCurrent().getIsDay();
                WeatherContidion weatherContidion = weatherResponse.getCurrent().getCondition();
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
