package com.eduvanz.newUI.newViews;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;
import com.eduvanz.newUI.VolleyCallNew;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class  GetMobileNo extends AppCompatActivity {

    TextView textViewToolbar;
    MainApplication mainApplication;
    static Context mContext;
    Button button;
    static EditText editTextGetMobileNo;
    AppCompatActivity mActivity;
    String mobileNO="";
    public int GET_MY_PERMISSION = 1, permission, permission1, permission2, permission3,
            permission4, permission5, permission6, policyAgreementStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_mobile_no);
        mActivity = this;
        mContext = this;
        mainApplication = new MainApplication();

        try {
            Bundle bundle = getIntent().getExtras();
            mobileNO = bundle.getString("mobile_no_val", "");
        }catch (Exception e){
            e.printStackTrace();
        }

        textViewToolbar = (TextView) findViewById(R.id.textView_getmobileno);
        mainApplication.applyTypefaceBold(textViewToolbar, mContext);

        button = (Button) findViewById(R.id.button_getotp);
        mainApplication.applyTypeface(button, mContext);

        editTextGetMobileNo = (EditText) findViewById(R.id.editText_getmobileno);
        editTextGetMobileNo.setText(mobileNO);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editTextGetMobileNo.getText().toString().equalsIgnoreCase("")){
                    if (Build.VERSION.SDK_INT >= 23)
                    {
                        permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.READ_SMS);

                        permission2 = ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.READ_CONTACTS);

                        permission3 = ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.READ_EXTERNAL_STORAGE);

                        permission4 = ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.READ_PHONE_STATE);

                        permission5 = ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE);

                        permission6 = ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION);


                        if (permission != PackageManager.PERMISSION_GRANTED ||
                                permission2 != PackageManager.PERMISSION_GRANTED ||
                                permission3 != PackageManager.PERMISSION_GRANTED ||
                                permission4 != PackageManager.PERMISSION_GRANTED ||
                                permission5 != PackageManager.PERMISSION_GRANTED ||
                                permission6 != PackageManager.PERMISSION_GRANTED ) {
//                            makeRequest();

                            AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                            } else {
                                builder = new AlertDialog.Builder(mContext);
                            }
                            builder.setTitle("Disclaimer")
                                    .setMessage("Dear Student, \n" +
                                            "This app will access your mobile details like contacts and SMS to calculate your eligibility and give faster loans. Incase if you are comfortable with the same press ok else cancel")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            ActivityCompat.requestPermissions(GetMobileNo.this,
                                                    new String[]{Manifest.permission.READ_CONTACTS,
                                                            Manifest.permission.READ_SMS,
                                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                                            Manifest.permission.READ_PHONE_STATE,
                                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                            Manifest.permission.ACCESS_FINE_LOCATION},
                                                    GET_MY_PERMISSION);

                                        }
                                    })
                                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();

                        } else {
                            apiCall();
                        }
                    }
                    else
                    {
                        apiCall();
                    }
            }else {
                editTextGetMobileNo.setError("Please Provide Mobile Number");
            }

            }
        });

//        if(policyAgreementStatus == 0){
//            AlertDialog.Builder eulaBuilder = new AlertDialog.Builder(this);
//            LayoutInflater eulaInflater = LayoutInflater.from(this);
//            View tncLayout = eulaInflater.inflate(R.layout.dialoglayout_tnc, null);
//            eulaBuilder.setView(tncLayout);
//            eulaBuilder.setCancelable(false);
//            WebView webView = (WebView) tncLayout.findViewById(R.id.tnc_webview);
//            webView.getSettings().setJavaScriptEnabled(true);
//            webView.loadUrl("http://eduvanz.com/demo/privacy_policy");
//            eulaBuilder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface d, int m) {
//
//                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putInt("userpolicyAgreement", 1);
//                    editor.apply();
//                    editor.commit();
//
//                    // permission for Stats
//                    if(!isAccessGranted()) {
//                        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//                    }
//                }
//            });
//            eulaBuilder.setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface d, int m) {
//                    ((Activity) mContext).finish();
//                }
//            });
//            eulaBuilder.show();
//        }else {
//            // permission for Stats
//            if(!isAccessGranted()) {
//                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//            }
//        }

        // permission for Stats
        if(!isAccessGranted()) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }


    }
    private boolean isAccessGranted() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        applicationInfo.uid, applicationInfo.packageName);
            }
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(GetMobileNo.this,
                new String[]{Manifest.permission.READ_SMS},
                GET_MY_PERMISSION);
    }

    public void apiCall(){
        /** API CALL GET OTP**/

            try {
                String url = MainApplication.mainUrl + "pqform/thirdPartyGenerateOtpCode";
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobileno", editTextGetMobileNo.getText().toString());
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "getOtp", params);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public void getOTP(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Intent intent = new Intent(GetMobileNo.this, OtpValidation.class);
                Bundle bundle = new Bundle();
                bundle.putString("mobile_no", editTextGetMobileNo.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    apiCall();
                } else {
                    //not granted
                    Log.e(MainApplication.TAG, "not granted: Dashboard " + grantResults[0]);
                }
                break;
        }
    }
}
