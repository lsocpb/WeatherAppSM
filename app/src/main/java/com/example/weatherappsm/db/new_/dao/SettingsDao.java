package com.example.weatherappsm.db.new_.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weatherappsm.db.new_.model.Settings;


@Dao
public interface SettingsDao {

    @Insert
    void insert(Settings settings);

    @Update
    void update(Settings settings);

    @Query("SELECT * FROM settings WHERE user_settings_id = :userId")
    Settings getSettingsByUserId(int userId);
}
