package com.example.weatherappsm.activities.settings;

import android.os.Bundle;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherappsm.R;
import com.example.weatherappsm.db.model.Settings;
import com.example.weatherappsm.db.model.User;
import com.example.weatherappsm.manager.UserManager;

public class SettingsMeasurementsActivity extends AppCompatActivity {

    private RadioButton idBtnC, idBtnF, idBtnKMH, idBtnMPH, idBtnHPA, idBtnMMHG, idBtn12h, idBtn24h;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_measurements);

        idBtnC = findViewById(R.id.idRBCelsius);
        idBtnF = findViewById(R.id.idRBFahrenheit);
        idBtnKMH = findViewById(R.id.idRBKilometerPerHour);
        idBtnMPH = findViewById(R.id.idRBMilesPerHour);
        idBtnHPA = findViewById(R.id.idRBHectoPascal);
        idBtnMMHG = findViewById(R.id.idRBMillimeterOfMercury);
        idBtn12h = findViewById(R.id.idRB12h);
        idBtn24h = findViewById(R.id.idRB24h);

        User user = UserManager.getInstance().getCurrentUser();
        Settings settings = user.getSettings();

        idBtnC.setOnClickListener(v -> settings.setTemperatureUnit(Settings.TemperatureUnit.CELSIUS));
        idBtnF.setOnClickListener(v -> settings.setTemperatureUnit(Settings.TemperatureUnit.FAHRENHEIT));

        idBtn24h.setOnClickListener(v -> settings.setHourFormat(Settings.HourFormat.TWENTY_FOUR));
        idBtn12h.setOnClickListener(v -> settings.setHourFormat(Settings.HourFormat.TWELVE));

        idBtnKMH.setOnClickListener(v -> settings.setWindSpeedUnit(Settings.WindSpeedUnit.KILLOMETERS_PER_HOUR));
        idBtnMPH.setOnClickListener(v -> settings.setWindSpeedUnit(Settings.WindSpeedUnit.MILES_PER_HOUR));

        idBtnHPA.setOnClickListener(v -> System.out.println("Hectopascals button clicked"));
        idBtnMMHG.setOnClickListener(v -> System.out.println("Millimeters of mercury button clicked"));
    }
}


