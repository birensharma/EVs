package com.example.evehicle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.evehicle.com.cabs.frnds.cabme.MainActivityRide;

public class SelectLogin extends AppCompatActivity {
private Button car,user,ride;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login);
        car=findViewById(R.id.carlogin);
        user=findViewById(R.id.userlogin);
        ride=findViewById(R.id.carride);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectLogin.this,CarLogin.class));
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectLogin.this,MainActivity.class));
            }
        });
        ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectLogin.this, MainActivityRide.class));
            }
        });

    }
}
