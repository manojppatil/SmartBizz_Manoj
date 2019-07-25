package com.eduvanzapplication.newUI.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.friendlyscore.StartActivity;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.adapter.CardStackAdapter;
import com.eduvanzapplication.newUI.newViews.EmiCalculatorActivity;
import com.eduvanzapplication.newUI.newViews.FinancialAnalysis;
import com.eduvanzapplication.newUI.newViews.LeadOwnerType;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;
import com.eduvanzapplication.newUI.newViews.TenureSelectionActivity;
import com.eduvanzapplication.newUI.pojo.MLeads;
import com.eduvanzapplication.newUI.adapter.ViewPagerAdapterDashboard;
import com.eduvanzapplication.newUI.pojo.DashboardBannerModel;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eduvanzapplication.newUI.MainApplication.TAG;

public class DashboardFragmentNew extends Fragment  {

    public static Context context;
    public static Fragment mFragment;
    static  ViewPager viewPagerDashboard;
    static ViewPagerAdapterDashboard viewPagerAdapterDashboard;
    static TextView textViewDealTitle;
    public static String dealID = "", userName = "", userId = "", student_id = "",
            mobile_no ="",email ="" ,auth_token ="", lead_id="";
    CirclePageIndicator circlePageIndicatorDashboard;
    public static ImageView ivPrevBtn,ivNextBtn;
    public static RelativeLayout relStartNewLayout;
    public static LinearLayout linLeadsLayout;
    public static ProgressDialog progressDialog;
    public static Boolean isLeadReload = false;

    static LinearLayout linearLayoutEligiblityChekck,
            linearLayoutApplyNow, linearLayoutContinueApplication;
    MainApplication mainApplication;
    SharedPref sharedPref;
    LinearLayout linProceedBtn, layout2,linFriendlyScore, linStartNew,linEMICalculator;
    ImageView ivStartNewBtn, ivEMICalculatorBtn;
    TextView txtCallUs, txtEmailUs, txtWhatsAppUs;
    ArrayList<DashboardBannerModel> bannerModelArrayList = new ArrayList<>();

    public static RecyclerView rvLeads;
    public static CardStackAdapter adapter;

    public static List<MLeads> mLeadsArrayList = new ArrayList<>();

    static String borrower = null, coBorrower = null, coBorrowerDocument = null,
            eligibility = null, borrowerDocument = null, signDocument = null,
            kyc = null, profileDashboardStats = null;

    public static View view;

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
                email = sharedPreferences.getString("email", "");
                student_id = sharedPreferences.getString("student_id", "");
                auth_token = sharedPreferences.getString("auth_token", "");
                lead_id = sharedPreferences.getString("lead_id", "");

            } catch (Exception e) {
                e.printStackTrace();
            }

//            MainApplication.student_id = student_id;
            MainActivity.auth_token = auth_token;
            MainApplication.lead_id = lead_id;
            MainActivity.lead_id = lead_id;

            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("lead_id", "");
                editor.apply();
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

            viewPagerDashboard = view.findViewById(R.id.viewPager_dashboard);
            linProceedBtn = view.findViewById(R.id.linProceedBtn);
            layout2 = view.findViewById(R.id.layout2);
            linFriendlyScore = view.findViewById(R.id.linFriendlyScore);
            linStartNew = view.findViewById(R.id.linStartNew);
            ivStartNewBtn = view.findViewById(R.id.ivStartNewBtn);
            /*linEMICalculator = view.findViewById(R.id.linEMICalculator);
            ivEMICalculatorBtn = view.findViewById(R.id.ivEMICalculatorBtn);*/
            txtCallUs = view.findViewById(R.id.txtCallUs);
            txtWhatsAppUs = view.findViewById(R.id.txtWhatsAppUs);
            txtEmailUs = view.findViewById(R.id.txtEmailUs);
            ivNextBtn = view.findViewById(R.id.ivNextBtn);
            ivPrevBtn = view.findViewById(R.id.ivPrevBtn);
            relStartNewLayout = view.findViewById(R.id.relStartNewLayout);
            linLeadsLayout = view.findViewById(R.id.linLeadsLayout);
