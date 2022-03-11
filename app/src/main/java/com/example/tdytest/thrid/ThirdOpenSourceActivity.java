package com.example.tdytest.thrid;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;
import com.example.tdytest.thrid.adapter.AdapterDemoActivity;

public class ThirdOpenSourceActivity extends BaseActivity implements View.OnClickListener {
    private Button adapterBtn;
    @Override
    public int getLayoutId() {
        return R.layout.activity_third_open_source;
    }

    @Override
    public void initView() {
        adapterBtn = findViewById(R.id.btn_adapter);
    }

    @Override
    public void initListener() {
        adapterBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_adapter:
                startActivity(new Intent(this, AdapterDemoActivity.class));
                break;
        }
    }
}