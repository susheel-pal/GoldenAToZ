package com.example.goldenatoz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.example.goldenatoz.attendance.CheckinActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class OTPActivity extends AppCompatActivity {

    private Log mLog;
    private static final String TAG = "OTPActivity";

    private Button myOTPBtn;
    private TextView myErrorOTP;
    private PinView myPinView;
    private ProgressBar myProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private String mAuthVerificationId;

    int present;

    long currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        myOTPBtn = findViewById(R.id.varify_button);
        myPinView = findViewById(R.id.firstPinView);
        myErrorOTP = findViewById(R.id.fillotp_id);
        myProgressBar = findViewById(R.id.verifyprogressbar2_id);

        myPinView = findViewById(R.id.firstPinView);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mAuthVerificationId = getIntent().getStringExtra("verificationID");


    }

    public void verifyOTPBtn (View view){

        String otp = Objects.requireNonNull(myPinView.getText()).toString();
        myErrorOTP.setVisibility(View.INVISIBLE);

        if(otp.isEmpty()){
            myErrorOTP.setText(R.string.please_enter_otp_first);
            myErrorOTP.setVisibility(View.VISIBLE);

        } else{

            myOTPBtn.setEnabled(false);

            myProgressBar.setVisibility(View.VISIBLE);

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mAuthVerificationId, otp);
            signInWithPhoneAuthCredential(credential);
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(OTPActivity.this, new OnCompleteListener<AuthResult>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            currentTime = System.currentTimeMillis();
                            myProgressBar.setVisibility(View.INVISIBLE);

                            present++;

                            sendUserToHome();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                myErrorOTP.setText(R.string.invalid_verification_code);
                                myErrorOTP.setVisibility(View.VISIBLE);
                                myProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                        myOTPBtn.setEnabled(true);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser != null){
            sendUserToHome();
        }
    }

    public void sendUserToHome() {
        Intent homeIntent = new Intent(OTPActivity.this, MainActivity.class);
        homeIntent.putExtra("login_time", currentTime);
        //homeIntent.putExtra("presentValue", present);
        homeIntent.putExtra("present", present);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }
}