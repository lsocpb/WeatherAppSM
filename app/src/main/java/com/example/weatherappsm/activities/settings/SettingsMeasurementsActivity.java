package com.example.weatherappsm.activities.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherappsm.R;
import com.example.weatherappsm.db.new_.model.Settings;
import com.example.weatherappsm.db.new_.UserMangerNew;
import com.google.android.material.snackbar.Snackbar;

public class SettingsMeasurementsActivity extends AppCompatActivity {

    private RadioButton idBtnC, idBtnF, idBtnKMH, idBtnMPH, idBtn12h, idBtn24h;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_measurements);

        idBtnC = findViewById(R.id.idRBCelsius);
        idBtnF = findViewById(R.id.idRBFahrenheit);
        idBtnKMH = findViewById(R.id.idRBKilometerPerHour);
        idBtnMPH = findViewById(R.id.idRBMilesPerHour);
        idBtn12h = findViewById(R.id.idRB12h);
        idBtn24h = findViewById(R.id.idRB24h);

        Settings settings = UserMangerNew.getInstance().getSettings();

        if (settings.getTemperatureUnit() == Settings.TemperatureUnit.CELSIUS)
            idBtnC.setChecked(true);
        else
            idBtnF.setChecked(true);

        if (settings.getWindSpeedUnit() == Settings.WindSpeedUnit.KILLOMETERS_PER_HOUR)
            idBtnKMH.setChecked(true);
        else
            idBtnMPH.setChecked(true);

        if (settings.getHourFormat() == Settings.HourFormat.TWELVE)
            idBtn12h.setChecked(true);
        else
            idBtn24h.setChecked(true);


        idBtnC.setOnClickListener(v -> {
            settings.setTemperatureUnit(Settings.TemperatureUnit.CELSIUS);
            UserMangerNew.getInstance().update();
            showSnackbar(getString(R.string.temperature_unit_changed) + " " + settings.getTemperatureUnit());
        });
        idBtnF.setOnClickListener(v -> {
            settings.setTemperatureUnit(Settings.TemperatureUnit.FAHRENHEIT);
            UserMangerNew.getInstance().update();
            showSnackbar(getString(R.string.temperature_unit_changed) + " " + settings.getTemperatureUnit());
        });

        idBtn24h.setOnClickListener(v -> {
            settings.setHourFormat(Settings.HourFormat.TWENTY_FOUR);
            UserMangerNew.getInstance().update();
            showSnackbar(getString(R.string.hour_format_changed) + " " + settings.getHourFormat());
        });
        idBtn12h.setOnClickListener(v -> {
            settings.setHourFormat(Settings.HourFormat.TWELVE);
            UserMangerNew.getInstance().update();
            showSnackbar(getString(R.string.hour_format_changed) + " " + settings.getHourFormat());
        });

        idBtnKMH.setOnClickListener(v -> {
            settings.setWindSpeedUnit(Settings.WindSpeedUnit.KILLOMETERS_PER_HOUR);
            UserMangerNew.getInstance().update();
            showSnackbar(getString(R.string.wind_speed_unit_changed) + " " + settings.getWindSpeedUnit());
        });
        idBtnMPH.setOnClickListener(v -> {
            settings.setWindSpeedUnit(Settings.WindSpeedUnit.MILES_PER_HOUR);
            UserMangerNew.getInstance().update();
            showSnackbar(getString(R.string.wind_speed_unit_changed) + " " + settings.getWindSpeedUnit());
        });

    }

    private void showSnackbar(String message) {
        View view = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}


