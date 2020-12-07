package com.zlylib.fileselectorlib.adapter;



import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zlylib.fileselectorlib.R;
import com.zlylib.fileselectorlib.bean.BreadModel;

import java.util.List;

/**
 * BreadAdapter
 * Created by zhangliyang on 2020/6/20.
 */

public class BreadAdapter extends BaseQuickAdapter<BreadModel, BaseViewHolder> {

    public BreadAdapter(@Nullable List<BreadModel> data) {
        super(R.layout.bread_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BreadModel item) {
        helper.setText(R.id.btn_bread,item.getCurName());
    }
}
