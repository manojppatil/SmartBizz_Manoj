package com.smartbizz.Util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class PermissionUtil {
    private static final String TAG = PermissionUtil.class.getSimpleName();

    private PermissionUtil() {
    }

    public static boolean isPermissionGranted(Context context, String permission) {
        try {
            int result = context.checkCallingOrSelfPermission(permission);
            return (result == PackageManager.PERMISSION_GRANTED);
        } catch (Exception e) {
            MyLogger.error(TAG, e);
        }
        return false;
    }

    public static boolean checkPermission(Context context, String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || (ContextCompat.checkSelfPermission(context, permission) == PackageManager
                .PERMISSION_GRANTED);
    }

    public static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static String[] listToArray(List<String> list) {
        if (list != null && !list.isEmpty()) {
            String[] array = new String[list.size()];
            return list.toArray(array);
        }
        return null;
    }

}