package com.example.istiqomahstore.adapters;

import android.app.Dialog;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private CartDetailActivity cartDetailActivity;
    private ArrayList<DetailCartData> detailCartData;
    private String activityTitle;

    //Views
    private ImageView ivProduct;
    private TextView tvProductName, tvQuantity, tvTotal;
    private Button btnAddToCart, btnCancel, btnPlus, btnMinus;
    private Dialog myDialog;

    //Variable
    private BigDecimal totalHarga;

    public CartAdapter(ArrayList<DetailCartData> detailCartData){
        this.detailCartData=detailCartData;
    }
    public void setData(ArrayList<DetailCartData> newData){
        this.detailCartData =newData;
        totalHarga = BigDecimal.valueOf(0);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        cartDetailActivity = (CartDetailActivity) parent.getContext();
        return new CartAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
