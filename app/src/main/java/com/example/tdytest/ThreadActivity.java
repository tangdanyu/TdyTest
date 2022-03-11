package com.example.tdytest;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

/**
 *  MainActivity: onPause
 *  ThreadActivity: onCreate
 *  ThreadActivity: onStart
 *  ThreadActivity: onResume
 *  MainActivity: onStop
 *  MainActivity: onSaveInstanceState
 *  My Thread ===>: run
 *  My Thread ===>: Thread-4 i=0
 *  My Thread ===>: Thread-4 i=1
 *  My Thread ===>: Thread-4 i=2
 *  My Thread ===>: Thread-4 i=3
 *  My Thread ===>: Thread-4 i=4
 *  My Thread ===>: Thread-4 i=5
 *  My Thread ===>: Thread-4 i=6
 *  My Thread ===>: Thread-4 i=7
 *  My Thread ===>: Thread-4 i=8
 *  My Thread ===>: Thread-4 i=9
 *  My Runnable ===>: run
 *  My Runnable ===>: Thread-5 i=0
 *  My Runnable ===>: Thread-5 i=1
 *  My Runnable ===>: Thread-5 i=2
 *  My Runnable ===>: Thread-5 i=3
 *  My Runnable ===>: Thread-5 i=4
 *  My Runnable ===>: Thread-5 i=5
 *  My Runnable ===>: Thread-5 i=6
 *  My Runnable ===>: Thread-5 i=6
 *  My Runnable ===>: Thread-5 i=7
 *  My Runnable ===>: Thread-5 i=8
 *  My Runnable ===>: Thread-5 i=9
 *  ThreadActivity: run
 *  ThreadActivity: Thread-6 i=0
 *  ThreadActivity: Thread-6 i=1
 *  ThreadActivity: Thread-6 i=2
 *  ThreadActivity: Thread-6 i=3
 *  ThreadActivity: Thread-6 i=4
 *  ThreadActivity: Thread-6 i=5
 *  ThreadActivity: Thread-6 i=6
 *  ThreadActivity: Thread-6 i=7
 *  ThreadActivity: Thread-6 i=8
 *  ThreadActivity: Thread-6 i=9
 *  ThreadActivity: main 0
 *  ThreadActivity: main 1
 *  ThreadActivity: main 2
 *  ThreadActivity: main 3
 *  ThreadActivity: main 4
 *  ThreadActivity: main 5
 *  ThreadActivity: main 6
 *  ThreadActivity: main 7
 *  ThreadActivity: onPause
 *  MainActivity: onRestart
 *  MainActivity: onStart
 *  MainActivity: onResume
 *  ThreadActivity: main 8
 *  ThreadActivity: onStop
 *  ThreadActivity: onDestroy
 *  有返回值的线程 0
 *  有返回值的线程 1
 *  有返回值的线程 2
 *  有返回值的线程 3
 *  有返回值的线程 4
 *  有返回值的线程 5
 *  有返回值的线程 6
 *  有返回值的线程 7
 *  有返回值的线程 8
 *  有返回值的线程 9
 *  子线程的返回值：10
 *  Runnable10
 *  Runnable9
 *  Runnable8
 *  num:8
 *  num:8
 *  Runnable7
 *  num:6
 *  Runnable6
 *  num:6
 *  Runnable5
 *  num:5
 *  Runnable4
 *  Runnable3
 *  num:3
 *  num:3
 *  Runnable2
 *  num:2
 *  Runnable1
 *  num:1
 *  Runnable0
 *  num:0
 *  num:0
 *  Semaphore semaphore = new Semaphore(1, true)
 *  semaphore.acquire();10
 *  semaphore.acquire();9
 *  semaphore.release();
 *  num:10
 *  semaphore.acquire();8
 *  semaphore.release();
 *  num:9
 *  semaphore.acquire();7
 *  semaphore.release();
 *  num:8
 *  semaphore.acquire();6
 *  semaphore.release();
 *  num:7
 *  semaphore.acquire();5
 *  semaphore.release();
 *  num:6
 *  semaphore.acquire();4
 *  semaphore.release();
 *  num:5
 *  semaphore.acquire();3
 *  semaphore.release();
 *  num:4
 *  semaphore.acquire();2
 *  semaphore.release();
 *  num:3
 *  semaphore.acquire();1
 *  semaphore.release();
 *  num:2
 *  semaphore.acquire();0
 *  semaphore.release();
 *  num:1
 *  semaphore.release();
 *  num:0
 */
public class ThreadActivity extends BaseActivity implements View.OnClickListener {

    private Button startThreadBtn;
    private Button startRunnableBtn;
    private Button startNewThreadBtn;
    private Button startHandlerBtn;
    private Button startCallableBtn;
    private Button noSemaphoreBtn;
    private Button useSemaphoreBtn;
    private Button startHandlerThreadBtn;
    private Button stopHandlerThreadBtn;
    private final Semaphore semaphore = new Semaphore(5, true);//信号量这个队列限制只有1个存在，那么在创建时要写0
    private String LogString;
    private HandlerThread mHandlerThread;
    private Handler mainHandler,workHandler;
    @Override
    public int getLayoutId() {
        return R.layout.activity_thread;
    }

    @Override
    public void initView() {
        startThreadBtn = findViewById(R.id.btn_start_thread);
        startRunnableBtn = findViewById(R.id.btn_start_runnable);
        startNewThreadBtn = findViewById(R.id.btn_start_new_thread);
        startHandlerBtn = findViewById(R.id.btn_start_handler);
        startCallableBtn = findViewById(R.id.btn_start_callable);
        noSemaphoreBtn = findViewById(R.id.btn_no_semaphore);
        useSemaphoreBtn = findViewById(R.id.btn_use_semaphore);
        startHandlerThreadBtn = findViewById(R.id.btn_start_handler_thread);
        stopHandlerThreadBtn = findViewById(R.id.btn_stop_handler_thread);
    }

