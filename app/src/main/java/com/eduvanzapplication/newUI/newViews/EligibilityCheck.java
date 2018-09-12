package com.eduvanzapplication.newUI.newViews;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.fragments.EligibilityCheckFragment_1;

public class EligibilityCheck extends AppCompatActivity {

    FrameLayout frameLayoutCheckEligibility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eligibility_check);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Eligiblity Check");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        frameLayoutCheckEligibility = (FrameLayout) findViewById(R.id.frameLayout_eligibilityCheck);
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_eligibilityCheck, new EligibilityCheckFragment_1()).commit();

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
