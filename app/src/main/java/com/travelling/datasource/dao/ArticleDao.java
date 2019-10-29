package com.travelling.datasource.dao;

import com.travelling.datasource.entity.Article;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class ArticleDao {
    @Query("select * from article where title like '%'|| :name ||'%'")
    public abstract LiveData<List<Article>> queryArticles(String name);

    @Query("select * from article where aid == :id")
    public abstract Article queryArticle(String id);

    @Query("select * from Article")
    public abstract LiveData<List<Article>> queryAllArticles();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertArticles(Article... articles);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract int updateArticles(Article... articles);

    @Query("delete from article")
    public abstract void deleteAll();

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void setFavorite(Article... articles);

    @Query("select * from Article where favorite == 1")
    public abstract LiveData<List<Article>> queryFavoriteArticles();

    @Query("select * from Article where bookmark == 1")
    public abstract LiveData<List<Article>> queryBookmarkArticles();
}
