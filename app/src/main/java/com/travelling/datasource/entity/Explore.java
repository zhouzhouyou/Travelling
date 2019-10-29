package com.travelling.datasource.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Explore {
    @PrimaryKey
    @NonNull
    public String eid;

    public String name;

    public String content;

    public int image;

    public Explore(String eid, String name, String content, int image) {
        this.eid = eid;
        this.name = name;
        this.content = content;
        this.image = image;
    }
}
