package com.eduvanzapplication.newUI.newViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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

        try {
            setContentView(R.layout.activity_emi_calculator);
            setViews();
            context = getApplicationContext();
            mActivity = this;

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("EMI Calculator");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
            toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
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
                        Snackbar.make(linCalculateBtn, "please enter loan amount", Snackbar.LENGTH_SHORT).show();
                } else if (edtRateOfInterest.toString().equals("")) {
                    Snackbar.make(linCalculateBtn, "Please enter rate of interest", Snackbar.LENGTH_SHORT).show();
                } else if (edtTenure.toString().equals("")) {
                    Snackbar.make(linCalculateBtn, "Please enter tenure", Snackbar.LENGTH_SHORT).show();
                    }
                    else {

                    //Getting value of principal ,rate,tenure(Months)
                    int principal = calprinc(Integer.parseInt(edtLoanAmt.getText().toString()));
                    float rate = calintrest(Float.parseFloat(edtRateOfInterest.getText().toString()));
                    int months = calMonth(Integer.parseInt(edtTenure.getText().toString()));
                        if (principal > 1000000) {
                            Snackbar.make(linCalculateBtn, "Loan amount not exceed than 10,00,000", Snackbar.LENGTH_SHORT).show();
                            txtCalculatedEmi.setText("");
                        } else if (rate >36) {
                            Snackbar.make(linCalculateBtn, "Rate of intrest not exceed than 36%", Snackbar.LENGTH_SHORT).show();
                            txtCalculatedEmi.setText("");
                        } else if (months > 96) {
                            Snackbar.make(linCalculateBtn, "Tenure not exceed than 96", Snackbar.LENGTH_SHORT).show();
                            txtCalculatedEmi.setText("");
                        }
                    //calculate (months*rate)+1200
                     float  calmonthsintorate=  calmonthsintorate(months,rate);

                  //calculate numerator result
                    float calnr=calnr(principal,calmonthsintorate);

                    //calculate denominator result
                    int caldr=caldr(months);
                    //calculate  EMI output
                    float EMIcalculate=calEMI(calnr,caldr);

                   int EMIoutput= (int)Math.ceil(EMIcalculate);

                    //store value in textbox EMI result
                    txtCalculatedEmi.setText(String.valueOf(EMIoutput));

                  /* // float dvdnt = caldvdnt(rate, months);
                    float fd = calFinalDvdnt(principal, dvdnt);
                    int divider = calDivider(months);

                    int emi = calEmi(fd, divider);*/

                //    txtCalculatedEmi.setText(String.valueOf(emi));
                    /*float ta = calTa(emi, months);
                    float ti = calTi(ta, principal);
                  txtCalculatedEmi.setText(String.valueOf(ti));*/

                    } }
        });
    }

    //method principal ,rate,months

    public int calprinc(int p) {
        return (int) p;
    }

    public float calintrest(float i) {
        return (float) i ;
    }

    public int calMonth(int y) {
        return (int) y ;
    }


    //method for calculate EMI output
    public float calmonthsintorate(int months,float rate){
        float calmonthsintorate=(int) (months*rate);
        calmonthsintorate= calmonthsintorate+1200;
        return calmonthsintorate;
    }
    public  float calnr(int principal,float calmonthsintorate){
        float calnr=(int)(principal*calmonthsintorate);
        return  calnr;

    }
    public  int caldr(int months){
        int caldr=months*1200;
        return  caldr;

    }
    public  float calEMI(float calnr,int caldr){
        float EMIcalculate=  (calnr/caldr);
        return  EMIcalculate;

    }



   /* public float calFinalDvdnt(float principal,  float dvdnt) {
        return (float) (principal* dvdnt);

    }

    public  int calDivider(int months) {
        return (int) (months*1200);
    }




    public float caldvdnt(float rate, int months) {

       *//* return (float) (Math.pow(1 + rate, months));*//*
        return (Float) (1200+(months*rate));
    }

    public int calEmi(float fd, int divider) {
        return (int) (fd / divider);

    }

    public float calTa(float emi, float months) {
        return (float) (emi * months);
    }

    public float calTi(float ta, float principal) {
        return (float) (ta - principal);

    }*/

}
