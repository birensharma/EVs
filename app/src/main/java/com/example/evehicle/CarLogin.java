package com.example.evehicle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class CarLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email,password;
    private Button log,signup;
    private DatabaseReference db,f;
    private ValueEventListener valueEventListener;
    @Override
    protected void onStart() {
        super.onStart();
        init();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
            navigate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db= FirebaseDatabase.getInstance().getReference();
        f= FirebaseDatabase.getInstance().getReference();
        email=findViewById(R.id.email);
        password=findViewById(R.id.pass);
        log=findViewById(R.id.log);
        signup=findViewById(R.id.signup);
        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isPresent=true;
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot dd: dataSnapshot.getChildren())
                    {
                        if(dd.child("email").getValue(String.class).equals(email.getText().toString()) )
                        {
                            isPresent=true;
                            break;
                        }
                        else isPresent=false;

                    }
                    if(!isPresent)
                    {
                        db.removeEventListener(valueEventListener);
                        DatabaseReference d =f.push();
                        d.child("email").setValue(email.getText().toString());
                        d.child("notification").child("text").setValue("Hello");
                    }
                    else
                        db.removeEventListener(valueEventListener);
                }
                else
                {
                    db.removeEventListener(valueEventListener);
                    DatabaseReference d=f.push();
                    d.child("email").setValue(email.getText().toString());
                    d.child("notification").child("text").setValue("Hello");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mAuth = FirebaseAuth.getInstance();
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                String pass=password.getText().toString();
                if(!TextUtils.isEmpty(mail)&&!TextUtils.isEmpty(pass))
                    login(mail,pass);
                else
                    Toast.makeText(CarLogin.this, "Fields are empty", Toast.LENGTH_SHORT).show();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CarLogin.this,SignUp.class);
                i.putExtra("type","car");
                startActivity(i);
            }
        });
    }
    private void login(final String email,String pass){
        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            db.addValueEventListener(valueEventListener);
                            navigate();
                        } else {
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void navigate(){
        startActivity(new Intent(this,CarDashboard.class));
    }
    private void init(){

        FirebaseMessaging.getInstance().subscribeToTopic("Message")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful())
                            Toast.makeText(CarLogin.this, "Unable to subscribe", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
