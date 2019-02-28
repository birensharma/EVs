package com.example.evehicle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class university extends AppCompatActivity {

    private Button send;
    private EditText input;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);

        init();
        db=FirebaseDatabase.getInstance().getReference("/notification/1/");
        send = findViewById(R.id.send);
        input =findViewById(R.id.input);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input.getText().toString().equals(""))
                    Toast.makeText(university.this, "Input is empty", Toast.LENGTH_SHORT).show();
                else
                    db.child("text").setValue(input.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(university.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                            });
            }
        });



    }
    private void init(){
        FirebaseMessaging.getInstance().subscribeToTopic("Message")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(university.this, "Subscribed to topic successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(university.this, "Unable to subscribe", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}
