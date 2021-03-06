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
 *  ????????????????????? 0
 *  ????????????????????? 1
 *  ????????????????????? 2
 *  ????????????????????? 3
 *  ????????????????????? 4
 *  ????????????????????? 5
 *  ????????????????????? 6
 *  ????????????????????? 7
 *  ????????????????????? 8
 *  ????????????????????? 9
 *  ????????????????????????10
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
    private final Semaphore semaphore = new Semaphore(5, true);//?????????????????????????????????1????????????????????????????????????0
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
        // ???????????????????????????Handler
        mainHandler = new Handler();
        /**
         * ??????1?????????HandlerThread????????????
         * ???????????? = ????????????????????? = ???????????????
         */
        mHandlerThread = new HandlerThread("handlerThread");
        /**
         * ??????2???????????????
         */
        mHandlerThread.start();
        /**
         * ??????3?????????????????????Handler & ??????handleMessage??????
         * ???????????????HandlerThread???Looper????????????????????????????????? & ???????????????????????????
         * ???????????????????????????HandlerMessage???????????????????????? = mHandlerThread?????????????????????????????????
         */
        workHandler = new Handler(mHandlerThread.getLooper()){
            @Override
            // ?????????????????????
            public void handleMessage(Message msg)
            {
                //?????????????????????????????????,??????msg???????????????
                switch(msg.what){
                    // ??????1
                    case 1:
                        try {
                            //????????????
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // ???????????????Handler.post???????????????????????????UI????????????
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run () {
                                 setTitle("????????????");
                            }
                        });
                        break;

                    // ??????2
                    case 2:
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run () {
                                setTitle("??????????????????");
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
                // ??????Handler????????????
                mHandler.post(mRunnable);  //?????????????????????????????????
                break;
            case R.id.btn_start_callable:
                MyCallable ctt = new MyCallable();
                FutureTask<Integer> ft = new FutureTask<>(ctt);
                new Thread(ft,"?????????????????????").start();
                try {
                    Log.e(TAG, "????????????????????????" + ft.get());
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
                 * ??????4?????????????????????Handler??????????????????????????????????????????
                 * ????????????????????????????????????????????????????????? & ?????????????????????????????????
                 */
                // ??????sendMessage????????????
                // a. ????????????????????????
                Message msg = Message.obtain();
                msg.what = 1; //???????????????
                msg.obj = "A"; // ???????????????
                // b. ??????Handler???????????????????????????????????????
                workHandler.sendMessage(msg);
                break;
            case R.id.btn_stop_handler_thread:
                mHandlerThread.quit();
                break;
        }
    }

    //????????????????????????????????????Thread???????????????run???????????????????????????
    public class MyThread extends Thread {
        //??????Thread??????????????????run??????
        private final static String TAG = "My Thread ===> ";
        public void run(){
            Log.e(TAG, "run");
            for(int i = 0; i<10; i++)
            {
                Log.e(TAG, Thread.currentThread().getName() + " i=" + i);
            }
        }
    }

    //?????????????????????????????????Runnable??????
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

    //?????????????????????????????????Callable???????????????????????????
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

    /**********??????????????????**********/
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

    /********???????????????***********/
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
    //??????Handler????????????
    private int count = 0;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        public void run() {
            Log.e(TAG, Thread.currentThread().getName() + " " + count);
            count++;
            setTitle("" + count);
            // ???3???????????????
            mHandler.postDelayed(mRunnable, 3000);  //?????????????????????????????????
        }
    };

    @Override
    protected void onDestroy() {
        //??????????????????
        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }
}