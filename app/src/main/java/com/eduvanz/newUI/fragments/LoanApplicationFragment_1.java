package com.eduvanz.newUI.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;
import com.eduvanz.fqform.borrowerdetail.pojo.BorrowerCurrentCityPersonalPOJO;
import com.eduvanz.fqform.borrowerdetail.pojo.BorrowerCurrentCountryPersonalPOJO;
import com.eduvanz.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceDurationPersonalPOJO;
import com.eduvanz.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceTypePersonalPOJO;
import com.eduvanz.fqform.borrowerdetail.pojo.BorrowerCurrentStatePersonalPOJO;
import com.eduvanz.fqform.borrowerdetail.pojo.BorrowerEducationDegreePOJO;
import com.eduvanz.fqform.borrowerdetail.pojo.BorrowerProfessionFinancePOJO;
import com.eduvanz.newUI.VolleyCallNew;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.eduvanz.newUI.MainApplication.TAG;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner
 */


public class LoanApplicationFragment_1 extends Fragment{

    public static Context context;
    public static Fragment mFragment;
    public static TextView birthdaycalender, lable, textViewbirthday;
    public static EditText fname, lname, adhaarno, panno, monthlyrent, currentaddress, currentpincode, permanentaddress, permanentpincode;
    public static RadioButton radioButtonMarried, radioButtonSingle;
    public static Spinner spinnerPermanentCity, spinnerPermanentCountry, spinnerPermanentState;
    public static ArrayList<String> permanentstate_arrayList, permanentcity_arrayList;
    public static ArrayAdapter arrayAdapter_permanentCity, arrayAdapter_permanentState, arrayAdapter_permanentCountry;
    public static ArrayList<BorrowerCurrentCityPersonalPOJO> borrowerPermanentCityPersonalPOJOArrayList;
    public static Spinner spinnerCurrentResidenceType;
    public static ArrayAdapter arrayAdapter_currentResidencetype;
    public static ArrayList<String> currentResidencetype_arrayList;
    public static ArrayList<BorrowerCurrentResidenceTypePersonalPOJO> borrowerCurrentResidencePersonalPOJOArrayList;
    public static Spinner spinnerCurrentCity;
    public static ArrayAdapter arrayAdapter_currentCity;
    public static ArrayList<String> currentcity_arrayList;
    public static ArrayList<BorrowerCurrentCityPersonalPOJO> borrowerCurrentCityPersonalPOJOArrayList;
    public static Spinner spinnerCurrentState;
    public static ArrayAdapter arrayAdapter_currentState;
    public static ArrayList<String> currentstate_arrayList;
    public static ArrayList<BorrowerCurrentStatePersonalPOJO> borrowerCurrentStatePersonalPOJOArrayList, borrowerPermanentStatePersonalPOJOArrayList;
    public static Spinner spinnerCurrentCountry;
    public static ArrayAdapter arrayAdapter_currentCountry;
    public static ArrayList<String> currentCountry_arrayList;
    public static ArrayList<BorrowerCurrentCountryPersonalPOJO> borrowerCurrentCountryPersonalPOJOArrayList;
    public static Spinner spinnerCurrentResidenceDuration;
    public static ArrayAdapter arrayAdapter_currentResidenceDuration;
    public static ArrayList<String> currentresidenceduration_arrayList;
    public static ArrayList<BorrowerCurrentResidenceDurationPersonalPOJO> currentResidenceDurationPersonalPOJOArrayList;
    public static RadioButton radioButtonisCgpaYes, radioButtonisCgpaNo;
    public static RadioGroup radioGroup;
    public static EditText editTextIsCgpaYes, editTextIsCgpaNo, editTextPassingYear;
    public static Spinner spinnerLastCompletedDegree;
    public static ArrayAdapter arrayAdapter_lastcompleteddegree;
    public static ArrayList<String> lastCompletedDegree_arratList;
    public static ArrayList<BorrowerEducationDegreePOJO> borrowerEducationDegreePOJOArrayList;
    public static RadioButton radioButtonisEmployed, radioButtonisStudent;
    public static EditText editTextAdvancePayment, editTextAnualIncome, editTextNameofCompany;
    public static Spinner spinnerJobDuration;
    public static ArrayAdapter arrayAdapter;
    public static ArrayList<String> professionfinance_arratList;
    public static ArrayList<BorrowerProfessionFinancePOJO> borrowerProfessionFinancePOJOArrayList;
    public static RadioGroup radioGroup_profession;
    public static FragmentTransaction transaction;
    public static ProgressBar progressBar;
    static View view;
    public String dateformate = "";
    public String permanentcityID = "", permanentstateID = "", permanentCountryID;
    public String currentResidencetypeID = "";
    public String currentcityID = "";
    public String currentstateID = "";
    public String currentcountryID = "";
    public String currentresidenceDurationID = "";
    public String degreeID = "";
    public String jobDurationID = "";
    Button buttonNext;
    TextView textView1, textView2, textView3;
    Calendar cal;
    MainApplication mainApplication;
    Typeface typeface;
    String userID = "";
    LinearLayout linearLayoutEmployed;


    public LoanApplicationFragment_1() {
        // Required empty public constructor
    }

