package com.example.bookreader.Model;

public class Cart_Model {
    String id, pro_id, quantity, image, product_name, price, total_price;

    public Cart_Model(String id, String pro_id, String quantity, String image, String product_name, String price, String total_price) {
        this.id = id;
        this.pro_id = pro_id;
        this.quantity = quantity;
        this.image = image;
        this.product_name = product_name;
        this.price = price;
        this.total_price = total_price;
    }

    public String getId() {
        return id;
    }

    public String getPro_id() {
        return pro_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getTotal_price() {
        return total_price;
    }
}
