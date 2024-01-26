package com.example.weatherappsm.db.new_.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.weatherappsm.MainActivity;
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

    @Ignore
    public CustomLocation getFavoriteLocationAsObject() {
        return deserializeFavoriteLocation(favoriteLocation);
    }

    @Ignore
    public void setFavoriteLocationAsString(CustomLocation favoriteLocation) {
        this.favoriteLocation = serializeFavoriteLocation(favoriteLocation);
    }

    public void setFavoriteLocation(String favoriteLocation) {
        this.favoriteLocation = favoriteLocation;
    }
}

