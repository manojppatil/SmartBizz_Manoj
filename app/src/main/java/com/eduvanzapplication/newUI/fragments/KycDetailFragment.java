package com.eduvanzapplication.newUI.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentStatePersonalPOJO;

import com.eduvanzapplication.newUI.VolleyCall;

import com.eduvanzapplication.newUI.newViews.LoanTabActivity;
import com.eduvanzapplication.pqformfragments.pojo.LocationsPOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfCoursePOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfInsitituePOJO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.eduvanzapplication.newUI.MainApplication.TAG;
import static com.eduvanzapplication.newUI.fragments.DashboardFragmentNew.isLeadReload;

public class KycDetailFragment extends Fragment {
    static View view;
    public static ViewPager viewPager;
    public static Context context;
    public static Fragment mFragment;
    public static ProgressDialog progressDialog;
    public static TextView txtPersonalToggle, txtIdentityToggle, txtCourseToggle;
    public static LinearLayout linPersonalToggle, linIdentityToggle, linCourseToggle;
    public static ImageView ivPersonalToggle, ivIdentityToggle, ivCourseToggle;
    public static LinearLayout linPersonalBlock, relIdentityBlock, relCourseBlock;
    public static Animation expandAnimationPersonal, collapseanimationPersonal;
    public static Animation expandAnimationIdentity, collapseAnimationIdentity;
    public static Animation expanAnimationCourse, collapseAnimationCourse;
    public static Fragment fragment;

    public static ArrayAdapter arrayAdapter_NameOfInsititue;
    public static ArrayList<String> nameofinstitute_arrayList;
    public static ArrayList<NameOfInsitituePOJO> nameOfInsitituePOJOArrayList;

    public static ArrayAdapter arrayAdapter_NameOfCourse;
    public static ArrayList<String> nameofcourse_arrayList;
    public static ArrayList<NameOfCoursePOJO> nameOfCoursePOJOArrayList;
    public static ArrayAdapter arrayAdapter_locations;
    public static ArrayList<String> locations_arrayList;
    public static ArrayList<LocationsPOJO> locationPOJOArrayList;

    public static ImageButton fabEditKycDetail, btnNextKycDetail;
    public static Switch switchMarital;
    public static TextView txtMaritalStatus;
    public static EditText edtFnameBr, edtMnameBr, edtLnameBr, edtEmailIdBr, edtMobileNoBr, edtAddressbr, edtLandmarkbr, edtPincodeBr;
    public static LinearLayout linMale, linFemale, linOther, linDob, linMaritalStatus;
    public static EditText edtAadhaar, edtPAN, edtLoanAmt;
    public static Spinner spCountry, spState, spCity, spInsttLocation, spCourse;
    public static AutoCompleteTextView acInstituteName;
    public static TextView txtCourseFee, txtDOB;

    public static OnFragmentInteracting mListener;

    public static String currentcityID = "", currentstateID = "", currentcountryID = "", instituteID = "", courseID = "", locationID = "";

    //city
    public static ArrayAdapter arrayAdapter_currentCity;
    public static ArrayList<String> currentcity_arrayList;
    public static ArrayList<BorrowerCurrentCityPersonalPOJO> borrowerCurrentCityPersonalPOJOArrayList;

    //state
    public static ArrayAdapter arrayAdapter_currentState;
    public static ArrayList<String> currentstate_arrayList;
    public static ArrayList<BorrowerCurrentStatePersonalPOJO> borrowerCurrentStatePersonalPOJOArrayList;

    //country
    public static ArrayAdapter arrayAdapter_currentCountry;
    public static ArrayList<String> currentCountry_arrayList;
    public static ArrayList<BorrowerCurrentCountryPersonalPOJO> borrowerCurrentCountryPersonalPOJOArrayList;
    public static String documents = "0";
    //Integer Values
    public static int loanAmountvalueInInt, courseFeeValueinint;
    //txterrorMsg
    public static TextView txtPersonalDetailsErrMsg, txtCourseDetailsErrMsg, txtIdentityDetailsErrMsg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kycdetail_stepper, container, false);
        context = getContext();
        mFragment = new KycDetailFragment();

        progressDialog = new ProgressDialog(getActivity());
        expandAnimationPersonal = AnimationUtils.loadAnimation(context, R.anim.scale_expand);
        expandAnimationIdentity = AnimationUtils.loadAnimation(context, R.anim.scale_expand);
        expanAnimationCourse = AnimationUtils.loadAnimation(context, R.anim.scale_expand);
        collapseanimationPersonal = AnimationUtils.loadAnimation(context, R.anim.scale_collapse);
        collapseAnimationIdentity = AnimationUtils.loadAnimation(context, R.anim.scale_collapse);
        collapseAnimationCourse = AnimationUtils.loadAnimation(context, R.anim.scale_collapse);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ivPersonalToggle = view.findViewById(R.id.ivPersonalToggle);
        ivIdentityToggle = view.findViewById(R.id.ivIdentityToggle);
        ivCourseToggle = view.findViewById(R.id.ivCourseToggle);

        txtPersonalToggle = view.findViewById(R.id.txtPersonalToggle);
        linPersonalBlock = view.findViewById(R.id.linPersonalBlock);
        txtIdentityToggle = view.findViewById(R.id.txtIdentityToggle);
        linPersonalToggle = view.findViewById(R.id.linPersonalToggle);
        linIdentityToggle = view.findViewById(R.id.linIdentityToggle);
        linCourseToggle = view.findViewById(R.id.linCourseToggle);
        relIdentityBlock = view.findViewById(R.id.relIdentityBlock);
        txtCourseToggle = view.findViewById(R.id.txtCourseToggle);
        relCourseBlock = view.findViewById(R.id.relCourseBlock);
        fabEditKycDetail = view.findViewById(R.id.fabEditKycDetail);
        btnNextKycDetail = view.findViewById(R.id.btnNextKycDetail);
        edtFnameBr = view.findViewById(R.id.edtFnameBr);
        edtMnameBr = view.findViewById(R.id.edtMnameBr);
        edtLnameBr = view.findViewById(R.id.edtLnameBr);
        switchMarital = view.findViewById(R.id.switchMaritalStatus);
        txtMaritalStatus = view.findViewById(R.id.txtMaritalStatus);
        viewPager = view.findViewById(R.id.viewpager1);

        edtEmailIdBr = view.findViewById(R.id.edtEmailIdBr);
        edtMobileNoBr = view.findViewById(R.id.edtMobileNoBr);
        edtAddressbr = view.findViewById(R.id.edtAddressbr);
        edtLandmarkbr = view.findViewById(R.id.edtLandmarkbr);
        edtPincodeBr = view.findViewById(R.id.edtPincodeBr);
        linMale = view.findViewById(R.id.linMale);
        linFemale = view.findViewById(R.id.linFemale);
        linOther = view.findViewById(R.id.linOther);
        linDob = view.findViewById(R.id.linDob);
        txtDOB = view.findViewById(R.id.textDob);
        linMaritalStatus = view.findViewById(R.id.linMaritalStatus);
        edtAadhaar = view.findViewById(R.id.edtAadhaar);
        edtPAN = view.findViewById(R.id.edtPAN);
        edtLoanAmt = view.findViewById(R.id.edtLoanAmt);
        spCountry = view.findViewById(R.id.spCountry);
        spState = view.findViewById(R.id.spState);
        spCity = view.findViewById(R.id.spCity);
        spInsttLocation = view.findViewById(R.id.spInsttLocation);
        spCourse = view.findViewById(R.id.spCourse);
        acInstituteName = view.findViewById(R.id.scInstituteName);
        txtCourseFee = view.findViewById(R.id.txtCourseFee);
        //errorMsg Id
        txtPersonalDetailsErrMsg = view.findViewById(R.id.txtPersonalDetailsErrMsg);
        txtIdentityDetailsErrMsg = view.findViewById(R.id.txtIdentityDetailsErrMsg);
        txtCourseDetailsErrMsg = view.findViewById(R.id.txtCourseDetailsErrMsg);

        LoanTabActivity.firstName = "";
        LoanTabActivity.lastName = "";
        LoanTabActivity.middleName = "";
        LoanTabActivity.gender = "";
        LoanTabActivity.dob = "";
        LoanTabActivity.maritalStatus = "2";
        LoanTabActivity.email = "";
        LoanTabActivity.mobile = "";
        LoanTabActivity.aadhar = "";
        LoanTabActivity.pan = "";
        LoanTabActivity.flatBuildingSociety = "";
        LoanTabActivity.streetLocalityLandmark = "";
        LoanTabActivity.pincode = "";
        LoanTabActivity.countryId = "";
        LoanTabActivity.stateId = "";
        LoanTabActivity.cityId = "";
        LoanTabActivity.instituteId = "";
        LoanTabActivity.courseId = "";
        LoanTabActivity.instituteLocationId = "";
        LoanTabActivity.courseFee = "";
        LoanTabActivity.applicant_id = "";
        LoanTabActivity.application_id = "";
        LoanTabActivity.requested_loan_amount = "";
        LoanTabActivity.institute_name = "";
        LoanTabActivity.location_name = "";
        LoanTabActivity.course_name = "";
        LoanTabActivity.course_cost = "";
        LoanTabActivity.fk_institutes_id = "";
        LoanTabActivity.fk_insitutes_location_id = "";
        LoanTabActivity.fk_course_id = "";
        LoanTabActivity.lead_status = "";
        LoanTabActivity.lead_sub_status = "";
        LoanTabActivity.current_status = "";
        LoanTabActivity.current_stage = "";
        LoanTabActivity.has_aadhar_pan = "";

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linPersonalBlock.startAnimation(expandAnimationPersonal);
        relIdentityBlock.startAnimation(collapseAnimationIdentity);
        relCourseBlock.startAnimation(collapseAnimationCourse);
        setViewsEnabled(false);

        btnNextKycDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LoanTabActivity.isKycEdit) {
                    if (LoanTabActivity.firstName.equals("") || LoanTabActivity.lastName.equals("") ||
                            LoanTabActivity.email.equals("") || LoanTabActivity.mobile.equals("") ||
                            LoanTabActivity.dob.equals("") || LoanTabActivity.gender.equals("") ||
                            LoanTabActivity.maritalStatus.equals("") || LoanTabActivity.flatBuildingSociety.equals("")
                            || LoanTabActivity.streetLocalityLandmark.equals("") || LoanTabActivity.pincode.equals("")
                            || LoanTabActivity.pincode.length() < 6 || LoanTabActivity.countryId.equals("") ||
                            LoanTabActivity.stateId.equals("") || LoanTabActivity.cityId.equals("") ||
                            LoanTabActivity.institute_name.equals("") || LoanTabActivity.instituteLocationId.equals("")
                            || LoanTabActivity.courseId.equals("") || LoanTabActivity.course_cost.equals("") ||
                            LoanTabActivity.requested_loan_amount.equals("") || (loanAmountvalueInInt > courseFeeValueinint)) {
                        mListener.onFragmentInteraction(false, 0);
                        chekAllFields();
                    } else {
                        saveEditedKycData();
//                        mListener.onFragmentInteraction(true, 1);
                    }

                } else {
                    mListener.onFragmentInteraction(true, 1);

                }

            }
        });

