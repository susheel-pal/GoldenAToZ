package com.example.goldenatoz.attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.goldenatoz.R;

public class ShiftEntryActivity extends AppCompatActivity {

    private TextView name;
    private TextView id;
    private Spinner sp;
    private TextClock clockFrom;
    private TextClock clockTo;
    private EditText remark;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_entry);

        name = findViewById(R.id.tv_name);
        id = findViewById(R.id.tv_id);
        sp = findViewById(R.id.spinner);
        clockFrom = findViewById(R.id.clock_from);
        clockTo = findViewById(R.id.clock_to);
        remark = findViewById(R.id.ed_remark);


        submit = findViewById(R.id.submit);
    }
}