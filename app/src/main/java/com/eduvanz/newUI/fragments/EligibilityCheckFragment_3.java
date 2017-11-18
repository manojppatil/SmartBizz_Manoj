package com.eduvanz.newUI.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.pqformfragments.pojo.LocationsPOJO;
import com.eduvanz.pqformfragments.pojo.NameOfCoursePOJO;
import com.eduvanz.pqformfragments.pojo.NameOfInsitituePOJO;
import com.eduvanz.volley.VolleyCall;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.eduvanz.MainApplication.TAG;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner
 */


public class EligibilityCheckFragment_3 extends Fragment {

    public static Spinner professionSpinner, documentSpinner;
    public static ArrayAdapter arrayAdapter_profession;
    public static ArrayList<String> document_arrayList;
    public static ArrayList<NameOfInsitituePOJO> nameOfInsitituePOJOArrayList;
    public static ArrayAdapter arrayAdapter_document;
    public static ArrayList<String> profession_arrayList;
    public static ArrayList<NameOfCoursePOJO> nameOfCoursePOJOArrayList;
    public static Context context;
    public static Fragment mFragment;
    Button buttonNext, buttonPrevious;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3;
    String instituteID = "", courseID = "", locationID="";

    public static Spinner spinnerLocationOfInstitute;

    public static ArrayAdapter arrayAdapter_locations;
    public static ArrayList<String> locations_arrayList;
    public static ArrayList<LocationsPOJO> locationPOJOArrayList;

    public EligibilityCheckFragment_3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eligibilitycheck_3, container, false);
        context = getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mFragment = new EligibilityCheckFragment_3();

        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        textView1 = (TextView) view.findViewById(R.id.textView1_e3);
        textView2 = (TextView) view.findViewById(R.id.textView2_e3);
        textView3 = (TextView) view.findViewById(R.id.textView3_e3);
        textView1.setTypeface(typefaceFont);
        textView2.setTypeface(typefaceFont);
        textView3.setTypeface(typefaceFontBold);

        professionSpinner = (Spinner) view.findViewById(R.id.spinner_yourprofession);
        profession_arrayList = new ArrayList<>();
        profession_arrayList.add("Student");
        profession_arrayList.add("Employed");
        profession_arrayList.add("Unemployed");
        arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
        professionSpinner.setAdapter(arrayAdapter_profession);
        arrayAdapter_profession.notifyDataSetChanged();

        documentSpinner = (Spinner) view.findViewById(R.id.spinner_document);
        document_arrayList = new ArrayList<>();
        document_arrayList.add("Pan Card");
        document_arrayList.add("Adhaar Card");
        document_arrayList.add("Both");
        document_arrayList.add("Neither");
        arrayAdapter_document = new ArrayAdapter(context, R.layout.custom_layout_spinner, document_arrayList);
        documentSpinner.setAdapter(arrayAdapter_document);
        arrayAdapter_profession.notifyDataSetChanged();

        buttonNext = (Button) view.findViewById(R.id.button_next_eligiblityfragment2);
        buttonNext.setTypeface(typefaceFontBold);

        buttonPrevious = (Button) view.findViewById(R.id.button_previous_eligiblityfragment2);
        buttonPrevious.setTypeface(typefaceFontBold);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EligibilityCheckFragment_4 eligibilityCheckFragment_4 = new EligibilityCheckFragment_4();
                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_4).commit();
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EligibilityCheckFragment_2 eligibilityCheckFragment_2 = new EligibilityCheckFragment_2();
                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_2).commit();
            }
        });

        return view;
    }

    public void courseApiCall() {
        //-------------------------------API CALL FOR COURSES-------------------------------------//
        try {
            String url = MainApplication.mainUrl + "pqform/apiPrefillCourses";
            Map<String, String> params = new HashMap<String, String>();
            params.put("instituteId", instituteID);
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(context, url, null, mFragment, "PrefillCourseFragment1", params);

        } catch (Exception e) {
            e.printStackTrace();
        }//------------------------------END API CALL FOR CITY------------------------------------//
    }

    public void locationApiCall(){
        //-----------------------------------------API CALL---------------------------------------//
        try {
            String url = MainApplication.mainUrl + "pqform/apiPrefillLocations";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", MainApplication.mainapp_instituteID);
            params.put("course_id", MainApplication.mainapp_courseID);
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(context, url, null, mFragment, "prefillLocationsFragment2", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-------------------------------------END OF API CALL------------------------------------//
    }


    private void loadPrevious() {
        if (MainApplication.previous == 1) {
            instituteID = MainApplication.mainapp_instituteID;
            courseID = MainApplication.mainapp_courseID;
            Log.e(TAG, "instituteID: " + instituteID + "  courseID " + courseID + " nameOfInsitituePOJOArrayList" + nameOfInsitituePOJOArrayList.size());
            for (int i = 0; i < nameOfInsitituePOJOArrayList.size(); i++) {
                Log.e(TAG, "for: " + nameOfInsitituePOJOArrayList.size());
                if (instituteID.equalsIgnoreCase(nameOfInsitituePOJOArrayList.get(i).instituteID)) {
                    Log.e(TAG, "nameOfInsitituePOJOArrayList: " + nameOfInsitituePOJOArrayList.get(i).instituteName);
                    break;
                }
            }
            courseApiCall();
        }
    }


    //---------------------------------RESPONSE OF API CALL---------------------------------------//
    public void prefillLocationsFragment2(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "PrefillInstitutesFragment1" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonData.getJSONArray("result");

                locationPOJOArrayList = new ArrayList<>();
                locations_arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    LocationsPOJO locationsPOJO = new LocationsPOJO();
                    JSONObject mJsonti = jsonArray.getJSONObject(i);
                    locationsPOJO.locationName = mJsonti.getString("location_name");
                    locations_arrayList.add(mJsonti.getString("location_name"));
                    locationsPOJO.locationID = mJsonti.getString("location_id");
                    locationPOJOArrayList.add(locationsPOJO);
                    Log.e("residential", "Spiner DATA:----------------- " + locationsPOJO.locationName);
                }
                arrayAdapter_locations = new ArrayAdapter(context, R.layout.custom_layout_spinner, locations_arrayList);
//                spinnerLocationOfInstitute.setItems(locations_arrayList);
//                spinnerLocationOfInstitute.setTextColor(getResources().getColor(R.color.black));
                spinnerLocationOfInstitute.setAdapter(arrayAdapter_locations);
                arrayAdapter_locations.notifyDataSetChanged();

            }else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            if(MainApplication.previousfragment3 == 1) {
//                instituteID = MainApplication.previous_pq2_instituteID;
//                courseID = MainApplication.previous_pq2_courseID;
                locationID = MainApplication.mainapp_locationID;
                Log.e(TAG, "instituteID: " + instituteID + "  courseID " + courseID + " locationPOJOArrayList" + locationPOJOArrayList.size());
                for (int i = 0; i < locationPOJOArrayList.size(); i++) {
                    Log.e(TAG, "for: " + locationPOJOArrayList.size());
                    if (locationID.equalsIgnoreCase(locationPOJOArrayList.get(i).locationID)) {
                        spinnerLocationOfInstitute.setSelection(i);
                        Log.e(TAG, "locationPOJOArrayList: " + locationPOJOArrayList.get(i).locationName);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
