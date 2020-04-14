package com.example.istiqomahstore.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import org.jetbrains.annotations.NotNull;

public class SessionManager {
    private static final String SP_DIMAS_APP = "spDimasApp";
    public static final String SP_IDUSER= "spIdUser";
    public static final String SP_NAME= "spName";
    public static final String SP_USERNAME= "spName";
    public static final String SP_EMAIL= "spEmail";
    public static final String SP_CREATEDON= "spCreatedOn";
    public static final String SP_LASTLOGIN= "spLastLogin";
    public static final String SP_ADDRESS= "spAddress";
    public static final String SP_PHONE= "spPhone";
    public static final String SP_TOKEN= "spToken";
    public static final String SP_CART= "spCart";
    public static final String SP_ALREADY_LOGIN = "spAlreadyLogin";

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    public SessionManager(@NotNull Context mContext){
        sp = mContext.getSharedPreferences(SP_DIMAS_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
        spEditor.apply();
    }

    void clearSP(){
        spEditor.clear().commit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPLong(String keySP, long value){
        spEditor.putLong(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSpSTOApp() {
        return SP_DIMAS_APP;
    }

    public int getSpIduser() {
        return sp.getInt(SP_IDUSER, 0);
    }

    public String getSpName() {
        return sp.getString(SP_NAME, "");
    }

    public String getSpUsername() {
        return sp.getString(SP_USERNAME, "");
    }

    public String getSpEmail() {
        return sp.getString(SP_EMAIL, "");
    }

    public int getSpCreatedOn() {
        return sp.getInt(SP_CREATEDON, 0);
    }

    public int getSpLastLogin() {
        return sp.getInt(SP_LASTLOGIN, 0);
    }

    public String getSpAddress() {
        return sp.getString(SP_ADDRESS, "");
    }

    public String getSpPhone() {
        return sp.getString(SP_PHONE, "");
    }

    public String getSpToken() {
        return sp.getString(SP_TOKEN, "");
    }

    public int getSpCart() {
        return sp.getInt(SP_CART, 0);
    }

    public Boolean getSpAlreadyLogin() { return sp.getBoolean(SP_ALREADY_LOGIN, false);}

}
