package com.example.bookreader.Model;

public class CategoryHome_Model {


    String id,type,image;
    String book_name,rating;

    public CategoryHome_Model(String id, String type, String image, String book_name, String rating) {
        this.id = id;
        this.type = type;
        this.image = image;
        this.book_name = book_name;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getRating() {
        return rating;
    }
}
