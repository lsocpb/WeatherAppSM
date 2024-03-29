package com.example.weatherappsm.ui.activites.settings;

import android.os.Bundle;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherappsm.R;
import com.example.weatherappsm.db.model.Settings;
import com.example.weatherappsm.db.UserMangerNew;
import com.example.weatherappsm.db.model.User;

public class SettingsNotificationsActivity extends AppCompatActivity {
    private RadioButton idBtnEnableNotifications, idBtnDisableNotifications,
            idBtn1h, idBtn2h, idBtn3h, idBtn6h, idBtnOnceADay;

    //private LinearLayout idBtnStartTime, idBtnEndTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_notifications);

        idBtnEnableNotifications = findViewById(R.id.idRBEnableNotifications);
        idBtnDisableNotifications = findViewById(R.id.idRBDisableNotifications);

        idBtn1h = findViewById(R.id.idRB1h);
        idBtn2h = findViewById(R.id.idRB2h);
        idBtn3h = findViewById(R.id.idRB3h);
        idBtn6h = findViewById(R.id.idRB6h);
        idBtnOnceADay = findViewById(R.id.idRBOnceADay);

        //add some listeners with debug messages to logcat
        User user = UserMangerNew.getInstance().getCurrentUser();
        com.example.weatherappsm.db.model.Settings settings = UserMangerNew.getInstance().getSettings();

        if (settings.isNotificationsEnabled())
            idBtnEnableNotifications.setChecked(true);
        else
            idBtnDisableNotifications.setChecked(true);

        if (settings.getNotificationFrequency() == Settings.NotificationFrequency.EVERY_1_HOURS)
            idBtn1h.setChecked(true);
        else if (settings.getNotificationFrequency() == Settings.NotificationFrequency.EVERY_2_HOURS)
            idBtn2h.setChecked(true);
        else if (settings.getNotificationFrequency() == Settings.NotificationFrequency.EVERY_3_HOURS)
            idBtn3h.setChecked(true);
        else if (settings.getNotificationFrequency() == Settings.NotificationFrequency.EVERY_6_HOURS)
            idBtn6h.setChecked(true);
        else if (settings.getNotificationFrequency() == Settings.NotificationFrequency.EVERY_24_HOURS)
            idBtnOnceADay.setChecked(true);

        idBtnEnableNotifications.setOnClickListener(v -> {
            settings.setNotificationsEnabled(true);
            UserMangerNew.getInstance().update();
        });
        idBtnDisableNotifications.setOnClickListener(v -> {
            System.out.println("Disable notifications button clicked");
            settings.setNotificationsEnabled(false);
            UserMangerNew.getInstance().update();
        });

        idBtn1h.setOnClickListener(v -> {
            System.out.println("1h button clicked");
            settings.setNotificationFrequency(Settings.NotificationFrequency.EVERY_1_HOURS);
            UserMangerNew.getInstance().update();
        });
        idBtn2h.setOnClickListener(v -> {
            System.out.println("2h button clicked");
            settings.setNotificationFrequency(Settings.NotificationFrequency.EVERY_2_HOURS);
            UserMangerNew.getInstance().update();
        });
        idBtn3h.setOnClickListener(v -> {
            System.out.println("3h button clicked");
            settings.setNotificationFrequency(Settings.NotificationFrequency.EVERY_3_HOURS);
            UserMangerNew.getInstance().update();
        });
        idBtn6h.setOnClickListener(v -> {
            System.out.println("6h button clicked");
            settings.setNotificationFrequency(Settings.NotificationFrequency.EVERY_6_HOURS);
            UserMangerNew.getInstance().update();
        });
        idBtnOnceADay.setOnClickListener(v -> {
            System.out.println("Once a day button clicked");
            settings.setNotificationFrequency(Settings.NotificationFrequency.EVERY_24_HOURS);
            UserMangerNew.getInstance().update();
        });

    }
}
