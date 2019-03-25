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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.adapter.LeadsAdapter;
import com.eduvanzapplication.newUI.newViews.LoanTabActivity;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;
import com.eduvanzapplication.newUI.pojo.MLeads;
import com.eduvanzapplication.newUI.adapter.ViewPagerAdapterDashboard;
import com.eduvanzapplication.newUI.newViews.BannerActivity;
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
    static LinearLayout   linearLayoutEligiblityChekck,
            linearLayoutApplyNow, linearLayoutContinueApplication;
    MainApplication mainApplication;
    SharedPref sharedPref;
    LinearLayout linProceedBtn, layout2, linStartNew;
    ImageView ivStartNewBtn;

    static String borrower = null, coBorrower = null, coBorrowerDocument = null,
            eligibility = null, borrowerDocument = null, signDocument = null,
            kyc = null, profileDashboardStats = null;
    List<MLeads> mLeadsArrayList = new ArrayList<>();

    LeadsAdapter leadsAdapter;

    View view;


    public DashboardFragmentNew() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        view = inflater.inflate(R.layout.fragment_dashboard_fragment_new, container, false);

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

//            MainApplication.student_id = student_id;
            MainApplication.auth_token = auth_token;
            MainApplication.lead_id = lead_id;

            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("lead_id", "");
                editor.apply();
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }


            viewPagerDashboard = (ViewPager) view.findViewById(R.id.viewPager_dashboard);
            linProceedBtn = view.findViewById(R.id.linProceedBtn);
            layout2 = view.findViewById(R.id.layout2);
            linStartNew = view.findViewById(R.id.linStartNew);
            ivStartNewBtn = view.findViewById(R.id.ivStartNewBtn);
            circlePageIndicatorDashboard = (CirclePageIndicator) view.findViewById(R.id.viewPageIndicator);
            final float density = getResources().getDisplayMetrics().density;
            circlePageIndicatorDashboard.setRadius(4 * density);

            viewPagerDashboardPOJOArrayList = new ArrayList<>();
            viewPagerAdapterDashboard = new ViewPagerAdapterDashboard(context, viewPagerDashboardPOJOArrayList);

            viewPagerDashboard.setAdapter(viewPagerAdapterDashboard);
            circlePageIndicatorDashboard.setViewPager(viewPagerDashboard);

            /** API CALL GET DASHBOARD BANNER**/
            try {
                String url = MainApplication.mainUrl + "mobileadverstisement/getBanner";//http://159.89.204.41/eduvanzApi/mobileadverstisement/getBanner
                Map<String, String> params = new HashMap<String, String>();
                if(!Globle.isNetworkAvailable(context))
                {
                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                } else {
                    VolleyCallNew volleyCall = new VolleyCallNew();//http://192.168.0.110/eduvanzapi/mobileadverstisement/getBanner
                    volleyCall.sendRequest(context, url, null, mFragment, "dashboardBanner", params,MainApplication.auth_token);
                }
            } catch (Exception e) {
                String className = this.getClass().getSimpleName();
                String name = new Object() {
                }.getClass().getEnclosingMethod().getName();
                String errorMsg = e.getMessage();
                String errorMsgDetails = e.getStackTrace().toString();
                String errorLine = String.valueOf(e.getStackTrace()[0]);
                Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
            }

            /** API CALL GET DEAL**/
            try {
                String url = MainApplication.mainUrl + "mobileadverstisement/getDeal";
                Map<String, String> params = new HashMap<String, String>();
                if(!Globle.isNetworkAvailable(context))
                {
                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                } else {
                    VolleyCallNew volleyCall = new VolleyCallNew();//http://192.168.0.110/eduvanzapi/mobileadverstisement/getDeal
                    volleyCall.sendRequest(context, url, null, mFragment, "getDeal", params,MainApplication.auth_token);
                }
            } catch (Exception e) {
                String className = this.getClass().getSimpleName();
                String name = new Object() {
                }.getClass().getEnclosingMethod().getName();
                String errorMsg = e.getMessage();
                String errorMsgDetails = e.getStackTrace().toString();
                String errorLine = String.valueOf(e.getStackTrace()[0]);
                Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
            }

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

