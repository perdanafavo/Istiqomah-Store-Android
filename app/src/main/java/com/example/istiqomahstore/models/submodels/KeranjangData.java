package com.example.istiqomahstore.models.submodels;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class KeranjangData {
    public KeranjangData(){

    }
    @SerializedName("id_keranjang")
    private int id_keranjang;

    @SerializedName("id_user")
    private int id_user;

    @SerializedName("tgl_keranjang")
    private int tgl_keranjang;

    @SerializedName("status")
    private String status;

    @SerializedName("total_harga")
    private BigDecimal total_harga;

    @SerializedName("pembayaran")
    private byte pembayaran;

    @SerializedName("norek")
    private String norek;

    public int getId_keranjang() {
        return id_keranjang;
    }

    public void setId_keranjang(int id_keranjang) {
        this.id_keranjang = id_keranjang;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getTgl_keranjang() {
        return tgl_keranjang;
    }

    public void setTgl_keranjang(int tgl_keranjang) {
        this.tgl_keranjang = tgl_keranjang;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(BigDecimal total_harga) {
        this.total_harga = total_harga;
    }

    public byte getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(byte pembayaran) {
        this.pembayaran = pembayaran;
    }

    public String getNorek() {
        return norek;
    }

    public void setNorek(String norek) {
        this.norek = norek;
    }

}
