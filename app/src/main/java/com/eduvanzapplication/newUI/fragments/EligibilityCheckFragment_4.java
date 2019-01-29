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
    LinearLayout linCompanyName, linSalary;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3;
    public static Spinner professionSpinner;

    EditText edtCompanyName, edtAnnualSal;
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

            linCompanyName = (LinearLayout) view.findViewById(R.id.linCompanyName);
            linSalary = (LinearLayout) view.findViewById(R.id.linSalary);

//            professionSpinner = (Spinner) view.findViewById(R.id.spProfession);
//            profession_arrayList = new ArrayList<>();
//            profession_arrayList.add("Select Any");
//            profession_arrayList.add("Student");
//            profession_arrayList.add("Employed");
//            profession_arrayList.add("Self Employed");
//            arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
//            professionSpinner.setAdapter(arrayAdapter_profession);
//            arrayAdapter_profession.notifyDataSetChanged();

            ProfessionApiCall();

//            professionSpinner = (Spinner) view.findViewById(R.id.spProfession);
//            profession_arrayList = new ArrayList<>();
//            profession_arrayList.add("Profession of Co-borrower");
//            profession_arrayList.add("Employed");
//            profession_arrayList.add("Self Employed");
//            arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
//            professionSpinner.setAdapter(arrayAdapter_profession);
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

                            saveBorrowerData();
