package com.eduvanzapplication.newUI.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceDurationPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceTypePersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentStatePersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerOffCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerOffCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerOffStatePersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerPermanentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerPermanentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerPermanentStatePersonalPOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerCurrentResidenceDurationPersonalPOJO;
import com.eduvanzapplication.fqform.coborrowerdetail.pojo.CoborrowerCurrentResidenceTypePersonalPOJO;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.model.CountryModel;
import com.eduvanzapplication.newUI.newViews.LoanTabActivity;
import com.eduvanzapplication.newUI.pojo.AddressSameAsCurrentPOJO;
import com.eduvanzapplication.newUI.pojo.ProfessionPOJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import moe.feng.common.stepperview.VerticalStepperItemView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class DetailedInfoFragment extends Fragment {

    public static ProgressDialog progressDialog;

    public static ViewPager viewPager;
    public static ImageButton btnEditDetailedInfo, btnNext;
    public static TextView txtResidentialToggle, txtProfessionalToggle;
    public static LinearLayout linResidentialBlock, linProfessionalBlock;
    public static Animation collapseanimationResidential, expandAnimationResidential,
            collapseanimationProfessional, expandAnimationProfessional;
    public static onDetailedInfoFragmentInteractionListener mListener;

    //Borrower
    public static EditText edtCourseFeeBr, edtLoanAmtBr, edtPermanentPincode, edtPermanentAddress, edtPermanentLandmark,
            edtPincodeOff, edtAddressOff, edtLandmarkOff, edtMonthlyRent, edtCompanyName, edtAnnualIncome;

    public static Switch switchIsPermanentAddressSame, switchResidenceType;

    public static Spinner spPermanentCountry, spPermanentState, spPermanentCity, spCountryOff, spStateOff, spCityOff, spOwnedBy,
            spProfession, spDurarionOfStay, spDurationOfJob, spEmployerType;

    public static LinearLayout linMonthlyRent;

    //Country
    public static ArrayList<String> permanentCountry_arrayList, offCountry_arrayList;
    public static ArrayList<BorrowerPermanentCountryPersonalPOJO> borrowerPermanentCountryPersonalPOJOArrayList;
    public static ArrayList<BorrowerOffCountryPersonalPOJO> borrowerOffCountryPersonalPOJOArrayList;
    public static ArrayAdapter arrayAdapter_permanentCountry, arrayAdapter_offCountry;

    //City
    public static ArrayAdapter arrayAdapter_permanentCity, arrayAdapter_offCity;
    public static ArrayList<String> permanentcity_arrayList, offcity_arrayList;
    public static ArrayList<BorrowerPermanentCityPersonalPOJO> borrowerPermanentCityPersonalPOJOArrayList;
    public static ArrayList<BorrowerOffCityPersonalPOJO> borrowerOffCityPersonalPOJOArrayList;

    //State
    public static ArrayAdapter arrayAdapter_permanentState, arrayAdapter_offState;
    public static ArrayList<String> permanentstate_arrayList, offstate_arrayList;
    public static ArrayList<BorrowerPermanentStatePersonalPOJO> borrowerPermanentStatePersonalPOJOArrayList;
    public static ArrayList<BorrowerOffStatePersonalPOJO> borrowerOffStatePersonalPOJOArrayList;

    public static ArrayAdapter arrayAdapter_currentResidencetype;
    public static ArrayList<String> currentResidencetype_arrayList;
    public static ArrayList<BorrowerCurrentResidenceTypePersonalPOJO> borrowerCurrentResidencePersonalPOJOArrayList;
    public static String currentResidencetypeID = "";

    public static ArrayAdapter arrayAdapter_currentResidenceDuration;
    public static ArrayList<String> currentresidenceduration_arrayList;
    public static ArrayList<BorrowerCurrentResidenceDurationPersonalPOJO> currentResidenceDurationPersonalPOJOArrayList;

    public static ArrayAdapter arrayAdapter_profession;
    public static ArrayList<String> profession_arrayList;
    public static ArrayList<ProfessionPOJO> professionPOJOArrayList;
    public String professionID = "";

    public ArrayList<String> duarationOfWorkListBr ;
    public ArrayList<String> employerType;


    public static String offcityID = "", offstateID = "", offcountryID = "",
            currentcityID = "", currentstateID = "", currentcountryID = "",
            permanentcityID = "", permanentstateID = "", permanentcountryID = "",SwitchAddressSameAs ="";

    public static String lead_id = "", application_id = "", requested_loan_amount = "", has_coborrower = "";

    public static String Brapplicant_id = "", Brfk_lead_id = "", Brfk_applicant_type_id = "", Brfirst_name = "", Brmiddle_name = "",
            Brlast_name = "", Brhas_aadhar_pan = "", Brdob = "", Brpan_number = "", Braadhar_number = "", Brmarital_status = "",
            Brgender_id = "", Brmobile_number = "", Bremail_id = "", Brrelationship_with_applicant = "", Brprofession = "",
            Bremployer_type = "1", Bremployer_name = "", Brannual_income = "", Brcurrent_employment_duration = "",
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
            Brpermanent_address_stay_duration = "",  Bris_deleted = "";


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


    static View view;

    public static Context context;
    public static Fragment mFragment;
    public static LinearLayout linBorrowerForm, linIfAddressNotSame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_detailedinfo_stepper, parent, false);
        context = getContext();
        mFragment = new DetailedInfoFragment();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        progressDialog = new ProgressDialog(context);


        btnEditDetailedInfo = view.findViewById(R.id.btnEditDetailedInfo);
        btnNext = view.findViewById(R.id.btnNext);

        txtResidentialToggle = view.findViewById(R.id.txtResidentialToggle);
        linResidentialBlock = view.findViewById(R.id.linResidentialBlock);
        txtProfessionalToggle = view.findViewById(R.id.txtProfessionalToggle);
        linProfessionalBlock = view.findViewById(R.id.linProfessionalBlock);

        expandAnimationResidential = AnimationUtils.loadAnimation(context, R.anim.scale_expand);
        collapseanimationResidential = AnimationUtils.loadAnimation(context, R.anim.scale_collapse);

        expandAnimationProfessional = AnimationUtils.loadAnimation(context, R.anim.scale_expand);
        collapseanimationProfessional = AnimationUtils.loadAnimation(context, R.anim.scale_collapse);

        linIfAddressNotSame = view.findViewById(R.id.linIfAddressNotSame);

        switchIsPermanentAddressSame = view.findViewById(R.id.switchIsPermanentAddressSame);
        switchResidenceType = view.findViewById(R.id.switchResidenceType);

        linMonthlyRent = view.findViewById(R.id.linMonthlyRent);

        edtCompanyName = view.findViewById(R.id.edtCompanyName);
        edtAnnualIncome = view.findViewById(R.id.edtAnnualIncome);
        edtMonthlyRent = view.findViewById(R.id.edtMonthlyRent);

        edtPermanentPincode = view.findViewById(R.id.edtPermanentPincode);
        edtPermanentAddress = view.findViewById(R.id.edtPermanentAddress);
        edtPermanentLandmark = view.findViewById(R.id.edtPermanentLandmark);

        spProfession = view.findViewById(R.id.spProfession);
        spOwnedBy = view.findViewById(R.id.spOwnedBy);

        edtPincodeOff = view.findViewById(R.id.edtPincodeOff);
        edtAddressOff = view.findViewById(R.id.edtAddressOff);
        edtLandmarkOff = view.findViewById(R.id.edtLandmarkOff);

        spPermanentCountry = view.findViewById(R.id.spPermanentCountry);
        spPermanentState = view.findViewById(R.id.spPermanentState);
        spPermanentCity = view.findViewById(R.id.spPermanentCity);

        spCountryOff = view.findViewById(R.id.spCountryOff);
        spStateOff = view.findViewById(R.id.spStateOff);
        spCityOff = view.findViewById(R.id.spCityOff);

        spDurarionOfStay = view.findViewById(R.id.spDurarionOfStay);
        spDurationOfJob = view.findViewById(R.id.spDurationOfJob);
        spEmployerType = view.findViewById(R.id.spEmployerType);
        viewPager = view.findViewById(R.id.viewpager);

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
        spDurarionOfStay.setAdapter(arrayAdapter_currentResidenceDuration);
        arrayAdapter_currentResidenceDuration.notifyDataSetChanged();

        duarationOfWorkListBr = new ArrayList<>();

        duarationOfWorkListBr.add("Select");
        duarationOfWorkListBr.add("Less Than 6 Month");
        duarationOfWorkListBr.add("6 Months to 1 Year");
        duarationOfWorkListBr.add("1 Year to 2 Years");
        duarationOfWorkListBr.add("More Than 2 years");
        ArrayAdapter workDurationAdapterBr = new ArrayAdapter(context, R.layout.custom_layout_spinner, duarationOfWorkListBr);
        spDurationOfJob.setAdapter(workDurationAdapterBr);

        employerType = new ArrayList<>();

        employerType.add("Select");
        employerType.add("Private Sector");
        employerType.add("Government Sector");
        ArrayAdapter employerTypeAdapter = new ArrayAdapter(context, R.layout.custom_layout_spinner, employerType);
        spEmployerType.setAdapter(employerTypeAdapter);

        //Resident Type Default Owned
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
        currentResidenceTypePersonalPOJO.residencetypeName = "Company Provided";
        currentResidenceTypePersonalPOJO.dresidencetypeID = "2";
        currentResidencetype_arrayList.add("Company Provided");
        borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

        currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
        currentResidenceTypePersonalPOJO.residencetypeName = "Owned By Parents/Siblings";
        currentResidenceTypePersonalPOJO.dresidencetypeID = "7";
        currentResidencetype_arrayList.add("Owned By Parents/Siblings");
        borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

        arrayAdapter_currentResidencetype = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentResidencetype_arrayList);
        spOwnedBy.setAdapter(arrayAdapter_currentResidencetype);
        arrayAdapter_currentResidencetype.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoanTabActivity.isDetailedInfoEdit){
                    if (Brpermanent_address_pin.equals("") || Brpermanent_address.equals("") || Brpermanent_landmark.equals("")
                            || permanentcityID.equals("") || permanentstateID.equals("") || permanentcountryID.equals("") || Brprofession.equals("")
                            || Bremployer_name.equals("") || Brannual_income.equals("") || Broffice_address_pin.equals("") || Broffice_address.equals("")
                            || Broffice_landmark.equals("") || Brcurrent_employment_duration.equals("") || Broffice_address_country.equals("")
                            || offstateID.equals("") || offcityID.equals("")){
                        mListener.onDetailedInfoFragment(false,1);
                    }
                    else {
//                        mListener.onDetailedInfoFragment(true,2);
                    }
                    submitDetailedInfo();
                }
                else{
                    mListener.onDetailedInfoFragment(true, 2);

                }
            }
        });

        linResidentialBlock.startAnimation(expandAnimationResidential);
        linProfessionalBlock.startAnimation(collapseanimationProfessional);
        setViewsEnabled(false);

        txtResidentialToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linResidentialBlock.getVisibility() == VISIBLE) {
                    linResidentialBlock.startAnimation(collapseanimationResidential);
                } else {
                    linResidentialBlock.startAnimation(expandAnimationResidential);
                }
            }
        });

        txtProfessionalToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linProfessionalBlock.getVisibility() == VISIBLE) {
                    linProfessionalBlock.startAnimation(collapseanimationProfessional);
                } else {
                    linProfessionalBlock.startAnimation(expandAnimationProfessional);
                }
            }
        });
