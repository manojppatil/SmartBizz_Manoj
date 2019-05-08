package com.eduvanzapplication.newUI.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.model.CityModel;
import com.eduvanzapplication.newUI.model.CountryModel;
import com.eduvanzapplication.newUI.model.StateModel;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CurrentAddressFragment extends Fragment {

    public static Context context;
    public static Activity activity;
    public static OnCurrentAddrFragmentInteractionListener mListener;
    public static EditText edtAddress, edtLandmark,edtPincode ;
    public static Spinner spCountry, spState, spCity;

    public static ArrayList<CountryModel> countryModelList = new ArrayList<>();
    public static ArrayList<String> countrList  = new ArrayList<>();

    public static ArrayList<String> stateList  = new ArrayList<>();
    public static ArrayList<StateModel> stateModelList = new ArrayList<>();
    public static ArrayAdapter stateAdapter;

    public static ArrayList<CityModel> cityModelList = new ArrayList<>();
    public static ArrayList<String> cityList  = new ArrayList<>();
    public static ArrayAdapter cityAdapter;


    public  static ProgressDialog progressDialog;

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
        View view=inflater.inflate(R.layout.fragment_current_address, container, false);
        context = getContext();
        activity = getActivity();
        edtAddress = view.findViewById(R.id.edtAddress);
        edtLandmark = view.findViewById(R.id.edtLandmark);
        edtPincode = view.findViewById(R.id.edtPincode);
        spCountry = view.findViewById(R.id.spCountry);
        spState = view.findViewById(R.id.spState);
        spCity = view.findViewById(R.id.spCity);
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(activity);
        countryApiResponse(new JSONObject());

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
                NewLeadActivity.flatBuildingSoc = s.toString();
                checkAllFields();
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
                NewLeadActivity.streetLocalityLandMark = s.toString();
                checkAllFields();
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
                if (s.toString().length()==6){
                NewLeadActivity.pinCode = s.toString();
                checkAllFields();
                }else{

                    edtPincode.setError("Please Enter valid PINCODE Number!");
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        countryApiCall();


        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                NewLeadActivity.countryId = String.valueOf(position); //selected val
                for (int i=0; i<countrList.size(); i++){
                    if (spCountry.getSelectedItem().toString().equals(countryModelList.get(i).getName())){
                        NewLeadActivity.countryId = countryModelList.get(i).getId();
                        stateApiCall(NewLeadActivity.countryId);
                        break;
                    }
                }
//                checkAllFields();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i=0; i<stateList.size(); i++){
                    if (spState.getSelectedItem().toString().equals(stateModelList.get(i).getName())){
                        NewLeadActivity.stateId = stateModelList.get(i).getId();
                        cityApiCall(NewLeadActivity.countryId, NewLeadActivity.stateId);
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i=0; i<cityList.size(); i++){
                    if (spCity.getSelectedItem().toString().equals(cityModelList.get(i).getName())){
                        NewLeadActivity.cityId = cityModelList.get(i).getId();
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void checkAllFields() {
        if (NewLeadActivity.flatBuildingSoc.equals("") || NewLeadActivity.streetLocalityLandMark.equals("")
            || NewLeadActivity.pinCode.equals("")){
            mListener.onOffButtonsCurrentAddress(false, false);
        }else
            mListener.onOffButtonsCurrentAddress(true, false);
    }
    public static void validate() {
        if (NewLeadActivity.flatBuildingSoc.equals("") || NewLeadActivity.streetLocalityLandMark.equals("")
                || NewLeadActivity.pinCode.equals("") )
            mListener.onCurrentAddrFragmentInteraction(false, 2);
        else
            mListener.onCurrentAddrFragmentInteraction(true, 3);
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

    }

    private void countryApiCall(){

    }
    public void countryApiResponse(JSONObject jsonObject){


        countryModelList.add(new CountryModel("0","Select"));
        countryModelList.add(new CountryModel("1","India"));
        countryModelList.add(new CountryModel("2","Germany"));
        countryModelList.add(new CountryModel("3","Behrain"));

        countrList.add("Select");
        countrList.add("India");
        countrList.add("Germany");
        countrList.add("Behrain");

        ArrayAdapter countryAdapter = new ArrayAdapter(getContext(),  android.R.layout.simple_list_item_1, countrList );
        spCountry.setAdapter(countryAdapter);
        countryAdapter.notifyDataSetChanged();

        if(NewLeadActivity.Astate.length() > 1){
                spState.setSelection(1);
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
                volleyCall.sendRequest(context, url, null, CurrentAddressFragment.this, "getStates", params, MainActivity.auth_token);
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

                int count = stateModelList.size();
                for (int i = 0; i < count; i++) {
                    if (stateModelList.get(i).getName().equalsIgnoreCase(NewLeadActivity.Astate)) {
                        spState.setSelection(i);
                    }
                }


            } else {

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
                volleyCall.sendRequest(context, url, null, CurrentAddressFragment.this, "getCity", params, MainActivity.auth_token);
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

                int count = cityModelList.size();
                for (int i = 0; i < count; i++) {
                    if (cityModelList.get(i).getName().equalsIgnoreCase(NewLeadActivity.Adistrict)) {
                        spCity.setSelection(i);
                    }
                }

            } else {

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


    public interface OnCurrentAddrFragmentInteractionListener {
        void onCurrentAddrFragmentInteraction(boolean valid, int next);
        void onOffButtonsCurrentAddress(boolean next, boolean prev);
    }
}
