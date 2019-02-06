package com.eduvanzapplication.newUI.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
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
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceDurationPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceTypePersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentStatePersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerJobDurationFinancePOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerOffCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerOffCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerOffStatePersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerPermanentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerPermanentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerPermanentStatePersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerProfessionFinancePOJO;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerCurrentResidenceDurationPersonalPOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerCurrentResidenceTypePersonalPOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerJobDurationFinancePOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerProfessionFinancePOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.RelationshipwithBorrowerPOJO;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.pojo.AddressSameAsCurrentPOJO;
import com.eduvanzapplication.newUI.pojo.ProfessionPOJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.eduvanzapplication.newUI.MainApplication.TAG;

/**
 * A simple {@link Fragment} subclass.
 */


public class LoanApplicationFragment_2 extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    FloatingActionButton btnEdit;
    boolean isEdit = false;
    public static LinearLayout linBorrowerForm, linCoCorrowerForm, linCurrentAddressBr, linCurrentAddressCoBr, linOfficeDetails, linOfficeDetailsCoBr;
    public static RelativeLayout relborrower, relCoborrower;
    public static Button buttonNext, buttonPrevious;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3, txtBorrowerArrowKey, txtCoBorrowerArrowKey;
    public static TextView birthdaycalender, lable, textViewbirthday;
    public static TextInputLayout input_cgpaBr, input_degreeBr, input_previousamt;
    Typeface typeface;
    Calendar Brcal, CoBrcal;
    public static String userID = "", coBorrowerBackground = "";
    MainApplication mainApplication;
    static View view;
    static FragmentTransaction transaction;
    public static ProgressBar progressBar;
    int borrowerVisiblity = 0, coborrowerVisiblity = 1;

    //Borrower
    public static EditText edtCourseFeeBr, edtLoanAmtBr, edtFnameBr, edtMnameBr, edtLnameBr, edtEmailIdBr, edtPanBr, edtAadhaarBr,
            edtCompanyBr, edtAnnualSalBr, edtCurrentAddressBr, edtCurrentLandmarkBr, edtCurrentPincodeBr, edtPermanentPincodeBr,
            edtPermanentAddressBr, edtPincodeOffBr, edtAddressOffBr, input_passingyearBr, edtMonthlyRentBr;

    public static RadioGroup rgGenderBr, rgiscgpaBr, rgemptypeBr, rgborrower_gapsBr, rgMaritalStatusBr;
    public static RadioButton rbMaleBr, rbFemaleBr;

    public static Spinner spProfessionBr, spCurrentCountryBr, spCurrentStateBr, spCurrentCityBr,
            spPermanentCountryBr, spPermanentStateBr, spPermanentCityBr,
            spCountryOffBr, spStateOffBr, spCityOffBr, splastdegreecompletedBr,
            spCurrentAddressSameAsKycBr, spCurrentAddressSameAsKycOrBorrowerBr, spDurationStayAtCurrentAddressBr, spdurationofjobBr;

    //CoBorrower
    public static EditText edtFnameCoBr, edtMnameCoBr, edtLnameCoBr, edtPanCoBr, edtAadhaarCoBr,
            edtCompanyCoBr, edtAnnualSalCoBr, edtCurrentAddressCoBr, edtCurrentPincodeCoBr, edtMonthlyRentCoBr,edtPermanentPincodeCoBr,
            edtPermanentAddressCoBr, edtPincodeOffCoBr, edtAddressOffCoBr, edtMobileCoBr, edtEmailCoBr;

    public static TextInputLayout edtMonthlyRentLayoutCoBr;

    public static RadioGroup rgGenderCoBr, rgMaritalStatusCoBr, rgemptypeCoBr;
    public static RadioButton rbMaleCoBr, rbFemaleCoBr, rbMarriedCoBr, rbSingleCoBr;

    public static Spinner spCurrentCountryCoBr, spCurrentStateCoBr, spCurrentCityCoBr, spPermanentCountryCoBr,
            spPermanentStateCoBr, spPermanentCityCoBr, spCountryOffCoBr, spStateOffCoBr, spCityOffCoBr, spResidentTypeBr,
            spCurrentAddressSameAsKycCoBr, spCurrentAddressSameAsKycOrBorrowerCoBr, spResidentTypeCoBr, spDurationStayAtCurrentAddressCoBr,
            spProfessionCoBr, spdurationofjobCoBr;

    public static TextView txtBirthdayCalenderBr, txtBirthdayCalenderCoBr, lblBirthdayBr, lblBirthdayCoBr, txtBirthdateBr, txtBirthdateCoBr;

    public static RadioButton radioButtonPreviousEmiYes, radioButtonPreviousEmiNo, radioButtonMale, radioButtonFemale;
    public static RadioButton rbMarriedBr, rbSingleBr, radioButtonGovernment,
            radioButtonPrivate, rbempType_govBr, rbempType_privateBr;

    public static String dateformate = "", mobileNo = "";
    public static String currentcityID = "", currentstateID = "", currentcountryID = "1",
            currentcityIDCoBr = "", currentstateIDCoBr = "", currentcountryIDCoBr = "1",
            offcityIDBr = "", offstateIDBr = "", offcountryIDBr = "1",
            permanentcityID = "", permanentstateID = "", permanentcountryID = "1",
            permanentcityIDCoBr = "", permanentstateIDCoBr = "", permanentcountryIDCoBr = "1",
            offcityIDCoBr = "", offstateIDCoBr = "", offcountryIDCoBr = "1";


    public static ArrayAdapter arrayAdapter_currentResidencetype, arrayAdapter_currentResidencetypeCoBr;
    public static ArrayList<String> currentResidencetype_arrayList, currentResidencetype_arrayListCoBr;
    public static ArrayList<BorrowerCurrentResidenceTypePersonalPOJO> borrowerCurrentResidencePersonalPOJOArrayList;
    public static ArrayList<CoborrowerCurrentResidenceTypePersonalPOJO> coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr;
    public static String currentResidencetypeID = "", currentResidencetypeIDCoBr = "";

    public ArrayAdapter arrayAdapter_currentResidenceDuration, arrayAdapter_currentResidenceDurationCoBr;
    public static ArrayList<String> currentresidenceduration_arrayList, currentresidenceduration_arrayListCoBr;
    public static ArrayList<BorrowerCurrentResidenceDurationPersonalPOJO> currentResidenceDurationPersonalPOJOArrayList;
    public static ArrayList<CoborrowerCurrentResidenceDurationPersonalPOJO> currentResidenceDurationPersonalPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_currentAddressSameAsBr, arrayAdapter_currentAddressSameAsCoBr;
    public static ArrayList<String> currentAddressSameAs_arrayListBr, currentAddressSameAs_arrayListCoBr;
    public static ArrayList<AddressSameAsCurrentPOJO> addressSameAsCurrentPOJOArrayListBr, addressSameAsCurrentPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_permanentAddressSameAsBr, arrayAdapter_permanentAddressSameAsCoBr;
    public static ArrayList<String> permanentAddressSameAs_arrayListBr, permanentAddressSameAs_arrayListCoBr;
    public static ArrayList<AddressSameAsCurrentPOJO> addressSameAspermanentPOJOArrayListBr, addressSameAspermanentPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_profession, arrayAdapter_professionCoBr;
    public static ArrayList<String> profession_arrayList, profession_arrayListCoBr;
    public static ArrayList<ProfessionPOJO> professionPOJOArrayList, professionPOJOArrayListCoBr;
    public String professionID = "", professionIDCoBr = "";

    public static ArrayAdapter arrayAdapter_currentCity, arrayAdapter_permanentCity, arrayAdapter_offCity,
            arrayAdapter_currentCityCoBr, arrayAdapter_permanentCityCoBr, arrayAdapter_offCityCoBr;
    public static ArrayList<String> currentcity_arrayList, permanentcity_arrayList, offcity_arrayList,
            currentcity_arrayListCoBr, permanentcity_arrayListCoBr, offcity_arrayListCoBr;
    public static ArrayList<BorrowerCurrentCityPersonalPOJO> borrowerCurrentCityPersonalPOJOArrayList, borrowerCurrentCityPersonalPOJOArrayListCoBr;
    public static ArrayList<BorrowerPermanentCityPersonalPOJO> borrowerPermanentCityPersonalPOJOArrayList, borrowerPermanentCityPersonalPOJOArrayListCoBr;
    public static ArrayList<BorrowerOffCityPersonalPOJO> borrowerOffCityPersonalPOJOArrayList, borrowerOffCityPersonalPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_currentState, arrayAdapter_permanentState, arrayAdapter_offState,
            arrayAdapter_currentStateCoBr, arrayAdapter_permanentStateCoBr, arrayAdapter_offStateCoBr;
    public static ArrayList<String> currentstate_arrayList, permanentstate_arrayList, offstate_arrayList,
            currentstate_arrayListCoBr, permanentstate_arrayListCoBr, offstate_arrayListCoBr;
    public static ArrayList<BorrowerCurrentStatePersonalPOJO> borrowerCurrentStatePersonalPOJOArrayList, borrowerCurrentStatePersonalPOJOArrayListCoBr;
    public static ArrayList<BorrowerPermanentStatePersonalPOJO> borrowerPermanentStatePersonalPOJOArrayList, borrowerPermanentStatePersonalPOJOArrayListCoBr;
    public static ArrayList<BorrowerOffStatePersonalPOJO> borrowerOffStatePersonalPOJOArrayList, borrowerOffStatePersonalPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_currentCountry, arrayAdapter_permanentCountry, arrayAdapter_offCountry,
            arrayAdapter_currentCountryCoBr, arrayAdapter_permanentCountryCoBr, arrayAdapter_offCountryCoBr;
    public static ArrayList<String> currentCountry_arrayList, permanentCountry_arrayList, offCountry_arrayList,
            currentCountry_arrayListCoBr, permanentCountry_arrayListCoBr, offCountry_arrayListCoBr;
    public static ArrayList<BorrowerCurrentCountryPersonalPOJO> borrowerCurrentCountryPersonalPOJOArrayList, borrowerCurrentCountryPersonalPOJOArrayListCoBr;
    public static ArrayList<BorrowerPermanentCountryPersonalPOJO> borrowerPermanentCountryPersonalPOJOArrayList, borrowerPermanentCountryPersonalPOJOArrayListCoBr;
    public static ArrayList<BorrowerOffCountryPersonalPOJO> borrowerOffCountryPersonalPOJOArrayList, borrowerOffCountryPersonalPOJOArrayListCoBr;
    public ArrayList<String> duarationOfWorkListCoBr = new ArrayList<>();
    public ArrayList<String> duarationOfWorkListBr = new ArrayList<>();
    public ArrayList<String> lastCompletedDegreeListBr = new ArrayList<>();
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

    public static ArrayAdapter profession_arrayAdapter, profession_arrayAdapterCoBr;
    public static ArrayList<String> professionfinance_arratList, professionfinance_arratListCoBr;
    public static ArrayList<BorrowerProfessionFinancePOJO> borrowerProfessionFinancePOJOArrayListCo;
    public static ArrayList<CoborrowerProfessionFinancePOJO> coborrowerProfessionFinancePOJOArrayListCoBr;

    public static ArrayAdapter jobduration_arrayAdapter, jobduration_arrayAdapterCoBr;
    public static ArrayList<String> jobduration_arratList, jobduration_arratListCoBr;
    public static ArrayList<BorrowerJobDurationFinancePOJO> borrowerJobDurationFinancePOJOArrayListCoBr;
    public static ArrayList<CoborrowerJobDurationFinancePOJO> coborrowerJobDurationFinancePOJOArrayListCoBr;
    public String jobDurationID = "", jobDurationIDCoBr = "";

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

//                    try {
//                        LoanApplicationFragment_3 loanApplicationFragment_3 = new LoanApplicationFragment_3();
//                        transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_3).commit();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    try {
                        if (isEdit) {
                            boolean isBrHasAdharOrPan = !edtPanBr.getText().toString().equals("") || !edtAadhaarBr.getText().toString().equals("");
                            boolean isRentedBr = (edtMonthlyRentBr.getVisibility() == View.VISIBLE) && (!edtMonthlyRentBr.getText().toString().equals(""));
                            boolean isProfessionVisibleBr = linOfficeDetails.getVisibility() == View.VISIBLE;
                            boolean isGenderSelectedBr = (rgGenderBr.getCheckedRadioButtonId() == R.id.rbMaleBr) || (rgGenderBr.getCheckedRadioButtonId() == R.id.rbFemaleBr);
                            boolean isMaritalSelectedBr = (rgMaritalStatusBr.getCheckedRadioButtonId() == R.id.rbMarriedBr) || (rgMaritalStatusBr.getCheckedRadioButtonId() == R.id.rbSingleBr);

                            boolean isSpCurrentResiValidBr = spResidentTypeBr.getSelectedItemPosition() != 0; //0 -> select any
                            boolean isSpCurrentStayDurationValidBr = spDurationStayAtCurrentAddressBr.getSelectedItemPosition() != 0; //0 -> select
                            boolean isSpCurrentCountryValidBr = spCurrentCountryBr.getSelectedItemPosition() != 0;
                            boolean isSpCurrentStateValidBr = spCurrentStateBr.getSelectedItemPosition() != 0;
                            boolean isSpCurrentCityValidBr = spCurrentCityBr.getSelectedItemPosition() != 0;

                            boolean isSpPermanentResiValidBr = spCurrentAddressSameAsKycOrBorrowerCoBr.getSelectedItemPosition() != 0;
                            boolean isSpPermanentCountryValidBr = spPermanentCountryBr.getSelectedItemPosition() != 0;
                            boolean isPermanentStateValidBr = spPermanentStateBr.getSelectedItemPosition() != 0;
                            boolean isPermanentCityValidBr = spPermanentCityBr.getSelectedItemPosition() != 0;

                            boolean isProfessionValidBr = spProfessionBr.getSelectedItemPosition() != 0;
                            boolean isEmployerTypeValidBr = (rgemptypeBr.getCheckedRadioButtonId() == R.id.rbempType_privateBr) || (rgemptypeBr.getCheckedRadioButtonId() == R.id.rbempType_govBr);
                            boolean isEmployementDurationValidBr = spdurationofjobBr.getSelectedItemPosition() != 0;

                            boolean isLastCompletedDegreeValidBr = splastdegreecompletedBr.getSelectedItemPosition() != 0;
                            boolean isScoreUnitValidBr = (rgiscgpaBr.getCheckedRadioButtonId() == R.id.rbiscgpa_yesBr) || (rgiscgpaBr.getCheckedRadioButtonId() == R.id.rbiscgpa_noBr);
                            boolean isGapInEducationValidBr = (rgborrower_gapsBr.getCheckedRadioButtonId() == R.id.rbgaps_yesBr) || (rgborrower_gapsBr.getCheckedRadioButtonId() == R.id.rbgaps_noBr);

                            //coborrower valideations
                            boolean isCoBrHasAdharOrPan = !edtPanCoBr.getText().toString().equals("") || !edtAadhaarCoBr.getText().toString().equals("");
                            boolean isRentedCoBr = (edtMonthlyRentCoBr.getVisibility() == View.VISIBLE) && (!edtMonthlyRentCoBr.getText().toString().equals(""));
                            boolean isProfessionVisibleCoBr = linOfficeDetailsCoBr.getVisibility() == View.VISIBLE;
                            boolean isGenderSelectedCoBr = (rgGenderCoBr.getCheckedRadioButtonId() == R.id.rbMaleCoBr) || (rgGenderCoBr.getCheckedRadioButtonId() == R.id.rbFemaleBr);
                            boolean isMaritalSelectedCoBr = (rgMaritalStatusCoBr.getCheckedRadioButtonId() == R.id.rbMarriedCoBr) || (rgMaritalStatusCoBr.getCheckedRadioButtonId() == R.id.rbSingleCoBr);

                            boolean isSpCurrentResiValidCoBr = spResidentTypeCoBr.getSelectedItemPosition() != 0; //0 -> select any
                            boolean isSpCurrentStayDurationValidCoBr = spDurationStayAtCurrentAddressCoBr.getSelectedItemPosition() != 0; //0 -> select
                            boolean isSpCurrentCountryValidCoBr = spCurrentCountryCoBr.getSelectedItemPosition() != 0;
                            boolean isSpCurrentStateValidCoBr = spCurrentStateCoBr.getSelectedItemPosition() != 0;
                            boolean isSpCurrentCityValidCoBr = spCurrentCityCoBr.getSelectedItemPosition() != 0;

                            boolean isSpPermanentCountryValidCoBr = spPermanentCountryCoBr.getSelectedItemPosition() != 0;
                            boolean isPermanentStateValidCoBr = spPermanentStateCoBr.getSelectedItemPosition() != 0;
                            boolean isPermanentCityValidCoBr = spPermanentCityCoBr.getSelectedItemPosition() != 0;

                            boolean isProfessionValidCoBr = spProfessionCoBr.getSelectedItemPosition() != 0;
                            boolean isEmployerTypeValidCoBr = (rgemptypeCoBr.getCheckedRadioButtonId() == R.id.rbempType_privateCoBr) || (rgemptypeBr.getCheckedRadioButtonId() == R.id.rbempType_govCoBr);
                            boolean isEmployementDurationValidCoBr = spdurationofjobCoBr.getSelectedItemPosition() != 0;


                            if (!edtFnameBr.getText().toString().equals("") && !edtLnameBr.getText().toString().equals("") &&
                                    !txtBirthdateBr.getText().toString().equals("") && isBrHasAdharOrPan && isRentedBr &&
                                    !edtCurrentAddressBr.getText().toString().equals("") && !edtCurrentPincodeBr.getText().toString().equals("")
                                    && isGenderSelectedBr && isMaritalSelectedBr && isSpCurrentResiValidBr
                                    && isSpCurrentStayDurationValidBr && isSpCurrentCountryValidBr && isSpCurrentStateValidBr && isSpCurrentCityValidBr
                                    && !edtPermanentAddressBr.getText().toString().equals("") && !edtPermanentPincodeBr.getText().toString().equals("")
                                    //                                && isSpPermanentResiValidBr
                                    && isSpPermanentCountryValidBr && isPermanentStateValidBr && isPermanentCityValidBr
                                    && isProfessionValidBr
                                    && isLastCompletedDegreeValidBr
                                    && !input_passingyearBr.getText().toString().equals("")
                                    && isScoreUnitValidBr
                                    && isGapInEducationValidBr) {

                                if (isProfessionVisibleBr) {   //profession case borrower

                                    //profession checking
                                    if (!edtCompanyBr.getText().toString().equals("") && !edtAddressOffBr.getText().toString().equals("")
                                            && !edtPincodeOffBr.getText().toString().equals("") && !edtAnnualSalBr.getText().toString().equals("")
                                            && isEmployerTypeValidBr && isEmployementDurationValidBr) {
                                        if (!has_coborrower.equals("0")) {   //has coborrower

                                            //coborrower checkings
                                            if (!edtFnameCoBr.getText().toString().equals("") && !edtLnameCoBr.getText().toString().equals("") &&
                                                    !txtBirthdateCoBr.getText().toString().equals("") && isCoBrHasAdharOrPan && isRentedCoBr &&
                                                    !edtCurrentAddressCoBr.getText().toString().equals("") && !edtCurrentPincodeCoBr.getText().toString().equals("")
                                                    && !edtMobileCoBr.getText().toString().equals("") && !edtEmailCoBr.getText().toString().equals("")
                                                    && isGenderSelectedCoBr && isMaritalSelectedCoBr && isSpCurrentResiValidCoBr
                                                    && isSpCurrentStayDurationValidCoBr && isSpCurrentCountryValidCoBr && isSpCurrentStateValidCoBr && isSpCurrentCityValidCoBr
                                                    && !edtPermanentAddressCoBr.getText().toString().equals("") && !edtPermanentPincodeCoBr.getText().toString().equals("")
                                                    //                                && isSpPermanentResiValidBr
                                                    && isSpPermanentCountryValidCoBr && isPermanentStateValidCoBr && isPermanentCityValidCoBr
                                                    && isProfessionValidCoBr) {

                                                if (isProfessionVisibleCoBr) {  //profession case

                                                    //emplyement chevking cobr
                                                    if (!edtCompanyCoBr.getText().toString().equals("") && !edtAddressOffCoBr.getText().toString().equals("")
                                                            && !edtPincodeOffCoBr.getText().toString().equals("") && !edtAnnualSalCoBr.getText().toString().equals("")
                                                            && isEmployerTypeValidCoBr && isEmployementDurationValidCoBr) {

                                                        Toast.makeText(context, "Valid", Toast.LENGTH_LONG).show();
                                                        submitDetailedInfo();
                                                    } else {  //coborrower checking else
                                                        Toast.makeText(context, "PLease enter all the fields", Toast.LENGTH_SHORT).show();
                                                        if (edtCompanyCoBr.getText().toString().equals(""))
                                                            edtCompanyCoBr.setError("Enter company name");
                                                        if (edtAddressOffCoBr.getText().toString().equals(""))
                                                            edtAddressOffCoBr.setError("Enter company address");
                                                        if (edtPincodeOffCoBr.getText().toString().equals(""))
                                                            edtPincodeOffCoBr.setError("Enter company Pincode");
                                                        if (edtAnnualSalCoBr.getText().toString().equals(""))
                                                            edtAnnualSalCoBr.setError("Enter annual salary");
                                                    }
                                                } else { //no profession case coborrower
                                                    submitDetailedInfo();
                                                }
                                            } else {  // coborrower checking else

                                                Toast.makeText(context, "PLease enter all the fields", Toast.LENGTH_SHORT).show();
                                                if (edtFnameCoBr.getText().toString().equals(""))
                                                    edtFnameCoBr.setError("Enter first name");
                                                if (edtLnameCoBr.getText().toString().equals(""))
                                                    edtLnameCoBr.setError("Enter last name");
                                                if (txtBirthdateCoBr.getText().toString().equals(""))
                                                    txtBirthdateCoBr.setError("Enter birth date");
                                                if (!isCoBrHasAdharOrPan) {
                                                    if (edtPanCoBr.getText().toString().equals(""))
                                                        edtPanCoBr.setError("Enter Pan");
                                                    else if (edtAadhaarCoBr.getText().toString().equals(""))
                                                        edtAadhaarCoBr.setError("Enter Pan");
                                                }
                                                if (edtCurrentAddressCoBr.getText().toString().equals(""))
                                                    edtCurrentAddressCoBr.setError("Enter current address");
                                                if (edtCurrentPincodeCoBr.getText().toString().equals(""))
                                                    edtCurrentPincodeCoBr.setError("Enter current pincode");
                                                if (edtPermanentAddressCoBr.getText().toString().equals(""))
                                                    edtPermanentAddressCoBr.setError("Enter permanent address");
                                                if (edtPermanentPincodeCoBr.getText().toString().equals(""))
                                                    edtPermanentPincodeCoBr.setError("Enter permanent pincode");
                                                if (edtEmailCoBr.getText().toString().equals(""))
                                                    edtEmailCoBr.setError("Enter email");
                                                if (edtMobileCoBr.getText().toString().equals(""))
                                                    edtMobileCoBr.setError("Enter mobile");
                                                if(!isSpCurrentResiValidCoBr) setSpinnerError(spResidentTypeCoBr, "Please select residency type");
                                                if (!isSpCurrentStayDurationValidCoBr) setSpinnerError(spDurationStayAtCurrentAddressCoBr,"Please select duration");
                                                if (!isSpCurrentCountryValidCoBr) setSpinnerError(spCurrentCountryCoBr, "Please select country");
                                                if (!isSpCurrentStateValidCoBr) setSpinnerError(spCurrentStateCoBr, "Please select state");
                                                if (!isSpCurrentCityValidCoBr) setSpinnerError(spCurrentCityCoBr, "Please select city");
                                                if (!isSpPermanentCountryValidCoBr) setSpinnerError(spPermanentCountryCoBr, "Please select country");
                                                if (!isPermanentStateValidCoBr) setSpinnerError(spPermanentStateCoBr, "Please select state");
                                                if (!isPermanentCityValidCoBr) setSpinnerError(spPermanentCityCoBr, "Please select city");
                                                if (!isProfessionValidCoBr) setSpinnerError(spProfessionCoBr, "Please select profession");
                                                if (!isEmployementDurationValidCoBr) setSpinnerError(spdurationofjobCoBr,"Please select duration");

                                            }

                                        } else {  //no borrower
                                            submitDetailedInfo();
                                        }
                                    } else {   //profession checking else
                                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                        if (edtCompanyBr.getText().toString().equals(""))
                                            edtCompanyBr.setError("Enter company name");
                                        if (edtAddressOffBr.getText().toString().equals(""))
                                            edtAddressOffBr.setError("Enter Address");
                                        if (edtAnnualSalBr.getText().toString().equals(""))
                                            edtAnnualSalBr.setError("Enter salary");
                                        if (edtPincodeOffBr.getText().toString().equals(""))
                                            edtPincodeOffBr.setError("Enter Pincode");
                                    }
                                } else {   //no profsseion
                                    submitDetailedInfo();
                                }
                            } else {
                                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                                if (edtFnameBr.getText().toString().equals(""))
                                    edtFnameBr.setError("Enter First name");
                                if (edtLnameBr.getText().toString().equals(""))
                                    edtLnameBr.setError("Enter Last name");
                                if (!isBrHasAdharOrPan) {
                                    if (edtPanBr.getText().toString().equals(""))
                                        edtPanBr.setError("Enter Pan");
                                    else if (edtAadhaarBr.getText().toString().equals(""))
                                        edtAadhaarBr.setError("Enter Pan");
                                }
                                if (!isRentedBr) edtMonthlyRentBr.setError("Enter Rent");
                                if (edtCurrentAddressBr.getText().toString().equals(""))
                                    edtCurrentAddressBr.setError("Enter Address");
                                if (edtCurrentPincodeBr.getText().toString().equals(""))
                                    edtCurrentPincodeBr.setError("Enter Pincode");
                                if (edtPermanentAddressBr.getText().toString().equals(""))
                                    edtPermanentAddressBr.setError("Enter address");
                                if (edtPermanentPincodeBr.getText().toString().equals(""))
                                    edtPermanentPincodeBr.setError("Enter Pincode");
                                if (input_passingyearBr.getText().toString().equals(""))
                                    input_passingyearBr.setError("Enter Passing year");
                            }
                        }else{
                    try {
                        LoanApplicationFragment_3 loanApplicationFragment_3 = new LoanApplicationFragment_3();
                        transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_3).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
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
                    String datenew = dayOfMonth + "-" + month + "-" + year;
                    dateformate = dateFormateSystem(datenew);
                    txtBirthdateBr.setText(datenew);
                    MainApplication.Brdobkyc = datenew;
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
                            .get(Calendar.YEAR), Brcal.get(Calendar.MONTH),
                            Brcal.get(Calendar.DAY_OF_MONTH));
