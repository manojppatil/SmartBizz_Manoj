package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
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

                /** API CALL GET OTP**/
                if(!editTextGetMobileNo.getText().toString().equalsIgnoreCase("")){
                    try {
                        String url = MainApplication.mainUrl + "authorization/generateOtpCode";
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("mobileno", editTextGetMobileNo.getText().toString());
                        VolleyCallNew volleyCall = new VolleyCallNew();
                        volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "getOtp", params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    editTextGetMobileNo.setError("Please Provide Mobile Number");
                }

            }
        });



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
