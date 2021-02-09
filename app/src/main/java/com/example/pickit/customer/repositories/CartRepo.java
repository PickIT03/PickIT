package com.example.pickit.customer.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pickit.customer.models.CartItems;
import com.example.pickit.customer.models.Product;
import com.example.pickit.customer.views.CustomerHomeScreen;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartRepo {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Customer/"+ CustomerHomeScreen.cusphoneNumber+"/cartDetails");
    public CartRepo(){

    }
    private MutableLiveData<List<CartItems>> cartlivedata =new MutableLiveData<>();
    public LiveData<List<CartItems>> getCart(){
        if(cartlivedata.getValue()==null){
            initCart();
        }
        return cartlivedata;
    }

    private void initCart() {
        cartlivedata.setValue(new ArrayList<CartItems>());
    }

    public boolean addToCart(Product product,int quantity){
        if(cartlivedata.getValue()==null){
            initCart();
        }
        List<CartItems> cartItemsList=new ArrayList<>(cartlivedata.getValue());
        CartItems cartItems =new CartItems(product,quantity);
        cartItemsList.add(cartItems);
        cartlivedata.setValue(cartItemsList);
        databaseReference.child(cartItems.getProduct().getUUID()).child(String.valueOf(cartItems.hashCode())).setValue(cartItems);
        return true;
    }

}
