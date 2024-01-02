package com.example.weatherappsm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class SearchHistoryViewModel extends AndroidViewModel {

    private SearchHistoryRepository searchHistoryRepository;

    public SearchHistoryViewModel(@NonNull Application application) {
        super(application);
        searchHistoryRepository = new SearchHistoryRepository(application);
    }

    public void insertSearchHistoryEntry(SearchHistoryEntry entry) {
        searchHistoryRepository.insertSearchHistoryEntry(entry);
    }

    public LiveData<List<SearchHistoryEntry>> getAllSearchHistory() {
        return searchHistoryRepository.getAllSearchHistory();
    }
}


