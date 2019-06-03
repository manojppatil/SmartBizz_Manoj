package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCallAlgo360;
import com.eduvanzapplication.newUI.fragments.DashboardFragmentNew;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FinancialAnalysis extends AppCompatActivity {
    public static TextView txteduvanzCreditScore;
    public static Context context;
    public static AppCompatActivity mActivity;
    SharedPref sharedPref;
    public static ProgressBar progressBar;
    public static LinearLayout linContinue;

    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorecard360);
        context = getApplicationContext();
        mActivity = this;
        linContinue = findViewById(R.id.linContinue);
        progressBar = findViewById(R.id.progressBar_finScore);

        linContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinancialAnalysis.this, TenureSelectionActivity.class));
                FinancialAnalysis.this.finish();
                finish();
            }
        });
        txteduvanzCreditScore = findViewById(R.id.txteduvanzCreditScore);
        saveAlgo360();
    }

    public static void saveAlgo360() {
        /** API CALL **/
        try {//auth_token
//            progressBar.setVisibility(View.VISIBLE);
//            String url = "http://192.168.1.63/eduvanzapi/dashboard/algo360RiskScore";
            String url = MainActivity.mainUrl + "dashboard/saveAlgo360response";
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile_no", DashboardActivity.userMobileNo);
            VolleyCallAlgo360 volleyCall = new VolleyCallAlgo360();
//            volleyCall.sendRequest(context, url, mActivity, null, "addAlgoscore", params, "90ad441a12b48c6d7c5524b8b2a334c3");
            volleyCall.sendRequest(context, url, mActivity, null, "addAlgoscore", params, MainActivity.auth_token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAlgo360Score(JSONObject jsonDataO) {

//        try {
//            progressBar.setVisibility(View.GONE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String status = jsonDataO.optString("status");
        String riskscore = jsonDataO.optString("risk_score_card");
        txteduvanzCreditScore.setText(riskscore);

    }


}