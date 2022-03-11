package com.example.tdytest;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.tdytest.activity.LaunchModeActivity;
import com.example.tdytest.activity.LifeActivity;
import com.example.tdytest.service.ServiceActivity;
import com.example.tdytest.thrid.ThirdOpenSourceActivity;

/**
 * 启动MainActivity生命周期:onCreate->onStart->onResume
 * MainActivity按返回键:onPause->onStop->onDestroy
 * MainActivity跳转到TestActivity:MainActivity onPause TestActivity onCreate->onStart->onResume MainActivity:onStop
 * MainActivity跳转到TestActivity按返回键：MainActivity：onRestart->onStart->onResume TestActivity:onStop->onDestroy
 * MainActivity到最近任务或者到手机主界面：onPause->onStop
 * MainActivity从最近任务返回或者从主界面图标进入：onRestart->onStart->onResume
 * MainActivity横屏跳转到TestActivity按返回键：MainActivity onDestroy->onCreate->onStart->onResume TestActivity onStop->onDestroy
 * MainActivity横竖屏切换：onPause->onStop->onDestroy->onCreate->onStart->onResume
 * MainActivity横竖屏切换(android:configChanges="orientation")：同上
 * MainActivity横竖屏切换(android:configChanges="orientation|screenSize")：TargetSdkVersion>=13,没有生命周期回调
 * 锁屏:onPause->onStop
 * 解锁屏幕:onRestart->onStart->onResume
 * 启动Theme为Dialog的Activity：MainActivity onPause DialogActivity onCreate->onStart->onResume
 * 销毁Theme为Dialog的Activity：DialogActivity onPause MainActivity onResume DialogActivity onStop->onDestroy
 */

/**
 * onSaveInstanceState调用
 * MainActivity跳转到TestActivity:MainActivity onPause TestActivity onCreate->onStart->onResume MainActivity:onStop->onSaveInstanceState
 * MainActivity到最近任务或者到手机主界面：onPause->onStop->onSaveInstanceState
 * MainActivity横竖屏切换：onPause->onStop->onSaveInstanceState->onDestroy->onCreate->onStart->onRestoreInstanceState->onResume
 * MainActivity横竖屏切换(设置android:configChanges="orientation")：onPause->onStop->onSaveInstanceState->onDestroy->onCreate->onStart->onRestoreInstanceState->onResume
 * MainActivity横竖屏切换(设置android:configChanges="orientation|screenSize")：TargetSdkVersion>=13,没有生命周期回调
 * MainActivity横屏跳转到TestActivity按返回键：MainActivity onDestroy->onCreate->onStart->onRestoreInstanceState->onResume TestActivity onStop->onDestroy
 * 锁屏:onPause->onStop->onSaveInstanceState
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button lifeBtn;
    private Button launchModeBtn;
    private Button handlerBtn;
    private Button threadBtn;
    private Button thirdOpenSourceBtn;
    private Button timeTaskBtn;
    private Button serviceBtn;
    private Button broadcastBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        lifeBtn = findViewById(R.id.btn_life);
        launchModeBtn = findViewById(R.id.btn_launch_mode);
        handlerBtn = findViewById(R.id.btn_handler);
        threadBtn = findViewById(R.id.btn_thread);
        thirdOpenSourceBtn = findViewById(R.id.btn_third_open_source);
        timeTaskBtn = findViewById(R.id.btn_time_task);
        serviceBtn = findViewById(R.id.btn_service);
        broadcastBtn = findViewById(R.id.btn_broadcast);
    }
    @Override
    public void initListener() {
        lifeBtn.setOnClickListener(this);
        launchModeBtn.setOnClickListener(this);
        handlerBtn.setOnClickListener(this);
        threadBtn.setOnClickListener(this);
        thirdOpenSourceBtn.setOnClickListener(this);
        timeTaskBtn.setOnClickListener(this);
        serviceBtn.setOnClickListener(this);
        broadcastBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_life:
                startActivity(new Intent(this, LifeActivity.class));
                break;
            case R.id.btn_launch_mode:
                startActivity(new Intent(this, LaunchModeActivity.class));
                break;
            case R.id.btn_handler:
                startActivity(new Intent(this, HandlerActivity.class));
                break;
            case R.id.btn_thread:
                startActivity(new Intent(this, ThreadActivity.class));
                break;
            case R.id.btn_third_open_source:
                startActivity(new Intent(this, ThirdOpenSourceActivity.class));
                break;
            case R.id.btn_time_task:
                startActivity(new Intent(this, TimeTaskActivity.class));
                break;
            case R.id.btn_service:
                startActivity(new Intent(this, ServiceActivity.class));
                break;
            case R.id.btn_broadcast:
                startActivity(new Intent(this, ServiceActivity.class));
                break;
        }
    }
}