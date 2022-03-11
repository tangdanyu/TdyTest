package com.example.tdytest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * 该类执行的工作与使用 IntentService 的示例完全相同。
 */
public class MyService extends Service {
    public static final String TAG = "MyService";
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    // 从线程Looper接收消息Message的Handler
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // 通常我们会在这里做一些工作，比如下载文件。
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // 恢复中断状态。
                Thread.currentThread().interrupt();
            }
            // 使用STARTID停止服务，这样我们就不会在处理另一个工作的过程中停止服务。
            stopSelf(msg.arg1);
        }
    }
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 启动运行服务的线程。
        // 请注意，我们创建了一个单独的线程，因为服务通常在进程的主线程中运行，我们不想阻止它。
        // 我们还将其设置为后台优先级，这样CPU密集型工作就不会干扰我们的UI。
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
//                Process.THREAD_PRIORITY_BACKGROUND);
                10);
        thread.start();

        // 获取HandlerThread的Looper，并将其用于我们的Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        // 对于每个启动请求，发送一条消息来启动一个作业，并传递开始ID，这样我们就知道在完成作业时要停止哪个请求
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);

        //如果我们被杀了，从这里回来后，重新开始
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

    }

}