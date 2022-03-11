package com.example.tdytest.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;

public class LaunchModeActivity extends BaseActivity implements View.OnClickListener {

    private TextView infoTv;
    private Button standardBtn;
    private Button singleTopBtn;
    private Button singleTaskBtn;
    private Button singleInstanceBtn;
    private Button singleInstancePerTaskBtn;
    private Button flagActivityNewTaskBtn;
    private Button flagActivitySingleTopBtn;
    private Button flagActivityClearTopBtn;
    private Button flagActivityExcludeFromRecentsSBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_lauchmode;
    }

    @Override
    public void initView() {
        infoTv = findViewById(R.id.tv_info);
        standardBtn = findViewById(R.id.btn_standard);
        singleTopBtn = findViewById(R.id.btn_singleTop);
        singleTaskBtn = findViewById(R.id.btn_singleTask);
        singleInstanceBtn = findViewById(R.id.btn_singleInstance);
        singleInstancePerTaskBtn = findViewById(R.id.btn_singleInstancePerTask);
        flagActivityNewTaskBtn = findViewById(R.id.btn_FLAG_ACTIVITY_NEW_TASK);
        flagActivitySingleTopBtn = findViewById(R.id.btn_FLAG_ACTIVITY_SINGLE_TOP);
        flagActivityClearTopBtn = findViewById(R.id.btn_FLAG_ACTIVITY_CLEAR_TOP);
        flagActivityExcludeFromRecentsSBtn = findViewById(R.id.btn_FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
    }

    @Override
    public void initListener() {
        standardBtn.setOnClickListener(this);
        singleTopBtn.setOnClickListener(this);
        singleTaskBtn.setOnClickListener(this);
        singleInstanceBtn.setOnClickListener(this);
        singleInstancePerTaskBtn.setOnClickListener(this);
        flagActivityNewTaskBtn.setOnClickListener(this);
        flagActivitySingleTopBtn.setOnClickListener(this);
        flagActivityClearTopBtn.setOnClickListener(this);
        flagActivityExcludeFromRecentsSBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        MyLog.e(TAG,"LaunchMode TaskId:"+ getTaskId()+"\n activity:"+this.toString());
        infoTv.setText( "TaskId:" + getTaskId() + "\nactivity:" + this.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_standard:
                for(int i = 0; i<3;i++) {
                    startActivity(new Intent(this, StandardActivity.class));
                }
                break;
            case R.id.btn_singleTop:
//                for(int i = 0; i<3;i++) {
                    startActivity(new Intent(this, SingleTopActivity.class));
//                }
                break;
            case R.id.btn_singleTask:
                for(int i = 0; i<3;i++) {
                    startActivity(new Intent(this, SingleTaskActivity.class));
                }
                break;
            case R.id.btn_singleInstance:
                for(int i = 0; i<3;i++) {
                    startActivity(new Intent(this, SingleInstanceActivity.class));
                }
                break;
            case R.id.btn_singleInstancePerTask:
//                for(int i = 0; i<3;i++) {
                    startActivity(new Intent(this, SingleInstancePerTaskActivity.class));
//                }
                break;
            case R.id.btn_FLAG_ACTIVITY_NEW_TASK:
                Intent intentNewTask = new Intent();
                intentNewTask.setClass(this,FlagActivity.class);
                intentNewTask.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentNewTask);
                break;
            case R.id.btn_FLAG_ACTIVITY_SINGLE_TOP:
                Intent intentSingleTop = new Intent();
                intentSingleTop.setClass(this,FlagActivity.class);
                intentSingleTop.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intentSingleTop);
                break;
            case R.id.btn_FLAG_ACTIVITY_CLEAR_TOP:
                Intent intentClearTop = new Intent();
                intentClearTop.setClass(this,FlagActivity.class);
                intentClearTop.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentClearTop);
                break;
            case R.id.btn_FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS:
                Intent intent = new Intent();
                intent.setClass(this,FlagActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
                break;
        }
    }
}