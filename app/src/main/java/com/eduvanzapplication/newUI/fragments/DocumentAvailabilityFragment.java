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
import android.text.InputFilter;
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

import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.isDocAvailabilityEnabled;
import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.viewPager;

public class DocumentAvailabilityFragment extends Fragment {

    private static OnDocumentFragmentInteractionListener mDocListener;
    public LinearLayout linAadharBtn, linPanBtn, linBothBtn, linNoneBtn;
    public static EditText edtAadhaar, edtPAN;
    public static TextView txtDocAvailableErrMsg;
    int keyDel;


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
        View view = inflater.inflate(R.layout.fragment_document_availability, container, false);

        edtAadhaar = view.findViewById(R.id.edtAadhaar);

        edtPAN = view.findViewById(R.id.edtPAN);
        txtDocAvailableErrMsg = view.findViewById(R.id.txtDocAvailableErrMsg);

        return view;
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


        edtAadhaar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isDocAvailabilityEnabled){

                    if (!Globle.validateAadharNumber(edtAadhaar.getText().toString())) {
//False
                    if (edtAadhaar.getText().toString().length() > 0 && edtPAN.getText().toString().length() > 0) {
                        NewLeadActivity.aadharNumber = "";
//                        edtAadhaar.setError("Please Enter valid Aadhaar Number!");
                        edtPAN.setError(null);
                        NewLeadActivity.documents = "3";
                    } else if (edtAadhaar.getText().toString().length() == 0 && edtPAN.getText().toString().length() > 0) {
                        NewLeadActivity.aadharNumber = "";
                        edtAadhaar.setError(null);
                        edtPAN.setError(null);
                        NewLeadActivity.documents = "2";
                    } else if (edtAadhaar.getText().toString().length() > 0 && edtPAN.getText().toString().length() == 0) {
                        NewLeadActivity.aadharNumber = "";
//                        edtAadhaar.setError("Please Enter valid Aadhaar Number!");
                        NewLeadActivity.documents = "1";
                    } else if (edtAadhaar.getText().toString().length() == 0 && edtPAN.getText().toString().length() == 0) {
                        NewLeadActivity.panNUmber = "";
//                        edtAadhaar.setError("Please Enter Aadhaar or PAN Number!");
                        edtPAN.setError(null);
                        NewLeadActivity.documents = "0";
                    }

                } else {
//True
                    if (edtPAN.getText().toString().length() > 0) {
                        NewLeadActivity.aadharNumber = edtAadhaar.getText().toString();
                        edtAadhaar.setError(null);
                        NewLeadActivity.documents = "3";
                    } else {
                        NewLeadActivity.aadharNumber = edtAadhaar.getText().toString();
                        edtAadhaar.setError(null);
                        NewLeadActivity.documents = "1";
                    }
                }

                    checkAllFields();
            }
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

                if (isDocAvailabilityEnabled){

                    if (!edtPAN.getText().toString().toUpperCase().matches(Globle.panPattern)) {
                    if (edtAadhaar.getText().toString().length() > 0 && edtPAN.getText().toString().length() > 0) {
                        NewLeadActivity.panNUmber = "";
//                        edtAadhaar.setError(null);
//                        edtPAN.setError("please Enter Valid PAN number!");
                        NewLeadActivity.documents = "3";
                    } else if (edtAadhaar.getText().toString().length() == 0 && edtPAN.getText().toString().length() > 0) {
                        NewLeadActivity.panNUmber = "";
                        edtAadhaar.setError(null);
//                        edtPAN.setError("please Enter Valid PAN number!");
                        NewLeadActivity.documents = "2";
                    } else if (edtAadhaar.getText().toString().length() > 0 && edtPAN.getText().toString().length() == 0) {
                        NewLeadActivity.panNUmber = "";
//                        edtAadhaar.setError(null);
                        edtPAN.setError(null);
                        NewLeadActivity.documents = "1";
                    } else if (edtAadhaar.getText().toString().length() == 0 && edtPAN.getText().toString().length() == 0) {
                        NewLeadActivity.panNUmber = "";
                        edtAadhaar.setError(null);
//                        edtPAN.setError("Please Enter PAN Number!");
                        NewLeadActivity.documents = "0";
                    }
                } else {
                    //Pan True
                    if (edtAadhaar.getText().toString().length() > 0) {
                        NewLeadActivity.panNUmber = edtPAN.getText().toString().toUpperCase().trim();
//                        edtAadhaar.setError(null);
                        edtPAN.setError(null);
                        NewLeadActivity.documents = "3";
                    } else {
                        NewLeadActivity.panNUmber = edtPAN.getText().toString().toUpperCase().trim();
//                        edtAadhaar.setError(null);
                        edtPAN.setError(null);
                        NewLeadActivity.documents = "2";
                    }
                }
                    checkAllFields();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (viewPager.getCurrentItem() == 1) {
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {

                    }
                }
            });
        }

    }

//    private  void checkNextButtonstate()'

