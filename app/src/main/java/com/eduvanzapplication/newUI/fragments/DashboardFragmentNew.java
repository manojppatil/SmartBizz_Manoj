package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.adapter.LeadsAdapter;
import com.eduvanzapplication.newUI.newViews.SignUp;
import com.eduvanzapplication.newUI.pojo.MLeads;
import com.eduvanzapplication.newUI.adapter.ViewPagerAdapterDashboard;
import com.eduvanzapplication.newUI.newViews.BannerActivity;
import com.eduvanzapplication.newUI.newViews.EligibilityCheck;
import com.eduvanzapplication.newUI.newViews.LoanApplication;
import com.eduvanzapplication.newUI.newViews.MyProfileNew;
import com.eduvanzapplication.newUI.newViews.Notification;
import com.eduvanzapplication.newUI.pojo.ViewPagerDashboardPOJO;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eduvanzapplication.newUI.MainApplication.TAG;

public class DashboardFragmentNew extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    static ViewPager viewPagerDashboard;
    static ViewPagerAdapterDashboard viewPagerAdapterDashboard;
    static TextView textViewDealTitle;
    static String dealID = "", userName = "", userId = "", student_id = "",mobile_no ="" ,auth_token ="", lead_id="";
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
    List<MLeads> mLeadsArrayList = new ArrayList<>();

    public static RecyclerView recyclerLeads;
    LeadsAdapter leadsAdapter;

    View view;

    public DashboardFragmentNew() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
//        if (height < 1080) {
//            view = inflater.inflate(R.layout.demo1, container, false);
//        } else {
            view = inflater.inflate(R.layout.fragment_dashboard_fragment_new, container, false);
        //}
        try {
            context = getContext();
            sharedPref = new SharedPref();
            mFragment = new DashboardFragmentNew();
            mainApplication = new MainApplication();

            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                userName = sharedPreferences.getString("first_name", "User");
                userId = sharedPreferences.getString("logged_id", "");
                mobile_no = sharedPreferences.getString("mobile_no", "");
                student_id = sharedPreferences.getString("student_id", "");
                auth_token = sharedPreferences.getString("auth_token", "");
                lead_id = sharedPreferences.getString("lead_id", "");

            } catch (Exception e) {
                e.printStackTrace();
            }
            MainApplication.student_id = student_id;
            MainApplication.auth_token = auth_token;

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

            recyclerLeads = (RecyclerView) view.findViewById(R.id.recyclerLeads);

            viewPagerDashboard = (ViewPager) view.findViewById(R.id.viewPager_dashboard);
            circlePageIndicatorDashboard = (CirclePageIndicator) view.findViewById(R.id.viewPageIndicator);
            final float density = getResources().getDisplayMetrics().density;
            circlePageIndicatorDashboard.setRadius(4 * density);

            viewPagerDashboardPOJOArrayList = new ArrayList<>();
            viewPagerAdapterDashboard = new ViewPagerAdapterDashboard(context, viewPagerDashboardPOJOArrayList);

            viewPagerDashboard.setAdapter(viewPagerAdapterDashboard);
            circlePageIndicatorDashboard.setViewPager(viewPagerDashboard);

            buttonApplyNow = (Button) view.findViewById(R.id.btnApplyNow);
            mainApplication.applyTypeface(buttonApplyNow, context);
            buttonApplyNow.setOnClickListener(new View.OnClickListener() {
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

                    if (sharedPref.getLoginDone(context)) {
                        Intent intent = new Intent(context, MyProfileNew.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, SignUp.class);
                        startActivity(intent);
                    }
                }
            });

            linearLayoutCallUs = (LinearLayout) view.findViewById(R.id.linearLayout_dashboard_callus);
            linearLayoutCallUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:022-49733674"));
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


//            /** API CALL GET DASHBOARD BANNER**/
//            try {
//                String url = MainApplication.mainUrl + "mobileadverstisement/getBanner";//http://159.89.204.41/eduvanzApi/mobileadverstisement/getBanner
//                Map<String, String> params = new HashMap<String, String>();
//                if(!Globle.isNetworkAvailable(context))
//                {
//                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//
////{"result":{"banner":[{"id":"1","title":"FINANCING YOUR FUTURE",
//// "image":"http:\/\/159.89.204.41\/eduvanzbeta\/uploads\/mobileadvertisement\/1\/image_1513427852.png"}]},
//// "status":1,"message":"success"}
//
//                } else {
//                    VolleyCallNew volleyCall = new VolleyCallNew();//http://192.168.0.110/eduvanzapi/mobileadverstisement/getBanner
//                    volleyCall.sendRequest(context, url, null, mFragment, "dashboardBanner", params);
//                }
//            } catch (Exception e) {
//                String className = this.getClass().getSimpleName();
//                String name = new Object() {
//                }.getClass().getEnclosingMethod().getName();
//                String errorMsg = e.getMessage();
//                String errorMsgDetails = e.getStackTrace().toString();
//                String errorLine = String.valueOf(e.getStackTrace()[0]);
//                Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
//            }

