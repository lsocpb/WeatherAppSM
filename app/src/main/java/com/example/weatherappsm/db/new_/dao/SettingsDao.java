package com.example.weatherappsm.db.new_.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.weatherappsm.db.new_.model.Settings;


@Dao
public interface SettingsDao {

    @Insert
    void insert(Settings settings);

    @Update
    void update(Settings settings);
}
