package com.example.weatherappsm.db.model;

import com.example.weatherappsm.objects.CustomLocation;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static final Gson gson = new Gson();

    public int id;

    private final String name;
    private final Settings settings;
    private String favoriteLocation;
    private final List<SearchHistory> searchHistory;
    private String googleId; //reserved for future use

    public User(String name) {
        this.name = name;
        this.settings = new Settings();
        this.favoriteLocation = serializeFavoriteLocation(new CustomLocation());
        this.searchHistory = new ArrayList<>();
    }

    public User(String name, Settings settings, CustomLocation favoriteLocation, List<SearchHistory> searchHistory) {
        this.name = name;
        this.settings = settings;
        this.favoriteLocation = serializeFavoriteLocation(favoriteLocation);
        this.searchHistory = searchHistory;
    }

    public String getName() {
        return name;
    }

    public Settings getSettings() {
        return settings;
    }

    public CustomLocation getFavoriteLocation() {
        CustomLocation favoriteLocation = deserializeFavoriteLocation(this.favoriteLocation);
        System.out.println("getFavoriteLocation: " + favoriteLocation.getCityName() + " " + favoriteLocation.getLatitude() + " " + favoriteLocation.getLongitude());

        return favoriteLocation;
    }

    public List<SearchHistory> getSearchHistory() {
        return searchHistory;
    }

    public void setFavoriteLocation(CustomLocation favoriteLocation) {
        this.favoriteLocation = serializeFavoriteLocation(favoriteLocation);
    }

    public void addSearchHistoryEntry(SearchHistory searchHistory) {
        this.searchHistory.add(searchHistory);
    }

    public void removeSearchHistoryEntry(SearchHistory searchHistory) {
        this.searchHistory.remove(searchHistory);
    }

    public void clearSearchHistory() {
        this.searchHistory.clear();
    }

    public String getGoogleId() {
        return googleId;
    }

    public CustomLocation deserializeFavoriteLocation(String json) {
        return gson.fromJson(json, CustomLocation.class);
    }

    public String serializeFavoriteLocation(CustomLocation location) {
        return gson.toJson(location);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFavoriteLocation(String favoriteLocation) {
        this.favoriteLocation = favoriteLocation;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

}
