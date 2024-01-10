package com.example.weatherappsm.manager;

import com.example.weatherappsm.objects.CustomLocation;
import com.example.weatherappsm.objects.Settings;
import com.example.weatherappsm.objects.User;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager instance;
    private User currentUser;

    private UserManager() {
        currentUser = mockUser();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    //MOCk!!
    private User mockUser() {
        List<CustomLocation> favoriteLocations = new ArrayList<>();
        favoriteLocations.add(new CustomLocation("London", 51.5074, 0.1278));
        favoriteLocations.add(new CustomLocation("Paris", 48.8566, 2.3522));
        favoriteLocations.add(new CustomLocation("New York", 40.7128, -74.0060));
        favoriteLocations.add(new CustomLocation("Tokyo", 35.6762, 139.6503));
        favoriteLocations.add(new CustomLocation("Moscow", 55.7558, 37.6173));

        List<String> searchHistory = new ArrayList<>();
        searchHistory.add("Ciechocinek");
        searchHistory.add("Warszawa");
        searchHistory.add("Kraków");
        searchHistory.add("Łódź");
        searchHistory.add("Wrocław");

        Settings settings = new Settings();
        settings.setTemperatureUnit(Settings.TemperatureUnit.CELSIUS);
        settings.setWindSpeedUnit(Settings.WindSpeedUnit.KILLOMETERS_PER_HOUR);
        settings.setHourFormat(Settings.HourFormat.TWENTY_FOUR);

        return new User("Default User", settings, favoriteLocations, searchHistory);
    }
}
