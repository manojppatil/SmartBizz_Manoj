package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;

public class EmploymentDetailsFragment extends Fragment {

    private static OnEmploymentFragmentInteractionListener mListener;
    public static TextInputLayout tilAnnualIncome, tilCompanyName;
    public static EditText companyedt,edtaanual;
    private LinearLayout linStudentBtn, linSalariedBtn, linSelfEmployedBtn;

    public EmploymentDetailsFragment() {
        // Required empty public constructor
    }

    public static EmploymentDetailsFragment newInstance(String param1, String param2) {
        EmploymentDetailsFragment fragment = new EmploymentDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employment_details, container, false);
        tilAnnualIncome = view.findViewById(R.id.tilAnnualIncome);
        tilCompanyName = view.findViewById(R.id.tilCompanyName);
        edtaanual=view.findViewById(R.id.edtaanual);
        companyedt=view.findViewById(R.id.companyedt);
        linStudentBtn = view.findViewById(R.id.linStudentBtn);
        linSalariedBtn = view.findViewById(R.id.linSalariedBtn);
        linSelfEmployedBtn = view.findViewById(R.id.linSelfEmployedBtn);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      /*  tilCompanyName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              *//*  if (count > 0) {
                    NewLeadActivity.companyName = s.toString();
                    tilCompanyName.setError(null);
                } else {
                    NewLeadActivity.companyName = "";
                    tilCompanyName.setError("Please enter Company Name");
                }
                checkAllFields();*//*
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/


        linStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.profession = "2";
                linStudentBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_green_filled));
                linSalariedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linSelfEmployedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            }
        });

        linSalariedBtn.setOnClickListener(v -> {
            NewLeadActivity.profession = "1";
            linStudentBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            linSalariedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_green_filled));
            linSelfEmployedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
        });

        linSelfEmployedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.profession = "3";
                linStudentBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linSalariedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linSelfEmployedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_green_filled));

            }
        });


        companyedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    NewLeadActivity.companyName = s.toString();
                    companyedt.setError(null);
                } else {
                    NewLeadActivity.companyName = "";
                    companyedt.setError("Please enter Company Name");
                }
                checkAllFields();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edtaanual.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {

                    NewLeadActivity.annualIncome = s.toString();
                    edtaanual.setError(null);

                } else {

                    NewLeadActivity.annualIncome = "";
                    edtaanual.setError("Please enter Annual Income:");
                }
                checkAllFields();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

      /*  tilAnnualIncome.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ((s.toString().length() > 0)) {
                    NewLeadActivity.annualIncome = s.toString();
                    tilAnnualIncome.setError(null);
                } else {
                    NewLeadActivity.annualIncome = "";
                    tilAnnualIncome.setError("Please enter your annual income");
                }
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
    }

    public static void checkAllFields() {
        if (NewLeadActivity.profession.equals("") || NewLeadActivity.companyName.equals("") ||companyedt.getText().toString().equals("") || edtaanual.getText().toString().equals("")|| NewLeadActivity.annualIncome.equals("")|| NewLeadActivity.annualIncome.equals(null)) {
            mListener.onOffButtonEmployent(false, true);
          //  mListener.onEmploymentFragmentInteraction(false,0);
        } else {
            mListener.onOffButtonEmployent(true, true);
        }
    }

    public static void validate() {

//        if (NewLeadActivity.companyName.equals("") || NewLeadActivity.annualIncome.equals(""))
//            mListener.onEmploymentFragmentInteraction(false,);
//
//        else
//            mListener.onOffButtonEmployent(true,true);
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEmploymentFragmentInteractionListener) {
            mListener = (OnEmploymentFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        tilCompanyName.getEditText().setText(NewLeadActivity.companyName);
        tilAnnualIncome.getEditText().setText(NewLeadActivity.annualIncome);


        if (NewLeadActivity.profession.equals("1")) {
            linStudentBtn.performClick();
        } else if (NewLeadActivity.profession.equals("2")) {
            linSalariedBtn.performClick();
        } else if (NewLeadActivity.profession.equals("3")) {
            linSelfEmployedBtn.performClick();
        }

    }

    public static void setEmploymentData() {
        try {
            tilCompanyName.getEditText().setText(NewLeadActivity.companyName);
            tilAnnualIncome.getEditText().setText(NewLeadActivity.annualIncome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public interface OnEmploymentFragmentInteractionListener {
        // TODO: Update argument type and name
        void onEmploymentFragmentInteraction(boolean valid, int next);
        void onOffButtonEmployent(boolean next, boolean prev);
    }
}
