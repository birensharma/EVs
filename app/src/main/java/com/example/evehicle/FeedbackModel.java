package com.example.evehicle;

public class FeedbackModel {
    float rating;
    String email;

    public FeedbackModel(float rating, String email) {
        this.rating = rating;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
