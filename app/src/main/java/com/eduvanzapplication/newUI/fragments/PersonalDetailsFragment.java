package com.eduvanzapplication.newUI.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;

import java.util.Calendar;

import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.viewPager;

public class PersonalDetailsFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputLayout tilFirstName,tilMiddleName, tilLastName;
    private LinearLayout linMaleBtn, linFemaleBtn, linOtherBtn, linDobBtn;
    private Switch switchMarital;
    private TextView txtMaritalStatus;

    private OnFragmentInteractionListener mListener;
    private TextView txtDOB;

    private String firstName="", lastName="", middleName="", gender="2", maritalStatus="0";
    public PersonalDetailsFragment() {
        // Required empty public constructor
    }

    public static PersonalDetailsFragment newInstance(String param1, String param2) {
        PersonalDetailsFragment fragment = new PersonalDetailsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_details, container, false);
        tilFirstName = view.findViewById(R.id.first_name);
        tilMiddleName =view.findViewById(R.id.middle_name);
        tilLastName =view.findViewById(R.id.last_name);
        linMaleBtn = view.findViewById(R.id.linMaleBtn);
        linFemaleBtn = view.findViewById(R.id.linFemaleBtn);
        linOtherBtn = view.findViewById(R.id.linOtherBtn);
        linDobBtn = view.findViewById(R.id.linDobBtn);
        txtDOB = view.findViewById(R.id.txtDOB);
        txtMaritalStatus = view.findViewById(R.id.txtMaritalStatus);
        switchMarital = view.findViewById(R.id.switchMarital);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linMaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "1";
                linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            }
        });

        linFemaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "2";
                linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            }
        });

        linOtherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "2";
                linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
            }
        });

        linDobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)-18);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity());
                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        }
                    });
                    datePickerDialog.show();
                }
            }
        });

        switchMarital.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                maritalStatus = isChecked ? "1" : "0";
                if (isChecked)
                    txtMaritalStatus.setText("Married");
                else
                    txtMaritalStatus.setText("Unmarried");
            }
        });

        tilFirstName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstName = tilFirstName.getEditText().getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tilMiddleName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                middleName = tilMiddleName.getEditText().getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tilLastName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastName = tilLastName.getEditText().getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE){

                    if (viewPager.getCurrentItem() == 1){
                        firstName = tilFirstName.getEditText().getText().toString();
                        validate();
//                        viewPager.setCurrentItem(0);
                    }
                }
            }
        });
    }

    private void checkAllFields() {
        if (!firstName.equals("") && !lastName.equals("") && !middleName.equals("")
        || txtDOB.getText().toString().equals("")){
            mListener.onOffButtons(true, false);
        }else
            mListener.onOffButtons(false, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void validate() {
        if (firstName.equals("") )
            mListener.onFragmentInteraction(false, 0);
        else
            mListener.onFragmentInteraction(true, 1);
        }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(boolean valid, int next);
        void onOffButtons(boolean next, boolean prev);
    }

}
