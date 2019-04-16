package com.example.evehicle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class University extends AppCompatActivity{

    private Button send;
    private EditText input;
    private DatabaseReference db;
    private ValueEventListener valueEventListener;
    private String umail;
    private Spinner from,to;
    private  ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_university);
        init();
        from=findViewById(R.id.from);
        to=findViewById(R.id.to);
        db=FirebaseDatabase.getInstance().getReference();
        send = findViewById(R.id.send);
        input =findViewById(R.id.input);
        umail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                db.removeEventListener(valueEventListener);
                for(DataSnapshot dd:dataSnapshot.getChildren()){
                    if(dd.child("email").getValue(String.class).equals(umail)){
                        db.child(dd.getKey()+"/notification/text").setValue(from.getSelectedItem().toString()+
                                " - "+to.getSelectedItem().toString()+
                                " | "+input.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(University.this, "Notification Sent", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(University.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input.getText().toString().equals(""))
                    Toast.makeText(University.this, "Input is empty", Toast.LENGTH_SHORT).show();
                else
                    db.addValueEventListener(valueEventListener);
            }
        });

       adapter= ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        from.setAdapter(adapter);
        to.setAdapter(adapter);


    }
    private void init(){
        FirebaseMessaging.getInstance().subscribeToTopic("Message")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful())
                            Toast.makeText(University.this, "Unable to subscribe", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
