package com.example.istiqomahstore.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.istiqomahstore.R;
import com.example.istiqomahstore.config.ENVIRONMENT;
import com.example.istiqomahstore.helpers.CustomCompatActivity;
import com.example.istiqomahstore.helpers.InternetChecker;
import com.example.istiqomahstore.helpers.LoginDialog;
import com.example.istiqomahstore.helpers.SessionManager;
import com.example.istiqomahstore.models.submodels.UsersData;
import com.example.istiqomahstore.presenters.ApplicationPresenter;
import com.example.istiqomahstore.views.ApplicationViews;

import java.util.ArrayList;

public class LoginActivity extends CustomCompatActivity implements ApplicationViews.LoginViews {

    private SessionManager sessionManager;
    private ApplicationPresenter applicationPresenter;
    private Button btnLogin;
    private EditText editUsername, editPassword;
    private ProgressDialog mDialog;
    private String username, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setVariable();
        createView();
        cekLogin();
    }

    private void createView() {
        mDialog = new ProgressDialog(LoginActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
    }

    private void setVariable() {
        sessionManager = new SessionManager(getApplicationContext());
        applicationPresenter = new ApplicationPresenter(LoginActivity.this);

        btnLogin = findViewById(R.id.btnLogin);
        editUsername = findViewById(R.id.etUsernameL);
        editPassword = findViewById(R.id.etPasswordL);
    }

    private void cekLogin() {
        if (sessionManager.getSpAlreadyLogin()) {
            simpleIntent(PaymentActivity.class);
        }  else {
            Login();
        }
    }

    private void Login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetChecker.isConnectedToInternet(getBaseContext())) {
                    mDialog.show();

                    username = editUsername.getText().toString();
                    password = editPassword.getText().toString();

                    if (username.matches("")) {
                        LoginDialog.paketLogin(1);
                        openDialog();
                        mDialog.dismiss();
                    } else if (password.matches("")) {
                        LoginDialog.paketLogin(2);
                        openDialog();
                        mDialog.dismiss();
                    } else {
                        applicationPresenter.loginProcess();
                    }
                } else {
                    simpleToast(ENVIRONMENT.NO_INTERNET);
                }
            }
        });
    }

    private void openDialog() {
        LoginDialog dialogue = new LoginDialog();
        dialogue.show(getSupportFragmentManager(), "Login Dialog");
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void successLogin(ArrayList<UsersData> data) {
        UsersData getResponse = data.get(0);
        mDialog.dismiss();

        sessionManager.saveSPInt(SessionManager.SP_IDUSER, getResponse.getId());
        sessionManager.saveSPString(SessionManager.SP_NAME, getResponse.getNama());
        sessionManager.saveSPString(SessionManager.SP_USERNAME, getResponse.getUsername());
        sessionManager.saveSPString(SessionManager.SP_EMAIL, getResponse.getEmail());
        sessionManager.saveSPInt(SessionManager.SP_CREATEDON, getResponse.getCreated_on());
        sessionManager.saveSPInt(SessionManager.SP_LASTLOGIN, getResponse.getLast_login());
        sessionManager.saveSPString(SessionManager.SP_ADDRESS, getResponse.getAlamat());
        sessionManager.saveSPString(SessionManager.SP_PHONE, getResponse.getPhone());
        sessionManager.saveSPString(SessionManager.SP_TOKEN, getResponse.getToken());
        sessionManager.saveSPBoolean(SessionManager.SP_ALREADY_LOGIN, true);
        simpleIntent(PaymentActivity.class);
    }

    @Override
    public void failedLogin(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }
}
