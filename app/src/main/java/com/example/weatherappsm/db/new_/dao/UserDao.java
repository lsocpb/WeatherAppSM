package com.example.weatherappsm.db.new_.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.weatherappsm.db.new_.model.User;
import com.example.weatherappsm.db.new_.model.UserAndSettings;
import com.example.weatherappsm.db.new_.model.UserWithSearchHistory;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT * FROM user WHERE name = :name")
    User getUserByName(String name);

    @Transaction
    @Query("SELECT * FROM User WHERE name = :name")
    List<UserAndSettings> getUsersAndLibraries(String name);

    @Transaction
    @Query("SELECT * FROM User WHERE name = :name")
    List<UserWithSearchHistory> getUsersWithPlaylists(String name);

    @Delete
    void deleteAll(User... users);
}
