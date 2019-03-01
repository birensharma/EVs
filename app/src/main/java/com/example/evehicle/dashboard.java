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

public class dashboard extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 123;
    private FirebaseAuth auth;
    private Button logout,getLoc,university;
    private TextView txt, loc;
    private double latitude = 0.0, longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        requestPermission();
        logout=findViewById(R.id.out);
        txt=findViewById(R.id.text);
        loc=findViewById(R.id.loc);
        getLoc=findViewById(R.id.getloc);
        university=findViewById(R.id.uni);

        university.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           startActivity(new Intent(dashboard.this,choice.class));
            }
        });
        String locationDetail="Latitide:"+latitude+"\nLongitude:"+longitude;

        loc.setText(locationDetail);

        auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        String u=user.getEmail();
        txt.setText(u);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(dashboard.this, "Logging out...", Toast.LENGTH_SHORT).show();
                auth.signOut();
                finish();
            }
        });
        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });

    }
    private void getLocation(){
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           Toast.makeText(this, "Requesting permissions ", Toast.LENGTH_SHORT).show();
            requestPermission();
        }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000, 10, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            longitude = location.getLongitude();
                            latitude = location.getLatitude();
                            String locationDetail="Latitide:"+latitude+"\nLongitude:"+longitude;
                            Toast.makeText(dashboard.this, locationDetail, Toast.LENGTH_SHORT).show();
                            loc.setText(locationDetail);                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {
                            Toast.makeText(dashboard.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onProviderEnabled(String provider) {
                            Toast.makeText(dashboard.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onProviderDisabled(String provider) {
                            Toast.makeText(dashboard.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();

                        }
                    });



    }
    public void  requestPermission(){

        ActivityCompat.requestPermissions(this,

                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_LOCATION);

    }


}
