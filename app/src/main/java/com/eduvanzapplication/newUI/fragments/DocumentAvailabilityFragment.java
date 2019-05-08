package com.eduvanzapplication.newUI.fragments;

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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;

import static android.view.View.GONE;
import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.documents;
import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.viewPager;

public class DocumentAvailabilityFragment extends Fragment {

    private static OnDocumentFragmentInteractionListener mDocListener;
    private LinearLayout linAadharBtn, linPanBtn, linBothBtn, linNoneBtn;
    private EditText edtAadhaar,edtPAN;
    int keyDel ;



    public DocumentAvailabilityFragment() {
        // Required empty public constructor
    }

    public static DocumentAvailabilityFragment newInstance(String param1, String param2) {
        DocumentAvailabilityFragment fragment = new DocumentAvailabilityFragment();
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
        View view =  inflater.inflate(R.layout.fragment_document_availability, container, false);
//        linAadharBtn = view.findViewById(R.id.linAadharBtn);
//        linPanBtn = view.findViewById(R.id.linPanBtn);
//        linBothBtn =view.findViewById(R.id.linBothBtn);
//        linNoneBtn = view.findViewById(R.id.linNoneBtn);

        edtAadhaar = view.findViewById(R.id.edtAadhaar);

        edtPAN = view.findViewById(R.id.edtPAN);

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //this is code for close PAN text After Enter Button

        edtPAN.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        }); //close PAN

//        tilAadhar.setVisibility(GONE);
//        tilPan.setVisibility(GONE);

//        linAadharBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NewLeadActivity.documents  ="1";
//                tilAadhar.setVisibility(View.VISIBLE);
//                tilPan.setVisibility(GONE);
//                linAadharBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
//                linPanBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linBothBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linNoneBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//            }
//        });
//
//        linPanBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NewLeadActivity.documents  ="2";
//                tilAadhar.setVisibility(View.GONE);
//                tilPan.setVisibility(View.VISIBLE);
//                linAadharBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linPanBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
//                linBothBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linNoneBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//            }
//        });
//
//        linBothBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NewLeadActivity.documents  ="3";
//                tilAadhar.setVisibility(View.VISIBLE);
//                tilPan.setVisibility(View.VISIBLE);
//                linAadharBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linPanBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linBothBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
//                linNoneBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//
//            }
//        });
//
//        linNoneBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NewLeadActivity.documents  ="4";
//                tilAadhar.setVisibility(View.GONE);
//                tilPan.setVisibility(View.GONE);
//                linAadharBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linPanBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linBothBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linNoneBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
//
//            }
//        });
//        linAadharBtn.performClick();

        edtAadhaar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }



            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                NewLeadActivity.aadharNumber = edtAadhaar.getText().toString();
                if(edtAadhaar.getText().toString().length() > 3 && edtPAN.getText().toString().length() > 3)
                {
                    documents = "3";
                }
                else {

                    documents = "1";
                }


                checkAllFields();

                //this is code for aadhar text format
/*
                NewLeadActivity.aadharNumber = edtAadhaar.getText().toString();

                edtAadhaar.setOnKeyListener(new View.OnKeyListener() {

                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = edtAadhaar.getText().length();
                    if(len == 4 || len==10) {
                        edtAadhaar.setText(edtAadhaar.getText() + "  ");
                        edtAadhaar.setSelection(edtAadhaar.getText().length());
                    }
                } else {
                    keyDel = 0;
                }*/


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edtPAN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NewLeadActivity.panNUmber = edtPAN.getText().toString();

                if(edtAadhaar.getText().toString().length() > 3 && edtPAN.getText().toString().length() > 3)
                {
                    documents = "3";
                }
                else {
                    documents = "2";
                }
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (viewPager.getCurrentItem() == 1){
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

                    }
                }
            });
        }

    }

//    private  void checkNextButtonstate()'

    private void checkAllFields() {

        switch (NewLeadActivity.documents){
            case "1":
                if(!Globle.validateAadharNumber(NewLeadActivity.aadharNumber)){
                    mDocListener.onOffButtonsDocuments(false, true);
                    //create method here
                    edtAadhaar.setError( "Please enter valid Aadhar" );
                    edtPAN.setError( null );

                }
                else
                    mDocListener.onOffButtonsDocuments(true, true);
                break;

            case "2":
                if (!NewLeadActivity.panNUmber.matches(Globle.panPattern)) {
                    mDocListener.onOffButtonsDocuments(false, true);
                    edtPAN.setError( "Plase enter valid Pan" );
                    edtAadhaar.setError( null );
                }
                else
                    mDocListener.onOffButtonsDocuments(true, true);
                break;

            case "3":
                if (!Globle.validateAadharNumber(NewLeadActivity.aadharNumber) && !NewLeadActivity.panNUmber.matches(Globle.panPattern))
                    mDocListener.onOffButtonsDocuments(false, true);
                else
                    mDocListener.onOffButtonsDocuments(true, true);
                edtAadhaar.setError( null );
                break;

//            case "4":
//                mDocListener.onOffButtonsDocuments(true, true);
//                break;
        }

    }

    public static void validate() {
        switch (NewLeadActivity.documents){
            case "1":
                if (!Globle.validateAadharNumber(NewLeadActivity.aadharNumber))
                    mDocListener.onDocumentFragmentInteraction(false, 1);
                else
                    mDocListener.onDocumentFragmentInteraction(true, 2);
                break;

            case "2":
                if (!NewLeadActivity.panNUmber.matches(Globle.panPattern))
                    mDocListener.onDocumentFragmentInteraction(false, 1);
                else
                    mDocListener.onDocumentFragmentInteraction(true, 2);
                break;

            case "3":
                if (!Globle.validateAadharNumber(NewLeadActivity.aadharNumber) && !NewLeadActivity.panNUmber.matches(Globle.panPattern))
                    mDocListener.onDocumentFragmentInteraction(false, 1);
                else
                    mDocListener.onDocumentFragmentInteraction(true, 2);
                break;

//            case "4":
//                mDocListener.onDocumentFragmentInteraction(true, 2);
//                break;
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mDocListener != null) {
            mDocListener.onDocumentFragmentInteraction(true,2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDocumentFragmentInteractionListener) {
            mDocListener = (OnDocumentFragmentInteractionListener) context;
            mDocListener.onOffButtonsDocuments(false, true);

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        edtAadhaar.setText(NewLeadActivity.Aaadhaarno);
        edtPAN.setText(NewLeadActivity.Ppanno);

//        if (NewLeadActivity.documents.equals("1")){
//            linAadharBtn.performClick();
//            tilAadhar.getEditText().setText(NewLeadActivity.aadharNumber);
//        }
//        else if (NewLeadActivity.documents.equals("2")){
//            linPanBtn.performClick();
//            tilPan.getEditText().setText(NewLeadActivity.panNUmber);
//        }
//        else if (NewLeadActivity.documents.equals("3")){
//            linBothBtn.performClick();
//            tilAadhar.getEditText().setText(NewLeadActivity.aadharNumber);
//            tilPan.getEditText().setText(NewLeadActivity.panNUmber);
//        }
//        else {
//            linNoneBtn.performClick();
//        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDocListener = null;
    }

    public interface OnDocumentFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDocumentFragmentInteraction(boolean valid, int next);
        void onOffButtonsDocuments(boolean next, boolean prev);

        boolean onOffButtonsDocuments(boolean b);
    }
}