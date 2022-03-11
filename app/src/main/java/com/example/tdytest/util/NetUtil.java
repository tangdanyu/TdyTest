package com.example.tdytest.util;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import androidx.appcompat.app.AlertDialog;

public class NetUtil {
    private Context context;
    private BroadcastReceiver mNetworkChangeReceiver;
    private AlertDialog dialog;
    //onResume时调用
    public void registerReceiver(Context context){
        this.context = context;
        initDialog();
        //注册广播用于监听网络状态改变
        mNetworkChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                detectNetWork();
            }
        };
        context.registerReceiver(mNetworkChangeReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
    }
    private void initDialog() {
        dialog = new AlertDialog.Builder(context).setTitle("网络异常")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                }).setMessage("网络发生异常，请检查后重试！").create();
    }
    protected void detectNetWork() {
        if (!isConnected(context)) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }
    //判断网络是否连接
    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
    //onPause时调用
    public void unregisterReceiver(){
        context.unregisterReceiver(mNetworkChangeReceiver);
    }

}
