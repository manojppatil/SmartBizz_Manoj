package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.newUI.adapter.ViewPagerAdapterBanner;
import com.eduvanz.newUI.pojo.ViewPagerDashboardPOJO;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class BannerActivity extends AppCompatActivity {

    ViewPager viewPagerBanner;
    ViewPagerAdapterBanner viewPagerAdapterBanner;
    CirclePageIndicator circlePageIndicatorBanner;
    ArrayList<ViewPagerDashboardPOJO> viewPagerBannerPOJOArrayList;
    ArrayList<Integer> images;
    TextView textViewBannerDetail;
    Button buttonCheckEligiblity;
    MainApplication mainApplication;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        context = this;
        mainApplication = new MainApplication();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Special Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        viewPagerBanner = (ViewPager) findViewById(R.id.viewPager_bannerpage);
        circlePageIndicatorBanner = (CirclePageIndicator) findViewById(R.id.viewPageIndicator_bannerpage);
        final float density = getResources().getDisplayMetrics().density;
        circlePageIndicatorBanner.setRadius(4 * density);

        images = new ArrayList<>();
        images.add(R.drawable.bannersplaceholder);
        images.add(R.drawable.bannersplaceholder);
        images.add(R.drawable.bannersplaceholder);

        viewPagerBannerPOJOArrayList = new ArrayList<>();
        viewPagerAdapterBanner = new ViewPagerAdapterBanner(context, images);

        viewPagerBanner.setAdapter(viewPagerAdapterBanner);
        circlePageIndicatorBanner.setViewPager(viewPagerBanner);

        textViewBannerDetail = (TextView) findViewById(R.id.textView_bannerDetail);
        mainApplication.applyTypeface(textViewBannerDetail, context);

        buttonCheckEligiblity = (Button) findViewById(R.id.button_bannerdetail_checkeligiblity);
        mainApplication.applyTypeface(buttonCheckEligiblity, context);

        buttonCheckEligiblity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EligibilityCheck.class);
                startActivity(intent);
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
