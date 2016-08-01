package com.test.music;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;
import com.test.music.Adapter.MusicListViewAdapter;
import com.test.music.Service.MusicService;
import com.test.music.Service.MusicService.MusicBinder;
import com.test.music.entity.MusicInfo;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ServiceConnection{
    private ImageView iv_play,tv_pause,tv_next,tv_queue;
    private ListView lv_music;
    private SeekBar sbr;
    private int next = 0;
    private int max=0;//最大进度
    private int mprogress ;//seekbar拖拉是保存进度
    List<MusicInfo> list= null;
    private MusicListViewAdapter musicAdapter;
    public  static final int REQUEST_CODE = 1;
    MusicService musicBinder = null;
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mbuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();//控件必须在最前面初始化。
        //获取应用权限
        MPermissions.requestPermissions(MainActivity.this, REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
        MyListener();
    }
    /**
     * 应用的点击事件
     */
    private void MyListener(){
        /**
         * listView点击事件，音乐的点击
         */
        lv_music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyIntentService(position);
                InitNotification(list.get(position).getTitle(),list.get(position).getDisplay_name());
                next = position;
            }
        });
        /**
         * seekbar拖拉事件
         */
        sbr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mprogress = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(musicBinder != null) {
                    musicBinder.pause();
                    musicBinder.seekTo(mprogress);
                    musicBinder.moveon();
                    mprogress = 0;
                }else {
                    sbr.setProgress(0);
                }
            }
        });
    }
    /**
     * 初始化控件
     */
    private void initView() {
        lv_music = (ListView) findViewById(R.id.lv_music);
        iv_play = (ImageView) findViewById(R.id.iv_play);
        iv_play.setOnClickListener(this);
        tv_pause = (ImageView) findViewById(R.id.tv_pause);
        tv_pause.setOnClickListener(this);
        tv_next = (ImageView) findViewById(R.id.tv_next);
        tv_next.setOnClickListener(this);
        tv_queue = (ImageView) findViewById(R.id.tv_queue);
        tv_queue.setOnClickListener(this);
        sbr = (SeekBar) findViewById(R.id.sbr);

    }

    /**
     * 获取SD卡里的全部歌曲
     * @return 歌曲的List集合
     */
    public List<MusicInfo> getAllMusic(){
        List<MusicInfo> list = new ArrayList<>();
        //读取所有短Uri uri = Uri.parse("content://");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(Media.EXTERNAL_CONTENT_URI, new String[]{Media._ID,Media.DURATION,Media.TITLE,Media.DISPLAY_NAME}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(Media._ID));
                String title = cursor.getString(cursor.getColumnIndex(Media.TITLE));
                long duration = cursor.getLong(cursor.getColumnIndex(Media.DURATION));
                String display_name = cursor.getString(cursor.getColumnIndex(Media.DISPLAY_NAME));
                MusicInfo  music = new MusicInfo(id,title,duration,display_name);
                Log.i("Music","id-"+id+"title-"+title+"duration-"+duration+"display_name-"+display_name);
                list.add(music);
            }
            cursor.close();
        }
        return list;
    }

    /**
     * 初始化进度条
     */
    public void initBar(){
        if (musicBinder != null){
                //连接之后启动子线程设置当前进度
                new Thread() {
                    @Override
                    public void run() {
                            while (true) {
                                if (musicBinder != null) {//防止musicBinder突然为空。
                                    sbr.setProgress(musicBinder.getMusicCurrentPosition());
                                    mbuilder.setProgress(max,musicBinder.getMusicCurrentPosition(), false);
                                    mNotificationManager.notify(0, mbuilder.build());
                                try {
                                    Thread.sleep(50);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }.start();
        }
    }

    /**
     *
     * @param count
     */
    private void MyIntentService(int count){
        if (musicBinder != null) {
            unbindService(MainActivity.this);
        }
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        intent.putExtra("music", list.get(count));
        bindService(intent, MainActivity.this, Context.BIND_AUTO_CREATE);
    }
    /**
     * 初始化通知栏
     * @param title
     * @param text
     */
    private void InitNotification(String title,String text){
    mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    mbuilder = new NotificationCompat.Builder(MainActivity.this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.music);
        mbuilder.setLargeIcon(bitmap);
        mbuilder.setSmallIcon(R.drawable.test);
        mbuilder.setContentTitle(title);
        mbuilder.setTicker(title);
        mbuilder.setContentText(text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play:
                if (musicBinder != null) {
                    musicBinder.moveon();
                }
                break;
            case R.id.tv_pause:
                if (musicBinder != null) {
                    musicBinder.pause();
                }
                break;
            case R.id.tv_next:
                if (list != null) {
                    next++;
                    MyIntentService(next);
                    InitNotification(list.get(next).getTitle(),list.get(next).getDisplay_name());
                    if (next > (list.size() - 1)) {
                        next = 0;
                    }
                }
                break;
            case R.id.tv_queue:
                if (musicBinder != null) {
                    musicBinder = null;//取消后初始化musicBinder
                    unbindService(this);
                }
                break;
        }
    }
    /**
     * 授权操作
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @PermissionGrant(REQUEST_CODE)
    public void requestSuccess() {
        //权限获取后执行
        list = getAllMusic();
        musicAdapter = new MusicListViewAdapter(MainActivity.this,list);
        if (list != null) {
            lv_music.setAdapter(musicAdapter);
        }else {
            Toast.makeText(MainActivity.this,"手机里没有歌曲，请导入歌曲到手机。",Toast.LENGTH_LONG).show();
        }
    }
    @PermissionDenied(REQUEST_CODE)
    public void requestFail() {
        Toast.makeText(this, "您已拒绝应用访问SD卡", Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        musicBinder = ((MusicBinder)service).getMusicService();
        max = musicBinder.getMusicDuration();
        sbr.setMax(max);
        mbuilder.setWhen(System.currentTimeMillis());
        mbuilder.setProgress(max, 0, false);//设置为true，表示流动
        mNotificationManager.notify(0, mbuilder.build());
        initBar();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        //service意外不销毁时调用。比如 内存的资源不足
        Toast.makeText(this,"应用出错啦！有可能是手机内存的资源不足",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNotificationManager.cancelAll();
        if (musicBinder != null){
            musicBinder.unbindService(this);
        }
    }
}
