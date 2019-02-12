package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.pojo.ProfessionPOJO;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */

public class EligibilityCheckFragment_4 extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    public Button buttonNext, buttonPrevious;
    public static LinearLayout linCompanyName, linSalary;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3;
    public static Spinner spProfession;

    public static EditText edtCompanyName, edtAnnualSal;
    public static String mobileNo = "", student_id = "", auth_token = "", lead_id = "", mobile_no = "";
    static ProgressBar progressBar;
    SharedPref shareP;
    static FragmentTransaction transaction;
    public String professionID = "";
    public static ArrayAdapter arrayAdapter_profession;
    public static ArrayList<String> profession_arrayList;
    public static ArrayList<ProfessionPOJO> professionPOJOArrayList;

    public EligibilityCheckFragment_4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eligibilitycheck_4, container, false);
        try {
            context = getContext();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            mFragment = new EligibilityCheckFragment_4();
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar_eligiblityCheck);

            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                mobileNo = sharedPreferences.getString("mobile_no", "null");
                student_id = sharedPreferences.getString("student_id", "");
                auth_token = sharedPreferences.getString("auth_token", "");
                lead_id = sharedPreferences.getString("lead_id", "");
                mobile_no = sharedPreferences.getString("mobile_no", "");

            } catch (Exception e) {
                e.printStackTrace();
            }

            typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
            typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

            transaction = getFragmentManager().beginTransaction();

            edtCompanyName = (EditText) view.findViewById(R.id.edtCompanyName);
            edtAnnualSal = (EditText) view.findViewById(R.id.edtAnnualSal);
            spProfession = (Spinner) view.findViewById(R.id.spProfession);

            linCompanyName = (LinearLayout) view.findViewById(R.id.linCompanyName);
            linSalary = (LinearLayout) view.findViewById(R.id.linSalary);

//            spProfession = (Spinner) view.findViewById(R.id.spProfession);
//            profession_arrayList = new ArrayList<>();
//            profession_arrayList.add("Select Any");
//            profession_arrayList.add("Student");
//            profession_arrayList.add("Employed");
//            profession_arrayList.add("Self Employed");
//            arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
//            spProfession.setAdapter(arrayAdapter_profession);
//            arrayAdapter_profession.notifyDataSetChanged();

            ProfessionApiCall();

