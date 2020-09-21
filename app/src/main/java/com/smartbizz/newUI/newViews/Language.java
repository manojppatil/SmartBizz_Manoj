package com.smartbizz.newUI.newViews;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartbizz.R;
import com.smartbizz.newUI.MainApplication;

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
