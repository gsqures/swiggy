package com.example.swiggy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class OpeningPageBeforeLogin extends AppCompatActivity {

    CarouselView carouselView;
    TextView login;
    Dialog dialog;
    int[] sampleImages = {R.drawable.fp1,R.drawable.fp2,R.drawable.fp3,R.drawable.fp4,R.drawable.fp5};
    ImageListener imageListener;
    Button submitph;
    EditText phn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openingpage_before_login);
        getSupportActionBar().hide();

        //alllogintextworks
        lginspantext();


        //carouseview
        setandshowcarouselView();

    }

    private void setandshowcarouselView() {
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

    private void lginspantext() {
        login = findViewById(R.id.logintext);
        Spannable spantext = new SpannableString("Have an Account ? Login");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //showinglogindialog
                showlogindialog();
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
    }

    private void showlogindialog() {

        dialog = new Dialog(OpeningPageBeforeLogin.this);
        LayoutInflater infset = this.getLayoutInflater();
        View dialogView = infset.inflate(R.layout.logindialog,null);
        dialog.setContentView(dialogView);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getAttributes().windowAnimations = R.style.DialogAnimation;
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        dialogView.findViewById(R.id.phonenumber).requestFocus();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.show();

        //alldialog elements
        phn=dialogView.findViewById(R.id.phonenumber);
        submitph=dialogView.findViewById(R.id.phonenumbersubmit);
        submitph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intn = new Intent(OpeningPageBeforeLogin.this,Verify_details.class);
                intn.putExtra("phonenumber",phn.getText().toString());
                startActivity(intn);
            }
        });


        //buttonenable
        phn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String stf = phn.getText().toString();
                Log.e("edittext",stf);
                if(stf.length()==10){
                    submitph.setText("Continue");
                    submitph.setEnabled(true);
                }else{
                    submitph.setText("Enter Phone Number");
                    submitph.setEnabled(false);
                }
            }
        });

    }

}