package com.eduvanz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class SuccessSplash extends AppCompatActivity {

    Thread splashTread;
    public static TextView textView1, textView2, textView3;
    Typeface typefaceFont, typefaceFontBold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR

        typefaceFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_bold.ttf");

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
        String firstnameshared = sharedPreferences.getString("first_name","null");
        String lastnameshared = sharedPreferences.getString("last_name","null");
        String emailshared = sharedPreferences.getString("user_email","null");
        String userpic = sharedPreferences.getString("user_image","null");

        textView1 = (TextView) findViewById(R.id.textview1_font_ss);
        textView1.setTypeface(typefaceFontBold);
        textView1.setText( "Hi " + firstnameshared + "!");
        textView2 = (TextView) findViewById(R.id.textview2_font_ss);
        textView2.setTypeface(typefaceFont);


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


        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }

                    Intent intent = new Intent(SuccessSplash.this, Main2ActivityNavigation.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("checkfor_eligibility", "0");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    SuccessSplash.this.finish();

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SuccessSplash.this.finish();
                }

            }
        };
        splashTread.start();

    }
}
