package com.eduvanzapplication.newUI.newViews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
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
    public  static  LinearLayout linHeader;
    public static TextView txtTenureTitle;
    public static RelativeLayout rel1,rel2;
    ProgressDialog progressDialog;
    public static String leadid ="", requestedtenure ="", requestedroi ="", requestedemi ="", offeredamount="",
            requestedloanamount ="", studentid = "", SLA = "", RLA = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenure_selection);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR

        setViews();
        getTenureListApiCall();
    }

    private void setViews() {
        progressDialog = new ProgressDialog(TenureSelectionActivity.this);
        rel1 = findViewById(R.id.rel1);
        rel2 = findViewById(R.id.rel2);
        rvRequested = findViewById(R.id.rvOffered);
        linProceedTenure = findViewById(R.id.linProceedTenure);
        txtTenureTitle = findViewById(R.id.txtTenureTitle);
        linHeader = findViewById(R.id.linHeader);

        linProceedTenure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(SLA.equals("0")) {
                        saveTenureApiCall();
                    }else{
                        Toast.makeText(getApplicationContext(), "Please select tenure", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

    private void saveTenureApiCall() {
        /**API CALL**/
        try {
            String url = MainActivity.mainUrl + "dashboard/saveTenure";
            Map<String, String> params = new HashMap<String, String>();
            params.put("lead_id", leadid);
            if(requestedtenure == null){
                requestedtenure ="";
            }if(requestedroi == null){
                requestedroi ="";
            }if(requestedemi == null){
                requestedemi ="";
            }if(offeredamount == null){
                offeredamount ="";
            }if(requestedloanamount == null){
                requestedloanamount ="";
            }
            params.put("requested_tenure", requestedtenure);
            params.put("requested_roi", requestedroi);
            params.put("requested_emi", requestedemi);
            params.put("offered_amount", offeredamount);
            params.put("student_id", DashboardActivity.student_id);
            params.put("SLA", offeredamount);
            params.put("RLA", requestedloanamount);

            if (!Globle.isNetworkAvailable(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                progressDialog.setMessage("Submitting Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(getApplicationContext(), url, TenureSelectionActivity.this, null, "saveTenure", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(TenureSelectionActivity.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void saveTenure(JSONObject jsonData) {
        try {
            progressDialog.dismiss();
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

             /*   startActivity(new Intent(TenureSelectionActivity.this, DashboardActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                TenureSelectionActivity.this.finish();*/

                Intent intent = new Intent(TenureSelectionActivity.this, LoanTabActivity.class);
                intent.putExtra("tenureselectedflag","1");
                intent.putExtra("lead_id", leadid);

                startActivity(intent);
                TenureSelectionActivity.this.finish();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getApplicationContext(), className, name, errorMsg, errorMsgDetails, errorLine);
        }
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

            requestedloanamount = jsonData.optString("requested_loan_amount");
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
                            mforrequestedloan.roi = jsonleadStatus.getString("roi");
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
                    txtTenureTitle.setVisibility(View.GONE);
                    linHeader.setVisibility(View.GONE);
                }

            } else {
                txtTenureTitle.setVisibility(View.GONE);
                linHeader.setVisibility(View.GONE);
                rel1.setVisibility(View.GONE);
                rel2.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
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

        }

    }
}
