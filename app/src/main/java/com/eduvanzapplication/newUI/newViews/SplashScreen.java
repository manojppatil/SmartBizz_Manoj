package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCallNew;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {
    ImageView imageViewCustomer;
    View view1;
    TextView textViewCustomer;
    Thread splashTread;
    RelativeLayout relativeLayoutCustomer;
    String checkOTPDone = "";
    SharedPref sharedPref = new SharedPref();
    static Context context;
    int policyAgreementStatus;
    AppCompatActivity mActivity;
    static Context mContext;
    private String isMandateToUpdate;
    private String updatedVersion;
    private String currentAppVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view1 = getLayoutInflater().inflate(R.layout.activity_splash_screen_customer, null);
        setContentView(view1);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR
        context = this;
        mActivity = this;

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        checkOTPDone = sharedPreferences.getString("otp_done", "0");
        policyAgreementStatus = sharedPreferences.getInt("userpolicyAgreement", 0);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        imageViewCustomer = (ImageView) findViewById(R.id.splash_image);
//            imageViewCustomer.setAlpha(80);
        textViewCustomer = (TextView) findViewById(R.id.splash_text);
        relativeLayoutCustomer = (RelativeLayout) findViewById(R.id.rel_lay);

        apiCall();

//        StartAnimationsCustomer();
//        displayDialogWindow();


        //printHashKey(getApplicationContext());

    }

    public void apiCall() {

        try {
            String url = MainApplication.mainUrl + "version/checkVersion";
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String currentAppVersion = packageInfo.versionName;

            Map<String, String> params = new HashMap<String, String>();
            params.put("app_current_version", String.valueOf(currentAppVersion));
            if (!Globle.isNetworkAvailable(SplashScreen.this)) {
                Toast.makeText(SplashScreen.this, "Please check your network connection", Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "checkVersion", params);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
                        Intent intent = new Intent(SplashScreen.this,
                                DashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        SplashScreen.this.finish();
                    } else {
                        if (policyAgreementStatus == 0) {
                            Intent intent = new Intent(SplashScreen.this,
                                    TermsAndCondition.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            SplashScreen.this.finish();
                        } else {

                            Intent intent = new Intent(SplashScreen.this,
                                    SingInWithTruecaller.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            SplashScreen.this.finish();
//
//                            Intent intent = new Intent(SplashScreen.this,
//                                    DashboardActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            startActivity(intent);
//                            SplashScreen.this.finish();
                        }

                    }

//                    if(checkForImageSlider.equalsIgnoreCase("1")) {
//                        if(sharedPref.getLoginDone(SplashScreen.this)) {
//                            Intent intent = new Intent(SplashScreen.this,
//                                    Main2ActivityNavigation.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            startActivity(intent);
//                            SplashScreen.this.finish();
//                        }
//                        else {
//                            Intent intent = new Intent(SplashScreen.this, SignupLogin.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    }else {
//                        Intent intent = new Intent(SplashScreen.this,
//                                ImageSlider.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                        startActivity(intent);
//                        SplashScreen.this.finish();
//                    }

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
            builder1.setMessage("New version is available , please update");
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

        }
    }
}
