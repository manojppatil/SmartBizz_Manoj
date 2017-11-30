package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.volley.VolleyCallNew;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpValidation extends AppCompatActivity {

    TextView textViewToolbar, textMobileNo, textViewResend;
    MainApplication mainApplication;
    static Context mContext;
    Button button;
    LinearLayout linearLayoutResendOtp;
    static EditText editTextRecivedOtp;
    String mobileNO="";
    AppCompatActivity mActivity;
    static ProgressBar progressBar;

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
        }catch (Exception e){
            e.printStackTrace();
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar_otpvalidation);

        textViewToolbar = (TextView) findViewById(R.id.textView_validateotp);
        mainApplication.applyTypeface(textViewToolbar, mContext);

        editTextRecivedOtp = (EditText) findViewById(R.id.editText_recievedOtp);
        textMobileNo = (TextView) findViewById(R.id.textView_mobileNo_otpValidation);
        textMobileNo.setText(mobileNO);

        button = (Button) findViewById(R.id.button_continue_validateotp);
        mainApplication.applyTypeface(button, mContext);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** API CALL GET OTP**/
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    String url = MainApplication.mainUrl + "authorization/verifyOtpCode";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mobileno", textMobileNo.getText().toString());
                    params.put("otpcode", editTextRecivedOtp.getText().toString());
                    VolleyCallNew volleyCall = new VolleyCallNew();
                    volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "setOtpValidation", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        textViewResend = (TextView) findViewById(R.id.textView_resendOtp);
        textViewResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** API CALL GET OTP**/

                    try {
                        progressBar.setVisibility(View.VISIBLE);
                        String url = MainApplication.mainUrl + "authorization/generateOtpCode";
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("mobileno", textMobileNo.getText().toString());
                        VolleyCallNew volleyCall = new VolleyCallNew();
                        volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "getOtpValidation", params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        });

        linearLayoutResendOtp = (LinearLayout) findViewById(R.id.linearLayout_resendOtp);
        linearLayoutResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpValidation.this, GetMobileNo.class);
                Bundle bundle = new Bundle();
                bundle.putString("mobile_no_val", textMobileNo.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
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
            }else {
                progressBar.setVisibility(View.GONE);
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
                editor.putString("otp_done","1");
                editor.putString("mobile_no", mobileNO);
                editor.apply();
                editor.commit();

                Intent intent = new Intent(OtpValidation.this, DashboardActivity.class);
                startActivity(intent);
                finish();

            }else {
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("otp_done","1");
                editor.putString("mobile_no", mobileNO);
                editor.apply();
                editor.commit();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
