package com.example.istiqomahstore.adapters;

import android.app.Dialog;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private MainProductActivity mainProductActivity;
    private ArrayList<ProdukData> produkData;
    private String activityTitle;

    private ImageView ivProduct;
    private TextView tvProductName, tvQuantity, tvTotal;
    private Button btnAddToCart, btnCancel, btnPlus, btnMinus;

    private Dialog myDialog;


    public ProductAdapter(ArrayList<ProdukData> produkData){
        this.produkData =produkData;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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

        final int finalPosition = position;
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                tvProductName.setText(produkData.get(finalPosition).getNama_produk());
                tvTotal.setText(produkData.get(finalPosition).getHarga_satuan().toString());

                if (produkData.get(finalPosition).getGambar() != null) {
                    Picasso.get().load(ENVIRONMENT.FOTO_URL+produkData.get(finalPosition).getGambar())
                            .error(R.drawable.ic_shopping_cart)
                            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .into(ivProduct);
                    ivProduct.setBackgroundResource(R.color.colorBlue);
                }

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //cancel
                        myDialog.dismiss();
                    }
                });

                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Add to cart
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
