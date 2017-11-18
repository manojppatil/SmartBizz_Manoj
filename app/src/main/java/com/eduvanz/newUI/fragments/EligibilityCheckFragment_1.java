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
    public static ArrayAdapter arrayAdapter_NameOfInsititue;
    public static ArrayList<String> insititutename_arrayList;
    public static ArrayList<NameOfInsitituePOJO> nameOfInsitituePOJOArrayList;
    public static ArrayAdapter arrayAdapter_insituteName;
    public static ArrayList<String> nameofcourse_arrayList;
    public static ArrayList<NameOfCoursePOJO> nameOfCoursePOJOArrayList;
    public static Context context;
    public static Fragment mFragment;
    Button buttonNext;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3;
    String instituteID = "", courseID = "", locationID="";

    public static Spinner spinnerLocationOfInstitute;

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
                EligibilityCheckFragment_2 eligibilityCheckFragment_2 = new EligibilityCheckFragment_2();
                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_2).commit();

            }
        });

        insititutenameSpinner = (Spinner) view.findViewById(R.id.spinner_institutename_eligiblity);
        insititutename_arrayList = new ArrayList<>();
        insititutename_arrayList.add("Student");
        insititutename_arrayList.add("Employed");
        insititutename_arrayList.add("Unemployed");
        arrayAdapter_insituteName = new ArrayAdapter(context, R.layout.custom_layout_spinner, insititutename_arrayList);
        insititutenameSpinner.setAdapter(arrayAdapter_insituteName);
        arrayAdapter_insituteName.notifyDataSetChanged();

        return view;
    }

}