//        fabEditKycDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!LoanTabActivity.isKycEdit){
//                    LoanTabActivity.isKycEdit = true;
//                    setViewsEnabled(true);
//                    fabEditKycDetail.setImageResource(R.drawable.ic_save_white_16dp);
//                    fabEditKycDetail.setBackgroundColor(getResources().getColor(R.color.colorGreen));
//                    chekAllFields();
//                }
//                else {
//
//                }
//            }
//        });

        fabEditKycDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//				if (lead_status.equals("1") && current_stage.equals("1")) {
                setViewsEnabled(true);
                LoanTabActivity.isKycEdit = true;
                fabEditKycDetail.setVisibility(View.GONE);
//				} else {
//
//				}
            }
        });

//Personal details
        txtPersonalToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linPersonalBlock.getVisibility() == VISIBLE) {
                    linPersonalBlock.startAnimation(collapseanimationPersonal);
                } else {
                    linPersonalBlock.startAnimation(expandAnimationPersonal);
                    chekAllFields();
                }
            }
        });
//Identity details
        txtIdentityToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relIdentityBlock.getVisibility() == VISIBLE) {
                    relIdentityBlock.startAnimation(collapseAnimationIdentity);
                } else {
                    relIdentityBlock.startAnimation(expandAnimationIdentity);
                    chekAllFields();
                }
            }
        });
//course details.
        txtCourseToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relCourseBlock.getVisibility() == VISIBLE) {
                    relCourseBlock.startAnimation(collapseAnimationCourse);
                } else {
                    relCourseBlock.startAnimation(expanAnimationCourse);
                    chekAllFields();
                }
            }
        });

        //Personal details
        linPersonalToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linPersonalBlock.getVisibility() == VISIBLE) {
                    linPersonalBlock.startAnimation(collapseanimationPersonal);
                } else {
                    linPersonalBlock.startAnimation(expandAnimationPersonal);
                    chekAllFields();
                }
            }
        });
//Identity details
        linIdentityToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relIdentityBlock.getVisibility() == VISIBLE) {
                    relIdentityBlock.startAnimation(collapseAnimationIdentity);
                } else {
                    relIdentityBlock.startAnimation(expandAnimationIdentity);
                    chekAllFields();
                }
            }
        });
