package com.example.weatherappsm;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappsm.api.ForecastHour;
import com.example.weatherappsm.api.WeatherResponse;
import com.example.weatherappsm.manager.UserManager;
import com.example.weatherappsm.objects.LocationService;
import com.example.weatherappsm.objects.CustomLocation;
import com.example.weatherappsm.objects.User;
import com.example.weatherappsm.util.PermissionsUtil;
import com.example.weatherappsm.util.WeatherDataManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView idTVtemp, idTVcityName, idTVweatherText;
    private ImageView idIVHomebg, idIVSearch, idIVtoolbar_1, idIVtoolbar_2, idIVlocationButton;

    private RelativeLayout idRLhome;

    public CustomLocation location;

    private WeatherDataManager weatherDataManager = new WeatherDataManager();


    private LinearLayout toolbar;
    private ArrayList<WeatherCV> weatherCVArrayList;
    private WeatherCVAdapter weatherCVAdapter;
    private RecyclerView weatherRV;

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

        String query = location.getLatitude() + "," + location.getLongitude();
        weatherDataManager.getWeatherData(query, new WeatherDataManager.WeatherDataCallback() {
            @Override
            public void onWeatherDataReceived(WeatherResponse weatherResponse) {
                weatherCVArrayList.clear();
                String temperature = weatherResponse.getCurrent().getTemperature();
                int isDay = weatherResponse.getCurrent().getIsDay();
                if (isDay == 0) {
                    Picasso.get().load("https://i.pinimg.com/564x/65/aa/a7/65aaa7720b8ea21a6bda8db5d48aa5a4.jpg").into(idIVHomebg);
                } else {
                    Picasso.get().load("https://i.pinimg.com/564x/82/09/39/820939917d5be5f24e2e1c5de26457e6.jpg").into(idIVHomebg);
                }
                idTVtemp.setText(temperature + "°C");
                idTVcityName.setText(MainActivity.this.location.getCityName());
                idTVweatherText.setText(weatherResponse.getCurrent().getCondition().getText());
                List<ForecastHour> hourList = weatherResponse.getForecastWeather().getForecastday().get(0).getHour();
                for (ForecastHour hour : hourList) {
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
