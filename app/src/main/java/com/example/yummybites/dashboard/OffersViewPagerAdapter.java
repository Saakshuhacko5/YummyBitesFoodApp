package com.example.yummybites.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.yummybites.R;
import com.example.yummybites.apiclasses.model.FoodOfferTypesModel;
import com.example.yummybites.databinding.ViewpagerItemsBinding;

import java.util.List;

public class OffersViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<FoodOfferTypesModel> list;
    MyAdapterCallBack myAdapterCallBack;
    ViewpagerItemsBinding binding;




    public OffersViewPagerAdapter(Context context, List<FoodOfferTypesModel> list, MyAdapterCallBack callBack) {
        this.list = list;
        this.context = context;
        myAdapterCallBack = callBack;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = ViewpagerItemsBinding.inflate(layoutInflater, container, false);

        binding.cardViewViewpager.setBackgroundResource(R.drawable.back_tabs_only);
        Glide.with(context)
                .load(list.get(position).getImg())
                .into(binding.imageOffers);
        binding.textSpecialOffers.setText(list.get(position).getName());
        binding.imageOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAdapterCallBack.sendCategoryList();
            }

        });

        ViewPager vp = (ViewPager) container;
        vp.addView(binding.getRoot(), 0);
        return binding.getRoot();

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
