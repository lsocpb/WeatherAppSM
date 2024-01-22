package com.example.weatherappsm.db.new_.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.weatherappsm.db.AppDatabase;
import com.example.weatherappsm.db.new_.dao.SearchHistoryDao;
import com.example.weatherappsm.db.new_.model.SearchHistory;

import java.util.List;

public class SearchHistoryRepository {

    private SearchHistoryDao searchHistoryDao;

    public SearchHistoryRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        searchHistoryDao = appDatabase.searchHistoryDao();
    }

    public void insertSearchHistoryEntry(SearchHistory entry) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            searchHistoryDao.insert(entry);
        });
    }

    public LiveData<List<SearchHistory>> getAllSearchHistory() {
        return searchHistoryDao.getAllSearchHistory();
    }
}

