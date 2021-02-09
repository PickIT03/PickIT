package com.example.pickit.shopowner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pickit.R;

public class Successfully_SignedUp_ShopOwner extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully__signed_up__shopowner);

    }

    public void loginSigned(View view) {
        startActivity(new Intent(Successfully_SignedUp_ShopOwner.this, ShopOwnerLogin.class));
    }
}
