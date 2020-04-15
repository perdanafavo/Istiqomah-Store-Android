package com.example.istiqomahstore.views;

import com.example.istiqomahstore.models.submodels.UsersData;

import java.util.ArrayList;
import java.util.Map;

public interface ApplicationViews {
    void failedRequest(String message);
    void successRequest();

    interface LoginViews{
        String getUsername();
        String getPassword();
        void successLogin(ArrayList<UsersData> data);
        void failedLogin(String message);
    }

    interface TokenViews{
        int getId();
        String getToken();
        void successToken(ArrayList<UsersData> data);
        void failedToken(String message);
    }

    interface RegisterViews{
        Map<String, String> GetRequestBody();
        void successAdd(String message);
        void failedAdd();
    }
}