/*=============================================Resendtial===================================*/
        collapseanimationResidential.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linResidentialBlock.setVisibility(GONE);
//          linProfessionalBlock.startAnimation(expandAnimationProfessional);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        expandAnimationResidential.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linResidentialBlock.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(linProfessionalBlock.getVisibility() == VISIBLE)
                {

                }else {
                    linProfessionalBlock.startAnimation(collapseanimationProfessional);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
/*=========================================Professional===========================================*/
        collapseanimationProfessional.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linProfessionalBlock.setVisibility(GONE);
//          linResidentialBlock.startAnimation(expandAnimationResidential);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        expandAnimationProfessional.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linProfessionalBlock.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(linResidentialBlock.getVisibility() == VISIBLE)
                {

                }
                else{
                    linResidentialBlock.startAnimation(collapseanimationResidential);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        switchIsPermanentAddressSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Current Add not same as Permanent
                    linIfAddressNotSame.setVisibility(View.VISIBLE);

                    edtPermanentPincode.setText(Brpermanent_address_pin);
                    edtPermanentAddress.setText(Brpermanent_address);
                    edtPermanentLandmark.setText(Brpermanent_landmark);

                    if (!Brpermanent_address_country.equals("null") && !Brpermanent_address_country.equals("")) {
                        permanentcountryID = Brpermanent_address_country;

                        try {
                            int count1 = borrowerPermanentCountryPersonalPOJOArrayList.size();
                            for (int i = 0; i < count1; i++) {
                                if (borrowerPermanentCountryPersonalPOJOArrayList.get(i).countryID.equalsIgnoreCase(permanentcountryID)) {
                                    spPermanentCountry.setSelection(i);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (!Brpermanent_address_state.equals("null") && !Brpermanent_address_state.equals("")) {
                        permanentstateID = Brpermanent_address_state;

                        if (borrowerPermanentStatePersonalPOJOArrayList != null) {
                            try {
                                int count1 = borrowerPermanentStatePersonalPOJOArrayList.size();
                                for (int i = 0; i < count1; i++) {
                                    if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(permanentstateID)) {
                                        spPermanentState.setSelection(i);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (!Brpermanent_address_city.equals("null") && !Brpermanent_address_city.equals("")) {
                        permanentcityID = Brpermanent_address_city;

                        if (borrowerPermanentCityPersonalPOJOArrayList != null) {
                            try {
                                int count2 = borrowerPermanentCityPersonalPOJOArrayList.size();
                                for (int i = 0; i < count2; i++) {
                                    if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityID)) {
                                        spPermanentCity.setSelection(i);
                                        checkAllFields();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    edtPermanentAddress.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            Brpermanent_address =  edtPermanentAddress.getText().toString();
                            checkAllFields();
                        }


                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    edtPermanentLandmark.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            Brpermanent_landmark = edtPermanentLandmark.getText().toString();
                        checkAllFields();
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    edtPermanentPincode.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

//                            edtPermanentPincode.setText(Brpermanent_address_pin);

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            Brpermanent_address_pin=edtPermanentPincode.getText().toString();
                            checkAllFields();
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });



                } else {

                    //Current Add same as Permanent
                    linIfAddressNotSame.setVisibility(View.GONE);

                }
            }
        });

        switchResidenceType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
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
                    currentResidenceTypePersonalPOJO.residencetypeName = "Company Provided";
                    currentResidenceTypePersonalPOJO.dresidencetypeID = "2";
                    currentResidencetype_arrayList.add("Company Provided");
                    borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

                    currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
                    currentResidenceTypePersonalPOJO.residencetypeName = "Owned By Parents/Siblings";
                    currentResidenceTypePersonalPOJO.dresidencetypeID = "7";
                    currentResidencetype_arrayList.add("Owned By Parents/Siblings");
                    borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);

                    arrayAdapter_currentResidencetype = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentResidencetype_arrayList);
                    spOwnedBy.setAdapter(arrayAdapter_currentResidencetype);
                    arrayAdapter_currentResidencetype.notifyDataSetChanged();
                    linMonthlyRent.setVisibility(GONE);

                } else {
                    currentResidencetype_arrayList = new ArrayList<>();
                    borrowerCurrentResidencePersonalPOJOArrayList = new ArrayList<>();

                    BorrowerCurrentResidenceTypePersonalPOJO currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
                    currentResidenceTypePersonalPOJO.residencetypeName = "Select Any";
                    currentResidenceTypePersonalPOJO.dresidencetypeID = "";
                    currentResidencetype_arrayList.add("Select Any");
                    borrowerCurrentResidencePersonalPOJOArrayList.add(currentResidenceTypePersonalPOJO);


                    currentResidenceTypePersonalPOJO = new BorrowerCurrentResidenceTypePersonalPOJO();
                    currentResidenceTypePersonalPOJO.residencetypeName = "Rented With Family";
                    currentResidenceTypePersonalPOJO.dresidencetypeID = "1";
                    currentResidencetype_arrayList.add("Rented With Family");
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

                    arrayAdapter_currentResidencetype = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentResidencetype_arrayList);
                    spOwnedBy.setAdapter(arrayAdapter_currentResidencetype);
                    arrayAdapter_currentResidencetype.notifyDataSetChanged();
                    linMonthlyRent.setVisibility(VISIBLE);

                }
            }
        });

        btnEditDetailedInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//          if (lead_status.equals("1") && current_stage.equals("1")) {
                setViewsEnabled(true);
                LoanTabActivity.isDetailedInfoEdit = true;
                btnEditDetailedInfo.setVisibility(View.GONE);
