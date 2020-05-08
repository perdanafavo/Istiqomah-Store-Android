package com.example.istiqomahstore.adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.istiqomahstore.R;
import com.example.istiqomahstore.activity.CartDetailActivity;
import com.example.istiqomahstore.config.ENVIRONMENT;
import com.example.istiqomahstore.models.submodels.DetailCartData;
import com.example.istiqomahstore.presenters.ApplicationPresenter;
import com.example.istiqomahstore.views.ApplicationViews;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ApplicationViews.DetailCartViews.putIsi putIsi;
    private CartDetailActivity cartDetailActivity;
    private ArrayList<DetailCartData> detailCartData;
    private ApplicationPresenter applicationPresenter;
    private String activityTitle;

    //Views
    private ImageView ivProduct;
    private TextView tvProductName, tvQuantity, tvTotal;
    private Button btnAddToCart, btnCancel, btnPlus, btnMinus;
    private Dialog myDialog;
    private ProgressDialog mDialog;

    //Variable
    private Context context;
    private int initialCounter;
    private BigDecimal hargaSatuan,newPrice,newTotalPrice,differencePrice;
    private BigDecimal totalHarga;
    private Map<String,String> param = new HashMap<>();
    private Map<String,String> paramCart = new HashMap<>();

    public CartAdapter(Context context, ArrayList<DetailCartData> detailCartData, BigDecimal totalHarga){
        this.detailCartData=detailCartData;
        this.totalHarga = totalHarga;
        this.context = context;
        if (context instanceof ApplicationViews.DetailCartViews.putIsi)
            putIsi = (ApplicationViews.DetailCartViews.putIsi) context;
        applicationPresenter = new ApplicationPresenter(context);
    }
    public void setData(ArrayList<DetailCartData> newData,BigDecimal totalHarga){
        this.detailCartData =newData;
        this.totalHarga = totalHarga;
    }

    public void setTotalHarga(BigDecimal totalHarga) {
        this.totalHarga = totalHarga;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        cartDetailActivity = (CartDetailActivity) parent.getContext();
        return new CartAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.productName.setText(detailCartData.get(position).getNama_produk());
        holder.productQuantity.setText(Integer.toString(detailCartData.get(position).getJumlah()));
        holder.productPrice.setText(detailCartData.get(position).getHarga().toString());
        if (detailCartData.get(position).getGambar() != null) {
            Picasso.get().load(ENVIRONMENT.FOTO_URL+detailCartData.get(position).getGambar())
                    .error(R.drawable.ic_shopping_cart)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(holder.productImage);
            holder.productImage.setBackgroundResource(R.color.colorBlue);
        }
        holder.editCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialCounter = detailCartData.get(position).getJumlah();
                hargaSatuan = detailCartData.get(position).getHarga_satuan();
                newPrice = detailCartData.get(position).getHarga();

                myDialog = new Dialog(cartDetailActivity);
                myDialog.setContentView(R.layout.custom_popup_cart);

                ivProduct = myDialog.findViewById(R.id.ivProduct);
                tvProductName = myDialog.findViewById(R.id.tvProductName);
                tvQuantity = myDialog.findViewById(R.id.tvQuantity);
                tvTotal = myDialog.findViewById(R.id.tvTotal);
                btnAddToCart = myDialog.findViewById(R.id.btnAddToCart);
                btnAddToCart.setText("Edit Quantity");
                btnCancel = myDialog.findViewById(R.id.btnCancel);
                btnPlus = myDialog.findViewById(R.id.btnPlus);
                btnMinus = myDialog.findViewById(R.id.btnMinus);


                myDialog.setCanceledOnTouchOutside(false);

                tvProductName.setText(detailCartData.get(position).getNama_produk());
                tvTotal.setText(newPrice.toString());
                tvQuantity.setText(Integer.toString(initialCounter));
                if (detailCartData.get(position).getGambar() != null) {
                    Picasso.get().load(ENVIRONMENT.FOTO_URL+detailCartData.get(position).getGambar())
                            .error(R.drawable.ic_shopping_cart)
                            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .into(ivProduct);
                    ivProduct.setBackgroundResource(R.color.colorBlue);
                }

                btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initialCounter = initialCounter+1;
                        newPrice = hargaSatuan.multiply(BigDecimal.valueOf(initialCounter));
                        tvTotal.setText(newPrice.toString());
                        tvQuantity.setText(Integer.toString(initialCounter));
                    }
                });

                btnMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (initialCounter>1){
                            initialCounter = initialCounter-1;
                            newPrice = hargaSatuan.multiply(BigDecimal.valueOf(initialCounter));
                            tvTotal.setText(newPrice.toString());
                            tvQuantity.setText(Integer.toString(initialCounter));
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });

                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        param.clear();
                        param.put("id_keranjang", Integer.toString(putIsi.getIdCart()));
                        param.put("id_produk", Integer.toString(detailCartData.get(position).getId_produk()));
                        param.put("jumlah", Integer.toString(initialCounter));
                        param.put("harga", newPrice.toString());
                        paramCart.clear();
                        differencePrice = newPrice.subtract(detailCartData.get(position).getHarga());
                        totalHarga = totalHarga.add(differencePrice);
                        newTotalPrice = totalHarga;
                        putIsi.setNewPrice(newTotalPrice.intValue());
                        paramCart.put("id_keranjang", Integer.toString(putIsi.getIdCart()));
                        paramCart.put("total_harga", newTotalPrice.toString());
                        applicationPresenter.putKeranjang(paramCart);
                        applicationPresenter.putIsi(param);
                        mDialog = new ProgressDialog(context);
                        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
                        mDialog.setCancelable(false);
                        mDialog.setIndeterminate(true);
                        mDialog.show();
                    }
                });

                if (myDialog.getWindow() != null) {
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                myDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (detailCartData!=null){return detailCartData.size();}
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, editCart, productQuantity;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tvProductName);
            productPrice = itemView.findViewById(R.id.tvProductPrice);
            productImage = itemView.findViewById(R.id.ivProduct);
            editCart = itemView.findViewById(R.id.tvEditProduct);
            productQuantity = itemView.findViewById(R.id.tvQuantity);

        }
    }
}
