package com.example.istiqomahstore.models;

import com.example.istiqomahstore.models.submodels.IsiData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IsiModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ArrayList<IsiData> data;

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

    public ArrayList<IsiData> getData() {
        return data;
    }

    public void setData(ArrayList<IsiData> data) {
        this.data = data;
    }
}
