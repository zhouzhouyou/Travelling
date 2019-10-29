package com.travelling.datasource.repository;

import android.os.AsyncTask;


import com.travelling.datasource.AppDatabase;
import com.travelling.datasource.dao.UserDao;
import com.travelling.datasource.entity.User;

import java.util.Date;

public class UserAsyncTasks {
    public static class insertUserTask extends AsyncTask<User, Void, Void> {
        private UserDao mUserDao;

        public insertUserTask(UserDao userDao) {
            mUserDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mUserDao.insertUsers(users);
            return null;
        }
    }

    public static class queryUserTask extends AsyncTask<String, Void, User> {
        private UserDao mUserDao;

        public queryUserTask(UserDao userDao) {
            mUserDao = userDao;
        }

        @Override
        protected User doInBackground(String... strings) {
            return mUserDao.queryUser(strings[0]);
        }
    }

    public static class populateUserDbTask extends AsyncTask<Void, Void, Void> {
        private final UserDao mUserDao;

        public populateUserDbTask(AppDatabase database) {
            mUserDao = database.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mUserDao.deleteAll();
            for (int i = 0; i < 10; i++) {
                User user = new User(String.valueOf(i), "username#" + i, "username#" + i);
                mUserDao.insertUsers(user);
            }
            return null;
        }
    }
}
