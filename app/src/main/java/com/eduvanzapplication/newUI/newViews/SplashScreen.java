package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.eduvanzapplication.ImageSlider;
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.database.DBAdapter;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.VolleyCallNew;

import io.fabric.sdk.android.Fabric;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.eduvanzapplication.database.DBAdapter.ExecuteSql;
import static com.eduvanzapplication.database.DBAdapter.getLocalData;

public class SplashScreen extends AppCompatActivity {
    public ImageView imageViewCustomer;
    View view1;
    public TextView textViewCustomer;
    Thread splashTread;
    public RelativeLayout relativeLayoutCustomer;
    public String checkOTPDone = "", checkForImageSlider = "";
    SharedPref sharedPref = new SharedPref();
    static Context context;
    int policyAgreementStatus;
    AppCompatActivity mActivity;
    static Context mContext;
    private String isMandateToUpdate;
    private String AppLanguage;
    ArrayList<String> errorLogIDArraylist;
    ArrayList<String> appNameArraylist;
    ArrayList<String> appVersionArraylist;
    ArrayList<String> userIDArraylist;
    ArrayList<String> errorDateArraylist;
    ArrayList<String> moduleNameArraylist;
    ArrayList<String> methodNameArraylist;
    ArrayList<String> errorMessageArraylist;
    ArrayList<String> errorMessageDtlsArraylist;
    ArrayList<String> OSVersionArraylist;
    ArrayList<String> IPAddressArraylist;
    ArrayList<String> deviceNameArraylist;
    ArrayList<String> lineNumberArraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        try {
            view1 = getLayoutInflater().inflate(R.layout.activity_splash_screen_customer, null);
            setContentView(view1);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR
            context = this;
            mActivity = this;

            DBAdapter.Init(this);

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            checkOTPDone = sharedPreferences.getString("otp_done", "0");
            policyAgreementStatus = sharedPreferences.getInt("userpolicyAgreement", 0);
            checkForImageSlider = sharedPreferences.getString("checkForImageSlider", "0");

            AppLanguage = sharedPreferences.getString("AppLanguage", "");

            if (Build.VERSION.SDK_INT < 16) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                View decorView = getWindow().getDecorView();
                // Hide Status Bar.
                int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }
            imageViewCustomer = (ImageView) view1.findViewById(R.id.splash_image);
            textViewCustomer = (TextView) view1.findViewById(R.id.splash_text);
            relativeLayoutCustomer = (RelativeLayout) view1.findViewById(R.id.rel_lay);

            Resources res = context.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.setLocale(new Locale(AppLanguage.toLowerCase())); // API 17+ only.

            res.updateConfiguration(conf, dm);

            apiCall();
//            StartAnimationsCustomer();

            //printHashKey(getApplicationContext());

