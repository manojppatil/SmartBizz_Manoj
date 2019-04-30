package com.eduvanzapplication.newUI.fragments;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.digio.in.esignsdk.Digio;
import com.digio.in.esignsdk.DigioConfig;
import com.digio.in.esignsdk.DigioEnvironment;
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.adapter.AmortAdapter;
import com.eduvanzapplication.newUI.adapter.NachAdapter;
import com.eduvanzapplication.newUI.newViews.LoanTabActivity;
import com.eduvanzapplication.newUI.pojo.MNach;
import com.eduvanzapplication.uploaddocs.PathFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.eduvanzapplication.MainActivity.TAG;
import static com.eduvanzapplication.R.*;
import static com.eduvanzapplication.newUI.MainApplication.staticdownloadurl;
import static com.eduvanzapplication.newUI.fragments.DashboardFragmentNew.userName;

public class PostApprovalDocFragment extends Fragment {

    static View view;
    private static Context context;
    private static Fragment mFragment;
    private int SELECT_DOC = 2;
    private static ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    DownloadManager downloadManager;
    private static String userId, uploadFilePath = "";
    StringBuffer sb;

    public static List<MNach> mNachArrayList;
    public static RecyclerView rvNach;
    public static NachAdapter adapter;

    public static Animation collapseanimationlinExpand, expandAnimationlinExpand;

    public String lead_id = "", application_loan_id = "", principal_amount = "", down_payment = "", rate_of_interest = "",
            emi_type = "", emi_amount = "", requested_loan_amount = "", requested_tenure = "", requested_roi = "", requested_emi = "",
            offered_amount = "", applicant_id = "", fk_lead_id = "", first_name = "", last_name = "", mobile_number = "",
            email_id = "", kyc_address = "", course_cost = "", paid_on = "", transaction_amount = "", kyc_status = "",
            disbursal_status = "", loan_agrement_upload_status = "";
    String downloadUrl = "", downloadSignedUrl = "";
    long downloadReference;

    public static LinearLayout linManualBtn, lineSignBtn, linOTPBtn, linData, linAggSignInBtn,
            linDownloadAgreement, linDownloadNach, linDisbursed, linAgreementSigned, linPayBtn, linPayStatus;
    public static RelativeLayout relExpandCollapse;
    public static ImageButton btnExpandCollapse;
    public static ImageView ivLeadDisbursed, ivAggSigned;

    public static TextView txtProcessingFee, txtLeadDisbursedStatus, txtAggSignedStatus;

