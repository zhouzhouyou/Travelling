package com.travelling.datasource.dao;

import com.travelling.datasource.entity.Article;
import com.travelling.datasource.entity.ArticleCategory;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class ArticleCategoryDao {
    @Query("select article.aid, title, content, favorite, bookmark, image from article, articlecategory ac where cid == :cid and article.aid == ac.aid")
    public abstract LiveData<List<Article>> queryArticleIdInCategory(String cid);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertArticleCategories(ArticleCategory... articleCategories);
}
