package com.example.istiqomahstore.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.istiqomahstore.R;
import com.example.istiqomahstore.adapters.CartAdapter;
import com.example.istiqomahstore.config.ENVIRONMENT;
import com.example.istiqomahstore.helpers.CustomCompatActivity;
import com.example.istiqomahstore.helpers.SessionManager;
import com.example.istiqomahstore.models.submodels.DetailCartData;
import com.example.istiqomahstore.presenters.ApplicationPresenter;
import com.example.istiqomahstore.views.ApplicationViews;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CartDetailActivity extends CustomCompatActivity implements ApplicationViews.DetailCartViews, ApplicationViews.DetailCartViews.getCart, ApplicationViews.DetailCartViews.putIsi{

    //Views
    private TextView tvProductCartPrice, tvPaymentTotal;
    private RecyclerView rvCart;
    private Button btnCheckout;
    private ImageButton btnBack;
    private RadioButton rbPayment;
    private ProgressDialog mDialog;

    //Variable
    private int idCart;
    private BigDecimal totalHarga = BigDecimal.valueOf(0);

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
        totalHarga = BigDecimal.valueOf(sessionManager.getSpPrice());
        tvProductCartPrice.setText(totalHarga.toString());
        tvPaymentTotal.setText(totalHarga.toString());
        applicationPresenter.getCart(idCart);

        backMenu();
        checkoutOrder();
    }

    private void checkoutOrder() {
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkkout order
                simpleIntent(PaymentActivity.class);
            }
        });
    }

    private void backMenu() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void successGetCart(ArrayList<DetailCartData> data) {
        cartData = data;
        cartAdapter = new CartAdapter(CartDetailActivity.this, cartData,totalHarga);
        rvCart.setAdapter(cartAdapter);
        mDialog.dismiss();
    }

    @Override
    public void failedGetCart(String message) {
        simpleToast(ENVIRONMENT.CART_EMPTY);
        mDialog.dismiss();
    }

    @Override
    public void successPutIsi(String message) {
        simpleToast(message);
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public void failedPutIsi(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }

    @Override
    public int getIdCart() {
        return sessionManager.getSpCart();
    }

    @Override
    public void setNewPrice(int price) {
        sessionManager.saveSPInt(SessionManager.SP_PRICE, price);
    }
}
