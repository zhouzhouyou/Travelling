package com.travelling.datasource.entity;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Entity;

@Entity(primaryKeys = {"aid", "cid"})
public class ArticleCategory {
    @NonNull
    public String aid;

    @NonNull
    public String cid;

    public ArticleCategory(@NonNull String aid, @NonNull String cid) {
        this.aid = aid;
        this.cid = cid;
    }
}
