package com.example.evehicle;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder> {

    private ArrayList<FeedbackModel> list;

    public RatingAdapter(ArrayList<FeedbackModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        holder.textView.setText(list.get(i).getEmail());
        holder.rate.setEnabled(false);
        holder.rate.setRating(list.get(i).getRating());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RatingBar rate;
        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.mail);
            rate=v.findViewById(R.id.rate);
        }
    }

}
