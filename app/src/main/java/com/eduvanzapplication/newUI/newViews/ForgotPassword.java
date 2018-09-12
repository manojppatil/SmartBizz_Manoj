package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCallNew;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    Button btnResetPassword;
    EditText edtEmailId;
    MainApplication mainApplication;
    Context context;
    AppCompatActivity mActivity;
    public static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        mainApplication = new MainApplication();
        context = this;
        mActivity = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        progressBar = (ProgressBar) findViewById(R.id.progressBar_emailsignin);
        edtEmailId = (EditText) findViewById(R.id.edtEmailId);
        mainApplication.applyTypeface(edtEmailId, context);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword);
        mainApplication.applyTypeface(btnResetPassword, context);

//        edtEmailId = (EditText) findViewById(R.id.edtEmailId);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** API CALL **/
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    String url = MainApplication.mainUrl + "authorization/submitforgotpassworddetails";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username",edtEmailId.getText().toString());
                    if(!Globle.isNetworkAvailable(ForgotPassword.this))
                    {
                        Toast.makeText(ForgotPassword.this, "Please check your network connection", Toast.LENGTH_SHORT).show();

                    } else {
                        VolleyCallNew volleyCall = new VolleyCallNew();
                        volleyCall.sendRequest(context, url, mActivity, null, "resetPassword", params);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /** END OF API CALL **/            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    /**RESPONSE OF API CALL **/
    public void resetPassword(JSONObject jsonData) {
        try {

            Log.e("SERVER", "logIn" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                progressBar.setVisibility(View.GONE);
                edtEmailId.setText("");

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(ForgotPassword.this, SignIn.class);
//                startActivity(intent);
//                finish();

            }else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
