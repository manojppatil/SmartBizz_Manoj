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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.model.CityModel;
import com.eduvanzapplication.newUI.model.CountryModel;
import com.eduvanzapplication.newUI.model.StateModel;
import com.eduvanzapplication.newUI.newViews.LoanTabActivity;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class KycDetailFragment extends Fragment {
    static View view;
    public static Context context;
    public static ProgressDialog progressDialog;
    public static TextView txtPersonalToggle, txtIdentityToggle, txtCourseToggle;
    public static LinearLayout linPersonalBlock,relIdentityBlock, relCourseBlock;
    public static Animation expandAnimationPersonal, collapseanimationPersonal;
    public static Animation expandAnimationIdentity, collapseAnimationIdentity;
    public static Animation expanAnimationCourse, collapseAnimationCourse;
    public static FloatingActionButton fabEdit;
    public static boolean isEdit = false;

    public static TextInputLayout tilFirstName, tilMiddleName, tilLastName, tilEmail, tilMobile, tilFlat, tilStreet, tilPincode;
    public static LinearLayout linMale,linFemale,linOther, linDob, linMaritalStatus;
    public static EditText edtAadhaar, edtPAN, edtLoanAmt;
    public static Spinner spCountry, spState, spCity, spInsttLocation, spCourse;
    public static AutoCompleteTextView acInstituteName;
    public static TextView txtCourseFee;

    public static ArrayList<CountryModel> countryModelList = new ArrayList<>();
    public static ArrayList<String> countrList  = new ArrayList<>();
    public static ArrayAdapter countryAdapter;

    public static ArrayList<String> stateList  = new ArrayList<>();
    public static ArrayList<StateModel> stateModelList = new ArrayList<>();
    public static ArrayAdapter stateAdapter;

    public static ArrayList<CityModel> cityModelList = new ArrayList<>();
    public static ArrayList<String> cityList  = new ArrayList<>();
    public static ArrayAdapter cityAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kycdetail_stepper, container, false);
        context = getContext();
        progressDialog = new ProgressDialog(getActivity());
        expandAnimationPersonal = AnimationUtils.loadAnimation(context, R.anim.scale_expand);
        expandAnimationIdentity= AnimationUtils.loadAnimation(context, R.anim.scale_expand);
        expanAnimationCourse = AnimationUtils.loadAnimation(context, R.anim.scale_expand);
        collapseanimationPersonal = AnimationUtils.loadAnimation(context,R.anim.scale_collapse);
        collapseAnimationIdentity = AnimationUtils.loadAnimation(context,R.anim.scale_collapse);
        collapseAnimationCourse = AnimationUtils.loadAnimation(context,R.anim.scale_collapse);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        txtPersonalToggle = view.findViewById(R.id.txtPersonalToggle);
        linPersonalBlock = view.findViewById(R.id.linPersonalBlock);
        txtIdentityToggle = view.findViewById(R.id.txtIdentityToggle);
        relIdentityBlock = view.findViewById(R.id.relIdentityBlock);
        txtCourseToggle = view.findViewById(R.id.txtCourseToggle);
        relCourseBlock = view.findViewById(R.id.relCourseBlock);
        fabEdit = view.findViewById(R.id.fabEdit);
        tilFirstName = view.findViewById(R.id.tilFirstName);
        tilMiddleName = view.findViewById(R.id.tilMiddleName);
        tilLastName = view.findViewById(R.id.tilLastName);

        tilEmail = view.findViewById(R.id.tilEmail);
        tilMobile = view.findViewById(R.id.tilMobile);
        tilFlat = view.findViewById(R.id.tilFlat);
        tilStreet = view.findViewById(R.id.tilStreet);
        tilPincode = view.findViewById(R.id.tilPincode);
        linMale = view.findViewById(R.id.linMale);
        linFemale = view.findViewById(R.id.linFemale);
        linOther = view.findViewById(R.id.linOther);
        linDob = view.findViewById(R.id.linDob);
        linMaritalStatus = view.findViewById(R.id.linMaritalStatus);
        edtAadhaar = view.findViewById(R.id.edtAadhaar);
        edtPAN = view.findViewById(R.id.edtPAN);
        edtLoanAmt = view.findViewById(R.id.edtLoanAmt);
        spCountry = view.findViewById(R.id.spCountry);
        spState = view.findViewById(R.id.spState);
        spCity = view.findViewById(R.id.spCity);
        spInsttLocation = view.findViewById(R.id.spInsttLocation);
        spCourse = view.findViewById(R.id.spCourse);
        acInstituteName = view.findViewById(R.id.acInstituteName);
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
                if (!isEdit){
                    isEdit = true;
                    setViewsEnabled(true);
                    fabEdit.setImageResource(R.drawable.ic_save_white_16dp);
                    fabEdit.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    chekAllFields();
                }
                else {

                }
            }
        });

        txtPersonalToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linPersonalBlock.getVisibility() == VISIBLE){
                    linPersonalBlock.startAnimation(collapseanimationPersonal);
                }else{
                    linPersonalBlock.startAnimation(expandAnimationPersonal);
                }
            }
        });

        txtIdentityToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relIdentityBlock.getVisibility() == VISIBLE){
                    relIdentityBlock.startAnimation(collapseAnimationIdentity);
                }else {
                    relIdentityBlock.startAnimation(expandAnimationIdentity);
                }
            }
        });

        txtCourseToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relCourseBlock.getVisibility() == VISIBLE){
                    relCourseBlock.startAnimation(collapseAnimationCourse);
                }else {
                    relCourseBlock.startAnimation(expanAnimationCourse);
                }
            }
        });


        collapseanimationPersonal.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                linPersonalBlock.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
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
            public void onAnimationRepeat(Animation animation) { }
        });

        collapseAnimationIdentity.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                relIdentityBlock.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
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
            public void onAnimationRepeat(Animation animation) { }
        });

        collapseAnimationCourse.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                relCourseBlock.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
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
            public void onAnimationRepeat(Animation animation) {}
        });

        applyFieldsChangeListener();

        kycApiCall();
        countryApiCall();
        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i=0; i<countrList.size(); i++){
                    if (spCountry.getSelectedItem().toString().equals(countryModelList.get(i).getName())){
                        LoanTabActivity.countryId = countryModelList.get(i).getId();
                        stateApiCall(LoanTabActivity.countryId);
                        break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i=0; i<stateList.size(); i++){
                    if (spState.getSelectedItem().toString().equals(stateModelList.get(i).getName())){
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

    }

    public void setViewsEnabled(boolean f){
        tilFirstName.setEnabled(f);
        tilMiddleName.setEnabled(f);
        tilLastName.setEnabled(f);
        linMale.setEnabled(f);
        linFemale.setEnabled(f);
        linOther.setEnabled(f);
        linDob.setEnabled(f);
        linMaritalStatus.setEnabled(f);
        tilEmail.setEnabled(f);
        tilMobile.setEnabled(f);
        edtAadhaar.setEnabled(f);
        edtPAN.setEnabled(f);
        tilFlat.setEnabled(f);
        tilStreet.setEnabled(f);
        tilPincode.setEnabled(f);
        spCountry.setEnabled(f);
        spState.setEnabled(f);
        spCity.setEnabled(f);
        acInstituteName.setEnabled(f);
        spInsttLocation.setEnabled(f);
        spCourse.setEnabled(f);
        txtCourseFee.setEnabled(f);
        edtLoanAmt.setEnabled(f);

    }

    public void applyFieldsChangeListener(){

        tilFirstName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { LoanTabActivity.firstName = s.toString(); chekAllFields();   }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        tilMiddleName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  LoanTabActivity.middleName = s.toString(); chekAllFields(); }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        tilLastName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { LoanTabActivity.lastName = s.toString(); chekAllFields();}
            @Override
            public void afterTextChanged(Editable s) {          }
        });

        tilEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { LoanTabActivity.email = s.toString(); chekAllFields(); }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        tilMobile.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {  LoanTabActivity.mobile = s.toString(); chekAllFields();}
            @Override
            public void afterTextChanged(Editable s) {           }
        });

        edtAadhaar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LoanTabActivity.aadhar = s.toString(); chekAllFields();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        edtPAN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LoanTabActivity.pan = s.toString(); chekAllFields();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void chekAllFields(){
        if (LoanTabActivity.firstName.equals("") || LoanTabActivity.middleName.equals("")  || LoanTabActivity.lastName.equals("")
                || LoanTabActivity.email.equals("") || LoanTabActivity.mobile.equals("") ){
            indicateValidationText(txtPersonalToggle, getResources().getDrawable(R.drawable.ic_user_check), false);
        }else {
            indicateValidationText(txtPersonalToggle, getResources().getDrawable(R.drawable.ic_user_check), true);
        }
        if (LoanTabActivity.aadhar.equals("") || LoanTabActivity.pan.equals("")){
            indicateValidationText(txtIdentityToggle, getResources().getDrawable(R.drawable.ic_address_card),false);
        }
        else {
            indicateValidationText(txtIdentityToggle, getResources().getDrawable(R.drawable.ic_address_card),true);
        }

    }

    public void indicateValidationText(TextView indicator, Drawable start, boolean valid){
        if (valid){
            indicator.setCompoundDrawablesRelativeWithIntrinsicBounds(start,null,getResources().getDrawable(R.drawable.ic_check_circle_green),null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                indicator.getCompoundDrawablesRelative()[0].setTint(getResources().getColor(R.color.colorGreen));
                indicator.getCompoundDrawablesRelative()[2].setTint(getResources().getColor(R.color.colorGreen));
            }
            indicator.setTextColor(getResources().getColor(R.color.colorGreen));
        }
        else{
            indicator.setCompoundDrawablesRelativeWithIntrinsicBounds(start,null,getResources().getDrawable(R.drawable.ic_exclamation_circle),null);
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

    private void countryApiCall(){
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
    public void countryApiResponse(JSONObject jsonObject){
        progressDialog.dismiss();
        countryModelList.clear();
        countrList.clear();
        try {
            String message = jsonObject.getString("message");
            if (jsonObject.getInt("status") == 1){
                JSONArray jsonArray = jsonObject.getJSONArray("countries");
                for (int i=0;i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    countryModelList.add(new CountryModel(jsonObject1.getString("country_id"), jsonObject1.getString("country_name")));
                    countrList.add(jsonObject1.getString("country_name"));
                }
                ArrayAdapter countryAdapter = new ArrayAdapter(getContext(),  android.R.layout.simple_list_item_1, countrList );
                spCountry.setAdapter(countryAdapter);

            }else{
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


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

    public void getStatesResponse(JSONObject jsonData){
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
        }catch (Exception e){
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
                for (int i=0; i<jsonArray3.length();i++){
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

        }catch (Exception e){
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }


    public void kycApiCall(){
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String url = MainActivity.mainUrl + "dashboard/getKycDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("lead_id",LoanTabActivity.lead_id);
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
            if (jsonData.getInt("status") == 1 ){
                if (jsonData.has("borrowerDetails") && jsonData.getJSONObject("borrowerDetails") != null){
                    JSONObject jsonborrowerDetails = jsonData.getJSONObject("borrowerDetails");
                    if (jsonborrowerDetails.getString("first_name") != null)
                        LoanTabActivity.firstName = jsonborrowerDetails.getString("first_name");


                    if (jsonborrowerDetails.getString("current_address_country") != null)
                        LoanTabActivity.countryId = jsonborrowerDetails.getString("current_address_country");


                }
            }
            else{
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
