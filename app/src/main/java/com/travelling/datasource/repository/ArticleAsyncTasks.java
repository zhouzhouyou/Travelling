package com.travelling.datasource.repository;

import android.os.AsyncTask;

import com.travelling.R;
import com.travelling.datasource.AppDatabase;
import com.travelling.datasource.dao.ArticleDao;
import com.travelling.datasource.entity.Article;

import java.util.List;

import androidx.lifecycle.LiveData;


public class ArticleAsyncTasks {
    public static class insertArticleTask extends AsyncTask<Article, Void, Void> {
        private ArticleDao mArticleDao;

        public insertArticleTask(ArticleDao articleDao) {
            mArticleDao = articleDao;
        }

        protected Void doInBackground(Article... articles) {
            mArticleDao.insertArticles(articles);
            return null;
        }
    }

    public static class queryArticleTask extends AsyncTask<String, Void, Article> {
        private ArticleDao mArticleDao;

        public queryArticleTask(ArticleDao articleDao) {
            mArticleDao = articleDao;
        }

        @Override
        protected Article doInBackground(String... strings) {
            return mArticleDao.queryArticle(strings[0]);
        }
    }

    public static class populateArticleDbTask extends AsyncTask<Void, Void, Void> {
        private final ArticleDao mArticleDao;

        public populateArticleDbTask(AppDatabase database) {
            mArticleDao = database.articleDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //mArticleDao.deleteAll();
            Article greatWall = new Article("1", "The Great Wall", "TheGreatWall", R.mipmap.the_great_wall);
            Article beijingZoo = new Article("2", "Beijing Zoo", "BeijingZoo", R.mipmap.beijing_zoo);
            Article luxun = new Article("3", "Lu Xun", "LuXun", R.mipmap.lu_xun);
            Article summerPalace = new Article("4", "Summer Palace", "SummerPalace", R.mipmap.summer_palace);
            Article palaceMuseum = new Article("5", "Palace Museum", "PalaceMuseum", R.mipmap.palace_museum);

            mArticleDao.insertArticles(greatWall, beijingZoo, luxun, summerPalace,palaceMuseum);
            return null;
        }
    }

    public static class updateArticleTask extends AsyncTask<Article, Void, Void> {
        private final ArticleDao mArticleDao;

        public updateArticleTask(ArticleDao articleDao) {
            mArticleDao = articleDao;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            mArticleDao.updateArticles(articles);
            return null;
        }
    }

    public static  class queryBookmarkArticleTask extends AsyncTask<Void, Void, LiveData<List<Article>>> {
        private ArticleDao mArticleDao;

        public queryBookmarkArticleTask(ArticleDao articleDao) {
            mArticleDao = articleDao;
        }

        @Override
        protected LiveData<List<Article>> doInBackground(Void... voids) {
            return mArticleDao.queryBookmarkArticles();
        }
    }

    public static class queryFavoriteArticleTask extends AsyncTask<Void, Void, LiveData<List<Article>>> {
        private ArticleDao mArticleDao;

        public queryFavoriteArticleTask(ArticleDao articleDao) {
            mArticleDao = articleDao;
        }

        @Override
        protected LiveData<List<Article>> doInBackground(Void... voids) {
            return mArticleDao.queryFavoriteArticles();
        }
    }

    public static class queryArticesTask extends AsyncTask<String, Void, LiveData<List<Article>>> {
        private ArticleDao mArticleDao;

        public queryArticesTask(ArticleDao articleDao) {
            mArticleDao = articleDao;
        }

        @Override
        protected LiveData<List<Article>> doInBackground(String... strings) {
            return mArticleDao.queryArticles(strings[0]);
        }
    }
}
