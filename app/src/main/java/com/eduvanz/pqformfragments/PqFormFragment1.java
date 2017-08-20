package com.eduvanz.pqformfragments;


import android.content.Context;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.pqformfragments.pojo.NameOfCoursePOJO;
import com.eduvanz.pqformfragments.pojo.NameOfInsitituePOJO;
import com.eduvanz.volley.VolleyCall;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.eduvanz.MainApplication.TAG;

/**
 * A simple {@link Fragment} subclass.
 */

/** SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner */


public class PqFormFragment1 extends Fragment {

//    public static MaterialSpinner ;
    public static Spinner spinnerNameOfInsititue, spinnerNameOfCourse;

    public static ArrayAdapter arrayAdapter_NameOfInsititue;
    public static ArrayList<String> nameofinstitute_arrayList;
    public static ArrayList<NameOfInsitituePOJO> nameOfInsitituePOJOArrayList;
    public static ArrayAdapter arrayAdapter_NameOfCourse;
    public static ArrayList<String> nameofcourse_arrayList;
    public static ArrayList<NameOfCoursePOJO> nameOfCoursePOJOArrayList;
    Button buttonNext;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2;
    public static Context context;
    public static Fragment mFragment;

    String instituteID="", courseID="";
    public PqFormFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pq_form_page1, container, false);
        context = getContext();

        mFragment = new PqFormFragment1();

        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf" );
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf" );

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        textView1 = (TextView) view.findViewById(R.id.textview1_font);
        textView2 = (TextView) view.findViewById(R.id.textview2_font);
        textView1.setTypeface(typefaceFontBold);
        textView2.setTypeface(typefaceFontBold);

        buttonNext = (Button) view.findViewById(R.id.button_next_fragment1);
        buttonNext.setTypeface(typefaceFontBold);

        spinnerNameOfInsititue = (Spinner) view.findViewById(R.id.spinner_nameofinsititue);
//        spinnerNameOfInsititue.setItems("JetKing Infotrain", "Arena Animation", "Performance Enhancement Fitness", "UltraMax IT", "Real Estate Management Institute");
//        spinnerNameOfInsititue.setTypeface(typefaceFont);

//        spinnerNameOfInsititue.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//
//            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Log.e("I_________D", "onItemClick: " + " CLICK HUA");
//
//                String text = item;
//                int count = nameOfInsitituePOJOArrayList.size();
//                Log.e("TAG", "count: "+count );
//                for (int i = 0; i < count; i++) {
//                    if (nameOfInsitituePOJOArrayList.get(i).instituteName.equalsIgnoreCase(text)) {
//                        instituteID = nameOfInsitituePOJOArrayList.get(i).instituteID;
//                        Log.e("I_________D", "onItemClick: " + instituteID);
//                    }
//                }
//                courseApiCall();
//            }
//        });

        spinnerNameOfInsititue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("I_________D", "onItemClick: " + " CLICK HUA");

                String text = spinnerNameOfInsititue.getSelectedItem().toString();
                int count = nameOfInsitituePOJOArrayList.size();
                Log.e("TAG", "count: "+count );
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

        spinnerNameOfCourse = (Spinner) view.findViewById(R.id.spinner_nameofcourse);
