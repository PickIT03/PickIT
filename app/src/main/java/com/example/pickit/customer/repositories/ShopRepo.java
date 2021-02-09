package com.example.pickit.customer.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pickit.customer.models.Product;
import com.example.pickit.customer.views.Cart;
import com.example.pickit.customer.views.CustomerHomeScreen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopRepo {
    DatabaseReference databaseReference;
    private MutableLiveData<List<Product>> mutableProductList;
    List<Product> products;
    public LiveData<List<Product>> getProducts(){
        if(mutableProductList==null){
            mutableProductList=new MutableLiveData<>();

            loadProducts();}
        return mutableProductList;
    }

    private Product loadAddress(final Product product) {
        databaseReference=FirebaseDatabase.getInstance().getReference("ShopOwner/"+product.getUUID());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                product.setAddress(String.valueOf(hashMap.get("dbAddress")));
                product.setShopName(String.valueOf(hashMap.get("dbShopName")));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return product;
    }

    private void loadProducts() {
        products=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("ShopOwner");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String,Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                for(String key:hashMap.keySet()){
                   HashMap<String,Object> hashMap1= (HashMap<String, Object>) hashMap.get(key);
                   HashMap<String,Object> productDetails= (HashMap<String, Object>) hashMap1.get("ProductDetails");
                   for(String keys:productDetails.keySet()){
                       String productName=keys;
                       HashMap<String,Object> details= (HashMap<String, Object>) productDetails.get(keys);
                       String imageUrl = String.valueOf(details.get("imageUrl"));
                       boolean availability=Boolean.valueOf(String.valueOf(details.get("availability")));
                       Double price=Double.valueOf(String.valueOf(details.get("price")));
                       Product product = new Product(key,productName,price,availability,imageUrl);
                       Product product1=loadAddress(product);
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

    private boolean inlist(Product product) {
        String  UUID=product.getUUID();
        String name = product.getName();
        if(products.size()!=0) {
            for (int i=0;i<products.size();i++) {
                Product p=products.get(i);
                if(p.getUUID().equals(UUID)&&p.getName().equals(name)){
                    products.get(i).setAvailable(product.isAvailable());
                    products.get(i).setPrice(product.getPrice());
                    CustomerHomeScreen.recyclerProductAdapter.notifyDataSetChanged();
                    Cart.cartRecyclerAdapter.notifyDataSetChanged();
                    return true;
                }
            }
        }
        return false;
    }

}
