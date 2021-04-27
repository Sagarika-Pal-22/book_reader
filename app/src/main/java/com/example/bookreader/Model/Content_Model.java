package com.example.bookreader.Model;

public class Content_Model {

    String chap_no,chap_name;

    public Content_Model(String chap_no, String chap_name) {
        this.chap_no = chap_no;
        this.chap_name = chap_name;
    }

    public String getChap_no() {
        return chap_no;
    }

    public String getChap_name() {
        return chap_name;
    }
}
