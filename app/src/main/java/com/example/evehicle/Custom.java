package com.example.evehicle;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Custom extends AppCompatActivity {
    private final int BULB_POWER=60;
    private final int FAN_POWER=30;
    private final int AC_POWER=1500;
    private final int OVEN_POWER=500;
    private final int FRIDGE_POWER=150;
    private int sum=0;
    private EditText bulb,fan,ac,oven,fridge;
    private Button submit;
    private Spinner from,to;
    private ArrayAdapter<CharSequence> adapter;
    private DatabaseReference db;
    private ValueEventListener valueEventListener;
    private String umail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        umail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        bulb=findViewById(R.id.bulb);
        fan=findViewById(R.id.fans);
        ac=findViewById(R.id.ac);
        oven=findViewById(R.id.oven);
        fridge=findViewById(R.id.fridge);
        submit=findViewById(R.id.submit);
        from=findViewById(R.id.from);
        to=findViewById(R.id.to);
        db= FirebaseDatabase.getInstance().getReference();
        final EditText[] ed=new EditText[]{bulb,fan,ac,oven,fridge};
        final int[] totalPower=new int[] {BULB_POWER,FAN_POWER,AC_POWER,OVEN_POWER,FRIDGE_POWER};
        adapter= ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        from.setAdapter(adapter);
        to.setAdapter(adapter);
        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                db.removeEventListener(valueEventListener);
                for(DataSnapshot dd:dataSnapshot.getChildren()){
                    if(dd.child("email").getValue(String.class).equals(umail)){
                        db.child(dd.getKey()+"/notification/text").setValue(from.getSelectedItem().toString()+
                                " - "+to.getSelectedItem().toString()+
                                " | "+sum)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Custom.this, "Notification Sent", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Custom.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum=0;
                int val,i=0;
                for(EditText e : ed ){
                    if(e.getText().toString().equals(""))
                        val=0;
                    else
                        val=Integer.parseInt(e.getText().toString());
                    sum+=val*totalPower[i];
                    i++;
                }
                show();
            }
        });
    }
    private void show(){

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Time:From "+from.getSelectedItem().toString()
                +" to "+to.getSelectedItem().toString()+"\nPower required "+sum+"W\nSure to send?");
        alertDialogBuilder.setPositiveButton("Send",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        db.addValueEventListener(valueEventListener);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
