package com.eduvanzapplication.newUI.fragments;

import android.app.ProgressDialog;
import android.content.Context;
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
    public static ImageView ivPersonalToggle, ivIdentityToggle, ivCourseToggle, ivPersonalTitle, ivIdentityTitle, ivCourseTitle,
            ivPersonalStatus, ivIdentityStatus, ivCourseStatus;
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

    public static ImageView ivMale, ivFemale, ivOther, iviewMale, iviewFemale, iviewOther;
    public static ImageButton btnNextKycDetail;
    public static Switch switchMarital;
    public static TextView txtMaritalStatus,tvGenderTitle,
                           tvDOBtitle,tvcountrytitle,tvstatetitle,
                           tvcitytitle,tvMaritalStatustitle,tvselctInstuTile,tvselecInstuLocnTitle,tvslectCourseTitile,tvcoursefeeTitle,tvloanamountTitle;
    public static EditText edtFnameBr, edtMnameBr, edtLnameBr, edtEmailIdBr, edtMobileNoBr, edtAddressbr, edtLandmarkbr, edtPincodeBr;
    public static LinearLayout linEditKycDetail, linMaleBtn, linFemaleBtn, linOtherBtn, linDob, linMaritalStatus;
    public static EditText edtAadhaar, edtPAN, edtLoanAmt;
    public static Spinner spCountry, spState, spCity, spInsttLocation, spCourse;
    public static AutoCompleteTextView acInstituteName;
    public static TextView txtCourseFee, txtDOB, txtMale, txtFemale, txtOther;
    public static TextView txtBtnEditKyc;
    public static ImageView ivBtnEditKyc;
    public static OnFragmentInteracting mListener;

    public static String currentcityID = "", currentstateID = "", currentcountryID = "", instituteID = "", courseID = "", locationID = "";

    //kyc values
    public static String firstName = "", lastName = "", middleName = "", gender = "", dob = "", maritalStatus = "2", email = "", mobile = "",
            aadhar = "", pan = "", flatBuildingSociety = "", streetLocalityLandmark = "", pincode = "", countryId = "", stateId = "", cityId = "",
            instituteId = "", courseId = "", instituteLocationId = "", courseFee = "", applicant_id = "",
            application_id = "", requested_loan_amount = "", institute_name = "", location_name = "",
            course_name = "", course_cost = "", fk_institutes_id = "", fk_insitutes_location_id = "", fk_course_id = "", lead_status = "",
            lead_sub_status = "", current_status = "", current_stage = "", has_aadhar_pan = "";

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
        MainActivity.currrentFrag = 1;

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

            //this is New titile Add
        tvGenderTitle = view.findViewById(R.id.tvGenderTitle);
        tvDOBtitle = view.findViewById(R.id.tvDOBtitle);
        tvcountrytitle = view.findViewById(R.id.countrytitle);
        tvstatetitle = view.findViewById(R.id.statetitle);
        tvcitytitle = view.findViewById(R.id.citytitle);
        tvMaritalStatustitle = view.findViewById(R.id.MaritalStatustitle);


        tvselctInstuTile = view.findViewById(R.id.tvselctInstuTile);
        tvselecInstuLocnTitle = view.findViewById(R.id.tvselecInstuLocnTitle);

        tvslectCourseTitile = view.findViewById(R.id.tvslectCourseTitile);
       tvcoursefeeTitle = view.findViewById(R.id.tvcoursefeeTitle);
        tvloanamountTitle = view.findViewById(R.id.tvloanamountTitle);




        /*--------------------------------------------------------------------*/

        ivPersonalTitle = view.findViewById(R.id.ivPersonalTitle);
        ivIdentityTitle = view.findViewById(R.id.ivIdentityTitle);
        ivCourseTitle = view.findViewById(R.id.ivCourseTitle);

        ivPersonalStatus = view.findViewById(R.id.ivPersonalStatus);
        ivIdentityStatus = view.findViewById(R.id.ivIdentityStatus);
        ivCourseStatus = view.findViewById(R.id.ivCourseStatus);

        txtPersonalToggle = view.findViewById(R.id.txtPersonalToggle);
        linPersonalBlock = view.findViewById(R.id.linPersonalBlock);
        txtIdentityToggle = view.findViewById(R.id.txtIdentityToggle);
        linPersonalToggle = view.findViewById(R.id.linPersonalToggle);
        linIdentityToggle = view.findViewById(R.id.linIdentityToggle);
        linCourseToggle = view.findViewById(R.id.linCourseToggle);
        relIdentityBlock = view.findViewById(R.id.relIdentityBlock);
        txtCourseToggle = view.findViewById(R.id.txtCourseToggle);
        relCourseBlock = view.findViewById(R.id.relCourseBlock);

        txtBtnEditKyc = view.findViewById(R.id.txtBtnEditKyc);
        ivBtnEditKyc = view.findViewById(R.id.ivBtnEditKyc);
        linEditKycDetail = view.findViewById(R.id.linEditKycDetail);
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
        linMaleBtn = view.findViewById(R.id.linMaleBtn);
        linFemaleBtn = view.findViewById(R.id.linFemaleBtn);
        linOtherBtn = view.findViewById(R.id.linOtherBtn);
        linDob = view.findViewById(R.id.linDob);
        ivMale = view.findViewById(R.id.ivMale);
        ivFemale = view.findViewById(R.id.ivFemale);
        ivOther = view.findViewById(R.id.ivOther);
        iviewMale = view.findViewById(R.id.iviewMale);
        iviewFemale = view.findViewById(R.id.iviewFemale);
        iviewOther = view.findViewById(R.id.iviewOther);
        txtMale = view.findViewById(R.id.txtMale);
        txtFemale = view.findViewById(R.id.txtFemale);
        txtOther = view.findViewById(R.id.txtOther);
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

        firstName = "";
        lastName = "";
        middleName = "";
        gender = "";
        dob = "";
        maritalStatus = "2";
        email = "";
        mobile = "";
        aadhar = "";
        pan = "";
        flatBuildingSociety = "";
        streetLocalityLandmark = "";
        pincode = "";
        countryId = "";
        stateId = "";
        cityId = "";
        instituteId = "";
        courseId = "";
        instituteLocationId = "";
        courseFee = "";
        applicant_id = "";
        application_id = "";
        requested_loan_amount = "";
        institute_name = "";
        location_name = "";
        course_name = "";
        course_cost = "";
        fk_institutes_id = "";
        fk_insitutes_location_id = "";
        fk_course_id = "";
        lead_status = "";
        lead_sub_status = "";
        current_status = "";
        current_stage = "";
        has_aadhar_pan = "";

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        linPersonalBlock.startAnimation(collapseanimationPersonal);
//        relIdentityBlock.startAnimation(collapseAnimationIdentity);
//        relCourseBlock.startAnimation(collapseAnimationCourse);
        setViewsEnabled(false);

        btnNextKycDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LoanTabActivity.isKycEdit) {
                    if (firstName.equals("") || lastName.equals("") ||
                            email.equals("") || mobile.equals("") ||
                            dob.equals("") || gender.equals("") ||
                            maritalStatus.equals("") || flatBuildingSociety.equals("")
                            || streetLocalityLandmark.equals("") || pincode.equals("")
                            || pincode.length() < 6 || countryId.equals("") ||
                            stateId.equals("") || cityId.equals("") ||
                            institute_name.equals("") || instituteLocationId.equals("")
                            || courseId.equals("") || course_cost.equals("") ||
                            requested_loan_amount.equals("") || (loanAmountvalueInInt > courseFeeValueinint)) {
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

        linEditKycDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (LoanTabActivity.isKycEdit) {
                    LoanTabActivity.isKycEdit = false;
                    txtBtnEditKyc.setText("Edit");
                    setViewsEnabled(false);

                    Drawable bg;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_pencil_alt, null);
                        ivBtnEditKyc.setColorFilter(context.getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg = ContextCompat.getDrawable(context, R.drawable.ic_pencil_alt);
                        DrawableCompat.setTint(bg, context.getResources().getColor(R.color.white));
                    }
                    ivBtnEditKyc.setImageDrawable(bg);
                    linEditKycDetail.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));
                } else {
                    LoanTabActivity.isKycEdit = true;
                    txtBtnEditKyc.setText("Cancel");
                    txtBtnEditKyc.setTag("Cancel");
                    setViewsEnabled(true);

                    Drawable bg;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_clear, null);
                        ivBtnEditKyc.setColorFilter(context.getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg = ContextCompat.getDrawable(context, R.drawable.ic_clear);
                        DrawableCompat.setTint(bg, context.getResources().getColor(R.color.white));
                    }
                    ivBtnEditKyc.setImageDrawable(bg);

                    linEditKycDetail.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                }
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
                    if (LoanTabActivity.isKycEdit) {
                        chekAllFields();
                    }
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
                    if (LoanTabActivity.isKycEdit) {
                        chekAllFields();
                    }
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
                    if (LoanTabActivity.isKycEdit) {
                        chekAllFields();
                    }
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
                    if (LoanTabActivity.isKycEdit) {
                        chekAllFields();
                    }
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
                    if (LoanTabActivity.isKycEdit) {
                        chekAllFields();
                    }
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
                    if (LoanTabActivity.isKycEdit) {
                        chekAllFields();
                    }
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

        expandAnimationPersonal.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linPersonalBlock.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if (relIdentityBlock.getVisibility() == VISIBLE) {
                    relIdentityBlock.startAnimation(collapseAnimationCourse);
                }
                if (relCourseBlock.getVisibility() == VISIBLE) {
                    relCourseBlock.startAnimation(collapseanimationPersonal);
                }
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

        expandAnimationIdentity.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                relIdentityBlock.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (linPersonalBlock.getVisibility() == VISIBLE) {
                    linPersonalBlock.startAnimation(collapseanimationPersonal);
                }
                if (relCourseBlock.getVisibility() == VISIBLE) {
                    relCourseBlock.startAnimation(collapseAnimationCourse);
                }
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

        expanAnimationCourse.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                relCourseBlock.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (linPersonalBlock.getVisibility() == VISIBLE) {
                    linPersonalBlock.startAnimation(collapseanimationPersonal);
                }
                if (relIdentityBlock.getVisibility() == VISIBLE) {
                    relIdentityBlock.startAnimation(collapseAnimationCourse);
                }
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
                            cityId = currentcityID = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
                            if (LoanTabActivity.isKycEdit) {
                                chekAllFields();
                            }
                            break;
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
                            stateId = currentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
                            if (LoanTabActivity.isKycEdit) {
                                chekAllFields();
                            }
                            break;
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
                            countryId = currentcountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                            if (LoanTabActivity.isKycEdit) {
                                chekAllFields();
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                }
                stateApiCall();
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
                            courseId = nameOfCoursePOJOArrayList.get(i).courseID;
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
                            instituteLocationId = locationPOJOArrayList.get(i).locationID;
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

            params.put("lead_id", MainActivity.lead_id);
            params.put("fk_institutes_id", instituteId);
            params.put("fk_insitutes_location_id", instituteLocationId);
            params.put("fk_course_id", courseId);
            params.put("requested_loan_amount", requested_loan_amount);
            params.put("applicant_id", applicant_id);
            params.put("profession", "");
            params.put("first_name", firstName);
            params.put("middle_name", middleName);
            params.put("last_name", lastName);
            params.put("dob", dob);
            params.put("gender_id", gender);
            params.put("mobile_number", mobile);
            params.put("email_id", email);
            params.put("pan_number", pan);
            params.put("aadhar_number", aadhar);
            params.put("kyc_address", flatBuildingSociety);
            params.put("kyc_landmark", streetLocalityLandmark);
            params.put("kyc_address_pin", pincode);
            params.put("marital_status", maritalStatus);
            params.put("kyc_address_country", countryId);
            params.put("kyc_address_state", stateId);
            params.put("kyc_address_city", cityId);
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
                linEditKycDetail.setVisibility(VISIBLE);
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
        //selctInstuTile,selecInstuLocnTitle,slectCourseTitile,coursefeeTitle,loanamountTitle

        tvselctInstuTile.setEnabled(f);
        tvselecInstuLocnTitle.setEnabled(f);
        tvslectCourseTitile.setEnabled(f);
        tvslectCourseTitile.setEnabled(f);
        tvcoursefeeTitle.setEnabled(f);
        tvloanamountTitle.setEnabled(f);


        tvGenderTitle.setEnabled(f);
        tvDOBtitle.setEnabled(f);
        tvcountrytitle.setEnabled(f);
        tvstatetitle.setEnabled(f);
        tvcitytitle.setEnabled(f);
        tvMaritalStatustitle.setEnabled(f);

        edtFnameBr.setEnabled(f);
        edtMnameBr.setEnabled(f);
        edtLnameBr.setEnabled(f);
        linMaleBtn.setEnabled(f);
        linFemaleBtn.setEnabled(f);
        linOtherBtn.setEnabled(f);
        ivMale.setEnabled(f);
        ivFemale.setEnabled(f);
        ivOther.setEnabled(f);
        txtMale.setEnabled(f);
        txtFemale.setEnabled(f);
        txtOther.setEnabled(f);
        iviewMale.setEnabled(f);
        iviewFemale.setEnabled(f);
        iviewOther.setEnabled(f);
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

        if(!f){

            if (LoanTabActivity.isKycEdit){

                 indicateValidationTextdefault(txtPersonalToggle, false);
            indicateValidationTextdefault(txtIdentityToggle, false);
                 indicateValidationTextdefault(txtCourseToggle, false);

                 indicateValidationTextdefaultBlue(txtPersonalToggle, false);
                 indicateValidationTextdefaultBlue(txtIdentityToggle, false);
                 indicateValidationTextdefaultBlue(txtCourseToggle, false);

                 //setgender colour blue when click on edit button

            } else {

//         if (txtBtnEditKyc.getTag().toString().toLowerCase().contains("cancel")) {
                //close cancel button
         if (txtBtnEditKyc.getTag() != null) {

                    //personal
             if (firstName.equals("") || lastName.equals("") || email.equals("") ||
                     mobile.equals("") || dob.equals("") || gender.equals("") ||
                     maritalStatus.equals("")){
                 indicateValidationTextdefault(txtPersonalToggle, false);
                 indicateValidationIcondefault(ivPersonalStatus, ivPersonalTitle, false);

             }else{
                 indicateValidationTextdefault(txtPersonalToggle, false);
                 indicateValidationIcondefault(ivPersonalStatus, ivPersonalTitle, true);
                 //select gender ,DOB and Marital status colour grey on click on cancel button
                     selectgendercolourGrey();
             }

             //Course
             if (instituteId.equals("") || instituteLocationId.equals("") ||
                     courseId.equals("") || course_cost.equals("") ||
                     requested_loan_amount.equals("")){

                 indicateValidationTextdefault(txtCourseToggle, false);
                 indicateValidationIcondefault(ivCourseStatus, ivCourseTitle, false);

             } else{

                 indicateValidationTextdefault(txtCourseToggle, false);
                 indicateValidationIcondefault(ivCourseStatus, ivCourseTitle, true);

             }

             //Identity

             if(flatBuildingSociety.equals("") || streetLocalityLandmark.equals("") ||
                     pincode.length() < 6 || (aadhar.equals("") && pan.equals("")) ||
                     countryId.equals("") || stateId.equals("") || cityId.equals("")){

                 indicateValidationTextdefault(txtIdentityToggle, false);
                 indicateValidationIcondefault(ivIdentityStatus, ivIdentityTitle, false);

             }else{

                 indicateValidationTextdefault(txtIdentityToggle, false);
                 indicateValidationIcondefault(ivIdentityStatus, ivIdentityTitle, true);
             }



         }else{

             //this below code for onpageload without click on edit btn

            indicateValidationTextdefault(txtPersonalToggle, false);
                 indicateValidationTextdefault(txtIdentityToggle, false);
            indicateValidationTextdefault(txtCourseToggle, false);


         }

            }

        } //outer if closed

        else {
                  //on KYC edit click

            if (txtBtnEditKyc.getText().toString().toLowerCase().contains("cancel")) {

                indicateValidationTextdefaultBlue(txtPersonalToggle, true);
                indicateValidationTextdefaultBlue(txtIdentityToggle, true);
                indicateValidationTextdefaultBlue(txtCourseToggle, true);

                indicateValidationIcondefaultBlue(ivPersonalStatus, ivPersonalTitle, true);
                indicateValidationIcondefaultBlue(ivIdentityStatus, ivIdentityTitle, true);
                indicateValidationIcondefaultBlue(ivCourseStatus, ivCourseTitle, true);

               // select gender ,DOB and Marital status colour Blue on edit btn enable
                selectgendercolourBlue();

                //  chekAllFields();
            }

           else  {

             /*  //default text true
                indicateValidationTextdefault(txtIdentityToggle, true);
                indicateValidationTextdefault(txtPersonalToggle, true);
                indicateValidationTextdefault(txtCourseToggle, true);
                //defaulticon true
                indicateValidationIcondefault(ivPersonalStatus, ivPersonalTitle, true);
                indicateValidationIcondefault(ivCourseStatus, ivCourseTitle, true);
                indicateValidationIcondefault(ivIdentityStatus, ivIdentityTitle, true);
                //texteditbtn
                indicateValidationTextdefaultBlue(txtPersonalToggle, true);
                indicateValidationTextdefaultBlue(txtIdentityToggle, true);
                indicateValidationTextdefaultBlue(txtCourseToggle, true);
                //iconeditbtn,titleditbtn
                indicateValidationIcondefaultBlue(ivPersonalStatus, ivPersonalTitle, true);
                indicateValidationIcondefaultBlue(ivIdentityStatus, ivIdentityTitle, true);
                indicateValidationIcondefaultBlue(ivCourseStatus, ivCourseTitle, true);*/

        }

        } //use this code when edit btn click and blue color

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
                        dob = day + "-" + month + "-" + year;
                        txtDOB.setText(dob);
                        if (LoanTabActivity.isKycEdit) {
                            chekAllFields();
                        }
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

                firstName = edtFnameBr.getText().toString();
                if (LoanTabActivity.isKycEdit) {
                    chekAllFields();
                }
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
                middleName = edtMnameBr.getText().toString();
                if (LoanTabActivity.isKycEdit) {
                    chekAllFields();
                }
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
                lastName = edtLnameBr.getText().toString();
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

                if (LoanTabActivity.isKycEdit) {
                    chekAllFields();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

//        edtEmailIdBr.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                LoanTabActivity.email = edtEmailIdBr.getText().toString();
//                chekAllFields();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

//        edtMobileNoBr.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                LoanTabActivity.mobile = edtMobileNoBr.getText().toString();
//                chekAllFields();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

        edtAadhaar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!Globle.validateAadharNumber(edtAadhaar.getText().toString())) {
//False
                    if (edtAadhaar.getText().toString().length() > 0 && edtPAN.getText().toString().length() > 0) {
                        aadhar = "";
//                        edtAadhaar.setError("* Please Enter valid Aadhaar Number!");
                        edtPAN.setError(null);
                        documents = "3";
                    } else if (edtAadhaar.getText().toString().length() == 0 && edtPAN.getText().toString().length() > 0) {
                        aadhar = "";
                        edtAadhaar.setError(null);
                        edtPAN.setError(null);
                        documents = "2";
                    } else if (edtAadhaar.getText().toString().length() > 0 && edtPAN.getText().toString().length() == 0) {
                        aadhar = "";
//                        edtAadhaar.setError("* Please Enter valid Aadhaar Number!");
//                        edtPAN.setError(null);
                        documents = "1";
                    } else if (edtAadhaar.getText().toString().length() == 0 && edtPAN.getText().toString().length() == 0) {
                        pan = "";
//                        edtAadhaar.setError("* Please Enter Aadhaar or PAN Number!");
                        edtPAN.setError(null);
                        documents = "0";
                    }

                } else {
//True
                    if (edtPAN.getText().toString().length() > 0) {
                        aadhar = edtAadhaar.getText().toString();
                        edtAadhaar.setError(null);
//                        edtPAN.setError(null);
                        documents = "3";
                    } else {
                        aadhar = edtAadhaar.getText().toString();
                        edtAadhaar.setError(null);
//                        edtPAN.setError(null);
                        documents = "1";
                    }
                }

                if (LoanTabActivity.isKycEdit) {
                    chekAllFields();
                }
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
                        pan = "";
