package com.example.yummybites.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummybites.R;
import com.example.yummybites.apiclasses.model.TopRatingFoodModel;
import com.example.yummybites.databinding.RecyclerViewfoodratingBinding;

import java.util.List;

public class FoodRatingAdapter extends RecyclerView.Adapter<FoodRatingAdapter.mViewHolder> {

    private Context context;
    public static List<TopRatingFoodModel> list;
    RecyclerViewfoodratingBinding recyclerViewfoodratingBinding;
    LayoutInflater layoutInflater;

    public FoodRatingAdapter(Context context, List<TopRatingFoodModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        recyclerViewfoodratingBinding = RecyclerViewfoodratingBinding.inflate(layoutInflater, parent, false);
        recyclerViewfoodratingBinding.wholeCard.setBackgroundResource(R.drawable.back_tabs_only);
        return new mViewHolder(recyclerViewfoodratingBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull FoodRatingAdapter.mViewHolder holder, int position) {

        Glide.with(context)
                .load(list.get(position).getImg())
                .into(recyclerViewfoodratingBinding.imageCard);
        recyclerViewfoodratingBinding.nameratingfood.setText("   " + list.get(position).getName());
        recyclerViewfoodratingBinding.categoryratingfood.setText("   " + list.get(position).getCategory());
        recyclerViewfoodratingBinding.priceofratingfood.setText(list.get(position).getPrice().toString());
        recyclerViewfoodratingBinding.currencyofratingfood.setText(list.get(position).getCurrency());
        recyclerViewfoodratingBinding.ratingofratingfood.setText(list.get(position).getRating().toString());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static int count() {
        return list.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder {


        public mViewHolder(View view) {
            super(view);

        }
    }
}


//            bookmark.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                   int pos = getAbsoluteAdapterPosition();
//
//                }
//            });