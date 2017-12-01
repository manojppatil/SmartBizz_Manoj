package com.eduvanz.newUI.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;
import com.eduvanz.newUI.SharedPref;
import com.eduvanz.newUI.newViews.SignUp;
import com.eduvanz.newUI.webviews.WebViewAboutUs;
import com.eduvanz.newUI.webviews.WebViewFAQs;
import com.eduvanz.newUI.adapter.ViewPagerAdapterDashboard;
import com.eduvanz.newUI.newViews.BannerActivity;
import com.eduvanz.newUI.newViews.ContactUs;
import com.eduvanz.newUI.newViews.EligibilityCheck;
import com.eduvanz.newUI.newViews.HowItWorks;
import com.eduvanz.newUI.newViews.LoanApplication;
import com.eduvanz.newUI.newViews.MyProfileNew;
import com.eduvanz.newUI.newViews.Notification;
import com.eduvanz.newUI.pojo.ViewPagerDashboardPOJO;
import com.eduvanz.newUI.VolleyCallNew;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eduvanz.newUI.MainApplication.TAG;

public class DashboardFragmentNew extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    static ViewPager viewPagerDashboard;
    static ViewPagerAdapterDashboard viewPagerAdapterDashboard;
    static TextView textViewDealTitle;
    static String dealID = "", userName = "", userId = "";
    CirclePageIndicator circlePageIndicatorDashboard;
    ArrayList<ViewPagerDashboardPOJO> viewPagerDashboardPOJOArrayList;
    static Button buttonCheckeligiblity, buttonApplyNow, buttonContinueLoanApplication, buttonContinueProfile;
    static LinearLayout linearLayoutMyProfile, linearLayoutCallUs, linearLayoutFaq, linearLayoutContactus,
            linearLayoutHowItWorks, linearLayoutDeal, linearLayoutEligiblityChekck,
            linearLayoutApplyNow, linearLayoutContinueApplication, linearLayoutMailUs, linearLayoutContinueProfile, linearLayoutAboutUs;
    MainApplication mainApplication;
    static TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8,
            textView9, textView10, textView11, textView12, textView13, textView14, textView15, textViewCongrats;
    SharedPref sharedPref;

    static LinearLayout notification_linearLayout;
    static String borrower = null, coBorrower = null, coBorrowerDocument = null,
            eligibility = null, borrowerDocument = null, signDocument = null,
            kyc = null, profileDashboardStats = null;


    public DashboardFragmentNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_fragment_new, container, false);
        context = getContext();
        sharedPref = new SharedPref();
        mFragment = new DashboardFragmentNew();
        mainApplication = new MainApplication();

        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            userName = sharedPreferences.getString("first_name", "User");
            userId = sharedPreferences.getString("logged_id", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        textView1 = (TextView) view.findViewById(R.id.textViewdash_1);
        mainApplication.applyTypeface(textView1, context);
        textView2 = (TextView) view.findViewById(R.id.textViewdash_2);
        mainApplication.applyTypeface(textView2, context);
        textView3 = (TextView) view.findViewById(R.id.textViewdash_3);
        mainApplication.applyTypeface(textView3, context);
        textView4 = (TextView) view.findViewById(R.id.textViewdash_4);
        mainApplication.applyTypeface(textView4, context);
        textView5 = (TextView) view.findViewById(R.id.textViewdash_5);
        mainApplication.applyTypeface(textView5, context);
        textView6 = (TextView) view.findViewById(R.id.textViewdash_6);
        mainApplication.applyTypeface(textView6, context);
        textView7 = (TextView) view.findViewById(R.id.textViewdash_7);
        mainApplication.applyTypeface(textView7, context);
        textView8 = (TextView) view.findViewById(R.id.textViewdash_8);
        mainApplication.applyTypeface(textView8, context);
        textView9 = (TextView) view.findViewById(R.id.textViewdash_9);
        mainApplication.applyTypeface(textView9, context);
        textView10 = (TextView) view.findViewById(R.id.textViewdash_10);
        mainApplication.applyTypeface(textView10, context);
        textView11 = (TextView) view.findViewById(R.id.textViewdash_11);
        mainApplication.applyTypeface(textView11, context);
        textView12 = (TextView) view.findViewById(R.id.textViewdash_12);
        mainApplication.applyTypeface(textView12, context);
        textView13 = (TextView) view.findViewById(R.id.textViewdash_13);

        mainApplication.applyTypeface(textView13, context);
        textView14 = (TextView) view.findViewById(R.id.textViewdash_14);
        mainApplication.applyTypeface(textView13, context);
        textView15 = (TextView) view.findViewById(R.id.textViewdash_15);
        mainApplication.applyTypefaceBold(textView13, context);

        textViewCongrats = (TextView) view.findViewById(R.id.textView_congratsmessage);
        mainApplication.applyTypeface(textViewCongrats, context);

        viewPagerDashboard = (ViewPager) view.findViewById(R.id.viewPager_dashboard);
        circlePageIndicatorDashboard = (CirclePageIndicator) view.findViewById(R.id.viewPageIndicator);
        final float density = getResources().getDisplayMetrics().density;
        circlePageIndicatorDashboard.setRadius(4 * density);

        viewPagerDashboardPOJOArrayList = new ArrayList<>();
        viewPagerAdapterDashboard = new ViewPagerAdapterDashboard(context, viewPagerDashboardPOJOArrayList);

        viewPagerDashboard.setAdapter(viewPagerAdapterDashboard);
        circlePageIndicatorDashboard.setViewPager(viewPagerDashboard);

        buttonCheckeligiblity = (Button) view.findViewById(R.id.button_dashboard_checkeligiblity);
        mainApplication.applyTypeface(buttonCheckeligiblity, context);
        buttonCheckeligiblity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EligibilityCheck.class);
                startActivity(intent);
            }
        });

        buttonApplyNow = (Button) view.findViewById(R.id.button_applynow_loan);
        mainApplication.applyTypeface(buttonApplyNow, context);
        buttonApplyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoanApplication.class);
                startActivity(intent);
            }
        });

        linearLayoutEligiblityChekck = (LinearLayout) view.findViewById(R.id.linearLayout_eligiblitycheck);
        linearLayoutApplyNow = (LinearLayout) view.findViewById(R.id.linearLayout_applyloan);
        linearLayoutContinueApplication = (LinearLayout) view.findViewById(R.id.linearLayout_continue_application);
        linearLayoutContinueProfile = (LinearLayout) view.findViewById(R.id.linearLayout_profileincomplete);

        if (sharedPref.getLoginDone(context)) {
            linearLayoutApplyNow.setVisibility(View.VISIBLE);
            linearLayoutEligiblityChekck.setVisibility(View.GONE);
            textViewCongrats.setText("Congrats " + userName + " you are now eligible for getting loan");
        }else {

        }


        linearLayoutMyProfile = (LinearLayout) view.findViewById(R.id.linearLayout_dashboard_myprofile);
        linearLayoutMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sharedPref.getLoginDone(context)) {
                    Intent intent = new Intent(context, MyProfileNew.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(context, SignUp.class);
                    startActivity(intent);
                }


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
                Intent intent = new Intent(context, WebViewFAQs.class);
                startActivity(intent);
            }
        });

        linearLayoutAboutUs = (LinearLayout) view.findViewById(R.id.linearLayout_dashboard_aboutus);
        linearLayoutAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebViewAboutUs.class);
                startActivity(intent);
            }
        });

        linearLayoutHowItWorks = (LinearLayout) view.findViewById(R.id.linearLayout_dashboard_howitworks);
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
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0224523689"));
                startActivity(intent);
            }
        });

        linearLayoutMailUs = (LinearLayout) view.findViewById(R.id.linearLayout_mailUs);
        linearLayoutMailUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               /* Create the Intent */
