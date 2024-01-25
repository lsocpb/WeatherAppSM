package com.example.weatherappsm;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappsm.activities.FavoriteLocationActivity;
import com.example.weatherappsm.activities.SearchActivity;
import com.example.weatherappsm.activities.SettingsActivity;
import com.example.weatherappsm.api.WeatherResponse;
import com.example.weatherappsm.db.new_.UserMangerNew;
import com.example.weatherappsm.db.new_.model.Settings;
import com.example.weatherappsm.db.new_.model.User;
import com.example.weatherappsm.db.new_.repository.SettingsRepository;
import com.example.weatherappsm.db.new_.repository.UserRepository;
import com.example.weatherappsm.manager.CustomNotificationManager;
import com.example.weatherappsm.manager.UserManager;
import com.example.weatherappsm.objects.LocationService;
import com.example.weatherappsm.objects.CustomLocation;
import com.example.weatherappsm.services.NotificationService;
import com.example.weatherappsm.util.PermissionsUtil;
import com.example.weatherappsm.util.WeatherDataManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity implements SwipeGestureListener.SwipeCallback {
    public static final String currentUser = "Default User";
    public static final Gson gson = new Gson();


    private TextView idTVtemp, idTVcityName, idTVweatherText, idTVtempRange, idTVindex,
            idTVindexText, idTVwindSpd, idTVWindDirection, idTVSunsetTime, idTVSunriseTime,
            idTVCloudValue, idTVRainValue, idTVPressure, idTVPressureMark;
    private ImageView idIVHomebg, idIVSearch, idIVtoolbar_1, idIVtoolbar_2, idIVlocationButton,
            idIVsettingsButton, idIVSunIcon;

    private ImageButton idIBfavorite;

    private RadioButton checkedRadioButton, uncheckedRadioButton;

    private RelativeLayout idRLhome;

    public CustomLocation location;

    private WeatherDataManager weatherDataManager = new WeatherDataManager();


    private LinearLayout toolbar;
    private ArrayList<WeatherCV> weatherCVArrayList;
    private WeatherCVAdapter weatherCVAdapter;
    private RecyclerView weatherRV;

    private ProgressBar idProgressBar;

    private final Settings settings = UserMangerNew.getInstance().getSettings();


    private GestureDetector gestureDetector;
    private SensorManager sensorManager;
    private Sensor pressureSensor;


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
        idTVPressure = findViewById(R.id.idTVPressure);
        idTVPressureMark = findViewById(R.id.idTVPresureMark);
        checkedRadioButton = findViewById(R.id.checked_radiobutton);
        uncheckedRadioButton = findViewById(R.id.unchecked_radiobutton);
        idIBfavorite = findViewById(R.id.idIBfav);
        gestureDetector = new GestureDetector(this, new SwipeGestureListener(this));

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        createDefaultUser();
        UserManager.init(getApplication());
        UserMangerNew.init(getApplication());
        createNotificationChannel();
        startService(new Intent(this, NotificationService.class));
//        testNotification();

        if (pressureSensor == null) {
            // fetch from api
            Log.e("TAG", "Pressure sensor not available");
        } else {
            sensorManager.registerListener(pressureListener, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }


        //start location service (init LocationManager and Geocoder)
        LocationService.startLocationService(this);

        checkPermissions();
        //checkFavoriteLocationIcon(location.getCityName());

        User user = UserMangerNew.getInstance().getCurrentUser();

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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // REDIRECT TO MENU ACTIVITY
        idIVtoolbar_1.setOnClickListener(v -> {
            Intent intent12 = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent12);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // Favorite location icon check

        User currentUser = UserMangerNew.getInstance().getCurrentUser();
        CustomLocation favoriteLocation = currentUser.getFavoriteLocationAsObject();
        if (favoriteLocation == null) {
            favoriteLocation = new CustomLocation("Ciechocinek", 52.8800, 18.7800);
            currentUser.setFavoriteLocation(favoriteLocation);
            UserMangerNew.getInstance().update();
        }

        if (favoriteLocation.getCityName().equals(location.getCityName())) {
            idIBfavorite.setImageResource(R.drawable.baseline_favorite_24);
        } else {
            idIBfavorite.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        // Favorite location button listener

        idIBfavorite.setOnClickListener(v -> {
            CustomLocation favLoc = currentUser.getFavoriteLocationAsObject();

            if (favLoc.getCityName().equals(location.getCityName())) {
                // mock
                currentUser.setFavoriteLocation(new CustomLocation("Ciechocinek", 52.8800, 18.7800));
                idIBfavorite.setImageResource(R.drawable.baseline_favorite_border_24);
            } else {
                currentUser.setFavoriteLocation(location);
                idIBfavorite.setImageResource(R.drawable.baseline_favorite_24);
            }

            UserMangerNew.getInstance().update();

            showSnackbar(String.format("Favorite location changed to %s", favLoc.getCityName()));
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
                builder.setMessage(getString(R.string.locationPermissionDeniedMessage))
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

    private void createDefaultUser() {
        UserRepository userRepository = new UserRepository(getApplication());

        new Thread(() -> {

        }).start();


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

                com.example.weatherappsm.db.new_.model.Settings userSettings = UserMangerNew.getInstance().getSettings();
                com.example.weatherappsm.db.model.Settings.TemperatureUnit userTempUnit = userSettings.getTemperatureUnit();
                com.example.weatherappsm.db.model.Settings.HourFormat hourFormat = userSettings.getHourFormat();

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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
        if (getClass().equals(FavoriteLocationActivity.class)) {
            changeToPreviousView();
        }
    }

    private final SensorEventListener pressureListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float pressureValue = event.values[0];
            updatePressureTextView(pressureValue);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void updatePressureTextView(float pressureValue) {
        if (pressureValue < 1013) {
            idTVPressureMark.setText("LOW");
        } else if (pressureValue > 1013 || pressureValue < 1017) {
            idTVPressureMark.setText("OPTIMAL");
        } else {
            idTVPressureMark.setText("HIGH");
        }
        idTVPressure.setText(String.format(Locale.getDefault(), "%.0f hPa", pressureValue));
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CustomNotificationManager.NotificationChannelID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system. You can't change the importance
            // or other notification behaviors after this.
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void testNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CustomNotificationManager.NotificationChannelID)
                .setSmallIcon(R.drawable.atmospheric)
                .setContentTitle("WeatherApp")
                .setContentText("This is a test notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(1, builder.build());
    }

    private void showSnackbar(String message) {
        View view = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload the weather data when the user returns to the app
        updateWeatherData(location);
    }
}


