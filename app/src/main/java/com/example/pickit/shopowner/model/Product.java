package com.example.pickit.shopowner.model;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable{
    private String id;
    private String name;
    private double price;
    private String availability;
    private String imageUrl;


    public Product(String id, String name, double price, String isAvailable, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.availability = isAvailable;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "UUID='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isAvailable=" + availability +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String isAvailability() {
        return availability;
    }

    public void setAvailablity(String available) {
        availability = available;
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
                isAvailability() == product.isAvailability() &&
                Objects.equals(getId(), product.getId()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getImageUrl(), product.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), isAvailability(), getImageUrl());
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

    @BindingAdapter("android:shopproductImage")
    public static void loadImage(ImageView imageView, String Url) {
        Glide.with(imageView).load(Url).fitCenter().into(imageView);

    }

}
