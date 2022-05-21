package com.example.yummybites.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummybites.R;
import com.example.yummybites.apiclasses.model.Offer1Fragment.FoodOffer1Model;
import com.example.yummybites.databinding.RecyclerViewfoodratingBinding;

import java.util.List;

public class ViewOfferFragmentAdapter extends RecyclerView.Adapter<ViewOfferFragmentAdapter.mViewHolder> {

    private Context context;
    private List<FoodOffer1Model> arrlistcategory;
    LayoutInflater layoutInflater;
    RecyclerViewfoodratingBinding recyclerViewfoodratingBinding;

    public ViewOfferFragmentAdapter(Context context, List<FoodOffer1Model> arrlistcategory) {
        this.context = context;
        this.arrlistcategory = arrlistcategory;
    }


    @NonNull
    @Override
    public ViewOfferFragmentAdapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        recyclerViewfoodratingBinding = RecyclerViewfoodratingBinding.inflate(layoutInflater, parent, false);
        recyclerViewfoodratingBinding.wholeCard.setBackgroundResource(R.drawable.back_tabs_only);
        return new mViewHolder(recyclerViewfoodratingBinding.getRoot());

    }

    @Override
    public void onBindViewHolder(@NonNull ViewOfferFragmentAdapter.mViewHolder holder, int position) {


        Glide.with(context)
                .load(arrlistcategory.get(position).getImg())
                .into(recyclerViewfoodratingBinding.imageCard);
        recyclerViewfoodratingBinding.nameratingfood.setText(" " + arrlistcategory.get(position).getName());
        recyclerViewfoodratingBinding.categoryratingfood.setText(" " + arrlistcategory.get(position).getCategory());
        recyclerViewfoodratingBinding.priceofratingfood.setText(arrlistcategory.get(position).getPrice().toString());
        recyclerViewfoodratingBinding.currencyofratingfood.setText(" " + arrlistcategory.get(position).getCurrency());
        recyclerViewfoodratingBinding.ratingofratingfood.setText(arrlistcategory.get(position).getRating().toString());
    }

    @Override
    public int getItemCount() {
        return arrlistcategory.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder {
        public mViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
