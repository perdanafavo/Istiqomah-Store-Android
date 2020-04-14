package com.example.istiqomahstore.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.example.istiqomahstore.R;
import com.example.istiqomahstore.helpers.CustomCompatActivity;

public class SuccessForgotActivity extends CustomCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_forgot);
        AndroidNetworking.initialize(getApplicationContext());
    }
}