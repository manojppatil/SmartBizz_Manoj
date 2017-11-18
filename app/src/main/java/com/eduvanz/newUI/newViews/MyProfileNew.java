package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.eduvanz.MainApplication;
import com.eduvanz.R;

public class MyProfileNew extends AppCompatActivity {

    TextView textViewUserName, textViewPercentage, textView1, textView2, textView3, textView4, textView5;
    MainApplication mainApplication;
    Context context;
    Button buttonCompleteNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_new);
        mainApplication = new MainApplication();
        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        textViewUserName = (TextView) findViewById(R.id.textView_profile_username);
        mainApplication.applyTypeface(textViewUserName, context);
        textViewPercentage = (TextView) findViewById(R.id.textView_profilecomplete_percentage);
        mainApplication.applyTypeface(textViewPercentage, context);
        textView1 = (TextView) findViewById(R.id.textView_profile1);
        mainApplication.applyTypeface(textView1, context);
        textView2 = (TextView) findViewById(R.id.textView_profile2);
        mainApplication.applyTypeface(textView2, context);
        textView3 = (TextView) findViewById(R.id.textView_profile3);
        mainApplication.applyTypeface(textView3, context);
        textView4 = (TextView) findViewById(R.id.textView_profile4);
        mainApplication.applyTypeface(textView4, context);
        textView5 = (TextView) findViewById(R.id.textView_profile5);
        mainApplication.applyTypeface(textView5, context);

        buttonCompleteNow = (Button) findViewById(R.id.button_mtprofile_completenow);
        mainApplication.applyTypeface(buttonCompleteNow, context);

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
