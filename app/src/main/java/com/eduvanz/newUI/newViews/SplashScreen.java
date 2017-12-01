package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eduvanz.R;
import com.eduvanz.newUI.SharedPref;

public class SplashScreen extends AppCompatActivity {
    ImageView  imageViewCustomer;
    View view1;
    TextView textViewCustomer;
    Thread splashTread;
    RelativeLayout relativeLayoutCustomer;
    String checkOTPDone="";
    SharedPref sharedPref = new SharedPref();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view1 = getLayoutInflater().inflate(R.layout.activity_splash_screen_customer, null);
        setContentView(view1);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR
        context = this;

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        checkOTPDone = sharedPreferences.getString("otp_done","0");

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
                        Intent intent = new Intent(SplashScreen.this,
                                GetMobileNo.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        SplashScreen.this.finish();
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
