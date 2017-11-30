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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.volley.VolleyCallNew;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner
 */


public class LoanApplicationFragment_4 extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    Button buttonNext, buttonPrevious, buttonUpload, buttonDownload;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3, textView17, textView4,textView5,textView6,textView7,
            textView8,textView9,textView10,textView11,textView12,textView13,textView14, textView19,
            textView15,textView16,textView18;
    RadioButton radioButtonManual, radioButtonEsign;
    LinearLayout linearLayoutManual;
    MainApplication mainApplication;

    public LoanApplicationFragment_4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loanapplication_4, container, false);
        context = getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mainApplication = new MainApplication();

        mFragment = new LoanApplicationFragment_4();

        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();


        textView1 = (TextView) view.findViewById(R.id.textView1_l4);
        mainApplication.applyTypeface(textView1, context);
        textView2 = (TextView) view.findViewById(R.id.textView2_l4);
        mainApplication.applyTypeface(textView2, context);
        textView3 = (TextView) view.findViewById(R.id.textView3_l4);
        mainApplication.applyTypefaceBold(textView3, context);

        textView4 = (TextView) view.findViewById(R.id.textView_signsubmit_1);
        mainApplication.applyTypeface(textView4, context);
        textView5 = (TextView) view.findViewById(R.id.textView_signsubmit_2);
        mainApplication.applyTypeface(textView5, context);

        textView6 = (TextView) view.findViewById(R.id.textView_signsubmit_3);
        mainApplication.applyTypeface(textView6, context);
        textView7 = (TextView) view.findViewById(R.id.textView_signsubmit_4);
        mainApplication.applyTypeface(textView7, context);
        textView8 = (TextView) view.findViewById(R.id.textView_signsubmit_5);
        mainApplication.applyTypeface(textView8, context);
        textView9 = (TextView) view.findViewById(R.id.textView_signsubmit_6);
        mainApplication.applyTypeface(textView9, context);
        textView10 = (TextView) view.findViewById(R.id.textView_signsubmit_7);
        mainApplication.applyTypeface(textView10, context);
        textView11 = (TextView) view.findViewById(R.id.textView_signsubmit_8);
        mainApplication.applyTypeface(textView11, context);
        textView12 = (TextView) view.findViewById(R.id.textView_signsubmit_9);
        mainApplication.applyTypeface(textView12, context);
        textView13 = (TextView) view.findViewById(R.id.textView_signsubmit_10);
        mainApplication.applyTypeface(textView13, context);
        textView14 = (TextView) view.findViewById(R.id.textView_signsubmit_11);
        mainApplication.applyTypeface(textView14, context);
        textView15 = (TextView) view.findViewById(R.id.textView_signsubmit_12);
        mainApplication.applyTypeface(textView15, context);
        textView16 = (TextView) view.findViewById(R.id.textView_signsubmit_13);
        mainApplication.applyTypeface(textView16, context);
        textView17 = (TextView) view.findViewById(R.id.textView_signsubmit_14);
        mainApplication.applyTypeface(textView17, context);
        textView18 = (TextView) view.findViewById(R.id.textView_signsubmit_15);
        mainApplication.applyTypeface(textView18, context);
        textView19 = (TextView) view.findViewById(R.id.textView_signsubmit_0);
        mainApplication.applyTypeface(textView19, context);

        buttonNext = (Button) view.findViewById(R.id.button_submit_loanappfragment4);
        buttonNext.setTypeface(typefaceFontBold);

        buttonPrevious = (Button) view.findViewById(R.id.button_previous_loanappfragment4);
        buttonPrevious.setTypeface(typefaceFontBold);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EligibilityCheckFragment_4 eligibilityCheckFragment_4 = new EligibilityCheckFragment_4();
//                transaction.replace(R.id.frameLayout_loanapplication, eligibilityCheckFragment_4).commit();
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanApplicationFragment_3 loanApplicationFragment_3 = new LoanApplicationFragment_3();
                transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_3).commit();
            }
        });

        linearLayoutManual = (LinearLayout) view.findViewById(R.id.linearLayout_manual);

        radioButtonManual = (RadioButton) view.findViewById(R.id.radioButton_manual);
        mainApplication.applyTypeface(radioButtonManual, context);
        radioButtonManual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(radioButtonManual.isChecked()){
                    linearLayoutManual.setVisibility(View.VISIBLE);
                }else if (!radioButtonManual.isChecked()){
                    linearLayoutManual.setVisibility(View.GONE);
                }
            }
        });

        radioButtonEsign = (RadioButton) view.findViewById(R.id.radioButton_esign);
        mainApplication.applyTypeface(radioButtonEsign, context);

        buttonDownload = (Button) view.findViewById(R.id.button_signnsubmit_downloadApplication);
        mainApplication.applyTypeface(buttonDownload, context);
        buttonUpload = (Button) view.findViewById(R.id.button_signnsubmit_uploadApplication);
        mainApplication.applyTypeface(buttonUpload, context);

        /** API CALL GET Dates**/
        try {
            String url = MainApplication.mainUrl + "dashboard/getProfileDashbBoardStatus";
            Map<String, String> params = new HashMap<String, String>();
            params.put("studentId","530");
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, null, mFragment, "ProfileDashbBoardStatusData", params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    public void setProfileDashbBoardStatusData(JSONObject jsonDataO) {
        Log.e("", "setProfileDashbBoardStatusData: "+jsonDataO );
    }
}
