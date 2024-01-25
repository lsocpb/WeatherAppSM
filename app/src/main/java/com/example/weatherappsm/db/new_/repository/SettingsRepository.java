package com.example.weatherappsm.db.new_.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.weatherappsm.db.AppDatabase;
import com.example.weatherappsm.db.new_.dao.SettingsDao;
import com.example.weatherappsm.db.new_.model.Settings;

public class SettingsRepository {
    private SettingsDao settingsDao;

    public SettingsRepository(Application application) {
        settingsDao = AppDatabase.getDatabase(application).settingsDao();
    }

    public void insert(Settings settings) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            settingsDao.insert(settings);
        });
    }

    public void update(Settings settings) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            settingsDao.update(settings);
        });
    }

    public Settings getSettingsByUserId(int userId) {
        return settingsDao.getSettingsByUserId(userId);
    }

    private static class InsertAsyncTask extends AsyncTask<Settings, Void, Void> {
        private SettingsDao settingsDao;

        private InsertAsyncTask(SettingsDao settingsDao) {
            this.settingsDao = settingsDao;
        }

        @Override
        protected Void doInBackground(Settings... settings) {
            settingsDao.insert(settings[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Settings, Void, Void> {
        private SettingsDao settingsDao;

        private UpdateAsyncTask(SettingsDao settingsDao) {
            this.settingsDao = settingsDao;
        }

        @Override
        protected Void doInBackground(Settings... settings) {
            settingsDao.update(settings[0]);
            return null;
        }
    }
}
