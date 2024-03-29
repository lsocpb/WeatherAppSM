package com.example.weatherappsm.db.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithSearchHistory {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "user_search_history_id"
    )
    public List<SearchHistory> searchHistory;
}
