package com.eduvanzapplication.newUI.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import android.widget.CompoundButton;
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

import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentStatePersonalPOJO;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerCurrentResidenceDurationPersonalPOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerCurrentResidenceTypePersonalPOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerJobDurationFinancePOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerProfessionFinancePOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.RelationshipwithBorrowerPOJO;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.newViews.LoanApplication;
import com.eduvanzapplication.newUI.pojo.ProfessionPOJO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.eduvanzapplication.database.DBAdapter.ExecuteSql;
import static com.eduvanzapplication.database.DBAdapter.getLocalData;
import static com.eduvanzapplication.newUI.MainApplication.TAG;

/**
 * A simple {@link Fragment} subclass.
 */


public class LoanApplicationFragment_2 extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    LinearLayout linBorrowerForm, linCoCorrowerForm, linCurrentAddress, linPermanentAddress;
    RelativeLayout relborrower, relCoborrower;
    int borrowerVisiblity = 1, coborrowerVisiblity = 1;
    Button buttonNext, buttonPrevious;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3, txtBorrowerArrowKey, txtCoBorrowerArrowKey;
    public static TextView birthdaycalender, lable, textViewbirthday;
    public static TextInputLayout input_cgpa, input_degree, input_previousamt;
    Typeface typeface;
    Calendar cal;
    public static String dateformate = "", userID = "", coBorrowerBackground = "";
    MainApplication mainApplication;
    static View view;
    static FragmentTransaction transaction;
    public static ProgressBar progressBar;

    //Borrower
    public static EditText edtCourseFeeBr, edtLoanAmtBr, edtFnameBr, edtMnameBr, edtLnameBr, edtEmailIdBr, edtPanBr, edtAadhaarBr,
            edtCompanyBr, edtAnnualSalBr, edtCurrentAddressBr, edtCurrentLandmarkBr, edtCurrentPincodeBr;

    public static RadioGroup rgGenderBr,radiogroup_iscgpa;
    public static RadioButton rbMaleBr, rbFemaleBr;

    public static Spinner spInstituteBr, spInsLocationBr, spCourseBr, spProfessionBr, spCurrentCountryBr, spCurrentStateBr,
            spCurrentCityBr;

    //CoBorrower
    public static EditText edtFnameCoBr, edtMnameCoBr, edtLnameCoBr, edtEmailIdCoBr, edtPanCoBr, edtAadhaarCoBr,
            edtCompanyCoBr, edtAnnualSalCoBr, edtCurrentAddressCoBr, edtCurrentLandmarkCoBr, edtCurrentPincodeCoBr;

    public static RadioGroup rgGenderCoBr;
    public static RadioButton rbMaleCoBr, rbFemaleCoBr;

    public static Spinner spCurrentCountryCoBr, spCurrentStateCoBr, spCurrentCityCoBr;

    public static RadioButton radioButtonPreviousEmiYes, radioButtonPreviousEmiNo, radioButtonMale, radioButtonFemale;
    public static RadioButton radioButtonMarried, radioButtonSingle, radioButtonGovernment,
            radioButtonPrivate;

    public static RadioGroup radioGroupMaritialStatus, radioGroupPreviousEmi, radioGroupGender, radioGroupEmployerType;
    public static EditText fname, lname, adhaarno, panno, currentaddress, currentpincode, permanentaddress,
            contactno, emailid, permanentpincode, monthlyrent, previousemi;

    public static Spinner spinnerPermanentCity, spinnerPermanentCountry, spinnerPermanentState;
    public static ArrayAdapter arrayAdapter_permanentCity, arrayAdapter_permanentState, arrayAdapter_permanentCountry;
    public String permanentcityID = "", permanentstateID = "", permanentCountryID = "";

    public static Spinner spinnerCurrentResidenceType;
    public static ArrayAdapter arrayAdapter_currentResidencetype;
    public static ArrayList<String> currentResidencetype_arrayList;
    public static ArrayList<CoborrowerCurrentResidenceTypePersonalPOJO> coborrowerCurrentResidenceTypePersonalPOJOArrayList;
    public String currentResidencetypeID = "";

    public static ArrayAdapter arrayAdapter_profession;
    public static ArrayList<String> profession_arrayList;
    public static ArrayList<ProfessionPOJO> professionPOJOArrayList;
    public String professionID = "";

    public static Spinner spinnerCurrentCity;
    public static ArrayAdapter arrayAdapter_currentCity;
    public static ArrayList<String> currentcity_arrayList, permanentcity_arrayList;
    public static ArrayList<BorrowerCurrentCityPersonalPOJO> borrowerCurrentCityPersonalPOJOArrayList, borrowerPermanentCityPersonalPOJOArrayList;
    public String currentcityID = "";

    public static Spinner spinnerCurrentState;
    public static ArrayAdapter arrayAdapter_currentState;
    public static ArrayList<String> currentstate_arrayList;
    public static ArrayList<BorrowerCurrentStatePersonalPOJO> borrowerCurrentStatePersonalPOJOArrayList, borrowerPermanentStatePersonalPOJOArrayList;
    public String currentstateID = "";

    public static Spinner spinnerCurrentCountry;
    public static ArrayAdapter arrayAdapter_currentCountry;
    public static ArrayList<String> currentCountry_arrayList;
    public static ArrayList<BorrowerCurrentCountryPersonalPOJO> borrowerCurrentCountryPersonalPOJOArrayList;
    public String currentcountryID = "";

    public static Spinner spinnerCurrentResidenceDuration;
    public static ArrayAdapter arrayAdapter_currentResidenceDuration;
    public static ArrayList<String> currentresidenceduration_arrayList;
    public static ArrayList<CoborrowerCurrentResidenceDurationPersonalPOJO> coborrowerCurrentResidenceDurationPersonalPOJOArrayList;
    public String currentresidenceDurationID = "";

    public static Spinner spinnerRelationshipwithBorrower;
    public static ArrayAdapter arrayAdapter_relationshipwithborrower;
    public static ArrayList<String> relationshipwithborrower_arrayList;
    public static ArrayList<RelationshipwithBorrowerPOJO> relationshipwithBorrowerPOJOArrayList;
    public String relationshipwithborrowerID = "";

    /**
     * FINANCIAL DETAILS
     **/
    public static EditText anuualincome, employeer;

    public static Spinner spinnerProfession;
    public static ArrayAdapter profession_arrayAdapter;
    public static ArrayList<String> professionfinance_arratList;
    public static ArrayList<CoborrowerProfessionFinancePOJO> coborrowerProfessionFinancePOJOArrayList;

    public static Spinner spinnerJobDuration;
    public static ArrayAdapter jobduration_arrayAdapter;
    public static ArrayList<String> jobduration_arratList;
    public static ArrayList<CoborrowerJobDurationFinancePOJO> coborrowerJobDurationFinancePOJOArrayList;
    public String jobDurationID = "";

    public static LinearLayout linearLayoutLeftOffcoBorrower;

    public static CheckBox checkBoxSameasAbove;

    public String lead_id = "", application_id = "", requested_loan_amount = "", institute_name = "", location_name = "",
            course_name = "", course_cost = "", has_coborrower = "";

    public String Brapplicant_id = "", Brfk_lead_id = "", Brfk_applicant_type_id = "", Brfirst_name = "", Brmiddle_name = "",
            Brlast_name = "", Brhas_aadhar_pan = "", Brdob = "", Brpan_number = "", Braadhar_number = "", Brmarital_status = "",
            Brgender_id = "", Brmobile_number = "", Bremail_id = "", Brrelationship_with_applicant = "", Brprofession = "",
            Bremployer_type = "", Bremployer_name = "", Brannual_income = "", Brcurrent_employment_duration = "",
            Brtotal_employement_duration = "", Bremployer_mobile_number = "", Bremployer_landline_number = "", Broffice_landmark = "",
            Broffice_address = "", Broffice_address_city = "", Broffice_address_state = "", Broffice_address_country = "",
            Broffice_address_pin = "", Brhas_active_loan = "", BrEMI_Amount = "", Brkyc_landmark = "", Brkyc_address = "",
            Brkyc_address_city = "", Brkyc_address_state = "", Brkyc_address_country = "", Brkyc_address_pin = "",
            Bris_borrower_current_address_same_as = "", Bris_coborrower_current_address_same_as = "", Brcurrent_residence_type = "",
            Brcurrent_landmark = "", Brcurrent_address = "", Brcurrent_address_city = "", Brcurrent_address_state = "",
            Brcurrent_address_country = "", Brcurrent_address_pin = "", Brcurrent_address_rent = "",
            Brcurrent_address_stay_duration = "", Bris_borrower_permanent_address_same_as = "",
            Bris_coborrower_permanent_address_same_as = "", Brpermanent_residence_type = "", Brpermanent_landmark = "",
            Brpermanent_address = "", Brpermanent_address_city = "", Brpermanent_address_state = "",
            Brpermanent_address_country = "", Brpermanent_address_pin = "", Brpermanent_address_rent = "",
            Brpermanent_address_stay_duration = "", Brlast_completed_degree = "", Brscore_unit = "", Brcgpa = "",
            Brpercentage = "", Brpassing_year = "", Brgap_in_education = "", Brfull_name_pan_response = "",
            Brcreated_by_id = "", Brcreated_date_time = "", Brcreated_ip_address = "", Brmodified_by = "",
            Brmodified_date_time = "", Brmodified_ip_address = "", Bris_deleted = "";

    public String CoBrapplicant_id = "", CoBrfk_lead_id = "", CoBrfk_applicant_type_id = "", CoBrfirst_name = "",
            CoBrmiddle_name = "", CoBrlast_name = "", CoBrhas_aadhar_pan = "", CoBrdob = "", CoBrpan_number = "",
            CoBraadhar_number = "", CoBrmarital_status = "", CoBrgender_id = "", CoBrmobile_number = "", CoBremail_id = "",
            CoBrrelationship_with_applicant = "", CoBrprofession = "", CoBremployer_type = "", CoBremployer_name = "",
            CoBrannual_income = "", CoBrcurrent_employment_duration = "", CoBrtotal_employement_duration = "",
            CoBremployer_mobile_number = "", CoBremployer_landline_number = "", CoBroffice_landmark = "",
            CoBroffice_address = "", CoBroffice_address_city = "", CoBroffice_address_state = "", CoBroffice_address_country = "",
            CoBroffice_address_pin = "", CoBrhas_active_loan = "", CoBrEMI_Amount = "", CoBrkyc_landmark = "",
            CoBrkyc_address = "", CoBrkyc_address_city = "", CoBrkyc_address_state = "", CoBrkyc_address_country = "",
            CoBrkyc_address_pin = "", CoBris_borrower_current_address_same_as = "", CoBris_coborrower_current_address_same_as = "",
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

    public String lead_status_id = "", fk_lead_id = "", lead_status = "", lead_sub_status = "", current_stage = "", current_status = "", lead_drop_status = "",
            lead_reject_status = "", lead_initiated_datetime = "", is_lead_owner_added = "", lead_owner_added_datetime = "",
            lead_owner_added_by = "", is_lead_counsellor_added = "", lead_counsellor_added_datetime = "", lead_counsellor_added_by = "",
            is_kyc_details_filled = "", kyc_details_filled_datetime = "", kyc_details_filled_by = "", coborrower_added_datetime = "",
            coborrower_added_by_id = "", is_detailed_info_filled = "", detailed_info_filled_datetime = "", detailed_info_filled_by_id = "",
            approval_request_sales_status = "", approval_request_sales_status_datetime = "", approval_request_sales_status_by_id = "",
            list_of_LAF_info_pending = "", list_of_LAF_info_filled = "", IPA_status = "", IPA_datetime = "", IPA_by_id = "",
            docs_upload_status = "", docs_upload_datetime = "", list_of_uplaoded_docs = "", list_of_pendingdocs = "",
            docs_verification_status = "", docs_verification_datetime = "", credit_approval_request_status = "",
            credit_approval_request_status_datetime = "", credit_approval_request_status_by_id = "", applicant_ekyc_status = "",
            applicant_ekyc_datetime = "", co_applicant_ekyc_status = "", co_applicant_ekyc_datetime = "", credit_assessment_status = "",
            credit_assessment_by_id = "", credit_assessment_datetime = "", loan_product_selection_status = "", loan_product_by_id = "",
            loan_product_datetime = "", underwriting_status = "", underwriting_by_id = "", underwriting_datetime = "",
            is_processing_fees_set = "", processing_fees_set_datetime = "", processing_fees_set_by_id = "", processing_fees_paid = "",
            processing_fees_paid_datetime = "", processing_fees_paid_by = "", lender_creation_status = "",
            lender_creation_modified_datetime = "", lender_creation_modified_by = "", amort_creation_status = "",
            amort_creation_modified_datetime = "", amort_creation_modified_by = "", borrower_pan_ekyc_response = "",
            borrower_aadhar_ekyc_response = "", borrower_pan_ekyc_status = "", borrower_aadhar_ekyc_status = "",
            coborrower_pan_ekyc_response = "", coborrower_aadhar_ekyc_response = "", coborrower_aadhar_ekyc_status = "",
            coborrower_pan_ekyc_status = "", is_cam_uploaded = "", is_finbit_uploaded = "", is_exception_uploaded = "",
            is_loan_agreement_uploaded = "", loan_agreement_uploaded_by = "", applicant_pan_verified_by = "",
            coapplicant_pan_verified_by = "", applicant_aadhar_verified_by = "", applicant_pan_verified_on = "", coapplicant_pan_verified_on = "",
            applicant_aadhar_verified_on = "", coapplicant_aadhar_verified_on = "", coapplicant_aadhar_verified_by = "",
            created_date_time = "", created_ip_address = "", modified_by = "", modified_date_time = "", modified_ip_address = "",
            is_deleted = "", borrower_required_docs = "", co_borrower_required_docs = "", co_borrower_pending_docs = "",
            borrower_extra_required_docs = "", co_borrower_extra_required_docs = "", id = "", status_name = "", stage_id = "";

    public LoanApplicationFragment_2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detailedinfo, container, false);

        try {
            context = getContext();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            mainApplication = new MainApplication();
            mFragment = new LoanApplicationFragment_2();
            transaction = getFragmentManager().beginTransaction();

            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            userID = sharedPreferences.getString("logged_id", "null");
            coBorrowerBackground = sharedPreferences.getString("coBorrowerBackground_dark", "0");

            MainApplication.currrentFrag = 2;

            typeface = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");

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

                    boolean isIDNull = !adhaarno.getText().toString().equals("") ||
                            !panno.getText().toString().equals("");

                    if (!fname.getText().toString().equals("") && !lname.getText().toString().equals("") &&
                            !textViewbirthday.getText().toString().equals("") &&
                            !currentaddress.getText().toString().equals("") &&
                            !currentpincode.getText().toString().equals("") &&
                            !permanentaddress.getText().toString().equals("") &&
                            !permanentpincode.getText().toString().equals("") &&
                            !contactno.getText().toString().equals("") &&
                            !emailid.getText().toString().equals("") &&
                            !anuualincome.getText().toString().equals("") &&
                            !employeer.getText().toString().equals("") && isIDNull) {

                        if (radioGroupGender.getCheckedRadioButtonId() > 0) {
                            radioButtonFemale.setError(null);

                            if (radioGroupMaritialStatus.getCheckedRadioButtonId() > 0) {
                                radioButtonSingle.setError(null);

                                if (radioGroupPreviousEmi.getCheckedRadioButtonId() > 0) {
                                    radioButtonPreviousEmiNo.setError(null);

                                    if (radioGroupEmployerType.getCheckedRadioButtonId() > 0) {
                                        radioButtonPrivate.setError(null);

                                        if (!currentcityID.equalsIgnoreCase("") && !currentstateID.equalsIgnoreCase("") &&
                                                !currentcountryID.equalsIgnoreCase("") && !permanentcityID.equalsIgnoreCase("") &&
                                                !permanentstateID.equalsIgnoreCase("") && !permanentCountryID.equalsIgnoreCase("") &&
                                                !currentResidencetypeID.equalsIgnoreCase("") && !currentresidenceDurationID.equalsIgnoreCase("") &&
                                                !relationshipwithborrowerID.equalsIgnoreCase("") && !professionID.equalsIgnoreCase("") &&
                                                !jobDurationID.equalsIgnoreCase("")) {

                                            try {
                                                String maritialstatus = "";
                                                if (radioButtonMarried.isChecked()) {
                                                    maritialstatus = "1";
                                                } else if (radioButtonSingle.isChecked()) {
                                                    maritialstatus = "2";
                                                }

                                                String previousEmi = "";
                                                if (radioButtonPreviousEmiYes.isChecked()) {
                                                    previousEmi = "1";
                                                } else if (radioButtonPreviousEmiNo.isChecked()) {
                                                    previousEmi = "0";
                                                }

                                                String gender = "";
                                                if (radioButtonMale.isChecked()) {
                                                    gender = "1";
                                                }
                                                if (radioButtonFemale.isChecked()) {
                                                    gender = "2";
                                                }

                                                String empType = "";
                                                if (radioButtonGovernment.isChecked()) {
                                                    empType = "0";
                                                }
                                                if (radioButtonPrivate.isChecked()) {
                                                    empType = "1";
                                                }

                                                progressBar.setVisibility(View.VISIBLE);
                                                String url = MainApplication.mainUrl + "algo/setCoBorrowerLoanDetails";
                                                Map<String, String> params = new HashMap<String, String>();
                                                params.put("logged_id", userID);
                                                params.put("coborrower_address_type", currentResidencetypeID);
                                                params.put("coborrower_monthly_rent", monthlyrent.getText().toString());
                                                params.put("coborrower_current_address", currentaddress.getText().toString());
                                                params.put("coborrower_current_city", currentcityID);
                                                params.put("coborrower_current_state", currentstateID);
                                                params.put("coborrower_current_country", currentcountryID);
                                                params.put("coborrower_current_pincode", currentpincode.getText().toString());
                                                params.put("coborrower_permanent_address", permanentaddress.getText().toString());
                                                params.put("coborrower_permanent_city", permanentcityID);
                                                params.put("coborrower_permanent_state", permanentstateID);
                                                params.put("coborrower_permanent_country", permanentCountryID);
                                                params.put("coborrower_permanent_pincode", permanentpincode.getText().toString());
                                                params.put("coborrower_first_name", fname.getText().toString());
                                                params.put("coborrower_last_name", lname.getText().toString());
                                                params.put("coborrower_dob", textViewbirthday.getText().toString());
                                                params.put("coborrower_is_married", maritialstatus);
                                                params.put("coborrower_pan_no", panno.getText().toString());
                                                params.put("coborrower_aadhar_no", adhaarno.getText().toString());

                                                params.put("coborrower_email", emailid.getText().toString());
                                                params.put("coborrower_mobile", contactno.getText().toString());
                                                params.put("coborrower_living_since", currentresidenceDurationID);
                                                params.put("coborrower_relationship", relationshipwithborrowerID);

                                                params.put("coborrower_profession", professionID);
                                                params.put("coborrower_income", anuualincome.getText().toString());
                                                params.put("coborrower_organization", employeer.getText().toString());
                                                params.put("coborrower_working_organization_since", jobDurationID);
                                                params.put("coborrower_has_any_emi", previousEmi);
                                                params.put("ip_address", Utils.getIPAddress(true));

                                                params.put("coborrower_previous_emi_amount", previousemi.getText().toString());

                                                params.put("coborrower_gender_id", gender);

                                                params.put("coborrower_employer_type", empType);

                                                MainApplication.coborrowerValue1 = currentResidencetypeID;
                                                MainApplication.coborrowerValue2 = monthlyrent.getText().toString();
                                                MainApplication.coborrowerValue3 = currentaddress.getText().toString();
                                                MainApplication.coborrowerValue4 = currentcityID;
                                                MainApplication.coborrowerValue5 = currentstateID;
                                                MainApplication.coborrowerValue6 = currentcountryID;
                                                MainApplication.coborrowerValue7 = currentpincode.getText().toString();
                                                MainApplication.coborrowerValue8 = permanentaddress.getText().toString();
                                                MainApplication.coborrowerValue9 = permanentcityID;
                                                MainApplication.coborrowerValue10 = permanentstateID;
                                                MainApplication.coborrowerValue11 = permanentCountryID;
                                                MainApplication.coborrowerValue12 = permanentpincode.getText().toString();
                                                MainApplication.coborrowerValue13 = fname.getText().toString();
                                                MainApplication.coborrowerValue14 = lname.getText().toString();
                                                MainApplication.coborrowerValue15 = textViewbirthday.getText().toString();
                                                MainApplication.coborrowerValue16 = maritialstatus;
                                                MainApplication.coborrowerValue17 = panno.getText().toString();
                                                MainApplication.coborrowerValue18 = adhaarno.getText().toString();

                                                MainApplication.coborrowerValue19 = emailid.getText().toString();
                                                MainApplication.coborrowerValue20 = contactno.getText().toString();
                                                MainApplication.coborrowerValue21 = currentresidenceDurationID;
                                                MainApplication.coborrowerValue22 = relationshipwithborrowerID;
                                                MainApplication.coborrowerValue23 = professionID;
                                                MainApplication.coborrowerValue24 = anuualincome.getText().toString();
                                                MainApplication.coborrowerValue25 = employeer.getText().toString();
                                                MainApplication.coborrowerValue26 = jobDurationID;
                                                MainApplication.coborrowerValue27 = previousEmi;
                                                MainApplication.coborrowerValue28 = previousemi.getText().toString();
                                                MainApplication.coborrowerValue29 = gender;
                                                MainApplication.coborrowerValue30 = empType;
                                                if (!Globle.isNetworkAvailable(context)) {
                                                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                                                } else {

                                                    VolleyCallNew volleyCall = new VolleyCallNew();
                                                    volleyCall.sendRequest(context, url, null, mFragment, "sendcoboorrowerDetails", params, MainApplication.auth_token);
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            if (spinnerCurrentResidenceDuration.getSelectedItemPosition() <= 0) {
                                                setSpinnerError(spinnerCurrentResidenceDuration, getString(R.string.please_select_duration_of_stay_at_current_address));
                                                spinnerCurrentResidenceDuration.requestFocus();
                                            }
                                            if (spinnerProfession.getSelectedItemPosition() <= 0) {
                                                setSpinnerError(spinnerProfession, getString(R.string.please_select_profession));
                                                spinnerProfession.requestFocus();
                                            }
                                            if (spinnerJobDuration.getSelectedItemPosition() <= 0) {
                                                setSpinnerError(spinnerJobDuration, getString(R.string.please_select_duration_of_job_business));
                                                spinnerJobDuration.requestFocus();
                                            }
                                            Toast.makeText(context, R.string.please_fill_up_all_the_details_to_continue, Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        radioButtonPrivate.setError(getString(R.string.you_need_to_select_employer_type));
                                        radioButtonPrivate.requestFocus();
                                    }

                                } else {
                                    radioButtonPreviousEmiNo.setError(getString(R.string.you_need_to_select_previous_emi_status));
                                    radioButtonPreviousEmiNo.requestFocus();
                                }

                            } else {
                                radioButtonSingle.setError(getString(R.string.you_need_to_select_maritial_status));
                                radioButtonSingle.requestFocus();
                            }
                        } else {
                            radioButtonFemale.setError(getString(R.string.you_need_to_select_gender));
                            radioButtonFemale.requestFocus();
                        }

                    } else {

                        if (fname.getText().toString().equalsIgnoreCase("")) {
                            fname.setError(getString(R.string.first_name_is_required));
                            fname.requestFocus();
                        } else {
                            fname.setError(null);

                        }

                        if (lname.getText().toString().equalsIgnoreCase("")) {
                            lname.setError(getString(R.string.last_name_is_required));
                            lname.requestFocus();
                        } else {
                            lname.setError(null);

                        }

                        if (textViewbirthday.getText().toString().equalsIgnoreCase("")) {
                            textViewbirthday.setError(getString(R.string.birthdate_is_required));
                            textViewbirthday.requestFocus();
                        } else if (textViewbirthday.getText().toString().toLowerCase().equals("birthdate")) {
                            textViewbirthday.setError(getString(R.string.birthdate_is_required));
                            textViewbirthday.requestFocus();
                        } else {
                            textViewbirthday.setError(null);
                        }

                        if (adhaarno.getText().toString().equalsIgnoreCase("")) {
                            adhaarno.setError(getString(R.string.adhaar_number_is_required));
                            adhaarno.requestFocus();
                        } else {
                            adhaarno.setError(null);
                        }
                        if (panno.getText().toString().equalsIgnoreCase("")) {
                            panno.setError(getString(R.string.pan_number_is_required));
                            panno.requestFocus();
                        } else {
                            panno.setError(null);
                        }

                        //                    if (adhaarno.getText().toString().equalsIgnoreCase("") && panno.getText().toString().equalsIgnoreCase("")) {
                        //                        adhaarno.setError("Aadhaar Number is Required");
                        //                        adhaarno.requestFocus();
                        //                    }
                        //                    else {
                        //                        adhaarno.setError(null);
                        //
                        //                    }
                        //                    if (panno.getText().toString().equalsIgnoreCase("") && adhaarno.getText().toString().equalsIgnoreCase("")) {
                        //                        panno.setError("PAN number is required is Required");
                        //                        panno.requestFocus();
                        //                    }else {
                        //                        panno.setError(null);
                        //
                        //                    }

                        if (currentaddress.getText().toString().equalsIgnoreCase("")) {
                            currentaddress.setError(getString(R.string.current_address_is_required));
                            currentaddress.requestFocus();
                        } else {
                            currentaddress.setError(null);
                        }

                        if (currentpincode.getText().toString().equalsIgnoreCase("")) {
                            currentpincode.setError(getString(R.string.current_pin_code_is_required));
                            currentpincode.requestFocus();
                        } else {
                            currentpincode.setError(null);

                        }

                        if (permanentaddress.getText().toString().equalsIgnoreCase("")) {
                            permanentaddress.setError(getString(R.string.permanent_address_is_required));
                            permanentaddress.requestFocus();
                        } else {
                            permanentaddress.setError(null);
                        }

                        if (permanentpincode.getText().toString().equalsIgnoreCase("")) {
                            permanentpincode.setError(getString(R.string.permanent_pin_is_required));
                            permanentpincode.requestFocus();
                        } else {
                            permanentpincode.setError(null);
                        }

                        if (contactno.getText().toString().equalsIgnoreCase("")) {
                            contactno.setError(getString(R.string.contact_number_is_required));
                            contactno.requestFocus();
                        } else {
                            contactno.setError(null);
                        }

                        if (emailid.getText().toString().equalsIgnoreCase("")) {
                            emailid.setError(getString(R.string.emailid_is_required));
                            emailid.requestFocus();
                        } else {
                            emailid.setError(null);

                        }

                        if (anuualincome.getText().toString().equalsIgnoreCase("")) {
                            anuualincome.setError(getString(R.string.annual_income_is_required));
                            anuualincome.requestFocus();
                        } else {
                            anuualincome.setError(null);

                        }
                        if (employeer.getText().toString().equalsIgnoreCase("")) {
                            employeer.setError(getString(R.string.employer_name_is_required));
                        }
                        if (!employeer.getText().toString().equalsIgnoreCase("")) {
                            employeer.setError(null);
                        }
                        if (spinnerRelationshipwithBorrower.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spinnerRelationshipwithBorrower, getString(R.string.please_select_relationship_with_borrower));
                            spinnerRelationshipwithBorrower.requestFocus();
                        }

                        if (spinnerCurrentResidenceType.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spinnerCurrentResidenceType, getString(R.string.please_select_resideny_type));
                            spinnerCurrentResidenceType.requestFocus();

                        }
                        if (spinnerCurrentCountry.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spinnerCurrentCountry, getString(R.string.please_select_current_country));

                        }
                        if (spinnerCurrentState.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spinnerCurrentState, getString(R.string.please_select_current_state));

                        }
                        if (spinnerCurrentCity.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spinnerCurrentCity, getString(R.string.please_select_current_city));

                        }
                        if (spinnerPermanentCountry.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spinnerPermanentCountry, getString(R.string.please_select_permanent_country));
                            spinnerPermanentCountry.requestFocus();

                        }
                        if (spinnerPermanentState.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spinnerPermanentState, getString(R.string.please_select_permanent_state));

                        }
                        if (spinnerPermanentCity.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spinnerPermanentCity, getString(R.string.please_select_permanent_city));

                        }
                        if (spinnerCurrentResidenceDuration.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spinnerCurrentResidenceDuration, getString(R.string.please_select_duration_of_stay_at_current_address));
                            spinnerCurrentResidenceDuration.requestFocus();

                        }
                        if (spinnerProfession.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spinnerProfession, getString(R.string.please_select_profession));
                            spinnerProfession.requestFocus();

                        }
                        if (spinnerJobDuration.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spinnerJobDuration, getString(R.string.please_select_duration_of_job_business));
                            spinnerJobDuration.requestFocus();
                        }
                    }
                }
            });

            buttonPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String url = MainApplication.mainUrl + "algo/setCoBorrowerLoanDetails";
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("logged_id", userID);
                        params.put("coborrower_address_type", MainApplication.coborrowerValue1);
                        params.put("coborrower_monthly_rent", MainApplication.coborrowerValue2);
                        params.put("coborrower_current_address", MainApplication.coborrowerValue3);
                        params.put("coborrower_current_city", MainApplication.coborrowerValue4);
                        params.put("coborrower_current_state", MainApplication.coborrowerValue5);
                        params.put("coborrower_current_country", MainApplication.coborrowerValue6);
                        params.put("coborrower_current_pincode", MainApplication.coborrowerValue7);
                        params.put("coborrower_permanent_address", MainApplication.coborrowerValue8);
                        params.put("coborrower_permanent_city", MainApplication.coborrowerValue9);
                        params.put("coborrower_permanent_state", MainApplication.coborrowerValue10);
                        params.put("coborrower_permanent_country", MainApplication.coborrowerValue11);
                        params.put("coborrower_permanent_pincode", MainApplication.coborrowerValue12);
                        params.put("coborrower_first_name", MainApplication.coborrowerValue13);
                        params.put("coborrower_last_name", MainApplication.coborrowerValue14);
                        params.put("coborrower_dob", MainApplication.coborrowerValue15);
                        params.put("coborrower_is_married", MainApplication.coborrowerValue16);
                        params.put("coborrower_pan_no", MainApplication.coborrowerValue17);
                        params.put("coborrower_aadhar_no", MainApplication.coborrowerValue18);

                        params.put("coborrower_email", MainApplication.coborrowerValue19);
                        params.put("coborrower_mobile", MainApplication.coborrowerValue20);
                        params.put("coborrower_living_since", MainApplication.coborrowerValue21);
                        params.put("coborrower_relationship", MainApplication.coborrowerValue22);

                        params.put("coborrower_profession", MainApplication.coborrowerValue23);
                        params.put("coborrower_income", MainApplication.coborrowerValue24);
                        params.put("coborrower_organization", MainApplication.coborrowerValue25);
                        params.put("coborrower_working_organization_since", MainApplication.coborrowerValue26);

                        VolleyCallNew volleyCall = new VolleyCallNew();
                        volleyCall.sendRequest(context, url, null, mFragment, "autosave", params, MainApplication.auth_token);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LoanApplicationFragment_1 loanApplicationFragment_1 = new LoanApplicationFragment_1();
                    transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_1).commit();
                }
            });

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
                    MainApplication.coborrowerValue15 = textViewbirthday.getText().toString();
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

            radioButtonMarried.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (radioButtonMarried.isChecked()) {
                        MainApplication.coborrowerValue16 = "1";
                    }
                }
            });

            radioButtonSingle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (radioButtonSingle.isChecked()) {
                        MainApplication.coborrowerValue16 = "2";
                    }
                }
            });

            radioButtonPreviousEmiYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (radioButtonPreviousEmiYes.isChecked()) {
                        MainApplication.coborrowerValue27 = "1";
                    }
                }
            });

            radioButtonPreviousEmiNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (radioButtonPreviousEmiNo.isChecked()) {
                        MainApplication.coborrowerValue27 = "0";
                    }
                }
            });


            radioGroupPreviousEmi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Log.e("TAG", "onCheckedChanged: ");
                    Log.e("TAG", "onCheckedChanged: " + radioGroupPreviousEmi.getCheckedRadioButtonId());
                    switch (radioGroupPreviousEmi.getCheckedRadioButtonId()) {
                        case R.id.radiobutton_emiyes_coborrower:
                            Log.e("TAG", "yes: ");
                            input_previousamt.setVisibility(View.VISIBLE);
                            previousemi.requestFocus();
                            //previousemi.setVisibility(View.VISIBLE);
                            break;
                        case R.id.radiobutton_emino_coborrower:
                            Log.e("TAG", "no: ");
                            input_previousamt.setVisibility(View.GONE);
                            //previousemi.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });

            /** EDIT TEXT CHANGE LISTENER FOR PARTIAL SAVING **/

            fname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue13 = fname.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            lname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue14 = lname.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            adhaarno.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue18 = adhaarno.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            panno.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue17 = panno.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            monthlyrent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue2 = monthlyrent.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            currentaddress.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue3 = currentaddress.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            currentpincode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue7 = currentpincode.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            permanentaddress.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue8 = permanentaddress.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            permanentpincode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue12 = permanentpincode.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            anuualincome.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue24 = anuualincome.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            employeer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue25 = employeer.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            contactno.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue20 = contactno.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            emailid.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue19 = emailid.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            /** END OF EDIT TEXT CHANGE LISTENER FOR PARTIAL SAVING**/

            /** SPINNER CLICK **/

            spinnerCurrentResidenceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spinnerCurrentResidenceType.getSelectedItem().toString();
                        int count = coborrowerCurrentResidenceTypePersonalPOJOArrayList.size();
//                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).residencetypeName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue1 = currentResidencetypeID = coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID;
                                Log.e(TAG, "ididididididid:" + coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID);
                                if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("1")) {
                                    monthlyrent.setVisibility(View.GONE);
                                    monthlyrent.setText("");
                                } else if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("3")) {
                                    monthlyrent.setVisibility(View.GONE);
                                    monthlyrent.setText("");
                                } else if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("8")) {
                                    monthlyrent.setVisibility(View.GONE);
                                    monthlyrent.setText("");
                                } else {
                                    monthlyrent.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerCurrentCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        if (spinnerCurrentCity.getSelectedItemPosition() > 0) {
                            checkBoxSameasAbove.setEnabled(true);
                            checkBoxSameasAbove.setClickable(true);
                        } else {
                            checkBoxSameasAbove.setEnabled(false);
                            checkBoxSameasAbove.setClickable(false);
                        }
                        String text = spinnerCurrentCity.getSelectedItem().toString();
                        int count = borrowerCurrentCityPersonalPOJOArrayList.size();
//                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue4 = currentcityID = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
                                Log.e("I_________D", "currentcityID: " + currentcityID);
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

            spinnerCurrentState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spinnerCurrentState.getSelectedItem().toString();
                        int count = borrowerCurrentStatePersonalPOJOArrayList.size();
//                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue5 = currentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
//                                Log.e("I_________D", "currentstateID: " + currentstateID);
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

            spinnerCurrentCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spinnerCurrentCountry.getSelectedItem().toString();
                        int count = borrowerCurrentCountryPersonalPOJOArrayList.size();
                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue6 = currentcountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                                Log.e("I_________D", "currentcountryID: " + currentcountryID);
                            }
                        }
                        stateApiCall();
                        if (currentcityID.equals("")) {
                            spinnerCurrentCity.setSelection(0);
                        } else {
                            spinnerCurrentCity.setSelection(Integer.parseInt(currentcityID) - 1);
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

            spinnerPermanentCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spinnerPermanentCity.getSelectedItem().toString();
                        int count = borrowerPermanentCityPersonalPOJOArrayList.size();
//                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue9 = permanentcityID = borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID;
//                                Log.e("I_________D", "permanentcityID: " + permanentcityID);
                            }
                        }

                        if (count > 0) {
                            if (checkBoxSameasAbove.isChecked()) {
                                for (int i = 0; i < borrowerPermanentCityPersonalPOJOArrayList.size(); i++) {
                                    if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                                        spinnerPermanentCity.setSelection(i);
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

            spinnerPermanentState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String text = spinnerPermanentState.getSelectedItem().toString();
                    try {
//                        int count = borrowerPermanentStatePersonalPOJOArrayList.size();
                        int count = borrowerCurrentStatePersonalPOJOArrayList.size();
                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue10 = permanentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
                                Log.e("I_________D", "permanentstateID: " + permanentstateID);
                            }
                        }

                        if (checkBoxSameasAbove.isChecked()) {
                            Log.e(TAG, "getPermanentStates: +++++++++++++++++++*********************" + currentstateID);
                            spinnerPermanentState.setSelection(Integer.parseInt(currentstateID));
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

            spinnerPermanentCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spinnerPermanentCountry.getSelectedItem().toString();
                        int count = borrowerCurrentCountryPersonalPOJOArrayList.size();
                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue10 = permanentCountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                                Log.e("I_________D", "permanentCountryID: " + permanentCountryID);
                            }
                        }
                        permanentStateApiCall();
                        if (permanentcityID.equals("")) {
                            spinnerPermanentCity.setSelection(0);
                        } else {
                            spinnerPermanentCity.setSelection(Integer.parseInt(permanentcityID) - 1);
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

            spinnerCurrentResidenceDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spinnerCurrentResidenceDuration.getSelectedItem().toString();
                        int count = coborrowerCurrentResidenceDurationPersonalPOJOArrayList.size();
                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (coborrowerCurrentResidenceDurationPersonalPOJOArrayList.get(i).durationName.equalsIgnoreCase(text)) {
                                currentresidenceDurationID = coborrowerCurrentResidenceDurationPersonalPOJOArrayList.get(i).durationID;
                                Log.e("I_________D", "currentresidenceDurationID: " + currentresidenceDurationID);
                            }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerRelationshipwithBorrower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spinnerRelationshipwithBorrower.getSelectedItem().toString();
                        int count = relationshipwithBorrowerPOJOArrayList.size();
                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (relationshipwithBorrowerPOJOArrayList.get(i).relationName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue22 = relationshipwithborrowerID = relationshipwithBorrowerPOJOArrayList.get(i).relationID;
                                Log.e("I_________D", "currentresidenceDurationID: " + relationshipwithborrowerID);
                            }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /** FINANCIAL DETAILS **/
            spinnerJobDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        Log.e("I_________D", "onItemClick: ");
                        String text = spinnerJobDuration.getSelectedItem().toString();
                        int count = coborrowerJobDurationFinancePOJOArrayList.size();
                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (coborrowerJobDurationFinancePOJOArrayList.get(i).durationName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue26 = jobDurationID = coborrowerJobDurationFinancePOJOArrayList.get(i).durationID;
                                Log.e("I_________D", "jobDurationID: " + jobDurationID);
                            }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /** EDUCATION **/
            radiogroup_iscgpa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (radiogroup_iscgpa.getCheckedRadioButtonId()) {
                        case R.id.radiobutton_iscgpa_yes:
                            input_cgpa.setVisibility(View.VISIBLE);
                            input_degree.setVisibility(View.GONE);
                            break;
                        case R.id.radiobutton_iscgpa_no:
                            input_cgpa.setVisibility(View.GONE);
                            input_degree.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }
                }
            });

            radioGroupPreviousEmi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (radioGroupPreviousEmi.getCheckedRadioButtonId()) {
                        case R.id.radiobutton_emiyes:
                            input_previousamt.setVisibility(View.VISIBLE);
                            previousemi.requestFocus();
                            //previousemi.setVisibility(View.VISIBLE);
                            break;
                        case R.id.radiobutton_emino:
                            input_previousamt.setVisibility(View.GONE);
                            //previousemi.setVisibility(View.GONE);
                            break;
                        default:
                            break;
                    }
                }
            });


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


            spinnerProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        Log.e("I_________D", "onItemClick: ");
                        String text = spinnerProfession.getSelectedItem().toString();
                        int count = coborrowerProfessionFinancePOJOArrayList.size();
                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (coborrowerProfessionFinancePOJOArrayList.get(i).professionName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue23 = professionID = coborrowerProfessionFinancePOJOArrayList.get(i).professionID;
                                Log.e("I_________D", "professionID: " + professionID);
                            }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            /** END SPINNER CLICK **/

            checkBoxSameasAbove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBoxSameasAbove.isChecked()) {
                        permanentaddress.setText(currentaddress.getText().toString());
                        permanentpincode.setText(currentpincode.getText().toString());

                        try {
                            spinnerPermanentCountry.setSelection(Integer.parseInt(currentcountryID));
                            spinnerPermanentState.setSelection(Integer.parseInt(currentstateID));
                            spinnerPermanentCity.setSelection(Integer.parseInt(currentcityID));

                            try {
                                spinnerPermanentCountry.setEnabled(false);
                                spinnerPermanentState.setEnabled(false);
                                spinnerPermanentCity.setEnabled(false);
                                permanentaddress.setEnabled(false);
                                permanentpincode.setEnabled(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                    } else {
                        permanentaddress.setText("");
                        permanentpincode.setText("");

                        try {
                            spinnerPermanentCountry.setSelection(1);
                            spinnerPermanentState.setSelection(0);
                            spinnerPermanentCity.setSelection(0);

                            try {
                                spinnerPermanentCountry.setEnabled(true);
                                spinnerPermanentState.setEnabled(true);
                                spinnerPermanentCity.setEnabled(true);
                                permanentaddress.setEnabled(true);
                                permanentpincode.setEnabled(true);
                            } catch (Exception e) {
                                String className = this.getClass().getSimpleName();
                                String name = new Object() {
                                }.getClass().getEnclosingMethod().getName();
                                String errorMsg = e.getMessage();
                                String errorMsgDetails = e.getStackTrace().toString();
                                String errorLine = String.valueOf(e.getStackTrace()[0]);
                                Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
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
            });


            if (!Globle.isNetworkAvailable(context)) {

            } else {
                JSONObject jsonObject = new JSONObject();

                getCurrentStatesCo(jsonObject);
                getCurrentCitiesCo(jsonObject);
                ProfessionApiCall();
            }

            /** API CALL POST LOGIN DASHBOARD STATUS **/
            try {
                String url = MainApplication.mainUrl + "dashboard/getDetailedInformation";
                Map<String, String> params = new HashMap<String, String>();
                params.put("lead_id", MainApplication.lead_id);
                if (!Globle.isNetworkAvailable(context)) {
                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                } else {
                    VolleyCallNew volleyCall = new VolleyCallNew();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
                    volleyCall.sendRequest(context, url, null, mFragment, "getDetailedInformation", params, MainApplication.auth_token);
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

            /** END OF API CALL **/
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
    }

    public void setDetailedInformation(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (jsonData.getInt("status") == 1) {

                if (jsonData.getJSONArray("countries").length() > 0) {
                    JSONArray jsonArraycountries = jsonData.getJSONArray("countries");
                }

                if (jsonData.getJSONArray("states").length() > 0) {
                    JSONArray jsonArraystates = jsonData.getJSONArray("states");
                    currentstate_arrayList = new ArrayList<>();
                    borrowerCurrentStatePersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArraystates.length(); i++) {
                        BorrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new BorrowerCurrentStatePersonalPOJO();
                        JSONObject mJsonti = jsonArraystates.getJSONObject(i);
                        borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                        currentstate_arrayList.add(mJsonti.getString("state_name"));
                        borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                        borrowerCurrentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
                    }
                    arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                    spCurrentStateBr.setAdapter(arrayAdapter_currentState);
                    arrayAdapter_currentState.notifyDataSetChanged();

//                    spCurrentStateBr.setSelection(Integer.parseInt(currentstateID));
                }

                if (jsonData.getJSONArray("cities").length() > 0) {

                    JSONArray jsonArraycities = jsonData.getJSONArray("cities");
                    currentcity_arrayList = new ArrayList<>();
                    borrowerCurrentCityPersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArraycities.length(); i++) {
                        BorrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new BorrowerCurrentCityPersonalPOJO();
                        JSONObject mJsonti = jsonArraycities.getJSONObject(i);
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
                }

                if (jsonData.getJSONArray("cocountries").length() > 0) {
                    JSONArray jsonArraycocountries = jsonData.getJSONArray("cocountries");
                }
                if (jsonData.getJSONArray("costates").length() > 0) {
                    JSONArray jsonArraycostates = jsonData.getJSONArray("costates");
                }
                if (jsonData.getJSONArray("cocities").length() > 0) {
                    JSONArray jsonArraycocities = jsonData.getJSONArray("cocities");
                }

                jsonData.getJSONObject("leadId");
                JSONObject jsonkycDetails = jsonData.getJSONObject("kycDetails");
                JSONObject jsondetailedInformation = jsonData.getJSONObject("detailedInformation");
                JSONObject jsonborrowerDetails = jsonData.getJSONObject("borrowerDetails");
                JSONObject jsoncoborrowerDetails = jsonData.getJSONObject("coborrowerDetails");
                JSONObject jsonleadStatus = jsonData.getJSONObject("leadStatus");
                JSONObject jsoncourseAmount = jsonData.getJSONObject("courseAmount");
                JSONObject jsonleadid = jsonData.getJSONObject("leadid");
                JSONObject jsonapplicantID = jsonData.getJSONObject("applicantID");

                lead_id = jsondetailedInformation.getString("lead_id");
                application_id = jsondetailedInformation.getString("application_id");
                requested_loan_amount = jsondetailedInformation.getString("requested_loan_amount");
                institute_name = jsondetailedInformation.getString("institute_name");
                location_name = jsondetailedInformation.getString("location_name");
                course_name = jsondetailedInformation.getString("course_name");
                course_cost = jsondetailedInformation.getString("course_cost");
                has_coborrower = jsondetailedInformation.getString("has_coborrower");

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
                coapplicant_pan_verified_by = jsonleadStatus.getString("coapplicant_pan_verified_by");
                applicant_aadhar_verified_by = jsonleadStatus.getString("applicant_aadhar_verified_by");
                applicant_pan_verified_on = jsonleadStatus.getString("applicant_pan_verified_on");
                coapplicant_pan_verified_on = jsonleadStatus.getString("coapplicant_pan_verified_on");
                applicant_aadhar_verified_on = jsonleadStatus.getString("applicant_aadhar_verified_on");
                coapplicant_aadhar_verified_on = jsonleadStatus.getString("coapplicant_aadhar_verified_on");
                coapplicant_aadhar_verified_by = jsonleadStatus.getString("coapplicant_aadhar_verified_by");
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
//                JSONObject jsonkycDetails = jsonData.getJSONObject("cities");
//                JSONObject jsonkycDetails = jsonData.getJSONObject("states");
//                JSONObject jsonkycDetails = jsonData.getJSONObject("countries");
//                JSONObject jsonkycDetails = jsonData.getJSONObject("cocities");
//                JSONObject jsonkycDetails = jsonData.getJSONObject("costates");
//                JSONObject jsonkycDetails = jsonData.getJSONObject("cocountries");
//                JSONObject jsonkycDetails = jsonData.getJSONObject("applicantID");

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
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    private void ProfessionApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "dashboard/getAllProfession";
            Map<String, String> params = new HashMap<String, String>();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getAllProfessiondetailedinfo", params, MainApplication.auth_token);
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

    public void getAllProfessiondetailedinfo(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    profession_arrayList = new ArrayList<>();
                    profession_arrayList.add("Select Profession");
                    arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
                    spProfessionBr.setAdapter(arrayAdapter_profession);
                    arrayAdapter_profession.notifyDataSetChanged();
                    spProfessionBr.setSelection(0);

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

                    JSONArray jsonArray3 = jsonObject.getJSONArray("profession");
                    profession_arrayList = new ArrayList<>();
                    professionPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        ProfessionPOJO borrowerCurrentStatePersonalPOJO = new ProfessionPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borrowerCurrentStatePersonalPOJO.Salaried = mJsonti.getString("profession");
                        profession_arrayList.add(mJsonti.getString("profession"));
                        borrowerCurrentStatePersonalPOJO.id = mJsonti.getString("id");
                        professionPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
                    }
                    arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
                    spProfessionBr.setAdapter(arrayAdapter_profession);
                    arrayAdapter_profession.notifyDataSetChanged();

                    spProfessionBr.setSelection(Integer.parseInt(professionID));

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


    private void cityApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getCities";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", currentcountryID);
            params.put("stateId", currentstateID);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentCities(currentstateID, currentcountryID);
            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getCurrentCityCo", params, MainApplication.auth_token);
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
                volleyCall.sendRequest(context, url, null, mFragment, "getCurrentStatesCo", params, MainApplication.auth_token);
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
//                getPermanentCities(permanentstateID, permanentCountryID);

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getPermanentCityCo", params, MainApplication.auth_token);
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
//                getPermanentStates(permanentCountryID);

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getPermanentStatesCo", params, MainApplication.auth_token);
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
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar_applylona_coborrower);

            linBorrowerForm = (LinearLayout) view.findViewById(R.id.linBorrowerForm);
            linCoCorrowerForm = (LinearLayout) view.findViewById(R.id.linCoCorrowerForm);

            linCurrentAddress = (LinearLayout) view.findViewById(R.id.linCurrentAddress);
            linPermanentAddress = (LinearLayout) view.findViewById(R.id.linPermanentAddress);

            relborrower = (RelativeLayout) view.findViewById(R.id.relborrower);
            relCoborrower = (RelativeLayout) view.findViewById(R.id.relCoborrower);

            txtBorrowerArrowKey = (TextView) view.findViewById(R.id.txtBorrowerArrowKey);
            txtCoBorrowerArrowKey = (TextView) view.findViewById(R.id.txtCoBorrowerArrowKey);

            linearLayoutLeftOffcoBorrower = (LinearLayout) view.findViewById(R.id.linearLayout_leftoff);
            textView1 = (TextView) view.findViewById(R.id.textView1_l2);
            mainApplication.applyTypeface(textView1, context);
            textView2 = (TextView) view.findViewById(R.id.textView2_l2);
            if (coBorrowerBackground.equalsIgnoreCase("1")) {
                textView2.setBackground(getResources().getDrawable(R.drawable.background_capsule_primarydark));
                linearLayoutLeftOffcoBorrower.setBackground(getResources().getDrawable(R.drawable.background_capsule_primarydark));
            }
            mainApplication.applyTypefaceBold(textView2, context);
            textView3 = (TextView) view.findViewById(R.id.textView3_l2);
            mainApplication.applyTypeface(textView3, context);

            buttonNext = (Button) view.findViewById(R.id.button_next_loanappfragment2);
            buttonNext.setTypeface(typefaceFontBold);

            buttonPrevious = (Button) view.findViewById(R.id.button_previous_loanappfragment2);
            buttonPrevious.setTypeface(typefaceFontBold);

            lable = (TextView) view.findViewById(R.id.lable_fqform);
            textViewbirthday = (TextView) view.findViewById(R.id.userInfoEdit_birthdatecoborrower);
            birthdaycalender = (TextView) view.findViewById(R.id.birthdayCalender_fqform);
            birthdaycalender.setTypeface(typeface);

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

            rgGenderBr = (RadioGroup) view.findViewById(R.id.rgGenderBr);
            radiogroup_iscgpa = (RadioGroup) view.findViewById(R.id.radiogroup_iscgpa);

            rbMaleBr = (RadioButton) view.findViewById(R.id.rbMaleBr);
            rbFemaleBr = (RadioButton) view.findViewById(R.id.rbFemaleBr);

            spInstituteBr = (Spinner) view.findViewById(R.id.spInstituteBr);
            spInsLocationBr = (Spinner) view.findViewById(R.id.spInsLocationBr);
            spCourseBr = (Spinner) view.findViewById(R.id.spCourseBr);
            spProfessionBr = (Spinner) view.findViewById(R.id.spProfessionBr);
            spCurrentCountryBr = (Spinner) view.findViewById(R.id.spCurrentCountryBr);
            spCurrentStateBr = (Spinner) view.findViewById(R.id.spCurrentStateBr);
            spCurrentCityBr = (Spinner) view.findViewById(R.id.spCurrentCityBr);

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

            rgGenderCoBr = (RadioGroup) view.findViewById(R.id.rgGenderCoBr);

            rbMaleCoBr = (RadioButton) view.findViewById(R.id.rbMaleCoBr);
            rbFemaleCoBr = (RadioButton) view.findViewById(R.id.rbFemaleCoBr);

            spCurrentCountryCoBr = (Spinner) view.findViewById(R.id.spCurrentCountryCoBr);
            spCurrentStateCoBr = (Spinner) view.findViewById(R.id.spCurrentStateCoBr);
            spCurrentCityCoBr = (Spinner) view.findViewById(R.id.spCurrentCityCoBr);


            /** PERSONAL DETAILS **/
            fname = (EditText) view.findViewById(R.id.input_coborrowerfirstname);
            lname = (EditText) view.findViewById(R.id.input_coborrowerlastname);
            adhaarno = (EditText) view.findViewById(R.id.input_adhaar_coborrower);
            panno = (EditText) view.findViewById(R.id.input_pan_coborrower);
            currentaddress = (EditText) view.findViewById(R.id.input_coborrowercurrentaddress);
            permanentaddress = (EditText) view.findViewById(R.id.input_coborrowerpermanentaddress);
            currentpincode = (EditText) view.findViewById(R.id.input_coborrowercurrentpincode);
            contactno = (EditText) view.findViewById(R.id.input_coborrowercontactno);
            emailid = (EditText) view.findViewById(R.id.input_coborroweremailid);
            monthlyrent = (EditText) view.findViewById(R.id.input_coborrowerrent);
            previousemi = (EditText) view.findViewById(R.id.input_previousemiamount_coborrower);
            permanentpincode = (EditText) view.findViewById(R.id.input_coborrowerpermanentpincode);
            radioButtonMarried = (RadioButton) view.findViewById(R.id.input_coborrowermarriedRadioButton);
            radioButtonSingle = (RadioButton) view.findViewById(R.id.input_coborrowersingleRadioButton);
            radioGroupMaritialStatus = (RadioGroup) view.findViewById(R.id.radioGroup_coborrower_maritialstatus);

            radioGroupEmployerType = (RadioGroup) view.findViewById(R.id.radiogroup_emptype_coborrower);
            radioGroupPreviousEmi = (RadioGroup) view.findViewById(R.id.radiogroup_hasanypreviousemi_coborrower);
            radioGroupGender = (RadioGroup) view.findViewById(R.id.radioGroup_coborrower_gender);
            radioButtonPreviousEmiYes = (RadioButton) view.findViewById(R.id.radiobutton_emiyes_coborrower);
            radioButtonPreviousEmiNo = (RadioButton) view.findViewById(R.id.radiobutton_emino_coborrower);

            radioButtonMale = (RadioButton) view.findViewById(R.id.radioButton_male_coborrower);
            radioButtonFemale = (RadioButton) view.findViewById(R.id.radioButton_female_coborrower);

            radioButtonGovernment = (RadioButton) view.findViewById(R.id.radiobutton_empType_gov_coborrower);
            radioButtonPrivate = (RadioButton) view.findViewById(R.id.radiobutton_empType_private_coborrower);

            spinnerCurrentResidenceType = (Spinner) view.findViewById(R.id.spinner_coborrowercurrentresidencytype);
            spinnerCurrentCity = (Spinner) view.findViewById(R.id.spinner_coborrowercurrentcity);
            spinnerCurrentCountry = (Spinner) view.findViewById(R.id.spinner_coborrowercurrentcountry);
            spinnerCurrentState = (Spinner) view.findViewById(R.id.spinner_coborrowercurrentstate);
            spinnerPermanentCity = (Spinner) view.findViewById(R.id.spinner_coborrowerpermanentcity);
            spinnerPermanentState = (Spinner) view.findViewById(R.id.spinner_coborrowerpermanentstate);
            spinnerPermanentCountry = (Spinner) view.findViewById(R.id.spinner_coborrowerpermanentcountry);
            spinnerCurrentResidenceDuration = (Spinner) view.findViewById(R.id.spinner_coborrowerdurationofstayatcurrentaddress);
            spinnerRelationshipwithBorrower = (Spinner) view.findViewById(R.id.spinner_coborrowerrelationshipwithborrower);

            /** FINANCIAL DETAILS**/
            spinnerJobDuration = (Spinner) view.findViewById(R.id.spinner_durationofjob_coborrower);
            spinnerProfession = (Spinner) view.findViewById(R.id.spinner_profession_coborrower);
            anuualincome = (EditText) view.findViewById(R.id.input_annualincome_coborrower);
            employeer = (EditText) view.findViewById(R.id.input_employeer_coborrower);

            input_previousamt = (TextInputLayout) view.findViewById(R.id.input_previousamt);

            checkBoxSameasAbove = view.findViewById(R.id.checkBox_sameasabove_fqpersonal_coborrower);

//        checkBoxSameasAbove.setEnabled(false);
//        checkBoxSameasAbove.setClickable(false);
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

    /**
     * RESPONSE OF API CALL
     **/
    public void coBorrowerLoanDetails(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "borrowerPersonal" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONArray jsonArray1 = jsonObject.getJSONArray("addressType");
                currentResidencetype_arrayList = new ArrayList<>();
                coborrowerCurrentResidenceTypePersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray1.length(); i++) {
                    CoborrowerCurrentResidenceTypePersonalPOJO currentResidenceTypePersonalPOJO = new CoborrowerCurrentResidenceTypePersonalPOJO();
                    JSONObject mJsonti = jsonArray1.getJSONObject(i);
                    currentResidenceTypePersonalPOJO.residencetypeName = mJsonti.getString("name");
                    currentResidencetype_arrayList.add(mJsonti.getString("name"));
                    currentResidenceTypePersonalPOJO.dresidencetypeID = mJsonti.getString("id");
                    coborrowerCurrentResidenceTypePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);
                    Log.e(MainApplication.TAG, "currentResidenceTypePersonalPOJO Spiner DATA:----------------- " + currentResidenceTypePersonalPOJO.residencetypeName);
                }
                arrayAdapter_currentResidencetype = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentResidencetype_arrayList);
                spinnerCurrentResidenceType.setAdapter(arrayAdapter_currentResidencetype);
                arrayAdapter_currentResidencetype.notifyDataSetChanged();

                JSONArray jsonArray5 = jsonObject.getJSONArray("currentResidence");
                currentresidenceduration_arrayList = new ArrayList<>();
                coborrowerCurrentResidenceDurationPersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray5.length(); i++) {
                    CoborrowerCurrentResidenceDurationPersonalPOJO borrowerCurrentResidenceDurationPersonalPOJO = new CoborrowerCurrentResidenceDurationPersonalPOJO();
                    JSONObject mJsonti = jsonArray5.getJSONObject(i);
                    borrowerCurrentResidenceDurationPersonalPOJO.durationName = mJsonti.getString("name");
                    currentresidenceduration_arrayList.add(mJsonti.getString("name"));
                    borrowerCurrentResidenceDurationPersonalPOJO.durationID = mJsonti.getString("id");
                    coborrowerCurrentResidenceDurationPersonalPOJOArrayList.add(borrowerCurrentResidenceDurationPersonalPOJO);
                    Log.e(MainApplication.TAG, "currentResidenceTypePersonalPOJO Spiner DATA:----------------- " + borrowerCurrentResidenceDurationPersonalPOJO.durationName);
                }
                arrayAdapter_currentResidenceDuration = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentresidenceduration_arrayList);
                spinnerCurrentResidenceDuration.setAdapter(arrayAdapter_currentResidenceDuration);
                arrayAdapter_currentResidenceDuration.notifyDataSetChanged();


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
                    Log.e(MainApplication.TAG, "borrowerCurrentCountryPersonalPOJO Spiner DATA:----------------- " + borrowerCurrentCountryPersonalPOJO.countryName);
                }
                arrayAdapter_currentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
                spinnerCurrentCountry.setAdapter(arrayAdapter_currentCountry);
                arrayAdapter_currentCountry.notifyDataSetChanged();
                arrayAdapter_permanentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
                spinnerPermanentCountry.setAdapter(arrayAdapter_permanentCountry);
                arrayAdapter_permanentCountry.notifyDataSetChanged();

                JSONArray jsonArray6 = jsonObject.getJSONArray("coborrower_relationship");
                relationshipwithborrower_arrayList = new ArrayList<>();
                relationshipwithBorrowerPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray6.length(); i++) {
                    RelationshipwithBorrowerPOJO relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
                    JSONObject mJsonti = jsonArray6.getJSONObject(i);
                    relationshipwithBorrowerPOJO.relationName = mJsonti.getString("name");
                    relationshipwithborrower_arrayList.add(mJsonti.getString("name"));
                    relationshipwithBorrowerPOJO.relationID = mJsonti.getString("id");
                    relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
                }
                arrayAdapter_relationshipwithborrower = new ArrayAdapter(context, R.layout.custom_layout_spinner, relationshipwithborrower_arrayList);
                spinnerRelationshipwithBorrower.setAdapter(arrayAdapter_relationshipwithborrower);
                arrayAdapter_relationshipwithborrower.notifyDataSetChanged();

