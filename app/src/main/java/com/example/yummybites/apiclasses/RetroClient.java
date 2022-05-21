package com.example.yummybites.apiclasses;

import com.example.yummybites.utils.AppConstant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    private static RetroClient instance;
    private FoodInterface foodInterface;

    public static synchronized RetroClient getInstance() {
        if (instance == null) {
            instance = new RetroClient();
        }
        return instance;
    }

    private RetroClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        foodInterface = retrofit.create(FoodInterface.class);

    }

    public FoodInterface getFoodInterface() {
        return foodInterface;
    }
}