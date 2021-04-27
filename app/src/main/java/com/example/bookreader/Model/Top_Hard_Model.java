package com.example.bookreader.Model;

public class Top_Hard_Model {

    String id, type, genre, book_image, book_name, writer, rating, price;

    public Top_Hard_Model(String id, String type, String genre, String book_image, String book_name, String writer, String rating, String price) {
        this.id = id;
        this.type = type;
        this.genre = genre;
        this.book_image = book_image;
        this.book_name = book_name;
        this.writer = writer;
        this.rating = rating;
        this.price = price;
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

    public String getBook_image() {
        return book_image;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getWriter() {
        return writer;
    }

    public String getRating() {
        return rating;
    }

    public String getPrice() {
        return price;
    }
}
