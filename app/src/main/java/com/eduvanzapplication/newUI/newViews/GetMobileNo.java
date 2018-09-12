package com.eduvanzapplication.newUI.newViews;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.BuildConfig;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueButton;
import com.truecaller.android.sdk.TrueClient;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//public class  GetMobileNo extends AppCompatActivity implements ITrueCallback {
public class  GetMobileNo extends AppCompatActivity {

//    private TrueClient mTrueClient;
    TextView textViewToolbar,txtnote;
    MainApplication mainApplication;
    static Context mContext;
    Button button,btnedusignin;
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

        textViewToolbar = (TextView) findViewById(R.id.textView_validateotp);
        mainApplication.applyTypefaceBold(textViewToolbar, mContext);

//        btnedusignin = (Button) findViewById(R.id.btnedusignin);
        button = (Button) findViewById(R.id.button_getotp);
        mainApplication.applyTypeface(button, mContext);

//        txtnote = (TextView) findViewById(R.id.txtnote);

        editTextGetMobileNo = (EditText) findViewById(R.id.editText_getmobileno);
        editTextGetMobileNo.setText(mobileNO);

//        TrueButton trueButton = (TrueButton) findViewById(R.id.com_truecaller_android_sdk_truebutton);
//        boolean usable = trueButton.isUsable();

//        if (usable) {
//            mTrueClient = new TrueClient(GetMobileNo.this, this);
//            mTrueClient.setReqNonce("1235678Min");
//            trueButton.setTrueClient(mTrueClient);
//            txtnote.setText(R.string.strgetmobilenonotetruecaller);
//        } else {
//            trueButton.setVisibility(View.GONE);
//            txtnote.setText(R.string.strgetmobilenonote);
//        }

//        btnedusignin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(button.getVisibility() == 0) {
//                    editTextGetMobileNo.setVisibility(View.GONE);
//                    button.setVisibility(View.GONE);
//                }
//                else {
//                    editTextGetMobileNo.setVisibility(View.VISIBLE);
//                    button.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                if(!editTextGetMobileNo.getText().toString().equalsIgnoreCase("")){
                    if (Build.VERSION.SDK_INT >= 23)
                    {
                        permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.READ_SMS);

                        if (permission != PackageManager.PERMISSION_GRANTED)
//                        {//Permission with disclaimer dialog
////                            makeRequest();
//
//                            AlertDialog.Builder builder;
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
//                            } else {
//                                builder = new AlertDialog.Builder(mContext);
//                            }
//                            builder.setTitle("Disclaimer")
//                                    .setMessage("Dear Student, \n" +
//                                            "This app will access your mobile details like contacts and SMS to calculate your eligibility and give faster loans. Incase if you are comfortable with the same press ok else cancel")
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                            ActivityCompat.requestPermissions(GetMobileNo.this,
//                                                    new String[]{Manifest.permission.READ_CONTACTS,
//                                                            Manifest.permission.READ_SMS,
//                                                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                                                            Manifest.permission.READ_PHONE_STATE,
//                                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                                            Manifest.permission.ACCESS_FINE_LOCATION},
//                                                    GET_MY_PERMISSION);
//
//                                        }
//                                    })
//                                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            finish();
//                                        }
//                                    })
//                                    .setIcon(android.R.drawable.ic_dialog_alert)
//                                    .show();
//
//                        }
                        {//Direct Permission without disclaimer dialog
                            ActivityCompat.requestPermissions(GetMobileNo.this,
                                    new String[]{Manifest.permission.READ_CONTACTS,
                                                            Manifest.permission.READ_SMS,
                                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                                            Manifest.permission.READ_PHONE_STATE,
                                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                            Manifest.permission.ACCESS_FINE_LOCATION},
                                                    GET_MY_PERMISSION);

                        }else {
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
             button.setEnabled(false);
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
            AppOpsManager appOpsManager = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            }
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
                if(!Globle.isNetworkAvailable(GetMobileNo.this))
                {
                    Toast.makeText(GetMobileNo.this, "Please check your network connection", Toast.LENGTH_SHORT).show();

                } else {
                    VolleyCallNew volleyCall = new VolleyCallNew();
                    volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "getOtp", params);
                }
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
            button.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 1:
                if (grantResults.length <= 0) {
                }
                else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[4] == PackageManager.PERMISSION_GRANTED && grantResults[5] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    apiCall();
                } else {
                    //not granted
//                    Log.e(MainApplication.TAG, "not granted: Dashboard " + grantResults[0]);
                    {
                        // Permission denied.
                        // Notify the user via a SnackBar that they have rejected a core permission for the
                        // app, which makes the Activity useless. In a real app, core permissions would
                        // typically be best requested during a welcome-screen flow.
                        // Additionally, it is important to remember that a permission might have been
                        // rejected without asking the user for permission (device policy or "Never ask
                        // again" prompts). Therefore, a user interface affordance is typically implemented
                        // when permissions are denied. Otherwise, your app could appear unresponsive to
                        // touches or interactions which have required permissions.
                        //                    Toast.makeText(this, R.string.permission_denied_explanation, Toast.LENGTH_LONG).show();
                        //                    finish();

                        try {
                            button.setEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Snackbar.make(
                                findViewById(R.id.rootViews),
                                R.string.permission_denied_explanation,
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.settings, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Build intent that displays the App settings screen.
                                        Intent intent = new Intent();
                                        intent.setAction(
                                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package",
                                                BuildConfig.APPLICATION_ID, null);
                                        intent.setData(uri);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                }
                break;
        }

    }

//    @Override
//    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
//        if (null != mTrueClient && mTrueClient.onActivityResult(requestCode, resultCode, data)) {
//            return;
//        }
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onSuccesProfileShared(@NonNull TrueProfile trueProfile) {
//
//        StringBuilder sr = new StringBuilder();
//        if (trueProfile.verificationMode != null) {
//            sr.append("Verification mode: ").append(trueProfile.verificationMode).append("\n");
//        }
//        if (trueProfile.verificationTimestamp != 0L) {
//            sr.append("Verification Time: ").append(trueProfile.verificationTimestamp).append("\n");
//        }
//
//        sr.append("Sim changed: ").append(trueProfile.isSimChanged).append("\n");
//        sr.append("RequestNonce: ").append(trueProfile.requestNonce).append("\n");
//
//        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("otp_done", "1");
//        editor.putString("mobile_no", trueProfile.phoneNumber);
//        editor.apply();
//        editor.commit();
//
//        Intent intent = new Intent(GetMobileNo.this, DashboardActivity.class);
//        startActivity(intent);
//        finish();
//
//    }
//
//    @Override
//    public void onFailureProfileShared(@NonNull TrueError trueError) {
//        Toast.makeText(this, "SignIn Failed", Toast.LENGTH_LONG).show();
//
//    }
}
