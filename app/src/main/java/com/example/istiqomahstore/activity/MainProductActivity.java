package com.example.istiqomahstore.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.istiqomahstore.models.submodels.ProdukData;
import com.example.istiqomahstore.presenters.ApplicationPresenter;
import com.example.istiqomahstore.views.ApplicationViews;

import java.util.ArrayList;

public class MainProductActivity extends CustomCompatActivity implements ApplicationViews.MainViews, ApplicationViews.MainViews.getProduk {


    //Views
    private Toolbar toolbarMainMenu;
    MenuItem menuItem;
    TextView badgeCounter, tvUser;
    SearchView searchProduct;
    RecyclerView rvProduct;
    private ProgressDialog mDialog;

    //Variable
    ArrayList<ProdukData> produkData;
    ProdukData prodData;

    //Constanta & Object
    int pendingNotifications = 0;
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

    private void createView() {
        setSupportActionBar(toolbarMainMenu);
        mDialog = new ProgressDialog(MainProductActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        mDialog.show();
        applicationPresenter.getProduk();
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

    private void setVariable() {
        sessionManager = new SessionManager(getApplicationContext());
        applicationPresenter = new ApplicationPresenter(MainProductActivity.this);

        tvUser = findViewById(R.id.tvUsername); tvUser.setText(sessionManager.getSpName());
        searchProduct = findViewById(R.id.searchProduct);
        rvProduct = findViewById(R.id.rvProduct);
        rvProduct.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        toolbarMainMenu = findViewById(R.id.toolbarMainMenu);
    }

    private void search(String text){
        ArrayList<ProdukData> filteredList = new ArrayList<>();
        for (ProdukData item : produkData){
            if ((item.getNama_produk().toLowerCase().contains(text.toLowerCase()))){
                filteredList.add(item);
            }
        }
        rvProduct.setAdapter(new ProductAdapter(filteredList));
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
                   Toast.makeText(getApplicationContext(),"Cart", Toast.LENGTH_SHORT).show();
               }
           });
       }

       menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem menuItem) {
               Toast.makeText(getApplicationContext(),"Cart", Toast.LENGTH_SHORT).show();
               return true;
           }
       });

       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart){
            Toast.makeText(getApplicationContext(),"Cart", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_edit_profile){
            Toast.makeText(getApplicationContext(),"Edit Profile", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_logout){
            outDialogMessage();
        }
        return true;
    }

    @Override
    public void successGetProduk(ArrayList<ProdukData> data) {
        mDialog.dismiss();
        produkData=data;
        rvProduct.setAdapter(new ProductAdapter(produkData));
    }

    @Override
    public void failedGetProduk(String message) {
        simpleToast(message);
    }
}
