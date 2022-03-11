package com.example.tdytest.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;

/**
 * 测试情景一：LaunchModeActivity for循环3次跳转到StandardActivity
 * 需要返回三次，每次taskId相同，activity实例不同
 * 测试情景二：重复跳转自己
 * 点击三次start,每次taskId相同，activity信息不同，需要返回三次
 * 结论：standard模式下，会不断地新建activity实例，都放入同一个task中
 */

public class StandardActivity extends BaseActivity implements View.OnClickListener {

    private TextView infoTv;
    private Button startBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_standard;
    }

    @Override
    public void initView() {
        infoTv = findViewById(R.id.tv_info);
        startBtn = findViewById(R.id.btn_start);
    }

    @Override
    public void initListener() {
        startBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        MyLog.e(TAG,"LaunchMode TaskId:"+ getTaskId()+"\n activity:"+this.toString());
        infoTv.setText("TaskId:"+ getTaskId()+"\nactivity:"+this.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                infoTv.setText("TaskId:"+ getTaskId()+"\nactivity:"+this.toString());
                startActivity(new Intent(this,StandardActivity.class));
                break;
        }
    }
}