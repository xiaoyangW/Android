package com.example.arvin.wxy.tools;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by acer1 on 2016/5/18.
 */
public class MyMap implements Serializable {
    private Map<String,Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "MyMap{" +
                "map=" + map +
                '}';
    }
}
