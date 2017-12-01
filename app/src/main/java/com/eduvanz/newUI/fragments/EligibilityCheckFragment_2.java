package com.eduvanz.newUI.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;
import com.eduvanz.newUI.VolleyCallNew;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner
 */


public class EligibilityCheckFragment_2 extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    Button buttonNext, buttonPrevious;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3;
    static TextView textViewCourseFee;
    EditText editTextLoanAmount, editTextFamilyIncome;

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

        MainApplication.currrentFrag = 2;

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

        editTextLoanAmount = (EditText) view.findViewById(R.id.editText_loanAmount);
        editTextFamilyIncome = (EditText) view.findViewById(R.id.editText_familyMonthlyIncome);

        if(!MainApplication.mainapp_fammilyincome.equals("") || !MainApplication.mainapp_loanamount.equals("")){
            editTextLoanAmount.setText(MainApplication.mainapp_loanamount);
            editTextFamilyIncome.setText(MainApplication.mainapp_fammilyincome);
        }

        textViewCourseFee = (TextView) view.findViewById(R.id.textView_coursefee);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editTextLoanAmount.getText().toString().equals("") && !editTextFamilyIncome.getText().toString().equals("")){
                    MainApplication.mainapp_fammilyincome = editTextFamilyIncome.getText().toString();
                    MainApplication.mainapp_loanamount= editTextLoanAmount.getText().toString();
                    EligibilityCheckFragment_3 eligibilityCheckFragment_3 = new EligibilityCheckFragment_3();
                    transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_3).commit();
                }else {
                    Toast.makeText(context, "Please provide your family income and loan amount", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EligibilityCheckFragment_1 eligibilityCheckFragment_1 = new EligibilityCheckFragment_1();
                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_1).commit();
            }
        });


        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "pqform/apiPrefillSliderAmount";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", MainApplication.mainapp_instituteID);
            params.put("course_id", MainApplication.mainapp_courseID);
            params.put("location_id", MainApplication.mainapp_locationID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "courseFee", params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    public void courseFee(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                textViewCourseFee.setText(jsonData.getString("result"));
                MainApplication.mainapp_coursefee = jsonData.getString("result");
            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
