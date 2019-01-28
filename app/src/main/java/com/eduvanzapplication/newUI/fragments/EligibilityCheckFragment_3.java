package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCityPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentCountryPersonalPOJO;
import com.eduvanzapplication.fqform.borrowerdetail.pojo.BorrowerCurrentStatePersonalPOJO;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.VolleyCallNew;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.eduvanzapplication.newUI.MainApplication.TAG;

/**
 * A simple {@link Fragment} subclass.
 */


public class EligibilityCheckFragment_3 extends Fragment {

    public static Spinner spCity, spCountry, spState;
    EditText edtPincode, edtAddressline1, edtAddressline2;
    public static Context context;
    public static Fragment mFragment;
    Button buttonNext, buttonPrevious;
    Typeface typefaceFont, typefaceFontBold;

    public String currentcityID = "", currentstateID = "", currentcountryID = "";

    public static ArrayAdapter arrayAdapter_currentCity;
    public static ArrayList<String> currentcity_arrayList;
    public static ArrayList<BorrowerCurrentCityPersonalPOJO> borrowerCurrentCityPersonalPOJOArrayList;
    public static ArrayAdapter arrayAdapter_currentState;

    public static ArrayList<String> currentstate_arrayList;
    public static ArrayList<BorrowerCurrentStatePersonalPOJO> borrowerCurrentStatePersonalPOJOArrayList;
    public static ArrayAdapter arrayAdapter_currentCountry;
    public static ArrayList<String> currentCountry_arrayList;
    public static ArrayList<BorrowerCurrentCountryPersonalPOJO> borrowerCurrentCountryPersonalPOJOArrayList;

    public EligibilityCheckFragment_3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eligibilitycheck_3, container, false);
        try {
            context = getContext();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            mFragment = new EligibilityCheckFragment_3();

            typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
            typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

            final FragmentTransaction transaction = getFragmentManager().beginTransaction();

            getLocationAddress();

            edtPincode = (EditText) view.findViewById(R.id.edtPincode);
            edtAddressline1 = (EditText) view.findViewById(R.id.edtAddressline1);
            edtAddressline2 = (EditText) view.findViewById(R.id.edtAddressline2);

            spCountry = (Spinner) view.findViewById(R.id.spCountry);
            spState = (Spinner) view.findViewById(R.id.spState);
            spCity = (Spinner) view.findViewById(R.id.spCity);

            buttonNext = (Button) view.findViewById(R.id.button_next_eligiblityfragment2);
            buttonNext.setTypeface(typefaceFontBold);

            buttonPrevious = (Button) view.findViewById(R.id.button_previous_eligiblityfragment2);
            buttonPrevious.setTypeface(typefaceFontBold);

//            edtPincode.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    if(s.length() == 6) {
//                        pincodeApiCall();
//                    }
//
//
//                }
//            });

            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!edtPincode.getText().toString().equals("") && !edtAddressline1.getText().toString().equals("") &&
                            !edtAddressline2.getText().toString().equals("")) {
                        if (!currentcityID.equalsIgnoreCase("") && !currentstateID.equalsIgnoreCase("") &&
                                !currentcountryID.equalsIgnoreCase("")) {

                            if (edtPincode.getText().toString().length() == 6) {

                                MainApplication.pincode = edtPincode.getText().toString().trim();
//                                MainApplication.cokyc_address_pin = edtPincode.getText().toString().trim();

                                MainApplication.kyc_address = edtAddressline1.getText().toString().trim();
                                MainApplication.kyc_landmark = edtAddressline2.getText().toString().trim();

//                                MainApplication.cokyc_address = edtAddressline1.getText().toString().trim();
//                                MainApplication.cokyc_landmark = edtAddressline2.getText().toString().trim();

                                EligibilityCheckFragment_4 eligibilityCheckFragment_4 = new EligibilityCheckFragment_4();
                                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_4).commit();
                            } else {
                                edtPincode.setError("Please enter valid Pincode");
                                edtPincode.requestFocus();
                                return;
                            }
                        }

                    } else {

                        EligibilityCheckFragment_4 eligibilityCheckFragment_4 = new EligibilityCheckFragment_4();
                        transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_4).commit();
