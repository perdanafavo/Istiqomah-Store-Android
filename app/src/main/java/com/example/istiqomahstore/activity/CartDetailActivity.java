package com.example.istiqomahstore.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.istiqomahstore.R;
import com.example.istiqomahstore.adapters.CartAdapter;
import com.example.istiqomahstore.config.ENVIRONMENT;
import com.example.istiqomahstore.helpers.CustomCompatActivity;
import com.example.istiqomahstore.helpers.SessionManager;
import com.example.istiqomahstore.models.submodels.DetailCartData;
import com.example.istiqomahstore.models.submodels.IsiData;
import com.example.istiqomahstore.models.submodels.ProdukData;
import com.example.istiqomahstore.presenters.ApplicationPresenter;
import com.example.istiqomahstore.views.ApplicationViews;

import java.util.ArrayList;

public class CartDetailActivity extends CustomCompatActivity implements ApplicationViews.DetailCartViews, ApplicationViews.DetailCartViews.getCart {

    //Views
    TextView tvProductCartPrice, tvPaymentTotal;
    RecyclerView rvCart;
    Button btnCheckout;
    ImageButton btnBack;
    RadioButton rbPayment;
    ProgressDialog mDialog;

    //Variable
    private int idCart;

    //Contanta & Object
    private SessionManager sessionManager;
    private ApplicationPresenter applicationPresenter;
    private CartAdapter cartAdapter;
    private ArrayList<DetailCartData> cartData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);
        setVariable();
        createView();
    }

    private void setVariable() {
        sessionManager = new SessionManager(getApplicationContext());
        applicationPresenter = new ApplicationPresenter(CartDetailActivity.this);
        idCart = sessionManager.getSpCart();
        tvProductCartPrice = findViewById(R.id.tvProductCartPrice);
        tvPaymentTotal = findViewById(R.id.tvPaymentTotals);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnBack = findViewById(R.id.btnBack);
        rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rbPayment = findViewById(R.id.radioPaymentBank);
    }

    private void createView() {
        mDialog = new ProgressDialog(CartDetailActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        mDialog.show();
        applicationPresenter.getCart(idCart);
    }


    @Override
    public void successGetCart(ArrayList<DetailCartData> data) {
        cartData = data;
        cartAdapter = new CartAdapter(cartData);
        rvCart.setAdapter(cartAdapter);
        mDialog.dismiss();
    }

    @Override
    public void failedGetCart(String message) {
        simpleToast(message);
    }
}
