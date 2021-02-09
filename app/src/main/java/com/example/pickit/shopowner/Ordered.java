package com.example.pickit.shopowner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pickit.R;
import com.example.pickit.shopowner.model.OrderModel;
import com.example.pickit.shopowner.viewmodel.ShopOwnerViewModel;

import java.util.List;

public class Ordered extends AppCompatActivity {
    ShopOrderAdapter shopOrderAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered);
        ShopOwnerViewModel shopOwnerViewModel = new ViewModelProvider(this).get(ShopOwnerViewModel.class);
        shopOrderAdapter = new ShopOrderAdapter();
        shopOwnerViewModel.getOrder().observe(this, new Observer<List<OrderModel>>() {
            @Override
            public void onChanged(List<OrderModel> orderModels) {
                shopOrderAdapter.submitList(orderModels);
            }
        });
        recyclerView=findViewById(R.id.orderedDetails);
        recyclerView.setAdapter(shopOrderAdapter);

    }

}