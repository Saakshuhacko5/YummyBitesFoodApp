package com.example.yummybites.loginscreens;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yummybites.R;
import com.example.yummybites.dashboard.NavigationScreenActivity;
import com.example.yummybites.database.DatabaseHelper;
import com.example.yummybites.databinding.SignupscreenBinding;
import com.example.yummybites.utils.AppConstant;
import com.example.yummybites.utils.AppUtil;
import com.example.yummybites.utils.SharedPreferenceUtil;
import com.example.yummybites.utils.ValidateUtil;

public class SignupScreenActivity extends AppCompatActivity implements View.OnClickListener {
    SignupscreenBinding signupscreenBinding;
    private DatabaseHelper databaseHelper;
    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupscreen);
        getSupportActionBar().hide();

        signupscreenBinding = SignupscreenBinding.inflate(getLayoutInflater());
        View view = signupscreenBinding.getRoot();
        signupscreenBinding.textViewMoveToSignIn.setText(getString(R.string.sign_in));
        signupscreenBinding.textViewHaveAcc.setText(getString(R.string.already_acc));
        setContentView(view);
        setOnClickListeners();
        initializeObjects();
        insertUser();

    }

    private void initializeObjects() {
        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        databaseHelper = new DatabaseHelper(this);
    }

    private void setOnClickListeners() {
        signupscreenBinding.buttonSignUp.setOnClickListener(this);
    }


    private void insertUser() {

        signupscreenBinding.textViewHaveAcc.setPaintFlags(signupscreenBinding.
                textViewHaveAcc.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        SpannableString span = new SpannableString(getString(R.string.sign_in));
        ClickableSpan click = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(SignupScreenActivity.this,
                        LoginScreenActivity.class);
                startActivity(intent);
            }
        };
        span.setSpan(click, 0, getString(R.string.sign_in).length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signupscreenBinding.textViewMoveToSignIn.setText(span);
        signupscreenBinding.textViewMoveToSignIn.setMovementMethod
                ((LinkMovementMethod.getInstance()));
    }

    public void validateUser() {
        if (!ValidateUtil.validateUsername(signupscreenBinding.userNameSignUp)
                | !ValidateUtil.validateEmail(signupscreenBinding.email)
                | !ValidateUtil.validatePassword
                (signupscreenBinding.passwordSignup)
                | !ValidateUtil.validatePhoneNumber(signupscreenBinding.phnNumber)
        ) {
            return;
        } else if (!databaseHelper.checkUserValidate(signupscreenBinding.email.getText().toString())) {
            AppUtil.getInstance().showToast(getApplicationContext(),
                    getString(R.string.already_reg));
            return;
        } else {
            boolean var = databaseHelper.registerUser(signupscreenBinding.
                            userNameSignUp.getText().toString(),
                    signupscreenBinding.email.getText().toString(),
                    signupscreenBinding.passwordSignup.getText().toString(),
                    signupscreenBinding.phnNumber.getText().toString());

//            if (!var) {
//                AppUtil.getInstance().showToast(getApplicationContext(), getString(R.string.not_registered));
//                return;
//            }
            sharedPreferenceUtil.saveString(AppConstant.EMAIL,
                    signupscreenBinding.email.getText().toString());
            sharedPreferenceUtil.saveString(AppConstant.USERNAME,
                    signupscreenBinding.userNameSignUp.getText().toString());
            sharedPreferenceUtil.saveString(AppConstant.PHONE_NO,
                    signupscreenBinding.phnNumber.getText().toString());

            AppUtil.getInstance().showToast(getApplicationContext(), getString(R.string.reg_successful));
            Intent intent = new Intent(SignupScreenActivity.this, NavigationScreenActivity.class);
            intent.putExtra(AppConstant.EMAIL, signupscreenBinding.email.getText().toString());
            intent.putExtra(AppConstant.USERNAME,
                    signupscreenBinding.userNameSignUp.getText().toString());
            startActivity(intent);
        }
    }

    public void onClick(View view) {
        if (view.getId() == signupscreenBinding.buttonSignUp.getId()) {
            validateUser();
        }
    }
}