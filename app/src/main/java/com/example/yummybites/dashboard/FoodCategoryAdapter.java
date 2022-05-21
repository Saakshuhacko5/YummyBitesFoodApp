package com.example.yummybites.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummybites.apiclasses.model.FoodCategoryModel;
import com.example.yummybites.databinding.RecyclerviewItemBinding;

import java.util.List;

public class FoodCategoryAdapter extends RecyclerView.Adapter<FoodCategoryAdapter.MyViewHolder> {
    private Context context;
    private List<FoodCategoryModel> arrlistcategory;
    RecyclerviewItemBinding recyclerviewItemBinding;
    LayoutInflater layoutInflater;

    public FoodCategoryAdapter(Context context, List<FoodCategoryModel> list) {
        this.context = context;
        this.arrlistcategory = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        recyclerviewItemBinding = RecyclerviewItemBinding.inflate(layoutInflater, viewGroup, false);
        return new MyViewHolder(recyclerviewItemBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull FoodCategoryAdapter.MyViewHolder holder, int position) {
        Glide.with(context)
                .load(arrlistcategory.get(position).getImg())
                .into(recyclerviewItemBinding.circleImageView);
        recyclerviewItemBinding.foodName.setText(arrlistcategory.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrlistcategory.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
        }
    }
}