//                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//
//                /* Fill it with Data */
//                emailIntent.setType("plain/text");
//                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"support@eduvanz.com"});
////                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
////                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");
//
//                /* Send it off to the Activity-Chooser */
//                context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                shareToGMail(new String[]{"support@eduvanz.com"}, "", "");
            }
        });

        notification_linearLayout = (LinearLayout) view.findViewById(R.id.notification_linearLayout);
        notification_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Notification.class);
                startActivity(intent);
            }
        });

        textViewDealTitle = (TextView) view.findViewById(R.id.textView_dealTitle);
        mainApplication.applyTypeface(textViewDealTitle, context);

        linearLayoutDeal = (LinearLayout) view.findViewById(R.id.linearLayout_dashdeal);
        linearLayoutDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BannerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("bannerID", dealID);
                bundle.putString("from_deal", "1");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        buttonContinueLoanApplication = (Button) view.findViewById(R.id.button_dashboard_continueapplication);
        mainApplication.applyTypeface(buttonContinueLoanApplication, context);
        buttonContinueLoanApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoanApplication.class);
                if(borrowerDocument.equalsIgnoreCase("1")){
                    Bundle bundle = new Bundle();
                    bundle.putString("toCoBorrower", "1");
                    intent.putExtras(bundle);
                }else if(coBorrowerDocument.equalsIgnoreCase("1") && borrowerDocument.equalsIgnoreCase("1")){
                    Bundle bundle = new Bundle();
                    bundle.putString("toDocUpload", "1");
                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }
        });

        buttonContinueProfile = (Button) view.findViewById(R.id.button_dashboard_profileincomplete);
        mainApplication.applyTypeface(buttonContinueProfile, context);
        buttonContinueProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyProfileNew.class);
                startActivity(intent);
            }
        });


        /** API CALL GET DASHBOARD BANNER**/
        try {
            String url = MainApplication.mainUrl + "mobileadverstisement/getBanner";
            Map<String, String> params = new HashMap<String, String>();
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "dashboardBanner", params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /** API CALL GET DEAL**/
        try {
            String url = MainApplication.mainUrl + "mobileadverstisement/getDeal";
            Map<String, String> params = new HashMap<String, String>();
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "getDeal", params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /** API CALL POST LOGIN DASHBOARD STATUS **/
        try {
            String url = MainApplication.mainUrl + "dashboard/getStudentDashbBoardStatus";
            Map<String, String> params = new HashMap<String, String>();
            params.put("studentId", userId);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "studentDashbBoardStatus", params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }//-----------------------------------END OF ON CREATE----------------------------------------//

    public void setDashboardImages(JSONObject jsonData) {
        try {
            Log.e(MainApplication.TAG, "setDashboardImages: " + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONArray jsonArray = jsonObject.getJSONArray("banner");

                viewPagerDashboardPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    ViewPagerDashboardPOJO viewPagerDashboardPOJO = new ViewPagerDashboardPOJO();
                    viewPagerDashboardPOJO.id = jsonObject1.getString("id");
                    viewPagerDashboardPOJO.title = jsonObject1.getString("title");
                    viewPagerDashboardPOJO.image = jsonObject1.getString("image");
                    viewPagerDashboardPOJOArrayList.add(viewPagerDashboardPOJO);
                }
                viewPagerAdapterDashboard = new ViewPagerAdapterDashboard(context, viewPagerDashboardPOJOArrayList);
                viewPagerDashboard.setAdapter(viewPagerAdapterDashboard);

            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDeal(JSONObject jsonData) {
        try {
            Log.e(MainApplication.TAG, "getDeal: " + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONArray jsonArray = jsonObject.getJSONArray("deal");

                JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                textViewDealTitle.setText(jsonObject1.getString("title"));

                dealID = jsonObject1.getString("id");
            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shareToGMail(String[] email, String subject, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
        final PackageManager pm = context.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
        ResolveInfo best = null;
        for(final ResolveInfo info : matches)
            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
        context.startActivity(emailIntent);
    }


    public void setProfileDashbBoardStatus(JSONObject jsonDataO) {
        Log.e(TAG, "getScrappingdates: " + jsonDataO);
        try {
            if (jsonDataO.getInt("status") == 1) {

                JSONObject mObject = jsonDataO.optJSONObject("result");
                borrower = mObject.getString("borrower");
                coBorrower = mObject.getString("coBorrower");
                coBorrowerDocument = mObject.getString("coBorrowerDocument");
                borrowerDocument = mObject.getString("borrowerDocument");
                signDocument = mObject.getString("signDocument");
                kyc = mObject.getString("kyc");
                profileDashboardStats = mObject.getString("profileDashboardStats");
                eligibility = mObject.getString("eligibility");

                if(borrowerDocument.equalsIgnoreCase("1")){
                    linearLayoutContinueApplication.setVisibility(View.VISIBLE);
                    linearLayoutApplyNow.setVisibility(View.GONE);
                    linearLayoutEligiblityChekck.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
