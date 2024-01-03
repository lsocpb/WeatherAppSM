package com.example.weatherappsm;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "search_history")
public class SearchHistoryEntry {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String cityName;
    public String date;

    public SearchHistoryEntry(String cityName, String date) {
        this.cityName = cityName;
        this.date = date;
    }
}
