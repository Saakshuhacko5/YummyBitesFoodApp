package com.example.yummybites.apiclasses.model.Offer1Fragment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodOfferFragment1Model implements Serializable
{
    @SerializedName("result")
    private Boolean result;
    @SerializedName("data")
    FoodOffer1ListModel foodOffer1ListModel;

    public FoodOffer1ListModel getFoodOffer1ListModel() {
        return foodOffer1ListModel;
    }

    public void setFoodOffer1ListModel(FoodOffer1ListModel foodOffer1ListModel) {
        this.foodOffer1ListModel = foodOffer1ListModel;
    }
}
