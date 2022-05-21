package com.example.yummybites.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class AlertUtil {

//    public static void exitAppDialog(Activity activity, Class activity2)
//    {
//        AlertDialog.Builder alertBox = new AlertDialog.Builder(activity);
//        alertBox.setTitle("Alert");
//        alertBox.setMessage("Are you sure you want to EXIT");
//        alertBox.setPositiveButton("Yes",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                       Intent intent = new Intent(activity,activity2);
//                       activity.startActivity(intent);
//                    }
//                });
//        alertBox.setNegativeButton("No", new
//                DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialogInterface, int arg1) {
//                        dialogInterface.cancel();
//                    }
//                });
//        AlertDialog alertDialog = alertBox.create();
//        alertDialog.show();
//
//    }

    public static void exitAppDialog(Bridge.AlertDialogBridge bridge, Activity activity, String title, String message) {
        AlertDialog.Builder alertBox = new AlertDialog.Builder(activity);
        alertBox.setTitle(title);
        alertBox.setMessage(message);
        alertBox.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int arg1) {
                        bridge.onPositiveClick(dialogInterface);
                    }
                });
        alertBox.setNegativeButton("No", new
                DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int arg1) {
                        bridge.onNegativeClick(dialogInterface);
                    }
                });
        AlertDialog alertDialog = alertBox.create();
        alertDialog.show();

    }
}
