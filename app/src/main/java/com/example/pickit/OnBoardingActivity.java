package com.example.pickit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class OnBoardingActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ScreenSligeAdapter screenSligeAdapter;
    private static final int NO_OF_PAGES=3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarading);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        viewPager= findViewById(R.id.pager);
        screenSligeAdapter=new ScreenSligeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(screenSligeAdapter);
    }
    private  class ScreenSligeAdapter extends FragmentStatePagerAdapter {


        public ScreenSligeAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new OnBoardingFragment1();
                case 1:
                    OnBoardingFragment2 onBoardingFragment2 = new OnBoardingFragment2();
                    return onBoardingFragment2;
                case 2:
                    OnBoardingFragment3 onBoardingFragment3=new OnBoardingFragment3();
                    return onBoardingFragment3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NO_OF_PAGES;
        }
    }
    public void skip(View v){
        startActivity(new Intent(OnBoardingActivity.this, LoginSignUp.class));
        finish();

    }
    public void continues(View v){
        startActivity(new Intent(OnBoardingActivity.this, LoginSignUp.class));
        finish();

    }
}