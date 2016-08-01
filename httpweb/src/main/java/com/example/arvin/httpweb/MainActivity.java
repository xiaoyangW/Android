package com.example.arvin.httpweb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView tv_html;
    private Button bt_xml;
    private ImageView iv_image;
    private WebView wv_html;
    String pathHtml = "http://192.168.1.164:8080/gogo/login.html";
    String pathImage = "http://192.168.1.164:8080/gogo/netease.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    public void getXml(View view){
        startActivity(new Intent(this,XmlActivity.class));
    }
    private void initView() {
        tv_html= (TextView) findViewById(R.id.tv_html);
        iv_image= (ImageView) findViewById(R.id.iv_image);
        wv_html= (WebView) findViewById(R.id.wv_html);
        //bt_xml= (Button) findViewById(R.id.bt_xml);
    }
    //联网必开线程

    public String getHtml(String path) throws Exception{
        //1.封装path
        URL url = new URL(path);
        //2.打开连接
        HttpURLConnection connection= (HttpURLConnection) url.openConnection();
        //3.设置连接超时
        connection.setConnectTimeout(5000);
        //4.设置请求方式 要大写
        connection.setRequestMethod("GET");
        //5.获得服务器响应码
        int responseCode = connection.getResponseCode();
        if (responseCode==HttpURLConnection.HTTP_OK){
            //连接成功
            //7.获得流
            InputStream is=connection.getInputStream();
            //8.解析流
            String html= parseHtml(is);

            return html;
        }
        return null;
    }

    /**
     * 获取html输出
     * @param inputStream
     * @return
     * @throws Exception
     */
    private String parseHtml(InputStream inputStream) throws  Exception{
        //1.构建一个输出流对象
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len=0;
        byte buffer[]= new byte[1024];
        while ((len=inputStream.read(buffer))!=-1){
            baos.write(buffer,0,len);
        }
        String s=baos.toString();
        baos.close();
        return s;
    }

    /**
     *
     * @param view
     */
    public void getHtmlCode(View view) {

        new Thread(){
            @Override
            public void run() {
                try {
                    String html=getHtml(pathHtml);
                   System.out.print("111");
                    if (html!=null){
                        //更新ui必须在主线程
                        //用Handle处理：能够在主线程和子线程中通信
                        //html封装成消息message
                        Message msg= new Message();
                        msg.obj=html;
                        msg.what=1;
                        mHandler.sendMessage(msg);
                    }else {
                        Message msg= new Message();
                        msg.what=2;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    Message msg= new Message();
                    msg.what=3;
                    mHandler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //主线程
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //更新ui
            switch (msg.what){
                case 1:
                    String html= (String) msg.obj;
                    tv_html.setText(html);
                    wv_html.loadDataWithBaseURL(null, html, "login/html", "utf-8", null);
                    wv_html.getSettings().setJavaScriptEnabled(true);
                    wv_html.setWebChromeClient(new WebChromeClient());
                    break;
                case 2:
                    Toast.makeText(MainActivity.this,"没有数据", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(MainActivity.this,"网络异常  ", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Bitmap image = (Bitmap) msg.obj;
                    iv_image.setImageBitmap(image);
                    break;
            }

        }
    };
    //获取网页图片
    public Bitmap getImage(String path)throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();
        if(responseCode==200){
            InputStream is = conn.getInputStream();
            //将流转为BitmapFactory
            return BitmapFactory.decodeStream(is);
        }
        return null;
    }

    public void getImage(View view){
        new Thread(){
            @Override
            public void run() {

                try {
                    Bitmap image = getImage(pathImage);
                    if(image!=null){
                        Message message = new Message();
                        message.obj = image;
                        message.what = 4;
                        mHandler.sendMessage(message);

                    }else{
                        Message msg = new Message();
                        msg.what = 2;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 3;
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }
}
