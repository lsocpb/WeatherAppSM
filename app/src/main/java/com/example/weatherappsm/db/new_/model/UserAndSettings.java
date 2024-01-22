package com.example.weatherappsm.db.new_.model;

import androidx.room.Embedded;
import androidx.room.Relation;


public class UserAndSettings {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "user_settings_id"
    )
    public Settings settings;
}
