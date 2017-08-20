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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.pqformfragments.pojo.LocationsPOJO;
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
public class PqFormFragment2 extends Fragment {

    public static Spinner spinnerLocationOfInstitute;
    public static Context context;
    Button buttonNext, buttonPrevious;
    View view1, view2;
    ImageView imageViewStep2;
    TextView textView1;
    Typeface typefaceFont, typefaceFontBold;
    String courseID="", instituteID="", locationID="";
    public static Fragment mFragment;

    public static ArrayAdapter arrayAdapter_locations;
    public static ArrayList<String> locations_arrayList;
    public static ArrayList<LocationsPOJO> locationPOJOArrayList;

    public PqFormFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pq_form_fragment2, container, false);
        context = getContext();
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        mFragment = new PqFormFragment2();


        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");
        textView1 = (TextView) view.findViewById(R.id.textview1_font_fragment2);
        textView1.setTypeface(typefaceFontBold);

        view1 = view.findViewById(R.id.view1_fragment2_step2);
        view2 = view.findViewById(R.id.view2_fragment2_step2);
        imageViewStep2 = (ImageView) view.findViewById(R.id.imageView_fragment2_step2);

        view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewStep2.setImageDrawable(getResources().getDrawable(R.drawable.step2_image));

        buttonPrevious = (Button) view.findViewById(R.id.button_previous_fragment2);
        buttonNext = (Button) view.findViewById(R.id.button_next_fragment2);
        buttonNext.setTypeface(typefaceFontBold);
        buttonPrevious.setTypeface(typefaceFontBold);

        spinnerLocationOfInstitute = (Spinner) view.findViewById(R.id.spinner_locationofinstitute);
//        spinnerLocationOfInstitute.setTypeface(typefaceFont);
//        spinnerLocationOfInstitute.setItems("Kandivali", "Borivali", "Churchgae", "Lower Parel", "Ballard Estate");
//        spinnerLocationOfInstitute.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//
//                String text = item;
//                int count = locationPOJOArrayList.size();
//                Log.e("TAG", "count: "+count );
//                for (int i = 0; i < count; i++) {
//                    if (locationPOJOArrayList.get(i).locationName.equalsIgnoreCase(text)) {
//                        locationID = locationPOJOArrayList.get(i).locationID;
//                        Log.e("I_________D", "onItemClick: " + locationID);
//                    }
//                }
//            }
//        });

        spinnerLocationOfInstitute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spinnerLocationOfInstitute.getSelectedItem().toString();
                int count = locationPOJOArrayList.size();
                Log.e("TAG", "count: "+count );
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

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PqFormFragment1 pqf1 = new PqFormFragment1 ();
//                MainApplication.previous_pq2_courseID = courseID;
//                MainApplication.previous_pq2_instituteID = instituteID;
                MainApplication.previous = 1;
                transaction.replace(R.id.framelayout_pqform, pqf1).commit();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(locationID!=""){
                    PqFormFragment3 pqf3 = new PqFormFragment3 ();
//                    Bundle args = new Bundle();
//                    args.putString("institute_id", instituteID);
//                    args.putString("course_id", courseID);
//                    args.putString("location_id", locationID);
//                    pqf3.setArguments(args);
//                    MainApplication.previous_pq2_courseID = courseID;
//                    MainApplication.previous_pq2_instituteID = instituteID;
//                    MainApplication.previous_pq3_locationID = locationID;
                    transaction.replace(R.id.framelayout_pqform, pqf3).commit();
                }else {
                    Toast.makeText(context, "Please Select Location", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        return view;
    }

    //---------------------------------RESPONSE OF API CALL---------------------------------------//
    public void prefillLocationsFragment2(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "PrefillInstitutesFragment1" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
