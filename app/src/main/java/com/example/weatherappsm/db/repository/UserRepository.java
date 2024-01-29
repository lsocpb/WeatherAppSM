package com.example.weatherappsm.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.weatherappsm.db.AppDatabase;
import com.example.weatherappsm.db.dao.UserDao;
import com.example.weatherappsm.db.model.User;
import com.example.weatherappsm.db.model.UserWithSearchHistory;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Application application) {
        userDao = AppDatabase.getDatabase(application).userDao();
    }

    public void insert(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });
    }

    public void insertSync(User user) {
        userDao.insert(user);
    }

    public void update(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDao.update(user);
        });
    }

    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public void deleteSync(User user) {
        userDao.deleteSync(user);
    }

    public UserWithSearchHistory getUsersWithSearchHistory(String name) {
        return userDao.getUsersWithSearchHistory(name);
    }


    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private InsertAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }


}
