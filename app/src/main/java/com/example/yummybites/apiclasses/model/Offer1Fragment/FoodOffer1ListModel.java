package com.example.yummybites.apiclasses.model.Offer1Fragment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FoodOffer1ListModel implements Serializable
{
    @SerializedName("food")
    List<FoodOffer1Model> list;

    public List<FoodOffer1Model> getList() {
        return list;
    }

    public void setList(List<FoodOffer1Model> list) {
        this.list = list;
    }
}
