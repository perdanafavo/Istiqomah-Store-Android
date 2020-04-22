package com.example.istiqomahstore.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.example.istiqomahstore.R;
import com.example.istiqomahstore.helpers.CustomCompatActivity;

public class PaymentActivity extends CustomCompatActivity {

    private ImageButton btnBack;
    private TextView tvTotal, tvInformationPayment, tvInformationConfirmation;
    private Button btnBackToStore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        AndroidNetworking.initialize(getApplicationContext());

        setVariable();
        createView();
    }

    private void createView() {
        backMenu();
        mainStore();
    }

    private void mainStore() {
        btnBackToStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleIntent(MainProductActivity.class);
            }
        });
    }

    private void backMenu() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setVariable() {
        btnBack = findViewById(R.id.btnBack);
        tvTotal = findViewById(R.id.tvTotal);
        tvInformationPayment = findViewById(R.id.tvInformationPayment);
        tvInformationConfirmation = findViewById(R.id.tvInformationConfirmation);
        btnBackToStore = findViewById(R.id.btnBackToStore);
    }

}