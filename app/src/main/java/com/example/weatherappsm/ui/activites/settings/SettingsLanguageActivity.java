package com.example.weatherappsm.ui.activites.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappsm.R;

public class SettingsLanguageActivity extends AppCompatActivity {
    String[] languageNames;

    RecyclerView recyclerView;

    int[] languageFlags = {R.drawable.flag_uk, R.drawable.flag_spain, R.drawable.flag_france,
            R.drawable.flag_germany};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_language);

        recyclerView = findViewById(R.id.settingsRecyclerView);

        languageNames = getResources().getStringArray(R.array.languages);

        SettingsLanguageActivityAdapter settingsLanguageActivityAdapter = new SettingsLanguageActivityAdapter(languageNames, languageFlags);

        recyclerView.setAdapter(settingsLanguageActivityAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        settingsLanguageActivityAdapter.setOnItemClickListener(position -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingsLanguageActivity.this);
            builder.setMessage(getString(R.string.change_language))
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        });
    }
}