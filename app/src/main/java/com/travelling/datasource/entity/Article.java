package com.travelling.datasource.entity;

import androidx.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Article {
    @PrimaryKey
    @NonNull
    public String aid;

    public String title;

    public String content;

    public boolean favorite;

    public boolean bookmark;

    public int image;

    public Article(@NonNull String aid, String title, String content, int image) {
        this.aid = aid;
        this.title = title;
        this.content = content;
        this.favorite = false;
        this.bookmark = false;
        this.image = image;
    }
}
