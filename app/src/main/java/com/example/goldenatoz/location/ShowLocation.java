package com.example.goldenatoz.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldenatoz.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ShowLocation extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;

    private TextView city;
    private TextView address;
    private TextView country;
    private TextView latitude;
    private TextView longitude;

    private Button getLocation;

    private final int REQUEST_CODE = 100;

    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        country = findViewById(R.id.country);
        getLocation = findViewById(R.id.getLocation);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
            }
        });

    }

    private void getLastLocation(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Geocoder geoCoder = new Geocoder(ShowLocation.this, Locale.getDefault());
                                List<Address> addresses;

                                try{

                                    addresses = geoCoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                                    double lat = addresses.get(0).getLatitude();
                                    double lon = addresses.get(0).getLongitude();
                                    String add = addresses.get(0).getAddressLine(0);
                                    String cityy = addresses.get(0).getLocality();
                                    String countryy = addresses.get(0).getCountryName();

                                    latitude.setText("Latitude: " + lat);
                                    longitude.setText("Longitude: " + lon);
                                    address.setText("Address: " + add);
                                    city.setText("City: "+ cityy);
                                    country.setText("Country: " + countryy);

                                    //Toast.makeText(ShowLocation.this, " latitude: " + lat, Toast.LENGTH_LONG).show();

                                } catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }
                    });

        } else {
            askPermission();
        }
    }

    private void askPermission(){
        ActivityCompat.requestPermissions(this,PERMISSIONS , REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE){

            if(grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            } else{
                Toast.makeText(this, "permission required", Toast.LENGTH_LONG).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}