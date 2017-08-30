package com.zhimadai.cctvmall.ucroptest;

import android.widget.TextView;


public interface Callback<T> {

    void submit(T t);


    interface InputCallback<T> extends Callback<T> {

        void clickRight(DialogHelper.CountDown countDown);
    }


    interface CouponCallback {

        void clickCoupon(TextView coupon, TextView amount);

        void clickPay();

    }

    interface AuthVipCallback {
        void clickCommUser();

        void clickVipUser();
    }
}
