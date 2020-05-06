package com.example.istiqomahstore.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.example.istiqomahstore.R;
import com.example.istiqomahstore.config.ENVIRONMENT;
import com.example.istiqomahstore.helpers.CustomCompatActivity;
import com.example.istiqomahstore.helpers.InternetChecker;
import com.example.istiqomahstore.presenters.ApplicationPresenter;
import com.example.istiqomahstore.views.ApplicationViews;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends CustomCompatActivity implements ApplicationViews.RegisterViews {

    private TextView tvLogin;
    private Button btnRegister;
    private EditText etName, etEmail, etAddress, etPhone, etUsername, etPassword;
    private ProgressDialog mDialog;
    private ApplicationPresenter applicationPresenter;
    private Map<String,String> param = new HashMap<>();
    private String name, email, address, phone, username, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AndroidNetworking.initialize(getApplicationContext());

        setVariable();
        createView();
    }

    private void createView() {
        mDialog = new ProgressDialog(RegisterActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        loginPage();
        registerUser();
    }

    private void registerUser() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetChecker.isConnectedToInternet(getBaseContext())) {
                    mDialog.show();
                    name = etName.getText().toString();
                    email = etEmail.getText().toString();
                    address = etAddress.getText().toString();
                    phone = etPhone.getText().toString();
                    username = etUsername.getText().toString();
                    password = etPassword.getText().toString();

                    if(name.matches("")||email.matches("")||address.matches("")||phone.matches("")||username.matches("")||password.matches("")){
                        mDialog.dismiss();
                        simpleToast(ENVIRONMENT.EMPTY_FIELD_REGISTER);
                    }
                    else{
                        param.clear();
                        param.put("username", username);
                        param.put("password", password);
                        param.put("nama", name);
                        param.put("email", email);
                        param.put("alamat", address);
                        param.put("phone", phone);
                        applicationPresenter.postUser();
                    }
                } else {
                    mDialog.dismiss();
                    simpleToast(ENVIRONMENT.NO_INTERNET);
                }
            }
        });
    }

    private void loginPage() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleIntent(LoginActivity.class);
                finishAffinity();
            }
        });
    }

    private void setVariable() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhoneNumber);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvLogin = findViewById(R.id.tvLogin);
        btnRegister = findViewById(R.id.btnRegister);
        applicationPresenter = new ApplicationPresenter(RegisterActivity.this);
    }

    @Override
    public Map<String, String> getParam() {
        return param;
    }

    @Override
    public void successPostUser(String message) {
        mDialog.dismiss();
        simpleToast(message);
        simpleIntent(LoginActivity.class);
    }

    @Override
    public void failedPostUser(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }
}