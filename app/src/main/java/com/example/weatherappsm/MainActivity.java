package com.example.weatherappsm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView idTVtemp, idTVcityName;
    private ImageView idIVHomebg;

    private RelativeLayout idRLhome;

    private LocationManager locationManager;
    private int PERMISSION_CODE = 1;

    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idTVcityName = findViewById(R.id.idTVcityName);
        idTVtemp = findViewById(R.id.idTVtemp);
        idIVHomebg = findViewById(R.id.idIVHomebg);
        idRLhome = findViewById(R.id.idRLHome);

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
    }

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
        String url = "https://api.weatherapi.com/v1/forecast.json?key=bb1ae4bd346c4643965140601233012&q="+cityName+"&days=1&aqi=no&alerts=no";
        idTVcityName.setText(cityName);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String temperature = response.getJSONObject("current").getString("temp_c");
                            idTVtemp.setText(temperature);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "Error getting weather data: " + error.getMessage());
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);
    }
}