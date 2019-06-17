package com.eduvanzapplication.newUI.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceDurationPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentResidenceTypePersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerOffCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerOffCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerOffStatePersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerPermanentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerPermanentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerPermanentStatePersonalPOJO;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.newViews.LoanTabActivity;
import com.eduvanzapplication.newUI.pojo.ProfessionPOJO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.eduvanzapplication.newUI.fragments.DashboardFragmentNew.isLeadReload;
import static com.eduvanzapplication.newUI.fragments.KycDetailFragment.ivCourseToggle;
import static com.eduvanzapplication.newUI.fragments.KycDetailFragment.txtIdentityToggle;

public class DetailedInfoFragment extends Fragment {

    public static ProgressDialog progressDialog;

    public static ViewPager viewPager;
    public static ImageButton btnNext;
    public static TextView txtResidentialToggle, txtProfessionalToggle;
    public static LinearLayout linResidentialToggle, linProfessionalToggle, linEditDetailedInfo;
    public static ImageView ivResidentialToggle, ivProfessionalToggle, ivResidentialTitle, ivResidentialStatus, ivProfessionalTitle, ivProfessionalStatus;
    public static LinearLayout linResidentialBlock, linProfessionalBlock;
    public static Animation collapseanimationResidential, expandAnimationResidential,
            collapseanimationProfessional, expandAnimationProfessional;
    public static onDetailedInfoFragmentInteractionListener mListener;
    public static TextView txtBtnEditDtl;
    public static ImageView ivBtnEditDtl;
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

    public ArrayList<String> duarationOfWorkListBr;
    public ArrayList<String> employerType;

    public static String offcityID = "", offstateID = "", offcountryID = "",
            currentcityID = "", currentstateID = "", currentcountryID = "",
            permanentcityID = "", permanentstateID = "", permanentcountryID = "", SwitchAddressSameAs = "";

    public static String lead_id = "", application_id = "", requested_loan_amount = "", has_coborrower = "";

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
            Brpermanent_address_stay_duration = "", Bris_deleted = "";

    public static String lead_status = "", lead_sub_status = "", current_stage = "", current_status = "", id = "";

    static View view;

    public static Context context;
    public static Fragment mFragment;
    public static LinearLayout linBorrowerForm, linIfAddressNotSame;


    //error
    public static TextView txtResidentialDetailsErrMsg, txtProfessionalDetailsErrMsg, tvOwnRenbytitle,
            tvduraofstayatcurraddtitle, tvcountrytitle,tvcountrytitle1, tvstatetitle,tvstatetitle1, tvcitytitle,tvcitytitle1, tvselectprofesstitle,
            tvperaddsameasaadhrtitle,tvemplytypetitle, tvcurremplydurntitle,txtCurrentResidentType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_detailedinfo_stepper, parent, false);
        context = getContext();
        mFragment = new DetailedInfoFragment();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        progressDialog = new ProgressDialog(context);

        txtBtnEditDtl = view.findViewById(R.id.txtBtnEditDtl);
        ivBtnEditDtl = view.findViewById(R.id.ivBtnEditDtl);
        linEditDetailedInfo = view.findViewById(R.id.linEditDetailedInfo);
        btnNext = view.findViewById(R.id.btnNext);

        ivResidentialToggle = view.findViewById(R.id.ivResidentialToggle);
        ivProfessionalToggle = view.findViewById(R.id.ivProfessionalToggle);

        ivResidentialTitle = view.findViewById(R.id.ivResidentialTitle);
        ivResidentialStatus = view.findViewById(R.id.ivResidentialStatus);
        ivProfessionalTitle = view.findViewById(R.id.ivProfessionalTitle);
        ivProfessionalStatus = view.findViewById(R.id.ivProfessionalStatus);

        txtResidentialToggle = view.findViewById(R.id.txtResidentialToggle);
        linResidentialBlock = view.findViewById(R.id.linResidentialBlock);

        linResidentialToggle = view.findViewById(R.id.linResidentialToggle);
        linProfessionalToggle = view.findViewById(R.id.linProfessionalToggle);

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

        //new add field

        tvOwnRenbytitle = view.findViewById(R.id.tvOwnRenbytitle);
        tvduraofstayatcurraddtitle = view.findViewById(R.id.tvduraofstayatcurraddtitle);
        tvcountrytitle = view.findViewById(R.id.tvcountrytitle);
        tvcountrytitle1 = view.findViewById(R.id.tvcountrytitle1);
        tvstatetitle = view.findViewById(R.id.tvstatetitle);
        tvcitytitle = view.findViewById(R.id.tvcitytitle);
        tvstatetitle1 = view.findViewById(R.id.tvstatetitle1);
        tvcitytitle1 = view.findViewById(R.id.tvcitytitle1);
        tvselectprofesstitle = view.findViewById(R.id.tvselectprofesstitle);
        tvemplytypetitle = view.findViewById(R.id.tvemplytypetitle);
        tvcurremplydurntitle = view.findViewById(R.id.tvcurremplydurntitle);
        tvperaddsameasaadhrtitle=view.findViewById(R.id.tvperaddsameasaadhrtitle);
        txtCurrentResidentType=view.findViewById(R.id.txtCurrentResidentType);

        //ErrorMsg ID
        txtResidentialDetailsErrMsg = view.findViewById(R.id.txtResidentialDetailsErrMsg);
        txtProfessionalDetailsErrMsg = view.findViewById(R.id.txtProfessionalDetailsErrMsg);

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

        Brapplicant_id = "";
        Brfk_lead_id = "";
        Brfk_applicant_type_id = "";
        Brfirst_name = "";
        Brmiddle_name = "";
        Brlast_name = "";
        Brhas_aadhar_pan = "";
        Brdob = "";
        Brpan_number = "";
        Braadhar_number = "";
        Brmarital_status = "";
        Brgender_id = "";
        Brmobile_number = "";
        Bremail_id = "";
        Brrelationship_with_applicant = "";
        Brprofession = "";
        Bremployer_type = "";
        Bremployer_name = "";
        Brannual_income = "";
        Brcurrent_employment_duration = "";
        Brtotal_employement_duration = "";
        Bremployer_mobile_number = "";
        Bremployer_landline_number = "";
        Broffice_landmark = "";
        Broffice_address = "";
        Broffice_address_city = "";
        Broffice_address_state = "";
        Broffice_address_country = "";
        Broffice_address_pin = "";
        Brhas_active_loan = "";
        BrEMI_Amount = "";
        Brkyc_landmark = "";
        Brkyc_address = "";
        Brkyc_address_city = "";
        Brkyc_address_state = "";
        Brkyc_address_country = "";
        Brkyc_address_pin = "";
        Bris_borrower_current_address_same_as = "";
        Bris_coborrower_current_address_same_as = "";
        Brcurrent_residence_type = "";
        Brcurrent_landmark = "";
        Brcurrent_address = "";
        Brcurrent_address_city = "";
        Brcurrent_address_state = "";
        Brcurrent_address_country = "";
        Brcurrent_address_pin = "";
        Brcurrent_address_rent = "";
        Brcurrent_address_stay_duration = "";
        Bris_borrower_permanent_address_same_as = "";
        Bris_coborrower_permanent_address_same_as = "";
        Brpermanent_residence_type = "";
        Brpermanent_landmark = "";
        Brpermanent_address = "";
        Brpermanent_address_city = "";
        Brpermanent_address_state = "";
        Brpermanent_address_country = "";
        Brpermanent_address_pin = "";
        Brpermanent_address_rent = "";
        Brpermanent_address_stay_duration = "";
        Bris_deleted = "";

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LoanTabActivity.isDetailedInfoEdit) {
                    validate();
                } else {
                    if (LoanTabActivity.isDetailedInfoEdit) {
                        checkAllFields();
                    }
                    mListener.onDetailedInfoFragment(true, 2);
                }

