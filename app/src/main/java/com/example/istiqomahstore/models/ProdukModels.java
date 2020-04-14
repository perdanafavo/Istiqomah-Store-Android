package com.example.istiqomahstore.models;

import com.example.istiqomahstore.models.submodels.ProdukData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProdukModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ArrayList<ProdukData> data;

    public void setStatus(boolean status){
        this.status= status;
    }
    public boolean getStatus(){
        return status;
    }

    public void setMessage(String message){
        this.message=message;
    }
    public String getMessage(){
        return this.message;
    }

    public void setData(ArrayList<ProdukData> data){
        this.data=data;
    }
    public ArrayList<ProdukData> getData(){
        return this.data;
    }
}
