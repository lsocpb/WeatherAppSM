package com.example.weatherappsm;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappsm.api.WeatherResponse;
import com.example.weatherappsm.manager.UserManager;
import com.example.weatherappsm.objects.LocationService;
import com.example.weatherappsm.objects.CustomLocation;
import com.example.weatherappsm.objects.Settings;
import com.example.weatherappsm.objects.User;
import com.example.weatherappsm.util.PermissionsUtil;
import com.example.weatherappsm.util.WeatherDataManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView idTVtemp, idTVcityName, idTVweatherText, idTVtempRange, idTVindex, idTVindexText;
    private ImageView idIVHomebg, idIVSearch, idIVtoolbar_1, idIVtoolbar_2, idIVlocationButton,
            idIVsettingsButton;

    private RelativeLayout idRLhome;

    public CustomLocation location;

    private WeatherDataManager weatherDataManager = new WeatherDataManager();


    private LinearLayout toolbar;
    private ArrayList<WeatherCV> weatherCVArrayList;
    private WeatherCVAdapter weatherCVAdapter;
    private RecyclerView weatherRV;

    private ProgressBar idProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idTVcityName = findViewById(R.id.idTVcityName);
        idTVtemp = findViewById(R.id.idTVtemp);
        idIVHomebg = findViewById(R.id.idIVHomebg);
        idRLhome = findViewById(R.id.idRLHome);
        toolbar = findViewById(R.id.toolbar);
        idIVtoolbar_1 = findViewById(R.id.toolbar_item1);
        idIVtoolbar_2 = findViewById(R.id.toolbar_item2);
        idIVlocationButton = findViewById(R.id.locationButton);
        weatherCVArrayList = new ArrayList<>();
        weatherCVAdapter = new WeatherCVAdapter(this, weatherCVArrayList);
        weatherRV = findViewById(R.id.idRVweather);
        weatherRV.setAdapter(weatherCVAdapter);
        idTVweatherText = findViewById(R.id.idTVtext);
        idIVsettingsButton = findViewById(R.id.settingsButton);
        idTVtempRange = findViewById(R.id.idTVtempRange);
        idTVindex = findViewById(R.id.idTVindex);
        idTVindexText = findViewById(R.id.idTVindexText);
        idProgressBar = findViewById(R.id.idProgressBar);

        //start location service (init LocationManager and Geocoder)
        LocationService.startLocationService(this);

        User user = UserManager.getInstance().getCurrentUser();
        List<String> searchHistory = user.getSearchHistory();
        List<CustomLocation> favoriteLocations = user.getFavoriteLocations();

        //TU SPRAWDZAM CZY INTENT PRZESŁAŁ JAKIEŚ NOWE DANE, JEŻELI TAK TO AKTUALIZUJE WIDOK
        Intent intent = getIntent();
        if (intent.hasExtra("cityName")) {
            String cityName = intent.getStringExtra("cityName");
            //location from SearchActivity
            location = LocationService.getInstance().getLocationByCityName(cityName);
        } else {
            location = LocationService.getInstance().fetchCurrentLocation(this);
        }
        updateWeatherData(location);

        //TU PRZEJŚCIE DO NOWEGO WIDOKU SEARCH ACTIVITY
        idIVlocationButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent1);
        });

        // REDIRECT TO MENU ACTIVITY
        idIVtoolbar_1.setOnClickListener(v -> {
            Intent intent12 = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent12);
        });

        idIVtoolbar_2.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + location.getCityName());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Uri mapWebsiteUri = Uri.parse("https://www.google.com/maps?q=" + location.getCityName());
                Intent mapWebsiteIntent = new Intent(Intent.ACTION_VIEW, mapWebsiteUri);
                startActivity(mapWebsiteIntent);
            }
        });

        // REDIRECT TO SETTINGS ACTIVITY
        idIVsettingsButton.setOnClickListener(v -> {
            Intent intent13 = new Intent(MainActivity.this, SettingsActivity.class);
            //intent.putExtra("cityName", cityName);
            startActivity(intent13);
        });
    }

    //TU SPRAWDZAM CZY UŻYTKOWNIK ZGADZA SIĘ NA UDOSTĘPNIENIE LOKALIZACJI
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsUtil.LOCATION_PERMISSION_REQUEST_CODE) {

            boolean isPermissionGranted = false;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    isPermissionGranted = true;
                    break;
                }
            }

            if (!isPermissionGranted) {
                Log.d("TAG", "PERMISSION DENIED");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Aplikacja wymaga dostępu do lokalizacji, udziel pozwolenia w ustawieniach aplikacji")
                        .setPositiveButton("OK", (dialog, id) -> {
                            finish();
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }

    //TU POBIERAM DANE O POGODZIE Z API
    private void updateWeatherData(CustomLocation location) {
        if (location == null || location.isEmpty()) {
            Log.e("TAG", "Location is null");
            return;
        }

        weatherDataManager.getWeatherData(location.getLatLong(), new WeatherDataManager.WeatherDataCallback() {
            @Override
            public void onWeatherDataReceived(WeatherResponse weatherResponse) {
                weatherCVArrayList.clear();

                User user = UserManager.getInstance().getCurrentUser();
                Settings userSettings = user.getSettings();
                Settings.TemperatureUnit userTempUnit = userSettings.getTemperatureUnit();

                WeatherResponse.CurrentWeather currentWeather = weatherResponse.getCurrent();
                WeatherResponse.ForecastWeather forecastWeather = weatherResponse.getForecastWeather();
                WeatherResponse.ForecastWeather.ForecastDay todayForecast = forecastWeather.getForecastday().get(0);
                WeatherResponse.ForecastWeather.ForecastDay.Day todayForecastDaily = todayForecast.getDay();
                List<WeatherResponse.ForecastWeather.ForecastDay.ForecastHour> todayForecastHourly = todayForecast.getHour();

                Picasso.get().load(currentWeather.isDay() ? getString(R.string.main_day_background_url) : getString(R.string.main_night_background_url)).into(idIVHomebg);

                String currentTempFormatted = currentWeather.getTemperatureFormatted(userTempUnit, false);
                Pair<String, String> minMaxFormatted = todayForecastDaily.getMinMaxFormatted(userTempUnit);

                idTVtemp.setText(currentTempFormatted);
                idTVcityName.setText(MainActivity.this.location.getCityName());
                idTVweatherText.setText(weatherResponse.getCurrent().getCondition().getText());
                idTVtempRange.setText(String.format("%s / %s", minMaxFormatted.first, minMaxFormatted.second));

                double valueofUV = weatherResponse.getCurrent().getUv();
                idProgressBar.setProgress((int) Math.round(valueofUV));
                idTVindex.setText(String.valueOf((valueofUV)));
                //<editor-fold desc="set uv index">
                if (weatherResponse.getCurrent().getUv() < 3) {
                    idTVindexText.setText(getString(R.string.low));
                } else if (weatherResponse.getCurrent().getUv() < 6) {
                    idTVindexText.setText(getString(R.string.moderate));
                } else if (weatherResponse.getCurrent().getUv() < 8) {
                    idTVindexText.setText(getString(R.string.high));
                } else if (weatherResponse.getCurrent().getUv() < 11) {
                    idTVindexText.setText(getString(R.string.very_high));
                } else {
                    idTVindexText.setText(getString(R.string.extreme));
                }
                //</editor-fold>

                for (WeatherResponse.ForecastWeather.ForecastDay.ForecastHour hour : todayForecastHourly) {
                    String time = hour.getTime();
                    String temp = String.valueOf(hour.getTempC());
                    String icon = hour.getCondition().getIcon();
                    String wind = String.valueOf(hour.getWindKph());
                    weatherCVArrayList.add(new WeatherCV(time, temp, icon, wind));
                }

                weatherCVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onWeatherDataError(String errorMessage) {

            }
        });
    }
}