    public BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadReference == referenceId) {

//                Button cancelDownload = (Button) findViewById(R.id.cancelDownload);
//                cancelDownload.setEnabled(false);
//file:///storage/emulated/0/Android/data/com.eduvanzemployees/files/Download/SIGNED APPLICATIONEdutesterEduvanz1530095441962.pdf

                int ch;
                ParcelFileDescriptor file;
                StringBuffer strContent = new StringBuffer("");
                StringBuffer countryData = new StringBuffer("");

                //parse the JSON data and display on the screen
                try {
                    file = downloadManager.openDownloadedFile(downloadReference);
                    FileInputStream fileInputStream
                            = new ParcelFileDescriptor.AutoCloseInputStream(file);

                    while ((ch = fileInputStream.read()) != -1)
                        strContent.append((char) ch);

//                    JSONObject responseObj = new JSONObject(strContent.toString());
//                    JSONArray countriesObj = responseObj.getJSONArray("countries");
//
//                    for (int i = 0; i < countriesObj.length(); i++) {
//                        Gson gson = new Gson();
//                        String countryInfo = countriesObj.getJSONObject(i).toString();
//                        Country countryId = gson.fromJson(countryInfo, Country.class);
//                        countryData.append(countryId.getCode() + ": " + countryId.getName() + "\n");
//                    }
//
//                    TextView showCountries = (TextView) findViewById(R.id.countryData);
//                    showCountries.setText(countryData.toString());

                    progressBar.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(context, R.string.downloading_of_file_just_finished, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
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
    };

    public PostApprovalDocFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNachArrayList = new ArrayList<>();
        lead_id = "";
        application_loan_id = "";
        principal_amount = "";
        down_payment = "";
        rate_of_interest = "";
        emi_type = "";
        emi_amount = "";
        requested_loan_amount = "";
        requested_tenure = "";
        requested_roi = "";
        requested_emi = "";
        offered_amount = "";
        applicant_id = "";
        fk_lead_id = "";
        first_name = "";
        last_name = "";
        mobile_number = "";
        email_id = "";
        kyc_address = "";
        course_cost = "";
        paid_on = "";
        transaction_amount = "";
        kyc_status = "";
        disbursal_status = "";
        loan_agrement_upload_status = "";
        downloadUrl = "";
        downloadSignedUrl = "";

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(layout.fragment_postapprovaldoc, container, false);

        context = getContext();
        mFragment = new PostApprovalDocFragment();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        progressBar = view.findViewById(R.id.progressBar_signsubmit);
        relExpandCollapse = view.findViewById(R.id.relExpandCollapse);
        linManualBtn = view.findViewById(R.id.linManualBtn);
        lineSignBtn = view.findViewById(R.id.lineSignBtn);
        linOTPBtn = view.findViewById(R.id.linOTPBtn);
        linPayBtn = view.findViewById(R.id.linPayBtn);
        linPayStatus = view.findViewById(R.id.linPayStatus);
        linData = view.findViewById(R.id.linData);
        linAggSignInBtn = view.findViewById(R.id.linAggSignInBtn);
        linDownloadAgreement = view.findViewById(R.id.linDownloadAgreement);
        linDownloadNach = view.findViewById(R.id.linDownloadNach);
        btnExpandCollapse = view.findViewById(R.id.btnExpandCollapse);
        ivLeadDisbursed = view.findViewById(R.id.ivLeadDisbursed);
        ivAggSigned = view.findViewById(R.id.ivAggSigned);
        linDisbursed = view.findViewById(R.id.linDisbursed);
        linAgreementSigned = view.findViewById(R.id.linAgreementSigned);

        txtProcessingFee = view.findViewById(id.txtProcessingFee);
        txtLeadDisbursedStatus = view.findViewById(id.txtLeadDisbursedStatus);
        txtAggSignedStatus = view.findViewById(id.txtAggSignedStatus);

        expandAnimationlinExpand = AnimationUtils.loadAnimation(context, R.anim.scale_expand);
        collapseanimationlinExpand = AnimationUtils.loadAnimation(context, R.anim.scale_collapse);

        rvNach = view.findViewById(id.rvNach);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNach.setLayoutManager(linearLayoutManager);
        adapter = new NachAdapter(mNachArrayList, context, getActivity());
        rvNach.setAdapter(adapter);
        rvNach.setNestedScrollingEnabled(false);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(downloadReceiver, filter);

        linManualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NewLeadActivity.gender = "1";
                linManualBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                lineSignBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linOTPBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                manualSignInDialog();
            }
        });

        lineSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NewLeadActivity.gender = "2";
                linManualBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                lineSignBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linOTPBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));

                try {
                    String ipaddress = Utils.getIPAddress(true);
                    String url = MainActivity.mainUrl + "laf/getDigioDocumentIdForStudent";
                    Map<String, String> params = new HashMap<String, String>();
                    if (!Globle.isNetworkAvailable(context)) {
                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                    } else {
                        VolleyCall volleyCall = new VolleyCall();
                        params.put("logged_id", LoanTabActivity.student_id);
                        params.put("created_by_ip", ipaddress);
                        if (!Globle.isNetworkAvailable(context)) {
                            Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                        } else {
                            volleyCall.sendRequest(context, url, null, mFragment, "getDigioDocumentIdForStudent", params, MainActivity.auth_token);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        linOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NewLeadActivity.gender = "2";
                linManualBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                lineSignBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linOTPBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
            }
        });

