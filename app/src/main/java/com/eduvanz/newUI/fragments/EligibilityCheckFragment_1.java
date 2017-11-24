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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.pqformfragments.PqFormFragment3;
import com.eduvanz.pqformfragments.pojo.LocationsPOJO;
import com.eduvanz.pqformfragments.pojo.NameOfCoursePOJO;
import com.eduvanz.pqformfragments.pojo.NameOfInsitituePOJO;
import com.eduvanz.volley.VolleyCall;
import com.eduvanz.volley.VolleyCallNew;

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


public class EligibilityCheckFragment_1 extends Fragment {

    public static Spinner insititutenameSpinner, insitituteLocationSpinner, coursenameSpinner;
    public static Context context;
    public static Fragment mFragment;
    Button buttonNext;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3;
    String instituteID = "", courseID = "", locationID="";
    public static ArrayAdapter arrayAdapter_NameOfInsititue;
    public static ArrayList<String> nameofinstitute_arrayList;
    public static ArrayList<NameOfInsitituePOJO> nameOfInsitituePOJOArrayList;
    public static ArrayAdapter arrayAdapter_NameOfCourse;
    public static ArrayList<String> nameofcourse_arrayList;
    public static ArrayList<NameOfCoursePOJO> nameOfCoursePOJOArrayList;
    public static ArrayAdapter arrayAdapter_locations;
    public static ArrayList<String> locations_arrayList;
    public static ArrayList<LocationsPOJO> locationPOJOArrayList;


