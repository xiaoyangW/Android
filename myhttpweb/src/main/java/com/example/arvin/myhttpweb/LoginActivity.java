package com.example.arvin.myhttpweb;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arvin.myhttpweb.biz.NetService;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by acer1 on 2016/5/20.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText et_name;
    private EditText et_password;
    private Button btn_login;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String username = et_name.getText().toString();
                String password = et_password.getText().toString();

                //用户名，密码是否填写
                //Toast.makeText(getContext(),username+","+password,Toast.LENGTH_SHORT).show();
                Map<String,String> map = new HashMap<String, String>();
                map.put("username",username);
                map.put("password",password);

                MyAsyncTask myAsyncTask = new MyAsyncTask(map);
                myAsyncTask.execute();
            }
        });
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
    }
    private class MyAsyncTask extends AsyncTask<Void,Void,String> {
        Map<String, String> map;
        NetService netService;
        String path = "http://192.168.1.184:8080/gogo/login";

        public MyAsyncTask(Map<String, String> map) {
            this.map = map;
            netService = new NetService();
        }

        //后台
        @Override
        protected String doInBackground(Void... params) {
            try {
                String s = netService.post(path, map);
                return s;
            } catch (Exception e) {
                e.printStackTrace();
            }
//            String path2 = netService.getGetPath(path,map);
//            try {
//                InputStream is = netService.toServer(path2);
//                String s = netService.parseInputStream(is);
//                return s;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return null;
        }
        //前台

        @Override
        protected void onPostExecute(String s) {
            if ("ok".equals(s.trim())) {
                //成功
                Toast.makeText(LoginActivity.this, "ok", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "no", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
