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

import com.airbnb.lottie.parser.IntegerParser;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceDurationPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceTypePersonalPOJO;
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
import com.eduvanzapplication.newUI.pojo.AddressSameAsCurrentPOJO;
import com.eduvanzapplication.newUI.pojo.ProfessionPOJO;
import com.eduvanzapplication.newUI.pojo.RelationShipPOJO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
    public static LinearLayout  linBorrowerForm, linCoCorrowerForm, linCurrentAddressBr, linCurrentAddressCoBr,linOfficeDetails,linOfficeDetailsCoBr;
    public static RelativeLayout  relborrower, relCoborrower;
    public static Button buttonNext, buttonPrevious;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3, txtBorrowerArrowKey, txtCoBorrowerArrowKey;
    public static TextView birthdaycalender, lable, textViewbirthday;
    public static TextInputLayout input_cgpaBr, input_degreeBr, input_previousamt;
    Typeface typeface;
    Calendar Brcal,CoBrcal;
    public static String userID = "", coBorrowerBackground = "";
    MainApplication mainApplication;
    static View view;
    static FragmentTransaction transaction;
    public static ProgressBar progressBar;
    int borrowerVisiblity = 0, coborrowerVisiblity = 1;

    //Borrower
    public static EditText edtCourseFeeBr, edtLoanAmtBr, edtFnameBr, edtMnameBr, edtLnameBr, edtEmailIdBr, edtPanBr, edtAadhaarBr,
            edtCompanyBr, edtAnnualSalBr, edtCurrentAddressBr, edtCurrentLandmarkBr, edtCurrentPincodeBr, edtPermanentPincodeBr,
            edtPermanentAddressBr, edtAnnualIncomeBr, edtPincodeOffBr, edtAddressOffBr, input_passingyearBr,edtMonthlyRentBr;

    public static RadioGroup rgGenderBr,rgiscgpaBr, rgemptypeBr, rgborrower_gapsBr,rgMaritalStatusBr;
    public static RadioButton rbMaleBr, rbFemaleBr;

    public static Spinner spProfessionBr, spCurrentCountryBr, spCurrentStateBr, spCurrentCityBr, 
            spCurrentAddressSameAsKycBr,spCurrentAddressSameAsKycOrBorrowerBr,spDurationStayAtCurrentAddressBr;

    //CoBorrower
    public static EditText edtFnameCoBr, edtMnameCoBr, edtLnameCoBr, edtEmailIdCoBr, edtPanCoBr, edtAadhaarCoBr,
            edtCompanyCoBr, edtAnnualSalCoBr, edtCurrentAddressCoBr, edtCurrentPincodeCoBr, edtMonthlyRentCoBr, edtPermPinCodeCoBr,
            edtPermanentAddressCoBr,edtPincodeOffCoBr;

    public static RadioGroup rgGenderCoBr, rgMaritalStatusCoBr, rgemptypeCoBr;
    public static RadioButton rbMaleCoBr, rbFemaleCoBr, rbMarriedCoBr, rbSingleCoBr ;

    public static Spinner spCurrentCountryCoBr, spCurrentStateCoBr, spCurrentCityCoBr,spPermanentCountryBr,
            spPermanentStateCoBr, spPermanentCityCoBr,spCountryOffCoBr, spStateOffCoBr , spCityOffCoBr,spResidentTypeBr,
            spCurrentAddressSameAsKycCoBr,spCurrentAddressSameAsKycOrBorrowerCoBr,spResidentTypeCoBr,spDurationStayAtCurrentAddressCoBr,
            spProfessionCoBr;

    public static TextView txtBirthdayCalenderBr, txtBirthdayCalenderCoBr, lblBirthdayBr, lblBirthdayCoBr, txtBirthdateBr, txtBirthdateCoBr;

    public static RadioButton radioButtonPreviousEmiYes, radioButtonPreviousEmiNo, radioButtonMale, radioButtonFemale;
    public static RadioButton rbMarriedBr, rbSingleBr, radioButtonGovernment,
            radioButtonPrivate, rbempType_govBr, rbempType_privateBr;

    public static String dateformate = "", mobileNo = "";
    public static String currentcityID = "", currentstateID = "", currentcountryID = "1",
            currentcityIDCoBr = "", currentstateIDCoBr = "", currentcountryIDCoBr = "1";

    public static ArrayList<BorrowerCurrentResidenceTypePersonalPOJO> borrowerCurrentResidencePersonalPOJOArrayList;
    
    public static ArrayAdapter arrayAdapter_currentResidencetype;
    public static ArrayList<String> currentResidencetype_arrayList;
    public static ArrayList<CoborrowerCurrentResidenceTypePersonalPOJO> coborrowerCurrentResidenceTypePersonalPOJOArrayList;
    public static String currentResidencetypeID = "";

    public static ArrayAdapter arrayAdapter_currentResidenceDuration;
    public static ArrayList<String> currentresidenceduration_arrayList;
    public static ArrayList<BorrowerCurrentResidenceDurationPersonalPOJO> currentResidenceDurationPersonalPOJOArrayList;

    public static ArrayAdapter arrayAdapter_currentAddressSameAsBr,arrayAdapter_currentAddressSameAsCoBr;
    public static ArrayList<String> currentAddressSameAs_arrayListBr,currentAddressSameAs_arrayListCoBr;
    public static ArrayList<AddressSameAsCurrentPOJO> addressSameAsCurrentPOJOArrayListBr,addressSameAsCurrentPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_permanentAddressSameAsBr,arrayAdapter_permanentAddressSameAsCoBr;
    public static ArrayList<String> permanentAddressSameAs_arrayListBr,permanentAddressSameAs_arrayListCoBr;
    public static ArrayList<AddressSameAsCurrentPOJO> addressSameAspermanentPOJOArrayListBr,addressSameAspermanentPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_profession;
    public static ArrayList<String> profession_arrayList;
    public static ArrayList<ProfessionPOJO> professionPOJOArrayList;
    public String professionID = "";

    public static ArrayAdapter arrayAdapter_currentCity, arrayAdapter_currentCityCoBr;
    public static ArrayList<String> currentcity_arrayList, currentcity_arrayListCoBr;
    public static ArrayList<BorrowerCurrentCityPersonalPOJO> borrowerCurrentCityPersonalPOJOArrayList, borrowerCurrentCityPersonalPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_currentState, arrayAdapter_currentStateCoBr;
    public static ArrayList<String> currentstate_arrayList, currentstate_arrayListCoBr;
    public static ArrayList<BorrowerCurrentStatePersonalPOJO> borrowerCurrentStatePersonalPOJOArrayList, borrowerCurrentStatePersonalPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_currentCountry, arrayAdapter_currentCountryCoBr;
    public static ArrayList<String> currentCountry_arrayList, currentCountry_arrayListCoBr;
    public static ArrayList<BorrowerCurrentCountryPersonalPOJO> borrowerCurrentCountryPersonalPOJOArrayList, borrowerCurrentCountryPersonalPOJOArrayListCoBr;

    public String currentAddressSameAsKycID = "";

    public String relationshipID = "";

    public static Spinner spinnerRelationshipwithBorrower;
    public static ArrayAdapter arrayAdapter_relationshipwithborrower;
    public static ArrayList<String> relationshipwithborrower_arrayList;
    public static ArrayList<RelationshipwithBorrowerPOJO> relationshipwithBorrowerPOJOArrayList;
    public String relationshipwithborrowerID = "";

    /**
     * FINANCIAL DETAILS
     **/

    public static ArrayAdapter profession_arrayAdapter;
    public static ArrayList<String> professionfinance_arratList;
    public static ArrayList<CoborrowerProfessionFinancePOJO> coborrowerProfessionFinancePOJOArrayList;

    public static ArrayAdapter jobduration_arrayAdapter;
    public static ArrayList<String> jobduration_arratList;
    public static ArrayList<CoborrowerJobDurationFinancePOJO> coborrowerJobDurationFinancePOJOArrayList;
    public String jobDurationID = "";

    public static LinearLayout linearLayoutLeftOffcoBorrower;

    public static String lead_id = "", application_id = "", requested_loan_amount = "", institute_name = "", location_name = "",
            course_name = "", course_cost = "", has_coborrower = "";

    public static String Brapplicant_id = "", Brfk_lead_id = "", Brfk_applicant_type_id = "", Brfirst_name = "", Brmiddle_name = "",
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

    public static String CoBrapplicant_id = "", CoBrfk_lead_id = "", CoBrfk_applicant_type_id = "", CoBrfirst_name = "",
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

    public static String lead_status_id = "", fk_lead_id = "", lead_status = "", lead_sub_status = "", current_stage = "", current_status = "", lead_drop_status = "",
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

                    LoanApplicationFragment_3 loanApplicationFragment_3 = new LoanApplicationFragment_3();
                    transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_3).commit();
                }

