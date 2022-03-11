package com.example.tdytest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.tdytest.service.AlarmService;

/**
 * 要在清单文件中注册
 * AlarmService模拟后台任务，定时发起广播
 * AlarmReceive启动AlarmService,达到循环启动Service的效果
 * 通过Service和Receiver的死循环，确保后台任务不被系统杀死。
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //循环启动Service
        Intent i = new Intent(context, AlarmService.class);
        context.startService(i);
    }
}