//                if (LoanTabActivity.isDetailedInfoEdit) {
//                    if (Brpermanent_address_pin.equals("") || Brpermanent_address.equals("") || Brpermanent_landmark.equals("")
//                            || permanentcityID.equals("") || permanentstateID.equals("") || permanentcountryID.equals("") || Brprofession.equals("")
//                            || Bremployer_name.equals("") || Brannual_income.equals("") || Broffice_address_pin.equals("") || Broffice_address.equals("")
//                            || Broffice_landmark.equals("") || Brcurrent_employment_duration.equals("") || Broffice_address_country.equals("")
//                            || offstateID.equals("") || offcityID.equals("")) {
//                        mListener.onDetailedInfoFragment(false, 1);
//                    } else {
//                        submitDetailedInfo();
////                        mListener.onDetailedInfoFragment(true,2);
//                    }
//                } else {
//                    mListener.onDetailedInfoFragment(true, 2);
//
//                }
            }
        });

//        linResidentialBlock.startAnimation(expandAnimationResidential);
//        linProfessionalBlock.startAnimation(collapseanimationProfessional);
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
                    //    txtProfessionalToggle.requestFocus();
                } else {
                    linProfessionalBlock.startAnimation(expandAnimationProfessional);
                }
            }
        });

        linResidentialToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linResidentialBlock.getVisibility() == VISIBLE) {
                    linResidentialBlock.startAnimation(collapseanimationResidential);
                    linResidentialToggle.requestFocus();
                } else {
                    linResidentialBlock.startAnimation(expandAnimationResidential);
                }
            }
        });

        linProfessionalToggle.setOnClickListener(new View.OnClickListener() {
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
                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_angle_down, null);
                    ivResidentialToggle.setColorFilter(context.getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_angle_down);
                    DrawableCompat.setTint(bg, context.getResources().getColor(R.color.darkblue));
                }
                ivResidentialToggle.setImageDrawable(bg);
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
                if (linProfessionalBlock.getVisibility() == VISIBLE) {
                    linProfessionalBlock.startAnimation(collapseanimationProfessional);
                }
                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_angle_down, null);
                    ivResidentialToggle.setColorFilter(context.getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_angle_down);
                    DrawableCompat.setTint(bg, context.getResources().getColor(R.color.darkblue));
                }
                ivResidentialToggle.setImageDrawable(bg);
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
                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_angle_up, null);
                    ivProfessionalToggle.setColorFilter(context.getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_angle_up);
                    DrawableCompat.setTint(bg, context.getResources().getColor(R.color.darkblue));
                }
                ivProfessionalToggle.setImageDrawable(bg);
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
                if (linResidentialBlock.getVisibility() == VISIBLE) {
                    linResidentialBlock.startAnimation(collapseanimationResidential);
                }
                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_angle_up, null);
                    ivProfessionalToggle.setColorFilter(context.getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_angle_up);
                    DrawableCompat.setTint(bg, context.getResources().getColor(R.color.darkblue));
                }
                ivProfessionalToggle.setImageDrawable(bg);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        switchIsPermanentAddressSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Current Add not same as Permanent
                    SwitchAddressSameAs = "3";
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
                                    break;
                                }
                                if (LoanTabActivity.isDetailedInfoEdit) {
                                    checkAllFields();
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
                                        break;
                                    }
                                    if (LoanTabActivity.isDetailedInfoEdit) {
                                        checkAllFields();
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
                                        break;
                                    }

                                }
                                if (LoanTabActivity.isDetailedInfoEdit) {
                                    checkAllFields();
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
                            Brpermanent_address = edtPermanentAddress.getText().toString();
                            if (LoanTabActivity.isDetailedInfoEdit) {
                                checkAllFields();
                            }
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
                            if (LoanTabActivity.isDetailedInfoEdit) {
                                checkAllFields();
                            }
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
                            Brpermanent_address_pin = edtPermanentPincode.getText().toString();
                            if (LoanTabActivity.isDetailedInfoEdit) {
                                checkAllFields();
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                } else {
                    //Current Add same as Permanent
                    SwitchAddressSameAs = "2";
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

                    try {
                        int count = borrowerCurrentResidencePersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase(offstateID)) {
                                spOwnedBy.setSelection(i);
                                break;
                            }
                            if (LoanTabActivity.isDetailedInfoEdit) {
                                checkAllFields();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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
//                    edtMonthlyRent.setEnabled(true);

                    try {
                        int count = borrowerCurrentResidencePersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase(offstateID)) {
                                spOwnedBy.setSelection(i);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        linEditDetailedInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LoanTabActivity.isDetailedInfoEdit) {
                    LoanTabActivity.isDetailedInfoEdit = false;
                    txtBtnEditDtl.setText("Edit");
                    setViewsEnabled(false);

                    Drawable bg;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_pencil_alt, null);
                        ivBtnEditDtl.setColorFilter(context.getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg = ContextCompat.getDrawable(context, R.drawable.ic_pencil_alt);
                        DrawableCompat.setTint(bg, context.getResources().getColor(R.color.white));
                    }
                    ivBtnEditDtl.setImageDrawable(bg);
                    linEditDetailedInfo.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));
                } else {
                    LoanTabActivity.isDetailedInfoEdit = true;
                    txtBtnEditDtl.setText("Cancel");
                    txtBtnEditDtl.setTag("Cancel");
                    setViewsEnabled(true);

                    Drawable bg;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_clear, null);
                        ivBtnEditDtl.setColorFilter(context.getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg = ContextCompat.getDrawable(context, R.drawable.ic_clear);
                        DrawableCompat.setTint(bg, context.getResources().getColor(R.color.white));
                    }
                    ivBtnEditDtl.setImageDrawable(bg);
                    linEditDetailedInfo.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                }
                if (LoanTabActivity.isDetailedInfoEdit) {
                    checkAllFields();
                }

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
                            }

                        }
                        if (LoanTabActivity.isDetailedInfoEdit) {
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

        spDurationOfJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Brcurrent_employment_duration = position + "";
                    if (LoanTabActivity.isDetailedInfoEdit) {
                        checkAllFields();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spEmployerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spEmployerType.getSelectedItem().toString();
                try {
                    if (position > 0) {
                        Bremployer_type = position - 1 + "";
                    } else {
                        Bremployer_type = position + "";
                    }
                    if (LoanTabActivity.isDetailedInfoEdit) {
                        checkAllFields();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spOwnedBy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spOwnedBy.getSelectedItem().toString();
                    if (borrowerCurrentResidencePersonalPOJOArrayList != null) {
                        int count = borrowerCurrentResidencePersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).residencetypeName.equalsIgnoreCase(text)) {
                                Brcurrent_residence_type = currentResidencetypeID = borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID;

                                break;
                            }
                        }
                        if (LoanTabActivity.isDetailedInfoEdit) {
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
                                break;
                            }
                        }
                        if (LoanTabActivity.isDetailedInfoEdit) {
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

                                break;
                            }
                        }
                        if (LoanTabActivity.isDetailedInfoEdit) {
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
                                break;
                            }
                        }
                        if (LoanTabActivity.isDetailedInfoEdit) {
                            checkAllFields();
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
                        for (int i = 1; i < count; i++) {
                            if (borrowerPermanentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                                permanentcountryID = Brpermanent_address_country = borrowerPermanentCountryPersonalPOJOArrayList.get(i).countryID;
                                break;
                            }
                        }
                        if (LoanTabActivity.isDetailedInfoEdit) {
                            checkAllFields();
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

                                break;
                            }
                        }
                        if (LoanTabActivity.isDetailedInfoEdit) {
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

                                break;
                            }
                        }
                        if (LoanTabActivity.isDetailedInfoEdit) {
                            checkAllFields();
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

                                break;
                            }
                        }
                        if (LoanTabActivity.isDetailedInfoEdit) {
                            checkAllFields();
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
                if (LoanTabActivity.isDetailedInfoEdit) {
                    checkAllFields();
                }
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
                Brannual_income = edtAnnualIncome.getText().toString();
                if (LoanTabActivity.isDetailedInfoEdit) {
                    checkAllFields();
                }
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
                Broffice_address = edtAddressOff.getText().toString();
                if (LoanTabActivity.isDetailedInfoEdit) {
                    checkAllFields();
                }
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
                if (LoanTabActivity.isDetailedInfoEdit) {
                    checkAllFields();
                }
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
                if (LoanTabActivity.isDetailedInfoEdit) {
                    checkAllFields();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ProfessionApiCall();
        countryApiCall();
        permanentstateApiCall();
        offstateApiCall();
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

            txtCurrentResidentType.setEnabled(f);

            tvperaddsameasaadhrtitle.setEnabled(f);
            tvOwnRenbytitle.setEnabled(f);
            tvduraofstayatcurraddtitle.setEnabled(f);
            tvcountrytitle.setEnabled(f);
            tvcountrytitle1.setEnabled(f);
            tvstatetitle.setEnabled(f);
            tvcitytitle.setEnabled(f);
            tvstatetitle1.setEnabled(f);
            tvcitytitle1.setEnabled(f);
            tvselectprofesstitle.setEnabled(f);
            tvemplytypetitle.setEnabled(f);
            tvcurremplydurntitle.setEnabled(f);


            switchIsPermanentAddressSame.setEnabled(f);
            switchResidenceType.setEnabled(f);

            linResidentialBlock.setEnabled(f);
            linProfessionalBlock.setEnabled(f);
            edtMonthlyRent.setEnabled(f);
            edtCompanyName.setEnabled(f);
            edtAnnualIncome.setEnabled(f);
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

            if(!f){

                if (LoanTabActivity.isDetailedInfoEdit) {

                indicateValidationTextdefault(txtResidentialToggle, false);
                indicateValidationTextdefault(txtProfessionalToggle, false);
                indicateValidationIcondefault(ivResidentialStatus, ivResidentialTitle, false);
                indicateValidationIcondefault(ivProfessionalStatus, ivProfessionalTitle, false);

                indicateValidationTextdefaultBlue(txtResidentialToggle, false);
                indicateValidationTextdefaultBlue(txtProfessionalToggle, false);
                indicateValidationIcondefaultBlue(ivResidentialStatus, ivResidentialTitle, false);
                indicateValidationIcondefaultBlue(ivProfessionalStatus, ivProfessionalTitle, false);

                }else{

                    if (txtBtnEditDtl.getTag() != null) {
                        if (switchIsPermanentAddressSame.isChecked()){

                        //Residential

                        if (Brcurrent_address_stay_duration.equals("") || Brcurrent_residence_type.equals("") || Brpermanent_address_pin.equals("") || Brpermanent_address.equals("") || Brpermanent_landmark.equals("")
                                || permanentcityID.equals("") || permanentstateID.equals("") || permanentcountryID.equals(""))
                        {

                indicateValidationTextdefault(txtResidentialToggle, false);
                            indicateValidationIcondefault(ivResidentialStatus, ivResidentialTitle, false);
                        }
                        else{
                            indicateValidationTextdefault(txtResidentialToggle, false);
                            indicateValidationIcondefault(ivResidentialStatus, ivResidentialTitle, true);

                        }}

                        else if (!switchIsPermanentAddressSame.isChecked()) {

                            if (Brcurrent_address_stay_duration.equals("") || Brcurrent_residence_type.equals("")){

                                indicateValidationTextdefault(txtResidentialToggle, false);
                                indicateValidationIcondefault(ivResidentialStatus, ivResidentialTitle, false);
                            }   else{

                                indicateValidationTextdefault(txtResidentialToggle, false);
                                indicateValidationIcondefault(ivResidentialStatus, ivResidentialTitle, true);
                            }
                        }




                     //profession
                        if(Brprofession.equals("") || Bremployer_name.equals("") || Brannual_income.equals("")
                                || Broffice_address_pin.equals("") || Broffice_address.equals("") || Broffice_landmark.equals("")
                                || Brcurrent_employment_duration.equals("") || Broffice_address_country.equals("") || offstateID.equals("") || offcityID.equals(""))
                        {
                indicateValidationTextdefault(txtProfessionalToggle, false);
                indicateValidationIcondefault(ivProfessionalStatus, ivProfessionalTitle, false);
                        }
                        else{

                            indicateValidationTextdefault(txtProfessionalToggle, false);
                            indicateValidationIcondefault(ivProfessionalStatus, ivProfessionalTitle, true);

                }





            }else{

                        //onpage load

                        indicateValidationTextdefault(txtResidentialToggle, false);
                        indicateValidationTextdefault(txtProfessionalToggle, false);

                        /* indicateValidationTextdefaultBlue(txtResidentialToggle, false);
                        indicateValidationTextdefaultBlue(txtProfessionalToggle, false);*/


                    }
                }

            }else{

                if (txtBtnEditDtl.getText().toString().toLowerCase().contains("cancel")) {

                    indicateValidationTextdefaultBlue(txtResidentialToggle, true);
                    indicateValidationTextdefaultBlue(txtProfessionalToggle, true);

                    indicateValidationIcondefaultBlue(ivResidentialStatus, ivResidentialTitle, true);
                    indicateValidationIcondefaultBlue(ivProfessionalStatus, ivProfessionalTitle, true);
                    //checkAllFields();
                }else{

                  /*  indicateValidationTextdefault(txtResidentialToggle, true);
                    indicateValidationTextdefault(txtProfessionalToggle, true);
                    indicateValidationIcondefault(ivResidentialStatus, ivResidentialTitle, true);
                    indicateValidationIcondefault(ivProfessionalStatus, ivProfessionalTitle, true);

                    indicateValidationTextdefaultBlue(txtResidentialToggle, true);
                    indicateValidationTextdefaultBlue(txtProfessionalToggle, true);
                    indicateValidationIcondefaultBlue(ivResidentialStatus, ivResidentialTitle, true);
                    indicateValidationIcondefaultBlue(ivProfessionalStatus, ivProfessionalTitle, true);*/

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


    public void indicateValidationIcondefault(ImageView ivStatus, ImageView ivTitle, boolean valid) {

        if (valid) {
            Drawable bg;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_check_circle_grey, null);
                ivStatus.setColorFilter(context.getResources().getColor(R.color.defaulticoncolor), PorterDuff.Mode.MULTIPLY);
            } else {
                bg = ContextCompat.getDrawable(context, R.drawable.ic_check_circle_grey);
                DrawableCompat.setTint(bg, context.getResources().getColor(R.color.defaulticoncolor));
            }
            ivStatus.setImageDrawable(bg);

            Drawable bg1;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                ivTitle.setColorFilter(context.getResources().getColor(R.color.defaulticoncolor), PorterDuff.Mode.MULTIPLY);
            } else {
                bg1 = ivTitle.getDrawable();
                DrawableCompat.setTint(bg1, context.getResources().getColor(R.color.defaulticoncolor));
                ivTitle.setImageDrawable(bg1);
            }

        } else {
            Drawable bg;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_exclamation_circle, null);
                ivStatus.setColorFilter(context.getResources().getColor(R.color.defaulticoncolor), PorterDuff.Mode.MULTIPLY);
            } else {
                bg = ContextCompat.getDrawable(context, R.drawable.ic_exclamation_circle);
                DrawableCompat.setTint(bg, context.getResources().getColor(R.color.defaulticoncolor));
            }
            ivStatus.setImageDrawable(bg);

            Drawable bg1;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                ivTitle.setColorFilter(context.getResources().getColor(R.color.defaulticoncolor), PorterDuff.Mode.MULTIPLY);
            } else {
                bg1 = ivTitle.getDrawable();
                DrawableCompat.setTint(bg1, context.getResources().getColor(R.color.defaulticoncolor));
                ivTitle.setImageDrawable(bg1);
            }
        }

    }
    public void indicateValidationIcondefaultBlue(ImageView ivStatus, ImageView ivTitle, boolean valid) {

        if (valid) {

          /*  Drawable bg;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_check_circle_green, null);
                ivStatus.setColorFilter(context.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
            } else {
                bg = ContextCompat.getDrawable(context, R.drawable.ic_check_circle_green);
                DrawableCompat.setTint(bg, context.getResources().getColor(R.color.colorPrimary));
            }
            ivStatus.setImageDrawable(bg);
*/          //this above for icon
            Drawable bg1;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                ivTitle.setColorFilter(context.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
            } else {
                bg1 = ivTitle.getDrawable();
                DrawableCompat.setTint(bg1, context.getResources().getColor(R.color.colorPrimary));
                ivTitle.setImageDrawable(bg1);
            }

        } else {

           /* Drawable bg;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_borrower_documents_pending, null);
                ivStatus.setColorFilter(context.getResources().getColor(R.color.defaulticoncolor), PorterDuff.Mode.MULTIPLY);
            } else {
                bg = ContextCompat.getDrawable(context, R.drawable.ic_borrower_documents_pending);
                DrawableCompat.setTint(bg, context.getResources().getColor(R.color.defaulticoncolor));
            }
            ivStatus.setImageDrawable(bg);
*/
            Drawable bg1;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                ivTitle.setColorFilter(context.getResources().getColor(R.color.defaulticoncolor), PorterDuff.Mode.MULTIPLY);
            } else {
                bg1 = ivTitle.getDrawable();
                DrawableCompat.setTint(bg1, context.getResources().getColor(R.color.defaulticoncolor));
                ivTitle.setImageDrawable(bg1);
            }
        }

    }


    public void indicateValidationTextdefault(TextView indicator, boolean valid) {
        if (valid) {
            indicator.setTextColor(context.getResources().getColor(R.color.colorGreen));
        } else {

            indicator.setTextColor(context.getResources().getColor(R.color.defaulticoncolor));
        }

    }

    public void indicateValidationTextdefaultBlue(TextView indicator, boolean valid) {
        if (valid) {
            indicator.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {

            indicator.setTextColor(context.getResources().getColor(R.color.defaulticoncolor));
        }

    }

    public void checkAllFields() {

        if (switchIsPermanentAddressSame.isChecked()) {

            if (Brcurrent_address_stay_duration.equals("") || Brcurrent_residence_type.equals("") || Brpermanent_address_pin.equals("") || Brpermanent_address.equals("") || Brpermanent_landmark.equals("")
                    || permanentcityID.equals("") || permanentstateID.equals("") || permanentcountryID.equals("")) {
                if (LoanTabActivity.isDetailedInfoEdit) {
                    txtResidentialDetailsErrMsg.setVisibility(VISIBLE);
                    if (Brcurrent_residence_type.equals("")) {
                        txtResidentialDetailsErrMsg.setText("*Please select Owned By");
                        //spOwnedBy.requestFocus();

                    } else if (Brcurrent_address_stay_duration.equals("")) {
                        txtResidentialDetailsErrMsg.setText("*Please select Stay Duration");
                        //spDurarionOfStay.requestFocus();
                    } else if (Brpermanent_address.equals("")) {
                        txtResidentialDetailsErrMsg.setText("*Please select Flat No,Building,Society Name");
                        //edtPermanentAddress.requestFocus();

                    } else if (Brpermanent_landmark.equals("")) {
                        txtResidentialDetailsErrMsg.setText("*Please select Street Name,Locality,LandMark");
                        //Brpermanent_landmark
                        //edtPermanentLandmark.requestFocus();


                    } else if (Brpermanent_address_pin.equals("")) {
                        txtResidentialDetailsErrMsg.setText("*Please select Pincode");
                        //edtPermanentPincode.requestFocus();

                    } else if (permanentcountryID.equals("")) {
                        txtResidentialDetailsErrMsg.setText("*Please select Country");
                        // spPermanentCountry.requestFocus();

                    } else if (permanentstateID.equals("")) {
                        txtResidentialDetailsErrMsg.setText("*Please select State");
                        //spPermanentState.requestFocus();

                    } else if (permanentcityID.equals("")) {
                        txtResidentialDetailsErrMsg.setText("*Please select city");
                        //  spPermanentCity.requestFocus();

                    }
                }else{
                    indicateValidationText(txtResidentialToggle, true);
                    indicateValidationIcon(ivResidentialStatus, ivResidentialTitle, true);
                }
                indicateValidationText(txtResidentialToggle, false);
                indicateValidationIcon(ivResidentialStatus, ivResidentialTitle, false);
            } else {
                txtResidentialDetailsErrMsg.setText(null);
                txtResidentialDetailsErrMsg.setVisibility(GONE);
                indicateValidationText(txtResidentialToggle, true);
                indicateValidationIcon(ivResidentialStatus, ivResidentialTitle, true);
            }
        } else if (!switchIsPermanentAddressSame.isChecked()) {

            if (Brcurrent_address_stay_duration.equals("") || Brcurrent_residence_type.equals("")) {
                if (LoanTabActivity.isDetailedInfoEdit) {
                    txtResidentialDetailsErrMsg.setVisibility(VISIBLE);
                    if (Brcurrent_residence_type.equals("")) {
                        txtResidentialDetailsErrMsg.setText("*Please select Owned By");
                    } else if (Brcurrent_address_stay_duration.equals("")) {
                        txtResidentialDetailsErrMsg.setText("*Please select Stay Duration");
                    }
                }else{
                    indicateValidationText(txtResidentialToggle, true);
                    indicateValidationIcon(ivResidentialStatus, ivResidentialTitle, true);

                }
                indicateValidationText(txtResidentialToggle, false);
                indicateValidationIcon(ivResidentialStatus, ivResidentialTitle, false);
            } else {
                txtResidentialDetailsErrMsg.setText(null);
                txtResidentialDetailsErrMsg.setVisibility(GONE);
                indicateValidationText(txtResidentialToggle, true);
                indicateValidationIcon(ivResidentialStatus, ivResidentialTitle, true);
            }
        }
        if (Brprofession.equals("") || Bremployer_name.equals("") || Brannual_income.equals("")
                || Broffice_address_pin.equals("") || Broffice_address.equals("") || Broffice_landmark.equals("")
                || Brcurrent_employment_duration.equals("") || Broffice_address_country.equals("") || offstateID.equals("") || offcityID.equals("")) {
            if (LoanTabActivity.isDetailedInfoEdit) {
                txtProfessionalDetailsErrMsg.setVisibility(VISIBLE);
                // txtProfessionalToggle.requestFocus();
                if (Brprofession.equals("")) {
                    txtProfessionalDetailsErrMsg.setText("*Please select Profession");
                    // spProfession.requestFocus();
                } else if (Bremployer_name.equals("")) {
                    txtProfessionalDetailsErrMsg.setText("*Please select Employer Type");
                    // spEmployerType.requestFocus();
                } else if (Brannual_income.equals("")) {
                    txtProfessionalDetailsErrMsg.setText("*Please select Annual Income");
                    edtAnnualIncome.requestFocus();
                } else if (Brcurrent_employment_duration.equals("")) {
                    txtProfessionalDetailsErrMsg.setText("*Please select Current Employment Duration");
                    //spEmployerType.requestFocus();
                } else if (Broffice_address.equals("")) {
                    txtProfessionalDetailsErrMsg.setText("*Please select Current Flat No,Building,Society Name");
                    //edtAddressOff.requestFocus();
                } else if (Broffice_landmark.equals("")) {
                    txtProfessionalDetailsErrMsg.setText("*Please select Street Name No,Locality,LandMark");
                    //edtLandmarkOff.requestFocus();
                } else if (Broffice_address_pin.equals("")) {
                    txtProfessionalDetailsErrMsg.setText("*Please select Pincode");
                    //edtPincodeOff.requestFocus();
                } else if (Broffice_address_country.equals("")) {
                    txtProfessionalDetailsErrMsg.setText("*Please select Country");
                    //spCountryOff.requestFocus();
                } else if (offstateID.equals("")) {
                    txtProfessionalDetailsErrMsg.setText("*Please select State");
                    //spStateOff.requestFocus();
                } else if (offcityID.equals("")) {
                    txtProfessionalDetailsErrMsg.setText("*Please select City");
                    //spCityOff.requestFocus();
                }
            }

            indicateValidationText(txtProfessionalToggle, false);
            indicateValidationIcon(ivProfessionalStatus, ivProfessionalTitle, false);
        } else {
            txtProfessionalDetailsErrMsg.setText(null);
            txtProfessionalDetailsErrMsg.setVisibility(GONE);
            indicateValidationText(txtProfessionalToggle, true);
            indicateValidationIcon(ivProfessionalStatus, ivProfessionalTitle, true);

        }
//        if(!switchResidenceType.isChecked())
//        {
//            if(edtMonthlyRent.getText().toString().equals("")){
//                indicateValidationText(txtResidentialToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_residential), false);
//            }else {
//                indicateValidationText(txtResidentialToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_residential), true);
//            }
//        }
    }

    public static void validate() {

        if (switchIsPermanentAddressSame.isChecked()) {
            if (Brcurrent_address_stay_duration.equals("") || Brcurrent_residence_type.equals("") || Brpermanent_address_pin.equals("") ||
                    Brpermanent_address.equals("") || Brpermanent_landmark.equals("") || permanentcityID.equals("") ||
                    permanentstateID.equals("") || permanentcountryID.equals("") || Brprofession.equals("") || Bremployer_name.equals("") ||
                    Brannual_income.equals("") || Broffice_address_pin.equals("") || Broffice_address.equals("") || Broffice_landmark.equals("")
                    || Brcurrent_employment_duration.equals("") || Broffice_address_country.equals("") || offstateID.equals("") ||
                    offcityID.equals("")) {
                mListener.onDetailedInfoFragment(false, 1);

            } else {
                submitDetailedInfo();
            }

        } else if (!switchIsPermanentAddressSame.isChecked()) {
            if (Brcurrent_address_stay_duration.equals("") || Brcurrent_residence_type.equals("") || Brprofession.equals("") ||
                    Bremployer_name.equals("") || Brannual_income.equals("") || Broffice_address_pin.equals("") ||
                    Broffice_address.equals("") || Broffice_landmark.equals("") || Brcurrent_employment_duration.equals("") ||
                    Broffice_address_country.equals("") || offstateID.equals("") || offcityID.equals("")) {
                mListener.onDetailedInfoFragment(false, 1);
            } else {
                submitDetailedInfo();
            }
        }

//        if (Brpermanent_address_pin.equals("") || Brpermanent_address.equals("") || Brpermanent_landmark.equals("")
//                || permanentcityID.equals("") || permanentstateID.equals("") || permanentcountryID.equals("") || Brprofession.equals("")
//                || Bremployer_name.equals("") || Brannual_income.equals("") || Broffice_address_pin.equals("") || Broffice_address.equals("")
//                || Broffice_landmark.equals("") || Brcurrent_employment_duration.equals("") || Broffice_address_country.equals("")
//                || offstateID.equals("") || offcityID.equals("")) {
//            mListener.onDetailedInfoFragment(false, 1);
//        } else {
//            submitDetailedInfo();
////            mListener.onDetailedInfoFragment(true,2);
//        }

    }

    public void indicateValidationText(TextView indicator, boolean valid) {
        if (valid) {
            indicator.setTextColor(context.getResources().getColor(R.color.colorGreen));
        } else {
            indicator.setTextColor(context.getResources().getColor(R.color.new_red));
        }
    }

    public void indicateValidationIcon(ImageView ivStatus, ImageView ivTitle, boolean valid) {

        if (valid) {
            Drawable bg;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_check_circle_green, null);
                ivStatus.setColorFilter(context.getResources().getColor(R.color.colorGreen), PorterDuff.Mode.MULTIPLY);
            } else {
                bg = ContextCompat.getDrawable(context, R.drawable.ic_check_circle_green);
                DrawableCompat.setTint(bg, context.getResources().getColor(R.color.colorGreen));
            }
            ivStatus.setImageDrawable(bg);

            Drawable bg1;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                ivTitle.setColorFilter(context.getResources().getColor(R.color.colorGreen), PorterDuff.Mode.MULTIPLY);
            } else {
                bg1 = ivTitle.getDrawable();
                DrawableCompat.setTint(bg1, context.getResources().getColor(R.color.colorGreen));
                ivTitle.setImageDrawable(bg1);
            }

        } else {
            Drawable bg;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_exclamation_circle, null);
                ivStatus.setColorFilter(context.getResources().getColor(R.color.new_red), PorterDuff.Mode.MULTIPLY);
            } else {
                bg = ContextCompat.getDrawable(context, R.drawable.ic_exclamation_circle);
                DrawableCompat.setTint(bg, context.getResources().getColor(R.color.new_red));
            }
            ivStatus.setImageDrawable(bg);

            Drawable bg1;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                ivTitle.setColorFilter(context.getResources().getColor(R.color.new_red), PorterDuff.Mode.MULTIPLY);
            } else {
                bg1 = ivTitle.getDrawable();
                DrawableCompat.setTint(bg1, context.getResources().getColor(R.color.new_red));
                ivTitle.setImageDrawable(bg1);
            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onDetailedInfoFragmentInteractionListener) {
            mListener = (onDetailedInfoFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onDetailedInfoFragmentInteractionListener");
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
//            progressDialog.setMessage("Loading");
//            progressDialog.show();
//            progressDialog.setCancelable(false);
            String url = MainActivity.mainUrl + "dashboard/getcountrylist";
            Map<String, String> params = new HashMap<String, String>();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, DetailedInfoFragment.this, "getCountriesDtl", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
//            progressDialog.dismiss();
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
//        progressDialog.dismiss();
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
//                    linEditDetailedInfo.setVisibility(View.GONE);
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
                        if (Bremployer_type.equals("0")) {
                            spEmployerType.setSelection(Integer.parseInt((Bremployer_type)) + 1);
                        } else if (Bremployer_type.equals("1")) {
                            spEmployerType.setSelection((Integer.parseInt(Bremployer_type)) + 1);
                        } else {
                            spEmployerType.setSelection(Integer.parseInt("0"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (!Brcurrent_residence_type.equals("") && !Brcurrent_residence_type.equals("null")) {

                    switch (Brcurrent_residence_type) {
                        case "0":
                            switchResidenceType.setChecked(true);
                            break;
                        case "1":
                            switchResidenceType.setChecked(false);
                            break;
                        case "2":
                            switchResidenceType.setChecked(true);
                            break;
                        case "3":
                            switchResidenceType.setChecked(false);
                            break;
                        case "4":
                            switchResidenceType.setChecked(false);
                            break;
                        case "5":
                            switchResidenceType.setChecked(false);
                            break;
                        case "6":
                            switchResidenceType.setChecked(false);
                            break;
                        case "7":
                            switchResidenceType.setChecked(true);
                            break;
                        case "8":
                            switchResidenceType.setChecked(false);
                            break;
                    }

                    try {
                        int count = borrowerCurrentResidencePersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentResidencePersonalPOJOArrayList.get(i).dresidencetypeID.equalsIgnoreCase(Brcurrent_residence_type)) {
                                spOwnedBy.setSelection(i);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

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
                        e.printStackTrace();
                        ProfessionApiCall();
                    }
                }else{
                    ProfessionApiCall();
                }

                if (!Brcurrent_employment_duration.equals("") && !Brcurrent_employment_duration.equals("null")) {
                    try {
                        spDurationOfJob.setSelection(Integer.parseInt(Brcurrent_employment_duration));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (Bris_borrower_permanent_address_same_as.equals("") || Bris_borrower_permanent_address_same_as.equals("null")
                        || Bris_borrower_permanent_address_same_as.equals("0") || Bris_borrower_permanent_address_same_as.equals("1")
                        || Bris_borrower_permanent_address_same_as.equals("2")) {
                    //Same
                    SwitchAddressSameAs = "3";
                    Bris_borrower_permanent_address_same_as = "3";
                    edtPermanentPincode.setText(Brpermanent_address_pin);
                    edtPermanentAddress.setText(Brpermanent_address);
                    edtPermanentLandmark.setText(Brpermanent_landmark);
                    try {
                        if (!Brpermanent_address_country.equals("") && !Brpermanent_address_country.equals("null")) {
                            try {
                                permanentcountryID = Brpermanent_address_country;
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
                                    break;
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
                                    break;
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
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
//          Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            if (!jsonData.get("leadStatus").equals(null)) {
                JSONObject jsonleadStatus = jsonData.getJSONObject("leadStatus");
                lead_status = jsonleadStatus.getString("lead_status");
                lead_sub_status = jsonleadStatus.getString("lead_sub_status");
                current_stage = jsonleadStatus.getString("current_stage");
                current_status = jsonleadStatus.getString("current_status");
            }

            if (lead_status.equals("1") && current_stage.equals("1")) {
            } else {
                linEditDetailedInfo.setVisibility(View.GONE);
            }
//             if(LoanTabActivity.isDetailedInfoEdit) {
//                    checkAllFields();
//                }
        } catch (Exception e) {
            progressDialog.dismiss();
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


    public static void submitDetailedInfo() {
        try {

            String url = MainActivity.mainUrl + "dashboard/editDetailedInformation";
            Map<String, String> params = new HashMap<String, String>();
            params.put("lead_id", lead_id);
            params.put("applicant_id", application_id);
            params.put("current_address_stay_duration", Brcurrent_address_stay_duration);
            params.put("current_employment_duration", Brcurrent_employment_duration);
            params.put("current_residence_type", Brcurrent_residence_type);
            if (switchResidenceType.isChecked()) {
                params.put("current_address_rent", edtMonthlyRent.getText().toString());
            } else {
                params.put("current_address_rent", edtMonthlyRent.getText().toString());
            }

            params.put("is_borrower_permanent_address_same_as", SwitchAddressSameAs);
            params.put("permanent_address", Brpermanent_address);
            params.put("permanent_landmark", Brpermanent_landmark);
            params.put("permanent_address_pin", Brpermanent_address_pin);
            params.put("permanent_address_country", permanentcountryID);
            params.put("permanent_address_state", permanentstateID);
            params.put("permanent_address_city", permanentcityID);

            params.put("office_address", Broffice_address);
            params.put("office_landmark", Broffice_landmark);
            params.put("office_address_pin", Broffice_address_pin);
            params.put("office_address_country", offcountryID);
            params.put("office_address_city", offcityID);
            params.put("office_address_state", offstateID);

            params.put("profession", Brprofession);
            params.put("employer_type", Bremployer_type);
            params.put("annual_income", Brannual_income);
            params.put("employer_name", Bremployer_name);

            Log.d("DETAILEDINFO", "PARAMS" + params.toString());

            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "editDetailedInformation", params, MainActivity.auth_token);
            }

        } catch (Exception e) {
            String className = context.getClass().getSimpleName();
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
                isLeadReload = true;
                setViewsEnabled(false);
                LoanTabActivity.isDetailedInfoEdit = false;
                linEditDetailedInfo.setVisibility(View.VISIBLE);
                mListener.onDetailedInfoFragment(true, 2);
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

    public interface onDetailedInfoFragmentInteractionListener {
        void onDetailedInfoFragment(boolean valid, int next);
    }

}
