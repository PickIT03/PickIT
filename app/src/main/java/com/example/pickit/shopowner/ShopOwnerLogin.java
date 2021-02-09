package com.example.pickit.shopowner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pickit.LoginSignUp;
import com.example.pickit.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class ShopOwnerLogin extends AppCompatActivity {
    CountryCodePicker picker;
    TextInputEditText passwordEditText, uuidEditText;
    Button button;
    public static String SHOP_UUID="shopuuid";
    public static  String shopOwnerUUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_login);
        picker=findViewById(R.id.soccpick);
        uuidEditText=findViewById(R.id.sophoneNoLoginEditText);
        passwordEditText=findViewById(R.id.soPasswordLoginEditText);
        button=findViewById(R.id.sobutton2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String dbUUID=picker.getSelectedCountryCodeWithPlus()+uuidEditText.getText().toString().trim();
                final String dbPassword=passwordEditText.getText().toString().trim();
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("ShopOwner");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String,Object> val = (HashMap<String, Object>) snapshot.getValue();
                        if(val.containsKey(dbUUID)){
                            HashMap<String,Object> hashMap=(HashMap<String, Object>) val.get(dbUUID);
                            String pass = String.valueOf(hashMap.get("dbPassword"));
                            if(dbPassword.equals(pass)){
                                Intent intent=new Intent(ShopOwnerLogin.this, ShopOwnerHomeScreen.class);
                                shopOwnerUUID=dbUUID;
                                intent.putExtra(SHOP_UUID,dbUUID);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(ShopOwnerLogin.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(ShopOwnerLogin.this,"SignUp Again",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ShopOwnerLogin.this, LoginSignUp.class));
    }
}