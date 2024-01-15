package com.example.weatherappsm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private LinearLayout idBtnMeasurements, idBtnChooseLanguage, idBtnReportIssue, idBtnRequestFeature, idBtnNightMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        idBtnMeasurements = findViewById(R.id.idLLUnits);
        idBtnChooseLanguage = findViewById(R.id.idLLLanguage);
        idBtnReportIssue = findViewById(R.id.idLLReportIssue);
        idBtnRequestFeature = findViewById(R.id.idLLRequestFeature);
        idBtnNightMode = findViewById(R.id.idLLNightMode);



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
        idBtnNightMode.setOnClickListener(v -> {
            System.out.println("Night Mode button clicked");
        });



    }
}
