package com.test.music.entity;

import java.io.Serializable;

/**
 * Created by acer1 on 2016/6/18.
 */

public class MusicInfo implements Serializable {
    int _id ;
    String title;
    long duration ;
    String display_name ;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public MusicInfo(int _id, String title, long duration, String display_name) {
        this._id = _id;
        this.title = title;
        this.duration = duration;
        this.display_name = display_name;
    }

    @Override
    public String toString() {
        return "MusicInfo{" +
                "_id=" + _id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", display_name='" + display_name + '\'' +
                '}';
    }
}