//        spinnerNameOfCourse.setTypeface(typefaceFont);
//        spinnerNameOfCourse.setItems("Personal Trainer", "Corporate Finance PG", "Data Analytics PG", "CDSS",
//                "Change Prodegree", "CIBOP","Personal Trainer", "Corporate Finance PG", "Data Analytics PG", "CDSS", "Change Prodegree", "CIBOP");
//        spinnerNameOfCourse.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//
//            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Log.e("I_________D", "onItemClick: " );
//                String text = item;
//                int count = nameOfCoursePOJOArrayList.size();
//                Log.e("TAG", "count: "+count );
//                for (int i = 0; i < count; i++) {
//                    if (nameOfCoursePOJOArrayList.get(i).courseName.equalsIgnoreCase(text)) {
//                        courseID = nameOfCoursePOJOArrayList.get(i).courseID;
//                        Log.e("I_________D", "onItemClick: " + courseID);
//                    }
//                }
//            }
//        });

        spinnerNameOfCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("I_________D", "onItemClick: " );
                String text = spinnerNameOfCourse.getSelectedItem().toString();
                int count = nameOfCoursePOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (nameOfCoursePOJOArrayList.get(i).courseName.equalsIgnoreCase(text)) {
                       MainApplication.mainapp_courseID = courseID = nameOfCoursePOJOArrayList.get(i).courseID;
                        Log.e("I_________D", "onItemClick: " + courseID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(instituteID!=""&&courseID!=""){
                    PqFormFragment2 pqf2 = new PqFormFragment2 ();
//                    Bundle args = new Bundle();
//                    args.putString("institute_id", instituteID);
//                    args.putString("course_id", courseID);
//                    pqf2.setArguments(args);
//                    MainApplication.previous_pq2_courseID = courseID;
//                    MainApplication.previous_pq2_instituteID = instituteID;

                    transaction.replace(R.id.framelayout_pqform, pqf2).commit();
                }else {
                    Toast.makeText(context, "Please Select Course and Institute", Toast.LENGTH_SHORT).show();
                }

            }
        });
        
            //-----------------------------------------API CALL---------------------------------------//
            try {
                String url = MainApplication.mainUrl + "pqform/apiPrefillInstitutes";
                Map<String, String> params = new HashMap<String, String>();
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "PrefillInstitutesFragment1", params);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //-------------------------------------END OF API CALL------------------------------------//

        return view;
    }

    public void courseApiCall()
    {
        //-------------------------------API CALL FOR COURSES-------------------------------------//
        try {
            String url = MainApplication.mainUrl + "pqform/apiPrefillCourses";
            Map<String, String> params = new HashMap<String, String>();
            params.put("instituteId", instituteID);
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(context, url, null , mFragment, "PrefillCourseFragment1", params);

        } catch (Exception e) {
            e.printStackTrace();
        }//------------------------------END API CALL FOR CITY------------------------------------//
    }

    //---------------------------------RESPONSE OF API CALL---------------------------------------//
    public void prefillInstitutesFragment1(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "PrefillInstitutesFragment1" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                    Log.e("residential", "Spiner DATA:----------------- " + nameOfInsitituePOJO.instituteName);
                }
                arrayAdapter_NameOfInsititue = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofinstitute_arrayList);
//                spinnerNameOfInsititue.setItems(nameofinstitute_arrayList);
//                spinnerNameOfInsititue.setTextColor(getResources().getColor(R.color.black));
                spinnerNameOfInsititue.setAdapter(arrayAdapter_NameOfInsititue);
                arrayAdapter_NameOfInsititue.notifyDataSetChanged();

                loadPrevious();

            }else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPrevious() {
        if(MainApplication.previous == 1) {
            instituteID = MainApplication.mainapp_instituteID;
            courseID = MainApplication.mainapp_courseID;
            Log.e(TAG, "instituteID: " + instituteID + "  courseID " + courseID+ " nameOfInsitituePOJOArrayList"+ nameOfInsitituePOJOArrayList.size());
            for (int i = 0; i < nameOfInsitituePOJOArrayList.size(); i++) {
                Log.e(TAG, "for: "+nameOfInsitituePOJOArrayList.size() );
                if (instituteID.equalsIgnoreCase(nameOfInsitituePOJOArrayList.get(i).instituteID)) {
                    spinnerNameOfInsititue.setSelection(i);
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
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                spinnerNameOfCourse.setAdapter(arrayAdapter_NameOfCourse);
                arrayAdapter_NameOfCourse.notifyDataSetChanged();

                if(MainApplication.previous == 1) {
                    courseID = MainApplication.mainapp_courseID;
                    Log.e(TAG, "instituteID: " + instituteID + "  courseID " + courseID+ " nameOfInsitituePOJOArrayList"+ nameOfInsitituePOJOArrayList.size());
                    for (int i = 0; i < nameOfCoursePOJOArrayList.size(); i++) {
                        Log.e(TAG, "for: "+nameOfCoursePOJOArrayList.size() );
                        if (courseID.equalsIgnoreCase(nameOfCoursePOJOArrayList.get(i).courseID)) {
                            spinnerNameOfCourse.setSelection(i);
                            Log.e(TAG, "nameOfInsitituePOJOArrayList: " + nameOfCoursePOJOArrayList.get(i).courseName);
                            break;
                        }
                    }
                }
            }else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
