package com.example.evehicle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CarDashboard extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button logout,resp;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_dashboard);
        logout=findViewById(R.id.out);
        txt=findViewById(R.id.text);
        resp=findViewById(R.id.resp);
        resp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CarDashboard.this,Response.class));
            }
        });
        auth=FirebaseAuth.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        String u=user.getEmail();
        txt.setText(u);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finish();
            }
        });

    }

}