            if (Globle.isNetworkAvailable(SplashScreen.this)) {
                BackgroundThread bv = new BackgroundThread();
                bv.execute(10);
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(SplashScreen.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }

    public void apiCall() {

        try {
            String url = MainActivity.mainUrl + "version/checkVersion";
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String currentAppVersion = packageInfo.versionName;

            Map<String, String> params = new HashMap<String, String>();
            params.put("app_current_version", String.valueOf(currentAppVersion));
            if (!Globle.isNetworkAvailable(SplashScreen.this)) {
                Toast.makeText(SplashScreen.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                StartAnimationsCustomer();

            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "checkVersion", params, MainApplication.auth_token);
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(SplashScreen.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }

    public void CheckVersion(JSONObject jsonData) {  //{"message":"update mandate","is_update_mandate":1}
        try {

            String message = jsonData.optString("message");
            isMandateToUpdate = jsonData.optString("is_update_mandate");

//            JSONObject jsonObject = jsonData.getJSONObject("result");
//                progressBar.setVisibility(View.GONE);

//                updatedVersion = jsonObject.optString("updatedVersion");
//                currentAppVersion = jsonObject.optString("currentAppVersion");
            if (isMandateToUpdate.equals("1")) {
                displayDialogWindow();
            } else {
                StartAnimationsCustomer();
            }

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(SplashScreen.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(MainApplication.TAG, "printHashKey() Hash Key: " + hashKey);//ohu4RrsVF00VGqvz2p9h7r7lJr0=
                //pOtQu4NWTGqMnnkvCK9SJKx36Mc=
                //com.eduvanz ga0RGNYHvNM5d0SLGQfpQWAPGJ8=
                //2jmj7l5rSw0yVb/vlWAYkK/YBwk=
                //ga0RGNYHvNM5d0SLGQfpQWAPGJ8=

            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(MainApplication.TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(MainApplication.TAG, "printHashKey()", e);
        }
    }

    private void StartAnimationsCustomer() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        relativeLayoutCustomer.clearAnimation();
//        relativeLayoutCustomer.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        imageViewCustomer.clearAnimation();
//        imageViewCustomer.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3000) {
                        sleep(100);
                        waited += 100;
                    }

                    if (checkOTPDone.equalsIgnoreCase("1")) {
                        Intent intent = new Intent(SplashScreen.this, DashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        SplashScreen.this.finish();
                    } else {

                        if (checkForImageSlider.equalsIgnoreCase("1")) {
                            if (sharedPref.getLoginDone(SplashScreen.this)) {
                                Intent intent = new Intent(SplashScreen.this, DashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                SplashScreen.this.finish();
                            } else {
                                if (policyAgreementStatus == 0) {
//                                    Intent intent = new Intent(SplashScreen.this, TermsAndCondition.class);
                                    Intent intent = new Intent(SplashScreen.this, DashboardActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                    startActivity(intent);
                                    SplashScreen.this.finish();
                                } else {
//                                    Intent intent = new Intent(SplashScreen.this, GetMobileNo.class);// This is commented for testing
                                    Intent intent = new Intent(SplashScreen.this, DashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        } else {
                            Intent intent = new Intent(SplashScreen.this, ImageSlider.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            SplashScreen.this.finish();
                        }

                    }


                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreen.this.finish();
                }
            }
        };
        splashTread.start();
    }

    public void displayDialogWindow() {
        try {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(SplashScreen.this);
            builder1.setTitle(R.string.app_name);
            builder1.setIcon(R.drawable.eduvanz_logo_new);
            builder1.setMessage(R.string.new_version_is_available_please_update);
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    R.string.lbr_Ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                            }
                            finish();
                        }
                    });

//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(SplashScreen.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void ErrorLogUpdate() {

        try {
            errorLogIDArraylist = new ArrayList<>();
            appNameArraylist = new ArrayList<>();
            appVersionArraylist = new ArrayList<>();
            userIDArraylist = new ArrayList<>();
            errorDateArraylist = new ArrayList<>();
            moduleNameArraylist = new ArrayList<>();
            methodNameArraylist = new ArrayList<>();
            errorMessageArraylist = new ArrayList<>();
            errorMessageDtlsArraylist = new ArrayList<>();
            OSVersionArraylist = new ArrayList<>();
            IPAddressArraylist = new ArrayList<>();
            deviceNameArraylist = new ArrayList<>();
            lineNumberArraylist = new ArrayList<>();

            String sSQL = "select errorLogID, appName, appVersion, userID, errorDate, moduleName, methodName, errorMessage, " +
                    "errorMessageDtls, OSVersion, IPAddress, deviceName, lineNumber, ISSaved, ISUploaded " +
                    "FROM ErrorLog WHERE ISUploaded = 'false'";

            Cursor cursor = getLocalData(context, sSQL);
            if (cursor.getCount() >= 1) {

                if (cursor.moveToFirst()) {
                    do {
                        errorLogIDArraylist.add(cursor.getString(0));
                        appNameArraylist.add(cursor.getString(1));
                        appVersionArraylist.add(cursor.getString(2));
                        userIDArraylist.add(cursor.getString(3));
                        errorDateArraylist.add(cursor.getString(4));
                        moduleNameArraylist.add(cursor.getString(5));
                        methodNameArraylist.add(cursor.getString(6));
                        errorMessageArraylist.add(cursor.getString(7));
                        errorMessageDtlsArraylist.add(cursor.getString(8));
                        OSVersionArraylist.add(cursor.getString(9));
                        IPAddressArraylist.add(cursor.getString(10));
                        deviceNameArraylist.add(cursor.getString(11));
                        lineNumberArraylist.add(cursor.getString(12));

                    }
                    while (cursor.moveToNext());
                    cursor.close();
                }
            }

            String url = MainActivity.mainUrl + "applog/apiErrorLog";

            for (int i = 0; i < errorLogIDArraylist.size(); i++) {
                Map<String, String> params = new HashMap<String, String>();

                params.put("errorLogID", errorLogIDArraylist.get(i));
                params.put("appName", appNameArraylist.get(i));
                params.put("appVersion", appVersionArraylist.get(i));
                params.put("userID", userIDArraylist.get(i));
                params.put("errorDate", errorDateArraylist.get(i));
                params.put("moduleName", moduleNameArraylist.get(i));
                params.put("methodName", methodNameArraylist.get(i));
                params.put("errorMessage", errorMessageArraylist.get(i));
                params.put("errorMessageDtls", errorMessageDtlsArraylist.get(i));
                params.put("OSVersion", OSVersionArraylist.get(i));
                params.put("IPAddress", IPAddressArraylist.get(i));
                params.put("deviceName", deviceNameArraylist.get(i));
                params.put("lineNumber", lineNumberArraylist.get(i));

                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest1(getApplicationContext(), url, mActivity, null, "getUploadErrorLog", params, errorLogIDArraylist.get(i));
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(SplashScreen.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }

    protected class BackgroundThread extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... arg0) {
            try {
                ErrorLogUpdate();
            } catch (Exception e) {
            }
            return null;
        }
    }
}
