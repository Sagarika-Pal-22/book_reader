package com.example.bookreader.Model;

public class Books_Chkout_Model {
    String text_books,price;

    public Books_Chkout_Model(String text_books, String price) {
        this.text_books = text_books;
        this.price = price;
    }

    public String getText_books() {
        return text_books;
    }

    public String getPrice() {
        return price;
    }
}