//                @Override
//                public void onClick(View v) {
//
//                    boolean isIDNull = !adhaarno.getText().toString().equals("") ||
//                            !panno.getText().toString().equals("");
//
//                    if (!fname.getText().toString().equals("") && !lname.getText().toString().equals("") &&
//                            !textViewbirthday.getText().toString().equals("") &&
//                            !currentaddress.getText().toString().equals("") &&
//                            !currentpincode.getText().toString().equals("") &&
//                            !permanentaddress.getText().toString().equals("") &&
//                            !permanentpincode.getText().toString().equals("") &&
//                            !contactno.getText().toString().equals("") &&
//                            !emailid.getText().toString().equals("") &&
//                            !edtAnnualSalCoBr.getText().toString().equals("") &&
//                            !employeer.getText().toString().equals("") && isIDNull) {
//
//                        if (radioGroupGender.getCheckedRadioButtonId() > 0) {
//                            radioButtonFemale.setError(null);
//
//                            if (rgMaritalStatusBr.getCheckedRadioButtonId() > 0) {
//                                rbSingleBr.setError(null);
//
//                                if (radioGroupPreviousEmi.getCheckedRadioButtonId() > 0) {
//                                    radioButtonPreviousEmiNo.setError(null);
//
//                                    if (radioGroupEmployerType.getCheckedRadioButtonId() > 0) {
//                                        radioButtonPrivate.setError(null);
//
//                                        if (!currentcityID.equalsIgnoreCase("") && !currentstateID.equalsIgnoreCase("") &&
//                                                !currentcountryID.equalsIgnoreCase("") && !permanentcityID.equalsIgnoreCase("") &&
//                                                !permanentstateID.equalsIgnoreCase("") && !permanentCountryID.equalsIgnoreCase("") &&
//                                                !currentResidencetypeID.equalsIgnoreCase("") && !currentresidenceDurationID.equalsIgnoreCase("") &&
//                                                !relationshipwithborrowerID.equalsIgnoreCase("") && !professionID.equalsIgnoreCase("") &&
//                                                !jobDurationID.equalsIgnoreCase("")) {
//
//                                            try {
//                                                String maritialstatus = "";
//                                                if (rbMarriedBr.isChecked()) {
//                                                    maritialstatus = "1";
//                                                } else if (rbSingleBr.isChecked()) {
//                                                    maritialstatus = "2";
//                                                }
//
//                                                String previousEmi = "";
//                                                if (radioButtonPreviousEmiYes.isChecked()) {
//                                                    previousEmi = "1";
//                                                } else if (radioButtonPreviousEmiNo.isChecked()) {
//                                                    previousEmi = "0";
//                                                }
//
//                                                String gender = "";
//                                                if (radioButtonMale.isChecked()) {
//                                                    gender = "1";
//                                                }
//                                                if (radioButtonFemale.isChecked()) {
//                                                    gender = "2";
//                                                }
//
//                                                String empType = "";
//                                                if (radioButtonGovernment.isChecked()) {
//                                                    empType = "0";
//                                                }
//                                                if (radioButtonPrivate.isChecked()) {
//                                                    empType = "1";
//                                                }
//
//                                                progressBar.setVisibility(View.VISIBLE);
//                                                String url = MainApplication.mainUrl + "algo/setCoBorrowerLoanDetails";
//                                                Map<String, String> params = new HashMap<String, String>();
//                                                params.put("logged_id", userID);
//                                                params.put("coborrower_address_type", currentResidencetypeID);
//                                                params.put("coborrower_monthly_rent", edtMonthlyRentBr.getText().toString());
//                                                params.put("coborrower_current_address", currentaddress.getText().toString());
//                                                params.put("coborrower_current_city", currentcityID);
//                                                params.put("coborrower_current_state", currentstateID);
//                                                params.put("coborrower_current_country", currentcountryID);
//                                                params.put("coborrower_current_pincode", currentpincode.getText().toString());
//                                                params.put("coborrower_permanent_address", permanentaddress.getText().toString());
//                                                params.put("coborrower_permanent_city", permanentcityID);
//                                                params.put("coborrower_permanent_state", permanentstateID);
//                                                params.put("coborrower_permanent_country", permanentCountryID);
//                                                params.put("coborrower_permanent_pincode", permanentpincode.getText().toString());
//                                                params.put("coborrower_first_name", fname.getText().toString());
//                                                params.put("coborrower_last_name", lname.getText().toString());
//                                                params.put("coborrower_dob", textViewbirthday.getText().toString());
//                                                params.put("coborrower_is_married", maritialstatus);
//                                                params.put("coborrower_pan_no", panno.getText().toString());
//                                                params.put("coborrower_aadhar_no", adhaarno.getText().toString());
//
//                                                params.put("coborrower_email", emailid.getText().toString());
//                                                params.put("coborrower_mobile", contactno.getText().toString());
//                                                params.put("coborrower_living_since", currentresidenceDurationID);
//                                                params.put("coborrower_relationship", relationshipwithborrowerID);
//
//                                                params.put("coborrower_profession", professionID);
//                                                params.put("coborrower_income", edtAnnualSalCoBr.getText().toString());
//                                                params.put("coborrower_organization", employeer.getText().toString());
//                                                params.put("coborrower_working_organization_since", jobDurationID);
//                                                params.put("coborrower_has_any_emi", previousEmi);
//                                                params.put("ip_address", Utils.getIPAddress(true));
//
//                                                params.put("coborrower_previous_emi_amount", previousemi.getText().toString());
//
//                                                params.put("coborrower_gender_id", gender);
//
//                                                params.put("coborrower_employer_type", empType);
//
//                                                MainApplication.coborrowerValue1 = currentResidencetypeID;
//                                                MainApplication.coborrowerValue2 = edtMonthlyRentBr.getText().toString();
//                                                MainApplication.coborrowerValue3 = currentaddress.getText().toString();
//                                                MainApplication.coborrowerValue4 = currentcityID;
//                                                MainApplication.coborrowerValue5 = currentstateID;
//                                                MainApplication.coborrowerValue6 = currentcountryID;
//                                                MainApplication.coborrowerValue7 = currentpincode.getText().toString();
//                                                MainApplication.coborrowerValue8 = permanentaddress.getText().toString();
//                                                MainApplication.coborrowerValue9 = permanentcityID;
//                                                MainApplication.coborrowerValue10 = permanentstateID;
//                                                MainApplication.coborrowerValue11 = permanentCountryID;
//                                                MainApplication.coborrowerValue12 = permanentpincode.getText().toString();
//                                                MainApplication.coborrowerValue13 = fname.getText().toString();
//                                                MainApplication.coborrowerValue14 = lname.getText().toString();
//                                                MainApplication.coborrowerValue15 = textViewbirthday.getText().toString();
//                                                MainApplication.coborrowerValue16 = maritialstatus;
//                                                MainApplication.coborrowerValue17 = panno.getText().toString();
//                                                MainApplication.coborrowerValue18 = adhaarno.getText().toString();
//
//                                                MainApplication.coborrowerValue19 = emailid.getText().toString();
//                                                MainApplication.coborrowerValue20 = contactno.getText().toString();
//                                                MainApplication.coborrowerValue21 = currentresidenceDurationID;
//                                                MainApplication.coborrowerValue22 = relationshipwithborrowerID;
//                                                MainApplication.coborrowerValue23 = professionID;
//                                                MainApplication.coborrowerValue24 = edtAnnualSalCoBr.getText().toString();
//                                                MainApplication.coborrowerValue25 = employeer.getText().toString();
//                                                MainApplication.coborrowerValue26 = jobDurationID;
//                                                MainApplication.coborrowerValue27 = previousEmi;
//                                                MainApplication.coborrowerValue28 = previousemi.getText().toString();
//                                                MainApplication.coborrowerValue29 = gender;
//                                                MainApplication.coborrowerValue30 = empType;
//                                                if (!Globle.isNetworkAvailable(context)) {
//                                                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                                                } else {
//
//                                                    VolleyCallNew volleyCall = new VolleyCallNew();
//                                                    volleyCall.sendRequest(context, url, null, mFragment, "sendcoboorrowerDetails", params, MainApplication.auth_token);
//                                                }
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                        } else {
//                                            if (spResidentTypeBr.getSelectedItemPosition() <= 0) {
//                                                setSpinnerError(spResidentTypeBr, getString(R.string.please_select_duration_of_stay_at_current_address));
//                                                spResidentTypeBr.requestFocus();
//                                            }
//                                            if (spProfessionCoBr.getSelectedItemPosition() <= 0) {
//                                                setSpinnerError(spProfessionCoBr, getString(R.string.please_select_profession));
//                                                spProfessionCoBr.requestFocus();
//                                            }
//                                            if (spDurationStayAtCurrentAddressCoBr.getSelectedItemPosition() <= 0) {
//                                                setSpinnerError(spDurationStayAtCurrentAddressCoBr, getString(R.string.please_select_duration_of_job_business));
//                                                spDurationStayAtCurrentAddressCoBr.requestFocus();
//                                            }
//                                            Toast.makeText(context, R.string.please_fill_up_all_the_details_to_continue, Toast.LENGTH_LONG).show();
//                                        }
//                                    } else {
//                                        radioButtonPrivate.setError(getString(R.string.you_need_to_select_employer_type));
//                                        radioButtonPrivate.requestFocus();
//                                    }
//
//                                } else {
//                                    radioButtonPreviousEmiNo.setError(getString(R.string.you_need_to_select_previous_emi_status));
//                                    radioButtonPreviousEmiNo.requestFocus();
//                                }
//
//                            } else {
//                                rbSingleBr.setError(getString(R.string.you_need_to_select_maritial_status));
//                                rbSingleBr.requestFocus();
//                            }
//                        } else {
//                            radioButtonFemale.setError(getString(R.string.you_need_to_select_gender));
//                            radioButtonFemale.requestFocus();
//                        }
//
//                    } else {
//
//                        if (fname.getText().toString().equalsIgnoreCase("")) {
//                            fname.setError(getString(R.string.first_name_is_required));
//                            fname.requestFocus();
//                        } else {
//                            fname.setError(null);
//
//                        }
//
//                        if (lname.getText().toString().equalsIgnoreCase("")) {
//                            lname.setError(getString(R.string.last_name_is_required));
//                            lname.requestFocus();
//                        } else {
//                            lname.setError(null);
//
//                        }
//
//                        if (textViewbirthday.getText().toString().equalsIgnoreCase("")) {
//                            textViewbirthday.setError(getString(R.string.birthdate_is_required));
//                            textViewbirthday.requestFocus();
//                        } else if (textViewbirthday.getText().toString().toLowerCase().equals("birthdate")) {
//                            textViewbirthday.setError(getString(R.string.birthdate_is_required));
//                            textViewbirthday.requestFocus();
//                        } else {
//                            textViewbirthday.setError(null);
//                        }
//
//                        if (adhaarno.getText().toString().equalsIgnoreCase("")) {
//                            adhaarno.setError(getString(R.string.adhaar_number_is_required));
//                            adhaarno.requestFocus();
//                        } else {
//                            adhaarno.setError(null);
//                        }
//                        if (panno.getText().toString().equalsIgnoreCase("")) {
//                            panno.setError(getString(R.string.pan_number_is_required));
//                            panno.requestFocus();
//                        } else {
//                            panno.setError(null);
//                        }
//
//                        //                    if (adhaarno.getText().toString().equalsIgnoreCase("") && panno.getText().toString().equalsIgnoreCase("")) {
//                        //                        adhaarno.setError("Aadhaar Number is Required");
//                        //                        adhaarno.requestFocus();
//                        //                    }
//                        //                    else {
//                        //                        adhaarno.setError(null);
//                        //
//                        //                    }
//                        //                    if (panno.getText().toString().equalsIgnoreCase("") && adhaarno.getText().toString().equalsIgnoreCase("")) {
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
//                        }
//
//                        if (permanentpincode.getText().toString().equalsIgnoreCase("")) {
//                            permanentpincode.setError(getString(R.string.permanent_pin_is_required));
//                            permanentpincode.requestFocus();
//                        } else {
//                            permanentpincode.setError(null);
//                        }
//
//                        if (contactno.getText().toString().equalsIgnoreCase("")) {
//                            contactno.setError(getString(R.string.contact_number_is_required));
//                            contactno.requestFocus();
//                        } else {
//                            contactno.setError(null);
//                        }
//
//                        if (emailid.getText().toString().equalsIgnoreCase("")) {
//                            emailid.setError(getString(R.string.emailid_is_required));
//                            emailid.requestFocus();
//                        } else {
//                            emailid.setError(null);
//
//                        }
//
//                        if (edtAnnualSalCoBr.getText().toString().equalsIgnoreCase("")) {
//                            edtAnnualSalCoBr.setError(getString(R.string.annual_income_is_required));
//                            edtAnnualSalCoBr.requestFocus();
//                        } else {
//                            edtAnnualSalCoBr.setError(null);
//
//                        }
//                        if (employeer.getText().toString().equalsIgnoreCase("")) {
//                            employeer.setError(getString(R.string.employer_name_is_required));
//                        }
//                        if (!employeer.getText().toString().equalsIgnoreCase("")) {
//                            employeer.setError(null);
//                        }
//                        if (spinnerRelationshipwithBorrower.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spinnerRelationshipwithBorrower, getString(R.string.please_select_relationship_with_borrower));
//                            spinnerRelationshipwithBorrower.requestFocus();
//                        }
//
//                        if (spResidentTypeBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spResidentTypeBr, getString(R.string.please_select_resideny_type));
//                            spResidentTypeBr.requestFocus();
//
//                        }
//                        if (spinnerCurrentCountry.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spinnerCurrentCountry, getString(R.string.please_select_current_country));
//
//                        }
//                        if (spinnerCurrentState.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spinnerCurrentState, getString(R.string.please_select_current_state));
//
//                        }
//                        if (spinnerCurrentCity.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spinnerCurrentCity, getString(R.string.please_select_current_city));
//
//                        }
//                        if (spinnerPermanentCountry.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spinnerPermanentCountry, getString(R.string.please_select_permanent_country));
//                            spinnerPermanentCountry.requestFocus();
//
//                        }
//                        if (spinnerPermanentState.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spinnerPermanentState, getString(R.string.please_select_permanent_state));
//
//                        }
//                        if (spinnerPermanentCity.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spinnerPermanentCity, getString(R.string.please_select_permanent_city));
//
//                        }
//                        if (spResidentTypeBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spResidentTypeBr, getString(R.string.please_select_duration_of_stay_at_current_address));
//                            spResidentTypeBr.requestFocus();
//
//                        }
//                        if (spProfessionCoBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spProfessionCoBr, getString(R.string.please_select_profession));
//                            spProfessionCoBr.requestFocus();
//
//                        }
//                        if (spDurationStayAtCurrentAddressCoBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spDurationStayAtCurrentAddressCoBr, getString(R.string.please_select_duration_of_job_business));
//                            spDurationStayAtCurrentAddressCoBr.requestFocus();
//                        }
//                    }
//                }
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

            lblBirthdayBr = (TextView) view.findViewById(R.id.lblBirthdayBr);
            lblBirthdayCoBr = (TextView) view.findViewById(R.id.lblBirthdayCoBr);
            txtBirthdateBr = (TextView) view.findViewById(R.id.txtBirthdateBr);
            txtBirthdateCoBr = (TextView) view.findViewById(R.id.txtBirthdateCoBr);
            txtBirthdayCalenderBr = (TextView) view.findViewById(R.id.txtBirthdayCalenderBr);
            txtBirthdayCalenderBr.setTypeface(typeface);

            txtBirthdayCalenderCoBr = (TextView) view.findViewById(R.id.txtBirthdayCalenderCoBr);
            txtBirthdayCalenderCoBr.setTypeface(typeface);

            final DatePickerDialog.OnDateSetListener dateBr = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    Brcal.set(Calendar.YEAR, year);
                    Brcal.set(Calendar.MONTH, monthOfYear);
                    Brcal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    int month = monthOfYear + 1;
                    String datenew = dayOfMonth + "/" + month + "/" + year;
                    dateformate = dateFormateSystem(datenew);
                    txtBirthdateBr.setText(dateformate);
                    MainApplication.Brdobkyc = dateformate;
                    txtBirthdateBr.setTextColor(getResources().getColor(R.color.black));
                    lblBirthdayBr.setVisibility(View.VISIBLE);
                }

            };
            Brcal = Calendar.getInstance();

            txtBirthdayCalenderBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Brcal = Calendar.getInstance();
                    Brcal.set(Calendar.YEAR, Brcal.get(Calendar.YEAR));
                    DatePickerDialog data = new DatePickerDialog(context, dateBr, Brcal
                            .get(Calendar.YEAR) - 18, 1,
                            1);
                    data.getDatePicker().setMaxDate(System.currentTimeMillis() - 1234564);
                    data.show();
                }
            });

            final DatePickerDialog.OnDateSetListener dateCoBr = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    CoBrcal.set(Calendar.YEAR, year);
                    CoBrcal.set(Calendar.MONTH, monthOfYear);
                    CoBrcal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    int month = monthOfYear + 1;
                    String datenew = dayOfMonth + "/" + month + "/" + year;
                    dateformate = dateFormateSystem(datenew);
                    txtBirthdateCoBr.setText(dateformate);
                    MainApplication.CoBrdobkyc = dateformate;
                    txtBirthdateCoBr.setTextColor(getResources().getColor(R.color.black));
                    lblBirthdayBr.setVisibility(View.VISIBLE);
                }

            };
            CoBrcal = Calendar.getInstance();

            txtBirthdayCalenderCoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CoBrcal = Calendar.getInstance();
                    CoBrcal.set(Calendar.YEAR, CoBrcal.get(Calendar.YEAR));
                    DatePickerDialog data = new DatePickerDialog(context, dateCoBr, CoBrcal
                            .get(Calendar.YEAR) - 18, 1,
                            1);
                    data.getDatePicker().setMaxDate(System.currentTimeMillis() - 1234564);
                    data.show();
                }
            });

            rbMarriedBr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (rbMarriedBr.isChecked()) {
                        MainApplication.coborrowerValue16 = "1";
                    }
                }
            });

            rbSingleBr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (rbSingleBr.isChecked()) {
                        MainApplication.coborrowerValue16 = "2";
                    }
                }
            });

//            radioButtonPreviousEmiYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (radioButtonPreviousEmiYes.isChecked()) {
//                        MainApplication.coborrowerValue27 = "1";
//                    }
//                }
//            });
//
//            radioButtonPreviousEmiNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (radioButtonPreviousEmiNo.isChecked()) {
//                        MainApplication.coborrowerValue27 = "0";
//                    }
//                }
//            });
//
//            radioGroupPreviousEmi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    Log.e("TAG", "onCheckedChanged: ");
//                    Log.e("TAG", "onCheckedChanged: " + radioGroupPreviousEmi.getCheckedRadioButtonId());
//                    switch (radioGroupPreviousEmi.getCheckedRadioButtonId()) {
//                        case R.id.radiobutton_emiyes_coborrower:
//                            Log.e("TAG", "yes: ");
//                            input_previousamt.setVisibility(View.VISIBLE);
//                            previousemi.requestFocus();
//                            //previousemi.setVisibility(View.VISIBLE);
//                            break;
//                        case R.id.radiobutton_emino_coborrower:
//                            Log.e("TAG", "no: ");
//                            input_previousamt.setVisibility(View.GONE);
//                            //previousemi.setVisibility(View.GONE);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            });

            /** EDIT TEXT CHANGE LISTENER FOR PARTIAL SAVING **/

            edtFnameBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue13 = edtFnameBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtMnameBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue13 = edtMnameBr.getText().toString();
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
                    MainApplication.coborrowerValue14 = edtLnameBr.getText().toString();
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
                    MainApplication.coborrowerValue18 = edtAadhaarBr.getText().toString();
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
                    MainApplication.coborrowerValue17 = edtPanBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtMonthlyRentBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue2 = edtMonthlyRentBr.getText().toString();
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
                    MainApplication.coborrowerValue3 = edtCurrentAddressBr.getText().toString();
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
                    MainApplication.coborrowerValue7 = edtCurrentPincodeBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtCurrentAddressCoBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue8 = edtCurrentAddressCoBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtCurrentPincodeCoBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue12 = edtCurrentPincodeCoBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtAnnualSalCoBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.coborrowerValue24 = edtAnnualSalCoBr.getText().toString();
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
                    MainApplication.coborrowerValue25 = edtCompanyBr.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

//            contactno.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    MainApplication.coborrowerValue20 = contactno.getText().toString();
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });
//
//            emailid.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    MainApplication.coborrowerValue19 = emailid.getText().toString();
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });

            /** END OF EDIT TEXT CHANGE LISTENER FOR PARTIAL SAVING**/

            /** SPINNER CLICK **/

            spCurrentAddressSameAsKycBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String text = spCurrentAddressSameAsKycBr.getSelectedItem().toString();

                    int count = addressSameAsCurrentPOJOArrayListBr.size();
                    for (int i = 0; i < count; i++) {
                        if (addressSameAsCurrentPOJOArrayListBr.get(i).address.equalsIgnoreCase(text)) {
                            MainApplication.Bris_borrower_current_address_same_asdtl = professionID = addressSameAsCurrentPOJOArrayListBr.get(i).id;
                        }
                    }

                    switch (MainApplication.Bris_borrower_current_address_same_asdtl)
                    {
                        case "0"://<option value="" selected="selected">Select</option>
                            break;

                        case "1"://<option value="1">Borrower Kyc Address</option>

                            MainApplication.Brkyc_address_pindtl = Brkyc_address_pin;
                            MainApplication.Brkyc_addressdtl = Brkyc_address;

                            edtCurrentPincodeBr.setText(Brkyc_address_pin);
                            edtCurrentAddressBr.setText(Brkyc_address);

                            if (!Brkyc_address_state.equals("null")) {
                                currentcountryID = "1";
                                spCurrentCountryBr.setSelection(Integer.parseInt(currentcountryID));
                            }
                            if (!Brkyc_address_state.equals("null")) {
                                currentstateID = Brkyc_address_state;

                                int count1 = borrowerCurrentStatePersonalPOJOArrayList.size();

                                for (int i = 0; i < count1; i++) {
                                    if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
                                        spCurrentStateBr.setSelection(i);
                                    }
                                }
                            }
                            if (!Brkyc_address_city.equals("null")) {
                                currentcityID = Brkyc_address_city;

                                int count2 = borrowerCurrentCityPersonalPOJOArrayList.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                                        spCurrentCityBr.setSelection(i);
                                    }
                                }
                            }

                            break;

                        case "2"://<option value="2">None</option>

                            MainApplication.Brcurrent_address_pindtl = Brcurrent_address_pin;
                            MainApplication.Brcurrent_addressdtl = Brcurrent_address;

                            edtCurrentPincodeBr.setText(Brcurrent_address_pin);
                            edtCurrentAddressBr.setText(Brcurrent_address);

                            if (!Brkyc_address_state.equals("null")) {
                                currentcountryID = "1";
                                spCurrentCountryBr.setSelection(Integer.parseInt(currentcountryID));
                            }
                            if (!Brkyc_address_state.equals("null")) {
                                currentstateID = Brkyc_address_state;

                                int count1 = borrowerCurrentStatePersonalPOJOArrayList.size();

                                for (int i = 0; i < count1; i++) {
                                    if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
                                        spCurrentStateBr.setSelection(i);
                                    }
                                }
                            }
                            if (!Brkyc_address_city.equals("null")) {
                                currentcityID = Brkyc_address_city;

                                int count2 = borrowerCurrentCityPersonalPOJOArrayList.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                                        spCurrentCityBr.setSelection(i);
                                    }
                                }
                            }

                            break;

                            default:
                                break;
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spCurrentAddressSameAsKycOrBorrowerBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String text = spCurrentAddressSameAsKycOrBorrowerBr.getSelectedItem().toString();

                    int count = addressSameAspermanentPOJOArrayListBr.size();
                    for (int i = 0; i < count; i++) {
                        if (addressSameAspermanentPOJOArrayListBr.get(i).address.equalsIgnoreCase(text)) {
                            MainApplication.Bris_borrower_permanent_address_same_askyc = professionID = addressSameAspermanentPOJOArrayListBr.get(i).id;
                        }
                    }

                    switch (MainApplication.Bris_borrower_permanent_address_same_askyc)
                    {
                        case "0"://<option value="" selected="selected">Select</option>
                            break;

                        case "1"://<option value="1">Borrower Kyc Address</option>

                            MainApplication.Brkyc_address_pindtl = Brkyc_address_pin;
                            MainApplication.Brkyc_addressdtl = Brkyc_address;

                            edtPermanentPincodeBr.setText(Brkyc_address_pin);
                            edtPermanentAddressBr.setText(Brkyc_address);

                            if (!Brkyc_address_state.equals("null")) {
                                currentcountryID = "1";
                                spPermanentCountryBr.setSelection(Integer.parseInt(currentcountryID));
                            }
                            if (!Brkyc_address_state.equals("null")) {
                                currentstateID = Brkyc_address_state;

                                int count1 = borrowerCurrentStatePersonalPOJOArrayList.size();

                                for (int i = 0; i < count1; i++) {
                                    if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
                                        spPermanentStateCoBr.setSelection(i);
                                    }
                                }
                            }
                            if (!Brkyc_address_city.equals("null")) {
                                currentcityID = Brkyc_address_city;

                                int count2 = borrowerCurrentCityPersonalPOJOArrayList.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                                        spCurrentCityBr.setSelection(i);
                                    }
                                }
                            }

                            break;

                        case "2"://<option value="2">Borrower Current Address</option>

                            MainApplication.Brcurrent_address_pindtl = Brcurrent_address_pin;
                            MainApplication.Brcurrent_addressdtl = Brcurrent_address;

                            edtCurrentPincodeBr.setText(Brcurrent_address_pin);
                            edtCurrentAddressBr.setText(Brcurrent_address);

                            if (!Brcurrent_address_city.equals("null")) {
                                currentcountryID = "1";
                                spPermanentCountryBr.setSelection(Integer.parseInt(currentcountryID));
                            }
                            if (!Brcurrent_address_state.equals("null")) {
                                currentstateID = Brkyc_address_state;

                                int count1 = borrowerCurrentStatePersonalPOJOArrayList.size();

                                for (int i = 0; i < count1; i++) {
                                    if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
                                        spPermanentStateCoBr.setSelection(i);
                                    }
                                }
                            }
                            if (!Brcurrent_address_city.equals("null")) {
                                currentcityID = Brkyc_address_city;

                                int count2 = borrowerCurrentCityPersonalPOJOArrayList.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                                        spCurrentCityBr.setSelection(i);
                                    }
                                }
                            }

                            break;

                        case "3"://<option value="3">none</option>

                            MainApplication.Brpermanent_address_pindtl = Brpermanent_address_pin;
                            MainApplication.Brpermanent_addressdtl = Brpermanent_address;

                            edtCurrentPincodeBr.setText(Brpermanent_address_pin);
                            edtCurrentAddressBr.setText(Brpermanent_address);

                            if (!Brcurrent_address_city.equals("null")) {
                                currentcountryID = "1";
                                spPermanentCountryBr.setSelection(Integer.parseInt(currentcountryID));
                            }
                            if (!Brcurrent_address_state.equals("null")) {
                                currentstateID = Brkyc_address_state;

                                int count1 = borrowerCurrentStatePersonalPOJOArrayList.size();

                                for (int i = 0; i < count1; i++) {
                                    if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
                                        spPermanentStateCoBr.setSelection(i);
                                    }
                                }
                            }
                            if (!Brcurrent_address_city.equals("null")) {
                                currentcityID = Brkyc_address_city;

                                int count2 = borrowerCurrentCityPersonalPOJOArrayList.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                                        spCurrentCityBr.setSelection(i);
                                    }
                                }
                            }

                            break;




                        default:
                            break;
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spResidentTypeBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spResidentTypeBr.getSelectedItem().toString();
                        int count = coborrowerCurrentResidenceTypePersonalPOJOArrayList.size();
