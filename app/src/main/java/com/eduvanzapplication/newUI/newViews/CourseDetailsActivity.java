package com.eduvanzapplication.newUI.newViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.pqformfragments.pojo.LocationsPOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfCoursePOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfInsitituePOJO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CourseDetailsActivity extends AppCompatActivity {

    public static ImageView ivNextBtn;
    public static AutoCompleteTextView acInstituteName;
    public static Spinner spInsttLocation, spCourse;
    public static TextView txtCourseFee,txtcourseDetailsErrMsg;
    public static EditText edtLoanAmt;
    public static Context context;
    public static AppCompatActivity mActivity;
    SharedPreferences sharedPreferences;
    public static String instituteID = "", courseID = "", locationID = "", lead_id = "", application_id = "";

    public ArrayAdapter arrayAdapter_NameOfInsititue;
    public ArrayList<String> nameofinstitute_arrayList;
    public ArrayList<NameOfInsitituePOJO> nameOfInsitituePOJOArrayList;

    public ArrayAdapter arrayAdapter_NameOfCourse;
    public ArrayList<String> nameofcourse_arrayList;
    public ArrayList<NameOfCoursePOJO> nameOfCoursePOJOArrayList;
    public ArrayAdapter arrayAdapter_locations;
    public ArrayList<String> locations_arrayList;
    public ArrayList<LocationsPOJO> locationPOJOArrayList;
    public static ProgressDialog progressDialog;

    //Integer Values
    public static int loanAmountvalueInInt, courseFeeValueinint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        setViews();
        context = getApplicationContext();
        mActivity = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR

        spCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String text = spCourse.getSelectedItem().toString();
                int count = nameOfCoursePOJOArrayList.size();

                for (int i = 0; i < count; i++) {
                    if (nameOfCoursePOJOArrayList.get(i).courseName.equalsIgnoreCase(text)) {
                        NewLeadActivity.courseId = nameOfCoursePOJOArrayList.get(i).courseID;
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
                        NewLeadActivity.instituteLocationId = locationPOJOArrayList.get(i).locationID;
                        break;
                    }
                }
                courseApiCall();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        instituteApiCall();

    }

    View.OnClickListener nextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveInstituteData();
        }
    };

    private void enableDisableButtons(boolean next) {
        if (next) {
            ivNextBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));
            ivNextBtn.setOnClickListener(nextClickListener);
            ivNextBtn.setEnabled(true);
        } else {
            ivNextBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
            ivNextBtn.setOnClickListener(null);
            ivNextBtn.setEnabled(false);
        }
    }

    private void setViews() {
        txtcourseDetailsErrMsg=findViewById(R.id.txtcourseDetailsErrMsg);
        ivNextBtn = findViewById(R.id.ivNextBtn);
        ivNextBtn.setEnabled(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        progressDialog = new ProgressDialog(CourseDetailsActivity.this);

        txtCourseFee = findViewById(R.id.txtCourseFee);
        edtLoanAmt = findViewById(R.id.edtLoanAmt);

        acInstituteName = findViewById(R.id.acInstituteName);
        spInsttLocation = findViewById(R.id.spInsttLocation);
        spCourse = findViewById(R.id.spCourse);

        ivNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acInstituteName.getText().toString().equals("")) {
                    Snackbar.make(ivNextBtn, "*Please enter institute name", Snackbar.LENGTH_SHORT).show();
                } else if (spInsttLocation.getSelectedItemPosition() == 0) {
                    Snackbar.make(ivNextBtn, "*Please select institute location", Snackbar.LENGTH_SHORT).show();
                } else if (spCourse.getSelectedItemPosition() == 0) {
                    Snackbar.make(ivNextBtn, "*Please select course name", Snackbar.LENGTH_SHORT).show();
                } else if (edtLoanAmt.getText().toString().equals("")) {
                    Snackbar.make(ivNextBtn, "*Please enter loan amount", Snackbar.LENGTH_SHORT).show();
                } else {
                    NewLeadActivity.courseFee = txtCourseFee.getText().toString();
                    NewLeadActivity.loanAmount = edtLoanAmt.getText().toString();
                    saveInstituteData();
                }
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
                        courseFeeValueinint = Integer.parseInt(txtCourseFee.getText().toString());

                        if (courseFeeValueinint >= loanAmountvalueInInt) {
                            if (loanAmountvalueInInt >= 5000) {
                                NewLeadActivity.loanAmount = edtLoanAmt.getText().toString();
                                txtcourseDetailsErrMsg.setText(null);
                                txtcourseDetailsErrMsg.setVisibility(View.GONE);
                                enableDisableButtons(true);
                            } else {
                                enableDisableButtons(false);
                                txtcourseDetailsErrMsg.setVisibility(View.VISIBLE);
                                txtcourseDetailsErrMsg.setText("*The Loan Amount must be greater than or equal to 5000.");
                            }
                        } else {
                            enableDisableButtons(false);
                            txtcourseDetailsErrMsg.setVisibility(View.VISIBLE);
                            txtcourseDetailsErrMsg.setText("*Loan Amount not exceed than course fees!");
                        }
                    } else {
                        enableDisableButtons(false);
                        txtcourseDetailsErrMsg.setVisibility(View.VISIBLE);
                        txtcourseDetailsErrMsg.setText("*Loan Amount not exceed than course fees!");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                volleyCall.sendRequest(context, url, mActivity, null, "instituteId", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
//            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
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
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void setInstituteAdaptor() {

        try {
            arrayAdapter_NameOfInsititue = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofinstitute_arrayList);

            //Getting the instance of AutoCompleteTextView
            acInstituteName.setThreshold(3);//will start working from first character
            acInstituteName.setAdapter(arrayAdapter_NameOfInsititue);//setting the adapter data into the AutoCompleteTextView
//          acInstituteName.setTextColor(Color.RED);

            acInstituteName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    int count = nameOfInsitituePOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (nameOfInsitituePOJOArrayList.get(i).instituteName.equalsIgnoreCase((String) arg0.getItemAtPosition(arg2))) {
                            NewLeadActivity.instituteId = nameOfInsitituePOJOArrayList.get(i).instituteID;
                            locationApiCall();
                            break;
                        }
                    }
                    InputMethodManager imm = (InputMethodManager) acInstituteName.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(acInstituteName.getWindowToken(), 0);

//                    CourseDetailsActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                    //this is code for close institute text keyboard close

                   /* acInstituteName.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {

                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_DONE) {
                                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                                return true;
                            }
                            return false;
                        }
                    });*/


                }
            });


        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void courseApiCall() {
        try {
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String url = MainActivity.mainUrl + "pqform/apiPrefillCourses";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", NewLeadActivity.instituteId);
            params.put("location_id", NewLeadActivity.instituteLocationId);

            VolleyCall volleyCall = new VolleyCall();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                volleyCall.sendRequest(context, url, mActivity, null, "courseId", params, MainActivity.auth_token);
            }

        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void locationApiCall() {
        try {
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String url = MainActivity.mainUrl + "pqform/apiPrefillLocations";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", NewLeadActivity.instituteId);
            VolleyCall volleyCall = new VolleyCall();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                volleyCall.sendRequest(context, url, mActivity, null, "locationName", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void courseFeeApiCall() {
        /**API CALL**/
        try {
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String url = MainActivity.mainUrl + "pqform/apiPrefillSliderAmount";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", NewLeadActivity.instituteId);
            params.put("course_id", NewLeadActivity.courseId);
            params.put("location_id", NewLeadActivity.instituteLocationId);
            VolleyCall volleyCall = new VolleyCall();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                volleyCall.sendRequest(context, url, mActivity, null, "courseFee", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
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
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void courseFee(JSONObject jsonData) {
        try {
            progressDialog.dismiss();
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                txtCourseFee.setText(jsonData.getString("result"));
                NewLeadActivity.courseFee = jsonData.getString("result");
            } else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void setCourseAdaptor() {

        try {
            arrayAdapter_NameOfCourse = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofcourse_arrayList);
            spCourse.setAdapter(arrayAdapter_NameOfCourse);
            arrayAdapter_NameOfCourse.notifyDataSetChanged();

            if (!NewLeadActivity.courseId.equals("")) {
                for (int i = 0; i < nameOfCoursePOJOArrayList.size(); i++) {
                    if (NewLeadActivity.courseId.equalsIgnoreCase(nameOfCoursePOJOArrayList.get(i).courseID)) {
                        spCourse.setSelection(i);
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
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
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
            progressDialog.dismiss();
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void setInstituteLocationAdaptor() {

        try {
            arrayAdapter_locations = new ArrayAdapter(context, R.layout.custom_layout_spinner, locations_arrayList);
            spInsttLocation.setAdapter(arrayAdapter_locations);
            arrayAdapter_locations.notifyDataSetChanged();

            if (!NewLeadActivity.instituteLocationId.equals("")) {
                for (int i = 0; i < locationPOJOArrayList.size(); i++) {
                    if (NewLeadActivity.instituteLocationId.equalsIgnoreCase(locationPOJOArrayList.get(i).locationID)) {
                        spInsttLocation.setSelection(i);
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
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
        }


    }

    private void saveInstituteData() {
        /** API CALL GET OTP**/
        try {//auth_token
            String url = MainActivity.mainUrl + "dashboard/saveInstitute";
            Map<String, String> params = new HashMap<String, String>();

            params.put("lead_id", NewLeadActivity.leadId);
            params.put("applicant_id", NewLeadActivity.applicantId);
            params.put("institute", NewLeadActivity.instituteId);
            params.put("course_name", NewLeadActivity.courseId);
            params.put("location", NewLeadActivity.instituteLocationId);
            params.put("loanAmount", edtLoanAmt.getText().toString().trim());

            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "saveInstitute", params, MainActivity.auth_token);
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void setsaveInstitute(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (jsonData.getInt("status") == 1) {

                //  Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CourseDetailsActivity.this, FinancialAnalysis.class));
//                startActivity(new Intent(CourseDetailsActivity.this, TenureSelectionActivity.class));
                CourseDetailsActivity.this.finish();

//                EligibilityCheckFragment_6 eligibilityCheckFragment_6 = new EligibilityCheckFragment_6();
//                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_6).commit();

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
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
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
                //spinner.performClick();
                // to open the spinner list if error is found.
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }


}
