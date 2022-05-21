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
import com.example.yummybites.databinding.LoginscreenBinding;
import com.example.yummybites.utils.AppConstant;
import com.example.yummybites.utils.AppUtil;
import com.example.yummybites.utils.SharedPreferenceUtil;
import com.example.yummybites.utils.ValidateUtil;

public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener {
    public LoginscreenBinding loginscreenBinding;
    private DatabaseHelper databaseHelper;
    SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        getSupportActionBar().hide();

        loginscreenBinding = LoginscreenBinding.inflate(getLayoutInflater());
        loginscreenBinding.textViewMoveToSignUp.setText(getString(R.string.sign_up));
        loginscreenBinding.textViewMoveToSignUp.setText(getString(R.string.acc_notPresent));
        View view = loginscreenBinding.getRoot();
        setContentView(view);
        setOnClickListeners();
        initializeObjects();
        loginUser();
    }

    private void initializeObjects() {
        sharedPreferenceUtil = new SharedPreferenceUtil(getApplicationContext());
        databaseHelper = new DatabaseHelper(this);

    }

    private void setOnClickListeners() {
        loginscreenBinding.buttonSignIn.setOnClickListener(this);
    }

    private void loginUser() {
        loginscreenBinding.textViewDonAcc.setPaintFlags(loginscreenBinding.
                textViewDonAcc.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        SpannableString span = new SpannableString(getString(R.string.sign_up));
        ClickableSpan click = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(LoginScreenActivity.this, SignupScreenActivity.class);
                startActivity(intent);
            }
        };
        span.setSpan(click, 0, getString(R.string.sign_up).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginscreenBinding.textViewMoveToSignUp.setText(span);
        loginscreenBinding.textViewMoveToSignUp.
                setMovementMethod((LinkMovementMethod.getInstance()));
    }

    private void validateLogin() {
        String userMail = loginscreenBinding.userMailSignIn.getText().toString();
        String passwordText = loginscreenBinding.passwordSignIn.getText().toString();

        if (userMail.isEmpty()) {
            loginscreenBinding.userMailSignIn.setError(getString(R.string.name_empty));
        }
        if (passwordText.isEmpty()) {
            loginscreenBinding.passwordSignIn.setError(getString(R.string.pass_empty));
        }
        boolean var = databaseHelper.checkUser(userMail,
                passwordText);
        if (!ValidateUtil.validatePassword((loginscreenBinding.passwordSignIn))) {
            return;
        } else if (!var) {
            AppUtil.getInstance().showToast(getApplicationContext(),
                    getString(R.string.details_correctly));
        } else {
            AppUtil.getInstance().showToast(getApplicationContext(),
                    getString(R.string.login_successful));
            Intent intent = new Intent(LoginScreenActivity.this,
                    NavigationScreenActivity.class);
            intent.putExtra(AppConstant.EMAIL, userMail);
            sharedPreferenceUtil.saveString(AppConstant.EMAIL,
                    loginscreenBinding.userMailSignIn.getText().toString());
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == loginscreenBinding.buttonSignIn.getId()) {
            validateLogin();
        }
    }
}