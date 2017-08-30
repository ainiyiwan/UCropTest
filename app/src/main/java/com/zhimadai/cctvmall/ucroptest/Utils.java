package com.zhimadai.cctvmall.ucroptest;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;

import java.util.List;

/**
 * Datae: 2016/8/25
 * Description:
 */
public class Utils {
    public static Context _context;


    public static Context getContext() {
        return _context;
    }

    /**
     * 是否是主线程
     *
     * @return true 主线程,
     */
    public static boolean isMainThread() {
        return getContext().getMainLooper() == Looper.myLooper();
    }

    /**
     * @param context
     * @param dipValue
     * @return
     * @Description: dp转px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * @return null may be returned if the specified process not found
     */
    private static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == android.os.Process.myPid()) {
                return procInfo.processName;
            }
        }
        return null;
    }

    public static boolean inUIProcess(Context context) {
        return TextUtils.equals(context.getPackageName(), getProcessName(context));
    }
}
