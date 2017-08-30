package com.zhimadai.cctvmall.ucroptest.util;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.zhimadai.cctvmall.ucroptest.R;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Author:BinarySatan
 * Time: 2017/1/13
 */

public class PerUtils {
    public static void somePermissionPermanentlyDenied(@NonNull Activity activity, @NonNull List<String> list) {
        if (EasyPermissions.somePermissionPermanentlyDenied(activity, list)) {
            new AppSettingsDialog.Builder(activity, activity.getString(R.string.per_ask_agein))
                    .setTitle(activity.getString(R.string.per_rejected))
                    .setPositiveButton(activity.getString(R.string.per_go_now))
                    .setNegativeButton(activity.getString(R.string.cancels), null /* click listener */)
                    .setRequestCode(100)
                    .build()
                    .show();
        }
    }
}
