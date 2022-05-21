package com.example.yummybites.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.yummybites.loginscreens.LoginScreenActivity;
import com.example.yummybites.R;
import com.example.yummybites.databinding.ActivityMainBinding;
import com.example.yummybites.utils.AppConstant;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    Animation topAnim, bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        activityMainBinding.logosplashscreen.setAnimation(topAnim);
        activityMainBinding.yummybitestextlogo.setAnimation(bottomAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this , LoginScreenActivity.class);
                startActivity(intent);
                finish();
            }
        }, AppConstant.SPLASH_SCREEN);

    }
}