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


    interface UpdateUsers{
        Map<String, String> getParam();
        void successUpdate(String message);
        void failedUpdate(String message);
    }

    interface RegisterViews{
        Map<String, String> getParam();
        void successPostUser(String message);
        void failedPostUser(String message);
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
            int getHargaTotal();
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
        interface putIsi{
            void successPutIsi(String message);
            void failedPutIsi(String message);
            int getIdCart();
            void setNewPrice(int price);
        }
    }

}
