package com.eduvanzapplication.newUI.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.model.CityModel;
import com.eduvanzapplication.newUI.model.CountryModel;
import com.eduvanzapplication.newUI.model.StateModel;
import com.eduvanzapplication.newUI.newViews.CourseDetailsActivity;
import com.eduvanzapplication.newUI.newViews.LoanTabActivity;
import com.eduvanzapplication.newUI.newViews.TenureSelectionActivity;
import com.eduvanzapplication.pqformfragments.pojo.LocationsPOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfCoursePOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfInsitituePOJO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class KycDetailFragment extends Fragment {
    static View view;
    public static Context context;
    public static Fragment mFragment;
    public static ProgressDialog progressDialog;
    public TextView txtPersonalToggle, txtIdentityToggle, txtCourseToggle;
    public LinearLayout linPersonalBlock, relIdentityBlock, relCourseBlock;
    public Animation expandAnimationPersonal, collapseanimationPersonal;
    public Animation expandAnimationIdentity, collapseAnimationIdentity;
    public Animation expanAnimationCourse, collapseAnimationCourse;
    public static Fragment fragment;

    public ArrayAdapter arrayAdapter_NameOfInsititue;
    public ArrayList<String> nameofinstitute_arrayList;
    public ArrayList<NameOfInsitituePOJO> nameOfInsitituePOJOArrayList;

    public ArrayAdapter arrayAdapter_NameOfCourse;
    public ArrayList<String> nameofcourse_arrayList;
    public ArrayList<NameOfCoursePOJO> nameOfCoursePOJOArrayList;
    public ArrayAdapter arrayAdapter_locations;
    public ArrayList<String> locations_arrayList;
    public ArrayList<LocationsPOJO> locationPOJOArrayList;

    public static ImageButton fabEditKycDetail, fabEdit;
    public static boolean isEdit = false;
    private Switch switchMarital;
    private TextView txtMaritalStatus;
    public static EditText edtFnameBr, edtMnameBr, edtLnameBr, edtEmailIdBr, edtMobileNoBr, edtAddressbr, edtLandmarkbr, edtPincodeBr;
    public static LinearLayout linMale, linFemale, linOther, linDob, linMaritalStatus;
    public static EditText edtAadhaar, edtPAN, edtLoanAmt;
    public static Spinner spCountry, spState, spCity, spInsttLocation, spCourse;
    public static AutoCompleteTextView acInstituteName;
    public static TextView txtCourseFee, txtDOB;

    public static String firstname;

    public static ArrayList<CountryModel> countryModelList = new ArrayList<>();
    public static ArrayList<String> countrList = new ArrayList<>();
    public static ArrayAdapter countryAdapter;

    public static ArrayList<String> stateList = new ArrayList<>();
    public static ArrayList<StateModel> stateModelList = new ArrayList<>();
    public static ArrayAdapter stateAdapter;

    public static ArrayList<CityModel> cityModelList = new ArrayList<>();
    public static ArrayList<String> cityList = new ArrayList<>();
    public static ArrayAdapter cityAdapter;

   public AnimationUtils animationUtils;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kycdetail_stepper, container, false);
        context = getContext();
        mFragment = new KycDetailFragment();

        progressDialog = new ProgressDialog(getActivity());

        expandAnimationPersonal = animationUtils.loadAnimation(context, R.anim.scale_expand);
        expandAnimationIdentity= animationUtils.loadAnimation(context, R.anim.scale_expand);
        expanAnimationCourse = animationUtils.loadAnimation(context, R.anim.scale_expand);
        collapseanimationPersonal = animationUtils.loadAnimation(context,R.anim.scale_collapse);
        collapseAnimationIdentity = animationUtils.loadAnimation(context,R.anim.scale_collapse);
        collapseAnimationCourse = animationUtils.loadAnimation(context,R.anim.scale_collapse);