//            relStartNewLayout.setVisibility(View.VISIBLE);
//            linLeadsLayout.setVisibility(View.GONE);
            progressDialog = new ProgressDialog(getActivity());

            rvLeads = view.findViewById(R.id.rvLeads);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvLeads.setLayoutManager(linearLayoutManager);
            adapter = new CardStackAdapter(mLeadsArrayList, context, getActivity());
            rvLeads.setAdapter(adapter);
            rvLeads.setNestedScrollingEnabled(false);
            circlePageIndicatorDashboard = view.findViewById(R.id.viewPageIndicator);
            final float density = getResources().getDisplayMetrics().density;
            circlePageIndicatorDashboard.setRadius(4 * density);

            bannerModelArrayList = new ArrayList<>();
            viewPagerAdapterDashboard = new ViewPagerAdapterDashboard(context, bannerModelArrayList);

            viewPagerDashboard.setAdapter(viewPagerAdapterDashboard);
            circlePageIndicatorDashboard.setViewPager(viewPagerDashboard);

            /** API CALL GET DASHBOARD BANNER**/
            try {
                String url = MainActivity.mainUrl + "mobileadverstisement/getBanner";//http://159.89.204.41/eduvanzApi/mobileadverstisement/getBanner
                Map<String, String> params = new HashMap<String, String>();
                if(!Globle.isNetworkAvailable(context))
                {
                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                } else {
                    VolleyCall volleyCall = new VolleyCall();//http://192.168.0.110/eduvanzapi/mobileadverstisement/getBanner
                    volleyCall.sendRequest(context, url, null, mFragment, "dashboardBanner", params,MainActivity.auth_token);
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

//            /** API CALL POST LOGIN DASHBOARD Details **/
//            try {
//                String url = MainActivity.mainUrl + "dashboard/getDashboardDetails";
//                Map<String, String> params = new HashMap<String, String>();
////                params.put("studentId","" );
//                params.put("studentId", student_id);
//                if (!Globle.isNetworkAvailable(context)) {
//                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                } else {
//                    VolleyCall volleyCall = new VolleyCall();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
//                    volleyCall.sendRequest(context, url, null, mFragment, "studentDashbBoardDetails", params, MainActivity.auth_token);
//                }
//            } catch (Exception e) {
//                String className = this.getClass().getSimpleName();
//                String name = new Object() {
//                }.getClass().getEnclosingMethod().getName();
//                String errorMsg = e.getMessage();
//                String errorMsgDetails = e.getStackTrace().toString();
//                String errorLine = String.valueOf(e.getStackTrace()[0]);
//                Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
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


    View.OnClickListener FriendlyScoreClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            startActivity(new Intent(getActivity(), FinancialAnalysis.class));
            startActivity(new Intent(getActivity(), StartActivity.class));
        }
    };


    View.OnClickListener newApplicationClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), LeadOwnerType.class));
