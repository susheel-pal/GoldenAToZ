package com.example.goldenatoz.languages;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goldenatoz.R;

public class MainFragment extends Fragment {
    // Initialize variable
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view =inflater.inflate(R.layout.fragment_main, container, false);

        // Assign variable
        textView=view.findViewById(R.id.text_view);

        // Get Title
        String sTitle=getArguments().getString("title");

        // Set title on text view
        textView.setText(sTitle);

        // return view
        return view;
    }
}