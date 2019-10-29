package com.travelling.datasource.dao;

import com.travelling.datasource.entity.Explore;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class ExploreDao {
    @Query("select * from explore where eid == :eid")
    public abstract Explore queryExplore(String eid);

    @Query("select * from explore")
    public abstract LiveData<List<Explore>> queryAllExplores();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertExplores(Explore... explores);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public abstract int updateExplores(Explore... explores);
    @Query("delete from explore")
    public abstract void deleteAll();
}
