package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Data;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.adapter.TenureOfferedAdapter;
import com.eduvanzapplication.newUI.adapter.TenureRequestedAdapter;
import com.eduvanzapplication.newUI.newViews.EligibilityCheck;
import com.eduvanzapplication.newUI.newViews.LoanApplication;
import com.eduvanzapplication.newUI.pojo.Mforoferedloan;
import com.eduvanzapplication.newUI.pojo.Mforrequestedloan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EligibilityCheckFragment_6 extends Fragment implements TenureRequestedAdapter.SingleClickListener, TenureOfferedAdapter.Single1ClickListener {

    public static Context context;
    public static Fragment mFragment;
    public static Button btnSubmitEl6;
    Typeface typefaceFont, typefaceFontBold;
    public static RecyclerView recyclerTenureOffered, recyclerTenureRequested;
    public TenureOfferedAdapter tenureOfferedAdapter;
    public TenureRequestedAdapter tenureRequestedAdapter;
    public List<Mforrequestedloan> mforrequestedloanArrayList = new ArrayList<>();
    public List<Mforoferedloan> mforoferedloanArrayList = new ArrayList<>();
    public static LinearLayout linOffered, linRequested,linAddBorrower;
    String lead_id = "", application_id = "";
    public static FragmentTransaction transaction;
    public static String leadid, requestedtenure, requestedroi, requestedemi, offeredamount, requestedloanamount, studentid, SLA, RLA;

    List<Data> data;
    static ProgressBar progressBar;
    SharedPref shareP;

    public EligibilityCheckFragment_6() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eligibilitycheck_6, container, false);
        try {
            context = getContext();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            mFragment = new EligibilityCheckFragment_6();
            progressBar = (ProgressBar) view.findViewById(R.id.proEligibility6);

            recyclerTenureOffered = (RecyclerView) view.findViewById(R.id.recyclerTenureOffered);
            recyclerTenureRequested = (RecyclerView) view.findViewById(R.id.recyclerTenureRequested);

            linOffered = (LinearLayout) view.findViewById(R.id.linOffered);
            linRequested = (LinearLayout) view.findViewById(R.id.linRequested);
            linAddBorrower = (LinearLayout) view.findViewById(R.id.linAddBorrower);

            typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
            typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

            final FragmentTransaction transaction = getFragmentManager().beginTransaction();

            btnSubmitEl6 = (Button) view.findViewById(R.id.btnSubmitEl6);
            btnSubmitEl6.setTypeface(typefaceFontBold);


            btnSubmitEl6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    saveTenureApiCall();

                }
            });


            getTenureListApiCall();

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
    }

    private void getTenureListApiCall() {
        /**API CALL**/
        try {

            String url = MainApplication.mainUrl + "dashboard/getTenureList";
            Map<String, String> params = new HashMap<String, String>();
            params.put("lead_id", MainApplication.lead_id);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {

                progressBar.setVisibility(View.VISIBLE);

                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getTenureList", params, MainApplication.auth_token);
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

    public void getTenureList(JSONObject jsonData) {
        try {

            progressBar.setVisibility(View.GONE);

            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            offeredamount = jsonData.optString("offered_loan_amount");
            requestedloanamount = jsonData.optString("requested_loan_amount");
            String has_coborrower = jsonData.optString("has_coborrower");
            leadid = jsonData.optString("lead_id");

            try {
                if(has_coborrower.equals("1"))
                {
                    linAddBorrower.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (status.equalsIgnoreCase("1")) {

                if (jsonData.getJSONArray("forrequestedloan").length() > 0) {

                    try {
                        linRequested.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    JSONArray jsonArray1 = jsonData.getJSONArray("forrequestedloan");
                    mforrequestedloanArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        Mforrequestedloan mforrequestedloan = new Mforrequestedloan();
                        JSONObject jsonleadStatus = jsonArray1.getJSONObject(i);

                        try {
                            mforrequestedloan.tenure = jsonleadStatus.getString("tenure");
                            mforrequestedloan.flat_interest = jsonleadStatus.getString("flat_interest");
                            mforrequestedloan.emi_amount = jsonleadStatus.getString("emi_amount");
                            mforrequestedloan.loan_amount = jsonleadStatus.getString("loan_amount");
                            mforrequestedloan.roi = jsonleadStatus.getString("roi");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mforrequestedloanArrayList.add(mforrequestedloan);

                    }

                    tenureRequestedAdapter = new TenureRequestedAdapter(mforrequestedloanArrayList, getActivity());
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerTenureRequested.setLayoutManager(horizontalLayoutManager);
                    recyclerTenureRequested.setAdapter(tenureRequestedAdapter);
                    recyclerTenureRequested.setHasFixedSize(true);
                    tenureRequestedAdapter.setOnItemClickListener(this);

                } else {

                    try {
                        linRequested.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (jsonData.getJSONArray("foroferedloan").length() > 0) {

                    try {
                        linOffered.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JSONArray jsonArray2 = jsonData.getJSONArray("foroferedloan");
                    mforoferedloanArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        Mforoferedloan mforrequestedloan = new Mforoferedloan();
                        JSONObject jsonleadStatus = jsonArray2.getJSONObject(i);

                        try {

                            mforrequestedloan.tenure = jsonleadStatus.getString("tenure");
                            mforrequestedloan.flat_interest = jsonleadStatus.getString("flat_interest");
                            mforrequestedloan.emi_amount = jsonleadStatus.getString("emi_amount");
                            mforrequestedloan.loan_amount = jsonleadStatus.getString("loan_amount");
                            mforrequestedloan.roi = jsonleadStatus.getString("roi");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mforoferedloanArrayList.add(mforrequestedloan);
                    }

                    tenureOfferedAdapter = new TenureOfferedAdapter(mforoferedloanArrayList, getActivity());
                    LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerTenureOffered.setLayoutManager(horizontalLayoutManager1);
                    recyclerTenureOffered.setAdapter(tenureOfferedAdapter);
                    recyclerTenureOffered.setHasFixedSize(true);
                    tenureOfferedAdapter.setOnItemClickListener(this);
                } else {
                    try {
                        linOffered.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            } else {
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

    private void saveTenureApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "dashboard/saveTenure";
            Map<String, String> params = new HashMap<String, String>();
            params.put("lead_id", leadid);
            params.put("requested_tenure", requestedtenure);
            params.put("requested_roi", requestedroi);
            params.put("requested_emi", requestedemi);
            params.put("offered_amount", offeredamount);
            params.put("student_id", MainApplication.student_id);
            params.put("SLA", offeredamount);
            params.put("RLA", requestedloanamount);

            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {

                progressBar.setVisibility(View.VISIBLE);

                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "saveTenure", params, MainApplication.auth_token);
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

    public void saveTenure(JSONObject jsonData) {
        try {

            progressBar.setVisibility(View.GONE);

            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                Intent intent = new Intent(context, LoanApplication.class);
                Bundle bundle = new Bundle();
                bundle.putString("lead_id", MainApplication.lead_id);
                bundle.putString("application_id", MainApplication.application_id);
                bundle.putString("fillinstutute", "filled");
                intent.putExtras(bundle);
                context.startActivity(intent);
                getActivity().finish();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

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

    @Override
    public void onItemClickListener(int position, View view, int id) {

        switch (id) {

            case R.id.rbrequested:

                tenureRequestedAdapter.selectedItem();
                requestedtenure = mforrequestedloanArrayList.get(position).tenure;
                requestedroi = mforrequestedloanArrayList.get(position).roi;
                requestedemi = mforrequestedloanArrayList.get(position).emi_amount;
                offeredamount = mforrequestedloanArrayList.get(position).loan_amount;
                SLA = "";
                RLA = requestedloanamount;

                break;

            case R.id.rbOffered:

                tenureOfferedAdapter.selectedItem();
                requestedtenure = mforoferedloanArrayList.get(position).tenure;
                requestedroi = mforoferedloanArrayList.get(position).roi;
                requestedemi = mforoferedloanArrayList.get(position).emi_amount;
                offeredamount = mforoferedloanArrayList.get(position).loan_amount;
                SLA = offeredamount;
                RLA = "";
                break;
        }


    }


}
