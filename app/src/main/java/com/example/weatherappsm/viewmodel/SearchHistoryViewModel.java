package com.example.weatherappsm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.weatherappsm.model.SearchHistoryEntry;
import com.example.weatherappsm.repository.SearchHistoryRepository;

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