//                JSONArray jsonArray2 = jsonObject.getJSONArray("currentCities");
//                currentcity_arrayList = new ArrayList<>();
//                borrowerCurrentCityPersonalPOJOArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray2.length(); i++) {
//                    BorrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new BorrowerCurrentCityPersonalPOJO();
//                    JSONObject mJsonti = jsonArray2.getJSONObject(i);
//                    borrowerCurrentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
//                    currentcity_arrayList.add(mJsonti.getString("city_name"));
//                    borrowerCurrentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
//                    borrowerCurrentCityPersonalPOJOArrayList.add(borrowerCurrentCityPersonalPOJO);
//                }
//                arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
//                spinnerCurrentCity.setAdapter(arrayAdapter_currentCity);
//                arrayAdapter_currentCity.notifyDataSetChanged();
//
//                JSONArray jsonArray3 = jsonObject.getJSONArray("currentStates");
//                currentstate_arrayList = new ArrayList<>();
//                borrowerCurrentStatePersonalPOJOArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray3.length(); i++) {
//                    BorrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new BorrowerCurrentStatePersonalPOJO();
//                    JSONObject mJsonti = jsonArray3.getJSONObject(i);
//                    borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
//                    currentstate_arrayList.add(mJsonti.getString("state_name"));
//                    borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
//                    borrowerCurrentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
//                }
//                arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
//                spinnerCurrentState.setAdapter(arrayAdapter_currentState);
//                arrayAdapter_currentState.notifyDataSetChanged();
//
//
//
//
//                JSONArray jsonArray9 = jsonObject.getJSONArray("permanentCities");
//                currentcity_arrayList = new ArrayList<>();
//                borrowerCurrentCityPersonalPOJOArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray9.length(); i++) {
//                    BorrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new BorrowerCurrentCityPersonalPOJO();
//                    JSONObject mJsonti = jsonArray9.getJSONObject(i);
//                    borrowerCurrentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
//                    currentcity_arrayList.add(mJsonti.getString("city_name"));
//                    borrowerCurrentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
//                    borrowerCurrentCityPersonalPOJOArrayList.add(borrowerCurrentCityPersonalPOJO);
//                }
//                arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
//                spinnerPermanentCity.setAdapter(arrayAdapter_permanentCity);
//                arrayAdapter_permanentCity.notifyDataSetChanged();
//
//                JSONArray jsonArray10 = jsonObject.getJSONArray("permanentStates");
//                currentstate_arrayList = new ArrayList<>();
//                borrowerCurrentStatePersonalPOJOArrayList = new ArrayList<>();
//                for (int i = 0; i < jsonArray10.length(); i++) {
//                    BorrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new BorrowerCurrentStatePersonalPOJO();
//                    JSONObject mJsonti = jsonArray10.getJSONObject(i);
//                    borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
//                    currentstate_arrayList.add(mJsonti.getString("state_name"));
//                    borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
//                    borrowerCurrentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
//                }
//                arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
//                spinnerPermanentState.setAdapter(arrayAdapter_permanentState);
//                arrayAdapter_permanentState.notifyDataSetChanged();

                JSONObject jsonObject1 = jsonObject.getJSONObject("coBorrowerDetails");
                String previous_emi = jsonObject1.getString("coborrower_previous_emi_amount");
                String currentadd = jsonObject1.getString("coborrower_current_address");
                String coborrower_gender = jsonObject1.getString("coborrower_gender_id");
                String currentPincode = jsonObject1.getString("coborrower_current_pincode");
                String permanentadd = jsonObject1.getString("coborrower_permanent_address");
                String permanentpin = jsonObject1.getString("coborrower_permanent_pincode");
                String firstname = jsonObject1.getString("coborrower_first_name");
                String lastname = jsonObject1.getString("coborrower_last_name");
                String dob = jsonObject1.getString("coborrower_dob");
                String panNo = jsonObject1.getString("coborrower_pan_no");
                String aadhaarno = jsonObject1.getString("coborrower_aadhar_no");
                String contactNo = jsonObject1.getString("coborrower_mobile");
                String email = jsonObject1.getString("coborrower_email");
                String maritialstatus = jsonObject1.getString("coborrower_is_married");
                String monthlyRent = jsonObject1.getString("coborrower_monthly_rent");
                String previousEmi = jsonObject1.getString("coborrower_has_any_extra_loan");


                currentResidencetypeID = jsonObject1.getString("coborrower_address_type");
                currentcityID = jsonObject1.getString("coborrower_current_city");
                currentstateID = jsonObject1.getString("coborrower_current_state");
                if (jsonObject1.getString("coborrower_current_country").equals("0")) {
                    currentcountryID = "1";
                } else if (jsonObject1.getString("coborrower_current_country").equals("")) {
                    currentcountryID = "1";
                } else {
                    currentcountryID = jsonObject1.getString("coborrower_current_country");
                }
