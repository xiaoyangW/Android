package com.test.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btn_show;
    /*notification管理*/
    int i =0;
    String string = "123";
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mbuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        btn_show = (Button) findViewById(R.id.btn_show);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mbuilder = new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("Picture Download")
                        .setContentText("Download in progress");
                mbuilder.setSmallIcon(R.drawable.test);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//悬挂式Notification，5.0后显示

                    *//*mbuilder.setContentText( "点击查看。").setFullScreenIntent(pendingIntent, true);*//*
                    mbuilder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
                    mbuilder.setSmallIcon(R.drawable.test);// 设置通知小ICON（5.0必须采用白色透明图片）

                }else{

                    mbuilder.setSmallIcon(R.drawable.test);// 设置通知小ICON

                    mbuilder.setContentText(string);

                }*/
                mbuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.music));// 设置通知大ICON

                mbuilder.setTicker("Notification"); // 通知首次出现在通知栏，带上升动画效果的

                mbuilder.setWhen(System.currentTimeMillis());// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间

                mbuilder.setPriority(NotificationCompat.PRIORITY_MAX); // 设置该通知优先级

                mbuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);//在任何情况下都显示，不受锁屏影响。

                mbuilder.setAutoCancel(true);// 设置这个标志当用户单击面板就可以让通知将自动取消

                mbuilder.setOngoing(false);// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用NotificationCompat.DEFAULT_ALL属性，可以组合
                mbuilder.setVibrate(new long[] { 0, 100, 500, 100 });//振动效果需要振动权限

                mbuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);//闪灯
                Notification notification = mbuilder.build();//API 16
                mNotificationManager.notify(1, notification);
                mbuilder.setProgress(100, i, false);//设置为true，表示流动
                mNotificationManager.notify(0, mbuilder.build());
                //5秒之后还停止流动
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(100);
                            i++;
                            mbuilder.setProgress(100, i, false);
                            mNotificationManager.notify(0, mbuilder.build());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //设置为true，表示刻度
                    }
                }).start();
            }
        });
    }
}
