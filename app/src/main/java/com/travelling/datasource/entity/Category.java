package com.travelling.datasource.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Category {
    @PrimaryKey
    @NonNull
    public String cid;

    public String name;

    public int image;

    public Category(@NonNull String cid, String name, int image) {
        this.cid = cid;
        this.name = name;
        this.image = image;
    }

    public enum CategoryType {
        NATURE("nature"),
        ANIMAL("animal"),
        CELEBRITY("celebrity"),
        ANCIENT("ancient");

        private String name;

        CategoryType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