//
//                        if (edtPincode.getText().toString().equalsIgnoreCase("")) {
//                            edtPincode.setError(getString(R.string.current_pin_code_is_required));
//                            edtPincode.requestFocus();
//                        } else {
//                            edtPincode.setError(null);
//                        }
//                        if (edtAddressline1.getText().toString().equalsIgnoreCase("")) {
//                            edtAddressline1.setError(getString(R.string.current_address_is_required));
//                            edtAddressline1.requestFocus();
//                        } else {
//                            edtAddressline1.setError(null);
//                        }
//                        if (edtAddressline2.getText().toString().equalsIgnoreCase("")) {
//                            edtAddressline2.setError(getString(R.string.current_address_is_required));
//                            edtAddressline2.requestFocus();
//                        } else {
//                            edtAddressline2.setError(null);
//                        }
                    }

                }
            });

            buttonPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EligibilityCheckFragment_2 eligibilityCheckFragment_2 = new EligibilityCheckFragment_2();
                    transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_2).commit();
                }
            });

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
            spCountry.setAdapter(arrayAdapter_currentCountry);
            arrayAdapter_currentCountry.notifyDataSetChanged();

            spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String text = spCountry.getSelectedItem().toString();
                        int count = borrowerCurrentCountryPersonalPOJOArrayList.size();
                        for (int i = 0; i < count; i++) {
                            if (borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryName.equalsIgnoreCase(text)) {
//                                MainApplication.borrowerValue6 = currentcountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                                MainApplication.kyc_address_country = currentcountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
//                                MainApplication.cokyc_address_country = currentcountryID = borrowerCurrentCountryPersonalPOJOArrayList.get(i).countryID;
                            }
                        }
                        stateApiCall();
                        if (currentcityID.equals("")) {
                            spCity.setSelection(0);
                        } else {
                            spCity.setSelection(Integer.parseInt(currentcityID) - 1);
                        }
                    } catch (Exception e) {
//                        String className = this.getClass().getSimpleName();
//                        String name = new Object() {
//                        }.getClass().getEnclosingMethod().getName();
//                        String errorMsg = e.getMessage();
//                        String errorMsgDetails = e.getStackTrace().toString();
//                        String errorLine = String.valueOf(e.getStackTrace()[0]);
//                        Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
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
//                                MainApplication.borrowerValue5 = currentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
                                MainApplication.kyc_address_state = currentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
//                                MainApplication.cokyc_address_state = currentstateID = borrowerCurrentStatePersonalPOJOArrayList.get(i).stateID;
                            }
                        }
                    } catch (Exception e) {
//                        String className = this.getClass().getSimpleName();
//                        String name = new Object() {
//                        }.getClass().getEnclosingMethod().getName();
//                        String errorMsg = e.getMessage();
//                        String errorMsgDetails = e.getStackTrace().toString();
//                        String errorLine = String.valueOf(e.getStackTrace()[0]);
//                        Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
                    }
                    cityApiCall();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

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
//                                MainApplication.borrowerValue4 = currentcityID = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
                                MainApplication.kyc_address_city = currentcityID = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
//                                MainApplication.cokyc_address_city = currentcityID = borrowerCurrentCityPersonalPOJOArrayList.get(i).cityID;
                            }
                        }
                    } catch (Exception e) {
//                        String className = this.getClass().getSimpleName();
//                        String name = new Object() {
//                        }.getClass().getEnclosingMethod().getName();
//                        String errorMsg = e.getMessage();
//                        String errorMsgDetails = e.getStackTrace().toString();
//                        String errorLine = String.valueOf(e.getStackTrace()[0]);
//                        Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
                    }

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
                //spinner.performClick(); // to open the spinner list if error is found.
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

    private void pincodeApiCall() {
        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "dashboard/fetchpincode";
            Map<String, String> params = new HashMap<String, String>();
            params.put("pincode", edtPincode.getText().toString().trim());
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
//                getCurrentStates(currentcountryID);

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(context, url, null, mFragment, "fetchpincode", params, MainApplication.auth_token);
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
                volleyCall.sendRequest(context, url, null, mFragment, "getStates", params, MainApplication.auth_token);
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
                volleyCall.sendRequest(context, url, null, mFragment, "getCity", params, MainApplication.auth_token);
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

    public void getfetchpincode(JSONObject jsonData) {
        try {

            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

//                    String message = jsonData.getString("message");

                JSONArray jsonArray1 = jsonData.getJSONArray("country");
                JSONArray jsonArray2 = jsonData.getJSONArray("state");
                JSONArray jsonArray3 = jsonData.getJSONArray("city");

                currentstate_arrayList = new ArrayList<>();
                currentstate_arrayList.add("Select Any");
                borrowerCurrentStatePersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray2.length(); i++) {
                    BorrowerCurrentStatePersonalPOJO borrowerCurrentStatePersonalPOJO = new BorrowerCurrentStatePersonalPOJO();
                    JSONObject mJsonti = jsonArray2.getJSONObject(i);
                    borrowerCurrentStatePersonalPOJO.stateName = mJsonti.getString("state_name");
                    currentstate_arrayList.add(mJsonti.getString("state_name"));
                    borrowerCurrentStatePersonalPOJO.stateID = mJsonti.getString("state_id");
                    borrowerCurrentStatePersonalPOJOArrayList.add(borrowerCurrentStatePersonalPOJO);
                }
                arrayAdapter_currentState = new ArrayAdapter(context, R.layout.custom_layout_spinner, currentstate_arrayList);
                spState.setAdapter(arrayAdapter_currentState);
                arrayAdapter_currentState.notifyDataSetChanged();
                spState.setSelection(Integer.parseInt(currentstateID));

                currentcity_arrayList = new ArrayList<>();
                currentcity_arrayList.add("Select Any");
                borrowerCurrentCityPersonalPOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray3.length(); i++) {
                    BorrowerCurrentCityPersonalPOJO borrowerCurrentCityPersonalPOJO = new BorrowerCurrentCityPersonalPOJO();
                    JSONObject mJsonti = jsonArray3.getJSONObject(i);
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

    public void getStates(JSONObject jsonData) {
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
                    spState.setAdapter(arrayAdapter_currentState);
                    arrayAdapter_currentState.notifyDataSetChanged();

                    spState.setSelection(Integer.parseInt(currentstateID));

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

    public void getCity(JSONObject jsonData) {
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
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }


    private void getLocationAddress() {
        String cityName = null;
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(MainApplication.latitude, MainApplication.longitde, 1);
            if (addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
//             If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String subLoca = addresses.get(0).getSubLocality();
                String state = addresses.get(0).getAdminArea();
                String countryG = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();
                Log.e(MainApplication.TAG, "onResume: Header getLocality" + city + "getSubLocality" + state + "\n country " + countryG);
//                countryByLoc=countryG;
//                if(MainApplication.mainapp_currentCity.equalsIgnoreCase("")){
////                    editTextCity.setText(city);
//                }

            }
            Log.e(TAG, "getLocationAddress: " + MainApplication.latitude);
            Log.e(TAG, "getLocationAddress: " + MainApplication.longitde);
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
