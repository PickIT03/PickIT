package com.example.pickit.customer.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pickit.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePassword extends AppCompatActivity {
    Button button;
    TextInputEditText oldpassedit,newpassedit,retypepassedit;
    String cusPhoneNo,cusPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        cusPhoneNo=getIntent().getExtras().getString(CustomerHomeScreen.CUS_PHONE_NO).trim();
        cusPass=getIntent().getExtras().getString(CustomerHomeScreen.CUS_PASS).trim();

        oldpassedit=findViewById(R.id.oldPassEditText);
        newpassedit=findViewById(R.id.newPassEditText);
        retypepassedit=findViewById(R.id.retypePassEditText);

        button=findViewById(R.id.saveChanges);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String oldpass=oldpassedit.getText().toString().trim();
                final String newpass=newpassedit.getText().toString().trim();
                final String repass=retypepassedit.getText().toString().trim();
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Customer");
                if(cusPass.equals(oldpass)) {
                    if(newpass.equals(repass)) {
                        databaseReference.child(cusPhoneNo).child("dbPass").setValue(newpass);
                        startActivity(new Intent(ChangePassword.this, CustomerLoginActivity.class));
                    }
                    else {
                        Toast.makeText(ChangePassword.this,"New Password and Retyped Password doesn't match",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(ChangePassword.this,"Worng Old Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}