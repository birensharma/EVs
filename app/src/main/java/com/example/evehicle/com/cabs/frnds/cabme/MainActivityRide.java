package com.example.evehicle.com.cabs.frnds.cabme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.evehicle.R;

public class MainActivityRide extends AppCompatActivity {
    private Button mdriver, mcustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ride);

        mdriver = (Button) findViewById(R.id.driver);
        mcustomer = (Button) findViewById(R.id.customer);

        mdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityRide.this, driverloginactivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });

        mcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityRide.this, customerloginactivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });



    }

}