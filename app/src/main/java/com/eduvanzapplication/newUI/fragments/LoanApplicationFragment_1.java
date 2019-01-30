package com.eduvanzapplication.newUI.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentStatePersonalPOJO;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.pojo.ProfessionPOJO;
import com.eduvanzapplication.pqformfragments.pojo.LocationsPOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfCoursePOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfInsitituePOJO;

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

public class LoanApplicationFragment_1 extends Fragment {

    public static Context context;
    public static Fragment mFragment;

    public static Button btnAddCoborrower;

    //Borrower
    public static EditText edtCourseFeeBr, edtLoanAmtBr, edtFnameBr, edtMnameBr, edtLnameBr, edtEmailIdBr, edtPanBr, edtAadhaarBr,
            edtCompanyBr, edtAnnualSalBr, edtCurrentAddressBr, edtCurrentLandmarkBr, edtCurrentPincodeBr;

    public static RadioGroup rgGenderBr;
    public static RadioButton rbMaleBr, rbFemaleBr;

    public static Spinner spInstituteBr, spInsLocationBr, spCourseBr, spProfessionBr, spCurrentCountryBr, spCurrentStateBr,
            spCurrentCityBr;

    //CoBorrower
    public static EditText edtFnameCoBr, edtMnameCoBr, edtLnameCoBr, edtEmailIdCoBr, edtPanCoBr, edtAadhaarCoBr,
            edtCompanyCoBr, edtAnnualSalCoBr, edtCurrentAddressCoBr, edtCurrentLandmarkCoBr, edtCurrentPincodeCoBr;

    public static RadioGroup rgGenderCoBr;
    public static RadioButton rbMaleCoBr, rbFemaleCoBr;

    public static Spinner spProfessionCoBr,spCurrentCountryCoBr, spCurrentStateCoBr, spCurrentCityCoBr;

    public static TextView txtBirthdayCalenderBr, txtBirthdayCalenderCoBr, lblBirthdayBr, lblBirthdayCoBr, txtBirthdateBr, txtBirthdateCoBr;

    //Borrower
    public static ArrayAdapter arrayAdapter_currentCity, arrayAdapter_currentCityCoBr;
    public static ArrayList<String> currentcity_arrayList, currentcity_arrayListCoBr;
    public static ArrayList<BorrowerCurrentCityPersonalPOJO> borrowerCurrentCityPersonalPOJOArrayList, borrowerCurrentCityPersonalPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_currentState, arrayAdapter_currentStateCoBr;
    public static ArrayList<String> currentstate_arrayList, currentstate_arrayListCoBr;
    public static ArrayList<BorrowerCurrentStatePersonalPOJO> borrowerCurrentStatePersonalPOJOArrayList, borrowerCurrentStatePersonalPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_currentCountry, arrayAdapter_currentCountryCoBr;
    public static ArrayList<String> currentCountry_arrayList, currentCountry_arrayListCoBr;
    public static ArrayList<BorrowerCurrentCountryPersonalPOJO> borrowerCurrentCountryPersonalPOJOArrayList, borrowerCurrentCountryPersonalPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_profession;
    public static ArrayList<String> profession_arrayList;
    public static ArrayList<ProfessionPOJO> professionPOJOArrayList;

    public static ArrayAdapter arrayAdapter_professionCoBr;
    public static ArrayList<String> profession_arrayListCoBr;
    public static ArrayList<ProfessionPOJO> professionPOJOArrayListCoBr;

    public static ArrayAdapter arrayAdapter_NameOfInsititue;
    public static ArrayList<String> nameofinstitute_arrayList;
    public static ArrayList<NameOfInsitituePOJO> nameOfInsitituePOJOArrayList;

    public static ArrayAdapter arrayAdapter_NameOfCourse;
    public static ArrayList<String> nameofcourse_arrayList;
    public static ArrayList<NameOfCoursePOJO> nameOfCoursePOJOArrayList;

    public static ArrayAdapter arrayAdapter_locations;
    public static ArrayList<String> locations_arrayList;
    public static ArrayList<LocationsPOJO> locationPOJOArrayList;

    public static FragmentTransaction transaction;
    public static ProgressBar progressBar;
    public static String userID = "", borrowerBackground = "", coBorrowerBackground = "";
    static View view;
    public static String dateformate = "", mobileNo = "";
    public static String currentcityID = "", currentstateID = "", currentcountryID = "1",
            currentcityIDCoBr = "", currentstateIDCoBr = "", currentcountryIDCoBr = "1";
    public static String instituteID = "", courseID = "", locationID = "", professionID = "", professionIDCoBr = "";

    Button buttonNext;
    TextView textView1, textView2, textView3;
    Calendar cal;
    MainApplication mainApplication;
    Typeface typeface;
    LinearLayout linearLayoutEmployed, linearLayoutLeftoff, linEmployed;
    public static CheckBox cbSameAsAboveBr;
    public static RelativeLayout relborrower, relCoborrower;
    public static LinearLayout linBorrowerForm, linCoCorrowerForm;
    public static TextView txtBorrowerArrowKey, txtCoBorrowerArrowKey;
    public static int borrowerVisiblity = 0, coborrowerVisiblity = 1;

