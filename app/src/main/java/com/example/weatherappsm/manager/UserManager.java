package com.example.weatherappsm.manager;

import android.app.Application;

import com.example.weatherappsm.db.model.SearchHistory;
import com.example.weatherappsm.db.new_.repository.UserRepository;
import com.example.weatherappsm.objects.CustomLocation;
import com.example.weatherappsm.db.model.Settings;
import com.example.weatherappsm.db.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserManager {
    private static UserManager instance;
    private UserRepository userRepository;
    private User currentUser;

    private UserManager() {
        currentUser = mockUser();
    }

    public static void init(Application application) {
        instance = new UserManager();
        instance.userRepository = new UserRepository(application);
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

        List<SearchHistory> searchHistory = new ArrayList<>();
        searchHistory.add(new SearchHistory("Ciechocinek", new Date().toString()));
        searchHistory.add(new SearchHistory("Kraków", new Date().toString()));

        Settings settings = new Settings();
        settings.setTemperatureUnit(Settings.TemperatureUnit.CELSIUS);
        settings.setWindSpeedUnit(Settings.WindSpeedUnit.KILLOMETERS_PER_HOUR);
        settings.setHourFormat(Settings.HourFormat.TWENTY_FOUR);
        settings.setNotificationFrequency(Settings.NotificationFrequency.EVERY_1_HOURS);

        return new User("Default User", settings, favoriteLocation, searchHistory);
    }
}
