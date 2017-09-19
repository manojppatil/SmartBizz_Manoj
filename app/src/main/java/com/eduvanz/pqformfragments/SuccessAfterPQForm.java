package com.eduvanz.pqformfragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.AlarmReceiver;
import com.eduvanz.DashBoardFragment;
import com.eduvanz.Main2ActivityNavigation;
import com.eduvanz.MainActivity;
import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.SharedPref;
import com.eduvanz.SuccessSplash;
import com.eduvanz.volley.VolleyCall;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.eduvanz.MainApplication.TAG;
import static com.eduvanz.R.string.calendar;

public class SuccessAfterPQForm extends AppCompatActivity {
    Toolbar mToolbar;
    public static AppCompatActivity mActivity;
    TextView textView4, textView3;
    String user_name;
    public static Button buttonSendOTP, buttonVerify;
    Typeface typefaceFont, typefaceFontBold;
    int permission;
    public String recivedotp="";
    public static EditText editTextInputName, editTextInputEmail, editTextInputNo, editTextOTP;
    SharedPref shareP;
    ProgressDialog mDialog;
    public int GET_MY_PERMISSION = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successafterpq);

//        mToolbar= (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setTitle("Details");

        typefaceFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_bold.ttf");

        mActivity = this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);// hide the keyboard everytime the activty starts.

        editTextInputName = (EditText) findViewById(R.id.input_name);
        editTextInputName.setTypeface(typefaceFont);
        editTextInputEmail = (EditText) findViewById(R.id.input_email);
        editTextInputEmail.setTypeface(typefaceFont);
        editTextInputNo = (EditText) findViewById(R.id.input_number);
        editTextInputNo.setTypeface(typefaceFont);
        editTextOTP = (EditText) findViewById(R.id.input_otp);
        editTextOTP.setTypeface(typefaceFont);

        textView3 = (TextView) findViewById(R.id.textview3_font_ss);
        textView3.setTypeface(typefaceFont);
        textView4 = (TextView) findViewById(R.id.textview_resendotp);
        textView4.setTypeface(typefaceFont);

        buttonVerify = (Button) findViewById(R.id.button_verify);
        buttonVerify.setTypeface(typefaceFont);

        buttonSendOTP = (Button) findViewById(R.id.button_sendOtp);
        buttonSendOTP.setTypeface(typefaceFont);
        buttonSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(editTextInputName.getText().toString().equalsIgnoreCase("")){
                    editTextInputName.setError("Name Cannot be Empty");
                    return;
                }
                if(editTextInputNo.getText().toString().equalsIgnoreCase("")){
                    editTextInputNo.setError("Mobile Number Cannot be Empty");
                    return;
                }
                if(editTextInputEmail.getText().toString().equalsIgnoreCase("")){
                    editTextInputEmail.setError("Email Cannot be Empty");
                    return;
                }

                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));

                if (Build.VERSION.SDK_INT >= 23)
                {
                    permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.READ_SMS);

                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        Log.i("TAG", "Permission to record denied");
                        makeRequest();
//                        ActivityCompat.requestPermissions(SuccessAfterPQForm.this,
//                                new String[]{Manifest.permission.READ_SMS},
//                                GET_MY_PERMISSION);
                    } else if(permission == PackageManager.PERMISSION_GRANTED){
                        apiCall();
                    }
                }
                else
                {
                    apiCall();
                }

