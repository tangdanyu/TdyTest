package com.example.tdytest.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.tdytest.R;


public class MyForegroundService extends Service {
    public MyForegroundService() {
    }
    private static final int SERVICE_ID = 101;
    private String TAG = "MyForegroundService";
    private int timeCount = 10 * 1000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotification() {
        String id = "UploadService";
        String name = "UploadService";

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;

        Intent intent = new Intent(this, ServiceActivity.class);
//        intent.setAction(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 5, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        String appName;
        int iconId;
        appName = getString(R.string.app_name);
        iconId =  R.mipmap.ic_launcher;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            notification = new NotificationCompat.Builder(this)
                    .setChannelId(id)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), iconId))
                    .setContentTitle(appName + "正在运行")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(iconId).build();
        } else {
            notification = new NotificationCompat.Builder(this)
                    .setContentTitle(appName + "正在运行")
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), iconId))
                    .setSmallIcon(iconId)
                    .setOngoing(true)
                    .setContentIntent(pendingIntent)
                    .setChannelId(id).build();
        }
        startForeground(SERVICE_ID, notification);//启动前台服务

    }
}