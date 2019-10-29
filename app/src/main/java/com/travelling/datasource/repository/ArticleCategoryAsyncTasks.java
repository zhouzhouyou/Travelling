package com.travelling.datasource.repository;

import android.os.AsyncTask;

import com.travelling.datasource.AppDatabase;
import com.travelling.datasource.dao.ArticleCategoryDao;
import com.travelling.datasource.entity.Article;
import com.travelling.datasource.entity.ArticleCategory;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ArticleCategoryAsyncTasks {
    public static class populateArticleCategoryDbTask extends AsyncTask<Void, Void, Void> {
        private ArticleCategoryDao mArticleCategoryDao;

        public populateArticleCategoryDbTask(AppDatabase appDatabase) {
            mArticleCategoryDao = appDatabase.articleCategoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArticleCategory ac1 = new ArticleCategory("1", "1");
            ArticleCategory ac2 = new ArticleCategory("1", "4");
            ArticleCategory ac3 = new ArticleCategory("2", "1");
            ArticleCategory ac4 = new ArticleCategory("2", "2");
            ArticleCategory ac5 = new ArticleCategory("3", "3");
            ArticleCategory ac6 = new ArticleCategory("4", "4");
            ArticleCategory ac7 = new ArticleCategory("5", "4");
            mArticleCategoryDao.insertArticleCategories(ac1, ac2, ac3, ac4, ac5, ac6, ac7);
            return null;
        }
    }

    public static class queryArticleInCategoryTask extends AsyncTask<String, Void, LiveData<List<Article>>> {
        private ArticleCategoryDao mArticleCategoryDao;

        public queryArticleInCategoryTask(ArticleCategoryDao articleCategoryDao) {
            mArticleCategoryDao = articleCategoryDao;
        }

        @Override
        protected LiveData<List<Article>> doInBackground(String... strings) {
            return mArticleCategoryDao.queryArticleIdInCategory(strings[0]);
        }
    }
}
