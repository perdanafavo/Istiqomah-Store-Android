package com.example.istiqomahstore.adapter;

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
import com.example.istiqomahstore.models.submodels.ProdukData;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private MainProductActivity mainProductActivity;
    private ArrayList<ProdukData> produkData;
    private String activityTitle;

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
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fungsi PopUp
            }
        });

    }

    @Override
    public int getItemCount() {
        if (produkData!=null){return produkData.size();}
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
