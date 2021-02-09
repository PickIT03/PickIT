package com.example.pickit.shopowner;

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

public class ShopOwnerSignUp extends AppCompatActivity {
    CountryCodePicker codePicker;
    TextInputLayout username, password, uuid, reverifypswd ,address;
    TextInputEditText usernameEdittext, passwordEditText, uuidEditText, reverifypswdEditText , addressEditText;
    Button retry;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_owner_sign_up);
        retry = findViewById(R.id.customerSignUpretry);

        codePicker=findViewById(R.id.pick);
        username = findViewById(R.id.ShopOwnerNameSignUp);
        uuid = findViewById(R.id.shopOwnerphoneNoSignUp);
        address = findViewById(R.id.ShopOwnerAddressSignUp);
        password = findViewById(R.id.ShopOwnerPasswordSignUp);
        reverifypswd = findViewById(R.id.ShopOwnerVerifyPasswordSignUp);

        usernameEdittext = findViewById(R.id.shopOwnerNameSignUpEditText);
        passwordEditText = findViewById(R.id.shopOwnerPasswordSignUpEditText);
        uuidEditText = findViewById(R.id.shopOwnerphoneNoSignUpEditText);
        addressEditText=findViewById(R.id.shopOwnerAddressSignUpEditText);
        reverifypswdEditText = findViewById(R.id.shopOwnerVerifyPasswordSignUpEditText);


        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setError(null);
                password.setError(null);
                address.setError(null);
                reverifypswd.setError(null);
                uuid.setError(null);
                usernameEdittext.setText("");
                passwordEditText.setText("");
                addressEditText.setText("");
                uuidEditText.setText("");
                reverifypswdEditText.setText("");
                retry.setVisibility(View.GONE);
            }
        });
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEdittext.getText().toString().equals("") || passwordEditText.getText().toString().equals("") || uuidEditText.getText().toString().equals("") || addressEditText.getText().toString().equals("") || reverifypswdEditText.getText().toString().equals("")) {
                    if (usernameEdittext.getText().toString().equals(""))
                        username.setError("Enter your name");
                    if (passwordEditText.getText().toString().equals(""))
                        password.setError("Enter Password");
                    if (reverifypswdEditText.getText().toString().equals(""))
                        reverifypswd.setError("Enter password again");
                    if (uuidEditText.getText().toString().equals(""))
                        uuid.setError("Enter your number");
                    if (addressEditText.getText().toString().equals(""))
                        address.setError("Enter your address");
                    retry.setVisibility(View.VISIBLE);
                }
                else if(!passwordEditText.getText().toString().equals(reverifypswdEditText.getText().toString())){
                    password.setError("Doesn't matches");
                    reverifypswd.setError("Doesn't matches");
                    retry.setVisibility(View.VISIBLE);
                }
                else{
                    final String dbUUid=codePicker.getSelectedCountryCodeWithPlus()+uuidEditText.getText().toString().trim();
                    final String dbShopName=usernameEdittext.getText().toString().trim();
                    final String dbPassword=passwordEditText.getText().toString().trim();
                    final String dbAddress=addressEditText.getText().toString().trim();
                    final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("ShopOwner");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap<String,Object> va = (HashMap<String,Object>) snapshot.getValue();
                            if(!va.containsKey(dbUUid)){
                                databaseReference.child(dbUUid).setValue(new ShopOwnerHelperClass(dbUUid,dbShopName,dbPassword,dbAddress));
                                startActivity(new Intent(ShopOwnerSignUp.this, Successfully_SignedUp_ShopOwner.class));
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });
    }

}