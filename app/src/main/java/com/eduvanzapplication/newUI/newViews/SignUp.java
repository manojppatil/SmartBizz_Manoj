package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;

public class SignUp extends AppCompatActivity {

    TextView textViewUserName, textView1;
    Button buttonSignIn, buttonSignUp;
    MainApplication mainApplication;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_sign_up);
            mainApplication = new MainApplication();
            context = this;

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.title_sign_up);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
            toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

            textViewUserName = (TextView) findViewById(R.id.textView_signup_username);
            mainApplication.applyTypeface(textViewUserName, context);
            textView1 = (TextView) findViewById(R.id.textView_signup_1);
            mainApplication.applyTypeface(textView1, context);

            buttonSignIn = (Button) findViewById(R.id.button_signup_signin);
            mainApplication.applyTypeface(buttonSignIn, context);

            buttonSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SignUp.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }
            });

            buttonSignUp = (Button) findViewById(R.id.button_signup_signup);
            mainApplication.applyTypeface(buttonSignUp, context);

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(SignUp.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }

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
}
