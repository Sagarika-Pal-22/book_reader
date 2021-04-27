package com.example.bookreader.Model;

public class EBook_Model {

    int image;
    String book_name,writer,rating,genre;

    public EBook_Model(int image, String book_name, String writer, String rating, String genre) {
        this.image = image;
        this.book_name = book_name;
        this.writer = writer;
        this.rating = rating;
        this.genre = genre;
    }

    public int getImage() {
        return image;
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

    public String getGenre() {
        return genre;
    }
}
