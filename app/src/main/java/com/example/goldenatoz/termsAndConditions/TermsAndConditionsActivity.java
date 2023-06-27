package com.example.goldenatoz.termsAndConditions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.goldenatoz.LoginActivity;
import com.example.goldenatoz.R;

public class TermsAndConditionsActivity extends AppCompatActivity {

    //Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

//        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean agreed = sharedPreferences.getBoolean("agreed",false);
//        if (!agreed) {
//            new AlertDialog.Builder(this)
//                    .setTitle("License agreement")
//                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putBoolean("agreed", true);
//                            editor.commit();
//
//                            Intent intent = new Intent(TermsAndConditionsActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                        }
//                    })
//                    .setNegativeButton("No", null)
//                    .setMessage(" terms and conditions...")
//                    .show();
//        }
    }
}