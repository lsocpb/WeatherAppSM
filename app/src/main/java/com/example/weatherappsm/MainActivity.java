package com.example.weatherappsm;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappsm.activities.FavoriteLocationActivity;
import com.example.weatherappsm.activities.SearchActivity;
import com.example.weatherappsm.activities.SettingsActivity;
import com.example.weatherappsm.api.WeatherResponse;
import com.example.weatherappsm.manager.UserManager;
import com.example.weatherappsm.objects.LocationService;
import com.example.weatherappsm.objects.CustomLocation;
import com.example.weatherappsm.db.model.Settings;
import com.example.weatherappsm.db.model.User;
import com.example.weatherappsm.util.PermissionsUtil;
import com.example.weatherappsm.util.WeatherDataManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeGestureListener.SwipeCallback {

    private TextView idTVtemp, idTVcityName, idTVweatherText, idTVtempRange, idTVindex,
            idTVindexText, idTVwindSpd, idTVWindDirection, idTVSunsetTime, idTVSunriseTime,
            idTVCloudValue, idTVRainValue;
    private ImageView idIVHomebg, idIVSearch, idIVtoolbar_1, idIVtoolbar_2, idIVlocationButton,
            idIVsettingsButton, idIVSunIcon;

    private RadioButton checkedRadioButton, uncheckedRadioButton;

    private RelativeLayout idRLhome;

    public CustomLocation location;

    private WeatherDataManager weatherDataManager = new WeatherDataManager();


    private LinearLayout toolbar;
    private ArrayList<WeatherCV> weatherCVArrayList;
    private WeatherCVAdapter weatherCVAdapter;
    private RecyclerView weatherRV;

    private ProgressBar idProgressBar;

    private final Settings settings = UserManager.getInstance().getCurrentUser().getSettings();


    private GestureDetector gestureDetector;


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
        idTVwindSpd = findViewById(R.id.idTVWindSpeed);
        idTVSunriseTime = findViewById(R.id.idTVSunriseTime);
        idTVSunsetTime = findViewById(R.id.idTVSunsetTime);
        idIVSunIcon = findViewById(R.id.idIVSunIcon);
        idTVWindDirection = findViewById(R.id.idTVWindDirection);
        idTVCloudValue = findViewById(R.id.idTVCloudValue);
        idTVRainValue = findViewById(R.id.idTVRainValue);
        checkedRadioButton = findViewById(R.id.checked_radiobutton);
        uncheckedRadioButton = findViewById(R.id.unchecked_radiobutton);
        gestureDetector = new GestureDetector(this, new SwipeGestureListener(this));


        //start location service (init LocationManager and Geocoder)
        LocationService.startLocationService(this);

        checkPermissions();

        User user = UserManager.getInstance().getCurrentUser();
        List<String> searchHistory = user.getSearchHistory();

        //TU SPRAWDZAM CZY INTENT PRZESŁAŁ JAKIEŚ NOWE DANE, JEŻELI TAK TO AKTUALIZUJE WIDOK
        Intent intent = getIntent();
        if (intent.hasExtra("cityName")) {
            String cityName = intent.getStringExtra("cityName");
            //location from SearchActivity
            location = LocationService.getInstance().getLocationByCityName(cityName);
            updateWeatherData(location);
        }

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

    //after we're sure that user granted location permission
    public void load() {
        //set only if location is null (during first launch)
        if (location == null) {
            location = LocationService.getInstance().fetchCurrentLocation(this);
            updateWeatherData(location);
        }
    }

    public void checkPermissions() {
        if (PermissionsUtil.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                PermissionsUtil.hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            load();
        }
        //display dialog box to ask for permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PermissionsUtil.LOCATION_PERMISSION_REQUEST_CODE);
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
                            Intent intent = new Intent();
                            intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);

                            finish();
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            //user granted permission, we can load data
            load();
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
                Settings.HourFormat hourFormat = userSettings.getHourFormat();

                WeatherResponse.CurrentWeather currentWeather = weatherResponse.getCurrent();
                WeatherResponse.ForecastWeather forecastWeather = weatherResponse.getForecastWeather();
                WeatherResponse.ForecastWeather.ForecastDay todayForecast = forecastWeather.getForecastday().get(0);
                WeatherResponse.ForecastWeather.ForecastDay.Day todayForecastDaily = todayForecast.getDay();
                List<WeatherResponse.ForecastWeather.ForecastDay.ForecastHour> todayForecastHourly = todayForecast.getHour();
                WeatherResponse.ForecastWeather.ForecastDay.Astro astro = todayForecast.getAstro();

                idTVSunriseTime.setText(astro.getSunrise(hourFormat));
                idTVSunsetTime.setText(astro.getSunset(hourFormat));

                Picasso.get().load(currentWeather.isDay() ? getString(R.string.main_day_background_url) : getString(R.string.main_night_background_url)).into(idIVHomebg);

                idTVcityName.setText(location.getCityName());
                idTVweatherText.setText(currentWeather.getCondition().getText());
                idTVWindDirection.setText(currentWeather.getWindDir());
                idTVCloudValue.setText(String.format("%s%%", currentWeather.getCloud()));
                idTVRainValue.setText(String.format("%smm", currentWeather.getPercip()));

                // SET WIND SPEED AND UNIT BASED ON USER SETTINGS
                String currentTempFormatted = currentWeather.getTemperatureFormatted(userTempUnit, false);
                String currentWindSpeedFormatted = currentWeather.getWindSpeedFormatted(userSettings.getWindSpeedUnit());
                Pair<String, String> minMaxFormatted = todayForecastDaily.getMinMaxFormatted(userTempUnit);

                idTVtemp.setText(currentTempFormatted);
                idTVwindSpd.setText(currentWindSpeedFormatted);
                idTVtempRange.setText(String.format("%s / %s", minMaxFormatted.first, minMaxFormatted.second));

                //<editor-fold desc="set uv index">
                double valueofUV = weatherResponse.getCurrent().getUv();
                idProgressBar.setProgress((int) Math.round(valueofUV));
                idTVindex.setText(String.valueOf((valueofUV)));

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

                for (WeatherResponse.ForecastWeather.ForecastDay.ForecastHour hourlyForecast : todayForecastHourly) {
                    String time = hourlyForecast.getTime(hourFormat);
                    String temp = hourlyForecast.getTemperatureFormatted(userTempUnit);
                    String icon = hourlyForecast.getCondition().getIcon();
                    String wind = hourlyForecast.getWindSpeedFormatted(userSettings.getWindSpeedUnit());
                    weatherCVArrayList.add(new WeatherCV(time, temp, icon, wind));
                }

                weatherCVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onWeatherDataError(String errorMessage) {

            }
        });
    }


    public void changeToNextView() {
        checkedRadioButton.setChecked(false);
        uncheckedRadioButton.setChecked(true);
        Intent intent = new Intent(this, FavoriteLocationActivity.class);
        intent.putExtra("isChecked", checkedRadioButton.isChecked());
        intent.putExtra("isUnchecked", uncheckedRadioButton.isChecked());
        startActivity(intent);
        finish();
    }

    public void changeToPreviousView() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    @Override
    public void onSwipeLeft() {
        changeToNextView();
    }

    @Override
    public void onSwipeRight() {
        changeToPreviousView();
    }
}


