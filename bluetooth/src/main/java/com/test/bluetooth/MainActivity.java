package com.test.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    private Button btn_open;
    private LinearLayout activity_main;
    List<String> list;//设备集合
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        activity_main = (LinearLayout) findViewById(R.id.activity_main);
        if (bluetoothAdapter == null) {
            Snackbar.make(activity_main, "无蓝牙", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(activity_main, "有蓝牙", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        btn_open = (Button) findViewById(R.id.btn_open);
        //打开BlueTooth
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.强制打开蓝牙(不建议使用)
               /* BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                if (defaultAdapter != null){
                    defaultAdapter.enable();
                }*/
                //弹出系统弹框提示用户打开Bluetooth
                openBluetooth();
                BondedDevices();
            }
        });
        //注册广播接收者
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReciver , filter);//在onDestroy时要注销广播。
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReciver);//注销广播
    }

    /**
     * 通过隐式标签开启蓝牙。
     */
    private void openBluetooth(){
        //2.打开Bluetooth请求
        Intent requestBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        //设置设备可被检测到
        requestBluetooth.setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        //设备可见时间
        requestBluetooth.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,250);
        //请求
        startActivityForResult(requestBluetooth,1313);
        //3.跳到系统Bluetooth设置自己设置开关
       /* startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));*/
    }

    /**
     * getBondedDevices方法可以获取查询到的所有设备集合
     */
    private List<String> BondedDevices(){
        list = new ArrayList();
        Set<BluetoothDevice> mdevices = bluetoothAdapter.getBondedDevices();
        if (mdevices.size() > 0){
            for (BluetoothDevice device : mdevices ){
                //添加到list中给适配器使用
                list.add(device.getName()+"\n"+device.getAddress());
                Log.i("WXY",device.getAddress());
            }
        }
        return list;
    }
    //用ACTION_FOUND为action注册广播接收器
    private final BroadcastReceiver mReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           String action = intent.getAction();
            //发现设备
            if (BluetoothDevice.ACTION_FOUND.equals(action)){
                //从Intent中获取设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //添加名字和地址到适配器中
                list.add(device.getName()+"\n"+device.getAddress());
            }else {

            }
        }
    };

}
