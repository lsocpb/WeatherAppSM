package com.example.weatherappsm.objects;

public class CustomLocation {
    private final String cityName;
    private final double latitude;
    private final double longitude;

    public CustomLocation(android.location.Location location, String cityName) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.cityName = cityName;
    }

    public CustomLocation(String cityName, double latitude, double longitude) {
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CustomLocation() {
        this.cityName = null;
        this.latitude = 0;
        this.longitude = 0;
    }

    public String getCityName() {
        return cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    /*
    * Returns true if any of the fields is empty
     */
    public boolean isEmpty() {
        return cityName == null || cityName.isEmpty() || latitude == 0 || longitude == 0;
    }
}
