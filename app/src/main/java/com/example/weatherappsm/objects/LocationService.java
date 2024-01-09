package com.example.weatherappsm.objects;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.weatherappsm.util.PermissionsUtil;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//LocationManager is reserved class name
public class LocationService {
    private static LocationService instance;
    private final LocationManager locationManager;
    private final Geocoder geocoder;

    private LocationService(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        geocoder = new Geocoder(context, Locale.getDefault());
    }

    public static LocationService getInstance() {
        if (instance == null) {
            throw new RuntimeException("LocationServiceManager is not initialized");
        }
        return instance;
    }

    public static void startLocationService(Context context) {
        if (instance != null) {
            throw new RuntimeException("LocationServiceManager is already initialized");
        }
        instance = new LocationService(context);

        //we should also handle the case when user granted only one of the permissions
        if (!PermissionsUtil.hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) &&
                !PermissionsUtil.hasPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            //display dialog box to ask for permission
            PermissionsUtil.requestPermission(context, PermissionsUtil.LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }

    private android.location.Location getLastKnownLocation(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "No location permission", Toast.LENGTH_SHORT).show();
            return null;
        }
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

    @NonNull
    public CustomLocation getCurrentLocation(Context context) {
        android.location.Location lastKnownLocation = getLastKnownLocation(context);
        if (lastKnownLocation == null) {
            return new CustomLocation();
        }
        return new CustomLocation(lastKnownLocation, getCityName(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));
    }

    private String getCityName(double lat, double lon) {
        String cityName = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 10);
            if (addresses == null) {
                return null;
            }

            for (Address adr : addresses) {
                if (adr.getLocality() != null) {
                    String city = adr.getLocality();
                    if (city != null && !city.isEmpty()) {
                        cityName = city;
                    } else {
                        cityName = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }
}
