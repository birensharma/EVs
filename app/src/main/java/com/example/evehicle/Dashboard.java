package com.example.evehicle;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Dashboard extends AppCompatActivity implements LocationListener{

    private static final int REQUEST_LOCATION = 123;
    private FirebaseAuth auth;
    private Button logout, university, rating;
    private TextView txt, loc;
    private double latitude = 0.0, longitude = 0.0;

    @Override
    protected void onStart() {
        super.onStart();
        getLocation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        requestPermission();
        logout = findViewById(R.id.out);
        txt = findViewById(R.id.text);
        loc = findViewById(R.id.loc);
        rating = findViewById(R.id.ratings);
//        getLoc=findViewById(R.id.getloc);
        university = findViewById(R.id.uni);
        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, ViewFeedback.class));
            }
        });
        university.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Choice.class));
            }
        });
        String locationDetail = "Latitide:" + latitude + "\nLongitude:" + longitude;

        loc.setText(locationDetail);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String u = user.getEmail();
        txt.setText(u);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Dashboard.this, "Logging out...", Toast.LENGTH_SHORT).show();
                auth.signOut();
                finish();
            }
        });
    }

    private void getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Requesting permissions ", Toast.LENGTH_SHORT).show();
            requestPermission();
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0,this);


    }

    public void requestPermission() {

        ActivityCompat.requestPermissions(this,

                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_LOCATION);

    }


    @Override
    public void onLocationChanged(Location location) {
        longitude = (location.getLongitude()/100.00)*100.00;
        latitude = (location.getLatitude()/100.00)*100.00;
        String locationDetail = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();
//        Toast.makeText(Dashboard.this, location+"", Toast.LENGTH_SHORT).show();
        loc.setText(locationDetail);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