//course details.
        linCourseToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relCourseBlock.getVisibility() == VISIBLE) {
                    relCourseBlock.startAnimation(collapseAnimationCourse);
                } else {
                    relCourseBlock.startAnimation(expanAnimationCourse);
                    chekAllFields();
                }
            }
        });

        /*================================personal details==========================================*/
        collapseanimationPersonal.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linPersonalBlock.setVisibility(GONE);
                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_angle_up, null);
                    ivPersonalToggle.setColorFilter(context.getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_angle_up);
                    DrawableCompat.setTint(bg, context.getResources().getColor(R.color.darkblue));
                }
                ivPersonalToggle.setImageDrawable(bg);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        expandAnimationPersonal.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linPersonalBlock.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                relIdentityBlock.startAnimation(collapseAnimationIdentity);
                relCourseBlock.startAnimation(collapseAnimationCourse);
                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_angle_down, null);
                    ivPersonalToggle.setColorFilter(context.getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_angle_down);
                    DrawableCompat.setTint(bg, context.getResources().getColor(R.color.darkblue));
                }
                ivPersonalToggle.setImageDrawable(bg);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        /*=============================================identity details===================================*/
        collapseAnimationIdentity.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                relIdentityBlock.setVisibility(GONE);
                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_angle_up, null);
                    ivIdentityToggle.setColorFilter(context.getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_angle_up);
                    DrawableCompat.setTint(bg, context.getResources().getColor(R.color.darkblue));
                }
                ivIdentityToggle.setImageDrawable(bg);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        expandAnimationIdentity.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                relIdentityBlock.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linPersonalBlock.startAnimation(collapseanimationPersonal);
                relCourseBlock.startAnimation(collapseAnimationCourse);
                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_angle_down, null);
                    ivIdentityToggle.setColorFilter(context.getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_angle_down);
                    DrawableCompat.setTint(bg, context.getResources().getColor(R.color.darkblue));
                }
                ivIdentityToggle.setImageDrawable(bg);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        /*===========================================course details=================================*/
        collapseAnimationCourse.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                relCourseBlock.setVisibility(GONE);
                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_angle_up, null);
                    ivCourseToggle.setColorFilter(context.getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_angle_up);
                    DrawableCompat.setTint(bg, context.getResources().getColor(R.color.darkblue));
                }
                ivCourseToggle.setImageDrawable(bg);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        expanAnimationCourse.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                relCourseBlock.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linPersonalBlock.startAnimation(collapseanimationPersonal);
                relIdentityBlock.startAnimation(collapseAnimationIdentity);
                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_angle_down, null);
                    ivCourseToggle.setColorFilter(context.getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_angle_down);
                    DrawableCompat.setTint(bg, context.getResources().getColor(R.color.darkblue));
                }
                ivCourseToggle.setImageDrawable(bg);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        /*=====================================end====================================================*/
        applyFieldsChangeListener();
        instituteApiCall();
        countryApiCall();

        kycApiCall();

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spCity.getSelectedItem().toString();
                    int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                            LoanTabActivity.cityId = currentcityID = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
                            Log.e(TAG, "spCurrentCityBr: +++++++++++++++++++*********************" + currentcityID);
                            chekAllFields();
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

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spState.getSelectedItem().toString();
                    int count = borrowerCurrentStatePersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateName.equalsIgnoreCase(text)) {
                            LoanTabActivity.stateId = currentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
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

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String text = spCountry.getSelectedItem().toString();
                    int count = borrowerCurrentCountryPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
                            LoanTabActivity.countryId = currentcountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                        }
                    }
                    stateApiCall();
                } catch (Exception e) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    String text = spCourse.getSelectedItem().toString();
                    int count = nameOfCoursePOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (nameOfCoursePOJOArrayList.get(i).courseName.equalsIgnoreCase(text)) {
                            LoanTabActivity.courseId = nameOfCoursePOJOArrayList.get(i).courseID;
                            break;
                        }
                    }
                    courseFeeApiCall();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spInsttLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    String text = spInsttLocation.getSelectedItem().toString();
                    int count = locationPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (locationPOJOArrayList.get(i).locationName.equalsIgnoreCase(text)) {
                            LoanTabActivity.instituteLocationId = locationPOJOArrayList.get(i).locationID;
                            break;
                        }
                    }
                    courseApiCall();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public static void saveEditedKycData() {

        try {

            String url = MainActivity.mainUrl + "dashboard/editKycDetails";
            Map<String, String> params = new HashMap<String, String>();

            params.put("lead_id", LoanTabActivity.lead_id);
            params.put("fk_institutes_id", LoanTabActivity.instituteId);
            params.put("fk_insitutes_location_id", LoanTabActivity.instituteLocationId);
            params.put("fk_course_id", LoanTabActivity.courseId);
            params.put("requested_loan_amount", LoanTabActivity.requested_loan_amount);
            params.put("applicant_id", LoanTabActivity.applicant_id);
            params.put("profession", "");
            params.put("first_name", LoanTabActivity.firstName);
            params.put("middle_name", LoanTabActivity.middleName);
            params.put("last_name", LoanTabActivity.lastName);
            params.put("dob", LoanTabActivity.dob);
            params.put("gender_id", LoanTabActivity.gender);
            params.put("mobile_number", LoanTabActivity.mobile);
            params.put("email_id", LoanTabActivity.email);
            params.put("pan_number", LoanTabActivity.pan);
            params.put("aadhar_number", LoanTabActivity.aadhar);
            params.put("current_address", LoanTabActivity.flatBuildingSociety);
            params.put("current_landmark", LoanTabActivity.streetLocalityLandmark);
            params.put("current_address_pin", LoanTabActivity.pincode);
            params.put("marital_status", LoanTabActivity.maritalStatus);
            params.put("current_address_country", LoanTabActivity.countryId);
            params.put("current_address_state", LoanTabActivity.stateId);
            params.put("current_address_city", LoanTabActivity.cityId);
            params.put("has_aadhar_pan", documents);

            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();

                volleyCall.sendRequest(context, url, null, mFragment, "editKycDetails", params, MainActivity.auth_token);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void editKycDetailsResponse(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (jsonData.getInt("status") == 1) {
                isLeadReload = true;
                setViewsEnabled(false);
                LoanTabActivity.isKycEdit = false;
                fabEditKycDetail.setVisibility(View.VISIBLE);
                mListener.onFragmentInteraction(true, 1);
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
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void setViewsEnabled(boolean f) {
        edtFnameBr.setEnabled(f);
        edtMnameBr.setEnabled(f);
        edtLnameBr.setEnabled(f);
        linMale.setEnabled(f);
        linFemale.setEnabled(f);
        linOther.setEnabled(f);
        switchMarital.setEnabled(f);
        linDob.setEnabled(f);
        linMaritalStatus.setEnabled(f);
//        edtEmailIdBr.setEnabled(f);
//        edtMobileNoBr.setEnabled(f);
        edtAadhaar.setEnabled(f);
        edtPAN.setEnabled(f);
        edtAddressbr.setEnabled(f);
        edtLandmarkbr.setEnabled(f);
        edtPincodeBr.setEnabled(f);
        spCountry.setEnabled(f);
        spState.setEnabled(f);
        spCity.setEnabled(f);
        acInstituteName.setEnabled(f);
        spInsttLocation.setEnabled(f);
        spCourse.setEnabled(f);
        txtCourseFee.setEnabled(f);
        edtLoanAmt.setEnabled(f);

    }

    public void applyFieldsChangeListener() {


        linDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();

                DatePickerPopWin datePickerPopWin = new DatePickerPopWin.Builder(getActivity(), new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
//                        Toast.makeText(getContext(), dateDesc, Toast.LENGTH_SHORT).show();
                        LoanTabActivity.dob = day + "-" + month + "-" + year;
                        txtDOB.setText(LoanTabActivity.dob);
                        chekAllFields();
                    }
                }).textConfirm("CONFIRM") //text of confirm button
                        .textCancel("CANCEL") //text of cancel button
                        .btnTextSize(16) // button text size
                        .viewTextSize(35) // pick view text size
                        .colorCancel(Color.parseColor("#999999")) //color of cancel button
                        .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                        .minYear(1900) //min year in loop
                        .maxYear(calendar.get(Calendar.YEAR) - 18) // max year in loop
                        .showDayMonthYear(false) // shows like dd mm yyyy (default is false)
                        .dateChose("2013-11-11") // date chose when init popwindow
                        .build();
                datePickerPopWin.showAsDropDown(linDob);
            }
        });

        edtFnameBr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                LoanTabActivity.firstName = edtFnameBr.getText().toString();
                chekAllFields();
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
                LoanTabActivity.middleName = edtMnameBr.getText().toString();
                chekAllFields();
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
                LoanTabActivity.lastName = edtLnameBr.getText().toString();
                edtLnameBr.setOnEditorActionListener(new EditText.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            return true;
                        }
                        return false;
                    }
                });

                chekAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edtEmailIdBr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LoanTabActivity.email = edtEmailIdBr.getText().toString();
                chekAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edtMobileNoBr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LoanTabActivity.mobile = edtMobileNoBr.getText().toString();
                chekAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edtAadhaar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!Globle.validateAadharNumber(edtAadhaar.getText().toString())) {
//False
                    if (edtAadhaar.getText().toString().length() > 0 && edtPAN.getText().toString().length() > 0) {
                        LoanTabActivity.aadhar = "";
                        edtAadhaar.setError("Please Enter valid Aadhaar Number!");
                        edtPAN.setError(null);
                        documents = "3";
                    } else if (edtAadhaar.getText().toString().length() == 0 && edtPAN.getText().toString().length() > 0) {
                        LoanTabActivity.aadhar = "";
                        edtAadhaar.setError(null);
                        edtPAN.setError(null);
                        documents = "2";
                    } else if (edtAadhaar.getText().toString().length() > 0 && edtPAN.getText().toString().length() == 0) {
                        LoanTabActivity.aadhar = "";
                        edtAadhaar.setError("Please Enter valid Aadhaar Number!");
//                        edtPAN.setError(null);
                        documents = "1";
                    } else if (edtAadhaar.getText().toString().length() == 0 && edtPAN.getText().toString().length() == 0) {
                        LoanTabActivity.pan = "";
                        edtAadhaar.setError("Please Enter Aadhaar or PAN Number!");
                        edtPAN.setError(null);
                        documents = "0";
                    }

                } else {
//True
                    if (edtPAN.getText().toString().length() > 0) {
                        LoanTabActivity.aadhar = edtAadhaar.getText().toString();
                        edtAadhaar.setError(null);
//                        edtPAN.setError(null);
                        documents = "3";
                    } else {
                        LoanTabActivity.aadhar = edtAadhaar.getText().toString();
                        edtAadhaar.setError(null);
//                        edtPAN.setError(null);
                        documents = "1";
                    }
                }

                chekAllFields();

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

                if (!edtPAN.getText().toString().toUpperCase().matches(Globle.panPattern)) {
                    if (edtAadhaar.getText().toString().length() > 0 && edtPAN.getText().toString().length() > 0) {
                        LoanTabActivity.pan = "";
//                        edtAadhaar.setError(null);
                        edtPAN.setError("Please Enter Valid PAN number!");
                        documents = "3";
                    } else if (edtAadhaar.getText().toString().length() == 0 && edtPAN.getText().toString().length() > 0) {
                        LoanTabActivity.pan = "";
                        edtAadhaar.setError(null);
                        edtPAN.setError("Please Enter Valid PAN number!");
                        documents = "2";
                    } else if (edtAadhaar.getText().toString().length() > 0 && edtPAN.getText().toString().length() == 0) {
                        LoanTabActivity.pan = "";
                        edtPAN.setError(null);
                        documents = "1";
                    } else if (edtAadhaar.getText().toString().length() == 0 && edtPAN.getText().toString().length() == 0) {
                        LoanTabActivity.pan = "";
                        edtAadhaar.setError(null);
                        edtPAN.setError("Please Enter Aadhaar or PAN Number!");
                        documents = "0";
                    }
                } else {
                    //Pan True
                    if (edtAadhaar.getText().toString().length() > 0) {
                        LoanTabActivity.pan = edtPAN.getText().toString().trim();
//                        edtAadhaar.setError(null);
                        edtPAN.setError(null);
                        documents = "3";
                    } else {
                        LoanTabActivity.pan = edtPAN.getText().toString().trim();
//                        edtAadhaar.setError(null);
                        edtPAN.setError(null);
                        documents = "2";
                    }
                }

                chekAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edtAddressbr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LoanTabActivity.flatBuildingSociety = edtAddressbr.getText().toString();
                chekAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        edtLandmarkbr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LoanTabActivity.streetLocalityLandmark = edtLandmarkbr.getText().toString();
                chekAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPincodeBr.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        edtPincodeBr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (edtPincodeBr.getText().toString().length() == 6) {
                    LoanTabActivity.pincode = edtPincodeBr.getText().toString();
                    edtPincodeBr.setError(null);
                } else {
                    LoanTabActivity.pincode = "";
                    edtPincodeBr.setError("Please enter valid pincode !");
                }

                chekAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtLoanAmt.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        edtLoanAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    if (s.length() > 0) {
                        loanAmountvalueInInt = Integer.parseInt(edtLoanAmt.getText().toString());
                        courseFeeValueinint = Integer.parseInt(LoanTabActivity.course_cost);

                        if (loanAmountvalueInInt > courseFeeValueinint) {
//                            edtLoanAmt.setError("Loan amount not exceed than course fees!");
                            LoanTabActivity.requested_loan_amount = "";

                        } else {
                            if (loanAmountvalueInInt >= 5000) {
                                LoanTabActivity.requested_loan_amount = edtLoanAmt.getText().toString();
                                edtLoanAmt.setError(null);
                            } else {
                                edtLoanAmt.setError("The Loan Amount must be greater than or equal to 5000.");
                                LoanTabActivity.requested_loan_amount = "";
                            }
                        }
                    }
                    if (LoanTabActivity.isKycEdit) {
                    chekAllFields();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        linMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanTabActivity.gender = "1";
                linMale.setBackground(context.getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linFemale.setBackground(context.getResources().getDrawable(R.drawable.border_circular));
                linOther.setBackground(context.getResources().getDrawable(R.drawable.border_circular));
            }
        });

        linFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanTabActivity.gender = "2";
                linMale.setBackground(context.getResources().getDrawable(R.drawable.border_circular));
                linFemale.setBackground(context.getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linOther.setBackground(context.getResources().getDrawable(R.drawable.border_circular));
            }
        });

        linOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanTabActivity.gender = "3";
                linMale.setBackground(context.getResources().getDrawable(R.drawable.border_circular));
                linFemale.setBackground(context.getResources().getDrawable(R.drawable.border_circular));
                linOther.setBackground(context.getResources().getDrawable(R.drawable.border_circular_blue_filled));
            }
        });

        switchMarital.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LoanTabActivity.maritalStatus = isChecked ? "1" : "2";
                if (isChecked) txtMaritalStatus.setText("Married");
                else txtMaritalStatus.setText("Unmarried");
                if (LoanTabActivity.isKycEdit) {
                chekAllFields();
            }
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteracting) {
            mListener = (OnFragmentInteracting) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteracting");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//    private void checkingAdharPanField() {
//
//        switch (documents) {
//            case "1":
//                if (!Globle.validateAadharNumber(LoanTabActivity.aadhar)) {
//                } else {
//                }
//                break;
//
//            case "2":
//                if (LoanTabActivity.pan.equals(Globle.panPattern))
//                else mDocListener.onOffButtonsDocuments(true, true);
//                break;
//
//            case "3":
//                if (!Globle.validateAadharNumber(LoanTabActivity.aadhar) && !LoanTabActivity.pan.equals(Globle.panPattern)) {
//                    mDocListener.onOffButtonsDocuments(false, true);
//                } else mDocListener.onOffButtonsDocuments(true, true);
//                break;
//        }
//
//    }

    public void chekAllFields() {

        if (LoanTabActivity.firstName.equals("") || LoanTabActivity.lastName.equals("") || LoanTabActivity.email.equals("") ||
                LoanTabActivity.mobile.equals("") || LoanTabActivity.dob.equals("") || LoanTabActivity.gender.equals("") ||
                LoanTabActivity.maritalStatus.equals("")) {
            indicateValidationText(txtPersonalToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_user), false);

            if (LoanTabActivity.isKycEdit) {
                txtPersonalDetailsErrMsg.setVisibility(VISIBLE);

                if (edtFnameBr.getText().toString().equals("")) {
                    txtPersonalDetailsErrMsg.setText("Please enter first name");
                    // edtFnameBr.requestFocus();

                } else if (edtLnameBr.getText().toString().equals("")) {
                    txtPersonalDetailsErrMsg.setText("Please enter last name");
                    //  edtLnameBr.requestFocus();

                } else if (LoanTabActivity.gender.equals("")) {
                    txtPersonalDetailsErrMsg.setText("Please enter gender");
                    //   linMale.requestFocus();

                } else if (LoanTabActivity.dob.equals("")) {
                    txtPersonalDetailsErrMsg.setText("Please enter DOB");
                    // txtDOB.requestFocus();
                } else if (LoanTabActivity.maritalStatus.equals("")) {
                    txtPersonalDetailsErrMsg.setText("Please enter maritalStatus");
                    //  txtMaritalStatus.requestFocus();
                } else if (LoanTabActivity.email.equals("")) {
                    txtPersonalDetailsErrMsg.setText("Please enter email");
                    //edtEmailIdBr.requestFocus();
                } else if (LoanTabActivity.mobile.equals("")) {
                    txtPersonalDetailsErrMsg.setText("Please enter mobile number");
                    // edtMobileNoBr.requestFocus();
                }
                linPersonalBlock.requestFocus();
                txtPersonalDetailsErrMsg.requestFocus();
            }
        } else {
            txtPersonalDetailsErrMsg.setVisibility(GONE);
            txtPersonalDetailsErrMsg.setText(null);
//            linPersonalBlock.startAnimation(collapseanimationPersonal);
            indicateValidationText(txtPersonalToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_user), true);
        }

        if (LoanTabActivity.instituteId.equals("") || LoanTabActivity.instituteLocationId.equals("") ||
                LoanTabActivity.courseId.equals("") || LoanTabActivity.course_cost.equals("") ||
                LoanTabActivity.requested_loan_amount.equals("")) {
            if (LoanTabActivity.isKycEdit) {
                txtCourseDetailsErrMsg.setVisibility(VISIBLE);

                if (LoanTabActivity.instituteId.equals("")) {
                    txtCourseDetailsErrMsg.setText("Please select Institue Name");
                    // spInsttLocation.requestFocus();
                } else if (LoanTabActivity.course_cost.equals("")) {
                    txtCourseDetailsErrMsg.setText("Please enter course fee");
                    //spCourse.requestFocus();
                } else if (LoanTabActivity.requested_loan_amount.equals("")) {
                    txtCourseDetailsErrMsg.setText("Please enter loan amount");
                    //edtLoanAmt.requestFocus();
                } else if (loanAmountvalueInInt > courseFeeValueinint) {
                    txtCourseDetailsErrMsg.setText("Loan amount not exceed than course fees!");
                    LoanTabActivity.requested_loan_amount = "";
                    //edtLoanAmt.requestFocus();
                }

                txtCourseDetailsErrMsg.requestFocus();
            }

            indicateValidationText(txtCourseToggle, context.getResources().getDrawable(R.drawable.ic_profession_student), false);
        } else {
            txtCourseDetailsErrMsg.setText(null);
            txtCourseDetailsErrMsg.setVisibility(GONE);
//            relCourseBlock.startAnimation(collapseAnimationCourse);
            indicateValidationText(txtCourseToggle, context.getResources().getDrawable(R.drawable.ic_profession_student), true);
        }

        if (documents.equals("0") || documents.equals("")) {
            if (LoanTabActivity.flatBuildingSociety.equals("") || LoanTabActivity.streetLocalityLandmark.equals("") ||
                    LoanTabActivity.pincode.length() < 6 || (LoanTabActivity.aadhar.equals("") && LoanTabActivity.pan.equals("")) ||
                    LoanTabActivity.countryId.equals("") || LoanTabActivity.stateId.equals("") || LoanTabActivity.cityId.equals("")) {

                if (LoanTabActivity.isKycEdit) {
                    txtIdentityDetailsErrMsg.setVisibility(VISIBLE);

                    if (LoanTabActivity.flatBuildingSociety.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter Flat No,Building Name,Society Name");
                        // edtAddressbr.requestFocus();

                    } else if (LoanTabActivity.streetLocalityLandmark.equals("")) {

                        txtIdentityDetailsErrMsg.setText("Please enter Street Name,Locality,LandMark");
                        //edtLandmarkbr.requestFocus();
                    } else if (LoanTabActivity.pincode.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter pincode");
                        //edtPincodeBr.requestFocus();
                    } else if (LoanTabActivity.aadhar.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter aadhar");
                        //edtAadhaar.requestFocus();
                    } else if (LoanTabActivity.pan.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter pan");
                        //edtPAN.requestFocus();
                    } else if (LoanTabActivity.countryId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter Country");
                        //spCountry.requestFocus();
                    } else if (LoanTabActivity.stateId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter State");
                        //spState.requestFocus();
                    } else if (LoanTabActivity.cityId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter City");
                        //spCity.requestFocus();
                    }
                    txtIdentityDetailsErrMsg.requestFocus();

                }

                indicateValidationText(txtIdentityToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_id_card), false);
            } else {
                txtIdentityDetailsErrMsg.setText(null);
                txtIdentityDetailsErrMsg.setVisibility(GONE);
//                relIdentityBlock.startAnimation(collapseAnimationIdentity);
                indicateValidationText(txtIdentityToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_id_card), true);
            }
        }
        if (documents.equals("1")) {
            if (LoanTabActivity.flatBuildingSociety.equals("") || LoanTabActivity.streetLocalityLandmark.equals("") ||
                    LoanTabActivity.pincode.length() < 6 || LoanTabActivity.aadhar.equals("") ||
                    !LoanTabActivity.pan.equals("") || LoanTabActivity.countryId.equals("") ||
                    LoanTabActivity.stateId.equals("") || LoanTabActivity.cityId.equals("")) {

                if (LoanTabActivity.isKycEdit) {
                    txtIdentityDetailsErrMsg.setVisibility(VISIBLE);

                    if (LoanTabActivity.flatBuildingSociety.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter Flat No,Building Name,Society Name");
                        // edtAddressbr.requestFocus();
                    } else if (LoanTabActivity.streetLocalityLandmark.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter Street Name,Locality,LandMark");
                        //edtLandmarkbr.requestFocus();
                    } else if (LoanTabActivity.pincode.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter pincode");
                        //edtPincodeBr.requestFocus();
                    } else if (LoanTabActivity.aadhar.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter aadhar");
                        //edtAadhaar.requestFocus();
                    } else if (LoanTabActivity.countryId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter Country");
                        //spCountry.requestFocus();
                    } else if (LoanTabActivity.stateId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter State");
                        //spState.requestFocus();
                    } else if (LoanTabActivity.cityId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter City");
                        //spCity.requestFocus();
                    }
                    txtIdentityDetailsErrMsg.requestFocus();

                }

                indicateValidationText(txtIdentityToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_id_card), false);
            } else {
                txtIdentityDetailsErrMsg.setText(null);
                txtIdentityDetailsErrMsg.setVisibility(GONE);
//                relIdentityBlock.startAnimation(collapseAnimationIdentity);
                indicateValidationText(txtIdentityToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_id_card), true);
            }
        } else if (documents.equals("2")) {
            if (LoanTabActivity.flatBuildingSociety.equals("") || LoanTabActivity.streetLocalityLandmark.equals("") ||
                    LoanTabActivity.pincode.length() < 6 || !LoanTabActivity.aadhar.equals("") ||
                    LoanTabActivity.pan.equals("") || LoanTabActivity.countryId.equals("") || LoanTabActivity.stateId.equals("")
                    || LoanTabActivity.cityId.equals("")) {

                if (LoanTabActivity.isKycEdit) {
                    txtIdentityDetailsErrMsg.setVisibility(VISIBLE);

                    if (LoanTabActivity.flatBuildingSociety.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter Flat No,Building Name,Society Name");
                        // edtAddressbr.requestFocus();
                    } else if (LoanTabActivity.streetLocalityLandmark.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter Street Name,Locality,LandMark");
                        //edtLandmarkbr.requestFocus();
                    } else if (LoanTabActivity.pincode.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter pincode");
                        //edtPincodeBr.requestFocus();
                    } else if (LoanTabActivity.pan.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter pan");
                        //edtPAN.requestFocus();
                    } else if (LoanTabActivity.countryId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter Country");
                        //spCountry.requestFocus();
                    } else if (LoanTabActivity.stateId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter State");
                        //spState.requestFocus();
                    } else if (LoanTabActivity.cityId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter City");
                        //spCity.requestFocus();
                    }
                    txtIdentityDetailsErrMsg.requestFocus();
                }

                indicateValidationText(txtIdentityToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_id_card), false);
            } else {
                txtIdentityDetailsErrMsg.setText(null);
                txtIdentityDetailsErrMsg.setVisibility(GONE);
//                relIdentityBlock.startAnimation(collapseAnimationIdentity);
                indicateValidationText(txtIdentityToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_id_card), true);
            }
        } else if (documents.equals("3")) {
            if (LoanTabActivity.flatBuildingSociety.equals("") || LoanTabActivity.streetLocalityLandmark.equals("") ||
                    LoanTabActivity.pincode.length() < 6 || LoanTabActivity.aadhar.equals("") || LoanTabActivity.pan.equals("") ||
                    LoanTabActivity.countryId.equals("") || LoanTabActivity.stateId.equals("") || LoanTabActivity.cityId.equals("")) {

                if (LoanTabActivity.isKycEdit) {
                    txtIdentityDetailsErrMsg.setVisibility(VISIBLE);

                    if (LoanTabActivity.flatBuildingSociety.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter Flat No,Building Name,Society Name");
                        //edtAddressbr.requestFocus();
                    } else if (LoanTabActivity.streetLocalityLandmark.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter Street Name,Locality,LandMark");
                        // edtLandmarkbr.requestFocus();
                    } else if (LoanTabActivity.pincode.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter pincode");
                        // edtPincodeBr.requestFocus();
                    } else if (LoanTabActivity.aadhar.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter aadhar");
                        //edtAadhaar.requestFocus();
                    } else if (LoanTabActivity.pan.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter pan");
                        //edtPAN.requestFocus();
                    } else if (LoanTabActivity.countryId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter Country");
                        //spCountry.requestFocus();
                    } else if (LoanTabActivity.stateId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter State");
                        //spState.requestFocus();
                    } else if (LoanTabActivity.cityId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("Please enter City");
                        //spCity.requestFocus();
                    }
                    txtIdentityDetailsErrMsg.requestFocus();
                }
                indicateValidationText(txtIdentityToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_id_card), false);
            } else {
                txtIdentityDetailsErrMsg.setText(null);
                txtIdentityDetailsErrMsg.setVisibility(GONE);
//                relIdentityBlock.startAnimation(collapseAnimationIdentity);
                indicateValidationText(txtIdentityToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_id_card), true);
            }
        } else {
            if (LoanTabActivity.flatBuildingSociety.equals("") || LoanTabActivity.streetLocalityLandmark.equals("") || LoanTabActivity.pincode.length() < 6 || (LoanTabActivity.aadhar.equals("") && LoanTabActivity.pan.equals("")) || LoanTabActivity.countryId.equals("") || LoanTabActivity.stateId.equals("") || LoanTabActivity.cityId.equals("")) {

                if (LoanTabActivity.isKycEdit) {
                    txtIdentityDetailsErrMsg.setVisibility(VISIBLE);

                    if (LoanTabActivity.flatBuildingSociety.equals("")) {
                        txtIdentityDetailsErrMsg.setText("please enter Flat No,Building Name,Society Name");
                        //edtAddressbr.requestFocus();

                    } else if (LoanTabActivity.streetLocalityLandmark.equals("")) {

                        txtIdentityDetailsErrMsg.setText("please enter Street Name,Locality,LandMark");
                        //edtLandmarkbr.requestFocus();
                    } else if (LoanTabActivity.pincode.equals("")) {
                        txtIdentityDetailsErrMsg.setText("please enter pincode");
                        //edtPincodeBr.requestFocus();
                    } else if (LoanTabActivity.aadhar.equals("")) {
                        txtIdentityDetailsErrMsg.setText("please enter aadhar");
                        // edtAadhaar.requestFocus();
                    } else if (LoanTabActivity.pan.equals("")) {
                        txtIdentityDetailsErrMsg.setText("please enter pan");
                        //edtPAN.requestFocus();
                    } else if (LoanTabActivity.countryId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("please enter Country");
                        //spCountry.requestFocus();
                    } else if (LoanTabActivity.stateId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("please enter State");
                        //spState.requestFocus();
                    } else if (LoanTabActivity.cityId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("please enter City");
                        //spCity.requestFocus();
                    }
                }
                indicateValidationText(txtIdentityToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_id_card), false);
            } else {
                txtIdentityDetailsErrMsg.setText(null);
                txtIdentityDetailsErrMsg.setVisibility(GONE);
                indicateValidationText(txtIdentityToggle, context.getResources().getDrawable(R.drawable.ic_borrower_details_id_card), true);
            }
        }

    }

    public static void validate() {
        if (LoanTabActivity.firstName.equals("") || LoanTabActivity.lastName.equals("") ||
                LoanTabActivity.email.equals("") || LoanTabActivity.mobile.equals("") ||
                LoanTabActivity.dob.equals("") || LoanTabActivity.gender.equals("") || (LoanTabActivity.aadhar.equals("") && LoanTabActivity.pan.equals("")) ||
                LoanTabActivity.maritalStatus.equals("") || LoanTabActivity.flatBuildingSociety.equals("")
                || LoanTabActivity.streetLocalityLandmark.equals("") || LoanTabActivity.pincode.equals("")
                || LoanTabActivity.pincode.length() < 6 || LoanTabActivity.countryId.equals("") ||
                LoanTabActivity.stateId.equals("") || LoanTabActivity.cityId.equals("") ||
                LoanTabActivity.institute_name.equals("") || LoanTabActivity.instituteLocationId.equals("")
                || LoanTabActivity.courseId.equals("") || LoanTabActivity.course_cost.equals("") ||
                LoanTabActivity.requested_loan_amount.equals("") || (loanAmountvalueInInt > courseFeeValueinint)) {

            mListener.onFragmentInteraction(false, 0);
        } else {
            saveEditedKycData();
//            mListener.onFragmentInteraction(true, 1);
        }
    }

    public void indicateValidationText(TextView indicator, Drawable start, boolean valid) {
        if (valid) {
            indicator.setCompoundDrawablesRelativeWithIntrinsicBounds(start, null, context.getResources().getDrawable(R.drawable.ic_check_circle_green), null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                indicator.getCompoundDrawablesRelative()[0].setTint(context.getResources().getColor(R.color.colorGreen));
                indicator.getCompoundDrawablesRelative()[2].setTint(context.getResources().getColor(R.color.colorGreen));
            }
            indicator.setTextColor(context.getResources().getColor(R.color.colorGreen));
        } else {
            indicator.setCompoundDrawablesRelativeWithIntrinsicBounds(start, null, context.getResources().getDrawable(R.drawable.ic_exclamation_circle), null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                indicator.getCompoundDrawablesRelative()[0].setTint(context.getResources().getColor(R.color.blue1));
                indicator.getCompoundDrawablesRelative()[2].setTint(context.getResources().getColor(R.color.new_red));
            }
            indicator.setTextColor(context.getResources().getColor(R.color.blue1));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
//        LoanTabActivity.isKycEdit = false;

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
                volleyCall.sendRequest(context, url, null, KycDetailFragment.this, "getCountriesKyc", params, MainActivity.auth_token);
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

                currentCountry_arrayList = new ArrayList<>();
                borrowerCurrentCountryPersonalPOJOArrayList = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    BorrowerCurrentCountryPersonalPOJO borrowerCurrentCountryPersonalPOJO = new BorrowerCurrentCountryPersonalPOJO();
                    borrowerCurrentCountryPersonalPOJO.countryName = jsonObject1.getString("country_name");
                    currentCountry_arrayList.add(jsonObject1.getString("country_name"));
                    borrowerCurrentCountryPersonalPOJO.countryID = jsonObject1.getString("country_id");
                    borrowerCurrentCountryPersonalPOJOArrayList.add(borrowerCurrentCountryPersonalPOJO);

                }

                arrayAdapter_currentCountry = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentCountry_arrayList);
                spCountry.setAdapter(arrayAdapter_currentCountry);
                arrayAdapter_currentCountry.notifyDataSetChanged();


            } else {
//				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stateApiCall() {
        /**API CALL**/
        try {
            String url = MainActivity.mainUrl + "algo/getStates";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", currentcountryID);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentStates(currentcountryID);

            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "getStatesKyc", params, MainActivity.auth_token);
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

    public void getCurrentStates(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    currentstate_arrayList = new ArrayList<>();
                    currentstate_arrayList.add("Select Any");
                    arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                    spState.setAdapter(arrayAdapter_currentState);
                    arrayAdapter_currentState.notifyDataSetChanged();
                    spState.setSelection(0);
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
                    spState.setAdapter(arrayAdapter_currentState);
                    arrayAdapter_currentState.notifyDataSetChanged();

                    int count = borrowerCurrentStatePersonalPOJOArrayList.size();

                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
                            spState.setSelection(i);
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

    private void cityApiCall() {
        /**API CALL**/
        try {
            String url = MainActivity.mainUrl + "algo/getCities"; //http://159.89.204.41/eduvanzApi/algo/getCities
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", currentcountryID);//1
            params.put("stateId", currentstateID);//2
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentCities(currentstateID,currentcountryID);
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "getCityKyc", params, MainActivity.auth_token);
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

    public void getCurrentCities(JSONObject jsonData) {
        try {
            if (jsonData.toString().equals("{}")) {
                try {
                    currentcity_arrayList = new ArrayList<>();
                    currentcity_arrayList.add("Select Any");
                    arrayAdapter_currentCity = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentcity_arrayList);
                    spCity.setAdapter(arrayAdapter_currentCity);
                    arrayAdapter_currentCity.notifyDataSetChanged();
                    spCity.setSelection(0);
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
                    spCity.setAdapter(arrayAdapter_currentCity);
                    arrayAdapter_currentCity.notifyDataSetChanged();

                    int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                            spCity.setSelection(i);
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

    public void kycApiCall() {
//        progressDialog.setMessage("Loading");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        String url = MainActivity.mainUrl + "dashboard/getKycDetails";
        Map<String, String> params = new HashMap<String, String>();
        params.put("lead_id", LoanTabActivity.lead_id);
        if (!Globle.isNetworkAvailable(context)) {
            Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
        } else {
            VolleyCall volleyCall = new VolleyCall();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
            volleyCall.sendRequest(context, url, null, KycDetailFragment.this, "studentKycDetails", params, MainActivity.auth_token);
        }
    }

    public void setStudentKycDetails(JSONObject jsonData) {
//        progressDialog.dismiss();
        String message = jsonData.optString("message");
        try {
            if (jsonData.getInt("status") == 1) {

                if (!jsonData.get("kycDetails").equals(null)) {
                    JSONObject jsonkycDetails = jsonData.getJSONObject("kycDetails");

//                    MainApplication.lead_idkyc = lead_id = jsonkycDetails.getString("lead_id");
                    LoanTabActivity.application_id = jsonkycDetails.getString("application_id");
                    LoanTabActivity.institute_name = jsonkycDetails.getString("institute_name");
                    LoanTabActivity.location_name = jsonkycDetails.getString("location_name");
                    LoanTabActivity.course_name = jsonkycDetails.getString("course_name");
                    LoanTabActivity.course_cost = jsonkycDetails.getString("course_cost");
                    LoanTabActivity.fk_institutes_id = jsonkycDetails.getString("fk_institutes_id");
                    LoanTabActivity.fk_insitutes_location_id = jsonkycDetails.getString("fk_insitutes_location_id");
                    LoanTabActivity.fk_course_id = jsonkycDetails.getString("fk_course_id");

//                    if (!LoanTabActivity.requested_loan_amount.equals("null")) {
//                        edtLoanAmt.setText(LoanTabActivity.requested_loan_amount);
//                    }

                    if (!LoanTabActivity.institute_name.equals("null") && !LoanTabActivity.institute_name.equals("")) {
                        acInstituteName.setText(LoanTabActivity.institute_name);
                    }
                    if (!LoanTabActivity.fk_institutes_id.equals("null") && !LoanTabActivity.fk_institutes_id.equals("")) {
                        LoanTabActivity.instituteId = instituteID = LoanTabActivity.fk_institutes_id;
                        locationApiCall();
                    }

                    if (!LoanTabActivity.fk_insitutes_location_id.equals("null") && !LoanTabActivity.fk_insitutes_location_id.equals("")) {

                        LoanTabActivity.instituteLocationId = locationID = LoanTabActivity.fk_insitutes_location_id;

//                        try {
//                            if (!LoanTabActivity.fk_insitutes_location_id.equals("") && !LoanTabActivity.fk_insitutes_location_id.equals("null")) {
//                                try {
//
//                                    int count = locationPOJOArrayList.size();
//                                    for (int i = 0; i < count; i++) {
//                                        if (locationPOJOArrayList.get(i).locationID.equalsIgnoreCase(LoanTabActivity.instituteLocationId)) {
//                                            spInsttLocation.setSelection(i);
//                                            break;
//                                        }
//                                    }
////                                    int count = locationPOJOArrayList.size();
////                                    for (int i = 0; i < count; i++) {
////                                        if (locationPOJOArrayList.get(i).locationID.equalsIgnoreCase(locationID)) {
////                                            spInsttLocation.setSelection(i);
////                                        }
////                                    }
//
//                                } catch (Exception e) {
//
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }

                    if (!LoanTabActivity.fk_course_id.equals("null") && !LoanTabActivity.fk_course_id.equals("")) {
                        LoanTabActivity.courseId = courseID = LoanTabActivity.fk_course_id;

//                        try {
//                            if (!LoanTabActivity.fk_course_id.equals("") && !LoanTabActivity.fk_course_id.equals("null")) {
//                                try {
//
//                                    int count = nameOfCoursePOJOArrayList.size();
//                                    for (int i = 0; i < count; i++) {
//                                        if (nameOfCoursePOJOArrayList.get(i).courseID.equalsIgnoreCase(LoanTabActivity.courseId)) {
//                                            spCourse.setSelection(i);
//                                            break;
//
//                                        }
//                                    }
//
////                                    int count = nameOfCoursePOJOArrayList.size();
////                                    for (int i = 0; i < count; i++) {
////                                        if (nameOfCoursePOJOArrayList.get(i).courseID.equalsIgnoreCase(courseID)) {
////                                            spCourse.setSelection(i);
////                                        }
////                                    }
//
//                                } catch (Exception e) {
//
//                                }
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
                    }

                    if (jsonkycDetails.getString("requested_loan_amount") != null) {
                        LoanTabActivity.requested_loan_amount = jsonkycDetails.getString("requested_loan_amount");
                        if (!LoanTabActivity.requested_loan_amount.equals("") && !LoanTabActivity.requested_loan_amount.equals("null")) {
                            edtLoanAmt.setText(LoanTabActivity.requested_loan_amount);
                        }
                    }

                    if (!LoanTabActivity.course_cost.equals("null")) {
                        txtCourseFee.setText(LoanTabActivity.course_cost);
                        if (edtLoanAmt.getText().toString().length() > 0 && txtCourseFee.getText().toString().length() > 0) {
                            loanAmountvalueInInt = Integer.parseInt(edtLoanAmt.getText().toString());
                            courseFeeValueinint = Integer.parseInt(LoanTabActivity.course_cost);

                            if (loanAmountvalueInInt > courseFeeValueinint) {
//                                edtLoanAmt.setError("Loan amount not exceed than course fees!");
                                LoanTabActivity.requested_loan_amount = "";

                            } else {
                                LoanTabActivity.requested_loan_amount = edtLoanAmt.getText().toString();
                                edtLoanAmt.setError(null);
                            }
//                            if(LoanTabActivity.isKycEdit) {
//                        chekAllFields();
//                    }
                        }

                    }

                }

                if (jsonData.has("borrowerDetails") && jsonData.getJSONObject("borrowerDetails") != null) {
                    JSONObject jsonborrowerDetails = jsonData.getJSONObject("borrowerDetails");

                    LoanTabActivity.has_aadhar_pan = documents = jsonborrowerDetails.getString("has_aadhar_pan");

                    if (jsonborrowerDetails.getString("first_name") != null) {
                        LoanTabActivity.firstName = jsonborrowerDetails.getString("first_name");
                        if (!LoanTabActivity.firstName.equals("") && !LoanTabActivity.firstName.equals("null")) {
                            edtFnameBr.setText(LoanTabActivity.firstName);
                        }
                    }
                    if (jsonborrowerDetails.getString("applicant_id") != null) {
                        LoanTabActivity.applicant_id = jsonborrowerDetails.getString("applicant_id");
                    }

                    if (jsonborrowerDetails.getString("middle_name") != null) {
                        LoanTabActivity.middleName = jsonborrowerDetails.getString("middle_name");
                        if (!LoanTabActivity.middleName.equals("") && !LoanTabActivity.middleName.equals("null")) {
                            edtMnameBr.setText(LoanTabActivity.middleName);
                        }
                    }

                    if (jsonborrowerDetails.getString("last_name") != null) {
                        LoanTabActivity.lastName = jsonborrowerDetails.getString("last_name");
                        if (!LoanTabActivity.lastName.equals("") && !LoanTabActivity.lastName.equals("null")) {
                            edtLnameBr.setText(LoanTabActivity.lastName);
                        }
                    }

                    if (jsonborrowerDetails.getString("email_id") != null) {
                        LoanTabActivity.email = jsonborrowerDetails.getString("email_id");
                        if (!LoanTabActivity.email.equals("") && !LoanTabActivity.email.equals("null")) {
                            edtEmailIdBr.setText(LoanTabActivity.email);
                        }
                    }

                    if (jsonborrowerDetails.getString("mobile_number") != null) {
                        LoanTabActivity.mobile = jsonborrowerDetails.getString("mobile_number");
                        if (!LoanTabActivity.mobile.equals("") && !LoanTabActivity.mobile.equals("null")) {
                            edtMobileNoBr.setText(LoanTabActivity.mobile);
                        }
                    }

                    if (jsonborrowerDetails.getString("dob") != null) {
                        LoanTabActivity.dob = jsonborrowerDetails.getString("dob");
                        if (!LoanTabActivity.dob.equals("") && !LoanTabActivity.dob.equals("null")) {
                            txtDOB.setText(LoanTabActivity.dob);
                        }
                    }

                    if (jsonborrowerDetails.getString("gender_id") != null) {
                        LoanTabActivity.gender = jsonborrowerDetails.getString("gender_id");
                        if (!LoanTabActivity.gender.equals("") && !LoanTabActivity.gender.equals("null")) {
                            if (LoanTabActivity.gender.equals("1")) {
                                linMale.setBackground(context.getResources().getDrawable(R.drawable.border_circular_blue_filled));
                                linFemale.setBackground(context.getResources().getDrawable(R.drawable.border_circular));
                                linOther.setBackground(context.getResources().getDrawable(R.drawable.border_circular));
                            } else if (LoanTabActivity.gender.equals("2")) {
                                linMale.setBackground(context.getResources().getDrawable(R.drawable.border_circular));
                                linFemale.setBackground(context.getResources().getDrawable(R.drawable.border_circular_blue_filled));
                                linOther.setBackground(context.getResources().getDrawable(R.drawable.border_circular));
                            } else if (LoanTabActivity.gender.equals("3")) {
                                linMale.setBackground(context.getResources().getDrawable(R.drawable.border_circular));
                                linFemale.setBackground(context.getResources().getDrawable(R.drawable.border_circular));
                                linOther.setBackground(context.getResources().getDrawable(R.drawable.border_circular_blue_filled));
                            }
                        }
                    }

                    if (jsonborrowerDetails.getString("marital_status") != null) {
                        if (!jsonborrowerDetails.getString("marital_status").equals("") && !jsonborrowerDetails.getString("marital_status").equals("null")) {
                            LoanTabActivity.maritalStatus = jsonborrowerDetails.getString("marital_status");
                            if (LoanTabActivity.maritalStatus.equals("1")) {
                                txtMaritalStatus.setText("Married");
                                switchMarital.setChecked(true);
                            } else if (LoanTabActivity.maritalStatus.equals("2")) {
                                txtMaritalStatus.setText("Unmarried");
                                switchMarital.setChecked(false);
                            }
                        } else {
                            LoanTabActivity.maritalStatus = "2";
                            switchMarital.setChecked(false);

                        }
                    }

                    if (jsonborrowerDetails.getString("aadhar_number") != null) {
                        LoanTabActivity.aadhar = jsonborrowerDetails.getString("aadhar_number");
                        if (!LoanTabActivity.aadhar.equals("") && !LoanTabActivity.aadhar.equals("null")) {
                            edtAadhaar.setText(LoanTabActivity.aadhar);
                        }
                    }

                    if (jsonborrowerDetails.getString("pan_number") != null) {
                        LoanTabActivity.pan = jsonborrowerDetails.getString("pan_number");
                        if (!LoanTabActivity.pan.equals("") && !LoanTabActivity.pan.equals("null")) {
                            edtPAN.setText(LoanTabActivity.pan);
                        }
                    }

                    if (jsonborrowerDetails.getString("current_address") != null) {
                        LoanTabActivity.flatBuildingSociety = jsonborrowerDetails.getString("current_address");
                        if (!LoanTabActivity.flatBuildingSociety.equals("") && !LoanTabActivity.flatBuildingSociety.equals("null")) {
                            edtAddressbr.setText(LoanTabActivity.flatBuildingSociety);
                        }
                    }
                    if (jsonborrowerDetails.getString("current_landmark") != null) {
                        LoanTabActivity.streetLocalityLandmark = jsonborrowerDetails.getString("current_landmark");
                        if (!LoanTabActivity.streetLocalityLandmark.equals("") && !LoanTabActivity.streetLocalityLandmark.equals("null")) {
                            edtLandmarkbr.setText(LoanTabActivity.streetLocalityLandmark);
                        }
                    }
                    if (jsonborrowerDetails.getString("current_address_pin") != null) {
                        LoanTabActivity.pincode = jsonborrowerDetails.getString("current_address_pin");
                        if (!LoanTabActivity.pincode.equals("") && !LoanTabActivity.pincode.equals("null")) {
                            edtPincodeBr.setText(LoanTabActivity.pincode);
                        }
                    }

                    if (jsonborrowerDetails.getString("current_address_country") != null) {
                        LoanTabActivity.countryId = jsonborrowerDetails.getString("current_address_country");

                        try {
                            if (!LoanTabActivity.countryId.equals("") && !LoanTabActivity.countryId.equals("null")) {
                                try {
                                    currentcountryID = LoanTabActivity.countryId;

                                    int count = borrowerCurrentCountryPersonalPOJOArrayList.size();
                                    for (int i = 0; i < count; i++) {
                                        if (borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID.equalsIgnoreCase(currentcountryID)) {
                                            spCountry.setSelection(i);
                                            break;
                                        }
                                    }

                                } catch (Exception e) {

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (jsonborrowerDetails.getString("current_address_state") != null) {
                        LoanTabActivity.stateId = jsonborrowerDetails.getString("current_address_state");

                        try {
                            if (!LoanTabActivity.stateId.equals("") && !LoanTabActivity.stateId.equals("null")) {
                                currentstateID = LoanTabActivity.stateId;
                                try {
                                    int count = borrowerCurrentStatePersonalPOJOArrayList.size();
                                    for (int i = 0; i < count; i++) {
                                        if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
                                            spState.setSelection(i);
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
                    }
                    if (jsonborrowerDetails.getString("current_address_city") != null) {
                        LoanTabActivity.cityId = jsonborrowerDetails.getString("current_address_city");

                        try {
                            if (!LoanTabActivity.cityId.equals("") && !LoanTabActivity.cityId.equals("null")) {
                                currentcityID = LoanTabActivity.cityId;
                                try {
                                    int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                                    for (int i = 0; i < count; i++) {
                                        if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
                                            spCity.setSelection(i);
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
                    }
                }
            } else {
                //           Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            if (!jsonData.get("leadStatus").equals(null)) {
                JSONObject jsonleadStatus = jsonData.getJSONObject("leadStatus");
                LoanTabActivity.lead_status = jsonleadStatus.getString("lead_status");
                LoanTabActivity.lead_sub_status = jsonleadStatus.getString("lead_sub_status");
                LoanTabActivity.current_stage = jsonleadStatus.getString("current_stage");
                LoanTabActivity.current_status = jsonleadStatus.getString("current_status");
            }

            if (LoanTabActivity.lead_status.equals("1") && LoanTabActivity.current_stage.equals("1")) {
            } else {
                fabEditKycDetail.setVisibility(View.GONE);
            }

//            if(LoanTabActivity.isKycEdit) {
//                        chekAllFields();
//                    }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void instituteApiCall() {
        /**API CALL**/
        try {
//            progressDialog.setMessage("Loading");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
            String url = MainActivity.mainUrl + "pqform/apiPrefillInstitutes";  //http://159.89.204.41/eduvanzApi/pqform/apiPrefillInstitutes
            Map<String, String> params = new HashMap<String, String>();
            VolleyCall volleyCall = new VolleyCall();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "instituteIdkyc", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
//            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void instituteName(JSONObject jsonData) {
        try {
//            progressDialog.dismiss();
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
//            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void setInstituteAdaptor() {

        try {

            arrayAdapter_NameOfInsititue = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofinstitute_arrayList);

            //Getting the instance of AutoCompleteTextView
            acInstituteName.setThreshold(3);//will start working from first character
            acInstituteName.setAdapter(arrayAdapter_NameOfInsititue);//setting the adapter data into the AutoCompleteTextView
//            acInstituteName.setTextColor(Color.RED);

            acInstituteName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    String countryName = (String) arg0.getItemAtPosition(arg2);
                    int count = nameOfInsitituePOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (nameOfInsitituePOJOArrayList.get(i).instituteName.equalsIgnoreCase((String) arg0.getItemAtPosition(arg2))) {
                            LoanTabActivity.instituteId = nameOfInsitituePOJOArrayList.get(i).instituteID;
                            locationApiCall();
                            break;
                        }
                    }
                    try {
                        ((LoanTabActivity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    } catch (Exception e) {
                        e.printStackTrace();
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
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void courseApiCall() {
        try {
//            progressDialog.setMessage("Loading");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
            String url = MainActivity.mainUrl + "pqform/apiPrefillCourses";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", LoanTabActivity.instituteId);
            params.put("location_id", LoanTabActivity.instituteLocationId);

            VolleyCall volleyCall = new VolleyCall();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "courseIdkyc", params, MainActivity.auth_token);
            }

        } catch (Exception e) {
//            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void locationApiCall() {
        try {
//            progressDialog.setMessage("Loading");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
            String url = MainActivity.mainUrl + "pqform/apiPrefillLocations";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", LoanTabActivity.instituteId);
            VolleyCall volleyCall = new VolleyCall();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "locationNamekyc", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
//            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void courseFeeApiCall() {
        /**API CALL**/
        try {
//            progressDialog.setMessage("Loading");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
            String url = MainActivity.mainUrl + "pqform/apiPrefillSliderAmount";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", LoanTabActivity.instituteId);
            params.put("course_id", LoanTabActivity.courseId);
            params.put("location_id", LoanTabActivity.instituteLocationId);
            VolleyCall volleyCall = new VolleyCall();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "courseFeekyc", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
//            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void courseFee(JSONObject jsonData) {
        try {
//            progressDialog.dismiss();
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                txtCourseFee.setText(jsonData.getString("result"));
                LoanTabActivity.course_cost = jsonData.getString("result");

                if (edtLoanAmt.getText().toString().length() > 0) {
                    loanAmountvalueInInt = Integer.parseInt(edtLoanAmt.getText().toString());
                    courseFeeValueinint = Integer.parseInt(LoanTabActivity.course_cost);

                    if (loanAmountvalueInInt > courseFeeValueinint) {
//                        edtLoanAmt.setError("Loan amount not exceed than course fees!");
                        LoanTabActivity.requested_loan_amount = "";

                    } else {
                        LoanTabActivity.requested_loan_amount = edtLoanAmt.getText().toString();
                        edtLoanAmt.setError(null);
                    }
                }
            } else {
                // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
            if (LoanTabActivity.isKycEdit) {
            chekAllFields();
            }
        } catch (Exception e) {
//            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }


    public void courseName(JSONObject jsonData) {
        try {
//            progressDialog.dismiss();
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

                arrayAdapter_NameOfCourse = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofcourse_arrayList);
                spCourse.setAdapter(arrayAdapter_NameOfCourse);
                arrayAdapter_NameOfCourse.notifyDataSetChanged();

                if (!LoanTabActivity.courseId.equals("")) {
                    int count = nameOfCoursePOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (nameOfCoursePOJOArrayList.get(i).courseID.equalsIgnoreCase(LoanTabActivity.courseId)) {
                            spCourse.setSelection(i);
                            break;
                        }
                    }
                    courseFeeApiCall();
                }

            } else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
//            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void locationName(JSONObject jsonData) {
        try {
//            progressDialog.dismiss();
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

                arrayAdapter_locations = new ArrayAdapter(context, R.layout.custom_layout_spinner, locations_arrayList);
                spInsttLocation.setAdapter(arrayAdapter_locations);
                arrayAdapter_locations.notifyDataSetChanged();

                if (!LoanTabActivity.instituteLocationId.equals("")) {

                    int count = locationPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (locationPOJOArrayList.get(i).locationID.equalsIgnoreCase(LoanTabActivity.instituteLocationId)) {
                            spInsttLocation.setSelection(i);
                            break;
                        }
                    }
                }

            } else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
//            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

//    public void setInstituteLocationAdaptor() {
//
//        try {
//            arrayAdapter_locations = new ArrayAdapter(context, R.layout.custom_layout_spinner, locations_arrayList);
//            spInsttLocation.setAdapter(arrayAdapter_locations);
//            arrayAdapter_locations.notifyDataSetChanged();
//
//            int count = borrowerCurrentCityPersonalPOJOArrayList.size();
//            for (int i = 0; i < count; i++) {
//                if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID.equalsIgnoreCase(currentcityID)) {
//                    spCity.setSelection(i);
//                }
//            }
//
//            if (!LoanTabActivity.instituteLocationId.equals("")) {
//                for (int i = 0; i < locationPOJOArrayList.size(); i++) {
//                    if (LoanTabActivity.instituteLocationId.equalsIgnoreCase(locationPOJOArrayList.get(i).locationID)) {
//                        spInsttLocation.setSelection(i);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            String className = this.getClass().getSimpleName();
//            String name = new Object() {
//            }.getClass().getEnclosingMethod().getName();
//            String errorMsg = e.getMessage();
//            String errorMsgDetails = e.getStackTrace().toString();
//            String errorLine = String.valueOf(e.getStackTrace()[0]);
//            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
//        }
//
//    }

    public interface OnFragmentInteracting {
        void onFragmentInteraction(boolean valid, int next);
    }

}