//        linExpandCollapse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (linData.getVisibility() == 0) {
//                    linData.setVisibility(View.GONE);
//                    btnExpandCollapse.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_chevron_down));
//                } else {
//                    linData.setVisibility(View.VISIBLE);
//                    btnExpandCollapse.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_chevron_up));
//                }
//
//            }
//        });

        btnExpandCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linData.getVisibility() == VISIBLE) {
                    linData.startAnimation(collapseanimationlinExpand);
                } else {
                    linData.startAnimation(expandAnimationlinExpand);
                }
            }
        });

        relExpandCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linData.getVisibility() == VISIBLE) {
                    linData.startAnimation(collapseanimationlinExpand);
                } else {
                    linData.startAnimation(expandAnimationlinExpand);
                }
            }
        });

        collapseanimationlinExpand.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linData.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        expandAnimationlinExpand.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linData.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        linDownloadAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (!linDownloadAgreement.getTag().toString().equalsIgnoreCase("")) {
                    downloadSignedUrl = linDownloadAgreement.getTag().toString();
                    downLoad(downloadSignedUrl, 2, "SignedAgreement");
                } else {
                    Toast.makeText(context, R.string.something_went_wrong_please_try_again_later, Toast.LENGTH_SHORT).show();

                }

            }
        });

        linDownloadNach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (!linDownloadNach.getTag().toString().equalsIgnoreCase("")) {
                    downloadSignedUrl = linDownloadNach.getTag().toString();
                    downLoad(downloadSignedUrl, 2, "Nach");
                } else {
                    Toast.makeText(context, R.string.something_went_wrong_please_try_again_later, Toast.LENGTH_SHORT).show();

                }

            }
        });

        try {
            String url = MainActivity.mainUrl + "laf/getLoanDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("lead_id", MainActivity.lead_id);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
                volleyCall.sendRequest(context, url, null, mFragment, "getLoanDetails", params, MainActivity.auth_token);
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

    public void setDigioDocumentIdForStudent(JSONObject jsonData) {//{"result":{"documentId":"DID180627125727122W11P5DAJQX5NZ2","email":"vijay.shukla@eduvanz.in"},"status":1,"message":"Please follow the instructions to esign the form."}
        try {
            Log.e("SERVER CALL", "getDocuments" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                JSONObject jsonObject = jsonData.getJSONObject("result");

                String documentID = jsonObject.getString("documentId");//DID180627125727122W11P5DAJQX5NZ2
                String email = jsonObject.getString("email");//vijay.shukla@eduvanz.in

                // Invoke Esign

                final Digio digio = new Digio();
                DigioConfig digioConfig = new DigioConfig();
                digioConfig.setLogo("https://lh3.googleusercontent.com/v6lR_JSsjovEzLBkHPYPbVuw1161rkBjahSxW0d38RT4f2YoOYeN2rQSrcW58MAfuA=w300"); //Your company logo
                digioConfig.setEnvironment(DigioEnvironment.PRODUCTION);   //Stage is sandbox

                try {
                    digio.init((Activity) context, digioConfig);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    digio.esign(documentID, email);
//                    Log.e(MainApplication.TAG, "downloadUrldownloadUrl: " + downloadUrl + " userEmailiduserEmailid" + userEmailid);
                } catch (Exception e) {
                    String className = this.getClass().getSimpleName();
                    String name = new Object() {
                    }.getClass().getEnclosingMethod().getName();
                    String errorMsg = e.getMessage();
                    String errorMsgDetails = e.getStackTrace().toString();
                    String errorLine = String.valueOf(e.getStackTrace()[0]);
                    Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
                }

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

    public void setLoanDetails(JSONObject jsonDataO) {
        Log.e(TAG, "setLoanDetails: " + jsonDataO);
        try {
//            progressDialog.dismiss();
            if (jsonDataO.getInt("status") == 1) {

                String message = jsonDataO.getString("message");

//                JSONArray jsonArray1 = jsonDataO.getJSONObject("loanData");
                if (!jsonDataO.get("loanData").equals(null)) {

                    JSONObject jsonloanDataDetails = jsonDataO.getJSONObject("loanData");

                    if (!jsonloanDataDetails.getString("lead_id").toString().equals("null"))
                        LoanTabActivity.Postlead_id = lead_id = jsonloanDataDetails.getString("lead_id");
                    if (!jsonloanDataDetails.getString("application_loan_id").toString().equals("null"))
                        LoanTabActivity.Postapplication_loan_id = application_loan_id = jsonloanDataDetails.getString("application_loan_id");
                    if (!jsonloanDataDetails.getString("principal_amount").toString().equals("null"))
                        LoanTabActivity.Postprincipal_amount = principal_amount = jsonloanDataDetails.getString("principal_amount");
                    if (!jsonloanDataDetails.getString("down_payment").toString().equals("null"))
                        LoanTabActivity.Postdown_payment = down_payment = jsonloanDataDetails.getString("down_payment");
                    if (!jsonloanDataDetails.getString("rate_of_interest").toString().equals("null"))
                        LoanTabActivity.Postrate_of_interest = rate_of_interest = jsonloanDataDetails.getString("rate_of_interest");
                    if (!jsonloanDataDetails.getString("emi_type").toString().equals("null"))
                        LoanTabActivity.Postemi_type = emi_type = jsonloanDataDetails.getString("emi_type");
                    if (!jsonloanDataDetails.getString("emi_amount").toString().equals("null"))
                        LoanTabActivity.Postemi_amount = emi_amount = jsonloanDataDetails.getString("emi_amount");
                    if (!jsonloanDataDetails.getString("requested_loan_amount").toString().equals("null"))
                        LoanTabActivity.Postrequested_loan_amount = requested_loan_amount = jsonloanDataDetails.getString("requested_loan_amount");
                    if (!jsonloanDataDetails.getString("requested_tenure").toString().equals("null"))
                        LoanTabActivity.Postrequested_tenure = requested_tenure = jsonloanDataDetails.getString("requested_tenure");
                    if (!jsonloanDataDetails.getString("requested_roi").toString().equals("null"))
                        LoanTabActivity.Postrequested_roi = requested_roi = jsonloanDataDetails.getString("requested_roi");
                    if (!jsonloanDataDetails.getString("requested_emi").toString().equals("null"))
                        LoanTabActivity.Postrequested_emi = requested_emi = jsonloanDataDetails.getString("requested_emi");
                    if (!jsonloanDataDetails.getString("offered_amount").toString().equals("null"))
                        LoanTabActivity.Postoffered_amount = offered_amount = jsonloanDataDetails.getString("offered_amount");
                    if (!jsonloanDataDetails.getString("applicant_id").toString().equals("null"))
                        LoanTabActivity.Postapplicant_id = applicant_id = jsonloanDataDetails.getString("applicant_id");
                    if (!jsonloanDataDetails.getString("fk_lead_id").toString().equals("null"))
                        LoanTabActivity.Postfk_lead_id = fk_lead_id = jsonloanDataDetails.getString("fk_lead_id");
                    if (!jsonloanDataDetails.getString("first_name").toString().equals("null"))
                        LoanTabActivity.Postfirst_name = first_name = jsonloanDataDetails.getString("first_name");
                    if (!jsonloanDataDetails.getString("last_name").toString().equals("null"))
                        LoanTabActivity.Postlast_name = last_name = jsonloanDataDetails.getString("last_name");
                    if (!jsonloanDataDetails.getString("mobile_number").toString().equals("null"))
                        LoanTabActivity.Postmobile_number = mobile_number = jsonloanDataDetails.getString("mobile_number");
                    if (!jsonloanDataDetails.getString("email_id").toString().equals("null"))
                        LoanTabActivity.Postemail_id = email_id = jsonloanDataDetails.getString("email_id");
                    if (!jsonloanDataDetails.getString("kyc_address").toString().equals("null"))
                        LoanTabActivity.Postkyc_address = kyc_address = jsonloanDataDetails.getString("kyc_address");
                    if (!jsonloanDataDetails.getString("course_cost").toString().equals("null"))
                        LoanTabActivity.Postcourse_cost = course_cost = jsonloanDataDetails.getString("course_cost");
                    if (!jsonloanDataDetails.getString("paid_on").toString().equals("null"))
                        LoanTabActivity.Postpaid_on = paid_on = jsonloanDataDetails.getString("paid_on");
                    if (!jsonloanDataDetails.getString("transaction_amount").toString().equals("null"))
                        LoanTabActivity.Posttransaction_amount = transaction_amount = jsonloanDataDetails.getString("transaction_amount");
                    if (!jsonloanDataDetails.getString("kyc_status").toString().equals("null"))
                        LoanTabActivity.Postkyc_status = kyc_status = jsonloanDataDetails.getString("kyc_status");
                    if (!jsonloanDataDetails.getString("disbursal_status").toString().equals("null"))
                        LoanTabActivity.Postdisbursal_status = disbursal_status = jsonloanDataDetails.getString("disbursal_status");
                    if (!jsonloanDataDetails.getString("loan_agrement_upload_status").toString().equals("null"))
                        LoanTabActivity.Postloan_agrement_upload_status = loan_agrement_upload_status = jsonloanDataDetails.getString("loan_agrement_upload_status");
                }

                //href="http://159.89.204.41/eduhtmlbeta/agreement/downloadSignedAgreement/415"
                //href="http://159.89.204.41/eduvanzbeta/download/downloadENach/415">Download NACH</a

                if (paid_on.length() > 5) {
                    linPayBtn.setVisibility(GONE);
                    linPayStatus.setVisibility(VISIBLE);
                } else {
                    linPayBtn.setVisibility(VISIBLE);
                    linPayStatus.setVisibility(GONE);
                }

                if (disbursal_status.equals("0") || disbursal_status.equals("1")) //Disbursed
                {
                    txtLeadDisbursedStatus.setText("Disbursal Pending");
                    ivLeadDisbursed.setBackground(context.getResources().getDrawable(drawable.ic_exclamation_triangle));
                    linDisbursed.setBackground(context.getResources().getDrawable(drawable.border_circular_yellow_filled));
                } else {
                    txtLeadDisbursedStatus.setText("Loan Disbursed");
                    linDisbursed.setBackgroundResource(R.color.colorGreen);
                    ivLeadDisbursed.setBackground(context.getResources().getDrawable(drawable.ic_check_circle_white));
                    linDisbursed.setBackground(context.getResources().getDrawable(drawable.border_circular_green_filled));
                    if (MainActivity.mainUrl.toString().contains("eduvanzApi")) {
                        linDownloadNach.setTag(MainActivity.mainUrl.toString().replace("eduvanzApi/", "").concat("eduvanzbeta/download/downloadENach/").concat(MainActivity.lead_id));
                    } else {

                    }
                }

                if (loan_agrement_upload_status.equals("1")) {
                    linAggSignInBtn.setVisibility(GONE);
                    linDownloadAgreement.setTag(VISIBLE);
                    linDownloadAgreement.setVisibility(VISIBLE);
                    txtAggSignedStatus.setText("Agreement Signed");
                    ivAggSigned.setBackground(context.getResources().getDrawable(drawable.ic_check_circle_white));
                    linAgreementSigned.setBackground(context.getResources().getDrawable(drawable.border_circular_green_filled));
                    if (MainActivity.mainUrl.toString().contains("eduvanzApi")) {
                        linDownloadAgreement.setTag(MainActivity.mainUrl.toString().replace("eduvanzApi/", "").concat("eduhtmlbeta/agreement/downloadSignedAgreement/").concat(MainActivity.lead_id));
                    } else {

                    }

                } else {
                    linAggSignInBtn.setVisibility(VISIBLE);
                    linDownloadAgreement.setVisibility(GONE);
                    txtAggSignedStatus.setText("Agreement Signed Pending");
                    ivAggSigned.setBackground(context.getResources().getDrawable(drawable.ic_exclamation_triangle));
                    linAgreementSigned.setBackground(context.getResources().getDrawable(drawable.border_circular_yellow_filled));

                }
            }
            if (!jsonDataO.get("nachData").equals(null)) {
                JSONArray jsonArray1 = jsonDataO.getJSONArray("nachData");

                for (int i = 0; i < jsonArray1.length(); i++) {
                    MNach mNach = new MNach();
                    JSONObject jsonEmiDetails = jsonArray1.getJSONObject(i);

                    try {
                        if (!jsonEmiDetails.getString("person_name").toString().equals("null"))
                            mNach.person_name = jsonEmiDetails.getString("person_name");

                        if (!jsonEmiDetails.getString("amount").toString().equals("null"))
                            mNach.amount = jsonEmiDetails.getString("amount");

                        if (!jsonEmiDetails.getString("umrn_num").toString().equals("null"))
                            mNach.umrn_num = jsonEmiDetails.getString("umrn_num");

                        if (!jsonEmiDetails.getString("end_date").toString().equals("null"))
                            mNach.end_date = jsonEmiDetails.getString("end_date");

                        if (!jsonEmiDetails.getString("frequency").toString().equals("null"))

                            switch (jsonEmiDetails.getString("frequency").toString()) {
                                case "1":
                                    mNach.frequency = "Monthly";
                                    break;

                                case "2":
                                    mNach.frequency = "Quarterly";
                                    break;

                                case "3":
                                    mNach.frequency = "Half-Yearly";
                                    break;

                                case "4":
                                    mNach.frequency = "Yearly";
                                    break;

                                case "5":
                                    mNach.frequency = "As and when presented";
                                    break;
                            }
//                            mNach.frequency = jsonEmiDetails.getString("frequency");

                        if (!jsonEmiDetails.getString("debit_type").toString().equals("null"))
                            mNach.debit_type = jsonEmiDetails.getString("debit_type");

                        if (!jsonEmiDetails.getString("status").toString().equals("null"))
                            mNach.status = jsonEmiDetails.getString("status");

                    } catch (JSONException e) {
                        String className = this.getClass().getSimpleName();
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        String errorMsg = e.getMessage();
                        String errorMsgDetails = e.getStackTrace().toString();
                        String errorLine = String.valueOf(e.getStackTrace()[0]);
                        Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
                    }
                    mNachArrayList.add(mNach);
                }
                adapter = new NachAdapter(mNachArrayList, context, getActivity());
                rvNach.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

//            if (!jsonDataO.get("applicantonlineData").equals(null)) {
//                JSONObject jsonloanDataDetails = jsonDataO.getJSONObject("applicantonlineData");
////                MainApplication.lead_idkyc = lead_id = jsonloanDataDetails.getString("lead_id");
//            }

            try {
                String url = MainActivity.mainUrl + "laf/genrateAgreement";
                Map<String, String> params = new HashMap<String, String>();
                params.put("lead_id", MainActivity.lead_id);
                if (!Globle.isNetworkAvailable(context)) {
                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                } else {
                    VolleyCall volleyCall = new VolleyCall();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
                    volleyCall.sendRequest(context, url, null, mFragment, "genrateAgreement", params, MainActivity.auth_token);
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

    public void setgenrateAgreement(JSONObject jsonDataO) {
        Log.e(TAG, "setLoanDetails: " + jsonDataO);
        try {
//            progressDialog.dismiss();
            if (jsonDataO.getInt("status") == 1) {

                String message = jsonDataO.getString("message");

                if (!jsonDataO.get("applicantonlineData").equals(null)) {
                    JSONObject jsonloanDataDetails = jsonDataO.getJSONObject("applicantonlineData");
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


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private void manualSignInDialog() {

        View view = getLayoutInflater().inflate(layout.signin_options, null);
        LinearLayout linClose = view.findViewById(R.id.linClose);
        LinearLayout linDownload = view.findViewById(R.id.linDownload);
        LinearLayout linUpload = view.findViewById(R.id.linUpload);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                .setFooterView(view);

        CFAlertDialog cfAlertDialog = builder.show();
        cfAlertDialog.setCancelable(false);
        cfAlertDialog.setCanceledOnTouchOutside(false);


        linClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cfAlertDialog.dismiss();
            }
        });


        linDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Log.e(MainApplication.TAG, "downloadUrl+++++: " + downloadUrl);
                    Uri Download_Uri = Uri.parse(downloadUrl);

                    DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setAllowedOverRoaming(false);
                    request.setTitle("Downloading LAF Document");
                    //                request.setDescription("Downloading " + "Sample" + ".png");
                    request.setVisibleInDownloadsUi(true);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Eduvanz/" + "/" + "LAF" + ".pdf");
                    progressBar.setVisibility(VISIBLE);
                    downLoad(downloadUrl, 1, "LAF");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        linUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    galleryDocIntent();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void galleryDocIntent() {
        Intent intent = new Intent();
        intent.setType("application/pdf");  // for all types of file
//        intent.setType("application/pdf"); // for pdf
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_DOC);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_DOC) {

                    Uri selectedImage = data.getData();
                    uploadFilePath = PathFile.getPath(context, selectedImage);
                    Log.e("TAG", "onActivityResult: DOC PATH " + uploadFilePath);
                    if (!uploadFilePath.equalsIgnoreCase("")) {
//                        progressBar.setVisibility(View.VISIBLE);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //creating new thread to handle Http Operations
                                uploadFile(uploadFilePath);
                            }
                        }).start();
                    }

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

    public int uploadFile(final String selectedFilePath) {
//        String urlup = MainApplication.mainUrl + "laf/lafDocumentUpload";
        String urlup = MainActivity.mainUrl + "laf/uploadAgreement";
//        lead_id,student_id,myfile(file type),
        Log.e(MainApplication.TAG, "urlup++++++: " + selectedFilePath + ", ipaddress : " + "dds");

        int serverResponseCode = 0;
        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);

        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()) {
            //dialog.dismiss();
//            progressBar.setVisibility(View.GONE);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG", "run: " + "Source File Doesn't Exist: " + selectedFilePath);
                }
            });
            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(urlup);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("Authorization", "Bearer " + MainActivity.auth_token);
                connection.setRequestProperty("document", selectedFilePath);
//              connection.setRequestProperty("user_id", user_id);
                Log.e("TAG", "Server property" + connection.getRequestMethod() + ":property " + connection.getRequestProperties());

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"myfile\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0) {
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    Log.e("TAG", " here: \n\n" + buffer + "\n" + bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"lead_id\";lead_id=" + LoanTabActivity.lead_id + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(LoanTabActivity.lead_id);
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"student_id\";student_id=" + LoanTabActivity.student_id + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(LoanTabActivity.lead_id);
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                Log.e("TAG", " here:server response serverResponseCode\n\n" + serverResponseCode);
                String serverResponseMessage = connection.getResponseMessage();
                Log.e("TAG", " here: server message serverResponseMessage \n\n" + serverResponseMessage.toString() + "\n" + bufferSize);
                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                String output = "";
                sb = new StringBuffer();

                while ((output = br.readLine()) != null) {
                    sb.append(output);
                    Log.e("TAG", "uploadFile: " + br);
                    Log.e("TAG", "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);
                }
                Log.e("TAG", "uploadFile: " + sb.toString());

                try {
                    JSONObject mJson = new JSONObject(sb.toString());
                    final String mData = mJson.getString("status");
                    final String mData1 = mJson.getString("message");
                    final JSONObject mData2 = mJson.getJSONObject("result");

                    Log.e("TAG", "uploadFile: code " + mData);
                    if (mData.equalsIgnoreCase("1")) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mData1, Toast.LENGTH_SHORT).show();

//                                linManual.setVisibility(View.GONE);
//                                btnDownloadSignedApplication.setVisibility(View.VISIBLE);
//                                txtChooseSignType.setVisibility(View.GONE);
//                                rgSignType.setVisibility(View.GONE);
//                                txtSubmitstatus2.setText("Signed");
//                                txtSubmitstatus2.setTextColor(((Activity) context).getResources().getColor(R.color.colorPrimary));
                                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                try {
                                    editor.putString("signed_application_url", mData2.getString("docPath"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                editor.apply();
                                editor.commit();
                                try {
                                    downloadSignedUrl = mData2.getString("docPath");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
//                                progressBar.setVisibility(View.GONE);
                            }
                        });

                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mData1, Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
                            }
                        });
//                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("TAG", " here: \n\n" + fileName);
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, R.string.file_not_found, Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.GONE);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(context, R.string.url_error, Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, R.string.cannot_read_write_file, Toast.LENGTH_SHORT).show();
            }
//            dialog.dismiss();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    progressBar.setVisibility(View.GONE);
                }
            });
            return serverResponseCode;
        }

    }

    public void downLoad(String uri, int status, String fileName) {
        try {
            String fname = "";
            if (status == 1) {
                fname = "LAF" + userName + System.currentTimeMillis() + ".pdf";
            } else if (status == 2) {
                fname = fileName + userName + System.currentTimeMillis() + ".pdf";
            }
            downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
            Uri Download_Uri = Uri.parse(uri);
            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

            //Restrict the types of networks over which this download may proceed.
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            //Set whether this download may proceed over a roaming connection.
            request.setAllowedOverRoaming(false);
            //Set the title of this download, to be displayed in notifications (if enabled).
            request.setTitle("Your Document is Downloading");
            //Set a description of this download, to be displayed in notifications (if enabled)
            request.setDescription("Android Data download using DownloadManager.");
            //Set the local destination for the downloaded file to a path within the application's external files directory
//        if(isImage) {
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fname);//
//        }else {
//            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_PICTURES, "DATA"+System.currentTimeMillis()+".pdf");
//        }
            //Enqueue a new download and same the referenceId
            downloadReference = downloadManager.enqueue(request);
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
