package com.example.pickit.shopowner;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pickit.R;
import com.example.pickit.databinding.RecyclerOrderBinding;
import com.example.pickit.shopowner.model.OrderModel;

public class ShopOrderAdapter extends ListAdapter<OrderModel, ShopOrderAdapter.ShopOrderViewHolder> {


    public ShopOrderAdapter(){
        super(OrderModel.productItemCallback);
    }

    @NonNull
    @Override
    public ShopOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerOrderBinding recyclerOrderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.recycler_order,parent,false);

        return new ShopOrderViewHolder(recyclerOrderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopOrderViewHolder holder, int position) {
        holder.recyclerOrderBinding.setOrder(getItem(position));
    }

    static class ShopOrderViewHolder extends RecyclerView.ViewHolder{
        RecyclerOrderBinding recyclerOrderBinding;
        public ShopOrderViewHolder(RecyclerOrderBinding recyclerOrderBinding) {
            super(recyclerOrderBinding.getRoot());
            this.recyclerOrderBinding=recyclerOrderBinding;
        }
    }
}
