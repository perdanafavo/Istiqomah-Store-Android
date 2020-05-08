package com.example.istiqomahstore.models.submodels;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class DetailCartData {
    public DetailCartData(){

    }
    @SerializedName("nama")
    private String nama;

    @SerializedName("id_keranjang")
    private int id_keranjang;

    @SerializedName("id_produk")
    private int id_produk;

    @SerializedName("nama_produk")
    private String nama_produk;

    @SerializedName("jumlah")
    private int jumlah;

    @SerializedName("harga")
    private BigDecimal harga;

    @SerializedName("harga_satuan")
    private BigDecimal harga_satuan;

    @SerializedName("gambar")
    private String gambar;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

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

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
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
