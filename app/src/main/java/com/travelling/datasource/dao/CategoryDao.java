package com.travelling.datasource.dao;

import com.travelling.datasource.entity.Article;
import com.travelling.datasource.entity.Category;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class CategoryDao {
    @Query("select * from category")
    public abstract LiveData<List<Category>> queryAllCategories();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertCategories(Category... categories);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract int updateCategories(Category... categories);

    @Query("delete from category")
    public abstract void deleteAll();
}
