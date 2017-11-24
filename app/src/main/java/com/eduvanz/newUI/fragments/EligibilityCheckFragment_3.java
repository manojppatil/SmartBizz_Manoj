package com.eduvanz.newUI.fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import static android.content.Context.LOCATION_SERVICE;
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
    public static ArrayAdapter arrayAdapter_document;
    public static ArrayList<String> profession_arrayList;
    public static Context context;
    public static Fragment mFragment;
    Button buttonNext, buttonPrevious;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3;
    EditText editTextCity;

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
        editTextCity = (EditText) view.findViewById(R.id.editText_cityName_ec);
        editTextCity.setText(MainApplication.mainapp_currentCity);
        professionSpinner = (Spinner) view.findViewById(R.id.spinner_yourprofession);
        profession_arrayList = new ArrayList<>();
        profession_arrayList.add("Select Any");
        profession_arrayList.add("Student");
        profession_arrayList.add("Employed");
        profession_arrayList.add("Self Employed");
        arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
        professionSpinner.setAdapter(arrayAdapter_profession);
        arrayAdapter_profession.notifyDataSetChanged();

        documentSpinner = (Spinner) view.findViewById(R.id.spinner_document);
        document_arrayList = new ArrayList<>();
        document_arrayList.add("Select Any");
        document_arrayList.add("Adhaar Card");
        document_arrayList.add("Pan Card");
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

                if(!MainApplication.mainapp_userdocument.equalsIgnoreCase("0") &&
                        !MainApplication.mainapp_userprofession.equalsIgnoreCase("0") &&
                        !editTextCity.getText().toString().equalsIgnoreCase("")){
                    MainApplication.mainapp_currentCity = editTextCity.getText().toString();
                    EligibilityCheckFragment_4 eligibilityCheckFragment_4 = new EligibilityCheckFragment_4();
                    transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_4).commit();
                }else {
                    Toast.makeText(context, "You need to select your profession, document & city to continue", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EligibilityCheckFragment_2 eligibilityCheckFragment_2 = new EligibilityCheckFragment_2();
                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_2).commit();
            }
        });

        if(!MainApplication.mainapp_userdocument.equals("")){

                if (MainApplication.mainapp_userdocument.equalsIgnoreCase("0")) {
                    documentSpinner.setSelection(0);
                }else if(MainApplication.mainapp_userdocument.equalsIgnoreCase("1")){
                    documentSpinner.setSelection(1);
                }else if(MainApplication.mainapp_userdocument.equalsIgnoreCase("2")){
                    documentSpinner.setSelection(2);
                }else if(MainApplication.mainapp_userdocument.equalsIgnoreCase("3")){
                    documentSpinner.setSelection(3);
                }else if(MainApplication.mainapp_userdocument.equalsIgnoreCase("4")){
                    documentSpinner.setSelection(4);
                }

        }

        if(!MainApplication.mainapp_userprofession.equals("")){
            if (MainApplication.mainapp_userprofession.equalsIgnoreCase("0")) {
                professionSpinner.setSelection(0);
            }else if(MainApplication.mainapp_userprofession.equalsIgnoreCase("Student")){
                professionSpinner.setSelection(1);
            }else if(MainApplication.mainapp_userprofession.equalsIgnoreCase("employed")){
                professionSpinner.setSelection(2);
            }else if(MainApplication.mainapp_userprofession.equalsIgnoreCase("selfEmployed")){
                professionSpinner.setSelection(3);
            }
        }

        documentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = documentSpinner.getSelectedItem().toString();
                    if (text.equalsIgnoreCase("Select Any")) {
                        MainApplication.mainapp_userdocument = "0";
                    }else if(text.equalsIgnoreCase("Adhaar Card")){
                        MainApplication.mainapp_userdocument = "1";
                    }else if(text.equalsIgnoreCase("Pan Card")){
                        MainApplication.mainapp_userdocument = "2";
                    }else if(text.equalsIgnoreCase("Both")){
                        MainApplication.mainapp_userdocument = "3";
                    }else if(text.equalsIgnoreCase("Neither")){
                        MainApplication.mainapp_userdocument = "4";
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        professionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = professionSpinner.getSelectedItem().toString();
                if (text.equalsIgnoreCase("Select Any")) {
                    MainApplication.mainapp_userprofession = "0";
                }else if(text.equalsIgnoreCase("Student")){
                    MainApplication.mainapp_userprofession = "Student";
                }else if(text.equalsIgnoreCase("Employed")){
                    MainApplication.mainapp_userprofession = "employed";
                }else if(text.equalsIgnoreCase("Self Employed")){
                    MainApplication.mainapp_userprofession = "selfEmployed";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }




}
