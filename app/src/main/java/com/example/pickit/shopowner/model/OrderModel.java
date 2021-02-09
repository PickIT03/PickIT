package com.example.pickit.shopowner.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class OrderModel {
    private String productname;
    private String phonenumber;

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public OrderModel(String productname, String phonenumber, int quantity) {
        this.productname = productname;
        this.phonenumber = phonenumber;
        this.quantity = quantity;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderModel that = (OrderModel) o;
        return quantity == that.quantity &&
                Objects.equals(productname, that.productname) &&
                Objects.equals(phonenumber, that.phonenumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productname, phonenumber, quantity);
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;

    public static DiffUtil.ItemCallback<OrderModel> productItemCallback = new DiffUtil.ItemCallback<OrderModel>() {

        @Override
        public boolean areItemsTheSame(@NonNull OrderModel oldItem, @NonNull OrderModel newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull OrderModel oldItem, @NonNull OrderModel newItem) {
            return oldItem.equals(newItem);
        }
    };
}