//                    data.getDatePicker().setMaxDate(System.currentTimeMillis() - 1234564);
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
                    String datenew = dayOfMonth + "-" + month + "-" + year;
                    dateformate = dateFormateSystem(datenew);
                    txtBirthdateCoBr.setText(datenew);
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

//                    if (!s.toString().matches(Globle.panPattern)){
//                        edtPanBr.setError("Please enter valid pan number");
//                        edtPanBr.requestFocus();
//                    }
//                    else {
                        MainApplication.coborrowerValue17 = edtPanBr.getText().toString();
//                        edtPanBr.setError(null);
//                    }

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
                            MainApplication.Bris_borrower_current_address_same_asdtl = Bris_borrower_current_address_same_as = addressSameAsCurrentPOJOArrayListBr.get(i).id;
                        }
                    }

                    switch (MainApplication.Bris_borrower_current_address_same_asdtl) {
                        case "0"://<option value="" selected="selected">Select</option>
                            break;

                        case "1"://<option value="1">Borrower Kyc Address</option>

                            MainApplication.Brkyc_address_pindtl = Brkyc_address_pin;
                            MainApplication.Brkyc_addressdtl = Brkyc_address;

                            edtCurrentPincodeBr.setText(Brkyc_address_pin);
                            edtCurrentAddressBr.setText(Brkyc_address);

                            if (!Brkyc_address_country.equals("null")) {
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

                            if (!Brcurrent_address_country.equals("null")) {
                                currentcountryID = "1";
                                spCurrentCountryBr.setSelection(Integer.parseInt(currentcountryID));
                            }
                            if (!Brcurrent_address_state.equals("null")) {
                                currentstateID = Brcurrent_address_state;

                                int count1 = borrowerCurrentStatePersonalPOJOArrayList.size();

                                for (int i = 0; i < count1; i++) {
                                    if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
                                        spCurrentStateBr.setSelection(i);
                                    }
                                }
                            }
                            if (!Brcurrent_address_city.equals("null")) {
                                currentcityID = Brcurrent_address_city;

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
                            MainApplication.Bris_borrower_permanent_address_same_askyc = Bris_borrower_permanent_address_same_as = addressSameAspermanentPOJOArrayListBr.get(i).id;
                        }
                    }

                    switch (MainApplication.Bris_borrower_permanent_address_same_askyc) {
                        case "0"://<option value="" selected="selected">Select</option>
                            break;

                        case "1"://<option value="1">Borrower Kyc Address</option>

                            MainApplication.Brkyc_address_pindtl = Brkyc_address_pin;
                            MainApplication.Brkyc_addressdtl = Brkyc_address;

                            edtPermanentPincodeBr.setText(Brkyc_address_pin);
                            edtPermanentAddressBr.setText(Brkyc_address);

                            if (!Brkyc_address_country.equals("null")) {
                                permanentcountryID = "1";
                                spPermanentCountryBr.setSelection(Integer.parseInt(permanentcountryID));
                            }
                            if (!Brkyc_address_state.equals("null") && !Brkyc_address_state.equals("")) {
                                permanentstateID = Brkyc_address_state;

                                int count1 = borrowerPermanentStatePersonalPOJOArrayList.size();

                                for (int i = 0; i < count1; i++) {
                                    if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(permanentstateID)) {
                                        spPermanentStateCoBr.setSelection(i);
                                    }
                                }
                            }
                            if (!Brkyc_address_city.equals("null") && !Brkyc_address_city.equals("")) {
                                permanentcityID = Brkyc_address_city;

                                int count2 = borrowerPermanentCityPersonalPOJOArrayList.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityID)) {
                                        spPermanentCityBr.setSelection(i);
                                    }
                                }
                            }

                            break;

                        case "2"://<option value="2">Borrower Current Address</option>

                            MainApplication.Brcurrent_address_pindtl = Brcurrent_address_pin;
                            MainApplication.Brcurrent_addressdtl = Brcurrent_address;

                            edtPermanentPincodeBr.setText(Brcurrent_address_pin);
                            edtPermanentAddressBr.setText(Brcurrent_address);

                            if (!Brcurrent_address_country.equals("null")) {
                                permanentcountryID = "1";
                                spPermanentCountryBr.setSelection(Integer.parseInt(permanentcountryID));
                            }
                            if (!Brcurrent_address_state.equals("null")) {
                                permanentstateID = Brcurrent_address_state;
                                if (borrowerPermanentStatePersonalPOJOArrayList != null){
                                    int count1 = borrowerPermanentStatePersonalPOJOArrayList.size();

                                    for (int i = 0; i < count1; i++) {
                                        if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(permanentstateID)) {
                                            spPermanentStateBr.setSelection(i);
                                        }
                                    }
                                }
                            }
                            if (!Brcurrent_address_city.equals("null")) {
                                permanentcityID = Brcurrent_address_city;
                                if (borrowerPermanentCityPersonalPOJOArrayList != null) {
                                    int count2 = borrowerPermanentCityPersonalPOJOArrayList.size();
                                    for (int i = 0; i < count2; i++) {
                                        if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityID)) {
                                            spPermanentCityBr.setSelection(i);
                                        }
                                    }
                                }
                            }

                            break;

                        case "3"://<option value="3">none</option>

                            MainApplication.Brpermanent_address_pindtl = Brpermanent_address_pin;
                            MainApplication.Brpermanent_addressdtl = Brpermanent_address;

                            edtPermanentPincodeBr.setText(Brpermanent_address_pin);
                            edtPermanentAddressBr.setText(Brpermanent_address);

                            if (!Brpermanent_address_country.equals("null")) {
                                permanentcountryID = "1";
                                spPermanentCountryBr.setSelection(Integer.parseInt(permanentcountryID));
                            }
                            if (!Brpermanent_address_state.equals("null")) {
                                permanentstateID = Brpermanent_address_state;

                                int count1 = borrowerPermanentStatePersonalPOJOArrayList.size();

                                for (int i = 0; i < count1; i++) {
                                    if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(permanentstateID)) {
                                        spPermanentStateBr.setSelection(i);
                                    }
                                }
                            }
                            if (!Brpermanent_address_city.equals("null")) {
                                permanentcityID = Brpermanent_address_city;

                                int count2 = borrowerPermanentCityPersonalPOJOArrayList.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityID)) {
                                        spPermanentCityBr.setSelection(i);
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

            spCurrentAddressSameAsKycCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String text = spCurrentAddressSameAsKycCoBr.getSelectedItem().toString();

                    int count = addressSameAsCurrentPOJOArrayListCoBr.size();
                    for (int i = 0; i < count; i++) {
                        if (addressSameAsCurrentPOJOArrayListCoBr.get(i).address.equalsIgnoreCase(text)) {
                            MainApplication.CoBris_borrower_current_address_same_asdtl = CoBris_coborrower_current_address_same_as = addressSameAsCurrentPOJOArrayListCoBr.get(i).id;
                        }
                    }

                    switch (MainApplication.CoBris_borrower_current_address_same_asdtl) {
                        case "0"://<option value="" selected="selected">Select</option>
                            break;

                        case "1"://<option value="1">CoBorrower Kyc Address</option>

                            MainApplication.CoBrkyc_address_pindtl = CoBrkyc_address_pin;
                            MainApplication.CoBrkyc_addressdtl = CoBrkyc_address;

                            edtCurrentPincodeCoBr.setText(CoBrkyc_address_pin);
                            edtCurrentAddressCoBr.setText(CoBrkyc_address);

                            if (!CoBrkyc_address_country.equals("null")) {
                                currentcountryIDCoBr = "1";
                                spCurrentCountryCoBr.setSelection(Integer.parseInt(currentcountryIDCoBr));
                            }

                            //state
                            if (!CoBrkyc_address_state.equals("null")) {
                                currentstateIDCoBr = CoBrkyc_address_state;
                                int count1 = borrowerCurrentStatePersonalPOJOArrayListCoBr.size();

                                for (int i = 0; i < borrowerCurrentStatePersonalPOJOArrayListCoBr.size(); i++) {
                                    if (borrowerCurrentStatePersonalPOJOArrayListCoBr.get(i).stateID.equalsIgnoreCase(currentstateIDCoBr)) {
                                        spCurrentStateCoBr.setSelection(i);
                                    }
                                }
                            }

                            //city
                            if (!CoBrkyc_address_city.equals("null")) {
                                currentcityIDCoBr = CoBrkyc_address_city;

                                int count2 = borrowerCurrentCityPersonalPOJOArrayListCoBr.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerCurrentCityPersonalPOJOArrayListCoBr.get(i).cityID.equalsIgnoreCase(currentcityIDCoBr)) {
                                        spCurrentCityCoBr.setSelection(i);
                                    }
                                }
                            }

                            break;


                        case "2":  //Borrower Current Address
                            MainApplication.CoBrcurrent_address_pindtl = Brcurrent_address_pin;
                            MainApplication.CoBrcurrent_addressdtl = Brcurrent_address;
                            edtCurrentPincodeCoBr.setText(Brcurrent_address_pin);
                            edtCurrentAddressCoBr.setText(Brcurrent_address);

                            if (!Brcurrent_address_country.equals("null")) {
                                currentcountryIDCoBr = "1";
                                spCurrentCountryCoBr.setSelection(Integer.parseInt(currentcountryIDCoBr));
                            }
                            //set state spinner as Borrower Current
                            if (!Brcurrent_address_state.equals("null")) {
                                currentstateIDCoBr = Brcurrent_address_state;
                                int count1 = borrowerCurrentStatePersonalPOJOArrayListCoBr.size();
                                for (int i = 0; i < count1; i++) {
                                    if (borrowerCurrentStatePersonalPOJOArrayListCoBr.get(i).stateID.equalsIgnoreCase(currentstateIDCoBr)) {
                                        spCurrentStateCoBr.setSelection(i);
                                        break;
                                    }
                                }
                            }

                            //set city spinner as Borrower Current
                            if (!Brcurrent_address_city.equals("null")) {
                                currentcityIDCoBr = Brcurrent_address_city;

                                int count2 = borrowerCurrentCityPersonalPOJOArrayListCoBr.size();
                                for (int i = 0; i < borrowerCurrentCityPersonalPOJOArrayListCoBr.size(); i++) {
                                    if (borrowerCurrentCityPersonalPOJOArrayListCoBr.get(i).cityID.equalsIgnoreCase(currentcityIDCoBr)) {
                                        spCurrentCityCoBr.setSelection(i);
                                        break;
                                    }
                                }
                            }

                            break;

                        case "3":  //Borrower Permanent Address
                            MainApplication.CoBrcurrent_address_pindtl = Brpermanent_address_pin;
                            MainApplication.CoBrcurrent_addressdtl = Brpermanent_address;
                            edtCurrentPincodeCoBr.setText(Brpermanent_address_pin);
                            edtCurrentAddressCoBr.setText(Brpermanent_address);

                            if (!Brpermanent_address_country.equals("null")) {
                                currentcountryIDCoBr = "1";
                                spCurrentCountryCoBr.setSelection(Integer.parseInt(currentcountryIDCoBr));
                            }
                            //set state spinner as Borrower Permanent
                            if (!Brpermanent_address_state.equals("null")) {
                                currentstateIDCoBr = Brpermanent_address_state;
                                int count1 = borrowerPermanentStatePersonalPOJOArrayListCoBr.size();
                                for (int i = 0; i < count1; i++) {
                                    if (borrowerPermanentStatePersonalPOJOArrayListCoBr.get(i).stateID.equalsIgnoreCase(currentstateIDCoBr)) {
                                        spCurrentStateCoBr.setSelection(i);
                                        break;
                                    }
                                }
                            }

                            //set city spinner as Borrower Permanent
                            if (!Brpermanent_address_city.equals("null")) {
                                currentcityIDCoBr = Brpermanent_address_city;

                                int count2 = borrowerPermanentCityPersonalPOJOArrayList.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityIDCoBr)) {
                                        spCurrentCityCoBr.setSelection(i);
                                        break;
                                    }
                                }
                            }

                            break;

                        case "4"://<option value="4">None</option>

                            MainApplication.CoBrcurrent_address_pindtl = CoBrcurrent_address_pin;
                            MainApplication.CoBrcurrent_addressdtl = CoBrcurrent_address;

                            edtCurrentPincodeCoBr.setText(CoBrcurrent_address_pin);
                            edtCurrentAddressCoBr.setText(CoBrcurrent_address);

                            if (!CoBrcurrent_address_country.equals("null")) {
                                currentcountryIDCoBr = "1";
                                spCurrentCountryCoBr.setSelection(Integer.parseInt(currentcountryIDCoBr));
                            }
                            if (!CoBrcurrent_address_state.equals("null") && !CoBrcurrent_address_state.equals("")) {
                                currentstateIDCoBr = CoBrcurrent_address_state;

                                int count1 = borrowerCurrentStatePersonalPOJOArrayListCoBr.size();

                                for (int i = 0; i < count1; i++) {
                                    if (borrowerCurrentStatePersonalPOJOArrayListCoBr.get(i).stateID.equalsIgnoreCase(currentstateIDCoBr)) {
                                        spCurrentStateCoBr.setSelection(i);
                                    }
                                }
                            }
                            if (!CoBrcurrent_address_city.equals("null") && !CoBrcurrent_address_city.equals("")) {
                                currentcityIDCoBr = CoBrcurrent_address_city;

                                int count2 = borrowerCurrentCityPersonalPOJOArrayListCoBr.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerCurrentCityPersonalPOJOArrayListCoBr.get(i).cityID.equalsIgnoreCase(currentcityIDCoBr)) {
                                        spCurrentCityCoBr.setSelection(i);
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

            spdurationofjobBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    MainApplication.Brcurrent_employment_durationdtl = Brcurrent_employment_duration = position+"";
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //PERMANENT ADDRESS OF COBORRORWER
            spCurrentAddressSameAsKycOrBorrowerCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String text = spCurrentAddressSameAsKycOrBorrowerCoBr.getSelectedItem().toString();

                    int count = addressSameAspermanentPOJOArrayListCoBr.size();
                    for (int i = 0; i < count; i++) {
                        if (addressSameAspermanentPOJOArrayListCoBr.get(i).address.equalsIgnoreCase(text)) {
                            MainApplication.CoBris_borrower_permanent_address_same_askyc = CoBris_coborrower_permanent_address_same_as = addressSameAspermanentPOJOArrayListCoBr.get(i).id;
                        }
                    }

                    switch (MainApplication.CoBris_borrower_permanent_address_same_askyc) {
                        case "0"://<option value="" selected="selected">Select</option>
                            break;

                        case "1"://<option value="1">Borrower Kyc Address</option>

                            MainApplication.CoBrkyc_address_pindtl = CoBrkyc_address_pin;
                            MainApplication.CoBrkyc_addressdtl = CoBrkyc_address;

                            edtPermanentPincodeCoBr.setText(CoBrkyc_address_pin);
                            edtPermanentAddressCoBr.setText(CoBrkyc_address);

                            if (!CoBrkyc_address_country.equals("null")) {
                                permanentcountryIDCoBr = "1";
                                spPermanentCountryCoBr.setSelection(Integer.parseInt(permanentcountryIDCoBr));
                            }
                            if (!CoBrkyc_address_state.equals("null")) {
                                permanentstateIDCoBr = CoBrkyc_address_state;

                                int count1 = borrowerPermanentStatePersonalPOJOArrayList.size();

                                for (int i = 0; i < count1; i++) {
                                    if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(permanentstateIDCoBr)) {
                                        spPermanentStateCoBr.setSelection(i);
                                    }
                                }
                            }
                            if (!CoBrkyc_address_city.equals("null")) {
                                permanentcityIDCoBr = CoBrkyc_address_city;

                                int count2 = borrowerPermanentCityPersonalPOJOArrayList.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityIDCoBr)) {
                                        spPermanentCityCoBr.setSelection(i);
                                    }
                                }
                            }

                            break;

                        case "2"://<option value="2">Borrower Current Address</option>

                            MainApplication.CoBrpermanent_address_pindtl = CoBrcurrent_address_pin;
                            MainApplication.CoBrpermanent_addressdtl = CoBrcurrent_address;

                            edtPermanentPincodeCoBr.setText(CoBrcurrent_address_pin);
                            edtPermanentAddressCoBr.setText(CoBrcurrent_address);

                            if (!CoBrcurrent_address_country.equals("null")) {
                                permanentcountryIDCoBr = "1";
                                spPermanentCountryCoBr.setSelection(Integer.parseInt(permanentcountryIDCoBr));
                            }
                            if (!CoBrcurrent_address_state.equals("null")) {
                                permanentstateIDCoBr = CoBrcurrent_address_state;

                                int count1 = borrowerPermanentStatePersonalPOJOArrayList.size();

                                for (int i = 0; i < count1; i++) {
                                    if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(permanentstateIDCoBr)) {
                                        spPermanentStateCoBr.setSelection(i);
                                    }
                                }
                            }
                            if (!CoBrcurrent_address_city.equals("null")) {
                                permanentcityIDCoBr = CoBrcurrent_address_city;

                                int count2 = borrowerPermanentCityPersonalPOJOArrayList.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityIDCoBr)) {
                                        spPermanentCityCoBr.setSelection(i);
                                    }
                                }
                            }

                            break;

                        case "3"://<option value="3">none</option>

                            MainApplication.CoBrpermanent_address_pindtl = CoBrpermanent_address_pin;
                            MainApplication.CoBrpermanent_addressdtl = CoBrpermanent_address;

                            edtPermanentPincodeCoBr.setText(CoBrpermanent_address_pin);
                            edtPermanentAddressCoBr.setText(CoBrpermanent_address);

                            if (!CoBrpermanent_address_country.equals("null")) {
                                permanentcountryIDCoBr = "1";
                                spPermanentCountryCoBr.setSelection(Integer.parseInt(permanentcountryIDCoBr));
                            }
                            if (!CoBrpermanent_address_state.equals("null")) {
                                permanentstateIDCoBr = CoBrpermanent_address_state;

                                int count1 = borrowerPermanentStatePersonalPOJOArrayList.size();

                                for (int i = 0; i < count1; i++) {
                                    if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(permanentstateIDCoBr)) {
                                        spPermanentStateCoBr.setSelection(i);
                                    }
                                }
                            }
                            if (!CoBrpermanent_address_city.equals("null")) {
                                permanentcityIDCoBr = CoBrpermanent_address_city;

                                int count2 = borrowerPermanentCityPersonalPOJOArrayList.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityIDCoBr)) {
                                        spPermanentCityCoBr.setSelection(i);
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

            spResidentTypeCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spResidentTypeCoBr.getSelectedItem().toString();
                        int count = coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.size();
//                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.get(i).residencetypeName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue1 = currentResidencetypeIDCoBr = CoBrcurrent_residence_type = coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.get(i).dresidencetypeID;
                                Log.e(TAG, "id:" + coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.get(i).dresidencetypeID);

                                if (coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.get(i).dresidencetypeID.equalsIgnoreCase("1")) {
                                    edtMonthlyRentLayoutCoBr.setVisibility(View.VISIBLE);
//                                    edtMonthlyRentCoBr.setText("");
                                } else if (coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.get(i).dresidencetypeID.equalsIgnoreCase("3")) {
                                    edtMonthlyRentLayoutCoBr.setVisibility(View.VISIBLE);
//                                    edtMonthlyRentCoBr.setText("");
                                } else if (coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.get(i).dresidencetypeID.equalsIgnoreCase("4")) {
                                    edtMonthlyRentLayoutCoBr.setVisibility(View.VISIBLE);
//                                    edtMonthlyRentCoBr.setText("");
                                } else if (coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.get(i).dresidencetypeID.equalsIgnoreCase("5")) {
                                    edtMonthlyRentLayoutCoBr.setVisibility(View.VISIBLE);
//                                    edtMonthlyRentCoBr.setText("");
                                } else if (coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.get(i).dresidencetypeID.equalsIgnoreCase("6")) {
                                    edtMonthlyRentLayoutCoBr.setVisibility(View.VISIBLE);
//                                    edtMonthlyRentCoBr.setText("");
                                } else {
                                    edtMonthlyRentLayoutCoBr.setVisibility(View.GONE);
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

                    int count = professionPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (professionPOJOArrayList.get(i).Salaried.equalsIgnoreCase(text)) {
                            MainApplication.Brprofessiondtl = professionID = Brprofession = professionPOJOArrayList.get(i).id;
                        }
                    }
                    if (position == 2 || position == 0) {
                        try {
                            linOfficeDetails.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
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

            spinnerRelationshipwithBorrower.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String text = spinnerRelationshipwithBorrower.getSelectedItem().toString();
                    for (int i = 0; i < relationshipwithBorrowerPOJOArrayList.size(); i++) {
                        if (relationshipwithBorrowerPOJOArrayList.get(i).relationName.equalsIgnoreCase(text)) {
                            MainApplication.CoBrrelationship_with_applicantdtl = CoBrrelationship_with_applicant = relationshipwithBorrowerPOJOArrayList.get(i).relationID;
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spProfessionCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    String text = spProfessionCoBr.getSelectedItem().toString();

                    int count = professionPOJOArrayListCoBr.size();
                    for (int i = 0; i < count; i++) {
                        if (professionPOJOArrayListCoBr.get(i).Salaried.equalsIgnoreCase(text)) {
                            MainApplication.CoBrprofessiondtl = professionIDCoBr = CoBrprofession = professionPOJOArrayListCoBr.get(i).id;
                        }
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

            spDurationStayAtCurrentAddressBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spDurationStayAtCurrentAddressBr.getSelectedItem().toString();
                        int count = currentResidenceDurationPersonalPOJOArrayList.size();
                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (currentResidenceDurationPersonalPOJOArrayList.get(i).durationName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue26 = Brcurrent_address_stay_duration = currentResidenceDurationPersonalPOJOArrayList.get(i).durationID;
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

            spDurationStayAtCurrentAddressCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spDurationStayAtCurrentAddressCoBr.getSelectedItem().toString();
                        int count = currentResidenceDurationPersonalPOJOArrayListCoBr.size();
                        for (int i = 0; i < count; i++) {
                            if (currentResidenceDurationPersonalPOJOArrayListCoBr.get(i).durationName.equalsIgnoreCase(text)) {
                                MainApplication.coborrowerValue26 = CoBrcurrent_address_stay_duration = currentResidenceDurationPersonalPOJOArrayListCoBr.get(i).durationID;
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
                        case R.id.rbiscgpa_yesBr:
                            input_cgpaBr.setVisibility(View.VISIBLE);
                            input_degreeBr.setVisibility(View.GONE);
                            break;
                        case R.id.rbiscgpa_noBr:
                            input_cgpaBr.setVisibility(View.GONE);
                            input_degreeBr.setVisibility(View.VISIBLE);
                            break;
                        default:
                            break;
                    }
                }
            });


            //Address Br
            spCurrentCityBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spCurrentCityBr.getSelectedItem().toString();
                        int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.Brkyc_address_citykyc = currentcityID = Brcurrent_address_city = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
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
                                MainApplication.Brkyc_address_statekyc = currentstateID = Brcurrent_address_state = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
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
                                MainApplication.Brkyc_address_countrykyc = currentcountryID = Brcurrent_address_country = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
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

            spPermanentCityBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {

                        String text = spPermanentCityBr.getSelectedItem().toString();
                        int count = borrowerPermanentCityPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.Brpermanent_address_citydtl = permanentcityID = Brpermanent_address_city = borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID;
                                Log.e(TAG, "sppermanentCityBr: +++++++++++++++++++*********************" + permanentcityID);
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

            spPermanentStateBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spPermanentStateBr.getSelectedItem().toString();
                        int count = borrowerPermanentStatePersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                                MainApplication.Brpermanent_address_statedtl = permanentstateID = Brpermanent_address_state = borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID;
                            }
                        }
                    } catch (Exception e) {

                    }
                    permanentcityApiCall();

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
                        int count = borrowerPermanentCountryPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerPermanentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                                MainApplication.Brpermanent_address_countrydtl = permanentcountryID = Brpermanent_address_country = borrowerPermanentCountryPersonalPOJOArrayList.get(i).countryID;
                            }
                        }
                        permanentstateApiCall();
//                        if (permanentcityID.equals("")) {
//                            sppermanentCityBr.setSelection(0);
//                        } else {
//                            spPermanentCityBr.setSelection(Integer.parseInt(permanentcityID));
//                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spCityOffBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {

                        String text = spCityOffBr.getSelectedItem().toString();
                        int count = borrowerOffCityPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerOffCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.Brkyc_address_citykyc = offcityIDBr = borrowerOffCityPersonalPOJOArrayList.get(i).cityID;
                                Log.e(TAG, "spOffCityBr: +++++++++++++++++++*********************" + offcityIDBr);
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

            spStateOffBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spStateOffBr.getSelectedItem().toString();
                        int count = borrowerOffStatePersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerOffStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                                MainApplication.Brkyc_address_statekyc = offstateIDBr = borrowerOffStatePersonalPOJOArrayList.get(i).stateID;
                            }
                        }
                    } catch (Exception e) {

                    }
                    offcityApiCall();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spCountryOffBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spCountryOffBr.getSelectedItem().toString();
                        int count = borrowerOffCountryPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerOffCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                                MainApplication.Broffice_address_countrydtl = offcountryIDBr = Broffice_address_country = borrowerOffCountryPersonalPOJOArrayList.get(i).countryID;
                            }
                        }
                        offstateApiCall();
