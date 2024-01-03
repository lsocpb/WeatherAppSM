package com.example.weatherappsm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private AutoCompleteTextView idACTVSearch;
    private SearchHistoryViewModel searchHistoryViewModel;
    private TextInputLayout idTILEdt;
    private ImageView idIVSearch;

    private String cityName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        idACTVSearch = findViewById(R.id.idACTVSearch);
        idTILEdt = findViewById(R.id.idTILEdt);
        idIVSearch = findViewById(R.id.idIVSearch);

        searchHistoryViewModel = new ViewModelProvider(this).get(SearchHistoryViewModel.class);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>());

        idACTVSearch.setAdapter(adapter);

        searchHistoryViewModel.getAllSearchHistory().observe(this, searchHistoryEntries -> {
            if (searchHistoryEntries != null) {
                List<String> cities = new ArrayList<>();
                for (SearchHistoryEntry entry : searchHistoryEntries) {
                    String cityName = entry.cityName;
                    if (!cities.contains(cityName)) {
                        cities.add(cityName);
                    }
                }
                adapter.clear();
                adapter.addAll(cities);
                adapter.notifyDataSetChanged();
            }
        });

        idIVSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchClick(v);
            }
        });
    }

    public void onSearchClick(View view) {
        String enteredCity = idACTVSearch.getText().toString();
        if (!enteredCity.isEmpty()) {
            cityName = enteredCity;
            searchHistoryViewModel.insertSearchHistoryEntry(new SearchHistoryEntry(cityName, String.valueOf(new Date())));
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            intent.putExtra("cityName", cityName);
            startActivity(intent);
            finish();
        } else {
            idACTVSearch.setError("Enter city name");
        }
    }
}
