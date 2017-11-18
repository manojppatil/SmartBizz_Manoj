package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.eduvanz.MainApplication;
import com.eduvanz.R;

public class SignIn extends AppCompatActivity {

    TextView textViewUserName, textView1, textView2, textView3;
    Button buttonSignIn;
    MainApplication mainApplication;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mainApplication = new MainApplication();
        context = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        textViewUserName = (TextView) findViewById(R.id.textView_signin_username);
        mainApplication.applyTypeface(textViewUserName, context);
        textView1 = (TextView) findViewById(R.id.textView_signin_1);
        mainApplication.applyTypeface(textView1, context);
        textView2 = (TextView) findViewById(R.id.textView_signin_2);
        mainApplication.applyTypeface(textView2, context);
        textView3 = (TextView) findViewById(R.id.textView_signin_3);
        mainApplication.applyTypeface(textView3, context);
        buttonSignIn = (Button) findViewById(R.id.button_signin_signin);
        mainApplication.applyTypeface(buttonSignIn, context);
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
