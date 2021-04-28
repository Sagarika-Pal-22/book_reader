package com.example.bookreader.Model;

public class SliderItem {
    String  id,type,type_name,genre_id,image;
    String book_name,writer,rating,description;

    public SliderItem(String id, String type, String type_name, String genre_id, String image, String book_name, String writer, String rating, String description) {
        this.id = id;
        this.type = type;
        this.type_name = type_name;
        this.genre_id = genre_id;
        this.image = image;
        this.book_name = book_name;
        this.writer = writer;
        this.rating = rating;
        this.description = description;
    }

    public String getRating() {
        return rating;
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

    public String getGenre_id() {
        return genre_id;
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

    public String getDescription() {
        return description;
    }
}
