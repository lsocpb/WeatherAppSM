package com.example.weatherappsm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherappsm.R;
import com.example.weatherappsm.activities.settings.SettingsFeatureRequestActivity;
import com.example.weatherappsm.activities.settings.SettingsIssueReportActivity;
import com.example.weatherappsm.activities.settings.SettingsLanguageActivity;
import com.example.weatherappsm.activities.settings.SettingsMeasurementsActivity;
import com.example.weatherappsm.activities.settings.SettingsNotificationsActivity;

public class SettingsActivity extends AppCompatActivity {
    private LinearLayout idBtnMeasurements, idBtnChooseLanguage, idBtnReportIssue, idBtnRequestFeature, idBtnNotifications;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        idBtnMeasurements = findViewById(R.id.idLLUnits);
        idBtnChooseLanguage = findViewById(R.id.idLLLanguage);
        idBtnReportIssue = findViewById(R.id.idLLReportIssue);
        idBtnRequestFeature = findViewById(R.id.idLLRequestFeature);
        idBtnNotifications = findViewById(R.id.idLLNotifications);


        idBtnMeasurements.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SettingsMeasurementsActivity.class);
            startActivity(intent);
        });

        idBtnChooseLanguage.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SettingsLanguageActivity.class);
            startActivity(intent);
        });
        idBtnReportIssue.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SettingsIssueReportActivity.class);
            startActivity(intent);
        });
        idBtnRequestFeature.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SettingsFeatureRequestActivity.class);
            startActivity(intent);
        });
        idBtnNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SettingsNotificationsActivity.class);
            startActivity(intent);
        });


    }
}