//        expandAnimationPersonal = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_expand);
//        expandAnimationIdentity = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_expand);
//        expanAnimationCourse = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_expand);
//        collapseanimationPersonal = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_collapse);
//        collapseAnimationIdentity = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_collapse);
//        collapseAnimationCourse = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_collapse);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        txtPersonalToggle = view.findViewById(R.id.txtPersonalToggle);
        linPersonalBlock = view.findViewById(R.id.linPersonalBlock);
        txtIdentityToggle = view.findViewById(R.id.txtIdentityToggle);
        relIdentityBlock = view.findViewById(R.id.relIdentityBlock);
        txtCourseToggle = view.findViewById(R.id.txtCourseToggle);
        relCourseBlock = view.findViewById(R.id.relCourseBlock);
        fabEditKycDetail = view.findViewById(R.id.fabEditKycDetail);
        fabEdit = view.findViewById(R.id.fabEdit);
        edtFnameBr = view.findViewById(R.id.edtFnameBr);
        edtMnameBr = view.findViewById(R.id.edtMnameBr);
        edtLnameBr = view.findViewById(R.id.edtLnameBr);
        switchMarital = view.findViewById(R.id.switchMaritalStatus);
        txtMaritalStatus = view.findViewById(R.id.txtMaritalStatus);

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

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linPersonalBlock.startAnimation(expandAnimationPersonal);
        relIdentityBlock.startAnimation(collapseAnimationIdentity);
        relCourseBlock.startAnimation(collapseAnimationCourse);
        setViewsEnabled(false);