//          } else {
//
//          }
            }
        });

        spDurarionOfStay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spDurarionOfStay.getSelectedItem().toString();
                    if (currentResidenceDurationPersonalPOJOArrayList != null) {
                        int count = currentResidenceDurationPersonalPOJOArrayList.size();
                        Log.e("TAG", "count: " + count);
                        for (int i = 0; i < count; i++) {
                            if (currentResidenceDurationPersonalPOJOArrayList.get(i).durationName.equalsIgnoreCase(text)) {
                                Brcurrent_address_stay_duration = currentResidenceDurationPersonalPOJOArrayList.get(i).durationID;
                                break;
//                                Log.e("I_________D", "jobDurationID: " + jobDurationID);
                            }
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

        spDurationOfJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Brcurrent_employment_duration = position + "";
                    checkAllFields();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//TODO: Create spinner for employer type
        spEmployerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {
                String text = spEmployerType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });

        spOwnedBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spOwnedBy.getSelectedItem().toString();

                    int count = borrowerCurrentResidencePersonalPOJOArrayList.size();

                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).residencetypeName.equalsIgnoreCase(text)) {
                            Brcurrent_residence_type = currentResidencetypeID = borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID;
//                            if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("")) {
//                                edtMonthlyRentLayoutBr.setVisibility(View.GONE);
//                                edtMonthlyRentBr.setVisibility(View.GONE);
////                                    edtMonthlyRentBr.setText("");
//                            } else if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("0")) {
//                                edtMonthlyRentLayoutBr.setVisibility(View.GONE);
//                                edtMonthlyRentBr.setVisibility(View.GONE);
////                                    edtMonthlyRentBr.setText("");
//                            } else if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase("7")) {
//                                edtMonthlyRentLayoutBr.setVisibility(View.GONE);
//                                edtMonthlyRentBr.setVisibility(View.GONE);
////                                    edtMonthlyRentBr.setText("");
//                            } else {
//                                edtMonthlyRentLayoutBr.setVisibility(View.VISIBLE);
//                                edtMonthlyRentBr.setVisibility(View.VISIBLE);
//                            }
                            checkAllFields();
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

        spProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    String text = spProfession.getSelectedItem().toString();
                    if (professionPOJOArrayList != null) {
                        int count = professionPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (professionPOJOArrayList.get(i).Salaried.equalsIgnoreCase(text)) {
                                Brprofession = professionID = Brprofession = professionPOJOArrayList.get(i).id;
                                checkAllFields();
                                break;
                            }
                        }
                    }
//                    if (position == 2 || position == 0) {
//                        try {
//                            linOfficeDetails.setVisibility(View.GONE);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        try {
//                            linOfficeDetails.setVisibility(View.VISIBLE);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spPermanentCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spPermanentCity.getSelectedItem().toString();
                    if (borrowerPermanentCityPersonalPOJOArrayList != null) {
                        int count = borrowerPermanentCityPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                                permanentcityID = Brpermanent_address_city = borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID;
                                checkAllFields();
                                break;
//                                Log.e(TAG, "sppermanentCityBr: +++++++++++++++++++*********************" + permanentcityID);
                            }
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

        spPermanentState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spPermanentState.getSelectedItem().toString();
                    if (borrowerPermanentStatePersonalPOJOArrayList != null) {
                        int count = borrowerPermanentStatePersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                                permanentstateID = Brpermanent_address_state = borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID;
                                checkAllFields();
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                permanentcityApiCall();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPermanentCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spPermanentCountry.getSelectedItem().toString();
                    if (borrowerPermanentCountryPersonalPOJOArrayList != null) {
                        int count = borrowerPermanentCountryPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerPermanentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                                permanentcountryID = Brpermanent_address_country = borrowerPermanentCountryPersonalPOJOArrayList.get(i).countryID;
                                checkAllFields();
                                break;
                            }
                        }
                    }
                    permanentstateApiCall();
//                        if (permanentcityID.equals("")) {
//                            sppermanentCityBr.setSelection(0);
//                        } else {
//                            spPermanentCityBr.setSelection(Integer.parseInt(permanentcityID));
//                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spCityOff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spCityOff.getSelectedItem().toString();
                    if (borrowerOffCityPersonalPOJOArrayList != null) {
                        int count = borrowerOffCityPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerOffCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                                offcityID = borrowerOffCityPersonalPOJOArrayList.get(i).cityID;
//                                Log.e(TAG, "spOffCityBr: +++++++++++++++++++*********************" + offcityIDBr);
                                checkAllFields();
                                break;
                            }
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

        spStateOff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spStateOff.getSelectedItem().toString();
                    if (borrowerOffStatePersonalPOJOArrayList != null) {
                        int count = borrowerOffStatePersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerOffStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                                offstateID = borrowerOffStatePersonalPOJOArrayList.get(i).stateID;
                                checkAllFields();
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                offcityApiCall();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCountryOff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spCountryOff.getSelectedItem().toString();
                    if (borrowerOffCountryPersonalPOJOArrayList != null) {
                        int count = borrowerOffCountryPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerOffCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                                offcountryID = Broffice_address_country = borrowerOffCountryPersonalPOJOArrayList.get(i).countryID;
                                checkAllFields();
                                break;
                            }
                        }
                    }
                    offstateApiCall();
//                        if (OffcityID.equals("")) {
//                            spOffCityBr.setSelection(0);
//                        } else {
//                            spOffCityBr.setSelection(Integer.parseInt(OffcityID));
//                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/*=====================================================professional detaisl==========================================*/
        edtCompanyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Bremployer_name = edtCompanyName.getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtAnnualIncome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Brannual_income=edtAnnualIncome.getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtAddressOff.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Broffice_address=edtAddressOff.getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtLandmarkOff.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Broffice_landmark = edtLandmarkOff.getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPincodeOff.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Broffice_address_pin = edtPincodeOff.getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        countryApiCall();
        permanentstateApiCall();
        offstateApiCall();
        ProfessionApiCall();
        detailedApiCall();

