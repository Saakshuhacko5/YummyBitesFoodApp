package com.example.yummybites.dashboard.bottom_nav_fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.yummybites.R;
import com.example.yummybites.apiclasses.RetroClient;
import com.example.yummybites.apiclasses.model.SnackFragment.FoodSnackModel;
import com.example.yummybites.apiclasses.model.SnackFragment.SnacksDrinksModel;
import com.example.yummybites.dashboard.Snacks_DrinksAdapter;
import com.example.yummybites.databinding.SnacksDrinksfragmentBinding;
import com.example.yummybites.utils.AlertUtil;
import com.example.yummybites.utils.AppUtil;
import com.example.yummybites.utils.Bridge;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link snacksDrinksfragment#} factory method to
 * create an instance of this fragment.
 */
public class snacksDrinksfragment extends Fragment {

    SnacksDrinksfragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = SnacksDrinksfragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.textSnacksDrinks.setText(getString(R.string.snacks_drinks));
        callingApi();
        return view;
    }

    private void callingApi() {

        Call<FoodSnackModel> call = RetroClient.getInstance()
                .getFoodInterface().getSnacks_Drinks();

        call.enqueue(new Callback<FoodSnackModel>() {
            @Override
            public void onResponse(Call<FoodSnackModel> call, Response<FoodSnackModel> response) {
                FoodSnackModel data = response.body();
                Log.d("response", "error");
                if (response != null && response.isSuccessful()) {
                    binding.shimmer.startShimmer();
                    binding.shimmer.setVisibility(View.GONE);
                    binding.recyclerViewSnacksDrinks.setVisibility(View.VISIBLE);
                    putData_inRecycleView(data.getSnacksDrinksListModel().getSnacks_drinks_list());
                } else {
                    AppUtil.getInstance().showToast(getContext(), getString(R.string.error));
                }
            }

            @Override
            public void onFailure(Call<FoodSnackModel> call, Throwable t) {
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

    private void putData_inRecycleView(List<SnacksDrinksModel> list) {
        binding.recyclerViewSnacksDrinks.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        Snacks_DrinksAdapter snacks_drinksAdapter = new Snacks_DrinksAdapter(getContext(), list);
        binding.recyclerViewSnacksDrinks.setAdapter(snacks_drinksAdapter);
    }
}