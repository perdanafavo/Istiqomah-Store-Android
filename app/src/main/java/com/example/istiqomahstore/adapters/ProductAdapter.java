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
import com.example.istiqomahstore.activity.MainProductActivity;
import com.example.istiqomahstore.config.ENVIRONMENT;
import com.example.istiqomahstore.models.submodels.ProdukData;
import com.example.istiqomahstore.presenters.ApplicationPresenter;
import com.example.istiqomahstore.views.ApplicationViews;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ApplicationViews.MainViews.getKeranjang putPrice;
    private MainProductActivity mainProductActivity;
    private ArrayList<ProdukData> produkData;
    private String activityTitle;

    private ImageView ivProduct;
    private TextView tvProductName, tvQuantity, tvTotal;
    private Button btnAddToCart, btnCancel, btnPlus, btnMinus;

    private Dialog myDialog;
    private int initialCounter;
    private Context context;
    private BigDecimal initialPrice,newPrice;
    private ApplicationPresenter applicationPresenter;
    private ProgressDialog mDialog;
    private Map<String,String> param = new HashMap<>();


    public ProductAdapter(Context context, ArrayList<ProdukData> produkData){
        this.produkData =produkData;
        this.context = context;
        if (context instanceof ApplicationViews.MainViews.getKeranjang)
            putPrice = (ApplicationViews.MainViews.getKeranjang) context;
        applicationPresenter = new ApplicationPresenter(context);
    }

    public void setData(ArrayList<ProdukData> newData){
        this.produkData =newData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mainProductActivity = (MainProductActivity) parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.productName.setText(produkData.get(position).getNama_produk());
        holder.productPrice.setText(produkData.get(position).getHarga_satuan().toString());

        //Fungsi set Image (SetImage) productImage
        if (produkData.get(position).getGambar() != null) {
            Picasso.get().load(ENVIRONMENT.FOTO_URL+produkData.get(position).getGambar())
                    .error(R.drawable.ic_shopping_cart)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(holder.productImage);
            holder.productImage.setBackgroundResource(R.color.colorBlue);
        }

        if(!produkData.get(position).getCek()) {
            holder.addToCart.setEnabled(false);
            holder.addToCart.setText(R.string.added);
            holder.addToCart.setBackgroundResource(R.drawable.custom_disable_button);
//            holder.addToCart.setBackgroundColor(-7829368);
        }
        else{
            holder.addToCart.setEnabled(true);
            holder.addToCart.setText(R.string.add_to_cart);
            holder.addToCart.setBackgroundResource(R.drawable.custom_button_1);
        }

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialCounter = 1;
                initialPrice = produkData.get(position).getHarga_satuan();
                newPrice = initialPrice;

                myDialog = new Dialog(mainProductActivity);
                myDialog.setContentView(R.layout.custom_popup_cart);

                ivProduct = myDialog.findViewById(R.id.ivProduct);
                tvProductName = myDialog.findViewById(R.id.tvProductName);
                tvQuantity = myDialog.findViewById(R.id.tvQuantity);
                tvTotal = myDialog.findViewById(R.id.tvTotal);
                btnAddToCart = myDialog.findViewById(R.id.btnAddToCart);
                btnCancel = myDialog.findViewById(R.id.btnCancel);
                btnPlus = myDialog.findViewById(R.id.btnPlus);
                btnMinus = myDialog.findViewById(R.id.btnMinus);


                myDialog.setCanceledOnTouchOutside(false);

                tvProductName.setText(produkData.get(position).getNama_produk());
                tvTotal.setText(initialPrice.toString());
                tvQuantity.setText(Integer.toString(initialCounter));
                if (produkData.get(position).getGambar() != null) {
                    Picasso.get().load(ENVIRONMENT.FOTO_URL+produkData.get(position).getGambar())
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
                        newPrice = initialPrice.multiply(BigDecimal.valueOf(initialCounter));
                        tvTotal.setText(newPrice.toString());
                        tvQuantity.setText(Integer.toString(initialCounter));
                    }
                });

                btnMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (initialCounter>1){
                            initialCounter = initialCounter-1;
                            newPrice = initialPrice.multiply(BigDecimal.valueOf(initialCounter));
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
                        param.put("id_keranjang", Integer.toString(putPrice.getIdKeranjang()));
                        param.put("id_produk", Integer.toString(produkData.get(position).getId_produk()));
                        param.put("jumlah", Integer.toString(initialCounter));
                        param.put("harga", newPrice.toString());
                        applicationPresenter.postIsi(param);
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
        if (produkData!=null){return produkData.size();}
        else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;
        Button addToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tvProductName);
            productPrice = itemView.findViewById(R.id.tvProductPrice);
            productImage = itemView.findViewById(R.id.ivProduct);
            addToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
