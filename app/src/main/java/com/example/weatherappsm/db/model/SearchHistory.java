package com.example.weatherappsm.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "search_history")
public class SearchHistory {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String cityName;
    public String date;

    public SearchHistory(String cityName, String date) {
        this.cityName = cityName;
        this.date = date;
    }
}