//                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).residencetypeName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue1 = currentResidencetypeID = coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID;
                                Log.e(TAG, "id:" + coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID);
                                if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("1")) {
                                    edtMonthlyRentBr.setVisibility(View.GONE);
                                    edtMonthlyRentBr.setText("");
                                } else if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("3")) {
                                    edtMonthlyRentBr.setVisibility(View.GONE);
                                    edtMonthlyRentBr.setText("");
                                } else if (coborrowerCurrentResidenceTypePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("8")) {
                                    edtMonthlyRentBr.setVisibility(View.GONE);
                                    edtMonthlyRentBr.setText("");
                                } else {
                                    edtMonthlyRentBr.setVisibility(View.VISIBLE);
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

            spProfessionBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String text = spProfessionBr.getSelectedItem().toString();
                    if (text.equalsIgnoreCase("Select Any")) {
                        MainApplication.mainapp_userprofession = "0";
                        MainApplication.profession = "0";
                        try {
                            linOfficeDetails.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (text.equalsIgnoreCase("Student")) {
                        MainApplication.mainapp_userprofession = "Student";
                        MainApplication.mainapp_userprofession = "1";
                        MainApplication.profession = "1";
                        try {
                            linOfficeDetails.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (text.equalsIgnoreCase("Employed")) {
                        MainApplication.mainapp_userprofession = "employed";
                        MainApplication.mainapp_userprofession = "2";
                        MainApplication.profession = "2";
                        try {
                            linOfficeDetails.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (text.equalsIgnoreCase("Self Employed")) {
                        MainApplication.mainapp_userprofession = "selfEmployed";
                        MainApplication.mainapp_userprofession = "3";
                        MainApplication.profession = "3";
                        try {
                            linOfficeDetails.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spResidentTypeBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spResidentTypeBr.getSelectedItem().toString();

                        int count = borrowerCurrentResidencePersonalPOJOArrayList.size();

                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).residencetypeName.equalsIgnoreCase(text)) {
                                MainApplication.borrowerValue1 = currentResidencetypeID = borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID;

                                if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("1")) {
                                    edtMonthlyRentBr.setVisibility(View.GONE);
                                    edtMonthlyRentBr.setText("");
                                } else if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("3")) {
                                    edtMonthlyRentBr.setVisibility(View.GONE);
                                    edtMonthlyRentBr.setText("");
                                } else if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("8")) {
                                    edtMonthlyRentBr.setVisibility(View.GONE);
                                    edtMonthlyRentBr.setText("");
                                } else {
                                    edtMonthlyRentBr.setVisibility(View.VISIBLE);
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

            spDurationStayAtCurrentAddressCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        Log.e("I_________D", "onItemClick: ");
                        String text = spDurationStayAtCurrentAddressCoBr.getSelectedItem().toString();
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

            rgiscgpaBr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (rgiscgpaBr.getCheckedRadioButtonId()) {
                        case R.id.radiobutton_iscgpa_yes:
                            input_cgpaBr.setVisibility(View.VISIBLE);
                            input_degreeBr.setVisibility(View.GONE);
                            break;
                        case R.id.radiobutton_iscgpa_no:
                            input_cgpaBr.setVisibility(View.GONE);
                            input_degreeBr.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }
                }
            });

            spProfessionCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        Log.e("I_________D", "onItemClick: ");
                        String text = spProfessionCoBr.getSelectedItem().toString();
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

            spCurrentCityBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {

                        String text = spCurrentCityBr.getSelectedItem().toString();
                        int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.Brkyc_address_citykyc = currentcityID = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
                                Log.e(TAG, "spCurrentCityBr: +++++++++++++++++++*********************" + currentcityID);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
                                MainApplication.Brkyc_address_statekyc = currentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
                            }
                        }
                    } catch (Exception e) {

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
                                MainApplication.Brkyc_address_countrykyc = currentcountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                            }
                        }
                        stateApiCall();
//                        if (currentcityID.equals("")) {
//                            spCurrentCityBr.setSelection(0);
//                        } else {
//                            spCurrentCityBr.setSelection(Integer.parseInt(currentcityID));
//                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spCurrentCityCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {

                        String text = spCurrentCityCoBr.getSelectedItem().toString();
                        int count = borrowerCurrentCityPersonalPOJOArrayListCoBr.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCityPersonalPOJOArrayListCoBr.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.CoBrkyc_address_citykyc = currentcityIDCoBr = borrowerCurrentCityPersonalPOJOArrayListCoBr.get(i).cityID;
//                                Log.e(TAG, "spCurrentCityCoBr: +++++++++++++++++++*********************" + currentcityIDCoBr);
                            }
                        }
                    } catch (Exception e) {
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spCurrentStateCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spCurrentStateCoBr.getSelectedItem().toString();
                        int count = borrowerCurrentStatePersonalPOJOArrayListCoBr.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentStatePersonalPOJOArrayListCoBr.get(i).stateName.equalsIgnoreCase(text)) {
                                MainApplication.CoBrkyc_address_statekyc = currentstateIDCoBr = borrowerCurrentStatePersonalPOJOArrayListCoBr.get(i).stateID;
                            }
                        }
                    } catch (Exception e) {

                    }
                    cityApiCallCoBr();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spCurrentCountryCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spCurrentCountryCoBr.getSelectedItem().toString();
                        int count = borrowerCurrentCountryPersonalPOJOArrayListCoBr.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCountryPersonalPOJOArrayListCoBr.get(i).countryName.equalsIgnoreCase(text)) {
                                MainApplication.CoBrkyc_address_countrykyc = currentcountryIDCoBr = borrowerCurrentCountryPersonalPOJOArrayListCoBr.get(i).countryID;
                            }
                        }
                        stateApiCallCoBr();
//                        if (currentcityIDCoBr.equals("")) {
//                            spCurrentCityCoBr.setSelection(0);
//                        } else {
//                            spCurrentCityCoBr.setSelection(Integer.parseInt(currentcityIDCoBr));
//                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            /** END SPINNER CLICK **/


            if (!Globle.isNetworkAvailable(context)) {

            } else {
                JSONObject jsonObject = new JSONObject();

                getCurrentStates(jsonObject);
                getCurrentCities(jsonObject);
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
                    currentstate_arrayListCoBr = new ArrayList<>();
                    borrowerCurrentStatePersonalPOJOArrayListCoBr = new ArrayList<>();
                    for (int i = 0; i < jsonArraycostates.length(); i++) {
                        BorrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJOCoBr = new BorrowerCurrentStatePersonalPOJO();
                        JSONObject mJsonti = jsonArraycostates.getJSONObject(i);
                        borrowerCurrentStatePersonalPOJOCoBr.stateName = mJsonti.getString("state_name");
                        currentstate_arrayListCoBr.add(mJsonti.getString("state_name"));
                        borrowerCurrentStatePersonalPOJOCoBr.stateID = mJsonti.getString("state_id");
                        borrowerCurrentStatePersonalPOJOArrayListCoBr.add(borrowerCurrentStatePersonalPOJOCoBr);
                    }
                    arrayAdapter_currentStateCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayListCoBr);
                    spCurrentStateCoBr.setAdapter(arrayAdapter_currentStateCoBr);
                    arrayAdapter_currentStateCoBr.notifyDataSetChanged();
                }
                if (jsonData.getJSONArray("cocities").length() > 0) {
                    JSONArray jsonArraycocities = jsonData.getJSONArray("cocities");
                    currentcity_arrayListCoBr = new ArrayList<>();
                    borrowerCurrentCityPersonalPOJOArrayListCoBr = new ArrayList<>();
                    for (int i = 0; i < jsonArraycocities.length(); i++) {
                        BorrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJOCoBr = new BorrowerCurrentCityPersonalPOJO();
                        JSONObject mJsonti = jsonArraycocities.getJSONObject(i);
                        borrowerCurrentCityPersonalPOJOCoBr.cityName = mJsonti.getString("city_name");
                        currentcity_arrayListCoBr.add(mJsonti.getString("city_name"));
                        borrowerCurrentCityPersonalPOJOCoBr.cityID = mJsonti.getString("city_id");
                        borrowerCurrentCityPersonalPOJOArrayListCoBr.add(borrowerCurrentCityPersonalPOJOCoBr);
                    }
                    arrayAdapter_currentCityCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayListCoBr);
                    spCurrentCityCoBr.setAdapter(arrayAdapter_currentCityCoBr);
                    arrayAdapter_currentCityCoBr.notifyDataSetChanged();

                    int count = borrowerCurrentCityPersonalPOJOArrayListCoBr.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentCityPersonalPOJOArrayListCoBr.get(i).cityID.equalsIgnoreCase(currentcityIDCoBr)) {
                            spCurrentCityCoBr.setSelection(i);
                        }
                    }
                }

                JSONObject jsondetailedInformation = jsonData.getJSONObject("detailedInformation");
                JSONObject jsonborrowerDetails = jsonData.getJSONObject("borrowerDetails");
                JSONObject jsoncoborrowerDetails = jsonData.getJSONObject("coborrowerDetails");
                JSONObject jsonleadStatus = jsonData.getJSONObject("leadStatus");
