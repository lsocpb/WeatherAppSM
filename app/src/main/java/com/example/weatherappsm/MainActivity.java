package com.example.weatherappsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weatherappsm.api.WeatherResponse;
import com.example.weatherappsm.util.WeatherDataManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView idTVtemp, idTVcityName;
    private ImageView idIVHomebg, idIVSearch, idIVtoolbar_1, idIVtoolbar_2, idIVlocationButton;

    private RelativeLayout idRLhome;

    private LocationManager locationManager;
    private int PERMISSION_CODE = 1;

    public String cityName;

    private WeatherDataManager weatherDataManager = new WeatherDataManager();


    private LinearLayout toolbar;

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

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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



        //TU SPRAWDZAM CZY INTENT PRZESŁAŁ JAKIEŚ NOWE DANE, JEŻELI TAK TO AKTUALIZUJE WIDOK
        Intent intent = getIntent();
        if (intent.hasExtra("cityName")) {
            cityName = intent.getStringExtra("cityName");
            getWeatherData(cityName);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                cityName = getCityName(location.getLatitude(), location.getLongitude());
                getWeatherData(cityName);
            }
        }

        //TU PRZEJŚCIE DO NOWEGO WIDOKU SEARCH ACTIVITY
        idIVlocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // REDIRECT TO MENU ACTIVITY
        idIVtoolbar_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        idIVtoolbar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + cityName);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Uri mapWebsiteUri = Uri.parse("https://www.google.com/maps?q=" + cityName);
                    Intent mapWebsiteIntent = new Intent(Intent.ACTION_VIEW, mapWebsiteUri);
                    startActivity(mapWebsiteIntent);
                }
            }
        });
    }

    //TU SPRAWDZAM CZY UŻYTKOWNIK ZGADZA SIĘ NA UDOSTĘPNIENIE LOKALIZACJI
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("TAG", "PERMISSION GRANTED");
            } else {
                Log.d("TAG", "PERMISSION DENIED");
                finish();
            }
        }
    }

    //TU USTALAM NAZWĘ MIASTA NA PODSTAWIE DANYCH OTRZYMANYCH Z API
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

    //TU POBIERAM DANE O POGODZIE Z API
    private void getWeatherData(String cityName) {
        weatherDataManager.getWeatherData(cityName, new WeatherDataManager.WeatherDataCallback() {
            @Override
            public void onWeatherDataReceived(WeatherResponse weatherResponse) {
                String temperature = weatherResponse.getCurrent().getTemperature();
                int isDay = weatherResponse.getCurrent().getIsDay();
                if (isDay == 0) {
                    Picasso.get().load("https://cdn.dribbble.com/users/925716/screenshots/3333720/attachments/722375/night.png").into(idIVHomebg);
                } else {
                    Picasso.get().load("https://cdn.dribbble.com/users/925716/screenshots/3333720/attachments/722376/after_noon.png").into(idIVHomebg);
                }
                idTVtemp.setText(temperature + "°C");
                idTVcityName.setText(cityName);
            }

            @Override
            public void onWeatherDataError(String errorMessage) {
            }
        });
    }
}
