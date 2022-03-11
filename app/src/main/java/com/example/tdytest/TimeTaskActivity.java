package com.example.tdytest;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.tdytest.service.AlarmService;

public class TimeTaskActivity extends BaseActivity implements View.OnClickListener {

    private Button serviceAlarmBroadcastBtn;
    @Override
    public int getLayoutId() {
        return R.layout.activity_time_task;
    }

    @Override
    public void initView() {
        serviceAlarmBroadcastBtn = findViewById(R.id.btn_service_alarm_broadcast);
    }

    @Override
    public void initListener() {
        serviceAlarmBroadcastBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_service_alarm_broadcast:
                //启动Service
                startService(new Intent(this, AlarmService.class));
                break;
        }
    }
}