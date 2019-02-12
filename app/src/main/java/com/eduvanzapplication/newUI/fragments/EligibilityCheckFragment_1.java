package com.eduvanzapplication.newUI.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.pojo.RelationShipPOJO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */

public class EligibilityCheckFragment_1 extends Fragment {

    Calendar cal;
    public static Context context;
    public static Fragment mFragment;
    Button buttonNext;
    Typeface typefaceFont, typefaceFontBold;
    public static EditText edtFirstName, edtMiddleName, edtLastName, edtCoMobileNo,edtEmail;
    public static TextView edtBirthDate;
    public static RadioGroup rgGender;
    public static RadioButton rbMale, rbFemale;
    public static String dateformate = "";
    public static Spinner spRelation;
    public static LinearLayout linRelationship, linMobileNo,linEmail;
    public String relationshipID = "";

    public static ArrayAdapter arrayAdapter_spRelation;
    public static ArrayList<String> spRelation_arrayList;
    public static ArrayList<RelationShipPOJO> relationShipPOJOArrayList;

    public EligibilityCheckFragment_1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eligibilitycheck_1, container, false);
        try {
            context = getContext();

            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            mFragment = new EligibilityCheckFragment_1();

            typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Regular.ttf");
            typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

            final FragmentTransaction transaction = getFragmentManager().beginTransaction();

            edtFirstName = (EditText) view.findViewById(R.id.edtFirstName);
            edtMiddleName = (EditText) view.findViewById(R.id.edtMiddleName);
            edtLastName = (EditText) view.findViewById(R.id.edtLastName);
            edtBirthDate = (TextView) view.findViewById(R.id.edtBirthDate);
            edtCoMobileNo = (EditText) view.findViewById(R.id.edtCoMobileNo);
            edtEmail = view.findViewById(R.id.edtEmail);

            rgGender = (RadioGroup) view.findViewById(R.id.rgGender);
            rbMale = (RadioButton) view.findViewById(R.id.rbMale);
            rbFemale = (RadioButton) view.findViewById(R.id.rbFemale);

            spRelation = (Spinner) view.findViewById(R.id.spRelation);

            linRelationship = (LinearLayout) view.findViewById(R.id.linRelationship);
            linMobileNo = (LinearLayout) view.findViewById(R.id.linMobileNo);
            linEmail = view.findViewById(R.id.linEmail);

            buttonNext = (Button) view.findViewById(R.id.button_next_eligiblityfragment1);
            buttonNext.setTypeface(typefaceFontBold);

//            spRelation_arrayList = new ArrayList<>();
//            spRelation_arrayList.add("Select Relationship");
//            spRelation_arrayList.add("Father");
//            spRelation_arrayList.add("Wife");
//            spRelation_arrayList.add("Sister");
//            spRelation_arrayList.add("Grand-Mother");
//            spRelation_arrayList.add("Mother");
//            spRelation_arrayList.add("Husband");
//            spRelation_arrayList.add("Brother");
//            spRelation_arrayList.add("Grand-Father");
//            spRelation_arrayList.add("Legal Guardian");
//            spRelation_arrayList.add("Other");
//
//            arrayAdapter_spRelation = new ArrayAdapter(context, R.layout.custom_layout_spinner, spRelation_arrayList);
//            spRelation.setAdapter(arrayAdapter_spRelation);

            if (MainApplication.isBorrower) {
                linRelationship.setVisibility(View.GONE);
                linMobileNo.setVisibility(View.GONE);
                linEmail.setVisibility(View.GONE);
            } else {
                relationshipwithapplicantApiCall();
                linRelationship.setVisibility(View.VISIBLE);
                linMobileNo.setVisibility(View.VISIBLE);
                linEmail.setVisibility(View.VISIBLE);
            }


            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, monthOfYear);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    int month = monthOfYear + 1;
                    String datenew = dayOfMonth + "/" + month + "/" + year;
                    dateformate = dateFormateSystem(datenew);
                    edtBirthDate.setText(dateformate);
                    MainApplication.coborrowerValue15 = edtBirthDate.getText().toString();
                    edtBirthDate.setTextColor(getResources().getColor(R.color.black));
                }

            };

            cal = Calendar.getInstance();
            edtBirthDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
                    DatePickerDialog data = new DatePickerDialog(context, date, cal
                            .get(Calendar.YEAR) - 18, cal.get(Calendar.MONTH),
                            1);
                    data.getDatePicker().setMaxDate(System.currentTimeMillis() - 1234564);
                    data.show();
                }
            });

            spRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spRelation.getSelectedItem().toString();
                        int count = relationShipPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (relationShipPOJOArrayList.get(i).relatioship.equalsIgnoreCase(text)) {
                                MainApplication.relationship_with_applicant = relationshipID = relationShipPOJOArrayList.get(i).id;
                            }
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

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( !edtFirstName.getText().toString().equals("") && !edtLastName.getText().toString().equals("") &&
                            !edtBirthDate.getText().toString().equals("") ) {

                        if (rgGender.getCheckedRadioButtonId() > 0) {

                            String gender = "";
                            if (rbMale.isChecked()) {
                                gender = "1";
                                MainApplication.gender_id = gender;
//                                MainApplication.cogender_id = gender;
                            }

                            if (rbFemale.isChecked()) {
                                gender = "2";
                                MainApplication.gender_id = gender;
//                                MainApplication.cogender_id = gender;
                            }

                            MainApplication.first_name = edtFirstName.getText().toString().trim();
                            MainApplication.middle_name = edtMiddleName.getText().toString().trim();
                            MainApplication.last_name = edtLastName.getText().toString().trim();
                            MainApplication.dob = edtBirthDate.getText().toString().trim();
                            MainApplication.cobrEmail = edtEmail.getText().toString().trim();

                            if (MainApplication.isBorrower) {
                                EligibilityCheckFragment_2 eligibilityCheckFragment_2 = new EligibilityCheckFragment_2();
                                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_2).commit();
                            } else {

                                if (!MainApplication.relationship_with_applicant.equalsIgnoreCase("0")) {

                                    if (edtCoMobileNo.getText().toString().equalsIgnoreCase("")) {
                                        edtCoMobileNo.setError("Mobile number is required");
                                        edtCoMobileNo.requestFocus();
                                    } else {
                                        if (edtCoMobileNo.getText().toString().length() == 10) {
                                            edtCoMobileNo.setError(null);
                                            MainApplication.mobile_number = edtCoMobileNo.getText().toString();
                                            EligibilityCheckFragment_2 eligibilityCheckFragment_2 = new EligibilityCheckFragment_2();
                                            transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_2).commit();
                                        } else {
                                            edtCoMobileNo.setError("Please enter correct mobile number");
                                        }
                                    }
                                    if (edtEmail.getText().toString().equals("")){
                                        edtEmail.setError(getString(R.string.email));
                                    }else
                                        MainApplication.cobrEmail = edtEmail.getText().toString().trim();


                                } else {
                                    if (spRelation.getSelectedItemPosition() <= 0) {
                                        setSpinnerError(spRelation, getString(R.string.please_select_relationship_with_borrower));
                                    }
                                }
                            }

