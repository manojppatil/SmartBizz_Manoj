package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.pojo.MLeads;
import com.eduvanzapplication.newUI.pojo.MLoanEmis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eduvanzapplication.newUI.MainApplication.TAG;
import static com.eduvanzapplication.newUI.newViews.LoanTabActivity.lead_id;


public class AmortizationFragment extends Fragment {

    static View view;
    private Context context;
    private Fragment mFragment;
    public static List<MLoanEmis> mLoanEmisArrayList = new ArrayList<>();

    public AmortizationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_amortization, container, false);

        context = getContext();
        mFragment = new AmortizationFragment();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        try {
            String url = MainActivity.mainUrl + "dashboard/ammortisation";
            Map<String, String> params = new HashMap<String, String>();
            params.put("lead_id", lead_id);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "getAmortDetails", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }

        return view;

    }

    public void setAmortDetails(JSONObject jsonDataO) {
        Log.e(TAG, "setProfileDashbBoardStatus: " + jsonDataO);
        try {//
//            progressDialog.dismiss();
            if (jsonDataO.getInt("status") == 1) {

                String message = jsonDataO.getString("message");

                JSONArray jsonArray1 = jsonDataO.getJSONArray("loanEmiDetails");

                for (int i = 0; i < jsonArray1.length(); i++) {
                    MLoanEmis mLoanEmis = new MLoanEmis();
                    JSONObject jsonEmiDetails = jsonArray1.getJSONObject(i);

                    try {

                        if (!jsonEmiDetails.getString("loan_emi_id").toString().equals("null"))
                            mLoanEmis.loan_emi_id = jsonEmiDetails.getString("loan_emi_id");

                        if (!jsonEmiDetails.getString("loan_id").toString().equals("null"))
                            mLoanEmis.loan_id = jsonEmiDetails.getString("loan_id");

                        if (!jsonEmiDetails.getString("emi_no").toString().equals("null"))
                            mLoanEmis.emi_no = jsonEmiDetails.getString("emi_no");

                        if (!jsonEmiDetails.getString("dpd").toString().equals("null"))
                            mLoanEmis.dpd = jsonEmiDetails.getString("dpd");

                        if (!jsonEmiDetails.getString("emi_amount").toString().equals("null"))
                            mLoanEmis.emi_amount = jsonEmiDetails.getString("emi_amount");

                        if (!jsonEmiDetails.getString("principle_amount").toString().equals("null"))
                            mLoanEmis.principle_amount = jsonEmiDetails.getString("principle_amount");

                        if (!jsonEmiDetails.getString("interest_amount").toString().equals("null"))
                            mLoanEmis.interest_amount = jsonEmiDetails.getString("interest_amount");

                        if (!jsonEmiDetails.getString("balance").toString().equals("null"))
                            mLoanEmis.balance = jsonEmiDetails.getString("balance");

                        if (!jsonEmiDetails.getString("proposed_payment_date").toString().equals("null"))
                            mLoanEmis.proposed_payment_date = jsonEmiDetails.getString("proposed_payment_date");

                        if (!jsonEmiDetails.getString("actual_payment_date").toString().equals("null"))
                            mLoanEmis.actual_payment_date = jsonEmiDetails.getString("actual_payment_date");

                        if (!jsonEmiDetails.getString("final_payment_date").toString().equals("null"))
                            mLoanEmis.final_payment_date = jsonEmiDetails.getString("final_payment_date");

                        if (!jsonEmiDetails.getString("total_amount_paid").toString().equals("null"))
                            mLoanEmis.total_amount_paid = jsonEmiDetails.getString("total_amount_paid");

                        if (!jsonEmiDetails.getString("status").toString().equals("null"))
                            mLoanEmis.status = jsonEmiDetails.getString("status");

                        if (!jsonEmiDetails.getString("comments").toString().equals("null"))
                            mLoanEmis.comments = jsonEmiDetails.getString("comments");


                    } catch (JSONException e) {
                        String className = this.getClass().getSimpleName();
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        String errorMsg = e.getMessage();
                        String errorMsgDetails = e.getStackTrace().toString();
                        String errorLine = String.valueOf(e.getStackTrace()[0]);
                        Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
                    }
                    mLoanEmisArrayList.add(mLoanEmis);

                }

//                adapter = new CardStackAdapter(mLeadsArrayList, context, getActivity());
//                manager = new CardStackLayoutManager(context,this);
//                setupCardStackView();
//                setupButton();

            } else {
                String message = jsonDataO.getString("message");
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
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
