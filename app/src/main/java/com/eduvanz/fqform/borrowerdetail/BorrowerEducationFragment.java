package com.eduvanz.fqform.borrowerdetail;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.fqform.borrowerdetail.pojo.BorrowerEducationDegreePOJO;
import com.eduvanz.pqformfragments.pojo.NameOfCoursePOJO;
import com.eduvanz.volley.VolleyCall;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BorrowerEducationFragment extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    public Button buttonSubmit;
    public static RadioButton radioButtonisCgpaYes, radioButtonisCgpaNo;
    public static EditText editTextIsCgpaYes, editTextIsCgpaNo, editTextPassingYear;
    String userID="";
    public static Spinner spinnerLastCompletedDegree;
    public static ArrayAdapter arrayAdapter;
    public static ArrayList<String> lastCompletedDegree_arratList;
    public static ArrayList<BorrowerEducationDegreePOJO> borrowerEducationDegreePOJOArrayList;
    public String degreeID="";

    public BorrowerEducationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fq_education_fragment2, container, false);
        context = getContext();
        mFragment = new BorrowerEducationFragment();

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("logged_id","null");

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        spinnerLastCompletedDegree = (Spinner) view.findViewById(R.id.spinner_lastdegreecompleted);
        radioButtonisCgpaYes = (RadioButton) view.findViewById(R.id.radiobutton_iscgpa_yes);
        radioButtonisCgpaNo = (RadioButton) view.findViewById(R.id.radiobutton_iscgpa_no);
        editTextIsCgpaYes = (EditText) view.findViewById(R.id.input_degree_cgpa);
        editTextIsCgpaNo = (EditText) view.findViewById(R.id.input_degree_percentage);
        editTextPassingYear = (EditText) view.findViewById(R.id.input_passingyear);

        buttonSubmit = (Button) view.findViewById(R.id.button_submit_education_borrower);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                transaction.replace(R.id.framelayout_fqform,new BorrowerEducationFragment()).commit();
                //------------------------------------API CALL------------------------------------//
                try {
                    String isCgpa="";
                    if(radioButtonisCgpaYes.isChecked())
                    {
                        isCgpa = "1";
                    }else if(radioButtonisCgpaYes.isChecked()){
                        isCgpa = "2";
                    }
                    String url = MainApplication.mainUrl + "algo/setEducationDetails";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("logged_id",userID);
                    params.put("last_degree_completed",degreeID);
                    params.put("is_cgpa",isCgpa);
                    params.put("last_degree_percentage",editTextIsCgpaNo.getText().toString());
                    params.put("last_degree_cgpa",editTextIsCgpaYes.getText().toString());
                    params.put("last_degree_year_completion",editTextPassingYear.getText().toString());
                    VolleyCall volleyCall = new VolleyCall();
                    volleyCall.sendRequest(context, url, null, mFragment, "setborrowerEducation", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //---------------------------------END OF API CALL--------------------------------//
            }
        });

        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup_iscgpa);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.e("TAG", "onCheckedChanged: " );
                Log.e("TAG", "onCheckedChanged: "+radioGroup.getCheckedRadioButtonId());
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.radiobutton_iscgpa_yes:
                        Log.e("TAG", "yes: " );
                        editTextIsCgpaYes.setVisibility(View.VISIBLE);
                        editTextIsCgpaNo.setVisibility(View.GONE);
                        break;
                    case R.id.radiobutton_iscgpa_no:
                        Log.e("TAG", "no: " );
                        editTextIsCgpaYes.setVisibility(View.GONE);
                        editTextIsCgpaNo.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });

        spinnerLastCompletedDegree.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("I_________D", "onItemClick: " );
                String text = spinnerLastCompletedDegree.getSelectedItem().toString();
                int count = borrowerEducationDegreePOJOArrayList.size();
                Log.e("TAG", "count: "+count );
                for (int i = 0; i < count; i++) {
                    if (borrowerEducationDegreePOJOArrayList.get(i).degreeName.equalsIgnoreCase(text)) {
                        degreeID = borrowerEducationDegreePOJOArrayList.get(i).degreeID;
                        Log.e("I_________D", "onItemClick: " + degreeID);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //-----------------------------------------API CALL---------------------------------------//
        try {
            String url = MainApplication.mainUrl + "algo/getEducationDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("logged_id",userID);
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(context, url, null, mFragment, "borrowerEducationFragment", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-------------------------------------END OF API CALL------------------------------------//

        return view;
    }//--------------------------------------END OD ONCREATE--------------------------------------//

    //---------------------------------RESPONSE OF API CALL---------------------------------------//
    public void borrowerEducationFragment(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendOtp" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = jsonData.getJSONObject("result");
                JSONArray jsonArray1 = jsonObject.getJSONArray("degrees");

                lastCompletedDegree_arratList = new ArrayList<>();
                borrowerEducationDegreePOJOArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray1.length(); i++) {
                    BorrowerEducationDegreePOJO borrowerEducationDegreePOJO = new BorrowerEducationDegreePOJO();
                    JSONObject mJsonti = jsonArray1.getJSONObject(i);
                    borrowerEducationDegreePOJO.degreeName = mJsonti.getString("name");
                    lastCompletedDegree_arratList.add(mJsonti.getString("name"));
                    borrowerEducationDegreePOJO.degreeID = mJsonti.getString("id");
                    borrowerEducationDegreePOJOArrayList.add(borrowerEducationDegreePOJO);
                    Log.e(MainApplication.TAG, "borrowerEducationDegreePOJO Spiner DATA:----------------- " + borrowerEducationDegreePOJO.degreeName);
                }
                arrayAdapter = new ArrayAdapter(context, R.layout.custom_layout_spinner, lastCompletedDegree_arratList);
                spinnerLastCompletedDegree.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();

                JSONObject jsonObject1 = jsonObject.getJSONObject("educationalDetails");
                String isCGPA = jsonObject1.getString("is_cgpa");
                String passingYear = jsonObject1.getString("last_degree_year_completion");
                String cgpa = jsonObject1.getString("last_degree_cgpa");
                String percentage = jsonObject1.getString("last_degree_percentage");
                degreeID = jsonObject1.getString("last_degree_completed");

                editTextIsCgpaYes.setText(cgpa);
                editTextIsCgpaNo.setText(percentage);
                editTextPassingYear.setText(passingYear);
                spinnerLastCompletedDegree.setSelection(Integer.parseInt(degreeID));

                if(isCGPA.equalsIgnoreCase("1")){
                    radioButtonisCgpaYes.setChecked(true);
                }else if(isCGPA.equalsIgnoreCase("2")){
                    radioButtonisCgpaNo.setChecked(true);
                }

            }else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setborrowerEducation(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "setborrowerPersonal" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
    }

}