//                currentcountryID = jsonObject1.getString("coborrower_current_country");
                if (jsonObject1.getString("coborrower_permanent_country").equals("0")) {
                    permanentCountryID = "1";
                } else if (jsonObject1.getString("coborrower_permanent_country").equals("")) {
                    permanentCountryID = "1";
                } else {
                    permanentCountryID = jsonObject1.getString("coborrower_permanent_country");
                }
//                permanentCountryID = jsonObject1.getString("coborrower_permanent_country");
                permanentcityID = jsonObject1.getString("coborrower_permanent_city");
                permanentstateID = jsonObject1.getString("coborrower_permanent_state");
                currentresidenceDurationID = jsonObject1.getString("coborrower_living_since");
                relationshipwithborrowerID = jsonObject1.getString("coborrower_relationship");

                if (coborrower_gender.equalsIgnoreCase("1")) {
                    radioButtonMale.setChecked(true);
                } else if (coborrower_gender.equalsIgnoreCase("2")) {
                    radioButtonFemale.setChecked(true);
                }

                int counts = coborrowerCurrentResidenceTypePersonalPOJOArrayList.size();

                for (int i = 0; i < counts; i++) {
                    if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase(currentResidencetypeID)) {

                        if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("1")) {
                            monthlyrent.setVisibility(View.GONE);
                            monthlyrent.setText("");
                            spinnerCurrentResidenceType.setSelection(i);
                        } else if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("3")) {
                            monthlyrent.setVisibility(View.GONE);
                            monthlyrent.setText("");
                            spinnerCurrentResidenceType.setSelection(i);
                        } else if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("8")) {
                            monthlyrent.setVisibility(View.GONE);
                            monthlyrent.setText("");
                            spinnerCurrentResidenceType.setSelection(i);
                        } else {
                            monthlyrent.setVisibility(View.VISIBLE);
                            spinnerCurrentResidenceType.setSelection(i);
                        }
                    }
                }