//                            EligibilityCheckFragment_5 eligibilityCheckFragment_5 = new EligibilityCheckFragment_5();
//                            transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_5).commit();

                        } else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("2")) {

                            if (edtCompanyName.getText().toString().equalsIgnoreCase("")) {
                                edtCompanyName.setError(getString(R.string.name_of_the_company_is_required));
                                edtCompanyName.requestFocus();
                            } else if (edtAnnualSal.getText().toString().equalsIgnoreCase("")) {
                                edtAnnualSal.setError(getString(R.string.annual_income_is_required));
                                edtAnnualSal.requestFocus();
                            } else {
                                MainApplication.employer_name = edtCompanyName.getText().toString().trim();
                                MainApplication.annual_income = edtAnnualSal.getText().toString().trim();

                                saveBorrowerData();

//                                MainApplication.employer_name = edtCompanyName.getText().toString().trim();
//                                MainApplication.annual_income = edtAnnualSal.getText().toString().trim();

//                                MainApplication.coemployer_name = edtCompanyName.getText().toString().trim();
//                                MainApplication.coannual_income = edtAnnualSal.getText().toString().trim();

//                                EligibilityCheckFragment_5 eligibilityCheckFragment_5 = new EligibilityCheckFragment_5();
//                                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_5).commit();

                            }
                        } else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("3")) {

                            if (edtCompanyName.getText().toString().equalsIgnoreCase("")) {
                                edtCompanyName.setError(getString(R.string.name_of_the_company_is_required));
                                edtCompanyName.requestFocus();
                            } else if (edtAnnualSal.getText().toString().equalsIgnoreCase("")) {
                                edtAnnualSal.setError(getString(R.string.annual_income_is_required));
                                edtAnnualSal.requestFocus();
                            } else {
                                saveBorrowerData();

//                                MainApplication.employer_name = edtCompanyName.getText().toString().trim();
//                                MainApplication.annual_income = edtAnnualSal.getText().toString().trim();

//                                MainApplication.coemployer_name = edtCompanyName.getText().toString().trim();
//                                MainApplication.coannual_income = edtAnnualSal.getText().toString().trim();

                                EligibilityCheckFragment_5 eligibilityCheckFragment_5 = new EligibilityCheckFragment_5();
                                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_5).commit();
                            }
                        } else {
                            saveBorrowerData();
                        }
                    } else {
                        if (professionSpinner.getSelectedItemPosition() <= 0) {
                            setSpinnerError(professionSpinner, getString(R.string.please_select_profession));
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
                    professionSpinner.setSelection(0);
                } else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("Student")) {
                    professionSpinner.setSelection(1);
                } else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("employed")) {
                    professionSpinner.setSelection(2);
                } else if (MainApplication.mainapp_userprofession.equalsIgnoreCase("selfEmployed")) {
                    professionSpinner.setSelection(3);
                }
            }

            professionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String text = professionSpinner.getSelectedItem().toString();
                    if (text.equalsIgnoreCase("Select Any")) {
                        MainApplication.mainapp_userprofession = "0";
                        MainApplication.profession = "0";

                        try {
                            linCompanyName.setVisibility(View.GONE);
                            linSalary.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (text.equalsIgnoreCase("Student")) {
                        MainApplication.mainapp_userprofession = "Student";
                        MainApplication.mainapp_userprofession = "1";
                        MainApplication.profession = "1";
                        try {
                            linCompanyName.setVisibility(View.GONE);
                            linSalary.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (text.equalsIgnoreCase("Employed")) {
                        MainApplication.mainapp_userprofession = "employed";
                        MainApplication.mainapp_userprofession = "2";
                        MainApplication.profession = "2";
                        try {
                            linCompanyName.setVisibility(View.VISIBLE);
                            linSalary.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (text.equalsIgnoreCase("Self Employed")) {
                        MainApplication.mainapp_userprofession = "selfEmployed";
                        MainApplication.mainapp_userprofession = "3";
                        MainApplication.profession = "3";
                        try {
                            linCompanyName.setVisibility(View.VISIBLE);
                            linSalary.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
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
                    professionSpinner.setAdapter(arrayAdapter_profession);
                    arrayAdapter_profession.notifyDataSetChanged();
                    professionSpinner.setSelection(0);

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
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray3 = jsonObject.getJSONArray("profession");
                    profession_arrayList = new ArrayList<>();
                    professionPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        ProfessionPOJO borrowerCurrentStatePersonalPOJO = new ProfessionPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borrowerCurrentStatePersonalPOJO.Salaried = mJsonti.getString("profession");
                        profession_arrayList.add(mJsonti.getString("profession"));
                        borrowerCurrentStatePersonalPOJO.id = mJsonti.getString("id");
                        professionPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
                    }
                    arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
                    professionSpinner.setAdapter(arrayAdapter_profession);
                    arrayAdapter_profession.notifyDataSetChanged();

                    professionSpinner.setSelection(Integer.parseInt(professionID));

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

    private void saveBorrowerData() {
        /** API CALL GET OTP**/
        try {//auth_token
            progressBar.setVisibility(View.VISIBLE);
            String url = MainApplication.mainUrl + "dashboard/addborrower";
            Map<String, String> params = new HashMap<String, String>();

            params.put("student_id", student_id);
            params.put("sourceId", "1");
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
            params.put("lead_id", MainApplication.annual_income);
            params.put("application_id", MainApplication.annual_income);
            params.put("lead_id", MainApplication.lead_id);
            params.put("application_id", MainApplication.application_id);

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
        /** API CALL GET OTP**/
        try {//auth_token
            progressBar.setVisibility(View.VISIBLE);
            String url = MainApplication.mainUrl + "dashboard/addcoborrower";
            Map<String, String> params = new HashMap<String, String>();

            params.put("lead_id", MainApplication.lead_id);
            params.put("first_name", MainApplication.first_name);
            params.put("middle_name", MainApplication.middle_name);
            params.put("last_name", MainApplication.last_name);
            params.put("gender_id", MainApplication.gender_id);
            params.put("dob", MainApplication.dob);
            params.put("mobile_number", MainApplication.dob);
            params.put("relationship_with_applicant", "1");
            params.put("has_aadhar_pan", MainApplication.has_aadhar_pan);
            params.put("aadhar_number", MainApplication.aadhar_number);
            params.put("PAN_number", MainApplication.pan_number);
            params.put("documentry_pincode", MainApplication.pincode);
            params.put("kyc_address_country", MainApplication.kyc_address_country);
            params.put("kyc_address_state", MainApplication.kyc_address_state);
            params.put("kyc_address_city", MainApplication.kyc_address_city);
            params.put("kyc_landmark", MainApplication.kyc_address);
            params.put("kyc_address", MainApplication.kyc_landmark);
            params.put("profession", MainApplication.profession);
            params.put("employer_name", MainApplication.employer_name);
            params.put("annual_income", MainApplication.annual_income);

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

            if (jsonData.getInt("status") == 1) {

//                JSONObject jsonObject = jsonData.getJSONObject("result");
//                JSONArray jsonArray = jsonObject.getJSONArray("leadId");

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

            if (jsonData.getInt("status") == 1) {

//                JSONObject jsonObject = jsonData.getJSONObject("result");
//                JSONArray jsonArray = jsonObject.getJSONArray("leadId");

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


}
