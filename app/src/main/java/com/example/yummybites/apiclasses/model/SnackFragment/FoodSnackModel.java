package com.example.yummybites.apiclasses.model.SnackFragment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodSnackModel implements Serializable {

    @SerializedName("data")
    SnacksDrinksListModel snacksDrinksListModel;

    public SnacksDrinksListModel getSnacksDrinksListModel() {
        return snacksDrinksListModel;
    }

    public void setSnacksDrinksListModel(SnacksDrinksListModel snacksDrinksListModel) {
        this.snacksDrinksListModel = snacksDrinksListModel;
    }
}
