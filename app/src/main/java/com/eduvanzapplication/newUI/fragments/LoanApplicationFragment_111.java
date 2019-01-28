package com.eduvanzapplication.newUI.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceTypePersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentStatePersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerProfessionFinancePOJO;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.pqformfragments.pojo.LocationsPOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfCoursePOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfInsitituePOJO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.eduvanzapplication.newUI.MainApplication.TAG;


public class LoanApplicationFragment_111 extends Fragment {

    public static Context context;
    public static Fragment mFragment;

    //Borrower
    public static EditText edtCourseFeeBr, edtLoanAmtBr, edtFnameBr, edtMnameBr, edtLnameBr, edtEmailIdBr, edtPanBr, edtAadhaarBr,
            edtCompanyBr, edtAnnualSalBr, edtCurrentAddressBr, edtCurrentLandmarkBr, edtCurrentPincodeBr, edtPermanentAddressBr,
            edtPermanentLandmarkBr, edtPermanentPincodeBr;

    public static RadioGroup rgGenderBr;
    public static RadioButton rbMaleBr, rbFemaleBr;

    public static Spinner spInstituteBr, spInsLocationBr, spCourseBr, spProfessionBr, spCurrentCountryBr, spCurrentStateBr,
            spCurrentCityBr, spPermanentCountryBr, spPermanentStateBr, spPermanentCityBr;

    //CoBorrower
    public static EditText edtFnameCoBr, edtMnameCoBr, edtLnameCoBr, edtEmailIdCoBr, edtPanCoBr, edtAadhaarCoBr,
            edtCompanyCoBr, edtAnnualSalCoBr, edtCurrentAddressCoBr, edtCurrentLandmarkCoBr, edtCurrentPincodeCoBr,
            edtPermanentAddressCoBr, edtPermanentLandmarkCoBr, edtPermanentPincodeCoBr;

    public static RadioGroup rgGenderCoBr;
    public static RadioButton rbMaleCoBr, rbFemaleCoBr;

    public static Spinner spCurrentCountryCoBr, spCurrentStateCoBr, spCurrentCityCoBr, spPermanentCountryCoBr,
            spPermanentStateCoBr, spPermanentCityCoBr;

    public static TextView birthdaycalender, lable, textViewbirthday;
//    public static TextView birthdaycalender, lable, textViewbirthday;

    public static ArrayList<String> permanentstate_arrayList, permanentcity_arrayList;
    public static ArrayAdapter arrayAdapter_permanentCity, arrayAdapter_permanentState, arrayAdapter_permanentCountry;
    public static ArrayList<BorrowerCurrentCityPersonalPOJO> borrowerPermanentCityPersonalPOJOArrayList;
    public static ArrayAdapter arrayAdapter_currentResidencetype;
    public static ArrayList<String> currentResidencetype_arrayList;
    public static ArrayList<BorrowerCurrentResidenceTypePersonalPOJO> borrowerCurrentResidencePersonalPOJOArrayList;
    public static ArrayAdapter arrayAdapter_currentCity;
    public static ArrayList<String> currentcity_arrayList;
    public static ArrayList<BorrowerCurrentCityPersonalPOJO> borrowerCurrentCityPersonalPOJOArrayList;
    public static ArrayAdapter arrayAdapter_currentState;
    public static ArrayList<String> currentstate_arrayList;
    public static ArrayList<BorrowerCurrentStatePersonalPOJO> borrowerCurrentStatePersonalPOJOArrayList,
            borrowerPermanentStatePersonalPOJOArrayList;
    public static ArrayAdapter arrayAdapter_currentCountry;
    public static ArrayList<String> currentCountry_arrayList;
    public static ArrayList<BorrowerCurrentCountryPersonalPOJO> borrowerCurrentCountryPersonalPOJOArrayList;

    public static ArrayAdapter arrayAdapter_profession;
    public static ArrayList<String> profession_arrayList;

    public static ArrayAdapter arrayAdapter_NameOfInsititue;
    public static ArrayList<String> nameofinstitute_arrayList;
    public static ArrayList<NameOfInsitituePOJO> nameOfInsitituePOJOArrayList;
    public static ArrayAdapter arrayAdapter_NameOfCourse;
    public static ArrayList<String> nameofcourse_arrayList;
    public static ArrayList<NameOfCoursePOJO> nameOfCoursePOJOArrayList;
    public static ArrayAdapter arrayAdapter_locations;
    public static ArrayList<String> locations_arrayList;
    public static ArrayList<LocationsPOJO> locationPOJOArrayList;

    public static EditText editTextAdvancePayment, editTextAnualIncome, editTextNameofCompany;
    public static ArrayAdapter arrayAdapter;
    public static ArrayList<String> professionfinance_arratList;
    public static ArrayList<BorrowerProfessionFinancePOJO> borrowerProfessionFinancePOJOArrayList;
    public static RadioGroup radioGroup_profession, radioGroupEmployerType;
    public static FragmentTransaction transaction;
    public static ProgressBar progressBar;
    public static String userID = "", borrowerBackground = "", coBorrowerBackground = "";
    static View view;
    public String dateformate = "";
    public String permanentcityID = "", permanentstateID = "", permanentCountryID = "";
    public String currentResidencetypeID = "";
    public String currentcityID = "", currentstateID = "", currentcountryID = "";
    public String currentresidenceDurationID = "";
    public String degreeID = "";
    public String jobDurationID = "";
    String instituteID = "", courseID = "", locationID = "";
//    String instituteID = "", courseID = "", locationID = "", lead_id = "", application_id = "";

    Button buttonNext;
    TextView textView1, textView2, textView3;
    Calendar cal;
    MainApplication mainApplication;
    Typeface typeface;
    LinearLayout linearLayoutEmployed, linearLayoutLeftoff, linEmployed;
    public static CheckBox cbSameAsAboveBr;
    RelativeLayout relborrower, relCoborrower;
    LinearLayout linBorrowerForm, linCoCorrowerForm;
    TextView txtBorrowerArrowKey, txtCoBorrowerArrowKey;
    int borrowerVisiblity = 0, coborrowerVisiblity = 1;

    public String lead_id = "", has_coborrower = "", application_id = "", requested_loan_amount = "", institute_name = "",
            location_name = "", course_name = "", course_cost = "", fk_institutes_id = "", fk_insitutes_location_id = "",
            fk_course_id = "";

    public String Brapplicant_id = "", Brfk_lead_id = "", Brfk_applicant_type_id = "", Brfirst_name = "", Brmiddle_name = "",
            Brlast_name = "", Brhas_aadhar_pan = "", Brdob = "", Brpan_number = "", Braadhar_number = "", Brmarital_status = "",
            Brgender_id = "", Brmobile_number = "", Bremail_id = "", Brrelationship_with_applicant = "", Brprofession = "",
            Bremployer_type = "", Bremployer_name = "", Brannual_income = "", Brcurrent_employment_duration = "",
            Brtotal_employement_duration = "", Bremployer_mobile_number = "", Bremployer_landline_number = "",
            Broffice_landmark = "", Broffice_address = "", Broffice_address_city = "", Broffice_address_state = "",
            Broffice_address_country = "", Broffice_address_pin = "", Brhas_active_loan = "", BrEMI_Amount = "", Brkyc_landmark = "",
            Brkyc_address = "", Brkyc_address_city = "", Brkyc_address_state = "", Brkyc_address_country = "", Brkyc_address_pin = "",
            Bris_borrower_current_address_same_as = "", Bris_coborrower_current_address_same_as = "", Brcurrent_residence_type = "",
            Brcurrent_landmark = "", Brcurrent_address = "", Brcurrent_address_city = "", Brcurrent_address_state = "",
            Brcurrent_address_country = "", Brcurrent_address_pin = "", Brcurrent_address_rent = "",
            Brcurrent_address_stay_duration = "", Bris_borrower_permanent_address_same_as = "",
            Bris_coborrower_permanent_address_same_as = "", Brpermanent_residence_type = "", Brpermanent_landmark = "",
            Brpermanent_address = "", Brpermanent_address_city = "", Brpermanent_address_state = "",
            Brpermanent_address_country = "", Brpermanent_address_pin = "", Brpermanent_address_rent = "",
            Brpermanent_address_stay_duration = "", Brlast_completed_degree = "", Brscore_unit = "", Brcgpa = "", Brpercentage = "",
            Brpassing_year = "", Brgap_in_education = "", Brfull_name_pan_response = "", Brcreated_by_id = "", Brcreated_date_time = "",
            Brcreated_ip_address = "", Brmodified_by = "", Brmodified_date_time = "", Brmodified_ip_address = "", Bris_deleted = "";

    public String CoBrapplicant_id = "", CoBrfk_lead_id = "", CoBrfk_applicant_type_id = "", CoBrfirst_name = "", CoBrmiddle_name = "",
            CoBrlast_name = "", CoBrhas_aadhar_pan = "", CoBrdob = "", CoBrpan_number = "", CoBraadhar_number = "",
            CoBrmarital_status = "", CoBrgender_id = "", CoBrmobile_number = "", CoBremail_id = "",
            CoBrrelationship_with_applicant = "", CoBrprofession = "", CoBremployer_type = "", CoBremployer_name = "",
            CoBrannual_income = "", CoBrcurrent_employment_duration = "", CoBrtotal_employement_duration = "",
            CoBremployer_mobile_number = "", CoBremployer_landline_number = "", CoBroffice_landmark = "",
            CoBroffice_address = "", CoBroffice_address_city = "", CoBroffice_address_state = "", CoBroffice_address_country = "",
            CoBroffice_address_pin = "", CoBrhas_active_loan = "", CoBrEMI_Amount = "", CoBrkyc_landmark = "", CoBrkyc_address = "",
            CoBrkyc_address_city = "", CoBrkyc_address_state = "", CoBrkyc_address_country = "", CoBrkyc_address_pin = "",
            CoBris_borrower_current_address_same_as = "", CoBris_coborrower_current_address_same_as = "",
            CoBrcurrent_residence_type = "", CoBrcurrent_landmark = "", CoBrcurrent_address = "", CoBrcurrent_address_city = "",
            CoBrcurrent_address_state = "", CoBrcurrent_address_country = "", CoBrcurrent_address_pin = "",
            CoBrcurrent_address_rent = "", CoBrcurrent_address_stay_duration = "", CoBris_borrower_permanent_address_same_as = "",
            CoBris_coborrower_permanent_address_same_as = "", CoBrpermanent_residence_type = "", CoBrpermanent_landmark = "",
            CoBrpermanent_address = "", CoBrpermanent_address_city = "", CoBrpermanent_address_state = "",
            CoBrpermanent_address_country = "", CoBrpermanent_address_pin = "", CoBrpermanent_address_rent = "",
            CoBrpermanent_address_stay_duration = "", CoBrlast_completed_degree = "", CoBrscore_unit = "", CoBrcgpa = "",
            CoBrpercentage = "", CoBrpassing_year = "", CoBrgap_in_education = "", CoBrfull_name_pan_response = "",
            CoBrcreated_by_id = "", CoBrcreated_date_time = "", CoBrcreated_ip_address = "", CoBrmodified_by = "",
            CoBrmodified_date_time = "", CoBrmodified_ip_address = "", CoBris_deleted = "";

    public String lead_status_id = "", fk_lead_id = "", lead_status = "", lead_sub_status = "", current_stage = "",
            current_status = "", lead_drop_status = "", lead_reject_status = "", lead_initiated_datetime = "",
            is_lead_owner_added = "", lead_owner_added_datetime = "", lead_owner_added_by = "", is_lead_counsellor_added = "",
            lead_counsellor_added_datetime = "", lead_counsellor_added_by = "", is_kyc_details_filled = "",
            kyc_details_filled_datetime = "", kyc_details_filled_by = "", coborrower_added_datetime = "", coborrower_added_by_id = "",
            is_detailed_info_filled = "", detailed_info_filled_datetime = "", detailed_info_filled_by_id = "",
            approval_request_sales_status = "", approval_request_sales_status_datetime = "", approval_request_sales_status_by_id = "",
            list_of_LAF_info_pending = "", list_of_LAF_info_filled = "", IPA_status = "", IPA_datetime = "", IPA_by_id = "",
            docs_upload_status = "", docs_upload_datetime = "", list_of_uplaoded_docs = "", list_of_pendingdocs = "",
            docs_verification_status = "", docs_verification_datetime = "", credit_approval_request_status = "",
            credit_approval_request_status_datetime = "", credit_approval_request_status_by_id = "", applicant_ekyc_status = "",
            applicant_ekyc_datetime = "", co_applicant_ekyc_status = "", co_applicant_ekyc_datetime = "",
            credit_assessment_status = "", credit_assessment_by_id = "", credit_assessment_datetime = "",
            loan_product_selection_status = "", loan_product_by_id = "", loan_product_datetime = "", underwriting_status = "",
            underwriting_by_id = "", underwriting_datetime = "", is_processing_fees_set = "", processing_fees_set_datetime = "",
            processing_fees_set_by_id = "", processing_fees_paid = "", processing_fees_paid_datetime = "",
            processing_fees_paid_by = "", lender_creation_status = "", lender_creation_modified_datetime = "",
            lender_creation_modified_by = "", amort_creation_status = "", amort_creation_modified_datetime = "",
            amort_creation_modified_by = "", borrower_pan_ekyc_response = "", borrower_aadhar_ekyc_response = "",
            borrower_pan_ekyc_status = "", borrower_aadhar_ekyc_status = "", coborrower_pan_ekyc_response = "",
            coborrower_aadhar_ekyc_response = "", coborrower_aadhar_ekyc_status = "", coborrower_pan_ekyc_status = "",
            is_cam_uploaded = "", is_finbit_uploaded = "", is_exception_uploaded = "", is_loan_agreement_uploaded = "",
            loan_agreement_uploaded_by = "", applicant_pan_verified_by = "", applicant_pan_verified_on = "", created_date_time = "", created_ip_address = "",
            modified_by = "", modified_date_time = "", modified_ip_address = "", is_deleted = "", borrower_required_docs = "",
            co_borrower_required_docs = "", co_borrower_pending_docs = "", borrower_extra_required_docs = "",
            co_borrower_extra_required_docs = "", id = "", status_name = "", stage_id = "";

