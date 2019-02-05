package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.database.DBAdapter;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.fragments.EligibilityCheckFragment_1;
import com.eduvanzapplication.newUI.fragments.EligibilityCheckFragment_3;
import com.eduvanzapplication.newUI.fragments.EligibilityCheckFragment_4;
import com.eduvanzapplication.newUI.fragments.EligibilityCheckFragment_5;
import com.eduvanzapplication.newUI.fragments.EligibilityCheckFragment_6;
import com.eduvanzapplication.newUI.services.LocationService;

public class EligibilityCheck extends AppCompatActivity {

    FrameLayout frameLayoutCheckEligibility;
    Context context;
    String fillinstutute ="",lead_id = "",application_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_eligibility_check);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Eduvanz");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
            toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
            context = getApplicationContext();

            try {
                Bundle bundle = getIntent().getExtras();
                fillinstutute = bundle.getString("fillinstutute");
                lead_id = bundle.getString("lead_id");
                application_id = bundle.getString("application_id");
            } catch (Exception e) {
                e.printStackTrace();
            }

            MainApplication.lead_id = lead_id;
            MainApplication.application_id = application_id;


            frameLayoutCheckEligibility = (FrameLayout) findViewById(R.id.frameLayout_eligibilityCheck);
            if(fillinstutute == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_eligibilityCheck, new EligibilityCheckFragment_1()).commit();
            }
            else {
//                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_eligibilityCheck, new EligibilityCheckFragment_6()).commit();
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_eligibilityCheck, new EligibilityCheckFragment_5()).commit();
            }
            startService(new Intent(EligibilityCheck.this, LocationService.class));

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(EligibilityCheck.this,className, name, errorMsg, errorMsgDetails, errorLine);
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
