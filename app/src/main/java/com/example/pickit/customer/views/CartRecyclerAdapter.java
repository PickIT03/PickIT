package com.example.pickit.customer.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pickit.R;
import com.example.pickit.customer.models.CartItems;
import com.example.pickit.databinding.CartItemRecyclerBinding;

public class CartRecyclerAdapter extends ListAdapter<CartItems, CartRecyclerAdapter.CartRecyclerViewHolder> {
    DeleteClicked deleteClicked;

    protected CartRecyclerAdapter(DeleteClicked deleteClicked) {
        super(CartItems.cartItemsItemCallback);
        this.deleteClicked=deleteClicked;
    }

    @NonNull
    @Override
    public CartRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        CartItemRecyclerBinding cartRecyclerBinding= DataBindingUtil.inflate(layoutInflater, R.layout.cart_item_recycler,parent,false);
        cartRecyclerBinding.setDelete(deleteClicked);
        return new CartRecyclerViewHolder(cartRecyclerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewHolder holder, int position) {
        CartItems cartItems=getItem(position);
        holder.cartRecyclerBinding.setItem(cartItems);
    }

    class CartRecyclerViewHolder extends RecyclerView.ViewHolder {
        CartItemRecyclerBinding cartRecyclerBinding;
        public CartRecyclerViewHolder(CartItemRecyclerBinding cartRecyclerBinding) {
            super(cartRecyclerBinding.getRoot());
            this.cartRecyclerBinding=cartRecyclerBinding;
        }
    }
    public interface DeleteClicked {
        void onDeleteClicked(CartItems cartItems);

    }
}
