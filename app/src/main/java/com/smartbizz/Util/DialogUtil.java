package com.smartbizz.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.Settings;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import com.smartbizz.R;


public class DialogUtil {
    private static String TAG = DialogUtil.class.getSimpleName();
    private static ProgressDialog progressDialog;

    private DialogUtil() {
    }

    public static void showProgressDialog(Activity activity) {
        showProgressDialog(activity, null);
    }

    public static void showProgressDialog(Activity activity, String message) {
        if (isActivityActive(activity)) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(activity, R.style.StyleProgressDialog);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                if (!TextUtils.isEmpty(message)) {
                    progressDialog.setMessage(message);
                }
                try {
                    progressDialog.show();
                } catch (Exception e) {
                    MyLogger.error(TAG, e);
                }
            }
        }
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                MyLogger.error(TAG, e);
            }
            progressDialog = null;
        }
    }

    private static boolean isActivityActive(Activity activity) {
        return (activity != null && !activity.isFinishing());
    }

    public static void showSettingsDialog(@NonNull final Activity activity) {
        showSettingsDialog(activity, "Please enable all required permissions.");
    }

    public static void showSettingsDialog(final Activity activity, @NonNull String message) {
        if (isActivityActive(activity)) {
            final AlertDialog alertDialog = new AlertDialog.Builder(activity).setTitle("Required permissions are disabled").setMessage(message).setPositiveButton("Open settings", (dialog, whichButton) -> {
                final Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.setData(Uri.parse("package:" + activity.getPackageName()));
                activity.startActivityForResult(i, Constants.RequestCode.CHECK_SETTINGS);
            }).setCancelable(false).create();
            alertDialog.setOnShowListener(dialog -> alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.RED));
            alertDialog.show();
        }
    }
}
