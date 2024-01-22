package com.example.weatherappsm.db.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class SearchHistory {

    public int id;
    public String cityName;
    public String date;

    public int userSearchHistoryId;

    public SearchHistory(String cityName, String date) {
        this.cityName = cityName;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserSearchHistoryId() {
        return userSearchHistoryId;
    }

    public void setUserSearchHistoryId(int userSearchHistoryId) {
        this.userSearchHistoryId = userSearchHistoryId;
    }
}
