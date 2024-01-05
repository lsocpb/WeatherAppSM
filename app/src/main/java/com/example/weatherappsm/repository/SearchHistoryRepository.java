package com.example.weatherappsm.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.weatherappsm.db.AppDatabase;
import com.example.weatherappsm.dao.SearchHistoryDao;
import com.example.weatherappsm.model.SearchHistoryEntry;

import java.util.List;

public class SearchHistoryRepository {

    private SearchHistoryDao searchHistoryDao;

    public SearchHistoryRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        searchHistoryDao = appDatabase.searchHistoryDao();
    }

    public void insertSearchHistoryEntry(SearchHistoryEntry entry) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            searchHistoryDao.insert(entry);
        });
    }

    public LiveData<List<SearchHistoryEntry>> getAllSearchHistory() {
        return searchHistoryDao.getAllSearchHistory();
    }
}

