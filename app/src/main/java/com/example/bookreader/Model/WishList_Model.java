package com.example.bookreader.Model;

public class WishList_Model {

    String id,img_wish,text_title,writer,price;

    public WishList_Model(String id, String img_wish, String text_title, String writer, String price) {
        this.id = id;
        this.img_wish = img_wish;
        this.text_title = text_title;
        this.writer = writer;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getImg_wish() {
        return img_wish;
    }

    public String getText_title() {
        return text_title;
    }

    public String getWriter() {
        return writer;
    }

    public String getPrice() {
        return price;
    }
}
