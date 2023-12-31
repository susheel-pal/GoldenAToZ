package com.example.goldenatoz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldenatoz.termsAndConditions.TermsAndConditionsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private EditText myPhoneNumber;
    private TextView myError;
    private Button myVerifyBtn;
    private ProgressBar myVerifyProgress;
    private ImageView TAndC;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    long currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        myPhoneNumber = findViewById(R.id.edit_enter_number);
        myVerifyBtn = findViewById(R.id.login_button);
        myVerifyProgress = findViewById(R.id.verifyprogressbar_id);
        myError = findViewById(R.id.fillnumber_id);
        TAndC = findViewById(R.id.show_term_and_condition);

        TAndC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, TermsAndConditionsActivity.class);
                startActivity(intent);
            }
        });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            signInWithPhoneAuthCredential(phoneAuthCredential);
            myVerifyProgress.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            String error = e.toString();
            Log.i("Message : ", e.toString());
            myError.setText(e.toString());

            String[] split = error.split(": ", 0);
            error = split[1];
            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();


            myVerifyProgress.setVisibility(View.INVISIBLE);
            myVerifyBtn.setEnabled(true);
        }

        @Override
        public void onCodeSent(@NonNull final String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            myVerifyProgress.setVisibility(View.INVISIBLE);
                            Intent otpIntent = new Intent(LoginActivity.this, OTPActivity.class);
                            otpIntent.putExtra("verificationID", s);
                            startActivity(otpIntent);
                        }
                    },
                    5000);
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);

        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            currentTime = System.currentTimeMillis();

                            sendUserToHome();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                myError.setText(R.string.error_in_signing_in);
                                myError.setVisibility(View.VISIBLE);
                                // The verification code entered was invalid
                            }
                        }

                        myVerifyBtn.setEnabled(true);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCurrentUser != null) {
            sendUserToHome();
        }
    }

    private void sendUserToHome() {
        Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
        homeIntent.putExtra("login_time", currentTime);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
        finish();
    }

    public void verifyNumberBtn(View view) {
        String phoneNumber = myPhoneNumber.getText().toString().trim();

        if (phoneNumber.isEmpty()) {

            myError.setText(R.string.please_enter_no_first);
            myError.setVisibility(View.VISIBLE);

        } else {

            if (phoneNumber.length() == 10) {

                myError.setVisibility(View.INVISIBLE);
                myVerifyProgress.setVisibility(View.VISIBLE);
                myVerifyBtn.setEnabled(false);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + phoneNumber,
                        60,
                        TimeUnit.SECONDS,
                        LoginActivity.this,
                        mCallbacks
                );

            } else {

                myError.setText(R.string.please_enter_proper_no);
                myError.setVisibility(View.VISIBLE);

            }
        }
    }
}