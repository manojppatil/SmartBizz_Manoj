package com.eduvanzapplication.newUI.newViews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.adapter.TenureOfferedAdapter;
import com.eduvanzapplication.newUI.adapter.TenureRequestedAdapter;
import com.eduvanzapplication.newUI.pojo.Mforoferedloan;
import com.eduvanzapplication.newUI.pojo.Mforrequestedloan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenureSelectionActivity extends AppCompatActivity implements TenureRequestedAdapter.SingleClickListener {

    List<Mforrequestedloan> horizontalList = new ArrayList<>();
    RecyclerView rvRequested;
    TenureRequestedAdapter requestedAdapter;
    LinearLayout linProceedTenure;
    ProgressDialog progressDialog;
    public static String leadid, requestedtenure, requestedroi, requestedemi, offeredamount, requestedloanamount, studentid, SLA, RLA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenure_selection);
        setViews();
        getTenureListApiCall();
//        setOfferedRecycler();
    }

    private void setOfferedRecycler() {
//        Mforrequestedloan mforrequestedloan = new Mforrequestedloan();
//        for (int i=0; i<10; i++){
//            mforrequestedloan = new Mforrequestedloan();
//            mforrequestedloan.emi_amount = "565588888888.3358";
//            mforrequestedloan.loan_amount = "888888888";
//            mforrequestedloan.tenure ="24";
//            horizontalList.add(mforrequestedloan);
//        }
//
//        rvRequested.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
//         requestedAdapter = new TenureRequestedAdapter(horizontalList, TenureSelectionActivity.this);
//        rvRequested.setAdapter(requestedAdapter);
//        rvRequested.setHasFixedSize(true);
    }

    private void setViews() {
        progressDialog = new ProgressDialog(TenureSelectionActivity.this);
        rvRequested = findViewById(R.id.rvOffered);
        linProceedTenure = findViewById(R.id.linProceedTenure);

        linProceedTenure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TenureSelectionActivity.this, DashboardActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    private void getTenureListApiCall() {
        /**API CALL**/
        try {
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String url = MainActivity.mainUrl + "dashboard/getTenureList";
            Map<String, String> params = new HashMap<String, String>();
            params.put("lead_id", NewLeadActivity.leadId);
            if (!Globle.isNetworkAvailable(getApplicationContext())) {
                Toast.makeText(TenureSelectionActivity.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {


                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(getApplicationContext(), url, TenureSelectionActivity.this, null, "getTenureList", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(TenureSelectionActivity.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void getTenureList(JSONObject jsonData) {
        try {

            progressDialog.dismiss();

            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            requestedloanamount = jsonData.optString("requested_loan_amount");
            String has_coborrower = jsonData.optString("has_coborrower");
            leadid = jsonData.optString("lead_id");


            if (status.equalsIgnoreCase("1")) {

                if (jsonData.getJSONArray("forrequestedloan").length() > 0) {

                    JSONArray jsonArray1 = jsonData.getJSONArray("forrequestedloan");
                    horizontalList = new ArrayList<>();
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        Mforrequestedloan mforrequestedloan = new Mforrequestedloan();
                        JSONObject jsonleadStatus = jsonArray1.getJSONObject(i);

                        try {
                            mforrequestedloan.tenure = jsonleadStatus.getString("tenure");
                            mforrequestedloan.flat_interest = jsonleadStatus.getString("flat_interest");
                            mforrequestedloan.emi_amount = jsonleadStatus.getString("emi_amount");
                            mforrequestedloan.loan_amount = jsonleadStatus.getString("loan_amount");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        horizontalList.add(mforrequestedloan);

                    }

                    requestedAdapter = new TenureRequestedAdapter(horizontalList, TenureSelectionActivity.this);
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    rvRequested.setLayoutManager(horizontalLayoutManager);
                    rvRequested.setAdapter(requestedAdapter);
                    rvRequested.setHasFixedSize(true);
                    requestedAdapter.setOnItemClickListener(this);

                } else {

                }



            } else {
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(TenureSelectionActivity.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    @Override
    public void onItemClickListener(int position, View view, int id) {
        switch (id) {

            case R.id.rbrequested:

                requestedAdapter.selectedItem();
                requestedtenure = horizontalList.get(position).tenure;
                requestedroi = horizontalList.get(position).roi;
                requestedemi = horizontalList.get(position).emi_amount;
                offeredamount = horizontalList.get(position).loan_amount;
                SLA = "0";
                RLA = requestedloanamount;

                break;

//            case R.id.rbOffered:
//
//                tenureOfferedAdapter.selectedItem();
//                requestedtenure = mforoferedloanArrayList.get(position).tenure;
//                requestedroi = mforoferedloanArrayList.get(position).roi;
//                requestedemi = mforoferedloanArrayList.get(position).emi_amount;
//                offeredamount = mforoferedloanArrayList.get(position).loan_amount;
//                SLA = offeredamount;
//                RLA = "0";
//                break;
        }

    }
}
