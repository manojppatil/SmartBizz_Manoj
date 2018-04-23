package com.eduvanz.newUI.newViews;

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

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;

public class SignUp extends AppCompatActivity {

    TextView textViewUserName, textView1;
    Button buttonSignIn, buttonSignUp;
    MainApplication mainApplication;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mainApplication = new MainApplication();
        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign Up");
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

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, EligibilityCheck.class);
                startActivity(intent);
                finish();
            }
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
}
