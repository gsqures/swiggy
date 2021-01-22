package com.example.swiggy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class OpeningPageBeforeLogin extends AppCompatActivity {

    CarouselView carouselView;
    TextView login;

    int[] sampleImages = {R.drawable.fp1,R.drawable.fp2,R.drawable.fp3,R.drawable.fp4,R.drawable.fp5};
    ImageListener imageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openingpage_before_login);
        getSupportActionBar().hide();

        login = findViewById(R.id.logintext);
        Spannable spantext = new SpannableString("Have an Account ? Login");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(OpeningPageBeforeLogin.this,LoginActivity.class));
                //Toast.makeText(OpeningPageBeforeLogin.this, "Click on span", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(Color.BLUE);
            }
        };
        spantext.setSpan(clickableSpan,18,23,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        login.setText(spantext);
        login.setClickable(true);
        login.setMovementMethod(LinkMovementMethod.getInstance());

        //carouseview
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };
        carouselView.setImageListener(imageListener);
    }

}