package com.example.pickit.customer.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.pickit.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class CustomerLoginActivity extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    TextInputEditText PhonenumberEditText,passwordEditText;
    Button button,forgotPassword;
    public static String USER_NAME;
    public static String USER_PHONE_NO;
    public static String USER_PASS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        countryCodePicker = findViewById(R.id.cupicker);
        passwordEditText = findViewById(R.id.customerPasswordLoginEditText);
        PhonenumberEditText = findViewById(R.id.customerphoneNoLoginEditText);
        forgotPassword=findViewById(R.id.forgotPass);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerLoginActivity.this,ForgotPassword.class));
            }
        });

        button=findViewById(R.id.cubutton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String dbPhoneNo=countryCodePicker.getSelectedCountryCodeWithPlus()+PhonenumberEditText.getText().toString().trim();
                final String dbPass=passwordEditText.getText().toString().trim();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Customer");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String,Object> value = (HashMap<String, Object>) snapshot.getValue();
                        if(value.containsKey(dbPhoneNo)){
                            HashMap<String,Object> hashMap=(HashMap<String, Object>) value.get(dbPhoneNo);
                            String pass = String.valueOf(hashMap.get("dbPass"));
                            String dbuser=String.valueOf(hashMap.get("dbUserName"));
                            String dbphone=String.valueOf(hashMap.get("dbPhoneNo"));
                            if(dbPass.equals(pass)){
                                Intent intent = new Intent(CustomerLoginActivity.this, CustomerHomeScreen.class);
                                USER_NAME=dbuser;
                                USER_PASS=dbPass;
                                USER_PHONE_NO=dbphone;
                                startActivity(intent);
                                Animatoo.animateDiagonal(CustomerLoginActivity.this);
                            }
                            else{
                                Toast.makeText(CustomerLoginActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(CustomerLoginActivity.this,"SignUp Again",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}