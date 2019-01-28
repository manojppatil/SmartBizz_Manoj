package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;

public class Language extends AppCompatActivity {

    TextView txtEnglish, txtMarathi, txtHindi;
    ImageView imgEnglish, imgMarathi, imgHindi;
    MainApplication mainApplication;
    Context context;
    AppCompatActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_language);

            context = this;
            mActivity = this;
            mainApplication = new MainApplication();

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Select Your Language");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
            toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

            txtEnglish = (TextView) findViewById(R.id.txtEnglish);
            mainApplication.applyTypeface(txtEnglish, context);
            txtMarathi = (TextView) findViewById(R.id.txtMarathi);
            mainApplication.applyTypeface(txtMarathi, context);
            txtHindi = (TextView) findViewById(R.id.txtHindi);

            imgEnglish = (ImageView) findViewById(R.id.imgEnglish);
            imgMarathi = (ImageView) findViewById(R.id.imgMarathi);
            imgHindi = (ImageView) findViewById(R.id.imgHindi);
            mainApplication.applyTypeface(txtHindi, context);

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(Language.this,className, name, errorMsg, errorMsgDetails, errorLine);
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
