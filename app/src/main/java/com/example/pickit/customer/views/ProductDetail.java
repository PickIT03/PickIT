package com.example.pickit.customer.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pickit.R;
import com.example.pickit.customer.models.CartItems;
import com.example.pickit.customer.models.Product;
import com.example.pickit.customer.viewmodels.ShopViewModel;
import com.example.pickit.databinding.ActivityProductDetailBinding;

import java.util.List;

public class ProductDetail extends AppCompatActivity {
    ActivityProductDetailBinding productDetailBinding;
    Spinner spinner;
    public static ShopViewModel shopViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);
        spinner=productDetailBinding.quantitySpinner;
        final Product product = ((Product) getIntent().getExtras().getSerializable("product"));
        productDetailBinding.setDetail(product);

        final ShopViewModel shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);

        shopViewModel.getCart().observe(this,new Observer<List<CartItems>>(){

            @Override
            public void onChanged(List<CartItems> cartItems) {
                System.out.println(cartItems.size());
            }
        });

        productDetailBinding.cartDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(String.valueOf(spinner.getSelectedItem()));
                shopViewModel.addItemToCart(product,quantity);
            }
        });
    }
}