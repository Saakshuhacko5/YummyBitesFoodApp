package com.example.yummybites.utils;

import android.content.Context;
import android.widget.Toast;

public class AppUtil {
    private static AppUtil instance;

    public static AppUtil getInstance() {
        if(instance == null) {
            instance = new AppUtil();
        }
        return instance;
    }

    public void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}

