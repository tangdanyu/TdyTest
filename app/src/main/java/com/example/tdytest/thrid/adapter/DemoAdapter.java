package com.example.tdytest.thrid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.tdytest.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DemoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    /**
     * 构造方法，此示例中，在实例化Adapter时就传入了一个List。
     * 如果后期设置数据，不需要传入初始List，直接调用 super(layoutResId); 即可
     */
    public DemoAdapter( List<String> list) {
        super(R.layout.item_adapter_demo, list);
    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull String item) {
        helper.setText(R.id.tv_name, item);
    }
}