//    private void checkAllFields() {
//
//        switch (NewLeadActivity.documents) {
//            case "1":
//                if (!Globle.validateAadharNumber(NewLeadActivity.aadharNumber)) {
//                    mDocListener.onOffButtonsDocuments(false, true);
//                    //create method here
//                    edtAadhaar.setError("Please enter valid Aadhar");
//                    edtPAN.setError(null);
//                } else {
//                    edtAadhaar.setError(null);
//                    mDocListener.onOffButtonsDocuments(true, true);
//                }
//                break;
//
//            case "2":
//                if (!NewLeadActivity.panNUmber.matches(Globle.panPattern)) {
//                    mDocListener.onOffButtonsDocuments(false, true);
//                    edtPAN.setError("Plase enter valid Pan");
//                    edtAadhaar.setError(null);
//                } else{
//                    edtPAN.setError(null);
//                mDocListener.onOffButtonsDocuments(true, true);
//                }
//                break;
//
//            case "3":
//                if (!(Globle.validateAadharNumber(NewLeadActivity.aadharNumber) && NewLeadActivity.panNUmber.matches(Globle.panPattern)))
//                {
//                    mDocListener.onOffButtonsDocuments(false, true);
//                }
//                else{
//                    edtAadhaar.setError(null);
//                edtPAN.setError(null);
//                mDocListener.onOffButtonsDocuments(true, true);}
//
//                break;
//
//        }
//
//    }

    public static void checkAllFields() {

        if (NewLeadActivity.documents.equals("1")) {
            if (NewLeadActivity.aadharNumber.equals("") ||
                    !NewLeadActivity.panNUmber.equals("")) {
                mDocListener.onOffButtonsDocuments(false, true);
                if(NewLeadActivity.aadharNumber.equals(""))
                {
                    txtDocAvailableErrMsg.setVisibility(View.VISIBLE);
                    txtDocAvailableErrMsg.setText("please enter valid aadhaar number!");
                    edtAadhaar.requestFocus();
                }
            } else {
                txtDocAvailableErrMsg.setText(null);
                txtDocAvailableErrMsg.setVisibility(View.GONE);
                mDocListener.onOffButtonsDocuments(true, true);
            }
        } else if (NewLeadActivity.documents.equals("2")) {
            if (!NewLeadActivity.aadharNumber.equals("") || NewLeadActivity.panNUmber.equals("")) {
                mDocListener.onOffButtonsDocuments(false, true);
                if(NewLeadActivity.panNUmber.equals(""))
                {
                    txtDocAvailableErrMsg.setVisibility(View.VISIBLE);
                    txtDocAvailableErrMsg.setText("please enter valid pan number!");
                    edtPAN.requestFocus();
                }
            } else {
                txtDocAvailableErrMsg.setText(null);
                txtDocAvailableErrMsg.setVisibility(View.GONE);
                mDocListener.onOffButtonsDocuments(true, true);
            }
        } else if (NewLeadActivity.documents.equals("3")) {
            if (NewLeadActivity.aadharNumber.equals("") || NewLeadActivity.panNUmber.equals("")) {
                mDocListener.onOffButtonsDocuments(false, true);
                if(NewLeadActivity.aadharNumber.equals("") && NewLeadActivity.panNUmber.equals(""))
                {
                    txtDocAvailableErrMsg.setVisibility(View.VISIBLE);
                    txtDocAvailableErrMsg.setText("please enter either aadhaar or pan number!");
                    edtAadhaar.requestFocus();
                    edtPAN.requestFocus();
                }

            } else {
                txtDocAvailableErrMsg.setText(null);
                txtDocAvailableErrMsg.setVisibility(View.GONE);
                mDocListener.onOffButtonsDocuments(true, true);
            }
        } else if (NewLeadActivity.documents.equals("0") || NewLeadActivity.documents.equals("")) {
            if ((NewLeadActivity.aadharNumber.equals("") && NewLeadActivity.panNUmber.equals(""))) {
                mDocListener.onOffButtonsDocuments(false, true);
                txtDocAvailableErrMsg.setVisibility(View.VISIBLE);
                txtDocAvailableErrMsg.setText("please enter either aadhaar or pan number");
            } else {
                txtDocAvailableErrMsg.setText(null);
                txtDocAvailableErrMsg.setVisibility(View.GONE);
                mDocListener.onOffButtonsDocuments(false, true);
            }
        } else {
            if ((NewLeadActivity.aadharNumber.equals("") && NewLeadActivity.panNUmber.equals(""))) {
                mDocListener.onOffButtonsDocuments(false, true);
                txtDocAvailableErrMsg.setVisibility(View.VISIBLE);
                txtDocAvailableErrMsg.setText("please enter either aadhaar or pan number");
            } else {
                txtDocAvailableErrMsg.setText(null);
                txtDocAvailableErrMsg.setVisibility(View.GONE);
                mDocListener.onOffButtonsDocuments(true, true);
            }
        }

    }

    public static void validate() {
        switch (NewLeadActivity.documents) {
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
                if (!(Globle.validateAadharNumber(NewLeadActivity.aadharNumber) && NewLeadActivity.panNUmber.matches(Globle.panPattern)))
                    mDocListener.onDocumentFragmentInteraction(false, 1);
                else
                    mDocListener.onDocumentFragmentInteraction(true, 2);
                break;
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mDocListener != null) {
            mDocListener.onDocumentFragmentInteraction(true, 2);
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
        edtAadhaar.setText(NewLeadActivity.aadharNumber);
        edtPAN.setText(NewLeadActivity.panNUmber);
        if (isDocAvailabilityEnabled) {
            checkAllFields();
        }
        isDocAvailabilityEnabled = true;
    }

    public static void setDcoAvailabilityData() {
        try {
            isDocAvailabilityEnabled = false;
            edtAadhaar.setText(NewLeadActivity.aadharNumber);
            edtPAN.setText(NewLeadActivity.panNUmber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isDocAvailabilityEnabled = true;
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