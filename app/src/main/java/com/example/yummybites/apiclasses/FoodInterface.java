package com.example.yummybites.apiclasses;

import com.example.yummybites.apiclasses.model.FoodModel;
import com.example.yummybites.apiclasses.model.Offer1Fragment.FoodOfferFragment1Model;
import com.example.yummybites.apiclasses.model.SnackFragment.FoodSnackModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodInterface {

    @GET("home")
    Call<FoodModel> getFood();

    @GET("snacks")
    Call<FoodSnackModel> getSnacks_Drinks();

    @GET("offers")
    Call<FoodOfferFragment1Model> getOfferId1(@Query("id") int id);

}
