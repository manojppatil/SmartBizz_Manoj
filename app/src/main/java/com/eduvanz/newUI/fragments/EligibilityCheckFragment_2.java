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


public class EligibilityCheckFragment_2 extends Fragment {

    public static ArrayAdapter arrayAdapter_NameOfInsititue;
    public static ArrayList<String> nameofinstitute_arrayList;
    public static ArrayList<NameOfInsitituePOJO> nameOfInsitituePOJOArrayList;
    public static ArrayAdapter arrayAdapter_NameOfCourse;
    public static ArrayList<String> nameofcourse_arrayList;
    public static ArrayList<NameOfCoursePOJO> nameOfCoursePOJOArrayList;
    public static Context context;
    public static Fragment mFragment;
    public static Spinner spinnerLocationOfInstitute;
    public static ArrayAdapter arrayAdapter_locations;
    public static ArrayList<String> locations_arrayList;
    public static ArrayList<LocationsPOJO> locationPOJOArrayList;
    Button buttonNext, buttonPrevious;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3;
    String instituteID = "", courseID = "", locationID = "";

    public EligibilityCheckFragment_2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eligibilitycheck_2, container, false);
        context = getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mFragment = new EligibilityCheckFragment_2();

        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        textView1 = (TextView) view.findViewById(R.id.textView1_e2);
        textView2 = (TextView) view.findViewById(R.id.textView2_e2);
        textView3 = (TextView) view.findViewById(R.id.textView3_e2);
        textView1.setTypeface(typefaceFont);
        textView2.setTypeface(typefaceFontBold);
        textView3.setTypeface(typefaceFont);

        buttonNext = (Button) view.findViewById(R.id.button_next_eligiblityfragment2);
        buttonNext.setTypeface(typefaceFontBold);

        buttonPrevious = (Button) view.findViewById(R.id.button_previous_eligiblityfragment2);
        buttonPrevious.setTypeface(typefaceFontBold);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EligibilityCheckFragment_3 eligibilityCheckFragment_3 = new EligibilityCheckFragment_3();
                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_3).commit();
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EligibilityCheckFragment_1 eligibilityCheckFragment_1 = new EligibilityCheckFragment_1();
                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_1).commit();
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

    public void locationApiCall() {
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

    public void PrefillCourseFragment1(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "PrefillCourseFragment1" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonData.getJSONArray("result");

                nameOfCoursePOJOArrayList = new ArrayList<>();
                nameofcourse_arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    NameOfCoursePOJO nameOfCoursePOJO = new NameOfCoursePOJO();
                    JSONObject mJsonti = jsonArray.getJSONObject(i);
                    nameOfCoursePOJO.courseName = mJsonti.getString("course_name");
                    nameofcourse_arrayList.add(mJsonti.getString("course_name"));
                    nameOfCoursePOJO.courseID = mJsonti.getString("course_id");
                    nameOfCoursePOJOArrayList.add(nameOfCoursePOJO);
                    Log.e(MainApplication.TAG, "nameOfCoursePOJO Spiner DATA:----------------- " + nameOfCoursePOJO.courseName);
                }
                arrayAdapter_NameOfCourse = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofcourse_arrayList);
//                spinnerNameOfCourse.setItems(nameofcourse_arrayList);
//                spinnerNameOfCourse.setTextColor(getResources().getColor(R.color.black));
                arrayAdapter_NameOfCourse.notifyDataSetChanged();

                if (MainApplication.previous == 1) {
                    courseID = MainApplication.mainapp_courseID;
                    Log.e(TAG, "instituteID: " + instituteID + "  courseID " + courseID + " nameOfInsitituePOJOArrayList" + nameOfInsitituePOJOArrayList.size());
                    for (int i = 0; i < nameOfCoursePOJOArrayList.size(); i++) {
                        Log.e(TAG, "for: " + nameOfCoursePOJOArrayList.size());
                        if (courseID.equalsIgnoreCase(nameOfCoursePOJOArrayList.get(i).courseID)) {
                            Log.e(TAG, "nameOfInsitituePOJOArrayList: " + nameOfCoursePOJOArrayList.get(i).courseName);
                            break;
                        }
                    }
                }
            } else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
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

            } else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            if (MainApplication.previousfragment3 == 1) {
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
