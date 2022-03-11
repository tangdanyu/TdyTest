package com.example.tdytest.activity;

import android.view.View;
import android.widget.Button;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;

/**
 * 主题是 Dialog 的Activity
 * ActivityA的onPause-> ActivityB的onCreate->onStart->onResume
 * 按返回键：ActivityB的onPause->ActivityA的onResume->Activity B的onStop->onDestroy
 */
public class DialogActivity extends BaseActivity implements View.OnClickListener{

    private Button btnYes;
    private Button btnNo;
    @Override
    public int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    public void initView(){
        btnYes= findViewById(R.id.btn_y);
        btnNo= findViewById(R.id.btn_n);
    }
    @Override
    public void initListener(){
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_y:
            case R.id.btn_n:
                finish();
                break;
        }
    }
}