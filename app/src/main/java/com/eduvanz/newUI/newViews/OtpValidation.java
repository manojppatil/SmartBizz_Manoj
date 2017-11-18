package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduvanz.MainApplication;
import com.eduvanz.R;

public class OtpValidation extends AppCompatActivity {

    TextView textViewToolbar;
    MainApplication mainApplication;
    Context mContext;
    Button button;
    LinearLayout linearLayoutResendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_validation);

        mContext = this;
        mainApplication = new MainApplication();

        textViewToolbar = (TextView) findViewById(R.id.textView_validateotp);
        mainApplication.applyTypeface(textViewToolbar, mContext);

        button = (Button) findViewById(R.id.button_continue_validateotp);
        mainApplication.applyTypeface(button, mContext);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpValidation.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        linearLayoutResendOtp = (LinearLayout) findViewById(R.id.linearLayout_resendOtp);
        linearLayoutResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtpValidation.this, GetMobileNo.class);
                startActivity(intent);
            }
        });
    }
}
