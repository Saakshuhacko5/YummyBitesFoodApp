package com.example.yummybites.apiclasses.model.Offer1Fragment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodOffer1Model implements Serializable
{
    @SerializedName("name")
    private String name;
    @SerializedName("category")
    private String category;
    @SerializedName("img")
    private String img;
    @SerializedName("price")
    private Integer price;
    @SerializedName("currency")
    private String currency;
    @SerializedName("rating")
    private Integer rating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

}
