package com.example.goldenatoz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldenatoz.attendance.BroadcastService;
import com.example.goldenatoz.attendance.CheckinActivity;
import com.example.goldenatoz.attendance.ShiftEntryActivity;
import com.example.goldenatoz.holiday.HolidayCalenderActivity;
import com.example.goldenatoz.languages.Languages;
import com.example.goldenatoz.languages.TabLayoutActivity;
import com.example.goldenatoz.languages.YourRoleActivity;
import com.example.goldenatoz.location.ShowLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private TextView loginTime;                  //for login time
    private TextView logoutTime;
    private Button getLocation2;
    private Button logout;
    private Button btnAttendance;
    private Button holiday;
    private Button shift;
    private Button yourRole;
    private Button languages;
    private Button tabLayout;

    public FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private long mLoginTime;
    private String mLogoutTime;
    int pVal;

    private Timer mTimer;

    BroadcastService broadcastService;
    Intent serviceIntent;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginTime = findViewById(R.id.login_time);
        logoutTime = findViewById(R.id.logout_time);
        getLocation2 = findViewById(R.id.getLocation2);
        logout = findViewById(R.id.logout);

        btnAttendance = findViewById(R.id.btn_attendance);
        holiday = findViewById(R.id.holiday);

        shift = findViewById(R.id.go_to_shift);

        yourRole = findViewById(R.id.your_role);

        languages = findViewById(R.id.languages);

        tabLayout = findViewById(R.id.tabLayout);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mLoginTime = getIntent().getLongExtra("login_time", 0);
        pVal = getIntent().getIntExtra("present", 0);

        loginTime.setText("" + mLoginTime);                   //for login time

        broadcastService = new BroadcastService();

        tabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TabLayoutActivity.class);
                startActivity(intent);
            }
        });

        yourRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, YourRoleActivity.class);
                startActivity(intent);
            }
        });

        languages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Languages.class);
                startActivity(intent);
            }
        });

        shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShiftEntryActivity.class);
                startActivity(intent);
            }
        });

        holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HolidayCalenderActivity.class);
                startActivity(intent);
            }
        });

        btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CheckinActivity.class);
                intent.putExtra("mVal" , pVal);
                intent.putExtra("loginTime", mLoginTime);
                startActivity(intent);
            }
        });

        getLocation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowLocation.class);
                startActivity(intent);
            }
        });


        if(mLoginTime != 0) {
            serviceIntent = new Intent(getApplicationContext(), BroadcastService.class);   //service
            startService(serviceIntent);
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();

                context = getApplicationContext();

                serviceIntent.setAction("com.example.goldenatoz.attendance.ACTION_STOP_SERVICE");
                context.stopService(serviceIntent);

                //stopTimer();
                mLogoutTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
                Toast.makeText(MainActivity.this, "Logout Time: " + mLogoutTime, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mCurrentUser == null){
            sendUserToLogin();
        }
    }

    public void sendUserToLogin(){
        Intent intent= new Intent(MainActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }




}