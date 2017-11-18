package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eduvanz.MainApplication;
import com.eduvanz.R;

public class GetMobileNo extends AppCompatActivity {

    TextView textViewToolbar;
    MainApplication mainApplication;
    Context mContext;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_mobile_no);

        mContext = this;
        mainApplication = new MainApplication();

        textViewToolbar = (TextView) findViewById(R.id.textView_getmobileno);
        mainApplication.applyTypeface(textViewToolbar, mContext);

        button = (Button) findViewById(R.id.button_getotp);
        mainApplication.applyTypeface(button, mContext);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetMobileNo.this, OtpValidation.class);
                startActivity(intent);
            }
        });

    }
}