//                        if (OffcityID.equals("")) {
//                            spOffCityBr.setSelection(0);
//                        } else {
//                            spOffCityBr.setSelection(Integer.parseInt(OffcityID));
//                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            //Address CoBr
            spCurrentCityCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {

                        String text = spCurrentCityCoBr.getSelectedItem().toString();
                        int count = borrowerCurrentCityPersonalPOJOArrayListCoBr.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCityPersonalPOJOArrayListCoBr.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.CoBrcurrent_address_citydtl = currentcityIDCoBr = CoBrcurrent_address_city = borrowerCurrentCityPersonalPOJOArrayListCoBr.get(i).cityID;
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
                                MainApplication.CoBrcurrent_address_countrydtl = currentcountryIDCoBr = CoBrcurrent_address_country = borrowerCurrentCountryPersonalPOJOArrayListCoBr.get(i).countryID;
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

            spPermanentCityCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {

                        String text = spPermanentCityCoBr.getSelectedItem().toString();
                        int count = borrowerPermanentCityPersonalPOJOArrayListCoBr.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerPermanentCityPersonalPOJOArrayListCoBr.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.CoBrpermanent_address_citydtl = permanentcityIDCoBr = CoBrpermanent_address_city = borrowerPermanentCityPersonalPOJOArrayListCoBr.get(i).cityID;
//                                Log.e(TAG, "spPermanentCityCoBr: +++++++++++++++++++*********************" + permanentcityIDCoBr);
                            }
                        }
                    } catch (Exception e) {
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spPermanentStateCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spPermanentStateCoBr.getSelectedItem().toString();
                        int count = borrowerPermanentStatePersonalPOJOArrayListCoBr.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerPermanentStatePersonalPOJOArrayListCoBr.get(i).stateName.equalsIgnoreCase(text)) {
                                MainApplication.CoBrpermanent_address_statedtl = permanentstateIDCoBr = CoBrpermanent_address_state = borrowerPermanentStatePersonalPOJOArrayListCoBr.get(i).stateID;
                            }
                        }
                    } catch (Exception e) {

                    }
                    permanentcityApiCallCoBr();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spPermanentCountryCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spPermanentCountryCoBr.getSelectedItem().toString();
                        int count = borrowerPermanentCountryPersonalPOJOArrayListCoBr.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerPermanentCountryPersonalPOJOArrayListCoBr.get(i).countryName.equalsIgnoreCase(text)) {
                                MainApplication.CoBrpermanent_address_countrydtl = permanentcountryIDCoBr = CoBrpermanent_address_country = borrowerPermanentCountryPersonalPOJOArrayListCoBr.get(i).countryID;
                            }
                        }
                        permanentstateApiCallCoBr();
//                        if (PermanentcityIDCoBr.equals("")) {
//                            spPermanentCityCoBr.setSelection(0);
//                        } else {
//                            spPermanentCityCoBr.setSelection(Integer.parseInt(PermanentcityIDCoBr));
//                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spCityOffCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {

                        String text = spCityOffCoBr.getSelectedItem().toString();
                        int count = borrowerOffCityPersonalPOJOArrayListCoBr.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerOffCityPersonalPOJOArrayListCoBr.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.CoBrkyc_address_citydtl = offcityIDCoBr = borrowerOffCityPersonalPOJOArrayListCoBr.get(i).cityID;
//                                Log.e(TAG, "spOffCityCoBr: +++++++++++++++++++*********************" + OffcityIDCoBr);
                            }
                        }
                    } catch (Exception e) {
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spStateOffCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spStateOffCoBr.getSelectedItem().toString();
                        int count = borrowerOffStatePersonalPOJOArrayListCoBr.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerOffStatePersonalPOJOArrayListCoBr.get(i).stateName.equalsIgnoreCase(text)) {
                                MainApplication.CoBroffice_address_statedtl = offstateIDCoBr = borrowerOffStatePersonalPOJOArrayListCoBr.get(i).stateID;
                            }
                        }
                    } catch (Exception e) {

                    }
                    offcityApiCallCoBr();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spCountryOffCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spCountryOffCoBr.getSelectedItem().toString();
                        int count = borrowerOffCountryPersonalPOJOArrayListCoBr.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerOffCountryPersonalPOJOArrayListCoBr.get(i).countryName.equalsIgnoreCase(text)) {
                                MainApplication.CoBrkyc_address_countrykyc = offcountryIDCoBr = borrowerOffCountryPersonalPOJOArrayListCoBr.get(i).countryID;
                            }
                        }
                        offstateApiCallCoBr();
