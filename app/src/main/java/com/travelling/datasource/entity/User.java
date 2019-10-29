package com.travelling.datasource.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String uid;

    public String username;

    public String password;

    public User(String uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
    }
}
