package com.example.swiggy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    FirebaseUser user;
    ImageView mickey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        mickey = findViewById(R.id.mickey);

        user = FirebaseAuth.getInstance().getCurrentUser();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user!=null){
                  startActivity(new Intent(SplashActivity.this, MainActivity.class));
                  finish();
                }else{
                    startActivity(new Intent(SplashActivity.this,OpeningPageBeforeLogin.class));
                    finish();
                }
            }
        }, 1500);
    }
    public void zoom(View view) {
        Animation anim = new ScaleAnimation(1.0f, 0.5f, 1.0f, 0.5f, mickey.getPivotX(), mickey.getPivotY());
        anim.setDuration(2000);
        //anim.setFillAfter(true);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.REVERSE);
        mickey.startAnimation(anim);
    }
}