//            startActivity(new Intent(getActivity(), NewLeadActivity.class));
        }
    };

    View.OnClickListener emiClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), EmiCalculatorActivity.class));
        }
    };

    View.OnClickListener callUsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:02249733624"));
            startActivity(intent);
        }
    };

    View.OnClickListener whatsAppUsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msg = "Hello Eduvanz, Please let me know more about your loan offering.";
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setPackage("com.whatsapp");
            String url = "https://api.whatsapp.com/send?phone=918070363636&text="+msg;
            sendIntent.setData(Uri.parse(url));
            if(sendIntent.resolveActivity(getContext().getPackageManager()) != null){
                startActivity(sendIntent);
            }else{
                Snackbar.make(txtWhatsAppUs, "Please Install WhatsApp Messenger in your Devices", Snackbar.LENGTH_SHORT).show();
            }
        }
    };

    View.OnClickListener emailUsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                try {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@eduvanz.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");
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
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linProceedBtn.setOnClickListener(newApplicationClkListnr);
        layout2.setOnClickListener(newApplicationClkListnr);
        linFriendlyScore.setOnClickListener(FriendlyScoreClkListnr);
        linStartNew.setOnClickListener(newApplicationClkListnr);
        ivStartNewBtn.setOnClickListener(newApplicationClkListnr);
        txtCallUs.setOnClickListener(callUsListener);
        txtWhatsAppUs.setOnClickListener(whatsAppUsListener);
        txtEmailUs.setOnClickListener(emailUsListener);
      //  linEMICalculator.setOnClickListener(emiClkListnr);
      //  ivEMICalculatorBtn.setOnClickListener(emiClkListnr);

        getDashboardDetails();
        onResume();

    }

    @Override
    public void onResume() {
        super.onResume();

        if(isLeadReload) {
            getDashboardDetails();
        }
    }

    private void getDashboardDetails() {   //get leads
        try {
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            if (!getActivity().isFinishing())
                progressDialog.show();
            String url = MainActivity.mainUrl + "dashboard/getDashboardDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("studentId", student_id);//3303
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
                volleyCall.sendRequest(context, url, null, mFragment, "studentDashbBoardDetails", params, MainActivity.auth_token);
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
            //{"result":{"banner":[{"id":"1","title":"FINANCING YOUR FUTURE","image":"https:\/\/eduvanz.com\/admin\/uploads\/mobileadvertisement\/1\/image_1545100643.png"}]},"status":1,"message":"success"}            Log.e(MainApplication.TAG, "setDashboardImages: " + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONArray jsonArray = jsonObject.getJSONArray("banner");

                bannerModelArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    DashboardBannerModel dashboardBannerModel = new DashboardBannerModel();
                    dashboardBannerModel.id = jsonObject1.getString("id");
                    dashboardBannerModel.title = jsonObject1.getString("title");
                    dashboardBannerModel.image = jsonObject1.getString("image");
                    bannerModelArrayList.add(dashboardBannerModel);
                }
                viewPagerAdapterDashboard = new ViewPagerAdapterDashboard(context, bannerModelArrayList);
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
//        Log.e(TAG, "setProfileDashbBoardStatus: " + jsonDataO);
        try {
            progressDialog.dismiss();
            if (jsonDataO.getInt("status") == 1) {

                JSONObject mObject = jsonDataO.optJSONObject("result");
                String message = jsonDataO.getString("message");

                JSONArray jsonArray1 = mObject.getJSONArray("leadsData");
                mLeadsArrayList = new ArrayList<>();
                if (jsonArray1.length() == 0){
                    linLeadsLayout.setVisibility(View.GONE);
                    relStartNewLayout.setVisibility(View.VISIBLE);
                }
                else {
                    relStartNewLayout.setVisibility(View.GONE);
                    linLeadsLayout.setVisibility(View.VISIBLE);
                }
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

                        if (!jsonleadStatus.getString("tickers").toString().equals("null"))
                            mLeads.tickers = jsonleadStatus.getString("tickers");

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
                adapter = new CardStackAdapter(mLeadsArrayList, context, getActivity());
                rvLeads.setAdapter(adapter);

                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("signed_appstatus", signDocument);
                editor.apply();
                editor.commit();

//                if (borrower.equalsIgnoreCase("1")) {
//                    linearLayoutContinueApplication.setVisibility(View.VISIBLE);
//                    linearLayoutApplyNow.setVisibility(View.GONE);
//                    linearLayoutEligiblityChekck.setVisibility(View.GONE);
//                }

            }
            else {
                relStartNewLayout.setVisibility(View.VISIBLE);
                linLeadsLayout.setVisibility(View.GONE);
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
