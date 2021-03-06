package com.example.istiqomahstore.models;

import com.example.istiqomahstore.models.submodels.KeranjangData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KeranjangModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ArrayList<KeranjangData> data;

    @SerializedName("id")
    private int id;

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

    public void setData(ArrayList<KeranjangData> data){
        this.data=data;
    }
    public ArrayList<KeranjangData> getData(){
        return this.data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
