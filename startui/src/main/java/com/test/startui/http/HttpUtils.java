package com.test.startui.http;

import com.test.startui.Utils.StreamUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by acer1 on 2016/6/25.
 */

public class HttpUtils {

    public static final String HOST = "http://192.168.10.122:8080";//访问IP
    public static final String SERVERHOST = HOST+"/NewsServerTest";//访问工程名
    public static final String URL_GETNEWSLIST = SERVERHOST+"GetNewsDataToPhone";//具体文件名
    /**
     * 获取网络数据
     * @param path（IP路径）
     * @return
     */
    public static  String get(String path){
        try {
            URL url = new URL(path);
            try {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                String  s = StreamUtil.streamToString(is);
                return s;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  String getNewsList(int page,int rows){
       return  get(URL_GETNEWSLIST+"?page="+page+"&rows"+rows);
    }
}
