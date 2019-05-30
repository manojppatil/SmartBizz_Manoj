package com.eduvanzapplication.newUI.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentStatePersonalPOJO;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.isCurrentAddEnabled;

public class CurrentAddressFragment extends Fragment {

    public static Context context;
    public static Activity activity;
    public static Fragment mFragment;
    public static OnCurrentAddrFragmentInteractionListener mListener;
    public static EditText edtAddress, edtLandmark, edtPincode;
    public static TextView txtcurrentAddressErrMsg;
    public static Spinner spCountry, spState, spCity;

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
    public static ProgressDialog progressDialog;

    public CurrentAddressFragment() {
    }


    public static CurrentAddressFragment newInstance(String param1, String param2) {
        CurrentAddressFragment fragment = new CurrentAddressFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_address, container, false);
        context = getContext();
        activity = getActivity();
        context = getContext();
        mFragment = new CurrentAddressFragment();
        edtAddress = view.findViewById(R.id.edtAddress);
        edtLandmark = view.findViewById(R.id.edtLandmark);
        edtPincode = view.findViewById(R.id.edtPincode);
        spCountry = view.findViewById(R.id.spCountry);
        spState = view.findViewById(R.id.spState);
        spCity = view.findViewById(R.id.spCity);
        txtcurrentAddressErrMsg = view.findViewById(R.id.txtcurrentAddressErrMsg);
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(activity);

        try {
            edtPincode.setOnEditorActionListener(new EditText.OnEditorActionListener() {

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

            edtAddress.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (isCurrentAddEnabled) {
                        if (edtAddress.getText().toString().equals("")) {
                            NewLeadActivity.flatBuildingSoc = "";
//                        edtFirstName.setError("Please enter first name");
                        } else {
                            NewLeadActivity.flatBuildingSoc = edtAddress.getText().toString();
                            edtAddress.setError(null);
                        }
                        checkAllFields();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtLandmark.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (isCurrentAddEnabled) {

                        if (edtLandmark.getText().toString().equals("")) {
                            NewLeadActivity.streetLocalityLandMark = "";
//                        edtFirstName.setError("Please enter first name");
                        } else {
                            NewLeadActivity.streetLocalityLandMark = edtLandmark.getText().toString();
                            edtLandmark.setError(null);
                        }
                        checkAllFields();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            edtPincode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (isCurrentAddEnabled) {
                        if (s.toString().length() == 6) {
                            NewLeadActivity.pinCode = s.toString();
                            edtPincode.setError(null);
                        } else {
                            NewLeadActivity.pinCode = "";
//                        edtPincode.setError("Please Enter valid PINCODE Number!");
                        }
                        checkAllFields();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spCity.getSelectedItem().toString();
                        int count = borrowerCurrentCityPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCityPersonalPOJOArrayList.get(i).cityName.equalsIgnoreCase(text)) {
                                NewLeadActivity.cityId = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
                                if (isCurrentAddEnabled) {
                                    checkAllFields();
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
                                NewLeadActivity.stateId = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
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
                                NewLeadActivity.countryId = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                                break;
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

            countryApiCall();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void checkAllFields() {
        if (NewLeadActivity.flatBuildingSoc.equals("") || NewLeadActivity.streetLocalityLandMark.equals("")
                || NewLeadActivity.pinCode.equals("")|| NewLeadActivity.pinCode.toString().length()<6 || NewLeadActivity.countryId.equals("") ||
                NewLeadActivity.stateId.equals("") || NewLeadActivity.cityId.equals("")) {
            mListener.onOffButtonsCurrentAddress(false, false);

            if (edtAddress.getText().toString().equals("")) {
                txtcurrentAddressErrMsg.setVisibility(View.VISIBLE);
                txtcurrentAddressErrMsg.setText("please enter FLAT NUMBER,BUILDING,SOCIETY NAME");
//                edtAddress.requestFocus();

            }else if (edtLandmark.getText().toString().equals("")) {
                txtcurrentAddressErrMsg.setVisibility(View.VISIBLE);
                txtcurrentAddressErrMsg.setText("please enter your STREET NAME,LOCALITY,LANDMARK");
//                edtLandmark.requestFocus();

            } else if (edtPincode.getText().toString().equals("")) {
                txtcurrentAddressErrMsg.setVisibility(View.VISIBLE);
                txtcurrentAddressErrMsg.setText("please enter your pincode");
//                edtPincode.requestFocus();

            }  else if (NewLeadActivity.countryId.equals("")) {
                txtcurrentAddressErrMsg.setVisibility(View.VISIBLE);
                txtcurrentAddressErrMsg.setText("please select country");
//                spCountry.requestFocus();

            } else if (NewLeadActivity.stateId.equals("")) {
                txtcurrentAddressErrMsg.setVisibility(View.VISIBLE);
                txtcurrentAddressErrMsg.setText("please select state");
//                spState.requestFocus();

            } else if (NewLeadActivity.cityId.equals("")) {
                txtcurrentAddressErrMsg.setVisibility(View.VISIBLE);
                txtcurrentAddressErrMsg.setText("please select city");
//                spCity.requestFocus();
            }

        } else {
            txtcurrentAddressErrMsg.setText(null);
            txtcurrentAddressErrMsg.setVisibility(View.GONE);
            mListener.onOffButtonsCurrentAddress(true, true);
        }
    }

    public static void validate() {
        if (NewLeadActivity.flatBuildingSoc.equals("") || NewLeadActivity.streetLocalityLandMark.equals("")
                || NewLeadActivity.pinCode.equals("")|| NewLeadActivity.pinCode.toString().length()<6 || NewLeadActivity.countryId.equals("") ||
                NewLeadActivity.stateId.equals("") || NewLeadActivity.cityId.equals("")) {

            mListener.onCurrentAddrFragmentInteraction(false, 2);
        } else{
            mListener.onCurrentAddrFragmentInteraction(true, 3);
    }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCurrentAddrFragmentInteractionListener) {
            mListener = (OnCurrentAddrFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        edtAddress.setText(NewLeadActivity.flatBuildingSoc);
        edtLandmark.setText(NewLeadActivity.streetLocalityLandMark);
        edtPincode.setText(NewLeadActivity.pinCode);

        if (isCurrentAddEnabled) {
            checkAllFields();
        }
        isCurrentAddEnabled = true;

    }

    public static void setCurrentAddressData() {
        try {
            edtAddress.setText(NewLeadActivity.flatBuildingSoc);
            edtLandmark.setText(NewLeadActivity.streetLocalityLandMark);
            edtPincode.setText(NewLeadActivity.pinCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                volleyCall.sendRequest(context, url, null, mFragment, "getCountriesCA", params, MainActivity.auth_token);
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
            params.put("countryId", NewLeadActivity.countryId);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "getStatesCA", params, MainActivity.auth_token);
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

    public void getStatesResponse(JSONObject jsonData) {
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

//                    int count = borrowerCurrentStatePersonalPOJOArrayList.size();

//                    for (int i = 0; i < count; i++) {
//                        if (borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID.equalsIgnoreCase(currentstateID)) {
//                            spState.setSelection(i);
//                        }
//                    }

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
            params.put("countryId", NewLeadActivity.countryId);//1
            params.put("stateId", NewLeadActivity.stateId);//2
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentCities(currentstateID,currentcountryID);
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "getCityCA", params, MainActivity.auth_token);
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

    public void getCityResponse(JSONObject jsonData) {
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

    public interface OnCurrentAddrFragmentInteractionListener {
        void onCurrentAddrFragmentInteraction(boolean valid, int next);

        void onOffButtonsCurrentAddress(boolean next, boolean prev);
    }
}