package com.example.swiggy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class log_in_page extends AppCompatActivity {

    CarouselView carouselView;

    int[] sampleImages = {R.drawable.fp1,R.drawable.fp2,R.drawable.fp3,R.drawable.fp4,R.drawable.fp5};
    ImageListener imageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_page);
        getSupportActionBar().hide();

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