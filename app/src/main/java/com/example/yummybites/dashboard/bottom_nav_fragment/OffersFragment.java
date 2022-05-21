package com.example.yummybites.dashboard.bottom_nav_fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.yummybites.R;
import com.example.yummybites.apiclasses.RetroClient;
import com.example.yummybites.apiclasses.model.FoodCategoryModel;
import com.example.yummybites.apiclasses.model.Offer1Fragment.FoodOffer1Model;
import com.example.yummybites.apiclasses.model.Offer1Fragment.FoodOfferFragment1Model;
import com.example.yummybites.dashboard.ViewOfferFragmentAdapter;
import com.example.yummybites.databinding.FragmentOffersBinding;
import com.example.yummybites.utils.AlertUtil;
import com.example.yummybites.utils.AppConstant;
import com.example.yummybites.utils.AppUtil;
import com.example.yummybites.utils.Bridge;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OffersFragment#} factory method to
 * create an instance of this fragment.
 */
public class OffersFragment extends Fragment {
    FragmentOffersBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOffersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        ArrayList<FoodCategoryModel> foodCategoryModels =
                (ArrayList<FoodCategoryModel>) getArguments().getSerializable(AppConstant.list);
        for (int i = 0; i < foodCategoryModels.size(); i++) {
            binding.tabOffers.addTab(binding.tabOffers.newTab()
                    .setText(foodCategoryModels.get(i).getName()));

            for (int j = 0; j < binding.tabOffers.getTabCount(); j++) {
                View tab = ((ViewGroup) binding.tabOffers.getChildAt(0)).getChildAt(i);
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
                p.setMargins(20, 0, 20, 0);
                tab.requestLayout();
            }
            if (i == 0) {
                callingApiFirst(i + 1);
            }
        }
        binding.tabOffers.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @SuppressLint("RestrictedApi")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int j = tab.getPosition();
                tab.getOrCreateBadge();

                callingApiFirst(j + 1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.removeBadge();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        return view;
    }

    private void callingApiFirst(int id) {

        Call<FoodOfferFragment1Model> call = RetroClient.getInstance()
                .getFoodInterface().getOfferId1(id);

        call.enqueue(new Callback<FoodOfferFragment1Model>() {
            @Override
            public void onResponse(Call<FoodOfferFragment1Model> call, Response<FoodOfferFragment1Model> response) {
                FoodOfferFragment1Model data = response.body();
                Log.d("response", "error");
                if (response != null && response.isSuccessful()) {

                    binding.shimmer.startShimmer();
                    binding.shimmer.setVisibility(View.GONE);
                    binding.viewpagerTabOffers.setVisibility(View.VISIBLE);
                    putData_inViewPager(data.getFoodOffer1ListModel().getList());
                } else {
                    AppUtil.getInstance().showToast(getContext(), getString(R.string.error));
                }
            }

            @Override
            public void onFailure(Call<FoodOfferFragment1Model> call, Throwable t) {
                AlertUtil.exitAppDialog(new Bridge.AlertDialogBridge() {
                                            @Override
                                            public void onNegativeClick(DialogInterface dialogInterface) {
                                                AppUtil.getInstance().showToast(getContext(), getString(R.string.logout));
                                            }

                                            @Override
                                            public void onPositiveClick(DialogInterface dialogInterface) {
                                                AppUtil.getInstance().showToast(getContext(), getString(R.string.cancel));

                                            }
                                        }, getActivity(), getString(R.string.alert)
                        , getString(R.string.wait_message));
            }
        });
    }


    private void putData_inViewPager(List<FoodOffer1Model> list) {
        binding.viewpagerTabOffers.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        ViewOfferFragmentAdapter offersViewPagerAdapter = new ViewOfferFragmentAdapter(getContext(), list);
        binding.viewpagerTabOffers.setAdapter(offersViewPagerAdapter);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}