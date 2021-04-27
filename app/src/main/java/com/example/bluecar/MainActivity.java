package com.example.bluecar;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bluecar.bluetooth.BluetoothDeviceListActivity;
import com.example.bluecar.bluetooth.BluetoothUtils;

import java.util.Objects;



public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    public static final int BT_ENABLE_TRUE = 123456;
    public static final int BT_ENABLE_FALSE = 654321;
    public static BluetoothUtils bluetoothUtils;

    public Handler mHandler;
//    AsrInit asrInit;
    //    private TextView showResults;
    private boolean isBTAvailable;
    private Button bt_startListen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothUtils = new BluetoothUtils();
        isBTAvailable = bluetoothUtils.isBlueToothAvailable();
        if (!isBTAvailable) {
            Toast.makeText(this, "蓝牙是不可用的", Toast.LENGTH_LONG).show();
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//                if (msg.what == BT_ENABLE_TRUE) {
//                    setBtEnabled(true);
//                } else if (msg.what == BT_ENABLE_FALSE) {
//                    setBtEnabled(false);
//                }
//            }
//        };

        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                if (message.what == BT_ENABLE_TRUE) {
                    setBtEnabled(true);
                } else if (message.what == BT_ENABLE_FALSE) {
                    setBtEnabled(false);
                }
                return false;
            }
        });


//        asrInit = new AsrInit(MainActivity.this, mHandler);
//        asrInit.init();

//        showResults = findViewById(R.id.textView);
//        bt_startListen = findViewById(R.id.bt_startListen);
//
//        bt_startListen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                asrInit.startRecord();
//            }
//        });

        findViewById(R.id.go).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(1);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(1);
                }
                view.performClick();
                return false;
            }
        });

        findViewById(R.id.back).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(2);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(2);
                }
                view.performClick();
                return false;
            }
        });

        findViewById(R.id.turnLeft).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(3);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(3);
                }
                return false;
            }
        });

        findViewById(R.id.turnRight).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(4);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(4);
                }
                return false;
            }
        });

        findViewById(R.id.stop).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(5);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(5);
                }
                return false;
            }
        });

        findViewById(R.id.spray).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(6);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(6);
                }
                return false;
            }
        });

        findViewById(R.id.autoMode).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(7);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP && bluetoothUtils.isConnected()) {
                    bluetoothUtils.write(7);
                }
                return false;
            }
        });

        findViewById(R.id.bt_bluetooth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!isBTAvailable) {
                    Toast.makeText(MainActivity.this, "蓝牙是不可用的", Toast.LENGTH_LONG).show();
                } else {
                    String[] items = new String[]{"打开蓝牙", "连接蓝牙", "断开蓝牙"};
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this).setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case 0:   //打开蓝牙
                                    bluetoothUtils.openBlueTooth(MainActivity.this);
                                    break; //可选
                                case 1:  //连接蓝牙
                                    if (!isBTAvailable) {
                                        Toast.makeText(MainActivity.this, "蓝牙是不可用的", Toast.LENGTH_LONG)
                                                .show();
                                    } else if (!bluetoothUtils.getBluetoothAdapter().isEnabled()) {
                                        Toast.makeText(MainActivity.this, "未打开蓝牙", Toast.LENGTH_SHORT)
                                                .show();
                                    } else {
                                        Intent serverIntent = new Intent(MainActivity.this,
                                                BluetoothDeviceListActivity.class);   //跳转到蓝牙扫描连接页面
                                        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                                    }
                                    break; //可选
                                case 2:// 断开连接
                                    if (!bluetoothUtils.isConnected()) {
                                        Toast.makeText(MainActivity.this, "无连接", Toast.LENGTH_SHORT)
                                                .show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "已断开连接", Toast.LENGTH_SHORT)
                                                .show();
                                        bluetoothUtils.cancelConnect();
                                    }
                                    break;
                                default: //可选
                                    //语句
                            }
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    //获取蓝牙设备名，并进行蓝牙连接
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CONNECT_DEVICE) {
            // 当DeviceListActivity返回与设备连接的消息
            if (resultCode == Activity.RESULT_OK) {
                // 得到链接设备的MAC
                String address = Objects.requireNonNull(data.getExtras()).getString(
                        BluetoothDeviceListActivity.EXTRA_DEVICE_ADDRESS, "");
                // 得到BluetoothDevice对象
                if (!TextUtils.isEmpty(address)) {
                    BluetoothDevice device = bluetoothUtils.getBluetoothAdapter().getRemoteDevice(address);
                    boolean conSt = bluetoothUtils.connectThread(device);
                    if (conSt) {
                        Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(TAG, "onBackPressed() : finish()");
        finish();
    }

//    private void reset() {
//        asrInit.cancelListening();
////        setBtEnabled(true);
//        bt_startListen.setEnabled(true);
////        showResult.setText("");
//    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause() ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop() ");
        super.onStop();
//        reset();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        asrInit.destroyEngine();
    }

    public void setBtEnabled(boolean isEnabled) {
        bt_startListen.setEnabled(isEnabled);
    }

}