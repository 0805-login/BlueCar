package com.example.bluecar.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class BluetoothUtils {

    private static final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int REQUEST_ENABLE_BT = 2;
    private BluetoothSocket bluetoothSocket;
    private boolean connectStatus = false;
    private BluetoothAdapter bluetoothAdapter;


    public BluetoothAdapter getBluetoothAdapter() {
        if (bluetoothAdapter != null) {
            return bluetoothAdapter;
        } else {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            return bluetoothAdapter;
        }
    }

    public boolean isConnected() {
        return connectStatus;
    }

    /**
     * 获取BluetoothAdapter对象，并判断蓝牙是否可用
     */
    public boolean isBlueToothAvailable() {
        //获取BluetoothAdapter对象
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // 判断蓝牙是否可用
        return bluetoothAdapter != null;
    }

    /**
     * 打开蓝牙
     *
     * @param activity 启动该方法的Activity
     */
    public void openBlueTooth(Activity activity) {
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            Toast.makeText(activity, "蓝牙已打开", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    /**
     * 蓝牙发送数据
     *
     * @param str 待发送的字符串
     */
    public void write(int i) {
        if (connectStatus) {
            try {
                bluetoothSocket.getOutputStream().write(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接设备
     *
     * @param device 蓝牙设备
     * @return 连接状态
     */
    public boolean connectThread(BluetoothDevice device) {
        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
            bluetoothAdapter.cancelDiscovery();
            bluetoothSocket.connect();
            connectStatus = true;  //连接成功
            // 接收数据进程
            new Thread(new Runnable() {  //读线程
                @Override
                public void run() {
                    int bytes;
                    byte[] buffer = new byte[256];
                    while (true) {
                        if (bluetoothSocket != null && bluetoothSocket.isConnected()) {
                            try { // 接收数据
                                bytes = bluetoothSocket.getInputStream().read(buffer);
                                final String readStr = new String(buffer, 0, bytes); //读出的数据
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            connectStatus = false;  //连接失败
            try {
                bluetoothSocket.close();
            } catch (IOException e2) {
                e.printStackTrace();
            }
        }
        return connectStatus;
    }

    /**
     * 取消连接
     */
    public void cancelConnect() {
        try {
            bluetoothSocket.close();
            connectStatus = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
