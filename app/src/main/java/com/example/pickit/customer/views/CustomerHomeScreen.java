package com.example.pickit.customer.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pickit.LoginSignUp;
import com.example.pickit.R;
import com.example.pickit.customer.models.Product;
import com.example.pickit.customer.viewmodels.ShopViewModel;
import com.example.pickit.databinding.ActivityCustomerHomeScreenBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class CustomerHomeScreen extends AppCompatActivity implements RecyclerProductAdapter.ShopInterface, NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActivityCustomerHomeScreenBinding customerHomeScreenBinding;
    RecyclerView recyclerView;
    SearchView searchView;
    public  static RecyclerProductAdapter recyclerProductAdapter;
    ShopViewModel shopViewModel;
    NavigationView navigationView;
    String cususerName;
    public static String cusphoneNumber;
    String cusPass;
    public static String CUS_PHONE_NO="cusPhoneNo";
    public static String CUS_PASS="cusPass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cususerName=CustomerLoginActivity.USER_NAME;
        cusphoneNumber=CustomerLoginActivity.USER_PHONE_NO;
        cusPass=CustomerLoginActivity.USER_PASS;

        customerHomeScreenBinding= DataBindingUtil.setContentView(this, R.layout.activity_customer_home_screen);
        drawerLayout=customerHomeScreenBinding.drawerLayout;
        navigationView=customerHomeScreenBinding.navigationView;

        recyclerView=customerHomeScreenBinding.customerProductRecycler;
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerProductAdapter=new RecyclerProductAdapter(this);
        recyclerView.setAdapter(recyclerProductAdapter);
        shopViewModel=new ViewModelProvider(this).get(ShopViewModel.class);
        shopViewModel.geProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                recyclerProductAdapter.submitList(products);
            }
        });

        drawerLayout.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
       View view= navigationView.inflateHeaderView(R.layout.header);
       TextView textView2 = view.findViewById(R.id.user_phone_no);
       textView2.setText(cusphoneNumber);
       TextView textView = view.findViewById(R.id.user_name);
       textView.setText(cususerName);

    }

    public void ClickNav(View view) {
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void logout(View view){
        logout(this);
    }
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }
    @Override
    public void addItem(Product product) {
        Toast.makeText(CustomerHomeScreen.this,"Item Added "+product.getName(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Product product) {
       shopViewModel.setProduct(product);
       Intent intent = new Intent(CustomerHomeScreen.this,ProductDetail.class);
       intent.putExtra("product",product);
       startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutmenu:
                logout(CustomerHomeScreen.this);
                break;
            case R.id.changepass:
                changePass(CustomerHomeScreen.this);
                break;
            default:
                Toast.makeText(CustomerHomeScreen.this,"Already in Home page ",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public static void logout(final Activity activity){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure ! You want to logout ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                activity.finishAffinity();
                activity.startActivity(new Intent(activity, LoginSignUp.class));
                activity.finish();
//                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void changePass(final Activity activity) {

        Intent intent=new Intent(activity,ChangePassword.class);
        intent.putExtra(CUS_PHONE_NO,cusphoneNumber);
        intent.putExtra(CUS_PASS,cusPass);
        activity.startActivity(intent);

    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
    public void cartClicked(View view){
        startActivity(new Intent(CustomerHomeScreen.this,Cart.class));
    }

    public void searchClicked(View view) {
        startActivity(new Intent(CustomerHomeScreen.this,SearchActivity.class));
    }
}