package com.example.yummybites.apiclasses.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodOfferTypesModel implements Serializable
{
    @SerializedName("img")
    String img;
    @SerializedName("name")
    private String name;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

