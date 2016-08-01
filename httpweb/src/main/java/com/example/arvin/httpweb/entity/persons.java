package com.example.arvin.httpweb.entity;

import java.io.Serializable;

/**
 * Created by acer1 on 2016/5/19.
 */
public class persons implements Serializable {
    public int _id;
    public String username;
    public String password;
    public String pic;

    public persons() {
    }

    public persons(String username, String password, String pic) {
        this.username = username;
        this.password = password;
        this.pic = pic;
    }

    public persons(int _id, String pic, String password, String username) {
        this._id = _id;
        this.pic = pic;
        this.password = password;
        this.username = username;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "persons{" +
                "_id=" + _id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
