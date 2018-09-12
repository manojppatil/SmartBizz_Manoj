package com.eduvanzapplication.fqform.coborrowerdetail;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerJobDurationFinancePOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerProfessionFinancePOJO;
import com.eduvanzapplication.volley.VolleyCall;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoBorrowerFinancialFragment extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    public Button buttonSubmit;
    Calendar cal;
    Typeface typeface;
    public static TextView lable, textViewDurationofJob, calender;
    public String dateformate="", userID="";

    public static EditText anuualincome, employeer;

    public static Spinner spinnerProfession;
    public static ArrayAdapter profession_arrayAdapter;
    public static ArrayList<String> professionfinance_arratList;
    public static ArrayList<CoborrowerProfessionFinancePOJO> coborrowerProfessionFinancePOJOArrayList;
    public String professionID="";

    public static Spinner spinnerJobDuration;
    public static ArrayAdapter jobduration_arrayAdapter;
    public static ArrayList<String> jobduration_arratList;
    public static ArrayList<CoborrowerJobDurationFinancePOJO> coborrowerJobDurationFinancePOJOArrayList;
    public String jobDurationID="";

    public CoBorrowerFinancialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coborrower_financial, container, false);
        context = getContext();
        mFragment = new CoBorrowerFinancialFragment();
        typeface = Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("logged_id","null");

        spinnerJobDuration = (Spinner) view.findViewById(R.id.spinner_durationofjob_coborrower);
        spinnerProfession = (Spinner) view.findViewById(R.id.spinner_profession_coborrower);
        anuualincome = (EditText) view.findViewById(R.id.input_annualincome_coborrower);
        employeer = (EditText) view.findViewById(R.id.input_employeer_coborrower);
        buttonSubmit = (Button) view.findViewById(R.id.button_submit_financial_coborrower);

        spinnerJobDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("I_________D", "onItemClick: " );
                String text = spinnerJobDuration.getSelectedItem().toString();
                int count = coborrowerJobDurationFinancePOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (coborrowerJobDurationFinancePOJOArrayList.get(i).durationName.equalsIgnoreCase(text)) {
                        jobDurationID = coborrowerJobDurationFinancePOJOArrayList.get(i).durationID;
                        Log.e("I_________D", "jobDurationID: " + jobDurationID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("I_________D", "onItemClick: " );
                String text = spinnerProfession.getSelectedItem().toString();
                int count = coborrowerProfessionFinancePOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (coborrowerProfessionFinancePOJOArrayList.get(i).professionName.equalsIgnoreCase(text)) {
                        professionID = coborrowerProfessionFinancePOJOArrayList.get(i).professionID;
                        Log.e("I_________D", "professionID: " + professionID);
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
                    String url = MainApplication.mainUrl + "algo/setCoborrowerFinancialDetails";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("logged_id", userID);
                    params.put("coborrower_profession",professionID);
                    params.put("coborrower_income", anuualincome.getText().toString());
                    params.put("coborrower_organization", employeer.getText().toString());
                    params.put("coborrower_working_organization_since",jobDurationID);

                    VolleyCall volleyCall = new VolleyCall();
                    volleyCall.sendRequest(context, url, null, mFragment, "setcoborrowerFinance", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //---------------------------------END OF API CALL--------------------------------//
            }
        });


        lable = (TextView) view.findViewById(R.id.lable_fqformcoborrower);
        textViewDurationofJob = (TextView) view.findViewById(R.id.input_durationofjob_coborrower);
        calender = (TextView) view.findViewById(R.id.durationofjob_coborrower);
        calender.setTypeface(typeface);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int month =monthOfYear+1;
                String datenew = dayOfMonth + "/" + month + "/" + year;
                dateformate = dateFormateSystem(datenew);
                textViewDurationofJob.setText(dateformate);
                textViewDurationofJob.setTextColor(getResources().getColor(R.color.black));
                lable.setVisibility(View.VISIBLE);
            }

        };
        cal = Calendar.getInstance();
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DatePickerDialog(SignUpStepOne.this, date, cal
//                        .get(Calendar.YEAR), cal.get(Calendar.MONTH),
//                        cal.get(Calendar.DAY_OF_MONTH)).show();
                cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
//                cal.set(Calendar.MONTH, 1);
//                cal.set(Calendar.DAY_OF_MONTH, 1);
                DatePickerDialog data=  new DatePickerDialog(context, date, cal
                        .get(Calendar.YEAR)-18, 1,
                        1);
                data.getDatePicker().setMaxDate(System.currentTimeMillis()-1234564);
                data.show();
            }
        });

        //-----------------------------------------API CALL---------------------------------------//
        try {
            String url = MainApplication.mainUrl + "algo/getCoborrowerFinancialDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("logged_id",userID);
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(context, url, null, mFragment, "coBorrowerProfessionalNFinance", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-------------------------------------END OF API CALL------------------------------------//


        return view;
    }//-----------------------------------------END OF ONCREATE-----------------------------------//

    public static String dateFormateSystem(String date)
    {
        String dateformate2 = null;
        try {
            String birthDate = date;
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            Date dateformate = fmt.parse(birthDate);
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yyyy");
            dateformate2 = fmtOut.format(dateformate);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return dateformate2;
    }

    //---------------------------------RESPONSE OF API CALL---------------------------------------//
    public void coBorrowerProfessionalNFinance(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendOtp" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = jsonData.getJSONObject("result");
                JSONArray jsonArray1 = jsonObject.getJSONArray("jobDuration");

                jobduration_arratList = new ArrayList<>();
                coborrowerJobDurationFinancePOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray1.length(); i++) {
                    CoborrowerJobDurationFinancePOJO coborrowerJobDurationFinancePOJO = new CoborrowerJobDurationFinancePOJO();
                    JSONObject mJsonti = jsonArray1.getJSONObject(i);
                    coborrowerJobDurationFinancePOJO.durationName = mJsonti.getString("name");
                    jobduration_arratList.add(mJsonti.getString("name"));
                    coborrowerJobDurationFinancePOJO.durationID = mJsonti.getString("id");
                    coborrowerJobDurationFinancePOJOArrayList.add(coborrowerJobDurationFinancePOJO);
                    Log.e(MainApplication.TAG, "borrowerEducationDegreePOJO Spiner DATA:----------------- " + coborrowerJobDurationFinancePOJO.durationName);
                }
                jobduration_arrayAdapter = new ArrayAdapter(context, R.layout.custom_layout_spinner, jobduration_arratList);
                spinnerJobDuration.setAdapter(jobduration_arrayAdapter);
                jobduration_arrayAdapter.notifyDataSetChanged();

                JSONArray jsonArray2 = jsonObject.getJSONArray("coBorrowerProfession");
                professionfinance_arratList = new ArrayList<>();
                coborrowerProfessionFinancePOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray2.length(); i++) {
                    CoborrowerProfessionFinancePOJO coborrowerProfessionFinancePOJO = new CoborrowerProfessionFinancePOJO();
                    JSONObject mJsonti = jsonArray2.getJSONObject(i);
                    coborrowerProfessionFinancePOJO.professionName = mJsonti.getString("name");
                    professionfinance_arratList.add(mJsonti.getString("name"));
                    coborrowerProfessionFinancePOJO.professionID = mJsonti.getString("id");
                    coborrowerProfessionFinancePOJOArrayList.add(coborrowerProfessionFinancePOJO);
                    Log.e(MainApplication.TAG, "coborrowerProfessionFinancePOJO Spiner DATA:----------------- " + coborrowerProfessionFinancePOJO.professionName);
                }
                profession_arrayAdapter = new ArrayAdapter(context, R.layout.custom_layout_spinner, professionfinance_arratList);
                spinnerProfession.setAdapter(profession_arrayAdapter);
                profession_arrayAdapter.notifyDataSetChanged();

                JSONObject jsonObject1 = jsonObject.getJSONObject("coBorrowerFinancialDetails");
                String organization = jsonObject1.getString("coborrower_organization");
                String anualIncome  = jsonObject1.getString("coborrower_income");
                professionID = jsonObject1.getString("coborrower_profession");
                jobDurationID = jsonObject1.getString("coborrower_working_organization_since");

                anuualincome.setText(anualIncome);
                employeer.setText(organization);
                spinnerJobDuration.setSelection(Integer.parseInt(jobDurationID));
                spinnerProfession.setSelection(Integer.parseInt(professionID));



            }else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setcoborrowerFinance(JSONObject jsonData) {
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
