package com.example.tdytest.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

/**
 * IntentService子类，用于在单独的处理程序线程上处理服务中的异步任务请求。
 */
public class MyIntentService extends IntentService {
    private String TAG = "MyIntentService";

    // 重命名操作，选择描述此任务的操作名称
    private static final String ACTION_FOO = "com.example.tdytest.service.action.FOO";
    private static final String ACTION_BAZ = "com.example.tdytest.service.action.BAZ";

    // 重命名参数
    private static final String EXTRA_PARAM1 = "com.example.tdytest.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.tdytest.service.extra.PARAM2";

    public MyIntentService() {
        super("MyIntentService");
        Log.e(TAG,"MyIntentService");
    }

    /**
     * 启动此服务以使用给定参数执行操作Foo。如果服务已在执行任务，则此操作将排队。
     */
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * 启动此服务以使用给定参数执行操作Baz。如果服务已在执行任务，则此操作将排队。
     */
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG,"onHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * 使用提供的参数在提供的后台线程中处理操作Foo。
     */
    private void handleActionFoo(String param1, String param2) {
        try {
            Thread.sleep(5000);
            Log.e(TAG,"handleActionFoo param1="+param1+" param2="+param2);
            Log.e(TAG,"handleActionFoo--" + Thread.currentThread().getName() + "--"+Thread.currentThread().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 使用提供的参数在提供的后台线程中处理操作Baz。
     */
    private void handleActionBaz(String param1, String param2) {
        Log.e(TAG,"handleActionBaz param1="+param1+" param2="+param2);
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}