//                        if (OffcityIDCoBr.equals("")) {
//                            spOffCityCoBr.setSelection(0);
//                        } else {
//                            spOffCityCoBr.setSelection(Integer.parseInt(OffcityIDCoBr));
//                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spdurationofjobCoBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    MainApplication.CoBrtotal_employement_durationdtl = CoBrtotal_employement_duration = "" + position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            /** END SPINNER CLICK **/


            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    enableDisableViews(true);
                    isEdit = true;
                    btnEdit.setVisibility(View.GONE);
                }
            });


            if (!Globle.isNetworkAvailable(context)) {

            } else {
                JSONObject jsonObject = new JSONObject();

                dtlgetCurrentStates(jsonObject);
                dtlgetCurrentCities(jsonObject);
                dtlgetPermanentStates(jsonObject);
                dtlgetPermanentCities(jsonObject);

                dtlgetCurrentStatesCoBr(jsonObject);
                dtlgetCurrentCitiesCoBr(jsonObject);
                dtlgetPermanentStatesCoBr(jsonObject);
                dtlgetPermanentCitiesCoBr(jsonObject);
                ProfessionApiCall();
//                ProfessionApiCallCoBr();
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

    private void enableDisableViews(boolean f) {
        edtFnameBr.setEnabled(f);
        edtMnameBr.setEnabled(f);
        edtLnameBr.setEnabled(f);
        txtBirthdayCalenderBr.setClickable(f);
        rgGenderBr.setEnabled(f);
        rbMaleBr.setEnabled(f);
        rbFemaleBr.setEnabled(f);
        rgMaritalStatusBr.setEnabled(f);
        rbSingleBr.setEnabled(f);
        rbMarriedBr.setEnabled(f);
        edtPanBr.setEnabled(f);
        edtAadhaarBr.setEnabled(f);
        spResidentTypeBr.setEnabled(f);
        edtMonthlyRentBr.setEnabled(f);
        spCurrentAddressSameAsKycBr.setEnabled(f);
        spdurationofjobBr.setEnabled(f);
        edtCurrentPincodeBr.setEnabled(f);
        edtCurrentAddressBr.setEnabled(f);
        spCurrentCountryBr.setEnabled(f);
        spCurrentStateBr.setEnabled(f);
        spCurrentCityBr.setEnabled(f);
        spCurrentAddressSameAsKycOrBorrowerBr.setEnabled(f);
        edtPermanentPincodeBr.setEnabled(f);
        edtPermanentAddressBr.setEnabled(f);
        spPermanentCountryBr.setEnabled(f);
        spPermanentStateBr.setEnabled(f);
        spPermanentCityBr.setEnabled(f);
        spProfessionBr.setEnabled(f);
        rbempType_govBr.setEnabled(f);
        rbempType_privateBr.setEnabled(f);
        edtCompanyBr.setEnabled(f);
        edtAnnualSalBr.setEnabled(f);
        edtPincodeOffBr.setEnabled(f);
        edtAddressOffBr.setEnabled(f);
        spCountryOffBr.setEnabled(f);
        spStateOffBr.setEnabled(f);
        spCityOffBr.setEnabled(f);
        splastdegreecompletedBr.setEnabled(f);
        input_passingyearBr.setEnabled(f);

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

                    //Current State Br
                    currentstate_arrayList = new ArrayList<>();
                    borrowerCurrentStatePersonalPOJOArrayList = new ArrayList<>();
                    currentstate_arrayList.add("Select Any");
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

                    //Permanent state Br
                    permanentstate_arrayList = new ArrayList<>();
                    borrowerPermanentStatePersonalPOJOArrayList = new ArrayList<>();
                    permanentstate_arrayList.add("Select Any");
                    for (int i = 0; i < jsonArraystates.length(); i++) {
                        BorrowerPermanentStatePersonalPOJO borrowerpermanentStatePersonalPOJO = new BorrowerPermanentStatePersonalPOJO();
                        JSONObject mJsonti = jsonArraystates.getJSONObject(i);
                        borrowerpermanentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                        permanentstate_arrayList.add(mJsonti.getString("state_name"));
                        borrowerpermanentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                        borrowerPermanentStatePersonalPOJOArrayList.add(borrowerpermanentStatePersonalPOJO);
                    }
                    arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentstate_arrayList);
                    spPermanentStateBr.setAdapter(arrayAdapter_permanentState);
                    arrayAdapter_permanentState.notifyDataSetChanged();

                }

                if (jsonData.getJSONArray("cities").length() > 0) {

                    JSONArray jsonArraycities = jsonData.getJSONArray("cities");

                    //Current City Br
                    currentcity_arrayList = new ArrayList<>();
                    borrowerCurrentCityPersonalPOJOArrayList = new ArrayList<>();
                    currentcity_arrayList.add("Select Any");
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

                    //Permanent City Br
                    permanentcity_arrayList = new ArrayList<>();
                    borrowerPermanentCityPersonalPOJOArrayList = new ArrayList<>();
                    permanentcity_arrayList.add("Select Any");
                    for (int i = 0; i < jsonArraycities.length(); i++) {
                        BorrowerPermanentCityPersonalPOJO borrowerpermanentCityPersonalPOJO = new BorrowerPermanentCityPersonalPOJO();
                        JSONObject mJsonti = jsonArraycities.getJSONObject(i);
                        borrowerpermanentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
                        permanentcity_arrayList.add(mJsonti.getString("city_name"));
                        borrowerpermanentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
                        borrowerPermanentCityPersonalPOJOArrayList.add(borrowerpermanentCityPersonalPOJO);
                    }
                    arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayList);
                    spPermanentCityBr.setAdapter(arrayAdapter_permanentCity);
                    arrayAdapter_permanentCity.notifyDataSetChanged();

                    int count1 = borrowerPermanentCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count1; i++) {
                        if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityID)) {
                            spPermanentCityBr.setSelection(i);
                        }
                    }
                }

                if (jsonData.getJSONArray("cocountries").length() > 0) {
                    JSONArray jsonArraycocountries = jsonData.getJSONArray("cocountries");
                }
                if (jsonData.getJSONArray("costates").length() > 0) {

                    JSONArray jsonArraycostates = jsonData.getJSONArray("costates");

                    //Current State CoBr
                    currentstate_arrayListCoBr = new ArrayList<>();
                    borrowerCurrentStatePersonalPOJOArrayListCoBr = new ArrayList<>();
                    currentstate_arrayListCoBr.add("Select Any");
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

                    //Permanent state CoBr
                    permanentstate_arrayListCoBr = new ArrayList<>();
                    borrowerPermanentStatePersonalPOJOArrayListCoBr = new ArrayList<>();
                    permanentstate_arrayListCoBr.add("Select Any");
                    for (int i = 0; i < jsonArraycostates.length(); i++) {
                        BorrowerPermanentStatePersonalPOJO borrowerpermanentStatePersonalPOJOCoBr = new BorrowerPermanentStatePersonalPOJO();
                        JSONObject mJsonti = jsonArraycostates.getJSONObject(i);
                        borrowerpermanentStatePersonalPOJOCoBr.stateName = mJsonti.getString("state_name");
                        permanentstate_arrayListCoBr.add(mJsonti.getString("state_name"));
                        borrowerpermanentStatePersonalPOJOCoBr.stateID = mJsonti.getString("state_id");
                        borrowerPermanentStatePersonalPOJOArrayListCoBr.add(borrowerpermanentStatePersonalPOJOCoBr);
                    }
                    arrayAdapter_permanentStateCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentstate_arrayListCoBr);
                    spPermanentStateCoBr.setAdapter(arrayAdapter_permanentStateCoBr);
                    arrayAdapter_permanentStateCoBr.notifyDataSetChanged();
                }

                if (jsonData.getJSONArray("cocities").length() > 0) {
                    JSONArray jsonArraycocities = jsonData.getJSONArray("cocities");

                    //Current City CoBr
                    currentcity_arrayListCoBr = new ArrayList<>();
                    borrowerCurrentCityPersonalPOJOArrayListCoBr = new ArrayList<>();
                    currentcity_arrayListCoBr.add("Select Any");
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

                    //Permanent City CoBr
                    permanentcity_arrayListCoBr = new ArrayList<>();
                    borrowerPermanentCityPersonalPOJOArrayListCoBr = new ArrayList<>();
                    permanentcity_arrayListCoBr.add("Select Any");
                    for (int i = 0; i < jsonArraycocities.length(); i++) {
                        BorrowerPermanentCityPersonalPOJO borrowerpermanentCityPersonalPOJOCoBr = new BorrowerPermanentCityPersonalPOJO();
                        JSONObject mJsonti = jsonArraycocities.getJSONObject(i);
                        borrowerpermanentCityPersonalPOJOCoBr.cityName = mJsonti.getString("city_name");
                        permanentcity_arrayListCoBr.add(mJsonti.getString("city_name"));
                        borrowerpermanentCityPersonalPOJOCoBr.cityID = mJsonti.getString("city_id");
                        borrowerPermanentCityPersonalPOJOArrayListCoBr.add(borrowerpermanentCityPersonalPOJOCoBr);
                    }
                    arrayAdapter_permanentCityCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayListCoBr);
                    spPermanentCityCoBr.setAdapter(arrayAdapter_permanentCityCoBr);
                    arrayAdapter_permanentCityCoBr.notifyDataSetChanged();

                    int count1 = borrowerPermanentCityPersonalPOJOArrayListCoBr.size();
                    for (int i = 0; i < count1; i++) {
                        if (borrowerPermanentCityPersonalPOJOArrayListCoBr.get(i).cityID.equalsIgnoreCase(permanentcityIDCoBr)) {
                            spPermanentCityCoBr.setSelection(i);
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

                MainApplication.lead_iddtl = lead_id = jsondetailedInformation.getString("lead_id");
                MainApplication.application_iddtl = application_id = jsondetailedInformation.getString("application_id");
                MainApplication.requested_loan_amountdtl = requested_loan_amount = jsondetailedInformation.getString("requested_loan_amount");
                MainApplication.institute_namedtl = institute_name = jsondetailedInformation.getString("institute_name");
                MainApplication.location_namedtl = location_name = jsondetailedInformation.getString("location_name");
                MainApplication.course_namedtl = course_name = jsondetailedInformation.getString("course_name");
                MainApplication.course_costdtl = course_cost = jsondetailedInformation.getString("course_cost");
                MainApplication.has_coborrowerdtl = has_coborrower = jsondetailedInformation.getString("has_coborrower");
                MainApplication.Brapplicant_iddtl = Brapplicant_id = jsonborrowerDetails.getString("applicant_id");
                MainApplication.Brfk_lead_iddtl = Brfk_lead_id = jsonborrowerDetails.getString("fk_lead_id");
                MainApplication.Brfk_applicant_type_iddtl = Brfk_applicant_type_id = jsonborrowerDetails.getString("fk_applicant_type_id");

                if (!jsonborrowerDetails.getString("first_name").toString().equals("null"))
                    MainApplication.Brfirst_namedtl = Brfirst_name = jsonborrowerDetails.getString("first_name");
                if (!jsonborrowerDetails.getString("first_name").toString().equals("null"))
                    MainApplication.Brfirst_namedtl = Brfirst_name = jsonborrowerDetails.getString("first_name");
                if (!jsonborrowerDetails.getString("middle_name").toString().equals("null"))
                    MainApplication.Brmiddle_namedtl = Brmiddle_name = jsonborrowerDetails.getString("middle_name");
                if (!jsonborrowerDetails.getString("last_name").toString().equals("null"))
                    MainApplication.Brlast_namedtl = Brlast_name = jsonborrowerDetails.getString("last_name");
                if (!jsonborrowerDetails.getString("has_aadhar_pan").toString().equals("null"))
                    MainApplication.Brhas_aadhar_pandtl = Brhas_aadhar_pan = jsonborrowerDetails.getString("has_aadhar_pan");
                if (!jsonborrowerDetails.getString("dob").toString().equals("null"))
                    MainApplication.Brdobdtl = Brdob = jsonborrowerDetails.getString("dob");
                if (!jsonborrowerDetails.getString("pan_number").toString().equals("null"))
                    MainApplication.Brpan_numberdtl = Brpan_number = jsonborrowerDetails.getString("pan_number");
                if (!jsonborrowerDetails.getString("aadhar_number").toString().equals("null"))
                    MainApplication.Braadhar_numberdtl = Braadhar_number = jsonborrowerDetails.getString("aadhar_number");
                if (!jsonborrowerDetails.getString("marital_status").toString().equals("null"))
                    MainApplication.Brmarital_statusdtl = Brmarital_status = jsonborrowerDetails.getString("marital_status");
                if (!jsonborrowerDetails.getString("gender_id").toString().equals("null"))
                    MainApplication.Brgender_iddtl = Brgender_id = jsonborrowerDetails.getString("gender_id");
                if (!jsonborrowerDetails.getString("mobile_number").toString().equals("null"))
                    MainApplication.Brmobile_numberdtl = Brmobile_number = jsonborrowerDetails.getString("mobile_number");
                if (!jsonborrowerDetails.getString("email_id").toString().equals("null"))
                    MainApplication.Bremail_iddtl = Bremail_id = jsonborrowerDetails.getString("email_id");
                if (!jsonborrowerDetails.getString("relationship_with_applicant").toString().equals("null"))
                    MainApplication.Brrelationship_with_applicantdtl = Brrelationship_with_applicant = jsonborrowerDetails.getString("relationship_with_applicant");
                if (!jsonborrowerDetails.getString("profession").toString().equals("null"))
                    MainApplication.Brprofessiondtl = Brprofession = jsonborrowerDetails.getString("profession");
                if (!jsonborrowerDetails.getString("employer_type").toString().equals("null"))
                    MainApplication.Bremployer_typedtl = Bremployer_type = jsonborrowerDetails.getString("employer_type");
                if (!jsonborrowerDetails.getString("employer_name").toString().equals("null"))
                    MainApplication.Bremployer_namedtl = Bremployer_name = jsonborrowerDetails.getString("employer_name");
                if (!jsonborrowerDetails.getString("annual_income").toString().equals("null"))
                    MainApplication.Brannual_incomedtl = Brannual_income = jsonborrowerDetails.getString("annual_income");
                if (!jsonborrowerDetails.getString("current_employment_duration").toString().equals("null"))
                    MainApplication.Brcurrent_employment_durationdtl = Brcurrent_employment_duration = jsonborrowerDetails.getString("current_employment_duration");
                if (!jsonborrowerDetails.getString("total_employement_duration").toString().equals("null"))
                    MainApplication.Brtotal_employement_durationdtl = Brtotal_employement_duration = jsonborrowerDetails.getString("total_employement_duration");
                if (!jsonborrowerDetails.getString("employer_mobile_number").toString().equals("null"))
                    MainApplication.Bremployer_mobile_numberdtl = Bremployer_mobile_number = jsonborrowerDetails.getString("employer_mobile_number");
                if (!jsonborrowerDetails.getString("employer_landline_number").toString().equals("null"))
                    MainApplication.Bremployer_landline_numberdtl = Bremployer_landline_number = jsonborrowerDetails.getString("employer_landline_number");
                if (!jsonborrowerDetails.getString("office_landmark").toString().equals("null"))
                    MainApplication.Broffice_landmarkdtl = Broffice_landmark = jsonborrowerDetails.getString("office_landmark");
                if (!jsonborrowerDetails.getString("office_address").toString().equals("null"))
                    MainApplication.Broffice_addressdtl = Broffice_address = jsonborrowerDetails.getString("office_address");
                if (!jsonborrowerDetails.getString("office_address_city").toString().equals("null"))
                    MainApplication.Broffice_address_citydtl = Broffice_address_city = jsonborrowerDetails.getString("office_address_city");
                if (!jsonborrowerDetails.getString("office_address_state").toString().equals("null"))
                    MainApplication.Broffice_address_statedtl = Broffice_address_state = jsonborrowerDetails.getString("office_address_state");
                if (!jsonborrowerDetails.getString("office_address_country").toString().equals("null"))
                    MainApplication.Broffice_address_countrydtl = Broffice_address_country = jsonborrowerDetails.getString("office_address_country");
                if (!jsonborrowerDetails.getString("office_address_pin").toString().equals("null"))
                    MainApplication.Broffice_address_pindtl = Broffice_address_pin = jsonborrowerDetails.getString("office_address_pin");
                if (!jsonborrowerDetails.getString("has_active_loan").toString().equals("null"))
                    MainApplication.Brhas_active_loandtl = Brhas_active_loan = jsonborrowerDetails.getString("has_active_loan");
                if (!jsonborrowerDetails.getString("EMI_Amount").toString().equals("null"))
                    MainApplication.BrEMI_Amountdtl = BrEMI_Amount = jsonborrowerDetails.getString("EMI_Amount");
                if (!jsonborrowerDetails.getString("kyc_landmark").toString().equals("null"))
                    MainApplication.Brkyc_landmarkdtl = Brkyc_landmark = jsonborrowerDetails.getString("kyc_landmark");
                if (!jsonborrowerDetails.getString("kyc_address").toString().equals("null"))
                    MainApplication.Brkyc_addressdtl = Brkyc_address = jsonborrowerDetails.getString("kyc_address");
                if (!jsonborrowerDetails.getString("kyc_address_city").toString().equals("null"))
                    MainApplication.Brkyc_address_citydtl = Brkyc_address_city = jsonborrowerDetails.getString("kyc_address_city");
                if (!jsonborrowerDetails.getString("kyc_address_state").toString().equals("null"))
                    MainApplication.Brkyc_address_statedtl = Brkyc_address_state = jsonborrowerDetails.getString("kyc_address_state");
                if (!jsonborrowerDetails.getString("kyc_address_country").toString().equals("null"))
                    MainApplication.Brkyc_address_countrydtl = Brkyc_address_country = jsonborrowerDetails.getString("kyc_address_country");
                if (!jsonborrowerDetails.getString("kyc_address_pin").toString().equals("null"))
                    MainApplication.Brkyc_address_pindtl = Brkyc_address_pin = jsonborrowerDetails.getString("kyc_address_pin");
                if (!jsonborrowerDetails.getString("is_borrower_current_address_same_as").toString().equals("null"))
                    MainApplication.Bris_borrower_current_address_same_asdtl = Bris_borrower_current_address_same_as = jsonborrowerDetails.getString("is_borrower_current_address_same_as");
                if (!jsonborrowerDetails.getString("is_coborrower_current_address_same_as").toString().equals("null"))
                    MainApplication.Bris_coborrower_current_address_same_asdtl = Bris_coborrower_current_address_same_as = jsonborrowerDetails.getString("is_coborrower_current_address_same_as");
                if (!jsonborrowerDetails.getString("current_residence_type").toString().equals("null"))
                    MainApplication.Brcurrent_residence_typedtl = Brcurrent_residence_type = jsonborrowerDetails.getString("current_residence_type");
                if (!jsonborrowerDetails.getString("current_landmark").toString().equals("null"))
                    MainApplication.Brcurrent_landmarkdtl = Brcurrent_landmark = jsonborrowerDetails.getString("current_landmark");
                if (!jsonborrowerDetails.getString("current_address").toString().equals("null"))
                    MainApplication.Brcurrent_addressdtl = Brcurrent_address = jsonborrowerDetails.getString("current_address");
                if (!jsonborrowerDetails.getString("current_address_city").toString().equals("null"))
                    MainApplication.Brcurrent_address_citydtl = Brcurrent_address_city = jsonborrowerDetails.getString("current_address_city");
                if (!jsonborrowerDetails.getString("current_address_state").toString().equals("null"))
                    MainApplication.Brcurrent_address_statedtl = Brcurrent_address_state = jsonborrowerDetails.getString("current_address_state");
                if (!jsonborrowerDetails.getString("current_address_country").toString().equals("null"))
                    MainApplication.Brcurrent_address_countrydtl = Brcurrent_address_country = jsonborrowerDetails.getString("current_address_country");
                if (!jsonborrowerDetails.getString("current_address_pin").toString().equals("null"))
                    MainApplication.Brcurrent_address_pindtl = Brcurrent_address_pin = jsonborrowerDetails.getString("current_address_pin");
                if (!jsonborrowerDetails.getString("current_address_rent").toString().equals("null"))
                    MainApplication.Brcurrent_address_rentdtl = Brcurrent_address_rent = jsonborrowerDetails.getString("current_address_rent");
                if (!jsonborrowerDetails.getString("current_address_stay_duration").toString().equals("null"))
                    MainApplication.Brcurrent_address_stay_durationdtl = Brcurrent_address_stay_duration = jsonborrowerDetails.getString("current_address_stay_duration");
                if (!jsonborrowerDetails.getString("is_borrower_permanent_address_same_as").toString().equals("null"))
                    MainApplication.Bris_borrower_permanent_address_same_asdtl = Bris_borrower_permanent_address_same_as = jsonborrowerDetails.getString("is_borrower_permanent_address_same_as");


                if (!jsonborrowerDetails.getString("permanent_residence_type").toString().equals("null"))
                    MainApplication.Brpermanent_residence_typedtl = Brpermanent_residence_type = jsonborrowerDetails.getString("permanent_residence_type");
                if (!jsonborrowerDetails.getString("permanent_landmark").toString().equals("null"))
                    MainApplication.Brpermanent_landmarkdtl = Brpermanent_landmark = jsonborrowerDetails.getString("permanent_landmark");
                if (!jsonborrowerDetails.getString("permanent_address").toString().equals("null"))
                    MainApplication.Brpermanent_addressdtl = Brpermanent_address = jsonborrowerDetails.getString("permanent_address");
                if (!jsonborrowerDetails.getString("permanent_address_city").toString().equals("null"))
                    MainApplication.Brpermanent_address_citydtl = Brpermanent_address_city = jsonborrowerDetails.getString("permanent_address_city");
                if (!jsonborrowerDetails.getString("permanent_address_state").toString().equals("null"))
                    MainApplication.Brpermanent_address_statedtl = Brpermanent_address_state = jsonborrowerDetails.getString("permanent_address_state");
                if (!jsonborrowerDetails.getString("permanent_address_country").toString().equals("null"))
                    MainApplication.Brpermanent_address_countrydtl = Brpermanent_address_country = jsonborrowerDetails.getString("permanent_address_country");
                if (!jsonborrowerDetails.getString("permanent_address_pin").toString().equals("null"))
                    MainApplication.Brpermanent_address_pindtl = Brpermanent_address_pin = jsonborrowerDetails.getString("permanent_address_pin");
                if (!jsonborrowerDetails.getString("permanent_address_rent").toString().equals("null"))
                    MainApplication.Brpermanent_address_rentdtl = Brpermanent_address_rent = jsonborrowerDetails.getString("permanent_address_rent");
                if (!jsonborrowerDetails.getString("permanent_address_stay_duration").toString().equals("null"))
                    MainApplication.Brpermanent_address_stay_durationdtl = Brpermanent_address_stay_duration = jsonborrowerDetails.getString("permanent_address_stay_duration");
                if (!jsonborrowerDetails.getString("last_completed_degree").toString().equals("null"))
                    MainApplication.Brlast_completed_degreedtl = Brlast_completed_degree = jsonborrowerDetails.getString("last_completed_degree");
                if (!jsonborrowerDetails.getString("score_unit").toString().equals("null"))
                    MainApplication.Brscore_unitdtl = Brscore_unit = jsonborrowerDetails.getString("score_unit");
                if (!jsonborrowerDetails.getString("cgpa").toString().equals("null"))
                    MainApplication.Brcgpadtl = Brcgpa = jsonborrowerDetails.getString("cgpa");
                if (!jsonborrowerDetails.getString("percentage").toString().equals("null"))
                    MainApplication.Brpercentagedtl = Brpercentage = jsonborrowerDetails.getString("percentage");
                if (!jsonborrowerDetails.getString("passing_year").toString().equals("null"))
                    MainApplication.Brpassing_yeardtl = Brpassing_year = jsonborrowerDetails.getString("passing_year");
                if (!jsonborrowerDetails.getString("gap_in_education").toString().equals("null"))
                    MainApplication.Brgap_in_educationdtl = Brgap_in_education = jsonborrowerDetails.getString("gap_in_education");
                if (!jsonborrowerDetails.getString("full_name_pan_response").toString().equals("null"))
                    MainApplication.Brfull_name_pan_responsedtl = Brfull_name_pan_response = jsonborrowerDetails.getString("full_name_pan_response");
                if (!jsonborrowerDetails.getString("created_by_id").toString().equals("null"))
                    MainApplication.Brcreated_by_iddtl = Brcreated_by_id = jsonborrowerDetails.getString("created_by_id");
                if (!jsonborrowerDetails.getString("created_date_time").toString().equals("null"))
                    MainApplication.Brcreated_date_timedtl = Brcreated_date_time = jsonborrowerDetails.getString("created_date_time");
                if (!jsonborrowerDetails.getString("created_ip_address").toString().equals("null"))
                    MainApplication.Brcreated_ip_addressdtl = Brcreated_ip_address = jsonborrowerDetails.getString("created_ip_address");
                if (!jsonborrowerDetails.getString("modified_by").toString().equals("null"))
                    MainApplication.Brmodified_bydtl = Brmodified_by = jsonborrowerDetails.getString("modified_by");
                if (!jsonborrowerDetails.getString("modified_date_time").toString().equals("null"))
                    MainApplication.Brmodified_date_timedtl = Brmodified_date_time = jsonborrowerDetails.getString("modified_date_time");
                if (!jsonborrowerDetails.getString("modified_ip_address").toString().equals("null"))
                    MainApplication.Brmodified_ip_addressdtl = Brmodified_ip_address = jsonborrowerDetails.getString("modified_ip_address");
                if (!jsonborrowerDetails.getString("is_deleted").toString().equals("null"))
                    MainApplication.Bris_deleteddtl = Bris_deleted = jsonborrowerDetails.getString("is_deleted");


                edtFnameBr.setText(Brfirst_name);
                edtMnameBr.setText(Brmiddle_name);
                edtLnameBr.setText(Brlast_name);
                edtAadhaarBr.setText(Braadhar_number);
                edtPanBr.setText(Brpan_number);
                txtBirthdateBr.setText(Brdob);

                if (!Brcurrent_address_stay_duration.equals("")){
                    try {
                        spDurationStayAtCurrentAddressBr.setSelection(Integer.parseInt(Brcurrent_address_stay_duration));
                    }catch (Exception e)
                    {e.printStackTrace();}
                }

                edtMonthlyRentBr.setText(Brcurrent_address_rent);
                if (Brgender_id.equals("1")) rbMaleBr.setChecked(true);
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

                if (!Brgap_in_education.equals("")) {
                    if (Brgap_in_education.equals("1"))
                        rgborrower_gapsBr.check(R.id.radioButton_gaps_yes_borrower);
                    else rgborrower_gapsBr.check(R.id.radioButton_gaps_no_borrower);
                }

                if (!Brprofession.equals("")){
                    try{
                    spProfessionBr.setSelection(Integer.parseInt(Brprofession));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }


                if (!Bremployer_type.equals("")) {
                    if (Bremployer_type.equals("0"))
                        rgemptypeBr.check(R.id.rbempType_privateBr);
                    else rgemptypeBr.check(R.id.rbempType_govBr);
                }

                if (!Brcurrent_employment_duration.equals("")){
                    try{
                        spdurationofjobBr.setSelection(Integer.parseInt(Brcurrent_employment_duration));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                if (!Broffice_address_country.equals("")){
                    try{
                        spCountryOffBr.setSelection(Integer.parseInt(Broffice_address_country));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                if (!Brscore_unit.equals("")) {
                    if (Brscore_unit.equals("1")) rgiscgpaBr.check(R.id.rbiscgpa_yesBr);
                    else rgiscgpaBr.check(R.id.rbiscgpa_noBr);
                }




                if (Bris_borrower_current_address_same_as.equals("")) {
                    MainApplication.Bris_borrower_current_address_same_asdtl = "0";
                    spCurrentAddressSameAsKycBr.setSelection(0);
                } else {
                    MainApplication.Bris_borrower_current_address_same_asdtl = Bris_borrower_current_address_same_as;
                    spCurrentAddressSameAsKycBr.setSelection(Integer.parseInt(Bris_borrower_current_address_same_as));
                }

                if (Bris_borrower_permanent_address_same_as.equals("")) {
                    MainApplication.Bris_borrower_permanent_address_same_asdtl = "0";
                    spCurrentAddressSameAsKycOrBorrowerBr.setSelection(0);
                } else {
                    MainApplication.Bris_borrower_permanent_address_same_asdtl = Bris_borrower_permanent_address_same_as;
                    spCurrentAddressSameAsKycOrBorrowerBr.setSelection(Integer.parseInt(MainApplication.Bris_borrower_permanent_address_same_asdtl));
                }

                if (!jsoncoborrowerDetails.getString("relationship_with_applicant").toString().equals("null"))
                    MainApplication.CoBrrelationship_with_applicantdtl = CoBrrelationship_with_applicant = jsoncoborrowerDetails.getString("relationship_with_applicant");

                if (!jsoncoborrowerDetails.getString("applicant_id").toString().equals("null"))
                    MainApplication.CoBrapplicant_iddtl = CoBrapplicant_id = jsoncoborrowerDetails.getString("applicant_id");
                if (!jsoncoborrowerDetails.getString("fk_applicant_type_id").toString().equals("null"))
                    MainApplication.CoBrfk_applicant_type_iddtl = CoBrfk_applicant_type_id = jsoncoborrowerDetails.getString("fk_applicant_type_id");
                if (!jsoncoborrowerDetails.getString("first_name").toString().equals("null"))
                    MainApplication.CoBrfirst_namedtl = CoBrfirst_name = jsoncoborrowerDetails.getString("first_name");
                if (!jsoncoborrowerDetails.getString("middle_name").toString().equals("null"))
                    MainApplication.CoBrmiddle_namedtl = CoBrmiddle_name = jsoncoborrowerDetails.getString("middle_name");
                if (!jsoncoborrowerDetails.getString("last_name").toString().equals("null"))
                    MainApplication.CoBrlast_namedtl = CoBrlast_name = jsoncoborrowerDetails.getString("last_name");
                if (!jsoncoborrowerDetails.getString("dob").toString().equals("null"))
                    MainApplication.CoBrdobdtl = CoBrdob = jsoncoborrowerDetails.getString("dob");
                if (!jsoncoborrowerDetails.getString("gender_id").toString().equals("null"))
                    MainApplication.CoBrgender_iddtl = CoBrgender_id = jsoncoborrowerDetails.getString("gender_id");

                if (!jsoncoborrowerDetails.getString("mobile_number").toString().equals("null"))
                    MainApplication.CoBrmobile_numberdtl = CoBrmobile_number = jsoncoborrowerDetails.getString("mobile_number");

                if (!jsoncoborrowerDetails.getString("email_id").toString().equals("null"))
                    MainApplication.CoBremail_iddtl = CoBremail_id = jsoncoborrowerDetails.getString("email_id");

                if (!jsoncoborrowerDetails.getString("has_aadhar_pan").toString().equals("null"))
                    MainApplication.CoBrhas_aadhar_pandtl = CoBrhas_aadhar_pan = jsoncoborrowerDetails.getString("has_aadhar_pan");
                if (!jsoncoborrowerDetails.getString("pan_number").toString().equals("null"))
                    MainApplication.CoBrpan_numberdtl = CoBrpan_number = jsoncoborrowerDetails.getString("pan_number");
                if (!jsoncoborrowerDetails.getString("aadhar_number").toString().equals("null"))
                    MainApplication.CoBraadhar_numberdtl = CoBraadhar_number = jsoncoborrowerDetails.getString("aadhar_number");
                if (!jsoncoborrowerDetails.getString("marital_status").toString().equals("null"))
                    MainApplication.CoBrmarital_statusdtl = CoBrmarital_status = jsoncoborrowerDetails.getString("marital_status");
                if (!jsoncoborrowerDetails.getString("mobile_number").toString().equals("null"))
                    MainApplication.CoBrmobile_numberdtl = CoBrmobile_number = jsoncoborrowerDetails.getString("mobile_number");
                if (!jsoncoborrowerDetails.getString("relationship_with_applicant").toString().equals("null"))
                    MainApplication.CoBrrelationship_with_applicantdtl = CoBrrelationship_with_applicant = jsoncoborrowerDetails.getString("relationship_with_applicant");

                if (!jsoncoborrowerDetails.getString("profession").toString().equals("null"))
                    MainApplication.CoBrprofessiondtl = CoBrprofession = jsoncoborrowerDetails.getString("profession");

                if (!jsoncoborrowerDetails.getString("employer_type").toString().equals("null"))
                    MainApplication.CoBremployer_typedtl = CoBremployer_type = jsoncoborrowerDetails.getString("employer_type");
                if (!jsoncoborrowerDetails.getString("employer_name").toString().equals("null"))
                    MainApplication.CoBremployer_namedtl = CoBremployer_name = jsoncoborrowerDetails.getString("employer_name");
                if (!jsoncoborrowerDetails.getString("current_employment_duration").toString().equals("null"))
                    MainApplication.CoBrtotal_employement_durationdtl = CoBrtotal_employement_duration = jsoncoborrowerDetails.getString("current_employment_duration");


                if (!jsoncoborrowerDetails.getString("annual_income").toString().equals("null"))
                    MainApplication.CoBrannual_incomedtl = CoBrannual_income = jsoncoborrowerDetails.getString("annual_income");
                if (!jsoncoborrowerDetails.getString("total_employement_duration").toString().equals("null"))
                    MainApplication.CoBrtotal_employement_durationdtl = CoBrtotal_employement_duration = jsoncoborrowerDetails.getString("total_employement_duration");
                if (!jsoncoborrowerDetails.getString("employer_landline_number").toString().equals("null"))
                    MainApplication.CoBremployer_landline_numberdtl = CoBremployer_landline_number = jsoncoborrowerDetails.getString("employer_landline_number");
                if (!jsoncoborrowerDetails.getString("office_address").toString().equals("null"))
                    MainApplication.CoBroffice_addressdtl = CoBroffice_address = jsoncoborrowerDetails.getString("office_address");
                if (!jsoncoborrowerDetails.getString("office_address_state").toString().equals("null"))
                    MainApplication.CoBroffice_address_statedtl = CoBroffice_address_state = offstateIDCoBr = jsoncoborrowerDetails.getString("office_address_state");
                if (!jsoncoborrowerDetails.getString("office_address_pin").toString().equals("null"))
                    MainApplication.CoBroffice_address_pindtl = CoBroffice_address_pin = jsoncoborrowerDetails.getString("office_address_pin");
                if (!jsoncoborrowerDetails.getString("office_address_country").toString().equals("null"))
                    MainApplication.CoBroffice_address_countrydtl = CoBroffice_address_country = jsoncoborrowerDetails.getString("office_address_country");
                if (!jsoncoborrowerDetails.getString("office_address_city").toString().equals("null"))
                    MainApplication.CoBroffice_address_citydtl = CoBroffice_address_city = offcityIDCoBr = jsoncoborrowerDetails.getString("office_address_city");

                if (!jsoncoborrowerDetails.getString("EMI_Amount").toString().equals("null"))
                    MainApplication.CoBrEMI_Amountdtl = CoBrEMI_Amount = jsoncoborrowerDetails.getString("EMI_Amount");
                if (!jsoncoborrowerDetails.getString("kyc_address").toString().equals("null"))
                    MainApplication.CoBrkyc_addressdtl = CoBrkyc_address = jsoncoborrowerDetails.getString("kyc_address");
                if (!jsoncoborrowerDetails.getString("kyc_address_state").toString().equals("null"))
                    MainApplication.CoBrkyc_address_statedtl = CoBrkyc_address_state = jsoncoborrowerDetails.getString("kyc_address_state");

                if (!jsoncoborrowerDetails.getString("kyc_address_city").toString().equals("null"))
                    MainApplication.CoBrkyc_address_citydtl = CoBrkyc_address_city = jsoncoborrowerDetails.getString("kyc_address_city");

                if (!jsoncoborrowerDetails.getString("kyc_address_pin").toString().equals("null"))
                    MainApplication.CoBrkyc_address_pindtl = CoBrkyc_address_pin = jsoncoborrowerDetails.getString("kyc_address_pin");
                if (!jsoncoborrowerDetails.getString("is_coborrower_current_address_same_as").toString().equals("null"))
                    MainApplication.CoBris_coborrower_current_address_same_asdtl = CoBris_coborrower_current_address_same_as = jsoncoborrowerDetails.getString("is_coborrower_current_address_same_as");


                if (!jsoncoborrowerDetails.getString("current_residence_type").toString().equals("null"))
                    MainApplication.CoBrcurrent_residence_typedtl = CoBrcurrent_residence_type = jsoncoborrowerDetails.getString("current_residence_type");

                if (!jsoncoborrowerDetails.getString("current_landmark").toString().equals("null"))
                    MainApplication.CoBrcurrent_landmarkdtl = CoBrcurrent_landmark = jsoncoborrowerDetails.getString("current_landmark");
                if (!jsoncoborrowerDetails.getString("current_address_city").toString().equals("null"))
                    MainApplication.CoBrcurrent_address_citydtl = CoBrcurrent_address_city = jsoncoborrowerDetails.getString("current_address_city");
                if (!jsoncoborrowerDetails.getString("current_address_country").toString().equals("null"))
                    MainApplication.CoBrcurrent_address_countrydtl = CoBrcurrent_address_country = jsoncoborrowerDetails.getString("current_address_country");

                if (!jsoncoborrowerDetails.getString("current_address_state").toString().equals("null"))
                    MainApplication.CoBrcurrent_address_statedtl = CoBrcurrent_address_state = jsoncoborrowerDetails.getString("current_address_state");

                if (!jsoncoborrowerDetails.getString("current_address_rent").toString().equals("null"))
                    MainApplication.CoBrcurrent_address_rentdtl = CoBrcurrent_address_rent = jsoncoborrowerDetails.getString("current_address_rent");
                if (!jsoncoborrowerDetails.getString("current_address_pin").toString().equals("null"))
                    MainApplication.CoBrcurrent_address_pindtl = CoBrcurrent_address_pin = jsoncoborrowerDetails.getString("current_address_pin");
                if (!jsoncoborrowerDetails.getString("current_address_stay_duration").toString().equals("null"))
                    MainApplication.CoBrcurrent_address_stay_durationdtl = CoBrcurrent_address_stay_duration = jsoncoborrowerDetails.getString("current_address_stay_duration");

                if (!jsoncoborrowerDetails.getString("is_coborrower_permanent_address_same_as").toString().equals("null"))
                    MainApplication.CoBris_coborrower_permanent_address_same_asdtl = CoBris_coborrower_permanent_address_same_as = jsoncoborrowerDetails.getString("is_coborrower_permanent_address_same_as");

                if (!jsoncoborrowerDetails.getString("permanent_residence_type").toString().equals("null"))
                    MainApplication.CoBrpermanent_residence_typedtl = CoBrpermanent_residence_type = jsoncoborrowerDetails.getString("permanent_residence_type");
                if (!jsoncoborrowerDetails.getString("permanent_address").toString().equals("null"))
                    MainApplication.CoBrpermanent_addressdtl = CoBrpermanent_address = jsoncoborrowerDetails.getString("permanent_address");
                if (!jsoncoborrowerDetails.getString("permanent_address_state").toString().equals("null"))
                    MainApplication.CoBrpermanent_address_statedtl = CoBrpermanent_address_state = jsoncoborrowerDetails.getString("permanent_address_state");

                if (!jsoncoborrowerDetails.getString("permanent_address_country").toString().equals("null"))
                    MainApplication.CoBrpermanent_address_countrydtl = CoBrpermanent_address_country = jsoncoborrowerDetails.getString("permanent_address_country");
                if (!jsoncoborrowerDetails.getString("permanent_address_city").toString().equals("null"))
                    MainApplication.CoBrpermanent_address_citydtl = CoBrpermanent_address_city = jsoncoborrowerDetails.getString("permanent_address_city");


                if (!jsoncoborrowerDetails.getString("permanent_address_pin").toString().equals("null"))
                    MainApplication.CoBrpermanent_address_pindtl = CoBrpermanent_address_pin = jsoncoborrowerDetails.getString("permanent_address_pin");
                if (!jsoncoborrowerDetails.getString("permanent_address_stay_duration").toString().equals("null"))
                    MainApplication.CoBrpermanent_address_stay_durationdtl = CoBrpermanent_address_stay_duration = jsoncoborrowerDetails.getString("permanent_address_stay_duration");
                if (!jsoncoborrowerDetails.getString("score_unit").toString().equals("null"))
                    MainApplication.CoBrscore_unitdtl = CoBrscore_unit = jsoncoborrowerDetails.getString("score_unit");
                if (!jsoncoborrowerDetails.getString("percentage").toString().equals("null"))
                    MainApplication.CoBrpercentagedtl = CoBrpercentage = jsoncoborrowerDetails.getString("percentage");
                if (!jsoncoborrowerDetails.getString("gap_in_education").toString().equals("null"))
                    MainApplication.CoBrgap_in_educationdtl = CoBrgap_in_education = jsoncoborrowerDetails.getString("gap_in_education");
                if (!jsoncoborrowerDetails.getString("created_by_id").toString().equals("null"))
                    MainApplication.CoBrcreated_by_iddtl = CoBrcreated_by_id = jsoncoborrowerDetails.getString("created_by_id");
                if (!jsoncoborrowerDetails.getString("created_ip_address").toString().equals("null"))
                    MainApplication.CoBrcreated_ip_addressdtl = CoBrcreated_ip_address = jsoncoborrowerDetails.getString("created_ip_address");
                if (!jsoncoborrowerDetails.getString("modified_date_time").toString().equals("null"))
                    MainApplication.CoBrmodified_date_timedtl = CoBrmodified_date_time = jsoncoborrowerDetails.getString("modified_date_time");
                if (!jsoncoborrowerDetails.getString("is_deleted").toString().equals("null"))
                    MainApplication.CoBris_deleteddtl = CoBris_deleted = jsoncoborrowerDetails.getString("is_deleted");


                edtFnameCoBr.setText(CoBrfirst_name);
                edtMnameCoBr.setText(CoBrmiddle_name);
                edtLnameCoBr.setText(CoBrlast_name);
                txtBirthdateCoBr.setText(CoBrdob);
                edtMobileCoBr.setText(CoBrmobile_number);
                edtEmailCoBr.setText(CoBremail_id);
                if (!CoBrgender_id.equals(""))
                    if (CoBrgender_id.equals("1")) rgGenderCoBr.check(R.id.rbMaleCoBr);
                    else rgGenderCoBr.check(R.id.rbFemaleCoBr);
                if (!CoBrmarital_status.equals(""))
                    if (CoBrmarital_status.equals("1"))
                        rgMaritalStatusCoBr.check(R.id.rbMarriedCoBr);
                    else rgMaritalStatusCoBr.check(R.id.rbSingleCoBr);

                if (!CoBremployer_type.equals(""))
                    if (CoBremployer_type.equals("0"))
                        rgemptypeCoBr.check(R.id.rbempType_privateCoBr);
                    else rgemptypeCoBr.check(R.id.rbempType_govCoBr);

                if (!CoBrcurrent_residence_type.equals("")) {
                    try {
                        spResidentTypeCoBr.setSelection((Integer.parseInt(CoBrcurrent_residence_type) + 1));
                    } catch (Exception e) {
                        e.printStackTrace();
                        spResidentTypeCoBr.setSelection(0);
                    }
                }

                if (!CoBris_coborrower_current_address_same_as.equals("")) {
                    if (Integer.parseInt(CoBris_coborrower_current_address_same_as) < spResidentTypeCoBr.getAdapter().getCount())
                        spCurrentAddressSameAsKycCoBr.setSelection(Integer.parseInt(CoBris_coborrower_current_address_same_as));
                    else
                        spCurrentAddressSameAsKycCoBr.setSelection(0);
                }

                if (!CoBrcurrent_address_stay_duration.equals("")) {
                    if (Integer.parseInt(CoBrcurrent_address_stay_duration) < spDurationStayAtCurrentAddressCoBr.getAdapter().getCount())
                        spDurationStayAtCurrentAddressCoBr.setSelection(Integer.parseInt(CoBrcurrent_address_stay_duration));
                    else spDurationStayAtCurrentAddressCoBr.setSelection(0);
                }

                edtMonthlyRentCoBr.setText(CoBrcurrent_address_rent);
                edtPanCoBr.setText(CoBrpan_number);
                edtAadhaarCoBr.setText(CoBraadhar_number);
                edtCompanyCoBr.setText(CoBremployer_name);
                edtAnnualSalCoBr.setText(CoBrannual_income);

                if (!CoBrprofession.equals("")) {
                    try {
                        spProfessionCoBr.setSelection(Integer.parseInt(CoBrprofession));
                    } catch (Exception e) {
                        e.printStackTrace();
                        spProfessionCoBr.setSelection(0);
                    }
                }

                if (!CoBrtotal_employement_duration.equals("")) {
                    if (Integer.parseInt(CoBrtotal_employement_duration) < spdurationofjobCoBr.getAdapter().getCount())
                        spdurationofjobCoBr.setSelection(Integer.parseInt(CoBrtotal_employement_duration));
                    else
                        spdurationofjobCoBr.setSelection(0);
                }

                if (!CoBroffice_address_country.equals("")) {
                    try {
                        spCountryOffCoBr.setSelection(Integer.parseInt(CoBroffice_address_country));
                    } catch (Exception e) {
                        e.printStackTrace();
                        spCountryOffCoBr.setSelection(0);
                    }
                }

                if (!CoBroffice_address_state.equals("")) {
                    try {
                        spStateOffCoBr.setSelection(Integer.parseInt(CoBroffice_address_state));
                    } catch (Exception e) {
                        e.printStackTrace();
                        spStateOffCoBr.setSelection(0);
                    }
                }

                if (!CoBroffice_address_city.equals("")) {
                    try {
                        spCountryOffCoBr.setSelection(Integer.parseInt(CoBroffice_address_city));
                    } catch (Exception e) {
                        e.printStackTrace();
                        spCountryOffCoBr.setSelection(0);
                    }
                }


                edtPincodeOffCoBr.setText(CoBroffice_address_pin);
                edtAddressOffCoBr.setText(CoBroffice_address);
                edtCurrentPincodeCoBr.setText(CoBrcurrent_address_pin);
                edtCurrentAddressCoBr.setText(CoBrcurrent_address);
                edtPermanentPincodeCoBr.setText(CoBrpermanent_address_pin);
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

    private void setViews() {

        try {

            btnEdit = view.findViewById(R.id.btnEdit);
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
            edtPermanentAddressBr = (EditText) view.findViewById(R.id.edtPermanentAddressBr);

            edtPincodeOffBr = (EditText) view.findViewById(R.id.edtPincodeOffBr);
            edtAddressOffBr = (EditText) view.findViewById(R.id.edtAddressOffBr);
            input_passingyearBr = (EditText) view.findViewById(R.id.input_passingyearBr);

            rgGenderBr = (RadioGroup) view.findViewById(R.id.rgGenderBr);
            rgMaritalStatusBr = view.findViewById(R.id.rgMaritalStatusBr);
            rgiscgpaBr = (RadioGroup) view.findViewById(R.id.rgiscgpaBr);
            rgemptypeBr = (RadioGroup) view.findViewById(R.id.rgemptypeBr);
            rgborrower_gapsBr = (RadioGroup) view.findViewById(R.id.rgborrower_gapsBr);
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

            spPermanentCountryBr = (Spinner) view.findViewById(R.id.spPermanentCountryBr);
            spPermanentStateBr = (Spinner) view.findViewById(R.id.spPermanentStateBr);
            spPermanentCityBr = (Spinner) view.findViewById(R.id.spPermanentCityBr);

            spCountryOffBr = (Spinner) view.findViewById(R.id.spCountryOffBr);
            spStateOffBr = (Spinner) view.findViewById(R.id.spStateOffBr);
            spCityOffBr = (Spinner) view.findViewById(R.id.spCityOffBr);

            spResidentTypeBr = (Spinner) view.findViewById(R.id.spResidentTypeBr);
            spDurationStayAtCurrentAddressBr = (Spinner) view.findViewById(R.id.spDurationStayAtCurrentAddressBr);
            spCurrentAddressSameAsKycBr = (Spinner) view.findViewById(R.id.spCurrentAddressSameAsKycBr);
            spCurrentAddressSameAsKycOrBorrowerBr = (Spinner) view.findViewById(R.id.spCurrentAddressSameAsKycOrBorrowerBr);
            spdurationofjobBr = view.findViewById(R.id.spdurationofjobBr);
            splastdegreecompletedBr = view.findViewById(R.id.splastdegreecompletedBr);

            //CoBorrower
            linOfficeDetailsCoBr = (LinearLayout) view.findViewById(R.id.linOfficeDetailsCoBr);
            linCurrentAddressCoBr = (LinearLayout) view.findViewById(R.id.linCurrentAddressCoBr);

            spinnerRelationshipwithBorrower = (Spinner) view.findViewById(R.id.spinner_coborrowerrelationshipwithborrower);
            edtFnameCoBr = (EditText) view.findViewById(R.id.edtFnameCoBr);
            edtMnameCoBr = (EditText) view.findViewById(R.id.edtMnameCoBr);
            edtLnameCoBr = (EditText) view.findViewById(R.id.edtLnameCoBr);
            edtMobileCoBr = view.findViewById(R.id.edtMobileCoBr);
            edtEmailCoBr = view.findViewById(R.id.edtEmailCoBr);
            edtPanCoBr = (EditText) view.findViewById(R.id.edtPanCoBr);
            edtAadhaarCoBr = (EditText) view.findViewById(R.id.edtAadhaarCoBr);
            edtCompanyCoBr = (EditText) view.findViewById(R.id.edtCompanyCoBr);
            edtAnnualSalCoBr = (EditText) view.findViewById(R.id.edtAnnualSalCoBr);
            edtCurrentAddressCoBr = (EditText) view.findViewById(R.id.edtCurrentAddressCoBr);
            edtCurrentPincodeCoBr = (EditText) view.findViewById(R.id.edtCurrentPincodeCoBr);
            edtPermanentPincodeCoBr = (EditText) view.findViewById(R.id.edtPermanentPincodeCoBr);
            edtPermanentAddressCoBr = (EditText) view.findViewById(R.id.edtPermanentAddressCoBr);
            edtPincodeOffCoBr = (EditText) view.findViewById(R.id.edtPincodeOffCoBr);
            edtMonthlyRentCoBr = (EditText) view.findViewById(R.id.edtMonthlyRentCoBr);
            edtMonthlyRentLayoutCoBr = view.findViewById(R.id.edtMonthlyRentLayoutCoBr);
            edtAddressOffCoBr = view.findViewById(R.id.edtAddressOffCoBr);

            rgGenderCoBr = (RadioGroup) view.findViewById(R.id.rgGenderCoBr);
            rgMaritalStatusCoBr = view.findViewById(R.id.rgMaritalStatusCoBr);
            rgemptypeCoBr = view.findViewById(R.id.rgemptypeCoBr);
            rbMaleCoBr = (RadioButton) view.findViewById(R.id.rbMaleCoBr);
            rbFemaleCoBr = (RadioButton) view.findViewById(R.id.rbFemaleCoBr);
            rbMarriedCoBr = view.findViewById(R.id.rbMarriedCoBr);
            rbSingleCoBr = view.findViewById(R.id.rbSingleCoBr);
            rbempType_govBr = view.findViewById(R.id.rbempType_govBr);
            rbempType_privateBr = view.findViewById(R.id.rbempType_privateBr);

            spCurrentCountryCoBr = (Spinner) view.findViewById(R.id.spCurrentCountryCoBr);
            spCurrentStateCoBr = (Spinner) view.findViewById(R.id.spCurrentStateCoBr);
            spCurrentCityCoBr = (Spinner) view.findViewById(R.id.spCurrentCityCoBr);

            spPermanentCountryCoBr = (Spinner) view.findViewById(R.id.spPermanentCountryCoBr);
            spPermanentStateCoBr = (Spinner) view.findViewById(R.id.spPermanentStateCoBr);
            spPermanentCityCoBr = (Spinner) view.findViewById(R.id.spPermanentCityCoBr);

            spCountryOffCoBr = (Spinner) view.findViewById(R.id.spCountryOffCoBr);
            spStateOffCoBr = (Spinner) view.findViewById(R.id.spStateOffCoBr);
            spCityOffCoBr = (Spinner) view.findViewById(R.id.spCityOffCoBr);
            spdurationofjobCoBr = view.findViewById(R.id.spdurationofjobCoBr);

            spResidentTypeCoBr = (Spinner) view.findViewById(R.id.spResidentTypeCoBr);
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


            permanentCountry_arrayList = new ArrayList<>();
            borrowerPermanentCountryPersonalPOJOArrayList = new ArrayList<>();

            BorrowerPermanentCountryPersonalPOJO borrowerPermanentCountryPersonalPOJO = new BorrowerPermanentCountryPersonalPOJO();
            borrowerPermanentCountryPersonalPOJO.countryName = "Select Any";
            permanentCountry_arrayList.add("Select Any");
            borrowerPermanentCountryPersonalPOJO.countryID = "";
            borrowerPermanentCountryPersonalPOJOArrayList.add(borrowerPermanentCountryPersonalPOJO);

            BorrowerPermanentCountryPersonalPOJO borrowerPermanentCountryPersonalPOJO1 = new BorrowerPermanentCountryPersonalPOJO();
            borrowerPermanentCountryPersonalPOJO1.countryName = "India";
            permanentCountry_arrayList.add("India");
            borrowerPermanentCountryPersonalPOJO1.countryID = "1";
            borrowerPermanentCountryPersonalPOJOArrayList.add(borrowerPermanentCountryPersonalPOJO1);

            arrayAdapter_permanentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentCountry_arrayList);
            spPermanentCountryBr.setAdapter(arrayAdapter_permanentCountry);
            arrayAdapter_permanentCountry.notifyDataSetChanged();

            offCountry_arrayList = new ArrayList<>();
            borrowerOffCountryPersonalPOJOArrayList = new ArrayList<>();

            BorrowerOffCountryPersonalPOJO borrowerOffCountryPersonalPOJO = new BorrowerOffCountryPersonalPOJO();
            borrowerOffCountryPersonalPOJO.countryName = "Select Any";
            offCountry_arrayList.add("Select Any");
            borrowerOffCountryPersonalPOJO.countryID = "";
            borrowerOffCountryPersonalPOJOArrayList.add(borrowerOffCountryPersonalPOJO);

            BorrowerOffCountryPersonalPOJO borrowerOffCountryPersonalPOJO1 = new BorrowerOffCountryPersonalPOJO();
            borrowerOffCountryPersonalPOJO1.countryName = "India";
            offCountry_arrayList.add("India");
            borrowerOffCountryPersonalPOJO1.countryID = "1";
            borrowerOffCountryPersonalPOJOArrayList.add(borrowerOffCountryPersonalPOJO1);

            arrayAdapter_offCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, offCountry_arrayList);
            spCountryOffBr.setAdapter(arrayAdapter_offCountry);
            arrayAdapter_offCountry.notifyDataSetChanged();

            //Co Br
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


            permanentCountry_arrayListCoBr = new ArrayList<>();
            borrowerPermanentCountryPersonalPOJOArrayListCoBr = new ArrayList<>();

            BorrowerPermanentCountryPersonalPOJO borrowerPermanentCountryPersonalPOJOCoBr = new BorrowerPermanentCountryPersonalPOJO();
            borrowerPermanentCountryPersonalPOJOCoBr.countryName = "Select Any";
            permanentCountry_arrayListCoBr.add("Select Any");
            borrowerPermanentCountryPersonalPOJOCoBr.countryID = "";
            borrowerPermanentCountryPersonalPOJOArrayListCoBr.add(borrowerPermanentCountryPersonalPOJOCoBr);

            BorrowerPermanentCountryPersonalPOJO borrowerPermanentCountryPersonalPOJO1CoBr = new BorrowerPermanentCountryPersonalPOJO();
            borrowerPermanentCountryPersonalPOJO1CoBr.countryName = "India";
            permanentCountry_arrayListCoBr.add("India");
            borrowerPermanentCountryPersonalPOJO1CoBr.countryID = "1";
            borrowerPermanentCountryPersonalPOJOArrayListCoBr.add(borrowerPermanentCountryPersonalPOJO1CoBr);

            arrayAdapter_permanentCountryCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentCountry_arrayListCoBr);
            spPermanentCountryCoBr.setAdapter(arrayAdapter_permanentCountryCoBr);
            arrayAdapter_permanentCountryCoBr.notifyDataSetChanged();

            offCountry_arrayListCoBr = new ArrayList<>();
            borrowerOffCountryPersonalPOJOArrayListCoBr = new ArrayList<>();

            BorrowerOffCountryPersonalPOJO borrowerOffCountryPersonalPOJOCoBr = new BorrowerOffCountryPersonalPOJO();
            borrowerOffCountryPersonalPOJOCoBr.countryName = "Select Any";
            offCountry_arrayListCoBr.add("Select Any");
            borrowerOffCountryPersonalPOJOCoBr.countryID = "";
            borrowerOffCountryPersonalPOJOArrayListCoBr.add(borrowerOffCountryPersonalPOJOCoBr);

            BorrowerOffCountryPersonalPOJO borrowerOffCountryPersonalPOJO1CoBr = new BorrowerOffCountryPersonalPOJO();
            borrowerOffCountryPersonalPOJO1CoBr.countryName = "India";
            offCountry_arrayListCoBr.add("India");
            borrowerOffCountryPersonalPOJO1CoBr.countryID = "1";
            borrowerOffCountryPersonalPOJOArrayListCoBr.add(borrowerOffCountryPersonalPOJO1CoBr);

            arrayAdapter_offCountryCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, offCountry_arrayListCoBr);
            spCountryOffCoBr.setAdapter(arrayAdapter_offCountryCoBr);
            arrayAdapter_offCountryCoBr.notifyDataSetChanged();

            //CurrentResidence Type
            currentResidencetype_arrayList = new ArrayList<>();
            borrowerCurrentResidencePersonalPOJOArrayList = new ArrayList<>();

            BorrowerCurrentResidenceTypePersonalPOJO currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
            currentResidenceTypePersonalPOJO.residencetypeName = "Select Any";
            currentResidenceTypePersonalPOJO.dresidencetypeID = "";
            currentResidencetype_arrayList.add("Select Any");
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
            currentResidenceTypePersonalPOJO.residencetypeName = "Owned by Self/Spouse/Parents/Sibling";
            currentResidenceTypePersonalPOJO.dresidencetypeID = "0";
            currentResidencetype_arrayList.add("Owned by Self/Spouse/Parents/Sibling");
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
            currentResidenceTypePersonalPOJO.residencetypeName = "Rented With Family";
            currentResidenceTypePersonalPOJO.dresidencetypeID = "1";
            currentResidencetype_arrayList.add("Rented With Family");
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
            currentResidenceTypePersonalPOJO.residencetypeName = "Company Provided";
            currentResidenceTypePersonalPOJO.dresidencetypeID = "2";
            currentResidencetype_arrayList.add("Company Provided");
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
            currentResidenceTypePersonalPOJO.residencetypeName = "Rented With Friends";
            currentResidenceTypePersonalPOJO.dresidencetypeID = "3";
            currentResidencetype_arrayList.add("Rented With Friends");
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
            currentResidenceTypePersonalPOJO.residencetypeName = "Rented Staying Alone";
            currentResidenceTypePersonalPOJO.dresidencetypeID = "4";
            currentResidencetype_arrayList.add("Rented Staying Alone");
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
            currentResidenceTypePersonalPOJO.residencetypeName = "Paying Guest";
            currentResidenceTypePersonalPOJO.dresidencetypeID = "5";
            currentResidencetype_arrayList.add("Paying Guest");
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
            currentResidenceTypePersonalPOJO.residencetypeName = "Hostel";
            currentResidenceTypePersonalPOJO.dresidencetypeID = "6";
            currentResidencetype_arrayList.add("Hostel");
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
            currentResidenceTypePersonalPOJO.residencetypeName = "Owned By Parents/Siblings";
            currentResidenceTypePersonalPOJO.dresidencetypeID = "7";
            currentResidencetype_arrayList.add("Owned By Parents/Siblings");
            borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

            arrayAdapter_currentResidenceDuration = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentResidencetype_arrayList);
            spResidentTypeBr.setAdapter(arrayAdapter_currentResidenceDuration);
            arrayAdapter_currentResidenceDuration.notifyDataSetChanged();

            //CurrentResidence Type CoBr
            currentResidencetype_arrayListCoBr = new ArrayList<>();
            coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr = new ArrayList<>();

            CoborrowerCurrentResidenceTypePersonalPOJO coborrowerCurrentResidenceTypePersonalPOJO = new CoborrowerCurrentResidenceTypePersonalPOJO();
            coborrowerCurrentResidenceTypePersonalPOJO.residencetypeName = "Select Any";
            currentResidencetype_arrayListCoBr.add("Select Any");
            coborrowerCurrentResidenceTypePersonalPOJO.dresidencetypeID = "";
            coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceTypePersonalPOJO);

            coborrowerCurrentResidenceTypePersonalPOJO = new CoborrowerCurrentResidenceTypePersonalPOJO();
            coborrowerCurrentResidenceTypePersonalPOJO.residencetypeName = "Owned by Self/Spouse/Parents/Sibling";
            currentResidencetype_arrayListCoBr.add("Owned by Self/Spouse/Parents/Sibling");
            coborrowerCurrentResidenceTypePersonalPOJO.dresidencetypeID = "0";
            coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceTypePersonalPOJO);

            coborrowerCurrentResidenceTypePersonalPOJO = new CoborrowerCurrentResidenceTypePersonalPOJO();
            coborrowerCurrentResidenceTypePersonalPOJO.residencetypeName = "Rented With Family";
            currentResidencetype_arrayListCoBr.add("Rented With Family");
            coborrowerCurrentResidenceTypePersonalPOJO.dresidencetypeID = "1";
            coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceTypePersonalPOJO);

            coborrowerCurrentResidenceTypePersonalPOJO = new CoborrowerCurrentResidenceTypePersonalPOJO();
            coborrowerCurrentResidenceTypePersonalPOJO.residencetypeName = "Company Provided";
            currentResidencetype_arrayListCoBr.add("Company Provided");
            coborrowerCurrentResidenceTypePersonalPOJO.dresidencetypeID = "2";
            coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceTypePersonalPOJO);

            coborrowerCurrentResidenceTypePersonalPOJO = new CoborrowerCurrentResidenceTypePersonalPOJO();
            coborrowerCurrentResidenceTypePersonalPOJO.residencetypeName = "Rented With Friends";
            currentResidencetype_arrayListCoBr.add("Rented With Friends");
            coborrowerCurrentResidenceTypePersonalPOJO.dresidencetypeID = "3";
            coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceTypePersonalPOJO);

            coborrowerCurrentResidenceTypePersonalPOJO = new CoborrowerCurrentResidenceTypePersonalPOJO();
            coborrowerCurrentResidenceTypePersonalPOJO.residencetypeName = "Rented Staying Alone";
            currentResidencetype_arrayListCoBr.add("Rented Staying Alone");
            coborrowerCurrentResidenceTypePersonalPOJO.dresidencetypeID = "4";
            coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceTypePersonalPOJO);

            coborrowerCurrentResidenceTypePersonalPOJO = new CoborrowerCurrentResidenceTypePersonalPOJO();
            coborrowerCurrentResidenceTypePersonalPOJO.residencetypeName = "Paying Guest";
            currentResidencetype_arrayListCoBr.add("Paying Guest");
            coborrowerCurrentResidenceTypePersonalPOJO.dresidencetypeID = "5";
            coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceTypePersonalPOJO);

            coborrowerCurrentResidenceTypePersonalPOJO = new CoborrowerCurrentResidenceTypePersonalPOJO();
            coborrowerCurrentResidenceTypePersonalPOJO.residencetypeName = "Hostel";
            currentResidencetype_arrayListCoBr.add("Hostel");
            coborrowerCurrentResidenceTypePersonalPOJO.dresidencetypeID = "6";
            coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceTypePersonalPOJO);


            coborrowerCurrentResidenceTypePersonalPOJO = new CoborrowerCurrentResidenceTypePersonalPOJO();
            coborrowerCurrentResidenceTypePersonalPOJO.residencetypeName = "Owned By Parents/Siblings";
            currentResidencetype_arrayListCoBr.add("Owned By Parents/Siblings");
            coborrowerCurrentResidenceTypePersonalPOJO.dresidencetypeID = "7";
            coborrowerCurrentResidenceTypePersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceTypePersonalPOJO);

            arrayAdapter_currentResidenceDuration = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentResidencetype_arrayListCoBr);
            spResidentTypeCoBr.setAdapter(arrayAdapter_currentResidenceDuration);
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

            //CurrentResidence Duration CoBr
            currentresidenceduration_arrayListCoBr = new ArrayList<>();
            currentResidenceDurationPersonalPOJOArrayListCoBr = new ArrayList<>();

            CoborrowerCurrentResidenceDurationPersonalPOJO coborrowerCurrentResidenceDurationPersonalPOJO = new CoborrowerCurrentResidenceDurationPersonalPOJO();
            coborrowerCurrentResidenceDurationPersonalPOJO.durationName = "Select Any";
            currentresidenceduration_arrayListCoBr.add("Select Any");
            coborrowerCurrentResidenceDurationPersonalPOJO.durationID = "";
            currentResidenceDurationPersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceDurationPersonalPOJO);

            CoborrowerCurrentResidenceDurationPersonalPOJO coborrowerCurrentResidenceDurationPersonalPOJO1 = new CoborrowerCurrentResidenceDurationPersonalPOJO();
            coborrowerCurrentResidenceDurationPersonalPOJO1.durationName = "Less Than 6 Months";
            currentresidenceduration_arrayListCoBr.add("Less Than 6 Months");
            coborrowerCurrentResidenceDurationPersonalPOJO1.durationID = "1";
            currentResidenceDurationPersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceDurationPersonalPOJO1);

            CoborrowerCurrentResidenceDurationPersonalPOJO coborrowerCurrentResidenceDurationPersonalPOJO2 = new CoborrowerCurrentResidenceDurationPersonalPOJO();
            coborrowerCurrentResidenceDurationPersonalPOJO2.durationName = "6 Months to 1 Year";
            currentresidenceduration_arrayListCoBr.add("6 Months to 1 Year");
            coborrowerCurrentResidenceDurationPersonalPOJO2.durationID = "2";
            currentResidenceDurationPersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceDurationPersonalPOJO2);

            CoborrowerCurrentResidenceDurationPersonalPOJO coborrowerCurrentResidenceDurationPersonalPOJO3 = new CoborrowerCurrentResidenceDurationPersonalPOJO();
            coborrowerCurrentResidenceDurationPersonalPOJO3.durationName = "1 Year to 2 Years";
            currentresidenceduration_arrayListCoBr.add("1 Year to 2 Years");
            coborrowerCurrentResidenceDurationPersonalPOJO3.durationID = "3";
            currentResidenceDurationPersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceDurationPersonalPOJO3);

            CoborrowerCurrentResidenceDurationPersonalPOJO coborrowerCurrentResidenceDurationPersonalPOJO4 = new CoborrowerCurrentResidenceDurationPersonalPOJO();
            coborrowerCurrentResidenceDurationPersonalPOJO4.durationName = "More Than 2 Years";
            currentresidenceduration_arrayListCoBr.add("More Than 2 Years");
            coborrowerCurrentResidenceDurationPersonalPOJO4.durationID = "4";
            currentResidenceDurationPersonalPOJOArrayListCoBr.add(coborrowerCurrentResidenceDurationPersonalPOJO4);

            arrayAdapter_currentResidenceDurationCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentresidenceduration_arrayListCoBr);
            spDurationStayAtCurrentAddressCoBr.setAdapter(arrayAdapter_currentResidenceDurationCoBr);
            arrayAdapter_currentResidenceDurationCoBr.notifyDataSetChanged();


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

            arrayAdapter_permanentAddressSameAsBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentAddressSameAs_arrayListBr);
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
            addressSameAsCurrentPOJO22.address = "CoBorrower Kyc Address";
            currentAddressSameAs_arrayListCoBr.add("CoBorrower Kyc Address");
            addressSameAsCurrentPOJO22.id = "1";
            addressSameAsCurrentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO22);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO24 = new AddressSameAsCurrentPOJO();
            currentAddressSameAs_arrayListCoBr.add("Borrower Current Address");
            addressSameAsCurrentPOJO24.address = "Borrower Current Address";
            addressSameAsCurrentPOJO24.id = "2";
            addressSameAsCurrentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO24);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO25 = new AddressSameAsCurrentPOJO();
            currentAddressSameAs_arrayListCoBr.add("Borrower Permanent Address");
            addressSameAsCurrentPOJO25.address = "Borrower Permanent Address";
            addressSameAsCurrentPOJO25.id = "3";
            addressSameAsCurrentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO25);


            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO23 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO23.address = "None";
            currentAddressSameAs_arrayListCoBr.add("None");
            addressSameAsCurrentPOJO23.id = "4";
            addressSameAsCurrentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO23);

            arrayAdapter_currentAddressSameAsCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentAddressSameAs_arrayListCoBr);
            spCurrentAddressSameAsKycCoBr.setAdapter(arrayAdapter_currentAddressSameAsCoBr);
            arrayAdapter_currentAddressSameAsCoBr.notifyDataSetChanged();

            //Coborrower Permanent Address same as
            permanentAddressSameAs_arrayListCoBr = new ArrayList<>();
            addressSameAspermanentPOJOArrayListCoBr = new ArrayList<>();

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO31 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO31.address = "Select Any";
            permanentAddressSameAs_arrayListCoBr.add("Select Any");
            addressSameAsCurrentPOJO31.id = "";
            addressSameAspermanentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO31);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO32 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO32.address = "CoBorrower Kyc Address";
            permanentAddressSameAs_arrayListCoBr.add("CoBorrower Kyc Address");
            addressSameAsCurrentPOJO32.id = "1";
            addressSameAspermanentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO32);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO33 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO33.address = "CoBorrower Current Address";
            permanentAddressSameAs_arrayListCoBr.add("CoBorrower Current Address");
            addressSameAsCurrentPOJO33.id = "2";
            addressSameAspermanentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO33);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO34 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO34.address = "Borrower Current Address";
            permanentAddressSameAs_arrayListCoBr.add("Borrower Current Address");
            addressSameAsCurrentPOJO34.id = "3";
            addressSameAspermanentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO34);

            AddressSameAsCurrentPOJO addressSameAsCurrentPOJO35 = new AddressSameAsCurrentPOJO();
            addressSameAsCurrentPOJO35.address = "Borrower Permanent Address";
            permanentAddressSameAs_arrayListCoBr.add("Borrower Permanent Address");
            addressSameAsCurrentPOJO35.id = "4";
            addressSameAspermanentPOJOArrayListCoBr.add(addressSameAsCurrentPOJO35);


            arrayAdapter_permanentAddressSameAsCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentAddressSameAs_arrayListCoBr);
            spCurrentAddressSameAsKycOrBorrowerCoBr.setAdapter(arrayAdapter_permanentAddressSameAsCoBr);
            arrayAdapter_permanentAddressSameAsCoBr.notifyDataSetChanged();

            duarationOfWorkListCoBr.add("Select");
            duarationOfWorkListCoBr.add("Less Than 6 Month");
            duarationOfWorkListCoBr.add("6 Months to 1 Year");
            duarationOfWorkListCoBr.add("1 Year to 2 Years");
            duarationOfWorkListCoBr.add("More Than 2 years");
            ArrayAdapter workDurationAdapterCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, duarationOfWorkListCoBr);
            spdurationofjobCoBr.setAdapter(workDurationAdapterCoBr);

            duarationOfWorkListBr.add("Select");
            duarationOfWorkListBr.add("Less Than 6 Month");
            duarationOfWorkListBr.add("6 Months to 1 Year");
            duarationOfWorkListBr.add("1 Year to 2 Years");
            duarationOfWorkListBr.add("More Than 2 years");
            ArrayAdapter workDurationAdapterBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, duarationOfWorkListBr);
            spdurationofjobBr.setAdapter(workDurationAdapterBr);


            lastCompletedDegreeListBr.add("Select");
            lastCompletedDegreeListBr.add("Grade Xth");
            lastCompletedDegreeListBr.add("Grade XIIth");
            lastCompletedDegreeListBr.add("Bachelors");
            lastCompletedDegreeListBr.add("Masters");
            lastCompletedDegreeListBr.add("Less Than Grade Xth");
            ArrayAdapter lastCompletedDegreeAdapterBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, lastCompletedDegreeListBr);
            splastdegreecompletedBr.setAdapter(lastCompletedDegreeAdapterBr);

            relationshipwithBorrowerPOJOArrayList = new ArrayList<>();
            relationshipwithborrower_arrayList = new ArrayList<>();
            RelationshipwithBorrowerPOJO relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
            relationshipwithBorrowerPOJO.relationID = "";
            relationshipwithBorrowerPOJO.relationName = "Select";
            relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
            relationshipwithborrower_arrayList.add("Select");

            relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
            relationshipwithBorrowerPOJO.relationID = "1";
            relationshipwithBorrowerPOJO.relationName = "Father";
            relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
            relationshipwithborrower_arrayList.add("Father");

            relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
            relationshipwithBorrowerPOJO.relationID = "3";
            relationshipwithBorrowerPOJO.relationName = "Wife";
            relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
            relationshipwithborrower_arrayList.add("Wife");

            relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
            relationshipwithBorrowerPOJO.relationID = "4";
            relationshipwithBorrowerPOJO.relationName = "Sister";
            relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
            relationshipwithborrower_arrayList.add("Sister");

            relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
            relationshipwithBorrowerPOJO.relationID = "5";
            relationshipwithBorrowerPOJO.relationName = "Grand-Mother";
            relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
            relationshipwithborrower_arrayList.add("Grand-Mother");

            relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
            relationshipwithBorrowerPOJO.relationID = "7";
            relationshipwithBorrowerPOJO.relationName = "Mother";
            relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
            relationshipwithborrower_arrayList.add("Mother");

            relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
            relationshipwithBorrowerPOJO.relationID = "9";
            relationshipwithBorrowerPOJO.relationName = "Husband";
            relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
            relationshipwithborrower_arrayList.add("Husband");

            relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
            relationshipwithBorrowerPOJO.relationID = "10";
            relationshipwithBorrowerPOJO.relationName = "Brother";
            relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
            relationshipwithborrower_arrayList.add("Brother");

            relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
            relationshipwithBorrowerPOJO.relationID = "11";
            relationshipwithBorrowerPOJO.relationName = "Grand-Father";
            relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
            relationshipwithborrower_arrayList.add("Grand-Father");

            relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
            relationshipwithBorrowerPOJO.relationID = "13";
            relationshipwithBorrowerPOJO.relationName = "Legal Guardian";
            relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
            relationshipwithborrower_arrayList.add("Legal Guardian");

            relationshipwithBorrowerPOJO = new RelationshipwithBorrowerPOJO();
            relationshipwithBorrowerPOJO.relationID = "14";
            relationshipwithBorrowerPOJO.relationName = "Other";
            relationshipwithBorrowerPOJOArrayList.add(relationshipwithBorrowerPOJO);
            relationshipwithborrower_arrayList.add("Other");

            ArrayAdapter relationWithBorrowerAdapterBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, relationshipwithborrower_arrayList);
            spinnerRelationshipwithBorrower.setAdapter(relationWithBorrowerAdapterBr);

            enableDisableViews(false);

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
                    JSONArray jsonArray3 = jsonData.getJSONArray("profession");
                    //borrower
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

                    //coborrower
                    profession_arrayListCoBr = new ArrayList<>();
                    professionPOJOArrayListCoBr = new ArrayList<>();
                    profession_arrayListCoBr.add("Select Profession");
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        ProfessionPOJO professionPOJO = new ProfessionPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        if (mJsonti.getString("id").equals("2")) {  //student
                            continue;
                        }
                        profession_arrayListCoBr.add(mJsonti.getString("profession"));
                        professionPOJO.Salaried = mJsonti.getString("profession");
                        professionPOJO.id = mJsonti.getString("id");
                        professionPOJOArrayListCoBr.add(professionPOJO);
                    }
                    arrayAdapter_professionCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayListCoBr);
                    spProfessionCoBr.setAdapter(arrayAdapter_professionCoBr);
                    arrayAdapter_professionCoBr.notifyDataSetChanged();

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

    private void ProfessionApiCallCoBr() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "dashboard/getAllProfession";
            Map<String, String> params = new HashMap<String, String>();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getAllProfessiondetailedinfoCoBr", params, MainApplication.auth_token);
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

    public void getAllProfessiondetailedinfoCoBr(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    profession_arrayListCoBr = new ArrayList<>();
                    profession_arrayListCoBr.add("Select Profession");
                    arrayAdapter_professionCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayListCoBr);
                    spProfessionCoBr.setAdapter(arrayAdapter_professionCoBr);
                    arrayAdapter_professionCoBr.notifyDataSetChanged();
