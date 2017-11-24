package com.eduvanz.fqform.coborrowerdetail;


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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.fqform.borrowerdetail.BorrowerEducationFragment;
import com.eduvanz.fqform.coborrowerdetail.pojo.CoborrowerCurrentCityPersonalPOJO;
import com.eduvanz.fqform.coborrowerdetail.pojo.CoborrowerCurrentCountryPersonalPOJO;
import com.eduvanz.fqform.coborrowerdetail.pojo.CoborrowerCurrentResidenceDurationPersonalPOJO;
import com.eduvanz.fqform.coborrowerdetail.pojo.CoborrowerCurrentResidenceTypePersonalPOJO;
import com.eduvanz.fqform.coborrowerdetail.pojo.CoborrowerCurrentStatePersonalPOJO;
import com.eduvanz.fqform.coborrowerdetail.pojo.RelationshipwithBorrowerPOJO;
import com.eduvanz.volley.VolleyCall;

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
public class CoBorrowerPersonalFragment extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    String userID="";
    public Button buttonSubmit;
    private String[] arraySpinner;
    public static TextView birthdaycalender, lable, textViewbirthday;
    Typeface typeface;
    Calendar cal;

    public static RadioButton radioButtonMarried, radioButtonSingle;
    public static EditText fname, lname, adhaarno, panno, currentaddress, currentpincode, permanentaddress, contactno, emailid, permanentpincode, monthlyrent;
    public String dateformate="";
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
    public static ArrayList<String> currentcity_arrayList;
    public static ArrayList<CoborrowerCurrentCityPersonalPOJO> coborrowerCurrentCityPersonalPOJOArrayList;
    public String currentcityID="";

    public static Spinner spinnerCurrentState;
    public static ArrayAdapter arrayAdapter_currentState;
    public static ArrayList<String> currentstate_arrayList;
    public static ArrayList<CoborrowerCurrentStatePersonalPOJO> coborrowerCurrentStatePersonalPOJOArrayList;
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


    public CoBorrowerPersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coborrower_personal_fragment, container, false);
        context = getContext();
        mFragment = new CoBorrowerPersonalFragment();
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("logged_id","null");

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        typeface = Typeface.createFromAsset(context.getAssets(),"fontawesome-webfont.ttf");

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
        permanentpincode = (EditText) view.findViewById(R.id.input_coborrowerpermanentpincode);
        radioButtonMarried = (RadioButton) view.findViewById(R.id.input_coborrowermarriedRadioButton);
        radioButtonSingle = (RadioButton) view.findViewById(R.id.input_coborrowersingleRadioButton);

        spinnerCurrentResidenceType = (Spinner) view.findViewById(R.id.spinner_coborrowercurrentresidencytype);
        spinnerCurrentCity = (Spinner) view.findViewById(R.id.spinner_coborrowercurrentcity);
        spinnerCurrentCountry = (Spinner) view.findViewById(R.id.spinner_coborrowercurrentcountry);
        spinnerCurrentState = (Spinner) view.findViewById(R.id.spinner_coborrowercurrentstate);
        spinnerPermanentCity = (Spinner) view.findViewById(R.id.spinner_coborrowerpermanentcity);
        spinnerPermanentState = (Spinner) view.findViewById(R.id.spinner_coborrowerpermanentstate);
        spinnerPermanentCountry = (Spinner) view.findViewById(R.id.spinner_coborrowerpermanentcountry);
        spinnerCurrentResidenceDuration = (Spinner) view.findViewById(R.id.spinner_coborrowerdurationofstayatcurrentaddress);
        spinnerRelationshipwithBorrower = (Spinner) view.findViewById(R.id.spinner_coborrowerrelationshipwithborrower);

        //-------------------------------------SPINNER CLICK--------------------------------------//

        spinnerCurrentResidenceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerCurrentResidenceType.getSelectedItem().toString();
                int count = coborrowerCurrentResidenceTypePersonalPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).residencetypeName.equalsIgnoreCase(text)) {
                        currentResidencetypeID = coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID;
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
                int count = coborrowerCurrentCityPersonalPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (coborrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                        currentcityID = coborrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
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
                int count = coborrowerCurrentStatePersonalPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (coborrowerCurrentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                        currentstateID = coborrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
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
                int count = coborrowerCurrentCountryPersonalPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (coborrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                        currentcountryID = coborrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
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
                int count = coborrowerCurrentCityPersonalPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (coborrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                        permanentcityID = coborrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
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
                int count = coborrowerCurrentStatePersonalPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (coborrowerCurrentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                        permanentstateID = coborrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
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
                int count = coborrowerCurrentCountryPersonalPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (coborrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                        permanentCountryID = coborrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
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
                int count = coborrowerCurrentResidenceDurationPersonalPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (coborrowerCurrentResidenceDurationPersonalPOJOArrayList.get(i).durationName.equalsIgnoreCase(text)) {
                        currentresidenceDurationID = coborrowerCurrentResidenceDurationPersonalPOJOArrayList.get(i).durationID;
                        Log.e("I_________D", "currentresidenceDurationID: " + currentresidenceDurationID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerRelationshipwithBorrower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerRelationshipwithBorrower.getSelectedItem().toString();
                int count = relationshipwithBorrowerPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (relationshipwithBorrowerPOJOArrayList.get(i).relationName.equalsIgnoreCase(text)) {
                        relationshipwithborrowerID = relationshipwithBorrowerPOJOArrayList.get(i).relationID;
                        Log.e("I_________D", "currentresidenceDurationID: " + relationshipwithborrowerID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //----------------------------------END SPINNER CLICK-------------------------------------//

        buttonSubmit = (Button) view.findViewById(R.id.button_submit_personalcoborrower);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    String url = MainApplication.mainUrl + "algo/setCoborrowerPersonalDetails";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("logged_id",userID);
                    params.put("coborrower_address_type",currentResidencetypeID);
                    params.put("coborrower_monthly_rent",monthlyrent.getText().toString());
                    params.put("coborrower_current_address",currentaddress.getText().toString());
                    params.put("coborrower_current_city",currentcityID);
                    params.put("coborrower_current_state",currentstateID);
                    params.put("coborrower_current_country",currentcountryID);
                    params.put("coborrower_current_pincode",currentpincode.getText().toString());
                    params.put("coborrower_permanent_address",permanentaddress.getText().toString());
                    params.put("coborrower_permanent_city",permanentcityID);
                    params.put("coborrower_permanent_state",permanentstateID);
                    params.put("coborrower_permanent_country",permanentCountryID);
                    params.put("coborrower_permanent_pincode",permanentpincode.getText().toString());
                    params.put("coborrower_first_name",fname.getText().toString());
                    params.put("coborrower_last_name",lname.getText().toString());
                    params.put("coborrower_dob",textViewbirthday.getText().toString());
                    params.put("coborrower_is_married",maritialstatus);
                    params.put("coborrower_pan_no",panno.getText().toString());
                    params.put("coborrower_aadhar_no",adhaarno.getText().toString());
                    params.put("coborrower_email",emailid.getText().toString());
                    params.put("coborrower_mobile",contactno.getText().toString());
                    params.put("coborrower_living_since",currentresidenceDurationID);
                    params.put("coborrower_relationship", relationshipwithborrowerID);
                    VolleyCall volleyCall = new VolleyCall();
                    volleyCall.sendRequest(context, url, null, mFragment, "setcoborrowerPersonal", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //-------------------------------------END OF API CALL------------------------------------//
            }
        });

//        this.arraySpinner = new String[] {
//                "Owned By Self/spouse/parents/Siblings", "Renting With Family/Friends"
//        };
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
//                android.R.layout.simple_dropdown_item_1line, arraySpinner);
//        spinnerCurrentResidence.setAdapter(adapter);

        lable = (TextView) view.findViewById(R.id.lable_fqform);
        textViewbirthday = (TextView) view.findViewById(R.id.userInfoEdit_birthdatecoborrower);
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
                int month =monthOfYear+1;
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
                DatePickerDialog data=  new DatePickerDialog(context, date, cal
                        .get(Calendar.YEAR)-18, 1,
                        1);
                data.getDatePicker().setMaxDate(System.currentTimeMillis()-1234564);
                data.show();
            }
        });

        //-----------------------------------------API CALL---------------------------------------//
        try {
            String url = MainApplication.mainUrl + "algo/getCoborrowerPersonalDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("logged_id",userID);
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(context, url, null, mFragment, "coBorrowerPersonal", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-------------------------------------END OF API CALL------------------------------------//

        return view;
    }//--------------------------------------END OF ONCREATE--------------------------------------//

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
    public void coBorrowerPersonal(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "borrowerPersonal" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                coborrowerCurrentStatePersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray3.length(); i++) {
                    CoborrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new CoborrowerCurrentStatePersonalPOJO();
                    JSONObject mJsonti = jsonArray3.getJSONObject(i);
                    borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                    currentstate_arrayList.add(mJsonti.getString("state_name"));
                    borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                    coborrowerCurrentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
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
                    Log.e(MainApplication.TAG, "relationshipwithBorrowerPOJO Spiner DATA:----------------- " + relationshipwithBorrowerPOJO.relationName);
                }
                arrayAdapter_relationshipwithborrower = new ArrayAdapter(context, R.layout.custom_layout_spinner, relationshipwithborrower_arrayList);
                spinnerRelationshipwithBorrower.setAdapter(arrayAdapter_relationshipwithborrower);
                arrayAdapter_relationshipwithborrower.notifyDataSetChanged();

                JSONObject jsonObject1 = jsonObject.getJSONObject("coBorrowerDetails");
                String currentadd = jsonObject1.getString("coborrower_current_address");
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


                        currentResidencetypeID = jsonObject1.getString("coborrower_address_type");
                currentcityID = jsonObject1.getString("coborrower_current_city");
                currentstateID = jsonObject1.getString("coborrower_current_state");
                currentcountryID = jsonObject1.getString("coborrower_current_country");
                permanentcityID= jsonObject1.getString("coborrower_permanent_city");
                permanentstateID = jsonObject1.getString("coborrower_permanent_state");
                permanentCountryID = jsonObject1.getString("coborrower_permanent_country");
                currentresidenceDurationID = jsonObject1.getString("coborrower_living_since");
                relationshipwithborrowerID = jsonObject1.getString("coborrower_relationship");

                spinnerCurrentResidenceType.setSelection(Integer.parseInt(currentResidencetypeID));
                spinnerCurrentCity.setSelection(Integer.parseInt(currentcityID));
                spinnerCurrentState.setSelection(Integer.parseInt(currentstateID));
                spinnerCurrentCountry.setSelection(Integer.parseInt(currentcountryID));
                spinnerPermanentCity.setSelection(Integer.parseInt(permanentcityID));
                spinnerPermanentState.setSelection(Integer.parseInt(permanentstateID));
                spinnerPermanentCountry.setSelection(Integer.parseInt(permanentCountryID));
                spinnerCurrentResidenceDuration.setSelection(Integer.parseInt(currentresidenceDurationID));
                spinnerRelationshipwithBorrower.setSelection(Integer.parseInt(relationshipwithborrowerID));

                fname.setText(firstname);
                lname.setText(lastname);
                adhaarno.setText(aadhaarno);
                panno.setText(panNo);
                currentaddress.setText(currentadd);
                currentpincode.setText(currentPincode);
                permanentaddress.setText(permanentadd);
                permanentpincode.setText(permanentpin);
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

    public void setcoborrowerPersonal(JSONObject jsonData) {
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
