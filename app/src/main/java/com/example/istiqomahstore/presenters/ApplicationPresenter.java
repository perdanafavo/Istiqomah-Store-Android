package com.example.istiqomahstore.presenters;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.istiqomahstore.config.ENVIRONMENT;
import com.example.istiqomahstore.models.Cek;
import com.example.istiqomahstore.models.UsersModels;
import com.example.istiqomahstore.views.ApplicationViews;

public class ApplicationPresenter {
    Context page;
    private ApplicationViews applicationViews;
    private ApplicationViews.LoginViews loginViews;

    public ApplicationPresenter(Context context) {
        if (context instanceof ApplicationViews) applicationViews = (ApplicationViews) context;
        if (context instanceof ApplicationViews.LoginViews)
            loginViews = (ApplicationViews.LoginViews) context;
        page = context;
    }

    public void loginProcess() {
        AndroidNetworking.get("https://istiqomah.diraya.co.id/api/authentication")
                .addQueryParameter("username", loginViews.getUsername())
                .addQueryParameter("password", loginViews.getPassword())
                .addQueryParameter("login", "TRUE")
                .addHeaders("X-API-KEY","bd347e289a6127112156ccbfe54b689f")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(UsersModels.class, new ParsedRequestListener<UsersModels>() {
                    @Override
                    public void onResponse(UsersModels response) {
                        if (response.getStatus()) {
                            loginViews.successLogin(response.getData());
                        } else {
                            loginViews.failedLogin(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loginViews.failedLogin(ENVIRONMENT.FAIL_GET);
                    }
                });
    }

    public void cek(){
        AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAnUserDetail/{userId}")
                .addPathParameter("userId", "1")
                .setTag("cek")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Cek.class, new ParsedRequestListener<Cek>() {
                    @Override
                    public void onResponse(Cek cek) {
                        // do anything with response
                        Log.d("_ASW", "id : " + cek.getId());
                        Log.d("_Nama", "firstname : " + cek.getFirstname());
                        Log.d("_AWEK", "lastname : " + cek.getLastname());
                    }
                    @Override
                    public void onError(ANError anError) {
                        // handle error
                    }
                });
    }

    //TAMBAH RIKUES DISINI
}
