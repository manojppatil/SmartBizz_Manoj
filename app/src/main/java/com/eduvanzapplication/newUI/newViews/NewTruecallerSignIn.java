///**
// * True SDK Copyright notice and License
// * <p/>
// * Copyright(c)2015-present,True Software Scandinavia AB.All rights reserved.
// * <p/>
// * In accordance with the separate agreement executed between You and True Software Scandinavia AB You are granted a
// * limited,non-exclusive,
// * non-sublicensable,non-transferrable,royalty-free,license to use the True SDK Product in object code form only,
// * solely for the purpose of using the
// * True SDK Product with the applications and API’s provided by True Software Scandinavia AB.
// * <p/>
// * THE TRUE SDK PRODUCT IS PROVIDED WITHOUT WARRANTY OF ANY KIND,EXPRESS OR IMPLIED,INCLUDING BUT NOT LIMITED TO THE
// * WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE,SOFTWARE QUALITY,PERFORMANCE,DATA ACCURACY AND NON-INFRINGEMENT.IN NO EVENT SHALL
// * THE AUTHORS OR COPYRIGHT HOLDERS
// * BE LIABLE FOR ANY CLAIM,DAMAGES OR OTHER LIABILITY,WHETHER IN AN ACTION OF CONTRACT,TORT OR OTHERWISE,ARISING
// * FROM,OUT OF OR IN CONNECTION WITH THE
// * TRUE SDK PRODUCT OR THE USE OR OTHER DEALINGS IN THE TRUE SDK PRODUCT.AS A RESULT,THE TRUE SDK PRODUCT IS
// * PROVIDED"AS IS"AND BY INTEGRATING THE TRUE
// * SDK PRODUCT YOU ARE ASSUMING THE ENTIRE RISK AS TO ITS QUALITY AND PERFORMANCE.
// **/
//package com.eduvanzapplication.newUI.newViews;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.AppOpsManager;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.content.SharedPreferences;
//import android.content.pm.ApplicationInfo;
//import android.content.pm.PackageInfo;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.BatteryManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.provider.Settings;
//import android.provider.Telephony;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.support.design.widget.Snackbar;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.telephony.SmsMessage;
//import android.telephony.SubscriptionInfo;
//import android.telephony.SubscriptionManager;
//import android.telephony.TelephonyManager;
//import android.text.InputFilter;
//import android.text.TextUtils;
//import android.util.Log;
//import android.util.Pair;
//import android.view.View;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.eduvanzapplication.BuildConfig;
//import com.eduvanzapplication.R;
//import com.eduvanzapplication.Util.Globle;
//import com.eduvanzapplication.Util.SmsUtils;
//import com.eduvanzapplication.Utils;
//import com.eduvanzapplication.newUI.MainApplication;
//import com.eduvanzapplication.newUI.VolleyCallNew;
//import com.github.lzyzsd.circleprogress.DonutProgress;
//import com.truecaller.android.sdk.ITrueCallback;
//import com.truecaller.android.sdk.TrueButton;
//import com.truecaller.android.sdk.TrueError;
//import com.truecaller.android.sdk.TrueException;
//import com.truecaller.android.sdk.TrueProfile;
//import com.truecaller.android.sdk.TrueSDK;
//import com.truecaller.android.sdk.TrueSdkScope;
//import com.truecaller.android.sdk.clients.VerificationCallback;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static vijay.createpdf.util.Constants.PREVIEW_IMAGES;
//
//public class NewTruecallerSignIn extends AppCompatActivity {
//
//    private static final String TAG = "SignInActivity";
//    private static final int REQUEST_PHONE = 0;
//    private static final int REQUEST_SMS = 2;
//
//    //constants for layouts
//    private static final int LANDING_LAYOUT = 1;
//    private static final int PROFILE_LAYOUT = 2;
//    private static final int LOADER_LAYOUT = 3;
//    private static final int FORM_LAYOUT = 4;
//
//    static Context mContext;
//    AppCompatActivity mActivity;
//    public int GET_MY_PERMISSION = 1, GET_MY_PERMISSION10 = 10, permission;
//    private BroadcastReceiver mTokenReceiver;
//    private int verificationCallbackType;
//    public String verificationtype = "Verified by Truecaller SDK";
//    private int batterylevel = 0;
//    public int scale = 0;
//    public String network_provider_0 = "";
//    public String network_provider_1 = "";
//    private final ITrueCallback sdkCallback = new ITrueCallback() {
//        @Override
//        public void onSuccessProfileShared(@NonNull final TrueProfile trueProfile) {
//            try {
//                countDownTimer1.cancel();
//                progressBar.setVisibility(View.GONE);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
////            Toast.makeText(NewTruecallerSignIn.this, "Verified without " + getViaText() + " ! (Truecaller User): " + trueProfile.firstName,
////                    Toast.LENGTH_SHORT).show();
////            showLayout(LANDING_LAYOUT);
//            setData(trueProfile);
//        }
//
//        @SuppressLint("WrongConstant")
//        @Override
//        public void onFailureProfileShared(@NonNull final TrueError trueError) {
//            try {
//                countDownTimer1.cancel();
//                progressBar.setVisibility(View.GONE);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (trueError.getErrorType() == 14) {
//                showLayout(FORM_LAYOUT);
//                initTrueSdk();
//            } else {
//                Toast.makeText(NewTruecallerSignIn.this, "onFailureProfileShared: " + trueError.getErrorType(), Toast
//                        .LENGTH_SHORT).show();
//                Intent intent = new Intent(NewTruecallerSignIn.this, GetMobileNo.class);
//                intent.putExtra("mPhoneField", mPhoneField.getText().toString().trim());
//                startActivity(intent);
//                finish();
//            }
//        }
//
//        @Override
//        public void onVerificationRequired() {
//            showLayout(FORM_LAYOUT);
//            findViewById(R.id.btnProceed).setOnClickListener(proceedClickListener);
////            if (ContextCompat.checkSelfPermission(MySignInActivity.this, Manifest.permission.READ_PHONE_STATE) ==
////                    PackageManager.PERMISSION_GRANTED) {
////                TelephonyManager systemService = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
////                String number = systemService.getLine1Number();
////                if (!TextUtils.isEmpty(number)) {
////                    Pair<String, String> phoneNumber = PhoneUtils.processPhoneNumber(number);
////                    ((EditText) findViewById(R.id.edtPhone)).setText(phoneNumber.second);
////                } else {
////                    Toast.makeText(MySignInActivity.this, "Couldn't auto fill the number", Toast.LENGTH_SHORT).show();
////                }
////            }
//        }
//    };
//
//    private final CountDownTimer countDownTimer = new CountDownTimer(240000, 1000) {
//
//        public void onTick(long millisUntilFinished) {
//            ((Button) findViewById(R.id.btnVerify)).setText("Verify (" + millisUntilFinished / 1000 + ")");
//        }
//
//        public void onFinish() {
//            ((Button) findViewById(R.id.btnVerify)).setText("Retry");
//            ((Button) findViewById(R.id.btnVerify)).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View view) {
//                    showLayout(FORM_LAYOUT);
//                }
//            });
//
//        }
//    };
//
//    private final CountDownTimer countDownTimer1 = new CountDownTimer(60000, 1000) {
////        int i = 100;
//
//        public void onTick(long millisUntilFinished) {
//            int timeremaining = (int) (millisUntilFinished / 1000);
////            i = i - 2;
////            if(i <= 0)
////            {
////                countDownTimer1.onFinish();
////            }
////            progressBar.setProgress(i);
//
//            if (timeremaining <= 0) {
//                countDownTimer1.onFinish();
//            }
//            progressBar.setProgress(timeremaining);
//        }
//
//        public void onFinish() {
//            try {
//                countDownTimer1.cancel();
//                progressBar.setVisibility(View.GONE);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Intent intent = new Intent(NewTruecallerSignIn.this, GetMobileNo.class);
//            intent.putExtra("mPhoneField", mPhoneField.getText().toString().trim());
//            startActivity(intent);
//            finish();
//
//        }
//    };
//
////    private final CountDownTimer countDownTimer1 = new CountDownTimer(60000, 1000) {
////
////        public void onTick(long millisUntilFinished) {
////            int timeremaining = (int) (millisUntilFinished / 1000);
////
//////            progressBar.setVisibility(View.VISIBLE);
////            progressBar.setProgress(timeremaining);
////
//////            ((ProgressBar) findViewById(R.id.progress_bar)).setVisibility(View.VISIBLE);
//////            ((ProgressBar) findViewById(R.id.progress_bar)).setProgress(timeremaining);
////        }
////
////        public void onFinish() {
////            try {
////                countDownTimer1.cancel();
////                progressBar.setVisibility(View.GONE);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////            Intent intent = new Intent(NewTruecallerSignIn.this, GetMobileNo.class);
////            intent.putExtra("mPhoneField", mPhoneField.getText().toString().trim());
////            startActivity(intent);
////            finish();
////
////        }
////    };
//
//    private final VerificationCallback apiCallback = new VerificationCallback() {
//
//        @Override
//        public void onRequestSuccess(final int requestCode, @Nullable String accessToken) {
//            if (requestCode == VerificationCallback.TYPE_MISSED_CALL || requestCode == VerificationCallback.TYPE_OTP) {
//                try {
//                    countDownTimer1.cancel();
//                    progressBar.setVisibility(View.GONE);
////                    ((ProgressBar) findViewById(R.id.progress_bar)).setVisibility(View.GONE);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
////                showLayout(PROFILE_LAYOUT);
////                findViewById(R.id.btnVerify).setOnClickListener(verifyClickListener);
////                Globle.appendLog("VerificationCallback onRequestSuccess if " + requestCode +" : " + getViaText());
//
//                Log.i(TAG, "VerificationCallback onRequestSuccess if" + requestCode + " : " + getViaText());
//
//                //Without showing click button verify missed call
//                try {
//
//                    verificationtype = "Verified by Truecaller Missed Call";
//                    final TrueProfile profile = new TrueProfile.Builder("FirstName", "LastName").build();
//
//                    TrueSDK.getInstance().verifyMissedCall(profile, apiCallback);
//                    Log.i(TAG, "verifyClickListener else " + profile.firstName + " : " + profile.verificationMode + " : " + profile.phoneNumber);
////                Globle.appendLog("verifyClickListener else " + profile.firstName +" : " + profile.verificationMode +" : " + profile.phoneNumber);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            } else {//accessToken =a1o0c--9gZXn_-RkL_ch5MSCAAYZNGPtkmTkX3akwrg0lpPr2JnvjAnAb4kvogTU
//                Toast.makeText(NewTruecallerSignIn.this,
//                        "Success: Verified with" + getViaText() + " with " + accessToken, Toast.LENGTH_SHORT).show();
////                showLayout(LANDING_LAYOUT);
//                Log.i(TAG, "VerificationCallback onRequestSuccess else" + requestCode + " : " + getViaText());
////                Globle.appendLog("VerificationCallback onRequestSuccess else " + requestCode +" : " + getViaText());
//                if(verificationCallbackType == 2) {
//                    verificationtype = "Verified by Truecaller Missed Call";
//                }else if(verificationCallbackType == 3)
//                {
//                    verificationtype = "Pre Verified by Truecaller SDK";
//                }
//                else if(verificationCallbackType == 1){
//                    verificationtype = "Verified by Truecaller OTP";
//                }
//                setData();
//            }
//        }
//
//        @Override
//        public void onRequestFailure(final int requestCode, @NonNull final TrueException e) {
//            try {
//                countDownTimer1.cancel();
//                progressBar.setVisibility(View.GONE);
////                ((ProgressBar) findViewById(R.id.progress_bar)).setVisibility(View.GONE);
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//            Toast.makeText(NewTruecallerSignIn.this, "OnFailureApiCallback : " + e.getExceptionMessage(),
//                    Toast.LENGTH_SHORT).show();
//            showLayout(FORM_LAYOUT);
//            Log.i(TAG, "VerificationCallback onRequestFailure " + e.getExceptionMessage());
////            Globle.appendLog("VerificationCallback onRequestFailure " + e.getExceptionMessage());
//            Intent intent = new Intent(NewTruecallerSignIn.this, GetMobileNo.class);
//            intent.putExtra("mPhoneField", mPhoneField.getText().toString().trim());
//            startActivity(intent);
//            finish();
//
//        }
//    };
//
//    @NonNull
//    private String getViaText() {
//        String viaText = "Unknown";
//        if (verificationCallbackType == VerificationCallback.TYPE_OTP) {
//            viaText = "OTP";
//        } else if (verificationCallbackType == VerificationCallback.TYPE_MISSED_CALL) {
//            viaText = "MISSED CALL";
//        }
//        return viaText;
//    }
//
//    //**********Click listeners  *************//
//    private final View.OnClickListener verifyClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(final View view) {
//            String otp = edtOtp.getText().toString().trim();
//            final String firstName = ((EditText) findViewById(R.id.edtFirstName)).getText().toString();
//            final String lastName = ((EditText) findViewById(R.id.edtLastName)).getText().toString();
//            final TrueProfile profile = new TrueProfile.Builder(firstName, lastName).build();
//
//            if (!TextUtils.isEmpty(otp) && verificationCallbackType == VerificationCallback.TYPE_OTP) {
//                try {
//                    otp = otp.substring(0, 6);
//                    showLoader("Verifying profile...", false);
//                    verificationtype = "Verified by Truecaller OTP";
//                    TrueSDK.getInstance().verifyOtp(profile, otp, apiCallback);
//                    Log.i(TAG, "verifyClickListener if " + profile.firstName + " : " + profile.verificationMode + " : " + profile.phoneNumber);
////                Globle.appendLog("verifyClickListener if " + profile.firstName +" : " + profile.verificationMode +" : " + profile.phoneNumber);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            } else {
//                try {
//
//                    verificationtype = "Verified by Truecaller Missed Call";
//                    TrueSDK.getInstance().verifyMissedCall(profile, apiCallback);
//                    Log.i(TAG, "verifyClickListener else " + profile.firstName + " : " + profile.verificationMode + " : " + profile.phoneNumber);
////                Globle.appendLog("verifyClickListener else " + profile.firstName +" : " + profile.verificationMode +" : " + profile.phoneNumber);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//    };
//
//    private final View.OnClickListener startClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(final View view) {
//            try {
//                if (Build.VERSION.SDK_INT >= 23) {
//
//                    permission = ContextCompat.checkSelfPermission(getApplicationContext(),
//                            Manifest.permission.READ_SMS);
//
//                    if (permission != PackageManager.PERMISSION_GRANTED)
//
//                    {//Direct Permission without disclaimer dialog
//                        ActivityCompat.requestPermissions(NewTruecallerSignIn.this,
//                                new String[]{Manifest.permission.READ_CONTACTS,
//                                        Manifest.permission.RECEIVE_SMS,
//                                        Manifest.permission.READ_SMS,
//                                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                                        Manifest.permission.READ_PHONE_STATE,
//                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                        Manifest.permission.ACCESS_FINE_LOCATION},
//                                GET_MY_PERMISSION);
//
//                    } else {
//                        trueButton.callOnClick();
//                    }
//                } else {
//                    trueButton.callOnClick();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    private final View.OnClickListener proceedClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(final View view) {
//            try {
//                if (Build.VERSION.SDK_INT >= 23) {
//
//                    permission = ContextCompat.checkSelfPermission(getApplicationContext(),
//                            Manifest.permission.READ_SMS);
//
//                    if (permission != PackageManager.PERMISSION_GRANTED)
//
//                    {//Direct Permission without disclaimer dialog
//                        ActivityCompat.requestPermissions(NewTruecallerSignIn.this,
//                                new String[]{Manifest.permission.READ_CONTACTS,
//                                        Manifest.permission.RECEIVE_SMS,
//                                        Manifest.permission.READ_SMS,
//                                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                                        Manifest.permission.READ_PHONE_STATE,
//                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                        Manifest.permission.ACCESS_FINE_LOCATION,
//                                        Manifest.permission.READ_CALL_LOG},
//                                GET_MY_PERMISSION10);
//
//                    } else {
//                        if ((ActivityCompat.checkSelfPermission(NewTruecallerSignIn.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
//                                || ActivityCompat.checkSelfPermission(NewTruecallerSignIn.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
//                                || !isAnswerCallPermissionEnabled()) {
//                            requestPhonePermission();
//                        } else {
//                            if(((EditText)findViewById(R.id.edtPhone)).getText().length() == 10) {
//                                verificationCallbackType = VerificationCallback.TYPE_MISSED_CALL;
//                                requestVerification();
//                            }
//                            else{
//                                Toast.makeText(NewTruecallerSignIn.this, "Please enter valid mobile number", Toast.LENGTH_LONG).show();
//                            }
//                        }
////             checkPhonePermission();
////            checkSMSPermission();
//                    }
//                } else {
//                    if ((ActivityCompat.checkSelfPermission(NewTruecallerSignIn.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
//                            || ActivityCompat.checkSelfPermission(NewTruecallerSignIn.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
//                            || !isAnswerCallPermissionEnabled()) {
//                        requestPhonePermission();
//                    } else {
//                        if(((EditText)findViewById(R.id.edtPhone)).getText().length() == 10) {
//                            verificationCallbackType = VerificationCallback.TYPE_MISSED_CALL;
//                            requestVerification();
//                        }
//                        else{
//                            Toast.makeText(NewTruecallerSignIn.this, "Please enter valid mobile number", Toast.LENGTH_LONG).show();
//                        }
//                    }
////            checkPhonePermission();
////            checkSMSPermission();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    private EditText edtOtp;
//    private TrueButton trueButton;
//    private EditText mPhoneField;
//    //    private ProgressBar progressBar;
//    private DonutProgress progressBar;
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_new_truecaller);
//
//        mActivity = this;
//        mContext = this;
//
//        edtOtp = findViewById(R.id.edtOtpCode);
//        mPhoneField = findViewById(R.id.edtPhone);
//        trueButton = findViewById(R.id.com_truecaller_android_sdk_truebutton);
//        progressBar = ((DonutProgress) findViewById(R.id.progress_bar));
//
//        progressBar.setMax(60);
//        progressBar.setFinishedStrokeColor(Color.parseColor("#EE415E"));
//        progressBar.setTextColor(Color.parseColor("#EE415E"));
//        progressBar.setKeepScreenOn(true);
//        showLayout(LANDING_LAYOUT);
//
//        findViewById(R.id.btnStart).setOnClickListener(startClickListener);
//
//        initTrueSdkIfPresent();
//        boolean usable = TrueSDK.getInstance().isUsable();
//        if (!usable) {
//            showLayout(FORM_LAYOUT);
//            initTrueSdk();
//        }
//
//
//        if (!isAccessGranted()) {
//            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//        }
//
//        System.out.println("phone permission " + (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager
//                .PERMISSION_GRANTED));
//        System.out.println("log permission " + (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager
//                .PERMISSION_GRANTED));
//        System.out.println("answer call permission " + isAnswerCallPermissionEnabled());
//    }
//
//    private void initTrueSdk() {
//
//        TrueSdkScope trueScope = new TrueSdkScope.Builder(this, sdkCallback)
//                .sdkOptions(TrueSdkScope.SDK_OPTION_WITH_OTP)             //SDK_OPTION_WITH_OTP //SDK_OPTION_WIHTOUT_OTP
//                .consentMode(TrueSdkScope.CONSENT_MODE_FULLSCREEN)              //CONSENT_MODE_POPUP //CONSENT_MODE_FULLSCREEN
//                .consentTitleOption(TrueSdkScope.SDK_CONSENT_TITLE_VERIFY)
//                .footerType(TrueSdkScope.FOOTER_TYPE_SKIP)                //FOOTER_TYPE_SKIP  //FOOTER_TYPE_CONTINUE
//                .build();
//        TrueSDK.init(trueScope);
//    }
//
//    private void initTrueSdkIfPresent() {
//
//        TrueSdkScope trueScope = new TrueSdkScope.Builder(this, sdkCallback)
//                .sdkOptions(TrueSdkScope.SDK_OPTION_WIHTOUT_OTP)          //SDK_OPTION_WITH_OTP //SDK_OPTION_WIHTOUT_OTP
//                .consentMode(TrueSdkScope.CONSENT_MODE_POPUP)              //CONSENT_MODE_POPUP //CONSENT_MODE_FULLSCREEN
//                .consentTitleOption(TrueSdkScope.SDK_CONSENT_TITLE_VERIFY)
//                .footerType(TrueSdkScope.FOOTER_TYPE_SKIP)                //FOOTER_TYPE_SKIP  //FOOTER_TYPE_CONTINUE
//                .build();
//        TrueSDK.init(trueScope);
//    }
//
//    public void requestVerification() {
//        final String phone = mPhoneField.getText().toString().trim();
//        if (!TextUtils.isEmpty(phone)) {
//            showLoader("Trying " + getViaText() + "...", verificationCallbackType == VerificationCallback.TYPE_MISSED_CALL);
////            showLoader("Trying " + getViaText() + "...", verificationCallbackType == VerificationCallback.TYPE_OTP);
//            try {
//                progressBar.setVisibility(View.VISIBLE);
//                countDownTimer1.start();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            verificationtype = "Pre Verified by Truecaller SDK";
//
//            TrueSDK.getInstance().requestVerification("IN", phone, apiCallback);
//        }
//    }
//
//    public void checkPhonePermission() {
//        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
//                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
//                || !isAnswerCallPermissionEnabled()) {
//            requestPhonePermission();
//        } else {
//            verificationCallbackType = VerificationCallback.TYPE_MISSED_CALL;
//            requestVerification();
//        }
//    }
//
//    /**
//     * Requests the phone permission.
//     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
//     * permission, otherwise it is requested directly.
//     */
//    private void requestPhonePermission() {
//        Log.i(TAG, "PHONE permission has NOT been granted. Requesting permission.");
//
//        // BEGIN_INCLUDE(phone_permission_request)
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)
//                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALL_LOG)
//                || shouldShowAnswerCallRequestPermissionRationale()) {
//            // Provide an additional rationale to the user if the permission was not granted
//            // and the user would benefit from additional context for the use of the permission.
//            // For example if the user has previously denied the permission.
//            Log.i(TAG,
//                    "Displaying camera permission rationale to provide additional context.");
//            Snackbar.make(findViewById(R.id.activity_landing), "Give permission to identify device.", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Allow", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            requestRequiredPhonePermissions();
//                        }
//                    })
//                    .show();
//        } else {
//            // Phone permission has not been granted yet. Request it directly.
//            requestRequiredPhonePermissions();
//        }
//        // END_INCLUDE(phone_permission_request)
//    }
//
//    public void checkSMSPermission() {
//        // BEGIN_INCLUDE(sms_permission)
//        // Check if the SMS permission is already available.
//        verificationCallbackType = VerificationCallback.TYPE_OTP;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
//            // SMS permission has not been granted.
//            requestSMSPermission();
//        } else {
//            waitForCode();
//            requestVerification();
//        }
//        // END_INCLUDE(SMS_permission)
//    }
//
//    /**
//     * Requests the SMS permission.
//     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
//     * permission, otherwise it is requested directly.
//     */
//    private void requestSMSPermission() {
//        Log.i(TAG, "SMS permission has NOT been granted. Requesting permission.");
//
//        // BEGIN_INCLUDE(sms_permission_request)
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.READ_SMS)) {
//            // Provide an additional rationale to the user if the permission was not granted
//            // and the user would benefit from additional context for the use of the permission.
//            // For example if the user has previously denied the permission.
//            Log.i(TAG,
//                    "Displaying SMS permission rationale to provide additional context.");
//            Snackbar.make(findViewById(R.id.activity_landing), "Give permission to identify device.",
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Allow", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            ActivityCompat.requestPermissions(NewTruecallerSignIn.this,
//                                    new String[]{Manifest.permission.READ_SMS},
//                                    REQUEST_SMS);
//                        }
//                    })
//                    .show();
//        } else {
//            // SMS permission has not been granted yet. Request it directly.
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS},
//                    REQUEST_SMS);
//        }
//        // END_INCLUDE(sms_permission_request)
//    }
//
//    @Override
//    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        TrueSDK.getInstance().onActivityResultObtained(this, resultCode, data);
//    }
//
//    private void waitForCode() {
//        //SmsUtils.register(this);
//        countDownTimer.start();
//        if (mTokenReceiver == null) {
//            mTokenReceiver = new BroadcastReceiver() {
//                @Override
//                public void onReceive(final Context context, final Intent intent) {
//                    if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
//                        SmsMessage message = SmsUtils.getFirstReceivedMessage(intent);
//                        if (message != null) {
//                            final String token = SmsUtils.getVerificationCode(message.getMessageBody());
//                            if (!TextUtils.isEmpty(token)) {
//                                EditText editText = findViewById(R.id.edtOtpCode);
//                                final String finalOtpText = getString(R.string.auto_fill_token, token);
//                                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(finalOtpText
//                                        .length())});
//                                editText.setText(finalOtpText);
//                            }
//                            countDownTimer.cancel();
//                            ((Button) findViewById(R.id.btnVerify)).setText(R.string.verify);
//                            unregisterReceiver(mTokenReceiver);
//                            mTokenReceiver = null;
//                        }
//                    }
//                }
//            };
//        }
//
//
//        final IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
//        //        intentFilter.addAction(SmsUtils.ACTION_PROFILE_RESPONSE);
//        registerReceiver(mTokenReceiver, intentFilter);
//    }
//
//    public void showLoader(String message, final boolean showSmsVerificationButton) {
////        showLayout(LOADER_LAYOUT);
////        ((TextView) findViewById(R.id.txtLoader)).setText(message);
//        if (showSmsVerificationButton) {
//            //            todo show sms button if number cant be resolved here
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        if (mTokenReceiver != null) {
//            unregisterReceiver(mTokenReceiver);
//        }
//        super.onDestroy();
//    }
//
//    @Override
//    public void onBackPressed() {
////            super.onBackPressed();
//    }
//
//    public void showLayout(int id) {
//        findViewById(R.id.landingLayout).setVisibility(id == LANDING_LAYOUT ? View.VISIBLE : View.GONE);
//        findViewById(R.id.profileLayout).setVisibility(id == PROFILE_LAYOUT ? View.VISIBLE : View.GONE);
//        findViewById(R.id.loaderLayout).setVisibility(id == LOADER_LAYOUT ? View.VISIBLE : View.GONE);
//        findViewById(R.id.formLayout).setVisibility(id == FORM_LAYOUT ? View.VISIBLE : View.GONE);
//        findViewById(R.id.btnProceed).setOnClickListener(proceedClickListener);
//
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(findViewById(R.id.formLayout).getWindowToken(), 0);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
//        //       we continue whether we get required phone permissions or not
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length <= 0) {
//                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
//                        grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED &&
//                        grantResults[4] == PackageManager.PERMISSION_GRANTED && grantResults[5] == PackageManager.PERMISSION_GRANTED &&
//                        grantResults[5] == PackageManager.PERMISSION_GRANTED) {
//                    //granted
//                    trueButton.callOnClick();
////                    apiCall();
//                } else {
//                    //not granted
////                    Log.e(MainApplication.TAG, "not granted: Dashboard " + grantResults[0]);
//                    {
//                        // Permission denied.
//                        // Notify the user via a SnackBar that they have rejected a core permission for the
//                        // app, which makes the Activity useless. In a real app, core permissions would
//                        // typically be best requested during a welcome-screen flow.
//                        // Additionally, it is important to remember that a permission might have been
//                        // rejected without asking the user for permission (device policy or "Never ask
//                        // again" prompts). Therefore, a user interface affordance is typically implemented
//                        // when permissions are denied. Otherwise, your app could appear unresponsive to
//                        // touches or interactions which have required permissions.
//                        //                    Toast.makeText(this, R.string.permission_denied_explanation, Toast.LENGTH_LONG).show();
//                        //                    finish();
//                        Snackbar.make(
//                                findViewById(R.id.rootViews),
//                                R.string.permission_denied_explanation,
//                                Snackbar.LENGTH_INDEFINITE)
//                                .setAction(R.string.settings, new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        // Build intent that displays the App settings screen.
//                                        Intent intent = new Intent();
//                                        intent.setAction(
//                                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                        Uri uri = Uri.fromParts("package",
//                                                BuildConfig.APPLICATION_ID, null);
//                                        intent.setData(uri);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
//                                    }
//                                })
//                                .show();
//                    }
//                }
//                break;
//
//            case 10:
//                if (grantResults.length <= 0) {
//                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
//                        grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED &&
//                        grantResults[4] == PackageManager.PERMISSION_GRANTED && grantResults[5] == PackageManager.PERMISSION_GRANTED
//                        && grantResults[6] == PackageManager.PERMISSION_GRANTED) {
//                    //granted
////                    checkPhonePermission();
//                    if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
//                            || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
//                            || !isAnswerCallPermissionEnabled()) {
//                        requestPhonePermission();
//                    } else {
//                        verificationCallbackType = VerificationCallback.TYPE_MISSED_CALL;
//                        requestVerification();
//                    }
//                } else {
//                    //not granted
////                    Log.e(MainApplication.TAG, "not granted: Dashboard " + grantResults[0]);
//                    {
//                        // Permission denied.
//                        // Notify the user via a SnackBar that they have rejected a core permission for the
//                        // app, which makes the Activity useless. In a real app, core permissions would
//                        // typically be best requested during a welcome-screen flow.
//                        // Additionally, it is important to remember that a permission might have been
//                        // rejected without asking the user for permission (device policy or "Never ask
//                        // again" prompts). Therefore, a user interface affordance is typically implemented
//                        // when permissions are denied. Otherwise, your app could appear unresponsive to
//                        // touches or interactions which have required permissions.
//                        //                    Toast.makeText(this, R.string.permission_denied_explanation, Toast.LENGTH_LONG).show();
//                        //                    finish();
//                        Snackbar.make(
//                                findViewById(R.id.rootViews),
//                                R.string.permission_denied_explanation,
//                                Snackbar.LENGTH_INDEFINITE)
//                                .setAction(R.string.settings, new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        // Build intent that displays the App settings screen.
//                                        Intent intent = new Intent();
//                                        intent.setAction(
//                                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                        Uri uri = Uri.fromParts("package",
//                                                BuildConfig.APPLICATION_ID, null);
//                                        intent.setData(uri);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);
//                                    }
//                                })
//                                .show();
//                    }
//                }
//                break;
//
//
//            case 0:
//                boolean isPhonePermissionsGiven = true;
//                for (int result : grantResults) {
//                    if (result != PackageManager.PERMISSION_GRANTED) {
//                        isPhonePermissionsGiven = false;
//                        break;
//                    }
//                }
////                if (isPhonePermissionsGiven) {
////                    //               this will start missed-call verification
////                    verificationCallbackType = VerificationCallback.TYPE_MISSED_CALL;
////                    requestVerification();
////                }
//                //                if any of the phone permissions are not given, we would fallback to otp flow
//                //                it would be a better place to request sms permission to auto-fill otp
////                else {
//                checkSMSPermission();
//
////                }
//
//                break;
//
//            case 2:
//                //            this will start sms verification
//                requestVerification();
//                break;
//
//        }
//    }
//
//    private boolean isAnswerCallPermissionEnabled() {
//        return Build.VERSION.SDK_INT < Build.VERSION_CODES.O
//                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ANSWER_PHONE_CALLS) == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private boolean shouldShowAnswerCallRequestPermissionRationale() {
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
//                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ANSWER_PHONE_CALLS);
//    }
//
//    private void requestRequiredPhonePermissions() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            ActivityCompat.requestPermissions(NewTruecallerSignIn.this,
//                    new String[]{Manifest.permission.ANSWER_PHONE_CALLS, Manifest.permission.READ_CALL_LOG,
//                            Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE);
//        } else {
//            ActivityCompat.requestPermissions(NewTruecallerSignIn.this,
//                    new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_PHONE_STATE}, REQUEST_PHONE);
//        }
//    }
//
//    public void setData() {  //{"message":"update mandate","is_update_mandate":1}
//        try {
//            try {
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//                Intent batteryStatus = NewTruecallerSignIn.this.registerReceiver(null, ifilter);
//
//                batterylevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//
////        float batteryPct = batterylevel / (float)scale;
//
//                SubscriptionManager subscriptionManager = null;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                    subscriptionManager = (SubscriptionManager) getApplicationContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
//                }
//
//                List<SubscriptionInfo> subscriptionInfoList = null;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                    subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
//
//                    try {
//                        network_provider_0 = String.valueOf(subscriptionInfoList.get(0).getDisplayName());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        network_provider_0 = network_provider_0 + "_" + subscriptionInfoList.get(0).getSimSlotIndex();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        network_provider_1 = String.valueOf(subscriptionInfoList.get(1).getDisplayName());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        network_provider_1 = network_provider_1 + "_" + subscriptionInfoList.get(1).getSimSlotIndex();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            String verification = "null";
//            String url = MainApplication.mainUrl + "truecallerresponse/insert";//http://192.168.0.110/eduvanzapi/pqform/thirdPartyGenerateOtpCode
//            Map<String, String> params = new HashMap<String, String>();
//
//            params.put("avatarUrl", "");
//            params.put("city", "");
//            params.put("companyName", "");
//            params.put("countryCode", "");
//            params.put("email", "");
//            params.put("facebookId", "");
//            params.put("firstName", "");
//            params.put("gender", "");
//            params.put("isAmbassador", "");
//            params.put("isSimChanged","");
//            params.put("isTrueName", "");
//            params.put("jobTitle", "");
//            params.put("lastName", "");
//            params.put("payload", "");
//            params.put("phoneNumber", "");
//            params.put("requestNonce","" );
//            params.put("signature", "");
//            params.put("street", "");
//            params.put("twitterId", "");
//            params.put("url", "");
//
//            params.put("verificationMode", verificationtype);
//            params.put("verificationTimestamp", "");
//            params.put("zipcode", "");
//
//            String appVersion ="",deviceVersion ="",device ="";
//            try {
//                PackageManager manager = NewTruecallerSignIn.this.getPackageManager();
//                PackageInfo info = manager.getPackageInfo(
//                        NewTruecallerSignIn.this.getPackageName(), 0);
//                appVersion = info.versionName;
//                deviceVersion = String.valueOf(Build.VERSION.SDK_INT);
//                device = String.valueOf(Build.MODEL);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            String ipaddress ="";
//            try {
//                ipaddress = Utils.getIPAddress(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            params.put("handset", device);
//            params.put("network_provider", network_provider_0 + "," + network_provider_1);
//            params.put("signal_strength", "");
//            params.put("battery_strength", String.valueOf(batterylevel));
//            params.put("last_connected_tower", "");
//            params.put("wifi_connected_network", "");
//            params.put("app_version", appVersion);
//            params.put("device_version", deviceVersion);
//            params.put("ip_address", ipaddress);
//
//            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("mobile_no", mPhoneField.getText().toString().trim());
//            editor.apply();
//            editor.commit();
//
//            if (!Globle.isNetworkAvailable(NewTruecallerSignIn.this)) {
//                Toast.makeText(NewTruecallerSignIn.this, getText(R.string.please_check_your_network_connection), Toast.LENGTH_SHORT).show();
//
//            } else {
//                VolleyCallNew volleyCall = new VolleyCallNew();
//                volleyCall.sendRequest(NewTruecallerSignIn.this, url, mActivity, null, "updateTrueData", params);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void setData(TrueProfile trueProfile) {  //{"message":"update mandate","is_update_mandate":1}
//        try {
//
//            try {
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//                Intent batteryStatus = NewTruecallerSignIn.this.registerReceiver(null, ifilter);
//
//                batterylevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//
////        float batteryPct = batterylevel / (float)scale;
//
//                SubscriptionManager subscriptionManager = null;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                    subscriptionManager = (SubscriptionManager) getApplicationContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
//                }
//
//                List<SubscriptionInfo> subscriptionInfoList = null;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                    subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
//
//                    try {
//                        network_provider_0 = String.valueOf(subscriptionInfoList.get(0).getDisplayName());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        network_provider_0 = network_provider_0 + "_" + subscriptionInfoList.get(0).getSimSlotIndex();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        network_provider_1 = String.valueOf(subscriptionInfoList.get(1).getDisplayName());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        network_provider_1 = network_provider_1 + "_" + subscriptionInfoList.get(1).getSimSlotIndex();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            String verification = "null";
//            String url = MainApplication.mainUrl + "truecallerresponse/insert";//http://192.168.0.110/eduvanzapi/pqform/thirdPartyGenerateOtpCode
//            Map<String, String> params = new HashMap<String, String>();
//
//            params.put("avatarUrl", String.valueOf(trueProfile.avatarUrl));
//            params.put("city", String.valueOf(trueProfile.city));
//            params.put("companyName", String.valueOf(trueProfile.companyName));
//            params.put("countryCode", String.valueOf(trueProfile.countryCode));
//            params.put("email", String.valueOf(trueProfile.email));
//            params.put("facebookId", String.valueOf(trueProfile.facebookId));
//            params.put("firstName", String.valueOf(trueProfile.firstName));
//            params.put("gender", String.valueOf(trueProfile.gender));
//            params.put("isAmbassador", String.valueOf(trueProfile.isAmbassador));
//            params.put("isSimChanged", String.valueOf(trueProfile.isSimChanged));
//            params.put("isTrueName", String.valueOf(trueProfile.isTrueName));
//            params.put("jobTitle", String.valueOf(trueProfile.jobTitle));
//            params.put("lastName", String.valueOf(trueProfile.lastName));
//            params.put("payload", String.valueOf(trueProfile.payload));
//            params.put("phoneNumber", String.valueOf(trueProfile.phoneNumber));
//            params.put("requestNonce", String.valueOf(trueProfile.requestNonce));
//            params.put("signature", String.valueOf(trueProfile.signature));
//            params.put("street", String.valueOf(trueProfile.street));
//            params.put("twitterId", String.valueOf(trueProfile.twitterId));
//            params.put("url", String.valueOf(trueProfile.url));
//
////            if (String.valueOf(trueProfile.verificationTimestamp) == null || String.valueOf(trueProfile.verificationTimestamp) == "") {
////                verification = "Verified by Truecaller SDK";
////            } else {
////                verification = String.valueOf(trueProfile.verificationTimestamp);
////            }
//            params.put("verificationMode", verificationtype);
//            params.put("verificationTimestamp", String.valueOf(trueProfile.verificationTimestamp));
//            params.put("zipcode", String.valueOf(trueProfile.zipcode));
//
//            String appVersion ="",deviceVersion ="",device ="";
//            try {
//                PackageManager manager = NewTruecallerSignIn.this.getPackageManager();
//                PackageInfo info = manager.getPackageInfo(
//                        NewTruecallerSignIn.this.getPackageName(), 0);
//                appVersion = info.versionName;
//                deviceVersion = String.valueOf(Build.VERSION.SDK_INT);
//                device = String.valueOf(Build.MODEL);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            String ipaddress ="";
//            try {
//                ipaddress = Utils.getIPAddress(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            params.put("handset", device);
//            params.put("network_provider", network_provider_0 + "," + network_provider_1);
//            params.put("signal_strength", "");
//            params.put("battery_strength", String.valueOf(batterylevel));
//            params.put("last_connected_tower", "");
//            params.put("wifi_connected_network", "");
//            params.put("app_version", appVersion);
//            params.put("device_version", deviceVersion);
//            params.put("ip_address", ipaddress);
//
//            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("mobile_no", trueProfile.phoneNumber);
//            editor.apply();
//            editor.commit();
//
//            if (!Globle.isNetworkAvailable(NewTruecallerSignIn.this)) {
//                Toast.makeText(NewTruecallerSignIn.this, getText(R.string.please_check_your_network_connection), Toast.LENGTH_SHORT).show();
//
//            } else {
//                VolleyCallNew volleyCall = new VolleyCallNew();
//                volleyCall.sendRequest(NewTruecallerSignIn.this, url, mActivity, null, "updateTrueData", params);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void UpdateTrueData() {  //{"message":"update mandate","is_update_mandate":1}
//        try {
//
//            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("otp_done", "1");
//            editor.apply();
//            editor.commit();
//
//            Intent intent = new Intent(NewTruecallerSignIn.this, DashboardActivity.class);
//            startActivity(intent);
//            finish();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private boolean isAccessGranted() {
//        try {
//            PackageManager packageManager = getPackageManager();
//            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
//            AppOpsManager appOpsManager = null;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
//            }
//            int mode = 0;
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
//                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
//                        applicationInfo.uid, applicationInfo.packageName);
//            }
//            return (mode == AppOpsManager.MODE_ALLOWED);
//
//        } catch (PackageManager.NameNotFoundException e) {
//            return false;
//        }
//    }
//
//    static class PhoneUtils {
//
//        @NonNull
//        /*package*/ static Pair<String, String> processPhoneNumber(@NonNull String phoneNumber) {
//            Pattern compile = Pattern.compile("^\\+\\d\\d");
//            Matcher matcher = compile.matcher(phoneNumber);
//            if (matcher.find()) {
//                int start = matcher.toMatchResult().start();
//                int end = matcher.toMatchResult().end();
//                return new Pair<>(phoneNumber.substring(start, end), phoneNumber.substring(end));
//            }
//            return new Pair<>("NONE", phoneNumber);
//        }
//    }
//
//}
