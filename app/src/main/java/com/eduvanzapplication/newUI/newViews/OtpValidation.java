package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCallNew;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpValidation extends AppCompatActivity {

    static Context mContext;
    static EditText editTextRecivedOtp;
    static ProgressBar progressBar;
    TextView textViewToolbar, textMobileNo, textViewResend;
    MainApplication mainApplication;
    static Button button, buttonSkip;
    LinearLayout linearLayoutResendOtp;
    String mobileNO = "", userID="";
    AppCompatActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_validation);
        mActivity = this;
        mContext = this;
        mainApplication = new MainApplication();

        try {
            Bundle bundle = getIntent().getExtras();
            mobileNO = bundle.getString("mobile_no");
        } catch (Exception e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("logged_id", "null");

        progressBar = (ProgressBar) findViewById(R.id.progressBar_otpvalidation);

        textViewToolbar = (TextView) findViewById(R.id.textView_validateotp);
        mainApplication.applyTypefaceBold(textViewToolbar, mContext);

        editTextRecivedOtp = (EditText) findViewById(R.id.editText_recievedOtp);
        textMobileNo = (TextView) findViewById(R.id.textView_mobileNo_otpValidation);
        textMobileNo.setText(mobileNO);

        button = (Button) findViewById(R.id.button_continue_validateotp);
        mainApplication.applyTypefaceBold(button, mContext);

        buttonSkip = (Button) findViewById(R.id.button_skip);
        mainApplication.applyTypefaceBold(buttonSkip, mContext);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** API CALL GET OTP**/
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    String url = MainApplication.mainUrl + "pqform/thirdPartyVerifyOtpCode";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobileno", textMobileNo.getText().toString());
                    params.put("otpcode", editTextRecivedOtp.getText().toString());
                    params.put("studentId", userID);
                    VolleyCallNew volleyCall = new VolleyCallNew();
                    volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "setOtpValidation", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        buttonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpValidation.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textViewResend = (TextView) findViewById(R.id.textView_resendOtp);
        textViewResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** API CALL GET OTP**/

                try {
                    progressBar.setVisibility(View.VISIBLE);
                    String url = MainApplication.mainUrl + "pqform/thirdPartyGenerateOtpCode";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobileno", textMobileNo.getText().toString());
                    if(!Globle.isNetworkAvailable(OtpValidation.this))
                    {
                        Toast.makeText(OtpValidation.this, "Please check your network connection", Toast.LENGTH_SHORT).show();

                    } else {
                        VolleyCallNew volleyCall = new VolleyCallNew();
                        volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "getOtpValidation", params);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        linearLayoutResendOtp = (LinearLayout) findViewById(R.id.linearLayout_resendOtp);
        linearLayoutResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(OtpValidation.this, GetMobileNo.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("mobile_no_val", textMobileNo.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setOtp(String otp) {
        progressBar.setVisibility(View.GONE);
        editTextRecivedOtp.setText(otp);
    }

    public void getOTPValidation(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                progressBar.setVisibility(View.GONE);
                int otpsendcount = jsonData.getInt("otpSentCount");
                if(otpsendcount == 3){
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    button.setVisibility(View.GONE);
                    buttonSkip.setVisibility(View.VISIBLE);
                }
            } else {
                progressBar.setVisibility(View.GONE);
                Log.e(MainApplication.TAG, "getOTPValidation: ");
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setOTPValidation(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                progressBar.setVisibility(View.GONE);

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("otp_done", "1");
                editor.putString("mobile_no", mobileNO);
                editor.apply();
                editor.commit();

                Intent intent = new Intent(OtpValidation.this, DashboardActivity.class);
                startActivity(intent);
                finish();

            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("otp_done", "0");
                editor.putString("mobile_no", mobileNO);
                editor.apply();
                editor.commit();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(OtpValidation.this, DashboardActivity.class);
//                startActivity(intent);
//                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