    public LoanApplicationFragment_111() {
        // Required empty public constructor
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kycdetails, container, false);

        try {
            context = getContext();
            mainApplication = new MainApplication();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            mFragment = new LoanApplicationFragment_111();
            transaction = getFragmentManager().beginTransaction();
            MainApplication.currrentFrag = 1;
            typeface = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");

            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            userID = sharedPreferences.getString("logged_id", "null");
            borrowerBackground = sharedPreferences.getString("borrowerBackground_dark", "0");
            coBorrowerBackground = sharedPreferences.getString("coBorrowerBackground_dark", "0");

            setViews();

            if (borrowerVisiblity == 0) {
                linBorrowerForm.setVisibility(View.VISIBLE);
                borrowerVisiblity = 1;
                txtBorrowerArrowKey.setText(getResources().getString(R.string.up));
                txtBorrowerArrowKey.setTypeface(typeface);
            } else if (borrowerVisiblity == 1) {
                linBorrowerForm.setVisibility(View.GONE);
                borrowerVisiblity = 0;
                txtBorrowerArrowKey.setText(getResources().getString(R.string.down));
                txtBorrowerArrowKey.setTypeface(typeface);
            }
            if (coborrowerVisiblity == 0) {
                linCoCorrowerForm.setVisibility(View.VISIBLE);
                coborrowerVisiblity = 1;
                txtCoBorrowerArrowKey.setText(getResources().getString(R.string.up));
                txtCoBorrowerArrowKey.setTypeface(typeface);
            } else if (coborrowerVisiblity == 1) {
                linCoCorrowerForm.setVisibility(View.GONE);
                coborrowerVisiblity = 0;
                txtCoBorrowerArrowKey.setText(getResources().getString(R.string.down));
                txtCoBorrowerArrowKey.setTypeface(typeface);
            }

            instituteApiCall();

            relborrower.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (borrowerVisiblity == 0) {
                        linBorrowerForm.setVisibility(View.VISIBLE);
                        borrowerVisiblity = 1;
                        txtBorrowerArrowKey.setText(getResources().getString(R.string.up));
                        txtBorrowerArrowKey.setTypeface(typeface);
                    } else if (borrowerVisiblity == 1) {
                        linBorrowerForm.setVisibility(View.GONE);
                        borrowerVisiblity = 0;
                        txtBorrowerArrowKey.setText(getResources().getString(R.string.down));
                        txtBorrowerArrowKey.setTypeface(typeface);
                    }

                }
            });

            relCoborrower.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (coborrowerVisiblity == 0) {
                        linCoCorrowerForm.setVisibility(View.VISIBLE);
                        coborrowerVisiblity = 1;
                        txtCoBorrowerArrowKey.setText(getResources().getString(R.string.up));
                        txtCoBorrowerArrowKey.setTypeface(typeface);
                    } else if (coborrowerVisiblity == 1) {
                        linCoCorrowerForm.setVisibility(View.GONE);
                        coborrowerVisiblity = 0;
                        txtCoBorrowerArrowKey.setText(getResources().getString(R.string.down));
                        txtCoBorrowerArrowKey.setTypeface(typeface);
                    }

                }
            });

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
//                public void onClick(View v) {
//
//                    //                boolean isIDNull = !edtAadhaarBr.getText().toString().equals("") ||
//                    //              !panno.getText().toString().equals("");
//
//                    if (!fname.getText().toString().equals("") && !lname.getText().toString().equals("") &&
//                            !textViewbirthday.getText().toString().equals("") &&
//                            !currentaddress.getText().toString().equals("") &&
//                            !currentpincode.getText().toString().equals("") &&
//                            !permanentaddress.getText().toString().equals("") &&
//                            !permanentpincode.getText().toString().equals("") &&
//                            !editTextPassingYear.getText().toString().equals("") &&
//                            !editTextAdvancePayment.getText().toString().equals("") &&
//                            !edtAadhaarBr.getText().toString().equals("") &&
//                            !panno.getText().toString().equals("")) {
//
//                        //                    !editTextAnualIncome.getText().toString().equals("") &&
//                        //                    !editTextNameofCompany.getText().toString().equals("") &&
//
//                        if (rgGenderBr.getCheckedRadioButtonId() > 0) {
//                            rbFemaleBr.setError(null);
//
//                            if (radioGroupMaritailStatus.getCheckedRadioButtonId() > 0) {
//                                radioButtonSingle.setError(null);
//
//                                if (radioGroupGaps.getCheckedRadioButtonId() > 0) {
//                                    radioButtonGapNO.setError(null);
//
//                                    if (radioGroup.getCheckedRadioButtonId() > 0) {
//                                        radioButtonisCgpaNo.setError(null);
//
//                                        if (radioGroup_profession.getCheckedRadioButtonId() > 0) {
//                                            radioButtonisStudent.setError(null);
//
//                                            if (radioGroupPreviousEmi.getCheckedRadioButtonId() > 0) {
//                                                radioButtonPreviousEmiNo.setError(null);
//
//                                                //                                            if (!previousemi.getText().toString().equalsIgnoreCase("")) {
//
//                                                if (!currentcityID.equalsIgnoreCase("") && !currentstateID.equalsIgnoreCase("") &&
//                                                        !currentcountryID.equalsIgnoreCase("") && !permanentcityID.equalsIgnoreCase("") &&
//                                                        !permanentstateID.equalsIgnoreCase("") && !permanentCountryID.equalsIgnoreCase("") &&
//                                                        !currentResidencetypeID.equalsIgnoreCase("") && !currentresidenceDurationID.equalsIgnoreCase("") &&
//                                                        !degreeID.equalsIgnoreCase("")) {
//
//                                                    /**API CALL**/
//                                                    try {
//                                                        String maritialstatus = "";
//                                                        if (radioButtonMarried.isChecked()) {
//                                                            maritialstatus = "1";
//                                                        }
//                                                        if (radioButtonSingle.isChecked()) {
//                                                            maritialstatus = "2";
//                                                        }
//
//                                                        String gender = "";
//                                                        if (rbMaleBr.isChecked()) {
//                                                            gender = "1";
//                                                        }
//                                                        if (rbFemaleBr.isChecked()) {
//                                                            gender = "2";
//                                                        }
//
//                                                        String gap = "";
//                                                        if (radioButtonGapYes.isChecked()) {
//                                                            gap = "1";
//                                                        }
//                                                        if (radioButtonGapNO.isChecked()) {
//                                                            gap = "2";
//                                                        }
//
//                                                        String empType = "";
//                                                        if (radioButtonGovernment.isChecked()) {
//                                                            empType = "0";
//                                                        }
//                                                        if (radioButtonPrivate.isChecked()) {
//                                                            empType = "1";
//                                                        }
//
//                                                        progressBar.setVisibility(View.VISIBLE);
//                                                        String url = MainApplication.mainUrl + "algo/setBorrowerLoanDetails";
//                                                        Map<String, String> params = new HashMap<String, String>();
//                                                        params.put("logged_id", userID);
//                                                        params.put("student_address_type", currentResidencetypeID);
//                                                        params.put("student_monthly_rent", monthlyrent.getText().toString());
//                                                        params.put("student_current_address", currentaddress.getText().toString());
//                                                        params.put("student_current_city", currentcityID);
//                                                        params.put("student_current_state", currentstateID);
//                                                        params.put("student_current_country", currentcountryID);
//                                                        params.put("student_current_pincode", currentpincode.getText().toString());
//                                                        params.put("student_permanent_address", permanentaddress.getText().toString());
//                                                        params.put("student_permanent_city", permanentcityID);
//                                                        params.put("student_permanent_state", permanentstateID);
//                                                        params.put("student_permanent_country", permanentCountryID);
//                                                        params.put("student_gender", gender);
//                                                        params.put("any_gaps", gap);
//                                                        params.put("student_permanent_pincode", permanentpincode.getText().toString());
//                                                        params.put("borrower_previous_emi_amount", previousemi.getText().toString());
//                                                        params.put("student_first_name", fname.getText().toString());
//                                                        params.put("student_last_name", lname.getText().toString());
//                                                        params.put("student_dob", textViewbirthday.getText().toString());
//                                                        params.put("student_married", maritialstatus);
//                                                        params.put("student_pan_card_no", panno.getText().toString());
//                                                        params.put("student_aadhar_card_no", edtAadhaarBr.getText().toString());
//                                                        params.put("student_current_address_duration", currentresidenceDurationID);
//                                                        params.put("borrower_employer_type", empType);
//
//                                                        /** EDUCATION**/
//                                                        String isCgpa = "";
//                                                        if (radioButtonisCgpaYes.isChecked()) {
//                                                            isCgpa = "1";
//                                                        }
//                                                        if (radioButtonisCgpaNo.isChecked()) {
//                                                            isCgpa = "2";
//                                                        }
//
//                                                        params.put("last_degree_completed", degreeID);
//                                                        params.put("is_cgpa", isCgpa);
//                                                        params.put("last_degree_percentage", editTextIsCgpaNo.getText().toString());
//                                                        params.put("last_degree_cgpa", editTextIsCgpaYes.getText().toString());
//                                                        params.put("last_degree_year_completion", editTextPassingYear.getText().toString());
//
//                                                        /** PROFESSION AND FINANCIAL **/
//                                                        String isWorking = "";
//                                                        if (radioButtonisEmployed.isChecked()) {
//                                                            isWorking = "1";
//                                                        }
//                                                        if (radioButtonisStudent.isChecked()) {
//                                                            isWorking = "2";
//                                                        }
//
//                                                        String previousEmi = "";
//                                                        if (radioButtonPreviousEmiYes.isChecked()) {
//                                                            previousEmi = "1";
//                                                        } else if (radioButtonPreviousEmiNo.isChecked()) {
//                                                            previousEmi = "0";
//                                                        }
//
//                                                        params.put("is_student_working", isWorking);
//                                                        params.put("student_working_organization", editTextNameofCompany.getText().toString());
//                                                        params.put("working_organization_since", jobDurationID);
//                                                        params.put("student_income", editTextAnualIncome.getText().toString());
//                                                        params.put("advance_payment", editTextAdvancePayment.getText().toString());
//                                                        params.put("borrower_has_any_emi", previousEmi);
//                                                        params.put("ip_address", Utils.getIPAddress(true));
//
//                                                        MainApplication.borrowerValue1 = currentResidencetypeID;
//                                                        MainApplication.borrowerValue2 = monthlyrent.getText().toString();
//                                                        MainApplication.borrowerValue3 = currentaddress.getText().toString();
//                                                        MainApplication.borrowerValue4 = currentcityID;
//                                                        MainApplication.borrowerValue5 = currentstateID;
//                                                        MainApplication.borrowerValue6 = currentcountryID;
//                                                        MainApplication.borrowerValue7 = currentpincode.getText().toString();
//                                                        MainApplication.borrowerValue8 = permanentaddress.getText().toString();
//                                                        MainApplication.borrowerValue9 = permanentcityID;
//                                                        MainApplication.borrowerValue10 = permanentstateID;
//                                                        MainApplication.borrowerValue11 = permanentCountryID;
//                                                        MainApplication.borrowerValue12 = permanentpincode.getText().toString();
//                                                        MainApplication.borrowerValue31 = previousemi.getText().toString();
//                                                        MainApplication.borrowerValue32 = gender;
//                                                        MainApplication.borrowerValue33 = gap;
//                                                        MainApplication.borrowerValue13 = fname.getText().toString();
//                                                        MainApplication.borrowerValue14 = lname.getText().toString();
//                                                        MainApplication.borrowerValue15 = textViewbirthday.getText().toString();
//                                                        MainApplication.borrowerValue16 = maritialstatus;
//                                                        MainApplication.borrowerValue17 = panno.getText().toString();
//                                                        MainApplication.borrowerValue18 = edtAadhaarBr.getText().toString();
//                                                        MainApplication.borrowerValue19 = degreeID;
//                                                        MainApplication.borrowerValue20 = isCgpa;
//                                                        MainApplication.borrowerValue21 = editTextIsCgpaNo.getText().toString();
//                                                        MainApplication.borrowerValue22 = editTextIsCgpaYes.getText().toString();
//                                                        MainApplication.borrowerValue23 = editTextPassingYear.getText().toString();
//                                                        MainApplication.borrowerValue24 = isWorking;
//                                                        MainApplication.borrowerValue25 = editTextNameofCompany.getText().toString();
//                                                        MainApplication.borrowerValue26 = jobDurationID;
//                                                        MainApplication.borrowerValue27 = editTextAnualIncome.getText().toString();
//                                                        MainApplication.borrowerValue28 = editTextAdvancePayment.getText().toString();
//                                                        MainApplication.borrowerValue30 = previousEmi;
//                                                        MainApplication.borrowerValue34 = empType;
//                                                        if (!Globle.isNetworkAvailable(context)) {
//                                                            Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//
//                                                        } else {
//
//                                                            VolleyCallNew volleyCall = new VolleyCallNew();
//                                                            volleyCall.sendRequest(context, url, null, mFragment, "sendborrowerDetails", params, MainApplication.auth_token);//http://159.89.204.41/eduvanzApi/algo/setBorrowerLoanDetails
//                                                        }
//
//                                                    } catch (Exception e) {
//                                                        String className = this.getClass().getSimpleName();
//                                                        String name = new Object() {
//                                                        }.getClass().getEnclosingMethod().getName();
//                                                        String errorMsg = e.getMessage();
//                                                        String errorMsgDetails = e.getStackTrace().toString();
//                                                        String errorLine = String.valueOf(e.getStackTrace()[0]);
//                                                        Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
//                                                    }
//                                                } else {
//                                                    if (spinnerCurrentResidenceDuration.getSelectedItemPosition() <= 0) {
//                                                        setSpinnerError(spinnerCurrentResidenceDuration, getString(R.string.please_select_duration_of_stay_at_current_address));
//                                                        spinnerCurrentResidenceDuration.requestFocus();
//
//                                                    }
//                                                    if (spinnerLastCompletedDegree.getSelectedItemPosition() <= 0) {
//                                                        setSpinnerError(spinnerLastCompletedDegree, getString(R.string.please_select_last_degree_completed));
//                                                        spinnerLastCompletedDegree.requestFocus();
//
//                                                    }
//                                                    if (spinnerJobDuration.getSelectedItemPosition() <= 0) {
//                                                        setSpinnerError(spinnerJobDuration, getString(R.string.please_select_duration_of_job_business));
//                                                        spinnerJobDuration.requestFocus();
//
//                                                    }
//                                                    Toast.makeText(context, R.string.please_fill_up_all_the_details_to_continue, Toast.LENGTH_LONG).show();
//                                                }
//
//                                                //                                            } else {
//                                                //                                                Toast.makeText(context, "Please fill up previous EMI to continue", Toast.LENGTH_LONG).show();
//                                                //
//                                                //                                            }
//
//                                            } else {
//                                                radioButtonPreviousEmiNo.setError(getString(R.string.you_need_to_select_previous_emi_status));
//                                            }
//
//                                        } else {
//                                            radioButtonisStudent.setError(getString(R.string.you_need_to_select_profession_status));
//                                            radioButtonisStudent.requestFocus();
//                                            fname.requestFocus();
//                                        }
//                                    } else {
//                                        radioButtonisCgpaNo.setError(getString(R.string.you_need_to_select_CGPA_status));
//                                        radioButtonisCgpaNo.requestFocus();
//                                    }
//
//                                } else {
//                                    radioButtonGapNO.setError(getString(R.string.you_need_to_select_education_gap_status));
//                                    radioButtonGapNO.requestFocus();
//                                }
//                            } else {
//                                radioButtonSingle.setError(getString(R.string.you_need_to_select_marital_status));
//                                radioButtonSingle.requestFocus();
//                            }
//
//                        } else {
//                            rbFemaleBr.setError(getString(R.string.you_need_to_select_gender));
//                            rbFemaleBr.requestFocus();
//                        }
//                    } else {
//
//                        if (fname.getText().toString().equalsIgnoreCase("")) {
//                            fname.setError(getString(R.string.first_name_is_required));
//                            fname.requestFocus();
//                        } else {
//                            fname.setError(null);
//
//                        }
//                        if (lname.getText().toString().equalsIgnoreCase("")) {
//                            lname.setError(getString(R.string.last_name_is_required));
//                            lname.requestFocus();
//                        } else {
//                            lname.setError(null);
//
//                        }
//                        if (textViewbirthday.getText().toString().equalsIgnoreCase("")) {
//                            textViewbirthday.setError(getString(R.string.birthdate_is_required));
//                            textViewbirthday.requestFocus();
//                        } else if (textViewbirthday.getText().toString().toLowerCase().equals("birthdate")) {
//                            textViewbirthday.setError(getString(R.string.birthdate_is_required));
//                            textViewbirthday.requestFocus();
//                        } else {
//                            textViewbirthday.setError(null);
//
//                        }
//
//                        if (edtAadhaarBr.getText().toString().equalsIgnoreCase("")) {
//                            edtAadhaarBr.setError(getString(R.string.adhaar_number_is_required));
//                            edtAadhaarBr.requestFocus();
//                        } else {
//                            edtAadhaarBr.setError(null);
//
//                        }
//                        if (panno.getText().toString().equalsIgnoreCase("")) {
//                            panno.setError(getString(R.string.pan_number_is_required));
//                            panno.requestFocus();
//                        } else {
//                            panno.setError(null);
//
//                        }
//
//                        //                    if (edtAadhaarBr.getText().toString().equalsIgnoreCase("") && panno.getText().toString().equalsIgnoreCase("")) {
//                        //                        edtAadhaarBr.setError("Aadhaar Number is Required");
//                        //                        edtAadhaarBr.requestFocus();
//                        //                    }
//                        //                    else {
//                        //                        edtAadhaarBr.setError(null);
//                        //
//                        //                    }
//                        //                    if (panno.getText().toString().equalsIgnoreCase("") && edtAadhaarBr.getText().toString().equalsIgnoreCase("")) {
//                        //                        panno.setError("PAN number is required is Required");
//                        //                        panno.requestFocus();
//                        //                    }else {
//                        //                        panno.setError(null);
//                        //
//                        //                    }
//
//                        if (currentaddress.getText().toString().equalsIgnoreCase("")) {
//                            currentaddress.setError(getString(R.string.current_address_is_required));
//                            currentaddress.requestFocus();
//                        } else {
//                            currentaddress.setError(null);
//
//                        }
//
//                        if (currentpincode.getText().toString().equalsIgnoreCase("")) {
//                            currentpincode.setError(getString(R.string.current_pin_code_is_required));
//                            currentpincode.requestFocus();
//                        } else {
//                            currentpincode.setError(null);
//
//                        }
//
//                        if (permanentaddress.getText().toString().equalsIgnoreCase("")) {
//                            permanentaddress.setError(getString(R.string.permanent_address_is_required));
//                            permanentaddress.requestFocus();
//                        } else {
//                            permanentaddress.setError(null);
//
//                        }
//
//                        if (permanentpincode.getText().toString().equalsIgnoreCase("")) {
//                            permanentpincode.setError(getString(R.string.permanent_pin_is_required));
//                            permanentpincode.requestFocus();
//                        } else {
//                            permanentpincode.setError(null);
//
//                        }
//
//                        //                    if (previousemi.getText().toString().equalsIgnoreCase("")) {
//                        //                        previousemi.setError("Previous EMI is Required");
//                        //                    }
//
//                        if (editTextPassingYear.getText().toString().equalsIgnoreCase("")) {
//                            editTextPassingYear.setError(getString(R.string.passing_year_is_required));
//                            editTextPassingYear.requestFocus();
//                        } else {
//                            editTextPassingYear.setError(null);
//
//                        }
//
//                        if (editTextNameofCompany.getText().toString().equalsIgnoreCase("")) {
//                            editTextNameofCompany.setError(getString(R.string.name_of_the_company_is_required));
//                            editTextNameofCompany.requestFocus();
//                        } else {
//                            editTextNameofCompany.setError(null);
//
//                        }
//                        if (editTextAnualIncome.getText().toString().equalsIgnoreCase("")) {
//                            editTextAnualIncome.setError(getString(R.string.annual_income_is_required));
//                            editTextAnualIncome.requestFocus();
//                        } else {
//                            editTextAnualIncome.setError(null);
//
//                        }
//                        if (editTextAdvancePayment.getText().toString().equalsIgnoreCase("")) {
//                            editTextAdvancePayment.setError(getString(R.string.advance_payment_is_required));
//                            editTextAdvancePayment.requestFocus();
//                        } else if (spinnerCurrentResidenceType.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spinnerCurrentResidenceType, getString(R.string.please_select_residency_type));
//                            spinnerCurrentResidenceType.requestFocus();
//
//                        } else if (spCurrentCountryBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spCurrentCountryBr, getString(R.string.please_select_current_country));
//
//                        } else if (spCurrentStateBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spCurrentStateBr, getString(R.string.please_select_current_state));
//
//                        } else if (spCurrentCityBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spCurrentCityBr, getString(R.string.please_select_current_city));
//
//                        } else if (spPermanentCountryBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spPermanentCountryBr, getString(R.string.please_select_permanent_country));
//
//                        } else if (spPermanentStateBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spPermanentStateBr, getString(R.string.please_select_permanent_state));
//
//                        } else if (spPermanentCountryBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spPermanentCountryBr, getString(R.string.please_select_permanent_city));
//
//                        }
//                        if (spinnerCurrentResidenceDuration.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spinnerCurrentResidenceDuration, getString(R.string.please_select_duration_of_stay_at_current_address));
//                            spinnerCurrentResidenceDuration.requestFocus();
//
//                        }
//                        if (spinnerLastCompletedDegree.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spinnerLastCompletedDegree, getString(R.string.please_select_last_degree_completed));
//                            spinnerLastCompletedDegree.requestFocus();
//
//                        }
//                        if (spinnerJobDuration.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spinnerJobDuration, getString(R.string.please_select_duration_of_job_business));
//                            spinnerJobDuration.requestFocus();
//                        }
//
//                    }
//                }
            });

            cbSameAsAboveBr = view.findViewById(R.id.cbSameAsAboveBr);

            lable = (TextView) view.findViewById(R.id.lable_personal_fqform);
            textViewbirthday = (TextView) view.findViewById(R.id.userInfoEdit_birthdateborrower);
            birthdaycalender = (TextView) view.findViewById(R.id.birthdayCalender_fqform);
            birthdaycalender.setTypeface(typeface);
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
                    textViewbirthday.setText(dateformate);
                    MainApplication.borrowerValue15 = dateformate;
                    textViewbirthday.setTextColor(getResources().getColor(R.color.black));
                    lable.setVisibility(View.VISIBLE);
                }

            };
            cal = Calendar.getInstance();

            birthdaycalender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
                    DatePickerDialog data = new DatePickerDialog(context, date, cal
                            .get(Calendar.YEAR) - 18, 1,
                            1);
                    data.getDatePicker().setMaxDate(System.currentTimeMillis() - 1234564);
                    data.show();
                }
            });

            /** EDIT TEXT CHANGE LISTENER FOR PARTIAL SAVING **/

            edtFnameBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue13 = edtFnameBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtLnameBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue14 = edtLnameBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtAadhaarBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue18 = edtAadhaarBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtPanBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue17 = edtPanBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtCurrentAddressBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue3 = edtCurrentAddressBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtCurrentLandmarkBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue3 = edtCurrentLandmarkBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtCurrentPincodeBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue7 = edtCurrentPincodeBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtPermanentAddressBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue8 = edtPermanentAddressBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtPermanentLandmarkBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue8 = edtPermanentLandmarkBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtPermanentPincodeBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue12 = edtPermanentPincodeBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


            edtAnnualSalBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue27 = editTextAnualIncome.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtCompanyBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue25 = editTextNameofCompany.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            /** END OF EDIT TEXT CHANGE LISTNER FOR PARTIAL SAVING**/

            /** SPINNER CLICK **/

            //Spinner Institute
            spInstituteBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("I_________D", "onItemClick: " + " CLICK HUA");

                    String text = spInstituteBr.getSelectedItem().toString();
                    int count = nameOfInsitituePOJOArrayList.size();
                    Log.e("TAG", "count: " + count);
                    for (int i = 0; i < count; i++) {
                        if (nameOfInsitituePOJOArrayList.get(i).instituteName.equalsIgnoreCase(text)) {
                            MainApplication.mainapp_instituteID = instituteID = nameOfInsitituePOJOArrayList.get(i).instituteID;
                            Log.e("I_________D", "onItemClick: " + instituteID);
                        }
                    }
                    courseApiCall();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spCourseBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("I_________D", "onItemClick: ");
                    String text = spCourseBr.getSelectedItem().toString();
                    int count = nameOfCoursePOJOArrayList.size();
                    Log.e("TAG", "count: " + count);
                    for (int i = 0; i < count; i++) {
                        if (nameOfCoursePOJOArrayList.get(i).courseName.equalsIgnoreCase(text)) {
                            MainApplication.mainapp_courseID = courseID = nameOfCoursePOJOArrayList.get(i).courseID;
                            Log.e("I_________D", "onItemClick: " + courseID);
                        }
                    }
                    locationApiCall();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spInsLocationBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String text = spInsLocationBr.getSelectedItem().toString();
                    int count = locationPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (locationPOJOArrayList.get(i).locationName.equalsIgnoreCase(text)) {
                            MainApplication.mainapp_locationID = locationID = locationPOJOArrayList.get(i).locationID;
                            Log.e("I_________D", "onItemClick: " + locationID);
                        }
                    }
                    courseFeeApiCall();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spProfessionBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String text = spProfessionBr.getSelectedItem().toString();
                    if (text.equalsIgnoreCase("Select Any")) {
                        MainApplication.mainapp_userprofession = "0";
                        MainApplication.profession = "0";
                        try {
                            linEmployed.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (text.equalsIgnoreCase("Student")) {
                        MainApplication.mainapp_userprofession = "Student";
                        MainApplication.mainapp_userprofession = "1";
                        MainApplication.profession = "1";
                        try {
                            linEmployed.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (text.equalsIgnoreCase("Employed")) {
                        MainApplication.mainapp_userprofession = "employed";
                        MainApplication.mainapp_userprofession = "2";
                        MainApplication.profession = "2";
                        try {
                            linEmployed.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (text.equalsIgnoreCase("Self Employed")) {
                        MainApplication.mainapp_userprofession = "selfEmployed";
                        MainApplication.mainapp_userprofession = "3";
                        MainApplication.profession = "3";
                        try {
                            linEmployed.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spCurrentCityBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        if (spCurrentCityBr.getSelectedItemPosition() > 0) {
                            cbSameAsAboveBr.setEnabled(true);
                            cbSameAsAboveBr.setClickable(true);
                        } else {
                            cbSameAsAboveBr.setEnabled(false);
                            cbSameAsAboveBr.setClickable(false);
                        }
                        String text = spCurrentCityBr.getSelectedItem().toString();
                        int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.borrowerValue4 = currentcityID = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
                                Log.e(TAG, "spCurrentCityBr: +++++++++++++++++++*********************" + currentcityID);
                            }
                        }
                    } catch (Exception e) {
//                        String className = this.getClass().getSimpleName();
//                        String name = new Object() {
//                        }.getClass().getEnclosingMethod().getName();
//                        String errorMsg = e.getMessage();
//                        String errorMsgDetails = e.getStackTrace().toString();
//                        String errorLine = String.valueOf(e.getStackTrace()[0]);
//                        Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spCurrentStateBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spCurrentStateBr.getSelectedItem().toString();
                        int count = borrowerCurrentStatePersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                                MainApplication.borrowerValue5 = currentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
                            }
                        }
                    } catch (Exception e) {
//                        String className = this.getClass().getSimpleName();
//                        String name = new Object() {
//                        }.getClass().getEnclosingMethod().getName();
//                        String errorMsg = e.getMessage();
//                        String errorMsgDetails = e.getStackTrace().toString();
//                        String errorLine = String.valueOf(e.getStackTrace()[0]);
//                        Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
                    }
                    cityApiCall();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spCurrentCountryBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spCurrentCountryBr.getSelectedItem().toString();
                        int count = borrowerCurrentCountryPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                                MainApplication.borrowerValue6 = currentcountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                            }
                        }
                        stateApiCall();
                        if (currentcityID.equals("")) {
                            spCurrentCityBr.setSelection(0);
                        } else {
                            spCurrentCityBr.setSelection(Integer.parseInt(currentcityID) - 1);
                        }
                    } catch (Exception e) {
//                        String className = this.getClass().getSimpleName();
//                        String name = new Object() {
//                        }.getClass().getEnclosingMethod().getName();
//                        String errorMsg = e.getMessage();
//                        String errorMsgDetails = e.getStackTrace().toString();
//                        String errorLine = String.valueOf(e.getStackTrace()[0]);
//                        Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spPermanentCityBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spPermanentCityBr.getSelectedItem().toString();
                        int count = borrowerPermanentCityPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.borrowerValue9 = permanentcityID = borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID;
                            }
                        }

                        if (count > 0) {
                            if (cbSameAsAboveBr.isChecked()) {
                                for (int i = 0; i < borrowerPermanentCityPersonalPOJOArrayList.size(); i++) {
                                    if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                                        spPermanentCityBr.setSelection(i);
                                    }
                                }

                            }
                        }
                    } catch (Exception e) {
//                        String className = this.getClass().getSimpleName();
//                        String name = new Object() {
//                        }.getClass().getEnclosingMethod().getName();
//                        String errorMsg = e.getMessage();
//                        String errorMsgDetails = e.getStackTrace().toString();
//                        String errorLine = String.valueOf(e.getStackTrace()[0]);
//                        Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spPermanentStateBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spPermanentStateBr.getSelectedItem().toString();
                        int count = borrowerPermanentStatePersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                                MainApplication.borrowerValue10 = permanentstateID = borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID;
                            }
                        }

                        if (cbSameAsAboveBr.isChecked()) {
                            Log.e(TAG, "getPermanentStates: +++++++++++++++++++*********************" + currentstateID);
                            spPermanentStateBr.setSelection(Integer.parseInt(currentstateID));
                        }

                    } catch (Exception e) {
//                        String className = this.getClass().getSimpleName();
//                        String name = new Object() {
//                        }.getClass().getEnclosingMethod().getName();
//                        String errorMsg = e.getMessage();
//                        String errorMsgDetails = e.getStackTrace().toString();
//                        String errorLine = String.valueOf(e.getStackTrace()[0]);
//                        Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
                    }
                    permanentCityApiCall();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spPermanentCountryBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spPermanentCountryBr.getSelectedItem().toString();
                        int count = borrowerCurrentCountryPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                                MainApplication.borrowerValue11 = permanentCountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                            }
                        }
                        permanentStateApiCall();
                        if (permanentcityID.equals("")) {
                            spPermanentCountryBr.setSelection(0);
                        } else {
                            spPermanentCountryBr.setSelection(Integer.parseInt(permanentcityID) - 1);
                        }
                    } catch (Exception e) {
//                        String className = this.getClass().getSimpleName();
//                        String name = new Object() {
//                        }.getClass().getEnclosingMethod().getName();
//                        String errorMsg = e.getMessage();
//                        String errorMsgDetails = e.getStackTrace().toString();
//                        String errorLine = String.valueOf(e.getStackTrace()[0]);
//                        Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            /**PROFESSION AND FINANCIAL **/

            /** END SPINNER CLICK **/


//            /** PROFESSION AND FINANCIAL **/
//            radioGroup_profession.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    switch (radioGroup_profession.getCheckedRadioButtonId()) {
//                        case R.id.radiobutton_isemployed:
//                            linearLayoutEmployed.setVisibility(View.VISIBLE);
//                            break;
//                        case R.id.radiobutton_isstudent:
//                            linearLayoutEmployed.setVisibility(View.GONE);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            });

            cbSameAsAboveBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cbSameAsAboveBr.isChecked()) {
                        edtPermanentAddressBr.setText(edtCurrentAddressBr.getText().toString());
                        edtPermanentLandmarkBr.setText(edtCurrentLandmarkBr.getText().toString());
                        edtPermanentPincodeBr.setText(edtCurrentPincodeBr.getText().toString());

                        try {
                            spPermanentCountryBr.setSelection(Integer.parseInt(currentcountryID));
                            spPermanentStateBr.setSelection(Integer.parseInt(currentstateID));
                            spPermanentCountryBr.setSelection(Integer.parseInt(currentcityID));

                            try {
                                spPermanentCountryBr.setEnabled(false);
                                spPermanentStateBr.setEnabled(false);
                                spPermanentCountryBr.setEnabled(false);
                                edtPermanentAddressBr.setEnabled(false);
                                edtPermanentPincodeBr.setEnabled(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                    } else {
                        edtPermanentAddressBr.setText("");
                        edtPermanentLandmarkBr.setText("");
                        edtPermanentPincodeBr.setText("");

                        try {
                            spPermanentCountryBr.setSelection(1);
                            spPermanentStateBr.setSelection(0);
                            spPermanentCountryBr.setSelection(0);

                            try {
                                spPermanentCountryBr.setEnabled(true);
                                spPermanentStateBr.setEnabled(true);
                                spPermanentCountryBr.setEnabled(true);
                                edtPermanentAddressBr.setEnabled(true);
                                edtPermanentLandmarkBr.setEnabled(true);
                                edtPermanentPincodeBr.setEnabled(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            JSONObject jsonObject = new JSONObject();

            if (!Globle.isNetworkAvailable(context)) {

//                getCurrentStates("");
//                getCurrentCities("","");

            } else {
                getCurrentStates(jsonObject);
                getCurrentCities(jsonObject);
            }

            /** API CALL POST LOGIN DASHBOARD STATUS **/
            try {
                String url = MainApplication.mainUrl + "dashboard/getKycDetails";
                Map<String, String> params = new HashMap<String, String>();
                params.put("lead_id", MainApplication.lead_id);
                if (!Globle.isNetworkAvailable(context)) {
                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                } else {
                    VolleyCallNew volleyCall = new VolleyCallNew();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
                    volleyCall.sendRequest(context, url, null, mFragment, "studentKycDetails", params, MainApplication.auth_token);
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


//            /**API CALL**/
//            try {
//                progressBar.setVisibility(View.VISIBLE);
//                String url = MainApplication.mainUrl + "algo/getBorrowerLoanDetails";
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("logged_id", userID);
//                if (!Globle.isNetworkAvailable(context)) {
//                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
////                    oflineborrowerLoanDetails();
//
//                } else {
//                    VolleyCallNew volleyCall = new VolleyCallNew();
//                    volleyCall.sendRequest(context, url, null, mFragment, "borrowerLoanDetails", params, MainApplication.auth_token);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

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

    private void setSpinnerError(Spinner spinner, String error) {
        try {
            View selectedView = spinner.getSelectedView();
            if (selectedView != null && selectedView instanceof TextView) {
                spinner.requestFocus();
                TextView selectedTextView = (TextView) selectedView;
                selectedTextView.setError(getString(R.string.error)); // any name of the error will do
                selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
                selectedTextView.setText(error); // actual error message
                spinner.performClick();
                // to open the spinner list if error is found.

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

    public void setStudentKycDetails(JSONObject jsonData) {
        try {
//            String status = jsonData.optString("status");
//            String message = jsonData.optString("message");

            if (jsonData.getInt("status") == 1) {

                jsonData.getJSONObject("leadid");
                if (!jsonData.get("kycDetails").equals(null)) {
                    JSONObject jsonkycDetails = jsonData.getJSONObject("kycDetails");
                    lead_id = jsonkycDetails.getString("lead_id");
                    has_coborrower = jsonkycDetails.getString("has_coborrower");
                    application_id = jsonkycDetails.getString("application_id");
                    requested_loan_amount = jsonkycDetails.getString("requested_loan_amount");
                    institute_name = jsonkycDetails.getString("institute_name");
                    location_name = jsonkycDetails.getString("location_name");
                    course_name = jsonkycDetails.getString("course_name");
                    course_cost = jsonkycDetails.getString("course_cost");
                    fk_institutes_id = jsonkycDetails.getString("fk_institutes_id");
                    fk_insitutes_location_id = jsonkycDetails.getString("fk_insitutes_location_id");
                    fk_course_id = jsonkycDetails.getString("fk_course_id");

                    if(!course_cost.equals("null")) {
                        edtCourseFeeBr.setText(course_cost);
                    }
                    if(!requested_loan_amount.equals("null")) {
                        edtLoanAmtBr.setText(requested_loan_amount);
                    }
                    if(!fk_institutes_id.equals("null")) {
                        spInstituteBr.setSelection(Integer.parseInt(fk_institutes_id));
                    }
                    if(!fk_insitutes_location_id.equals("null")) {
                        spInsLocationBr.setSelection(Integer.parseInt(fk_insitutes_location_id));
                    }
                    if(!fk_course_id.equals("null")) {
                        spCourseBr.setSelection(Integer.parseInt(fk_course_id));
                    }
                }

                if (!jsonData.get("borrowerDetails").equals(null)) {
                    JSONObject jsonborrowerDetails = jsonData.getJSONObject("borrowerDetails");
                    Brapplicant_id = jsonborrowerDetails.getString("applicant_id");
                    Brfk_lead_id = jsonborrowerDetails.getString("fk_lead_id");
                    Brfk_applicant_type_id = jsonborrowerDetails.getString("fk_applicant_type_id");
                    Brfirst_name = jsonborrowerDetails.getString("first_name");
                    Brmiddle_name = jsonborrowerDetails.getString("middle_name");
                    Brlast_name = jsonborrowerDetails.getString("last_name");
                    Brhas_aadhar_pan = jsonborrowerDetails.getString("has_aadhar_pan");
                    Brdob = jsonborrowerDetails.getString("dob");
                    Brpan_number = jsonborrowerDetails.getString("pan_number");
                    Braadhar_number = jsonborrowerDetails.getString("aadhar_number");
                    Brmarital_status = jsonborrowerDetails.getString("marital_status");
                    Brgender_id = jsonborrowerDetails.getString("gender_id");
                    Brmobile_number = jsonborrowerDetails.getString("mobile_number");
                    Bremail_id = jsonborrowerDetails.getString("email_id");
                    Brrelationship_with_applicant = jsonborrowerDetails.getString("relationship_with_applicant");
                    Brprofession = jsonborrowerDetails.getString("profession");
                    Bremployer_type = jsonborrowerDetails.getString("employer_type");
                    Bremployer_name = jsonborrowerDetails.getString("employer_name");
                    Brannual_income = jsonborrowerDetails.getString("annual_income");
                    Brcurrent_employment_duration = jsonborrowerDetails.getString("current_employment_duration");
                    Brtotal_employement_duration = jsonborrowerDetails.getString("total_employement_duration");
                    Bremployer_mobile_number = jsonborrowerDetails.getString("employer_mobile_number");
                    Bremployer_landline_number = jsonborrowerDetails.getString("employer_landline_number");
                    Broffice_landmark = jsonborrowerDetails.getString("office_landmark");
                    Broffice_address = jsonborrowerDetails.getString("office_address");
                    Broffice_address_city = jsonborrowerDetails.getString("office_address_city");
                    Broffice_address_state = jsonborrowerDetails.getString("office_address_state");
                    Broffice_address_country = jsonborrowerDetails.getString("office_address_country");
                    Broffice_address_pin = jsonborrowerDetails.getString("office_address_pin");
                    Brhas_active_loan = jsonborrowerDetails.getString("has_active_loan");
                    BrEMI_Amount = jsonborrowerDetails.getString("EMI_Amount");
                    Brkyc_landmark = jsonborrowerDetails.getString("kyc_landmark");
                    Brkyc_address = jsonborrowerDetails.getString("kyc_address");
                    Brkyc_address_city = jsonborrowerDetails.getString("kyc_address_city");
                    Brkyc_address_state = jsonborrowerDetails.getString("kyc_address_state");
                    Brkyc_address_country = jsonborrowerDetails.getString("kyc_address_country");
                    Brkyc_address_pin = jsonborrowerDetails.getString("kyc_address_pin");
                    Bris_borrower_current_address_same_as = jsonborrowerDetails.getString("is_borrower_current_address_same_as");
                    Bris_coborrower_current_address_same_as = jsonborrowerDetails.getString("is_coborrower_current_address_same_as");
                    Brcurrent_residence_type = jsonborrowerDetails.getString("current_residence_type");
                    Brcurrent_landmark = jsonborrowerDetails.getString("current_landmark");
                    Brcurrent_address = jsonborrowerDetails.getString("current_address");
                    Brcurrent_address_city = jsonborrowerDetails.getString("current_address_city");
                    Brcurrent_address_state = jsonborrowerDetails.getString("current_address_state");
                    Brcurrent_address_country = jsonborrowerDetails.getString("current_address_country");
                    Brcurrent_address_pin = jsonborrowerDetails.getString("current_address_pin");
                    Brcurrent_address_rent = jsonborrowerDetails.getString("current_address_rent");
                    Brcurrent_address_stay_duration = jsonborrowerDetails.getString("current_address_stay_duration");
                    Bris_borrower_permanent_address_same_as = jsonborrowerDetails.getString("is_borrower_permanent_address_same_as");
                    Bris_coborrower_permanent_address_same_as = jsonborrowerDetails.getString("is_coborrower_permanent_address_same_as");
                    Brpermanent_residence_type = jsonborrowerDetails.getString("permanent_residence_type");
                    Brpermanent_landmark = jsonborrowerDetails.getString("permanent_landmark");
                    Brpermanent_address = jsonborrowerDetails.getString("permanent_address");
                    Brpermanent_address_city = jsonborrowerDetails.getString("permanent_address_city");
                    Brpermanent_address_state = jsonborrowerDetails.getString("permanent_address_state");
                    Brpermanent_address_country = jsonborrowerDetails.getString("permanent_address_country");
                    Brpermanent_address_pin = jsonborrowerDetails.getString("permanent_address_pin");
                    Brpermanent_address_rent = jsonborrowerDetails.getString("permanent_address_rent");
                    Brpermanent_address_stay_duration = jsonborrowerDetails.getString("permanent_address_stay_duration");
                    Brlast_completed_degree = jsonborrowerDetails.getString("last_completed_degree");
                    Brscore_unit = jsonborrowerDetails.getString("score_unit");
                    Brcgpa = jsonborrowerDetails.getString("cgpa");
                    Brpercentage = jsonborrowerDetails.getString("percentage");
                    Brpassing_year = jsonborrowerDetails.getString("passing_year");
                    Brgap_in_education = jsonborrowerDetails.getString("gap_in_education");
                    Brfull_name_pan_response = jsonborrowerDetails.getString("full_name_pan_response");
                    Brcreated_by_id = jsonborrowerDetails.getString("created_by_id");
                    Brcreated_date_time = jsonborrowerDetails.getString("created_date_time");
                    Brcreated_ip_address = jsonborrowerDetails.getString("created_ip_address");
                    Brmodified_by = jsonborrowerDetails.getString("modified_by");
                    Brmodified_date_time = jsonborrowerDetails.getString("modified_date_time");
                    Brmodified_ip_address = jsonborrowerDetails.getString("modified_ip_address");
                    Bris_deleted = jsonborrowerDetails.getString("is_deleted");

                    if(!Brfirst_name.equals("null")) {
                        edtFnameBr.setSelection(Integer.parseInt(Brfirst_name));
                    }if(!Brmiddle_name.equals("null")) {
                        edtMnameBr.setSelection(Integer.parseInt(Brmiddle_name));
                    }if(!Brlast_name.equals("null")) {
                        edtLnameBr.setSelection(Integer.parseInt(Brlast_name));
                    }if(!Bremail_id.equals("null")) {
                        edtEmailIdBr.setSelection(Integer.parseInt(Bremail_id));
                    }if(!Brpan_number.equals("null")) {
                        edtPanBr.setSelection(Integer.parseInt(Brpan_number));
                    }if(!Braadhar_number.equals("null")) {
                        edtAadhaarBr.setSelection(Integer.parseInt(Braadhar_number));
                    }if(!Bremployer_name.equals("null")) {
                        edtCompanyBr.setSelection(Integer.parseInt(Bremployer_name));
                    }if(!Brannual_income.equals("null")) {
                        edtAnnualSalBr.setSelection(Integer.parseInt(Brannual_income));
                    }if(!Brkyc_address.equals("null")) {
                        edtCurrentAddressBr.setSelection(Integer.parseInt(Brkyc_address));
                    }if(!Brkyc_landmark.equals("null")) {
                        edtCurrentLandmarkBr.setSelection(Integer.parseInt(Brkyc_landmark));
                    }if(!Brkyc_address_pin.equals("null")) {
                        edtCurrentPincodeBr.setSelection(Integer.parseInt(Brkyc_address_pin));
                    }if(!Brkyc_address_pin.equals("null")) {
                        edtCurrentPincodeBr.setSelection(Integer.parseInt(Brkyc_address_pin));
                    }if(!Brkyc_address_pin.equals("null")) {
                        edtCurrentPincodeBr.setSelection(Integer.parseInt(Brkyc_address_pin));
                    }

                }


                if (!jsonData.get("coborrowerDetails").equals(null)) {
                    JSONObject jsoncoborrowerDetails = jsonData.getJSONObject("coborrowerDetails");
                    CoBrapplicant_id = jsoncoborrowerDetails.getString("applicant_id");
                    CoBrfk_lead_id = jsoncoborrowerDetails.getString("fk_lead_id");
                    CoBrfk_applicant_type_id = jsoncoborrowerDetails.getString("fk_applicant_type_id");
                    CoBrfirst_name = jsoncoborrowerDetails.getString("first_name");
                    CoBrmiddle_name = jsoncoborrowerDetails.getString("middle_name");
                    CoBrlast_name = jsoncoborrowerDetails.getString("last_name");
                    CoBrhas_aadhar_pan = jsoncoborrowerDetails.getString("has_aadhar_pan");
                    CoBrdob = jsoncoborrowerDetails.getString("dob");
                    CoBrpan_number = jsoncoborrowerDetails.getString("pan_number");
                    CoBraadhar_number = jsoncoborrowerDetails.getString("aadhar_number");
                    CoBrmarital_status = jsoncoborrowerDetails.getString("marital_status");
                    CoBrgender_id = jsoncoborrowerDetails.getString("gender_id");
                    CoBrmobile_number = jsoncoborrowerDetails.getString("mobile_number");
                    CoBremail_id = jsoncoborrowerDetails.getString("email_id");
                    CoBrrelationship_with_applicant = jsoncoborrowerDetails.getString("relationship_with_applicant");
                    CoBrprofession = jsoncoborrowerDetails.getString("profession");
                    CoBremployer_type = jsoncoborrowerDetails.getString("employer_type");
                    CoBremployer_name = jsoncoborrowerDetails.getString("employer_name");
                    CoBrannual_income = jsoncoborrowerDetails.getString("annual_income");
                    CoBrcurrent_employment_duration = jsoncoborrowerDetails.getString("current_employment_duration");
                    CoBrtotal_employement_duration = jsoncoborrowerDetails.getString("total_employement_duration");
                    CoBremployer_mobile_number = jsoncoborrowerDetails.getString("employer_mobile_number");
                    CoBremployer_landline_number = jsoncoborrowerDetails.getString("employer_landline_number");
                    CoBroffice_landmark = jsoncoborrowerDetails.getString("office_landmark");
                    CoBroffice_address = jsoncoborrowerDetails.getString("office_address");
                    CoBroffice_address_city = jsoncoborrowerDetails.getString("office_address_city");
                    CoBroffice_address_state = jsoncoborrowerDetails.getString("office_address_state");
                    CoBroffice_address_country = jsoncoborrowerDetails.getString("office_address_country");
                    CoBroffice_address_pin = jsoncoborrowerDetails.getString("office_address_pin");
                    CoBrhas_active_loan = jsoncoborrowerDetails.getString("has_active_loan");
                    CoBrEMI_Amount = jsoncoborrowerDetails.getString("EMI_Amount");
                    CoBrkyc_landmark = jsoncoborrowerDetails.getString("kyc_landmark");
                    CoBrkyc_address = jsoncoborrowerDetails.getString("kyc_address");
                    CoBrkyc_address_city = jsoncoborrowerDetails.getString("kyc_address_city");
                    CoBrkyc_address_state = jsoncoborrowerDetails.getString("kyc_address_state");
                    CoBrkyc_address_country = jsoncoborrowerDetails.getString("kyc_address_country");
                    CoBrkyc_address_pin = jsoncoborrowerDetails.getString("kyc_address_pin");
                    CoBris_borrower_current_address_same_as = jsoncoborrowerDetails.getString("is_borrower_current_address_same_as");
                    CoBris_coborrower_current_address_same_as = jsoncoborrowerDetails.getString("is_coborrower_current_address_same_as");
                    CoBrcurrent_residence_type = jsoncoborrowerDetails.getString("current_residence_type");
                    CoBrcurrent_landmark = jsoncoborrowerDetails.getString("current_landmark");
                    CoBrcurrent_address = jsoncoborrowerDetails.getString("current_address");
                    CoBrcurrent_address_city = jsoncoborrowerDetails.getString("current_address_city");
                    CoBrcurrent_address_state = jsoncoborrowerDetails.getString("current_address_state");
                    CoBrcurrent_address_country = jsoncoborrowerDetails.getString("current_address_country");
                    CoBrcurrent_address_pin = jsoncoborrowerDetails.getString("current_address_pin");
                    CoBrcurrent_address_rent = jsoncoborrowerDetails.getString("current_address_rent");
                    CoBrcurrent_address_stay_duration = jsoncoborrowerDetails.getString("current_address_stay_duration");
                    CoBris_borrower_permanent_address_same_as = jsoncoborrowerDetails.getString("is_borrower_permanent_address_same_as");
                    CoBris_coborrower_permanent_address_same_as = jsoncoborrowerDetails.getString("is_coborrower_permanent_address_same_as");
                    CoBrpermanent_residence_type = jsoncoborrowerDetails.getString("permanent_residence_type");
                    CoBrpermanent_landmark = jsoncoborrowerDetails.getString("permanent_landmark");
                    CoBrpermanent_address = jsoncoborrowerDetails.getString("permanent_address");
                    CoBrpermanent_address_city = jsoncoborrowerDetails.getString("permanent_address_city");
                    CoBrpermanent_address_state = jsoncoborrowerDetails.getString("permanent_address_state");
                    CoBrpermanent_address_country = jsoncoborrowerDetails.getString("permanent_address_country");
                    CoBrpermanent_address_pin = jsoncoborrowerDetails.getString("permanent_address_pin");
                    CoBrpermanent_address_rent = jsoncoborrowerDetails.getString("permanent_address_rent");
                    CoBrpermanent_address_stay_duration = jsoncoborrowerDetails.getString("permanent_address_stay_duration");
                    CoBrlast_completed_degree = jsoncoborrowerDetails.getString("last_completed_degree");
                    CoBrscore_unit = jsoncoborrowerDetails.getString("score_unit");
                    CoBrcgpa = jsoncoborrowerDetails.getString("cgpa");
                    CoBrpercentage = jsoncoborrowerDetails.getString("percentage");
                    CoBrpassing_year = jsoncoborrowerDetails.getString("passing_year");
                    CoBrgap_in_education = jsoncoborrowerDetails.getString("gap_in_education");
                    CoBrfull_name_pan_response = jsoncoborrowerDetails.getString("full_name_pan_response");
                    CoBrcreated_by_id = jsoncoborrowerDetails.getString("created_by_id");
                    CoBrcreated_date_time = jsoncoborrowerDetails.getString("created_date_time");
                    CoBrcreated_ip_address = jsoncoborrowerDetails.getString("created_ip_address");
                    CoBrmodified_by = jsoncoborrowerDetails.getString("modified_by");
                    CoBrmodified_date_time = jsoncoborrowerDetails.getString("modified_date_time");
                    CoBrmodified_ip_address = jsoncoborrowerDetails.getString("modified_ip_address");
                    CoBris_deleted = jsoncoborrowerDetails.getString("is_deleted");

                }

                if (!jsonData.get("leadStatus").equals(null)) {
                    JSONObject jsonleadStatus = jsonData.getJSONObject("leadStatus");
                    lead_status_id = jsonleadStatus.getString("lead_status_id");
                    fk_lead_id = jsonleadStatus.getString("fk_lead_id");
                    lead_status = jsonleadStatus.getString("lead_status");
                    lead_sub_status = jsonleadStatus.getString("lead_sub_status");
                    current_stage = jsonleadStatus.getString("current_stage");
                    current_status = jsonleadStatus.getString("current_status");
                    lead_drop_status = jsonleadStatus.getString("lead_drop_status");
                    lead_reject_status = jsonleadStatus.getString("lead_reject_status");
                    lead_initiated_datetime = jsonleadStatus.getString("lead_initiated_datetime");
                    is_lead_owner_added = jsonleadStatus.getString("is_lead_owner_added");
                    lead_owner_added_datetime = jsonleadStatus.getString("lead_owner_added_datetime");
                    lead_owner_added_by = jsonleadStatus.getString("lead_owner_added_by");
                    is_lead_counsellor_added = jsonleadStatus.getString("is_lead_counsellor_added");
                    lead_counsellor_added_datetime = jsonleadStatus.getString("lead_counsellor_added_datetime");
                    lead_counsellor_added_by = jsonleadStatus.getString("lead_counsellor_added_by");
                    is_kyc_details_filled = jsonleadStatus.getString("is_kyc_details_filled");
                    kyc_details_filled_datetime = jsonleadStatus.getString("kyc_details_filled_datetime");
                    kyc_details_filled_by = jsonleadStatus.getString("kyc_details_filled_by");
                    coborrower_added_datetime = jsonleadStatus.getString("coborrower_added_datetime");
                    coborrower_added_by_id = jsonleadStatus.getString("coborrower_added_by_id");
                    is_detailed_info_filled = jsonleadStatus.getString("is_detailed_info_filled");
                    detailed_info_filled_datetime = jsonleadStatus.getString("detailed_info_filled_datetime");
                    detailed_info_filled_by_id = jsonleadStatus.getString("detailed_info_filled_by_id");
                    approval_request_sales_status = jsonleadStatus.getString("approval_request_sales_status");
                    approval_request_sales_status_datetime = jsonleadStatus.getString("approval_request_sales_status_datetime");
                    approval_request_sales_status_by_id = jsonleadStatus.getString("approval_request_sales_status_by_id");
                    list_of_LAF_info_pending = jsonleadStatus.getString("list_of_LAF_info_pending");
                    list_of_LAF_info_filled = jsonleadStatus.getString("list_of_LAF_info_filled");
                    IPA_status = jsonleadStatus.getString("IPA_status");
                    IPA_datetime = jsonleadStatus.getString("IPA_datetime");
                    IPA_by_id = jsonleadStatus.getString("IPA_by_id");
                    docs_upload_status = jsonleadStatus.getString("docs_upload_status");
                    docs_upload_datetime = jsonleadStatus.getString("docs_upload_datetime");
                    list_of_uplaoded_docs = jsonleadStatus.getString("list_of_uplaoded_docs");
                    list_of_pendingdocs = jsonleadStatus.getString("list_of_pendingdocs");
                    docs_verification_status = jsonleadStatus.getString("docs_verification_status");
                    docs_verification_datetime = jsonleadStatus.getString("docs_verification_datetime");
                    credit_approval_request_status = jsonleadStatus.getString("credit_approval_request_status");
                    credit_approval_request_status_datetime = jsonleadStatus.getString("credit_approval_request_status_datetime");
                    credit_approval_request_status_by_id = jsonleadStatus.getString("credit_approval_request_status_by_id");
                    applicant_ekyc_status = jsonleadStatus.getString("applicant_ekyc_status");
                    applicant_ekyc_datetime = jsonleadStatus.getString("applicant_ekyc_datetime");
                    co_applicant_ekyc_status = jsonleadStatus.getString("co_applicant_ekyc_status");
                    co_applicant_ekyc_datetime = jsonleadStatus.getString("co_applicant_ekyc_datetime");
                    credit_assessment_status = jsonleadStatus.getString("credit_assessment_status");
                    credit_assessment_by_id = jsonleadStatus.getString("credit_assessment_by_id");
                    credit_assessment_datetime = jsonleadStatus.getString("credit_assessment_datetime");
                    loan_product_selection_status = jsonleadStatus.getString("loan_product_selection_status");
                    loan_product_by_id = jsonleadStatus.getString("loan_product_by_id");
                    loan_product_datetime = jsonleadStatus.getString("loan_product_datetime");
                    underwriting_status = jsonleadStatus.getString("underwriting_status");
                    underwriting_by_id = jsonleadStatus.getString("underwriting_by_id");
                    underwriting_datetime = jsonleadStatus.getString("underwriting_datetime");
                    is_processing_fees_set = jsonleadStatus.getString("is_processing_fees_set");
                    processing_fees_set_datetime = jsonleadStatus.getString("processing_fees_set_datetime");
                    processing_fees_set_by_id = jsonleadStatus.getString("processing_fees_set_by_id");
                    processing_fees_paid = jsonleadStatus.getString("processing_fees_paid");
                    processing_fees_paid_datetime = jsonleadStatus.getString("processing_fees_paid_datetime");
                    processing_fees_paid_by = jsonleadStatus.getString("processing_fees_paid_by");
                    lender_creation_status = jsonleadStatus.getString("lender_creation_status");
                    lender_creation_modified_datetime = jsonleadStatus.getString("lender_creation_modified_datetime");
                    lender_creation_modified_by = jsonleadStatus.getString("lender_creation_modified_by");
                    amort_creation_status = jsonleadStatus.getString("amort_creation_status");
                    amort_creation_modified_datetime = jsonleadStatus.getString("amort_creation_modified_datetime");
                    amort_creation_modified_by = jsonleadStatus.getString("amort_creation_modified_by");
                    borrower_pan_ekyc_response = jsonleadStatus.getString("borrower_pan_ekyc_response");
                    borrower_aadhar_ekyc_response = jsonleadStatus.getString("borrower_aadhar_ekyc_response");
                    borrower_pan_ekyc_status = jsonleadStatus.getString("borrower_pan_ekyc_status");
                    borrower_aadhar_ekyc_status = jsonleadStatus.getString("borrower_aadhar_ekyc_status");
                    coborrower_pan_ekyc_response = jsonleadStatus.getString("coborrower_pan_ekyc_response");
                    coborrower_aadhar_ekyc_response = jsonleadStatus.getString("coborrower_aadhar_ekyc_response");
                    coborrower_aadhar_ekyc_status = jsonleadStatus.getString("coborrower_aadhar_ekyc_status");
                    coborrower_pan_ekyc_status = jsonleadStatus.getString("coborrower_pan_ekyc_status");
                    is_cam_uploaded = jsonleadStatus.getString("is_cam_uploaded");
                    is_finbit_uploaded = jsonleadStatus.getString("is_finbit_uploaded");
                    is_exception_uploaded = jsonleadStatus.getString("is_exception_uploaded");
                    is_loan_agreement_uploaded = jsonleadStatus.getString("is_loan_agreement_uploaded");
                    loan_agreement_uploaded_by = jsonleadStatus.getString("loan_agreement_uploaded_by");
                    applicant_pan_verified_by = jsonleadStatus.getString("applicant_pan_verified_by");
                    applicant_pan_verified_on = jsonleadStatus.getString("applicant_pan_verified_on");
                    created_date_time = jsonleadStatus.getString("created_date_time");
                    created_ip_address = jsonleadStatus.getString("created_ip_address");
                    modified_by = jsonleadStatus.getString("modified_by");
                    modified_date_time = jsonleadStatus.getString("modified_date_time");
                    modified_ip_address = jsonleadStatus.getString("modified_ip_address");
                    is_deleted = jsonleadStatus.getString("is_deleted");
                    borrower_required_docs = jsonleadStatus.getString("borrower_required_docs");
                    co_borrower_required_docs = jsonleadStatus.getString("co_borrower_required_docs");
                    co_borrower_pending_docs = jsonleadStatus.getString("co_borrower_pending_docs");
                    borrower_extra_required_docs = jsonleadStatus.getString("borrower_extra_required_docs");
                    co_borrower_extra_required_docs = jsonleadStatus.getString("co_borrower_extra_required_docs");
                    id = jsonleadStatus.getString("id");
                    status_name = jsonleadStatus.getString("status_name");
                    stage_id = jsonleadStatus.getString("stage_id");
                }
                String courseAmount = String.valueOf(jsonData.get("courseAmount"));

                if (jsonData.getJSONArray("cities").length() > 0) {
                    JSONArray jsonArraycities = jsonData.getJSONArray("cities");
                }
                if (jsonData.getJSONArray("states").length() > 0) {
                    JSONArray jsonArraystates = jsonData.getJSONArray("states");
                }
                if (jsonData.getJSONArray("countries").length() > 0) {
                    JSONArray jsonArraycountries = jsonData.getJSONArray("countries");
                }
                if (jsonData.getJSONArray("cocities").length() > 0) {
                    JSONArray jsonArraycocities = jsonData.getJSONArray("cocities");
                }
                if (jsonData.getJSONArray("costates").length() > 0) {
                    JSONArray jsonArraycostates = jsonData.getJSONArray("costates");
                }
                if (jsonData.getJSONArray("cocountries").length() > 0) {
                    JSONArray jsonArraycocountries = jsonData.getJSONArray("cocountries");
                }


            } else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    public void instituteApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "pqform/apiPrefillInstitutes";  //http://159.89.204.41/eduvanzApi/pqform/apiPrefillInstitutes
            Map<String, String> params = new HashMap<String, String>();
            VolleyCallNew volleyCall = new VolleyCallNew();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                GetInstituteName();
            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "instituteNamekyc", params, MainApplication.auth_token);
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

    public void courseApiCall() {
        try {
            String url = MainApplication.mainUrl + "pqform/apiPrefillCourses";
            Map<String, String> params = new HashMap<String, String>();
            params.put("instituteId", instituteID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                if(instituteID.length()>0) {
//                    GetCourseName(instituteID);
//                }

            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "courseNamekyc", params, MainApplication.auth_token);
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

    public void locationApiCall() {
        try {
            String url = MainApplication.mainUrl + "pqform/apiPrefillLocations";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", MainApplication.mainapp_instituteID);
            params.put("course_id", MainApplication.mainapp_courseID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                if(MainApplication.mainapp_instituteID.length()>0) {
//                    GetInstituteLocation(MainApplication.mainapp_instituteID);
//                }

            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "locationNamekyc", params, MainApplication.auth_token);
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

    public void courseFeeApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "pqform/apiPrefillSliderAmount";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", MainApplication.mainapp_instituteID);
            params.put("course_id", MainApplication.mainapp_courseID);
            params.put("location_id", MainApplication.mainapp_locationID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                    MainApplication.mainapp_coursefee = "999";
//                    textViewCourseFee.setText(MainApplication.mainapp_coursefee);

            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "courseFeekyc", params, MainApplication.auth_token);
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

    public void instituteName(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "PrefillInstitutesFragment1" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonData.getJSONArray("result");

                nameOfInsitituePOJOArrayList = new ArrayList<>();
                nameofinstitute_arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    NameOfInsitituePOJO nameOfInsitituePOJO = new NameOfInsitituePOJO();
                    JSONObject mJsonti = jsonArray.getJSONObject(i);
                    nameOfInsitituePOJO.instituteName = mJsonti.getString("institute_name");
                    nameofinstitute_arrayList.add(mJsonti.getString("institute_name"));
                    nameOfInsitituePOJO.instituteID = mJsonti.getString("institute_id");
                    nameOfInsitituePOJOArrayList.add(nameOfInsitituePOJO);

                }
                setInstituteAdaptor();

            } else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    public void setInstituteAdaptor() {

        try {
            arrayAdapter_NameOfInsititue = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofinstitute_arrayList);
            spInstituteBr.setAdapter(arrayAdapter_NameOfInsititue);
            arrayAdapter_NameOfInsititue.notifyDataSetChanged();

            if (!MainApplication.mainapp_instituteID.equals("")) {
                for (int i = 0; i < nameOfInsitituePOJOArrayList.size(); i++) {
                    if (MainApplication.mainapp_instituteID.equalsIgnoreCase(nameOfInsitituePOJOArrayList.get(i).instituteID)) {
                        spInstituteBr.setSelection(i);
                    }
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

    public void courseName(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "PrefillCourseFragment1" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonData.getJSONArray("result");

                nameOfCoursePOJOArrayList = new ArrayList<>();
                nameofcourse_arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    NameOfCoursePOJO nameOfCoursePOJO = new NameOfCoursePOJO();
                    JSONObject mJsonti = jsonArray.getJSONObject(i);
                    nameOfCoursePOJO.courseName = mJsonti.getString("course_name");
                    nameofcourse_arrayList.add(mJsonti.getString("course_name"));
                    nameOfCoursePOJO.courseID = mJsonti.getString("course_id");
                    nameOfCoursePOJOArrayList.add(nameOfCoursePOJO);

                }
                setCourseAdaptor();

            } else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    public void courseFee(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                edtCourseFeeBr.setText(jsonData.getString("result"));
                MainApplication.mainapp_coursefee = jsonData.getString("result");
            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }


    public void setCourseAdaptor() {

        try {
            arrayAdapter_NameOfCourse = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofcourse_arrayList);
            spCourseBr.setAdapter(arrayAdapter_NameOfCourse);
            arrayAdapter_NameOfCourse.notifyDataSetChanged();

            if (!MainApplication.mainapp_courseID.equals("")) {

                for (int i = 0; i < nameOfCoursePOJOArrayList.size(); i++) {
                    if (MainApplication.mainapp_courseID.equalsIgnoreCase(nameOfCoursePOJOArrayList.get(i).courseID)) {
                        spCourseBr.setSelection(i);
                    }
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

    public void locationName(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "PrefillInstitutesFragment1" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = jsonData.getJSONArray("result");

                locationPOJOArrayList = new ArrayList<>();
                locations_arrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    LocationsPOJO locationsPOJO = new LocationsPOJO();
                    JSONObject mJsonti = jsonArray.getJSONObject(i);
                    locationsPOJO.locationName = mJsonti.getString("location_name");
                    locations_arrayList.add(mJsonti.getString("location_name"));
                    locationsPOJO.locationID = mJsonti.getString("location_id");
                    locationPOJOArrayList.add(locationsPOJO);

                }
                setInstituteLocationAdaptor();

            } else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    public void setInstituteLocationAdaptor() {

        try {
            arrayAdapter_locations = new ArrayAdapter(context, R.layout.custom_layout_spinner, locations_arrayList);
            spInsLocationBr.setAdapter(arrayAdapter_locations);
            arrayAdapter_locations.notifyDataSetChanged();

            if (!MainApplication.mainapp_locationID.equals("")) {

                for (int i = 0; i < locationPOJOArrayList.size(); i++) {
                    if (MainApplication.mainapp_locationID.equalsIgnoreCase(locationPOJOArrayList.get(i).locationID)) {
                        spInsLocationBr.setSelection(i);
                    }
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

    private void cityApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getCities"; //http://159.89.204.41/eduvanzApi/algo/getCities
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", currentcountryID);//1
            params.put("stateId", currentstateID);//2
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentCities(currentstateID,currentcountryID);
            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getCurrentCity", params, MainApplication.auth_token);
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

    private void stateApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getStates";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", currentcountryID);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentStates(currentcountryID);

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getCurrentStates", params, MainApplication.auth_token);
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

    private void permanentCityApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getCities";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", permanentCountryID);
            params.put("stateId", permanentstateID);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getPermanentCities(permanentstateID,permanentCountryID);
            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getPermanentCity", params, MainApplication.auth_token);
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

    private void permanentStateApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getStates";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", permanentCountryID);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//               getPermanentStates(permanentCountryID);
            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getPermanentStates", params, MainApplication.auth_token);
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

    private void setViews() {

        try {
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar_applylona_borrower);

            linBorrowerForm = (LinearLayout) view.findViewById(R.id.linBorrowerForm);
            linCoCorrowerForm = (LinearLayout) view.findViewById(R.id.linCoCorrowerForm);

            relborrower = (RelativeLayout) view.findViewById(R.id.relborrower);
            relCoborrower = (RelativeLayout) view.findViewById(R.id.relCoborrower);

            txtBorrowerArrowKey = (TextView) view.findViewById(R.id.txtBorrowerArrowKey);
            txtCoBorrowerArrowKey = (TextView) view.findViewById(R.id.txtCoBorrowerArrowKey);

            linEmployed = (LinearLayout) view.findViewById(R.id.linEmployed);
            linearLayoutLeftoff = (LinearLayout) view.findViewById(R.id.linearLayout_leftoff1);
            textView1 = (TextView) view.findViewById(R.id.textView_l1);
            textView2 = (TextView) view.findViewById(R.id.textView_l2);
            if (borrowerBackground.equalsIgnoreCase("1")) {
                textView1.setBackground(getResources().getDrawable(R.drawable.background_capsule_primarydark));
            }
            if (borrowerBackground.equalsIgnoreCase("1") && coBorrowerBackground.equalsIgnoreCase("1")) {
                textView2.setBackground(getResources().getDrawable(R.drawable.background_capsule_primarydark));
                linearLayoutLeftoff.setBackground(getResources().getDrawable(R.drawable.background_capsule_primarydark));
            }
            mainApplication.applyTypefaceBold(textView1, context);
            mainApplication.applyTypeface(textView2, context);
            textView3 = (TextView) view.findViewById(R.id.textView_l3);
            mainApplication.applyTypeface(textView3, context);

            buttonNext = (Button) view.findViewById(R.id.button_next_borrower_loanapplication);
            mainApplication.applyTypeface(buttonNext, context);

            edtCourseFeeBr = (EditText) view.findViewById(R.id.edtCourseFeeBr);
            edtLoanAmtBr = (EditText) view.findViewById(R.id.edtLoanAmtBr);
            edtFnameBr = (EditText) view.findViewById(R.id.edtFnameBr);
            edtMnameBr = (EditText) view.findViewById(R.id.edtMnameBr);
            edtLnameBr = (EditText) view.findViewById(R.id.edtLnameBr);
            edtEmailIdBr = (EditText) view.findViewById(R.id.edtEmailIdBr);
            edtPanBr = (EditText) view.findViewById(R.id.edtPanBr);
            edtAadhaarBr = (EditText) view.findViewById(R.id.edtAadhaarBr);
            edtCompanyBr = (EditText) view.findViewById(R.id.edtCompanyBr);
            edtAnnualSalBr = (EditText) view.findViewById(R.id.edtAnnualSalBr);
            edtCurrentAddressBr = (EditText) view.findViewById(R.id.edtCurrentAddressBr);
            edtCurrentLandmarkBr = (EditText) view.findViewById(R.id.edtCurrentLandmarkBr);
            edtCurrentPincodeBr = (EditText) view.findViewById(R.id.edtCurrentPincodeBr);
            edtCurrentLandmarkBr = (EditText) view.findViewById(R.id.edtCurrentLandmarkBr);
            edtCurrentLandmarkBr = (EditText) view.findViewById(R.id.edtCurrentLandmarkBr);
            edtPermanentAddressBr = (EditText) view.findViewById(R.id.edtPermanentAddressBr);
            edtPermanentLandmarkBr = (EditText) view.findViewById(R.id.edtPermanentLandmarkBr);
            edtPermanentPincodeBr = (EditText) view.findViewById(R.id.edtPermanentPincodeBr);

            rgGenderBr = (RadioGroup) view.findViewById(R.id.rgGenderBr);

            rbMaleBr = (RadioButton) view.findViewById(R.id.rbMaleBr);
            rbFemaleBr = (RadioButton) view.findViewById(R.id.rbFemaleBr);

            spInstituteBr = (Spinner) view.findViewById(R.id.spInstituteBr);
            spInsLocationBr = (Spinner) view.findViewById(R.id.spInsLocationBr);
            spCourseBr = (Spinner) view.findViewById(R.id.spCourseBr);
            spProfessionBr = (Spinner) view.findViewById(R.id.spProfessionBr);
            spCurrentCountryBr = (Spinner) view.findViewById(R.id.spCurrentCountryBr);
            spCurrentStateBr = (Spinner) view.findViewById(R.id.spCurrentStateBr);
            spCurrentCityBr = (Spinner) view.findViewById(R.id.spCurrentCityBr);
            spPermanentCountryBr = (Spinner) view.findViewById(R.id.spPermanentCountryBr);
            spPermanentStateBr = (Spinner) view.findViewById(R.id.spPermanentStateBr);
            spPermanentCityBr = (Spinner) view.findViewById(R.id.spPermanentCityBr);

            //CoBorrower

            edtFnameCoBr = (EditText) view.findViewById(R.id.edtFnameCoBr);
            edtMnameCoBr = (EditText) view.findViewById(R.id.edtMnameCoBr);
            edtLnameCoBr = (EditText) view.findViewById(R.id.edtLnameCoBr);
            edtEmailIdCoBr = (EditText) view.findViewById(R.id.edtEmailIdCoBr);
            edtPanCoBr = (EditText) view.findViewById(R.id.edtPanCoBr);
            edtAadhaarCoBr = (EditText) view.findViewById(R.id.edtAadhaarCoBr);
            edtCompanyCoBr = (EditText) view.findViewById(R.id.edtCompanyCoBr);
            edtAnnualSalCoBr = (EditText) view.findViewById(R.id.edtAnnualSalCoBr);
            edtCurrentAddressCoBr = (EditText) view.findViewById(R.id.edtCurrentAddressCoBr);
            edtCurrentLandmarkCoBr = (EditText) view.findViewById(R.id.edtCurrentLandmarkCoBr);
            edtCurrentPincodeCoBr = (EditText) view.findViewById(R.id.edtCurrentPincodeCoBr);
            edtCurrentLandmarkCoBr = (EditText) view.findViewById(R.id.edtCurrentLandmarkCoBr);
            edtCurrentLandmarkCoBr = (EditText) view.findViewById(R.id.edtCurrentLandmarkCoBr);
            edtPermanentAddressCoBr = (EditText) view.findViewById(R.id.edtPermanentAddressCoBr);
            edtPermanentLandmarkCoBr = (EditText) view.findViewById(R.id.edtPermanentLandmarkCoBr);
            edtPermanentPincodeCoBr = (EditText) view.findViewById(R.id.edtPermanentPincodeCoBr);

            rgGenderCoBr = (RadioGroup) view.findViewById(R.id.rgGenderCoBr);

            rbMaleCoBr = (RadioButton) view.findViewById(R.id.rbMaleCoBr);
            rbFemaleCoBr = (RadioButton) view.findViewById(R.id.rbFemaleCoBr);

            spCurrentCountryCoBr = (Spinner) view.findViewById(R.id.spCurrentCountryCoBr);
            spCurrentStateCoBr = (Spinner) view.findViewById(R.id.spCurrentStateCoBr);
            spCurrentCityCoBr = (Spinner) view.findViewById(R.id.spCurrentCityCoBr);
            spPermanentCountryCoBr = (Spinner) view.findViewById(R.id.spPermanentCountryCoBr);
            spPermanentStateCoBr = (Spinner) view.findViewById(R.id.spPermanentStateCoBr);
            spPermanentCityCoBr = (Spinner) view.findViewById(R.id.spPermanentCityCoBr);

            currentCountry_arrayList = new ArrayList<>();
            borrowerCurrentCountryPersonalPOJOArrayList = new ArrayList<>();

            BorrowerCurrentCountryPersonalPOJO borrowerCurrentCountryPersonalPOJO = new BorrowerCurrentCountryPersonalPOJO();
            borrowerCurrentCountryPersonalPOJO.countryName = "Select Any";
            currentCountry_arrayList.add("Select Any");
            borrowerCurrentCountryPersonalPOJO.countryID = "";
            borrowerCurrentCountryPersonalPOJOArrayList.add(borrowerCurrentCountryPersonalPOJO);

            BorrowerCurrentCountryPersonalPOJO borrowerCurrentCountryPersonalPOJO1 = new BorrowerCurrentCountryPersonalPOJO();
            borrowerCurrentCountryPersonalPOJO1.countryName = "India";
            currentCountry_arrayList.add("India");
            borrowerCurrentCountryPersonalPOJO1.countryID = "1";
            borrowerCurrentCountryPersonalPOJOArrayList.add(borrowerCurrentCountryPersonalPOJO1);

            arrayAdapter_currentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
            spCurrentCountryBr.setAdapter(arrayAdapter_currentCountry);
            arrayAdapter_currentCountry.notifyDataSetChanged();

            arrayAdapter_currentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
            spPermanentCountryBr.setAdapter(arrayAdapter_currentCountry);
            arrayAdapter_currentCountry.notifyDataSetChanged();

            //Set Profession Spinner
            profession_arrayList = new ArrayList<>();
            profession_arrayList.add("Select Any");
            profession_arrayList.add("Student");
            profession_arrayList.add("Employed");
            profession_arrayList.add("Self Employed");
            arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
            spProfessionBr.setAdapter(arrayAdapter_profession);
            arrayAdapter_profession.notifyDataSetChanged();

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

    /**
     * RESPONSE OF API CALL
     **/
    public void borrowerLoanDetails(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "borrowerPersonal" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONArray jsonArray4 = jsonObject.getJSONArray("country");
                currentCountry_arrayList = new ArrayList<>();
                borrowerCurrentCountryPersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray4.length(); i++) {
                    BorrowerCurrentCountryPersonalPOJO borrowerCurrentCountryPersonalPOJO = new BorrowerCurrentCountryPersonalPOJO();
                    JSONObject mJsonti = jsonArray4.getJSONObject(i);
                    borrowerCurrentCountryPersonalPOJO.countryName = mJsonti.getString("country_name");
                    currentCountry_arrayList.add(mJsonti.getString("country_name"));
                    borrowerCurrentCountryPersonalPOJO.countryID = mJsonti.getString("country_id");
                    borrowerCurrentCountryPersonalPOJOArrayList.add(borrowerCurrentCountryPersonalPOJO);
                }
                arrayAdapter_currentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
                spCurrentCountryBr.setAdapter(arrayAdapter_currentCountry);
                arrayAdapter_currentCountry.notifyDataSetChanged();
                arrayAdapter_permanentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
                spPermanentCountryBr.setAdapter(arrayAdapter_permanentCountry);
                arrayAdapter_permanentCountry.notifyDataSetChanged();

                JSONObject jsonObject1 = jsonObject.getJSONObject("personalDetails");
                String monthlyRent = jsonObject1.getString("student_monthly_rent");
                String borrower_gender = jsonObject1.getString("student_gender");
                String borrower_gaps = jsonObject1.getString("any_gaps");
                String currentadd = jsonObject1.getString("student_current_address");
                String currentPincode = jsonObject1.getString("student_current_pincode");
                String permanentadd = jsonObject1.getString("student_permanent_address");
                String permanentpin = jsonObject1.getString("student_permanent_pincode");
                String firstname = jsonObject1.getString("student_first_name");
                String lastname = jsonObject1.getString("student_last_name");
                String dob = jsonObject1.getString("student_dob");
                String panNo = jsonObject1.getString("student_pan_card_no");
                String aadhaarno = jsonObject1.getString("student_aadhar_card_no");
                String maritialstatus = jsonObject1.getString("student_married");

                currentResidencetypeID = jsonObject1.getString("student_address_type");
                currentcityID = jsonObject1.getString("student_current_city");
                currentstateID = jsonObject1.getString("student_current_state");
                if (jsonObject1.getString("student_current_country").equals("0")) {
                    currentcountryID = "1";
                } else if (jsonObject1.getString("student_current_country").equals("")) {
                    currentcountryID = "1";
                } else {
                    currentcountryID = jsonObject1.getString("student_current_country");
                }
//                jsonObject1.getString("student_permanent_country")

                permanentcityID = jsonObject1.getString("student_permanent_city");
                permanentstateID = jsonObject1.getString("student_permanent_state");

                if (jsonObject1.getString("student_permanent_country").equals("0")) {
                    permanentCountryID = "1";
                } else if (jsonObject1.getString("student_permanent_country").equals("")) {
                    permanentCountryID = "1";
                } else {
                    permanentCountryID = jsonObject1.getString("student_permanent_country");
                }
//
//                if (currentcityID.equals("")) {
//                    spCurrentCityBr.setSelection(0);
//                } else {
//                    spCurrentCityBr.setSelection(Integer.parseInt(currentcityID));
//                }
//                if (currentstateID.equals("")) {
//                    spCurrentStateBr.setSelection(0);
//                } else {
//                    spCurrentStateBr.setSelection(Integer.parseInt(currentstateID));
//                }
                if (currentcountryID.equals("")) {
                    spCurrentCountryBr.setSelection(1);
                } else {
                    spCurrentCountryBr.setSelection(Integer.parseInt(currentcountryID));
                }
//                if (permanentcityID.equals("")) {
//                    spPermanentCountryBr.setSelection(0);
//                } else {
//                    spPermanentCountryBr.setSelection(Integer.parseInt(permanentcityID));
//                }
//                if (permanentstateID.equals("")) {
//                    spPermanentStateBr.setSelection(0);
//                } else {
//                    spPermanentStateBr.setSelection(Integer.parseInt(permanentstateID));
//                }
                if (permanentCountryID.equals("")) {
                    spPermanentCountryBr.setSelection(1);
                } else {
                    spPermanentCountryBr.setSelection(Integer.parseInt(permanentCountryID));
                }

                edtFnameBr.setText(firstname);
                edtLnameBr.setText(lastname);
                edtAadhaarBr.setText(aadhaarno);
                edtPanBr.setText(panNo);
                edtCurrentAddressBr.setText(currentadd);
                if (currentPincode.equals("0")) {
                    edtCurrentPincodeBr.setText("");
                } else {
                    edtCurrentPincodeBr.setText(currentPincode);

                }
                edtPermanentAddressBr.setText(permanentadd);

                if (permanentpin.equals("0")) {
                    edtPermanentPincodeBr.setText("");
                } else {
                    edtPermanentPincodeBr.setText(permanentpin);

                }

                if (!dob.equalsIgnoreCase("") || !dob.isEmpty()) {
                    textViewbirthday.setText(dob);
                    textViewbirthday.setTextColor(Color.BLACK);
                    lable.setVisibility(View.VISIBLE);
                } else {
                    textViewbirthday.setText(R.string.birthdate);
//                    textViewbirthday.setTextColor(808080);
                }

                if (borrower_gender.equalsIgnoreCase("1")) {
                    rbMaleBr.setChecked(true);
                } else if (borrower_gender.equalsIgnoreCase("2")) {
                    rbFemaleBr.setChecked(true);
                }

//                if (employerType.equalsIgnoreCase("1")) {
//                    radioButtonPrivate.setChecked(true);
//                } else if (employerType.equalsIgnoreCase("0")) {
//                    radioButtonGovernment.setChecked(true);
//                }
                MainApplication.borrowerValue1 = currentResidencetypeID;
                MainApplication.borrowerValue3 = edtCurrentAddressBr.getText().toString();
                MainApplication.borrowerValue4 = currentcityID;
                MainApplication.borrowerValue5 = currentstateID;
                MainApplication.borrowerValue6 = currentcountryID;
                MainApplication.borrowerValue7 = edtCurrentPincodeBr.getText().toString();
                MainApplication.borrowerValue8 = edtPermanentAddressBr.getText().toString();
                MainApplication.borrowerValue9 = permanentcityID;
                MainApplication.borrowerValue10 = permanentstateID;
                MainApplication.borrowerValue11 = permanentCountryID;
                MainApplication.borrowerValue12 = edtPermanentPincodeBr.getText().toString();
                MainApplication.borrowerValue32 = borrower_gender;
                MainApplication.borrowerValue33 = borrower_gaps;
                MainApplication.borrowerValue13 = edtFnameBr.getText().toString();
                MainApplication.borrowerValue14 = edtLnameBr.getText().toString();
                MainApplication.borrowerValue15 = textViewbirthday.getText().toString();
                MainApplication.borrowerValue16 = maritialstatus;
                MainApplication.borrowerValue17 = edtPanBr.getText().toString();
                MainApplication.borrowerValue18 = edtAadhaarBr.getText().toString();
                MainApplication.borrowerValue19 = degreeID;

                MainApplication.borrowerValue25 = editTextNameofCompany.getText().toString();
                MainApplication.borrowerValue26 = jobDurationID;
                MainApplication.borrowerValue27 = editTextAnualIncome.getText().toString();
                MainApplication.borrowerValue28 = editTextAdvancePayment.getText().toString();
                MainApplication.borrowerValue29 = currentresidenceDurationID;

                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    public void sendBorrowerDetails(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

//                String LocalsSql = "UPDATE BorrowerLAF SET ISUploaded = '" + true + "' WHERE logged_id = '" + userID + "'";
//                ExecuteSql(LocalsSql);

                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("first_name", edtFnameBr.getText().toString());
                editor.putString("last_name", edtLnameBr.getText().toString());
                editor.putString("borrowerBackground_dark", "1");
                editor.apply();
                editor.commit();

                LoanApplicationFragment_2 loanApplicationFragment_2 = new LoanApplicationFragment_2();
                transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_2).commit();

                textView1.setBackground(getResources().getDrawable(R.drawable.background_capsule_primarydark));
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    public void getCurrentStates(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    currentstate_arrayList = new ArrayList<>();
                    currentstate_arrayList.add("Select Any");
                    arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                    spCurrentStateBr.setAdapter(arrayAdapter_currentState);
                    arrayAdapter_currentState.notifyDataSetChanged();
                    spCurrentStateBr.setSelection(0);
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
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray3 = jsonObject.getJSONArray("states");
                    currentstate_arrayList = new ArrayList<>();
                    borrowerCurrentStatePersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        BorrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new BorrowerCurrentStatePersonalPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                        currentstate_arrayList.add(mJsonti.getString("state_name"));
                        borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                        borrowerCurrentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
                    }
                    arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                    spCurrentStateBr.setAdapter(arrayAdapter_currentState);
                    arrayAdapter_currentState.notifyDataSetChanged();

                    spCurrentStateBr.setSelection(Integer.parseInt(currentstateID));

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

    public void getCurrentCities(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    currentcity_arrayList = new ArrayList<>();
                    currentcity_arrayList.add("Select Any");
                    arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
                    spCurrentCityBr.setAdapter(arrayAdapter_currentCity);
                    arrayAdapter_currentCity.notifyDataSetChanged();
                    spCurrentCityBr.setSelection(0);
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
                Log.e("SERVER CALL", "getCurrentCities+++" + jsonData);

                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray2 = jsonObject.getJSONArray("cities");
                    currentcity_arrayList = new ArrayList<>();
                    borrowerCurrentCityPersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        BorrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new BorrowerCurrentCityPersonalPOJO();
                        JSONObject mJsonti = jsonArray2.getJSONObject(i);
                        borrowerCurrentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
                        currentcity_arrayList.add(mJsonti.getString("city_name"));
                        borrowerCurrentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
                        borrowerCurrentCityPersonalPOJOArrayList.add(borrowerCurrentCityPersonalPOJO);
                    }
                    arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
                    spCurrentCityBr.setAdapter(arrayAdapter_currentCity);
                    arrayAdapter_currentCity.notifyDataSetChanged();


                    int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                            spCurrentCityBr.setSelection(i);
                        }
                    }

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

    public void getPermanentStates(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    permanentstate_arrayList = new ArrayList<>();
                    permanentstate_arrayList.add("Select Any");
                    arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                    spPermanentStateBr.setAdapter(arrayAdapter_permanentState);
                    arrayAdapter_permanentState.notifyDataSetChanged();
                    spPermanentStateBr.setSelection(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("SERVER CALL", "getPermanentStates" + jsonData);
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray3 = jsonObject.getJSONArray("states");
                    permanentstate_arrayList = new ArrayList<>();
                    borrowerPermanentStatePersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        BorrowerCurrentStatePersonalPOJO borrowerpermanentStatePersonalPOJO = new BorrowerCurrentStatePersonalPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borrowerpermanentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                        permanentstate_arrayList.add(mJsonti.getString("state_name"));
                        borrowerpermanentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                        borrowerPermanentStatePersonalPOJOArrayList.add(borrowerpermanentStatePersonalPOJO);
                    }
                    arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentstate_arrayList);
                    spPermanentStateBr.setAdapter(arrayAdapter_permanentState);
                    arrayAdapter_permanentState.notifyDataSetChanged();

                    spPermanentStateBr.setSelection(Integer.parseInt(permanentstateID));

                    int count = borrowerPermanentStatePersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(permanentstateID)) {
                            spPermanentStateBr.setSelection(i);
                        }
                    }

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

    public void getPermanentCities(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    permanentcity_arrayList = new ArrayList<>();
                    permanentcity_arrayList.add("Select Any");
                    arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayList);
                    spPermanentCountryBr.setAdapter(arrayAdapter_permanentCity);
                    arrayAdapter_permanentCity.notifyDataSetChanged();
                    spPermanentCountryBr.setSelection(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("SERVER CALL", "getPermanentCities+++" + jsonData);
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray2 = jsonObject.getJSONArray("cities");
                    permanentcity_arrayList = new ArrayList<>();
                    borrowerPermanentCityPersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        BorrowerCurrentCityPersonalPOJO borrowerpermanentCityPersonalPOJO = new BorrowerCurrentCityPersonalPOJO();
                        JSONObject mJsonti = jsonArray2.getJSONObject(i);
                        borrowerpermanentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
                        permanentcity_arrayList.add(mJsonti.getString("city_name"));
                        borrowerpermanentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
                        borrowerPermanentCityPersonalPOJOArrayList.add(borrowerpermanentCityPersonalPOJO);
                    }
                    arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayList);
                    spPermanentCountryBr.setAdapter(arrayAdapter_permanentCity);
                    arrayAdapter_permanentCity.notifyDataSetChanged();

                    int count = borrowerPermanentCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityID)) {
                            spPermanentCountryBr.setSelection(i);
                        }
                    }

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

}
