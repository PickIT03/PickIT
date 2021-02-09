package com.example.pickit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.pickit.customer.views.CustomerLoginActivity;
import com.example.pickit.customer.views.CustomerSignUp;
import com.example.pickit.shopowner.ShopOwnerLogin;
import com.example.pickit.shopowner.ShopOwnerSignUp;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginSignUp extends AppCompatActivity {
    Spinner spinner;
    ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);
        strings=new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.entry)));
        spinner=findViewById(R.id.spinnerView);
        if(!checkInternetConnection(LoginSignUp.this)){
            createNotification();
        }
    }
    @Override
    public void onBackPressed() {
        LoginSignUp.this.finishAffinity();
        System.exit(0);
    }

    public void login(View view) {

        String seleted=spinner.getSelectedItem().toString().trim();
        if(seleted.equals(strings.get(0))){
            startActivity(new Intent(LoginSignUp.this, CustomerLoginActivity.class));

            Animatoo.animateSpin(LoginSignUp.this);

        } else{
            startActivity(new Intent(LoginSignUp.this, ShopOwnerLogin.class));
        }
    }



    public void signUp(View view) {

        String seleted=spinner.getSelectedItem().toString().trim();
        if(seleted.equals(strings.get(0))){
            startActivity(new Intent(LoginSignUp.this, CustomerSignUp.class));
        } else{
            startActivity(new Intent(LoginSignUp.this, ShopOwnerSignUp.class));
        }
    }

    private void createNotification() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginSignUp.this);
        builder.setMessage("Please Connect the internet ....")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create().show();

    }

    private boolean checkInternetConnection(LoginSignUp loginSignUp) {
        ConnectivityManager connectivityManager = (ConnectivityManager) loginSignUp.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifiInfo!=null&&wifiInfo.isConnected())||(mobileInfo!=null&&mobileInfo.isConnected())){
            return true;
        }
        else{
            return false;
        }


    }
}