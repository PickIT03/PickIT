package com.example.pickit.customer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pickit.customer.models.CartItems;
import com.example.pickit.customer.models.Product;
import com.example.pickit.customer.repositories.CartRepo;
import com.example.pickit.customer.repositories.DisplayRepo;
import com.example.pickit.customer.repositories.ShopRepo;

import java.util.List;

public class ShopViewModel extends ViewModel {
    ShopRepo shopRepo=new ShopRepo();
    DisplayRepo displayRepo=new DisplayRepo();
    MutableLiveData<Product> productMutableLiveData = new MutableLiveData<>();
    public LiveData<List<Product>> geProducts(){
        return shopRepo.getProducts();
    }
    public  void setProduct(Product product){
        productMutableLiveData.setValue(product);
    }

    public LiveData<Product> getProductMutableLiveData() {
        return productMutableLiveData;
    }

    CartRepo cartRepo=new CartRepo();
    public LiveData<List<CartItems>> getCart(){
        return cartRepo.getCart();
    }
    public boolean addItemToCart(Product product,int quantity){
        return cartRepo.addToCart(product,quantity);
    }
    public LiveData<List<CartItems>> getCartItems(){
        return displayRepo.displayCartItem();
    }
}
