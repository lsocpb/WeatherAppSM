package com.example.weatherappsm.db.new_;

import android.app.Application;

import com.example.weatherappsm.MainActivity;
import com.example.weatherappsm.db.new_.model.Settings;
import com.example.weatherappsm.db.new_.model.User;
import com.example.weatherappsm.db.new_.repository.SettingsRepository;
import com.example.weatherappsm.db.new_.repository.UserRepository;
import com.example.weatherappsm.manager.UserManager;

import java.util.concurrent.CountDownLatch;

public class UserMangerNew {
    private static UserMangerNew instance;
    private UserRepository userRepository;
    private SettingsRepository settingsRepository;
    private User currentUser;
    private Settings settings;

    public static void init(Application application) {
        instance = new UserMangerNew();
        instance.userRepository = new UserRepository(application);
        instance.settingsRepository = new SettingsRepository(application);
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            instance.currentUser = instance.userRepository.getUserByName("Default User");
            if (instance.currentUser == null) {
                instance.currentUser = new User();
                instance.currentUser.setName("Default User");
                instance.currentUser.setFavoriteLocation("");
                instance.currentUser.setId(1);
                instance.userRepository.insert(instance.currentUser);
            }
            instance.settings = instance.settingsRepository.getSettingsByUserId(instance.currentUser.getId());
            if (instance.settings == null) {
                com.example.weatherappsm.db.new_.model.Settings settings_ = new com.example.weatherappsm.db.new_.model.Settings();
                settings_.setHourFormat(com.example.weatherappsm.db.model.Settings.HourFormat.TWELVE);
                settings_.setTemperatureUnit(com.example.weatherappsm.db.model.Settings.TemperatureUnit.CELSIUS);
                settings_.setWindSpeedUnit(com.example.weatherappsm.db.model.Settings.WindSpeedUnit.KILLOMETERS_PER_HOUR);
                settings_.setNotificationFrequency(com.example.weatherappsm.db.model.Settings.NotificationFrequency.EVERY_1_HOURS);
                settings_.setNotificationsEnabled(true);
                settings_.setId(1);
                settings_.setUserId(instance.currentUser.getId());

                instance.settings = settings_;
                instance.settingsRepository.insert(instance.settings);
            }
            latch.countDown();
        }).start();

        try {
            latch.await();
            System.out.println("UserMangerNew: " + instance.currentUser.getName());
            System.out.println("UserMangerNew: " + instance.settings.getTemperatureUnit());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void createDefaultUser(SettingsRepository settingsRepository, UserRepository userRepository) {
        com.example.weatherappsm.db.new_.model.User user = userRepository.getUserByName(MainActivity.currentUser);
        if (user == null) {
            com.example.weatherappsm.db.new_.model.User user_ = new com.example.weatherappsm.db.new_.model.User();
            user_.setName("Default User");
            user_.setFavoriteLocation("");
            user_.setId(1);
            userRepository.insert(user_);

            com.example.weatherappsm.db.new_.model.Settings settingsByUserId = settingsRepository.getSettingsByUserId(user_.getId());
            if (settingsByUserId == null) {
                com.example.weatherappsm.db.new_.model.Settings settings_ = new com.example.weatherappsm.db.new_.model.Settings();
                settings_.setHourFormat(com.example.weatherappsm.db.model.Settings.HourFormat.TWELVE);
                settings_.setTemperatureUnit(com.example.weatherappsm.db.model.Settings.TemperatureUnit.CELSIUS);
                settings_.setWindSpeedUnit(com.example.weatherappsm.db.model.Settings.WindSpeedUnit.KILLOMETERS_PER_HOUR);
                settings_.setNotificationFrequency(com.example.weatherappsm.db.model.Settings.NotificationFrequency.EVERY_1_HOURS);
                settings_.setNotificationsEnabled(true);
                settings_.setId(1);
                settings_.setUserId(user_.getId());

                settingsRepository.insert(settings_);
            }

            //magia
//                settingsRepository.getSettingsByUserId(1).observe(this, settings -> {
//                    System.out.println("settings: " + settings);
//                });
        }
    }

    public static UserMangerNew getInstance() {
        if (instance == null) {
            instance = new UserMangerNew();
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
