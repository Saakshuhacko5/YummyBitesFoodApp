package com.example.yummybites.utils;

import android.content.DialogInterface;

public class Bridge {
    public interface AlertDialogBridge{
        void onNegativeClick(DialogInterface  dialogInterface);
        void onPositiveClick(DialogInterface  dialogInterface);
    }
}
