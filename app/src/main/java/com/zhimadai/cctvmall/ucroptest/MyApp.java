package com.zhimadai.cctvmall.ucroptest;

import android.app.Application;
import android.content.Context;


public class MyApp extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
