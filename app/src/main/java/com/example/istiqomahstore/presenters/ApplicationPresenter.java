package com.example.istiqomahstore.presenters;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.istiqomahstore.config.ENVIRONMENT;
import com.example.istiqomahstore.models.Cek;
import com.example.istiqomahstore.models.DetailCartModels;
import com.example.istiqomahstore.models.IsiModels;
import com.example.istiqomahstore.models.KeranjangModels;
import com.example.istiqomahstore.models.ProdukModels;
import com.example.istiqomahstore.models.UsersModels;
import com.example.istiqomahstore.models.submodels.IsiData;
import com.example.istiqomahstore.views.ApplicationViews;

import java.util.Map;

public class ApplicationPresenter {
    Context page;
    private ApplicationViews applicationViews;
    private ApplicationViews.LoginViews loginViews;
    private ApplicationViews.UpdateUsers updateUsers;
    private ApplicationViews.MainViews mainViews;
    private ApplicationViews.MainViews.getProduk getProduk;
    private ApplicationViews.MainViews.getKeranjang getKeranjang;
    private ApplicationViews.MainViews.postKeranjang postKeranjang;
    private ApplicationViews.MainViews.isiViews isiViews;
    private ApplicationViews.MainViews.postIsi postIsi;
    private ApplicationViews.DetailCartViews detailCartViews;
    private ApplicationViews.DetailCartViews.getCart getCart;


    public ApplicationPresenter(Context context) {
        if (context instanceof ApplicationViews) applicationViews = (ApplicationViews) context;
        if (context instanceof ApplicationViews.LoginViews)
            loginViews = (ApplicationViews.LoginViews) context;
        if (context instanceof ApplicationViews.UpdateUsers)
            updateUsers = (ApplicationViews.UpdateUsers) context;
        if (context instanceof ApplicationViews.MainViews)
            mainViews = (ApplicationViews.MainViews) context;
        if (context instanceof ApplicationViews.MainViews.getProduk)
            getProduk = (ApplicationViews.MainViews.getProduk) context;
        if (context instanceof ApplicationViews.MainViews.getKeranjang)
            getKeranjang = (ApplicationViews.MainViews.getKeranjang) context;
        if (context instanceof ApplicationViews.MainViews.postKeranjang)
            postKeranjang = (ApplicationViews.MainViews.postKeranjang) context;
        if (context instanceof ApplicationViews.MainViews.isiViews)
            isiViews = (ApplicationViews.MainViews.isiViews) context;
        if (context instanceof ApplicationViews.MainViews.postIsi)
            postIsi = (ApplicationViews.MainViews.postIsi) context;
        if (context instanceof ApplicationViews.DetailCartViews)
            detailCartViews = (ApplicationViews.DetailCartViews) context;
        if (context instanceof ApplicationViews.DetailCartViews.getCart)
            getCart = (ApplicationViews.DetailCartViews.getCart) context;
        page = context;
    }

