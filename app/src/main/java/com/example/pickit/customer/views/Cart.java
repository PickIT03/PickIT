package com.example.pickit.customer.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pickit.R;
import com.example.pickit.customer.models.CartItems;
import com.example.pickit.customer.repositories.DisplayRepo;
import com.example.pickit.customer.viewmodels.ShopViewModel;
import com.example.pickit.databinding.ActivityCartBinding;
import com.example.pickit.shopowner.PlaceOrderHelperClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity implements CartRecyclerAdapter.DeleteClicked {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Customer/"+ CustomerHomeScreen.cusphoneNumber+"/cartDetails");
    ActivityCartBinding activityCartBinding;
    RecyclerView recyclerView;
    public static CartRecyclerAdapter cartRecyclerAdapter;
    DisplayRepo displayRepo=new DisplayRepo();
    List<CartItems> cartItemsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCartBinding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        cartRecyclerAdapter=new CartRecyclerAdapter(this);
        cartItemsList=new ArrayList<>();
        recyclerView=activityCartBinding.cartRecycler;
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        ShopViewModel shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        shopViewModel.getCartItems().observe(this, new Observer<List<CartItems>>() {
           @Override
           public void onChanged(List<CartItems> cartItems) {

               cartRecyclerAdapter.submitList(cartItems);
               cartItemsList.addAll(cartItems);
           }
        });
        if(displayRepo.isIsthere())
            recyclerView.setAdapter(cartRecyclerAdapter);
        else {

        }
    }

    @Override
    public void onDeleteClicked(CartItems cartItems) {
        databaseReference.child(String.valueOf(cartItems.getProduct().getUUID())).child(String.valueOf(cartItems.hashCode())).removeValue();
        cartRecyclerAdapter.notifyDataSetChanged();
        Cart.this.finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Cart.this, CustomerHomeScreen.class));
        finish();
    }

    public void placed(View view) {
        DatabaseReference databaseReference1;
        String url="ShopOwner/";


        for(CartItems cartItems:cartItemsList){
            databaseReference1=FirebaseDatabase.getInstance().getReference(url+cartItems.getProduct().getUUID()+"/OrderDetails");

            PlaceOrderHelperClass placeOrderHelperClass = new PlaceOrderHelperClass(cartItems.getProduct().getName(),cartItems.getQuantity());
            databaseReference1.child(CustomerHomeScreen.cusphoneNumber).child(cartItems.getProduct().getName()).setValue(placeOrderHelperClass);
        }
        startActivity(new Intent(Cart.this, SuccessfullyOrdered.class));
    }
}