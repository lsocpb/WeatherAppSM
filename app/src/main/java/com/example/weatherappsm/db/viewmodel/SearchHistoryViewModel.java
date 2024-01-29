package com.example.weatherappsm.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.weatherappsm.db.model.SearchHistory;
import com.example.weatherappsm.db.repository.SearchHistoryRepository;

import java.util.List;

public class SearchHistoryViewModel extends AndroidViewModel {

    private SearchHistoryRepository searchHistoryRepository;

    public SearchHistoryViewModel(@NonNull Application application) {
        super(application);
        searchHistoryRepository = new SearchHistoryRepository(application);
    }

    public void insertSearchHistoryEntry(SearchHistory entry) {
        searchHistoryRepository.insertSearchHistoryEntry(entry);
    }

    public LiveData<List<SearchHistory>> getAllSearchHistory() {
        return searchHistoryRepository.getAllSearchHistory();
    }
}


