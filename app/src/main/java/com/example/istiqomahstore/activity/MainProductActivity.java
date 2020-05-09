package com.example.istiqomahstore.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.istiqomahstore.R;
import com.example.istiqomahstore.adapters.ProductAdapter;
import com.example.istiqomahstore.config.ENVIRONMENT;
import com.example.istiqomahstore.helpers.CustomCompatActivity;
import com.example.istiqomahstore.helpers.SessionManager;
import com.example.istiqomahstore.models.submodels.IsiData;
import com.example.istiqomahstore.models.submodels.KeranjangData;
import com.example.istiqomahstore.models.submodels.ProdukData;
import com.example.istiqomahstore.presenters.ApplicationPresenter;
import com.example.istiqomahstore.views.ApplicationViews;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MainProductActivity extends CustomCompatActivity implements ApplicationViews.MainViews, ApplicationViews.MainViews.getProduk, ApplicationViews.MainViews.getKeranjang, ApplicationViews.MainViews.postKeranjang, ApplicationViews.MainViews.isiViews, ApplicationViews.MainViews.postIsi {

    //Views
    private Toolbar toolbarMainMenu;
    protected MenuItem menuItem;
    protected TextView badgeCounter, tvUser;
    private SearchView searchProduct;
    private RecyclerView rvProduct;
    private ProgressDialog mDialog;

    //Variable
    private ArrayList<ProdukData> produkData;
    private ArrayList<IsiData> isiData;
    private int idCart;
    private int hargaTotal;

    //Constanta & Object
    protected int pendingNotifications = 0;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    private SessionManager sessionManager;
    private ApplicationPresenter applicationPresenter;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_product);

        setVariable();
        createView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void setVariable() {
        sessionManager = new SessionManager(getApplicationContext());
        applicationPresenter = new ApplicationPresenter(MainProductActivity.this);
        tvUser = findViewById(R.id.tvUsername); tvUser.setText(sessionManager.getSpName());
        searchProduct = findViewById(R.id.searchProduct);
        rvProduct = findViewById(R.id.rvProduct);
        rvProduct.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        toolbarMainMenu = findViewById(R.id.toolbarMainMenu);
    }

    private void createView() {
        setSupportActionBar(toolbarMainMenu);
        mDialog = new ProgressDialog(MainProductActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        mDialog.show();

        applicationPresenter.getKeranjang(sessionManager.getSpIduser());

        searchProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });
    }

    private void search(String text){
        ArrayList<ProdukData> filteredList = new ArrayList<>();
        for (ProdukData item : produkData){
            if ((item.getNama_produk().toLowerCase().contains(text.toLowerCase()))){
                filteredList.add(item);
            }
        }
        productAdapter.setData(filteredList);
        productAdapter.notifyDataSetChanged();
//        rvProduct.setAdapter(new ProductAdapter(filteredList));
    }

    public void itemCounter(int count){
        pendingNotifications = count;
        if (pendingNotifications == 0){
            menuItem.setActionView(null);
        } else {
            menuItem.setActionView(R.layout.notification_badge);
            View view =  menuItem.getActionView();
            badgeCounter = view.findViewById(R.id.badge_counter);
            badgeCounter.setText(String.valueOf(pendingNotifications));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    simpleIntent(CartDetailActivity.class);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        //Klik 2x untuk keluar
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            finishAffinity();
        }
        else {
            simpleToast(ENVIRONMENT.BACKPRESSED_MESSAGE);
        }
        mBackPressed = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.toolbar_menu, menu);

       menuItem = menu.findItem(R.id.action_cart);
       badgeCounter = findViewById(R.id.badge_counter);

       itemCounter(0);

       menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem menuItem) {
               simpleIntent(CartDetailActivity.class);
               return true;
           }
       });

       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart){
//            Intent intent = new Intent(this, CartDetailActivity.class);
//            intent.putExtra("ISI", isiData);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            this.startActivity(intent);
            simpleIntent(CartDetailActivity.class);
        }
        else if (id == R.id.action_edit_profile) {
            simpleIntent(EditProfileActivity.class);
        }
        else if (id == R.id.action_logout){
            outDialogMessage();
        }
        return true;
    }

    @Override
    public void successGetProduk(ArrayList<ProdukData> data) {
        List<Integer> produkAdded = new ArrayList<>();

        if(isiData==null){
            produkData=data;
        }
        else{
            for (IsiData isi : isiData){
                produkAdded.add(isi.getId_produk());
            }
            Log.d("_Cek", "message" +produkAdded);
            for (int a : produkAdded){
                for (ProdukData item : data){
                    if(item.getId_produk()==a){
                        item.setCek(false);
                    }
                }
            }
            produkData=data;
        }
        productAdapter =  new ProductAdapter(MainProductActivity.this, produkData);
        rvProduct.setAdapter(productAdapter);
        mDialog.dismiss();
    }

    @Override
    public void failedGetProduk(String message) {
        simpleToast(message+"failProduk");
    }

    @Override
    public void successGetKeranjang(ArrayList<KeranjangData> data) {
        idCart = data.get(0).getId_keranjang();
        hargaTotal = data.get(0).getTotal_harga().intValue();
        sessionManager.saveSPInt(SessionManager.SP_CART, idCart);
        sessionManager.saveSPInt(SessionManager.SP_PRICE, hargaTotal);
        applicationPresenter.getIsi(idCart);
    }

    @Override
    public void failedGetKeranjang(String message) {
        applicationPresenter.postKeranjang(sessionManager.getSpIduser());
    }

    @Override
    public void setNewPrice(int price) {
        hargaTotal = price;
        sessionManager.saveSPInt(SessionManager.SP_PRICE, hargaTotal);
    }

    @Override
    public int getIdKeranjang() {
        return idCart;
    }

    @Override
    public int getHargaTotal() {
        return hargaTotal;
    }

    @Override
    public void successPostKeranjang(int id) {
        idCart = id;
        hargaTotal = 0;
        sessionManager.saveSPInt(SessionManager.SP_CART, idCart);
        sessionManager.saveSPInt(SessionManager.SP_PRICE, hargaTotal);
        applicationPresenter.getIsi(sessionManager.getSpCart());
    }

    @Override
    public void failedPostKeranjang(String message) {
        simpleToast(message+"FailPostKeranjang");
        mDialog.dismiss();
    }

    @Override
    public void successGetIsi(ArrayList<IsiData> data) {
        isiData = data;
        applicationPresenter.getProduk();
        itemCounter(isiData.size());
    }

    @Override
    public void failedGetIsi(String message) {
        applicationPresenter.getProduk();
    }

    @Override
    public void successPostIsi(String messsage) {
        simpleToast(messsage);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void failedPostIsi(String message) {
        simpleToast(message);
    }
}