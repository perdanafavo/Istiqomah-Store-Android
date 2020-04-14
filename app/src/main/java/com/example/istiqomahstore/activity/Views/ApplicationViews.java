package com.example.istiqomahstore.activity.Views;

import com.example.istiqomahstore.activity.Models.SubModels.UsersData;

import java.util.ArrayList;

public interface ApplicationViews {
    void failedRequest(String message);
    void successRequest();

    interface LoginViews{
        String getUsername();
        String getPassword();
        void successLogin(ArrayList<UsersData> data);
        void failedLogin(String message);
    }
}
