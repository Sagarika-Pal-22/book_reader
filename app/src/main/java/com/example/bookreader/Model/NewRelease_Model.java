package com.example.bookreader.Model;

public class NewRelease_Model {

    String id,type,genre,image,book_name,writer,price,rating;

    public NewRelease_Model(String id, String type, String genre, String image, String book_name, String writer, String price, String rating) {
        this.id = id;
        this.type = type;
        this.genre = genre;
        this.image = image;
        this.book_name = book_name;
        this.writer = writer;
        this.price = price;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getGenre() {
        return genre;
    }

    public String getImage() {
        return image;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getWriter() {
        return writer;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }
}
