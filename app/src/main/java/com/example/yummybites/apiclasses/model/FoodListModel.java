package com.example.yummybites.apiclasses.model;

import com.example.yummybites.apiclasses.model.FoodCategoryModel;
import com.example.yummybites.apiclasses.model.OffersModel;
import com.example.yummybites.apiclasses.model.SnackFragment.SnacksDrinksModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FoodListModel implements Serializable
{
    @SerializedName("offers")
    OffersModel offersModel;
    @SerializedName("catogries")
    List<FoodCategoryModel> catogries = null;
    @SerializedName("top_rating_foods")
    List<TopRatingFoodModel> ratingFoodModelList = null;

    public List<TopRatingFoodModel> getRatingFoodModelList() {
        return ratingFoodModelList;
    }

    public void setRatingFoodModelList(List<TopRatingFoodModel> ratingFoodModelList) {
        this.ratingFoodModelList = ratingFoodModelList;
    }

    public List<FoodCategoryModel> getCatogries() {
        return catogries;
    }

    public void setCatogries(List<FoodCategoryModel> catogries) {
        this.catogries = catogries;
    }

    public OffersModel getOffers() {
        return offersModel;
    }

    public void setOffers(OffersModel offersModel) {
        this.offersModel = offersModel;
    }
}
