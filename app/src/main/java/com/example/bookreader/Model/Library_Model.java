package com.example.bookreader.Model;

public class Library_Model {

    String id, image, text_title;

    public Library_Model(String id, String image, String text_title) {
        this.id = id;
        this.image = image;
        this.text_title = text_title;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getText_title() {
        return text_title;
    }
}
