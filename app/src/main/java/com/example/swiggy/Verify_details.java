package com.example.swiggy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verify_details extends AppCompatActivity {
    TextView phnno,countertv,resendtv;
    PinView otpnumber;
    Button submitotp;
    String otp,mverificationId,number;
    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;
    int cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_details);
        getSupportActionBar().hide();

        //initialize
        initializedelements();

        //mCallbackInitialize
        mcallbackiniti();


        //sendotp
        sendotp();


    }

    private void sendotp() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        resendtv.setEnabled(false);
        counterstartfun();
        Toast.makeText(getApplicationContext(), "OTP sent successfully", Toast.LENGTH_LONG).show();
    }

    private void counterstartfun() {
        cnt = 60;
        new CountDownTimer(60000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                if(cnt==60){
                    countertv.setText("01:00");
                }else{
                    countertv.setText("00:"+cnt);
                }
                cnt --;
            }

            @Override
            public void onFinish() {
                resendtv.setEnabled(true);
                countertv.setText("Didn't receive the OTP ?");
            }
        }.start();
    }

    private void mcallbackiniti() {
        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d("Complete", "onVerificationCompleted:" + credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w("Verify Failed", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    resendtv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                Log.d("Code sent", "onCodeSent:" + verificationId);

                mverificationId = verificationId;
            }
        };
    }

    private void initializedelements() {
        Intent intn = getIntent();
        String str = intn.getStringExtra("phonenumber");
        phnno = findViewById(R.id.phonenextpage);
        phnno.setText("OTP sent to "+str);
        number = "+91"+str;

        otpnumber = findViewById(R.id.pinview);
        submitotp = findViewById(R.id.otpsubmit);
        mAuth = FirebaseAuth.getInstance();
        countertv = findViewById(R.id.counter);
        resendtv = findViewById(R.id.resendotp);

        resendtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendotp();
            }
        });

        submitotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mverificationId, otp);
                signInWithPhoneAuthCredential(credential);
            }
        });

        otpnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String stri = otpnumber.getText().toString();
                Log.e("pin",stri);
                if(stri.length() ==6){
                    submitotp.setEnabled(true);
                    submitotp.setText("Continue");
                    submitotp.setBackgroundColor(Color.parseColor("#F66E42"));
                    otp=stri;
                }else{
                    submitotp.setEnabled(false);
                    submitotp.setText("ENTER OTP");
                    submitotp.setBackgroundColor(Color.parseColor("#F8AB92"));
                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Successful Login", "signInWithCredential:success");

                            startActivity(new Intent(Verify_details.this,MainActivity.class));
                            finish();
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("Login Failed", "signInWithCredential:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getApplicationContext(), "OTP is wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Verify_details.this,OpeningPageBeforeLogin.class));
        finish();
    }
}