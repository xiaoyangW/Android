package com.test.okhttp;

import java.io.Serializable;

/**
 * Created by acer1 on 2016/6/30.
 */

public class Future implements Serializable{
    private String date;

    private String dayTime;

    private String night;

    private String temperature;

    private String week;

    private String wind;

    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }
    public void setDayTime(String dayTime){
        this.dayTime = dayTime;
    }
    public String getDayTime(){
        return this.dayTime;
    }
    public void setNight(String night){
        this.night = night;
    }
    public String getNight(){
        return this.night;
    }
    public void setTemperature(String temperature){
        this.temperature = temperature;
    }
    public String getTemperature(){
        return this.temperature;
    }
    public void setWeek(String week){
        this.week = week;
    }
    public String getWeek(){
        return this.week;
    }
    public void setWind(String wind){
        this.wind = wind;
    }
    public String getWind(){
        return this.wind;
    }
}