    public EligibilityCheckFragment_1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eligibilitycheck_1, container, false);
        context = getContext();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mFragment = new EligibilityCheckFragment_1();

        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Regular.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        textView1 = (TextView) view.findViewById(R.id.textView_e1);
        textView2 = (TextView) view.findViewById(R.id.textView_e2);
        textView3 = (TextView) view.findViewById(R.id.textView_e3);
        textView1.setTypeface(typefaceFontBold);
        textView2.setTypeface(typefaceFont);
        textView3.setTypeface(typefaceFont);

        buttonNext = (Button) view.findViewById(R.id.button_next_eligiblityfragment1);
        buttonNext.setTypeface(typefaceFontBold);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!instituteID.equals("") && !courseID.equals("")) {
                    EligibilityCheckFragment_2 eligibilityCheckFragment_2 = new EligibilityCheckFragment_2();
                    transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_2).commit();
                }else {
                    Toast.makeText(context, "Please Select Course, Institute & Institute Location to Continue", Toast.LENGTH_SHORT).show();
                }


            }
        });

        insititutenameSpinner = (Spinner) view.findViewById(R.id.spinner_institutename_eligiblity);
        insitituteLocationSpinner = (Spinner) view.findViewById(R.id.spinner_institutelocation_eligiblity);
        coursenameSpinner = (Spinner) view.findViewById(R.id.spinner_coursename_eligiblity);


        insititutenameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("I_________D", "onItemClick: " + " CLICK HUA");

                String text = insititutenameSpinner.getSelectedItem().toString();
                int count = nameOfInsitituePOJOArrayList.size();
                Log.e("TAG", "count: " + count);
                for (int i = 0; i < count; i++) {
                    if (nameOfInsitituePOJOArrayList.get(i).instituteName.equalsIgnoreCase(text)) {
                        MainApplication.mainapp_instituteID = instituteID = nameOfInsitituePOJOArrayList.get(i).instituteID;
                        Log.e("I_________D", "onItemClick: " + instituteID);
                    }
                }
                courseApiCall();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        coursenameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("I_________D", "onItemClick: ");
                String text = coursenameSpinner.getSelectedItem().toString();
                int count = nameOfCoursePOJOArrayList.size();
                Log.e("TAG", "count: " + count);
                for (int i = 0; i < count; i++) {
                    if (nameOfCoursePOJOArrayList.get(i).courseName.equalsIgnoreCase(text)) {
                        MainApplication.mainapp_courseID = courseID = nameOfCoursePOJOArrayList.get(i).courseID;
                        Log.e("I_________D", "onItemClick: " + courseID);
                    }
                }
                locationApiCall();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        insitituteLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = insitituteLocationSpinner.getSelectedItem().toString();
                int count = locationPOJOArrayList.size();
                for (int i = 0; i < count; i++) {
                    if (locationPOJOArrayList.get(i).locationName.equalsIgnoreCase(text)) {
                        MainApplication.mainapp_locationID = locationID = locationPOJOArrayList.get(i).locationID;
                        Log.e("I_________D", "onItemClick: " + locationID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "pqform/apiPrefillInstitutes";
            Map<String, String> params = new HashMap<String, String>();
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "instituteName", params);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    public void courseApiCall() {
        try {
            String url = MainApplication.mainUrl + "pqform/apiPrefillCourses";
            Map<String, String> params = new HashMap<String, String>();
            params.put("instituteId", instituteID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "courseName", params);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void locationApiCall(){
        try {
            String url = MainApplication.mainUrl + "pqform/apiPrefillLocations";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", MainApplication.mainapp_instituteID);
            params.put("course_id", MainApplication.mainapp_courseID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "locationName", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** RESPONSE OF API CALL **/
    public void instituteName(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "PrefillInstitutesFragment1" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonData.getJSONArray("result");

                nameOfInsitituePOJOArrayList = new ArrayList<>();
                nameofinstitute_arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    NameOfInsitituePOJO nameOfInsitituePOJO = new NameOfInsitituePOJO();
                    JSONObject mJsonti = jsonArray.getJSONObject(i);
                    nameOfInsitituePOJO.instituteName = mJsonti.getString("institute_name");
                    nameofinstitute_arrayList.add(mJsonti.getString("institute_name"));
                    nameOfInsitituePOJO.instituteID = mJsonti.getString("institute_id");
                    nameOfInsitituePOJOArrayList.add(nameOfInsitituePOJO);
                }
                arrayAdapter_NameOfInsititue = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofinstitute_arrayList);
                insititutenameSpinner.setAdapter(arrayAdapter_NameOfInsititue);
                arrayAdapter_NameOfInsititue.notifyDataSetChanged();

                if(!MainApplication.mainapp_instituteID.equals("")){
                    for (int i = 0; i < nameOfInsitituePOJOArrayList.size(); i++) {
                        if (MainApplication.mainapp_instituteID.equalsIgnoreCase(nameOfInsitituePOJOArrayList.get(i).instituteID)) {
                            insititutenameSpinner.setSelection(i);
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


    public void courseName(JSONObject jsonData) {
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
                }
                arrayAdapter_NameOfCourse = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofcourse_arrayList);
                coursenameSpinner.setAdapter(arrayAdapter_NameOfCourse);
                arrayAdapter_NameOfCourse.notifyDataSetChanged();

                if(!MainApplication.mainapp_courseID.equals("")){

                    for (int i = 0; i < nameOfCoursePOJOArrayList.size(); i++) {
                        if (MainApplication.mainapp_courseID.equalsIgnoreCase(nameOfCoursePOJOArrayList.get(i).courseID)) {
                            coursenameSpinner.setSelection(i);
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

    public void locationName(JSONObject jsonData) {
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
                }
                arrayAdapter_locations = new ArrayAdapter(context, R.layout.custom_layout_spinner, locations_arrayList);
                insitituteLocationSpinner.setAdapter(arrayAdapter_locations);
                arrayAdapter_locations.notifyDataSetChanged();

                if(!MainApplication.mainapp_locationID.equals("")){

                    for (int i = 0; i <  locationPOJOArrayList.size(); i++) {
                        if (MainApplication.mainapp_locationID.equalsIgnoreCase(locationPOJOArrayList.get(i).locationID)) {
                            insitituteLocationSpinner.setSelection(i);
                        }
                    }
                }


            }else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
