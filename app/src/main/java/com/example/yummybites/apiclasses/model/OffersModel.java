package com.example.yummybites.apiclasses.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OffersModel implements Serializable
{
    @SerializedName("offer1")
    FoodOfferTypesModel offer1;
    @SerializedName("offer2")
    FoodOfferTypesModel offer2;
    @SerializedName("offer3")
    FoodOfferTypesModel offer3;

    public FoodOfferTypesModel getOffer1()
    {
        return offer1;
    }

    public void setOffer1(FoodOfferTypesModel foodOfferTypesModel)
    {
        this.offer1 = foodOfferTypesModel;
    }


    public FoodOfferTypesModel getOffer2() {
        return offer2;
    }

    public void setOffer2(FoodOfferTypesModel offer2) {
        this.offer2 = offer2;
    }

    public FoodOfferTypesModel getOffer3() {
        return offer3;
    }

    public void setOffer3(FoodOfferTypesModel offer3) {
        this.offer3 = offer3;
    }

}
