package com.eduvanzapplication.fqform.borrowerdetail;


import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceDurationPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceTypePersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentStatePersonalPOJO;
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
public class BorrowerPersonalFragment extends Fragment {

    public static EditText fname, lname, adhaarno, panno, monthlyrent, currentaddress, currentpincode, permanentaddress, permanentpincode;
    public String dateformate = "";
    public static Context context;
    public static Button buttonSubmit;
    public static TextView birthdaycalender, lable, textViewbirthday;
    Typeface typeface;
    Calendar cal;
    Fragment mFragment;
    String userID="";
    public static RadioButton radioButtonMarried, radioButtonSingle;
    private String[] arraySpinner;
    public static Spinner spinnerPermanentCity, spinnerPermanentCountry, spinnerPermanentState;
    public static ArrayAdapter arrayAdapter_permanentCity, arrayAdapter_permanentState, arrayAdapter_permanentCountry;
    public String permanentcityID="", permanentstateID="", permanentCountryID;

    public static Spinner spinnerCurrentResidenceType;
    public static ArrayAdapter arrayAdapter_currentResidencetype;
    public static ArrayList<String> currentResidencetype_arrayList;
    public static ArrayList<BorrowerCurrentResidenceTypePersonalPOJO> borrowerCurrentResidencePersonalPOJOArrayList;
    public String currentResidencetypeID="";

    public static Spinner spinnerCurrentCity;
    public static ArrayAdapter arrayAdapter_currentCity;
    public static ArrayList<String> currentcity_arrayList;
    public static ArrayList<BorrowerCurrentCityPersonalPOJO> borrowerCurrentCityPersonalPOJOArrayList;
    public String currentcityID="";

    public static Spinner spinnerCurrentState;
    public static ArrayAdapter arrayAdapter_currentState;
    public static ArrayList<String> currentstate_arrayList;
    public static ArrayList<BorrowerCurrentStatePersonalPOJO> borrowerCurrentStatePersonalPOJOArrayList;
    public String currentstateID="";

    public static Spinner spinnerCurrentCountry;
    public static ArrayAdapter arrayAdapter_currentCountry;
    public static ArrayList<String> currentCountry_arrayList;
    public static ArrayList<BorrowerCurrentCountryPersonalPOJO> borrowerCurrentCountryPersonalPOJOArrayList;
    public String currentcountryID="";

    public static Spinner spinnerCurrentResidenceDuration;
    public static ArrayAdapter arrayAdapter_currentResidenceDuration;
    public static ArrayList<String> currentresidenceduration_arrayList;
    public static ArrayList<BorrowerCurrentResidenceDurationPersonalPOJO> currentResidenceDurationPersonalPOJOArrayList;
    public String currentresidenceDurationID="";

