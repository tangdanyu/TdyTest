package com.example.tdytest.activity;

import static android.os.Build.VERSION_CODES.P;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;
import com.example.tdytest.activity.DialogActivity;
import com.example.tdytest.activity.TestActivity;
/**
 * 生命周期相关
 */
/**
 * 启动MainActivity生命周期:onCreate->onStart->onResume
 * MainActivity按返回键:onPause->onStop->onDestroy
 * MainActivity跳转到TestActivity:MainActivity onPause TestActivity onCreate->onStart->onResume -> MainActivity:onStop
 * MainActivity跳转到TestActivity按返回键：TestActivity:onPause ->MainActivity：onRestart->onStart->onResume ->TestActivity:onStop->onDestroy
 * MainActivity到最近任务或者到手机主界面：onPause->onStop
 * MainActivity从最近任务返回或者从主界面图标进入：onRestart->onStart->onResume
 * MainActivity横屏跳转到TestActivity按返回键：MainActivity onDestroy->onCreate->onStart->onResume TestActivity onStop->onDestroy
 * MainActivity横竖屏切换：onPause->onStop->onDestroy->onCreate->onStart->onResume
 * MainActivity横竖屏切换(android:configChanges="orientation")：同上
 * MainActivity横竖屏切换(android:configChanges="orientation|screenSize")：TargetSdkVersion>=13,没有生命周期回调
 * 锁屏:onPause->onStop
 * 解锁屏幕:onRestart->onStart->onResume
 * 启动Theme为Dialog的Activity：MainActivity onPause -> DialogActivity onCreate->onStart->onResume
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


public class LifeActivity extends BaseActivity implements View.OnClickListener {

    private Button startActBtn;
    private Button startDialogBtn;
    private Button startDialogActBtn;


    @Override
    public int getLayoutId() {
        return R.layout.activity_life;
    }

    @Override
    public void initView() {
        startActBtn = findViewById(R.id.btn_startActivity);
        startDialogBtn = findViewById(R.id.btn_startDialog);
        startDialogActBtn = findViewById(R.id.btn_startDialogActivity);
    }
    @Override
    @TargetApi( P)
    public void initListener() {
        startActBtn.setOnClickListener(this);
        startDialogBtn.setOnClickListener(this);
        startDialogActBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_startActivity:
                startActivity(new Intent(this, TestActivity.class));
                break;
            case R.id.btn_startDialog:
                AlertDialog alertDialog  = new AlertDialog.Builder(this)
                        .setTitle("这是标题")
                        .setMessage("有多个按钮")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(  LifeActivity.this, "这是确定按钮", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(LifeActivity.this, "这是取消按钮", Toast.LENGTH_SHORT).show();
                            }
                        })

                        .create();
                alertDialog.show();

                break;
            case R.id.btn_startDialogActivity:
                startActivity(new Intent(this, DialogActivity.class));
                break;
        }
    }
}