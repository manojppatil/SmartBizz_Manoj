package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;

import java.util.ArrayList;

public class CurrentAddressFragment extends Fragment {

    private static OnCurrentAddrFragmentInteractionListener mListener;
    private TextInputLayout tilFlat, tilStreet,tilPincode ;
    private Spinner spCountry, spState, spCity;
    private ArrayList<String> countrList  = new ArrayList<>();

    public CurrentAddressFragment() {
        // Required empty public constructor
    }

    public static CurrentAddressFragment newInstance(String param1, String param2) {
        CurrentAddressFragment fragment = new CurrentAddressFragment();
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
        View view=inflater.inflate(R.layout.fragment_current_address, container, false);
        tilFlat = view.findViewById(R.id.tilFlat);
        tilStreet = view.findViewById(R.id.tilStreet);
        tilPincode = view.findViewById(R.id.tilPincode);
        spCountry = view.findViewById(R.id.spCountry);
        spState = view.findViewById(R.id.spState);
        spCity = view.findViewById(R.id.spCity);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilFlat.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NewLeadActivity.flatBuildingSoc = s.toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tilStreet.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NewLeadActivity.streetLocalityLandMark = s.toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tilPincode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NewLeadActivity.pinCode = s.toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        countrList.add("Select");
        countrList.add("India");
        countrList.add("Germany");
        countrList.add("Behrain");

        ArrayAdapter countryAdapter = new ArrayAdapter(getContext(),  android.R.layout.simple_list_item_1, countrList );
        spCountry.setAdapter(countryAdapter);

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NewLeadActivity.country = String.valueOf(position); //selected val
                checkAllFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        spState.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                NewLeadActivity.state = s.toString();
//                checkAllFields();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        spCity.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                NewLeadActivity.city = s.toString();
//                checkAllFields();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    private void checkAllFields() {
        if (NewLeadActivity.flatBuildingSoc.equals("") || NewLeadActivity.streetLocalityLandMark.equals("")
            || NewLeadActivity.pinCode.equals("")){
            mListener.onOffButtonsCurrentAddress(false, false);
        }else
            mListener.onOffButtonsCurrentAddress(true, false);
    }
    public static void validate() {
        if (NewLeadActivity.flatBuildingSoc.equals("") || NewLeadActivity.streetLocalityLandMark.equals("")
                || NewLeadActivity.pinCode.equals("") )
            mListener.onCurrentAddrFragmentInteraction(false, 2);
        else
            mListener.onCurrentAddrFragmentInteraction(true, 3);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCurrentAddrFragmentInteractionListener) {
            mListener = (OnCurrentAddrFragmentInteractionListener) context;
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
        tilFlat.getEditText().setText(NewLeadActivity.flatBuildingSoc);
        tilStreet.getEditText().setText(NewLeadActivity.streetLocalityLandMark);
        tilPincode.getEditText().setText(NewLeadActivity.pinCode);

    }

    public interface OnCurrentAddrFragmentInteractionListener {
        void onCurrentAddrFragmentInteraction(boolean valid, int next);
        void onOffButtonsCurrentAddress(boolean next, boolean prev);
    }
}
