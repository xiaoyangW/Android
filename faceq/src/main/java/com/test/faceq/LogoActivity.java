package com.test.faceq;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by acer1 on 2016/6/14.
 */

public class LogoActivity extends Activity{
    private Button bt_man,bt_woman;
    private SoundPool sp;//声明一个SoundPool

    Map<String,Object> map=new HashMap();

    private int music;//定义一个整型用load（）；来设置suondID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        initView();
        bt_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(LogoActivity.this,
                        R.raw.boy);
                mediaPlayer.start();

                Intent intent = new Intent(LogoActivity.this,MainActivity.class);
                intent.putExtra("isBoy",true);
                startActivity(intent);
            }
        });
        bt_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(LogoActivity.this,
                        R.raw.girl);
                mediaPlayer.start();
                Intent intent = new Intent(LogoActivity.this,MainActivity.class);
                intent.putExtra("isBoy",false);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        bt_man = (Button) findViewById(R.id.bt_man);
        bt_woman = (Button) findViewById(R.id.bt_woman);

    }

}
