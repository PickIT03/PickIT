package com.example.pickit.customer.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pickit.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class ForgotPassword extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    Button next;
    TextInputEditText phoneNoEditText;
    public static String PHONE_NO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        countryCodePicker=findViewById(R.id.ccPicker);
        phoneNoEditText=findViewById(R.id.forgotPhoneNoEditText);
        next=findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNo=countryCodePicker.getSelectedCountryCodeWithPlus()+phoneNoEditText.getText().toString().trim();
                Query checkUser = FirebaseDatabase.getInstance().getReference("Customer").orderByChild("dbPhoneNo").equalTo(phoneNo);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            phoneNoEditText.setError(null);
                            Intent intent=new Intent(ForgotPassword.this, VerifyOTP.class);
                            PHONE_NO=phoneNo;
                            startActivity(intent);
                            finish();
                        }
                        else{
                            phoneNoEditText.setError("No such Customer exists");
                            phoneNoEditText.requestFocus();
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