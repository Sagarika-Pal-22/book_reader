package com.example.bookreader.Model;

public class Review_Model {
    String id,text_person,text_review,rating,text_date;

    public Review_Model(String id, String text_person, String text_review, String rating, String text_date) {
        this.id = id;
        this.text_person = text_person;
        this.text_review = text_review;
        this.rating = rating;
        this.text_date = text_date;
    }

    public String getId() {
        return id;
    }

    public String getRating() {
        return rating;
    }

    public String getText_person() {
        return text_person;
    }

    public String getText_review() {
        return text_review;
    }

    public String getText_date() {
        return text_date;
    }
}
