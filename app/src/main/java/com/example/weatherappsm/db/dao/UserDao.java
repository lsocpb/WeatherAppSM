package com.example.weatherappsm.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.weatherappsm.db.model.User;
import com.example.weatherappsm.db.model.UserAndSettings;
import com.example.weatherappsm.db.model.UserWithSearchHistory;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void deleteSync(User user);

    @Query("SELECT * FROM user WHERE name = :name")
    User getUserByName(String name);

    @Transaction
    @Query("SELECT * FROM User WHERE name = :name")
    UserAndSettings getUserAndSettings(String name);

    @Transaction
    @Query("SELECT * FROM User WHERE name = :name")
    UserWithSearchHistory getUsersWithSearchHistory(String name);

    @Delete
    void deleteAll(User... users);
}
