package com.eduvanzapplication.newUI.newViews;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
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
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.webviews.VolleyCallLogin;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OtpValidation extends AppCompatActivity {

    static Context mContext;
    static EditText editTextRecivedOtp;
    static ProgressBar progressBar;
    TextView textViewToolbar, textMobileNo, textViewResend;
    MainApplication mainApplication;
    static Button button, buttonSkip;
    LinearLayout linearLayoutResendOtp;
    String mobileNO = "", userID="",emailId ="",firstName ="";
    AppCompatActivity mActivity;
    private int batterylevel = 0;
    private int scale = 0;
    private String network_provider_0 = "";
    private String network_provider_1 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
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
            try {
                Bundle bundle = getIntent().getExtras();
                emailId = bundle.getString("emailId");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Bundle bundle = getIntent().getExtras();
                firstName = bundle.getString("firstName");
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

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = OtpValidation.this.registerReceiver(null, ifilter);

            batterylevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            SubscriptionManager subscriptionManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                subscriptionManager = (SubscriptionManager) OtpValidation.this.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            }

            List<SubscriptionInfo> subscriptionInfoList = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();

                try {
                    network_provider_0 = String.valueOf(subscriptionInfoList.get(0).getDisplayName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    network_provider_0 = network_provider_0 + "_" + subscriptionInfoList.get(0).getSimSlotIndex();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    network_provider_1 = String.valueOf(subscriptionInfoList.get(1).getDisplayName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    network_provider_1 = network_provider_1 + "_" + subscriptionInfoList.get(1).getSimSlotIndex();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /** API CALL GET OTP**/
                    try {//auth_token
                        progressBar.setVisibility(View.VISIBLE);
                        String url = MainApplication.mainUrl + "authorization/verifyOtpCode";
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("mobileno", textMobileNo.getText().toString());
                        params.put("otpcode", editTextRecivedOtp.getText().toString());
                        params.put("email", emailId);
                        params.put("name", firstName);
//                        params.put("studentId", userID);
                        VolleyCallNew volleyCall = new VolleyCallNew();
                        volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "setOtpValidation", params, MainApplication.auth_token);
                    } catch (Exception e) {
                        String className = this.getClass().getSimpleName();
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        String errorMsg = e.getMessage();
                        String errorMsgDetails = e.getStackTrace().toString();
                        String errorLine = String.valueOf(e.getStackTrace()[0]);
                        Globle.ErrorLog(OtpValidation.this,className, name, errorMsg, errorMsgDetails, errorLine);
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
                        String url = MainApplication.mainUrl + "authorization/generateOtpCode";//http://192.168.0.110/eduvanzapi/pqform/thirdPartyGenerateOtpCode
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("mobileno", textMobileNo.getText().toString());
                        params.put("name", firstName);
                        params.put("email", emailId);
                        if(!Globle.isNetworkAvailable(OtpValidation.this))
                        {//{"auth_token":{"userName":"8983501513","auth_token":"4c0565ca540403684c520cf9f6976b56"},"result":[],"status":1,"message":"OTP Successfully Verified"}
                            Toast.makeText(OtpValidation.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                        } else {
                            VolleyCallLogin volleyCall = new VolleyCallLogin();
                            volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "getOtpValidation", params);
                        }
                    } catch (Exception e) {
                        String className = this.getClass().getSimpleName();
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        String errorMsg = e.getMessage();
                        String errorMsgDetails = e.getStackTrace().toString();
                        String errorLine = String.valueOf(e.getStackTrace()[0]);
                        Globle.ErrorLog(OtpValidation.this,className, name, errorMsg, errorMsgDetails, errorLine);
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
                        String className = this.getClass().getSimpleName();
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        String errorMsg = e.getMessage();
                        String errorMsgDetails = e.getStackTrace().toString();
                        String errorLine = String.valueOf(e.getStackTrace()[0]);
                        Globle.ErrorLog(OtpValidation.this,className, name, errorMsg, errorMsgDetails, errorLine);
                    }
                }
            });

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(OtpValidation.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
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
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(OtpValidation.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void setOTPValidation(JSONObject jsonData) {
        try {//{"auth_token":{"userName":"8983501513","auth_token":"90a876cf5617b74f1e034c6561669803"},"student_id":null,"lead_id":null,"status":1,"message":"OTP Successfully Verified"}
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");
            String userName = new JSONObject(jsonData.optString("auth_token")).getString("userName");
            String auth_token = new JSONObject(jsonData.optString("auth_token")).getString("auth_token");
            String student_id = jsonData.optString("student_id");
            String lead_id = jsonData.optString("lead_id");

            if (status.equalsIgnoreCase("1")) {
                progressBar.setVisibility(View.GONE);

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("otp_done", "1");
                editor.putString("mobile_no", mobileNO);
                editor.putString("userName", userName);
                editor.putString("auth_token", auth_token);
                editor.putString("student_id", student_id);
                editor.putString("lead_id", lead_id);
                editor.apply();
                editor.commit();

//                Intent intent = new Intent(OtpValidation.this, DashboardActivity.class);
//                startActivity(intent);
//                finish();
                setOTPData();

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
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(OtpValidation.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void setOTPData() {  //{"message":"update mandate","is_update_mandate":1}
        try {
            String verification = "null";
            String url = MainApplication.mainUrl + "truecallerresponse/insert";//http://192.168.0.110/eduvanzapi/pqform/thirdPartyGenerateOtpCode
            Map<String, String> params = new HashMap<String, String>();

            params.put("avatarUrl", "");
            params.put("city", "");
            params.put("companyName", "");
            params.put("countryCode", "");
            params.put("email", "");
            params.put("facebookId", "");
            params.put("firstName", "");
            params.put("gender", "");
            params.put("isAmbassador", "");
            params.put("isSimChanged","");
            params.put("isTrueName", "");
            params.put("jobTitle", "");
            params.put("lastName", "");
            params.put("payload", "");
            params.put("phoneNumber", mobileNO);
            params.put("requestNonce","" );
            params.put("signature", "");
            params.put("street", "");
            params.put("twitterId", "");
            params.put("url", "");

            params.put("verificationMode", "Verified by Eduvanz OTP");
            params.put("verificationTimestamp", "");
            params.put("zipcode", "");

            String appVersion ="",deviceVersion ="",device ="";
            try {
                PackageManager manager = OtpValidation.this.getPackageManager();
                PackageInfo info = manager.getPackageInfo(
                        OtpValidation.this.getPackageName(), 0);
                appVersion = info.versionName;
                deviceVersion = String.valueOf(Build.VERSION.SDK_INT);
                device = String.valueOf(Build.MODEL);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            String ipaddress ="";
            try {
                ipaddress = Utils.getIPAddress(true);
            } catch (Exception e) {
                String className = this.getClass().getSimpleName();
                String name = new Object() {
                }.getClass().getEnclosingMethod().getName();
                String errorMsg = e.getMessage();
                String errorMsgDetails = e.getStackTrace().toString();
                String errorLine = String.valueOf(e.getStackTrace()[0]);
                Globle.ErrorLog(OtpValidation.this,className, name, errorMsg, errorMsgDetails, errorLine);
            }

            params.put("handset", device);
            params.put("network_provider", network_provider_0 + "," + network_provider_1);
            params.put("signal_strength", "");
            params.put("battery_strength", String.valueOf(batterylevel));
            params.put("last_connected_tower", "");
            params.put("wifi_connected_network", "");
            params.put("app_version", appVersion);
            params.put("device_version", deviceVersion);
            params.put("ip_address", ipaddress);

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("mobile_no", mobileNO);
            editor.apply();
            editor.commit();

            if (!Globle.isNetworkAvailable(OtpValidation.this)) {
                Toast.makeText(OtpValidation.this, getText(R.string.please_check_your_network_connection), Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(OtpValidation.this, url, mActivity, null, "updateOTPData", params, MainApplication.auth_token);
            }

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(OtpValidation.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void UpdateOTPData() {  //{"message":"update mandate","is_update_mandate":1}
        try {

            Intent intent = new Intent(OtpValidation.this, DashboardActivity.class);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(OtpValidation.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

}
