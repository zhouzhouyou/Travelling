package com.travelling.datasource.repository;

import android.os.AsyncTask;

import com.travelling.R;
import com.travelling.datasource.AppDatabase;
import com.travelling.datasource.dao.ExploreDao;
import com.travelling.datasource.entity.Explore;


public class ExploreAsyncTasks {
    public static class insertExploreTask extends AsyncTask<Explore, Void, Void> {
        private ExploreDao mExploreDao;

        public insertExploreTask(ExploreDao exploreDao) {
            mExploreDao = exploreDao;
        }

        @Override
        protected Void doInBackground(Explore... explores) {
            mExploreDao.insertExplores(explores);
            return null;
        }
    }

    public static class queryExploreTask extends AsyncTask<String, Void, Explore> {
        private ExploreDao mExploreDao;

        public queryExploreTask(ExploreDao exploreDao) {
            mExploreDao = exploreDao;
        }

        @Override
        protected Explore doInBackground(String... strings) {
            return mExploreDao.queryExplore(strings[0]);
        }
    }

    public static class populateExploreDbTask extends AsyncTask<Void, Void, Void> {
        private ExploreDao mExploreDao;

        public populateExploreDbTask(AppDatabase appDatabase) {
            mExploreDao = appDatabase.exploreDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Explore fashion = new Explore("1", "Fashion", "FashionExplore", R.mipmap.palace_museum);
            Explore cultural = new Explore("2", "Culture","CulturalExplore", R.mipmap.summer_palace);
            Explore palace = new Explore("3", "Palace","PalaceExplore", R.mipmap.lu_xun);

            mExploreDao.insertExplores(fashion, cultural, palace);
            return null;
        }
    }
}
