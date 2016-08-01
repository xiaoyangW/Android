package com.fwd.mob;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

public class MainActivity extends AppCompatActivity implements Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //初始化短信验证码SD
        SMSSDK.initSDK(this, "134e82e533574", "7537cdefb6c63f1ef954e1b9577d284a",true);

        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int arg0, int arg1, Object arg2) {
                Message msg = new Message();
                msg.arg1 = arg0;
                msg.arg2 = arg1;
                msg.obj = arg2;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
        SMSSDK.getVerificationCode("86","15717518134");
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println("--------result" + result);
                // 短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                    Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 已经验证
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                }

            } else {
                ((Throwable) data).printStackTrace();
                Toast.makeText(MainActivity.this, "验证码错误",
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "123",
                        Toast.LENGTH_SHORT).show();
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(MainActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }

        }
    };

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case 1:{

            }
        }
        return false;
    }
}