//        countryApiResponse(new JSONObject());  //temporary code

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEdit) {
                    if (!LoanTabActivity.firstName.equals("") && !LoanTabActivity.middleName.equals("") && !LoanTabActivity.lastName.equals("")
                            && !LoanTabActivity.flatBuildingSociety.equals("") && !LoanTabActivity.dob.equals("") && !LoanTabActivity.maritalStatus.equals("")
                            && !LoanTabActivity.email.equals("") && !LoanTabActivity.mobile.equals("") && !LoanTabActivity.streetLocalityLandmark.equals("")
                            && !LoanTabActivity.countryId.equals("") && !LoanTabActivity.stateId.equals("") && !LoanTabActivity.cityId.equals("")
                            && !LoanTabActivity.gender.equals("")) {


                    }
                }

            }
        });

        fabEditKycDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEdit) {
                    isEdit = true;
                    setViewsEnabled(true);
                    fabEditKycDetail.setImageResource(R.drawable.ic_save_white_16dp);
                    fabEditKycDetail.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    chekAllFields();
                } else {

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
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        /*=====================================end====================================================*/
        applyFieldsChangeListener();

        kycApiCall();
        countryApiCall();
        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < countrList.size(); i++) {
                    if (spCountry.getSelectedItem().toString().equals(countryModelList.get(i).getName())) {
                        LoanTabActivity.countryId = countryModelList.get(i).getId();
                        stateApiCall(LoanTabActivity.countryId);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < stateList.size(); i++) {
                    if (spState.getSelectedItem().toString().equals(stateModelList.get(i).getName())) {
                        LoanTabActivity.stateId = stateModelList.get(i).getId();
                        cityApiCall(LoanTabActivity.countryId, LoanTabActivity.stateId);
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("I_________D", "onItemClick: ");
                String text = spCourse.getSelectedItem().toString();
                int count = nameOfCoursePOJOArrayList.size();
                Log.e("TAG", "count: " + count);
                for (int i = 0; i < count; i++) {
                    if (nameOfCoursePOJOArrayList.get(i).courseName.equalsIgnoreCase(text)) {
                        LoanTabActivity.courseId = nameOfCoursePOJOArrayList.get(i).courseID;
                        courseFeeApiCall();
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spInsttLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = spInsttLocation.getSelectedItem().toString();
                int count = locationPOJOArrayList.size();
                for (int i = 0; i < count; i++) {
                    if (locationPOJOArrayList.get(i).locationName.equalsIgnoreCase(text)) {
                        LoanTabActivity.instituteLocationId = locationPOJOArrayList.get(i).locationID;
                        break;
                    }
                }
//                    courseFeeApiCall();
                courseApiCall();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void courseFeeApiCall() {
        /**API CALL**/
        try {
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String url = MainActivity.mainUrl + "pqform/apiPrefillSliderAmount";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", LoanTabActivity.instituteId);
            params.put("course_id", LoanTabActivity.courseId);
            params.put("location_id", LoanTabActivity.instituteLocationId);
            VolleyCall volleyCall = new VolleyCall();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                volleyCall.sendRequest(context, url, null, fragment, "courseFee", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void courseApiCall() {
        try {
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String url = MainActivity.mainUrl + "pqform/apiPrefillCourses";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", LoanTabActivity.instituteId);
            params.put("location_id", LoanTabActivity.instituteLocationId);

            VolleyCall volleyCall = new VolleyCall();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                volleyCall.sendRequest(context, url, null, fragment, "courseId", params, MainActivity.auth_token);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
        edtEmailIdBr.setEnabled(f);
        edtMobileNoBr.setEnabled(f);
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
                //               edtFnameBr.setText(firstname);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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
                LoanTabActivity.middleName = s.toString();
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
                LoanTabActivity.lastName = s.toString();
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
                LoanTabActivity.email = s.toString();
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
                LoanTabActivity.mobile = s.toString();
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
                LoanTabActivity.aadhar = s.toString();
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
                LoanTabActivity.pan = s.toString();
                chekAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        linMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanTabActivity.gender = "1";
                linMale.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linFemale.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linOther.setBackground(getResources().getDrawable(R.drawable.border_circular));
            }
        });

        linFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanTabActivity.gender = "2";
                linMale.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linFemale.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linOther.setBackground(getResources().getDrawable(R.drawable.border_circular));
            }
        });

        linOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanTabActivity.gender = "3";
                linMale.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linFemale.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linOther.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
            }
        });

        switchMarital.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LoanTabActivity.maritalStatus = isChecked ? "1" : "2";
                if (isChecked)
                    txtMaritalStatus.setText("Married");
                else
                    txtMaritalStatus.setText("Unmarried");
                chekAllFields();
            }
        });

    }

    public void chekAllFields() {
        if (LoanTabActivity.firstName.equals("") || LoanTabActivity.middleName.equals("") || LoanTabActivity.lastName.equals("")
                || LoanTabActivity.email.equals("") || LoanTabActivity.mobile.equals("") || LoanTabActivity.dob.equals("") || LoanTabActivity.gender.equals("")
                || LoanTabActivity.maritalStatus.equals("")) {
            indicateValidationText(txtPersonalToggle, getResources().getDrawable(R.drawable.ic_user_check), false);
        } else {
            indicateValidationText(txtPersonalToggle, getResources().getDrawable(R.drawable.ic_user_check), true);
        }
        if (LoanTabActivity.aadhar.equals("") || LoanTabActivity.pan.equals("")) {
            indicateValidationText(txtIdentityToggle, getResources().getDrawable(R.drawable.ic_address_card), false);
        } else {
            indicateValidationText(txtIdentityToggle, getResources().getDrawable(R.drawable.ic_address_card), true);
        }
        if (LoanTabActivity.flatBuildingSociety.equals("")
                || LoanTabActivity.streetLocalityLandmark.equals("") || LoanTabActivity.pincode.equals("") || LoanTabActivity.countryId.equals("")
                || LoanTabActivity.stateId.equals("") || LoanTabActivity.cityId.equals("")) {
            indicateValidationText(txtIdentityToggle, getResources().getDrawable(R.drawable.ic_address_card), false);
        } else {
            indicateValidationText(txtIdentityToggle, getResources().getDrawable(R.drawable.ic_address_card), true);
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
    public void onResume() {
        super.onResume();
        isEdit = false;

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
                volleyCall.sendRequest(context, url, null, KycDetailFragment.this, "getCountriesKyc", params, MainActivity.auth_token);
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
        countryModelList.clear();
        countrList.clear();

        countryModelList.add(new CountryModel("0", "Select"));
        countryModelList.add(new CountryModel("1", "India"));
        countryModelList.add(new CountryModel("2", "Germany"));
        countryModelList.add(new CountryModel("3", "Behrain"));

        countrList.add("Select");
        countrList.add("India");
        countrList.add("Germany");
        countrList.add("Behrain");

        ArrayAdapter countryAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, countrList);
        spCountry.setAdapter(countryAdapter);
        countryAdapter.notifyDataSetChanged();


    }

    private void stateApiCall(String countryId) {
        /**API CALL**/
        try {
            progressDialog.setMessage("Loading");
            progressDialog.show();
            progressDialog.setCancelable(false);
            String url = MainActivity.mainUrl + "algo/getStates";
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", countryId);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, KycDetailFragment.this, "getStatesKyc", params, MainActivity.auth_token);
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

    public void getStatesResponse(JSONObject jsonData) {
        progressDialog.dismiss();
        stateList.clear();
        stateModelList.clear();
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");
                JSONArray jsonArray3 = jsonObject.getJSONArray("states");
                StateModel stateModel = new StateModel();
                for (int i = 0; i < jsonArray3.length(); i++) {
                    JSONObject mJsonti = jsonArray3.getJSONObject(i);
                    stateModel = new StateModel(mJsonti.getString("state_id"), mJsonti.getString("state_name"));
                    stateModelList.add(stateModel);
                    stateList.add(mJsonti.getString("state_name"));
                }
                stateAdapter = new ArrayAdapter(context, R.layout.custom_layout_spinner, stateList);
                spState.setAdapter(stateAdapter);
                stateAdapter.notifyDataSetChanged();

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

    private void cityApiCall(String countryId, String stateId) {
        /**API CALL**/
        try {
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading");
            String url = MainActivity.mainUrl + "algo/getCities"; //http://159.89.204.41/eduvanzApi/algo/getCities
            Map<String, String> params = new HashMap<String, String>();
            params.put("countryId", countryId);//1
            params.put("stateId", stateId);//2
            if (!Globle.isNetworkAvailable(context)) {
                progressDialog.dismiss();
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, KycDetailFragment.this, "getCityKyc", params, MainActivity.auth_token);
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

    public void getCityResponse(JSONObject jsonData) {
        progressDialog.dismiss();
        cityList.clear();
        cityModelList.clear();
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");
                JSONArray jsonArray3 = jsonObject.getJSONArray("cities");
                CityModel cityModel = new CityModel();
                for (int i = 0; i < jsonArray3.length(); i++) {
                    JSONObject mJsonti = jsonArray3.getJSONObject(i);
                    cityModel = new CityModel(mJsonti.getString("city_id"), mJsonti.getString("city_name"));
                    cityModelList.add(cityModel);
                    cityList.add(mJsonti.getString("city_name"));
                }
                cityAdapter = new ArrayAdapter(context, R.layout.custom_layout_spinner, cityList);
                spCity.setAdapter(cityAdapter);
                cityAdapter.notifyDataSetChanged();


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


    public void kycApiCall() {
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
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
        progressDialog.dismiss();
        String message = jsonData.optString("message");
        try {
            if (jsonData.getInt("status") == 1) {
                if (jsonData.has("borrowerDetails") && jsonData.getJSONObject("borrowerDetails") != null) {
                    JSONObject jsonborrowerDetails = jsonData.getJSONObject("borrowerDetails");
                    if (jsonborrowerDetails.getString("first_name") != null) {
                        LoanTabActivity.firstName = jsonborrowerDetails.getString("first_name");
                        edtFnameBr.setText(LoanTabActivity.firstName);
                    }
                    if (jsonborrowerDetails.getString("middle_name") != null) {
                        LoanTabActivity.middleName = jsonborrowerDetails.getString("middle_name");
                        edtMnameBr.setText(LoanTabActivity.middleName);
                    }

                    if (jsonborrowerDetails.getString("last_name") != null) {
                        LoanTabActivity.lastName = jsonborrowerDetails.getString("last_name");
                        edtLnameBr.setText(LoanTabActivity.lastName);
                    }

                    if (jsonborrowerDetails.getString("email_id") != null) {
                        LoanTabActivity.email = jsonborrowerDetails.getString("email_id");
                        edtEmailIdBr.setText(LoanTabActivity.email);
                    }

                    if (jsonborrowerDetails.getString("mobile_number") != null) {
                        LoanTabActivity.mobile = jsonborrowerDetails.getString("mobile_number");
                        edtMobileNoBr.setText(LoanTabActivity.mobile);
                    }

                    if (jsonborrowerDetails.getString("dob") != null) {
                        LoanTabActivity.dob = jsonborrowerDetails.getString("dob");
                        txtDOB.setText(LoanTabActivity.dob);
                    }

                    if (jsonborrowerDetails.getString("gender_id") != null) {
                        LoanTabActivity.gender = jsonborrowerDetails.getString("gender_id");

                        if (LoanTabActivity.gender.equals("1")) {
                            linMale.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                            linFemale.setBackground(getResources().getDrawable(R.drawable.border_circular));
                            linOther.setBackground(getResources().getDrawable(R.drawable.border_circular));
                        } else if (LoanTabActivity.gender.equals("2")) {
                            linMale.setBackground(getResources().getDrawable(R.drawable.border_circular));
                            linFemale.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                            linOther.setBackground(getResources().getDrawable(R.drawable.border_circular));
                        } else if (LoanTabActivity.gender.equals("3")) {
                            linMale.setBackground(getResources().getDrawable(R.drawable.border_circular));
                            linFemale.setBackground(getResources().getDrawable(R.drawable.border_circular));
                            linOther.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                        }
                    }

                    if (jsonborrowerDetails.getString("marital_status") != null) {
                        LoanTabActivity.maritalStatus = jsonborrowerDetails.getString("marital_status");
                        if (LoanTabActivity.maritalStatus.equals("1")) {
                            txtMaritalStatus.setText("Married");
                            switchMarital.setChecked(true);
                        } else if (LoanTabActivity.maritalStatus.equals("2")) {
                            txtMaritalStatus.setText("Unmarried");
                            switchMarital.setChecked(false);
                        }
                    }

                    if (jsonborrowerDetails.getString("aadhar_number") != null) {
                        LoanTabActivity.aadhar = jsonborrowerDetails.getString("aadhar_number");
                        edtAadhaar.setText(LoanTabActivity.aadhar);
                    }

                    if (jsonborrowerDetails.getString("pan_number") != null) {
                        LoanTabActivity.pan = jsonborrowerDetails.getString("pan_number");
                        edtPAN.setText(LoanTabActivity.pan);
                    }

                    if (jsonborrowerDetails.getString("current_address") != null) {
                        LoanTabActivity.flatBuildingSociety = jsonborrowerDetails.getString("current_address");
                        edtAddressbr.setText(LoanTabActivity.flatBuildingSociety);
                    }
                    if (jsonborrowerDetails.getString("current_landmark") != null) {
                        LoanTabActivity.streetLocalityLandmark = jsonborrowerDetails.getString("current_landmark");
                        edtLandmarkbr.setText(LoanTabActivity.streetLocalityLandmark);
                    }
                    if (jsonborrowerDetails.getString("current_address_pin") != null) {
                        LoanTabActivity.pincode = jsonborrowerDetails.getString("current_address_pin");
                        edtPincodeBr.setText(LoanTabActivity.pincode);
                    }
                    if (jsonborrowerDetails.getString("current_address_country") != null) {
                        LoanTabActivity.countryId = jsonborrowerDetails.getString("current_address_country");
                        spCountry.setSelection(Integer.parseInt(LoanTabActivity.countryId));
                    }

                    if (jsonborrowerDetails.getString("current_address_state") != null) {
                        LoanTabActivity.stateId = jsonborrowerDetails.getString("current_address_state");
                        int count = stateModelList.size();

                        for (int i = 0; i < count; i++) {
                            if (stateModelList.get(i).getId().equalsIgnoreCase(LoanTabActivity.stateId)) {
                                spState.setSelection(i);
                                Log.d("another one", String.valueOf(i));
                            }
                        }
                    }
                    if (jsonborrowerDetails.getString("current_address_city") != null) {
                        LoanTabActivity.cityId = jsonborrowerDetails.getString("current_address_city");
                        int count = cityModelList.size();

                        for (int i = 0; i < count; i++) {
                            if (cityModelList.get(i).getId().equalsIgnoreCase(LoanTabActivity.cityId)) {
                                spCity.setSelection(i);
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setInstituteAdaptor() {

        try {

//            ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                    (this, android.R.layout.select_dialog_item, nameofinstitute_arrayList);

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

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void locationApiCall() {
        try {
            String url = MainActivity.mainUrl + "pqform/apiPrefillLocations";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", LoanTabActivity.instituteId);
            VolleyCall volleyCall = new VolleyCall();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                volleyCall.sendRequest(context, url, null, fragment, "locationName", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void instituteName(JSONObject jsonData) {
        try {
            progressDialog.dismiss();
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
            e.printStackTrace();
        }
    }

    public void courseName(JSONObject jsonData) {
        try {
            progressDialog.dismiss();
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
            e.printStackTrace();
        }
    }

    public void courseFee(JSONObject jsonData) {
        try {
            progressDialog.dismiss();
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                txtCourseFee.setText(jsonData.getString("result"));
                LoanTabActivity.courseFee = jsonData.getString("result");
            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setCourseAdaptor() {

        try {
            arrayAdapter_NameOfCourse = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofcourse_arrayList);
            spCourse.setAdapter(arrayAdapter_NameOfCourse);
            arrayAdapter_NameOfCourse.notifyDataSetChanged();

            if (!LoanTabActivity.courseId.equals("")) {
                for (int i = 0; i < nameOfCoursePOJOArrayList.size(); i++) {
                    if (LoanTabActivity.courseId.equalsIgnoreCase(nameOfCoursePOJOArrayList.get(i).courseID)) {
                        spCourse.setSelection(i);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void locationName(JSONObject jsonData) {
        try {
            progressDialog.dismiss();
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
            e.printStackTrace();
        }
    }

    public void setInstituteLocationAdaptor() {

        try {
            arrayAdapter_locations = new ArrayAdapter(context, R.layout.custom_layout_spinner, locations_arrayList);
            spInsttLocation.setAdapter(arrayAdapter_locations);
            arrayAdapter_locations.notifyDataSetChanged();

            if (!LoanTabActivity.instituteLocationId.equals("")) {
                for (int i = 0; i < locationPOJOArrayList.size(); i++) {
                    if (LoanTabActivity.instituteLocationId.equalsIgnoreCase(locationPOJOArrayList.get(i).locationID)) {
                        spInsttLocation.setSelection(i);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void saveInstituteData() {
        /** API CALL GET OTP**/
        try {//auth_token
            String url = MainActivity.mainUrl + "dashboard/saveInstitute";
            Map<String, String> params = new HashMap<String, String>();

            params.put("lead_id", LoanTabActivity.lead_id);
            params.put("applicant_id", MainActivity.applicant_id);
            params.put("institute", LoanTabActivity.instituteId);
            params.put("course_name", LoanTabActivity.courseId);
            params.put("location", LoanTabActivity.instituteLocationId);
            params.put("loanAmount", edtLoanAmt.getText().toString().trim());

            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(getActivity(), url, null, fragment, "saveInstitute", params, MainActivity.auth_token);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setsaveInstitute(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (jsonData.getInt("status") == 1) {

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

//                EligibilityCheckFragment_6 eligibilityCheckFragment_6 = new EligibilityCheckFragment_6();
//                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_6).commit();

            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //	private VerticalStepperItemView mSteppers[] = new VerticalStepperItemView[3];
//	private Button btnNextKycDetail0, btnNextKycDetail1, btnPreviousKycDetail1, btnNextKycDetail2, btnPreviousKycDetail2;
//
//    private int mActivatedColorRes = R.color.material_blue_500;
//	private int mDoneIconRes = R.drawable.ic_done_white_16dp;
//
//    static View view;
//
//	public static Context context;
//	public static Fragment mFragment;
//    public static RelativeLayout relborrower;
//    public static LinearLayout linBorrowerForm;
//	public static TextView txtBorrowerArrowKey;
//	public static int borrowerVisiblity = 0;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
//
//		view = inflater.inflate(R.layout.fragment_kycdetail_stepper, parent, false);
//
//        linBorrowerForm = (LinearLayout) view.findViewById(R.id.linBorrowerForm);
//
//        relborrower = (RelativeLayout) view.findViewById(R.id.relborrower);
//
//        txtBorrowerArrowKey = (TextView) view.findViewById(R.id.txtBorrowerArrowKey);
//
//		context = getContext();
//		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
////		if (borrowerVisiblity == 0) {
////			linBorrowerForm.setVisibility(View.VISIBLE);
////			borrowerVisiblity = 1;
////			txtBorrowerArrowKey.setText(getResources().getString(R.string.up));
////		} else if (borrowerVisiblity == 1) {
////			linBorrowerForm.setVisibility(View.GONE);
////			borrowerVisiblity = 0;
////			txtBorrowerArrowKey.setText(getResources().getString(R.string.down));
////		}
////
////
////		relborrower.setOnClickListener(new View.OnClickListener() {
////			@Override
////			public void onClick(View v) {
////				if (borrowerVisiblity == 0) {
////					linBorrowerForm.setVisibility(View.VISIBLE);
////					borrowerVisiblity = 1;
////					txtBorrowerArrowKey.setText(getResources().getString(R.string.up));
////				} else if (borrowerVisiblity == 1) {
////					linBorrowerForm.setVisibility(View.GONE);
////					borrowerVisiblity = 0;
////					txtBorrowerArrowKey.setText(getResources().getString(R.string.down));
////				}
////
////			}
////		});
//
//        return view;
//    }
//
//	@Override
//	public void onViewCreated(View view, Bundle savedInstanceState) {
//
//	    mSteppers[0] = view.findViewById(R.id.stepperKyc0);
//		mSteppers[1] = view.findViewById(R.id.stepperKyc1);
//		mSteppers[2] = view.findViewById(R.id.stepperKyc2);
//
//		VerticalStepperItemView.bindSteppers(mSteppers);
//
//		btnNextKycDetail0 = view.findViewById(R.id.btnNextKycDetail0);
//		btnNextKycDetail0.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[0].nextStep();
//			}
//		});
//
//		mSteppers[0].setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[0].nextStep();
//			}
//		});
//
//		mSteppers[1].setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[0].nextStep();
//			}
//		});
//
//		mSteppers[2].setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[1].nextStep();
//			}
//		});
//
//		view.findViewById(R.id.button_test_error).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				if (mSteppers[0].getErrorText() != null) {
//					mSteppers[0].setErrorText(null);
//				} else {
//					mSteppers[0].setErrorText("Test error!");
//				}
//			}
//		});
//
//		btnPreviousKycDetail1 = view.findViewById(R.id.btnPreviousKycDetail1);
//		btnPreviousKycDetail1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[1].prevStep();
//			}
//		});
//
//		btnNextKycDetail1 = view.findViewById(R.id.btnNextKycDetail1);
//		btnNextKycDetail1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[1].nextStep();
//			}
//		});
//
//		btnPreviousKycDetail2 = view.findViewById(R.id.btnPreviousKycDetail2);
//		btnPreviousKycDetail2.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[2].prevStep();
//			}
//		});
//
//		btnNextKycDetail2 = view.findViewById(R.id.btnNextKycDetail2);
//		btnNextKycDetail2.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Snackbar.make(view, "Finish!", Snackbar.LENGTH_LONG).show();
//			}
//		});
//
//
//		view.findViewById(R.id.btn_change_point_color).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				if (mActivatedColorRes == R.color.material_blue_500) {
//					mActivatedColorRes = R.color.material_deep_purple_500;
//				} else {
//					mActivatedColorRes = R.color.material_blue_500;
//				}
//				for (VerticalStepperItemView stepper : mSteppers) {
//					stepper.setActivatedColorResource(mActivatedColorRes);
//				}
//			}
//		});
//		view.findViewById(R.id.btn_change_done_icon).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				if (mDoneIconRes == R.drawable.ic_done_white_16dp) {
//					mDoneIconRes = R.drawable.ic_save_white_16dp;
//				} else {
//					mDoneIconRes = R.drawable.ic_done_white_16dp;
//				}
//				for (VerticalStepperItemView stepper : mSteppers) {
//					stepper.setDoneIconResource(mDoneIconRes);
//				}
//			}
//		});
//	}


}
