package com.example.swiggy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    LinearLayout linearLayout;
    LayoutInflater layoutInflater;
    View profileview,homeview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        getSupportActionBar().hide();

        //bottomnavigation allwork
        bottomnavfun();


    }

    private void bottomnavfun() {
        bottomNavigationView = findViewById(R.id.bottomnavmain);
        linearLayout = findViewById(R.id.mainactivitypage);
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        profileview = layoutInflater.inflate(R.layout.user_profile,null);
        homeview = layoutInflater.inflate(R.layout.home_activity,null);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profilemenu:
                        linearLayout.removeAllViews();
                        linearLayout.addView(profileview);
                        break;
                    case R.id.navhome:
                        linearLayout.removeAllViews();
                        linearLayout.addView(homeview);
                        break;
                }
                return true;
            }
        });
    }
}