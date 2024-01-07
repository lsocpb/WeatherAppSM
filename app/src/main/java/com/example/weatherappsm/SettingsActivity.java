package com.example.weatherappsm;

import android.os.Bundle;
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

        //add some listeners with debug messages to logcat

        idBtnMeasurements.setOnClickListener(v -> {
            System.out.println("Measurements button clicked");
        });
        idBtnChooseLanguage.setOnClickListener(v -> {
            System.out.println("Language button clicked");
        });
        idBtnReportIssue.setOnClickListener(v -> {
            System.out.println("Report Issue button clicked");
        });
        idBtnRequestFeature.setOnClickListener(v -> {
            System.out.println("Request Feature button clicked");
        });
        idBtnNightMode.setOnClickListener(v -> {
            System.out.println("Night Mode button clicked");
        });



    }
}
