package com.example.yummybites.apiclasses.model.SnackFragment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SnacksDrinksListModel implements Serializable
{
    @SerializedName("snacks_drinks")
    List<SnacksDrinksModel> snacks_drinks_list;

    public List<SnacksDrinksModel> getSnacks_drinks_list() {
        return snacks_drinks_list;
    }

    public void setSnacks_drinks_list(List<SnacksDrinksModel> snacks_drinks_list) {
        this.snacks_drinks_list = snacks_drinks_list;
    }
}
