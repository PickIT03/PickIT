package com.example.pickit.customer.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pickit.R;

public class SuccessfullyOrdered extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succuessfully_ordered);
        button=findViewById(R.id.continueShopping);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                button.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
            }
        },3000);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuccessfullyOrdered.this, CustomerHomeScreen.class));
            }
        });
    }
}