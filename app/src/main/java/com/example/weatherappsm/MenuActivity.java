package com.example.weatherappsm;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    private Button idBtnMeasurements, idBtnChooseLanguage, idBtnReportIssue,
            idBtnRequestFeature, idBtnNightMode;

    private ImageView idBtnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        idBtnBack = findViewById(R.id.backButton);
        idBtnMeasurements = findViewById(R.id.measurementsButton);
        idBtnChooseLanguage = findViewById(R.id.chooseLanguageButton);
        idBtnReportIssue = findViewById(R.id.reportIssueButton);
        idBtnRequestFeature = findViewById(R.id.requestFeatureButton);
        idBtnNightMode = findViewById(R.id.switchToNightModeButton);

        idBtnBack.setOnClickListener(v -> {
            finish();
        });

    }
}