    public static String lead_id = "", has_coborrower = "", application_id = "", requested_loan_amount = "", institute_name = "",
            location_name = "", course_name = "", course_cost = "", fk_institutes_id = "", fk_insitutes_location_id = "",
            fk_course_id = "";

    public static String Brapplicant_id = "", Brfk_lead_id = "", Brfk_applicant_type_id = "", Brfirst_name = "", Brmiddle_name = "",
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

    public static String CoBrapplicant_id = "", CoBrfk_lead_id = "", CoBrfk_applicant_type_id = "", CoBrfirst_name = "", CoBrmiddle_name = "",
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

    public static String lead_status_id = "", fk_lead_id = "", lead_status = "", lead_sub_status = "", current_stage = "",
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

    public LoanApplicationFragment_1() {
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
            mFragment = new LoanApplicationFragment_1();
            transaction = getFragmentManager().beginTransaction();
            MainApplication.currrentFrag = 1;
            typeface = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");

            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                userID = sharedPreferences.getString("logged_id", "null");
                borrowerBackground = sharedPreferences.getString("borrowerBackground_dark", "0");
                coBorrowerBackground = sharedPreferences.getString("coBorrowerBackground_dark", "0");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                mobileNo = sharedPreferences.getString("mobile_no", "null");

            } catch (Exception e) {
                e.printStackTrace();
            }

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
                        setCoborrower();

                    } else if (coborrowerVisiblity == 1) {
                        linCoCorrowerForm.setVisibility(View.GONE);
                        coborrowerVisiblity = 0;
                        txtCoBorrowerArrowKey.setText(getResources().getString(R.string.down));
                        txtCoBorrowerArrowKey.setTypeface(typeface);
                    }

                }
            });

            btnAddCoborrower.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    btnAddCoborrower.setVisibility(View.GONE);
                    relCoborrower.setVisibility(View.VISIBLE);
                    linCoCorrowerForm.setVisibility(View.VISIBLE);
                    coborrowerVisiblity = 1;
                    txtCoBorrowerArrowKey.setText(getResources().getString(R.string.up));
                    txtCoBorrowerArrowKey.setTypeface(typeface);
                    setCoborrower();

                }
            });

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LoanApplicationFragment_2 loanApplicationFragment_2 = new LoanApplicationFragment_2();
                    transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_2).commit();
                }
