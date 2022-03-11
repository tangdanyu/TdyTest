package com.example.tdytest.thrid.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tdytest.BaseActivity;
import com.example.tdytest.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterDemoActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private DemoAdapter adapter;
    private List<String> nameList = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_adapter_demo;
    }

    @Override
    public void initView() {
        recyclerView = findViewById(R.id.v_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        adapter = new DemoAdapter(nameList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        nameList.add("张三");
        nameList.add("李四");
        nameList.add("hello");
        nameList.add("world");
        adapter.setList(nameList);
    }
}