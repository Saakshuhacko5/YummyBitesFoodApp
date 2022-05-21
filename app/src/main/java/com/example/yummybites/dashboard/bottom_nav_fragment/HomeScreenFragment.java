package com.example.yummybites.dashboard.bottom_nav_fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.yummybites.R;
import com.example.yummybites.apiclasses.RetroClient;
import com.example.yummybites.apiclasses.model.FoodCategoryModel;
import com.example.yummybites.apiclasses.model.FoodModel;
import com.example.yummybites.apiclasses.model.FoodOfferTypesModel;
import com.example.yummybites.apiclasses.model.TopRatingFoodModel;
import com.example.yummybites.dashboard.FoodCategoryAdapter;
import com.example.yummybites.dashboard.FoodRatingAdapter;
import com.example.yummybites.dashboard.MyAdapterCallBack;
import com.example.yummybites.dashboard.OffersViewPagerAdapter;
import com.example.yummybites.database.DatabaseHelper;
import com.example.yummybites.databinding.FragmentNewHomeScreenBinding;
import com.example.yummybites.utils.AlertUtil;
import com.example.yummybites.utils.AppConstant;
import com.example.yummybites.utils.AppUtil;
import com.example.yummybites.utils.Bridge;
import com.example.yummybites.utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeScreenFragment#} factory method to
 * create an instance of this fragment.
 */
public class HomeScreenFragment extends Fragment implements MyAdapterCallBack {

    FragmentNewHomeScreenBinding binding;
    SharedPreferenceUtil sharedPreferenceUtil;
    DatabaseHelper databaseHelper;
    static ArrayList<FoodCategoryModel> foodCategories;


    public HomeScreenFragment() {

    }


    private void initializeObjects(View view) {
        sharedPreferenceUtil = new SharedPreferenceUtil(getContext());
        databaseHelper = new DatabaseHelper(getContext());
    }

    private void callApi() {
        Call<FoodModel> call = RetroClient.getInstance()
                .getFoodInterface().getFood();

        call.enqueue(new Callback<FoodModel>() {
            @Override
            public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                FoodModel data = response.body();
                if (response != null && response.isSuccessful()) {

                    ArrayList<FoodOfferTypesModel> list = new ArrayList<>();
                    list.add(data.getData().getOffers().getOffer1());
                    list.add(data.getData().getOffers().getOffer2());
                    list.add(data.getData().getOffers().getOffer3());
                    putDatainViewAdapter(list);

                    foodCategories = (ArrayList<FoodCategoryModel>)
                            data.getData().getCatogries();

                    putDatainRecyclerView(foodCategories);

                    putDatainRecyclerViewfoodrating(data.getData().getRatingFoodModelList());
                } else {
                    AppUtil.getInstance().showToast(getContext(), getString(R.string.error));
                }
            }


            @Override
            public void onFailure(Call<FoodModel> call, Throwable t) {
                AlertUtil.exitAppDialog(new Bridge.AlertDialogBridge() {
                    @Override
                    public void onNegativeClick(DialogInterface dialogInterface) {
                        AppUtil.getInstance().showToast(getContext(), getString(R.string.logout));
                    }

                    @Override
                    public void onPositiveClick(DialogInterface dialogInterface) {
                        AppUtil.getInstance().showToast(getContext(), getString(R.string.cancel));

                    }
                }, getActivity(), getString(R.string.alert), getString(R.string.wait_message));

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewHomeScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.setText.setText(getString(R.string.welcome) + " ");
        binding.foodCategory.setText(getString(R.string.food_category));
        binding.foodRating.setText(getString(R.string.top_ratings));
        initializeObjects(view);
        callApi();
        return view;
    }

    private void putDatainRecyclerView(List<FoodCategoryModel> list) {
        binding.recyclerviewCategories.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        FoodCategoryAdapter adapterClass = new FoodCategoryAdapter(getContext(), list);
        binding.recyclerviewCategories.setAdapter(adapterClass);
    }

    private void putDatainViewAdapter(List<FoodOfferTypesModel> list) {
        OffersViewPagerAdapter viewPagerAdapter = new OffersViewPagerAdapter(getContext(), list, this);
        binding.viewPagerOffers.setAdapter(viewPagerAdapter);
    }

    private void putDatainRecyclerViewfoodrating(List<TopRatingFoodModel> list) {
        binding.recyclerViewRatingFood.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        FoodRatingAdapter foodRatingAdapter = new FoodRatingAdapter(getContext(), list);
        binding.recyclerViewRatingFood.setAdapter(foodRatingAdapter);
    }

    @Override
    public void sendCategoryList() {
        Bundle bundle = new Bundle();
        OffersFragment offersFragment = new OffersFragment();
        bundle.putSerializable(AppConstant.list, foodCategories);
        offersFragment.setArguments(bundle);
        ((FragmentActivity) getContext()).getSupportFragmentManager()
                .beginTransaction().replace(R.id.fragement_container,
                offersFragment).commit();
    }

    public static ArrayList<FoodCategoryModel> send() {
        return foodCategories;
    }
}
