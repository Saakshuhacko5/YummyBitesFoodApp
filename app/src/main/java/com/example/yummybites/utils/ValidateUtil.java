package com.example.yummybites.utils;

import android.widget.TextView;
import android.widget.Toast;

import com.example.yummybites.R;

public class ValidateUtil {
    public static Boolean validatePassword(TextView textView) {
        String val = textView.getText().toString();
        String passvalidation = "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,12}$";
        if (val.isEmpty()) {
            textView.setError("Password can not empty");
            return false;
        } else if (!(val.matches(passvalidation))) {
            textView.setError("Password should have atleast  1 small,1 capital and 1 special characters");
            return false;
        } else {
            return true;
        }
    }


    public static Boolean validateUsername(TextView textView) {
        String val = textView.getText().toString();

        if (val.isEmpty()) {
            textView.setError(AppConstant.name_empty);
            return false;
        } else if (val.length() < 3) {
            textView.setError(AppConstant.name_length);
            return false;
        } else {
            return true;
        }
    }
    public static Boolean validateAddress(TextView textView) {
        String val = textView.getText().toString();

        if (val.isEmpty()) {
            textView.setError(AppConstant.address_empty);
            return false;
        } else if (val.length() < 5) {
            textView.setError(AppConstant.getAddress_length);
            return false;
        } else {
            return true;
        }
    }

    public static Boolean validatePhoneNumber(TextView textView) {
        String val = textView.getText().toString();
        if (val.isEmpty()) {
            textView.setError(AppConstant.phone_empty);
            return false;
        } else if (val.length() < 10) {
            textView.setError(AppConstant.phone_length);
            return false;
        } else {
            return true;
        }
    }

    public static Boolean validateEmail(TextView textView) {
        String val = textView.getText().toString();
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (val.isEmpty()) {
            textView.setError(AppConstant.email_empty);
            return false;
        } else if (!(val.matches(emailPattern))) {
            textView.setError(AppConstant.email_format);
            return false;
        } else {
            return true;
        }
    }

    public static boolean isTextFieldEmpty(TextView textView, String msg) {
        String text = textView.getText().toString();
        if (text.isEmpty()) {
            textView.setError(msg);
            return false;
        }
        return true;
    }
}
