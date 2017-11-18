package com.eduvanz.newUI.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.eduvanz.R;
import com.eduvanz.newUI.adapter.ViewPagerAdapterDashboard;
import com.eduvanz.newUI.newViews.BannerActivity;
import com.eduvanz.newUI.newViews.ContactUs;
import com.eduvanz.newUI.newViews.EligibilityCheck;
import com.eduvanz.newUI.newViews.Faq;
import com.eduvanz.newUI.newViews.HowItWorks;
import com.eduvanz.newUI.newViews.LoanApplication;
import com.eduvanz.newUI.newViews.MyProfileNew;
import com.eduvanz.newUI.pojo.ViewPagerDashboardPOJO;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class DashboardFragmentNew extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    ViewPager viewPagerDashboard;
    ViewPagerAdapterDashboard viewPagerAdapterDashboard;
    CirclePageIndicator circlePageIndicatorDashboard;
    ArrayList<ViewPagerDashboardPOJO> viewPagerDashboardPOJOArrayList;
    ArrayList<Integer> images;
    Button buttonCheckeligiblity;
    LinearLayout linearLayoutMyProfile, linearLayoutCallUs, linearLayoutFaq, linearLayoutContactus,
                 linearLayoutHowItWorks;

    public DashboardFragmentNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_fragment_new, container, false);
        context = getContext();
        mFragment = new DashboardFragmentNew();

        viewPagerDashboard = (ViewPager) view.findViewById(R.id.viewPager_dashboard);
        circlePageIndicatorDashboard = (CirclePageIndicator) view.findViewById(R.id.viewPageIndicator);
        final float density = getResources().getDisplayMetrics().density;
        circlePageIndicatorDashboard.setRadius(4 * density);

        images = new ArrayList<>();
        images.add(R.drawable.bannersplaceholder);
        images.add(R.drawable.bannersplaceholder);
        images.add(R.drawable.bannersplaceholder);

        viewPagerDashboardPOJOArrayList = new ArrayList<>();
        viewPagerAdapterDashboard = new ViewPagerAdapterDashboard(context, images);

        viewPagerDashboard.setAdapter(viewPagerAdapterDashboard);
        circlePageIndicatorDashboard.setViewPager(viewPagerDashboard);

        buttonCheckeligiblity = (Button) view.findViewById(R.id.button_dashboard_checkeligiblity);
        buttonCheckeligiblity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EligibilityCheck.class);
                startActivity(intent);
            }
        });

        linearLayoutMyProfile = (LinearLayout) view.findViewById(R.id.linearLayout_dashboard_myprofile);
        linearLayoutMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyProfileNew.class);
                startActivity(intent);
            }
        });

        linearLayoutContactus = (LinearLayout) view.findViewById(R.id.linearLayout_dashboard_contactus);
        linearLayoutContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContactUs.class);
                startActivity(intent);
            }
        });

        linearLayoutFaq = (LinearLayout) view.findViewById(R.id.linearLayout_dashboard_faq);
        linearLayoutFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Faq.class);
                startActivity(intent);
            }
        });

        linearLayoutHowItWorks= (LinearLayout) view.findViewById(R.id.linearLayout_dashboard_howitworks);
        linearLayoutHowItWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HowItWorks.class);
                startActivity(intent);
            }
        });

        linearLayoutCallUs = (LinearLayout) view.findViewById(R.id.linearLayout_dashboard_callus);
        linearLayoutCallUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoanApplication.class);
                startActivity(intent);
            }
        });

        return view;
    }//-----------------------------------END OF ON CREATE----------------------------------------//

}
