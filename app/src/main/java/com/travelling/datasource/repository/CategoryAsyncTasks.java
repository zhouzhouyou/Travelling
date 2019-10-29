package com.travelling.datasource.repository;

import android.os.AsyncTask;

import com.travelling.R;
import com.travelling.datasource.AppDatabase;
import com.travelling.datasource.dao.CategoryDao;
import com.travelling.datasource.entity.Article;
import com.travelling.datasource.entity.Category;
import com.travelling.datasource.entity.Category.CategoryType;

import java.util.List;

import androidx.lifecycle.LiveData;


public class CategoryAsyncTasks {
    public static class insertCategoryTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao mCategoryDao;

        public insertCategoryTask(CategoryDao categoryDao) {
            mCategoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            mCategoryDao.insertCategories(categories);
            return null;
        }
    }

    public static class populateCategoryDbTask extends AsyncTask<Void, Void, Void> {
        private CategoryDao mCategoryDao;

        public populateCategoryDbTask(AppDatabase appDatabase) {
            mCategoryDao = appDatabase.categoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Category nature = new Category("1", CategoryType.NATURE.getName(), R.mipmap.the_great_wall);
            Category animal = new Category("2", CategoryType.ANIMAL.getName(), R.mipmap.beijing_zoo);
            Category celebrity = new Category("3",CategoryType.CELEBRITY.getName(), R.mipmap.lu_xun);
            Category ancient = new Category("4", CategoryType.ANCIENT.getName(), R.mipmap.palace_museum);

            mCategoryDao.insertCategories(nature, animal, celebrity, ancient);
            return null;
        }
    }
}
