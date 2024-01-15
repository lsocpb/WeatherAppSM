package com.example.weatherappsm.activities.settings;

import android.os.Bundle;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherappsm.R;

public class SettingsMeasurementsActivity extends AppCompatActivity {

    private RadioButton idBtnC, idBtnF, idBtnMS, idBtnKMH, idBtnMPH, idBtnHPA, idBtnMMHG, idBtnKM,
            idBtnM, idBtnMI, idBtn12h, idBtn24h;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_measurements);

        idBtnC = findViewById(R.id.idRBCelsius);
        idBtnF = findViewById(R.id.idRBFahrenheit);
        idBtnMS = findViewById(R.id.idRBMeterPerSecond);
        idBtnKMH = findViewById(R.id.idRBKilometerPerHour);
        idBtnMPH = findViewById(R.id.idRBMilesPerHour);
        idBtnHPA = findViewById(R.id.idRBHectoPascal);
        idBtnMMHG = findViewById(R.id.idRBMillimeterOfMercury);
        idBtnKM = findViewById(R.id.idRBKilometer);
        idBtnM = findViewById(R.id.idRBMeter);
        idBtnMI = findViewById(R.id.idRBMile);
        idBtn12h = findViewById(R.id.idRB12h);
        idBtn24h = findViewById(R.id.idRB24h);

        //add some listeners with debug messages to logcat

        idBtnC.setOnClickListener(v -> {
            System.out.println("Celsius button clicked");
        });
        idBtnF.setOnClickListener(v -> {
            System.out.println("Fahrenheit button clicked");
        });
        idBtn24h.setOnClickListener(v -> {
            System.out.println("24h button clicked");
        });
        idBtn12h.setOnClickListener(v -> {
            System.out.println("12h button clicked");
        });
        idBtnMS.setOnClickListener(v -> {
            System.out.println("Meters per second button clicked");
        });
        idBtnKMH.setOnClickListener(v -> {
            System.out.println("Kilometers per hour button clicked");
        });
        idBtnMPH.setOnClickListener(v -> {
            System.out.println("Miles per hour button clicked");
        });
        idBtnHPA.setOnClickListener(v -> {
            System.out.println("Hectopascals button clicked");
        });
        idBtnMMHG.setOnClickListener(v -> {
            System.out.println("Millimeters of mercury button clicked");
        });
        idBtnKM.setOnClickListener(v -> {
            System.out.println("Kilometers button clicked");
        });
        idBtnM.setOnClickListener(v -> {
            System.out.println("Meters button clicked");
        });
        idBtnMI.setOnClickListener(v -> {
            System.out.println("Miles button clicked");
        });
    }
}


