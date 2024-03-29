package com.example.weatherappsm.ui.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherappsm.R;
import com.example.weatherappsm.ui.activites.settings.SettingsFeatureRequestActivity;
import com.example.weatherappsm.ui.activites.settings.SettingsIssueReportActivity;
import com.example.weatherappsm.ui.activites.settings.SettingsLanguageActivity;
import com.example.weatherappsm.ui.activites.settings.SettingsMeasurementsActivity;
import com.example.weatherappsm.ui.activites.settings.SettingsNotificationsActivity;
import com.example.weatherappsm.db.UserMangerNew;

public class SettingsActivity extends AppCompatActivity {
    private LinearLayout idBtnMeasurements, idBtnChooseLanguage, idBtnReportIssue,
            idBtnRequestFeature, idBtnNotifications, idBtnResetSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        idBtnMeasurements = findViewById(R.id.idLLUnits);
        idBtnChooseLanguage = findViewById(R.id.idLLLanguage);
        idBtnReportIssue = findViewById(R.id.idLLReportIssue);
        idBtnRequestFeature = findViewById(R.id.idLLRequestFeature);
        idBtnNotifications = findViewById(R.id.idLLNotifications);
        idBtnResetSettings = findViewById(R.id.idLLResetSettings);


        idBtnMeasurements.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SettingsMeasurementsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        idBtnChooseLanguage.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SettingsLanguageActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        idBtnReportIssue.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SettingsIssueReportActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        idBtnRequestFeature.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SettingsFeatureRequestActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        idBtnNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SettingsNotificationsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        idBtnResetSettings.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
            builder.setMessage(getString(R.string.reset_settings))
                    .setPositiveButton(getString(R.string.yes), (dialog, id) -> {
                        // Implement reset settings
                        UserMangerNew.reset(getApplication());
                        finish();
                    })
                    .setNegativeButton(getString(R.string.no), (dialog, id) -> {
                        dialog.dismiss();
                    });
            builder.create().show();

        });


    }
}