//                        edtAadhaar.setError(null);
//                        edtPAN.setError("* Please Enter Valid PAN number!");
                        documents = "3";
                    } else if (edtAadhaar.getText().toString().length() == 0 && edtPAN.getText().toString().length() > 0) {
                        pan = "";
                        edtAadhaar.setError(null);
//                        edtPAN.setError("* Please Enter Valid PAN number!");
                        documents = "2";
                    } else if (edtAadhaar.getText().toString().length() > 0 && edtPAN.getText().toString().length() == 0) {
                        pan = "";
                        edtPAN.setError(null);
                        documents = "1";
                    } else if (edtAadhaar.getText().toString().length() == 0 && edtPAN.getText().toString().length() == 0) {
                        pan = "";
                        edtAadhaar.setError(null);
//                        edtPAN.setError("* Please Enter Aadhaar or PAN Number!");
                        documents = "0";
                    }
                } else {
                    //Pan True
                    if (edtAadhaar.getText().toString().length() > 0) {
                        pan = edtPAN.getText().toString().trim();
//                        edtAadhaar.setError(null);
                        edtPAN.setError(null);
                        documents = "3";
                    } else {
                        pan = edtPAN.getText().toString().trim();
//                        edtAadhaar.setError(null);
                        edtPAN.setError(null);
                        documents = "2";
                    }
                }

                if (LoanTabActivity.isKycEdit) {
                    chekAllFields();
                }
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
                flatBuildingSociety = edtAddressbr.getText().toString();
                if (LoanTabActivity.isKycEdit) {
                    chekAllFields();
                }
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
                streetLocalityLandmark = edtLandmarkbr.getText().toString();
                if (LoanTabActivity.isKycEdit) {
                    chekAllFields();
                }
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
                    pincode = edtPincodeBr.getText().toString();
                    edtPincodeBr.setError(null);
                } else {
                    pincode = "";
                    edtPincodeBr.setError("* Please enter valid pincode !");
                }

                if (LoanTabActivity.isKycEdit) {
                    chekAllFields();
                }
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
                        courseFeeValueinint = Integer.parseInt(course_cost);

                        if (loanAmountvalueInInt > courseFeeValueinint) {
//                            edtLoanAmt.setError("Loan amount not exceed than course fees!");
                            requested_loan_amount = "";

                        } else {
                            if (loanAmountvalueInInt >= 5000) {
                                requested_loan_amount = edtLoanAmt.getText().toString();
                                edtLoanAmt.setError(null);
                            } else {
                                edtLoanAmt.setError("The Loan Amount must be greater than or equal to 5000.");
                                requested_loan_amount = "";
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

        linMaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "1";

                linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
                linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));

                txtMale.setTextColor(getResources().getColor(R.color.white));
                txtFemale.setTextColor(getResources().getColor(R.color.blue1));
                txtOther.setTextColor(getResources().getColor(R.color.blue1));

                iviewMale.setBackgroundColor(getResources().getColor(R.color.white));
                iviewFemale.setBackgroundColor(getResources().getColor(R.color.blue1));
                iviewOther.setBackgroundColor(getResources().getColor(R.color.blue1));

                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                    ivMale.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                    DrawableCompat.setTint(bg, getResources().getColor(R.color.white));
                }
                ivMale.setImageDrawable(bg);

                Drawable bg1;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                    ivFemale.setColorFilter(getResources().getColor(R.color.blue1), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                    DrawableCompat.setTint(bg1, getResources().getColor(R.color.blue1));
                }
                ivFemale.setImageDrawable(bg1);

                Drawable bg2;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                    ivOther.setColorFilter(getResources().getColor(R.color.blue1), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
                    DrawableCompat.setTint(bg2, getResources().getColor(R.color.blue1));
                }
                ivOther.setImageDrawable(bg2);
            }
        });

        linFemaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "2";

                linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
                linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));

                txtMale.setTextColor(getResources().getColor(R.color.blue1));
                txtFemale.setTextColor(getResources().getColor(R.color.white));
                txtOther.setTextColor(getResources().getColor(R.color.blue1));

                iviewMale.setBackgroundColor(getResources().getColor(R.color.blue1));
                iviewFemale.setBackgroundColor(getResources().getColor(R.color.white));
                iviewOther.setBackgroundColor(getResources().getColor(R.color.blue1));

                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                    ivMale.setColorFilter(getResources().getColor(R.color.blue1), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                    DrawableCompat.setTint(bg, getResources().getColor(R.color.blue1));
                }
                ivMale.setImageDrawable(bg);

                Drawable bg1;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                    ivFemale.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                    DrawableCompat.setTint(bg1, getResources().getColor(R.color.white));
                }
                ivFemale.setImageDrawable(bg1);

                Drawable bg2;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                    ivOther.setColorFilter(getResources().getColor(R.color.blue1), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
                    DrawableCompat.setTint(bg2, getResources().getColor(R.color.blue1));
                }
                ivOther.setImageDrawable(bg2);

            }
        });

        linOtherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "3";

                linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
                linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
                linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));

                txtMale.setTextColor(getResources().getColor(R.color.blue1));
                txtFemale.setTextColor(getResources().getColor(R.color.blue1));
                txtOther.setTextColor(getResources().getColor(R.color.white));

                iviewMale.setBackgroundColor(getResources().getColor(R.color.blue1));
                iviewFemale.setBackgroundColor(getResources().getColor(R.color.blue1));
                iviewOther.setBackgroundColor(getResources().getColor(R.color.white));

                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                    ivMale.setColorFilter(getResources().getColor(R.color.blue1), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                    DrawableCompat.setTint(bg, getResources().getColor(R.color.blue1));
                }
                ivMale.setImageDrawable(bg);

                Drawable bg1;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                    ivFemale.setColorFilter(getResources().getColor(R.color.blue1), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                    DrawableCompat.setTint(bg1, getResources().getColor(R.color.blue1));
                }
                ivFemale.setImageDrawable(bg1);

                Drawable bg2;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                    ivOther.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
                    DrawableCompat.setTint(bg2, getResources().getColor(R.color.white));
                }
                ivOther.setImageDrawable(bg2);
            }
        });

        switchMarital.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                maritalStatus = isChecked ? "1" : "2";
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
//    }

    public void chekAllFields() {

        if (firstName.equals("") || lastName.equals("") || email.equals("") ||
                mobile.equals("") || dob.equals("") || gender.equals("") ||
                maritalStatus.equals("")) {

            indicateValidationText(txtPersonalToggle, false);
            indicateValidationIcon(ivPersonalStatus, ivPersonalTitle, false);

            if (LoanTabActivity.isKycEdit) {
                txtPersonalDetailsErrMsg.setVisibility(VISIBLE);

                if (edtFnameBr.getText().toString().equals("")) {
                    txtPersonalDetailsErrMsg.setText("*Please enter first name");
                    // edtFnameBr.requestFocus();
                } else if (edtLnameBr.getText().toString().equals("")) {
                    txtPersonalDetailsErrMsg.setText("*Please enter last name");
                    //  edtLnameBr.requestFocus();
                } else if (gender.equals("")) {
                    txtPersonalDetailsErrMsg.setText("*Please enter gender");
                    //   linMaleBtn.requestFocus();
                } else if (dob.equals("")) {
                    txtPersonalDetailsErrMsg.setText("*Please enter DOB");
                    // txtDOB.requestFocus();
                } else if (maritalStatus.equals("")) {
                    txtPersonalDetailsErrMsg.setText("*Please enter maritalStatus");
                    //  txtMaritalStatus.requestFocus();
                } else if (email.equals("")) {
                    txtPersonalDetailsErrMsg.setText("*Please enter email");
                    //edtEmailIdBr.requestFocus();
                } else if (mobile.equals("")) {
                    txtPersonalDetailsErrMsg.setText("*Please enter mobile number");
                    // edtMobileNoBr.requestFocus();
                }
                linPersonalBlock.requestFocus();
                txtPersonalDetailsErrMsg.requestFocus();
            }
        } else {
            txtPersonalDetailsErrMsg.setVisibility(GONE);
            txtPersonalDetailsErrMsg.setText(null);
//            linPersonalBlock.startAnimation(collapseanimationPersonal);
            indicateValidationText(txtPersonalToggle, true);
            indicateValidationIcon(ivPersonalStatus, ivPersonalTitle, true);
        }

        if (instituteId.equals("") || instituteLocationId.equals("") ||
                courseId.equals("") || course_cost.equals("") ||
                requested_loan_amount.equals("")) {
            if (LoanTabActivity.isKycEdit) {
                txtCourseDetailsErrMsg.setVisibility(VISIBLE);

                if (instituteId.equals("")) {
                    txtCourseDetailsErrMsg.setText("* Please select Institue Name");
                    // spInsttLocation.requestFocus();
                } else if (course_cost.equals("")) {
                    txtCourseDetailsErrMsg.setText("* Please enter course fee");
                    //spCourse.requestFocus();
                } else if (requested_loan_amount.equals("")) {
                    txtCourseDetailsErrMsg.setText("* Please enter loan amount");
                    //edtLoanAmt.requestFocus();
                } else if (loanAmountvalueInInt > courseFeeValueinint) {
                    txtCourseDetailsErrMsg.setText("Loan amount not exceed than course fees!");
                    requested_loan_amount = "";
                    //edtLoanAmt.requestFocus();
                }
                txtCourseDetailsErrMsg.requestFocus();
            }

            indicateValidationText(txtCourseToggle, false);
            indicateValidationIcon(ivCourseStatus, ivCourseTitle, false);
        } else {
            txtCourseDetailsErrMsg.setText(null);
            txtCourseDetailsErrMsg.setVisibility(GONE);
//            relCourseBlock.startAnimation(collapseAnimationCourse);
            indicateValidationText(txtCourseToggle, true);
            indicateValidationIcon(ivCourseStatus, ivCourseTitle, true);
        }

        if (documents.equals("0") || documents.equals("")) {
            if (flatBuildingSociety.equals("") || streetLocalityLandmark.equals("") ||
                    pincode.length() < 6 || (aadhar.equals("") && pan.equals("")) ||
                    countryId.equals("") || stateId.equals("") || cityId.equals("")) {

                if (LoanTabActivity.isKycEdit) {
                    txtIdentityDetailsErrMsg.setVisibility(VISIBLE);

                    if (flatBuildingSociety.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter Flat No,Building Name,Society Name");
                        // edtAddressbr.requestFocus();

                    } else if (streetLocalityLandmark.equals("")) {

                        txtIdentityDetailsErrMsg.setText("* Please enter Street Name,Locality,LandMark");
                        //edtLandmarkbr.requestFocus();
                    } else if (pincode.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter pincode");
                        //edtPincodeBr.requestFocus();
                    } else if (aadhar.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter aadhar");
                        //edtAadhaar.requestFocus();
                    } else if (pan.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter pan");
                        //edtPAN.requestFocus();
                    } else if (countryId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter Country");
                        //spCountry.requestFocus();
                    } else if (stateId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter State");
                        //spState.requestFocus();
                    } else if (cityId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter City");
                        //spCity.requestFocus();
                    }
                    txtIdentityDetailsErrMsg.requestFocus();
                }

                indicateValidationText(txtIdentityToggle, false);
                indicateValidationIcon(ivIdentityStatus, ivIdentityTitle, false);
            } else {
                txtIdentityDetailsErrMsg.setText(null);
                txtIdentityDetailsErrMsg.setVisibility(GONE);
//                relIdentityBlock.startAnimation(collapseAnimationIdentity);
                indicateValidationText(txtIdentityToggle, true);
                indicateValidationIcon(ivIdentityStatus, ivIdentityTitle, true);
            }
        }
        if (documents.equals("1")) {
            if (flatBuildingSociety.equals("") || streetLocalityLandmark.equals("") ||
                    pincode.length() < 6 || aadhar.equals("") ||
                    !pan.equals("") || countryId.equals("") ||
                    stateId.equals("") || cityId.equals("")) {

                if (LoanTabActivity.isKycEdit) {
                    txtIdentityDetailsErrMsg.setVisibility(VISIBLE);

                    if (flatBuildingSociety.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter Flat No,Building Name,Society Name");
                        // edtAddressbr.requestFocus();
                    } else if (streetLocalityLandmark.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter Street Name,Locality,LandMark");
                        //edtLandmarkbr.requestFocus();
                    } else if (pincode.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter pincode");
                        //edtPincodeBr.requestFocus();
                    } else if (aadhar.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter aadhar");
                        //edtAadhaar.requestFocus();
                    } else if (countryId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter Country");
                        //spCountry.requestFocus();
                    } else if (stateId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter State");
                        //spState.requestFocus();
                    } else if (cityId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("* Please enter City");
                        //spCity.requestFocus();
                    }
                    txtIdentityDetailsErrMsg.requestFocus();

                }

                indicateValidationText(txtIdentityToggle, false);
                indicateValidationIcon(ivIdentityStatus, ivIdentityTitle, false);
            } else {
                txtIdentityDetailsErrMsg.setText(null);
                txtIdentityDetailsErrMsg.setVisibility(GONE);
//                relIdentityBlock.startAnimation(collapseAnimationIdentity);
                indicateValidationText(txtIdentityToggle, true);
                indicateValidationIcon(ivIdentityStatus, ivIdentityTitle, true);
            }
        } else if (documents.equals("2")) {
            if (flatBuildingSociety.equals("") || streetLocalityLandmark.equals("") ||
                    pincode.length() < 6 || !aadhar.equals("") ||
                    pan.equals("") || countryId.equals("") || stateId.equals("")
                    || cityId.equals("")) {

                if (LoanTabActivity.isKycEdit) {
                    txtIdentityDetailsErrMsg.setVisibility(VISIBLE);

                    if (flatBuildingSociety.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter Flat No,Building Name,Society Name");
                        // edtAddressbr.requestFocus();
                    } else if (streetLocalityLandmark.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter Street Name,Locality,LandMark");
                        //edtLandmarkbr.requestFocus();
                    } else if (pincode.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter pincode");
                        //edtPincodeBr.requestFocus();
                    } else if (pan.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter pan");
                        //edtPAN.requestFocus();
                    } else if (countryId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter Country");
                        //spCountry.requestFocus();
                    } else if (stateId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter State");
                        //spState.requestFocus();
                    } else if (cityId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter City");
                        //spCity.requestFocus();
                    }
                    txtIdentityDetailsErrMsg.requestFocus();
                }

                indicateValidationText(txtIdentityToggle, false);
                indicateValidationIcon(ivIdentityStatus, ivIdentityTitle, false);
            } else {
                txtIdentityDetailsErrMsg.setText(null);
                txtIdentityDetailsErrMsg.setVisibility(GONE);
                indicateValidationText(txtIdentityToggle, true);
                indicateValidationIcon(ivIdentityStatus, ivIdentityTitle, true);
            }
        } else if (documents.equals("3")) {
            if (flatBuildingSociety.equals("") || streetLocalityLandmark.equals("") ||
                    pincode.length() < 6 || aadhar.equals("") || pan.equals("") ||
                    countryId.equals("") || stateId.equals("") || cityId.equals("")) {

                if (LoanTabActivity.isKycEdit) {
                    txtIdentityDetailsErrMsg.setVisibility(VISIBLE);

                    if (flatBuildingSociety.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter Flat No,Building Name,Society Name");
                        //edtAddressbr.requestFocus();
                    } else if (streetLocalityLandmark.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter Street Name,Locality,LandMark");
                        // edtLandmarkbr.requestFocus();
                    } else if (pincode.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter pincode");
                        // edtPincodeBr.requestFocus();
                    } else if (aadhar.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter aadhar");
                        //edtAadhaar.requestFocus();
                    } else if (pan.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter pan");
                        //edtPAN.requestFocus();
                    } else if (countryId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter Country");
                        //spCountry.requestFocus();
                    } else if (stateId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter State");
                        //spState.requestFocus();
                    } else if (cityId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter City");
                        //spCity.requestFocus();
                    }
                    txtIdentityDetailsErrMsg.requestFocus();
                }
                indicateValidationText(txtIdentityToggle, false);
                indicateValidationIcon(ivIdentityStatus, ivIdentityTitle, false);
            } else {
                txtIdentityDetailsErrMsg.setText(null);
                txtIdentityDetailsErrMsg.setVisibility(GONE);
//                relIdentityBlock.startAnimation(collapseAnimationIdentity);
                indicateValidationText(txtIdentityToggle, true);
                indicateValidationIcon(ivIdentityStatus, ivIdentityTitle, true);
            }
        } else {
            if (flatBuildingSociety.equals("") || streetLocalityLandmark.equals("") || pincode.length() < 6 || (aadhar.equals("") && pan.equals("")) || countryId.equals("") || stateId.equals("") || cityId.equals("")) {

                if (LoanTabActivity.isKycEdit) {
                    txtIdentityDetailsErrMsg.setVisibility(VISIBLE);

                    if (flatBuildingSociety.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter Flat No,Building Name,Society Name");
                        //edtAddressbr.requestFocus();

                    } else if (streetLocalityLandmark.equals("")) {

                        txtIdentityDetailsErrMsg.setText("*Please enter Street Name,Locality,LandMark");
                        //edtLandmarkbr.requestFocus();
                    } else if (pincode.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter pincode");
                        //edtPincodeBr.requestFocus();
                    } else if (aadhar.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter aadhar");
                        // edtAadhaar.requestFocus();
                    } else if (pan.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter pan");
                        //edtPAN.requestFocus();
                    } else if (countryId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter Country");
                        //spCountry.requestFocus();
                    } else if (stateId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter State");
                        //spState.requestFocus();
                    } else if (cityId.equals("")) {
                        txtIdentityDetailsErrMsg.setText("*Please enter City");
                        //spCity.requestFocus();
                    }
                }
                indicateValidationText(txtIdentityToggle, false);
                indicateValidationIcon(ivIdentityStatus, ivIdentityTitle, false);
            } else {
                txtIdentityDetailsErrMsg.setText(null);
                txtIdentityDetailsErrMsg.setVisibility(GONE);
                indicateValidationText(txtIdentityToggle, true);
                indicateValidationIcon(ivIdentityStatus, ivIdentityTitle, true);
            }
        }

    }

    public static void validate() {
        if (firstName.equals("") || lastName.equals("") ||
                email.equals("") || mobile.equals("") ||
                dob.equals("") || gender.equals("") || (aadhar.equals("") && pan.equals("")) ||
                maritalStatus.equals("") || flatBuildingSociety.equals("")
                || streetLocalityLandmark.equals("") || pincode.equals("")
                || pincode.length() < 6 || countryId.equals("") ||
                stateId.equals("") || cityId.equals("") ||
                institute_name.equals("") || instituteLocationId.equals("")
                || courseId.equals("") || course_cost.equals("") ||
                requested_loan_amount.equals("") || (loanAmountvalueInInt > courseFeeValueinint)) {

            mListener.onFragmentInteraction(false, 0);
        } else {
            saveEditedKycData();
//            mListener.onFragmentInteraction(true, 1);
        }
    }

    public void indicateValidationIcon(ImageView ivStatus, ImageView ivTitle, boolean valid) {
//        if (valid) {
//            indicator.setCompoundDrawablesRelativeWithIntrinsicBounds(start, null, context.getResources().getDrawable(R.drawable.ic_check_circle_green), null);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                indicator.getCompoundDrawablesRelative()[0].setTint(context.getResources().getColor(R.color.colorGreen));
//                indicator.getCompoundDrawablesRelative()[2].setTint(context.getResources().getColor(R.color.colorGreen));
//            }
//            indicator.setTextColor(context.getResources().getColor(R.color.colorGreen));
//        } else {
//            indicator.setCompoundDrawablesRelativeWithIntrinsicBounds(start, null, context.getResources().getDrawable(R.drawable.ic_exclamation_circle), null);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                indicator.getCompoundDrawablesRelative()[0].setTint(context.getResources().getColor(R.color.blue1));
//                indicator.getCompoundDrawablesRelative()[2].setTint(context.getResources().getColor(R.color.new_red));
//            }
//            indicator.setTextColor(context.getResources().getColor(R.color.blue1));
//        }

        if (valid) {

            if (LoanTabActivity.isKycEdit) {
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
                    bg = VectorDrawableCompat.create(context.getResources(), R.drawable.ic_check_circle_green, null);
                    ivStatus.setColorFilter(context.getResources().getColor(R.color.defaulticoncolor), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(context, R.drawable.ic_check_circle_green);
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


        } else {
            if (LoanTabActivity.isKycEdit) {
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

    public void indicateValidationText(TextView indicator, boolean valid) {
        if (valid) {
            if (LoanTabActivity.isKycEdit) {
            indicator.setTextColor(context.getResources().getColor(R.color.colorGreen));
        } else {
                indicator.setTextColor(context.getResources().getColor(R.color.defaulticoncolor));
            }
        } else {
            if (LoanTabActivity.isKycEdit) {
            indicator.setTextColor(context.getResources().getColor(R.color.new_red));
            } else {
                indicator.setTextColor(context.getResources().getColor(R.color.defaulticoncolor));
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
        params.put("lead_id", MainActivity.lead_id);
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
                    application_id = jsonkycDetails.getString("application_id");
                    institute_name = jsonkycDetails.getString("institute_name");
                    location_name = jsonkycDetails.getString("location_name");
                    course_name = jsonkycDetails.getString("course_name");
                    course_cost = jsonkycDetails.getString("course_cost");
                    fk_institutes_id = jsonkycDetails.getString("fk_institutes_id");
                    fk_insitutes_location_id = jsonkycDetails.getString("fk_insitutes_location_id");
                    fk_course_id = jsonkycDetails.getString("fk_course_id");

//                    if (!LoanTabActivity.requested_loan_amount.equals("null")) {
//                        edtLoanAmt.setText(LoanTabActivity.requested_loan_amount);
//                    }

                    if (!institute_name.equals("null") && !institute_name.equals("")) {
                        acInstituteName.setText(institute_name);
                    }
                    if (!fk_institutes_id.equals("null") && !fk_institutes_id.equals("")) {
                        instituteId = instituteID = fk_institutes_id;
                        locationApiCall();
                    }

                    if (!fk_insitutes_location_id.equals("null") && !fk_insitutes_location_id.equals("")) {

                        instituteLocationId = locationID = fk_insitutes_location_id;

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

                    if (!fk_course_id.equals("null") && !fk_course_id.equals("")) {
                        courseId = courseID = fk_course_id;

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
                        requested_loan_amount = jsonkycDetails.getString("requested_loan_amount");
                        if (!requested_loan_amount.equals("") && !requested_loan_amount.equals("null")) {
                            edtLoanAmt.setText(requested_loan_amount);
                        }
                    }

                    if (!course_cost.equals("null")) {
                        txtCourseFee.setText(course_cost);
                        if (edtLoanAmt.getText().toString().length() > 0 && txtCourseFee.getText().toString().length() > 0) {
                            loanAmountvalueInInt = Integer.parseInt(edtLoanAmt.getText().toString());
                            courseFeeValueinint = Integer.parseInt(course_cost);

                            if (loanAmountvalueInInt > courseFeeValueinint) {
//                                edtLoanAmt.setError("Loan amount not exceed than course fees!");
                                requested_loan_amount = "";
                            } else {
                                requested_loan_amount = edtLoanAmt.getText().toString();
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

                    has_aadhar_pan = documents = jsonborrowerDetails.getString("has_aadhar_pan");

                    if (jsonborrowerDetails.getString("first_name") != null) {
                        firstName = jsonborrowerDetails.getString("first_name");
                        if (!firstName.equals("") && !firstName.equals("null")) {
                            edtFnameBr.setText(firstName);
                        }
                    }
                    if (jsonborrowerDetails.getString("applicant_id") != null) {
                        LoanTabActivity.applicant_id = applicant_id = jsonborrowerDetails.getString("applicant_id");
                    }

                    if (jsonborrowerDetails.getString("middle_name") != null) {
                        middleName = jsonborrowerDetails.getString("middle_name");
                        if (!middleName.equals("") && !middleName.equals("null")) {
                            edtMnameBr.setText(middleName);
                        }
                    }

                    if (jsonborrowerDetails.getString("last_name") != null) {
                        lastName = jsonborrowerDetails.getString("last_name");
                        if (!lastName.equals("") && !lastName.equals("null")) {
                            edtLnameBr.setText(lastName);
                        }
                    }

                    if (jsonborrowerDetails.getString("email_id") != null) {
                        email = jsonborrowerDetails.getString("email_id");
                        if (!email.equals("") && !email.equals("null")) {
                            edtEmailIdBr.setText(email);
                        }
                    }

                    if (jsonborrowerDetails.getString("mobile_number") != null) {
                        mobile = jsonborrowerDetails.getString("mobile_number");
                        if (!mobile.equals("") && !mobile.equals("null")) {
                            edtMobileNoBr.setText(mobile);
                        }
                    }

                    if (jsonborrowerDetails.getString("dob") != null) {
                        dob = jsonborrowerDetails.getString("dob");
                        if (!dob.equals("") && !dob.equals("null")) {
                            txtDOB.setText(dob);
                        }
                    }

                    if (jsonborrowerDetails.getString("gender_id") != null) {
                        gender = jsonborrowerDetails.getString("gender_id");
                        if (!gender.equals("") && !gender.equals("null")) {
                            if (gender.equals("1")) {

                                linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
                                linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                                linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));

                                txtMale.setTextColor(getResources().getColor(R.color.white));
                                txtFemale.setTextColor(getResources().getColor(R.color.textcolordark));
                                txtOther.setTextColor(getResources().getColor(R.color.textcolordark));

                                iviewMale.setBackgroundColor(getResources().getColor(R.color.white));
                                iviewFemale.setBackgroundColor(getResources().getColor(R.color.textcolordark));
                                iviewOther.setBackgroundColor(getResources().getColor(R.color.textcolordark));

                                Drawable bg;
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                                    ivMale.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                                } else {
                                    bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                                    DrawableCompat.setTint(bg, getResources().getColor(R.color.white));
                                }
                                ivMale.setImageDrawable(bg);

                                Drawable bg1;
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                                    ivFemale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                                } else {
                                    bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                                    DrawableCompat.setTint(bg1, getResources().getColor(R.color.darkblue));
                                }
                                ivFemale.setImageDrawable(bg1);

                                Drawable bg2;
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                                    ivOther.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                                } else {
                                    bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
                                    DrawableCompat.setTint(bg2, getResources().getColor(R.color.darkblue));
                                }
                                ivOther.setImageDrawable(bg2);
                            } else if (gender.equals("2")) {

                                linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                                linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
                                linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));

                                txtMale.setTextColor(getResources().getColor(R.color.textcolordark));
                                txtFemale.setTextColor(getResources().getColor(R.color.white));
                                txtOther.setTextColor(getResources().getColor(R.color.textcolordark));

                                iviewMale.setBackgroundColor(getResources().getColor(R.color.textcolordark));
                                iviewFemale.setBackgroundColor(getResources().getColor(R.color.white));
                                iviewOther.setBackgroundColor(getResources().getColor(R.color.textcolordark));

                                Drawable bg;
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                                    ivMale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                                } else {
                                    bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                                    DrawableCompat.setTint(bg, getResources().getColor(R.color.darkblue));
                                }
                                ivMale.setImageDrawable(bg);

                                Drawable bg1;
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                                    ivFemale.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                                } else {
                                    bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                                    DrawableCompat.setTint(bg1, getResources().getColor(R.color.white));
                                }
                                ivFemale.setImageDrawable(bg1);

                                Drawable bg2;
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                                    ivOther.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                                } else {
                                    bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
                                    DrawableCompat.setTint(bg2, getResources().getColor(R.color.darkblue));
                                }
                                ivOther.setImageDrawable(bg2);
                            } else if (gender.equals("3")) {
                                linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                                linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                                linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));

                                txtMale.setTextColor(getResources().getColor(R.color.textcolordark));
                                txtFemale.setTextColor(getResources().getColor(R.color.textcolordark));
                                txtOther.setTextColor(getResources().getColor(R.color.white));

                                iviewMale.setBackgroundColor(getResources().getColor(R.color.blue1));
                                iviewFemale.setBackgroundColor(getResources().getColor(R.color.blue1));
                                iviewOther.setBackgroundColor(getResources().getColor(R.color.white));

                                Drawable bg;
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                                    ivMale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                                } else {
                                    bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                                    DrawableCompat.setTint(bg, getResources().getColor(R.color.darkblue));
                                }
                                ivMale.setImageDrawable(bg);

                                Drawable bg1;
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                                    ivFemale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                                } else {
                                    bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                                    DrawableCompat.setTint(bg1, getResources().getColor(R.color.darkblue));
                                }
                                ivFemale.setImageDrawable(bg1);

                                Drawable bg2;
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                                    bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                                    ivOther.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                                } else {
                                    bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
                                    DrawableCompat.setTint(bg2, getResources().getColor(R.color.white));
                                }
                                ivOther.setImageDrawable(bg2);
                            }
                        }
                    }

                    if (jsonborrowerDetails.getString("marital_status") != null) {
                        if (!jsonborrowerDetails.getString("marital_status").equals("") && !jsonborrowerDetails.getString("marital_status").equals("null")) {
                            maritalStatus = jsonborrowerDetails.getString("marital_status");
                            if (maritalStatus.equals("1")) {
                                txtMaritalStatus.setText("Married");
                                switchMarital.setChecked(true);
                            } else if (maritalStatus.equals("2")) {
                                txtMaritalStatus.setText("Unmarried");
                                switchMarital.setChecked(false);
                            }
                        } else {
                            maritalStatus = "2";
                            switchMarital.setChecked(false);
                        }
                    }

                    if (jsonborrowerDetails.getString("aadhar_number") != null) {
                        aadhar = jsonborrowerDetails.getString("aadhar_number");
                        if (!aadhar.equals("") && !aadhar.equals("null")) {
                            edtAadhaar.setText(aadhar);
                        }
                    }

                    if (jsonborrowerDetails.getString("pan_number") != null) {
                        pan = jsonborrowerDetails.getString("pan_number");
                        if (!pan.equals("") && !pan.equals("null")) {
                            edtPAN.setText(pan);
                        }
                    }

                   /* if (jsonborrowerDetails.getString("current_address") != null) {
                        flatBuildingSociety = jsonborrowerDetails.getString("current_address");
                        if (!flatBuildingSociety.equals("") && !flatBuildingSociety.equals("null")) {
                            edtAddressbr.setText(flatBuildingSociety);
                        }
                    }*/

                    if (jsonborrowerDetails.getString("kyc_address") != null) {
                        flatBuildingSociety = jsonborrowerDetails.getString("kyc_address");
                        if (!flatBuildingSociety.equals("") && !flatBuildingSociety.equals("null")) {
                            edtAddressbr.setText(flatBuildingSociety);
                    }
                    }

                   /* if (jsonborrowerDetails.getString("current_landmark") != null) {
                        streetLocalityLandmark = jsonborrowerDetails.getString("current_landmark");
                        if (!streetLocalityLandmark.equals("") && !streetLocalityLandmark.equals("null")) {
                            edtLandmarkbr.setText(streetLocalityLandmark);
                        }
                    }*/


                    if (jsonborrowerDetails.getString("kyc_landmark") != null) {
                        streetLocalityLandmark = jsonborrowerDetails.getString("kyc_landmark");
                        if (!streetLocalityLandmark.equals("") && !streetLocalityLandmark.equals("null")) {
                            edtLandmarkbr.setText(streetLocalityLandmark);
                    }
                    }

                  /*  if (jsonborrowerDetails.getString("current_address_pin") != null) {
                        pincode = jsonborrowerDetails.getString("current_address_pin");
                        if (!pincode.equals("") && !pincode.equals("null")) {
                            edtPincodeBr.setText(pincode);
                        }
                    }
*/
                    if (jsonborrowerDetails.getString("kyc_address_pin") != null) {
                        pincode = jsonborrowerDetails.getString("kyc_address_pin");
                        if (!pincode.equals("") && !pincode.equals("null")) {
                            edtPincodeBr.setText(pincode);
                        }
                    }


              /*      if (jsonborrowerDetails.getString("current_address_country") != null) {
                        countryId = jsonborrowerDetails.getString("current_address_country");

                        try {
                            if (!countryId.equals("") && !countryId.equals("null")) {
                                try {
                                    currentcountryID = countryId;

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
                    }*/


                    if (jsonborrowerDetails.getString("kyc_address_country") != null) {
                        countryId = jsonborrowerDetails.getString("kyc_address_country");

                        try {
                            if (!countryId.equals("") && !countryId.equals("null")) {
                                try {
                                    currentcountryID = countryId;

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

                   /* if (jsonborrowerDetails.getString("current_address_state") != null) {
                        stateId = jsonborrowerDetails.getString("current_address_state");

                        try {
                            if (!stateId.equals("") && !stateId.equals("null")) {
                                currentstateID = stateId;
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
                    }*/


                    if (jsonborrowerDetails.getString("kyc_address_state") != null) {
                        stateId = jsonborrowerDetails.getString("kyc_address_state");

                        try {
                            if (!stateId.equals("") && !stateId.equals("null")) {
                                currentstateID = stateId;
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
                 /*   if (jsonborrowerDetails.getString("current_address_city") != null) {
                        cityId = jsonborrowerDetails.getString("current_address_city");

                        try {
                            if (!cityId.equals("") && !cityId.equals("null")) {
                                currentcityID = cityId;
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
                    }*/



                    if (jsonborrowerDetails.getString("kyc_address_city") != null) {
                        cityId = jsonborrowerDetails.getString("kyc_address_city");

                        try {
                            if (!cityId.equals("") && !cityId.equals("null")) {
                                currentcityID = cityId;
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
                lead_status = jsonleadStatus.getString("lead_status");
                lead_sub_status = jsonleadStatus.getString("lead_sub_status");
                current_stage = jsonleadStatus.getString("current_stage");
                current_status = jsonleadStatus.getString("current_status");
            }

            if (lead_status.equals("1") && current_stage.equals("1")) {
            } else {
                linEditKycDetail.setVisibility(GONE);
            }

//            if(LoanTabActivity.isKycEdit) {
//                        chekAllFields();
//                    }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectgendercolourGrey(){


        if (gender.equals("1")) {

            linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
            linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));

            txtMale.setTextColor(getResources().getColor(R.color.white));
            txtFemale.setTextColor(getResources().getColor(R.color.textcolordark));
            txtOther.setTextColor(getResources().getColor(R.color.textcolordark));

            iviewMale.setBackgroundColor(getResources().getColor(R.color.white));
            iviewFemale.setBackgroundColor(getResources().getColor(R.color.textcolordark));
            iviewOther.setBackgroundColor(getResources().getColor(R.color.textcolordark));

            Drawable bg;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                ivMale.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
            } else {
                bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                DrawableCompat.setTint(bg, getResources().getColor(R.color.white));
            }
            ivMale.setImageDrawable(bg);

            Drawable bg1;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                ivFemale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
            } else {
                bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                DrawableCompat.setTint(bg1, getResources().getColor(R.color.darkblue));
            }
            ivFemale.setImageDrawable(bg1);

            Drawable bg2;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                ivOther.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
            } else {
                bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
                DrawableCompat.setTint(bg2, getResources().getColor(R.color.darkblue));
            }
            ivOther.setImageDrawable(bg2);
        } else if (gender.equals("2")) {

            linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
            linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));

            txtMale.setTextColor(getResources().getColor(R.color.textcolordark));
            txtFemale.setTextColor(getResources().getColor(R.color.white));
            txtOther.setTextColor(getResources().getColor(R.color.textcolordark));

            iviewMale.setBackgroundColor(getResources().getColor(R.color.textcolordark));
            iviewFemale.setBackgroundColor(getResources().getColor(R.color.white));
            iviewOther.setBackgroundColor(getResources().getColor(R.color.textcolordark));

            Drawable bg;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                ivMale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
            } else {
                bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                DrawableCompat.setTint(bg, getResources().getColor(R.color.darkblue));
            }
            ivMale.setImageDrawable(bg);

            Drawable bg1;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                ivFemale.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
            } else {
                bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                DrawableCompat.setTint(bg1, getResources().getColor(R.color.white));
            }
            ivFemale.setImageDrawable(bg1);

            Drawable bg2;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                ivOther.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
            } else {
                bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
                DrawableCompat.setTint(bg2, getResources().getColor(R.color.darkblue));
            }
            ivOther.setImageDrawable(bg2);
        } else if (gender.equals("3")) {
            linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));

            txtMale.setTextColor(getResources().getColor(R.color.textcolordark));
            txtFemale.setTextColor(getResources().getColor(R.color.textcolordark));
            txtOther.setTextColor(getResources().getColor(R.color.white));

            iviewMale.setBackgroundColor(getResources().getColor(R.color.blue1));
            iviewFemale.setBackgroundColor(getResources().getColor(R.color.blue1));
            iviewOther.setBackgroundColor(getResources().getColor(R.color.white));

            Drawable bg;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                ivMale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
            } else {
                bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                DrawableCompat.setTint(bg, getResources().getColor(R.color.darkblue));
            }
            ivMale.setImageDrawable(bg);

            Drawable bg1;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                ivFemale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
            } else {
                bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                DrawableCompat.setTint(bg1, getResources().getColor(R.color.darkblue));
            }
            ivFemale.setImageDrawable(bg1);

            Drawable bg2;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                ivOther.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
            } else {
                bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
                DrawableCompat.setTint(bg2, getResources().getColor(R.color.white));
            }
            ivOther.setImageDrawable(bg2);



        }

        //DOB

        linDob.setBackground(getResources().getDrawable(R.drawable.border_circular));

        //Marital status
        linMaritalStatus.setBackground(getResources().getDrawable(R.drawable.border_circular));


    }


    public void selectgendercolourBlue(){


        if (gender.equals("1")) {

            linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
            linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
            linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));

            txtMale.setTextColor(getResources().getColor(R.color.white));
            txtFemale.setTextColor(getResources().getColor(R.color.blue1));
            txtOther.setTextColor(getResources().getColor(R.color.blue1));

            iviewMale.setBackgroundColor(getResources().getColor(R.color.white));
            iviewFemale.setBackgroundColor(getResources().getColor(R.color.blue1));
            iviewOther.setBackgroundColor(getResources().getColor(R.color.blue1));

            Drawable bg;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                ivMale.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
            } else {
                bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                DrawableCompat.setTint(bg, getResources().getColor(R.color.white));
            }
            ivMale.setImageDrawable(bg);

            Drawable bg1;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                ivFemale.setColorFilter(getResources().getColor(R.color.blue1), PorterDuff.Mode.MULTIPLY);
            } else {
                bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                DrawableCompat.setTint(bg1, getResources().getColor(R.color.blue1));
            }
            ivFemale.setImageDrawable(bg1);

            Drawable bg2;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                ivOther.setColorFilter(getResources().getColor(R.color.blue1), PorterDuff.Mode.MULTIPLY);
            } else {
                bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
                DrawableCompat.setTint(bg2, getResources().getColor(R.color.blue1));
            }
            ivOther.setImageDrawable(bg2);
        } else if (gender.equals("2")) {

            linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
            linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
            linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));

            txtMale.setTextColor(getResources().getColor(R.color.blue1));
            txtFemale.setTextColor(getResources().getColor(R.color.white));
            txtOther.setTextColor(getResources().getColor(R.color.blue1));

            iviewMale.setBackgroundColor(getResources().getColor(R.color.blue1));
            iviewFemale.setBackgroundColor(getResources().getColor(R.color.white));
            iviewOther.setBackgroundColor(getResources().getColor(R.color.blue1));

            Drawable bg;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                ivMale.setColorFilter(getResources().getColor(R.color.blue1), PorterDuff.Mode.MULTIPLY);
            } else {
                bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                DrawableCompat.setTint(bg, getResources().getColor(R.color.blue1));
            }
            ivMale.setImageDrawable(bg);

            Drawable bg1;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                ivFemale.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
            } else {
                bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                DrawableCompat.setTint(bg1, getResources().getColor(R.color.white));
            }
            ivFemale.setImageDrawable(bg1);

            Drawable bg2;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                ivOther.setColorFilter(getResources().getColor(R.color.blue1), PorterDuff.Mode.MULTIPLY);
            } else {
                bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
                DrawableCompat.setTint(bg2, getResources().getColor(R.color.blue1));
            }
            ivOther.setImageDrawable(bg2);
        } else if (gender.equals("3")) {
            linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
            linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
            linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));

            txtMale.setTextColor(getResources().getColor(R.color.blue1));
            txtFemale.setTextColor(getResources().getColor(R.color.blue1));
            txtOther.setTextColor(getResources().getColor(R.color.white));

            iviewMale.setBackgroundColor(getResources().getColor(R.color.blue1));
            iviewFemale.setBackgroundColor(getResources().getColor(R.color.blue1));
            iviewOther.setBackgroundColor(getResources().getColor(R.color.white));

            Drawable bg;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                ivMale.setColorFilter(getResources().getColor(R.color.blue1), PorterDuff.Mode.MULTIPLY);
            } else {
                bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                DrawableCompat.setTint(bg, getResources().getColor(R.color.blue1));
            }
            ivMale.setImageDrawable(bg);

            Drawable bg1;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                ivFemale.setColorFilter(getResources().getColor(R.color.blue1), PorterDuff.Mode.MULTIPLY);
            } else {
                bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                DrawableCompat.setTint(bg1, getResources().getColor(R.color.blue1));
            }
            ivFemale.setImageDrawable(bg1);

            Drawable bg2;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                ivOther.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
            } else {
                bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
                DrawableCompat.setTint(bg2, getResources().getColor(R.color.white));
            }
            ivOther.setImageDrawable(bg2);
        }

        //DOB

        linDob.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));

        //Marital status
        linMaritalStatus.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
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
                            instituteId = nameOfInsitituePOJOArrayList.get(i).instituteID;
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
            params.put("institute_id", instituteId);
            params.put("location_id", instituteLocationId);

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
            params.put("institute_id", instituteId);
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
            params.put("institute_id", instituteId);
            params.put("course_id", courseId);
            params.put("location_id", instituteLocationId);
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
                course_cost = jsonData.getString("result");

                if (edtLoanAmt.getText().toString().length() > 0) {
                    loanAmountvalueInInt = Integer.parseInt(edtLoanAmt.getText().toString());
                    courseFeeValueinint = Integer.parseInt(course_cost);

                    if (loanAmountvalueInInt > courseFeeValueinint) {
//                        edtLoanAmt.setError("Loan amount not exceed than course fees!");
                        requested_loan_amount = "";

                    } else {
                        requested_loan_amount = edtLoanAmt.getText().toString();
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

                if (!courseId.equals("")) {
                    int count = nameOfCoursePOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (nameOfCoursePOJOArrayList.get(i).courseID.equalsIgnoreCase(courseId)) {
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

                if (!instituteLocationId.equals("")) {

                    int count = locationPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (locationPOJOArrayList.get(i).locationID.equalsIgnoreCase(instituteLocationId)) {
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

    public interface OnFragmentInteracting {
        void onFragmentInteraction(boolean valid, int next);
    }

}
