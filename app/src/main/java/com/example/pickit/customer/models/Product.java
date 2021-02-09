package com.example.pickit.customer.models;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    public Product() {
    }

    private String UUID;
    private String name;
    private double price;
    private boolean isAvailable;
    private String imageUrl;
    private String address;
    private String shopName;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Product(String UUID, String name, double price, boolean isAvailable, String imageUrl) {
        this.UUID = UUID;
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
        this.imageUrl = imageUrl;

    }

    @Override
    public String toString() {
        return "Product{" +
                "UUID='" + UUID + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", imageUrl='" + imageUrl + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.getPrice(), getPrice()) == 0 &&
                isAvailable() == product.isAvailable() &&
                Objects.equals(getUUID(), product.getUUID()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getImageUrl(), product.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUUID(), getName(), getPrice(), isAvailable(), getImageUrl());
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static DiffUtil.ItemCallback<Product> productItemCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };

    @BindingAdapter("android:productImage")
    public static void loadImage(ImageView imageView, String Url) {
        Glide.with(imageView).load(Url).fitCenter().into(imageView);

    }

}
