package com.example.weatherappsm.manager;

import androidx.core.app.NotificationCompat;

public class CustomNotificationManager {
    public static final String NotificationChannelID = "WeatherAppChannelID";

    public static CustomNotificationManager instance;

    private CustomNotificationManager() {
    }

    public static CustomNotificationManager getInstance() {
        if (instance == null) {
            instance = new CustomNotificationManager();
        }
        return instance;
    }
}
