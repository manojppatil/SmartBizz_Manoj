package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
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

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.SharedPref;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreen extends AppCompatActivity {
    ImageView  imageViewCustomer;
    View view1;
    TextView textViewCustomer;
    Thread splashTread;
    RelativeLayout relativeLayoutCustomer;
    String checkOTPDone="";
    SharedPref sharedPref = new SharedPref();
    static Context context;
    int policyAgreementStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view1 = getLayoutInflater().inflate(R.layout.activity_splash_screen_customer, null);
        setContentView(view1);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR
        context = this;

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        checkOTPDone = sharedPreferences.getString("otp_done","0");
        policyAgreementStatus = sharedPreferences.getInt("userpolicyAgreement", 0);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            View decorView = getWindow().getDecorView();
            // Hide Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

            imageViewCustomer = (ImageView) findViewById(R.id.splash_image);
//            imageViewCustomer.setAlpha(80);
            textViewCustomer = (TextView) findViewById(R.id.splash_text);
            relativeLayoutCustomer = (RelativeLayout) findViewById(R.id.rel_lay);
            StartAnimationsCustomer();

    printHashKey(getApplicationContext());
        
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

                    if(checkOTPDone.equalsIgnoreCase("1")){
                        Intent intent = new Intent(SplashScreen.this,
                                DashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        SplashScreen.this.finish();
                    }else {
                        if (policyAgreementStatus == 0){
                            Intent intent = new Intent(SplashScreen.this,
                                    TermsAndCondition.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            SplashScreen.this.finish();
                        }else {

                            Intent intent = new Intent(SplashScreen.this,
                                    SingInWithTruecaller.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                            SplashScreen.this.finish();

//                            Intent intent = new Intent(SplashScreen.this,
//                                    GetMobileNo.class);
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
}
