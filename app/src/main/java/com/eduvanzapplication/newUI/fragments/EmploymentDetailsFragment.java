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
import android.widget.TextView;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;

import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.isCurrentAddEnabled;
import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.isEmploymentDtlEnabled;

public class EmploymentDetailsFragment extends Fragment {

    private static OnEmploymentFragmentInteractionListener mListener;
    public static TextInputLayout tilAnnualIncome, tilCompanyName;
    public static EditText companyedt,edtaanual;
    public static TextView txtEmploymentDetailsErrMsg;


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
        txtEmploymentDetailsErrMsg = view.findViewById(R.id.txtEmploymentDetailsErrMsg);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        companyedt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isEmploymentDtlEnabled) {
                if (s.length() > 0) {
                    NewLeadActivity.companyName = s.toString();
                    companyedt.setError(null);
                } else {
                    NewLeadActivity.companyName = "";
                   // companyedt.setError("Please enter Company Name");
                }
                checkAllFields();
                }
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

                if (isEmploymentDtlEnabled) {
                if (s.length() > 0) {
                    NewLeadActivity.annualIncome = s.toString();
                    edtaanual.setError(null);

                } else {
                    NewLeadActivity.annualIncome = "";
                 //   edtaanual.setError("Please enter Annual Income:");
                }
                checkAllFields();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public static void checkAllFields() {
        if (NewLeadActivity.companyName.equals("") || NewLeadActivity.annualIncome.equals("")) {
          //  mListener.onEmploymentFragmentInteraction(false,0);
            if (companyedt.getText().toString().equals("")) {
            txtEmploymentDetailsErrMsg.setVisibility(View.VISIBLE);
                txtEmploymentDetailsErrMsg.setText("Please Enter Company Name");
            } else if (edtaanual.getText().toString().equals("")) {
                txtEmploymentDetailsErrMsg.setVisibility(View.VISIBLE);
                txtEmploymentDetailsErrMsg.setText("Please Enter Annual Income");
            }
            mListener.onOffButtonEmployentSubmit(false, true);

        } else {
            txtEmploymentDetailsErrMsg.setVisibility(View.GONE);
            txtEmploymentDetailsErrMsg.setText(null);
            mListener.onOffButtonEmployentSubmit(true, true);
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
        if(isEmploymentDtlEnabled) {
            checkAllFields();
        }
        isEmploymentDtlEnabled = true;
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

        void onOffButtonEmployentSubmit(boolean next, boolean prev);
    }
}
