package com.eduvanzapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.newViews.DashboardActivity;
import com.eduvanzapplication.newUI.newViews.GetMobileNo;
import com.eduvanzapplication.newUI.newViews.TermsAndCondition;
import com.viewpagerindicator.CirclePageIndicator;

import java.nio.charset.StandardCharsets;

/**
 * Created by nikhil on 23/9/16.
 */

public class ImageSlider extends AppCompatActivity {
    ViewPager mPager;
    CirclePageIndicator indicator;
    public static int currentPage=0;
    public static  int numPages=0;
    LinearLayout linProceed;
    ImageButton btnNext;
    String checkForImageSlider = "1";
    ImageView imageView1, imageView2;
    SharedPref sharedPref;
    Context context;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_slider);
        context = this;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR

        imageView1 = findViewById(R.id.imageview1);
        imageView2 = findViewById(R.id.imageview2);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("checkForImageSlider",checkForImageSlider);
        editor.commit();

        String[] dataTitle={"Welcome",
                "Details Required"
                ,"Stay Updated"};

        String[] data={"Easy and Hassle-free Education Loans to Help You Grow!",
                "Fill in Your Details with Relevant Documents!"
                ,"Get All Your Loan Related Info at Your fingertips!"};

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR

        int[] mResources = {
                R.drawable.ic_1,
                R.drawable.ic_2,
                R.drawable.ic_3
        };

        int[] mResourcesBg = {
                R.drawable.ic_bg1,
                R.drawable.ic_bg2,
                R.drawable.ic_bg3
        };

        mPager=  findViewById(R.id.Viewpager);
        indicator= findViewById(R.id.indicator);
        btnNext = findViewById(R.id.btnNext);
        linProceed = findViewById(R.id.linProceed);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
            }
        });

        linProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("checkForImageSlider",checkForImageSlider);
                editor.commit();

               sharedPref = new SharedPref();
                if(sharedPref.getLoginDone(ImageSlider.this)){
                    Intent intent = new Intent(ImageSlider.this,DashboardActivity.class);
                    startActivity(intent);
                    finish();

                }else {
//                    Intent intent = new Intent(ImageSlider.this, GetMobileNo.class);
                    Intent intent = new Intent(ImageSlider.this, TermsAndCondition.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        mPager.setAdapter(new SlidingImageAdapter(this,data,dataTitle, mResources,mResourcesBg));
        mPager.setOffscreenPageLimit(0);
        mPager.destroyDrawingCache();
        mPager.clearDisappearingChildren();
        indicator.setViewPager(mPager);


        final float density=getResources().getDisplayMetrics().density;
        indicator.setRadius(5*density);

        byte [] datarec= "865067022375300".getBytes();
        Log.e("data Converted to bytes", "onCreate:"+datarec.toString());
        String s="383635303637303232333735333030F66F00087F";
        String data1=s.substring(0,15);
        Log.e("ImageSlider", "onCreate:substring "+data1 );
        hexStringToByteArray(s);
        byte[] bytes = {0x38,0x36,35,30,36,37,30,32,32,33,37,35,33,30,30};

        try {
            String str = new String(bytes, StandardCharsets.UTF_8); // for UTF-8 encoding
            Log.e("slider", "onCreate string value:"+str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bytesToHex(bytes);
        numPages=3;
        //page Listener and Indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels)
            {
                Log.e("ImageSlider","onPageScrolled"+position);

            }
            @Override
            public void onPageSelected(int position)
            {
                currentPage=position;
                if(currentPage == 2)
                {
                    btnNext.setVisibility(View.GONE);
                    linProceed.setVisibility(View.VISIBLE);
                }
                else{
                    btnNext.setVisibility(View.VISIBLE);
                    linProceed.setVisibility(View.GONE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("ImageSlider","onPageScrollStateChanged"+state);
            }
        });
    }
    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        Log.e("data Converted to bytes", "onCreate:conversion "+hexChars.toString());
//        for (int j = 0; j < bytes.length; j++) {
//            int v = bytes[j] & 0xFF;
//            hexChars[j * 2] = hexArray[v >>> 4];
//            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
//        }
        Log.e("data Converted to bytes", "onCreate:conversion "+hexChars.toString());
        return new String(hexChars);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data2 = new byte[15];

        byte[] data = new byte[len/2];

        for(int i = 0; i < len; i+=2){
            data[i/2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
            Log.e("hexStringToByteArray", "onCreate:conversion "+data[i/2]);

        }
        System.arraycopy(data,0,data2,0,15);
        Log.e("hexStringToByteArray", "onCreate:all data bytes "+data2+"\n"+data);
        String stri= new String(data2, StandardCharsets.UTF_8);
        Log.e("hexStringToByteArray", "onCreate:all data string"+stri);
        return data;
    }
}
