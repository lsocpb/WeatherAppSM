package com.example.weatherappsm.db.new_;

import android.app.Application;
import android.util.Log;

import com.example.weatherappsm.MainActivity;
import com.example.weatherappsm.db.new_.model.Settings;
import com.example.weatherappsm.db.new_.model.User;
import com.example.weatherappsm.db.new_.repository.SettingsRepository;
import com.example.weatherappsm.db.new_.repository.UserRepository;
import com.example.weatherappsm.objects.CustomLocation;

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
                instance.currentUser.setFavoriteLocation(new CustomLocation("Ciechocinek", 52.8800, 18.7800));
                instance.currentUser.setId(1);
                instance.userRepository.insertSync(instance.currentUser);
                Log.d("UserMangerNew", "created new user: " + instance.currentUser.getId());
            }
            instance.settings = instance.settingsRepository.getSettingsByUserId(instance.currentUser.getId());
            if (instance.settings == null) {
                instance.settings = new Settings();
                instance.settings.setHourFormat(Settings.HourFormat.TWELVE);
                instance.settings.setTemperatureUnit(Settings.TemperatureUnit.CELSIUS);
                instance.settings.setWindSpeedUnit(Settings.WindSpeedUnit.KILLOMETERS_PER_HOUR);
                instance.settings.setNotificationFrequency(Settings.NotificationFrequency.EVERY_1_HOURS);
                instance.settings.setNotificationsEnabled(true);
                instance.settings.setId(1);
                instance.settings.setUserId(instance.currentUser.getId());

                instance.settingsRepository.insertSync(instance.settings);
                Log.d("UserMangerNew", "created new settings: " + instance.settings.getId());
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
                settings_.setHourFormat(Settings.HourFormat.TWELVE);
                settings_.setTemperatureUnit(Settings.TemperatureUnit.CELSIUS);
                settings_.setWindSpeedUnit(Settings.WindSpeedUnit.KILLOMETERS_PER_HOUR);
                settings_.setNotificationFrequency(Settings.NotificationFrequency.EVERY_1_HOURS);
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
