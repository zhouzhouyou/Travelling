package com.travelling.datasource;

import android.app.Application;

import com.travelling.datasource.dao.ArticleCategoryDao;
import com.travelling.datasource.dao.ArticleDao;
import com.travelling.datasource.dao.CategoryDao;
import com.travelling.datasource.dao.ExploreDao;
import com.travelling.datasource.dao.UserDao;
import com.travelling.datasource.entity.Article;
import com.travelling.datasource.entity.Category;
import com.travelling.datasource.entity.Explore;
import com.travelling.datasource.entity.User;
import com.travelling.datasource.repository.ArticleAsyncTasks;
import com.travelling.datasource.repository.ArticleCategoryAsyncTasks;
import com.travelling.datasource.repository.CategoryAsyncTasks;
import com.travelling.datasource.repository.ExploreAsyncTasks;
import com.travelling.datasource.repository.UserAsyncTasks;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class Repository {
    private UserDao mUserDao;
    private ArticleDao mArticleDao;
    private CategoryDao mCategoryDao;
    private ExploreDao mExploreDao;
    private ArticleCategoryDao mArticleCategoryDao;
    private LiveData<List<Explore>> mAllExplores;
    private LiveData<List<Category>> mAllCategories;
    private LiveData<List<User>> mAllUsers;
    private LiveData<List<Article>> mAllArticles;

    public Repository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        mUserDao = database.userDao();
        mArticleDao = database.articleDao();
        mCategoryDao = database.categoryDao();
        mExploreDao = database.exploreDao();
        mArticleCategoryDao = database.articleCategoryDao();
        mAllExplores = mExploreDao.queryAllExplores();
        mAllCategories = mCategoryDao.queryAllCategories();
        mAllUsers = mUserDao.queryAllUser();
        mAllArticles = mArticleDao.queryAllArticles();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public LiveData<List<Article>> getAllArticles() {
        return mAllArticles;
    }

    public LiveData<List<Explore>> getAllExplores() {
        return mAllExplores;
    }

    public LiveData<List<Category>> getAllCategories() {
        return mAllCategories;
    }

    public Article queryArticle(String aid) {
        try {
            return new ArticleAsyncTasks.queryArticleTask(mArticleDao).execute(aid).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateArticle(Article... articles) {
        new ArticleAsyncTasks.updateArticleTask(mArticleDao).execute(articles);
    }

    public LiveData<List<Article>> getFavoriteArticles() {
        try {
            return new ArticleAsyncTasks.queryFavoriteArticleTask(mArticleDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Article>> getBookmarkArticles() {
        try {
            return new ArticleAsyncTasks.queryBookmarkArticleTask(mArticleDao).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertUser(User... users) {
        new UserAsyncTasks.insertUserTask(mUserDao).execute(users);
    }

    public void insertArticle(Article... articles) {
        new ArticleAsyncTasks.insertArticleTask(mArticleDao).execute(articles);
    }

    public User queryUser(String uid) {
        try {
            return new UserAsyncTasks.queryUserTask(mUserDao).execute(uid).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertExplore(Explore... explores) {
        new ExploreAsyncTasks.insertExploreTask(mExploreDao).execute(explores);
    }

    public void insertCategory(Category... categories) {
        new CategoryAsyncTasks.insertCategoryTask(mCategoryDao).execute(categories);
    }

    public Explore queryExplore(String eid) {
        try {
            return new ExploreAsyncTasks.queryExploreTask(mExploreDao).execute(eid).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Article>> queryArticleInCategory(String cid) {
        try {
            return new ArticleCategoryAsyncTasks.queryArticleInCategoryTask(mArticleCategoryDao).execute(cid).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LiveData<List<Article>> queryArticles(String query) {
        try {
            return new ArticleAsyncTasks.queryArticlesTask(mArticleDao).execute(query).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
