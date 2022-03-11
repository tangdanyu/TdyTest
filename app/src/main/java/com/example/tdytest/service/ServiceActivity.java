package com.example.tdytest.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;

public class ServiceActivity extends BaseActivity implements View.OnClickListener {
    private Button startServiceBtn;
    private Button stopServiceBtn;
    private Button bindServiceBtn;
    private Button unbindServiceBtn;
    private Button startIntentServiceBtn;
    private Button startForegroundServiceBtn;
    private Button stopForegroundServiceBtn;

    private SimpleService.SimpleBinder binder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "onServiceConnected "+name.toString());
            binder = (SimpleService.SimpleBinder) service;
            binder.doTask();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG,"onServiceDisconnected "+ name.toString());
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_service;
    }

    @Override
    public void initView() {
        startServiceBtn = findViewById(R.id.btn_start_service);
        stopServiceBtn = findViewById(R.id.btn_stop_service);
        bindServiceBtn = findViewById(R.id.btn_bind_service);
        unbindServiceBtn = findViewById(R.id.btn_unbind_service);
        startIntentServiceBtn = findViewById(R.id.btn_start_intent_service);
        startForegroundServiceBtn = findViewById(R.id.btn_start_foreground_service);
        stopForegroundServiceBtn = findViewById(R.id.btn_stop_foreground_service);

    }

    @Override
    public void initListener() {
        startServiceBtn.setOnClickListener(this);
        stopServiceBtn.setOnClickListener(this);
        bindServiceBtn.setOnClickListener(this);
        unbindServiceBtn.setOnClickListener(this);
        startIntentServiceBtn.setOnClickListener(this);
        startForegroundServiceBtn.setOnClickListener(this);
        stopForegroundServiceBtn.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_service:
                Intent startServiceIntent = new Intent(this,SimpleService.class);
                startService(startServiceIntent);
                break;
            case R.id.btn_stop_service:
                Intent stopServiceIntent = new Intent(this,SimpleService.class);
                stopService(stopServiceIntent);
                break;
            case R.id.btn_bind_service:
                Intent bindIntent = new Intent(this, SimpleService.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                try {
                    unbindService(connection);
                }catch (Exception e){
                    Log.e(TAG,"unbindService "+ e );
                }
                break;
            case R.id.btn_start_intent_service:
                for(int i = 0;i<5;i++){
                    MyIntentService.startActionFoo(this,"aaa"+i,"bbb"+i);
                }
                break;
            case R.id.btn_start_foreground_service:
                Intent intentStartForegroundService = new Intent(this, MyForegroundService.class);
                ContextCompat.startForegroundService(this, intentStartForegroundService);
                break;
            case R.id.btn_stop_foreground_service:
                Intent intentStopForegroundService = new Intent(this, MyForegroundService.class);
                stopService(intentStopForegroundService);
                break;
        }
    }
}