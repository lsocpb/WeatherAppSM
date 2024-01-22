package com.example.weatherappsm.db.new_.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.weatherappsm.db.AppDatabase;
import com.example.weatherappsm.db.new_.dao.UserDao;
import com.example.weatherappsm.db.new_.model.User;

import java.util.concurrent.Future;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Application application) {
        userDao = AppDatabase.getDatabase(application).userDao();
    }

    public void insert(User user) {
        new InsertAsyncTask(userDao).execute(user);
    }

    public LiveData<User> getUserByName(String name) {
        return userDao.getUserByName(name);
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

    private static class GetUserByNameAsyncTask extends AsyncTask<String, Void, LiveData<User>> {
        private UserDao userDao;

        private GetUserByNameAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected LiveData<User> doInBackground(String... strings) {
            return userDao.getUserByName(strings[0]);
        }
    }
}