//                if (ContextCompat.checkSelfPermission(SuccessAfterPQForm.this, Manifest.permission.READ_SMS)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(SuccessAfterPQForm.this,
//                            Manifest.permission.READ_SMS)) {
//            /* do nothing*/
//                    } else {
//
//                        ActivityCompat.requestPermissions(SuccessAfterPQForm.this,
//                                new String[]{Manifest.permission.READ_SMS}, GET_MY_PERMISSION);
//                    }
//                }else {
//
//                }
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCall();
            }
        });

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //-----------------------------------------API CALL---------------------------------------//
                try {
                    String url = MainApplication.mainUrl + "pqform/checkOtpVerification";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("institute", MainApplication.mainapp_instituteID);
                    params.put("course",  MainApplication.mainapp_courseID);
                    params.put("location",  MainApplication.mainapp_locationID);
                    params.put("loanAmount",  MainApplication.mainapp_loanamount);
                    params.put("cc",  MainApplication.mainapp_doccheck);
                    params.put("familyIncomeAmount",  MainApplication.mainapp_fammilyincome);
                    params.put("studentProfessional",  MainApplication.mainapp_professioncheck);
                    params.put("currentResidence",  MainApplication.mainapp_currentCity);
                    params.put("borrowerName",  editTextInputName.getText().toString());
                    params.put("borrowerMobileNo", editTextInputNo.getText().toString());
                    params.put("email",  editTextInputEmail.getText().toString());
                    params.put("otp",  editTextOTP.getText().toString());
                    VolleyCall volleyCall = new VolleyCall();
                    volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "verifyOtp", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //-------------------------------------END OF API CALL------------------------------------//
            }
        });
    }//-------------------------------------END OF NOCREATE---------------------------------------//

    protected void makeRequest() {
        ActivityCompat.requestPermissions(SuccessAfterPQForm.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                GET_MY_PERMISSION);
    }

    public void apiCall(){
        //-----------------------------------------API CALL---------------------------------------//
        try {
            String url = MainApplication.mainUrl + "pqform/sendSms";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute", MainApplication.mainapp_instituteID);
            params.put("course",  MainApplication.mainapp_courseID);
            params.put("location",  MainApplication.mainapp_locationID);
            params.put("loanAmount",  MainApplication.mainapp_loanamount);
            params.put("cc",  MainApplication.mainapp_doccheck);
            params.put("familyIncomeAmount",  MainApplication.mainapp_fammilyincome);
            params.put("studentProfessional",  MainApplication.mainapp_professioncheck);
            params.put("currentResidence",  MainApplication.mainapp_currentCity);
            params.put("borrowerName",  editTextInputName.getText().toString());
            params.put("borrowerMobileNo", editTextInputNo.getText().toString());
            params.put("email",  editTextInputEmail.getText().toString());
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "sendOtp", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-------------------------------------END OF API CALL------------------------------------//
    }

    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    public void setOtp(String otp) {
        recivedotp = otp;
        editTextOTP.setText(recivedotp);
    }

    //---------------------------------RESPONSE OF API CALL---------------------------------------//
    public void sendOtp(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendOtp" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                buttonSendOTP.setVisibility(View.GONE);
                buttonVerify.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.VISIBLE);

            }else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void verifyOtp(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendOtp" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = jsonData.getJSONObject("result");
                String loggedID = jsonObject.optString("logged_id");
                String firstName = jsonObject.optString("first_name");
                String lastName = jsonObject.optString("last_name");
                String userType = jsonObject.optString("user_type");
                String userImage = jsonObject.optString("img_profile");
                String useremail = jsonObject.optString("email");
                String mobileNo = jsonObject.optString("mobile_no");

                shareP =new SharedPref();
                shareP.setLoginDone(this,true);

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("logged_id",loggedID);
                editor.putString("first_name",firstName);
                editor.putString("last_name",lastName);
                editor.putString("user_type",userType);
                editor.putString("mobile_no",mobileNo);
                editor.putString("user_image",userImage);
                editor.putString("user_email",useremail);
                editor.commit();

//                MainApplication.readSms(getApplicationContext(), mobileNo, loggedID);
//                mReadJsonData();

//                MainApplication.contactsRead(getApplicationContext(), mobileNo, loggedID);
//                mReadJsonDataContacts();

                Intent intent = new Intent(this, SuccessSplash.class);
                startActivity(intent);
                finish();

            }else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showProressBar(String s) {
        mDialog = new ProgressDialog(SuccessAfterPQForm.this);
        mDialog.setMessage(s);
//        mDialog.setIndeterminate(false);
//        mDialog.setMax(100);
//        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCancelable(true);
        mDialog.show();
//        return pDialog;
    }
}
