package com.smartbizz.newUI.newViews;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.smartbizz.R;
import com.smartbizz.Util.CommonUtil;
import com.smartbizz.Util.Constants;
import com.smartbizz.Util.DialogUtil;
import com.smartbizz.Util.PermissionUtil;
import com.smartbizz.Util.PreferenceManager;
import com.smartbizz.Util.ResourceUtil;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    public Activity activity;
    public static final int READ_WRITE_STORAGE = 52;
    public ProgressDialog mProgressDialog;

    protected String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;

        checkAllPermissions();

    }

    public void makeToast(String message) {
        CommonUtil.makeToast(activity, message);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> activity.finish())
                .setNegativeButton("No", null)
                .show();
    }

    protected void openGooglePlay(){
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +
                    appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google" +
                    ".com/store/apps/details?id=" + appPackageName)));
        }
    }

    public boolean isActivityActive(Activity activity) {
        return (activity != null && !activity.isFinishing());
    }

    protected boolean askRequiredPermissions() {
        boolean isAllPermissionsGranted = true;

        for (String p : permissions) {
            isAllPermissionsGranted &= PermissionUtil.checkPermission(this, p);
            if (!isAllPermissionsGranted)
                break;
        }

        if (!isAllPermissionsGranted) {
            PermissionUtil.requestPermissions(this, permissions, Constants.RequestCode.CHECK_ALL_PERMISSIONS);
        }

        return isAllPermissionsGranted;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkAllPermissions();
    }

    private void checkAllPermissions(){
        if(PreferenceManager.getBoolean(activity, Constants.AppStage.PERMISSIONS_COMPLETED)) {
            if (askRequiredPermissions()) {
                //Do Nothing
            }
        }
    }


    public boolean requestPermission(String permission) {
        boolean isGranted = ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        if (!isGranted) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{permission},
                    READ_WRITE_STORAGE);
        }
        return isGranted;
    }

    public void isPermissionGranted(boolean isGranted, String permission) {

    }

    public void makeFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case READ_WRITE_STORAGE:
//                isPermissionGranted(grantResults[0] == PackageManager.PERMISSION_GRANTED, permissions[0]);
//                break;
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case READ_WRITE_STORAGE:
                isPermissionGranted(grantResults[0] == PackageManager.PERMISSION_GRANTED, permissions[0]);
                break;
        }

        if (grantResults.length > 0) {

            boolean allPermissionsSuccess = true;
            final List<String> failedPermissions = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsSuccess = false;
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                        failedPermissions.add(permissions[i]);
                    }
                }
            }

            if (!failedPermissions.isEmpty()) {
                PermissionUtil.requestPermissions(activity,
                        PermissionUtil.listToArray(failedPermissions),
                        Constants.RequestCode.CHECK_ALL_PERMISSIONS);
            } else if (!allPermissionsSuccess) {
                DialogUtil.showSettingsDialog(activity);
            } else {
                //Do Nothing
            }
        }
    }

    public void showLoading(@NonNull String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void showSnackbar(@NonNull String message) {
        View view = findViewById(android.R.id.content);
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    protected void validField(View view){
        TextView label = findViewById(ResourceUtil.getErrorView(activity, view));
        TextView error = findViewById(ResourceUtil.getErrorView(activity, label));
        label.setTextColor(ContextCompat.getColor(activity, R.color.colorSubTitle));
        error.setVisibility(View.GONE);
    }

    protected void inValidField(View view){
        TextView label = findViewById(ResourceUtil.getErrorView(activity, view));
        TextView error = findViewById(ResourceUtil.getErrorView(activity, label));
        label.setTextColor(ContextCompat.getColor(activity, R.color.colorInvalid));
        error.setVisibility(View.VISIBLE);
    }

    protected void inValidField(View view, String message){
        TextView label = findViewById(ResourceUtil.getErrorView(activity, view));
        TextView error = findViewById(ResourceUtil.getErrorView(activity, label));
        label.setTextColor(ContextCompat.getColor(activity, R.color.colorInvalid));
        error.setVisibility(View.VISIBLE);
        error.setText(message);
    }

    protected void inValidField(TextView label, TextView error){
        label.setTextColor(ContextCompat.getColor(activity, R.color.colorInvalid));
        error.setVisibility(View.VISIBLE);
    }

    protected void inValidField(TextView label, TextView error, String errorMessage){
        label.setTextColor(ContextCompat.getColor(activity, R.color.colorInvalid));
        error.setVisibility(View.VISIBLE);
        error.setText(errorMessage);
    }

    protected void validField(TextView label, TextView error){
        label.setTextColor(ContextCompat.getColor(activity, R.color.colorSubTitle));
        error.setVisibility(View.GONE);
    }

}
