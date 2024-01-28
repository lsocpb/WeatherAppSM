package com.example.weatherappsm.db.new_;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.weatherappsm.db.new_.model.Settings;
import com.example.weatherappsm.db.new_.model.User;
import com.example.weatherappsm.db.new_.model.UserWithSearchHistory;
import com.example.weatherappsm.db.new_.repository.SearchHistoryRepository;
import com.example.weatherappsm.db.new_.repository.SettingsRepository;
import com.example.weatherappsm.db.new_.repository.UserRepository;
import com.example.weatherappsm.objects.CustomLocation;

import java.util.concurrent.CountDownLatch;

public class UserMangerNew {
    public static final String defaultUserName = "Default User";
    private static UserMangerNew instance;

    private UserRepository userRepository;
    private SettingsRepository settingsRepository;
    private SearchHistoryRepository searchHistoryRepository;
    private User currentUser;
    private Settings settings;

    public static void init(Application application) {
        instance = new UserMangerNew();
        instance.userRepository = new UserRepository(application);
        instance.settingsRepository = new SettingsRepository(application);
        instance.searchHistoryRepository = new SearchHistoryRepository(application);
        CountDownLatch latch = new CountDownLatch(1);

        new Thread(() -> {
            instance.currentUser = instance.userRepository.getUserByName(defaultUserName);
            if (instance.currentUser == null) {
                instance.currentUser = createDefaultUser();
                instance.userRepository.insertSync(instance.currentUser);
                Log.d("UserMangerNew", "Created new default user: " + instance.currentUser.getId());
            }
            instance.settings = instance.settingsRepository.getSettingsByUserId(instance.currentUser.getId());
            if (instance.settings == null) {
                instance.settings = createDefaultSettings(instance.currentUser);

                instance.settingsRepository.insertSync(instance.settings);
                Log.d("UserMangerNew", "Created new default settings: " + instance.settings.getId());
            }
            latch.countDown();
        }).start();

        //wait for the thread to finish
        try {
            latch.await();
            System.out.println("UserMangerNew: " + instance.currentUser.getName());
            System.out.println("UserMangerNew: " + instance.settings.getTemperatureUnit());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static User createDefaultUser() {
        User user = new User();
        user.setName(defaultUserName);
        user.setFavoriteLocationAsString(new CustomLocation("Ciechocinek", 52.8800, 18.7800));
        return user;
    }

    private static Settings createDefaultSettings(User user) {
        Settings settings = new Settings();
        settings.setHourFormat(Settings.HourFormat.TWELVE);
        settings.setTemperatureUnit(Settings.TemperatureUnit.CELSIUS);
        settings.setWindSpeedUnit(Settings.WindSpeedUnit.KILLOMETERS_PER_HOUR);
        settings.setNotificationFrequency(Settings.NotificationFrequency.EVERY_1_HOURS);
        settings.setNotificationsEnabled(true);
        settings.setUserId(user.getId());
        return settings;
    }

    public static void reset(Application application) {
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            UserWithSearchHistory history = instance.userRepository.getUsersWithSearchHistory(instance.getCurrentUser().getName());
            instance.searchHistoryRepository.deleteSearchHistorySync(history.searchHistory);
            instance.userRepository.deleteSync(instance.currentUser);
            instance.settingsRepository.deleteSync(instance.settings);
            instance.currentUser = createDefaultUser();
            instance.userRepository.insertSync(instance.currentUser);
            instance.settings = createDefaultSettings(instance.currentUser);
            instance.settingsRepository.insertSync(instance.settings);

            latch.countDown();

        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static UserMangerNew getInstance() {
        if (instance == null) {
            throw new RuntimeException("UserMangerNew not initialized");
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Settings getSettings() {
        return settings;
    }

    public void update() {
        new Thread(() -> {
            instance.userRepository.update(instance.currentUser);
            instance.settingsRepository.update(instance.settings);
        }).start();
    }
}
