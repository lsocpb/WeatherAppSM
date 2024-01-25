package com.example.weatherappsm.db.new_.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.weatherappsm.MainActivity;
import com.example.weatherappsm.db.model.SearchHistory;
import com.example.weatherappsm.db.model.Settings;
import com.example.weatherappsm.objects.CustomLocation;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String name;
    private String favoriteLocation;

    public CustomLocation deserializeFavoriteLocation(String json) {
        return MainActivity.gson.fromJson(json, CustomLocation.class);
    }

    public String serializeFavoriteLocation(CustomLocation location) {
        return MainActivity.gson.toJson(location);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavoriteLocation() {
        return favoriteLocation;
    }

    public CustomLocation getFavoriteLocationAsObject() {
        return deserializeFavoriteLocation(favoriteLocation);
    }

    public void setFavoriteLocation(String favoriteLocation) {
        this.favoriteLocation = favoriteLocation;
    }

    public void setFavoriteLocation(CustomLocation favoriteLocation) {
        this.favoriteLocation = serializeFavoriteLocation(favoriteLocation);
    }
}

