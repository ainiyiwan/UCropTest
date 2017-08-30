package com.zhimadai.cctvmall.ucroptest;

/**
 * Author ： zhangyang
 * Date   ： 2017/4/18
 * Email  :  18610942105@163.com
 * Description  :
 */

public class ClickUtil {
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