    public BorrowerPersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fq_personal_fragment, container, false);
        context = getContext();
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        mFragment = new BorrowerPersonalFragment();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);// hide the keyboard everytime the activty starts.

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("logged_id","null");

        typeface = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");

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

        //-------------------------------------SPINNER CLICK--------------------------------------//

        spinnerCurrentResidenceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerCurrentResidenceType.getSelectedItem().toString();
                int count = borrowerCurrentResidencePersonalPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).residencetypeName.equalsIgnoreCase(text)) {
                        currentResidencetypeID = borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID;
                        Log.e("I_________D", "currentResidencetypeID: " + currentResidencetypeID);
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
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                        currentcityID = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
                        Log.e("I_________D", "currentcityID: " + currentcityID);
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
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                        currentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
                        Log.e("I_________D", "currentstateID: " + currentstateID);
                    }
                }
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
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                        currentcountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                        Log.e("I_________D", "currentcountryID: " + currentcountryID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerPermanentCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerPermanentCity.getSelectedItem().toString();
                int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                        permanentcityID = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
                        Log.e("I_________D", "permanentcityID: " + permanentcityID);
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
                int count = borrowerCurrentStatePersonalPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                        permanentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
                        Log.e("I_________D", "permanentstateID: " + permanentstateID);
                    }
                }
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
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                        permanentCountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                        Log.e("I_________D", "permanentCountryID: " + permanentCountryID);
                    }
                }
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
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (currentResidenceDurationPersonalPOJOArrayList.get(i).durationName.equalsIgnoreCase(text)) {
                        currentresidenceDurationID = currentResidenceDurationPersonalPOJOArrayList.get(i).durationID;
                        Log.e("I_________D", "currentresidenceDurationID: " + currentresidenceDurationID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //----------------------------------END SPINNER CLICK-------------------------------------//

//        buttonSubmit = (Button) view.findViewById(R.id.button_submit_personalborrower);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (fname.getText().toString().equalsIgnoreCase("") || lname.getText().toString().equalsIgnoreCase("") || adhaarno.getText().toString().equalsIgnoreCase("") || panno.getText().toString().equalsIgnoreCase("")) {
//                    fname.setError("Your First Name is Required");
//                    lname.setError("Your Last Name is Required");
//                    adhaarno.setError("Adhaar Number is Required");
//                    panno.setError("PAN Number is Required");
//                } else {
//
//                }
//                transaction.replace(R.id.framelayout_fqform,new BorrowerEducationFragment()).commit();

                //-----------------------------------------API CALL---------------------------------------//
                try {
                    String maritialstatus="";
                    if(radioButtonMarried.isChecked())
                    {
                        maritialstatus = "1";
                    }else if(radioButtonSingle.isChecked()){
                        maritialstatus = "2";
                    }
                    String url = MainApplication.mainUrl + "algo/setPersonalDetails";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("logged_id",userID);
                    params.put("student_address_type",currentResidencetypeID);
                    params.put("student_monthly_rent",monthlyrent.getText().toString());
                    params.put("student_current_address",currentaddress.getText().toString());
                    params.put("student_current_city",currentcityID);
                    params.put("student_current_state",currentstateID);
                    params.put("student_current_country",currentcountryID);
                    params.put("student_current_pincode",currentpincode.getText().toString());
                    params.put("student_permanent_address",permanentaddress.getText().toString());
                    params.put("student_permanent_city",permanentcityID);
                    params.put("student_permanent_state",permanentstateID);
                    params.put("student_permanent_country",permanentCountryID);
                    params.put("student_permanent_pincode",permanentpincode.getText().toString());
                    params.put("student_first_name",fname.getText().toString());
                    params.put("student_last_name",lname.getText().toString());
                    params.put("student_dob",textViewbirthday.getText().toString());
                    params.put("student_married",maritialstatus);
                    params.put("student_pan_card_no",panno.getText().toString());
                    params.put("student_aadhar_card_no",adhaarno.getText().toString());
                    VolleyCall volleyCall = new VolleyCall();
                    volleyCall.sendRequest(context, url, null, mFragment, "setborrowerPersonal", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //-------------------------------------END OF API CALL------------------------------------//
            }
        });


//        this.arraySpinner = new String[]{
//                "Owned By Self/spouse/parents/Siblings", "Renting With Family/Friends"
//        };
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
//                android.R.layout.simple_dropdown_item_1line, arraySpinner);
//        spinnerCurrentResidence.setAdapter(adapter);

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
                textViewbirthday.setTextColor(getResources().getColor(R.color.black));
                lable.setVisibility(View.VISIBLE);
            }

        };
        cal = Calendar.getInstance();
        birthdaycalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DatePickerDialog(SignUpStepOne.this, date, cal
//                        .get(Calendar.YEAR), cal.get(Calendar.MONTH),
//                        cal.get(Calendar.DAY_OF_MONTH)).show();
                cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
//                cal.set(Calendar.MONTH, 1);
//                cal.set(Calendar.DAY_OF_MONTH, 1);
                DatePickerDialog data = new DatePickerDialog(context, date, cal
                        .get(Calendar.YEAR) - 18, 1,
                        1);
                data.getDatePicker().setMaxDate(System.currentTimeMillis() - 1234564);
                data.show();
            }
        });


        //-----------------------------------------API CALL---------------------------------------//
        try {
            String url = MainApplication.mainUrl + "algo/getPersonalDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("logged_id",userID);
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(context, url, null, mFragment, "borrowerPersonal", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-------------------------------------END OF API CALL------------------------------------//
        return view;
    }//--------------------------------------END OD ON CREATE-------------------------------------//

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

    //---------------------------------RESPONSE OF API CALL---------------------------------------//
    public void borrowerPersonal(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "borrowerPersonal" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                    Log.e(MainApplication.TAG, "currentResidenceTypePersonalPOJO Spiner DATA:----------------- " + currentResidenceTypePersonalPOJO.residencetypeName);
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
                    Log.e(MainApplication.TAG, "currentResidenceTypePersonalPOJO Spiner DATA:----------------- " + borrowerCurrentResidenceDurationPersonalPOJO.durationName);
                }
                arrayAdapter_currentResidenceDuration = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentresidenceduration_arrayList);
                spinnerCurrentResidenceDuration.setAdapter(arrayAdapter_currentResidenceDuration);
                arrayAdapter_currentResidenceDuration.notifyDataSetChanged();

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
                    Log.e(MainApplication.TAG, "borrowerCurrentCityPersonalPOJO Spiner DATA:----------------- " + borrowerCurrentCityPersonalPOJO.cityName);
                }
                arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
                spinnerCurrentCity.setAdapter(arrayAdapter_currentCity);
                arrayAdapter_currentCity.notifyDataSetChanged();
                arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
                spinnerPermanentCity.setAdapter(arrayAdapter_permanentCity);
                arrayAdapter_permanentCity.notifyDataSetChanged();

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
                    Log.e(MainApplication.TAG, "borrowerCurrentStatePersonalPOJO Spiner DATA:----------------- " + borrowerCurrentStatePersonalPOJO.stateName);
                }
                arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                spinnerCurrentState.setAdapter(arrayAdapter_currentState);
                arrayAdapter_currentState.notifyDataSetChanged();
                arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                spinnerPermanentState.setAdapter(arrayAdapter_permanentState);
                arrayAdapter_permanentState.notifyDataSetChanged();

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
                    Log.e(MainApplication.TAG, "borrowerCurrentCountryPersonalPOJO Spiner DATA:----------------- " + borrowerCurrentCountryPersonalPOJO.countryName);
                }
                arrayAdapter_currentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
                spinnerCurrentCountry.setAdapter(arrayAdapter_currentCountry);
                arrayAdapter_currentCountry.notifyDataSetChanged();
                arrayAdapter_permanentCountry= new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
                spinnerPermanentCountry.setAdapter(arrayAdapter_permanentCountry);
                arrayAdapter_permanentCountry.notifyDataSetChanged();

                JSONObject jsonObject1 = jsonObject.getJSONObject("personalDetails");
                Log.e(MainApplication.TAG, "JSONOBJECT1: "+jsonObject1 );
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
                permanentcityID= jsonObject1.getString("student_permanent_city");
                permanentstateID = jsonObject1.getString("student_permanent_state");
                permanentCountryID = jsonObject1.getString("student_permanent_country");
                currentresidenceDurationID = jsonObject1.getString("student_current_address_duration");

                spinnerCurrentResidenceType.setSelection(Integer.parseInt(currentResidencetypeID));
                spinnerCurrentCity.setSelection(Integer.parseInt(currentcityID));
                spinnerCurrentState.setSelection(Integer.parseInt(currentstateID));
                spinnerCurrentCountry.setSelection(Integer.parseInt(currentcountryID));
                spinnerPermanentCity.setSelection(Integer.parseInt(permanentcityID));
                spinnerPermanentState.setSelection(Integer.parseInt(permanentstateID));
                spinnerPermanentCountry.setSelection(Integer.parseInt(permanentCountryID));

                fname.setText(firstname);
                lname.setText(lastname);
                adhaarno.setText(aadhaarno);
                panno.setText(panNo);
                monthlyrent.setText(monthlyRent);
                currentaddress.setText(currentadd);
                currentpincode.setText(currentPincode);
                permanentpincode.setText(permanentpin);
                permanentaddress.setText(permanentadd);
                if(!dob.equalsIgnoreCase("") || !dob.isEmpty() )
                {
                    textViewbirthday.setText(dob);
                    textViewbirthday.setTextColor(Color.BLACK);
                    lable.setVisibility(View.VISIBLE);
                } else {
                    textViewbirthday.setText("Birthdate");
                    textViewbirthday.setTextColor(808080);
                }

                if(maritialstatus.equalsIgnoreCase("1")){
                    radioButtonMarried.setChecked(true);
                }else if(maritialstatus.equalsIgnoreCase("2")){
                    radioButtonSingle.setChecked(true);
                }

            }else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setborrowerPersonal(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "setborrowerPersonal" + jsonData);
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

