package com.test.sms.Entity;

/**
 * Created by acer1 on 2016/6/15.
 */

public class MySms {
    private int _id;
    private String address;
    private String body;
    private String date;
    private int type;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MySms(int _id, String address, String body, String date, int type) {
        this._id = _id;
        this.address = address;
        this.body = body;
        this.date = date;
        this.type = type;
    }

    public MySms(String address, String body, String date, int type) {
        this.address = address;
        this.body = body;
        this.date = date;
        this.type = type;
    }

    public MySms() {
    }
}
