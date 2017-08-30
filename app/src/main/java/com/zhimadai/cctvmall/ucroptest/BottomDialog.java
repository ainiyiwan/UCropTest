package com.zhimadai.cctvmall.ucroptest;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * Desc   :
 * <p/>
 * Author ： BinarySatan
 * Date   ： 2016/11/1
 * Email  :  450778776@qq.com
 */
public class BottomDialog extends Dialog {
    private final Context mContext;
    private final View mContentView;
    private final int mWindowHeight;

    public BottomDialog(Context context, View contentView, boolean cancelable) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        this.mContentView = contentView;
        this.mWindowHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        setCanceledOnTouchOutside(cancelable);
        setContentView(mContentView);
    }
    public BottomDialog(Context context, View contentView, boolean cancelable, int windowHeight) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        this.mContentView = contentView;
        this.mWindowHeight = windowHeight;
        setCanceledOnTouchOutside(cancelable);
        setContentView(mContentView);
    }

    public void dismiss() {
        super.dismiss();
    }

    public void show() {
        if (isShowing()) {
            dismiss();
        }
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.commDialogAnim); //设置窗口弹出动画
            window.setBackgroundDrawableResource(android.R.color.transparent); //设置对话框背景为透明

            WindowManager.LayoutParams wl = window.getAttributes();
            //窗口需要显示的位置
            wl.gravity = Gravity.BOTTOM;
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = mWindowHeight;
            window.setAttributes(wl);
        }
        super.show();
    }

    /**
     * 菜单构造器
     */
    private static class Builder {
        private Context context;
        private boolean cancelable;
        private View contentView;
        private int windowHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder setWindowHeight(int windowHeight) {
            this.windowHeight = windowHeight;
            return this;
        }

        /**
         * Create the custom dialog
         */
        public BottomDialog create() {
            return new BottomDialog(context, contentView, cancelable, windowHeight);
        }

        public BottomDialog show() {
            BottomDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