//                if (currentcityID.equals("")) {
//                    spinnerCurrentCity.setSelection(0);
//                } else {
//                    spinnerCurrentCity.setSelection(Integer.parseInt(currentcityID));
//                }
//                if (currentstateID.equals("")) {
//                    spinnerCurrentState.setSelection(0);
//                } else {
//                    spinnerCurrentState.setSelection(Integer.parseInt(currentstateID));
//                }
                if (currentcountryID.equals("")) {
                    spinnerCurrentCountry.setSelection(1);
                } else {
                    spinnerCurrentCountry.setSelection(Integer.parseInt(currentcountryID));
                }
//                if (permanentcityID.equals("")) {
//                    spinnerPermanentCity.setSelection(0);
//                } else {
//                    spinnerPermanentCity.setSelection(Integer.parseInt(permanentcityID));
//                }
//                if (permanentstateID.equals("")) {
//                    spinnerPermanentState.setSelection(0);
//                } else {
//                    spinnerPermanentState.setSelection(Integer.parseInt(permanentstateID));
//                }
                if (permanentCountryID.equals("")) {
                    spinnerPermanentCountry.setSelection(1);
                } else {
                    spinnerPermanentCountry.setSelection(Integer.parseInt(permanentCountryID));
                }

                if (relationshipwithborrowerID.equals("")) {
                    spinnerRelationshipwithBorrower.setSelection(0);
                } else {
                    spinnerRelationshipwithBorrower.setSelection(Integer.parseInt(relationshipwithborrowerID));
                }

                if (currentresidenceDurationID.equals("")) {
                    spinnerCurrentResidenceDuration.setSelection(0);
                } else {
                    spinnerCurrentResidenceDuration.setSelection(Integer.parseInt(currentresidenceDurationID));
                }

                if (previousEmi.equalsIgnoreCase("1")) {
                    radioButtonPreviousEmiYes.setChecked(true);
                } else if (previousEmi.equalsIgnoreCase("0")) {
                    radioButtonPreviousEmiNo.setChecked(true);
                }


                fname.setText(firstname);
                lname.setText(lastname);
                adhaarno.setText(aadhaarno);
                panno.setText(panNo);
                currentaddress.setText(currentadd);
                // currentpincode.setText(currentPincode);
                if (currentPincode.equals("0")) {
                    currentpincode.setText("");
                } else {
                    currentpincode.setText(currentPincode);

                }
                permanentaddress.setText(permanentadd);
                // permanentpincode.setText(permanentpin);
                if (permanentpin.equals("0")) {
                    permanentpincode.setText("");
                } else {
                    permanentpincode.setText(permanentpin);

                }
                emailid.setText(email);
                contactno.setText(contactNo);
                monthlyrent.setText(monthlyRent);

                if (!dob.equalsIgnoreCase("") || !dob.isEmpty()) {
                    textViewbirthday.setText(dob);
                    textViewbirthday.setTextColor(Color.BLACK);
                    lable.setVisibility(View.VISIBLE);
                } else {
                    textViewbirthday.setText(R.string.birthdate);
//                    textViewbirthday.setTextColor(808080);
                }

                if (maritialstatus.equalsIgnoreCase("1")) {
                    radioButtonMarried.setChecked(true);
                } else if (maritialstatus.equalsIgnoreCase("2")) {
                    radioButtonSingle.setChecked(true);
                }

                /** FINANCIAL DETAILS**/
                JSONArray jsonArray7 = jsonObject.getJSONArray("jobDuration");

                jobduration_arratList = new ArrayList<>();
                coborrowerJobDurationFinancePOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray7.length(); i++) {
                    CoborrowerJobDurationFinancePOJO coborrowerJobDurationFinancePOJO = new CoborrowerJobDurationFinancePOJO();
                    JSONObject mJsonti = jsonArray7.getJSONObject(i);
                    coborrowerJobDurationFinancePOJO.durationName = mJsonti.getString("name");
                    jobduration_arratList.add(mJsonti.getString("name"));
                    coborrowerJobDurationFinancePOJO.durationID = mJsonti.getString("id");
                    coborrowerJobDurationFinancePOJOArrayList.add(coborrowerJobDurationFinancePOJO);
                }
                jobduration_arrayAdapter = new ArrayAdapter(context, R.layout.custom_layout_spinner, jobduration_arratList);
                spinnerJobDuration.setAdapter(jobduration_arrayAdapter);
                jobduration_arrayAdapter.notifyDataSetChanged();

                JSONArray jsonArray8 = jsonObject.getJSONArray("coBorrowerProfession");
                professionfinance_arratList = new ArrayList<>();
                coborrowerProfessionFinancePOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray8.length(); i++) {
                    CoborrowerProfessionFinancePOJO coborrowerProfessionFinancePOJO = new CoborrowerProfessionFinancePOJO();
                    JSONObject mJsonti = jsonArray8.getJSONObject(i);
                    coborrowerProfessionFinancePOJO.professionName = mJsonti.getString("name");
                    professionfinance_arratList.add(mJsonti.getString("name"));
                    coborrowerProfessionFinancePOJO.professionID = mJsonti.getString("id");
                    coborrowerProfessionFinancePOJOArrayList.add(coborrowerProfessionFinancePOJO);
                    Log.e(MainApplication.TAG, "coborrowerProfessionFinancePOJO Spiner DATA:----------------- " + coborrowerProfessionFinancePOJO.professionName);
                }
                profession_arrayAdapter = new ArrayAdapter(context, R.layout.custom_layout_spinner, professionfinance_arratList);
                spinnerProfession.setAdapter(profession_arrayAdapter);
                profession_arrayAdapter.notifyDataSetChanged();

                JSONObject jsonObject6 = jsonObject.getJSONObject("coBorrowerFinancialDetails");
                String organization = jsonObject6.getString("coborrower_organization");
                String anualIncome = jsonObject6.getString("coborrower_income");
                professionID = jsonObject6.getString("coborrower_profession");
                jobDurationID = jsonObject6.getString("coborrower_working_organization_since");
                String employerType = jsonObject6.getString("coborrower_employer_type");

                anuualincome.setText(anualIncome);
                employeer.setText(organization);
                previousemi.setText(previous_emi);

                if (permanentCountryID.equals("")) {
                    spinnerPermanentCountry.setSelection(1);
                } else {
                    spinnerPermanentCountry.setSelection(Integer.parseInt(permanentCountryID));
                }
                if (permanentCountryID.equals("")) {
                    spinnerPermanentCountry.setSelection(1);
                } else {
                    spinnerPermanentCountry.setSelection(Integer.parseInt(permanentCountryID));
                }
                if (jobDurationID.equals("")) {
                    spinnerJobDuration.setSelection(0);
                } else {
                    spinnerJobDuration.setSelection(Integer.parseInt(jobDurationID));
                }
                if (professionID.equals("")) {
                    spinnerProfession.setSelection(0);
                } else {
                    int count = coborrowerProfessionFinancePOJOArrayList.size();
                    Log.e("TAG", "count: " + count);
                    for (int i = 0; i < count; i++) {
                        if (coborrowerProfessionFinancePOJOArrayList.get(i).professionID.equalsIgnoreCase(professionID)) {
                            spinnerProfession.setSelection(i);
                        }
                    }

                }

                if (employerType.equalsIgnoreCase("1")) {
                    radioButtonPrivate.setChecked(true);
                } else if (employerType.equalsIgnoreCase("0")) {
                    radioButtonGovernment.setChecked(true);
                }

                MainApplication.coborrowerValue1 = currentResidencetypeID;
                MainApplication.coborrowerValue2 = monthlyrent.getText().toString();
                MainApplication.coborrowerValue3 = currentaddress.getText().toString();
                MainApplication.coborrowerValue4 = currentcityID;
                MainApplication.coborrowerValue5 = currentstateID;
                MainApplication.coborrowerValue6 = currentcountryID;
                MainApplication.coborrowerValue7 = currentpincode.getText().toString();
                MainApplication.coborrowerValue8 = permanentaddress.getText().toString();
                MainApplication.coborrowerValue9 = permanentcityID;
                MainApplication.coborrowerValue10 = permanentstateID;
                MainApplication.coborrowerValue11 = permanentCountryID;
                MainApplication.coborrowerValue12 = permanentpincode.getText().toString();
                MainApplication.coborrowerValue13 = fname.getText().toString();
                MainApplication.coborrowerValue14 = lname.getText().toString();
                MainApplication.coborrowerValue15 = textViewbirthday.getText().toString();
                MainApplication.coborrowerValue16 = maritialstatus;
                MainApplication.coborrowerValue17 = panno.getText().toString();
                MainApplication.coborrowerValue18 = adhaarno.getText().toString();

                MainApplication.coborrowerValue19 = emailid.getText().toString();
                MainApplication.coborrowerValue20 = contactno.getText().toString();
                MainApplication.coborrowerValue21 = currentresidenceDurationID;
                MainApplication.coborrowerValue22 = relationshipwithborrowerID;
                MainApplication.coborrowerValue23 = professionID;
                MainApplication.coborrowerValue24 = anuualincome.getText().toString();
                MainApplication.coborrowerValue25 = employeer.getText().toString();
                MainApplication.coborrowerValue26 = jobDurationID;
                MainApplication.coborrowerValue28 = previousemi.getText().toString();
                MainApplication.coborrowerValue29 = coborrower_gender;
                MainApplication.coborrowerValue30 = employerType;

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

    public void sendCoborrowerPersonal(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendCoborrowerPersonal" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                progressBar.setVisibility(View.GONE);

                JSONObject jsonObject = jsonData.getJSONObject("result");
                int docupload = jsonObject.getInt("docUpload");

                if (docupload == 1) {

                    String LocalsSql = "UPDATE CoBorrowerLAF SET ISUploaded = '" + true + "' WHERE logged_id = '" + userID + "'";
                    ExecuteSql(LocalsSql);

                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("coBorrowerBackground_dark", "1");
                    Log.e(TAG, "onCreateView:++++++++ sharedPreferences" + coBorrowerBackground);
                    editor.apply();
                    editor.commit();

                    LoanApplicationFragment_3 loanApplicationFragment_3 = new LoanApplicationFragment_3();
                    transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_3).commit();
                } else if (docupload == 0) {
                    Toast.makeText(context, R.string.please_complete_borrower_and_co_borrower_forms, Toast.LENGTH_SHORT).show();
                }

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

    public void getCurrentStatesCo(JSONObject jsonData) {
        try {

            if (jsonData.toString().equals("{}")) {
                try {
                    currentstate_arrayList = new ArrayList<>();
                    currentstate_arrayList.add("Select Any");
                    arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                    spinnerCurrentState.setAdapter(arrayAdapter_currentState);
                    arrayAdapter_currentState.notifyDataSetChanged();
                    spinnerCurrentState.setSelection(0);
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
                Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
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
                    spinnerCurrentState.setAdapter(arrayAdapter_currentState);
                    arrayAdapter_currentState.notifyDataSetChanged();

                    int count = borrowerCurrentStatePersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
                            spinnerCurrentState.setSelection(i);
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

    public void getCurrentCitiesCo(JSONObject jsonData) {
        try {

            if (jsonData.toString().equals("{}")) {
                try {
                    currentcity_arrayList = new ArrayList<>();
                    currentcity_arrayList.add("Select Any");
                    arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
                    spinnerCurrentCity.setAdapter(arrayAdapter_currentCity);
                    arrayAdapter_currentCity.notifyDataSetChanged();
                    spinnerCurrentCity.setSelection(0);
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

                Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
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
                    spinnerCurrentCity.setAdapter(arrayAdapter_currentCity);
                    arrayAdapter_currentCity.notifyDataSetChanged();

                    int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                            spinnerCurrentCity.setSelection(i);
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

    public void getPermanentStatesCo(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    currentstate_arrayList = new ArrayList<>();
                    currentstate_arrayList.add("Select Any");
                    arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                    spinnerPermanentState.setAdapter(arrayAdapter_permanentState);
                    arrayAdapter_permanentState.notifyDataSetChanged();
                    spinnerPermanentState.setSelection(0);
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
                Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray3 = jsonObject.getJSONArray("states");
                    currentstate_arrayList = new ArrayList<>();
                    borrowerPermanentStatePersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        BorrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new BorrowerCurrentStatePersonalPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                        currentstate_arrayList.add(mJsonti.getString("state_name"));
                        borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                        borrowerPermanentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
                    }
                    arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                    spinnerPermanentState.setAdapter(arrayAdapter_permanentState);
                    arrayAdapter_permanentState.notifyDataSetChanged();


                    int count = borrowerPermanentStatePersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(permanentstateID)) {
                            spinnerPermanentState.setSelection(i);
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

    public void getPermanentCitiesCo(JSONObject jsonData) {
        try {

            if (jsonData.toString().equals("{}")) {
                try {
                    permanentcity_arrayList = new ArrayList<>();
                    permanentcity_arrayList.add("Select Any");
                    arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayList);
                    spinnerPermanentCity.setAdapter(arrayAdapter_permanentCity);
                    arrayAdapter_permanentCity.notifyDataSetChanged();
                    spinnerPermanentCity.setSelection(0);
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
                Log.e("SERVER CALL", "sendBorrowerDetails" + jsonData);
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray2 = jsonObject.getJSONArray("cities");
                    permanentcity_arrayList = new ArrayList<>();
                    borrowerPermanentCityPersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        BorrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new BorrowerCurrentCityPersonalPOJO();
                        JSONObject mJsonti = jsonArray2.getJSONObject(i);
                        borrowerCurrentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
                        permanentcity_arrayList.add(mJsonti.getString("city_name"));
                        borrowerCurrentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
                        borrowerPermanentCityPersonalPOJOArrayList.add(borrowerCurrentCityPersonalPOJO);
                    }
                    arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayList);
                    spinnerPermanentCity.setAdapter(arrayAdapter_permanentCity);
                    arrayAdapter_permanentCity.notifyDataSetChanged();

//                    Log.e(TAG, "999999999: " + permanentcityID);

                    int count = borrowerPermanentCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityID)) {
                            spinnerPermanentCity.setSelection(i);
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

}
