package com.zhimadai.cctvmall.ucroptest;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class MyDialogAdapter extends BaseQuickAdapter<AuthBottomDlgEntity, BaseViewHolder> {

    public MyDialogAdapter(int layoutResId, List<AuthBottomDlgEntity> data) {
        super(layoutResId, data);


    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateViewHolder(parent, viewType);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, AuthBottomDlgEntity entity) {
        baseViewHolder.setText(R.id.tvName, entity.name);
//        baseViewHolder.setVisible(R.id.ivRight, entity.isCheck);
        baseViewHolder.getConvertView().setClickable(true);
    }
}
