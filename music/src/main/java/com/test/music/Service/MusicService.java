package com.test.music.Service;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore.Audio.Media;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.test.music.entity.MusicInfo;
import com.test.music.minterface.IServiceListener;

import java.io.IOException;

/**
 * Created by acer1 on 2016/6/18.
 */

public class MusicService extends Service{
    boolean isPlaying = false;
    MusicInfo musicInfo=null;

   public static MediaPlayer mp = null;
    //播放音乐
    public void playMusic(int id){
        //1.初始化mp
        if (mp == null) {
            mp = new MediaPlayer();
        }
        //2.重置
        mp.reset();
        //3.设置播放的歌曲来源
        Uri uri = ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI,id);
        try {
            mp.setDataSource(this,uri);
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //写暂停
    public void pause(){
        if (mp !=null) {
            mp.pause();
        }
        //初始化播放音乐
    }
    //写继续
    public void moveon(){
        if (mp !=null) {
            mp.start();
        }
    }
    /**
     * 获取歌曲长度
     * @return
     */
    public int getMusicDuration()
    {
        int rtn = 0;
        if (mp != null)
        {
            rtn = mp.getDuration();
        }
        return rtn;
    }

    /**
     * 获取当前播放进度
     * @return
     */
    public int getMusicCurrentPosition()
    {
        int rtn = 0;
        if (mp != null) {

            try {
                rtn = mp.getCurrentPosition();
            }catch (IllegalArgumentException e) {
                e.printStackTrace();
            }catch (SecurityException e) {
                e.printStackTrace();
            }catch (IllegalStateException e) {
                e.printStackTrace();
            }

        }
        return rtn;
    }
    public void seekTo(int position)
    {
        if (mp != null) {
            mp.seekTo(position);
        }
    }
    MusicBinder musicBinder = new MusicBinder();

    public class MusicBinder extends Binder {
        public MusicService getMusicService(){
            return MusicService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       musicInfo = (MusicInfo) intent.getSerializableExtra("music");
        isPlaying = true;
        if (musicInfo != null) {
            playMusic(musicInfo.get_id());
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //Toast.makeText(getApplicationContext(),"播放完毕",Toast.LENGTH_LONG).show();
                    //播放完毕后循环播放
                    playMusic(musicInfo.get_id());
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"歌曲为空",Toast.LENGTH_LONG).show();
        }
        return musicBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mp.stop();
        mp = null;//防止IllegalStateException
        return super.onUnbind(intent);
    }

    //来电处理及第二点击时的IllegalStateException将mp=null
    @Override
    public void onDestroy() {
        if (mp != null){
            if (mp.isPlaying()){
                mp.stop();
            }
            mp.release();
            mp = null;
        }
        super.onDestroy();
    }

}
