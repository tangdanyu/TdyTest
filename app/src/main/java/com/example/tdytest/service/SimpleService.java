package com.example.tdytest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Service是运行在主线程的
 * startService -> onCreate -> onStartCommand -> onStart -> stopService -> onDestroy
 * startService -> onCreate -> onStartCommand -> onStart ->startService -> onStartCommand -> onStart-> stopService -> onDestroy
 * startService -> onCreate -> onStartCommand -> onStart -> bindService -> onBind ->onServiceConnected -> doTask -> unbindService -> onUnbind -> stopService ->onDestroy
 * startService -> onCreate -> onStartCommand -> onStart -> bindService -> onBind ->onServiceConnected -> doTask -> stopService -> unbindService -> onUnbind -> onDestroy
 * bindService -> onCreate -> onBind -> onServiceConnected -> doTask ->unbindService -> onUnbind-> onDestroy
 * bindService -> onCreate -> onBind -> onServiceConnected -> doTask -> startService -> onStartCommand-> onStart ->unbindService -> onUnbind-> stopService-> onDestroy
 * bindService -> onCreate -> onBind -> onServiceConnected -> doTask -> startService -> onStartCommand-> onStart -> stopService -> unbindService -> onUnbind-> onDestroy
 * unbindService两次，出现异常：Service not registered
 * startService -> onCreate -> onStartCommand -> onStart-> bindService -> onServiceConnected -> doTask -> stopService 无效，必须要unbindService,之后无需stopService
 */
public class SimpleService extends Service {

    public static final String TAG = "SimpleService";
    private SimpleBinder mBinder;
    public SimpleService() {
    }

    //首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
    //如果服务已在运行，则不会调用此方法。该方法只被调用一次
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate");//在主线程中 main
//        Log.e(TAG, "SimpleService--" + Thread.currentThread().getName() + "--"+Thread.currentThread().getId());
        mBinder = new SimpleBinder();
    }


    //在onStartCommand之后调用
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(TAG, "onStart");
    }

    //每次通过startService()方法启动Service时都会被回调。
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 任务逻辑
//                Log.e(TAG, "onStartCommand 子线程处理耗时操作");
//                Log.e(TAG, "onStartCommand--" + Thread.currentThread().getName() + "--"+Thread.currentThread().getId());
                //stopSelf();调用此方法相当于调用stopService() 来停止服务，会走onDestroy回调
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    //服务销毁时的回调
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    //绑定服务时才会调用
    //必须要实现的方法
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        // TODO: Return the communication channel to the service.
        if (mBinder != null) {
            return mBinder;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind");
//        return super.onUnbind(intent);
        return true;
    }

    //如果在 onUnbind()方法返回 true 的情况下会执行,否则不执行。
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.e(TAG, "onRebind");
    }

    class SimpleBinder extends Binder {

        public void doTask() {
            Log.e(TAG, "doTask");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 任务逻辑
//                    Log.e(TAG, "doTask 子线程处理耗时操作");
//                    Log.e(TAG, "doTask--" + Thread.currentThread().getName() + "--"+Thread.currentThread().getId());
                }
            }).start();
        }
    }
}
