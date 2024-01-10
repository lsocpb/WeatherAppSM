package com.example.weatherappsm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SettingsLanguageActivity extends AppCompatActivity {
    String[] languageNames;

    RecyclerView recyclerView;

    int[] languageFlags = {R.drawable.flag_uk, R.drawable.flag_spain, R.drawable.flag_france,
            R.drawable.flag_germany, R.drawable.flag_italy, R.drawable.flag_portugal,
            R.drawable.flag_china, R.drawable.flag_japan, R.drawable.flag_korea, R.drawable.flag_india};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_language);

        recyclerView = findViewById(R.id.settingsRecyclerView);

        languageNames = getResources().getStringArray(R.array.languages);

        //create adapter and pass data to it

        SettingsLanguageActivityAdapter settingsLanguageActivityAdapter = new SettingsLanguageActivityAdapter(languageNames, languageFlags);

        //set adapter to recyclerview

        recyclerView.setAdapter(settingsLanguageActivityAdapter);

        //set layout manager to recyclerview

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        settingsLanguageActivityAdapter.setOnItemClickListener(position -> {
            System.out.println("Language " + languageNames[position] + " clicked");
        });
    }



}