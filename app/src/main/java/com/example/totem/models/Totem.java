package com.example.totem.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Totem {
    @PrimaryKey
    private int id;
    private String imgUrl;
    private String name;
    private String description;

    public Totem(int id, String imgUrl, String name, String description) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
