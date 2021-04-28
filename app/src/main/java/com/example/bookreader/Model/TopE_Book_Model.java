package com.example.bookreader.Model;

public class TopE_Book_Model {

    String id,type,type_name,image,book_name,writer,price,rating,genre;

    public TopE_Book_Model(String id, String type, String type_name, String image, String book_name, String writer, String price, String rating, String genre) {
        this.id = id;
        this.type = type;
        this.type_name = type_name;
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

    public String getType_name() {
        return type_name;
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
