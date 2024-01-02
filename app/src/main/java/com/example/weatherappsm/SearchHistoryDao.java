package com.example.weatherappsm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SearchHistoryDao {
    @Insert
    void insert(SearchHistoryEntry entry);

    @Query("SELECT * FROM search_history ORDER BY date DESC")
    LiveData<List<SearchHistoryEntry>> getAllSearchHistory();
}

