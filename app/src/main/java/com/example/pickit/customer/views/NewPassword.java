package com.example.pickit.customer.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pickit.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewPassword extends AppCompatActivity {
    TextInputEditText nepass,repass;
    TextInputLayout ne,re;
    String pho;
    Button click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        nepass=findViewById(R.id.fornewpassedit);
        repass=findViewById(R.id.forrepassedit);
        ne=findViewById(R.id.fornewpass);
        re=findViewById(R.id.forrepass);
        pho=VerifyOTP.PHONE;
        click=findViewById(R.id.update);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nepass.getText().toString().equals(repass.getText().toString())){
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Customer");
                    databaseReference.child(pho).child("dbPass").setValue(nepass.getText().toString());
                    startActivity(new Intent(NewPassword.this,CustomerLoginActivity.class));
                }
                else {
                    Toast.makeText(NewPassword.this,"New Password and Retyped Password doesn't match",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}