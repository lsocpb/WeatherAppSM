package com.example.weatherappsm.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weatherappsm.db.model.Settings;


@Dao
public interface SettingsDao {

    @Insert
    void insert(Settings settings);

    @Update
    void update(Settings settings);

    @Delete
    void deleteSync(Settings settings);

    @Query("SELECT * FROM settings WHERE user_settings_id = :userId")
    Settings getSettingsByUserId(int userId);
}
