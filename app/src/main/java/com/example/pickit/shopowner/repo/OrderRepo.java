package com.example.pickit.shopowner.repo;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pickit.shopowner.Ordered;
import com.example.pickit.shopowner.ShopOwnerHomeScreen;
import com.example.pickit.shopowner.model.OrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderRepo {
    List<OrderModel> list = new ArrayList<>();
    private MutableLiveData<List<OrderModel>> data;

    public LiveData<List<OrderModel>> getProducts() {
        if (data == null) {
            data = new MutableLiveData<>();

            loadOrder();
        }
        return data;
    }

    private void loadOrder() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ShopOwner/" + ShopOwnerHomeScreen.UUID + "/OrderDetails");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                if (hashMap.size()>0) {
                    for (String keys : hashMap.keySet()) {
                        HashMap<String, Object> hashMap1 = (HashMap<String, Object>) hashMap.get(keys);
                        String phonenumber = keys;
                        for (String key : hashMap1.keySet()) {
                            HashMap<String, Object> hashmap2 = (HashMap<String, Object>) hashMap1.get(key);
                            String productname = key;
                            int quantity = Integer.valueOf(String.valueOf(hashmap2.get("quantity")));
                            list.add(new OrderModel(productname, phonenumber, quantity));
                        }
                    }
                    data.setValue(list);
                }else{
//                    Toast.makeText(Ordered, "No orders recieved", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
