package com.test.faceq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

/**
 * Created by Avrin on 2016/6/14.
 */

public class SplashActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplash);
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(2000);
                startActivity(new Intent(SplashActivity.this,LogoActivity.class));
                finish();
            }
        }.start();
    }
}
