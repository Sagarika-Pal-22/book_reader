package com.example.bookreader.Model;

public class Genre_Model {

    String id,text_genre;

    public Genre_Model(String id, String text_genre) {
        this.id = id;
        this.text_genre = text_genre;
    }

    public String getId() {
        return id;
    }

    public String getText_genre() {
        return text_genre;
    }
}
