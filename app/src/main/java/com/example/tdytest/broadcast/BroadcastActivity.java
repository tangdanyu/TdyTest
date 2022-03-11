package com.example.tdytest.broadcast;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;
import com.example.tdytest.receiver.MyBroadcastReceiver;

public class BroadcastActivity extends BaseActivity {

    private BroadcastReceiver br;

    @Override
    public int getLayoutId() {
        return R.layout.activity_broadcast;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        br = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        this.registerReceiver(br, filter);
         
    }

    @Override
    protected void onDestroy() {
        if(br!= null){
            unregisterReceiver(br);
        }
        super.onDestroy();
    }
}