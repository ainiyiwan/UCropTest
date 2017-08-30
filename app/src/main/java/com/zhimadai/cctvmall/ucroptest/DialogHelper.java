package com.zhimadai.cctvmall.ucroptest;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;


public class DialogHelper {

    /**
     * 保存分享图片
     */
    public static void showSaveDlg(Context context, int index, final Callback<DlgCallbackEntity> callback) {
        if (ClickUtil.isFastDoubleClick()) return;
        View content = View.inflate(context, R.layout.dlg_auth_personal, null);
        final BottomDialog bottomDialog = new BottomDialog(context, content, true);

        RecyclerView recyclerView = (RecyclerView) content.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new MyDialogAdapter(R.layout.item_dlg_share, DataHelper.getShareData(index)));
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                bottomDialog.dismiss();
                AuthBottomDlgEntity entity = (AuthBottomDlgEntity) baseQuickAdapter.getItem(position);

                DlgCallbackEntity datas = new DlgCallbackEntity();
                datas.content = entity.name;
                datas.index = position;
                callback.submit(datas);
            }
        });


        bottomDialog.show();
    }

    public static class CountDown extends CountDownTimer {

        TextView dynamicText;
        Context context;

        public CountDown(Context context, long millisInFuture, long countDownInterval, TextView dynamicText) {
            super(millisInFuture, countDownInterval);
            this.dynamicText = dynamicText;
            this.context = context.getApplicationContext();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            dynamicText.setClickable(false);
            dynamicText.setText(String.format(context.getString(R.string.verif_second), millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            dynamicText.setClickable(true);
            dynamicText.setText(context.getString(R.string.reget));

        }
    }
}
