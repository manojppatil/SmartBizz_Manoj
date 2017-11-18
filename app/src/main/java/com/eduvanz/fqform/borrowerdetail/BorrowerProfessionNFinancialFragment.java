package com.eduvanz.fqform.borrowerdetail;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.fqform.borrowerdetail.pojo.BorrowerProfessionFinancePOJO;
import com.eduvanz.volley.VolleyCall;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BorrowerProfessionNFinancialFragment extends Fragment {

    public static Context context;
    public static RadioButton radioButtonisEmployed, radioButtonisStudent;
    public Button buttonSubmit;
    LinearLayout linearLayoutEmployed;
    Fragment mFragment;
    String userID="";
    public static EditText editTextAdvancePayment, editTextAnualIncome, editTextNameofCompany;
    public static Spinner spinnerJobDuration;
    public static ArrayAdapter arrayAdapter;
    public static ArrayList<String> professionfinance_arratList;
    public static ArrayList<BorrowerProfessionFinancePOJO> borrowerProfessionFinancePOJOArrayList;
    public String jobDurationID="";
    public BorrowerProfessionNFinancialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fq_professionandfinancial_fragment3, container, false);
        context = getContext();

        mFragment = new BorrowerProfessionNFinancialFragment();

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("logged_id","null");

//        buttonSubmit = (Button) view.findViewById(R.id.button_submit_finance_borrower);
        radioButtonisStudent = (RadioButton) view.findViewById(R.id.radiobutton_isstudent);
        radioButtonisEmployed = (RadioButton) view.findViewById(R.id.radiobutton_isemployed);
        linearLayoutEmployed = (LinearLayout) view.findViewById(R.id.linearlayout_isempoyeed);
        spinnerJobDuration = (Spinner) view.findViewById(R.id.spinner_jobduration);
        editTextAdvancePayment = (EditText) view.findViewById(R.id.input_advancepayment);
        editTextAnualIncome = (EditText) view.findViewById(R.id.input_annualincome);
        editTextNameofCompany = (EditText) view.findViewById(R.id.input_nameofcompany);

        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup_profession);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("TAG", "onCheckedChanged: " );
                Log.e("TAG", "onCheckedChanged: "+radioGroup.getCheckedRadioButtonId());
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.radiobutton_isemployed:
                        Log.e("TAG", "employed: " );
                        linearLayoutEmployed.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radiobutton_isstudent:
                        Log.e("TAG", "student: " );
                        linearLayoutEmployed.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        spinnerJobDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("I_________D", "onItemClick: " );
                String text = spinnerJobDuration.getSelectedItem().toString();
                int count = borrowerProfessionFinancePOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (borrowerProfessionFinancePOJOArrayList.get(i).durationName.equalsIgnoreCase(text)) {
                        jobDurationID = borrowerProfessionFinancePOJOArrayList.get(i).durationID;
                        Log.e("I_________D", "onItemClick: " + jobDurationID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------------------------------API CALL------------------------------------//
                try {
                    String isWorking="";
                    if(radioButtonisEmployed.isChecked())
                    {
                        isWorking = "1";
                    }else if(radioButtonisStudent.isChecked()){
                        isWorking = "2";
                    }
                    String url = MainApplication.mainUrl + "algo/setfinanceDetails";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("logged_id", userID);
                    params.put("is_student_working",isWorking);
                    params.put("student_working_organization", editTextNameofCompany.getText().toString());
                    params.put("working_organization_since", jobDurationID);
                    params.put("student_income",editTextAnualIncome.getText().toString());
                    params.put("advance_payment",editTextAdvancePayment.getText().toString());

                    VolleyCall volleyCall = new VolleyCall();
                    volleyCall.sendRequest(context, url, null, mFragment, "setborrowerFinance", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //---------------------------------END OF API CALL--------------------------------//
            }
        });

        //-----------------------------------------API CALL---------------------------------------//
        try {
            String url = MainApplication.mainUrl + "algo/getfinanceDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("logged_id",userID);
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(context, url, null, mFragment, "borrowerProfessionalNFinance", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-------------------------------------END OF API CALL------------------------------------//

        return view;
    }//----------------------------------------END OF ONCREATE-----------------------------------//

    //---------------------------------RESPONSE OF API CALL---------------------------------------//
    public void borrowerProfessionalNFinance(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendOtp" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = jsonData.getJSONObject("result");
                JSONArray jsonArray1 = jsonObject.getJSONArray("jobDuration");

                professionfinance_arratList = new ArrayList<>();
                borrowerProfessionFinancePOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray1.length(); i++) {
                    BorrowerProfessionFinancePOJO borrowerProfessionFinancePOJO = new BorrowerProfessionFinancePOJO();
                    JSONObject mJsonti = jsonArray1.getJSONObject(i);
                    borrowerProfessionFinancePOJO.durationName = mJsonti.getString("name");
                    professionfinance_arratList.add(mJsonti.getString("name"));
                    borrowerProfessionFinancePOJO.durationID = mJsonti.getString("id");
                    borrowerProfessionFinancePOJOArrayList.add(borrowerProfessionFinancePOJO);
                    Log.e(MainApplication.TAG, "borrowerEducationDegreePOJO Spiner DATA:----------------- " + borrowerProfessionFinancePOJO.durationName);
                }
                arrayAdapter = new ArrayAdapter(context, R.layout.custom_layout_spinner, professionfinance_arratList);
                spinnerJobDuration.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();

                JSONObject jsonObject1 = jsonObject.getJSONObject("financialDetails");
                String isWorking = jsonObject1.getString("is_student_working");
                String advancePayment = jsonObject1.getString("advance_payment");
                String nameofCompany = jsonObject1.getString("student_working_organization");
                jobDurationID = jsonObject1.getString("working_organization_since");
                String anualIncome  = jsonObject1.getString("student_income");

                editTextAdvancePayment.setText(advancePayment);
                editTextAnualIncome.setText(anualIncome);
                editTextNameofCompany.setText(nameofCompany);
                spinnerJobDuration.setSelection(Integer.parseInt(jobDurationID));

                if(isWorking.equalsIgnoreCase("1")){
                    radioButtonisEmployed.setChecked(true);
                }else if(isWorking.equalsIgnoreCase("2")){
                    radioButtonisStudent.setChecked(true);
                }

            }else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setborrowerFinance(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "setborrowerFinance" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }

}
