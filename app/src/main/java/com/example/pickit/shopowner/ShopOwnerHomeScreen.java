package com.example.pickit.shopowner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pickit.R;
import com.example.pickit.customer.models.Product;
import com.example.pickit.databinding.ActivityShopownerHomeScreenBinding;
import com.example.pickit.shopowner.viewmodel.ShopOwnerViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShopOwnerHomeScreen extends AppCompatActivity implements ShopRecyclerAdapterClass.ShopClickListener {
    public static String UUID;
    DatabaseReference databaseReference;
    public static ShopRecyclerAdapterClass shopRecyclerAdapterClass;
    ActivityShopownerHomeScreenBinding activityShopownerHomeScreenBinding;
    List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityShopownerHomeScreenBinding= DataBindingUtil.setContentView(this, R.layout.activity_shopowner_home_screen);
        UUID = getIntent().getExtras().getString(ShopOwnerLogin.SHOP_UUID);
        productList = new ArrayList<>();
        shopRecyclerAdapterClass=new ShopRecyclerAdapterClass(this);
        ShopOwnerViewModel shopOwnerViewModel = new ViewModelProvider(this).get(ShopOwnerViewModel.class);
        shopOwnerViewModel.getProducts().observe(this, new Observer<List<com.example.pickit.shopowner.model.Product>>() {
            @Override
            public void onChanged(List<com.example.pickit.shopowner.model.Product> products) {
                shopRecyclerAdapterClass.submitList(products);
            }
        });
        activityShopownerHomeScreenBinding.shoprecycler.setAdapter(shopRecyclerAdapterClass);


    }

    @Override
    public void Onclicked(com.example.pickit.shopowner.model.Product product,View view) {
        Switch switc = (Switch) view;
        databaseReference= FirebaseDatabase.getInstance().getReference("ShopOwner/"+ ShopOwnerLogin.shopOwnerUUID+"/ProductDetails/"+product.getName()+"/availability");
        if(switc.isChecked()){
            databaseReference.setValue("true");
        }else {
            databaseReference.setValue("false");
        }
        shopRecyclerAdapterClass.notifyDataSetChanged();
        ShopOwnerHomeScreen.this.finish();

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ShopOwnerHomeScreen.this, ShopOwnerLogin.class));
    }

    public void productAddClicked(View view) {
        startActivity(new Intent(ShopOwnerHomeScreen.this, AddProActivity.class));
    }

    public void orderDetailsClicked(View view) {
        startActivity(new Intent(ShopOwnerHomeScreen.this, Ordered.class));
    }
}
