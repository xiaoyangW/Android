package com.test.startui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.test.startui.R;
import com.test.startui.Utils.ActivityUtils;

/**
 * Created by acer1 on 2016/6/17.
 */

public class SplashActivity extends AppCompatActivity{
    Handler handler  = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isFristRun()){
            startActivity(new Intent(SplashActivity.this,GuideActivity.class));
            finish();
        }else {
                Toast.makeText(SplashActivity.this,"不是第一次进入应用",Toast.LENGTH_LONG).show();
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //ActivityUtils.init(this);
        handler.postDelayed(runnable,3000);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    // 判断是否是第一次启动程序 利用 SharedPreferences 将数据保存在本地
    private boolean isFristRun() {
        //实例化SharedPreferences对象（第一步）
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "share", MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!isFirstRun) {
            return false;
        } else {
            //保存数据 （第三步）
            editor.putBoolean("isFirstRun", false);
            //提交当前数据 （第四步）
            editor.commit();
            return true;
        }
    }
}
