package com.example.weatherappsm.objects;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String name;
    private final Settings settings;
    private final List<CustomLocation> favoriteLocations;
    private final List<String> searchHistory;
    private String googleId; //reserved for future use

    public User(String name) {
        this.name = name;
        this.settings = new Settings();
        this.favoriteLocations = new ArrayList<>();
        this.searchHistory = new ArrayList<>();
    }

    public User(String name, Settings settings, List<CustomLocation> favoriteLocations, List<String> searchHistory) {
        this.name = name;
        this.settings = settings;
        this.favoriteLocations = favoriteLocations;
        this.searchHistory = searchHistory;
    }

    public String getName() {
        return name;
    }

    public Settings getSettings() {
        return settings;
    }

    public List<CustomLocation> getFavoriteLocations() {
        return favoriteLocations;
    }

    public List<String> getSearchHistory() {
        return searchHistory;
    }

    public void addSearchHistoryEntry(String searchHistory) {
        this.searchHistory.add(searchHistory);
    }

    public void removeSearchHistoryEntry(String searchHistory) {
        this.searchHistory.remove(searchHistory);
    }

    public void clearSearchHistory() {
        this.searchHistory.clear();
    }

    public String getGoogleId() {
        return googleId;
    }
}
