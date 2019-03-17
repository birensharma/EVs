package com.example.evehicle;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Response extends AppCompatActivity {
    private Button a,r;
    private DatabaseReference db;
    private String user=null,uid,req;
    private ValueEventListener listener;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        user= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        db= FirebaseDatabase.getInstance().getReference();
        a=findViewById(R.id.accept);
        r=findViewById(R.id.reject);
        tv=findViewById(R.id.req);
        a.setEnabled(false);
        r.setEnabled(false);
        listener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds :dataSnapshot.getChildren()){
                    if(user!=null && !ds.child("email").getValue(String.class).equals(user) ) {
                        db.removeEventListener(listener);
                        tv.setText("Service required from "+ds.child("notification/text").getValue(String.class)+"W");
                        uid=ds.getKey();
                        a.setEnabled(true);
                        r.setEnabled(true);
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Response.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
        db.addValueEventListener(listener);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Response.this, "Accepted", Toast.LENGTH_SHORT).show();
                db.child(uid+"/notification/response").setValue("accepted");
                startActivity(new Intent(Response.this,LocationMap.class));
                finish();
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Response.this, "Rejected", Toast.LENGTH_SHORT).show();
                db.child(uid+"/notification/response").setValue("rejected");
                finish();
            }
        });
    }
    private void update(){

    }
}
