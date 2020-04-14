package com.example.istiqomahstore.activity.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.example.istiqomahstore.R;
import com.example.istiqomahstore.activity.Helpers.CustomCompatActivity;

public class EditProfileActivity extends CustomCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        AndroidNetworking.initialize(getApplicationContext());
    }
}