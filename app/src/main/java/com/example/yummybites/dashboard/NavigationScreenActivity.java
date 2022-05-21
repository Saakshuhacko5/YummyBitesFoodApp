package com.example.yummybites.dashboard;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.yummybites.AccountSectionDetailsActivity;
import com.example.yummybites.Fragments.OrderPageFragment;
import com.example.yummybites.R;
import com.example.yummybites.apiclasses.model.FoodCategoryModel;
import com.example.yummybites.dashboard.bottom_nav_fragment.CartFragment;
import com.example.yummybites.dashboard.bottom_nav_fragment.FavouritesFragment;
import com.example.yummybites.dashboard.bottom_nav_fragment.HomeScreenFragment;
import com.example.yummybites.dashboard.bottom_nav_fragment.OffersFragment;
import com.example.yummybites.dashboard.bottom_nav_fragment.snacksDrinksfragment;
import com.example.yummybites.databinding.NavHeaderBinding;
import com.example.yummybites.databinding.NavigationscreenBinding;
import com.example.yummybites.loginscreens.LoginScreenActivity;
import com.example.yummybites.utils.AlertUtil;
import com.example.yummybites.utils.AppConstant;
import com.example.yummybites.utils.AppUtil;
import com.example.yummybites.utils.Bridge;
import com.example.yummybites.utils.SharedPreferenceUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class NavigationScreenActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationscreenBinding navigationscreenBinding;
    NavHeaderBinding navHeaderBinding;
    String flags;
    Activity activity;
    BottomNavigationView bottomNavigationView;
    SharedPreferenceUtil sharedPreferenceUtil;
    MyAdapterCallBack myAdapterCallBack;
    ArrayList<FoodCategoryModel> foodCategoryModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationscreen);

        navigationscreenBinding = NavigationscreenBinding.inflate(getLayoutInflater());
        View view = navigationscreenBinding.getRoot();
        setContentView(view);

        bottomNavigationView = findViewById(R.id.bottomnavigationbar);
        activity = NavigationScreenActivity.this;
        setSupportActionBar(navigationscreenBinding.toolbar);
        flags = getIntent().getStringExtra("flag");
        myAdapterCallBack = new MyAdapterCallBack() {
            @Override
            public void sendCategoryList() {
                foodCategoryModels = HomeScreenFragment.send();
                Bundle bundle = new Bundle();
                OffersFragment offersFragment = new OffersFragment();
                bundle.putSerializable(AppConstant.list,foodCategoryModels);
                offersFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                        offersFragment).commit();

            }
        };

        //transferring username & email to homefragment class by setting them in nav_view
        View header = navigationscreenBinding.navView.getHeaderView(0);
        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        String email_user = sharedPreferenceUtil.getString(AppConstant.EMAIL, "");
        navHeaderBinding = NavHeaderBinding.bind(header);
        navHeaderBinding.setuseremail.setText(email_user);

        navigationscreenBinding.navView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                navigationscreenBinding.drawlayout, navigationscreenBinding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        navigationscreenBinding.drawlayout.addDrawerListener(toggle);
        toggle.syncState();

        if (flags != null && flags.equals("1")) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                    new OrderPageFragment()).commit();
            navigationscreenBinding.navView.setCheckedItem(R.id.nav_orders);
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                    new HomeScreenFragment()).commit();
            navigationscreenBinding.navView.setCheckedItem(R.id.home);

        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                                new HomeScreenFragment()).commit();
                        return true;

                    case R.id.snacks_drinks:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                                new snacksDrinksfragment()).commit();

                        return true;
                    case R.id.favourites:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                                new FavouritesFragment()).commit();
                        return true;

                    case R.id.offers:
                        myAdapterCallBack.sendCategoryList();
                        return true;
                    case R.id.cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                                new CartFragment()).commit();

                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragement_container,
                        new OrderPageFragment()).commit();
                break;
            case R.id.nav_account:
                Intent intent = new Intent(NavigationScreenActivity.this, AccountSectionDetailsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                AlertUtil.exitAppDialog(new Bridge.AlertDialogBridge() {
                    @Override
                    public void onNegativeClick(DialogInterface dialogInterface) {
                        AppUtil.getInstance().showToast(getApplicationContext(), getString(R.string.cancel));
                    }

                    @Override
                    public void onPositiveClick(DialogInterface dialogInterface) {
                        AppUtil.getInstance().showToast(getApplicationContext(), getString(R.string.logout));
                        Intent intent1 = new Intent(NavigationScreenActivity.this, LoginScreenActivity.class);
                        startActivity(intent1);

                    }
                }, activity, getString(R.string.alert), getString(R.string.exit_message));
        }
        navigationscreenBinding.drawlayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
