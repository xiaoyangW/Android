package com.test.sms;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony.Sms;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.test.sms.Adapter.Adapter;
import com.test.sms.Entity.MySms;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lv_sms;
    private Adapter SMSadapter;
    List<MySms> list = null;
    public  static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        MPermissions.requestPermissions(MainActivity.this, REQUEST_CODE, Manifest.permission.READ_SMS);
        MPermissions.requestPermissions(MainActivity.this, REQUEST_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void initView() {
        lv_sms = (ListView) findViewById(R.id.lv_sms);
    }

    /**
     * 获取短信
     *
     * @return
     */
    private List<MySms> ReadSMS() {
        List<MySms> list = new ArrayList<>();
        //读取所有短信
        Uri uri = Uri.parse("content://sms/");
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, new String[]{Sms._ID, Sms.ADDRESS, Sms.BODY, Sms.DATE, Sms.TYPE}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            int _id;
            String address;
            String body;
            String date;
            int type;
            while (cursor.moveToNext()) {

                _id = cursor.getInt(0);
                address = cursor.getString(1);
                body = cursor.getString(2);
                date = cursor.getString(3);
                type = cursor.getInt(4);
                MySms sms = new MySms(address, body, toTime(date), type);
                list.add(sms);
            }
        }
        return list;
    }

    /**
     * 将Long类型的时间转换为标准时间
     *
     * @param time
     * @return
     */
    private String toTime(String time) {
        long l = Long.valueOf(time).longValue();
        Date date = new Date(l);
        String strs = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-ss");
            strs = sdf.format(date);
            System.out.println(strs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //授权后的操作
    @PermissionGrant(REQUEST_CODE)
    public void requestSuccess() {
        list = ReadSMS();
        SMSadapter = new Adapter(list, MainActivity.this);
        lv_sms.setAdapter(SMSadapter);
    }
    //用户拒绝权限申请后的操作
    @PermissionDenied(REQUEST_CODE)
    public void requestFail() {
        Toast.makeText(this, "您已拒绝应用访问SD卡", Toast.LENGTH_SHORT).show();
        finish();
    }
}
