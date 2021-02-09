package com.example.pickit.customer.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pickit.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class CustomerSignUp extends AppCompatActivity {
    TextInputLayout username, password, phonenumber, reverifypswd;
    TextInputEditText usernameEdittext, passwordEditText, PhonenumberEditText, reverifypswdEditText;
    CountryCodePicker countryCodePicker;
    Button retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);
        retry = findViewById(R.id.customerSignUpretry);
        username = findViewById(R.id.customerNameSignUp);
        phonenumber = findViewById(R.id.customerphoneNoSignUp);
        password = findViewById(R.id.customerPasswordSignUp);
        reverifypswd = findViewById(R.id.customerVerifyPasswordSignUp);
        countryCodePicker = findViewById(R.id.picker);

        usernameEdittext = findViewById(R.id.customerNameSignUpEditText);
        passwordEditText = findViewById(R.id.customerPasswordSignUpEditText);
        PhonenumberEditText = findViewById(R.id.customerphoneNoSignUpEditText);
        reverifypswdEditText = findViewById(R.id.customerVerifyPasswordSignUpEditText);



        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setError(null);
                password.setError(null);
                reverifypswd.setError(null);
                phonenumber.setError(null);
                usernameEdittext.setText("");
                passwordEditText.setText("");
                PhonenumberEditText.setText("");
                reverifypswdEditText.setText("");
                retry.setVisibility(View.GONE);
            }
        });

    }
    public void signed(View view) {
        if (usernameEdittext.getText().toString().equals("") || passwordEditText.getText().toString().equals("") || PhonenumberEditText.getText().toString().equals("") || reverifypswdEditText.getText().toString().equals("")||PhonenumberEditText.getText().toString().length()<=9) {
            if (usernameEdittext.getText().toString().equals(""))
                username.setError("Enter your name");
            if (passwordEditText.getText().toString().equals(""))
                password.setError("Enter Password");
            if (reverifypswdEditText.getText().toString().equals(""))
                reverifypswd.setError("Enter password again");
            if (PhonenumberEditText.getText().toString().equals(""))
                phonenumber.setError("Enter your number");
            if(PhonenumberEditText.getText().toString().length()<=9)
                phonenumber.setError("Invalid Number");
            retry.setVisibility(View.VISIBLE);
        }
        else if(!passwordEditText.getText().toString().equals(reverifypswdEditText.getText().toString())){
            password.setError("Doesn't matches");
            reverifypswd.setError("Doesn't matches");
            retry.setVisibility(View.VISIBLE);
        }
        else{
            final String dbPhoneNo=countryCodePicker.getSelectedCountryCodeWithPlus()+PhonenumberEditText.getText().toString().trim();
            final String dbUserName=usernameEdittext.getText().toString().trim();
            final String dbPass=passwordEditText.getText().toString().trim();
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Customer");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String,Object> value = (HashMap<String,Object>) snapshot.getValue();
                    if(!value.containsKey(dbPhoneNo)){
                        databaseReference.child(dbPhoneNo).setValue(new CustomerHelperclass(dbUserName,dbPass,dbPhoneNo));
                        startActivity(new Intent(CustomerSignUp.this,Successfully_SignedUp_Customer.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }


}