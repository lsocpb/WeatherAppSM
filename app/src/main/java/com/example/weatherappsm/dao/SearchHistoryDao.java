package com.example.weatherappsm.dao;
import com.example.weatherappsm.model.SearchHistoryEntry;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SearchHistoryEntry entry);

    @Query("SELECT * FROM search_history ORDER BY date DESC")
    LiveData<List<SearchHistoryEntry>> getAllSearchHistory();
}