//            /** API CALL GET DEAL**/
//            try {
//                String url = MainApplication.mainUrl + "mobileadverstisement/getDeal";
//                Map<String, String> params = new HashMap<String, String>();
//                if(!Globle.isNetworkAvailable(context))
//                {
//                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//
//                } else {
//                    VolleyCallNew volleyCall = new VolleyCallNew();//http://192.168.0.110/eduvanzapi/mobileadverstisement/getDeal
//                    volleyCall.sendRequest(context, url, null, mFragment, "getDeal", params);
//                }
//            } catch (Exception e) {
//                String className = this.getClass().getSimpleName();
//                String name = new Object() {
//                }.getClass().getEnclosingMethod().getName();
//                String errorMsg = e.getMessage();
//                String errorMsgDetails = e.getStackTrace().toString();
//                String errorLine = String.valueOf(e.getStackTrace()[0]);
//                Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
//            }

            /** API CALL POST LOGIN DASHBOARD Details **/
            try {
                String url = MainApplication.mainUrl + "dashboard/getDashboardDetails";
                Map<String, String> params = new HashMap<String, String>();
//                params.put("studentId","" );
                params.put("studentId", student_id);
                if (!Globle.isNetworkAvailable(context)) {
                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                } else {
                    VolleyCallNew volleyCall = new VolleyCallNew();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
                    volleyCall.sendRequest(context, url, null, mFragment, "studentDashbBoardDetails", params, MainApplication.auth_token);
                }
            } catch (Exception e) {
                String className = this.getClass().getSimpleName();
                String name = new Object() {
                }.getClass().getEnclosingMethod().getName();
                String errorMsg = e.getMessage();
                String errorMsgDetails = e.getStackTrace().toString();
                String errorLine = String.valueOf(e.getStackTrace()[0]);
                Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
            }

