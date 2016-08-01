package com.test.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.test.music.Service.MusicService;
import com.test.music.minterface.IServiceListener;

import java.text.SimpleDateFormat;


public class PlayerActivity extends AppCompatActivity implements IServiceListener {
    boolean playing;
    ImageView iv_needle;
    ImageView iv_disk;
    Animation play;
    ImageView iv_indicator;
    RotateAnimation animation;
    TextView tv_current_title;
    TextView tv_current_artist;
    TextView tv_duration;
    ProgressBar progressBar1;
    ImageView iv_musicplay;
    public TextView tv_current_time;
    Mp3Application application;
    MusicService service;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        findView();
        application = (Mp3Application)getApplication();
        service = application.service;
        playing = true;

    }

    private void findView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        /*View view = LayoutInflater.from(this).inflate(R.layout.player_actionbar_layout,null);
        actionBar.setCustomView(view);*/
        actionBar.setDisplayShowTitleEnabled(false);
        iv_needle = (ImageView) findViewById(R.id.iv_needle);
        iv_disk = (ImageView) findViewById(R.id.iv_disk);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
   /*     tv_current_artist = (TextView) findViewById(R.id.tv_current_artist);
        tv_current_title = (TextView) view.findViewById(R.id.tv_current_title);*/
        tv_duration = (TextView) findViewById(R.id.tv_duration);
        iv_musicplay = (ImageView) findViewById(R.id.iv_musicplay);
        tv_current_time = (TextView) findViewById(R.id.tv_current_time);
        iv_indicator = (ImageView) findViewById(R.id.iv_indicator);
    }


    @Override
    protected void onResume() {
        super.onResume();
        new Thread(){
            @Override
            public void run() {
                play = new RotateAnimation(0,359, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                play.setDuration(10000);
                play.setFillAfter(false);
                play.setRepeatCount(Integer.MAX_VALUE);
                play.setInterpolator(new LinearInterpolator());
                iv_disk.setAnimation(play);
            }
        }.start();
    }


    public void back(View view) {
        finish();
    }

    public void play(View view) {
        playing = !playing;
        Intent intent = new Intent(CONST.ACTION_PLAY);
        this.sendBroadcast(intent);

        if(playing){
            iv_disk.startAnimation(play);
            animation.cancel();
            iv_musicplay.setImageResource(R.drawable.play_btn_pause);


        }else {
            play.cancel();
            iv_musicplay.setImageResource(R.drawable.play_btn_play);
            animation = new RotateAnimation(0,-30);
            animation.setDuration(200);
            animation.setFillAfter(true);
            iv_needle.startAnimation(animation);
        }
    }

    public void next(View view) {
        service.moveon();
    }

    public void prev(View view) {
        service.pause();
    }

    @Override
    public void setProgressbar(int progress, int max) {
        progressBar1.setProgress(progress);
        progressBar1.setMax(max);
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        String duration = format.format(max);
        String current = format.format(progress);
        tv_duration.setText(duration);
        tv_current_time.setText(current);
        TranslateAnimation tr = new TranslateAnimation((progressBar1.getWidth()-10)*progress/max,(progressBar1.getWidth()-10)*progress/max+1,0,0);
        tr.setDuration(100);
        tr.setFillAfter(true);
        iv_indicator.startAnimation(tr);
    }


}
