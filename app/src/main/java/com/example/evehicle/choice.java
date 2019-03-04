package com.example.evehicle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choice extends AppCompatActivity {

    private Button custom, standard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        custom = findViewById(R.id.custom);
        standard = findViewById(R.id.standard);

        standard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(choice.this,university.class));
            }
        });
        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(choice.this,Custom.class));
            }
        });
    }
}
