package com.example.goldenatoz.attendance;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.goldenatoz.LoginActivity;
import com.example.goldenatoz.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class BroadcastService extends Service {

    private final static String TAG = "BroadcastService";

    public static final String COUNTDOWN_BR = "com.example.goldenatoz.attendance.countdown_br";
    Intent bi = new Intent(COUNTDOWN_BR);

    public CountDownTimer countDownTimer;
    public long timeLeftInMillis = 32400000;
    long time;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Starting timer...");

//        cdt = new CountDownTimer(30000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
//                bi.putExtra("countdown", millisUntilFinished);
//                sendBroadcast(bi);
//            }
//
//            @Override
//            public void onFinish() {
//                Log.i(TAG, "Timer finished");
//            }
//        };
//
//        cdt.start();

        mAuth = FirebaseAuth.getInstance();

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                time++;

                bi.putExtra("countdown", time);
                sendBroadcast(bi);
            }

            @Override
            public void onFinish() {
                //todayHours.setText("Timer finished");
                // Perform any desired action when the timer finishes
                //Toast.makeText(BroadcastService.this, " logout ", Toast.LENGTH_SHORT).show();

                mAuth.signOut();
                Intent intent = new Intent(BroadcastService.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }.start();
    }

    @Override
    public void onDestroy() {

        countDownTimer.cancel();
        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }

    public void stopTimer() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getAction();
        if (action != null && action.equals("com.example.goldenatoz.attendance.ACTION_STOP_SERVICE")) {
            stopSelf();
        }



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}