//                public void onClick(View v) {
//
//                    if (!edtFnameBr.getText().toString().equals("") && !edtLnameBr.getText().toString().equals("") &&
//                            !txtBirthdateBr.getText().toString().equals("") && !edtEmailIdBr.getText().toString().equals("") &&
//                            !edtCurrentAddressBr.getText().toString().equals("") && !edtCurrentPincodeBr.getText().toString().equals("") &&
//                            !edtAadhaarBr.getText().toString().equals("") && !edtPanBr.getText().toString().equals("")) {
//
//                        if (rgGenderBr.getCheckedRadioButtonId() > 0) {
//                            rbFemaleBr.setError(null);
//
//                            if (!currentcityID.equalsIgnoreCase("") && !currentstateID.equalsIgnoreCase("") &&
//                                    !currentcountryID.equalsIgnoreCase("") && !instituteID.equalsIgnoreCase("") &&
//                                    !courseID.equalsIgnoreCase("") && !locationID.equalsIgnoreCase("")) {
//
//                                try {
//
//                                    String gender = "";
//                                    if (rbMaleBr.isChecked()) {
//                                        gender = "1";
//                                    }
//                                    if (rbFemaleBr.isChecked()) {
//                                        gender = "2";
//                                    }
//
//                                    progressBar.setVisibility(View.VISIBLE);
//                                    String url = MainApplication.mainUrl + "dashboard/editKycDetails";
//                                    Map<String, String> params = new HashMap<String, String>();
//
//                                    params.put("lead_id", MainApplication.lead_id);
//                                    params.put("fk_institutes_id", instituteID);
//                                    params.put("fk_insitutes_location_id", locationID);
//                                    params.put("fk_course_id", courseID);
//                                    params.put("requested_loan_amount", "");
//                                    params.put("applicant_id", MainApplication.application_id);
//                                    params.put("profession", "");
//                                    params.put("first_name", edtFnameBr.getText().toString());
//                                    params.put("middle_name", edtMnameBr.getText().toString());
//                                    params.put("last_name", edtLnameBr.getText().toString());
//                                    params.put("dob", "");
//                                    params.put("gender_id", gender);
//                                    params.put("mobile_number", mobileNo);
//                                    params.put("email_id", edtEmailIdBr.getText().toString());
//                                    params.put("pan_number", edtPanBr.getText().toString());
//                                    params.put("aadhar_number", edtAadhaarBr.getText().toString());
//                                    params.put("employer_name", edtCompanyBr.getText().toString());
//                                    params.put("annual_income", edtAnnualSalBr.getText().toString());
//                                    params.put("kyc_address", edtCurrentAddressBr.getText().toString());
//                                    params.put("kyc_landmark", edtCurrentLandmarkBr.getText().toString());
//                                    params.put("kyc_address_pin", edtCurrentPincodeBr.getText().toString());
//                                    params.put("kyc_address_country", currentcountryID);
//                                    params.put("kyc_address_state", currentstateID);
//                                    params.put("kyc_address_city", currentcityID);
//
//                                    params.put("coapplicant_id", "");
//                                    params.put("cofirst_name", "");
//                                    params.put("comiddle_name", "");
//                                    params.put("colast_name", "");
//                                    params.put("codob", "");
//                                    params.put("cogender_id", "");
//                                    params.put("comobile_number", "");
//                                    params.put("coemail_id", "");
//                                    params.put("copan_number", "");
//                                    params.put("coaadhar_number", "");
//                                    params.put("coemployer_name", "");
//                                    params.put("coannual_income", "");
//                                    params.put("cokyc_address", "");
//                                    params.put("cokyc_landmark", "");
//                                    params.put("cokyc_address_pin", "");
//                                    params.put("cokyc_address_country", "");
//                                    params.put("cokyc_address_state", "");
//                                    params.put("cokyc_address_city", "");
//                                    params.put("has_coborrower", "");
//
//                                    if (!Globle.isNetworkAvailable(context)) {
//                                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        VolleyCallNew volleyCall = new VolleyCallNew();
//                                        volleyCall.sendRequest(context, url, null, mFragment, "editKycDetails", params, MainApplication.auth_token);//http://159.89.204.41/eduvanzApi/algo/setBorrowerLoanDetails
//                                    }
//
//                                } catch (Exception e) {
//                                    String className = this.getClass().getSimpleName();
//                                    String name = new Object() {
//                                    }.getClass().getEnclosingMethod().getName();
//                                    String errorMsg = e.getMessage();
//                                    String errorMsgDetails = e.getStackTrace().toString();
//                                    String errorLine = String.valueOf(e.getStackTrace()[0]);
//                                    Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
//                                }
//                            } else {
//
//                                if (spInstituteBr.getSelectedItemPosition() <= 0) {
//                                    setSpinnerError(spInstituteBr, getString(R.string.please_select_institue_name));
//                                    spInstituteBr.requestFocus();
//                                }
//                                if (spCourseBr.getSelectedItemPosition() <= 0) {
//                                    setSpinnerError(spCourseBr, getString(R.string.please_select_course_name));
//                                    spCourseBr.requestFocus();
//                                }
//                                if (spInsLocationBr.getSelectedItemPosition() <= 0) {
//                                    setSpinnerError(spInsLocationBr, getString(R.string.please_select_institue_location));
//                                    spInsLocationBr.requestFocus();
//                                }
//                                if (spCurrentCountryBr.getSelectedItemPosition() <= 0) {
//                                    setSpinnerError(spCurrentCountryBr, getString(R.string.please_select_duration_of_stay_at_current_address));
//                                    spCurrentCountryBr.requestFocus();
//                                }
//                                if (spCurrentStateBr.getSelectedItemPosition() <= 0) {
//                                    setSpinnerError(spCurrentStateBr, getString(R.string.please_select_last_degree_completed));
//                                    spCurrentStateBr.requestFocus();
//                                }
//                                if (spCurrentCityBr.getSelectedItemPosition() <= 0) {
//                                    setSpinnerError(spCurrentCityBr, getString(R.string.please_select_duration_of_job_business));
//                                    spCurrentCityBr.requestFocus();
//                                }
//                                Toast.makeText(context, R.string.please_fill_up_all_the_details_to_continue, Toast.LENGTH_LONG).show();
//                            }
//
//                        } else {
//                            rbFemaleBr.setError(getString(R.string.you_need_to_select_gender));
//                            rbFemaleBr.requestFocus();
//                        }
//                    } else {
//
//                        if (edtCourseFeeBr.getText().toString().equalsIgnoreCase("")) {
//                            edtCourseFeeBr.setError(getString(R.string.first_name_is_required));
//                            edtCourseFeeBr.requestFocus();
//                        } else {
//                            edtCourseFeeBr.setError(null);
//
//                        }
//                        if (edtLoanAmtBr.getText().toString().equalsIgnoreCase("")) {
//                            edtLoanAmtBr.setError(getString(R.string.first_name_is_required));
//                            edtLoanAmtBr.requestFocus();
//                        } else {
//                            edtLoanAmtBr.setError(null);
//
//                        }
//                        if (edtFnameBr.getText().toString().equalsIgnoreCase("")) {
//                            edtFnameBr.setError(getString(R.string.first_name_is_required));
//                            edtFnameBr.requestFocus();
//                        } else {
//                            edtFnameBr.setError(null);
//
//                        }
//                        if (edtLnameBr.getText().toString().equalsIgnoreCase("")) {
//                            edtLnameBr.setError(getString(R.string.last_name_is_required));
//                            edtLnameBr.requestFocus();
//                        } else {
//                            edtLnameBr.setError(null);
//
//                        }
//                        if (edtEmailIdBr.getText().toString().equalsIgnoreCase("")) {
//                            edtEmailIdBr.setError(getString(R.string.emailid_is_required));
//                            edtEmailIdBr.requestFocus();
//                        } else {
//                            edtEmailIdBr.setError(null);
//
//                        }
//                        if (txtBirthdateBr.getText().toString().equalsIgnoreCase("")) {
//                            txtBirthdateBr.setError(getString(R.string.birthdate_is_required));
//                            txtBirthdateBr.requestFocus();
//                        } else if (txtBirthdateBr.getText().toString().toLowerCase().equals("birthdate")) {
//                            txtBirthdateBr.setError(getString(R.string.birthdate_is_required));
//                            txtBirthdateBr.requestFocus();
//                        } else {
//                            txtBirthdateBr.setError(null);
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
//                        if (edtPanBr.getText().toString().equalsIgnoreCase("")) {
//                            edtPanBr.setError(getString(R.string.pan_number_is_required));
//                            edtPanBr.requestFocus();
//                        } else {
//                            edtPanBr.setError(null);
//                        }
//
//                        if (edtCurrentAddressBr.getText().toString().equalsIgnoreCase("")) {
//                            edtCurrentAddressBr.setError(getString(R.string.flat_no_building_name_society_name));
//                            edtCurrentAddressBr.requestFocus();
//                        } else {
//                            edtCurrentAddressBr.setError(null);
//                        }
//
//                        if (edtCurrentLandmarkBr.getText().toString().equalsIgnoreCase("")) {
//                            edtCurrentLandmarkBr.setError(getString(R.string.street_name_locality_land_mark));
//                            edtCurrentLandmarkBr.requestFocus();
//                        } else {
//                            edtCurrentLandmarkBr.setError(null);
//                        }
//
//                        if (edtCurrentPincodeBr.getText().toString().equalsIgnoreCase("")) {
//                            edtCurrentPincodeBr.setError(getString(R.string.current_pin_code_is_required));
//                            edtCurrentPincodeBr.requestFocus();
//                        } else {
//                            edtCurrentPincodeBr.setError(null);
//                        }
//
//                        if (edtCompanyBr.getText().toString().equalsIgnoreCase("")) {
//                            edtCompanyBr.setError(getString(R.string.name_of_the_company_is_required));
//                            edtCompanyBr.requestFocus();
//                        } else {
//                            edtCompanyBr.setError(null);
//                        }
//                        if (edtAnnualSalBr.getText().toString().equalsIgnoreCase("")) {
//                            edtAnnualSalBr.setError(getString(R.string.annual_income_is_required));
//                            edtAnnualSalBr.requestFocus();
//                        } else {
//                            edtAnnualSalBr.setError(null);
//
//                        }
//                        if (spCurrentCountryBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spCurrentCountryBr, getString(R.string.please_select_current_country));
//
//                        } else if (spCurrentStateBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spCurrentStateBr, getString(R.string.please_select_current_state));
//
//                        } else if (spCurrentCityBr.getSelectedItemPosition() <= 0) {
//                            setSpinnerError(spCurrentCityBr, getString(R.string.please_select_current_city));
//
//                        }
//
//                    }
//                }
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
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, monthOfYear);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    int month = monthOfYear + 1;
                    String datenew = dayOfMonth + "/" + month + "/" + year;
                    dateformate = dateFormateSystem(datenew);
                    txtBirthdateBr.setText(dateformate);
                    MainApplication.borrowerValue15 = dateformate;
                    txtBirthdateBr.setTextColor(getResources().getColor(R.color.black));
                    lblBirthdayBr.setVisibility(View.VISIBLE);
                }

            };
            cal = Calendar.getInstance();

            txtBirthdayCalenderCoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
                    DatePickerDialog data = new DatePickerDialog(context, dateBr, cal
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
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, monthOfYear);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    int month = monthOfYear + 1;
                    String datenew = dayOfMonth + "/" + month + "/" + year;
                    dateformate = dateFormateSystem(datenew);
                    txtBirthdateCoBr.setText(dateformate);
                    MainApplication.borrowerValue15 = dateformate;
                    txtBirthdateCoBr.setTextColor(getResources().getColor(R.color.black));
                    lblBirthdayBr.setVisibility(View.VISIBLE);
                }

            };
            cal = Calendar.getInstance();

            txtBirthdayCalenderCoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
                    DatePickerDialog data = new DatePickerDialog(context, dateCoBr, cal
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

            edtAnnualSalBr.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    MainApplication.borrowerValue27 = edtAnnualSalBr.getText().toString();
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
                    MainApplication.borrowerValue25 = edtCompanyBr.getText().toString();
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

                    try {
                        String text = spInstituteBr.getSelectedItem().toString();
                        int count = nameOfInsitituePOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (nameOfInsitituePOJOArrayList.get(i).instituteName.equalsIgnoreCase(text)) {
                                MainApplication.mainapp_instituteID = instituteID = nameOfInsitituePOJOArrayList.get(i).instituteID;
                                Log.e("I_________D", "onItemClickIns: " + instituteID);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    courseApiCall();
//                    if (MainApplication.mainapp_courseID.equals("")) {
//                        spCourseBr.setSelection(0);
//                    } else {
//                        spCourseBr.setSelection(Integer.parseInt(MainApplication.mainapp_courseID) - 1);
//                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spCourseBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    try {
                        String text = spCourseBr.getSelectedItem().toString();
                        int count = nameOfCoursePOJOArrayList.size();
                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (nameOfCoursePOJOArrayList.get(i).courseName.equalsIgnoreCase(text)) {
                                MainApplication.mainapp_courseID = courseID = nameOfCoursePOJOArrayList.get(i).courseID;
                                Log.e("I_________D", "onItemClickCourse: " + courseID);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
                    try {
                        String text = spInsLocationBr.getSelectedItem().toString();
                        int count = locationPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (locationPOJOArrayList.get(i).locationName.equalsIgnoreCase(text)) {
                                MainApplication.mainapp_locationID = locationID = locationPOJOArrayList.get(i).locationID;
                                Log.e("I_________D", "onItemClickLoc: " + locationID);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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

                    String text = spProfessionCoBr.getSelectedItem().toString();

                    int count = professionPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (professionPOJOArrayList.get(i).Salaried.equalsIgnoreCase(text)) {
                            professionID = professionPOJOArrayList.get(i).id;
                        }
                    }
                    if(professionID.equals("0") || professionID.equals("2"))
                    {
                        try {
                            linEmployed.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        try {
                            linEmployed.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

//                    String text = spProfessionBr.getSelectedItem().toString();
//                    if (text.equalsIgnoreCase("Select Any")) {
//                        MainApplication.mainapp_userprofession = "0";
//                        MainApplication.profession = "0";
//                        try {
//                            linEmployed.setVisibility(View.GONE);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    } else if (text.equalsIgnoreCase("Student")) {
//                        MainApplication.mainapp_userprofession = "Student";
//                        MainApplication.mainapp_userprofession = "1";
//                        MainApplication.profession = "1";
//                        try {
//                            linEmployed.setVisibility(View.GONE);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    } else if (text.equalsIgnoreCase("Employed")) {
//                        MainApplication.mainapp_userprofession = "employed";
//                        MainApplication.mainapp_userprofession = "2";
//                        MainApplication.profession = "2";
//                        try {
//                            linEmployed.setVisibility(View.VISIBLE);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    } else if (text.equalsIgnoreCase("Self Employed")) {
//                        MainApplication.mainapp_userprofession = "selfEmployed";
//                        MainApplication.mainapp_userprofession = "3";
//                        MainApplication.profession = "3";
//                        try {
//                            linEmployed.setVisibility(View.VISIBLE);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
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
                             professionIDCoBr = professionPOJOArrayListCoBr.get(i).id;
                        }
                    }
                    if(professionIDCoBr.equals("0") || professionIDCoBr.equals("2"))
                    {
//                        try {
//                            linEmployed.setVisibility(View.GONE);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }else{
//                        try {
//                            linEmployed.setVisibility(View.VISIBLE);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }
//                    if (text.equalsIgnoreCase("Select Any")) {
//                        MainApplication.mainapp_userprofession = "0";
//                        MainApplication.profession = "0";
//
//                    } else if (text.equalsIgnoreCase("Student")) {
//                        MainApplication.mainapp_userprofession = "Student";
//                        MainApplication.mainapp_userprofession = "1";
//                        MainApplication.profession = "1";
//
//                    } else if (text.equalsIgnoreCase("Employed")) {
//                        MainApplication.mainapp_userprofession = "employed";
//                        MainApplication.mainapp_userprofession = "2";
//                        MainApplication.profession = "2";
//
//                    } else if (text.equalsIgnoreCase("Self Employed")) {
//                        MainApplication.mainapp_userprofession = "selfEmployed";
//                        MainApplication.mainapp_userprofession = "3";
//                        MainApplication.profession = "3";
//
//                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spCurrentCityBr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
//                        if (spCurrentCityBr.getSelectedItemPosition() > 0) {
//                            cbSameAsAboveBr.setEnabled(true);
//                            cbSameAsAboveBr.setClickable(true);
//                        } else {
//                            cbSameAsAboveBr.setEnabled(false);
//                            cbSameAsAboveBr.setClickable(false);
//                        }
                        String text = spCurrentCityBr.getSelectedItem().toString();
                        int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                                MainApplication.borrowerValue4 = currentcityID = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
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
                                MainApplication.borrowerValue5 = currentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
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
                                MainApplication.borrowerValue4 = currentcityIDCoBr = borrowerCurrentCityPersonalPOJOArrayListCoBr.get(i).cityID;
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
                                MainApplication.borrowerValue5 = currentstateIDCoBr = borrowerCurrentStatePersonalPOJOArrayListCoBr.get(i).stateID;
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
                                MainApplication.borrowerValue6 = currentcountryIDCoBr = borrowerCurrentCountryPersonalPOJOArrayListCoBr.get(i).countryID;
                            }
                        }
                        stateApiCallCoBr();
                        if (currentcityIDCoBr.equals("")) {
                            spCurrentCityCoBr.setSelection(0);
                        } else {
                            spCurrentCityCoBr.setSelection(Integer.parseInt(currentcityIDCoBr) - 1);
                        }
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
//                instituteName(jsonObject);
//                courseName(jsonObject);
//                locationName(jsonObject);
                instituteApiCall();
//              getAllProfessionkyc(jsonObject);
                ProfessionApiCall();
                ProfessionApiCallCoBr();
                getCurrentStates(jsonObject);
                getCurrentCities(jsonObject);
                getCurrentStatesCoBr(jsonObject);
                getCurrentCitiesCoBr(jsonObject);
            }

            /** API CALL POST LOGIN DASHBOARD STATUS **/

            try {
                String url = MainApplication.mainUrl + "dashboard/getKycDetails";
                Map<String, String> params = new HashMap<String, String>();
                params.put("lead_id", MainApplication.lead_id);
//                params.put("student_id", MainApplication.student_id);
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
            String message = jsonData.optString("message");

            if (jsonData.getInt("status") == 1) {

                if (jsonData.getJSONArray("countries").length() > 0) {
                    JSONArray jsonArraycountries = jsonData.getJSONArray("countries");
                }else {
//                    JSONObject jsonObject = new JSONObject();
//                    getCurrentStates(jsonObject);
//                    getCurrentCities(jsonObject);
////                getCurrentStatesCoBr(jsonObject);
////                getCurrentCitiesCoBr(jsonObject);
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

                    if (!course_cost.equals("null")) {
                        edtCourseFeeBr.setText(course_cost);
                    }
                    if (!requested_loan_amount.equals("null")) {
                        edtLoanAmtBr.setText(requested_loan_amount);
                    }
                    if (!fk_institutes_id.equals("null")) {
                        MainApplication.mainapp_instituteID = instituteID = fk_institutes_id;
                        spInstituteBr.setSelection(Integer.parseInt(instituteID));
                    }
                    if (!fk_course_id.equals("null")) {
                        MainApplication.mainapp_courseID = courseID = fk_course_id;
                        spCourseBr.setSelection(Integer.parseInt(courseID));
                    }
                    if (!fk_insitutes_location_id.equals("null")) {
                        MainApplication.mainapp_locationID = locationID = fk_insitutes_location_id;
                        spInsLocationBr.setSelection(Integer.parseInt(locationID));
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

                    if (!Brfirst_name.equals("null")) {
                        edtFnameBr.setText(Brfirst_name);
                    }
                    if (!Brmiddle_name.equals("null")) {
                        edtMnameBr.setText(Brmiddle_name);
                    }
                    if (!Brlast_name.equals("null")) {
                        edtLnameBr.setText(Brlast_name);
                    }
                    if (!Bremail_id.equals("null")) {
                        edtEmailIdBr.setText(Bremail_id);
                    }
                    if (Brhas_aadhar_pan.equals("1")) {
                        edtAadhaarBr.setVisibility(View.VISIBLE);
                        edtPanBr.setVisibility(View.GONE);
                        if (!Braadhar_number.equals("null")) {
                            edtAadhaarBr.setText(Braadhar_number);
                        }
                    } else if (Brhas_aadhar_pan.equals("2")) {
                        edtPanBr.setVisibility(View.VISIBLE);
                        edtAadhaarBr.setVisibility(View.GONE);
                        if (!Brpan_number.equals("null")) {
                            edtPanBr.setText(Brpan_number);
                        }
                    } else if (Brhas_aadhar_pan.equals("3") || Brhas_aadhar_pan.equals("0")) {
                        edtAadhaarBr.setVisibility(View.VISIBLE);
                        edtPanBr.setVisibility(View.VISIBLE);
                        if (!Braadhar_number.equals("null")) {
                            edtAadhaarBr.setText(Braadhar_number);
                        }
                        if (!Brpan_number.equals("null")) {
                            edtPanBr.setText(Brpan_number);
                        }
                    } else {
                        edtAadhaarBr.setVisibility(View.GONE);
                        edtPanBr.setVisibility(View.GONE);
                    }

                    if (!Bremployer_name.equals("null")) {
                        edtCompanyBr.setText(Bremployer_name);
                    }
                    if (!Brannual_income.equals("null")) {
                        edtAnnualSalBr.setText(Brannual_income);
                    }
                    if (!Brkyc_address.equals("null")) {
                        edtCurrentAddressBr.setText(Brkyc_address);
                    }
                    if (!Brkyc_landmark.equals("null")) {
                        edtCurrentLandmarkBr.setText(Brkyc_landmark);
                    }
                    if (!Brkyc_address_pin.equals("null")) {
                        edtCurrentPincodeBr.setText(Brkyc_address_pin);
                    }

                    if (!Brdob.equals("null")) {
                        txtBirthdateBr.setText(Brdob);
                        txtBirthdateBr.setTextColor(Color.BLACK);
                        lblBirthdayBr.setVisibility(View.VISIBLE);
                    } else {
                        txtBirthdateBr.setText(R.string.birthdate);
                    }

                    if (!Brgender_id.equals("null")) {
                        if (Brgender_id.equalsIgnoreCase("1")) {
                            rbMaleBr.setChecked(true);
                        } else if (Brgender_id.equalsIgnoreCase("2")) {
                            rbFemaleBr.setChecked(true);
                        }
                    }

                    if (!Brprofession.equals("null")) {
                        if (Brprofession.equals("")) {
                            professionID = "0";
                        } else {
                            professionID = "1";
                        }
                        spProfessionBr.setSelection(Integer.parseInt(professionID));
                    }
                    if (!Brkyc_address_country.equals("null")) {
                        currentcountryID = "1";
                        spCurrentCountryBr.setSelection(Integer.parseInt(currentcountryID));
                    }
                    if (!Brkyc_address_state.equals("null")) {
                        currentstateID = Brkyc_address_state;
                        spCurrentStateBr.setSelection(Integer.parseInt(currentstateID));
                    }
                    if (!Brkyc_address_city.equals("null")) {
                        currentcityID = Brkyc_address_city;
                        spCurrentCityBr.setSelection(Integer.parseInt(currentcityID));
                    }

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

                String courseAmount = String.valueOf(jsonData.get("courseAmount"));

                if (has_coborrower.equals("0") && current_status.equals("1")) {
                    try {
                        btnAddCoborrower.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        btnAddCoborrower.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (has_coborrower.equals("0")) {
                    try {
                        coborrowerVisiblity = 1;
                        relCoborrower.setVisibility(View.GONE);
                        linCoCorrowerForm.setVisibility(View.GONE);
                        txtCoBorrowerArrowKey.setText(getResources().getString(R.string.down));
                        txtCoBorrowerArrowKey.setTypeface(typeface);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        coborrowerVisiblity = 0;
                        relCoborrower.setVisibility(View.VISIBLE);
                        linCoCorrowerForm.setVisibility(View.VISIBLE);
                        txtCoBorrowerArrowKey.setText(getResources().getString(R.string.up));
                        txtCoBorrowerArrowKey.setTypeface(typeface);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


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
            params.put("institute_id", instituteID);
            params.put("course_id", courseID);
            params.put("location_id", locationID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "courseFeekyc", params, MainApplication.auth_token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void instituteName(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {

                    nameofinstitute_arrayList = new ArrayList<>();
                    nameofinstitute_arrayList.add("Select Institute");
                    arrayAdapter_NameOfInsititue = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofinstitute_arrayList);
                    spInstituteBr.setAdapter(arrayAdapter_NameOfInsititue);
                    arrayAdapter_NameOfInsititue.notifyDataSetChanged();
                    spInstituteBr.setSelection(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = jsonData.getJSONArray("result");

                    nameOfInsitituePOJOArrayList = new ArrayList<>();
                    nameofinstitute_arrayList = new ArrayList<>();
//                    nameofinstitute_arrayList.add("Select Institute");
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
            if (jsonData.toString().equals("{}")) {
                try {
                    nameofcourse_arrayList = new ArrayList<>();
                    nameofcourse_arrayList.add("Select Course");
                    arrayAdapter_NameOfCourse = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofcourse_arrayList);
                    spCourseBr.setAdapter(arrayAdapter_NameOfCourse);
                    arrayAdapter_NameOfCourse.notifyDataSetChanged();
                    spCourseBr.setSelection(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = jsonData.getJSONArray("result");

                    nameOfCoursePOJOArrayList = new ArrayList<>();
                    nameofcourse_arrayList = new ArrayList<>();
//                    nameofcourse_arrayList.add("Select Course");
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
//                try {
//                    spInsLocationBr.setSelection(Integer.parseInt(MainApplication.mainapp_locationID));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
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
            if (jsonData.toString().equals("{}")) {
                try {
                    locations_arrayList = new ArrayList<>();
                    locations_arrayList.add("Select Institute Location");
                    arrayAdapter_locations = new ArrayAdapter(context, R.layout.custom_layout_spinner, locations_arrayList);
                    spInsLocationBr.setAdapter(arrayAdapter_locations);
                    arrayAdapter_locations.notifyDataSetChanged();
                    spInsLocationBr.setSelection(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                String status = jsonData.optString("status");
                String message = jsonData.optString("message");

                if (status.equalsIgnoreCase("1")) {

//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = jsonData.getJSONArray("result");

                    locationPOJOArrayList = new ArrayList<>();
                    locations_arrayList = new ArrayList<>();
//                    locations_arrayList.add("Select Institute Location");
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

    private void ProfessionApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "dashboard/getAllProfession";
            Map<String, String> params = new HashMap<String, String>();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getAllProfessionkyc", params, MainApplication.auth_token);
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

    public void getAllProfessionkyc(JSONObject jsonData) {
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

    private void ProfessionApiCallCoBr() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "dashboard/getAllProfession";
            Map<String, String> params = new HashMap<String, String>();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "getAllProfessionkycCoBr", params, MainApplication.auth_token);
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

    public void getAllProfessionkycCoBr(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    profession_arrayListCoBr = new ArrayList<>();
                    profession_arrayListCoBr.add("Select Profession");
                    arrayAdapter_professionCoBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayListCoBr);
                    spProfessionCoBr.setAdapter(arrayAdapter_professionCoBr);
                    arrayAdapter_professionCoBr.notifyDataSetChanged();
                    spProfessionCoBr.setSelection(0);

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
                        if(mJsonti.getString("id").equals("2")){
                        }else {
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

            btnAddCoborrower = (Button) view.findViewById(R.id.btnAddCoborrower);

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

            rgGenderCoBr = (RadioGroup) view.findViewById(R.id.rgGenderCoBr);

            rbMaleCoBr = (RadioButton) view.findViewById(R.id.rbMaleCoBr);
            rbFemaleCoBr = (RadioButton) view.findViewById(R.id.rbFemaleCoBr);

            spProfessionCoBr = (Spinner) view.findViewById(R.id.spProfessionCoBr);
            spCurrentCountryCoBr = (Spinner) view.findViewById(R.id.spCurrentCountryCoBr);
            spCurrentStateCoBr = (Spinner) view.findViewById(R.id.spCurrentStateCoBr);
            spCurrentCityCoBr = (Spinner) view.findViewById(R.id.spCurrentCityCoBr);

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
//            spCurrentCountryCoBr.setSelection(1);

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

    private void setCoborrower() {

        try {

            if (!CoBrfirst_name.equals("null")) {
                edtFnameCoBr.setText(CoBrfirst_name);
            }
            if (!CoBrmiddle_name.equals("null")) {
                edtMnameCoBr.setText(CoBrmiddle_name);
            }
            if (!CoBrlast_name.equals("null")) {
                edtLnameCoBr.setText(CoBrlast_name);
            }
            if (!CoBremail_id.equals("null")) {
                edtEmailIdCoBr.setText(CoBremail_id);
            }
            if (!CoBrpan_number.equals("null")) {
                edtPanCoBr.setText(CoBrpan_number);
            }
            if (!CoBraadhar_number.equals("null")) {
                edtAadhaarCoBr.setText(CoBraadhar_number);
            }
            if (!CoBremployer_name.equals("null")) {
                edtCompanyCoBr.setText(CoBremployer_name);
            }
            if (!CoBrannual_income.equals("null")) {
                edtAnnualSalCoBr.setText(CoBrannual_income);
            }
            if (!CoBrkyc_address.equals("null")) {
                edtCurrentAddressCoBr.setText(CoBrkyc_address);
            }
            if (!CoBrkyc_landmark.equals("null")) {
                edtCurrentLandmarkCoBr.setText(CoBrkyc_landmark);
            }
            if (!CoBrkyc_address_pin.equals("null")) {
                edtCurrentPincodeCoBr.setText(CoBrkyc_address_pin);
            }

            if (!CoBrdob.equals("null")) {
                txtBirthdateCoBr.setText(CoBrdob);
                txtBirthdateCoBr.setTextColor(Color.BLACK);
                lblBirthdayCoBr.setVisibility(View.VISIBLE);
            } else {
                txtBirthdateCoBr.setText(R.string.birthdate);
            }

            if (!CoBrgender_id.equals("null")) {
                if (CoBrgender_id.equalsIgnoreCase("1")) {
                    rbMaleCoBr.setChecked(true);
                } else if (CoBrgender_id.equalsIgnoreCase("2")) {
                    rbFemaleCoBr.setChecked(true);
                }
            }

            if (!CoBrprofession.equals("null")) {
                professionIDCoBr = CoBrprofession;
               spProfessionCoBr.setSelection(Integer.parseInt(professionIDCoBr));
            }

            if (!CoBrkyc_address.equals("null")) {
                currentcountryIDCoBr = "1";
                spCurrentCountryCoBr.setSelection(Integer.parseInt(currentcountryIDCoBr));
            }
            if (!CoBrkyc_address_state.equals("null")) {
                currentstateIDCoBr = CoBrkyc_address_state;
                spCurrentStateCoBr.setSelection(Integer.parseInt(currentstateIDCoBr));
            }
            if (!CoBrkyc_address_city.equals("null")) {
                currentcityIDCoBr = CoBrkyc_address_city;
                spCurrentCityCoBr.setSelection(Integer.parseInt(currentcityIDCoBr));
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
                    if (!currentstateIDCoBr.equals("")) {
                        spCurrentStateCoBr.setSelection(Integer.parseInt(currentstateIDCoBr));
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
                    arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayListCoBr);
                    spCurrentCityCoBr.setAdapter(arrayAdapter_currentCityCoBr);
                    arrayAdapter_currentCityCoBr.notifyDataSetChanged();
                    spCurrentCityCoBr.setSelection(0);
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

                    int count = borrowerCurrentCityPersonalPOJOArrayList.size();
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
