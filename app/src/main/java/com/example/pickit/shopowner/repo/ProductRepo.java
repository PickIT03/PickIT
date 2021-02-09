package com.example.pickit.shopowner.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pickit.shopowner.ShopOwnerHomeScreen;
import com.example.pickit.shopowner.ShopOwnerLogin;
import com.example.pickit.shopowner.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductRepo {
    DatabaseReference databaseReference;
    private MutableLiveData<List<Product>> mutableProductList;
    List<Product> products;
    public LiveData<List<Product>> getProducts(){
        if(mutableProductList==null){
            mutableProductList=new MutableLiveData<>();

            loadProducts();}
        return mutableProductList;
    }

    private void loadProducts() {
        products=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("ShopOwner/"+ ShopOwnerLogin.shopOwnerUUID+"/ProductDetails");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                if(hashMap==null){

                }else{
                    for(String keys:hashMap.keySet()){
                        HashMap<String,Object> hashMap1= (HashMap<String, Object>) hashMap.get(keys);
                        String productName = keys;
                        String imageUrl = String.valueOf(hashMap1.get("imageUrl"));
                        double price = Double.valueOf(String.valueOf(hashMap1.get("price")));
                        String availability = (String.valueOf(hashMap1.get("availability")));
                        Product product1=new Product(ShopOwnerLogin.shopOwnerUUID,productName,price,availability,imageUrl);
                        if(!inlist(product1))
                            products.add(product1);
                    }
                }
                mutableProductList.setValue(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private boolean inlist(Product product1) {
        String  UUID=product1.getId();
        String name = product1.getName();
        if(products.size()!=0) {
            for (int i=0;i<products.size();i++) {
                Product p=products.get(i);
                if(p.getId().equals(UUID)&&p.getName().equals(name)){
                    products.get(i).setAvailablity(product1.isAvailability());
                    products.get(i).setPrice(product1.getPrice());
                    ShopOwnerHomeScreen.shopRecyclerAdapterClass.notifyDataSetChanged();
                    return true;
                }
            }
        }
        return false;
    }

}
