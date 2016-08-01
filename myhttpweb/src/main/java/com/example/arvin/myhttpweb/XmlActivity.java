package com.example.arvin.myhttpweb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.example.arvin.myhttpweb.adapter.PersonAdapter;
import com.example.arvin.myhttpweb.entity.persons;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by acer1 on 2016/5/19.
 */
public class XmlActivity extends AppCompatActivity{
    private Button btn_vollery;
    private Button btn_addxml;
    private ListView lv_xml;
    private PersonAdapter adapter;
    private Map<String,Object> map= new ArrayMap<>();
    private List<Map<String,Object>> listsmap= new ArrayList<>();
    private String TAG="XmlActivity";
    String pathHtml = "http://192.168.1.184:8080/gogo/persons.xml";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        initView();
        btn_vollery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(XmlActivity.this,MyVollyActivity.class));
            }
        });
    }
    public void btGetxml(View view){
        /*new Thread(){
            @Override
            public void run() {
                try {
                    List<persons> lists = parseXml(getXml(pathHtml));
                    for (persons p : lists){
                        Log.d(TAG,p+"");
                        map.put("name",p.getUsername());
                        map.put("word",p.getUsername());
                        map.put("pic",p.getPic().toString());
                        listsmap.add(map);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();*/
        MyAsyncTask myAsyncTask= new MyAsyncTask();
        myAsyncTask.execute();
    }
    private void initView() {
        btn_addxml= (Button) findViewById(R.id.btn_addxml);
        lv_xml= (ListView) findViewById(R.id.lv_xml);
        btn_vollery= (Button) findViewById(R.id.btn_vollery);
    }
    public InputStream getXml(String path)throws Exception{
        URL url= new URL(path);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(5000);
        urlConnection.setRequestMethod("GET");
        int responseCode = urlConnection.getResponseCode();
        if (responseCode==HttpURLConnection.HTTP_OK){
           return urlConnection.getInputStream();
        }
        return null;
    }
    //解析
    public List<persons> parseXml(InputStream inputStream)throws Exception{
        //1.解析服务器
        XmlPullParser parser= Xml.newPullParser();
        //2.用什么编码解析流
        parser.setInput(inputStream,"utf-8");
        //3.得到类型
        int type = parser.getEventType();
        //循环
        List<persons> lists=null;
        persons persons=null;
        while (XmlPullParser.END_DOCUMENT != type){
            switch (type){
                case XmlPullParser.START_TAG:
                    //如果遇到persons创集合
                    if ("persons".equals(parser.getName())){
                        lists = new ArrayList<>();
                    }else if("person".equals(parser.getName())) {
                        //如果遇到person，创实体
                        persons = new persons();
                    }else if("_id".equals(parser.getName())) {
                        //如果遇到_id，为实体_id赋值  nextText()
                        int _id = Integer.parseInt(parser.nextText());
                        persons._id = _id;
                    }else if("username".equals(parser.getName())){
                        String username = parser.nextText();
                        persons.username = username;
                    }else if("password".equals(parser.getName())){
                        String password = parser.nextText();
                        persons.password = password;
                    }else if("pic".equals(parser.getName())){
                        String pic = parser.nextText();
                        persons.pic = pic;
                    }

                    break;
                //如果是结束符
                case XmlPullParser.END_TAG:
                    if("person".equals(parser.getName())){
                        //添加到集合
                        lists.add(persons);
                        persons=null;
                    }
                    break;
            }
            type=parser.next();
        }
        return lists;
    }


    public class  MyAsyncTask extends AsyncTask<Void,Void,List<persons>>{
        //相当与子线程
        @Override
        protected List<persons> doInBackground(Void... params) {
            List<persons> list=null;
            try {
                list=parseXml(getXml(pathHtml));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
        //主线程
        @Override
        protected void onPostExecute(List<persons> personses) {
            adapter= new PersonAdapter(personses,XmlActivity.this,lv_xml);
            lv_xml.setAdapter(adapter);
        }
    }
    public void toLogin(View view){
        startActivity(new Intent(XmlActivity.this,LoginActivity.class));
    }
}
