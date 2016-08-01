package com.example.arvin.myhttpweb.biz;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Emily on 16/5/20.
 */
public class NetService {
    public String post(String path, Map<String,String> map) throws Exception{
        //1参数单独以字节数组形式传
        String s = getEntity(map);
        byte[] entity = s.getBytes();
        //2.
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        //3.两个属性是必须的
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", entity.length+"");
        //4.设置对外输出为true
        conn.setDoOutput(true);
        //5.得到输出流
        OutputStream os = conn.getOutputStream();
        //6.写到服务器
        os.write(entity);
        //7.得到服务器响应
        int responseCode = conn.getResponseCode();
        if(HttpURLConnection.HTTP_OK==responseCode){
            InputStream is = conn.getInputStream();
            String s1 = parseInputStream(is);
            return s1;
        }
        return null;
    }
    //拼map
    public String getEntity(Map<String,String> map){
        StringBuffer sb = null;
        if(map!=null&&map.size()>0) {
            sb = new StringBuffer();
            for (Map.Entry<String, String> temp : map.entrySet()) {
                String key = temp.getKey();
                String value = temp.getValue();
                sb.append(key).append("=").append(value);
                sb.append("&");
            }
            //去掉最后一个
            sb.deleteCharAt(sb.length() - 1);
        }
        return  sb==null?"":sb.toString();
    }
    //解析map，拼成网址的路径 ?xxx=xx&xxx=xxx
    //http://10.0.2.2:8080/gogo/login  path
    //?username=admin&password=admin
    public String getGetPath(String path, Map<String,String> map){
        StringBuffer sb = new StringBuffer(path);
        String entity = getEntity(map);
        if(!"".equals(entity)){
            sb.append("?");
            sb.append(getEntity(map));//拼好了
        }
        Log.d("", sb.toString());
        return sb.toString();
    }
    //连网
    public InputStream toServer(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);//anr
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();
        if(responseCode==HttpURLConnection.HTTP_OK){
            //连网成功
            InputStream is = conn.getInputStream();
            return is;
        }
        return null;
    }
    //解析InputStream
    public String parseInputStream(InputStream is) throws Exception{
        //抱死
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len = 0;
        byte buffer[] = new byte[1024];//
        while((len=is.read(buffer))!= -1){
            baos.write(buffer,0,len);
        }
        String s = baos.toString();
        baos.close();
        return s;
    }
}
