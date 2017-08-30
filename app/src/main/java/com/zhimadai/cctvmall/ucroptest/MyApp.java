package com.zhimadai.cctvmall.ucroptest;

import android.app.Application;
import android.content.Context;

/**
 * Author ： zhangyang
 * Date   ： 2017/8/28
 * Email  :  18610942105@163.com
 * Description  :
 */

public class MyApp extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
