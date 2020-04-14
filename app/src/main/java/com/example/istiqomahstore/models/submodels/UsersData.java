package com.example.istiqomahstore.models.submodels;


import com.google.gson.annotations.SerializedName;

public class UsersData{
    public UsersData(){

    }
    @SerializedName("id")
    private int id;

    @SerializedName("ip_address")
    private String ip_address;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("salt")
    private String salt;

    @SerializedName("email")
    private String email;

    @SerializedName("activation_code")
    private String activation_code;

    @SerializedName("forgotten_password_code")
    private String forgotten_password_code;

    @SerializedName("forgotten_password_time")
    private int forgotten_password_time;

    @SerializedName("remember_code")
    private String remember_code;

    @SerializedName("created_on")
    private int created_on;

    @SerializedName("last_login")
    private int last_login;

    @SerializedName("active")
    private byte active;

    @SerializedName("nama")
    private String nama;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("phone")
    private String phone;

    @SerializedName("token")
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivation_code() {
        return activation_code;
    }

    public void setActivation_code(String activation_code) {
        this.activation_code = activation_code;
    }

    public String getForgotten_password_code() {
        return forgotten_password_code;
    }

    public void setForgotten_password_code(String forgotten_password_code) {
        this.forgotten_password_code = forgotten_password_code;
    }

    public int getForgotten_password_time() {
        return forgotten_password_time;
    }

    public void setForgotten_password_time(int forgotten_password_time) {
        this.forgotten_password_time = forgotten_password_time;
    }

    public String getRemember_code() {
        return remember_code;
    }

    public void setRemember_code(String remember_code) {
        this.remember_code = remember_code;
    }

    public int getCreated_on() {
        return created_on;
    }

    public void setCreated_on(int created_on) {
        this.created_on = created_on;
    }

    public int getLast_login() {
        return last_login;
    }

    public void setLast_login(int last_login) {
        this.last_login = last_login;
    }

    public byte getActive() {
        return active;
    }

    public void setActive(byte active) {
        this.active = active;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
