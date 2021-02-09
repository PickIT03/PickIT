package com.example.pickit.shopowner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pickit.R;
import com.example.pickit.databinding.ShopownerRecyclerBinding;
import com.example.pickit.shopowner.model.Product;

public class ShopRecyclerAdapterClass extends ListAdapter<Product, ShopRecyclerAdapterClass.ShopRecyclerViewHolder> {
    ShopClickListener shopClickListener;
    public ShopRecyclerAdapterClass(ShopClickListener shopClickListener) {
        super(Product.productItemCallback);
        this.shopClickListener=shopClickListener;
    }

    @NonNull
    @Override
    public ShopRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ShopownerRecyclerBinding shopownerRecyclerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.shopowner_recycler, parent, false);
        return new ShopRecyclerViewHolder(shopownerRecyclerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopRecyclerViewHolder holder, final int position) {
        holder.shopownerRecyclerBinding.setSoproduct(getItem(position));
        holder.shopownerRecyclerBinding.switchavai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopClickListener.Onclicked(getItem(position),v);
            }
        });


    }

    class ShopRecyclerViewHolder extends RecyclerView.ViewHolder {
        ShopownerRecyclerBinding shopownerRecyclerBinding;

        public ShopRecyclerViewHolder(@NonNull ShopownerRecyclerBinding shopownerRecyclerBinding) {
            super(shopownerRecyclerBinding.getRoot());
            this.shopownerRecyclerBinding = shopownerRecyclerBinding;

        }
    }

    public interface ShopClickListener {
        void Onclicked(Product product,View view);
    }
}
