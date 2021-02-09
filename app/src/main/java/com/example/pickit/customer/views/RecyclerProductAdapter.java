package com.example.pickit.customer.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pickit.R;
import com.example.pickit.customer.models.Product;
import com.example.pickit.databinding.ProductRecyclerCustomerBinding;

public class RecyclerProductAdapter extends ListAdapter<Product, RecyclerProductAdapter.ShopViewHolder> {
    ShopInterface shopInterface;

    protected RecyclerProductAdapter(ShopInterface shopInterface) {
        super(Product.productItemCallback);
        this.shopInterface=shopInterface;

    }

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
        ProductRecyclerCustomerBinding productRecyclerCustomerBinding= DataBindingUtil.inflate(layoutInflater, R.layout.product_recycler_customer,parent,false);
        productRecyclerCustomerBinding.setShopinterface(shopInterface);
        return new ShopViewHolder(productRecyclerCustomerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Product product = getItem(position);
        holder.productRecyclerCustomerBinding.setProduct(product);


    }

    static class ShopViewHolder extends RecyclerView.ViewHolder{
        ProductRecyclerCustomerBinding productRecyclerCustomerBinding;
        public ShopViewHolder(@NonNull ProductRecyclerCustomerBinding productRecyclerCustomerBinding) {
            super(productRecyclerCustomerBinding.getRoot());
            this.productRecyclerCustomerBinding=productRecyclerCustomerBinding;
        }
    }
    public interface ShopInterface{
        void addItem(Product product);
        void  onItemClick(Product product);
    }
}