    public void loginProcess() {
        AndroidNetworking.get(ENVIRONMENT.BASE_URL + "authentication")
                .addQueryParameter("username", loginViews.getUsername())
                .addQueryParameter("password", loginViews.getPassword())
                .addQueryParameter("login", "TRUE")
                .addHeaders("X-API-KEY","bd347e289a6127112156ccbfe54b689f")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(UsersModels.class, new ParsedRequestListener<UsersModels>() {
                    @Override
                    public void onResponse(UsersModels response) {
                        if (response.getStatus()) {
                            loginViews.successLogin(response.getData());
                        } else {
                            loginViews.failedLogin(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            UsersModels er = anError.getErrorAsObject(UsersModels.class);
                            loginViews.failedLogin(er.getMessage());
                        } else {
                            loginViews.failedLogin(ENVIRONMENT.FAIL_GET);
                        }
                    }
                });
    }

    public void putUsers(){
        AndroidNetworking.put(ENVIRONMENT.BASE_URL + "authentication")
                .addHeaders("X-API-KEY","bd347e289a6127112156ccbfe54b689f")
                .addBodyParameter(updateUsers.getParam())
//                .addBodyParameter("id", Integer.toString(tokenViews.getId()))
//                .addBodyParameter("token", tokenViews.getToken())
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(UsersModels.class, new ParsedRequestListener<UsersModels>() {
                    @Override
                    public void onResponse(UsersModels response) {
                        if (response.getStatus()) {
                            updateUsers.successUpdate(response.getMessage());
                        } else {
                            updateUsers.failedUpdate(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            UsersModels er = anError.getErrorAsObject(UsersModels.class);
                            loginViews.failedLogin(er.getMessage());
                        } else {
                            loginViews.failedLogin(ENVIRONMENT.FAIL_GET);
                        }
                    }
                });
    }

    public void getProduk(){
        AndroidNetworking.get(ENVIRONMENT.BASE_URL + "produk")
                .addHeaders("X-API-KEY","bd347e289a6127112156ccbfe54b689f")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(ProdukModels.class, new ParsedRequestListener<ProdukModels>() {
                    @Override
                    public void onResponse(ProdukModels response) {
                        if (response.getStatus()) {
                            //Log.d("_CEK", "cek : "+ response.getData().get(0).getHarga_satuan());
                            getProduk.successGetProduk(response.getData());
                        } else {
                            getProduk.failedGetProduk(response.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            ProdukModels er = anError.getErrorAsObject(ProdukModels.class);
                            getProduk.failedGetProduk(er.getMessage());
                        } else {
                            getProduk.failedGetProduk(ENVIRONMENT.FAIL_GET);
                        }
                    }
                });
    }

    public void getKeranjang(int id){
        AndroidNetworking.get(ENVIRONMENT.BASE_URL + "keranjang")
                .addQueryParameter("id_user", Integer.toString(id))
                .addQueryParameter("status", "BELUM DIBAYAR")
                .addHeaders("X-API-KEY","bd347e289a6127112156ccbfe54b689f")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(KeranjangModels.class, new ParsedRequestListener<KeranjangModels>() {
                    @Override
                    public void onResponse(KeranjangModels response) {
                        if (response.getStatus()) {
                            getKeranjang.successGetKeranjang(response.getData());
                        } else {
                            getKeranjang.failedGetKeranjang(response.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            KeranjangModels er = anError.getErrorAsObject(KeranjangModels.class);
                            getKeranjang.failedGetKeranjang(er.getMessage());
                        } else {
                            getKeranjang.failedGetKeranjang(ENVIRONMENT.FAIL_GET);
                        }
                    }
                });
    }

    public void putKeranjang(Map<String, String> param){
        AndroidNetworking.put(ENVIRONMENT.BASE_URL + "keranjang")
                .addHeaders("X-API-KEY","bd347e289a6127112156ccbfe54b689f")
                .addBodyParameter(param)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(UsersModels.class, new ParsedRequestListener<UsersModels>() {
                    @Override
                    public void onResponse(UsersModels response) {
                        if (response.getStatus()) {
                        } else {
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {

                        } else {

                        }
                    }
                });
    }

    public void getIsi(int id){
        AndroidNetworking.get(ENVIRONMENT.BASE_URL + "isi")
                .addQueryParameter("id_keranjang", Integer.toString(id))
                .addHeaders("X-API-KEY","bd347e289a6127112156ccbfe54b689f")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(IsiModels.class, new ParsedRequestListener<IsiModels>() {
                    @Override
                    public void onResponse(IsiModels response) {
                        if (response.isStatus()) {
                            isiViews.successGetIsi(response.getData());
                        } else {
                            isiViews.failedGetIsi(response.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            IsiModels er = anError.getErrorAsObject(IsiModels.class);
                            isiViews.failedGetIsi(er.getMessage());
                        } else {
                            getProduk.failedGetProduk(ENVIRONMENT.FAIL_GET);
                        }
                    }
                });
    }

    public void postKeranjang(int id){
        AndroidNetworking.post(ENVIRONMENT.BASE_URL + "keranjang")
                .addBodyParameter("id_user", Integer.toString(id))
                .addBodyParameter("tgl_keranjang", Long.toString(System.currentTimeMillis() / 1000L))
                .addHeaders("X-API-KEY","bd347e289a6127112156ccbfe54b689f")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(KeranjangModels.class, new ParsedRequestListener<KeranjangModels>() {
                    @Override
                    public void onResponse(KeranjangModels response) {
                        if (response.getStatus()) {
                            postKeranjang.successPostKeranjang(response.getId());
                        } else {
                            postKeranjang.failedPostKeranjang(response.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            KeranjangModels er = anError.getErrorAsObject(KeranjangModels.class);
                            postKeranjang.failedPostKeranjang(er.getMessage());
                        } else {
                            postKeranjang.failedPostKeranjang(ENVIRONMENT.FAIL_GET);
                        }
                    }
                });
    }

    public void getCart(int id){
        AndroidNetworking.get(ENVIRONMENT.BASE_URL + "viewisi")
                .addHeaders("X-API-KEY","bd347e289a6127112156ccbfe54b689f")
                .addQueryParameter("id_keranjang", Integer.toString(id))
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(DetailCartModels.class, new ParsedRequestListener<DetailCartModels>() {
                    @Override
                    public void onResponse(DetailCartModels response) {
                        if (response.isStatus()) {
                            //Log.d("_CEK", "cek : "+ response.getData().get(0).getHarga_satuan());
                            getCart.successGetCart(response.getData());
                        } else {
                            getCart.failedGetCart(response.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            DetailCartModels er = anError.getErrorAsObject(DetailCartModels.class);
                            getCart.failedGetCart(er.getMessage());
                        } else {
                            getProduk.failedGetProduk(ENVIRONMENT.FAIL_GET);
                        }
                    }
                });
    }

    public void postIsi(Map<String, String> param){
        AndroidNetworking.post(ENVIRONMENT.BASE_URL + "isi")
                .addBodyParameter(param)
                .addHeaders("X-API-KEY","bd347e289a6127112156ccbfe54b689f")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(IsiModels.class, new ParsedRequestListener<IsiModels>() {
                    @Override
                    public void onResponse(IsiModels response) {
                        if (response.isStatus()) {
                            postIsi.successPostIsi(response.getMessage());
                        } else {
                            postIsi.failedPostIsi(response.getMessage());
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        if (anError.getErrorCode() != 0) {
                            KeranjangModels er = anError.getErrorAsObject(KeranjangModels.class);
                            postIsi.failedPostIsi(er.getMessage());
                        } else {
                            postIsi.failedPostIsi(ENVIRONMENT.FAIL_GET);
                        }
                    }
                });
    }

    public void cek(){
        AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAnUserDetail/{userId}")
                .addPathParameter("userId", "1")
                .setTag("cek")
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(Cek.class, new ParsedRequestListener<Cek>() {
                    @Override
                    public void onResponse(Cek cek) {
                        // do anything with response
                        Log.d("_ASW", "id : " + cek.getId());
                        Log.d("_Nama", "firstname : " + cek.getFirstname());
                        Log.d("_AWEK", "lastname : " + cek.getLastname());
                    }
                    @Override
                    public void onError(ANError anError) {
                        // handle error
                    }
                });
    }

    //TAMBAH RIKUES DISINI
}