//            /** API CALL POST LOGIN DASHBOARD STATUS **/
//            try {
//                String url = MainApplication.mainUrl + "dashboard/getStudentDashbBoardStatus";
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("studentId", userId);
//                if(!Globle.isNetworkAvailable(context))
//                {
//                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                } else {
//                    VolleyCallNew volleyCall = new VolleyCallNew();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
//                    volleyCall.sendRequest(context, url, null, mFragment, "studentDashbBoardStatus", params);
//                }
//            } catch (Exception e) {
//                String className = this.getClass().getSimpleName();
//                String name = new Object() {
//                }.getClass().getEnclosingMethod().getName();
//                String errorMsg = e.getMessage();
//                String errorMsgDetails = e.getStackTrace().toString();
//                String errorLine = String.valueOf(e.getStackTrace()[0]);
//                Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
//            }

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }

        return view;
    }//-----------------------------------END OF ON CREATE----------------------------------------//

    @Override
    public void onResume() {
        super.onResume();
        /** API CALL POST LOGIN DASHBOARD STATUS **/
        try {
            String url = MainApplication.mainUrl + "dashboard/getStudentDashbBoardStatus";
            Map<String, String> params = new HashMap<String, String>();
            params.put("studentId", userId);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "studentDashbBoardStatus", params,MainApplication.auth_token);
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void setDashboardImages(JSONObject jsonData) {
        try {
            //{"result":{"banner":[{"id":"1","title":"FINANCING YOUR FUTURE","image":"http:\/\/eduvanz.com\/admin\/uploads\/mobileadvertisement\/1\/image_1513427852.png"}]},"status":1,"message":"success"}
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
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void getDeal(JSONObject jsonData) {
        try {
            //{"result":{"deal":[{"id":"6","title":"Eduvanz provides low cost, smart loans to finance","image":"http:\/\/eduvanz.com\/admin\/uploads\/mobileadvertisement\/6\/image_1511869962.png"}]},"status":1,"message":"success"}
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
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void shareToGMail(String[] email, String subject, String content) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_TEXT, content);
            final PackageManager pm = context.getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
            ResolveInfo best = null;
            for (final ResolveInfo info : matches)
                if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
                    best = info;
            if (best != null)
                emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            context.startActivity(emailIntent);
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void setstudentDashbBoardDetails(JSONObject jsonDataO) {
        Log.e(TAG, "setProfileDashbBoardStatus: " + jsonDataO);
        try {//
            if (jsonDataO.getInt("status") == 1) {

                JSONObject mObject = jsonDataO.optJSONObject("result");
                String message = jsonDataO.getString("message");

                JSONArray jsonArray1 = mObject.getJSONArray("leadsData");
                mLeadsArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray1.length(); i++) {
                    MLeads mLeads = new MLeads();
                    JSONObject jsonleadStatus = jsonArray1.getJSONObject(i);

                    try {

                        mLeads.requested_loan_amount = jsonleadStatus.getString("requested_loan_amount");
                        mLeads.lead_id = jsonleadStatus.getString("lead_id");
                        mLeads.application_id = jsonleadStatus.getString("application_id");
                        mLeads.first_name = jsonleadStatus.getString("first_name");
                        mLeads.middle_name = jsonleadStatus.getString("middle_name");
                        mLeads.last_name = jsonleadStatus.getString("last_name");
                        mLeads.created_date_time = jsonleadStatus.getString("created_date_time");
                        mLeads.profession = jsonleadStatus.getString("profession");
                        mLeads.fk_applicant_type_id = jsonleadStatus.getString("fk_applicant_type_id");
                        mLeads.has_coborrower = jsonleadStatus.getString("has_coborrower");
                        mLeads.course_name = jsonleadStatus.getString("course_name");
                        mLeads.course_cost = jsonleadStatus.getString("course_cost");
                        mLeads.status_name = jsonleadStatus.getString("status_name");
                        mLeads.location_name = jsonleadStatus.getString("location_name");
                        mLeads.institute_name = jsonleadStatus.getString("institute_name");
                        mLeads.student_id = jsonleadStatus.getString("student_id");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mLeadsArrayList.add(mLeads);

                }

                leadsAdapter = new LeadsAdapter(mLeadsArrayList,context);
                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                recyclerLeads.setLayoutManager(horizontalLayoutManager);
                recyclerLeads.setAdapter(leadsAdapter);

                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("signed_appstatus", signDocument);
                editor.apply();
                editor.commit();

                if (borrower.equalsIgnoreCase("1")) {
                    linearLayoutContinueApplication.setVisibility(View.VISIBLE);
                    linearLayoutApplyNow.setVisibility(View.GONE);
                    linearLayoutEligiblityChekck.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }

//    public void setstudentDashbBoardDetails(JSONObject jsonDataO) {
//        Log.e(TAG, "setProfileDashbBoardStatus: " + jsonDataO);
//        try {//
//            if (jsonDataO.getInt("status") == 1) {
//
//                JSONObject mObject = jsonDataO.optJSONObject("result");
//                String student_id = mObject.getString("student_id");
//                String message = jsonDataO.getString("message");
//
//                JSONArray jsonArray1 = mObject.getJSONArray("country");
//                mLeadsArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray1.length(); i++) {
//                    MLeads mLeads = new MLeads();
//                    JSONObject mJsonti = jsonArray1.getJSONObject(i);
//
//                    try {
//                        mLeads.lead_id = mJsonti.getString("lead_id");
//                        mLeads.fk_user_id = mJsonti.getString("fk_user_id");
//                        mLeads.application_id = mJsonti.getString("application_id");
//                        mLeads.fk_institutes_id = mJsonti.getString("fk_institutes_id");
//                        mLeads.fk_course_id = mJsonti.getString("fk_course_id");
//                        mLeads.fk_insitutes_location_id = mJsonti.getString("fk_insitutes_location_id");
//                        mLeads.requested_loan_amount = mJsonti.getString("requested_loan_amount");
//                        mLeads.fk_lead_source_id = mJsonti.getString("fk_lead_source_id");
//                        mLeads.assigned_councellor = mJsonti.getString("assigned_councellor");
//                        mLeads.lead_owner = mJsonti.getString("lead_owner");
//                        mLeads.lender_id = mJsonti.getString("lender_id");
//                        mLeads.lead_created_by_id = mJsonti.getString("lead_created_by_id");
//                        mLeads.created_date_time = mJsonti.getString("created_date_time");
//                        mLeads.created_ip_address = mJsonti.getString("created_ip_address");
//                        mLeads.modified_by = mJsonti.getString("modified_by");
//                        mLeads.modified_date_time = mJsonti.getString("modified_date_time");
//                        mLeads.modified_ip_address = mJsonti.getString("modified_ip_address");
//                        mLeads.is_deleted = mJsonti.getString("is_deleted");
//                        mLeads.requested_tenure = mJsonti.getString("requested_tenure");
//                        mLeads.requested_roi = mJsonti.getString("requested_roi");
//                        mLeads.requested_emi = mJsonti.getString("requested_emi");
//                        mLeads.offered_amount = mJsonti.getString("offered_amount");
//                        mLeads.has_coborrower = mJsonti.getString("has_coborrower");
//                        mLeads.co_applicant_required = mJsonti.getString("co_applicant_required");
//                        mLeads.has_processing = mJsonti.getString("has_processing");
//                        mLeads.mverify_document_store_path = mJsonti.getString("mverify_document_store_path");
//                        mLeads.api_request_apirequestid = mJsonti.getString("api_request_apirequestid");
//                        mLeads.lead_status_id = mJsonti.getString("lead_status_id");
//                        mLeads.fk_lead_id = mJsonti.getString("fk_lead_id");
//                        mLeads.lead_status = mJsonti.getString("lead_status");
//                        mLeads.lead_sub_status = mJsonti.getString("lead_sub_status");
//                        mLeads.current_stage = mJsonti.getString("current_stage");
//                        mLeads.current_status = mJsonti.getString("current_status");
//                        mLeads.lead_drop_status = mJsonti.getString("lead_drop_status");
//                        mLeads.lead_reject_status = mJsonti.getString("lead_reject_status");
//                        mLeads.lead_initiated_datetime = mJsonti.getString("lead_initiated_datetime");
//                        mLeads.is_lead_owner_added = mJsonti.getString("is_lead_owner_added");
//                        mLeads.lead_owner_added_datetime = mJsonti.getString("lead_owner_added_datetime");
//                        mLeads.lead_owner_added_by = mJsonti.getString("lead_owner_added_by");
//                        mLeads.is_lead_counsellor_added = mJsonti.getString("is_lead_counsellor_added");
//                        mLeads.lead_counsellor_added_datetime = mJsonti.getString("lead_counsellor_added_datetime");
//                        mLeads.lead_counsellor_added_by = mJsonti.getString("lead_counsellor_added_by");
//                        mLeads.is_kyc_details_filled = mJsonti.getString("is_kyc_details_filled");
//                        mLeads.kyc_details_filled_datetime = mJsonti.getString("kyc_details_filled_datetime");
//                        mLeads.kyc_details_filled_by = mJsonti.getString("kyc_details_filled_by");
//                        mLeads.coborrower_added_datetime = mJsonti.getString("coborrower_added_datetime");
//                        mLeads.coborrower_added_by_id = mJsonti.getString("coborrower_added_by_id");
//                        mLeads.is_detailed_info_filled = mJsonti.getString("is_detailed_info_filled");
//                        mLeads.detailed_info_filled_datetime = mJsonti.getString("detailed_info_filled_datetime");
//                        mLeads.detailed_info_filled_by_id = mJsonti.getString("detailed_info_filled_by_id");
//                        mLeads.approval_request_sales_status = mJsonti.getString("approval_request_sales_status");
//                        mLeads.approval_request_sales_status_datetime = mJsonti.getString("approval_request_sales_status_datetime");
//                        mLeads.approval_request_sales_status_by_id = mJsonti.getString("approval_request_sales_status_by_id");
//                        mLeads.list_of_LAF_info_pending = mJsonti.getString("list_of_LAF_info_pending");
//                        mLeads.list_of_LAF_info_filled = mJsonti.getString("list_of_LAF_info_filled");
//                        mLeads.IPA_status = mJsonti.getString("IPA_status");
//                        mLeads.IPA_datetime = mJsonti.getString("IPA_datetime");
//                        mLeads.IPA_by_id = mJsonti.getString("IPA_by_id");
//                        mLeads.docs_upload_status = mJsonti.getString("docs_upload_status");
//                        mLeads.docs_upload_datetime = mJsonti.getString("docs_upload_datetime");
//                        mLeads.list_of_uplaoded_docs = mJsonti.getString("list_of_uplaoded_docs");
//                        mLeads.list_of_pendingdocs = mJsonti.getString("list_of_pendingdocs");
//                        mLeads.docs_verification_status = mJsonti.getString("docs_verification_status");
//                        mLeads.docs_verification_datetime = mJsonti.getString("docs_verification_datetime");
//                        mLeads.credit_approval_request_status = mJsonti.getString("credit_approval_request_status");
//                        mLeads.credit_approval_request_status_datetime = mJsonti.getString("credit_approval_request_status_datetime");
//                        mLeads.credit_approval_request_status_by_id = mJsonti.getString("credit_approval_request_status_by_id");
//                        mLeads.applicant_ekyc_status = mJsonti.getString("applicant_ekyc_status");
//                        mLeads.applicant_ekyc_datetime = mJsonti.getString("applicant_ekyc_datetime");
//                        mLeads.co_applicant_ekyc_status = mJsonti.getString("co_applicant_ekyc_status");
//                        mLeads.co_applicant_ekyc_datetime = mJsonti.getString("co_applicant_ekyc_datetime");
//                        mLeads.credit_assessment_status = mJsonti.getString("credit_assessment_status");
//                        mLeads.credit_assessment_by_id = mJsonti.getString("credit_assessment_by_id");
//                        mLeads.credit_assessment_datetime = mJsonti.getString("credit_assessment_datetime");
//                        mLeads.loan_product_selection_status = mJsonti.getString("loan_product_selection_status");
//                        mLeads.loan_product_by_id = mJsonti.getString("loan_product_by_id");
//                        mLeads.loan_product_datetime = mJsonti.getString("loan_product_datetime");
//                        mLeads.underwriting_status = mJsonti.getString("underwriting_status");
//                        mLeads.underwriting_by_id = mJsonti.getString("underwriting_by_id");
//                        mLeads.underwriting_datetime = mJsonti.getString("underwriting_datetime");
//                        mLeads.is_processing_fees_set = mJsonti.getString("is_processing_fees_set");
//                        mLeads.processing_fees_set_datetime = mJsonti.getString("processing_fees_set_datetime");
//                        mLeads.processing_fees_set_by_id = mJsonti.getString("processing_fees_set_by_id");
//                        mLeads.processing_fees_paid = mJsonti.getString("processing_fees_paid");
//                        mLeads.processing_fees_paid_datetime = mJsonti.getString("processing_fees_paid_datetime");
//                        mLeads.processing_fees_paid_by = mJsonti.getString("processing_fees_paid_by");
//                        mLeads.lender_creation_status = mJsonti.getString("lender_creation_status");
//                        mLeads.lender_creation_modified_datetime = mJsonti.getString("lender_creation_modified_datetime");
//                        mLeads.lender_creation_modified_by = mJsonti.getString("lender_creation_modified_by");
//                        mLeads.amort_creation_status = mJsonti.getString("amort_creation_status");
//                        mLeads.amort_creation_modified_datetime = mJsonti.getString("amort_creation_modified_datetime");
//                        mLeads.amort_creation_modified_by = mJsonti.getString("amort_creation_modified_by");
//                        mLeads.borrower_pan_ekyc_response = mJsonti.getString("borrower_pan_ekyc_response");
//                        mLeads.borrower_aadhar_ekyc_response = mJsonti.getString("borrower_aadhar_ekyc_response");
//                        mLeads.borrower_pan_ekyc_status = mJsonti.getString("borrower_pan_ekyc_status");
//                        mLeads.borrower_aadhar_ekyc_status = mJsonti.getString("borrower_aadhar_ekyc_status");
//                        mLeads.coborrower_pan_ekyc_response = mJsonti.getString("coborrower_pan_ekyc_response");
//                        mLeads.coborrower_aadhar_ekyc_response = mJsonti.getString("coborrower_aadhar_ekyc_response");
//                        mLeads.coborrower_aadhar_ekyc_status = mJsonti.getString("coborrower_aadhar_ekyc_status");
//                        mLeads.coborrower_pan_ekyc_status = mJsonti.getString("coborrower_pan_ekyc_status");
//                        mLeads.is_cam_uploaded = mJsonti.getString("is_cam_uploaded");
//                        mLeads.is_finbit_uploaded = mJsonti.getString("is_finbit_uploaded");
//                        mLeads.is_exception_uploaded = mJsonti.getString("is_exception_uploaded");
//                        mLeads.is_loan_agreement_uploaded = mJsonti.getString("is_loan_agreement_uploaded");
//                        mLeads.loan_agreement_uploaded_by = mJsonti.getString("loan_agreement_uploaded_by");
//                        mLeads.applicant_pan_verified_by = mJsonti.getString("applicant_pan_verified_by");
//                        mLeads.applicant_pan_verified_on = mJsonti.getString("applicant_pan_verified_on");
//                        mLeads.borrower_required_docs = mJsonti.getString("borrower_required_docs");
//                        mLeads.co_borrower_required_docs = mJsonti.getString("co_borrower_required_docs");
//                        mLeads.co_borrower_pending_docs = mJsonti.getString("co_borrower_pending_docs");
//                        mLeads.borrower_extra_required_docs = mJsonti.getString("borrower_extra_required_docs");
//                        mLeads.co_borrower_extra_required_docs = mJsonti.getString("co_borrower_extra_required_docs");
//                        mLeads.id = mJsonti.getString("id");
//                        mLeads.status_name = mJsonti.getString("status_name");
//                        mLeads.stage_id = mJsonti.getString("stage_id");
//                        mLeads.student_id = mJsonti.getString("student_id");
//                        mLeads.first_name = mJsonti.getString("first_name");
//                        mLeads.middle_name = mJsonti.getString("middle_name");
//                        mLeads.last_name = mJsonti.getString("last_name");
//                        mLeads.img = mJsonti.getString("img");
//                        mLeads.dob = mJsonti.getString("dob");
//                        mLeads.gender_id = mJsonti.getString("gender_id");
//                        mLeads.salutation_id = mJsonti.getString("salutation_id");
//                        mLeads.mobile = mJsonti.getString("mobile");
//                        mLeads.has_aadhar_pan = mJsonti.getString("has_aadhar_pan");
//                        mLeads.aadhar_no = mJsonti.getString("aadhar_no");
//                        mLeads.pan_no = mJsonti.getString("pan_no");
//                        mLeads.telephone = mJsonti.getString("telephone");
//                        mLeads.email = mJsonti.getString("email");
//                        mLeads.password = mJsonti.getString("password");
//                        mLeads.ln_id = mJsonti.getString("ln_id");
//                        mLeads.ln_url = mJsonti.getString("ln_url");
//                        mLeads.fb_id = mJsonti.getString("fb_id");
//                        mLeads.fb_url = mJsonti.getString("fb_url");
//                        mLeads.gplus_id = mJsonti.getString("gplus_id");
//                        mLeads.gplus_url = mJsonti.getString("gplus_url");
//                        mLeads.dependent_no = mJsonti.getString("dependent_no");
//                        mLeads.is_disabled = mJsonti.getString("is_disabled");
//                        mLeads.marital_status_id = mJsonti.getString("marital_status_id");
//                        mLeads.verification_link = mJsonti.getString("verification_link");
//                        mLeads.link_verified = mJsonti.getString("link_verified");
//                        mLeads.reset_link = mJsonti.getString("reset_link");
//                        mLeads.is_corporate_user = mJsonti.getString("is_corporate_user");
//                        mLeads.corporate_id = mJsonti.getString("corporate_id");
//                        mLeads.last_login = mJsonti.getString("last_login");
//                        mLeads.want_sms_notification = mJsonti.getString("want_sms_notification");
//                        mLeads.is_ls_imported_record = mJsonti.getString("is_ls_imported_record");
//                        mLeads.deleted_by_id = mJsonti.getString("deleted_by_id");
//                        mLeads.deleted_by_ip = mJsonti.getString("deleted_by_ip");
//                        mLeads.deleted_datetime = mJsonti.getString("deleted_datetime");
//                        mLeads.modified_by_id = mJsonti.getString("modified_by_id");
//                        mLeads.modified_by_ip = mJsonti.getString("modified_by_ip");
//                        mLeads.modified_datetime = mJsonti.getString("modified_datetime");
//                        mLeads.created_by_id = mJsonti.getString("created_by_id");
//                        mLeads.created_by_ip = mJsonti.getString("created_by_ip");
//                        mLeads.created_datetime = mJsonti.getString("created_datetime");
//                        mLeads.created_by_type = mJsonti.getString("created_by_type");
//                        mLeads.modified_by_type = mJsonti.getString("modified_by_type");
//                        mLeads.deleted_by_type = mJsonti.getString("deleted_by_type");
//                        mLeads.is_migrate = mJsonti.getString("is_migrate");
//                        mLeads.applicant_id = mJsonti.getString("applicant_id");
//                        mLeads.fk_applicant_type_id = mJsonti.getString("fk_applicant_type_id");
//                        mLeads.pan_number = mJsonti.getString("pan_number");
//                        mLeads.aadhar_number = mJsonti.getString("aadhar_number");
//                        mLeads.marital_status = mJsonti.getString("marital_status");
//                        mLeads.mobile_number = mJsonti.getString("mobile_number");
//                        mLeads.email_id = mJsonti.getString("email_id");
//                        mLeads.relationship_with_applicant = mJsonti.getString("relationship_with_applicant");
//                        mLeads.profession = mJsonti.getString("profession");
//                        mLeads.employer_type = mJsonti.getString("employer_type");
//                        mLeads.employer_name = mJsonti.getString("employer_name");
//                        mLeads.annual_income = mJsonti.getString("annual_income");
//                        mLeads.current_employment_duration = mJsonti.getString("current_employment_duration");
//                        mLeads.total_employement_duration = mJsonti.getString("total_employement_duration");
//                        mLeads.employer_mobile_number = mJsonti.getString("employer_mobile_number");
//                        mLeads.employer_landline_number = mJsonti.getString("employer_landline_number");
//                        mLeads.office_landmark = mJsonti.getString("office_landmark");
//                        mLeads.office_address = mJsonti.getString("office_address");
//                        mLeads.office_address_city = mJsonti.getString("office_address_city");
//                        mLeads.office_address_state = mJsonti.getString("office_address_state");
//                        mLeads.office_address_country = mJsonti.getString("office_address_country");
//                        mLeads.office_address_pin = mJsonti.getString("office_address_pin");
//                        mLeads.has_active_loan = mJsonti.getString("has_active_loan");
//                        mLeads.EMI_Amount = mJsonti.getString("EMI_Amount");
//                        mLeads.kyc_landmark = mJsonti.getString("kyc_landmark");
//                        mLeads.kyc_address = mJsonti.getString("kyc_address");
//                        mLeads.kyc_address_city = mJsonti.getString("kyc_address_city");
//                        mLeads.kyc_address_state = mJsonti.getString("kyc_address_state");
//                        mLeads.kyc_address_country = mJsonti.getString("kyc_address_country");
//                        mLeads.kyc_address_pin = mJsonti.getString("kyc_address_pin");
//                        mLeads.is_borrower_current_address_same_as = mJsonti.getString("is_borrower_current_address_same_as");
//                        mLeads.is_coborrower_current_address_same_as = mJsonti.getString("is_coborrower_current_address_same_as");
//                        mLeads.current_residence_type = mJsonti.getString("current_residence_type");
//                        mLeads.current_landmark = mJsonti.getString("current_landmark");
//                        mLeads.current_address = mJsonti.getString("current_address");
//                        mLeads.current_address_city = mJsonti.getString("current_address_city");
//                        mLeads.current_address_state = mJsonti.getString("current_address_state");
//                        mLeads.current_address_country = mJsonti.getString("current_address_country");
//                        mLeads.current_address_pin = mJsonti.getString("current_address_pin");
//                        mLeads.current_address_rent = mJsonti.getString("current_address_rent");
//                        mLeads.current_address_stay_duration = mJsonti.getString("current_address_stay_duration");
//                        mLeads.is_borrower_permanent_address_same_as = mJsonti.getString("is_borrower_permanent_address_same_as");
//                        mLeads.is_coborrower_permanent_address_same_as = mJsonti.getString("is_coborrower_permanent_address_same_as");
//                        mLeads.permanent_residence_type = mJsonti.getString("permanent_residence_type");
//                        mLeads.permanent_landmark = mJsonti.getString("permanent_landmark");
//                        mLeads.permanent_address = mJsonti.getString("permanent_address");
//                        mLeads.permanent_address_city = mJsonti.getString("permanent_address_city");
//                        mLeads.permanent_address_state = mJsonti.getString("permanent_address_state");
//                        mLeads.permanent_address_country = mJsonti.getString("permanent_address_country");
//                        mLeads.permanent_address_pin = mJsonti.getString("permanent_address_pin");
//                        mLeads.permanent_address_rent = mJsonti.getString("permanent_address_rent");
//                        mLeads.permanent_address_stay_duration = mJsonti.getString("permanent_address_stay_duration");
//                        mLeads.last_completed_degree = mJsonti.getString("last_completed_degree");
//                        mLeads.score_unit = mJsonti.getString("score_unit");
//                        mLeads.cgpa = mJsonti.getString("cgpa");
//                        mLeads.percentage = mJsonti.getString("percentage");
//                        mLeads.passing_year = mJsonti.getString("passing_year");
//                        mLeads.gap_in_education = mJsonti.getString("gap_in_education");
//                        mLeads.full_name_pan_response = mJsonti.getString("full_name_pan_response");
//                        mLeads.course_id = mJsonti.getString("course_id");
//                        mLeads.institute_id = mJsonti.getString("institute_id");
//                        mLeads.course_name = mJsonti.getString("course_name");
//                        mLeads.course_type_id = mJsonti.getString("course_type_id");
//                        mLeads.course_type_other = mJsonti.getString("course_type_other");
//                        mLeads.course_duration = mJsonti.getString("course_duration");
//                        mLeads.course_duration_unit = mJsonti.getString("course_duration_unit");
//                        mLeads.course_img = mJsonti.getString("course_img");
//                        mLeads.course_duration_type = mJsonti.getString("course_duration_type");
//                        mLeads.course_stream_id = mJsonti.getString("course_stream_id");
//                        mLeads.course_stream_other = mJsonti.getString("course_stream_other");
//                        mLeads.course_cost = mJsonti.getString("course_cost");
//                        mLeads.course_description = mJsonti.getString("course_description");
//                        mLeads.course_scope = mJsonti.getString("course_scope");
//                        mLeads.is_corporate_course = mJsonti.getString("is_corporate_course");
//                        mLeads.course_profit = mJsonti.getString("course_profit");
//                        mLeads.course_discount = mJsonti.getString("course_discount");
//                        mLeads.course_interest = mJsonti.getString("course_interest");
//                        mLeads.AROCE = mJsonti.getString("AROCE");
//                        mLeads.IRR = mJsonti.getString("IRR");
//                        mLeads.related_industry = mJsonti.getString("related_industry");
//                        mLeads.institute_score = mJsonti.getString("institute_score");
//                        mLeads.course_schedule = mJsonti.getString("course_schedule");
//                        mLeads.placement_percent = mJsonti.getString("placement_percent");
//                        mLeads.placement_industry = mJsonti.getString("placement_industry");
//                        mLeads.course_faculty = mJsonti.getString("course_faculty");
//                        mLeads.course_certificate = mJsonti.getString("course_certificate");
//                        mLeads.skill_id = mJsonti.getString("skill_id");
//                        mLeads.course_eligibility = mJsonti.getString("course_eligibility");
//                        mLeads.placement_course = mJsonti.getString("placement_course");
//                        mLeads.course_key_features = mJsonti.getString("course_key_features");
//                        mLeads.course_overview = mJsonti.getString("course_overview");
//                        mLeads.course_score = mJsonti.getString("course_score");
//                        mLeads.status = mJsonti.getString("status");
//                        mLeads.course_loan_tenure = mJsonti.getString("course_loan_tenure");
//                        mLeads.proposed_tranches = mJsonti.getString("proposed_tranches");
//                        mLeads.course_category = mJsonti.getString("course_category");
//                        mLeads.branch_id = mJsonti.getString("branch_id");
//                        mLeads.location_id = mJsonti.getString("location_id");
//                        mLeads.state_id = mJsonti.getString("state_id");
//                        mLeads.city_id = mJsonti.getString("city_id");
//                        mLeads.is_branch_mapped = mJsonti.getString("is_branch_mapped");
//                        mLeads.institute_name = mJsonti.getString("institute_name");
//                        mLeads.institute_email = mJsonti.getString("institute_email");
//                        mLeads.institute_password = mJsonti.getString("institute_password");
//                        mLeads.institute_telephone = mJsonti.getString("institute_telephone");
//                        mLeads.institute_secondary_telephone = mJsonti.getString("institute_secondary_telephone");
//                        mLeads.contact_person_name = mJsonti.getString("contact_person_name");
//                        mLeads.contact_person_designation = mJsonti.getString("contact_person_designation");
//                        mLeads.contact_person_mobile = mJsonti.getString("contact_person_mobile");
//                        mLeads.contact_person_email = mJsonti.getString("contact_person_email");
//                        mLeads.institute_registration_no = mJsonti.getString("institute_registration_no");
//                        mLeads.institute_branches_location = mJsonti.getString("institute_branches_location");
//                        mLeads.institute_address = mJsonti.getString("institute_address");
//                        mLeads.institute_city_id = mJsonti.getString("institute_city_id");
//                        mLeads.institute_state_id = mJsonti.getString("institute_state_id");
//                        mLeads.institute_country_id = mJsonti.getString("institute_country_id");
//                        mLeads.institute_pin = mJsonti.getString("institute_pin");
//                        mLeads.institute_description = mJsonti.getString("institute_description");
//                        mLeads.placement_institute = mJsonti.getString("placement_institute");
//                        mLeads.institute_img = mJsonti.getString("institute_img");
//                        mLeads.banner_img = mJsonti.getString("banner_img");
//                        mLeads.institute_establishment_date = mJsonti.getString("institute_establishment_date");
//                        mLeads.institute_num_branches = mJsonti.getString("institute_num_branches");
//                        mLeads.institute_website = mJsonti.getString("institute_website");
//                        mLeads.institute_owner = mJsonti.getString("institute_owner");
//                        mLeads.institute_importance_to_eduvanz = mJsonti.getString("institute_importance_to_eduvanz");
//                        mLeads.no_of_courses = mJsonti.getString("no_of_courses");
//                        mLeads.institute_type = mJsonti.getString("institute_type");
//                        mLeads.parent_id = mJsonti.getString("parent_id");
//                        mLeads.institute_auth_key = mJsonti.getString("institute_auth_key");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    mLeadsArrayList.add(mLeads);
//
//                }
//
//                leadsAdapter = new LeadsAdapter(mLeadsArrayList,context);
//                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//                recyclerLeads.setLayoutManager(horizontalLayoutManager);
//                recyclerLeads.setAdapter(leadsAdapter);
//
//                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("signed_appstatus", signDocument);
//                editor.apply();
//                editor.commit();
//
//                if (borrower.equalsIgnoreCase("1")) {
//                    linearLayoutContinueApplication.setVisibility(View.VISIBLE);
//                    linearLayoutApplyNow.setVisibility(View.GONE);
//                    linearLayoutEligiblityChekck.setVisibility(View.GONE);
//                }
//            }
//        } catch (Exception e) {
//            String className = this.getClass().getSimpleName();
//            String name = new Object() {
//            }.getClass().getEnclosingMethod().getName();
//            String errorMsg = e.getMessage();
//            String errorMsgDetails = e.getStackTrace().toString();
//            String errorLine = String.valueOf(e.getStackTrace()[0]);
//            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
//        }
//
//    }

    public void setProfileDashbBoardStatus(JSONObject jsonDataO) {
        Log.e(TAG, "setProfileDashbBoardStatus: " + jsonDataO);
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

                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("signed_appstatus", signDocument);
                editor.apply();
                editor.commit();

                if (borrower.equalsIgnoreCase("1")) {
                    linearLayoutContinueApplication.setVisibility(View.VISIBLE);
                    linearLayoutApplyNow.setVisibility(View.GONE);
                    linearLayoutEligiblityChekck.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }
}
