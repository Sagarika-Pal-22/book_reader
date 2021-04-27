package com.example.bookreader.Model;

public class Order_Model {

    String id,img_1,img_2,book_name,invoice,price,date;

    public Order_Model(String id, String img_1, String img_2, String book_name, String invoice, String price, String date) {
        this.id = id;
        this.img_1 = img_1;
        this.img_2 = img_2;
        this.book_name = book_name;
        this.invoice = invoice;
        this.price = price;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getImg_1() {
        return img_1;
    }

    public String getImg_2() {
        return img_2;
    }

    public String getBook_name() {
        return book_name;
    }

    public String getInvoice() {
        return invoice;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }
}
