package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCallAlgo360;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FinancialAnalysisOnNavigationBar extends AppCompatActivity {
    public static TextView txteduvanzCreditScore;
    public static Context context;
    public static AppCompatActivity mActivity;
    public ImageView ivbackbtn;
    SharedPref sharedPref;
    public static ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scorecard360nav);
        context = getApplicationContext();
        mActivity = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR

        ivbackbtn = findViewById(R.id.ivbackbtn);
        progressBar = findViewById(R.id.progressBar_finScore);

        ivbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DashboardActivity.class);
                startActivity(intent);
            }
        });

        txteduvanzCreditScore = findViewById(R.id.txteduvanzCreditScore);
        saveAlgo360();
    }

    public  void saveAlgo360() {
        /** API CALL **/
        try {
            //visible progressbar

            progressBar.setVisibility(View.VISIBLE);
            String url = MainActivity.mainUrl + "dashboard/algo360RiskScore";
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobile_no", DashboardActivity.userMobileNo);
            VolleyCallAlgo360 volleyCall = new VolleyCallAlgo360();

            volleyCall.sendRequest(context, url, mActivity, null, "addAlgoscorenavbar", params, MainActivity.auth_token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAlgo360ScoreOnNav(JSONObject jsonDataO) {

        //close progressbar

        try {
            progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String status = jsonDataO.optString("status");
        String riskscore = jsonDataO.optString("risk_score_card");
        try {
            if (riskscore.equals("") || riskscore.equals("null")) {
                txteduvanzCreditScore.setText("-");
            }else{
                txteduvanzCreditScore.setText(riskscore);
            }
        } catch (Exception e) {
            txteduvanzCreditScore.setText(riskscore);
        }
    }


}
