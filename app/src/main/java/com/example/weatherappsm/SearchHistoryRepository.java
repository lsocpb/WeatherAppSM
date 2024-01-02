package com.example.weatherappsm;

import android.app.Application;

import androidx.lifecycle.LiveData;

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

