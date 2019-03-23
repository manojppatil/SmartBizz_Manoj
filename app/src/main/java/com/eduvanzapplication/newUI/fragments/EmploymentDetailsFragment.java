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

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;

public class EmploymentDetailsFragment extends Fragment {

    private static OnEmploymentFragmentInteractionListener mListener;
    TextInputLayout tilAnnualIncome, tilCompanyName;

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
        View view =inflater.inflate(R.layout.fragment_employment_details, container, false);
        tilAnnualIncome = view.findViewById(R.id.tilAnnualIncome);
        tilCompanyName = view.findViewById(R.id.tilCompanyName);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilCompanyName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NewLeadActivity.companyName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tilAnnualIncome.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NewLeadActivity.annualIncome = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void checkAllFields(){
        if (NewLeadActivity.companyName.equals("") || NewLeadActivity.annualIncome.equals("")){
            mListener.onOffButtonEmployent(false,true);
        }
        else
            mListener.onOffButtonEmployent(true,true);
    }

    public static  void validate(){
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

    public interface OnEmploymentFragmentInteractionListener {
        // TODO: Update argument type and name
        void onEmploymentFragmentInteraction(boolean valid, int next);
        void onOffButtonEmployent(boolean next, boolean prev);
    }
}
