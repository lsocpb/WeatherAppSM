package com.example.weatherappsm.activities.settings;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherappsm.R;

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

        idBtnEnableNotifications.setOnClickListener(v -> {
            System.out.println("Enable notifications button clicked");
        });
        idBtnDisableNotifications.setOnClickListener(v -> {
            System.out.println("Disable notifications button clicked");
        });
        idBtn1h.setOnClickListener(v -> {
            System.out.println("1h button clicked");
        });
        idBtn2h.setOnClickListener(v -> {
            System.out.println("2h button clicked");
        });
        idBtn3h.setOnClickListener(v -> {
            System.out.println("3h button clicked");
        });
        idBtn6h.setOnClickListener(v -> {
            System.out.println("6h button clicked");
        });
        idBtnOnceADay.setOnClickListener(v -> {
            System.out.println("Once a day button clicked");
        });


        LinearLayout startTime = findViewById(R.id.idLLStartTime);
        LinearLayout endTime = findViewById(R.id.idLLEndTime);
        TextView startTimeText = findViewById(R.id.idTVStartTime);
        TextView endTimeText = findViewById(R.id.idTVEndTime);

        startTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(SettingsNotificationsActivity.this,
                    (view, hourOfDay, minute) -> startTimeText.setText((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + (minute < 10 ? minute + "0" : minute)), 0, 0, true);
            timePickerDialog.show();
        });

        endTime.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(SettingsNotificationsActivity.this,
                    (view, hourOfDay, minute) -> endTimeText.setText((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay) + ":" + (minute < 10 ? minute + "0" : minute)), 0, 0, true);
            timePickerDialog.show();
        });

    }
}
