package com.example.pickit.customer.views;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pickit.R;

import java.util.ArrayList;
import java.util.List;

public class ShopSelection extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editText;
    ImageButton button;
    List<CustomerRecyclerHelperClass>list=new ArrayList<>();
    CustomerRecycler customerRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_selection);
        recyclerView=findViewById(R.id.search_recycler);
        editText=findViewById(R.id.shopSearchName);
        button=findViewById(R.id.searchButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopSearch();
            }
        });
        /*list.add(new CustomerRecyclerHelperClass("One"));
        list.add(new CustomerRecyclerHelperClass("Two"));
        list.add(new CustomerRecyclerHelperClass("Three"));
        customerRecycler=new CustomerRecycler(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(customerRecycler);
        recyclerView.setHasFixedSize(true);*/
    }

    private void shopSearch() {
    }

    public class ShopSearchViewHolder extends RecyclerView.ViewHolder{
        View view;

        public ShopSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
        }
    }
}
