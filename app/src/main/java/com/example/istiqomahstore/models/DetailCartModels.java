package com.example.istiqomahstore.models;

import com.example.istiqomahstore.models.submodels.DetailCartData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DetailCartModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ArrayList<DetailCartData> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<DetailCartData> getData() {
        return data;
    }

    public void setData(ArrayList<DetailCartData> data) {
        this.data = data;
    }
}
