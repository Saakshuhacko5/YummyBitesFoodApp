package com.example.yummybites.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yummybites.R;
import com.example.yummybites.apiclasses.model.SnackFragment.SnacksDrinksModel;
import com.example.yummybites.databinding.RecyclerViewfoodratingBinding;

import java.util.List;

public class Snacks_DrinksAdapter extends RecyclerView.Adapter<Snacks_DrinksAdapter.sdViewHolder> {

    private Context context;
    private List<SnacksDrinksModel> drinksModelList;
    RecyclerViewfoodratingBinding recyclerViewfoodratingBinding;
    LayoutInflater layoutInflater;

    public Snacks_DrinksAdapter(Context context, List<SnacksDrinksModel> drinksModelList) {
        this.context = context;
        this.drinksModelList = drinksModelList;
    }

    @NonNull
    @Override
    public sdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        recyclerViewfoodratingBinding = RecyclerViewfoodratingBinding.inflate(layoutInflater, parent, false);
        recyclerViewfoodratingBinding.wholeCard.setBackgroundResource(R.drawable.back_tabs_only);
        return new sdViewHolder(recyclerViewfoodratingBinding.getRoot());

    }

    @Override
    public void onBindViewHolder(@NonNull sdViewHolder holder, int position) {
        Glide.with(context)
                .load(drinksModelList.get(position).getImg())
                .into(recyclerViewfoodratingBinding.imageCard);
        recyclerViewfoodratingBinding.nameratingfood.setText(" " + drinksModelList.get(position).getName());
        recyclerViewfoodratingBinding.categoryratingfood.setText(" " + drinksModelList.get(position).getCategory());
        recyclerViewfoodratingBinding.priceofratingfood.setText(drinksModelList.get(position).getPrice().toString());
        recyclerViewfoodratingBinding.currencyofratingfood.setText(" " + drinksModelList.get(position).getCurrency());
        recyclerViewfoodratingBinding.ratingofratingfood.setText(drinksModelList.get(position).getRating().toString());
    }

    @Override
    public int getItemCount() {
        return drinksModelList.size();
    }

    public class sdViewHolder extends RecyclerView.ViewHolder {
        public sdViewHolder(View view) {
            super(view);

        }
    }
}