//                    spProfessionCoBr.setSelection(0);

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
                    profession_arrayListCoBr = new ArrayList<>();
                    professionPOJOArrayListCoBr = new ArrayList<>();
                    profession_arrayListCoBr.add("Select Profession");
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        ProfessionPOJO professionPOJO = new ProfessionPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        if (mJsonti.getString("id").equals("2")) {
                        } else {
                            professionPOJO.Salaried = mJsonti.getString("profession");
                            profession_arrayListCoBr.add(mJsonti.getString("profession"));
                            professionPOJO.id = mJsonti.getString("id");
                            professionPOJOArrayListCoBr.add(professionPOJO);
                        }
                    }
                    arrayAdapter_professionCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayListCoBr);
                    spProfessionCoBr.setAdapter(arrayAdapter_professionCoBr);
                    arrayAdapter_professionCoBr.notifyDataSetChanged();

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
            String url = MainApplication.mainUrl + "algo/getCities"; //http://159.89.204.41/eduvanzApi/algo/getCities
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", currentcountryID);//1
            params.put("stateId", currentstateID);//2
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentCities(currentstateID,currentcountryID);
            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetCurrentCity", params, MainApplication.auth_token);
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
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetCurrentStates", params, MainApplication.auth_token);
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

    public void dtlgetCurrentCities(JSONObject jsonData) {
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

    public void dtlgetCurrentStates(JSONObject jsonData) {
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


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int count = borrowerCurrentStatePersonalPOJOArrayList.size();
                            for (int i = 0; i < count; i++) {
                                if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
                                    spCurrentStateBr.setSelection(i);
                                }
                            }
                        }
                    },500);


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


    private void permanentcityApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getCities"; //http://159.89.204.41/eduvanzApi/algo/getCities
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", permanentcountryID);//1
            params.put("stateId", permanentstateID);//2
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentCities(currentstateID,currentcountryID);
            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetPermanentCity", params, MainApplication.auth_token);
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

    private void permanentstateApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getStates";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", permanentcountryID);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentStates(currentcountryID);

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetPermanentStates", params, MainApplication.auth_token);
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

    public void dtlgetPermanentCities(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    permanentcity_arrayList = new ArrayList<>();
                    permanentcity_arrayList.add("Select Any");
                    arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayList);
                    spPermanentCityBr.setAdapter(arrayAdapter_permanentCity);
                    arrayAdapter_permanentCity.notifyDataSetChanged();
                    spPermanentCityBr.setSelection(0);
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
                Log.e("SERVER CALL", "getpermanentCities+++" + jsonData);

                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray2 = jsonObject.getJSONArray("cities");
                    permanentcity_arrayList = new ArrayList<>();
                    borrowerPermanentCityPersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        BorrowerPermanentCityPersonalPOJO borrowerpermanentCityPersonalPOJO = new BorrowerPermanentCityPersonalPOJO();
                        JSONObject mJsonti = jsonArray2.getJSONObject(i);
                        borrowerpermanentCityPersonalPOJO.cityName = mJsonti.getString("city_name");
                        permanentcity_arrayList.add(mJsonti.getString("city_name"));
                        borrowerpermanentCityPersonalPOJO.cityID = mJsonti.getString("city_id");
                        borrowerPermanentCityPersonalPOJOArrayList.add(borrowerpermanentCityPersonalPOJO);
                    }
                    arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayList);
                    spPermanentCityBr.setAdapter(arrayAdapter_permanentCity);
                    arrayAdapter_permanentCity.notifyDataSetChanged();

                    int count = borrowerPermanentCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityID)) {
                            spPermanentCityBr.setSelection(i);
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

    public void dtlgetPermanentStates(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    permanentstate_arrayList = new ArrayList<>();
                    permanentstate_arrayList.add("Select Any");
                    arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentstate_arrayList);
                    spPermanentStateBr.setAdapter(arrayAdapter_permanentState);
                    arrayAdapter_permanentState.notifyDataSetChanged();
                    spPermanentStateBr.setSelection(0);
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
                    permanentstate_arrayList = new ArrayList<>();
                    borrowerPermanentStatePersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        BorrowerPermanentStatePersonalPOJO borrowerPermanentStatePersonalPOJO = new BorrowerPermanentStatePersonalPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borrowerPermanentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                        permanentstate_arrayList.add(mJsonti.getString("state_name"));
                        borrowerPermanentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                        borrowerPermanentStatePersonalPOJOArrayList.add(borrowerPermanentStatePersonalPOJO);
                    }
                    arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentstate_arrayList);
                    spPermanentStateBr.setAdapter(arrayAdapter_permanentState);
                    arrayAdapter_permanentState.notifyDataSetChanged();

                    int count = borrowerPermanentStatePersonalPOJOArrayList.size();

                    for (int i = 0; i < count; i++) {
                        if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(CoBrpermanent_address_state)) {
                            spPermanentStateBr.setSelection(i);
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


    private void offcityApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getCities"; //http://159.89.204.41/eduvanzApi/algo/getCities
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", offcountryIDBr);//1
            params.put("stateId", offstateIDBr);//2
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentCities(currentstateID,currentcountryID);
            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetOffCities", params, MainApplication.auth_token);
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

    private void offstateApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getStates";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", offcountryIDBr);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentStates(currentcountryID);

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetOffStates", params, MainApplication.auth_token);
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

    public void dtlgetOffCities(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    offcity_arrayList = new ArrayList<>();
                    offcity_arrayList.add("Select Any");
                    arrayAdapter_offCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, offcity_arrayList);
                    spCityOffBr.setAdapter(arrayAdapter_offCity);
                    arrayAdapter_offCity.notifyDataSetChanged();
                    spCityOffBr.setSelection(0);
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
                Log.e("SERVER CALL", "getoffCities+++" + jsonData);

                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
                    JSONObject jsonObject = jsonData.getJSONObject("result");

                    JSONArray jsonArray2 = jsonObject.getJSONArray("cities");
                    offcity_arrayList = new ArrayList<>();
                    borrowerOffCityPersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        BorrowerOffCityPersonalPOJO borroweroffCityPersonalPOJO = new BorrowerOffCityPersonalPOJO();
                        JSONObject mJsonti = jsonArray2.getJSONObject(i);
                        borroweroffCityPersonalPOJO.cityName = mJsonti.getString("city_name");
                        offcity_arrayList.add(mJsonti.getString("city_name"));
                        borroweroffCityPersonalPOJO.cityID = mJsonti.getString("city_id");
                        borrowerOffCityPersonalPOJOArrayList.add(borroweroffCityPersonalPOJO);
                    }
                    arrayAdapter_offCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, offcity_arrayList);
                    spCityOffBr.setAdapter(arrayAdapter_offCity);
                    arrayAdapter_offCity.notifyDataSetChanged();

                    int count = borrowerOffCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerOffCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(offcityIDBr)) {
                            spCityOffBr.setSelection(i);
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

    public void dtlgetOffStates(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    offstate_arrayList = new ArrayList<>();
                    offstate_arrayList.add("Select Any");
                    arrayAdapter_offState = new ArrayAdapter(context, R.layout.custom_layout_spinner, offstate_arrayList);
                    spStateOffBr.setAdapter(arrayAdapter_offState);
                    arrayAdapter_offState.notifyDataSetChanged();
                    spStateOffBr.setSelection(0);
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
                    offstate_arrayList = new ArrayList<>();
                    borrowerOffStatePersonalPOJOArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        BorrowerOffStatePersonalPOJO borrowerOffStatePersonalPOJO = new BorrowerOffStatePersonalPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borrowerOffStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                        offstate_arrayList.add(mJsonti.getString("state_name"));
                        borrowerOffStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                        borrowerOffStatePersonalPOJOArrayList.add(borrowerOffStatePersonalPOJO);
                    }
                    arrayAdapter_offState = new ArrayAdapter(context, R.layout.custom_layout_spinner, offstate_arrayList);
                    spStateOffBr.setAdapter(arrayAdapter_offState);
                    arrayAdapter_offState.notifyDataSetChanged();

                    int count = borrowerOffStatePersonalPOJOArrayList.size();

                    for (int i = 0; i < count; i++) {
                        if (borrowerOffStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(offstateIDBr)) {
                            spStateOffBr.setSelection(i);
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
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetCurrentCitiesCoBr", params, MainApplication.auth_token);
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
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetCurrentStatesCoBr", params, MainApplication.auth_token);
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

    public void dtlgetCurrentStatesCoBr(JSONObject jsonData) {
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

    public void dtlgetCurrentCitiesCoBr(JSONObject jsonData) {
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

    private void permanentcityApiCallCoBr() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getCities"; //http://159.89.204.41/eduvanzApi/algo/getCities
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", permanentcountryIDCoBr);//1
            params.put("stateId", permanentstateIDCoBr);//2
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentCities(currentstateID,currentcountryID);
            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetPermanentCitiesCoBr", params, MainApplication.auth_token);
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

    private void permanentstateApiCallCoBr() {
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
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetPermanentStatesCoBr", params, MainApplication.auth_token);
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

    public void dtlgetPermanentCitiesCoBr(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    permanentcity_arrayListCoBr = new ArrayList<>();
                    permanentcity_arrayListCoBr.add("Select Any");
                    arrayAdapter_permanentCityCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayListCoBr);
                    spPermanentCityCoBr.setAdapter(arrayAdapter_permanentCityCoBr);
                    arrayAdapter_permanentCityCoBr.notifyDataSetChanged();
//                    sppermanentCityCoBr.setSelection(0);
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
                    permanentcity_arrayListCoBr = new ArrayList<>();
                    borrowerPermanentCityPersonalPOJOArrayListCoBr = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        BorrowerPermanentCityPersonalPOJO borrowerpermanentCityPersonalPOJOCoBr = new BorrowerPermanentCityPersonalPOJO();
                        JSONObject mJsonti = jsonArray2.getJSONObject(i);
                        borrowerpermanentCityPersonalPOJOCoBr.cityName = mJsonti.getString("city_name");
                        permanentcity_arrayListCoBr.add(mJsonti.getString("city_name"));
                        borrowerpermanentCityPersonalPOJOCoBr.cityID = mJsonti.getString("city_id");
                        borrowerPermanentCityPersonalPOJOArrayListCoBr.add(borrowerpermanentCityPersonalPOJOCoBr);
                    }
                    arrayAdapter_permanentCityCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayListCoBr);
                    spPermanentCityCoBr.setAdapter(arrayAdapter_permanentCityCoBr);
                    arrayAdapter_permanentCityCoBr.notifyDataSetChanged();

                    int count = borrowerPermanentCityPersonalPOJOArrayListCoBr.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerPermanentCityPersonalPOJOArrayListCoBr.get(i).cityID.equalsIgnoreCase(permanentcityIDCoBr)) {
                            spPermanentCityCoBr.setSelection(i);
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

    public void dtlgetPermanentStatesCoBr(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    permanentstate_arrayListCoBr = new ArrayList<>();
                    permanentstate_arrayListCoBr.add("Select Any");
                    arrayAdapter_permanentStateCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentstate_arrayListCoBr);
                    spPermanentStateCoBr.setAdapter(arrayAdapter_permanentStateCoBr);
                    arrayAdapter_permanentStateCoBr.notifyDataSetChanged();
                    spPermanentStateCoBr.setSelection(0);
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
                    permanentstate_arrayListCoBr = new ArrayList<>();
                    borrowerPermanentStatePersonalPOJOArrayListCoBr = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        BorrowerPermanentStatePersonalPOJO borrowerpermanentStatePersonalPOJOCoBr = new BorrowerPermanentStatePersonalPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borrowerpermanentStatePersonalPOJOCoBr.stateName = mJsonti.getString("state_name");
                        permanentstate_arrayListCoBr.add(mJsonti.getString("state_name"));
                        borrowerpermanentStatePersonalPOJOCoBr.stateID = mJsonti.getString("state_id");
                        borrowerPermanentStatePersonalPOJOArrayListCoBr.add(borrowerpermanentStatePersonalPOJOCoBr);
                    }
                    arrayAdapter_permanentStateCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentstate_arrayListCoBr);
                    spPermanentStateCoBr.setAdapter(arrayAdapter_permanentStateCoBr);
                    arrayAdapter_permanentStateCoBr.notifyDataSetChanged();

                    int count = borrowerPermanentStatePersonalPOJOArrayList.size();

                    for (int i = 0; i < count; i++) {
                        if (borrowerPermanentStatePersonalPOJOArrayListCoBr.get(i).stateID.equalsIgnoreCase(permanentstateIDCoBr)) {
                            spPermanentStateCoBr.setSelection(i);
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


    private void offcityApiCallCoBr() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getCities"; //http://159.89.204.41/eduvanzApi/algo/getCities
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", offcountryIDCoBr);//1
            params.put("stateId", offstateIDCoBr);//2
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentCities(currentstateID,currentcountryID);
            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetOffCitiesCoBr", params, MainApplication.auth_token);
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

    private void offstateApiCallCoBr() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "algo/getStates";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", offcountryIDCoBr);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentStates(currentcountryID);

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetOffStatesCoBr", params, MainApplication.auth_token);
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


    public void dtlgetOffCitiesCoBr(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    offcity_arrayListCoBr = new ArrayList<>();
                    offcity_arrayListCoBr.add("Select Any");
                    arrayAdapter_offCityCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, offcity_arrayListCoBr);
                    spCityOffCoBr.setAdapter(arrayAdapter_offCityCoBr);
                    arrayAdapter_offCityCoBr.notifyDataSetChanged();
//                    sppermanentCityCoBr.setSelection(0);
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
                    offcity_arrayListCoBr = new ArrayList<>();
                    borrowerOffCityPersonalPOJOArrayListCoBr = new ArrayList<>();
                    for (int i = 0; i < jsonArray2.length(); i++) {
                        BorrowerOffCityPersonalPOJO borroweroffCityPersonalPOJOCoBr = new BorrowerOffCityPersonalPOJO();
                        JSONObject mJsonti = jsonArray2.getJSONObject(i);
                        borroweroffCityPersonalPOJOCoBr.cityName = mJsonti.getString("city_name");
                        borroweroffCityPersonalPOJOCoBr.cityID = mJsonti.getString("city_id");
                        offcity_arrayListCoBr.add(mJsonti.getString("city_name"));
                        borrowerOffCityPersonalPOJOArrayListCoBr.add(borroweroffCityPersonalPOJOCoBr);
                    }
                    arrayAdapter_offCityCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, offcity_arrayListCoBr);
                    spCityOffCoBr.setAdapter(arrayAdapter_offCityCoBr);
                    arrayAdapter_offCityCoBr.notifyDataSetChanged();

                    int count = borrowerOffCityPersonalPOJOArrayListCoBr.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerOffCityPersonalPOJOArrayListCoBr.get(i).cityID.equalsIgnoreCase(offcityIDCoBr)) {
                            spCityOffCoBr.setSelection(i);
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

    public void dtlgetOffStatesCoBr(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    offstate_arrayListCoBr = new ArrayList<>();
                    offstate_arrayListCoBr.add("Select Any");
                    arrayAdapter_offStateCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, offstate_arrayListCoBr);
                    spStateOffCoBr.setAdapter(arrayAdapter_offStateCoBr);
                    arrayAdapter_offStateCoBr.notifyDataSetChanged();
                    spStateOffCoBr.setSelection(0);
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
                    offstate_arrayListCoBr = new ArrayList<>();
                    borrowerOffStatePersonalPOJOArrayListCoBr = new ArrayList<>();
                    for (int i = 0; i < jsonArray3.length(); i++) {
                        BorrowerOffStatePersonalPOJO borroweroffStatePersonalPOJOCoBr = new BorrowerOffStatePersonalPOJO();
                        JSONObject mJsonti = jsonArray3.getJSONObject(i);
                        borroweroffStatePersonalPOJOCoBr.stateName = mJsonti.getString("state_name");
                        borroweroffStatePersonalPOJOCoBr.stateID = mJsonti.getString("state_id");
                        offstate_arrayListCoBr.add(mJsonti.getString("state_name"));
                        borrowerOffStatePersonalPOJOArrayListCoBr.add(borroweroffStatePersonalPOJOCoBr);
                    }
                    arrayAdapter_offStateCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, offstate_arrayListCoBr);
                    spStateOffCoBr.setAdapter(arrayAdapter_offStateCoBr);
                    arrayAdapter_offStateCoBr.notifyDataSetChanged();

                    int count = borrowerOffStatePersonalPOJOArrayListCoBr.size();

                    for (int i = 0; i < count; i++) {
                        if (borrowerOffStatePersonalPOJOArrayListCoBr.get(i).stateID.equalsIgnoreCase(offstateIDCoBr)) {
                            spStateOffCoBr.setSelection(i);
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

    public void submitDetailedInfo() {
        try {
            if (rgGenderBr.getCheckedRadioButtonId() == R.id.rbMaleBr) Brgender_id = "1";
            else Brgender_id = "2";

            if (rgMaritalStatusBr.getCheckedRadioButtonId() == R.id.rbMarriedBr)
                Brmarital_status = "1";
            else Brmarital_status = "2";

            if (rgemptypeBr.getCheckedRadioButtonId() == R.id.rbempType_privateBr)
                Bremployer_type = "0";
            else Bremployer_type = "1";

            if (rgiscgpaBr.getCheckedRadioButtonId() == R.id.rbiscgpa_yesBr) Brscore_unit = "1";
            else Brscore_unit = "2";

            String BrGap = "";
            if (rgborrower_gapsBr.getCheckedRadioButtonId() == R.id.rbgaps_yesBr) BrGap = "1";
            else BrGap = "2";

            if (rgGenderCoBr.getCheckedRadioButtonId() == R.id.rbMaleCoBr) CoBrgender_id = "1";
            else CoBrgender_id = "2";

            if (rgMaritalStatusCoBr.getCheckedRadioButtonId() == R.id.rbMarriedCoBr)
                CoBrmarital_status = "1";
            else CoBrmarital_status = "2";

            if (rgemptypeCoBr.getCheckedRadioButtonId() == R.id.rbempType_govCoBr)
                CoBremployer_type = "1";
            else CoBremployer_type = "0";

            progressBar.setVisibility(View.VISIBLE);
            String url = MainApplication.mainUrl + "dashboard/editDetailedInformation";
            Map<String, String> params = new HashMap<String, String>();
            params.put("lead_id", lead_id);
            params.put("applicant_id", application_id);
            params.put("first_name", edtFnameBr.getText().toString());
            params.put("middle_name", edtMnameBr.getText().toString());
            params.put("last_name", edtLnameBr.getText().toString());
            params.put("gender_id", Brgender_id);
            params.put("dob", txtBirthdateBr.getText().toString());
            params.put("marital_status", Brmarital_status);
            params.put("pan_number", edtPanBr.getText().toString());
            params.put("aadhar_number", edtAadhaarBr.getText().toString());
            params.put("current_residence_type", currentResidencetypeID);
            params.put("current_address_rent", edtMonthlyRentBr.getText().toString());
            params.put("current_address_stay_duration", Brcurrent_address_stay_duration);
            params.put("is_borrower_current_address_same_as", Bris_borrower_current_address_same_as);
            params.put("current_address", edtCurrentAddressBr.getText().toString());
            params.put("current_address_pin", edtCurrentAddressBr.getText().toString());
            params.put("current_address_country", Brcurrent_address_country);
            params.put("current_address_state", Brcurrent_address_state);
            params.put("current_address_city", Brcurrent_address_city);
            params.put("is_borrower_permanent_address_same_as", Bris_borrower_permanent_address_same_as);
            params.put("permanent_address", edtPermanentAddressBr.getText().toString());
            params.put("permanent_address_pin", edtPermanentPincodeBr.getText().toString());
            params.put("permanent_address_country", Brpermanent_address_country);
            params.put("permanent_address_state", Brpermanent_address_state);
            params.put("permanent_address_city", Brpermanent_address_city);
            params.put("profession", Brprofession);
            params.put("employer_type", Bremployer_type);
            params.put("annual_income", edtAnnualSalBr.getText().toString());
            params.put("current_employment_duration", spdurationofjobBr.getSelectedItemPosition() + ""); ////////////////////Make model for list
            params.put("office_address", edtAddressOffBr.getText().toString());
            params.put("last_completed_degree", splastdegreecompletedBr.getSelectedItemPosition() + ""); ////////////////////Make model for list
            params.put("passing_year", input_passingyearBr.getText().toString());
            params.put("score_unit", Brscore_unit);
            params.put("cgpa", input_degreeBr.getEditText().getText().toString());
            params.put("percentage", input_degreeBr.getEditText().getText().toString());
            params.put("gap_in_education", BrGap);


            params.put("coapplicant_id", CoBrapplicant_id);
            params.put("corelationship_with_applicant", CoBrrelationship_with_applicant);
            params.put("cofirst_name", edtFnameCoBr.getText().toString());
            params.put("comiddle_name", edtMnameCoBr.getText().toString());
            params.put("colast_name", edtLnameCoBr.getText().toString());
            params.put("cogender_id", CoBrgender_id);
            params.put("codob", txtBirthdateCoBr.getText().toString());
            params.put("comarital_status", CoBrmarital_status);
            params.put("comobile_number", edtMobileCoBr.getText().toString());
            params.put("coemail_id", edtEmailCoBr.getText().toString());
            params.put("copan_number", edtPanCoBr.getText().toString());
            params.put("coaadhar_number", edtAadhaarCoBr.getText().toString());
            params.put("cocurrent_residence_type", CoBrcurrent_residence_type);
            params.put("cocurrent_address_rent", edtMonthlyRentCoBr.getText().toString());
            params.put("cocurrent_address_stay_duration", CoBrcurrent_address_stay_duration);
            params.put("cois_coborrower_current_address_same_as", CoBris_coborrower_current_address_same_as);
            params.put("cocurrent_address", edtCurrentAddressCoBr.getText().toString());
            params.put("cocurrent_address_pin", edtCurrentPincodeCoBr.getText().toString());
            params.put("cocurrent_address_country", CoBrcurrent_address_country);
            params.put("cocurrent_address_state", CoBrcurrent_address_state);
            params.put("cocurrent_address_city", CoBrcurrent_address_city);
            params.put("cois_coborrower_permanent_address_same_as", CoBris_coborrower_permanent_address_same_as);
            params.put("copermanent_address", edtPermanentAddressCoBr.getText().toString());
            params.put("copermanent_address_pin", edtPermanentPincodeCoBr.getText().toString());
            params.put("copermanent_address_country", CoBrpermanent_address_country);
            params.put("copermanent_address_state", CoBrpermanent_address_state);
            params.put("copermanent_address_city", CoBrpermanent_address_city);
            params.put("coprofession", CoBrprofession);
            params.put("coemployer_type", CoBremployer_type);
            params.put("coannual_income", edtAnnualSalCoBr.getText().toString());
            params.put("cocurrent_employment_duration", spdurationofjobCoBr.getSelectedItemPosition() + "");  ///////////////Make list of pjo model
            params.put("cooffice_address", edtAddressOffCoBr.getText().toString());

            Log.d("DETAILEDINFO", "PARAMS" + params.toString());

            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "editDetailedInformation", params, MainApplication.auth_token);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editDetailedInformation(JSONObject jsonDataO) {
        progressBar.setVisibility(View.GONE);
        try {
            if (jsonDataO.getInt("status") == 1) {
                Toast.makeText(context, jsonDataO.getString("message"), Toast.LENGTH_SHORT).show();
                LoanApplicationFragment_3 loanApplicationFragment_3 = new LoanApplicationFragment_3();
                transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_3).commit();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}