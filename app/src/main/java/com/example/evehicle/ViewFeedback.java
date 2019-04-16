package com.example.evehicle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewFeedback extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RatingAdapter adapter;
    private DatabaseReference db;
    private ValueEventListener valueEventListener;
    private ArrayList<FeedbackModel> list=new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.removeEventListener(valueEventListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    list.add(new FeedbackModel(ds.child("rating").getValue(Float.class),
                            ds.child("email").getValue(String.class)));
                }
                adapter = new RatingAdapter(list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        db= FirebaseDatabase.getInstance().getReference("Feedback");
        db.addValueEventListener(valueEventListener);

    }
}
