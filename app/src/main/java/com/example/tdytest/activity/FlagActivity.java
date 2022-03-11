package com.example.tdytest.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;

/**
 * `FLAG_ACTIVITY_NEW_TASK` : 对应singleTask启动模式，其效果和在XML中指定该启动模式相同；
 * `FLAG_ACTIVITY_SINGLE_TOP` : 对应singleTop启动模式，其效果和在XML中指定该启动模式相同；
 * `FLAG_ACTIVITY_CLEAR_TOP` : 具有此标记位的Activity，当它启动时，在同一个任务栈中所有位于它上面的Activity都要出栈。
 * `FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS` : 具有这个标记的 Activity 不会出现在历史 Activity 列表中；
 */
public class FlagActivity extends BaseActivity implements View.OnClickListener {

    private TextView infoTv;
    private Button flagActivityNewTaskBtn;
    private Button flagActivitySingleTopBtn;
    private Button flagActivityClearTopBtn;
    private Button flagActivityExcludeFromRecentsSBtn;
    private Button backBtn;
    private int count = 0;
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MyLog.e(TAG,"LaunchMode onNewIntent");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_flag;
    }

    @Override
    public void initView() {
        infoTv = findViewById(R.id.tv_info);
        flagActivityNewTaskBtn = findViewById(R.id.btn_FLAG_ACTIVITY_NEW_TASK);
        flagActivitySingleTopBtn = findViewById(R.id.btn_FLAG_ACTIVITY_SINGLE_TOP);
        flagActivityClearTopBtn = findViewById(R.id.btn_FLAG_ACTIVITY_CLEAR_TOP);
        flagActivityExcludeFromRecentsSBtn = findViewById(R.id.btn_FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        backBtn = findViewById(R.id.btn_back);
    }

    @Override
    public void initListener() {
        flagActivityNewTaskBtn.setOnClickListener(this);
        flagActivitySingleTopBtn.setOnClickListener(this);
        flagActivityClearTopBtn.setOnClickListener(this);
        flagActivityExcludeFromRecentsSBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        MyLog.e(TAG,"LaunchMode TaskId:"+ getTaskId()+"\n activity:"+this.toString());
        infoTv.setText("count:" + count + "\nTaskId:" + getTaskId() + "\nactivity:" + this.toString());
    }

    @Override
    public void onClick(View v) {
        infoTv.setText("count:" + count + "\nTaskId:" + getTaskId() + "\nactivity:" + this.toString());
        switch (v.getId()) {
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
            case R.id.btn_back:
                startActivity(new Intent(this, LaunchModeActivity.class));
                break;
        }
    }

}