//    applyFieldsChangeListener();

    }

    @Override
    public void onResume() {
        super.onResume();
//        isEdit = false;
    }


    private void setViewsEnabled(boolean f) {
        try {

            switchIsPermanentAddressSame.setEnabled(f);
            switchResidenceType.setEnabled(f);

            linMonthlyRent.setEnabled(f);
            edtCompanyName.setEnabled(f);
            edtAnnualIncome.setEnabled(f);
            edtMonthlyRent.setEnabled(f);
            edtPermanentPincode.setEnabled(f);
            edtPermanentAddress.setEnabled(f);
            edtPermanentLandmark.setEnabled(f);

            edtPincodeOff.setEnabled(f);
            edtAddressOff.setEnabled(f);
            edtLandmarkOff.setEnabled(f);

            spPermanentCountry.setEnabled(f);
            spPermanentState.setEnabled(f);
            spPermanentCity.setEnabled(f);
            spCountryOff.setEnabled(f);
            spStateOff.setEnabled(f);
            spCityOff.setEnabled(f);

            spProfession.setEnabled(f);
            spOwnedBy.setEnabled(f);
            spDurarionOfStay.setEnabled(f);
            spDurationOfJob.setEnabled(f);
            spEmployerType.setEnabled(f);

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }

    public void checkAllFields() {

        if (Brpermanent_address_pin.equals("") || Brpermanent_address.equals("") || Brpermanent_landmark.equals("")
                || permanentcityID.equals("") || permanentstateID.equals("") || permanentcountryID.equals("")) {
            indicateValidationText(txtResidentialToggle, getResources().getDrawable(R.drawable.ic_home_heart), false);
        } else {
            indicateValidationText(txtResidentialToggle, getResources().getDrawable(R.drawable.ic_home_heart), true);
        }

        if (Brprofession.equals("")  || Bremployer_name.equals("") || Brannual_income.equals("")
        || Broffice_address_pin.equals("") || Broffice_address.equals("") || Broffice_landmark.equals("")
        || Brcurrent_employment_duration.equals("") || Broffice_address_country.equals("") || offstateID.equals("") || offcityID.equals("")) {
            indicateValidationText(txtProfessionalToggle, getResources().getDrawable(R.drawable.ic_business_time), false);
        } else {
            indicateValidationText(txtProfessionalToggle, getResources().getDrawable(R.drawable.ic_business_time), true);
        }
    }

    public static void validate(){
        if (Brpermanent_address_pin.equals("") || Brpermanent_address.equals("") || Brpermanent_landmark.equals("")
        || permanentcityID.equals("") || permanentstateID.equals("") || permanentcountryID.equals("") || Brprofession.equals("")
        || Bremployer_name.equals("") || Brannual_income.equals("") || Broffice_address_pin.equals("") || Broffice_address.equals("")
        || Broffice_landmark.equals("") || Brcurrent_employment_duration.equals("") || Broffice_address_country.equals("")
        || offstateID.equals("") || offcityID.equals("")){
            mListener.onDetailedInfoFragment(false,1);
        }
        else {
//            mListener.onDetailedInfoFragment(true,2);
        }
    }

    public void indicateValidationText(TextView indicator, Drawable start, boolean valid) {
        if (valid) {
            indicator.setCompoundDrawablesRelativeWithIntrinsicBounds(start, null, getResources().getDrawable(R.drawable.ic_check_circle_green), null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                indicator.getCompoundDrawablesRelative()[0].setTint(getResources().getColor(R.color.colorGreen));
                indicator.getCompoundDrawablesRelative()[2].setTint(getResources().getColor(R.color.colorGreen));
            }
            indicator.setTextColor(getResources().getColor(R.color.colorGreen));
        } else {
            indicator.setCompoundDrawablesRelativeWithIntrinsicBounds(start, null, getResources().getDrawable(R.drawable.ic_exclamation_circle), null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                indicator.getCompoundDrawablesRelative()[0].setTint(getResources().getColor(R.color.blue1));
                indicator.getCompoundDrawablesRelative()[2].setTint(getResources().getColor(R.color.new_red));
            }
            indicator.setTextColor(getResources().getColor(R.color.blue1));
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onDetailedInfoFragmentInteractionListener){
            mListener = (onDetailedInfoFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
            +" must implement onDetailedInfoFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void countryApiCall() {
        //api is pending
        try {
            progressDialog.setMessage("Loading");
            progressDialog.show();
            progressDialog.setCancelable(false);
            String url = MainActivity.mainUrl + "dashboard/getcountrylist";
            Map<String, String> params = new HashMap<String, String>();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, DetailedInfoFragment.this, "getCountriesDtl", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }


    }

    public void countryApiResponse(JSONObject jsonObject) {
        progressDialog.dismiss();
        try {
            String message = jsonObject.getString("message");
            if (jsonObject.getInt("status") == 1) {
                JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("countries");

                permanentCountry_arrayList = new ArrayList<>();
                borrowerPermanentCountryPersonalPOJOArrayList = new ArrayList<>();

                offCountry_arrayList = new ArrayList<>();
                borrowerOffCountryPersonalPOJOArrayList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    BorrowerPermanentCountryPersonalPOJO borrowerPermanentCountryPersonalPOJO1 = new BorrowerPermanentCountryPersonalPOJO();
                    borrowerPermanentCountryPersonalPOJO1.countryName = jsonObject1.getString("country_name");
                    permanentCountry_arrayList.add(jsonObject1.getString("country_name"));
                    borrowerPermanentCountryPersonalPOJO1.countryID = jsonObject1.getString("country_id");
                    borrowerPermanentCountryPersonalPOJOArrayList.add(borrowerPermanentCountryPersonalPOJO1);

                    BorrowerOffCountryPersonalPOJO borrowerOffCountryPersonalPOJO1 = new BorrowerOffCountryPersonalPOJO();
                    borrowerOffCountryPersonalPOJO1.countryName = jsonObject1.getString("country_name");
                    offCountry_arrayList.add(jsonObject1.getString("country_name"));
                    borrowerOffCountryPersonalPOJO1.countryID = jsonObject1.getString("country_id");
                    borrowerOffCountryPersonalPOJOArrayList.add(borrowerOffCountryPersonalPOJO1);

                }

                arrayAdapter_permanentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentCountry_arrayList);
                spPermanentCountry.setAdapter(arrayAdapter_permanentCountry);
                arrayAdapter_permanentCountry.notifyDataSetChanged();

                arrayAdapter_offCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, offCountry_arrayList);
                spCountryOff.setAdapter(arrayAdapter_offCountry);
                arrayAdapter_offCountry.notifyDataSetChanged();

            } else {
//          Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void permanentcityApiCall() {
        /**API CALL**/
        try {
            String url = MainActivity.mainUrl + "algo/getCities"; //http://159.89.204.41/eduvanzApi/algo/getCities
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", permanentcountryID);//1
            params.put("stateId", permanentstateID);//2
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentCities(currentstateID,currentcountryID);
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetPermanentCity", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void dtlgetPermanentCities(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    permanentcity_arrayList = new ArrayList<>();
                    permanentcity_arrayList.add("Select Any");
                    arrayAdapter_permanentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentcity_arrayList);
                    spPermanentCity.setAdapter(arrayAdapter_permanentCity);
                    arrayAdapter_permanentCity.notifyDataSetChanged();
                    spPermanentCity.setSelection(0);
                } catch (Exception e) {
                    String className = this.getClass().getSimpleName();
                    String name = new Object() {
                    }.getClass().getEnclosingMethod().getName();
                    String errorMsg = e.getMessage();
                    String errorMsgDetails = e.getStackTrace().toString();
                    String errorLine = String.valueOf(e.getStackTrace()[0]);
                    Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
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
                    spPermanentCity.setAdapter(arrayAdapter_permanentCity);
                    arrayAdapter_permanentCity.notifyDataSetChanged();

                    int count = borrowerPermanentCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerPermanentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(permanentcityID)) {
                            spPermanentCity.setSelection(i);
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
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    private void permanentstateApiCall() {
        /**API CALL**/
        try {
            String url = MainActivity.mainUrl + "algo/getStates";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", permanentcountryID);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentStates(currentcountryID);

            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetPermanentStates", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void dtlgetPermanentStates(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    permanentstate_arrayList = new ArrayList<>();
                    permanentstate_arrayList.add("Select Any");
                    arrayAdapter_permanentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, permanentstate_arrayList);
                    spPermanentState.setAdapter(arrayAdapter_permanentState);
                    arrayAdapter_permanentState.notifyDataSetChanged();
                    spPermanentState.setSelection(0);
                } catch (Exception e) {
                    String className = this.getClass().getSimpleName();
                    String name = new Object() {
                    }.getClass().getEnclosingMethod().getName();
                    String errorMsg = e.getMessage();
                    String errorMsgDetails = e.getStackTrace().toString();
                    String errorLine = String.valueOf(e.getStackTrace()[0]);
                    Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
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
                    spPermanentState.setAdapter(arrayAdapter_permanentState);
                    arrayAdapter_permanentState.notifyDataSetChanged();

                    int count = borrowerPermanentStatePersonalPOJOArrayList.size();

                    for (int i = 0; i < count; i++) {
                        if (borrowerPermanentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(permanentstateID)) {
                            spPermanentState.setSelection(i);
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
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }


    private void offcityApiCall() {
        /**API CALL**/
        try {
            String url = MainActivity.mainUrl + "algo/getCities"; //http://159.89.204.41/eduvanzApi/algo/getCities
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", offcountryID);//1
            params.put("stateId", offstateID);//2
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentCities(currentstateID,currentcountryID);
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetOffCities", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    private void offstateApiCall() {
        /**API CALL**/
        try {
            String url = MainActivity.mainUrl + "algo/getStates";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", offcountryID);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentStates(currentcountryID);

            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "dtlgetOffStates", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void dtlgetOffCities(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    offcity_arrayList = new ArrayList<>();
                    offcity_arrayList.add("Select Any");
                    arrayAdapter_offCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, offcity_arrayList);
                    spCityOff.setAdapter(arrayAdapter_offCity);
                    arrayAdapter_offCity.notifyDataSetChanged();
                    spCityOff.setSelection(0);
                } catch (Exception e) {
                    String className = this.getClass().getSimpleName();
                    String name = new Object() {
                    }.getClass().getEnclosingMethod().getName();
                    String errorMsg = e.getMessage();
                    String errorMsgDetails = e.getStackTrace().toString();
                    String errorLine = String.valueOf(e.getStackTrace()[0]);
                    Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
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
                    spCityOff.setAdapter(arrayAdapter_offCity);
                    arrayAdapter_offCity.notifyDataSetChanged();

                    int count = borrowerOffCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerOffCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(offcityID)) {
                            spCityOff.setSelection(i);
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
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void dtlgetOffStates(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    offstate_arrayList = new ArrayList<>();
                    offstate_arrayList.add("Select Any");
                    arrayAdapter_offState = new ArrayAdapter(context, R.layout.custom_layout_spinner, offstate_arrayList);
                    spStateOff.setAdapter(arrayAdapter_offState);
                    arrayAdapter_offState.notifyDataSetChanged();
                    spStateOff.setSelection(0);
                } catch (Exception e) {
                    String className = this.getClass().getSimpleName();
                    String name = new Object() {
                    }.getClass().getEnclosingMethod().getName();
                    String errorMsg = e.getMessage();
                    String errorMsgDetails = e.getStackTrace().toString();
                    String errorLine = String.valueOf(e.getStackTrace()[0]);
                    Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
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
                    spStateOff.setAdapter(arrayAdapter_offState);
                    arrayAdapter_offState.notifyDataSetChanged();

                    int count = borrowerOffStatePersonalPOJOArrayList.size();

                    for (int i = 0; i < count; i++) {
                        if (borrowerOffStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(offstateID)) {
                            spStateOff.setSelection(i);
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
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    private void ProfessionApiCall() {
        /**API CALL**/
        try {
            String url = MainActivity.mainUrl + "dashboard/getAllProfession";
            Map<String, String> params = new HashMap<String, String>();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "getAllProfessiondetailedinfo", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void getAllProfessiondetailedinfo(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    profession_arrayList = new ArrayList<>();
                    profession_arrayList.add("Select Profession");
                    arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
                    spProfession.setAdapter(arrayAdapter_profession);
                    arrayAdapter_profession.notifyDataSetChanged();
                    spProfession.setSelection(0);

                } catch (Exception e) {
                    String className = this.getClass().getSimpleName();
                    String name = new Object() {
                    }.getClass().getEnclosingMethod().getName();
                    String errorMsg = e.getMessage();
                    String errorMsgDetails = e.getStackTrace().toString();
                    String errorLine = String.valueOf(e.getStackTrace()[0]);
                    Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
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
                        if (mJsonti.getString("id").equals("2")) {  //student
                            continue;
                        }
                        professionPOJO.Salaried = mJsonti.getString("profession");
                        profession_arrayList.add(mJsonti.getString("profession"));
                        professionPOJO.id = mJsonti.getString("id");
                        professionPOJOArrayList.add(professionPOJO);
                    }
                    arrayAdapter_profession = new ArrayAdapter(context, R.layout.custom_layout_spinner, profession_arrayList);
                    spProfession.setAdapter(arrayAdapter_profession);
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
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }


    public void detailedApiCall() {
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String url = MainActivity.mainUrl + "dashboard/getDetailedInformation";
        Map<String, String> params = new HashMap<String, String>();
        params.put("lead_id", LoanTabActivity.lead_id);
        if (!Globle.isNetworkAvailable(context)) {
            Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
        } else {
            VolleyCall volleyCall = new VolleyCall();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
            volleyCall.sendRequest(context, url, null, DetailedInfoFragment.this, "getDetailedInformation", params, MainActivity.auth_token);
        }
    }

    public void setDetailedInformation(JSONObject jsonData) {
        try {
            progressDialog.dismiss();
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (jsonData.getInt("status") == 1) {

                JSONObject jsondetailedInformation = jsonData.getJSONObject("detailedInformation");
                JSONObject jsonborrowerDetails = jsonData.getJSONObject("borrowerDetails");
//
//                if (!jsonData.get("leadStatus").equals(null)) {
//                    JSONObject jsonleadStatus = jsonData.getJSONObject("leadStatus");
//                    lead_status = jsonleadStatus.getString("lead_status");
//                    lead_sub_status = jsonleadStatus.getString("lead_sub_status");
//                    current_stage = jsonleadStatus.getString("current_stage");
//                    current_status = jsonleadStatus.getString("current_status");
//                }
//
//                if(LoanTabActivity.lead_status.equals("1") && LoanTabActivity.current_stage.equals("1"))
//                {
//                }
//                else{
//                    btnEditDetailedInfo.setVisibility(View.GONE);
//                }

                lead_id = jsondetailedInformation.getString("lead_id");
                application_id = jsondetailedInformation.getString("application_id");
                requested_loan_amount = jsondetailedInformation.getString("requested_loan_amount");
                has_coborrower = jsondetailedInformation.getString("has_coborrower");

                if (!jsonborrowerDetails.getString("relationship_with_applicant").toString().equals("null"))
                    Brrelationship_with_applicant = jsonborrowerDetails.getString("relationship_with_applicant");
                if (!jsonborrowerDetails.getString("profession").toString().equals("null"))
                    Brprofession = jsonborrowerDetails.getString("profession");
                if (!jsonborrowerDetails.getString("employer_type").toString().equals("null"))
                    Bremployer_type = jsonborrowerDetails.getString("employer_type");
                if (!jsonborrowerDetails.getString("employer_name").toString().equals("null"))
                    Bremployer_name = jsonborrowerDetails.getString("employer_name");
                if (!jsonborrowerDetails.getString("annual_income").toString().equals("null"))
                    Brannual_income = jsonborrowerDetails.getString("annual_income");
                if (!jsonborrowerDetails.getString("current_employment_duration").toString().equals("null"))
                    Brcurrent_employment_duration = jsonborrowerDetails.getString("current_employment_duration");
                if (!jsonborrowerDetails.getString("total_employement_duration").toString().equals("null"))
                    Brtotal_employement_duration = jsonborrowerDetails.getString("total_employement_duration");
                if (!jsonborrowerDetails.getString("employer_mobile_number").toString().equals("null"))
                    Bremployer_mobile_number = jsonborrowerDetails.getString("employer_mobile_number");
                if (!jsonborrowerDetails.getString("employer_landline_number").toString().equals("null"))
                    Bremployer_landline_number = jsonborrowerDetails.getString("employer_landline_number");

                if (!jsonborrowerDetails.getString("office_landmark").toString().equals("null"))
                    Broffice_landmark = jsonborrowerDetails.getString("office_landmark");
                if (!jsonborrowerDetails.getString("office_address").toString().equals("null"))
                    Broffice_address = jsonborrowerDetails.getString("office_address");
                if (!jsonborrowerDetails.getString("office_address_city").toString().equals("null"))
                    offcityID = jsonborrowerDetails.getString("office_address_city");
                if (!jsonborrowerDetails.getString("office_address_state").toString().equals("null"))
                    offstateID = jsonborrowerDetails.getString("office_address_state");
                if (!jsonborrowerDetails.getString("office_address_country").toString().equals("null"))
                    Broffice_address_country = jsonborrowerDetails.getString("office_address_country");
                if (!jsonborrowerDetails.getString("office_address_pin").toString().equals("null"))
                    Broffice_address_pin = jsonborrowerDetails.getString("office_address_pin");
                if (!jsonborrowerDetails.getString("has_active_loan").toString().equals("null"))
                    Brhas_active_loan = jsonborrowerDetails.getString("has_active_loan");
                if (!jsonborrowerDetails.getString("EMI_Amount").toString().equals("null"))
                    BrEMI_Amount = jsonborrowerDetails.getString("EMI_Amount");
//          if (!jsonborrowerDetails.getString("kyc_landmark").toString().equals("null"))
//              Brkyc_landmark = jsonborrowerDetails.getString("kyc_landmark");
//          if (!jsonborrowerDetails.getString("kyc_address").toString().equals("null"))
//              Brkyc_address = jsonborrowerDetails.getString("kyc_address");
//          if (!jsonborrowerDetails.getString("kyc_address_city").toString().equals("null"))
//              Brkyc_address_city = jsonborrowerDetails.getString("kyc_address_city");
//          if (!jsonborrowerDetails.getString("kyc_address_state").toString().equals("null"))
//              Brkyc_address_state = jsonborrowerDetails.getString("kyc_address_state");
//          if (!jsonborrowerDetails.getString("kyc_address_country").toString().equals("null"))
//              Brkyc_address_country = jsonborrowerDetails.getString("kyc_address_country");
//          if (!jsonborrowerDetails.getString("kyc_address_pin").toString().equals("null"))
//             Brkyc_address_pin = jsonborrowerDetails.getString("kyc_address_pin");

                if (!jsonborrowerDetails.getString("is_borrower_current_address_same_as").toString().equals("null"))
                    Bris_borrower_current_address_same_as = jsonborrowerDetails.getString("is_borrower_current_address_same_as");
                if (!jsonborrowerDetails.getString("is_coborrower_current_address_same_as").toString().equals("null"))
                    Bris_coborrower_current_address_same_as = jsonborrowerDetails.getString("is_coborrower_current_address_same_as");
                if (!jsonborrowerDetails.getString("current_residence_type").toString().equals("null"))
                    Brcurrent_residence_type = jsonborrowerDetails.getString("current_residence_type");

                if (!jsonborrowerDetails.getString("current_landmark").toString().equals("null"))
                    Brcurrent_landmark = jsonborrowerDetails.getString("current_landmark");
                if (!jsonborrowerDetails.getString("current_address").toString().equals("null"))
                    Brcurrent_address = jsonborrowerDetails.getString("current_address");
                if (!jsonborrowerDetails.getString("current_address_city").toString().equals("null"))
                    Brcurrent_address_city = jsonborrowerDetails.getString("current_address_city");
                if (!jsonborrowerDetails.getString("current_address_state").toString().equals("null"))
                    Brcurrent_address_state = jsonborrowerDetails.getString("current_address_state");
                if (!jsonborrowerDetails.getString("current_address_country").toString().equals("null"))
                    Brcurrent_address_country = jsonborrowerDetails.getString("current_address_country");
                if (!jsonborrowerDetails.getString("current_address_pin").toString().equals("null"))
                    Brcurrent_address_pin = jsonborrowerDetails.getString("current_address_pin");
                if (!jsonborrowerDetails.getString("current_address_rent").toString().equals("null"))
                    Brcurrent_address_rent = jsonborrowerDetails.getString("current_address_rent");
                if (!jsonborrowerDetails.getString("current_address_stay_duration").toString().equals("null"))
                    Brcurrent_address_stay_duration = jsonborrowerDetails.getString("current_address_stay_duration");
                if (!jsonborrowerDetails.getString("is_borrower_permanent_address_same_as").toString().equals("null"))
                    Bris_borrower_permanent_address_same_as = jsonborrowerDetails.getString("is_borrower_permanent_address_same_as");

                if (!jsonborrowerDetails.getString("permanent_residence_type").toString().equals("null"))
                    Brpermanent_residence_type = jsonborrowerDetails.getString("permanent_residence_type");
                if (!jsonborrowerDetails.getString("permanent_landmark").toString().equals("null"))
                    Brpermanent_landmark = jsonborrowerDetails.getString("permanent_landmark");
                if (!jsonborrowerDetails.getString("permanent_address").toString().equals("null"))
                    Brpermanent_address = jsonborrowerDetails.getString("permanent_address");
                if (!jsonborrowerDetails.getString("permanent_address_city").toString().equals("null"))
                    Brpermanent_address_city = jsonborrowerDetails.getString("permanent_address_city");
                if (!jsonborrowerDetails.getString("permanent_address_state").toString().equals("null"))
                    Brpermanent_address_state = jsonborrowerDetails.getString("permanent_address_state");
                if (!jsonborrowerDetails.getString("permanent_address_country").toString().equals("null"))
                    Brpermanent_address_country = jsonborrowerDetails.getString("permanent_address_country");
                if (!jsonborrowerDetails.getString("permanent_address_pin").toString().equals("null"))
                    Brpermanent_address_pin = jsonborrowerDetails.getString("permanent_address_pin");
                if (!jsonborrowerDetails.getString("permanent_address_rent").toString().equals("null"))
                    Brpermanent_address_rent = jsonborrowerDetails.getString("permanent_address_rent");
                if (!jsonborrowerDetails.getString("permanent_address_stay_duration").toString().equals("null"))
                    Brpermanent_address_stay_duration = jsonborrowerDetails.getString("permanent_address_stay_duration");
//          if (!jsonborrowerDetails.getString("last_completed_degree").toString().equals("null"))
//             Brlast_completed_degree = jsonborrowerDetails.getString("last_completed_degree");
//          if (!jsonborrowerDetails.getString("score_unit").toString().equals("null"))
//             Brscore_unit = jsonborrowerDetails.getString("score_unit");
//          if (!jsonborrowerDetails.getString("cgpa").toString().equals("null"))
//             Brcgpa = jsonborrowerDetails.getString("cgpa");
//          if (!jsonborrowerDetails.getString("percentage").toString().equals("null"))
//             Brpercentage = jsonborrowerDetails.getString("percentage");
//
//          if (!jsonborrowerDetails.getString("gap_in_education").toString().equals("null"))
//             Brgap_in_education = jsonborrowerDetails.getString("gap_in_education");
//
//
//          if (!jsonborrowerDetails.getString("passing_year").toString().equals("null"))
//             Brpassing_year = jsonborrowerDetails.getString("passing_year");
//          if (!jsonborrowerDetails.getString("gap_in_education").toString().equals("null"))
//             Brgap_in_education = jsonborrowerDetails.getString("gap_in_education");

//          if (!jsonborrowerDetails.getString("full_name_pan_response").toString().equals("null"))
//             Brfull_name_pan_response = jsonborrowerDetails.getString("full_name_pan_response");
//          if (!jsonborrowerDetails.getString("created_by_id").toString().equals("null"))
//             Brcreated_by_id = jsonborrowerDetails.getString("created_by_id");
//          if (!jsonborrowerDetails.getString("created_date_time").toString().equals("null"))
//             Brcreated_date_time = jsonborrowerDetails.getString("created_date_time");
//          if (!jsonborrowerDetails.getString("created_ip_address").toString().equals("null"))
//             Brcreated_ip_address = jsonborrowerDetails.getString("created_ip_address");
//          if (!jsonborrowerDetails.getString("modified_by").toString().equals("null"))
//             Brmodified_by = jsonborrowerDetails.getString("modified_by");
//          if (!jsonborrowerDetails.getString("modified_date_time").toString().equals("null"))
//             Brmodified_date_time = jsonborrowerDetails.getString("modified_date_time");
//          if (!jsonborrowerDetails.getString("modified_ip_address").toString().equals("null"))
//             Brmodified_ip_address = jsonborrowerDetails.getString("modified_ip_address");

                if (!jsonborrowerDetails.getString("is_deleted").toString().equals("null"))
                    Bris_deleted = jsonborrowerDetails.getString("is_deleted");

                if (!Brcurrent_address_stay_duration.equals("") && !Brcurrent_address_stay_duration.equals("null")) {
                    try {
                        spDurarionOfStay.setSelection(Integer.parseInt(Brcurrent_address_stay_duration));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (!Bremployer_type.equals("") && !Bremployer_type.equals("null")) {
                    try {
                        spEmployerType.setSelection(Integer.parseInt(Bremployer_type));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

//                if (!Brcurrent_residence_type.equals("") && !Brcurrent_residence_type.equals("null")) {
//                    spOwnedBy.setSelection((Integer.parseInt(Brcurrent_residence_type)));
//                }

                edtMonthlyRent.setText(Brcurrent_address_rent);
                edtCompanyName.setText(Bremployer_name);
                edtAnnualIncome.setText(Brannual_income);

                edtPincodeOff.setText(Broffice_address_pin);
                edtAddressOff.setText(Broffice_address);
                edtLandmarkOff.setText(Broffice_landmark);

                if (!Brprofession.equals("") && !Brprofession.equals("null")) {
                    try {
                        spProfession.setSelection(Integer.parseInt(Brprofession));
                    } catch (Exception e) {
                        String className = this.getClass().getSimpleName();
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        String errorMsg = e.getMessage();
                        String errorMsgDetails = e.getStackTrace().toString();
                        String errorLine = String.valueOf(e.getStackTrace()[0]);
                        Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
                    }
                }

                if (!Brcurrent_employment_duration.equals("") && !Brcurrent_employment_duration.equals("null")) {
                    try {
                        spDurationOfJob.setSelection(Integer.parseInt(Brcurrent_employment_duration));
                    } catch (Exception e) {
                        String className = this.getClass().getSimpleName();
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        String errorMsg = e.getMessage();
                        String errorMsgDetails = e.getStackTrace().toString();
                        String errorLine = String.valueOf(e.getStackTrace()[0]);
                        Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
                    }
                }


//          if (Bris_borrower_current_address_same_as.equals("") || Bris_borrower_current_address_same_as.equals("null")) {
//             Bris_borrower_current_address_same_as = "0";
//             spCurrentAddressSameAsKycBr.setSelection(Integer.parseInt(Bris_borrower_current_address_same_as));
//          } else {
//             Bris_borrower_current_address_same_asdtl = Bris_borrower_current_address_same_as;
//             spCurrentAddressSameAsKycBr.setSelection(Integer.parseInt(Bris_borrower_current_address_same_as));
//          }

                if (Bris_borrower_permanent_address_same_as.equals("") || Bris_borrower_permanent_address_same_as.equals("null") || Bris_borrower_permanent_address_same_as.equals("0") || Bris_borrower_permanent_address_same_as.equals("1") || Bris_borrower_permanent_address_same_as.equals("3")) {

                    if(Bris_borrower_permanent_address_same_as.equals("") || Bris_borrower_permanent_address_same_as.equals("null")) {
                        SwitchAddressSameAs = "1";
                        Bris_borrower_permanent_address_same_as ="1";
                    }
                    else {
                        SwitchAddressSameAs = Bris_borrower_permanent_address_same_as;
                    }

//                    SwitchAddressSameAs = "1";


                    try {
                        if (!Brpermanent_address_country.equals("") && !Brpermanent_address_country.equals("null")) {
                            try {
                                permanentcountryID = Brpermanent_address_country;

//                            int count = borrowerPermanentCountryPersonalPOJOArrayList.size();
//                            for (int i = 0; i < count; i++) {
//                                if (borrowerPermanentCountryPersonalPOJOArrayList.get(i).countryID.equalsIgnoreCase(permanentcountryID)) {
//                                    spPermanentCountry.setSelection(i);
//                                }
//                            }
                            } catch (Exception e) {

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (!Brpermanent_address_state.equals("") && !Brpermanent_address_state.equals("null")) {
                            permanentstateID = Brpermanent_address_state;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (!Brpermanent_address_city.equals("") && !Brpermanent_address_city.equals("null")) {
                            permanentcityID = Brpermanent_address_city;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    switchIsPermanentAddressSame.setChecked(false);

                } else {
                    try {
                        if (!Brcurrent_address_country.equals("") && !Brcurrent_address_country.equals("null")) {
                            try {
                                permanentcountryID = Brcurrent_address_country;

                            } catch (Exception e) {

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (!Brcurrent_address_state.equals("") && !Brcurrent_address_state.equals("null")) {
                            permanentstateID = Brcurrent_address_state;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (!Brcurrent_address_city.equals("") && !Brcurrent_address_city.equals("null")) {
                            permanentcityID = Brcurrent_address_city;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    SwitchAddressSameAs = Bris_borrower_permanent_address_same_as;

                    switchIsPermanentAddressSame.setChecked(true);

                }

                try {
                    if (!Broffice_address_country.equals("") && !Broffice_address_country.equals("null")) {
                        try {
                            offcountryID = Broffice_address_country;

                            int count = borrowerOffCountryPersonalPOJOArrayList.size();
                            for (int i = 0; i < count; i++) {
                                if (borrowerOffCountryPersonalPOJOArrayList.get(i).countryID.equalsIgnoreCase(offcountryID)) {
                                    spCountryOff.setSelection(i);
                                }
                            }

                        } catch (Exception e) {

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (!Broffice_address_state.equals("") && !Broffice_address_state.equals("null")) {
                        offstateID = Broffice_address_state;
                        try {
                            int count = borrowerOffStatePersonalPOJOArrayList.size();
                            for (int i = 0; i < count; i++) {
                                if (borrowerOffStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(offstateID)) {
                                    spStateOff.setSelection(i);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (!Broffice_address_city.equals("") && !Broffice_address_city.equals("null")) {
                        offcityID = Broffice_address_city;
                        try {
                            int count = borrowerOffCityPersonalPOJOArrayList.size();
                            for (int i = 0; i < count; i++) {
                                if (borrowerOffCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(offcityID)) {
                                    spCityOff.setSelection(i);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                lead_status_id = jsonleadStatus.getString("lead_status_id");
//                fk_lead_id = jsonleadStatus.getString("fk_lead_id");
//                lead_status = jsonleadStatus.getString("lead_status");
//                lead_sub_status = jsonleadStatus.getString("lead_sub_status");
//                current_stage = jsonleadStatus.getString("current_stage");

//          try {
//             current_status = jsonleadStatus.getString("current_status");
//             lead_drop_status = jsonleadStatus.getString("lead_drop_status");
//             lead_reject_status = jsonleadStatus.getString("lead_reject_status");
//             lead_initiated_datetime = jsonleadStatus.getString("lead_initiated_datetime");
//             is_lead_owner_added = jsonleadStatus.getString("is_lead_owner_added");
//             lead_owner_added_datetime = jsonleadStatus.getString("lead_owner_added_datetime");
//             lead_owner_added_by = jsonleadStatus.getString("lead_owner_added_by");
//             is_lead_counsellor_added = jsonleadStatus.getString("is_lead_counsellor_added");
//             lead_counsellor_added_datetime = jsonleadStatus.getString("lead_counsellor_added_datetime");
//             lead_counsellor_added_by = jsonleadStatus.getString("lead_counsellor_added_by");
//             is_kyc_details_filled = jsonleadStatus.getString("is_kyc_details_filled");
//             kyc_details_filled_datetime = jsonleadStatus.getString("kyc_details_filled_datetime");
//             kyc_details_filled_by = jsonleadStatus.getString("kyc_details_filled_by");
//             coborrower_added_datetime = jsonleadStatus.getString("coborrower_added_datetime");
//             coborrower_added_by_id = jsonleadStatus.getString("coborrower_added_by_id");
//             is_detailed_info_filled = jsonleadStatus.getString("is_detailed_info_filled");
//             detailed_info_filled_datetime = jsonleadStatus.getString("detailed_info_filled_datetime");
//             detailed_info_filled_by_id = jsonleadStatus.getString("detailed_info_filled_by_id");
//             approval_request_sales_status = jsonleadStatus.getString("approval_request_sales_status");
//             approval_request_sales_status_datetime = jsonleadStatus.getString("approval_request_sales_status_datetime");
//             approval_request_sales_status_by_id = jsonleadStatus.getString("approval_request_sales_status_by_id");
//             list_of_LAF_info_pending = jsonleadStatus.getString("list_of_LAF_info_pending");
//             list_of_LAF_info_filled = jsonleadStatus.getString("list_of_LAF_info_filled");
//             IPA_status = jsonleadStatus.getString("IPA_status");
//             IPA_datetime = jsonleadStatus.getString("IPA_datetime");
//             IPA_by_id = jsonleadStatus.getString("IPA_by_id");
//             docs_upload_status = jsonleadStatus.getString("docs_upload_status");
//             docs_upload_datetime = jsonleadStatus.getString("docs_upload_datetime");
//             list_of_uplaoded_docs = jsonleadStatus.getString("list_of_uplaoded_docs");
//             list_of_pendingdocs = jsonleadStatus.getString("list_of_pendingdocs");
//             docs_verification_status = jsonleadStatus.getString("docs_verification_status");
//             docs_verification_datetime = jsonleadStatus.getString("docs_verification_datetime");
//             credit_approval_request_status = jsonleadStatus.getString("credit_approval_request_status");
//             credit_approval_request_status_datetime = jsonleadStatus.getString("credit_approval_request_status_datetime");
//             credit_approval_request_status_by_id = jsonleadStatus.getString("credit_approval_request_status_by_id");
//             applicant_ekyc_status = jsonleadStatus.getString("applicant_ekyc_status");
//             applicant_ekyc_datetime = jsonleadStatus.getString("applicant_ekyc_datetime");
//             co_applicant_ekyc_status = jsonleadStatus.getString("co_applicant_ekyc_status");
//             co_applicant_ekyc_datetime = jsonleadStatus.getString("co_applicant_ekyc_datetime");
//             credit_assessment_status = jsonleadStatus.getString("credit_assessment_status");
//             credit_assessment_by_id = jsonleadStatus.getString("credit_assessment_by_id");
//             credit_assessment_datetime = jsonleadStatus.getString("credit_assessment_datetime");
//             loan_product_selection_status = jsonleadStatus.getString("loan_product_selection_status");
//             loan_product_by_id = jsonleadStatus.getString("loan_product_by_id");
////                loan_product_datetime = jsonleadStatus.getString("loan_product_datetime");
////                underwriting_status = jsonleadStatus.getString("underwriting_status");
////                underwriting_by_id = jsonleadStatus.getString("underwriting_by_id");
////                underwriting_datetime = jsonleadStatus.getString("underwriting_datetime");
////                is_processing_fees_set = jsonleadStatus.getString("is_processing_fees_set");
////                processing_fees_set_datetime = jsonleadStatus.getString("processing_fees_set_datetime");
////                processing_fees_set_by_id = jsonleadStatus.getString("processing_fees_set_by_id");
////                processing_fees_paid = jsonleadStatus.getString("processing_fees_paid");
////                processing_fees_paid_datetime = jsonleadStatus.getString("processing_fees_paid_datetime");
////                processing_fees_paid_by = jsonleadStatus.getString("processing_fees_paid_by");
////                lender_creation_status = jsonleadStatus.getString("lender_creation_status");
////                lender_creation_modified_datetime = jsonleadStatus.getString("lender_creation_modified_datetime");
////                lender_creation_modified_by = jsonleadStatus.getString("lender_creation_modified_by");
////                amort_creation_status = jsonleadStatus.getString("amort_creation_status");
////                amort_creation_modified_datetime = jsonleadStatus.getString("amort_creation_modified_datetime");
////                amort_creation_modified_by = jsonleadStatus.getString("amort_creation_modified_by");
////                borrower_pan_ekyc_response = jsonleadStatus.getString("borrower_pan_ekyc_response");
////                borrower_aadhar_ekyc_response = jsonleadStatus.getString("borrower_aadhar_ekyc_response");
////                borrower_pan_ekyc_status = jsonleadStatus.getString("borrower_pan_ekyc_status");
////                borrower_aadhar_ekyc_status = jsonleadStatus.getString("borrower_aadhar_ekyc_status");
////                coborrower_pan_ekyc_response = jsonleadStatus.getString("coborrower_pan_ekyc_response");
////                coborrower_aadhar_ekyc_response = jsonleadStatus.getString("coborrower_aadhar_ekyc_response");
////                coborrower_aadhar_ekyc_status = jsonleadStatus.getString("coborrower_aadhar_ekyc_status");
////                coborrower_pan_ekyc_status = jsonleadStatus.getString("coborrower_pan_ekyc_status");
////                is_cam_uploaded = jsonleadStatus.getString("is_cam_uploaded");
////                is_finbit_uploaded = jsonleadStatus.getString("is_finbit_uploaded");
////                is_exception_uploaded = jsonleadStatus.getString("is_exception_uploaded");
////                is_loan_agreement_uploaded = jsonleadStatus.getString("is_loan_agreement_uploaded");
////                loan_agreement_uploaded_by = jsonleadStatus.getString("loan_agreement_uploaded_by");
////                applicant_pan_verified_by = jsonleadStatus.getString("applicant_pan_verified_by");
////                coapplicant_pan_verified_by = jsonleadStatus.getString("coapplicant_pan_verified_by");
////                applicant_aadhar_verified_by = jsonleadStatus.getString("applicant_aadhar_verified_by");
////                applicant_pan_verified_on = jsonleadStatus.getString("applicant_pan_verified_on");
////                coapplicant_pan_verified_on = jsonleadStatus.getString("coapplicant_pan_verified_on");
////                applicant_aadhar_verified_on = jsonleadStatus.getString("applicant_aadhar_verified_on");
////                coapplicant_aadhar_verified_on = jsonleadStatus.getString("coapplicant_aadhar_verified_on");
////                coapplicant_aadhar_verified_by = jsonleadStatus.getString("coapplicant_aadhar_verified_by");
////                created_date_time = jsonleadStatus.getString("created_date_time");
////                created_ip_address = jsonleadStatus.getString("created_ip_address");
////                modified_by = jsonleadStatus.getString("modified_by");
////                modified_date_time = jsonleadStatus.getString("modified_date_time");
////                modified_ip_address = jsonleadStatus.getString("modified_ip_address");
////                is_deleted = jsonleadStatus.getString("is_deleted");
//             borrower_required_docs = jsonleadStatus.getString("borrower_required_docs");
//             co_borrower_required_docs = jsonleadStatus.getString("co_borrower_required_docs");
//             co_borrower_pending_docs = jsonleadStatus.getString("co_borrower_pending_docs");
//             borrower_extra_required_docs = jsonleadStatus.getString("borrower_extra_required_docs");
//             co_borrower_extra_required_docs = jsonleadStatus.getString("co_borrower_extra_required_docs");
//          } catch (JSONException e) {
//             e.printStackTrace();
//          }

            } else {
//          Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }


    public void submitDetailedInfo() {
        try {

            String url = MainActivity.mainUrl + "dashboard/editDetailedInformation";
            Map<String, String> params = new HashMap<String, String>();
            params.put("lead_id", lead_id);
            params.put("applicant_id", application_id);
//            params.put("is_borrower_current_address_same_as", Bris_borrower_current_address_same_as);

            params.put("is_borrower_permanent_address_same_as", Bris_borrower_permanent_address_same_as);
            params.put("current_address_stay_duration", Brcurrent_address_stay_duration);
            params.put("current_residence_type", Brcurrent_residence_type);
            params.put("permanent_address", Brpermanent_address);
            params.put("permanent_landmark", Brpermanent_landmark);
            params.put("permanent_address_pin",Brpermanent_address_pin);

            params.put("permanent_address_country", permanentcountryID);
            params.put("permanent_address_state", permanentstateID);
            params.put("permanent_address_city", permanentcityID);

            params.put("office_address", Broffice_address);
            params.put("office_landmark", Broffice_landmark);
            params.put("office_address_pin", Broffice_address_pin);
            params.put("office_address_country", offcountryID);
            params.put("office_address_city", offcityID);
            params.put("office_address_state",offstateID );

            params.put("profession", Brprofession);
            params.put("employer_type", Bremployer_type);
            params.put("annual_income", Brannual_income);
            params.put("employer_name", Bremployer_name);
            params.put("current_employment_duration", Brcurrent_employment_duration);

//            params.put("coapplicant_id", CoBrapplicant_id);
//            params.put("corelationship_with_applicant", CoBrrelationship_with_applicant);
//            params.put("cocurrent_address_stay_duration", CoBrcurrent_address_stay_duration);
//            params.put("cocurrent_residence_type", currentResidencetypeIDCoBr);
//            params.put("cocurrent_address_rent", edtMonthlyRentCoBr.getText().toString());
//            params.put("cois_coborrower_current_address_same_as", CoBris_coborrower_current_address_same_as);

//            params.put("cocurrent_address", edtCurrentAddressCoBr.getText().toString());
//            params.put("cocurrent_landmark", edtCurrentLandmarkCoBr.getText().toString());
//            params.put("cocurrent_address_pin", edtCurrentPincodeCoBr.getText().toString());
//            params.put("cocurrent_address_country", currentcountryIDCoBr);
//            params.put("cocurrent_address_state", currentstateIDCoBr);
//            params.put("cocurrent_address_city", currentcityIDCoBr);
//            params.put("cois_coborrower_permanent_address_same_as", CoBris_coborrower_permanent_address_same_as);
//
//            params.put("copermanent_address", edtPermanentAddressCoBr.getText().toString());
//            params.put("copermanent_landmark", edtPermanentLandmarkCoBr.getText().toString());
//            params.put("copermanent_address_pin", edtPermanentPincodeCoBr.getText().toString());
//            params.put("copermanent_address_country", permanentcountryIDCoBr);
//            params.put("copermanent_address_state", permanentstateIDCoBr);
//            params.put("copermanent_address_city", permanentcityIDCoBr);
//
//            params.put("cooffice_address", edtAddressOffCoBr.getText().toString());
//            params.put("cooffice_landmark", edtLandmarkOffCoBr.getText().toString());
//            params.put("cooffice_address_pin", edtPincodeOffCoBr.getText().toString());
//            params.put("cooffice_address_country", offcountryIDCoBr);
//            params.put("cooffice_address_city", offcityIDCoBr);
//            params.put("cooffice_address_state", offstateIDCoBr);
//
//
//            params.put("coprofession", CoBrprofession);
//            params.put("coemployer_name", edtCompanyCoBr.getText().toString());
//            params.put("coemployer_type", CoBremployer_type);
//            params.put("coannual_income", edtAnnualSalCoBr.getText().toString());
//            params.put("cocurrent_employment_duration", spdurationofjobCoBr.getSelectedItemPosition() + "");  ///////////////Make list of pjo model

            Log.d("DETAILEDINFO", "PARAMS" + params.toString());

            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "editDetailedInformation", params, MainActivity.auth_token);
            }

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }
    public void editDetailedInfoResponse(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (jsonData.getInt("status") == 1) {
                mListener.onDetailedInfoFragment(true,2);
//                LoanApplicationFragment_2 loanApplicationFragment_2 = new LoanApplicationFragment_2();
//                transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_2).commit();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public interface onDetailedInfoFragmentInteractionListener{
        void onDetailedInfoFragment(boolean valid,int next);
    }

}