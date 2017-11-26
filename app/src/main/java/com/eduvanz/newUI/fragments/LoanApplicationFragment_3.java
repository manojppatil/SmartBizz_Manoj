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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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


public class LoanApplicationFragment_3 extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    Button buttonNext, buttonPrevious;
    static Typeface typefaceFont, typefaceFontBold, typeface;
    static TextView textView1, textView2, textView3, textViewArrowDown, textViewArrowDownCo;
    MainApplication mainApplication;
    LinearLayout linearLayoutBorrower, linearLayoutCoBorrower;
    RelativeLayout relativeLayoutBorrower, relativeLayoutCoBorrower;
    int borrowerVisiblity = 0, coborrowerVisiblity = 0;

    public LoanApplicationFragment_3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loanapplication_3, container, false);
        context = getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mainApplication = new MainApplication();

        mFragment = new LoanApplicationFragment_3();

        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");
        typeface = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        textView1 = (TextView) view.findViewById(R.id.textView1_l3);
        mainApplication.applyTypeface(textView1, context);
        textView2 = (TextView) view.findViewById(R.id.textView2_l3);
        mainApplication.applyTypeface(textView2, context);
        textView3 = (TextView) view.findViewById(R.id.textView3_l3);
        mainApplication.applyTypefaceBold(textView3, context);

        textViewArrowDown = (TextView) view.findViewById(R.id.texView_borrower_arrowdown);
        textViewArrowDown.setTypeface(typeface);

        textViewArrowDownCo = (TextView) view.findViewById(R.id.texView_coborrower_arrowdown);
        textViewArrowDownCo.setTypeface(typeface);

        buttonNext = (Button) view.findViewById(R.id.button_next_loanappfragment3);
        buttonNext.setTypeface(typefaceFontBold);

        buttonPrevious = (Button) view.findViewById(R.id.button_previous_loanappfragment3);
        buttonPrevious.setTypeface(typefaceFontBold);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanApplicationFragment_4 loanApplicationFragment_4 = new LoanApplicationFragment_4();
                transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_4).commit();
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanApplicationFragment_2 loanApplicationFragment_2 = new LoanApplicationFragment_2();
                transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_2).commit();
            }
        });

        linearLayoutBorrower = (LinearLayout) view.findViewById(R.id.linearLayout_docupload_borrower);
        relativeLayoutBorrower = (RelativeLayout) view.findViewById(R.id.relativeLayout_borrower);

        linearLayoutCoBorrower = (LinearLayout) view.findViewById(R.id.linearLayout_docupload_coborrower);
        relativeLayoutCoBorrower = (RelativeLayout) view.findViewById(R.id.relativeLayout_coborrower);

        relativeLayoutBorrower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(borrowerVisiblity == 0){
                    linearLayoutBorrower.setVisibility(View.VISIBLE);
                    borrowerVisiblity = 1;
                    textViewArrowDown.setText(getResources().getString(R.string.up));
                    textViewArrowDown.setTypeface(typeface);
                }else if(borrowerVisiblity == 1) {
                    linearLayoutBorrower.setVisibility(View.GONE);
                    borrowerVisiblity = 0;
                    textViewArrowDown.setText(getResources().getString(R.string.down));
                    textViewArrowDown.setTypeface(typeface);
                }

            }
        });

        relativeLayoutCoBorrower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coborrowerVisiblity == 0){
                    linearLayoutCoBorrower.setVisibility(View.VISIBLE);
                    coborrowerVisiblity = 1;
                    textViewArrowDownCo.setText(getResources().getString(R.string.up));
                    textViewArrowDownCo.setTypeface(typeface);
                }else if(coborrowerVisiblity == 1) {
                    linearLayoutCoBorrower.setVisibility(View.GONE);
                    coborrowerVisiblity = 0;
                    textViewArrowDownCo.setText(getResources().getString(R.string.down));
                    textViewArrowDownCo.setTypeface(typeface);
                }

            }
        });

        return view;
    }


}
