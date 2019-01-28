package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.pqformfragments.pojo.LocationsPOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfCoursePOJO;
import com.eduvanzapplication.pqformfragments.pojo.NameOfInsitituePOJO;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


public class EligibilityCheckFragment_5 extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    public Button buttonNext, buttonPrevious;
    Typeface typefaceFont, typefaceFontBold;
    public static Spinner spInstitute, spCourse, spInsLocation;
    public static TextView txtCourseFee;
    EditText edtLoanAmt;
    String instituteID = "", courseID = "", locationID = "", lead_id = "", application_id = "";
    static ProgressBar progressBar;
    SharedPref shareP;
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


    public EligibilityCheckFragment_5() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eligibilitycheck_5, container, false);
        try {
            context = getContext();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            mFragment = new EligibilityCheckFragment_5();
            progressBar = (ProgressBar) view.findViewById(R.id.progressInstitute);

            txtCourseFee = (TextView) view.findViewById(R.id.txtCourseFee);
            edtLoanAmt = (EditText) view.findViewById(R.id.edtLoanAmt);

            spInstitute = (Spinner) view.findViewById(R.id.spInstitute);
            spInsLocation = (Spinner) view.findViewById(R.id.spInsLocation);
            spCourse = (Spinner) view.findViewById(R.id.spCourse);

            typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
            typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

            transaction = getFragmentManager().beginTransaction();

            buttonNext = (Button) view.findViewById(R.id.button_next_loanappfragment5);
            buttonNext.setTypeface(typefaceFontBold);

            buttonPrevious = (Button) view.findViewById(R.id.button_previous_loanappfragment4);
            buttonPrevious.setTypeface(typefaceFontBold);

            lead_id = MainApplication.lead_id;
            application_id = MainApplication.application_id;

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!instituteID.equals("") && !courseID.equals("") && !locationID.equals("")) {

//                        if (edtCourseFee.getText().toString().equalsIgnoreCase("")) {
//                            edtCourseFee.setError(getString(R.string.name_of_the_company_is_required));
//                            edtCourseFee.requestFocus();
//                        }
//                       else
                           if (edtLoanAmt.getText().toString().equalsIgnoreCase("")) {
                            edtLoanAmt.setError(getString(R.string.annual_income_is_required));
                            edtLoanAmt.requestFocus();
                        }
                        else {
                            saveInstituteData();
                        }

                    } else {

//                        if (edtCourseFee.getText().toString().equalsIgnoreCase("")) {
//                            edtCourseFee.setError(getString(R.string.name_of_the_company_is_required));
//                            edtCourseFee.requestFocus();
//                        }
                        if (edtLoanAmt.getText().toString().equalsIgnoreCase("")) {
                            edtLoanAmt.setError(getString(R.string.annual_income_is_required));
                            edtLoanAmt.requestFocus();
                        }
                        if (spInstitute.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spInstitute, getString(R.string.please_select_institue_name));

                        }
                        if (spCourse.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spCourse, getString(R.string.please_select_course_name));

                        }
                        if (spInsLocation.getSelectedItemPosition() <= 0) {
                            setSpinnerError(spInsLocation, getString(R.string.please_select_institue_location));
                        }
                    }
                }
            });


            buttonPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EligibilityCheckFragment_4 eligibilityCheckFragment_4 = new EligibilityCheckFragment_4();
                    transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_4).commit();
                }
            });

            spInstitute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("I_________D", "onItemClick: " + " CLICK HUA");

                    String text = spInstitute.getSelectedItem().toString();
                    int count = nameOfInsitituePOJOArrayList.size();
                    Log.e("TAG", "count: " + count);
                    for (int i = 0; i < count; i++) {
                        if (nameOfInsitituePOJOArrayList.get(i).instituteName.equalsIgnoreCase(text)) {
                            MainApplication.mainapp_instituteID = instituteID = nameOfInsitituePOJOArrayList.get(i).instituteID;
                            Log.e("I_________D", "onItemClick: " + instituteID);
                        }
                    }
                    courseApiCall();
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
                            MainApplication.mainapp_courseID = courseID = nameOfCoursePOJOArrayList.get(i).courseID;
                            Log.e("I_________D", "onItemClick: " + courseID);
                        }
                    }
                    locationApiCall();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spInsLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String text = spInsLocation.getSelectedItem().toString();
                    int count = locationPOJOArrayList.size();
                    for (int i = 0; i < count; i++) {
                        if (locationPOJOArrayList.get(i).locationName.equalsIgnoreCase(text)) {
                            MainApplication.mainapp_locationID = locationID = locationPOJOArrayList.get(i).locationID;
                            Log.e("I_________D", "onItemClick: " + locationID);
                        }
                    }
                    courseFeeApiCall();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }

        instituteApiCall();

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
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    private void saveInstituteData() {
        /** API CALL GET OTP**/
        try {//auth_token
            progressBar.setVisibility(View.VISIBLE);
            String url = MainApplication.mainUrl + "dashboard/saveInstitute";
            Map<String, String> params = new HashMap<String, String>();

            params.put("lead_id", lead_id);
            params.put("applicant_id", application_id);
            params.put("institute", instituteID);
            params.put("course_name", courseID);
            params.put("location", locationID);
            params.put("loanAmount", edtLoanAmt.getText().toString().trim());

            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(getApplicationContext(), url, null, mFragment, "saveInstitute", params, MainApplication.auth_token);
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

    public void setsaveInstitute(JSONObject jsonData) {
        try {

            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (jsonData.getInt("status") == 1) {

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                EligibilityCheckFragment_6 eligibilityCheckFragment_6 = new EligibilityCheckFragment_6();
                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_6).commit();

            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            try {
                progressBar.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
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
            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "instituteName", params, MainApplication.auth_token);
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

            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "courseName", params, MainApplication.auth_token);
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

            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "locationName", params, MainApplication.auth_token);
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
            params.put("institute_id", MainApplication.mainapp_instituteID);
            params.put("course_id", MainApplication.mainapp_courseID);
            params.put("location_id", MainApplication.mainapp_locationID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "courseFee", params, MainApplication.auth_token);
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

    public void instituteName(JSONObject jsonData) {
        try {
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
            spInstitute.setAdapter(arrayAdapter_NameOfInsititue);
            arrayAdapter_NameOfInsititue.notifyDataSetChanged();

            if (!MainApplication.mainapp_instituteID.equals("")) {
                for (int i = 0; i < nameOfInsitituePOJOArrayList.size(); i++) {
                    if (MainApplication.mainapp_instituteID.equalsIgnoreCase(nameOfInsitituePOJOArrayList.get(i).instituteID)) {
                        spInstitute.setSelection(i);
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
                txtCourseFee.setText(jsonData.getString("result"));
                MainApplication.mainapp_coursefee = jsonData.getString("result");
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
            Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }


    public void setCourseAdaptor() {

        try {
            arrayAdapter_NameOfCourse = new ArrayAdapter(context, R.layout.custom_layout_spinner, nameofcourse_arrayList);
            spCourse.setAdapter(arrayAdapter_NameOfCourse);
            arrayAdapter_NameOfCourse.notifyDataSetChanged();

            if (!MainApplication.mainapp_courseID.equals("")) {
                for (int i = 0; i < nameOfCoursePOJOArrayList.size(); i++) {
                    if (MainApplication.mainapp_courseID.equalsIgnoreCase(nameOfCoursePOJOArrayList.get(i).courseID)) {
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
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void locationName(JSONObject jsonData) {
        try {
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
            spInsLocation.setAdapter(arrayAdapter_locations);
            arrayAdapter_locations.notifyDataSetChanged();

            if (!MainApplication.mainapp_locationID.equals("")) {
                for (int i = 0; i < locationPOJOArrayList.size(); i++) {
                    if (MainApplication.mainapp_locationID.equalsIgnoreCase(locationPOJOArrayList.get(i).locationID)) {
                        spInsLocation.setSelection(i);
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

}
