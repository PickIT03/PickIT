package com.example.pickit.customer.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class CartItems {
    private Product product;
    private int quantity;

    public CartItems(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItems)) return false;
        CartItems cartItems = (CartItems) o;
        return getQuantity() == cartItems.getQuantity() &&
                getProduct().equals(cartItems.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduct(), getQuantity());
    }

    @Override
    public String toString() {
        return "CartItems{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
    public static DiffUtil.ItemCallback<CartItems>cartItemsItemCallback=new DiffUtil.ItemCallback<CartItems>() {
        @Override
        public boolean areItemsTheSame(@NonNull CartItems oldItem, @NonNull CartItems newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CartItems oldItem, @NonNull CartItems newItem) {
            return oldItem.equals(newItem);
        }
    };
}
