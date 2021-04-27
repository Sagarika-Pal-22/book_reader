package com.example.bookreader.Model;

public class TopE_Book_Model {

    String id,type,image,book_name,writer,price,rating,genre;

    public TopE_Book_Model(String id, String type, String image, String book_name, String writer, String price, String rating, String genre) {
        this.id = id;
        this.type = type;
        this.image = image;
        this.book_name = book_name;
        this.writer = writer;
        this.price = price;
        this.rating = rating;
        this.genre = genre;
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

    public String getWriter() {
        return writer;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }
}
