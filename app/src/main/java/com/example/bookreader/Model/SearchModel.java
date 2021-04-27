package com.example.bookreader.Model;

import androidx.annotation.NonNull;

public class SearchModel {
    String id,img,book_name,type;

    public SearchModel(String id, String img, String book_name, String type) {
        this.id = id;
        this.img = img;
        this.book_name = book_name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return  book_name ;
    }
}
