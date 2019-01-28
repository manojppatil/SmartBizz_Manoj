package com.eduvanzapplication.newUI.fragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.VolleyCallNew;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */


public class EligibilityCheckFragment_2 extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    public static Spinner documentSpinner;
    public static ArrayList<String> document_arrayList;
    public static ArrayAdapter arrayAdapter_document;
    Button buttonNext, buttonPrevious;
    EditText edtAadhaar, edtPan;
    Typeface typefaceFont, typefaceFontBold;
    LinearLayout linAadhaar, linPAN;

    public EligibilityCheckFragment_2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eligibilitycheck_2, container, false);
        try {
            context = getContext();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            mFragment = new EligibilityCheckFragment_2();

            MainApplication.currrentFrag = 2;

            typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
            typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

            final FragmentTransaction transaction = getFragmentManager().beginTransaction();

            edtAadhaar = (EditText) view.findViewById(R.id.edtAadhaar);
            edtPan = (EditText) view.findViewById(R.id.edtPan);
            documentSpinner = (Spinner) view.findViewById(R.id.spdocument);

            linAadhaar = (LinearLayout) view.findViewById(R.id.linAadhaar);
            linPAN = (LinearLayout) view.findViewById(R.id.linPAN);

            document_arrayList = new ArrayList<>();
            document_arrayList.add("Select Any");
            document_arrayList.add("Adhaar Card");
            document_arrayList.add("Pan Card");
            document_arrayList.add("Both");
            document_arrayList.add("Neither");
            arrayAdapter_document = new ArrayAdapter(context, R.layout.custom_layout_spinner, document_arrayList);
            documentSpinner.setAdapter(arrayAdapter_document);

            buttonNext = (Button) view.findViewById(R.id.button_next_eligiblityfragment2);
            buttonNext.setTypeface(typefaceFontBold);

            buttonPrevious = (Button) view.findViewById(R.id.button_previous_eligiblityfragment2);
            buttonPrevious.setTypeface(typefaceFontBold);

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!MainApplication.mainapp_userdocument.equalsIgnoreCase("0")) {

                        if (MainApplication.mainapp_userdocument.equalsIgnoreCase("1")) {

                            if (edtAadhaar.getText().toString().equalsIgnoreCase("")) {
                                edtAadhaar.setError(getString(R.string.adhaar_number_is_required));
                                edtAadhaar.requestFocus();
                            } else {
                                if (!(Globle.validateAadharNumber(edtAadhaar.getText().toString()))) {
                                    edtAadhaar.setError("Please enter valid adhaar number");
                                    edtAadhaar.requestFocus();
                                    return;
                                } else {

                                    MainApplication.aadhar_number = edtAadhaar.getText().toString().trim();
//                                    MainApplication.coaadhar_number = edtAadhaar.getText().toString().trim();

                                    EligibilityCheckFragment_3 eligibilityCheckFragment_3 = new EligibilityCheckFragment_3();
                                    transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_3).commit();
                                }

                            }
                        } else if (MainApplication.mainapp_userdocument.equalsIgnoreCase("2")) {

                            if (edtPan.getText().toString().equalsIgnoreCase("")) {
                                edtPan.setError(getString(R.string.pan_number_is_required));
                                edtPan.requestFocus();
                            } else {
                                if (!edtPan.getText().toString().matches(Globle.panPattern) && edtPan.getText().toString().length() == 10) {
                                    edtPan.setError("Please enter valid PAN number");
                                    edtPan.requestFocus();
                                    return;
                                } else {

                                    MainApplication.pan_number = edtPan.getText().toString().trim();
//                                    MainApplication.copan_number = edtPan.getText().toString().trim();

                                    EligibilityCheckFragment_3 eligibilityCheckFragment_3 = new EligibilityCheckFragment_3();
                                    transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_3).commit();
                                }
                            }
                        } else if (MainApplication.mainapp_userdocument.equalsIgnoreCase("3")) {

                            if (edtAadhaar.getText().toString().equalsIgnoreCase("")) {
                                edtAadhaar.setError(getString(R.string.adhaar_number_is_required));
                                edtAadhaar.requestFocus();
                            }else if (edtPan.getText().toString().equalsIgnoreCase("")) {
                                edtPan.setError(getString(R.string.pan_number_is_required));
                                edtPan.requestFocus();
                            }else{
                                if (!(Globle.validateAadharNumber(edtAadhaar.getText().toString()))) {
                                    edtAadhaar.setError("Please enter valid adhaar number");
                                    edtAadhaar.requestFocus();
                                    return;
                                } else {
                                    if (!edtPan.getText().toString().matches(Globle.panPattern) && edtPan.getText().toString().length() == 10) {
                                        edtPan.setError("Please enter valid PAN number");
                                        edtPan.requestFocus();
                                        return;
                                    } else {

                                        MainApplication.aadhar_number = edtAadhaar.getText().toString().trim();
//                                        MainApplication.coaadhar_number = edtAadhaar.getText().toString().trim();

                                        MainApplication.pan_number = edtPan.getText().toString().trim();
//                                        MainApplication.copan_number = edtPan.getText().toString().trim();

                                        EligibilityCheckFragment_3 eligibilityCheckFragment_3 = new EligibilityCheckFragment_3();
                                        transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_3).commit();
                                    }
                                }
                            }
                        } else {
                            EligibilityCheckFragment_3 eligibilityCheckFragment_3 = new EligibilityCheckFragment_3();
                            transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_3).commit();
                        }

                    } else {
                        if (documentSpinner.getSelectedItemPosition() <= 0) {
                            setSpinnerError(documentSpinner, getString(R.string.please_select_document_type));
                        }
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


        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }

        if (!MainApplication.mainapp_userdocument.equals("")) {

            if (MainApplication.mainapp_userdocument.equalsIgnoreCase("0")) {
                documentSpinner.setSelection(0);
            }
            if (MainApplication.mainapp_userdocument.equalsIgnoreCase("1")) {
                documentSpinner.setSelection(1);
            }
            if (MainApplication.mainapp_userdocument.equalsIgnoreCase("2")) {
                documentSpinner.setSelection(2);

            }
            if (MainApplication.mainapp_userdocument.equalsIgnoreCase("3")) {
                documentSpinner.setSelection(3);

            }
            if (MainApplication.mainapp_userdocument.equalsIgnoreCase("4")) {
                documentSpinner.setSelection(4);
            }

        }

        documentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = documentSpinner.getSelectedItem().toString();
                if (text.equalsIgnoreCase("Select Any")) {
                    MainApplication.mainapp_userdocument = "0";
                    MainApplication.has_aadhar_pan ="0";
                    try {
                        linAadhaar.setVisibility(View.GONE);
                        linPAN.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (text.equalsIgnoreCase("Adhaar Card")) {
                    MainApplication.mainapp_userdocument = "1";
                    MainApplication.has_aadhar_pan ="1";
                    try {
                        linAadhaar.setVisibility(View.VISIBLE);
                        linPAN.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (text.equalsIgnoreCase("Pan Card")) {
                    MainApplication.mainapp_userdocument = "2";
                    MainApplication.has_aadhar_pan ="2";
                    try {
                        linPAN.setVisibility(View.VISIBLE);
                        linAadhaar.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (text.equalsIgnoreCase("Both")) {
                    MainApplication.mainapp_userdocument = "3";
                    MainApplication.has_aadhar_pan ="3";
                    try {
                        linAadhaar.setVisibility(View.VISIBLE);
                        linPAN.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (text.equalsIgnoreCase("Neither")) {
                    MainApplication.mainapp_userdocument = "4";
                    MainApplication.has_aadhar_pan ="4";
                    try {
                        linAadhaar.setVisibility(View.GONE);
                        linPAN.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private void setSpinnerError(Spinner spinner, String error) {
        try {
            View selectedView = spinner.getSelectedView();
            if (selectedView != null && selectedView instanceof TextView) {
                spinner.requestFocus();
                TextView selectedTextView = (TextView) selectedView;
                selectedTextView.setError(getString(R.string.error)); // any name of the error will do
                selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
                selectedTextView.setText(error); // actual error message
                //spinner.performClick(); // to open the spinner list if error is found.
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }


}
