package com.example.weatherappsm.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.weatherappsm.db.dao.SearchHistoryDao;
import com.example.weatherappsm.db.dao.SettingsDao;
import com.example.weatherappsm.db.dao.UserDao;
import com.example.weatherappsm.db.model.SearchHistory;
import com.example.weatherappsm.db.model.Settings;
import com.example.weatherappsm.db.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {SearchHistory.class, User.class, Settings.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SearchHistoryDao searchHistoryDao();

    public abstract UserDao userDao();

    public abstract SettingsDao settingsDao();

    private static volatile AppDatabase INSTANCE;

    public static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "app_database"

                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