//                            MainApplication.cofirst_name = edtFirstName.getText().toString().trim();
//                            MainApplication.comiddle_name = edtMiddleName.getText().toString().trim();
//                            MainApplication.colast_name = edtLastName.getText().toString().trim();
//                            MainApplication.codob = edtBirthDate.getText().toString().trim();
//                            MainApplication.comobile_number = edtCoMobileNo.getText().toString().trim();

                        } else {
                            rbFemale.setError(getString(R.string.you_need_to_select_gender));
                            rbFemale.requestFocus();
                        }
                    } else {
                        if (edtFirstName.getText().toString().equalsIgnoreCase("")) {
                            edtFirstName.setError(getString(R.string.first_name_is_required));
                            edtFirstName.requestFocus();
                        } else {
                            edtFirstName.setError(null);
                        }
                        if (edtLastName.getText().toString().equalsIgnoreCase("")) {
                            edtLastName.setError(getString(R.string.last_name_is_required));
                            edtLastName.requestFocus();
                        } else {
                            edtLastName.setError(null);

                        }
                        if (edtBirthDate.getText().toString().equalsIgnoreCase("")) {
                            edtBirthDate.setError(getString(R.string.birthdate_is_required));
                            edtBirthDate.requestFocus();
                        } else if (edtBirthDate.getText().toString().toLowerCase().equals("birthdate")) {
                            edtBirthDate.setError(getString(R.string.birthdate_is_required));
                            edtBirthDate.requestFocus();
                        } else {
                            edtBirthDate.setError(null);

                        }
                    }

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

        return view;
    }

    private void relationshipwithapplicantApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "dashboard/getAllRelationshipWithCoborrower";
            Map<String, String> params = new HashMap<String, String>();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getAllRelationshipWithCoborrower", params, MainApplication.auth_token);
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

    public void getAllRelationshipWithCoborrower(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    spRelation_arrayList = new ArrayList<>();
                    spRelation_arrayList.add("Select Relationship");
                    arrayAdapter_spRelation = new ArrayAdapter(context, R.layout.custom_layout_spinner, spRelation_arrayList);
                    spRelation.setAdapter(arrayAdapter_spRelation);
                    arrayAdapter_spRelation.notifyDataSetChanged();
                    spRelation.setSelection(0);

                } catch (Exception e) {
                    String className = this.getClass().getSimpleName();
                    String name = new Object() {
                    }.getClass().getEnclosingMethod().getName();
                    String errorMsg = e.getMessage();
                    String errorMsgDetails = e.getStackTrace().toString();
                    String errorLine = String.valueOf(e.getStackTrace()[0]);
                    Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
                }

            } else {
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {

                    JSONArray jsonArray3 = jsonData.getJSONArray("relationship");
                    spRelation_arrayList = new ArrayList<>();
                    relationShipPOJOArrayList = new ArrayList<>();
                    spRelation_arrayList.add("Select Relationship");
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        RelationShipPOJO borrowerCurrentStatePersonalPOJO = new RelationShipPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borrowerCurrentStatePersonalPOJO.relatioship = mJsonti.getString("relatioship");
                        spRelation_arrayList.add(mJsonti.getString("relatioship"));
                        borrowerCurrentStatePersonalPOJO.id = mJsonti.getString("id");
                        relationShipPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
                    }
                    arrayAdapter_spRelation = new ArrayAdapter(context, R.layout.custom_layout_spinner, spRelation_arrayList);
                    spRelation.setAdapter(arrayAdapter_spRelation);
                    arrayAdapter_spRelation.notifyDataSetChanged();

                    spRelation.setSelection(0);

                } else {
                }
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

    public static String dateFormateSystem(String date) {
        String dateformate2 = null;
        try {
            String birthDate = date;
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            Date dateformate = fmt.parse(birthDate);
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yyyy");
            dateformate2 = fmtOut.format(dateformate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateformate2;
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