    @Override
    public void initListener() {
        startThreadBtn.setOnClickListener(this);
        startRunnableBtn.setOnClickListener(this);
        startNewThreadBtn.setOnClickListener(this);
        startHandlerBtn.setOnClickListener(this);
        startCallableBtn.setOnClickListener(this);
        noSemaphoreBtn.setOnClickListener(this);
        useSemaphoreBtn.setOnClickListener(this);
        startHandlerThreadBtn.setOnClickListener(this);
        stopHandlerThreadBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        // 创建与主线程关联的Handler
        mainHandler = new Handler();
        /**
         * 步骤1：创建HandlerThread实例对象
         * 传入参数 = 线程名字，作用 = 标记该线程
         */
        mHandlerThread = new HandlerThread("handlerThread");
        /**
         * 步骤2：启动线程
         */
        mHandlerThread.start();
        /**
         * 步骤3：创建工作线程Handler & 复写handleMessage（）
         * 作用：关联HandlerThread的Looper对象、实现消息处理操作 & 与其他线程进行通信
         * 注：消息处理操作（HandlerMessage（））的执行线程 = mHandlerThread所创建的工作线程中执行
         */
        workHandler = new Handler(mHandlerThread.getLooper()){
            @Override
            // 消息处理的操作
            public void handleMessage(Message msg)
            {
                //设置了两种消息处理操作,通过msg来进行识别
                switch(msg.what){
                    // 消息1
                    case 1:
                        try {
                            //延时操作
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 通过主线程Handler.post方法进行在主线程的UI更新操作
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run () {
                                 setTitle("我爱学习");
                            }
                        });
                        break;

                    // 消息2
                    case 2:
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run () {
                                setTitle("我不喜欢学习");
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_thread:
                new MyThread().start();
                break;
            case R.id.btn_start_runnable:
                new Thread(new MyRunnable()).start();
                break;
            case R.id.btn_start_new_thread:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e(TAG, "run");
                            for(int i = 0; i<10; i++)
                            {
                                Log.e(TAG, Thread.currentThread().getName() + " i=" + i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_start_handler:
                // 通过Handler启动线程
                mHandler.post(mRunnable);  //发送消息，启动线程运行
                break;
            case R.id.btn_start_callable:
                MyCallable ctt = new MyCallable();
                FutureTask<Integer> ft = new FutureTask<>(ctt);
                new Thread(ft,"有返回值的线程").start();
                try {
                    Log.e(TAG, "子线程的返回值：" + ft.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_no_semaphore:
                startNoSemaphore();
                break;
            case R.id.btn_use_semaphore:
                startUseSemaphore();
                break;
            case R.id.btn_start_handler_thread:
                /**
                 * 步骤4：使用工作线程Handler向工作线程的消息队列发送消息
                 * 在工作线程中，当消息循环时取出对应消息 & 在工作线程执行相关操作
                 */
                // 通过sendMessage（）发送
                // a. 定义要发送的消息
                Message msg = Message.obtain();
                msg.what = 1; //消息的标识
                msg.obj = "A"; // 消息的存放
                // b. 通过Handler发送消息到其绑定的消息队列
                workHandler.sendMessage(msg);
                break;
            case R.id.btn_stop_handler_thread:
                mHandlerThread.quit();
                break;
        }
    }

    //第一种启用方法是通过继承Thread类，并改写run方法来实现一个线程
    public class MyThread extends Thread {
        //继承Thread类，并改写其run方法
        private final static String TAG = "My Thread ===> ";
        public void run(){
            Log.e(TAG, "run");
            for(int i = 0; i<10; i++)
            {
                Log.e(TAG, Thread.currentThread().getName() + " i=" + i);
            }
        }
    }

    //第二种启用方式创建一个Runnable对象
    public class MyRunnable implements Runnable{
        private final static String TAG = "My Runnable ===> ";
        @Override
        public void run() {
            Log.e(TAG, "run");
            for(int i = 0; i<10; i++) {
                Log.e(TAG, Thread.currentThread().getName() + " i=" + i);
            }
        }
    }

    //第三种启用方式通过实现Callable接口，可以有返回值
    private class MyCallable implements Callable<Integer>{
        @Override
        public Integer call() throws Exception {
            int i = 0;
            for(;i<10;i++) {
                Log.e(TAG, Thread.currentThread().getName() + " " + i);
            }
            return i;
        }
    }

    /**********不使用信号量**********/
    private void startNoSemaphore() {
        for (int i = 10; i >= 0; i--) {
            LogString = "num:" + i;
            printLogString(i);
        }
    }

    private void printLogString(final int delayTime) {
        Log.e(TAG, "Runnable" + delayTime);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, LogString);
            }
        }).start();
    }

    /********使用信号量***********/
    private void startUseSemaphore() {
        for (int i = 10; i >= 0; i--) {
            try {
                Log.e(TAG, "semaphore.acquire();" + i);
                semaphore.acquire();
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
            LogString = "num:" + i;
            printLogStringUseSemaphore(i);
        }
    }

    private void printLogStringUseSemaphore(final int delayTime) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "semaphore.release();");
                Log.e(TAG, LogString);
                semaphore.release();
            }
        }).start();
    }
    //通过Handler启动线程
    private int count = 0;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        public void run() {
            Log.e(TAG, Thread.currentThread().getName() + " " + count);
            count++;
            setTitle("" + count);
            // 每3秒执行一次
            mHandler.postDelayed(mRunnable, 3000);  //给自己发送消息，自运行
        }
    };

    @Override
    protected void onDestroy() {
        //将线程销毁掉
        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }
}