//            spProfession = (Spinner) view.findViewById(R.id.spProfession);
//            profession_arrayList = new ArrayList<>();
//            profession_arrayList.add("Profession of Co-borrower");
//            profession_arrayList.add("Employed");
//            profession_arrayList.add("Self Employed");
//            arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
//            spProfession.setAdapter(arrayAdapter_profession);
//            arrayAdapter_profession.notifyDataSetChanged();

            buttonNext = (Button) view.findViewById(R.id.button_submit_eligibility);
            buttonNext.setTypeface(typefaceFontBold);

            buttonPrevious = (Button) view.findViewById(R.id.button_previous_eligiblityfragment2);
            buttonPrevious.setTypeface(typefaceFontBold);

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!MainApplication.mainapp_userprofession.equalsIgnoreCase("0")) {

                        if (MainApplication.mainapp_userprofession.equalsIgnoreCase("1")) {

                            if (edtCompanyName.getText().toString().equalsIgnoreCase("")) {
                                edtCompanyName.setError(getString(R.string.name_of_the_company_is_required));
                                edtCompanyName.requestFocus();
                            } else if (edtAnnualSal.getText().toString().equalsIgnoreCase("")) {
                                edtAnnualSal.setError(getString(R.string.annual_income_is_required));
                                edtAnnualSal.requestFocus();
                            } else {
                                MainApplication.employer_name = edtCompanyName.getText().toString().trim();
                                MainApplication.annual_income = edtAnnualSal.getText().toString().trim();

                                if (MainApplication.isBorrower) {
                                    saveBorrowerData();
                                } else {
                                    saveCoBorrowerData();
                                }

                            }
                        }else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("2")) {

                                if (MainApplication.isBorrower) {
                                    saveBorrowerData();
                                } else {
                                    saveCoBorrowerData();
                                }

                        } else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("3")) {

                            if (edtCompanyName.getText().toString().equalsIgnoreCase("")) {
                                edtCompanyName.setError(getString(R.string.name_of_the_company_is_required));
                                edtCompanyName.requestFocus();
                            } else if (edtAnnualSal.getText().toString().equalsIgnoreCase("")) {
                                edtAnnualSal.setError(getString(R.string.annual_income_is_required));
                                edtAnnualSal.requestFocus();
                            } else {
                                MainApplication.employer_name = edtCompanyName.getText().toString().trim();
                                MainApplication.annual_income = edtAnnualSal.getText().toString().trim();
                                if (MainApplication.isBorrower) {
                                    saveBorrowerData();
                                } else {
                                    saveCoBorrowerData();
                                }
//                                MainApplication.employer_name = edtCompanyName.getText().toString().trim();
//                                MainApplication.annual_income = edtAnnualSal.getText().toString().trim();

//                                MainApplication.coemployer_name = edtCompanyName.getText().toString().trim();
//                                MainApplication.coannual_income = edtAnnualSal.getText().toString().trim();

                            }
                        }
                        else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("4")) {

                            if (edtCompanyName.getText().toString().equalsIgnoreCase("")) {
                                edtCompanyName.setError(getString(R.string.name_of_the_company_is_required));
                                edtCompanyName.requestFocus();
                            } else if (edtAnnualSal.getText().toString().equalsIgnoreCase("")) {
                                edtAnnualSal.setError(getString(R.string.annual_income_is_required));
                                edtAnnualSal.requestFocus();
                            } else {
                                MainApplication.employer_name = edtCompanyName.getText().toString().trim();
                                MainApplication.annual_income = edtAnnualSal.getText().toString().trim();
                                if (MainApplication.isBorrower) {
                                    saveBorrowerData();
                                } else {
                                    saveCoBorrowerData();
                                }
//                                MainApplication.employer_name = edtCompanyName.getText().toString().trim();
//                                MainApplication.annual_income = edtAnnualSal.getText().toString().trim();

//                                MainApplication.coemployer_name = edtCompanyName.getText().toString().trim();
//                                MainApplication.coannual_income = edtAnnualSal.getText().toString().trim();

                            }
                        }else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("5")) {

                            if (edtCompanyName.getText().toString().equalsIgnoreCase("")) {
                                edtCompanyName.setError(getString(R.string.name_of_the_company_is_required));
                                edtCompanyName.requestFocus();
                            } else if (edtAnnualSal.getText().toString().equalsIgnoreCase("")) {
                                edtAnnualSal.setError(getString(R.string.annual_income_is_required));
                                edtAnnualSal.requestFocus();
                            } else {
                                MainApplication.employer_name = edtCompanyName.getText().toString().trim();
                                MainApplication.annual_income = edtAnnualSal.getText().toString().trim();
                                if (MainApplication.isBorrower) {
                                    saveBorrowerData();
                                } else {
                                    saveCoBorrowerData();
                                }
//                                MainApplication.employer_name = edtCompanyName.getText().toString().trim();
//                                MainApplication.annual_income = edtAnnualSal.getText().toString().trim();

//                                MainApplication.coemployer_name = edtCompanyName.getText().toString().trim();
//                                MainApplication.coannual_income = edtAnnualSal.getText().toString().trim();

                            }
                        }
                        else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("6")) {

                            if (edtCompanyName.getText().toString().equalsIgnoreCase("")) {
                                edtCompanyName.setError(getString(R.string.name_of_the_company_is_required));
                                edtCompanyName.requestFocus();
                            } else if (edtAnnualSal.getText().toString().equalsIgnoreCase("")) {
                                edtAnnualSal.setError(getString(R.string.annual_income_is_required));
                                edtAnnualSal.requestFocus();
                            } else {
                                MainApplication.employer_name = edtCompanyName.getText().toString().trim();
                                MainApplication.annual_income = edtAnnualSal.getText().toString().trim();
                                if (MainApplication.isBorrower) {
                                    saveBorrowerData();
                                } else {
                                    saveCoBorrowerData();
                                }
//                                MainApplication.employer_name = edtCompanyName.getText().toString().trim();
//                                MainApplication.annual_income = edtAnnualSal.getText().toString().trim();

//                                MainApplication.coemployer_name = edtCompanyName.getText().toString().trim();
//                                MainApplication.coannual_income = edtAnnualSal.getText().toString().trim();

                            }
                        }else {
                            MainApplication.employer_name = edtCompanyName.getText().toString().trim();
                            MainApplication.annual_income = edtAnnualSal.getText().toString().trim();
                            if (MainApplication.isBorrower) {
                                saveBorrowerData();
                            } else {
                                saveCoBorrowerData();
                            }
                        }
                    } else {
                        if (spProfession.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spProfession, getString(R.string.please_select_profession));
                        }
                    }

                }
            });


            buttonPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EligibilityCheckFragment_3 eligibilityCheckFragment_3 = new EligibilityCheckFragment_3();
                    transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_3).commit();
                }
            });

            if (!MainApplication.mainapp_userprofession.equals("")) {
                if (MainApplication.mainapp_userprofession.equalsIgnoreCase("0")) {
                    spProfession.setSelection(0);
                } else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("Student")) {
                    spProfession.setSelection(1);
                } else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("employed")) {
                    spProfession.setSelection(2);
                } else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("selfEmployed")) {
                    spProfession.setSelection(3);
                }
            }

            spProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String text = spProfession.getSelectedItem().toString();

                    if (MainApplication.isBorrower){
                        switch (position) {

                            case 0:

                                MainApplication.mainapp_userprofession = String.valueOf(position);
                                MainApplication.profession = String.valueOf(position);

                                try {
                                    linCompanyName.setVisibility(View.GONE);
                                    linSalary.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;

                            case 1:

                                MainApplication.mainapp_userprofession = String.valueOf(position);
                                MainApplication.profession = String.valueOf(position);
                                try {
                                    linCompanyName.setVisibility(View.VISIBLE);
                                    linSalary.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;

                            case 2:

                                MainApplication.mainapp_userprofession = String.valueOf(position);
                                MainApplication.profession = String.valueOf(position);
                                try {
                                    linCompanyName.setVisibility(View.GONE);
                                    linSalary.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;

                            case 3:

                                MainApplication.mainapp_userprofession = String.valueOf(position);
                                MainApplication.profession = String.valueOf(position);
                                try {
                                    linCompanyName.setVisibility(View.VISIBLE);
                                    linSalary.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                break;

                            case 4:

                                MainApplication.mainapp_userprofession = String.valueOf(position);
                                MainApplication.profession = String.valueOf(position);
                                try {
                                    linCompanyName.setVisibility(View.VISIBLE);
                                    linSalary.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;

                            case 5:

                                MainApplication.mainapp_userprofession = String.valueOf(position);
                                MainApplication.profession = String.valueOf(position);
                                try {
                                    linCompanyName.setVisibility(View.VISIBLE);
                                    linSalary.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;

                            case 6:

                                MainApplication.mainapp_userprofession = String.valueOf(position);
                                MainApplication.profession = String.valueOf(position);
                                try {
                                    linCompanyName.setVisibility(View.VISIBLE);
                                    linSalary.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    else {  //coborrower case

                        switch (position) {

                            case 0:
                                MainApplication.mainapp_userprofession = String.valueOf(position);
                                MainApplication.profession = String.valueOf(position);
                                try {
                                    linCompanyName.setVisibility(View.GONE);
                                    linSalary.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;

                            case 1:

                                MainApplication.mainapp_userprofession = String.valueOf(position);
                                MainApplication.profession = String.valueOf(position);
                                try {
                                    linCompanyName.setVisibility(View.VISIBLE);
                                    linSalary.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;

                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                MainApplication.mainapp_userprofession = String.valueOf(position+1);
                                MainApplication.profession = String.valueOf(position+1);
                                try {
                                    linCompanyName.setVisibility(View.VISIBLE);
                                    linSalary.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }

        return view;
    }

    private void setSpinnerError(Spinner spinner, String error) {
        try {
            View selectedView = spinner.getSelectedView();
            if (selectedView != null && selectedView instanceof TextView) {
                spinner.requestFocus();
                TextView selectedTextView = (TextView) selectedView;
                selectedTextView.setError(getString(R.string.error)); // any name of the error will do
                selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
                selectedTextView.setText(error); // actual error message
                //spinner.performClick(); // to open the spinner list if error is found.
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

    private void ProfessionApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "dashboard/getAllProfession";
            Map<String, String> params = new HashMap<String, String>();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getAllProfession", params, MainApplication.auth_token);
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

    public void getAllProfession(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    profession_arrayList = new ArrayList<>();
                    profession_arrayList.add("Select Profession");
                    arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
                    spProfession.setAdapter(arrayAdapter_profession);
                    arrayAdapter_profession.notifyDataSetChanged();
                    spProfession.setSelection(0);

                } catch (Exception e) {
                    String className = this.getClass().getSimpleName();
                    String name = new Object() {
                    }.getClass().getEnclosingMethod().getName();
                    String errorMsg = e.getMessage();
                    String errorMsgDetails = e.getStackTrace().toString();
                    String errorLine = String.valueOf(e.getStackTrace()[0]);
                    Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
                }

            } else {
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {

                    JSONArray jsonArray3 = jsonData.getJSONArray("profession");
                    profession_arrayList = new ArrayList<>();
                    professionPOJOArrayList = new ArrayList<>();
                    profession_arrayList.add("Select Profession");
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        ProfessionPOJO borrowerCurrentStatePersonalPOJO = new ProfessionPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        if (mJsonti.getString("profession").equalsIgnoreCase("student") && !MainApplication.isBorrower)
                            continue;
                        borrowerCurrentStatePersonalPOJO.Salaried = mJsonti.getString("profession");
                        profession_arrayList.add(mJsonti.getString("profession"));
                        borrowerCurrentStatePersonalPOJO.id = mJsonti.getString("id");
                        professionPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
                    }
                    arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
                    spProfession.setAdapter(arrayAdapter_profession);
                    arrayAdapter_profession.notifyDataSetChanged();

                    spProfession.setSelection(0);

                } else {
                }
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

//    0 = {HashMap$Node@6739} "sourceId" -> "2"
//            1 = {HashMap$Node@6740} "profession" -> "2"
//            2 = {HashMap$Node@6741} "pincode" -> "896856"
//            3 = {HashMap$Node@6742} "kyc_address" -> "hxhc"
//            4 = {HashMap$Node@6743} "student_id" -> "3300"
//            5 = {HashMap$Node@6744} "last_name" -> "Xhhd"
//            6 = {HashMap$Node@6745} "kyc_address_city" -> "59"
//            7 = {HashMap$Node@6746} "loan_amount" ->
//            8 = {HashMap$Node@6747} "employer_name" ->
//            9 = {HashMap$Node@6748} "kyc_landmark" -> "hfut"
//            10 = {HashMap$Node@6749} "kyc_address_country" -> "1"
//            11 = {HashMap$Node@6750} "middle_name" -> "Xvxg"
//            12 = {HashMap$Node@6751} "gender_id" -> "1"
//            13 = {HashMap$Node@6752} "application_id" -> "null"
//            14 = {HashMap$Node@6753} "aadhar_number" ->
//            15 = {HashMap$Node@6754} "pan_number" ->
//            16 = {HashMap$Node@6755} "kyc_address_state" -> "3"
//            17 = {HashMap$Node@6756} "dob" -> "01-Feb-2001"
//            18 = {HashMap$Node@6757} "has_aadhar_pan" -> "4"
//            19 = {HashMap$Node@6758} "mobile_number" -> "7787838985"
//            20 = {HashMap$Node@6759} "first_name" -> "Gxhx"
//            21 = {HashMap$Node@6760} "lead_id" -> "null"
//            22 = {HashMap$Node@6761} "annual_income" ->

    private void saveBorrowerData() {
        /** API CALL GET OTP**/
        try {//auth_token
            progressBar.setVisibility(View.VISIBLE);
            String url = MainApplication.mainUrl + "dashboard/addborrower";
            Map<String, String> params = new HashMap<String, String>();

            params.put("student_id", student_id);
            params.put("sourceId", "2");
            params.put("first_name", MainApplication.first_name);
            params.put("middle_name", MainApplication.middle_name);
            params.put("last_name", MainApplication.last_name);
            params.put("pincode", MainApplication.pincode);
            params.put("dob", MainApplication.dob);
            params.put("kyc_address", MainApplication.kyc_address);
            params.put("kyc_address_city", MainApplication.kyc_address_city);
            params.put("kyc_address_state", MainApplication.kyc_address_state);
            params.put("pan_number", MainApplication.pan_number);
            params.put("aadhar_number", MainApplication.aadhar_number);
            params.put("mobile_number", mobileNo);
            params.put("loan_amount", MainApplication.loan_amount);
            params.put("kyc_landmark", MainApplication.kyc_landmark);
            params.put("kyc_address_country", MainApplication.kyc_address_country);
            params.put("gender_id", MainApplication.gender_id);
            params.put("has_aadhar_pan", MainApplication.has_aadhar_pan);
            params.put("profession", MainApplication.profession);
            params.put("employer_name", MainApplication.employer_name);
            params.put("annual_income", MainApplication.annual_income);
            if(MainApplication.lead_id == null) {
                params.put("lead_id", "");
            }
            else {
                params.put("lead_id", MainApplication.lead_id);
            }
            if(MainApplication.application_id == null) {

                params.put("application_id", "");
            }else{
                params.put("application_id", MainApplication.application_id);

            }
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(getApplicationContext(), url, null, mFragment, "addborrower", params, MainApplication.auth_token);
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

    private void saveCoBorrowerData() {
        /** API CALL **/
        try {//auth_token
            progressBar.setVisibility(View.VISIBLE);
            String url = MainApplication.mainUrl + "dashboard/addcoborrower";
            Map<String, String> params = new HashMap<String, String>();

//            params.put("lead_id", MainApplication.lead_id);
            params.put("first_name", MainApplication.first_name);
            params.put("middle_name", MainApplication.middle_name);
            params.put("last_name", MainApplication.last_name);
            params.put("gender_id", MainApplication.gender_id);
            params.put("dob", MainApplication.dob);
            params.put("mobile_number", MainApplication.mobile_number);
            params.put("email",MainApplication.cobrEmail);
            params.put("relationship_with_applicant", MainApplication.relationship_with_applicant);
            params.put("has_aadhar_pan", MainApplication.has_aadhar_pan);
            params.put("aadhar_number", MainApplication.aadhar_number);
            params.put("pan_number", MainApplication.pan_number);
            params.put("documentry_pincode", MainApplication.pincode);
            params.put("kyc_address_country", MainApplication.kyc_address_country);
            params.put("kyc_address_state", MainApplication.kyc_address_state);
            params.put("kyc_address_city", MainApplication.kyc_address_city);
            params.put("kyc_landmark", MainApplication.kyc_address);
            params.put("kyc_address", MainApplication.kyc_landmark);
            params.put("profession", MainApplication.profession);
            params.put("employer_name", MainApplication.employer_name);
            params.put("annual_income", MainApplication.annual_income);

            if(MainApplication.lead_id == null) {
                params.put("lead_id", "");
            }
            else {
                params.put("lead_id", MainApplication.lead_id);
            }
            if(MainApplication.application_id == null) {

                params.put("application_id", "");
            }else{
                params.put("application_id", MainApplication.application_id);

            }

            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(getApplicationContext(), url, null, mFragment, "addcoborrower", params, MainApplication.auth_token);
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

    public void setaddborrower(JSONObject jsonData) {
        try {
            Log.e(MainApplication.TAG, "setDashboardImages: " + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            MainApplication.lead_id = jsonData.optString("lead_id");
            MainApplication.applicant_id = jsonData.optString("applicant_id");

            if (jsonData.getInt("status") == 1) {

                EligibilityCheckFragment_5 eligibilityCheckFragment_5 = new EligibilityCheckFragment_5();
                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_5).commit();

            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    public void setaddcoborrower(JSONObject jsonData) {
        try {
            Log.e(MainApplication.TAG, "setDashboardImages: " + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            MainApplication.lead_id = jsonData.optString("lead_id");
            MainApplication.applicant_id = jsonData.optString("applicant_id");

            if (jsonData.getInt("status") == 1) {

                EligibilityCheckFragment_6 eligibilityCheckFragment_6 = new EligibilityCheckFragment_6();
                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_6).commit();

            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
