package com.test.okhttp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.gson.Gson;
import java.io.IOException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private Button btn_get;
    private Button btn_post;
    OkHttpClient client = new OkHttpClient();
    String json= "{\"msg\":\"success\",\"result\":[{\"airCondition\":\"优\",\"city\":\"长沙\",\"coldIndex\":\"低发期\",\"date\":\"2016-06-30\",\"distrct\":\"长沙\",\"dressingIndex\":\"薄短袖类\",\"exerciseIndex\":\"不适宜\",\"future\":[{\"date\":\"2016-06-30\",\"dayTime\":\"多云\",\"night\":\"多云\",\"temperature\":\"33°C / 26°C\",\"week\":\"今天\",\"wind\":\"南风 小于3级\"},{\"date\":\"2016-07-01\",\"dayTime\":\"多云\",\"night\":\"多云\",\"temperature\":\"32°C / 27°C\",\"week\":\"星期五\",\"wind\":\"南风 3～4级\"},{\"date\":\"2016-07-02\",\"dayTime\":\"阵雨\",\"night\":\"中雨\",\"temperature\":\"30°C / 26°C\",\"week\":\"星期六\",\"wind\":\"南风 小于3级\"},{\"date\":\"2016-07-03\",\"dayTime\":\"中雨\",\"night\":\"多云\",\"temperature\":\"29°C / 25°C\",\"week\":\"星期日\",\"wind\":\"南风 小于3级\"},{\"date\":\"2016-07-04\",\"dayTime\":\"多云\",\"night\":\"多云\",\"temperature\":\"32°C / 26°C\",\"week\":\"星期一\",\"wind\":\"南风 小于3级\"},{\"date\":\"2016-07-05\",\"dayTime\":\"多云\",\"night\":\"晴\",\"temperature\":\"35°C / 27°C\",\"week\":\"星期二\",\"wind\":\"南风 小于3级\"},{\"date\":\"2016-07-06\",\"dayTime\":\"晴\",\"night\":\"晴\",\"temperature\":\"36°C / 27°C\",\"week\":\"星期三\",\"wind\":\"南风 小于3级\"},{\"date\":\"2016-07-07\",\"dayTime\":\"局部多云\",\"night\":\"局部多云\",\"temperature\":\"32°C / 26°C\",\"week\":\"星期四\",\"wind\":\"西南偏南风 3级\"},{\"date\":\"2016-07-08\",\"dayTime\":\"局部多云\",\"night\":\"局部多云\",\"temperature\":\"33°C / 26°C\",\"week\":\"星期五\",\"wind\":\"西南偏南风 3级\"},{\"date\":\"2016-07-09\",\"dayTime\":\"局部多云\",\"night\":\"零散雷雨\",\"temperature\":\"33°C / 26°C\",\"week\":\"星期六\",\"wind\":\"西南偏南风 4级\"}],\"humidity\":\"湿度：77%\",\"pollutionIndex\":\"23\",\"province\":\"湖南\",\"sunrise\":\"05:35\",\"sunset\":\"19:29\",\"temperature\":\"31℃\",\"time\":\"14:12\",\"updateTime\":\"20160630142951\",\"washIndex\":\"不适宜\",\"weather\":\"多云\",\"week\":\"周四\",\"wind\":\"南风3级\"}],\"retCode\":\"200\"}\n";
    //JSON数据不可再子线程中转换输出，要在主线程中转换输出。
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String jsonString = (String) msg.obj;
            Gson gson = new Gson();
            Root fromJson = gson.fromJson(jsonString,Root.class);
            Log.i("wxy",fromJson.getRetCode());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_get = (Button) findViewById(R.id.btn_get);
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRequest();
            }
        });
        btn_post = (Button) findViewById(R.id.btn_post);
    }
    private void testJson(){
        Gson gson = new Gson();
        Root fromJson = gson.fromJson(json,Root.class);
        Log.i("wxy",fromJson.getMsg());
    }
    //同步get方式提交
    private void getRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://apicloud.mob.com/v1/weather/query?key=146d30f8f3b93&city=长沙&province=湖南";
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Message mag = handler.obtainMessage();
                        mag.obj = string;
                        handler.sendMessage(mag);
                    } else {
                        Log.i("wxy", "okHttp is request error");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * 异步 Get方法
     */
    private void okHttp_asynchronousGet(){
        try {
            Log.i("wxy","main thread id is "+Thread.currentThread().getId());
            String url = "http://apicloud.mob.com/v1/weather/query?key=146d30f8f3b93&city=长沙&province=湖南";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    // 注：该回调是子线程，非主线程
                    Log.i("wxy","callback thread id is "+Thread.currentThread().getId());
                    Log.i("wxy",response.body().string());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Post 提交键值对
     * 如果是异步同get方式异步提交一致
     */
    private void okHttp_postFromParameters() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 请求完整url：http://api.k780.com:88/?app=weather.future&weaid=1&&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json
                    String url = "http://api.k780.com:88/";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder().add("app", "weather.Future")
                            .add("weaid", "1").add("appkey", "10003").add("sign",
                                    "b59bc3ef6191eb9f747dd4e83c99f2a4").add("format", "json")
                            .build();
                    Request request = new Request.Builder().url(url).post(formBody).build();
                    okhttp3.Response response = okHttpClient.newCall(request).execute();
                    Log.i("wxy", response.body().string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
