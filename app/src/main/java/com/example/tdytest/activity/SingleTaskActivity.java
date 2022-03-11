package com.example.tdytest.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;

/**
 * 测试情景一：LaunchModeActivity for循环3次跳转到SingleTaskActivity
 * 需要返回一次，每次taskId相同,调用两次onNewIntent
 * onCreate->onStart->onNewIntent->onNewIntent->onResume
 * 测试情景二：重复跳转自己
 * 看起来好像没有点击一样，其实是点击了的，点击了3次，三次的实例和taskId都是没有变化的，且最后退出应用就用了一次点击
 * onPause -> onNewIntent ->onResume
 * 测试情景三：LaunchModeActivity <->SingleTaskActivity
 * L->S->L->S,两次跳转到SingleTaskActivity，是同一实例，返回一次到最初的LaunchModeActivity,
 * 结论：只要是task中有这个实例，就会一直复用，而且每次复用这个已存在的实例，都会清空上面的其他实例（这里的FirstActivity），将自己直接提升到栈顶位置，显示自己
 */
public class SingleTaskActivity extends BaseActivity implements View.OnClickListener {

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
        return R.layout.activity_single_task;
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
                startActivity(new Intent(this, SingleTaskActivity.class));
                break;
            case R.id.btn_back:
                startActivity(new Intent(this, LaunchModeActivity.class));
                break;
        }
    }

}