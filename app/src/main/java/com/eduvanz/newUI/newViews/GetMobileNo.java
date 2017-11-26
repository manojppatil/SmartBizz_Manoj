package com.eduvanz.newUI.newViews;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.pqformfragments.SuccessAfterPQForm;
import com.eduvanz.volley.VolleyCall;
import com.eduvanz.volley.VolleyCallNew;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetMobileNo extends AppCompatActivity {

    TextView textViewToolbar;
    MainApplication mainApplication;
    static Context mContext;
    Button button;
    static EditText editTextGetMobileNo;
    AppCompatActivity mActivity;
    String mobileNO="";
    public int GET_MY_PERMISSION = 1, permission;

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
        mainApplication.applyTypeface(textViewToolbar, mContext);

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

                        if (permission != PackageManager.PERMISSION_GRANTED) {
//                            makeRequest();
                        ActivityCompat.requestPermissions(GetMobileNo.this,
                                new String[]{Manifest.permission.READ_SMS},
                                GET_MY_PERMISSION);
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

    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(GetMobileNo.this,
                new String[]{Manifest.permission.READ_SMS},
                GET_MY_PERMISSION);
    }

    public void apiCall(){
        /** API CALL GET OTP**/

            try {
                String url = MainApplication.mainUrl + "authorization/generateOtpCode";
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
}
