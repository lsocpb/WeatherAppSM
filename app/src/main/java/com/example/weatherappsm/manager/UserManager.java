package com.example.weatherappsm.manager;

import com.example.weatherappsm.objects.CustomLocation;
import com.example.weatherappsm.db.model.Settings;
import com.example.weatherappsm.db.model.User;

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
        CustomLocation favoriteLocation = new CustomLocation("Ciechocinek", 52.8800, 18.7800);

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

        return new User("Default User", settings, favoriteLocation, searchHistory);
    }
}