    public static String dateFormateSystem(String date) {
        String dateformate2 = null;
        try {
            String birthDate = date;
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            Date dateformate = fmt.parse(birthDate);
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yyyy");
            dateformate2 = fmtOut.format(dateformate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateformate2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loanapplication_1, container, false);
        context = getContext();
        mainApplication = new MainApplication();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mFragment = new LoanApplicationFragment_1();
        transaction = getFragmentManager().beginTransaction();
        MainApplication.currrentFrag = 1;
        typeface = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("logged_id", "null");

        setViews();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!fname.getText().toString().equals("")) {
                    if (!lname.getText().toString().equals("")) {
                        if (!textViewbirthday.getText().toString().equals("")) {
                            if (!adhaarno.getText().toString().equals("")) {
                                /**API CALL**/
                                try {
                                    String maritialstatus = "";
                                    if (radioButtonMarried.isChecked()) {
                                        maritialstatus = "1";
                                    }
                                    if (radioButtonSingle.isChecked()) {
                                        maritialstatus = "2";
                                    }
                                    progressBar.setVisibility(View.VISIBLE);
                                    String url = MainApplication.mainUrl + "algo/setBorrowerLoanDetails";
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("logged_id", userID);
                                    params.put("student_address_type", currentResidencetypeID);
                                    params.put("student_monthly_rent", monthlyrent.getText().toString());
                                    params.put("student_current_address", currentaddress.getText().toString());
                                    params.put("student_current_city", currentcityID);
                                    params.put("student_current_state", currentstateID);
                                    params.put("student_current_country", currentcountryID);
                                    params.put("student_current_pincode", currentpincode.getText().toString());
                                    params.put("student_permanent_address", permanentaddress.getText().toString());
                                    params.put("student_permanent_city", permanentcityID);
                                    params.put("student_permanent_state", permanentstateID);
                                    params.put("student_permanent_country", permanentCountryID);
                                    params.put("student_permanent_pincode", permanentpincode.getText().toString());
                                    params.put("student_first_name", fname.getText().toString());
                                    params.put("student_last_name", lname.getText().toString());
                                    params.put("student_dob", textViewbirthday.getText().toString());
                                    params.put("student_married", maritialstatus);
                                    params.put("student_pan_card_no", panno.getText().toString());
                                    params.put("student_aadhar_card_no", adhaarno.getText().toString());
                                    params.put("student_current_address_duration", currentresidenceDurationID);

//                                    Log.e(TAG, "------------------ "+currentcountryID +"   ,  "+ currentstateID + "   ,  "+ currentcityID+ "  ,    "+permanentCountryID +"   ,    "+ permanentstateID + "  ,     "+ permanentcityID  );

                                    /** EDUCATION**/
                                    String isCgpa = "";
                                    if (radioButtonisCgpaYes.isChecked()) {
                                        isCgpa = "1";
                                    }
                                    if (radioButtonisCgpaNo.isChecked()) {
                                        isCgpa = "2";
                                    }

                                    params.put("last_degree_completed", degreeID);
                                    params.put("is_cgpa", isCgpa);
                                    params.put("last_degree_percentage", editTextIsCgpaNo.getText().toString());
                                    params.put("last_degree_cgpa", editTextIsCgpaYes.getText().toString());
                                    params.put("last_degree_year_completion", editTextPassingYear.getText().toString());

                                    /** PROFESSION AND FINANCIAL **/
                                    String isWorking = "";
                                    if (radioButtonisEmployed.isChecked()) {
                                        isWorking = "1";
                                    }
                                    if (radioButtonisStudent.isChecked()) {
                                        isWorking = "2";
                                    }

                                    params.put("is_student_working", isWorking);
                                    params.put("student_working_organization", editTextNameofCompany.getText().toString());
                                    params.put("working_organization_since", jobDurationID);
                                    params.put("student_income", editTextAnualIncome.getText().toString());
                                    params.put("advance_payment", editTextAdvancePayment.getText().toString());

                                    MainApplication.borrowerValue1 = currentResidencetypeID;
                                    MainApplication.borrowerValue2 = monthlyrent.getText().toString();
                                    MainApplication.borrowerValue3 = currentaddress.getText().toString();
                                    MainApplication.borrowerValue4 = currentcityID;
                                    MainApplication.borrowerValue5 = currentstateID;
                                    MainApplication.borrowerValue6 = currentcountryID;
                                    MainApplication.borrowerValue7 = currentpincode.getText().toString();
                                    MainApplication.borrowerValue8 = permanentaddress.getText().toString();
                                    MainApplication.borrowerValue9 = permanentcityID;
                                    MainApplication.borrowerValue10 = permanentstateID;
                                    MainApplication.borrowerValue11 = permanentCountryID;
                                    MainApplication.borrowerValue12 = permanentpincode.getText().toString();
                                    MainApplication.borrowerValue13 = fname.getText().toString();
                                    MainApplication.borrowerValue14 = lname.getText().toString();
                                    MainApplication.borrowerValue15 = textViewbirthday.getText().toString();
                                    MainApplication.borrowerValue16 = maritialstatus;
                                    MainApplication.borrowerValue17 = panno.getText().toString();
                                    MainApplication.borrowerValue18 = adhaarno.getText().toString();
                                    MainApplication.borrowerValue19 = degreeID;
                                    MainApplication.borrowerValue20 = isCgpa;
                                    MainApplication.borrowerValue21 = editTextIsCgpaNo.getText().toString();
                                    MainApplication.borrowerValue22 = editTextIsCgpaYes.getText().toString();
                                    MainApplication.borrowerValue23 = editTextPassingYear.getText().toString();
                                    MainApplication.borrowerValue24 = isWorking;
                                    MainApplication.borrowerValue25 = editTextNameofCompany.getText().toString();
                                    MainApplication.borrowerValue26 = jobDurationID;
                                    MainApplication.borrowerValue27 = editTextAnualIncome.getText().toString();
                                    MainApplication.borrowerValue28 = editTextAdvancePayment.getText().toString();
                                    MainApplication.borrowerValue29 = currentresidenceDurationID;


                                    VolleyCallNew volleyCall = new VolleyCallNew();
                                    volleyCall.sendRequest(context, url, null, mFragment, "sendborrowerDetails", params);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                adhaarno.setError("Aadhaar Number is Required");
                            }
                        } else {
                            textViewbirthday.setError("Birthdate is Required");
                        }
                    } else {
                        lname.setError("Last Name is Required");
                    }
                } else {
                    fname.setError("First Name is Required");
                }

            }
        });

        lable = (TextView) view.findViewById(R.id.lable_personal_fqform);
        textViewbirthday = (TextView) view.findViewById(R.id.userInfoEdit_birthdateborrower);
        birthdaycalender = (TextView) view.findViewById(R.id.birthdayCalender_fqform);
        birthdaycalender.setTypeface(typeface);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int month = monthOfYear + 1;
                String datenew = dayOfMonth + "/" + month + "/" + year;
                dateformate = dateFormateSystem(datenew);
                textViewbirthday.setText(dateformate);
                MainApplication.borrowerValue15 = dateformate;
                textViewbirthday.setTextColor(getResources().getColor(R.color.black));
                lable.setVisibility(View.VISIBLE);
            }

        };
        cal = Calendar.getInstance();
        birthdaycalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
                DatePickerDialog data = new DatePickerDialog(context, date, cal
                        .get(Calendar.YEAR) - 18, 1,
                        1);
                data.getDatePicker().setMaxDate(System.currentTimeMillis() - 1234564);
                data.show();
            }
        });

        radioButtonMarried.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radioButtonMarried.isChecked()) {
                    MainApplication.borrowerValue16 = "1";
                }
            }
        });

        radioButtonSingle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (radioButtonSingle.isChecked()) {
                    MainApplication.borrowerValue16 = "2";
                }
            }
        });

        radioButtonisCgpaYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radioButtonisCgpaYes.isChecked()) {
                    MainApplication.borrowerValue20 = "1";
                }
            }
        });

        radioButtonisCgpaNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radioButtonisCgpaNo.isChecked()) {
                    MainApplication.borrowerValue20 = "2";
                }
            }
        });

        radioButtonisEmployed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radioButtonisEmployed.isChecked()) {
                    MainApplication.borrowerValue24 = "1";
                }
            }
        });

        radioButtonisStudent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if (radioButtonisStudent.isChecked()) {
                     MainApplication.borrowerValue24 = "2";
                }
            }
        });

        /** EDIT TEXT CHANGE LISTENER FOR PARTIAL SAVING **/

        fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue13 = fname.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue14 = lname.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        adhaarno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue18 = adhaarno.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        panno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue17 = panno.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        monthlyrent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue2 = monthlyrent.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        currentaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue3 = currentaddress.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        currentpincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue7 = currentpincode.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        permanentaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue8 = permanentaddress.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        permanentpincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue12 = permanentpincode.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextIsCgpaYes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue22 = editTextIsCgpaYes.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextIsCgpaNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue21 = editTextIsCgpaNo.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextPassingYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue23 = editTextPassingYear.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextAdvancePayment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue28 = editTextAdvancePayment.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextAnualIncome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               MainApplication.borrowerValue27 = editTextAnualIncome.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextNameofCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.borrowerValue25 = editTextNameofCompany.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /** END OF EDIT TEXT CHANGE LISTNER FOR PARTIAL SAVING**/

        /** SPINNER CLICK **/

        spinnerCurrentResidenceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerCurrentResidenceType.getSelectedItem().toString();
                Log.e(TAG, "onItemSelected:" + text);
                if (text.equalsIgnoreCase("Renting with Family/Friends")) {
                    monthlyrent.setVisibility(View.VISIBLE);
                } else {
                    monthlyrent.setVisibility(View.GONE);
                }
                int count = borrowerCurrentResidencePersonalPOJOArrayList.size();
                Log.e("TAG", "count: " + count);
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).residencetypeName.equalsIgnoreCase(text)) {
                        MainApplication.borrowerValue1 = currentResidencetypeID = borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCurrentCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerCurrentCity.getSelectedItem().toString();
                int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                        MainApplication.borrowerValue4 = currentcityID = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCurrentState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerCurrentState.getSelectedItem().toString();
                int count = borrowerCurrentStatePersonalPOJOArrayList.size();
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                        MainApplication.borrowerValue5 = currentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
                    }
                }
                cityApiCall();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCurrentCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerCurrentCountry.getSelectedItem().toString();
                int count = borrowerCurrentCountryPersonalPOJOArrayList.size();
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                        MainApplication.borrowerValue6 = currentcountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                    }
                }
                stateApiCall();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPermanentCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerPermanentCity.getSelectedItem().toString();
                int count = borrowerPermanentCityPersonalPOJOArrayList.size();
                for (int i = 0; i < count; i++) {
                    if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                        MainApplication.borrowerValue9 = permanentcityID = borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPermanentState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerPermanentState.getSelectedItem().toString();
                int count = borrowerPermanentStatePersonalPOJOArrayList.size();
                for (int i = 0; i < count; i++) {
                    if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                        MainApplication.borrowerValue10 = permanentstateID = borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID;
                    }
                }
                permanentCityApiCall();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPermanentCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerPermanentCountry.getSelectedItem().toString();
                int count = borrowerCurrentCountryPersonalPOJOArrayList.size();
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                        MainApplication.borrowerValue11 = permanentCountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                    }
                }
                permanentStateApiCall();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCurrentResidenceDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerCurrentResidenceDuration.getSelectedItem().toString();
                int count = currentResidenceDurationPersonalPOJOArrayList.size();
                for (int i = 0; i < count; i++) {
                    if (currentResidenceDurationPersonalPOJOArrayList.get(i).durationName.equalsIgnoreCase(text)) {
                        currentresidenceDurationID = currentResidenceDurationPersonalPOJOArrayList.get(i).durationID;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /** EDUCATION **/
        spinnerLastCompletedDegree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("I_________D", "onItemClick: ");
                String text = spinnerLastCompletedDegree.getSelectedItem().toString();
                int count = borrowerEducationDegreePOJOArrayList.size();
                Log.e("TAG", "count: " + count);
                for (int i = 0; i < count; i++) {
                    if (borrowerEducationDegreePOJOArrayList.get(i).degreeName.equalsIgnoreCase(text)) {
                        MainApplication.borrowerValue19 = degreeID = borrowerEducationDegreePOJOArrayList.get(i).degreeID;
                        Log.e("I_________D", "onItemClick: " + degreeID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**PROFESSION AND FINANCIAL **/
        spinnerJobDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("I_________D", "onItemClick: ");
                String text = spinnerJobDuration.getSelectedItem().toString();
                int count = borrowerProfessionFinancePOJOArrayList.size();
                Log.e("TAG", "count: " + count);
                for (int i = 0; i < count; i++) {
                    if (borrowerProfessionFinancePOJOArrayList.get(i).durationName.equalsIgnoreCase(text)) {
                        MainApplication.borrowerValue26 = jobDurationID = borrowerProfessionFinancePOJOArrayList.get(i).durationID;
                        Log.e("I_________D", "onItemClick: " + jobDurationID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /** END SPINNER CLICK **/


        /** EDUCATION **/
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("TAG", "onCheckedChanged: ");
                Log.e("TAG", "onCheckedChanged: " + radioGroup.getCheckedRadioButtonId());
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radiobutton_iscgpa_yes:
                        Log.e("TAG", "yes: ");
                        editTextIsCgpaYes.setVisibility(View.VISIBLE);
                        editTextIsCgpaNo.setVisibility(View.GONE);
                        break;
                    case R.id.radiobutton_iscgpa_no:
                        Log.e("TAG", "no: ");
                        editTextIsCgpaYes.setVisibility(View.GONE);
                        editTextIsCgpaNo.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });


        /** PROFESSION AND FINANCIAL **/
        radioGroup_profession.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("TAG", "onCheckedChanged: ");
                Log.e("TAG", "onCheckedChanged: " + radioGroup.getCheckedRadioButtonId());
                switch (radioGroup_profession.getCheckedRadioButtonId()) {
                    case R.id.radiobutton_isemployed:
                        Log.e("TAG", "employed: ");
                        linearLayoutEmployed.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radiobutton_isstudent:
                        Log.e("TAG", "student: ");
                        linearLayoutEmployed.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        /**API CALL**/
        try {
            progressBar.setVisibility(View.VISIBLE);
            String url = MainApplication.mainUrl + "algo/getBorrowerLoanDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("logged_id", userID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "borrowerLoanDetails", params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void cityApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getCities";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", currentcountryID);
            params.put("stateId", currentstateID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "getCurrentCity", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stateApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getStates";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", currentcountryID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "getCurrentStates", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void permanentCityApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getCities";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", permanentCountryID);
            params.put("stateId", permanentstateID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "getPermanentCity", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void permanentStateApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getStates";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", permanentCountryID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "getPermanentStates", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setViews() {

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_applylona_borrower);

        textView1 = (TextView) view.findViewById(R.id.textView_l1);
        mainApplication.applyTypefaceBold(textView1, context);
        textView2 = (TextView) view.findViewById(R.id.textView_l2);
        mainApplication.applyTypeface(textView2, context);
        textView3 = (TextView) view.findViewById(R.id.textView_l3);
        mainApplication.applyTypeface(textView3, context);

        buttonNext = (Button) view.findViewById(R.id.button_next_borrower_loanapplication);
        mainApplication.applyTypeface(buttonNext, context);

        fname = (EditText) view.findViewById(R.id.input_borrowerfirstname);
        lname = (EditText) view.findViewById(R.id.input_borrowerlastname);
        adhaarno = (EditText) view.findViewById(R.id.input_borroweradharnumber);
        panno = (EditText) view.findViewById(R.id.input_borrowerpannumber);
        monthlyrent = (EditText) view.findViewById(R.id.input_borrowerrent);
        currentaddress = (EditText) view.findViewById(R.id.input_borrowercurrentaddress);
        permanentaddress = (EditText) view.findViewById(R.id.input_borrowerpermanentaddress);
        currentpincode = (EditText) view.findViewById(R.id.input_borrowercurrentpincode);
        permanentpincode = (EditText) view.findViewById(R.id.input_borrowerpermanentpincode);

        radioButtonMarried = (RadioButton) view.findViewById(R.id.radioButton_married_borrower);
        radioButtonSingle = (RadioButton) view.findViewById(R.id.radioButton_single_borrower);

        spinnerCurrentResidenceType = (Spinner) view.findViewById(R.id.spinner_currentresidencytype_borrower);
        spinnerCurrentCity = (Spinner) view.findViewById(R.id.spinner_currentcity);
        spinnerCurrentCountry = (Spinner) view.findViewById(R.id.spinner_currentcountry);
        spinnerCurrentState = (Spinner) view.findViewById(R.id.spinner_currentstate);
        spinnerPermanentCity = (Spinner) view.findViewById(R.id.spinner_borrowerpermanentcity);
        spinnerPermanentState = (Spinner) view.findViewById(R.id.spinner_borrowerpermanentstate);
        spinnerPermanentCountry = (Spinner) view.findViewById(R.id.spinner_borrowerpermanentcountry);
        spinnerCurrentResidenceDuration = (Spinner) view.findViewById(R.id.spinner_currentresidenceduration);

        /** EDUCATION **/
        spinnerLastCompletedDegree = (Spinner) view.findViewById(R.id.spinner_lastdegreecompleted);
        radioButtonisCgpaYes = (RadioButton) view.findViewById(R.id.radiobutton_iscgpa_yes);
        radioButtonisCgpaNo = (RadioButton) view.findViewById(R.id.radiobutton_iscgpa_no);
        editTextIsCgpaYes = (EditText) view.findViewById(R.id.input_degree_cgpa);
        editTextIsCgpaNo = (EditText) view.findViewById(R.id.input_degree_percentage);
        editTextPassingYear = (EditText) view.findViewById(R.id.input_passingyear);
        radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup_iscgpa);

        /** PROFESSION AND FINANCIAL **/
        radioButtonisStudent = (RadioButton) view.findViewById(R.id.radiobutton_isstudent);
        radioButtonisEmployed = (RadioButton) view.findViewById(R.id.radiobutton_isemployed);
        linearLayoutEmployed = (LinearLayout) view.findViewById(R.id.linearlayout_isempoyeed);
        spinnerJobDuration = (Spinner) view.findViewById(R.id.spinner_jobduration);
        editTextAdvancePayment = (EditText) view.findViewById(R.id.input_advancepayment);
        editTextAnualIncome = (EditText) view.findViewById(R.id.input_annualincome);
        editTextNameofCompany = (EditText) view.findViewById(R.id.input_nameofcompany);
        radioGroup_profession = (RadioGroup) view.findViewById(R.id.radiogroup_profession);
    }

    /**
     * RESPONSE OF API CALL
     **/
    public void borrowerLoanDetails(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "borrowerPersonal" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONArray jsonArray1 = jsonObject.getJSONArray("addressType");
                currentResidencetype_arrayList = new ArrayList<>();
                borrowerCurrentResidencePersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray1.length(); i++) {
                    BorrowerCurrentResidenceTypePersonalPOJO currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
                    JSONObject mJsonti = jsonArray1.getJSONObject(i);
                    currentResidenceTypePersonalPOJO.residencetypeName = mJsonti.getString("name");
                    currentResidencetype_arrayList.add(mJsonti.getString("name"));
                    currentResidenceTypePersonalPOJO.dresidencetypeID = mJsonti.getString("id");
                    borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);
                }
                arrayAdapter_currentResidencetype = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentResidencetype_arrayList);
                spinnerCurrentResidenceType.setAdapter(arrayAdapter_currentResidencetype);
                arrayAdapter_currentResidencetype.notifyDataSetChanged();

                JSONArray jsonArray5 = jsonObject.getJSONArray("currentResidence");
                currentresidenceduration_arrayList = new ArrayList<>();
                currentResidenceDurationPersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray5.length(); i++) {
                    BorrowerCurrentResidenceDurationPersonalPOJO borrowerCurrentResidenceDurationPersonalPOJO = new BorrowerCurrentResidenceDurationPersonalPOJO();
                    JSONObject mJsonti = jsonArray5.getJSONObject(i);
                    borrowerCurrentResidenceDurationPersonalPOJO.durationName = mJsonti.getString("name");
                    currentresidenceduration_arrayList.add(mJsonti.getString("name"));
                    borrowerCurrentResidenceDurationPersonalPOJO.durationID = mJsonti.getString("id");
                    currentResidenceDurationPersonalPOJOArrayList.add(borrowerCurrentResidenceDurationPersonalPOJO);
                }
                arrayAdapter_currentResidenceDuration = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentresidenceduration_arrayList);
                spinnerCurrentResidenceDuration.setAdapter(arrayAdapter_currentResidenceDuration);
                arrayAdapter_currentResidenceDuration.notifyDataSetChanged();

                JSONArray jsonArray4 = jsonObject.getJSONArray("country");
                currentCountry_arrayList = new ArrayList<>();
                borrowerCurrentCountryPersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray4.length(); i++) {
                    BorrowerCurrentCountryPersonalPOJO borrowerCurrentCountryPersonalPOJO = new BorrowerCurrentCountryPersonalPOJO();
                    JSONObject mJsonti = jsonArray4.getJSONObject(i);
                    borrowerCurrentCountryPersonalPOJO.countryName = mJsonti.getString("country_name");
                    currentCountry_arrayList.add(mJsonti.getString("country_name"));
                    borrowerCurrentCountryPersonalPOJO.countryID = mJsonti.getString("country_id");
                    borrowerCurrentCountryPersonalPOJOArrayList.add(borrowerCurrentCountryPersonalPOJO);
                }
                arrayAdapter_currentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
                spinnerCurrentCountry.setAdapter(arrayAdapter_currentCountry);
                arrayAdapter_currentCountry.notifyDataSetChanged();
                arrayAdapter_permanentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
                spinnerPermanentCountry.setAdapter(arrayAdapter_permanentCountry);
                arrayAdapter_permanentCountry.notifyDataSetChanged();

//                JSONArray jsonArray2 = jsonObject.getJSONArray("currentCities");
//                currentcity_arrayList = new ArrayList<>();
//                borrowerCurrentCityPersonalPOJOArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray2.length(); i++) {
//                    BorrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new BorrowerCurrentCityPersonalPOJO();
//                    JSONObject mJsonti = jsonArray2.getJSONObject(i);
//                    borrowerCurrentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
//                    currentcity_arrayList.add(mJsonti.getString("city_name"));
//                    borrowerCurrentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
//                    borrowerCurrentCityPersonalPOJOArrayList.add(borrowerCurrentCityPersonalPOJO);
//                }
//                arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
//                spinnerCurrentCity.setAdapter(arrayAdapter_currentCity);
//                arrayAdapter_currentCity.notifyDataSetChanged();
//
//
//                JSONArray jsonArray9 = jsonObject.getJSONArray("permanentCities");
//                permanentcity_arrayList = new ArrayList<>();
//                borrowerPermanentCityPersonalPOJOArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray9.length(); i++) {
//                    BorrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new BorrowerCurrentCityPersonalPOJO();
//                    JSONObject mJsonti = jsonArray9.getJSONObject(i);
//                    borrowerCurrentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
//                    permanentcity_arrayList.add(mJsonti.getString("city_name"));
//                    borrowerCurrentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
//                    borrowerPermanentCityPersonalPOJOArrayList.add(borrowerCurrentCityPersonalPOJO);
//                }
//                arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayList);
//                spinnerPermanentCity.setAdapter(arrayAdapter_permanentCity);
//                arrayAdapter_permanentCity.notifyDataSetChanged();
//
//                JSONArray jsonArray3 = jsonObject.getJSONArray("currentStates");
//                currentstate_arrayList = new ArrayList<>();
//                borrowerCurrentStatePersonalPOJOArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray3.length(); i++) {
//                    BorrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new BorrowerCurrentStatePersonalPOJO();
//                    JSONObject mJsonti = jsonArray3.getJSONObject(i);
//                    borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
//                    currentstate_arrayList.add(mJsonti.getString("state_name"));
//                    borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
//                    borrowerCurrentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
//                }
//                arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
//                spinnerCurrentState.setAdapter(arrayAdapter_currentState);
//                arrayAdapter_currentState.notifyDataSetChanged();
//
//                JSONArray jsonArray10 = jsonObject.getJSONArray("permanentStates");
//                permanentstate_arrayList = new ArrayList<>();
//                borrowerPermanentStatePersonalPOJOArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray10.length(); i++) {
//                    BorrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new BorrowerCurrentStatePersonalPOJO();
//                    JSONObject mJsonti = jsonArray10.getJSONObject(i);
//                    borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
//                    permanentstate_arrayList.add(mJsonti.getString("state_name"));
//                    borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
//                    borrowerPermanentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
//                }
//                arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentstate_arrayList);
//                spinnerPermanentState.setAdapter(arrayAdapter_permanentState);
//                arrayAdapter_permanentState.notifyDataSetChanged();

                JSONObject jsonObject1 = jsonObject.getJSONObject("personalDetails");
                String monthlyRent = jsonObject1.getString("student_monthly_rent");
                String currentadd = jsonObject1.getString("student_current_address");
                String currentPincode = jsonObject1.getString("student_current_pincode");
                String permanentadd = jsonObject1.getString("student_permanent_address");
                String permanentpin = jsonObject1.getString("student_permanent_pincode");
                String firstname = jsonObject1.getString("student_first_name");
                String lastname = jsonObject1.getString("student_last_name");
                String dob = jsonObject1.getString("student_dob");
                String panNo = jsonObject1.getString("student_pan_card_no");
                String aadhaarno = jsonObject1.getString("student_aadhar_card_no");
                String maritialstatus = jsonObject1.getString("student_married");

                currentResidencetypeID = jsonObject1.getString("student_address_type");
                currentcityID = jsonObject1.getString("student_current_city");
                currentstateID = jsonObject1.getString("student_current_state");
                currentcountryID = jsonObject1.getString("student_current_country");
                permanentcityID = jsonObject1.getString("student_permanent_city");
                permanentstateID = jsonObject1.getString("student_permanent_state");
                permanentCountryID = jsonObject1.getString("student_permanent_country");
                currentresidenceDurationID = jsonObject1.getString("student_current_address_duration");

//                Log.e(TAG, "++++++++++ "+currentcountryID +"  ,"+ currentstateID + "  ,"+ currentcityID+ "  ,"+permanentCountryID +"  ,"+ permanentstateID + " , "+ permanentcityID  );


                if (currentResidencetypeID.equals("")) {
                    monthlyrent.setVisibility(View.GONE);
                    spinnerCurrentResidenceType.setSelection(0);
                } else if (currentResidencetypeID.equals("2")) {
                    monthlyrent.setVisibility(View.VISIBLE);
                    spinnerCurrentResidenceType.setSelection(Integer.parseInt(currentResidencetypeID));
                } else {
                    monthlyrent.setVisibility(View.GONE);
                    spinnerCurrentResidenceType.setSelection(Integer.parseInt(currentResidencetypeID));
                }
//                if (currentcityID.equals("")) {
//                    spinnerCurrentCity.setSelection(0);
//                } else {
//                    spinnerCurrentCity.setSelection(Integer.parseInt(currentcityID));
//                }
//                if (currentstateID.equals("")) {
//                    spinnerCurrentState.setSelection(0);
//                } else {
//                    Log.e(TAG, "currentstateID+++++: "+ currentstateID);
//                    spinnerCurrentState.setSelection(Integer.parseInt(currentstateID));
//                }
                if (currentcountryID.equals("")) {
                    spinnerCurrentCountry.setSelection(0);
                } else {
                    spinnerCurrentCountry.setSelection(Integer.parseInt(currentcountryID));
                }
//                if (permanentcityID.equals("")) {
//                    spinnerPermanentCity.setSelection(0);
//                } else {
//                    spinnerPermanentCity.setSelection(Integer.parseInt(permanentcityID));
//                }
//                if (permanentstateID.equals("")) {
//                    spinnerPermanentState.setSelection(0);
//                } else {
//                    spinnerPermanentState.setSelection(Integer.parseInt(permanentstateID));
//                }
                if (permanentCountryID.equals("")) {
                    spinnerPermanentCountry.setSelection(0);
                } else {
                    spinnerPermanentCountry.setSelection(Integer.parseInt(permanentCountryID));
                }
                if (currentresidenceDurationID.equals("")) {
                    Log.e(TAG, "888888888: "+ currentresidenceDurationID);
                    spinnerCurrentResidenceDuration.setSelection(0);
                } else {
                    Log.e(TAG, "888888888: "+ currentresidenceDurationID);
                    int count = currentResidenceDurationPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (currentResidenceDurationPersonalPOJOArrayList.get(i).durationID.equalsIgnoreCase(currentresidenceDurationID)) {
                            spinnerCurrentResidenceDuration.setSelection(i);
                        }
                    }
                }

                fname.setText(firstname);
                lname.setText(lastname);
                adhaarno.setText(aadhaarno);
                panno.setText(panNo);
                monthlyrent.setText(monthlyRent);
                currentaddress.setText(currentadd);
                currentpincode.setText(currentPincode);
                permanentpincode.setText(permanentpin);
                permanentaddress.setText(permanentadd);
                if (!dob.equalsIgnoreCase("") || !dob.isEmpty()) {
                    textViewbirthday.setText(dob);
                    textViewbirthday.setTextColor(Color.BLACK);
                    lable.setVisibility(View.VISIBLE);
                } else {
                    textViewbirthday.setText("Birthdate");
                    textViewbirthday.setTextColor(808080);
                }

                if (maritialstatus.equalsIgnoreCase("1")) {
                    radioButtonMarried.setChecked(true);
                } else if (maritialstatus.equalsIgnoreCase("2")) {
                    radioButtonSingle.setChecked(true);
                }

                /** EDUCATION **/
                JSONArray jsonArray6 = jsonObject.getJSONArray("degrees");
                lastCompletedDegree_arratList = new ArrayList<>();
                borrowerEducationDegreePOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray6.length(); i++) {
                    BorrowerEducationDegreePOJO borrowerEducationDegreePOJO = new BorrowerEducationDegreePOJO();
                    JSONObject mJsonti = jsonArray6.getJSONObject(i);
                    borrowerEducationDegreePOJO.degreeName = mJsonti.getString("name");
                    lastCompletedDegree_arratList.add(mJsonti.getString("name"));
                    borrowerEducationDegreePOJO.degreeID = mJsonti.getString("id");
                    borrowerEducationDegreePOJOArrayList.add(borrowerEducationDegreePOJO);
                    Log.e(MainApplication.TAG, "borrowerEducationDegreePOJO Spiner DATA:----------------- " + borrowerEducationDegreePOJO.degreeName);
                }
                arrayAdapter_lastcompleteddegree = new ArrayAdapter(context, R.layout.custom_layout_spinner, lastCompletedDegree_arratList);
                spinnerLastCompletedDegree.setAdapter(arrayAdapter_lastcompleteddegree);
                arrayAdapter_lastcompleteddegree.notifyDataSetChanged();

                JSONObject jsonObject6 = jsonObject.getJSONObject("educationalDetails");
                String isCGPA = jsonObject6.getString("is_cgpa");
                String passingYear = jsonObject6.getString("last_degree_year_completion");
                String cgpa = jsonObject6.getString("last_degree_cgpa");
                String percentage = jsonObject6.getString("last_degree_percentage");
                degreeID = jsonObject6.getString("last_degree_completed");

                editTextIsCgpaYes.setText(cgpa);
                editTextIsCgpaNo.setText(percentage);
                editTextPassingYear.setText(passingYear);

                if (degreeID.equals("")) {
                    spinnerLastCompletedDegree.setSelection(0);
                } else {
                    spinnerLastCompletedDegree.setSelection(Integer.parseInt(degreeID));
                }

                if (isCGPA.equalsIgnoreCase("1")) {
                    radioButtonisCgpaYes.setChecked(true);
                } else if (isCGPA.equalsIgnoreCase("2")) {
                    radioButtonisCgpaNo.setChecked(true);
                }


                /**PROFESSION AND FINANCIAL**/
                JSONArray jsonArray7 = jsonObject.getJSONArray("jobDuration");
                professionfinance_arratList = new ArrayList<>();
                borrowerProfessionFinancePOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray7.length(); i++) {
                    BorrowerProfessionFinancePOJO borrowerProfessionFinancePOJO = new BorrowerProfessionFinancePOJO();
                    JSONObject mJsonti = jsonArray7.getJSONObject(i);
                    borrowerProfessionFinancePOJO.durationName = mJsonti.getString("name");
                    professionfinance_arratList.add(mJsonti.getString("name"));
                    borrowerProfessionFinancePOJO.durationID = mJsonti.getString("id");
                    borrowerProfessionFinancePOJOArrayList.add(borrowerProfessionFinancePOJO);
                    Log.e(MainApplication.TAG, "borrowerEducationDegreePOJO Spiner DATA:----------------- " + borrowerProfessionFinancePOJO.durationName);
                }
                arrayAdapter = new ArrayAdapter(context, R.layout.custom_layout_spinner, professionfinance_arratList);
                spinnerJobDuration.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();

                JSONObject jsonObject7 = jsonObject.getJSONObject("financialDetails");
                String isWorking = jsonObject7.getString("is_student_working");
                String advancePayment = jsonObject7.getString("advance_payment");
                String nameofCompany = jsonObject7.getString("student_working_organization");
                jobDurationID = jsonObject7.getString("working_organization_since");
                String anualIncome = jsonObject7.getString("student_income");

                editTextAdvancePayment.setText(advancePayment);
                editTextAnualIncome.setText(anualIncome);
                editTextNameofCompany.setText(nameofCompany);

                if (jobDurationID.equals("")) {
                    spinnerJobDuration.setSelection(0);
                } else {
                    spinnerJobDuration.setSelection(Integer.parseInt(jobDurationID));
                }

                if (isWorking.equalsIgnoreCase("1")) {
                    radioButtonisEmployed.setChecked(true);
                } else if (isWorking.equalsIgnoreCase("2")) {
                    radioButtonisStudent.setChecked(true);
                }

                MainApplication.borrowerValue1 = currentResidencetypeID;
                MainApplication.borrowerValue2 = monthlyrent.getText().toString();
                MainApplication.borrowerValue3 = currentaddress.getText().toString();
                MainApplication.borrowerValue4 = currentcityID;
                MainApplication.borrowerValue5 = currentstateID;
                MainApplication.borrowerValue6 = currentcountryID;
                MainApplication.borrowerValue7 = currentpincode.getText().toString();
                MainApplication.borrowerValue8 = permanentaddress.getText().toString();
                MainApplication.borrowerValue9 = permanentcityID;
                MainApplication.borrowerValue10 = permanentstateID;
                MainApplication.borrowerValue11 = permanentCountryID;
                MainApplication.borrowerValue12 = permanentpincode.getText().toString();
                MainApplication.borrowerValue13 = fname.getText().toString();
                MainApplication.borrowerValue14 = lname.getText().toString();
                MainApplication.borrowerValue15 = textViewbirthday.getText().toString();
                MainApplication.borrowerValue16 = maritialstatus;
                MainApplication.borrowerValue17 = panno.getText().toString();
                MainApplication.borrowerValue18 = adhaarno.getText().toString();
                MainApplication.borrowerValue19 = degreeID;
                MainApplication.borrowerValue20 = isCGPA;
                MainApplication.borrowerValue21 = editTextIsCgpaNo.getText().toString();
                MainApplication.borrowerValue22 = editTextIsCgpaYes.getText().toString();
                MainApplication.borrowerValue23 = editTextPassingYear.getText().toString();
                MainApplication.borrowerValue24 = isWorking;
                MainApplication.borrowerValue25 = editTextNameofCompany.getText().toString();
                MainApplication.borrowerValue26 = jobDurationID;
                MainApplication.borrowerValue27 = editTextAnualIncome.getText().toString();
                MainApplication.borrowerValue28 = editTextAdvancePayment.getText().toString();
                MainApplication.borrowerValue29 = currentresidenceDurationID;

                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendBorrowerDetails(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("first_name", fname.getText().toString());
                editor.putString("last_name", lname.getText().toString());
                editor.apply();
                editor.commit();

                LoanApplicationFragment_2 loanApplicationFragment_2 = new LoanApplicationFragment_2();
                transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_2).commit();

                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }

    public void getCurrentStates(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONArray jsonArray3 = jsonObject.getJSONArray("states");
                currentstate_arrayList = new ArrayList<>();
                borrowerCurrentStatePersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray3.length(); i++) {
                    BorrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new BorrowerCurrentStatePersonalPOJO();
                    JSONObject mJsonti = jsonArray3.getJSONObject(i);
                    borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                    currentstate_arrayList.add(mJsonti.getString("state_name"));
                    borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                    borrowerCurrentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
                }
                arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                spinnerCurrentState.setAdapter(arrayAdapter_currentState);
                arrayAdapter_currentState.notifyDataSetChanged();

                spinnerCurrentState.setSelection(Integer.parseInt(currentstateID));

            } else {
            }
        } catch (Exception e) {

        }
    }

    public void getCurrentCities(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONArray jsonArray2 = jsonObject.getJSONArray("cities");
                currentcity_arrayList = new ArrayList<>();
                borrowerCurrentCityPersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray2.length(); i++) {
                    BorrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new BorrowerCurrentCityPersonalPOJO();
                    JSONObject mJsonti = jsonArray2.getJSONObject(i);
                    borrowerCurrentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
                    currentcity_arrayList.add(mJsonti.getString("city_name"));
                    borrowerCurrentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
                    borrowerCurrentCityPersonalPOJOArrayList.add(borrowerCurrentCityPersonalPOJO);
                }
                arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
                spinnerCurrentCity.setAdapter(arrayAdapter_currentCity);
                arrayAdapter_currentCity.notifyDataSetChanged();

                Log.e(TAG, "6666666666: "+ currentcityID);

                int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                Log.e("TAG", "count: " + count);
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                        spinnerCurrentCity.setSelection(i);
                    }
                }

            } else {
            }
        } catch (Exception e) {

        }
    }

    public void getPermanentStates(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONArray jsonArray3 = jsonObject.getJSONArray("states");
                permanentstate_arrayList = new ArrayList<>();
                borrowerPermanentStatePersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray3.length(); i++) {
                    BorrowerCurrentStatePersonalPOJO borrowerpermanentStatePersonalPOJO = new BorrowerCurrentStatePersonalPOJO();
                    JSONObject mJsonti = jsonArray3.getJSONObject(i);
                    borrowerpermanentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                    permanentstate_arrayList.add(mJsonti.getString("state_name"));
                    borrowerpermanentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                    borrowerPermanentStatePersonalPOJOArrayList.add(borrowerpermanentStatePersonalPOJO);
                }
                arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentstate_arrayList);
                spinnerPermanentState.setAdapter(arrayAdapter_permanentState);
                arrayAdapter_permanentState.notifyDataSetChanged();

                spinnerPermanentState.setSelection(Integer.parseInt(permanentstateID));

                int count = borrowerPermanentStatePersonalPOJOArrayList.size();
                Log.e("TAG", "count: " + count);
                for (int i = 0; i < count; i++) {
                    if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(permanentstateID)) {
                        spinnerPermanentState.setSelection(i);
                    }
                }
            } else {
            }
        } catch (Exception e) {

        }
    }

    public void getPermanentCities(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONArray jsonArray2 = jsonObject.getJSONArray("cities");
                permanentcity_arrayList = new ArrayList<>();
                borrowerPermanentCityPersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray2.length(); i++) {
                    BorrowerCurrentCityPersonalPOJO borrowerpermanentCityPersonalPOJO = new BorrowerCurrentCityPersonalPOJO();
                    JSONObject mJsonti = jsonArray2.getJSONObject(i);
                    borrowerpermanentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
                    permanentcity_arrayList.add(mJsonti.getString("city_name"));
                    borrowerpermanentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
                    borrowerPermanentCityPersonalPOJOArrayList.add(borrowerpermanentCityPersonalPOJO);
                }
                arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayList);
                spinnerPermanentCity.setAdapter(arrayAdapter_permanentCity);
                arrayAdapter_permanentCity.notifyDataSetChanged();


                int count = borrowerPermanentCityPersonalPOJOArrayList.size();
                Log.e("TAG", "count: " + count);
                for (int i = 0; i < count; i++) {
                    if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityID)) {
                        spinnerPermanentCity.setSelection(i);
                    }
                }
            } else {
            }
        } catch (Exception e) {

        }
    }

}
