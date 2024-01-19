package com.example.weatherappsm.db.dao;

import com.example.weatherappsm.db.model.SearchHistory;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SearchHistory entry);

    @Query("SELECT * FROM search_history ORDER BY date DESC")
    LiveData<List<SearchHistory>> getAllSearchHistory();
}

