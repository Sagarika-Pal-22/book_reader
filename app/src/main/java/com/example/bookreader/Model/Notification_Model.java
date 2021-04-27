package com.example.bookreader.Model;

public class Notification_Model {

    String id,image,notification,date,time;

    public Notification_Model(String id, String image, String notification, String date, String time) {
        this.id = id;
        this.image = image;
        this.notification = notification;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getNotification() {
        return notification;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
