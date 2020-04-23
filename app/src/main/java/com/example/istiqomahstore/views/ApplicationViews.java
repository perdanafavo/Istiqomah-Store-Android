package com.example.istiqomahstore.views;

import com.example.istiqomahstore.models.submodels.DetailCartData;
import com.example.istiqomahstore.models.submodels.IsiData;
import com.example.istiqomahstore.models.submodels.KeranjangData;
import com.example.istiqomahstore.models.submodels.ProdukData;
import com.example.istiqomahstore.models.submodels.UsersData;

import java.util.ArrayList;
import java.util.Map;

public interface ApplicationViews {
    void failedRequest(String message);
    void successRequest();

    interface LoginViews{
        String getUsername();
        String getPassword();
        void successLogin(ArrayList<UsersData> data);
        void failedLogin(String message);
    }

    interface TokenViews{
        int getId();
        String getToken();
        void successToken(ArrayList<UsersData> data);
        void failedToken(String message);
    }

    interface RegisterViews{
        Map<String, String> GetRequestBody();
        void successPostUser(String message);
        void failedPostUser();
    }

    interface MainViews{
        interface getProduk{
            void successGetProduk(ArrayList<ProdukData> data);
            void failedGetProduk(String message);
        }
        interface getKeranjang{
            void successGetKeranjang(ArrayList<KeranjangData> data);
            void failedGetKeranjang(String message);
            void setNewPrice(int price);
            int getIdKeranjang();
        }
        interface postKeranjang{
            void successPostKeranjang(int id);
            void failedPostKeranjang(String message);
        }
        interface isiViews{
            void successGetIsi(ArrayList<IsiData> data);
            void failedGetIsi(String message);
        }
        interface postIsi{
            void successPostIsi(String messsage);
            void failedPostIsi(String message);
        }
    }

    interface DetailCartViews{
        interface getCart{
            void successGetCart(ArrayList<DetailCartData> data);
            void failedGetCart(String message);
        }
    }

}
