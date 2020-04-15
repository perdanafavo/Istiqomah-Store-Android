package com.example.istiqomahstore.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.example.istiqomahstore.R;
import com.example.istiqomahstore.helpers.CustomCompatActivity;

public class ForgotPasswordActivity extends CustomCompatActivity {

    private ImageView btnBack;
    private EditText etEmail;
    private Button btnRequestPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        AndroidNetworking.initialize(getApplicationContext());

        setVariable();
        createView();
    }

    private void createView() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setVariable() {
        btnBack = findViewById(R.id.btnBack);
        etEmail = findViewById(R.id.etEmail);
        btnRequestPassword = findViewById(R.id.btnRequestPassword);
    }
}