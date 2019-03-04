package com.example.evehicle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Response extends AppCompatActivity {
    private Button a,r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        a=findViewById(R.id.accept);
        r=findViewById(R.id.reject);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Response.this, "Accepted", Toast.LENGTH_SHORT).show();
            finish();
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Response.this, "Rejected", Toast.LENGTH_SHORT).show();
            finish();
            }
        });
    }
}
