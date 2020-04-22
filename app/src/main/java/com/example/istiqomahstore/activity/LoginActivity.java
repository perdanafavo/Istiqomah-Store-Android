package com.example.istiqomahstore.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.istiqomahstore.R;
import com.example.istiqomahstore.config.ENVIRONMENT;
import com.example.istiqomahstore.helpers.CustomCompatActivity;
import com.example.istiqomahstore.helpers.InternetChecker;
import com.example.istiqomahstore.helpers.LoginDialog;
import com.example.istiqomahstore.helpers.RandomString;
import com.example.istiqomahstore.helpers.SessionManager;
import com.example.istiqomahstore.models.submodels.UsersData;
import com.example.istiqomahstore.presenters.ApplicationPresenter;
import com.example.istiqomahstore.views.ApplicationViews;

import java.util.ArrayList;

public class LoginActivity extends CustomCompatActivity implements ApplicationViews.LoginViews,ApplicationViews.TokenViews {

    private SessionManager sessionManager;
    private ApplicationPresenter applicationPresenter;
    private Button btnLogin;
    private EditText etUsername, etPassword;
    private ProgressDialog mDialog;
    private int id;
    private String username, password, token;
    private TextView tvForgotPassword, tvRegister;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

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

        registerPage();
        forgotPasswordPage();
    }

    private void forgotPasswordPage() {
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleIntent(ForgotPasswordActivity.class);
            }
        });
    }

    private void registerPage() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleIntent(RegisterActivity.class);
            }
        });
    }

    private void setVariable() {
        sessionManager = new SessionManager(getApplicationContext());
        applicationPresenter = new ApplicationPresenter(LoginActivity.this);

        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);
    }

    private void cekLogin() {
        if (sessionManager.getSpAlreadyLogin()) {
            simpleIntent(MainProductActivity.class);
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

                    username = etUsername.getText().toString();
                    password = etPassword.getText().toString();

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
        token  = RandomString.token();
        id = getResponse.getId();
        sessionManager.saveSPInt(SessionManager.SP_IDUSER, getResponse.getId());
        sessionManager.saveSPString(SessionManager.SP_NAME, getResponse.getNama());
        sessionManager.saveSPString(SessionManager.SP_USERNAME, getResponse.getUsername());
        sessionManager.saveSPString(SessionManager.SP_EMAIL, getResponse.getEmail());
        sessionManager.saveSPInt(SessionManager.SP_CREATEDON, getResponse.getCreated_on());
        sessionManager.saveSPInt(SessionManager.SP_LASTLOGIN, getResponse.getLast_login());
        sessionManager.saveSPString(SessionManager.SP_ADDRESS, getResponse.getAlamat());
        sessionManager.saveSPString(SessionManager.SP_PHONE, getResponse.getPhone());
        sessionManager.saveSPString(SessionManager.SP_TOKEN, token);
        sessionManager.saveSPBoolean(SessionManager.SP_ALREADY_LOGIN, true);
        applicationPresenter.updateToken();
    }

    @Override
    public void failedLogin(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }

    @Override
    public void onBackPressed() {
        //Klik 2x untuk keluar
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            finishAffinity();
        }
        else {
            simpleToast(ENVIRONMENT.BACKPRESSED_MESSAGE);
        }
        mBackPressed = System.currentTimeMillis();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void successToken(ArrayList<UsersData> data) {
        mDialog.dismiss();
        simpleIntent(MainProductActivity.class);
    }

    @Override
    public void failedToken(String message) {

    }
}
