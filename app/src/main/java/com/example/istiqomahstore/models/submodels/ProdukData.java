package com.example.istiqomahstore.models.submodels;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class ProdukData {
    public ProdukData(){

    }
    @SerializedName("id_produk")
    private int id_produk;

    @SerializedName("nama_produk")
    private String nama_produk;

    @SerializedName("harga_satuan")
    private BigDecimal harga_satuan;

    @SerializedName("gambar")
    private String gambar;

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public BigDecimal getHarga_satuan() {
        return harga_satuan;
    }

    public void setHarga_satuan(BigDecimal harga_satuan) {
        this.harga_satuan = harga_satuan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