//                JSONObject jsoncourseAmount = jsonData.getJSONObject("courseAmount");
//                JSONObject jsonleadid = jsonData.getJSONObject("leadid");
//                JSONObject jsonapplicantID = jsonData.getJSONObject("applicantID");


                MainApplication.lead_iddtl = lead_id =  jsondetailedInformation.getString("lead_id");
                MainApplication.application_iddtl = application_id =  jsondetailedInformation.getString("application_id");
                MainApplication.requested_loan_amountdtl = requested_loan_amount =  jsondetailedInformation.getString("requested_loan_amount");
                MainApplication.institute_namedtl = institute_name =  jsondetailedInformation.getString("institute_name");
                MainApplication.location_namedtl = location_name =  jsondetailedInformation.getString("location_name");
                MainApplication.course_namedtl = course_name =  jsondetailedInformation.getString("course_name");
                MainApplication.course_costdtl = course_cost =  jsondetailedInformation.getString("course_cost");
                MainApplication.has_coborrowerdtl = has_coborrower =  jsondetailedInformation.getString("has_coborrower");
                MainApplication.Brapplicant_iddtl = Brapplicant_id =  jsonborrowerDetails.getString("applicant_id");
                MainApplication.Brfk_lead_iddtl = Brfk_lead_id =  jsonborrowerDetails.getString("fk_lead_id");
                MainApplication.Brfk_applicant_type_iddtl = Brfk_applicant_type_id =  jsonborrowerDetails.getString("fk_applicant_type_id");

                if (!jsonborrowerDetails.getString("first_name").toString().equals("null")) MainApplication.Brfirst_namedtl= Brfirst_name = jsonborrowerDetails.getString("first_name");
                if (!jsonborrowerDetails.getString("first_name").toString().equals("null")) MainApplication.Brfirst_namedtl= Brfirst_name = jsonborrowerDetails.getString("first_name");
                if (!jsonborrowerDetails.getString("middle_name").toString().equals("null")) MainApplication.Brmiddle_namedtl= Brmiddle_name = jsonborrowerDetails.getString("middle_name");
                if (!jsonborrowerDetails.getString("last_name").toString().equals("null")) MainApplication.Brlast_namedtl= Brlast_name = jsonborrowerDetails.getString("last_name");
                if (!jsonborrowerDetails.getString("has_aadhar_pan").toString().equals("null")) MainApplication.Brhas_aadhar_pandtl= Brhas_aadhar_pan = jsonborrowerDetails.getString("has_aadhar_pan");
                if (!jsonborrowerDetails.getString("dob").toString().equals("null")) MainApplication.Brdobdtl= Brdob = jsonborrowerDetails.getString("dob");
                if (!jsonborrowerDetails.getString("pan_number").toString().equals("null")) MainApplication.Brpan_numberdtl= Brpan_number = jsonborrowerDetails.getString("pan_number");
                if (!jsonborrowerDetails.getString("aadhar_number").toString().equals("null")) MainApplication.Braadhar_numberdtl= Braadhar_number = jsonborrowerDetails.getString("aadhar_number");
                if (!jsonborrowerDetails.getString("marital_status").toString().equals("null")) MainApplication.Brmarital_statusdtl= Brmarital_status = jsonborrowerDetails.getString("marital_status");
                if (!jsonborrowerDetails.getString("gender_id").toString().equals("null")) MainApplication.Brgender_iddtl= Brgender_id = jsonborrowerDetails.getString("gender_id");
                if (!jsonborrowerDetails.getString("mobile_number").toString().equals("null")) MainApplication.Brmobile_numberdtl= Brmobile_number = jsonborrowerDetails.getString("mobile_number");
                if (!jsonborrowerDetails.getString("email_id").toString().equals("null")) MainApplication.Bremail_iddtl= Bremail_id = jsonborrowerDetails.getString("email_id");
                if (!jsonborrowerDetails.getString("relationship_with_applicant").toString().equals("null")) MainApplication.Brrelationship_with_applicantdtl= Brrelationship_with_applicant = jsonborrowerDetails.getString("relationship_with_applicant");
                if (!jsonborrowerDetails.getString("profession").toString().equals("null")) MainApplication.Brprofessiondtl= Brprofession = jsonborrowerDetails.getString("profession");
                if (!jsonborrowerDetails.getString("employer_type").toString().equals("null")) MainApplication.Bremployer_typedtl= Bremployer_type = jsonborrowerDetails.getString("employer_type");
                if (!jsonborrowerDetails.getString("employer_name").toString().equals("null")) MainApplication.Bremployer_namedtl= Bremployer_name = jsonborrowerDetails.getString("employer_name");
                if (!jsonborrowerDetails.getString("annual_income").toString().equals("null")) MainApplication.Brannual_incomedtl= Brannual_income = jsonborrowerDetails.getString("annual_income");
                if (!jsonborrowerDetails.getString("current_employment_duration").toString().equals("null")) MainApplication.Brcurrent_employment_durationdtl= Brcurrent_employment_duration = jsonborrowerDetails.getString("current_employment_duration");
                if (!jsonborrowerDetails.getString("total_employement_duration").toString().equals("null")) MainApplication.Brtotal_employement_durationdtl= Brtotal_employement_duration = jsonborrowerDetails.getString("total_employement_duration");
                if (!jsonborrowerDetails.getString("employer_mobile_number").toString().equals("null")) MainApplication.Bremployer_mobile_numberdtl= Bremployer_mobile_number = jsonborrowerDetails.getString("employer_mobile_number");
                if (!jsonborrowerDetails.getString("employer_landline_number").toString().equals("null")) MainApplication.Bremployer_landline_numberdtl= Bremployer_landline_number = jsonborrowerDetails.getString("employer_landline_number");
                if (!jsonborrowerDetails.getString("office_landmark").toString().equals("null")) MainApplication.Broffice_landmarkdtl= Broffice_landmark = jsonborrowerDetails.getString("office_landmark");
                if (!jsonborrowerDetails.getString("office_address").toString().equals("null")) MainApplication.Broffice_addressdtl= Broffice_address = jsonborrowerDetails.getString("office_address");
                if (!jsonborrowerDetails.getString("office_address_city").toString().equals("null")) MainApplication.Broffice_address_citydtl= Broffice_address_city = jsonborrowerDetails.getString("office_address_city");
                if (!jsonborrowerDetails.getString("office_address_state").toString().equals("null")) MainApplication.Broffice_address_statedtl= Broffice_address_state = jsonborrowerDetails.getString("office_address_state");
                if (!jsonborrowerDetails.getString("office_address_country").toString().equals("null")) MainApplication.Broffice_address_countrydtl= Broffice_address_country = jsonborrowerDetails.getString("office_address_country");
                if (!jsonborrowerDetails.getString("office_address_pin").toString().equals("null")) MainApplication.Broffice_address_pindtl= Broffice_address_pin = jsonborrowerDetails.getString("office_address_pin");
                if (!jsonborrowerDetails.getString("has_active_loan").toString().equals("null")) MainApplication.Brhas_active_loandtl= Brhas_active_loan = jsonborrowerDetails.getString("has_active_loan");
                if (!jsonborrowerDetails.getString("EMI_Amount").toString().equals("null")) MainApplication.BrEMI_Amountdtl= BrEMI_Amount = jsonborrowerDetails.getString("EMI_Amount");
                if (!jsonborrowerDetails.getString("kyc_landmark").toString().equals("null")) MainApplication.Brkyc_landmarkdtl= Brkyc_landmark = jsonborrowerDetails.getString("kyc_landmark");
                if (!jsonborrowerDetails.getString("kyc_address").toString().equals("null")) MainApplication.Brkyc_addressdtl= Brkyc_address = jsonborrowerDetails.getString("kyc_address");
                if (!jsonborrowerDetails.getString("kyc_address_city").toString().equals("null")) MainApplication.Brkyc_address_citydtl= Brkyc_address_city = jsonborrowerDetails.getString("kyc_address_city");
                if (!jsonborrowerDetails.getString("kyc_address_state").toString().equals("null")) MainApplication.Brkyc_address_statedtl= Brkyc_address_state = jsonborrowerDetails.getString("kyc_address_state");
                if (!jsonborrowerDetails.getString("kyc_address_country").toString().equals("null")) MainApplication.Brkyc_address_countrydtl= Brkyc_address_country = jsonborrowerDetails.getString("kyc_address_country");
                if (!jsonborrowerDetails.getString("kyc_address_pin").toString().equals("null")) MainApplication.Brkyc_address_pindtl= Brkyc_address_pin = jsonborrowerDetails.getString("kyc_address_pin");
                if (!jsonborrowerDetails.getString("is_borrower_current_address_same_as").toString().equals("null")) MainApplication.Bris_borrower_current_address_same_asdtl= Bris_borrower_current_address_same_as = jsonborrowerDetails.getString("is_borrower_current_address_same_as");
                if (!jsonborrowerDetails.getString("is_coborrower_current_address_same_as").toString().equals("null")) MainApplication.Bris_coborrower_current_address_same_asdtl= Bris_coborrower_current_address_same_as = jsonborrowerDetails.getString("is_coborrower_current_address_same_as");
                if (!jsonborrowerDetails.getString("current_residence_type").toString().equals("null")) MainApplication.Brcurrent_residence_typedtl= Brcurrent_residence_type = jsonborrowerDetails.getString("current_residence_type");
                if (!jsonborrowerDetails.getString("current_landmark").toString().equals("null")) MainApplication.Brcurrent_landmarkdtl= Brcurrent_landmark = jsonborrowerDetails.getString("current_landmark");
                if (!jsonborrowerDetails.getString("current_address").toString().equals("null")) MainApplication.Brcurrent_addressdtl= Brcurrent_address = jsonborrowerDetails.getString("current_address");
                if (!jsonborrowerDetails.getString("current_address_city").toString().equals("null")) MainApplication.Brcurrent_address_citydtl= Brcurrent_address_city = jsonborrowerDetails.getString("current_address_city");
                if (!jsonborrowerDetails.getString("current_address_state").toString().equals("null")) MainApplication.Brcurrent_address_statedtl= Brcurrent_address_state = jsonborrowerDetails.getString("current_address_state");
                if (!jsonborrowerDetails.getString("current_address_country").toString().equals("null")) MainApplication.Brcurrent_address_countrydtl= Brcurrent_address_country = jsonborrowerDetails.getString("current_address_country");
                if (!jsonborrowerDetails.getString("current_address_pin").toString().equals("null")) MainApplication.Brcurrent_address_pindtl= Brcurrent_address_pin = jsonborrowerDetails.getString("current_address_pin");
                if (!jsonborrowerDetails.getString("current_address_rent").toString().equals("null")) MainApplication.Brcurrent_address_rentdtl= Brcurrent_address_rent = jsonborrowerDetails.getString("current_address_rent");
                if (!jsonborrowerDetails.getString("current_address_stay_duration").toString().equals("null")) MainApplication.Brcurrent_address_stay_durationdtl= Brcurrent_address_stay_duration = jsonborrowerDetails.getString("current_address_stay_duration");
                if (!jsonborrowerDetails.getString("is_borrower_permanent_address_same_as").toString().equals("null")) MainApplication.Bris_borrower_permanent_address_same_asdtl= Bris_borrower_permanent_address_same_as = jsonborrowerDetails.getString("is_borrower_permanent_address_same_as");
                if (!jsonborrowerDetails.getString("is_coborrower_permanent_address_same_as").toString().equals("null")) MainApplication.Bris_coborrower_permanent_address_same_asdtl= Bris_coborrower_permanent_address_same_as = jsonborrowerDetails.getString("is_coborrower_permanent_address_same_as");
                if (!jsonborrowerDetails.getString("permanent_residence_type").toString().equals("null")) MainApplication.Brpermanent_residence_typedtl= Brpermanent_residence_type = jsonborrowerDetails.getString("permanent_residence_type");
                if (!jsonborrowerDetails.getString("permanent_landmark").toString().equals("null")) MainApplication.Brpermanent_landmarkdtl= Brpermanent_landmark = jsonborrowerDetails.getString("permanent_landmark");
                if (!jsonborrowerDetails.getString("permanent_address").toString().equals("null")) MainApplication.Brpermanent_addressdtl= Brpermanent_address = jsonborrowerDetails.getString("permanent_address");
                if (!jsonborrowerDetails.getString("permanent_address_city").toString().equals("null")) MainApplication.Brpermanent_address_citydtl= Brpermanent_address_city = jsonborrowerDetails.getString("permanent_address_city");
                if (!jsonborrowerDetails.getString("permanent_address_state").toString().equals("null")) MainApplication.Brpermanent_address_statedtl= Brpermanent_address_state = jsonborrowerDetails.getString("permanent_address_state");
                if (!jsonborrowerDetails.getString("permanent_address_country").toString().equals("null")) MainApplication.Brpermanent_address_countrydtl= Brpermanent_address_country = jsonborrowerDetails.getString("permanent_address_country");
                if (!jsonborrowerDetails.getString("permanent_address_pin").toString().equals("null")) MainApplication.Brpermanent_address_pindtl= Brpermanent_address_pin = jsonborrowerDetails.getString("permanent_address_pin");
                if (!jsonborrowerDetails.getString("permanent_address_rent").toString().equals("null")) MainApplication.Brpermanent_address_rentdtl= Brpermanent_address_rent = jsonborrowerDetails.getString("permanent_address_rent");
                if (!jsonborrowerDetails.getString("permanent_address_stay_duration").toString().equals("null")) MainApplication.Brpermanent_address_stay_durationdtl= Brpermanent_address_stay_duration = jsonborrowerDetails.getString("permanent_address_stay_duration");
                if (!jsonborrowerDetails.getString("last_completed_degree").toString().equals("null")) MainApplication.Brlast_completed_degreedtl= Brlast_completed_degree = jsonborrowerDetails.getString("last_completed_degree");
                if (!jsonborrowerDetails.getString("score_unit").toString().equals("null")) MainApplication.Brscore_unitdtl= Brscore_unit = jsonborrowerDetails.getString("score_unit");
                if (!jsonborrowerDetails.getString("cgpa").toString().equals("null")) MainApplication.Brcgpadtl= Brcgpa = jsonborrowerDetails.getString("cgpa");
                if (!jsonborrowerDetails.getString("percentage").toString().equals("null")) MainApplication.Brpercentagedtl= Brpercentage = jsonborrowerDetails.getString("percentage");
                if (!jsonborrowerDetails.getString("passing_year").toString().equals("null")) MainApplication.Brpassing_yeardtl= Brpassing_year = jsonborrowerDetails.getString("passing_year");
                if (!jsonborrowerDetails.getString("gap_in_education").toString().equals("null")) MainApplication.Brgap_in_educationdtl= Brgap_in_education = jsonborrowerDetails.getString("gap_in_education");
                if (!jsonborrowerDetails.getString("full_name_pan_response").toString().equals("null")) MainApplication.Brfull_name_pan_responsedtl= Brfull_name_pan_response = jsonborrowerDetails.getString("full_name_pan_response");
                if (!jsonborrowerDetails.getString("created_by_id").toString().equals("null")) MainApplication.Brcreated_by_iddtl= Brcreated_by_id = jsonborrowerDetails.getString("created_by_id");
                if (!jsonborrowerDetails.getString("created_date_time").toString().equals("null")) MainApplication.Brcreated_date_timedtl= Brcreated_date_time = jsonborrowerDetails.getString("created_date_time");
                if (!jsonborrowerDetails.getString("created_ip_address").toString().equals("null")) MainApplication.Brcreated_ip_addressdtl= Brcreated_ip_address = jsonborrowerDetails.getString("created_ip_address");
                if (!jsonborrowerDetails.getString("modified_by").toString().equals("null")) MainApplication.Brmodified_bydtl= Brmodified_by = jsonborrowerDetails.getString("modified_by");
                if (!jsonborrowerDetails.getString("modified_date_time").toString().equals("null")) MainApplication.Brmodified_date_timedtl= Brmodified_date_time = jsonborrowerDetails.getString("modified_date_time");
                if (!jsonborrowerDetails.getString("modified_ip_address").toString().equals("null")) MainApplication.Brmodified_ip_addressdtl= Brmodified_ip_address = jsonborrowerDetails.getString("modified_ip_address");
                if (!jsonborrowerDetails.getString("is_deleted").toString().equals("null")) MainApplication.Bris_deleteddtl= Bris_deleted = jsonborrowerDetails.getString("is_deleted");


                edtFnameBr.setText(Brfirst_name);
                edtMnameBr.setText(Brmiddle_name);
                edtLnameBr.setText(Brlast_name);
                edtAadhaarBr.setText(Braadhar_number);
                edtPanBr.setText(Brpan_number);
                txtBirthdateBr.setText(Brdob);
                if(Brgender_id.equals("1")) rbMaleBr.setChecked(true);
                else rbFemaleBr.setChecked(true);
                if (Brmarital_status.equals("2")) rbSingleBr.setChecked(true);
                else rbMarriedBr.setChecked(true);

                if (!Brcurrent_residence_type.equals("")) {
                    spResidentTypeBr.setSelection((Integer.parseInt(Brcurrent_residence_type) + 1));
                }



                edtCompanyBr.setText(Bremployer_name);
                edtAnnualSalBr.setText(Brannual_income);
                edtPincodeOffBr.setText(Broffice_address_pin);
                edtAddressOffBr.setText(Broffice_address);
                input_passingyearBr.setText(Brpassing_year);

                if (!Brgap_in_education.equals("")){
                    if (Brgap_in_education.equals("1")) rgborrower_gapsBr.check(R.id.radioButton_gaps_yes_borrower);
                    else rgborrower_gapsBr.check(R.id.radioButton_gaps_no_borrower);
                }

                if(!Bremployer_type.equals("")){
                    if(Bremployer_type.equals("0")) rgemptypeBr.check(R.id.radiobutton_empType_private);
                    else rgemptypeBr.check(R.id.radiobutton_empType_gov);
                }
                if (!Brscore_unit.equals("")){
                    if (Brscore_unit.equals("1"))rgiscgpaBr.check(R.id.radiobutton_iscgpa_yes);
                    else rgiscgpaBr.check(R.id.radiobutton_iscgpa_no);
                }


                if(Bris_borrower_current_address_same_as.equals(""))
                {
                    MainApplication.Bris_borrower_current_address_same_asdtl = "0";
                    spCurrentAddressSameAsKycBr.setSelection(0);
                }
                else{
                    MainApplication.Bris_borrower_current_address_same_asdtl = Bris_borrower_current_address_same_as;
                    spCurrentAddressSameAsKycBr.setSelection(Integer.parseInt(MainApplication.Bris_borrower_current_address_same_asdtl));
                }

                if(Bris_borrower_permanent_address_same_as.equals(""))
                {
                    MainApplication.Bris_borrower_permanent_address_same_asdtl = "0";
                    spCurrentAddressSameAsKycOrBorrowerBr.setSelection(0);
                }
                else{
                    MainApplication.Bris_borrower_permanent_address_same_asdtl = Bris_borrower_permanent_address_same_as;
                    spCurrentAddressSameAsKycOrBorrowerBr.setSelection(Integer.parseInt(MainApplication.Bris_borrower_permanent_address_same_asdtl));
                }

                if (!jsoncoborrowerDetails.getString("applicant_id").toString().equals("null")) MainApplication.CoBrapplicant_iddtl = CoBrapplicant_id =  jsoncoborrowerDetails.getString("applicant_id");
                if (!jsoncoborrowerDetails.getString("fk_applicant_type_id").toString().equals("null")) MainApplication.CoBrfk_applicant_type_iddtl = CoBrfk_applicant_type_id =  jsoncoborrowerDetails.getString("fk_applicant_type_id");
                if (!jsoncoborrowerDetails.getString("middle_name").toString().equals("null")) MainApplication.CoBrmiddle_namedtl = CoBrmiddle_name =  jsoncoborrowerDetails.getString("middle_name");
                if (!jsoncoborrowerDetails.getString("has_aadhar_pan").toString().equals("null")) MainApplication.CoBrhas_aadhar_pandtl = CoBrhas_aadhar_pan =  jsoncoborrowerDetails.getString("has_aadhar_pan");
                if (!jsoncoborrowerDetails.getString("pan_number").toString().equals("null")) MainApplication.CoBrpan_numberdtl = CoBrpan_number =  jsoncoborrowerDetails.getString("pan_number");
                if (!jsoncoborrowerDetails.getString("marital_status").toString().equals("null")) MainApplication.CoBrmarital_statusdtl = CoBrmarital_status =  jsoncoborrowerDetails.getString("marital_status");
                if (!jsoncoborrowerDetails.getString("mobile_number").toString().equals("null")) MainApplication.CoBrmobile_numberdtl = CoBrmobile_number =  jsoncoborrowerDetails.getString("mobile_number");
                if (!jsoncoborrowerDetails.getString("relationship_with_applicant").toString().equals("null")) MainApplication.CoBrrelationship_with_applicantdtl = CoBrrelationship_with_applicant =  jsoncoborrowerDetails.getString("relationship_with_applicant");
                if (!jsoncoborrowerDetails.getString("employer_type").toString().equals("null")) MainApplication.CoBremployer_typedtl = CoBremployer_type =  jsoncoborrowerDetails.getString("employer_type");
                if (!jsoncoborrowerDetails.getString("annual_income").toString().equals("null")) MainApplication.CoBrannual_incomedtl = CoBrannual_income =  jsoncoborrowerDetails.getString("annual_income");
                if (!jsoncoborrowerDetails.getString("total_employement_duration").toString().equals("null")) MainApplication.CoBrtotal_employement_durationdtl = CoBrtotal_employement_duration =  jsoncoborrowerDetails.getString("total_employement_duration");
                if (!jsoncoborrowerDetails.getString("employer_landline_number").toString().equals("null")) MainApplication.CoBremployer_landline_numberdtl = CoBremployer_landline_number =  jsoncoborrowerDetails.getString("employer_landline_number");
                if (!jsoncoborrowerDetails.getString("office_address").toString().equals("null")) MainApplication.CoBroffice_addressdtl = CoBroffice_address =  jsoncoborrowerDetails.getString("office_address");
                if (!jsoncoborrowerDetails.getString("office_address_state").toString().equals("null")) MainApplication.CoBroffice_address_statedtl = CoBroffice_address_state =  jsoncoborrowerDetails.getString("office_address_state");
                if (!jsoncoborrowerDetails.getString("office_address_pin").toString().equals("null")) MainApplication.CoBroffice_address_pindtl = CoBroffice_address_pin =  jsoncoborrowerDetails.getString("office_address_pin");
                if (!jsoncoborrowerDetails.getString("EMI_Amount").toString().equals("null")) MainApplication.CoBrEMI_Amountdtl = CoBrEMI_Amount =  jsoncoborrowerDetails.getString("EMI_Amount");
                if (!jsoncoborrowerDetails.getString("kyc_address").toString().equals("null")) MainApplication.CoBrkyc_addressdtl = CoBrkyc_address =  jsoncoborrowerDetails.getString("kyc_address");
                if (!jsoncoborrowerDetails.getString("kyc_address_state").toString().equals("null")) MainApplication.CoBrkyc_address_statedtl = CoBrkyc_address_state =  jsoncoborrowerDetails.getString("kyc_address_state");
                if (!jsoncoborrowerDetails.getString("kyc_address_pin").toString().equals("null")) MainApplication.CoBrkyc_address_pindtl = CoBrkyc_address_pin =  jsoncoborrowerDetails.getString("kyc_address_pin");
                if (!jsoncoborrowerDetails.getString("is_coborrower_current_address_same_as").toString().equals("null")) MainApplication.CoBris_coborrower_current_address_same_asdtl = CoBris_coborrower_current_address_same_as =  jsoncoborrowerDetails.getString("is_coborrower_current_address_same_as");
                if (!jsoncoborrowerDetails.getString("current_landmark").toString().equals("null")) MainApplication.CoBrcurrent_landmarkdtl = CoBrcurrent_landmark =  jsoncoborrowerDetails.getString("current_landmark");
                if (!jsoncoborrowerDetails.getString("current_address_city").toString().equals("null")) MainApplication.CoBrcurrent_address_citydtl = CoBrcurrent_address_city =  jsoncoborrowerDetails.getString("current_address_city");
                if (!jsoncoborrowerDetails.getString("current_address_country").toString().equals("null")) MainApplication.CoBrcurrent_address_countrydtl = CoBrcurrent_address_country =  jsoncoborrowerDetails.getString("current_address_country");
                if (!jsoncoborrowerDetails.getString("current_address_rent").toString().equals("null")) MainApplication.CoBrcurrent_address_rentdtl = CoBrcurrent_address_rent =  jsoncoborrowerDetails.getString("current_address_rent");
                if (!jsoncoborrowerDetails.getString("is_borrower_permanent_address_same_as").toString().equals("null")) MainApplication.CoBris_borrower_permanent_address_same_asdtl = CoBris_borrower_permanent_address_same_as =  jsoncoborrowerDetails.getString("is_borrower_permanent_address_same_as");
                if (!jsoncoborrowerDetails.getString("permanent_residence_type").toString().equals("null")) MainApplication.CoBrpermanent_residence_typedtl = CoBrpermanent_residence_type =  jsoncoborrowerDetails.getString("permanent_residence_type");
                if (!jsoncoborrowerDetails.getString("permanent_address").toString().equals("null")) MainApplication.CoBrpermanent_addressdtl = CoBrpermanent_address =  jsoncoborrowerDetails.getString("permanent_address");
                if (!jsoncoborrowerDetails.getString("permanent_address_state").toString().equals("null")) MainApplication.CoBrpermanent_address_statedtl = CoBrpermanent_address_state =  jsoncoborrowerDetails.getString("permanent_address_state");
                if (!jsoncoborrowerDetails.getString("permanent_address_pin").toString().equals("null")) MainApplication.CoBrpermanent_address_pindtl = CoBrpermanent_address_pin =  jsoncoborrowerDetails.getString("permanent_address_pin");
                if (!jsoncoborrowerDetails.getString("permanent_address_stay_duration").toString().equals("null")) MainApplication.CoBrpermanent_address_stay_durationdtl = CoBrpermanent_address_stay_duration =  jsoncoborrowerDetails.getString("permanent_address_stay_duration");
                if (!jsoncoborrowerDetails.getString("score_unit").toString().equals("null")) MainApplication.CoBrscore_unitdtl = CoBrscore_unit =  jsoncoborrowerDetails.getString("score_unit");
                if (!jsoncoborrowerDetails.getString("percentage").toString().equals("null")) MainApplication.CoBrpercentagedtl = CoBrpercentage =  jsoncoborrowerDetails.getString("percentage");
                if (!jsoncoborrowerDetails.getString("gap_in_education").toString().equals("null")) MainApplication.CoBrgap_in_educationdtl = CoBrgap_in_education =  jsoncoborrowerDetails.getString("gap_in_education");
                if (!jsoncoborrowerDetails.getString("created_by_id").toString().equals("null")) MainApplication.CoBrcreated_by_iddtl = CoBrcreated_by_id =  jsoncoborrowerDetails.getString("created_by_id");
                if (!jsoncoborrowerDetails.getString("created_ip_address").toString().equals("null")) MainApplication.CoBrcreated_ip_addressdtl = CoBrcreated_ip_address =  jsoncoborrowerDetails.getString("created_ip_address");
                if (!jsoncoborrowerDetails.getString("modified_date_time").toString().equals("null")) MainApplication.CoBrmodified_date_timedtl = CoBrmodified_date_time =  jsoncoborrowerDetails.getString("modified_date_time");
                if (!jsoncoborrowerDetails.getString("is_deleted").toString().equals("null")) MainApplication.CoBris_deleteddtl = CoBris_deleted =  jsoncoborrowerDetails.getString("is_deleted");


                edtFnameCoBr.setText(CoBrfirst_name);
                edtMnameCoBr.setText(CoBrmiddle_name);
                edtLnameCoBr.setText(CoBrlast_name);
                txtBirthdateCoBr.setText(CoBrdob);
                if(!CoBrgender_id.equals(""))
                    if (CoBrgender_id.equals("1")) rgGenderCoBr.check(R.id.rbMaleCoBr);
                    else rgGenderCoBr.check(R.id.rbFemaleCoBr);
                if(!CoBrmarital_status.equals(""))
                    if (CoBrmarital_status.equals("1")) rgMaritalStatusCoBr.check(R.id.rbMarriedCoBr);
                    else rgMaritalStatusCoBr.check(R.id.rbSingleCoBr);
                edtPanCoBr.setText(CoBrpan_number);
                edtAadhaarCoBr.setText(CoBraadhar_number);
                edtCurrentPincodeCoBr.setText(CoBrcurrent_address_pin);
                edtCurrentAddressCoBr.setText(CoBrcurrent_address);
                edtPermPinCodeCoBr.setText(CoBrpermanent_address_pin);
                edtPermanentAddressCoBr.setText(CoBrpermanent_address);


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

            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
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

                    JSONArray jsonArray3 = jsonData.getJSONArray("profession");
                    profession_arrayList = new ArrayList<>();
                    professionPOJOArrayList = new ArrayList<>();
                    profession_arrayList.add("Select Profession");
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        ProfessionPOJO professionPOJO = new ProfessionPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        professionPOJO.Salaried = mJsonti.getString("profession");
                        profession_arrayList.add(mJsonti.getString("profession"));
                        professionPOJO.id = mJsonti.getString("id");
                        professionPOJOArrayList.add(professionPOJO);
                    }
                    arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
                    spProfessionBr.setAdapter(arrayAdapter_profession);
                    arrayAdapter_profession.notifyDataSetChanged();

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


    private void setViews() {

        try {
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar_applylona_coborrower);

            linBorrowerForm = (LinearLayout) view.findViewById(R.id.linBorrowerForm);
            linCoCorrowerForm = (LinearLayout) view.findViewById(R.id.linCoCorrowerForm);
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

            //Borrower
            linOfficeDetails = (LinearLayout) view.findViewById(R.id.linOfficeDetailsBr);
            linCurrentAddressBr = (LinearLayout) view.findViewById(R.id.linCurrentAddressBr);
            
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
            edtMonthlyRentBr = (EditText) view.findViewById(R.id.edtMonthlyRentBr);

            edtCurrentAddressBr = (EditText) view.findViewById(R.id.edtCurrentAddressBr);
            edtCurrentPincodeBr = (EditText) view.findViewById(R.id.edtCurrentPincodeBr);

            edtPermanentPincodeBr = (EditText) view.findViewById(R.id.edtPermanentPincodeBr);
            edtPermanentAddressBr  = (EditText) view.findViewById(R.id.edtPermanentAddressBr);
            
            edtPincodeOffBr = (EditText) view.findViewById(R.id.edtPincodeOffBr);
            edtAddressOffBr = (EditText) view.findViewById(R.id.edtAddressOffBr);
            input_passingyearBr = (EditText) view.findViewById(R.id.input_passingyearBr);

            rgGenderBr = (RadioGroup) view.findViewById(R.id.rgGenderBr);
            rgiscgpaBr = (RadioGroup) view.findViewById(R.id.rgiscgpaBr);
            rgemptypeBr = (RadioGroup) view.findViewById(R.id.rgemptypeBr);
            rgborrower_gapsBr = (RadioGroup)  view.findViewById(R.id.rgborrower_gapsBr);
            rgemptypeBr=  (RadioGroup) view.findViewById(R.id.rgemptypeBr);
            rbMaleBr = (RadioButton) view.findViewById(R.id.rbMaleBr);
            rbFemaleBr = (RadioButton) view.findViewById(R.id.rbFemaleBr);
            rbMarriedBr = (RadioButton) view.findViewById(R.id.rbMarriedBr);
            rbSingleBr = (RadioButton) view.findViewById(R.id.rbSingleBr);
            
            input_cgpaBr = (TextInputLayout) view.findViewById(R.id.input_cgpaBr);
            input_degreeBr = (TextInputLayout) view.findViewById(R.id.input_degreeBr);

            spResidentTypeBr = view.findViewById(R.id.spResidentTypeBr);
            spProfessionBr = (Spinner) view.findViewById(R.id.spProfessionBr);
            spCurrentCountryBr = (Spinner) view.findViewById(R.id.spCurrentCountryBr);
            spCurrentStateBr = (Spinner) view.findViewById(R.id.spCurrentStateBr);
            spCurrentCityBr = (Spinner) view.findViewById(R.id.spCurrentCityBr);
            spResidentTypeBr = (Spinner) view.findViewById(R.id.spResidentTypeBr);
            spDurationStayAtCurrentAddressBr = (Spinner) view.findViewById(R.id.spDurationStayAtCurrentAddressBr);
            spCurrentAddressSameAsKycBr = (Spinner) view.findViewById(R.id.spCurrentAddressSameAsKycBr);
            spCurrentAddressSameAsKycOrBorrowerBr = (Spinner) view.findViewById(R.id.spCurrentAddressSameAsKycOrBorrowerBr);

            
            //CoBorrower
            linOfficeDetailsCoBr = (LinearLayout) view.findViewById(R.id.linOfficeDetailsCoBr);
            linCurrentAddressCoBr = (LinearLayout) view.findViewById(R.id.linCurrentAddressCoBr);

            spinnerRelationshipwithBorrower = (Spinner) view.findViewById(R.id.spinner_coborrowerrelationshipwithborrower);
            edtFnameCoBr = (EditText) view.findViewById(R.id.edtFnameCoBr);
            edtMnameCoBr = (EditText) view.findViewById(R.id.edtMnameCoBr);
            edtLnameCoBr = (EditText) view.findViewById(R.id.edtLnameCoBr);
            edtEmailIdCoBr = (EditText) view.findViewById(R.id.edtEmailIdCoBr);
            edtPanCoBr = (EditText) view.findViewById(R.id.edtPanCoBr);
            edtAadhaarCoBr = (EditText) view.findViewById(R.id.edtAadhaarCoBr);
            edtCompanyCoBr = (EditText) view.findViewById(R.id.edtCompanyCoBr);
            edtAnnualSalCoBr = (EditText) view.findViewById(R.id.edtAnnualSalCoBr);
            edtCurrentAddressCoBr = (EditText) view.findViewById(R.id.edtCurrentAddressCoBr);
            edtCurrentPincodeCoBr = (EditText) view.findViewById(R.id.edtCurrentPincodeCoBr);
            edtPermPinCodeCoBr = (EditText) view.findViewById(R.id.edtPermanentPincodeCoBr);
            edtPermanentAddressCoBr =(EditText)  view.findViewById(R.id.edtPermanentAddressCoBr);
            edtPincodeOffCoBr = (EditText) view.findViewById(R.id.edtPincodeOffCoBr);
            edtMonthlyRentCoBr = (EditText) view.findViewById(R.id.input_coborrowerrent);

            rgGenderCoBr = (RadioGroup) view.findViewById(R.id.rgGenderCoBr);
            rgMaritalStatusCoBr = view.findViewById(R.id.rgMaritalStatusCoBr);
            rbMaleCoBr = (RadioButton) view.findViewById(R.id.rbMaleCoBr);
            rbFemaleCoBr = (RadioButton) view.findViewById(R.id.rbFemaleCoBr);
            rbMarriedCoBr = view.findViewById(R.id.rbMarriedCoBr);
            rbSingleCoBr = view.findViewById(R.id.rbSingleCoBr);
            rbempType_govBr = view.findViewById(R.id.rbempType_govBr);
            rbempType_privateBr = view.findViewById(R.id.rbempType_privateBr);

            spCurrentCountryCoBr = (Spinner) view.findViewById(R.id.spCurrentCountryCoBr);
            spCurrentStateCoBr = (Spinner) view.findViewById(R.id.spCurrentStateCoBr);
            spCurrentCityCoBr = (Spinner) view.findViewById(R.id.spCurrentCityCoBr);
            spPermanentCountryBr = (Spinner) view.findViewById(R.id.spPermanentCountryCoBr);
            spPermanentStateCoBr= (Spinner) view.findViewById(R.id.spPermanentStateCoBr);
            spResidentTypeCoBr = (Spinner) view.findViewById(R.id.spResidentTypeCoBr);
            spPermanentCityCoBr = (Spinner) view.findViewById(R.id.spPermanentCityCoBr);
            spCountryOffCoBr = (Spinner) view.findViewById(R.id.spCountryOffCoBr);
            spStateOffCoBr =(Spinner) view.findViewById(R.id.spStateOffCoBr);
            spCityOffCoBr = (Spinner) view.findViewById(R.id.spCityOffCoBr);
            spCurrentAddressSameAsKycCoBr = (Spinner) view.findViewById(R.id.spCurrentAddressSameAsKycCoBr);
            spCurrentAddressSameAsKycOrBorrowerCoBr = (Spinner) view.findViewById(R.id.spCurrentAddressSameAsKycOrBorrowerCoBr);
            /** PERSONAL DETAILS **/
            
            rgGenderCoBr = (RadioGroup) view.findViewById(R.id.rgGenderCoBr);
            rgMaritalStatusCoBr = view.findViewById(R.id.rgMaritalStatusCoBr);
            rbMaleCoBr = (RadioButton) view.findViewById(R.id.rbMaleCoBr);
            rbFemaleCoBr = (RadioButton) view.findViewById(R.id.rbFemaleCoBr);
            rbMarriedCoBr = view.findViewById(R.id.rbMarriedCoBr);
            rbSingleCoBr = view.findViewById(R.id.rbSingleCoBr);
            rbempType_govBr = view.findViewById(R.id.rbempType_govBr);
            rbempType_privateBr = view.findViewById(R.id.rbempType_privateBr);
            
            radioButtonPreviousEmiYes = (RadioButton) view.findViewById(R.id.radiobutton_emiyes_coborrower);
            radioButtonPreviousEmiNo = (RadioButton) view.findViewById(R.id.radiobutton_emino_coborrower);

            radioButtonGovernment = (RadioButton) view.findViewById(R.id.radiobutton_empType_gov_coborrower);
            radioButtonPrivate = (RadioButton) view.findViewById(R.id.radiobutton_empType_private_coborrower);
            
            /** FINANCIAL DETAILS**/
            spDurationStayAtCurrentAddressCoBr = (Spinner) view.findViewById(R.id.spDurationStayAtCurrentAddressCoBr);
            spProfessionCoBr = (Spinner) view.findViewById(R.id.spProfessionCoBr);
            edtAnnualSalCoBr = (EditText) view.findViewById(R.id.edtAnnualSalCoBr);

            //Current Address Br
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

            currentCountry_arrayListCoBr = new ArrayList<>();
            borrowerCurrentCountryPersonalPOJOArrayListCoBr = new ArrayList<>();

            BorrowerCurrentCountryPersonalPOJO borrowerCurrentCountryPersonalPOJOCoBr = new BorrowerCurrentCountryPersonalPOJO();
            borrowerCurrentCountryPersonalPOJOCoBr.countryName = "Select Any";
            currentCountry_arrayListCoBr.add("Select Any");
            borrowerCurrentCountryPersonalPOJOCoBr.countryID = "";
            borrowerCurrentCountryPersonalPOJOArrayListCoBr.add(borrowerCurrentCountryPersonalPOJOCoBr);

            BorrowerCurrentCountryPersonalPOJO borrowerCurrentCountryPersonalPOJO1CoBr = new BorrowerCurrentCountryPersonalPOJO();
            borrowerCurrentCountryPersonalPOJO1CoBr.countryName = "India";
            currentCountry_arrayListCoBr.add("India");
            borrowerCurrentCountryPersonalPOJO1CoBr.countryID = "1";
            borrowerCurrentCountryPersonalPOJOArrayListCoBr.add(borrowerCurrentCountryPersonalPOJO1CoBr);

            arrayAdapter_currentCountryCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayListCoBr);
            spCurrentCountryCoBr.setAdapter(arrayAdapter_currentCountryCoBr);
            arrayAdapter_currentCountryCoBr.notifyDataSetChanged();

            //CurrentResidence Type
            currentResidencetype_arrayList = new ArrayList<>();
            borrowerCurrentResidencePersonalPOJOArrayList = new ArrayList<>();

            BorrowerCurrentResidenceTypePersonalPOJO currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();

            currentResidenceTypePersonalPOJO.residencetypeName = "Select Any";
            currentResidencetype_arrayList.add("Select Any");
            currentResidenceTypePersonalPOJO.dresidencetypeID = "";
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO.residencetypeName = "Owned by Self/Spouse/Parents/Sibling";
            currentResidencetype_arrayList.add("Owned by Self/Spouse/Parents/Sibling");
            currentResidenceTypePersonalPOJO.dresidencetypeID = "0";
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO.residencetypeName = "Rented With Family";
            currentResidencetype_arrayList.add("Rented With Family");
            currentResidenceTypePersonalPOJO.dresidencetypeID = "1";
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO.residencetypeName = "Company Provided";
            currentResidencetype_arrayList.add("Company Provided");
            currentResidenceTypePersonalPOJO.dresidencetypeID = "2";
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO.residencetypeName = "Rented With Friends";
            currentResidencetype_arrayList.add("Rented With Friends");
            currentResidenceTypePersonalPOJO.dresidencetypeID = "3";
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO.residencetypeName = "Rented Staying Alone";
            currentResidencetype_arrayList.add("Rented Staying Alone");
            currentResidenceTypePersonalPOJO.dresidencetypeID = "4";
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO.residencetypeName = "Paying Guest";
            currentResidencetype_arrayList.add("Paying Guest");
            currentResidenceTypePersonalPOJO.dresidencetypeID = "5";
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO.residencetypeName = "Hostel";
            currentResidencetype_arrayList.add("Hostel");
            currentResidenceTypePersonalPOJO.dresidencetypeID = "6";
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO.residencetypeName = "Owned By Parents/Siblings";
            currentResidencetype_arrayList.add("Owned By Parents/Siblings");
            currentResidenceTypePersonalPOJO.dresidencetypeID = "7";
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            arrayAdapter_currentResidenceDuration = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentResidencetype_arrayList);
            spResidentTypeBr.setAdapter(arrayAdapter_currentResidenceDuration);
            arrayAdapter_currentResidenceDuration.notifyDataSetChanged();

            //CurrentResidence Duration
            currentresidenceduration_arrayList = new ArrayList<>();
            currentResidenceDurationPersonalPOJOArrayList = new ArrayList<>();

            BorrowerCurrentResidenceDurationPersonalPOJO borrowerCurrentResidenceDurationPersonalPOJO = new BorrowerCurrentResidenceDurationPersonalPOJO();
            borrowerCurrentResidenceDurationPersonalPOJO.durationName = "Select Any";
            currentresidenceduration_arrayList.add("Select Any");
            borrowerCurrentResidenceDurationPersonalPOJO.durationID = "";
            currentResidenceDurationPersonalPOJOArrayList.add(borrowerCurrentResidenceDurationPersonalPOJO);

            BorrowerCurrentResidenceDurationPersonalPOJO borrowerCurrentResidenceDurationPersonalPOJO1 = new BorrowerCurrentResidenceDurationPersonalPOJO();
            borrowerCurrentResidenceDurationPersonalPOJO1.durationName = "Less Than 6 Months";
            currentresidenceduration_arrayList.add("Less Than 6 Months");
            borrowerCurrentResidenceDurationPersonalPOJO1.durationID = "1";
            currentResidenceDurationPersonalPOJOArrayList.add(borrowerCurrentResidenceDurationPersonalPOJO1);

            BorrowerCurrentResidenceDurationPersonalPOJO borrowerCurrentResidenceDurationPersonalPOJO2 = new BorrowerCurrentResidenceDurationPersonalPOJO();
            borrowerCurrentResidenceDurationPersonalPOJO2.durationName = "6 Months to 1 Year";
            currentresidenceduration_arrayList.add("6 Months to 1 Year");
            borrowerCurrentResidenceDurationPersonalPOJO2.durationID = "2";
            currentResidenceDurationPersonalPOJOArrayList.add(borrowerCurrentResidenceDurationPersonalPOJO2);

            BorrowerCurrentResidenceDurationPersonalPOJO borrowerCurrentResidenceDurationPersonalPOJO3 = new BorrowerCurrentResidenceDurationPersonalPOJO();
            borrowerCurrentResidenceDurationPersonalPOJO3.durationName = "1 Year to 2 Years";
            currentresidenceduration_arrayList.add("1 Year to 2 Years");
            borrowerCurrentResidenceDurationPersonalPOJO3.durationID = "3";
            currentResidenceDurationPersonalPOJOArrayList.add(borrowerCurrentResidenceDurationPersonalPOJO3);

            BorrowerCurrentResidenceDurationPersonalPOJO borrowerCurrentResidenceDurationPersonalPOJO4 = new BorrowerCurrentResidenceDurationPersonalPOJO();
            borrowerCurrentResidenceDurationPersonalPOJO4.durationName = "More Than 2 Years";
            currentresidenceduration_arrayList.add("More Than 2 Years");
            borrowerCurrentResidenceDurationPersonalPOJO4.durationID = "4";
            currentResidenceDurationPersonalPOJOArrayList.add(borrowerCurrentResidenceDurationPersonalPOJO4);

            arrayAdapter_currentResidenceDuration = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentresidenceduration_arrayList);
            spDurationStayAtCurrentAddressBr.setAdapter(arrayAdapter_currentResidenceDuration);
            arrayAdapter_currentResidenceDuration.notifyDataSetChanged();


            //Current Address same as borrower Br
            currentAddressSameAs_arrayListBr = new ArrayList<>();
            addressSameAsCurrentPOJOArrayListBr = new ArrayList<>();

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO.address = "Select Any";
            currentAddressSameAs_arrayListBr.add("Select Any");
            addressSameAsCurrentPOJO.id = "";
            addressSameAsCurrentPOJOArrayListBr.add(addressSameAsCurrentPOJO);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO2 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO2.address = "Borrower Kyc Address";
            currentAddressSameAs_arrayListBr.add("Borrower Kyc Address");
            addressSameAsCurrentPOJO2.id = "1";
            addressSameAsCurrentPOJOArrayListBr.add(addressSameAsCurrentPOJO2);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO3 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO3.address = "None";
            currentAddressSameAs_arrayListBr.add("None");
            addressSameAsCurrentPOJO3.id = "2";
            addressSameAsCurrentPOJOArrayListBr.add(addressSameAsCurrentPOJO3);

            arrayAdapter_currentAddressSameAsBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentAddressSameAs_arrayListBr);
            spCurrentAddressSameAsKycBr.setAdapter(arrayAdapter_currentAddressSameAsBr);
            arrayAdapter_currentAddressSameAsBr.notifyDataSetChanged();

            //Permanent Address same as borrower Br
            permanentAddressSameAs_arrayListBr = new ArrayList<>();
            addressSameAspermanentPOJOArrayListBr = new ArrayList<>();

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO11 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO11.address = "Select Any";
            permanentAddressSameAs_arrayListBr.add("Select Any");
            addressSameAsCurrentPOJO11.id = "";
            addressSameAspermanentPOJOArrayListBr.add(addressSameAsCurrentPOJO11);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO12 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO12.address = "Borrower Kyc Address";
            permanentAddressSameAs_arrayListBr.add("Borrower Kyc Address");
            addressSameAsCurrentPOJO12.id = "1";
            addressSameAspermanentPOJOArrayListBr.add(addressSameAsCurrentPOJO12);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO13 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO13.address = "Borrower Current Address";
            permanentAddressSameAs_arrayListBr.add("Borrower Current Address");
            addressSameAsCurrentPOJO13.id = "2";
            addressSameAspermanentPOJOArrayListBr.add(addressSameAsCurrentPOJO13);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO14 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO14.address = "None";
            permanentAddressSameAs_arrayListBr.add("None");
            addressSameAsCurrentPOJO14.id = "3";
            addressSameAspermanentPOJOArrayListBr.add(addressSameAsCurrentPOJO14);

            arrayAdapter_permanentAddressSameAsBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, addressSameAspermanentPOJOArrayListBr);
            spCurrentAddressSameAsKycOrBorrowerBr.setAdapter(arrayAdapter_permanentAddressSameAsBr);
            arrayAdapter_permanentAddressSameAsBr.notifyDataSetChanged();


            //Current Address same as borrower CoBr
            currentAddressSameAs_arrayListCoBr = new ArrayList<>();
            addressSameAsCurrentPOJOArrayListCoBr = new ArrayList<>();

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO21 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO21.address = "Select Any";
            currentAddressSameAs_arrayListCoBr.add("Select Any");
            addressSameAsCurrentPOJO21.id = "";
            addressSameAsCurrentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO21);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO22 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO22.address = "Borrower Kyc Address";
            currentAddressSameAs_arrayListCoBr.add("Borrower Kyc Address");
            addressSameAsCurrentPOJO22.id = "1";
            addressSameAsCurrentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO22);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO23 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO23.address = "None";
            currentAddressSameAs_arrayListCoBr.add("None");
            addressSameAsCurrentPOJO23.id = "2";
            addressSameAsCurrentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO23);

            arrayAdapter_currentAddressSameAsCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentAddressSameAs_arrayListCoBr);
            spCurrentAddressSameAsKycCoBr.setAdapter(arrayAdapter_currentAddressSameAsCoBr);
            arrayAdapter_currentAddressSameAsCoBr.notifyDataSetChanged();

            //Permanent Address same as borrower CoBr
            permanentAddressSameAs_arrayListCoBr = new ArrayList<>();
            addressSameAspermanentPOJOArrayListCoBr = new ArrayList<>();

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO31 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO31.address = "Select Any";
            permanentAddressSameAs_arrayListCoBr.add("Select Any");
            addressSameAsCurrentPOJO31.id = "";
            addressSameAspermanentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO31);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO32 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO32.address = "Borrower Kyc Address";
            permanentAddressSameAs_arrayListCoBr.add("Borrower Kyc Address");
            addressSameAsCurrentPOJO32.id = "1";
            addressSameAspermanentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO32);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO33 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO33.address = "Borrower Current Address";
            permanentAddressSameAs_arrayListCoBr.add("Borrower Current Address");
            addressSameAsCurrentPOJO33.id = "2";
            addressSameAspermanentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO33);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO34 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO34.address = "None";
            permanentAddressSameAs_arrayListCoBr.add("None");
            addressSameAsCurrentPOJO34.id = "3";
            addressSameAspermanentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO34);

            arrayAdapter_permanentAddressSameAsCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, addressSameAspermanentPOJOArrayListCoBr);
            spCurrentAddressSameAsKycOrBorrowerCoBr.setAdapter(arrayAdapter_permanentAddressSameAsCoBr);
            arrayAdapter_permanentAddressSameAsCoBr.notifyDataSetChanged();

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

    private void cityApiCallCoBr() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getCities"; //http://159.89.204.41/eduvanzApi/algo/getCities
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", currentcountryIDCoBr);//1
            params.put("stateId", currentstateIDCoBr);//2
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentCities(currentstateID,currentcountryID);
            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getCurrentCitiesCoBr", params, MainApplication.auth_token);
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

    private void stateApiCallCoBr() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getStates";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", currentcountryIDCoBr);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentStates(currentcountryID);

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getCurrentStatesCoBr", params, MainApplication.auth_token);
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
//                try {
//                    currentstate_arrayList = new ArrayList<>();
//                    currentstate_arrayList.add("Select Any");
//                    arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
//                    spCurrentStateBr.setAdapter(arrayAdapter_currentState);
//                    arrayAdapter_currentState.notifyDataSetChanged();
//                    spCurrentStateBr.setSelection(0);
//                } catch (Exception e) {
//                    String className = this.getClass().getSimpleName();
//                    String name = new Object() {
//                    }.getClass().getEnclosingMethod().getName();
//                    String errorMsg = e.getMessage();
//                    String errorMsgDetails = e.getStackTrace().toString();
//                    String errorLine = String.valueOf(e.getStackTrace()[0]);
//                    Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
//                }

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

                    int count = borrowerCurrentStatePersonalPOJOArrayList.size();

                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
                            spCurrentStateBr.setSelection(i);
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

    public void getCurrentCities(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
//                try {
//                    currentcity_arrayList = new ArrayList<>();
//                    currentcity_arrayList.add("Select Any");
//                    arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
//                    spCurrentCityBr.setAdapter(arrayAdapter_currentCity);
//                    arrayAdapter_currentCity.notifyDataSetChanged();
//                    spCurrentCityBr.setSelection(0);
//                } catch (Exception e) {
//                    String className = this.getClass().getSimpleName();
//                    String name = new Object() {
//                    }.getClass().getEnclosingMethod().getName();
//                    String errorMsg = e.getMessage();
//                    String errorMsgDetails = e.getStackTrace().toString();
//                    String errorLine = String.valueOf(e.getStackTrace()[0]);
//                    Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
//                }

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

    public void getCurrentStatesCoBr(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    currentstate_arrayListCoBr = new ArrayList<>();
                    currentstate_arrayListCoBr.add("Select Any");
                    arrayAdapter_currentStateCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayListCoBr);
                    spCurrentStateCoBr.setAdapter(arrayAdapter_currentStateCoBr);
                    arrayAdapter_currentStateCoBr.notifyDataSetChanged();
                    spCurrentStateCoBr.setSelection(0);
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
                    currentstate_arrayListCoBr = new ArrayList<>();
                    borrowerCurrentStatePersonalPOJOArrayListCoBr = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        BorrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJOCoBr = new BorrowerCurrentStatePersonalPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borrowerCurrentStatePersonalPOJOCoBr.stateName = mJsonti.getString("state_name");
                        currentstate_arrayListCoBr.add(mJsonti.getString("state_name"));
                        borrowerCurrentStatePersonalPOJOCoBr.stateID = mJsonti.getString("state_id");
                        borrowerCurrentStatePersonalPOJOArrayListCoBr.add(borrowerCurrentStatePersonalPOJOCoBr);
                    }
                    arrayAdapter_currentStateCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayListCoBr);
                    spCurrentStateCoBr.setAdapter(arrayAdapter_currentStateCoBr);
                    arrayAdapter_currentStateCoBr.notifyDataSetChanged();

                    int count = borrowerCurrentStatePersonalPOJOArrayList.size();

                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentStatePersonalPOJOArrayListCoBr.get(i).stateID.equalsIgnoreCase(currentstateIDCoBr)) {
                            spCurrentStateCoBr.setSelection(i);
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

    public void getCurrentCitiesCoBr(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    currentcity_arrayListCoBr = new ArrayList<>();
                    currentcity_arrayListCoBr.add("Select Any");
                    arrayAdapter_currentCityCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayListCoBr);
                    spCurrentCityCoBr.setAdapter(arrayAdapter_currentCityCoBr);
                    arrayAdapter_currentCityCoBr.notifyDataSetChanged();
//                    spCurrentCityCoBr.setSelection(0);
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

                    JSONArray jsonArray2 = jsonObject.getJSONArray("cities");
                    currentcity_arrayListCoBr = new ArrayList<>();
                    borrowerCurrentCityPersonalPOJOArrayListCoBr = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        BorrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJOCoBr = new BorrowerCurrentCityPersonalPOJO();
                        JSONObject mJsonti = jsonArray2.getJSONObject(i);
                        borrowerCurrentCityPersonalPOJOCoBr.cityName = mJsonti.getString("city_name");
                        currentcity_arrayList.add(mJsonti.getString("city_name"));
                        borrowerCurrentCityPersonalPOJOCoBr.cityID = mJsonti.getString("city_id");
                        borrowerCurrentCityPersonalPOJOArrayListCoBr.add(borrowerCurrentCityPersonalPOJOCoBr);
                    }
                    arrayAdapter_currentCityCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayListCoBr);
                    spCurrentCityCoBr.setAdapter(arrayAdapter_currentCityCoBr);
                    arrayAdapter_currentCityCoBr.notifyDataSetChanged();

                    int count = borrowerCurrentCityPersonalPOJOArrayListCoBr.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentCityPersonalPOJOArrayListCoBr.get(i).cityID.equalsIgnoreCase(currentcityIDCoBr)) {
                            spCurrentCityCoBr.setSelection(i);
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