//            Intent intent = new Intent(context, LoanApplication.class);
//            startActivity(intent);

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }

        linProceedBtn.setOnClickListener(newApplicationClkListnr);
        layout2.setOnClickListener(newApplicationClkListnr);
        linStartNew.setOnClickListener(newApplicationClkListnr);
        ivStartNewBtn.setOnClickListener(newApplicationClkListnr);
        return view;
    }//-----------------------------------END OF ON CREATE----------------------------------------//

    View.OnClickListener newApplicationClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), NewLeadActivity.class));
        }
    };
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

                        if (!jsonleadStatus.getString("requested_loan_amount").toString().equals("null"))
                            mLeads.requested_loan_amount  = jsonleadStatus.getString("requested_loan_amount");

                        if (!jsonleadStatus.getString("lead_id").toString().equals("null"))
                        mLeads.lead_id = jsonleadStatus.getString("lead_id");

                        if (!jsonleadStatus.getString("application_id").toString().equals("null"))
                            mLeads.application_id = jsonleadStatus.getString("application_id");

                        if (!jsonleadStatus.getString("first_name").toString().equals("null"))
                            mLeads.first_name = jsonleadStatus.getString("first_name");

                        if (!jsonleadStatus.getString("middle_name").toString().equals("null"))
                            mLeads.middle_name = jsonleadStatus.getString("middle_name");

                        if (!jsonleadStatus.getString("last_name").toString().equals("null"))
                            mLeads.last_name = jsonleadStatus.getString("last_name");

                        if (!jsonleadStatus.getString("created_date_time").toString().equals("null"))
                            mLeads.created_date_time = jsonleadStatus.getString("created_date_time");

                        if (!jsonleadStatus.getString("profession").toString().equals("null"))
                            mLeads.profession = jsonleadStatus.getString("profession");

                        if (!jsonleadStatus.getString("fk_applicant_type_id").toString().equals("null"))
                            mLeads.fk_applicant_type_id = jsonleadStatus.getString("fk_applicant_type_id");

                        if (!jsonleadStatus.getString("has_coborrower").toString().equals("null"))
                            mLeads.has_coborrower = jsonleadStatus.getString("has_coborrower");

                        if (!jsonleadStatus.getString("course_name").toString().equals("null"))
                            mLeads.course_name = jsonleadStatus.getString("course_name");

                        if (!jsonleadStatus.getString("course_cost").toString().equals("null"))
                            mLeads.course_cost = jsonleadStatus.getString("course_cost");

                        if (!jsonleadStatus.getString("status_name").toString().equals("null"))
                            mLeads.status_name = jsonleadStatus.getString("status_name");

                        if (!jsonleadStatus.getString("location_name").toString().equals("null"))
                            mLeads.location_name = jsonleadStatus.getString("location_name");

                        if (!jsonleadStatus.getString("institute_name").toString().equals("null"))
                            mLeads.institute_name = jsonleadStatus.getString("institute_name");

                        if (!jsonleadStatus.getString("student_id").toString().equals("null"))
                            mLeads.student_id = jsonleadStatus.getString("student_id");


//                        mLeads.requested_loan_amount = jsonleadStatus.getString("requested_loan_amount");
//                        mLeads.lead_id = jsonleadStatus.getString("lead_id");
//                        mLeads.application_id = jsonleadStatus.getString("application_id");
//                        mLeads.first_name = jsonleadStatus.getString("first_name");
//                        mLeads.middle_name = jsonleadStatus.getString("middle_name");
//                        mLeads.last_name = jsonleadStatus.getString("last_name");
//                        mLeads.created_date_time = jsonleadStatus.getString("created_date_time");
//                        mLeads.profession = jsonleadStatus.getString("profession");
//                        mLeads.fk_applicant_type_id = jsonleadStatus.getString("fk_applicant_type_id");
//                        mLeads.has_coborrower = jsonleadStatus.getString("has_coborrower");
//                        mLeads.course_name = jsonleadStatus.getString("course_name");
//                        mLeads.course_cost = jsonleadStatus.getString("course_cost");
//                        mLeads.status_name = jsonleadStatus.getString("status_name");
//                        mLeads.location_name = jsonleadStatus.getString("location_name");
//                        mLeads.institute_name = jsonleadStatus.getString("institute_name");
//                        mLeads.student_id = jsonleadStatus.getString("student_id");

                    } catch (JSONException e) {
                        String className = this.getClass().getSimpleName();
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        String errorMsg = e.getMessage();
                        String errorMsgDetails = e.getStackTrace().toString();
                        String errorLine = String.valueOf(e.getStackTrace()[0]);
                        Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
                    }
                    mLeadsArrayList.add(mLeads);

                }
                //146  150 184 217

                leadsAdapter = new LeadsAdapter(mLeadsArrayList,context);
                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

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
