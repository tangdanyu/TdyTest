package com.example.tdytest.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;

/**
 * 测试情景一：LaunchModeActivity for循环3次跳转到SingleTaskActivity
 * 需要返回一次， taskId和LaunchModeActivity不同
 * 测试情景二：重复跳转自己
 * 看起来好像没有点击一样，其实是点击了的，点击了3次，三次的实例和taskId都是没有变化的，且最后退出应用就用了一次点击
 * onPause ->onResume
 * 测试情景三：LaunchModeActivity <->SingleInstanceActivity
 * 是同一实例，taskId和LaunchModeActivity不同，
 * L->S->L->S,返回一次到SingleInstanceActivity，然后把之前压入栈的FirstActivity全部返回完才退出了应用；
 * L->S->L->S->L，那么会把之前压入栈的LaunchModeActivity全部返回完之后才进入SingleInstanceActivity页面，然后从SingleInstanceActivity一次就能退出应用；
 * 结论：初始时都是新建一个task栈给这个页面，然后后面一直复用这个栈内的页面。注意，只有这种模式会新建一个栈给初始化的页面。最后返回的规律是，先把本页面所在的栈都出完，然后再弹出下一个栈的每个页面。
 */
public class SingleInstanceActivity extends BaseActivity implements View.OnClickListener {

    private TextView infoTv;
    private Button startBtn;
    private Button backBtn;
    private int count = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_single_instance;
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
                startActivity(new Intent(this, SingleInstanceActivity.class));
                break;
            case R.id.btn_back:
                startActivity(new Intent(this, LaunchModeActivity.class));
                break;
        }
    }

}