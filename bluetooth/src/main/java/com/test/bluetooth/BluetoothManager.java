package com.test.bluetooth;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by WX on 2016/7/4.
 * 蓝牙
 */

public class BluetoothManager {
    /**
     * 当前设备是否支持Bluetooth
     * @return true：支持
     */
    public static boolean isBluetoothSupported(){
        return BluetoothAdapter.getDefaultAdapter() != null ? true : false;
    }

    /**
     * 检测当前Bluetooth是否开启
     * @return true:开启
     */
    public  static boolean isBluetoothEnabled(){
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null){
            return defaultAdapter.isEnabled();
        }
        return false;
    }

    /**
     * 强制打开Bluetooth（不建议使用）
     * @return true：打开
     */
    public static boolean turnOnBluetooth(){
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null){
            return defaultAdapter.enable();
        }
        return false;
    }
    /**
     * 强制打开Bluetooth（不建议使用）
     * @return true：关闭
     */
    public static boolean turnOffBluetooth(){
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null){
            return defaultAdapter.disable();
        }
        return false;
    }

}
