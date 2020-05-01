package com.example.istiqomahstore.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.androidnetworking.AndroidNetworking;
import com.example.istiqomahstore.R;
import com.example.istiqomahstore.config.ENVIRONMENT;
import com.example.istiqomahstore.helpers.CustomCompatActivity;
import com.example.istiqomahstore.helpers.InternetChecker;
import com.example.istiqomahstore.helpers.SessionManager;
import com.example.istiqomahstore.models.submodels.UsersData;
import com.example.istiqomahstore.presenters.ApplicationPresenter;
import com.example.istiqomahstore.views.ApplicationViews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends CustomCompatActivity implements ApplicationViews.UpdateUsers {
    //Views
    private EditText etName, etEmail, etAddress, etPhoneNumber, etUsername, etPassword;
    private Button btnUpdateProfile;
    private ProgressDialog mDialog;
    private Map<String, String> param = new HashMap<>();

    //Variable
    private String name, email, address, phone, username, newPassword;

    //Object
    private SessionManager sessionManager;
    private ApplicationPresenter applicationPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        AndroidNetworking.initialize(getApplicationContext());

        setVariable();
        createView();
    }

    private void createView() {
        etName.setText(sessionManager.getSpName());
        etEmail.setText(sessionManager.getSpEmail());
        etAddress.setText(sessionManager.getSpAddress());
        etPhoneNumber.setText(sessionManager.getSpPhone());
        etUsername.setText(sessionManager.getSpUsername());

        mDialog = new ProgressDialog(EditProfileActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        editUser();
    }

    private void setVariable() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnUpdateProfile = findViewById(R.id.btnEditProfile);
        sessionManager = new SessionManager(getApplicationContext());
        applicationPresenter = new ApplicationPresenter(EditProfileActivity.this);
    }

    private void editUser() {
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetChecker.isConnectedToInternet(getBaseContext())) {
                    mDialog.show();

                    name = etName.getText().toString();
                    email = etEmail.getText().toString();
                    address = etAddress.getText().toString();
                    phone = etPhoneNumber.getText().toString();
                    username = etUsername.getText().toString();
                    newPassword = etPassword.getText().toString();

                    if(name.matches("")||email.matches("")||address.matches("")||phone.matches("")||username.matches("")){
                        simpleToast(ENVIRONMENT.EMPTY_FIELD);
                    }
                    else{
                        param.clear();
                        param.put("id", Integer.toString(sessionManager.getSpIduser()));
                        param.put("nama", name);
                        if(!email.matches(sessionManager.getSpEmail())) {
                            param.put("email", email);
                        }
                        param.put("alamat", address);
                        param.put("phone", phone);
                        if(!newPassword.matches("")){
                            param.put("password", newPassword);
                        }
                        applicationPresenter.putUsers();
                    }
                } else {
                    simpleToast(ENVIRONMENT.NO_INTERNET);
                }
            }
        });
    }

    @Override
    public Map<String, String> getParam() {
        return param;
    }

    @Override
    public void successUpdate(String message) {
        sessionManager.saveSPString(SessionManager.SP_NAME, name);
        sessionManager.saveSPString(SessionManager.SP_EMAIL, email);
        sessionManager.saveSPString(SessionManager.SP_ADDRESS, address);
        sessionManager.saveSPString(SessionManager.SP_PHONE, phone);
        mDialog.dismiss();
        simpleToast(message);
        simpleIntent(MainProductActivity.class);
    }

    @Override
    public void failedUpdate(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }
}