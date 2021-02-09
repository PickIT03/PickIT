package com.example.pickit.customer.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pickit.R;
import com.example.pickit.customer.models.Product;
import com.example.pickit.databinding.ProductRecyclerCustomerBinding;
import com.example.pickit.shopowner.ShopOwnerHomeScreen;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button button;
    TextInputEditText editText;
    private String searchInput;
    RecyclerProductAdapter.ShopInterface shopInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editText=findViewById(R.id.searchtext);
        button=findViewById(R.id.searchbtn);
        recyclerView=findViewById(R.id.search_recycler);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput=editText.getText().toString().trim();
                Log.d("search", "onClick: ");
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String UUID= ShopOwnerHomeScreen.UUID;
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("ShopOwner/"+UUID+"/ProductDetails");
        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>().setQuery(databaseReference.orderByChild("name").startAt(searchInput), Product.class).build();
        FirebaseRecyclerAdapter<Product, RecyclerProductAdapter.ShopViewHolder>adapter=new FirebaseRecyclerAdapter<Product, RecyclerProductAdapter.ShopViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerProductAdapter.ShopViewHolder holder, int position, @NonNull Product model) {
                Product product = getItem(position);
                holder.productRecyclerCustomerBinding.setProduct(product);
            }

            @NonNull
            @Override
            public RecyclerProductAdapter.ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               LayoutInflater layoutInflater =  LayoutInflater.from(parent.getContext());
                ProductRecyclerCustomerBinding productRecyclerCustomerBinding= DataBindingUtil.inflate(layoutInflater, R.layout.product_recycler_customer,parent,false);
                productRecyclerCustomerBinding.setShopinterface(shopInterface);
                return new RecyclerProductAdapter.ShopViewHolder(productRecyclerCustomerBinding);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}