package com.example.weatherappsm.objects;

public class CustomLocation {
    private String cityName;
    private double latitude;
    private double longitude;

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

    public String getLatLong() {
        return latitude + "," + longitude;
    }

    /*
    * Returns true if any of the fields is empty
     */
    public boolean isEmpty() {
        return cityName == null || cityName.isEmpty() || latitude == 0 || longitude == 0;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
