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

import static com.eduvanz.MainApplication.TAG;

public class MyProfileNew extends AppCompatActivity {

    static TextView textViewUserName, textViewPercentage, textView1, textView2, textView3, textView4,
            textView5;
    static ImageView imageViewEligiblityCheck, imageViewMobileVerification, imageViewEmailVerification,
            imageViewSocialScore;
    MainApplication mainApplication;
    static Context context;
    Button buttonCompleteNow;
    AppCompatActivity mActivity;
    String userID="", fname="", lname="",mobile_no="",user_email="";
    public static TextView applicationStatus,applicationStatusName,loanDetailsHeader,personalDetailsHeader,
            institute,instituteName,course,courseName,fees,feesName,loanAmount,loanAmountText,
            mobile,mobileName,email,emailName,address,addressName;
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
            user_email = sharedPreferences.getString("user_email", "--");
            mobile_no = sharedPreferences.getString("mobile_no", "--");
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

        // new View start
        applicationStatus= (TextView) findViewById(R.id.applicationStatus_textview);
        applicationStatusName= (TextView) findViewById(R.id.applicationStatus_name_textview);
        loanDetailsHeader= (TextView) findViewById(R.id.loanDetails_textview);
        personalDetailsHeader= (TextView) findViewById(R.id.personal_details_textview);

        institute= (TextView) findViewById(R.id.institute_textView);
        instituteName= (TextView) findViewById(R.id.institute_name_textview);
        course= (TextView) findViewById(R.id.course_textView);
        courseName= (TextView) findViewById(R.id.course_name_textView);
        fees= (TextView) findViewById(R.id.fee_textView);
        feesName= (TextView) findViewById(R.id.fees_value_textView);
        loanAmount= (TextView) findViewById(R.id.loan_textview);
        loanAmountText= (TextView) findViewById(R.id.loan_value_textview);

        mobile= (TextView) findViewById(R.id.mobile_textView);
        mobileName= (TextView) findViewById(R.id.mobile_name_textview);
        email= (TextView) findViewById(R.id.email_textView);
        emailName= (TextView) findViewById(R.id.email_name_textView);
        address= (TextView) findViewById(R.id.address_textView);
        addressName= (TextView) findViewById(R.id.address_name_textView);

        applicationStatus.setTypeface(MainApplication.typefaceFont);
        applicationStatusName.setTypeface(MainApplication.typefaceFont);
        loanDetailsHeader.setTypeface(MainApplication.typefaceFont);
        personalDetailsHeader.setTypeface(MainApplication.typefaceFont);
        institute.setTypeface(MainApplication.typefaceFont);
        instituteName.setTypeface(MainApplication.typefaceFont);
        course.setTypeface(MainApplication.typefaceFont);
        courseName.setTypeface(MainApplication.typefaceFont);
        fees.setTypeface(MainApplication.typefaceFont);
        feesName.setTypeface(MainApplication.typefaceFont);
        loanAmount.setTypeface(MainApplication.typefaceFont);
        loanAmountText.setTypeface(MainApplication.typefaceFont);
        mobile.setTypeface(MainApplication.typefaceFont);
        mobileName.setTypeface(MainApplication.typefaceFont);
        email.setTypeface(MainApplication.typefaceFont);
        emailName.setTypeface(MainApplication.typefaceFont);
        address.setTypeface(MainApplication.typefaceFont);
        addressName.setTypeface(MainApplication.typefaceFont);

        mobileName.setText("+91 "+mobile_no);
        mobileName.setText(user_email);

// new View end
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
        /** API CALL GET Dates**/
        try {
            String url = MainApplication.mainUrl + "dashboard/getProfileDashbBoardStatus";
            Map<String, String> params = new HashMap<String, String>();
            params.put("studentId",userID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, mActivity, null, "ProfileDashbBoardStatusData", params);
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
    public void setProfileDashbBoardStatusData(JSONObject jsonDataO) {
        Log.e("", "setProfileDashbBoardStatusData: "+jsonDataO );
        Log.e(TAG, "getScrappingdates: "+jsonDataO );
        try {
            if (jsonDataO.getInt("status") == 1) {

                String courseFees = null, addressT = null, loanAmount = null,
                        courseT = null, instituteT = null, applicationStatus = null;
                JSONObject mObject = jsonDataO.optJSONObject("result");
                applicationStatus= mObject.getString("applicationStatus");
                instituteT= mObject.getString("institute");
                courseT= mObject.getString("course");
                loanAmount= mObject.getString("loanAmount");
                courseFees= mObject.getString("courseFees");
                addressT= mObject.getString("address");

                applicationStatusName.setText(applicationStatus);
                institute.setText(instituteT);
                course.setText(courseT);
                addressName.setText(addressT);
                course.setText(courseFees);
                loanAmountText.setText(loanAmount);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
