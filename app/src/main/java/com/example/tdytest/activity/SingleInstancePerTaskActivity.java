package com.example.tdytest.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;

/**
 * 测试情景一：LaunchModeActivity for循环3次跳转到SingleInstancePerTaskActivity
 * 需要返回三次， taskId和LaunchModeActivity相同
 * 测试情景二：重复跳转自己
 * 和Standard一样
 * 测试情景三：LaunchModeActivity <->SingleInstanceActivity
 * 和Standard一样
 * 结论：低于Android12，和standard一样；在Android12的环境下，和singleInstance一样。
 */
public class SingleInstancePerTaskActivity extends BaseActivity implements View.OnClickListener {

    private TextView infoTv;
    private Button startBtn;
    private Button backBtn;
    private int count = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_single_instance_per_task;
    }

    @Override
    public void initView() {
        infoTv = findViewById(R.id.tv_info);
        startBtn = findViewById(R.id.btn_start);
        backBtn = findViewById(R.id.btn_back);
    }

    @Override
    public void initListener() {
        startBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        MyLog.e(TAG,"LaunchMode TaskId:"+ getTaskId()+"\n activity:"+this.toString());
        infoTv.setText("count:" + count + "\nTaskId:" + getTaskId() + "\nactivity:" + this.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                count++;
                infoTv.setText("count:" + count + "\nTaskId:" + getTaskId() + "\nactivity:" + this.toString());
                startActivity(new Intent(this, SingleInstancePerTaskActivity.class));
                break;
            case R.id.btn_back:
                startActivity(new Intent(this, LaunchModeActivity.class));
                break;
        }
    }

}