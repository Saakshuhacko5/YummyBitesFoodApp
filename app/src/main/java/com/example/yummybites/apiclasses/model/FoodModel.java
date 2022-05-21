package com.example.yummybites.apiclasses.model;

import com.example.yummybites.apiclasses.model.FoodListModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodModel implements Serializable
{
    @SerializedName("result")
    Boolean result = false;
    @SerializedName("data")
    FoodListModel foodListModel;

    public FoodListModel getData() {
        return foodListModel;
    }

    public void setData(FoodListModel foodListModel) {
        this.foodListModel = foodListModel;
    }
}

