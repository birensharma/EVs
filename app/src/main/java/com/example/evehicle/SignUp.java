package com.example.evehicle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {
    private Button reg;
    private EditText name,email,pass;
    private DatabaseReference db;
    private ValueEventListener valueEventListener;
    String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i=getIntent();
        type=i.getStringExtra("type");

        setContentView(R.layout.activity_sign_up);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        db= FirebaseDatabase.getInstance().getReference();
        reg=findViewById(R.id.reg);

        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check())
                    signUp(email.getText().toString(),pass.getText().toString());

            }
        });

    }
    private boolean check(){
        EditText[] ed=new EditText[]{name,email,pass};
        boolean pass=false;
        for(EditText e:ed){
            if(e.getText().toString().equals("")){
                e.setError("This is required field");
                pass= false;
                break;
            }
            else
                pass= true;
        }
        return pass;
    }
    private void signUp(String mail,String passwd){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,passwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(SignUp.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            createUser();
                            finish();
                        }
                        else
                            Toast.makeText(SignUp.this, "Unable to Register", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void createUser(){
    DatabaseReference d= db.push();
    d.child("name").setValue(name.getText().toString());
    d.child("email").setValue(email.getText().toString());
    d.child("type").setValue(type);
    d.child("notification/response").setValue("rejected");
    d.child("notification/text").setValue("00:00 - 00:00 | 0");
    }
}
