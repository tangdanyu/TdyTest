package com.example.tdytest.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;

/**
 * 测试情景一：LaunchModeActivity for循环3次跳转到SingleTopActivity
 * 需要返回三次，每次taskId相同，activity实例不同
 * 测试情景二：重复跳转自己
 * 看起来好像没有点击一样，其实是点击了的，点击了3次，三次的实例和taskId都是没有变化的，且最后退出应用就用了一次点击
 * onPause -> onNewIntent ->onResume
 * 测试情景三：LaunchModeActivity <->SingleTopActivity
 * L->S->L->S,返回两次到L,和standard一样,虽然第二次跳转到SingleTopActivity的时候已经存在了SingleTopActivity实例，可是因为不在栈顶，所以又新建了一个SingleTopActivity实例。
 * 结论：只有在栈顶才不会创建实例，直接复用
 */
public class SingleTopActivity extends BaseActivity implements View.OnClickListener {

    private TextView infoTv;
    private Button startBtn;
    private Button backBtn;
    private int count = 0;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        MyLog.e(TAG,"LaunchMode onNewIntent");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_single_top;
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
                startActivity(new Intent(this, SingleTopActivity.class));
                break;
            case R.id.btn_back:
                startActivity(new Intent(this, LaunchModeActivity.class));
                break;
        }
    }

}