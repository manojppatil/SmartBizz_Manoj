package com.eduvanzapplication.newUI.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import android.widget.CheckBox;
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

import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerCurrentCityPersonalPOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerCurrentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerCurrentResidenceDurationPersonalPOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerCurrentResidenceTypePersonalPOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerCurrentStatePersonalPOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerJobDurationFinancePOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerProfessionFinancePOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.RelationshipwithBorrowerPOJO;
import com.eduvanzapplication.newUI.VolleyCallNew;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.eduvanzapplication.newUI.MainApplication.TAG;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner
 */


public class LoanApplicationFragment_2 extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    Button buttonNext, buttonPrevious;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3;
    public static TextView birthdaycalender, lable, textViewbirthday;
    public static TextInputLayout input_cgpa,input_degree,input_previousamt;
    Typeface typeface;
    Calendar cal;
    public static String dateformate="", userID="", coBorrowerBackground="";
    MainApplication mainApplication;
    static View view;
    static FragmentTransaction transaction;
    public static ProgressBar progressBar;

    public static RadioButton radioButtonPreviousEmiYes, radioButtonPreviousEmiNo, radioButtonMale, radioButtonFemale;
    public static RadioButton radioButtonMarried, radioButtonSingle, radioButtonGovernment,
            radioButtonPrivate;

    public static RadioGroup radioGroupMaritialStatus, radioGroupPreviousEmi, radioGroupGender, radioGroupEmployerType;
    public static EditText fname, lname, adhaarno, panno, currentaddress, currentpincode, permanentaddress,
            contactno, emailid, permanentpincode, monthlyrent, previousemi;

    public static Spinner spinnerPermanentCity, spinnerPermanentCountry, spinnerPermanentState;
    public static ArrayAdapter arrayAdapter_permanentCity, arrayAdapter_permanentState, arrayAdapter_permanentCountry;
    public String permanentcityID="", permanentstateID="", permanentCountryID;

    public static Spinner spinnerCurrentResidenceType;
    public static ArrayAdapter arrayAdapter_currentResidencetype;
    public static ArrayList<String> currentResidencetype_arrayList;
    public static ArrayList<CoborrowerCurrentResidenceTypePersonalPOJO> coborrowerCurrentResidenceTypePersonalPOJOArrayList;
    public String currentResidencetypeID="";

    public static Spinner spinnerCurrentCity;
    public static ArrayAdapter arrayAdapter_currentCity;
    public static ArrayList<String> currentcity_arrayList, permanentcity_arrayList;
    public static ArrayList<CoborrowerCurrentCityPersonalPOJO> coborrowerCurrentCityPersonalPOJOArrayList, coborrowerPermanentCityPersonalPOJOArrayList;
    public String currentcityID="";

    public static Spinner spinnerCurrentState;
    public static ArrayAdapter arrayAdapter_currentState;
    public static ArrayList<String> currentstate_arrayList;
    public static ArrayList<CoborrowerCurrentStatePersonalPOJO> coborrowerCurrentStatePersonalPOJOArrayList, coborrowerPermanentStatePersonalPOJOArrayList ;
    public String currentstateID="";

    public static Spinner spinnerCurrentCountry;
    public static ArrayAdapter arrayAdapter_currentCountry;
    public static ArrayList<String> currentCountry_arrayList;
    public static ArrayList<CoborrowerCurrentCountryPersonalPOJO> coborrowerCurrentCountryPersonalPOJOArrayList;
    public String currentcountryID="";

    public static Spinner spinnerCurrentResidenceDuration;
    public static ArrayAdapter arrayAdapter_currentResidenceDuration;
    public static ArrayList<String> currentresidenceduration_arrayList;
    public static ArrayList<CoborrowerCurrentResidenceDurationPersonalPOJO> coborrowerCurrentResidenceDurationPersonalPOJOArrayList;
    public String currentresidenceDurationID="";

    public static Spinner spinnerRelationshipwithBorrower;
    public static ArrayAdapter arrayAdapter_relationshipwithborrower;
    public static ArrayList<String> relationshipwithborrower_arrayList;
    public static ArrayList<RelationshipwithBorrowerPOJO> relationshipwithBorrowerPOJOArrayList;
    public String relationshipwithborrowerID="";

    /** FINANCIAL DETAILS **/
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

    public static LinearLayout linearLayoutLeftOffcoBorrower;

    public static CheckBox checkBoxSameasAbove;

    public LoanApplicationFragment_2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loanapplication_2, container, false);
        context = getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mainApplication = new MainApplication();
        mFragment = new LoanApplicationFragment_2();
        transaction = getFragmentManager().beginTransaction();
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("logged_id","null");
        coBorrowerBackground = sharedPreferences.getString("coBorrowerBackground_dark","0");

        MainApplication.currrentFrag = 2;

        typeface = Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");

        setViews();

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isIDNull = !adhaarno.getText().toString().equals("") ||
                        !panno.getText().toString().equals("");

                if (!fname.getText().toString().equals("") && !lname.getText().toString().equals("") &&
                        !textViewbirthday.getText().toString().equals("") &&
                        !currentaddress.getText().toString().equals("") &&
                        !currentpincode.getText().toString().equals("") &&
                        !permanentaddress.getText().toString().equals("") &&
                        !permanentpincode.getText().toString().equals("") &&
                        !contactno.getText().toString().equals("") &&
                        !emailid.getText().toString().equals("")&&
                        !anuualincome.getText().toString().equals("") &&
                        !employeer.getText().toString().equals("") && isIDNull) {

                    if (radioGroupGender.getCheckedRadioButtonId() > 0) {
                        radioButtonFemale.setError(null);

                    if(radioGroupMaritialStatus.getCheckedRadioButtonId()>0) {
                        radioButtonSingle.setError(null);

                        if (radioGroupPreviousEmi.getCheckedRadioButtonId() > 0) {
                            radioButtonPreviousEmiNo.setError(null);

                            if (radioGroupEmployerType.getCheckedRadioButtonId() > 0) {
                                radioButtonPrivate.setError(null);

                                if (!currentcityID.equalsIgnoreCase("") && !currentstateID.equalsIgnoreCase("") &&
                                        !currentcountryID.equalsIgnoreCase("") && !permanentcityID.equalsIgnoreCase("") &&
                                        !permanentstateID.equalsIgnoreCase("") && !permanentCountryID.equalsIgnoreCase("") &&
                                        !currentResidencetypeID.equalsIgnoreCase("") && !currentresidenceDurationID.equalsIgnoreCase("") &&
                                        !relationshipwithborrowerID.equalsIgnoreCase("") && !professionID.equalsIgnoreCase("") &&
                                        !jobDurationID.equalsIgnoreCase("")) {

                                    try {
                                        String maritialstatus = "";
                                        if (radioButtonMarried.isChecked()) {
                                            maritialstatus = "1";
                                        } else if (radioButtonSingle.isChecked()) {
                                            maritialstatus = "2";
                                        }

                                        String previousEmi = "";
                                        if (radioButtonPreviousEmiYes.isChecked()) {
                                            previousEmi = "1";
                                        } else if (radioButtonPreviousEmiNo.isChecked()) {
                                            previousEmi = "0";
                                        }

                                        String gender = "";
                                        if (radioButtonMale.isChecked()) {
                                            gender = "1";
                                        }
                                        if (radioButtonFemale.isChecked()) {
                                            gender = "2";
                                        }

                                        String empType = "";
                                        if (radioButtonGovernment.isChecked()) {
                                            empType = "0";
                                        }
                                        if (radioButtonPrivate.isChecked()) {
                                            empType = "1";
                                        }

                                        progressBar.setVisibility(View.VISIBLE);
                                        String url = MainApplication.mainUrl + "algo/setCoBorrowerLoanDetails";
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("logged_id", userID);
                                        params.put("coborrower_address_type", currentResidencetypeID);
                                        params.put("coborrower_monthly_rent", monthlyrent.getText().toString());
                                        params.put("coborrower_current_address", currentaddress.getText().toString());
                                        params.put("coborrower_current_city", currentcityID);
                                        params.put("coborrower_current_state", currentstateID);
                                        params.put("coborrower_current_country", currentcountryID);
                                        params.put("coborrower_current_pincode", currentpincode.getText().toString());
                                        params.put("coborrower_permanent_address", permanentaddress.getText().toString());
                                        params.put("coborrower_permanent_city", permanentcityID);
                                        params.put("coborrower_permanent_state", permanentstateID);
                                        params.put("coborrower_permanent_country", permanentCountryID);
                                        params.put("coborrower_permanent_pincode", permanentpincode.getText().toString());
                                        params.put("coborrower_first_name", fname.getText().toString());
                                        params.put("coborrower_last_name", lname.getText().toString());
                                        params.put("coborrower_dob", textViewbirthday.getText().toString());
                                        params.put("coborrower_is_married", maritialstatus);
                                        params.put("coborrower_pan_no", panno.getText().toString());
                                        params.put("coborrower_aadhar_no", adhaarno.getText().toString());

                                        params.put("coborrower_email", emailid.getText().toString());
                                        params.put("coborrower_mobile", contactno.getText().toString());
                                        params.put("coborrower_living_since", currentresidenceDurationID);
                                        params.put("coborrower_relationship", relationshipwithborrowerID);

                                        params.put("coborrower_profession", professionID);
                                        params.put("coborrower_income", anuualincome.getText().toString());
                                        params.put("coborrower_organization", employeer.getText().toString());
                                        params.put("coborrower_working_organization_since", jobDurationID);
                                        params.put("coborrower_has_any_emi", previousEmi);
                                        params.put("ip_address", Utils.getIPAddress(true));

                                        params.put("coborrower_previous_emi_amount", previousemi.getText().toString());

                                        params.put("coborrower_gender_id", gender);

                                        params.put("coborrower_employer_type", empType);

                                        MainApplication.coborrowerValue1 = currentResidencetypeID;
                                        MainApplication.coborrowerValue2 = monthlyrent.getText().toString();
                                        MainApplication.coborrowerValue3 = currentaddress.getText().toString();
                                        MainApplication.coborrowerValue4 = currentcityID;
                                        MainApplication.coborrowerValue5 = currentstateID;
                                        MainApplication.coborrowerValue6 = currentcountryID;
                                        MainApplication.coborrowerValue7 = currentpincode.getText().toString();
                                        MainApplication.coborrowerValue8 = permanentaddress.getText().toString();
                                        MainApplication.coborrowerValue9 = permanentcityID;
                                        MainApplication.coborrowerValue10 = permanentstateID;
                                        MainApplication.coborrowerValue11 = permanentCountryID;
                                        MainApplication.coborrowerValue12 = permanentpincode.getText().toString();
                                        MainApplication.coborrowerValue13 = fname.getText().toString();
                                        MainApplication.coborrowerValue14 = lname.getText().toString();
                                        MainApplication.coborrowerValue15 = textViewbirthday.getText().toString();
                                        MainApplication.coborrowerValue16 = maritialstatus;
                                        MainApplication.coborrowerValue17 = panno.getText().toString();
                                        MainApplication.coborrowerValue18 = adhaarno.getText().toString();

                                        MainApplication.coborrowerValue19 = emailid.getText().toString();
                                        MainApplication.coborrowerValue20 = contactno.getText().toString();
                                        MainApplication.coborrowerValue21 = currentresidenceDurationID;
                                        MainApplication.coborrowerValue22 = relationshipwithborrowerID;
                                        MainApplication.coborrowerValue23 = professionID;
                                        MainApplication.coborrowerValue24 = anuualincome.getText().toString();
                                        MainApplication.coborrowerValue25 = employeer.getText().toString();
                                        MainApplication.coborrowerValue26 = jobDurationID;
                                        MainApplication.coborrowerValue27 = previousEmi;
                                        MainApplication.coborrowerValue28 = previousemi.getText().toString();
                                        MainApplication.coborrowerValue29 = gender;
                                        MainApplication.coborrowerValue30 = empType;
                                        if(!Globle.isNetworkAvailable(context))
                                        {
                                            Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

                                        } else {
                                            VolleyCallNew volleyCall = new VolleyCallNew();
                                            volleyCall.sendRequest(context, url, null, mFragment, "sendcoboorrowerDetails", params);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    if(spinnerCurrentResidenceDuration.getSelectedItemPosition() <= 0)
                                    {
                                        setSpinnerError(spinnerCurrentResidenceDuration,"Please select duration of stay at current address");
                                        spinnerCurrentResidenceDuration.requestFocus();

                                    } if(spinnerProfession.getSelectedItemPosition() <= 0)
                                    {
                                        setSpinnerError(spinnerProfession,"Please select profession");
                                        spinnerProfession.requestFocus();

                                    }  if(spinnerJobDuration.getSelectedItemPosition() <= 0)
                                    {
                                        setSpinnerError(spinnerJobDuration,"Please select duration of job/business");
                                        spinnerJobDuration.requestFocus();
                                    }
                                    Toast.makeText(context, "Please fill up all the details to continue", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                radioButtonPrivate.setError("You need to select Employer Type");
                                radioButtonPrivate.requestFocus();
                            }

                        }else {
                            radioButtonPreviousEmiNo.setError("You need to select Previous Emi status");
                            radioButtonPreviousEmiNo.requestFocus();
                        }

                    }else {
                        radioButtonSingle.setError("You need to select Maritial status");
                        radioButtonSingle.requestFocus();
                    }
                    }else {
                        radioButtonFemale.setError("You need to select Gender");
                        radioButtonFemale.requestFocus();
                    }

                } else {

                    if (fname.getText().toString().equalsIgnoreCase("")) {
                        fname.setError("First Name is Required");
                        fname.requestFocus();
                    }
                    else {
                        fname.setError(null);

                    }

                    if (lname.getText().toString().equalsIgnoreCase("")) {
                        lname.setError("Last Name is Required");
                        lname.requestFocus();
                    }else {
                        lname.setError(null);

                    }

                    if (textViewbirthday.getText().toString().equalsIgnoreCase("")) {
                        textViewbirthday.setError("Birthdate is Required");
                        textViewbirthday.requestFocus();
                    }
                    else if (textViewbirthday.getText().toString().toLowerCase().equals("birthdate")) {
                        textViewbirthday.setError("Birthdate is Required");
                        textViewbirthday.requestFocus();
                    }
                    else {
                        textViewbirthday.setError(null);
                    }

                    if (adhaarno.getText().toString().equalsIgnoreCase("")) {
                        adhaarno.setError("Aadhaar Number is Required");
                        adhaarno.requestFocus();
                    }
                    else {
                        adhaarno.setError(null);
                    }
                    if (panno.getText().toString().equalsIgnoreCase("")) {
                        panno.setError("PAN number is required is Required");
                        panno.requestFocus();
                    }else {
                        panno.setError(null);
                    }

//                    if (adhaarno.getText().toString().equalsIgnoreCase("") && panno.getText().toString().equalsIgnoreCase("")) {
//                        adhaarno.setError("Aadhaar Number is Required");
//                        adhaarno.requestFocus();
//                    }
//                    else {
//                        adhaarno.setError(null);
//
//                    }
//                    if (panno.getText().toString().equalsIgnoreCase("") && adhaarno.getText().toString().equalsIgnoreCase("")) {
//                        panno.setError("PAN number is required is Required");
//                        panno.requestFocus();
//                    }else {
//                        panno.setError(null);
//
//                    }

                    if (currentaddress.getText().toString().equalsIgnoreCase("")) {
                        currentaddress.setError("Current Address is Required");
                        currentaddress.requestFocus();
                    }else {
                        currentaddress.setError(null);
                    }

                    if (currentpincode.getText().toString().equalsIgnoreCase("")) {
                        currentpincode.setError("Current Pin code is Required");
                        currentpincode.requestFocus();
                    }else {
                        currentpincode.setError(null);

                    }

                    if (permanentaddress.getText().toString().equalsIgnoreCase("")) {
                        permanentaddress.setError("Permanent Address is Required");
                        permanentaddress.requestFocus();
                    }
                    else {
                        permanentaddress.setError(null);
                    }

                    if (permanentpincode.getText().toString().equalsIgnoreCase("")) {
                        permanentpincode.setError("Permanent Pin code is Required");
                        permanentpincode.requestFocus();
                    }else {
                        permanentpincode.setError(null);
                    }

                    if (contactno.getText().toString().equalsIgnoreCase("")) {
                        contactno.setError("Contact Number is Required");
                        contactno.requestFocus();
                    }else {
                        contactno.setError(null);
                    }

                    if (emailid.getText().toString().equalsIgnoreCase("")) {
                        emailid.setError("Email Id is Required");
                        emailid.requestFocus();
                    }else {
                        emailid.setError(null);

                    }

                    if (anuualincome.getText().toString().equalsIgnoreCase("")) {
                        anuualincome.setError("Annual Income is Required");
                        anuualincome.requestFocus();
                    }else {
                        anuualincome.setError(null);

                    }
                    if (employeer.getText().toString().equalsIgnoreCase("")) {
                        employeer.setError("Employeer name is Required");
                    }if (!employeer.getText().toString().equalsIgnoreCase("")) {
                        employeer.setError(null);
                    }
                     if(spinnerRelationshipwithBorrower.getSelectedItemPosition() <= 0)
                    {
                        setSpinnerError(spinnerRelationshipwithBorrower,"Please select relationship with borrower");
                        spinnerRelationshipwithBorrower.requestFocus();
                    }

                     if(spinnerCurrentResidenceType.getSelectedItemPosition() <= 0)
                    {
                        setSpinnerError(spinnerCurrentResidenceType,"Please select residency type");
                        spinnerCurrentResidenceType.requestFocus();

                    } if(spinnerCurrentCountry.getSelectedItemPosition() <= 0)
                    {
                        setSpinnerError(spinnerCurrentCountry,"Please select current country");

                    }
                     if(spinnerCurrentState.getSelectedItemPosition() <= 0)
                    {
                        setSpinnerError(spinnerCurrentState,"Please select current state");

                    }
                     if(spinnerCurrentCity.getSelectedItemPosition() <= 0)
                    {
                        setSpinnerError(spinnerCurrentCity,"Please select current city");

                    } if(spinnerPermanentCountry.getSelectedItemPosition() <= 0)
                    {
                        setSpinnerError(spinnerPermanentCountry,"Please select permanent country");
                        spinnerPermanentCountry.requestFocus();

                    }
                     if(spinnerPermanentState.getSelectedItemPosition() <= 0)
                    {
                        setSpinnerError(spinnerPermanentState,"Please select permanent state");

                    }
                     if(spinnerPermanentCity.getSelectedItemPosition() <= 0)
                    {
                        setSpinnerError(spinnerPermanentCity,"Please select permanent city");

                    } if(spinnerCurrentResidenceDuration.getSelectedItemPosition() <= 0)
                    {
                        setSpinnerError(spinnerCurrentResidenceDuration,"Please select duration of stay at current address");
                        spinnerCurrentResidenceDuration.requestFocus();

                    } if(spinnerProfession.getSelectedItemPosition() <= 0)
                    {
                        setSpinnerError(spinnerProfession,"Please select profession");
                        spinnerProfession.requestFocus();

                    } if(spinnerJobDuration.getSelectedItemPosition() <= 0)
                    {
                        setSpinnerError(spinnerJobDuration,"Please select duration of job/business");
                        spinnerJobDuration.requestFocus();
                    }
                }
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanApplicationFragment_1 loanApplicationFragment_1 = new LoanApplicationFragment_1();
                transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_1).commit();
            }
        });

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
                textViewbirthday.setText(dateformate);
                MainApplication.coborrowerValue15 = textViewbirthday.getText().toString();
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
                DatePickerDialog data=  new DatePickerDialog(context, date, cal
                        .get(Calendar.YEAR)-18, 1,
                        1);
                data.getDatePicker().setMaxDate(System.currentTimeMillis()-1234564);
                data.show();
            }
        });

        radioButtonMarried.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radioButtonMarried.isChecked()) {
                    MainApplication.coborrowerValue16 = "1";
                }
            }
        });

        radioButtonSingle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radioButtonSingle.isChecked()) {
                    MainApplication.coborrowerValue16 = "2";
                }
            }
        });

        radioButtonPreviousEmiYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(radioButtonPreviousEmiYes.isChecked()){
                    MainApplication.coborrowerValue27 = "1";
                }
            }
        });

        radioButtonPreviousEmiNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(radioButtonPreviousEmiNo.isChecked()){
                    MainApplication.coborrowerValue27 = "0";
                }
            }
        });


        radioGroupPreviousEmi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("TAG", "onCheckedChanged: ");
                Log.e("TAG", "onCheckedChanged: " + radioGroupPreviousEmi.getCheckedRadioButtonId());
                switch (radioGroupPreviousEmi.getCheckedRadioButtonId()) {
                    case R.id.radiobutton_emiyes_coborrower:
                        Log.e("TAG", "yes: ");
                        input_previousamt.setVisibility(View.VISIBLE);
                        previousemi.requestFocus();
                        //previousemi.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radiobutton_emino_coborrower:
                        Log.e("TAG", "no: ");
                        input_previousamt.setVisibility(View.GONE);
                        //previousemi.setVisibility(View.GONE);
                        break;
                    default:
                        break;
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
                MainApplication.coborrowerValue13 = fname.getText().toString();
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
                MainApplication.coborrowerValue14 = lname.getText().toString();
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
                MainApplication.coborrowerValue18 = adhaarno.getText().toString();
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
                MainApplication.coborrowerValue17 = panno.getText().toString();
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
                MainApplication.coborrowerValue2 = monthlyrent.getText().toString();
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
                MainApplication.coborrowerValue3 = currentaddress.getText().toString();
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
                MainApplication.coborrowerValue7 = currentpincode.getText().toString();
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
                MainApplication.coborrowerValue8 = permanentaddress.getText().toString();
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
                MainApplication.coborrowerValue12 = permanentpincode.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        anuualincome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.coborrowerValue24 = anuualincome.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        employeer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.coborrowerValue25 = employeer.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contactno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.coborrowerValue20 = contactno.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        emailid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.coborrowerValue19 = emailid.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /** END OF EDIT TEXT CHANGE LISTENER FOR PARTIAL SAVING**/

        /** SPINNER CLICK **/

        spinnerCurrentResidenceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spinnerCurrentResidenceType.getSelectedItem().toString();
                    int count = coborrowerCurrentResidenceTypePersonalPOJOArrayList.size();
                    Log.e("TAG", "count: "+count );
                    for (int i = 0; i < count; i++) {
                        if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).residencetypeName.equalsIgnoreCase(text)) {
                            MainApplication.coborrowerValue1 = currentResidencetypeID = coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID;
                            Log.e(TAG, "ididididididid:" + coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID);
                            if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("1")) {
                                monthlyrent.setVisibility(View.GONE);
                                monthlyrent.setText("");
                            } else if(coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("3")){
                                monthlyrent.setVisibility(View.GONE);
                                monthlyrent.setText("");
                            }else if(coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("8")){
                                monthlyrent.setVisibility(View.GONE);
                                monthlyrent.setText("");
                            } else {
                                monthlyrent.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerCurrentCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if(spinnerCurrentCity.getSelectedItemPosition()>0)
                    {
                        checkBoxSameasAbove.setEnabled(true);
                        checkBoxSameasAbove.setClickable(true);
                    }
                    else{
                        checkBoxSameasAbove.setEnabled(false);
                        checkBoxSameasAbove.setClickable(false);
                    }
                    String text = spinnerCurrentCity.getSelectedItem().toString();
                    int count = coborrowerCurrentCityPersonalPOJOArrayList.size();
                    Log.e("TAG", "count: "+count );
                    for (int i = 0; i < count; i++) {
                        if (coborrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                            MainApplication.coborrowerValue4 = currentcityID = coborrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
                            Log.e("I_________D", "currentcityID: " + currentcityID);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCurrentState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spinnerCurrentState.getSelectedItem().toString();
                    int count = coborrowerCurrentStatePersonalPOJOArrayList.size();
                    Log.e("TAG", "count: "+count );
                    for (int i = 0; i < count; i++) {
                        if (coborrowerCurrentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                            MainApplication.coborrowerValue5 = currentstateID = coborrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
                            Log.e("I_________D", "currentstateID: " + currentstateID);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
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
                try {
                    String text = spinnerCurrentCountry.getSelectedItem().toString();
                    int count = coborrowerCurrentCountryPersonalPOJOArrayList.size();
                    Log.e("TAG", "count: "+count );
                    for (int i = 0; i < count; i++) {
                        if (coborrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                            MainApplication.coborrowerValue6 = currentcountryID = coborrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                            Log.e("I_________D", "currentcountryID: " + currentcountryID);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                stateApiCall();
                spinnerCurrentCity.setSelection(0);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spinnerPermanentCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spinnerPermanentCity.getSelectedItem().toString();
                    int count = coborrowerPermanentCityPersonalPOJOArrayList.size();
                    Log.e("TAG", "count: "+count );
                    for (int i = 0; i < count; i++) {
                        if (coborrowerPermanentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                            MainApplication.coborrowerValue9 = permanentcityID = coborrowerPermanentCityPersonalPOJOArrayList.get(i).cityID;
                            Log.e("I_________D", "permanentcityID: " + permanentcityID);
                        }
                    }

                    if(count>0){
                        if(checkBoxSameasAbove.isChecked()){
                            for(int i=0; i < coborrowerPermanentCityPersonalPOJOArrayList.size(); i++){
                                if(coborrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)){
                                    spinnerPermanentCity.setSelection(i);
                                }
                            }

                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
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
                try{
                    int count = coborrowerCurrentStatePersonalPOJOArrayList.size();
                    Log.e("TAG", "count: "+count );
                    for (int i = 0; i < count; i++) {
                        if (coborrowerCurrentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                            MainApplication.coborrowerValue10 = permanentstateID = coborrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
                            Log.e("I_________D", "permanentstateID: " + permanentstateID);
                        }
                    }

                    if(checkBoxSameasAbove.isChecked()){
                        Log.e(TAG, "getPermanentStates: +++++++++++++++++++*********************"+currentstateID );
                        spinnerPermanentState.setSelection(Integer.parseInt(currentstateID));
                    }

                    permanentCityApiCall();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPermanentCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spinnerPermanentCountry.getSelectedItem().toString();
                    int count = coborrowerCurrentCountryPersonalPOJOArrayList.size();
                    Log.e("TAG", "count: "+count );
                    for (int i = 0; i < count; i++) {
                        if (coborrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                            MainApplication.coborrowerValue10 = permanentCountryID = coborrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                            Log.e("I_________D", "permanentCountryID: " + permanentCountryID);
                        }
                    }
                    permanentStateApiCall();
                    spinnerPermanentCity.setSelection(0);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCurrentResidenceDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spinnerCurrentResidenceDuration.getSelectedItem().toString();
                    int count = coborrowerCurrentResidenceDurationPersonalPOJOArrayList.size();
                    Log.e("TAG", "count: "+count );
                    for (int i = 0; i < count; i++) {
                        if (coborrowerCurrentResidenceDurationPersonalPOJOArrayList.get(i).durationName.equalsIgnoreCase(text)) {
                            currentresidenceDurationID = coborrowerCurrentResidenceDurationPersonalPOJOArrayList.get(i).durationID;
                            Log.e("I_________D", "currentresidenceDurationID: " + currentresidenceDurationID);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerRelationshipwithBorrower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spinnerRelationshipwithBorrower.getSelectedItem().toString();
                    int count = relationshipwithBorrowerPOJOArrayList.size();
                    Log.e("TAG", "count: "+count );
                    for (int i = 0; i < count; i++) {
                        if (relationshipwithBorrowerPOJOArrayList.get(i).relationName.equalsIgnoreCase(text)) {
                            MainApplication.coborrowerValue22 = relationshipwithborrowerID = relationshipwithBorrowerPOJOArrayList.get(i).relationID;
                            Log.e("I_________D", "currentresidenceDurationID: " + relationshipwithborrowerID);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /** FINANCIAL DETAILS **/
        spinnerJobDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Log.e("I_________D", "onItemClick: " );
                    String text = spinnerJobDuration.getSelectedItem().toString();
                    int count = coborrowerJobDurationFinancePOJOArrayList.size();
                    Log.e("TAG", "count: "+count );
                    for (int i = 0; i < count; i++) {
                        if (coborrowerJobDurationFinancePOJOArrayList.get(i).durationName.equalsIgnoreCase(text)) {
                            MainApplication.coborrowerValue26 = jobDurationID = coborrowerJobDurationFinancePOJOArrayList.get(i).durationID;
                            Log.e("I_________D", "jobDurationID: " + jobDurationID);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Log.e("I_________D", "onItemClick: " );
                    String text = spinnerProfession.getSelectedItem().toString();
                    int count = coborrowerProfessionFinancePOJOArrayList.size();
                    Log.e("TAG", "count: "+count );
                    for (int i = 0; i < count; i++) {
                        if (coborrowerProfessionFinancePOJOArrayList.get(i).professionName.equalsIgnoreCase(text)) {
                            MainApplication.coborrowerValue23 = professionID = coborrowerProfessionFinancePOJOArrayList.get(i).professionID;
                            Log.e("I_________D", "professionID: " + professionID);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /** END SPINNER CLICK **/

        checkBoxSameasAbove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBoxSameasAbove.isChecked()){
                    permanentaddress.setText(currentaddress.getText().toString());
                    permanentpincode.setText(currentpincode.getText().toString());

                    try {
                        spinnerPermanentCountry.setSelection(Integer.parseInt(currentcountryID));
                        spinnerPermanentState.setSelection(Integer.parseInt(currentstateID));
                        spinnerPermanentCity.setSelection(Integer.parseInt(currentcityID));

                        try {
                            spinnerPermanentCountry.setEnabled(false);
                            spinnerPermanentState.setEnabled(false);
                            spinnerPermanentCity.setEnabled(false);
                            permanentaddress.setEnabled(false);
                            permanentpincode.setEnabled(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                }else {
                    permanentaddress.setText("");
                    permanentpincode.setText("");

                    try {
                        spinnerPermanentCountry.setSelection(0);
                        spinnerPermanentState.setSelection(0);
                        spinnerPermanentCity.setSelection(0);

                        try {
                            spinnerPermanentCountry.setEnabled(true);
                            spinnerPermanentState.setEnabled(true);
                            spinnerPermanentCity.setEnabled(true);
                            permanentaddress.setEnabled(true);
                            permanentpincode.setEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /** API CALL **/
        try {
            progressBar.setVisibility(View.VISIBLE);
            String url = MainApplication.mainUrl + "algo/getCoBorrowerLoanDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("logged_id",userID);//2212
            if(!Globle.isNetworkAvailable(context))
            {
                Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "coBorrowerLoanDetails", params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();

        getCurrentStatesCo(jsonObject);
        getCurrentCitiesCo(jsonObject);
       // getPermanentStatesCo(jsonObject);
       // getPermanentCitiesCo(jsonObject);

        /** END OF API CALL **/

        return view;
    }

    private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick();
            // to open the spinner list if error is found.

        }
    }

    private void cityApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getCities";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", currentcountryID);
            params.put("stateId", currentstateID);
            if(!Globle.isNetworkAvailable(context))
            {
                Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getCurrentCityCo", params);
            }
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
            if(!Globle.isNetworkAvailable(context))
            {
                Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getCurrentStatesCo", params);
            }
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
            if(!Globle.isNetworkAvailable(context))
            {
                Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getPermanentCityCo", params);
            }
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
            if(!Globle.isNetworkAvailable(context))
            {
                Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getPermanentStatesCo", params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setViews() {
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_applylona_coborrower);
        linearLayoutLeftOffcoBorrower = (LinearLayout) view.findViewById(R.id.linearLayout_leftoff);
        textView1 = (TextView) view.findViewById(R.id.textView1_l2);
        mainApplication.applyTypeface(textView1, context);
        textView2 = (TextView) view.findViewById(R.id.textView2_l2);
        if(coBorrowerBackground.equalsIgnoreCase("1")){
            textView2.setBackground(getResources().getDrawable(R.drawable.background_capsule_primarydark));
            linearLayoutLeftOffcoBorrower.setBackground(getResources().getDrawable(R.drawable.background_capsule_primarydark));
        }
        mainApplication.applyTypefaceBold(textView2, context);
        textView3 = (TextView) view.findViewById(R.id.textView3_l2);
        mainApplication.applyTypeface(textView3, context);

        buttonNext = (Button) view.findViewById(R.id.button_next_loanappfragment2);
        buttonNext.setTypeface(typefaceFontBold);

        buttonPrevious = (Button) view.findViewById(R.id.button_previous_loanappfragment2);
        buttonPrevious.setTypeface(typefaceFontBold);

        lable = (TextView) view.findViewById(R.id.lable_fqform);
        textViewbirthday = (TextView) view.findViewById(R.id.userInfoEdit_birthdatecoborrower);
        birthdaycalender = (TextView) view.findViewById(R.id.birthdayCalender_fqform);
        birthdaycalender.setTypeface(typeface);

        /** PERSONAL DETAILS **/
        fname = (EditText) view.findViewById(R.id.input_coborrowerfirstname);
        lname = (EditText) view.findViewById(R.id.input_coborrowerlastname);
        adhaarno = (EditText) view.findViewById(R.id.input_adhaar_coborrower);
        panno = (EditText) view.findViewById(R.id.input_pan_coborrower);
        currentaddress = (EditText) view.findViewById(R.id.input_coborrowercurrentaddress);
        permanentaddress = (EditText) view.findViewById(R.id.input_coborrowerpermanentaddress);
        currentpincode = (EditText) view.findViewById(R.id.input_coborrowercurrentpincode);
        contactno = (EditText) view.findViewById(R.id.input_coborrowercontactno);
        emailid = (EditText) view.findViewById(R.id.input_coborroweremailid);
        monthlyrent = (EditText) view.findViewById(R.id.input_coborrowerrent);
        previousemi = (EditText) view.findViewById(R.id.input_previousemiamount_coborrower);
        permanentpincode = (EditText) view.findViewById(R.id.input_coborrowerpermanentpincode);
        radioButtonMarried = (RadioButton) view.findViewById(R.id.input_coborrowermarriedRadioButton);
        radioButtonSingle = (RadioButton) view.findViewById(R.id.input_coborrowersingleRadioButton);
        radioGroupMaritialStatus = (RadioGroup) view.findViewById(R.id.radioGroup_coborrower_maritialstatus);

        radioGroupEmployerType = (RadioGroup) view.findViewById(R.id.radiogroup_emptype_coborrower);
        radioGroupPreviousEmi = (RadioGroup) view.findViewById(R.id.radiogroup_hasanypreviousemi_coborrower);
        radioGroupGender = (RadioGroup) view.findViewById(R.id.radioGroup_coborrower_gender);
        radioButtonPreviousEmiYes = (RadioButton) view.findViewById(R.id.radiobutton_emiyes_coborrower);
        radioButtonPreviousEmiNo = (RadioButton) view.findViewById(R.id.radiobutton_emino_coborrower);

        radioButtonMale = (RadioButton) view.findViewById(R.id.radioButton_male_coborrower);
        radioButtonFemale = (RadioButton) view.findViewById(R.id.radioButton_female_coborrower);

        radioButtonGovernment = (RadioButton) view.findViewById(R.id.radiobutton_empType_gov_coborrower);
        radioButtonPrivate = (RadioButton) view.findViewById(R.id.radiobutton_empType_private_coborrower);

        spinnerCurrentResidenceType = (Spinner) view.findViewById(R.id.spinner_coborrowercurrentresidencytype);
        spinnerCurrentCity = (Spinner) view.findViewById(R.id.spinner_coborrowercurrentcity);
        spinnerCurrentCountry = (Spinner) view.findViewById(R.id.spinner_coborrowercurrentcountry);
        spinnerCurrentState = (Spinner) view.findViewById(R.id.spinner_coborrowercurrentstate);
        spinnerPermanentCity = (Spinner) view.findViewById(R.id.spinner_coborrowerpermanentcity);
        spinnerPermanentState = (Spinner) view.findViewById(R.id.spinner_coborrowerpermanentstate);
        spinnerPermanentCountry = (Spinner) view.findViewById(R.id.spinner_coborrowerpermanentcountry);
        spinnerCurrentResidenceDuration = (Spinner) view.findViewById(R.id.spinner_coborrowerdurationofstayatcurrentaddress);
        spinnerRelationshipwithBorrower = (Spinner) view.findViewById(R.id.spinner_coborrowerrelationshipwithborrower);

        /** FINANCIAL DETAILS**/
        spinnerJobDuration = (Spinner) view.findViewById(R.id.spinner_durationofjob_coborrower);
        spinnerProfession = (Spinner) view.findViewById(R.id.spinner_profession_coborrower);
        anuualincome = (EditText) view.findViewById(R.id.input_annualincome_coborrower);
        employeer = (EditText) view.findViewById(R.id.input_employeer_coborrower);

        input_previousamt = (TextInputLayout) view.findViewById(R.id.input_previousamt);

        checkBoxSameasAbove = view.findViewById(R.id.checkBox_sameasabove_fqpersonal_coborrower);

//        checkBoxSameasAbove.setEnabled(false);
//        checkBoxSameasAbove.setClickable(false);
    }

    public static String dateFormateSystem(String date) {
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

    /** RESPONSE OF API CALL **/
    public void coBorrowerLoanDetails(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "borrowerPersonal" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONArray jsonArray1 = jsonObject.getJSONArray("addressType");
                currentResidencetype_arrayList = new ArrayList<>();
                coborrowerCurrentResidenceTypePersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray1.length(); i++) {
                    CoborrowerCurrentResidenceTypePersonalPOJO currentResidenceTypePersonalPOJO = new CoborrowerCurrentResidenceTypePersonalPOJO();
                    JSONObject mJsonti = jsonArray1.getJSONObject(i);
                    currentResidenceTypePersonalPOJO.residencetypeName = mJsonti.getString("name");
                    currentResidencetype_arrayList.add(mJsonti.getString("name"));
                    currentResidenceTypePersonalPOJO.dresidencetypeID = mJsonti.getString("id");
                    coborrowerCurrentResidenceTypePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);
                    Log.e(MainApplication.TAG, "currentResidenceTypePersonalPOJO Spiner DATA:----------------- " + currentResidenceTypePersonalPOJO.residencetypeName);
                }
                arrayAdapter_currentResidencetype = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentResidencetype_arrayList);
                spinnerCurrentResidenceType.setAdapter(arrayAdapter_currentResidencetype);
                arrayAdapter_currentResidencetype.notifyDataSetChanged();

                JSONArray jsonArray5 = jsonObject.getJSONArray("currentResidence");
                currentresidenceduration_arrayList = new ArrayList<>();
                coborrowerCurrentResidenceDurationPersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray5.length(); i++) {
                    CoborrowerCurrentResidenceDurationPersonalPOJO borrowerCurrentResidenceDurationPersonalPOJO = new CoborrowerCurrentResidenceDurationPersonalPOJO();
                    JSONObject mJsonti = jsonArray5.getJSONObject(i);
                    borrowerCurrentResidenceDurationPersonalPOJO.durationName = mJsonti.getString("name");
                    currentresidenceduration_arrayList.add(mJsonti.getString("name"));
                    borrowerCurrentResidenceDurationPersonalPOJO.durationID = mJsonti.getString("id");
                    coborrowerCurrentResidenceDurationPersonalPOJOArrayList.add(borrowerCurrentResidenceDurationPersonalPOJO);
                    Log.e(MainApplication.TAG, "currentResidenceTypePersonalPOJO Spiner DATA:----------------- " + borrowerCurrentResidenceDurationPersonalPOJO.durationName);
                }
                arrayAdapter_currentResidenceDuration = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentresidenceduration_arrayList);
                spinnerCurrentResidenceDuration.setAdapter(arrayAdapter_currentResidenceDuration);
                arrayAdapter_currentResidenceDuration.notifyDataSetChanged();


                JSONArray jsonArray4 = jsonObject.getJSONArray("country");
                currentCountry_arrayList = new ArrayList<>();
                coborrowerCurrentCountryPersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray4.length(); i++) {
                    CoborrowerCurrentCountryPersonalPOJO borrowerCurrentCountryPersonalPOJO = new CoborrowerCurrentCountryPersonalPOJO();
                    JSONObject mJsonti = jsonArray4.getJSONObject(i);
                    borrowerCurrentCountryPersonalPOJO.countryName = mJsonti.getString("country_name");
                    currentCountry_arrayList.add(mJsonti.getString("country_name"));
                    borrowerCurrentCountryPersonalPOJO.countryID = mJsonti.getString("country_id");
                    coborrowerCurrentCountryPersonalPOJOArrayList.add(borrowerCurrentCountryPersonalPOJO);
                    Log.e(MainApplication.TAG, "borrowerCurrentCountryPersonalPOJO Spiner DATA:----------------- " + borrowerCurrentCountryPersonalPOJO.countryName);
                }
                arrayAdapter_currentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
                spinnerCurrentCountry.setAdapter(arrayAdapter_currentCountry);
                arrayAdapter_currentCountry.notifyDataSetChanged();
                arrayAdapter_permanentCountry= new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
                spinnerPermanentCountry.setAdapter(arrayAdapter_permanentCountry);
                arrayAdapter_permanentCountry.notifyDataSetChanged();

                JSONArray jsonArray6 = jsonObject.getJSONArray("coborrower_relationship");
                relationshipwithborrower_arrayList = new ArrayList<>();
                relationshipwithBorrowerPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray6.length(); i++) {
                    RelationshipwithBorrowerPOJO relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
                    JSONObject mJsonti = jsonArray6.getJSONObject(i);
                    relationshipwithBorrowerPOJO.relationName = mJsonti.getString("name");
                    relationshipwithborrower_arrayList.add(mJsonti.getString("name"));
                    relationshipwithBorrowerPOJO.relationID = mJsonti.getString("id");
                    relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
                }
                arrayAdapter_relationshipwithborrower = new ArrayAdapter(context, R.layout.custom_layout_spinner, relationshipwithborrower_arrayList);
                spinnerRelationshipwithBorrower.setAdapter(arrayAdapter_relationshipwithborrower);
                arrayAdapter_relationshipwithborrower.notifyDataSetChanged();


//                JSONArray jsonArray2 = jsonObject.getJSONArray("currentCities");
//                currentcity_arrayList = new ArrayList<>();
//                coborrowerCurrentCityPersonalPOJOArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray2.length(); i++) {
//                    CoborrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new CoborrowerCurrentCityPersonalPOJO();
//                    JSONObject mJsonti = jsonArray2.getJSONObject(i);
//                    borrowerCurrentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
//                    currentcity_arrayList.add(mJsonti.getString("city_name"));
//                    borrowerCurrentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
//                    coborrowerCurrentCityPersonalPOJOArrayList.add(borrowerCurrentCityPersonalPOJO);
//                }
//                arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
//                spinnerCurrentCity.setAdapter(arrayAdapter_currentCity);
//                arrayAdapter_currentCity.notifyDataSetChanged();
//
//                JSONArray jsonArray3 = jsonObject.getJSONArray("currentStates");
//                currentstate_arrayList = new ArrayList<>();
//                coborrowerCurrentStatePersonalPOJOArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray3.length(); i++) {
//                    CoborrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new CoborrowerCurrentStatePersonalPOJO();
//                    JSONObject mJsonti = jsonArray3.getJSONObject(i);
//                    borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
//                    currentstate_arrayList.add(mJsonti.getString("state_name"));
//                    borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
//                    coborrowerCurrentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
//                }
//                arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
//                spinnerCurrentState.setAdapter(arrayAdapter_currentState);
//                arrayAdapter_currentState.notifyDataSetChanged();
//
//
//
//
//                JSONArray jsonArray9 = jsonObject.getJSONArray("permanentCities");
//                currentcity_arrayList = new ArrayList<>();
//                coborrowerCurrentCityPersonalPOJOArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray9.length(); i++) {
//                    CoborrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new CoborrowerCurrentCityPersonalPOJO();
//                    JSONObject mJsonti = jsonArray9.getJSONObject(i);
//                    borrowerCurrentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
//                    currentcity_arrayList.add(mJsonti.getString("city_name"));
//                    borrowerCurrentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
//                    coborrowerCurrentCityPersonalPOJOArrayList.add(borrowerCurrentCityPersonalPOJO);
//                }
//                arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
//                spinnerPermanentCity.setAdapter(arrayAdapter_permanentCity);
//                arrayAdapter_permanentCity.notifyDataSetChanged();
//
//                JSONArray jsonArray10 = jsonObject.getJSONArray("permanentStates");
//                currentstate_arrayList = new ArrayList<>();
//                coborrowerCurrentStatePersonalPOJOArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray10.length(); i++) {
//                    CoborrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new CoborrowerCurrentStatePersonalPOJO();
//                    JSONObject mJsonti = jsonArray10.getJSONObject(i);
//                    borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
//                    currentstate_arrayList.add(mJsonti.getString("state_name"));
//                    borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
//                    coborrowerCurrentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
//                }
//                arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
//                spinnerPermanentState.setAdapter(arrayAdapter_permanentState);
//                arrayAdapter_permanentState.notifyDataSetChanged();


                JSONObject jsonObject1 = jsonObject.getJSONObject("coBorrowerDetails");
                String previous_emi = jsonObject1.getString("coborrower_previous_emi_amount");
                String currentadd = jsonObject1.getString("coborrower_current_address");
                String coborrower_gender = jsonObject1.getString("coborrower_gender_id");
                String currentPincode = jsonObject1.getString("coborrower_current_pincode");
                String permanentadd = jsonObject1.getString("coborrower_permanent_address");
                String permanentpin = jsonObject1.getString("coborrower_permanent_pincode");
                String firstname = jsonObject1.getString("coborrower_first_name");
                String lastname = jsonObject1.getString("coborrower_last_name");
                String dob = jsonObject1.getString("coborrower_dob");
                String panNo = jsonObject1.getString("coborrower_pan_no");
                String aadhaarno = jsonObject1.getString("coborrower_aadhar_no");
                String contactNo = jsonObject1.getString("coborrower_mobile");
                String email = jsonObject1.getString("coborrower_email");
                String maritialstatus = jsonObject1.getString("coborrower_is_married");
                String monthlyRent = jsonObject1.getString("coborrower_monthly_rent");
                String previousEmi = jsonObject1.getString("coborrower_has_any_extra_loan");


                currentResidencetypeID = jsonObject1.getString("coborrower_address_type");
                currentcityID = jsonObject1.getString("coborrower_current_city");
                currentstateID = jsonObject1.getString("coborrower_current_state");
                if(jsonObject1.getString("coborrower_current_country").equals("0")) {
                    currentcountryID = "1";
                }
                else if(jsonObject1.getString("coborrower_current_country").equals("")) {
                    currentcountryID = "1";
                }
                else {
                    currentcountryID = jsonObject1.getString("coborrower_current_country");
                }
//                currentcountryID = jsonObject1.getString("coborrower_current_country");
                if(jsonObject1.getString("coborrower_permanent_country").equals("0")) {
                    permanentCountryID = "1";
                }
                else if(jsonObject1.getString("coborrower_permanent_country").equals("")) {
                    permanentCountryID = "1";
                }
                else {
                    permanentCountryID = jsonObject1.getString("coborrower_permanent_country");
                }
//                permanentCountryID = jsonObject1.getString("coborrower_permanent_country");
                permanentcityID= jsonObject1.getString("coborrower_permanent_city");
                permanentstateID = jsonObject1.getString("coborrower_permanent_state");
                currentresidenceDurationID = jsonObject1.getString("coborrower_living_since");
                relationshipwithborrowerID = jsonObject1.getString("coborrower_relationship");

                if (coborrower_gender.equalsIgnoreCase("1")) {
                    radioButtonMale.setChecked(true);
                } else if (coborrower_gender.equalsIgnoreCase("2")) {
                    radioButtonFemale.setChecked(true);
                }

                int counts = coborrowerCurrentResidenceTypePersonalPOJOArrayList.size();

                for (int i = 0; i < counts; i++) {
                    if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase(currentResidencetypeID)) {

                        if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("1")) {
                            monthlyrent.setVisibility(View.GONE);
                            monthlyrent.setText("");
                            spinnerCurrentResidenceType.setSelection(i);
                        } else if(coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("3")){
                            monthlyrent.setVisibility(View.GONE);
                            monthlyrent.setText("");
                            spinnerCurrentResidenceType.setSelection(i);
                        }else if(coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("8")){
                            monthlyrent.setVisibility(View.GONE);
                            monthlyrent.setText("");
                            spinnerCurrentResidenceType.setSelection(i);
                        } else {
                            monthlyrent.setVisibility(View.VISIBLE);
                            spinnerCurrentResidenceType.setSelection(i);
                        }
                    }
                }
//                if (currentcityID.equals("")) {
//                    spinnerCurrentCity.setSelection(0);
//                } else {
//                    spinnerCurrentCity.setSelection(Integer.parseInt(currentcityID));
//                }
//                if (currentstateID.equals("")) {
//                    spinnerCurrentState.setSelection(0);
//                } else {
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
                    spinnerPermanentCountry.setSelection(1);
                } else {
                    spinnerPermanentCountry.setSelection(Integer.parseInt(permanentCountryID));
                }

                if (relationshipwithborrowerID.equals("")) {
                    spinnerRelationshipwithBorrower.setSelection(0);
                } else {
                    spinnerRelationshipwithBorrower.setSelection(Integer.parseInt(relationshipwithborrowerID));
                }

                if (currentresidenceDurationID.equals("")) {
                    spinnerCurrentResidenceDuration.setSelection(0);
                } else {
                    spinnerCurrentResidenceDuration.setSelection(Integer.parseInt(currentresidenceDurationID));
                }

                if (previousEmi.equalsIgnoreCase("1")) {
                    radioButtonPreviousEmiYes.setChecked(true);
                } else if (previousEmi.equalsIgnoreCase("0")) {
                    radioButtonPreviousEmiNo.setChecked(true);
                }


                fname.setText(firstname);
                lname.setText(lastname);
                adhaarno.setText(aadhaarno);
                panno.setText(panNo);
                currentaddress.setText(currentadd);
               // currentpincode.setText(currentPincode);
                if(currentPincode.equals("0")) {
                    currentpincode.setText("");
                }
                else
                {
                    currentpincode.setText(currentPincode);

                }
                permanentaddress.setText(permanentadd);
               // permanentpincode.setText(permanentpin);
                if(permanentpin.equals("0")) {
                    permanentpincode.setText("");
                }
                else
                {
                    permanentpincode.setText(permanentpin);

                }
                emailid.setText(email);
                contactno.setText(contactNo);
                monthlyrent.setText(monthlyRent);

                if(!dob.equalsIgnoreCase("") || !dob.isEmpty() )
                {
                    textViewbirthday.setText(dob);
                    textViewbirthday.setTextColor(Color.BLACK);
                    lable.setVisibility(View.VISIBLE);
                } else {
                    textViewbirthday.setText("Birthdate");
//                    textViewbirthday.setTextColor(808080);
                }

                if(maritialstatus.equalsIgnoreCase("1")){
                    radioButtonMarried.setChecked(true);
                }else if(maritialstatus.equalsIgnoreCase("2")){
                    radioButtonSingle.setChecked(true);
                }

                /** FINANCIAL DETAILS**/
                JSONArray jsonArray7 = jsonObject.getJSONArray("jobDuration");

                jobduration_arratList = new ArrayList<>();
                coborrowerJobDurationFinancePOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray7.length(); i++) {
                    CoborrowerJobDurationFinancePOJO coborrowerJobDurationFinancePOJO = new CoborrowerJobDurationFinancePOJO();
                    JSONObject mJsonti = jsonArray7.getJSONObject(i);
                    coborrowerJobDurationFinancePOJO.durationName = mJsonti.getString("name");
                    jobduration_arratList.add(mJsonti.getString("name"));
                    coborrowerJobDurationFinancePOJO.durationID = mJsonti.getString("id");
                    coborrowerJobDurationFinancePOJOArrayList.add(coborrowerJobDurationFinancePOJO);
                }
                jobduration_arrayAdapter = new ArrayAdapter(context, R.layout.custom_layout_spinner, jobduration_arratList);
                spinnerJobDuration.setAdapter(jobduration_arrayAdapter);
                jobduration_arrayAdapter.notifyDataSetChanged();

                JSONArray jsonArray8 = jsonObject.getJSONArray("coBorrowerProfession");
                professionfinance_arratList = new ArrayList<>();
                coborrowerProfessionFinancePOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray8.length(); i++) {
                    CoborrowerProfessionFinancePOJO coborrowerProfessionFinancePOJO = new CoborrowerProfessionFinancePOJO();
                    JSONObject mJsonti = jsonArray8.getJSONObject(i);
                    coborrowerProfessionFinancePOJO.professionName = mJsonti.getString("name");
                    professionfinance_arratList.add(mJsonti.getString("name"));
                    coborrowerProfessionFinancePOJO.professionID = mJsonti.getString("id");
                    coborrowerProfessionFinancePOJOArrayList.add(coborrowerProfessionFinancePOJO);
                    Log.e(MainApplication.TAG, "coborrowerProfessionFinancePOJO Spiner DATA:----------------- " + coborrowerProfessionFinancePOJO.professionName);
                }
                profession_arrayAdapter = new ArrayAdapter(context, R.layout.custom_layout_spinner, professionfinance_arratList);
                spinnerProfession.setAdapter(profession_arrayAdapter);
                profession_arrayAdapter.notifyDataSetChanged();

                JSONObject jsonObject6 = jsonObject.getJSONObject("coBorrowerFinancialDetails");
                String organization = jsonObject6.getString("coborrower_organization");
                String anualIncome  = jsonObject6.getString("coborrower_income");
                professionID = jsonObject6.getString("coborrower_profession");
                jobDurationID = jsonObject6.getString("coborrower_working_organization_since");
                String employerType = jsonObject6.getString("coborrower_employer_type");

                anuualincome.setText(anualIncome);
                employeer.setText(organization);
                previousemi.setText(previous_emi);

                if (permanentCountryID.equals("")) {
                    spinnerPermanentCountry.setSelection(0);
                } else {
                    spinnerPermanentCountry.setSelection(Integer.parseInt(permanentCountryID));
                }
                if (permanentCountryID.equals("")) {
                    spinnerPermanentCountry.setSelection(0);
                } else {
                    spinnerPermanentCountry.setSelection(Integer.parseInt(permanentCountryID));
                }
                if (jobDurationID.equals("")) {
                    spinnerJobDuration.setSelection(0);
                } else {
                    spinnerJobDuration.setSelection(Integer.parseInt(jobDurationID));
                }
                if (professionID.equals("")) {
                    spinnerProfession.setSelection(0);
                } else {
                    int count = coborrowerProfessionFinancePOJOArrayList.size();
                    Log.e("TAG", "count: "+count );
                    for (int i = 0; i < count; i++) {
                        if (coborrowerProfessionFinancePOJOArrayList.get(i).professionID.equalsIgnoreCase(professionID)) {
                            spinnerProfession.setSelection(i);
                        }
                    }

                }

                if (employerType.equalsIgnoreCase("1")) {
                    radioButtonPrivate.setChecked(true);
                } else if (employerType.equalsIgnoreCase("0")) {
                    radioButtonGovernment.setChecked(true);
                }

                MainApplication.coborrowerValue1 = currentResidencetypeID;
                MainApplication.coborrowerValue2 = monthlyrent.getText().toString();
                MainApplication.coborrowerValue3 = currentaddress.getText().toString();
                MainApplication.coborrowerValue4 = currentcityID;
                MainApplication.coborrowerValue5 = currentstateID;
                MainApplication.coborrowerValue6 = currentcountryID;
                MainApplication.coborrowerValue7 = currentpincode.getText().toString();
                MainApplication.coborrowerValue8 = permanentaddress.getText().toString();
                MainApplication.coborrowerValue9 = permanentcityID;
                MainApplication.coborrowerValue10 = permanentstateID;
                MainApplication.coborrowerValue11 = permanentCountryID;
                MainApplication.coborrowerValue12 = permanentpincode.getText().toString();
                MainApplication.coborrowerValue13 = fname.getText().toString();
                MainApplication.coborrowerValue14 = lname.getText().toString();
                MainApplication.coborrowerValue15 = textViewbirthday.getText().toString();
                MainApplication.coborrowerValue16 = maritialstatus;
                MainApplication.coborrowerValue17 = panno.getText().toString();
                MainApplication.coborrowerValue18 = adhaarno.getText().toString();

                MainApplication.coborrowerValue19 = emailid.getText().toString();
                MainApplication.coborrowerValue20 = contactno.getText().toString();
                MainApplication.coborrowerValue21 = currentresidenceDurationID;
                MainApplication.coborrowerValue22 = relationshipwithborrowerID;
                MainApplication.coborrowerValue23 = professionID;
                MainApplication.coborrowerValue24 = anuualincome.getText().toString();
                MainApplication.coborrowerValue25 = employeer.getText().toString();
                MainApplication.coborrowerValue26 = jobDurationID;
                MainApplication.coborrowerValue28 = previousemi.getText().toString();
                MainApplication.coborrowerValue29 = coborrower_gender;
                MainApplication.coborrowerValue30 = employerType;

                progressBar.setVisibility(View.GONE);
            }else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCoborrowerPersonal(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendCoborrowerPersonal" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                progressBar.setVisibility(View.GONE);

                JSONObject jsonObject = jsonData.getJSONObject("result");
                int docupload = jsonObject.getInt("docUpload");

                if(docupload == 1){
                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("coBorrowerBackground_dark", "1");
                    Log.e(TAG, "onCreateView:++++++++ sharedPreferences"+coBorrowerBackground );
                    editor.apply();
                    editor.commit();

                    LoanApplicationFragment_3 loanApplicationFragment_3 = new LoanApplicationFragment_3();
                    transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_3).commit();
                }else if(docupload == 0){
                    Toast.makeText(context, "Please Complete Borrower & Co Borrower Forms", Toast.LENGTH_SHORT).show();
                }

            }else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }

    public void getCurrentStatesCo(JSONObject jsonData) {
        try {

            if(jsonData.toString().equals("{}"))
            {
                try {
                    currentstate_arrayList = new ArrayList<>();
                    currentstate_arrayList.add("Select Any");
                    arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                    spinnerCurrentState.setAdapter(arrayAdapter_currentState);
                    arrayAdapter_currentState.notifyDataSetChanged();
                    spinnerCurrentState.setSelection(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else {
                Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray3 = jsonObject.getJSONArray("states");
                    currentstate_arrayList = new ArrayList<>();
                    coborrowerCurrentStatePersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        CoborrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new CoborrowerCurrentStatePersonalPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                        currentstate_arrayList.add(mJsonti.getString("state_name"));
                        borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                        coborrowerCurrentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
                    }
                    arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                    spinnerCurrentState.setAdapter(arrayAdapter_currentState);
                    arrayAdapter_currentState.notifyDataSetChanged();

                    int count = coborrowerCurrentStatePersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (coborrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
                            spinnerCurrentState.setSelection(i);
                        }
                    }

                } else {
                }
            }
        } catch (Exception e) {

        }
    }

    public void getCurrentCitiesCo(JSONObject jsonData) {
        try {

            if(jsonData.toString().equals("{}"))
            {
                try {
                    currentcity_arrayList = new ArrayList<>();
                    currentcity_arrayList.add("Select Any");
                    arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
                    spinnerCurrentCity.setAdapter(arrayAdapter_currentCity);
                    arrayAdapter_currentCity.notifyDataSetChanged();
                    spinnerCurrentCity.setSelection(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
           else {

                Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray2 = jsonObject.getJSONArray("cities");
                    currentcity_arrayList = new ArrayList<>();
                    coborrowerCurrentCityPersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        CoborrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new CoborrowerCurrentCityPersonalPOJO();
                        JSONObject mJsonti = jsonArray2.getJSONObject(i);
                        borrowerCurrentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
                        currentcity_arrayList.add(mJsonti.getString("city_name"));
                        borrowerCurrentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
                        coborrowerCurrentCityPersonalPOJOArrayList.add(borrowerCurrentCityPersonalPOJO);
                    }
                    arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
                    spinnerCurrentCity.setAdapter(arrayAdapter_currentCity);
                    arrayAdapter_currentCity.notifyDataSetChanged();

                    int count = coborrowerCurrentCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (coborrowerCurrentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                            spinnerCurrentCity.setSelection(i);
                        }
                    }

                } else {
                }
            }

        }
        catch (Exception e) {

        }
    }

    public void getPermanentStatesCo(JSONObject jsonData) {
        try {
            if(jsonData.toString().equals("{}"))
            {
                try {
                    currentstate_arrayList = new ArrayList<>();
                    currentstate_arrayList.add("Select Any");
                    arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                    spinnerPermanentState.setAdapter(arrayAdapter_permanentState);
                    arrayAdapter_permanentState.notifyDataSetChanged();
                    spinnerPermanentState.setSelection(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else {
                Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray3 = jsonObject.getJSONArray("states");
                    currentstate_arrayList = new ArrayList<>();
                    coborrowerPermanentStatePersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        CoborrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new CoborrowerCurrentStatePersonalPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                        currentstate_arrayList.add(mJsonti.getString("state_name"));
                        borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                        coborrowerPermanentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
                    }
                    arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                    spinnerPermanentState.setAdapter(arrayAdapter_permanentState);
                    arrayAdapter_permanentState.notifyDataSetChanged();


                    int count = coborrowerPermanentStatePersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (coborrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(permanentstateID)) {
                            spinnerPermanentState.setSelection(i);
                        }
                    }

                } else {
                }
            }
        } catch (Exception e) {

        }
    }

    public void getPermanentCitiesCo(JSONObject jsonData) {
        try {

            if(jsonData.toString().equals("{}"))
            {
                try {
                    permanentcity_arrayList = new ArrayList<>();
                    permanentcity_arrayList.add("Select Any");
                    arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayList);
                    spinnerPermanentCity.setAdapter(arrayAdapter_permanentCity);
                    arrayAdapter_permanentCity.notifyDataSetChanged();
                    spinnerPermanentCity.setSelection(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray2 = jsonObject.getJSONArray("cities");
                    permanentcity_arrayList = new ArrayList<>();
                    coborrowerPermanentCityPersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        CoborrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new CoborrowerCurrentCityPersonalPOJO();
                        JSONObject mJsonti = jsonArray2.getJSONObject(i);
                        borrowerCurrentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
                        permanentcity_arrayList.add(mJsonti.getString("city_name"));
                        borrowerCurrentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
                        coborrowerPermanentCityPersonalPOJOArrayList.add(borrowerCurrentCityPersonalPOJO);
                    }
                    arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayList);
                    spinnerPermanentCity.setAdapter(arrayAdapter_permanentCity);
                    arrayAdapter_permanentCity.notifyDataSetChanged();

                    Log.e(TAG, "999999999: " + permanentcityID);

                    int count = coborrowerPermanentCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (coborrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityID)) {
                            spinnerPermanentCity.setSelection(i);
                        }
                    }

                } else {
                }
            }
        } catch (Exception e) {

        }
    }

}
