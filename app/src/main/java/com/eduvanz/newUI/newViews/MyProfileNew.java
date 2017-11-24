package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.pqformfragments.pojo.LocationsPOJO;
import com.eduvanz.volley.VolleyCallNew;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyProfileNew extends AppCompatActivity {

    static TextView textViewUserName, textViewPercentage, textView1, textView2, textView3, textView4,
            textView5;
    static ImageView imageViewEligiblityCheck, imageViewMobileVerification, imageViewEmailVerification,
            imageViewSocialScore;
    MainApplication mainApplication;
    static Context context;
    Button buttonCompleteNow;
    AppCompatActivity mActivity;
    String userID="", fname="", lname="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_new);
        mainApplication = new MainApplication();
        context = this;
        mActivity = this;

        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            userID = sharedPreferences.getString("logged_id", "");
            fname = sharedPreferences.getString("first_name", "--");
            lname = sharedPreferences.getString("last_name", "--");
        }catch (Exception e){
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        textViewUserName = (TextView) findViewById(R.id.textView_profile_username);
        textViewUserName.setText(fname + " "+ lname);
        mainApplication.applyTypeface(textViewUserName, context);
        textViewPercentage = (TextView) findViewById(R.id.textView_profilecomplete_percentage);
        mainApplication.applyTypeface(textViewPercentage, context);
        textView1 = (TextView) findViewById(R.id.textView_profile1);
        mainApplication.applyTypeface(textView1, context);
        textView2 = (TextView) findViewById(R.id.textView_profile2);
        mainApplication.applyTypeface(textView2, context);
        textView3 = (TextView) findViewById(R.id.textView_profile3);
        mainApplication.applyTypeface(textView3, context);
        textView4 = (TextView) findViewById(R.id.textView_profile4);
        mainApplication.applyTypeface(textView4, context);
        textView5 = (TextView) findViewById(R.id.textView_profile5);
        mainApplication.applyTypeface(textView5, context);

        imageViewEligiblityCheck = (ImageView) findViewById(R.id.imageView_myprofile_eliglibilityCheck);
        imageViewMobileVerification = (ImageView) findViewById(R.id.imageView_myprofile_mobileverification);
        imageViewEmailVerification = (ImageView) findViewById(R.id.imageView_myprofile_emailVerification);
        imageViewSocialScore = (ImageView) findViewById(R.id.imageView_myprofile_socialScore);

        buttonCompleteNow = (Button) findViewById(R.id.button_mtprofile_completenow);
        mainApplication.applyTypeface(buttonCompleteNow, context);

        buttonCompleteNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "dashboard/getProfileDashbBoardStatus";
            Map<String, String> params = new HashMap<String, String>();
            params.put("studentId", userID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, mActivity, null, "myProfile", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    /** RESPONSE OF API CALL**/
    public void myProfile(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "myProfile" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");

                String ec = jsonObject.getString("eligibility");
                String mv = jsonObject.getString("mobileVerified");
                String ev = jsonObject.getString("emailVerified");
                String ss = jsonObject.getString("friendlyScore");

                String profilePercentage = jsonObject.getString("profileDashboardStats");

                textViewPercentage.setText(profilePercentage+"%");

                if(ec.equalsIgnoreCase("1")){
                    imageViewEligiblityCheck.setImageDrawable(getResources().getDrawable(R.drawable.verified));
                }else {
                    imageViewEligiblityCheck.setImageDrawable(getResources().getDrawable(R.drawable.crossbox));
                }

                if(mv.equalsIgnoreCase("1")){
                    imageViewMobileVerification.setImageDrawable(getResources().getDrawable(R.drawable.verified));
                }else {
                    imageViewMobileVerification.setImageDrawable(getResources().getDrawable(R.drawable.crossbox));
                }

                if(ev.equalsIgnoreCase("1")){
                    imageViewEmailVerification.setImageDrawable(getResources().getDrawable(R.drawable.verified));
                }else {
                    imageViewEmailVerification.setImageDrawable(getResources().getDrawable(R.drawable.crossbox));
                }

                if(ss.equalsIgnoreCase("1")){
                    imageViewSocialScore.setImageDrawable(getResources().getDrawable(R.drawable.verified));
                }else {
                    imageViewSocialScore.setImageDrawable(getResources().getDrawable(R.drawable.crossbox));
                }

            }else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
