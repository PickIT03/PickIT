package com.example.pickit.shopowner;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pickit.R;
import com.example.pickit.shopowner.model.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductActivity extends AppCompatActivity {
    EditText pname;
    EditText price;
    Spinner productSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        pname=findViewById(R.id.editTextProductName);
        price=findViewById(R.id.editTextPrice);
        productSelect=findViewById(R.id.spinnerProduct);
    }

    public void addProduct(View view) {
        Product product;
        String ProductName = pname.getText().toString();
        double value= Double.valueOf(price.getText().toString());
        String availability="true";
        String UUID= ShopOwnerHomeScreen.UUID;
        String imageUrl="";
        String spinnerValue = (String) productSelect.getSelectedItem();

        switch (spinnerValue){
            case "apple":
                imageUrl=getResources().getString(R.string.apple);
                break;
            case "lays":
                imageUrl=getResources().getString(R.string.lays);
                break;
            case "maggi":
                imageUrl=getResources().getString(R.string.maggi);
                break;
            case "DoveShampoo":
                imageUrl=getResources().getString(R.string.doveshampoo);
                break;
            case "Salt":
                imageUrl=getResources().getString(R.string.salt);
                break;
            case "SugarLoose":
                imageUrl=getResources().getString(R.string.sugarloose);
                break;
            case "Udhayam Moong Dhall":
                imageUrl=getResources().getString(R.string.udhayammdhaal);
                break;
            case "Udhayam Toor Dhall":
                imageUrl=getResources().getString(R.string.udhayamtdhall);
                break;
            case "DarkFantasy":
                imageUrl=getResources().getString(R.string.darkfantasy);
                break;
            case "Ujala":
                imageUrl=getResources().getString(R.string.ujala);
                break;
            case "Oats":
                imageUrl=getResources().getString(R.string.oats);
                break;
        }
        product=new Product(UUID,ProductName,value,availability,imageUrl);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ShopOwner/"+UUID+"/ProductDetails");
        databaseReference.child(ProductName).setValue(product);
        pname.setText("");
        price.setText("");


    }
}