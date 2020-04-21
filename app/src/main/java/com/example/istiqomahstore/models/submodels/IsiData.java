package com.example.istiqomahstore.models.submodels;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class IsiData {
    public IsiData(){

    }
    @SerializedName("id_keranjang")
    private int id_keranjang;

    @SerializedName("id_produk")
    private int id_produk;

    @SerializedName("jumlah")
    private int jumlah;

    @SerializedName("harga")
    private BigDecimal harga;

    public int getId_keranjang() {
        return id_keranjang;
    }

    public void setId_keranjang(int id_keranjang) {
        this.id_keranjang = id_keranjang;
    }

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }
}
