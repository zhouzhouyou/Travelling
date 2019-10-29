package com.travelling.datasource;

import android.content.Context;


import com.travelling.datasource.dao.ArticleCategoryDao;
import com.travelling.datasource.dao.ArticleDao;
import com.travelling.datasource.dao.CategoryDao;
import com.travelling.datasource.dao.ExploreDao;
import com.travelling.datasource.dao.UserDao;
import com.travelling.datasource.entity.Article;
import com.travelling.datasource.entity.ArticleCategory;
import com.travelling.datasource.entity.Category;
import com.travelling.datasource.entity.Explore;
import com.travelling.datasource.entity.User;
import com.travelling.datasource.repository.ArticleAsyncTasks;
import com.travelling.datasource.repository.ArticleCategoryAsyncTasks;
import com.travelling.datasource.repository.CategoryAsyncTasks;
import com.travelling.datasource.repository.ExploreAsyncTasks;
import com.travelling.datasource.repository.UserAsyncTasks;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class, Article.class, Explore.class, Category.class, ArticleCategory.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    private static RoomDatabase.Callback sCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new ArticleAsyncTasks.populateArticleDbTask(INSTANCE).execute();
            new UserAsyncTasks.populateUserDbTask(INSTANCE).execute();
            new ExploreAsyncTasks.populateExploreDbTask(INSTANCE).execute();
            new CategoryAsyncTasks.populateCategoryDbTask(INSTANCE).execute();
            new ArticleCategoryAsyncTasks.populateArticleCategoryDbTask(INSTANCE).execute();
        }
    };

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "travel_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();

    public abstract ArticleDao articleDao();

    public abstract CategoryDao categoryDao();

    public abstract ExploreDao exploreDao();

    public abstract ArticleCategoryDao articleCategoryDao();

}
