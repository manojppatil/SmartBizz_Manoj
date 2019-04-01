package com.eduvanzapplication.newUI.newViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.VolleyCall;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class  GetMobileNo extends AppCompatActivity {

    private final String TAG = GetMobileNo.class.getSimpleName();
    EditText edtMobile, edtOtp, edtFirstName, edtEmail;
    ImageView ivRetry, ivIndicator;
    TextView txtGetOtp, txtMsg1, txtMsg2;
    String userEmail = "";
    LinearLayout linGetOtp, layoutOtp, linEmailLayout, linFacebook, linLinkedIn, linGoogle;
    LoginButton fbLoginButton;
    ProgressDialog progressDialog ;
    private boolean mobileNoDone = false, signUpCalled = false;
    CallbackManager callbackManagerFb = CallbackManager.Factory.create();
    private static final String EMAIL = "email";

    final private int RC_SIGN_IN = 112;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_get_mobile_no);
        setViews();
        setFacebookLogin();

    }

    private void setViews() {
        progressDialog = new ProgressDialog(GetMobileNo.this);
        edtMobile = findViewById(R.id.edtMobile);
        edtOtp = findViewById(R.id.edtOtp);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtEmail = findViewById(R.id.edtEmail);
        ivRetry  =findViewById(R.id.ivRetry);
        ivIndicator = findViewById(R.id.ivIndicator);
        txtGetOtp = findViewById(R.id.txtGetOtp);
        txtGetOtp.setText(getString(R.string.submit));
        txtMsg1 = findViewById(R.id.txtMsg1);
        txtMsg2 = findViewById(R.id.txtMsg2);
        linGetOtp = findViewById(R.id.linGetOtp);
        linGetOtp.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
        layoutOtp = findViewById(R.id.layoutOtp);
        layoutOtp.setVisibility(View.GONE);
        linEmailLayout = findViewById(R.id.linEmailLayout);
        linEmailLayout.setVisibility(View.GONE);
        linFacebook = findViewById(R.id.linFacebook);
        linLinkedIn = findViewById(R.id.linLinkedIn);
        linGoogle = findViewById(R.id.linGoogle);
        fbLoginButton = findViewById(R.id.fb_login_button);



        edtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 10){
                    // api call - get otp
                    getOtp();
                }

                if (s.length() < 10){
                    linGetOtp.setOnClickListener(null);
                    linGetOtp.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
                }
                else {
                    linGetOtp.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));
                    linGetOtp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!mobileNoDone){

                            }else if (mobileNoDone){
                                //api call - verify otp
                                if (layoutOtp.getVisibility() == View.VISIBLE && !signUpCalled)
                                        otpLogin();
                                else{
                                    if (edtFirstName.getText().toString().equals("")){
                                        edtFirstName.setError("Please provide your first name");
                                    }else if (edtMobile.getText().toString().equals("")){
                                        edtMobile.setError("Please provide mobile number");
                                    }else if (edtEmail.getText().toString().equals("")){
                                        edtEmail.setError("Please provide email");
                                    }else{
                                        if (!signUpCalled){
                                            generateOtpCode();  //signUp
                                        }
                                        else
                                            verifyOtpCode();
                                    }
                                }

                                edtOtp.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        if (s.length() == 0){
                                            linGetOtp.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
                                        }
                                        else
                                            linGetOtp.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));
                                    }
                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }
                                    @Override
                                    public void afterTextChanged(Editable s) {

                                    }
                                });
