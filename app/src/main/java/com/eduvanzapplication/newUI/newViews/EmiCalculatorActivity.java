package com.eduvanzapplication.newUI.newViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.pqformfragments.pojo.LocationsPOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfCoursePOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfInsitituePOJO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmiCalculatorActivity extends AppCompatActivity {

    public static LinearLayout linCalculateBtn;
    public static EditText edtLoanAmt, edtRateOfInterest, edtTenure;
    public static TextView txtCalculatedEmi;
    public static Context context;
    public static AppCompatActivity mActivity;
    SharedPreferences sharedPreferences;
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emi_calculator);
        setViews();
        context = getApplicationContext();
        mActivity = this;

    }

    private void setViews() {
        linCalculateBtn = findViewById(R.id.linCalculateBtn);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        progressDialog = new ProgressDialog(EmiCalculatorActivity.this);

        edtLoanAmt = findViewById(R.id.edtLoanAmt);
        edtRateOfInterest = findViewById(R.id.edtRateOfInterest);
        edtTenure = findViewById(R.id.edtTenure);
        txtCalculatedEmi = findViewById(R.id.txtCalculatedEmi);

        linCalculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtLoanAmt.getText().toString().equals("")) {
                    Snackbar.make(linCalculateBtn, "Please enter loan amount", Snackbar.LENGTH_SHORT).show();
                } else if (edtRateOfInterest.toString().equals("")) {
                    Snackbar.make(linCalculateBtn, "Please enter rate of interest", Snackbar.LENGTH_SHORT).show();
                } else if (edtTenure.toString().equals("")) {
                    Snackbar.make(linCalculateBtn, "Please enter tenure", Snackbar.LENGTH_SHORT).show();
                } else {

                    //use method
                    float principal = calprinc(Float.parseFloat(edtLoanAmt.getText().toString()));
                    float rate = calintrest(Float.parseFloat(edtRateOfInterest.getText().toString()));
                    float months = calMonth(Float.parseFloat(edtTenure.getText().toString()));

                    float dvdnt = caldvdnt(rate, months);
                    float fd = calFinalDvdnt(principal, rate, dvdnt);
                    float divider = calDivider(dvdnt);

                    float emi = calEmi(fd, divider);

                    txtCalculatedEmi.setText(String.valueOf(emi));
                    float ta = calTa(emi, months);
                    float ti = calTi(ta, principal);
                    txtCalculatedEmi.setText(String.valueOf(ti));
                }
            }
        });
    }

    public float calprinc(float p) {
        return (float) p;
    }

    public float calintrest(float i) {
        return (float) i / 12 / 100;
    }

    public float calMonth(float y) {
        return (float) y * 12;
    }

    public float calFinalDvdnt(float principal, float rate, float dvdnt) {
        return (float) (principal * rate * dvdnt);

    }

    public float calDivider(float dvdnt) {
        return (float) (dvdnt - 1);
    }


    public float caldvdnt(float rate, float months) {
        return (float) (Math.pow(1 + rate, months));
    }

    public float calEmi(float fd, float divider) {
        return (float) (fd / divider);

    }

    public float calTa(float emi, float months) {
        return (float) (emi * months);
    }

    public float calTi(float ta, float principal) {
        return (float) (ta - principal);

    }

}
