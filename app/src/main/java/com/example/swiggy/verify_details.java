package com.example.swiggy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class verify_details extends AppCompatActivity {
    TextView phnno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_details);
        getSupportActionBar().hide();

        Intent intn = getIntent();
        String str = intn.getStringExtra("phonenumber");
        phnno = findViewById(R.id.phonenextpage);
        phnno.setText("OTP sent to "+str);
    }
}