//                                if (!edtOtp.getText().toString().equals("")){
//                                    startActivity(new Intent(GetMobileNo.this, GetEmailActivity.class)
//                                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                                }
                            }
                        }
                    });

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        linFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLoginButton.performClick();
            }
        });

        linGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGoogleLogin();
            }
        });


    }

    private void setFacebookLogin() {
        fbLoginButton.setReadPermissions(Arrays.asList(EMAIL));
        fbLoginButton.registerCallback(callbackManagerFb, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                Log.d("FBSUCESS","RESULT - "+profile.getFirstName() +""+profile.getId());
                edtFirstName.setText(profile.getFirstName());
                if (profile.getId().contains("@")){
                    edtEmail.setText(profile.getId());
                }else
                    Snackbar.make(linGetOtp, "Unable to get Email ID, Please enter it manually",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FBONERROR","ERROR - "+ error.toString());
            }
        });

    }

    private void setGoogleLogin() {
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManagerFb.onActivityResult(requestCode, resultCode, data); //facebook
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            userEmail =  account.getEmail();
            edtEmail.setText(userEmail);
            edtFirstName.setText(account.getDisplayName());
            Log.d(TAG,"Google Mail - "+userEmail);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }


    private void verifyOtpCode() {
        try {
            if (!isFinishing())
                progressDialog.show();
            String url = MainActivity.mainUrl + "authorization/verifyOtpCode";
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobileno", edtMobile.getText().toString());
            params.put("name", edtFirstName.getText().toString());
            params.put("email", edtEmail.getText().toString());
            params.put("otpcode", edtOtp.getText().toString());
            if (!Globle.isNetworkAvailable(GetMobileNo.this)) {
                Snackbar.make(linGetOtp, getString(R.string.please_check_your_network_connection),Snackbar.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(getApplicationContext(), url, GetMobileNo.this, null, "verifyOtpCode", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(GetMobileNo.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }

    public void getOtp(){
        try {
            if (!isFinishing())
                progressDialog.show();
            String url = MainActivity.mainUrl + "authorization/getLoginOtp";
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile", edtMobile.getText().toString());
            if (!Globle.isNetworkAvailable(GetMobileNo.this)) {
                Snackbar.make(linGetOtp, getString(R.string.please_check_your_network_connection),Snackbar.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(getApplicationContext(), url, GetMobileNo.this, null, "getOtp", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(GetMobileNo.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void getOTPResponse(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");
            progressDialog.dismiss();
            mobileNoDone = true;
            if (status.equalsIgnoreCase("1")) {

                saveUserPrefernce("otp_done","1");
                saveUserPrefernce("mobile_no",edtMobile.getText().toString().trim());
                saveUserPrefernce("userpolicyAgreement", "1");

                layoutOtp.setVisibility(View.VISIBLE);
                linEmailLayout.setVisibility(View.GONE);
                txtGetOtp.setText(getString(R.string.submit));
                ivIndicator.setImageDrawable(getResources().getDrawable(R.drawable.ic_angle_right));
                txtMsg1.setText(getString(R.string.to_verify));
                txtMsg2.setText(getString(R.string.enter_received_otp));

            }else {

                layoutOtp.setVisibility(View.GONE);
                linEmailLayout.setVisibility(View.VISIBLE);

//                Toast.makeText(GetMobileNo.this, message, Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(GetMobileNo.this, GetEmailActivity.class)
//                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }


        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(GetMobileNo.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void otpLogin(){
        try {
            progressDialog.setMessage("Loading");
            if (!isFinishing())
                progressDialog.show();
            String url = MainActivity.mainUrl + "authorization/otpLogin";
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile", edtMobile.getText().toString());
            params.put("otp", edtOtp.getText().toString());
            if (!Globle.isNetworkAvailable(GetMobileNo.this)) {
                Snackbar.make(linGetOtp, getString(R.string.please_check_your_network_connection),Snackbar.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(getApplicationContext(), url, GetMobileNo.this, null, "otpLogin", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(GetMobileNo.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void otpLoginResponse(JSONObject jsonData){
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                progressDialog.dismiss();
                saveUserPrefernce("name",jsonData.getJSONObject("result").getString("first_name"));
                saveUserPrefernce("otp_done","1");
                saveUserPrefernce("mobile_no",edtMobile.getText().toString().trim());
                saveUserPrefernce("student_id",jsonData.optString("student_id"));
                saveUserPrefernce("user_img",jsonData.getJSONObject("result").getString("img_profile"));
                saveUserPrefernce("email",jsonData.getJSONObject("result").getString("email"));
                saveUserPrefernce("auth_token", jsonData.getJSONObject("auth_token").getString("auth_token"));
                saveUserPrefernce("student_id", jsonData.optString("student_id"));
                saveUserPrefernce("userpolicyAgreement", "1");

                startActivity(new Intent(GetMobileNo.this, DashboardActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            } else {
                progressDialog.dismiss();
                Log.e(MainApplication.TAG, "getOTPValidation: ");
                Toast.makeText(GetMobileNo.this, message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(GetMobileNo.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void generateOtpCode(){
        try {
            progressDialog.setMessage("Loading");
            if (!isFinishing())
                progressDialog.show();
            String url = MainActivity.mainUrl + "authorization/generateOtpCode";
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobileno", edtMobile.getText().toString());
            params.put("name", edtFirstName.getText().toString());
            params.put("email", edtEmail.getText().toString());
            if (!Globle.isNetworkAvailable(GetMobileNo.this)) {
                Snackbar.make(linGetOtp, getString(R.string.please_check_your_network_connection),Snackbar.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(getApplicationContext(), url, GetMobileNo.this, null, "generateOtpCode", params, MainActivity.auth_token);
                signUpCalled = true;
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(GetMobileNo.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }


    }

    public void generateOtpCodeResponse(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                progressDialog.dismiss();
                layoutOtp.setVisibility(View.VISIBLE);
                linEmailLayout.setVisibility(View.GONE);
//                startActivity(new Intent(GetMobileNo.this, DashboardActivity.class)
//                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            } else {
                progressDialog.dismiss();
                Toast.makeText(GetMobileNo.this, message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(GetMobileNo.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }

    public void verifyOtpCodeResponse(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                progressDialog.dismiss();

//                MainActivity.auth_token  = jsonData.getJSONObject("auth_token").getString("auth_token");
                saveUserPrefernce("name",jsonData.getJSONObject("result").getString("first_name"));
                saveUserPrefernce("otp_done","1");
                saveUserPrefernce("mobile_no",edtMobile.getText().toString().trim());
                saveUserPrefernce("student_id",jsonData.optString("student_id"));
                saveUserPrefernce("user_img",jsonData.getJSONObject("result").getString("img"));
                saveUserPrefernce("email",jsonData.getJSONObject("result").getString("email"));
                saveUserPrefernce("auth_token", jsonData.getJSONObject("auth_token").getString("auth_token"));
                saveUserPrefernce("student_id", jsonData.optString("student_id"));
                saveUserPrefernce("userpolicyAgreement", "1");

                startActivity(new Intent(GetMobileNo.this, DashboardActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            } else {
                progressDialog.dismiss();
                Toast.makeText(GetMobileNo.this, message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(GetMobileNo.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }



   private void saveUserPrefernce(String key, String value){
       SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putString(key, value);
       editor.apply();
       editor.commit();

   }
}
    //    TextView textViewToolbar;
//    MainApplication mainApplication;
//    static Context mContext;
//    Button button;
//    static EditText edtName, edtMobileNo, edtEmailId;
//    AppCompatActivity mActivity;
//    String mobileNO="";
//    public int GET_MY_PERMISSION = 1, permission, permission1, permission2, permission3,
//            permission4, permission5, permission6, policyAgreementStatus;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        try {
//            setContentView(R.layout.activity_get_mobile_no);
//            mActivity = this;
//            mContext = this;
//            mainApplication = new MainApplication();
//
//            try {
//                Bundle bundle = getIntent().getExtras();
//                if (bundle != null) {
//                    mobileNO = bundle.getString("mobile_no_val", "");
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            try {
//                Bundle bundle = getIntent().getExtras();
//                mobileNO = (bundle.getString("mPhoneField"));
//            } catch (Exception e) {
//            }
//
//            textViewToolbar = (TextView) findViewById(R.id.textView_validateotp);
//            mainApplication.applyTypefaceBold(textViewToolbar, mContext);
//
//            button = (Button) findViewById(R.id.button_getotp);
//            mainApplication.applyTypeface(button, mContext);
//
//            edtName = (EditText) findViewById(R.id.edtName);
//            edtMobileNo = (EditText) findViewById(R.id.edtMobileNo);
//            edtEmailId = (EditText) findViewById(R.id.edtEmailId);
//            edtMobileNo.setText(mobileNO);
//
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                    }
//
//                    if(!edtName.getText().toString().equalsIgnoreCase("")
//                            && !edtMobileNo.getText().toString().equalsIgnoreCase("")
//                            && !edtEmailId.getText().toString().equalsIgnoreCase("")){
//                        if (Build.VERSION.SDK_INT >= 23)
//                        {
//                            permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
//                            permission4 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//
//                            if (permission != PackageManager.PERMISSION_GRANTED || permission1!=PackageManager.PERMISSION_GRANTED )
//
//                            {//Direct Permission without disclaimer dialog
//                                ActivityCompat.requestPermissions(GetMobileNo.this,
//                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                                                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                                        GET_MY_PERMISSION);
//                            }else {
//                                apiCall();
//                            }
//                        }
//                        else
//                        {
//                            apiCall();
//                        }
//                }else {
//                        if(edtName.getText().toString().equalsIgnoreCase("")){
//                            edtName.setError("Please provide name");
//                        }
//                        if(edtMobileNo.getText().toString().equalsIgnoreCase("")){
//                            edtMobileNo.setError(getString(R.string.please_provide_mobile_number));
//                        }
//                        if(edtEmailId.getText().toString().equalsIgnoreCase("")){
//                            edtEmailId.setError("Please provide email id");
//                        }
//                }
//                 button.setEnabled(false);
//                }
//            });
//
////        if(policyAgreementStatus == 0){
////            AlertDialog.Builder eulaBuilder = new AlertDialog.Builder(this);
////            LayoutInflater eulaInflater = LayoutInflater.from(this);
////            View tncLayout = eulaInflater.inflate(R.layout.dialoglayout_tnc, null);
////            eulaBuilder.setView(tncLayout);
////            eulaBuilder.setCancelable(false);
////            WebView webView = (WebView) tncLayout.findViewById(R.id.tnc_webview);
////            webView.getSettings().setJavaScriptEnabled(true);
////            webView.loadUrl("http://eduvanz.com/demo/privacy_policy");
////            eulaBuilder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
////                public void onClick(DialogInterface d, int m) {
////
////                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
////                    SharedPreferences.Editor editor = sharedPreferences.edit();
////                    editor.putInt("userpolicyAgreement", 1);
////                    editor.apply();
////                    editor.commit();
////
////                    // permission for Stats
////                    if(!isAccessGranted()) {
////                        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
////                    }
////                }
////            });
////            eulaBuilder.setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
////                public void onClick(DialogInterface d, int m) {
////                    ((Activity) mContext).finish();
////                }
////            });
////            eulaBuilder.show();
////        }else {
////            // permission for Stats
////            if(!isAccessGranted()) {
////                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
////            }
////        }
//
//
//            //setLanguage();
//        } catch (Exception e) {
//            String className = this.getClass().getSimpleName();
//            String name = new Object() {
//            }.getClass().getEnclosingMethod().getName();
//            String errorMsg = e.getMessage();
//            String errorMsgDetails = e.getStackTrace().toString();
//            String errorLine = String.valueOf(e.getStackTrace()[0]);
//            Globle.ErrorLog(GetMobileNo.this,className, name, errorMsg, errorMsgDetails, errorLine);
//        }
//
//    }
//
//    private void setLanguage()
//    {
//        try {
//            AlertDialog.Builder builderSingle = new AlertDialog.Builder(GetMobileNo.this);
//            builderSingle.setIcon(R.drawable.eduvanz_logo_new);
//            builderSingle.setTitle("Select Language:-");
//
//            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(GetMobileNo.this, android.R.layout.select_dialog_singlechoice);
//            arrayAdapter.add("English");
//            arrayAdapter.add("Marathi");
//            arrayAdapter.add("Hindi");
//
//            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//
//            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    String strName = arrayAdapter.getItem(which);
//                    AlertDialog.Builder builderInner = new AlertDialog.Builder(GetMobileNo.this);
//                    builderInner.setMessage(strName);
//                    builderInner.setTitle("Your Selected Item is");
//                    builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog,int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builderInner.show();
//                }
//            });
//            builderSingle.show();
//
//        } catch (Exception e) {
//            String className = this.getClass().getSimpleName();
//            String name = new Object() {
//            }.getClass().getEnclosingMethod().getName();
//            String errorMsg = e.getMessage();
//            String errorMsgDetails = e.getStackTrace().toString();
//            String errorLine = String.valueOf(e.getStackTrace()[0]);
//            Globle.ErrorLog(GetMobileNo.this,className, name, errorMsg, errorMsgDetails, errorLine);
//        }
//    }
//
//    protected void makeRequest() {
//        ActivityCompat.requestPermissions(GetMobileNo.this,
//                new String[]{},
//                GET_MY_PERMISSION);
//    }
//
//    public void apiCall(){
//        /** API CALL GET OTP**/
//
//            try {
////                String url = MainActivity.mainUrl + "pqform/thirdPartyGenerateOtpCode";//http://192.168.0.110/eduvanzapi/pqform/thirdPartyGenerateOtpCode
//                String url = MainActivity.mainUrl + "authorization/generateOtpCode";//http://192.168.0.110/eduvanzapi/pqform/thirdPartyGenerateOtpCode
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("mobileno", edtMobileNo.getText().toString());
//                params.put("name", edtName.getText().toString());
//                params.put("email", edtEmailId.getText().toString());
//                if(!Globle.isNetworkAvailable(GetMobileNo.this))
//                {
//                    Toast.makeText(GetMobileNo.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//
//                } else {
//                    VolleyCallLogin volleyCall = new VolleyCallLogin();
//                    volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "getOtp", params);
//                }
//            } catch (Exception e) {
//                String className = this.getClass().getSimpleName();
//                String name = new Object() {
//                }.getClass().getEnclosingMethod().getName();
//                String errorMsg = e.getMessage();
//                String errorMsgDetails = e.getStackTrace().toString();
//                String errorLine = String.valueOf(e.getStackTrace()[0]);
//                Globle.ErrorLog(GetMobileNo.this,className, name, errorMsg, errorMsgDetails, errorLine);
//            }
//
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//
//            case 1:
//                if (grantResults.length <= 0) {
//                }
//                else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    //granted
//                    apiCall();
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
//
//                        try {
//                            button.setEnabled(true);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
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
//        }
//
//    }
//
////    @Override
////    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
////        if (null != mTrueClient && mTrueClient.onActivityResult(requestCode, resultCode, data)) {
////            return;
////        }
////
////        super.onActivityResult(requestCode, resultCode, data);
////    }
////
////    @Override
////    public void onSuccesProfileShared(@NonNull TrueProfile trueProfile) {
////
////        StringBuilder sr = new StringBuilder();
////        if (trueProfile.verificationMode != null) {
////            sr.append("Verification mode: ").append(trueProfile.verificationMode).append("\n");
////        }
////        if (trueProfile.verificationTimestamp != 0L) {
////            sr.append("Verification Time: ").append(trueProfile.verificationTimestamp).append("\n");
////        }
////
////        sr.append("Sim changed: ").append(trueProfile.isSimChanged).append("\n");
////        sr.append("RequestNonce: ").append(trueProfile.requestNonce).append("\n");
////
////        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
////        SharedPreferences.Editor editor = sharedPreferences.edit();
////        editor.putString("otp_done", "1");
////        editor.putString("mobile_no", trueProfile.phoneNumber);
////        editor.apply();
////        editor.commit();
////
////        Intent intent = new Intent(GetMobileNo.this, DashboardActivity.class);
////        startActivity(intent);
////        finish();
////
////    }
////
////    @Override
////    public void onFailureProfileShared(@NonNull TrueError trueError) {
////        Toast.makeText(this, "SignIn Failed", Toast.LENGTH_LONG).show();
////
////    }

