package com.example.weatherappsm;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    private Button idBtnBack, idBtnMeasurements, idBtnChooseLanguage, idBtnReportIssue,
            idBtnRequestFeature, idBtnNightMode;

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

        // back - on click redirect to main activity
        idBtnBack.setOnClickListener(v -> {
            finish();
        });

    }
}
