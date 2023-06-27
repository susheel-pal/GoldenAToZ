package com.example.goldenatoz.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldenatoz.MainActivity;
import com.example.goldenatoz.R;

import java.util.Calendar;

public class CheckinActivity extends AppCompatActivity {

    private static final String TAG = "CheckinActivity";
    private TextView pickDateBtn;
    private TextView date;
    private TextView dateText;
    private TextView shift;
    private TextView shiftText;
    public TextView todayHours;
    private TextView breakHours;

    int mPresent;
    long loginTime;
    private TextView presentValue;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        date = findViewById(R.id.tv_month_body_date);
        dateText = findViewById(R.id.tv_date_text);
        todayHours = findViewById(R.id.tv_today_hours);
        shift = findViewById(R.id.tv_shift_value);
        shiftText = findViewById(R.id.tv_shift_text);
        breakHours = findViewById(R.id.tv_break_time_hours);
        pickDateBtn = findViewById(R.id.tv_month_body_add_date);
        presentValue = findViewById(R.id.total_present_value);

        date.setVisibility(View.GONE);
        shift.setVisibility(View.GONE);
        dateText.setVisibility(View.GONE);
        shiftText.setVisibility(View.GONE);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CheckinActivity.this);
        //SharedPreferences.Editor editor = sharedPreferences.edit();

        todayHours.setText("08:00:00 hrs");

        loginTime = getIntent().getLongExtra("loginTime",0);
        //Toast.makeText(this, "" + loginTime, Toast.LENGTH_SHORT).show();

        breakHours.setText("01:00 hrs");

        mPresent = getIntent().getIntExtra("mVal" , 0);

        if(mPresent != 0){

            presentValue.setText("" + mPresent);

            //int pre = Integer.parseInt(presentValue.getText().toString());

            //editor.putInt("present", pre);
            //editor.apply();

        } else {

            Log.e("valueError", "value not found");
        }


        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                dateText.setVisibility(View.VISIBLE);
                shiftText.setVisibility(View.VISIBLE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(

                        CheckinActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                date.setVisibility(View.VISIBLE);
                            }
                        },

                        year, month, day);

                datePickerDialog.show();

                shift.setText("Morning");
                shift.setVisibility(View.VISIBLE);
            }
        });

        if(loginTime != 0){

            startService(new Intent(this, BroadcastService.class));
            Log.i(TAG, "Started service");
        }

    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        Log.i(TAG, "Registered broacast receiver");
    }



    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long sTime = intent.getLongExtra("countdown", 0);

            int totalSeconds = (int) sTime;
            int hours = totalSeconds / 3600;
            int remainingSeconds = totalSeconds % 3600;
            int minutes = remainingSeconds / 60;
            int seconds = remainingSeconds % 60;

            todayHours.setText("" + hours + ":" + minutes + ":" + seconds + " hrs ");
        }
    }

    //    @Override
//    protected void onResume() {
//
//        int pre2 = sharedPreferences.getInt("present", 0);
//        Toast.makeText(this, "value " + pre2 , Toast.LENGTH_SHORT).show();
//
//        presentValue.setText("" + pre2);
//
//        super.onResume();
//    }

}


