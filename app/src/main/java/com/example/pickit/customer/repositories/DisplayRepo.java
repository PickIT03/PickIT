package com.example.pickit.customer.repositories;

import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pickit.customer.models.CartItems;
import com.example.pickit.customer.models.Product;
import com.example.pickit.customer.views.CustomerHomeScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisplayRepo {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Customer/"+ CustomerHomeScreen.cusphoneNumber+"/cartDetails");
    DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference("Customer/"+ CustomerHomeScreen.cusphoneNumber);
    public boolean isthere=true;
    private MutableLiveData<List<CartItems>> displayList;
    List<CartItems> cartDisplayItem;
    SearchView searchView;
    public LiveData<List<CartItems>> displayCartItem(){
        if(displayList==null){
            displayList=new MutableLiveData<>();
            loadItems();
        }
        return displayList;
    }

    private void loadItems() {
        cartDisplayItem=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HashMap<String,Object> hashMap= (HashMap<String, Object>) snapshot.getValue();
                if(hashMap==null){
                    isthere=false;
                }
                else {
                    for (String keys : hashMap.keySet()) {
                        HashMap<String, Object> hashMap1 = (HashMap<String, Object>) hashMap.get(keys);
                        for (String key : hashMap1.keySet()) {
                            HashMap<String, Object> hashMap2 = (HashMap<String, Object>) hashMap1.get(key);
                            HashMap<String, Object> hashMap3 = (HashMap<String, Object>) hashMap2.get("product");
                            String address = String.valueOf(hashMap3.get("address"));
                            Boolean available = Boolean.valueOf(String.valueOf(hashMap3.get("available")));
                            String imageUrl = String.valueOf(hashMap3.get("imageUrl"));
                            String name = String.valueOf(hashMap3.get("name"));
                            Double price = Double.valueOf(String.valueOf(hashMap3.get("price")));
                            String uuid = String.valueOf(hashMap3.get("uuid"));
                            Product product = new Product(uuid, name, price, available, imageUrl);
                            product.setAddress(address);
                            int quantity = Integer.parseInt(String.valueOf(hashMap2.get("quantity")));
                            CartItems cartItems = new CartItems(product, quantity);

                            cartDisplayItem.add(cartItems);
                        }
                    }
                    displayList.setValue(cartDisplayItem);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public boolean isIsthere() {
        return isthere;
    }
}
