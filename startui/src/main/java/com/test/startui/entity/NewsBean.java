package com.test.startui.entity;

import java.util.Date;

/**
 * Created by acer1 on 2016/6/25.
 */

public class NewsBean {
    int id;
    String title;
    String detail;
    String imgUrl;
    Date created;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public NewsBean(int id, String title, String detail, String imgUrl, Date created) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.imgUrl = imgUrl;
        this.created = created;
    }
    public NewsBean(String title, String detail, String imgUrl, Date created) {
        this.title = title;
        this.detail = detail;
        this.imgUrl = imgUrl;
        this.created = created;
    }
}
