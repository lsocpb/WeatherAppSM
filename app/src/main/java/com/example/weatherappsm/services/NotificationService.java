package com.example.weatherappsm.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.weatherappsm.R;
import com.example.weatherappsm.api.WeatherResponse;
import com.example.weatherappsm.db.new_.model.User;
import com.example.weatherappsm.db.new_.UserMangerNew;
import com.example.weatherappsm.db.new_.model.Settings;
import com.example.weatherappsm.manager.CustomNotificationManager;
import com.example.weatherappsm.util.WeatherDataManager;

public class NotificationService extends Service {

    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        startNotificationListener();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopNotificationListener();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void startNotificationListener() {
        // Start a new thread
        new Thread(() -> {
            // Run the notification task initially
            handler.post(notificationTask);
            // Schedule the task to run every hour
            Settings settings = UserMangerNew.getInstance().getSettings();
            handler.postDelayed(notificationTask, settings.getNotificationFrequency().getFrequency_ms());
        }).start();
    }

    private void stopNotificationListener() {
        handler.removeCallbacks(notificationTask);
    }

    private final Runnable notificationTask = new Runnable() {
        @Override
        public void run() {
            // Fetching notifications from the server
            // If there are notifications, call this method
            User user = UserMangerNew.getInstance().getCurrentUser();
            WeatherDataManager.getInstance().getWeatherData(user.getFavoriteLocationAsObject().getLatLong(), new WeatherDataManager.WeatherDataCallback() {
                @Override
                public void onWeatherDataReceived(WeatherResponse weatherResponse) {
                    showNotification(weatherResponse);
                }

                @Override
                public void onWeatherDataError(String errorMessage) {
                    // Show error message

                }
            });
//            showNotification();
            // Schedule the next run after the interval
            Settings settings = UserMangerNew.getInstance().getSettings();
            handler.postDelayed(this, settings.getNotificationFrequency().getFrequency_ms());
        }
    };

    public void showNotification(WeatherResponse response) {
        User user = UserMangerNew.getInstance().getCurrentUser();
        Settings settings = UserMangerNew.getInstance().getSettings();
        String tempNow = response.getCurrent().getTemperatureFormatted(settings.getTemperatureUnit(), false);
        String title = String.format(getString(R.string.notification_title), tempNow, user.getFavoriteLocationAsObject().getCityName());

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(getBaseContext(), CustomNotificationManager.NotificationChannelID)
                .setSmallIcon(R.drawable.atmospheric)
                .setContentTitle(title)
                .setContentText(getString(R.string.notification_click_for_details))
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .build();
        notificationManager.notify(0, notification);
    }
}