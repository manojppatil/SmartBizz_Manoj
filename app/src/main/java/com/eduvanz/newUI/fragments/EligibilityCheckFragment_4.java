package com.eduvanz.newUI.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;
import com.eduvanz.newUI.SharedPref;
import com.eduvanz.SuccessSplash;
import com.eduvanz.newUI.VolleyCallNew;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner
 */


public class EligibilityCheckFragment_4 extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    Button buttonNext, buttonPrevious;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3;
    EditText editTextFirstname, editTextLastname, editTextEmailID;
    public static String mobileNo="";
    static ProgressBar progressBar;
    SharedPref shareP;

    public EligibilityCheckFragment_4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eligibilitycheck_4, container, false);
        context = getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mFragment = new EligibilityCheckFragment_4();
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_eligiblityCheck);

        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            mobileNo = sharedPreferences.getString("mobile_no", "null");
        }catch (Exception e){
            e.printStackTrace();
        }

        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        textView1 = (TextView) view.findViewById(R.id.textView1_e3);
        textView2 = (TextView) view.findViewById(R.id.textView2_e3);
        textView3 = (TextView) view.findViewById(R.id.textView3_e3);
        textView1.setTypeface(typefaceFont);
        textView2.setTypeface(typefaceFont);
        textView3.setTypeface(typefaceFontBold);

        editTextFirstname = (EditText) view.findViewById(R.id.editText_firstname_el);
        editTextFirstname.setText(MainApplication.mainapp_firstname);
        editTextLastname = (EditText) view.findViewById(R.id.editText_lastname_el);
        editTextLastname.setText(MainApplication.mainapp_lastname);
        editTextEmailID = (EditText) view.findViewById(R.id.editText_emailid_el);
        editTextEmailID.setText(MainApplication.mainapp_emailid);

        buttonNext = (Button) view.findViewById(R.id.button_submit_eligibility);
        buttonNext.setTypeface(typefaceFontBold);

        buttonPrevious = (Button) view.findViewById(R.id.button_previous_eligiblityfragment2);
        buttonPrevious.setTypeface(typefaceFontBold);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!MainApplication.mainapp_firstname.equals("") && !MainApplication.mainapp_lastname.equals("") &&
                        !MainApplication.mainapp_emailid.equals("")){

                            /**API CALL**/
                            try {
                                progressBar.setVisibility(View.VISIBLE);
                                String url = MainApplication.mainUrl + "pqform/registerUserForEligibility";
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("institute", MainApplication.mainapp_instituteID);
                                params.put("course", MainApplication.mainapp_courseID);
                                params.put("location", MainApplication.mainapp_locationID);
                                params.put("loanAmount", MainApplication.mainapp_loanamount);
                                params.put("cc", MainApplication.mainapp_userdocument);
                                params.put("familyIncomeAmount", MainApplication.mainapp_fammilyincome);
                                params.put("studentProfessional", MainApplication.mainapp_userprofession);
                                params.put("currentResidence", MainApplication.mainapp_currentCity);
                                params.put("borrowerFirstName", MainApplication.mainapp_firstname);
                                params.put("borrowerLastName", MainApplication.mainapp_lastname);
                                params.put("mobile", mobileNo);
                                params.put("email", MainApplication.mainapp_emailid);
                                VolleyCallNew volleyCall = new VolleyCallNew();
                                Log.e(TAG, "sendRequestsendRequest: " );
                                volleyCall.sendRequest(context, url, null, mFragment, "checkEligiblity", params);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                }else {
                    if(editTextFirstname.getText().toString().equalsIgnoreCase("")){
                        editTextFirstname.setError("Please provide First Name");
                    }
                    if(editTextEmailID.getText().toString().equalsIgnoreCase("")){
                        editTextEmailID.setError("Please provide Email ID");
                    }
                    if(editTextLastname.getText().toString().equalsIgnoreCase("")){
                        editTextLastname.setError("Please provide Last Name");
                    }

                }
            }
        });

        editTextFirstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.mainapp_firstname = editTextFirstname.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextLastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.mainapp_lastname = editTextLastname.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextEmailID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainApplication.mainapp_emailid = editTextEmailID.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EligibilityCheckFragment_3 eligibilityCheckFragment_3 = new EligibilityCheckFragment_3();
                transaction.replace(R.id.frameLayout_eligibilityCheck, eligibilityCheckFragment_3).commit();
            }
        });

        return view;
    }

    public void checkEligiblity(JSONObject jsonData) {
        try {
            Log.e(MainApplication.TAG, "jsonDatajsonData: "+jsonData );
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                progressBar.setVisibility(View.GONE);
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                JSONObject jsonObject = jsonData.getJSONObject("result");
                String loggedID = jsonObject.optString("logged_id");
                String firstName = jsonObject.optString("first_name");
                String lastName = jsonObject.optString("last_name");
                String userType = jsonObject.optString("user_type");
                String userImage = jsonObject.optString("img_profile");
                String useremail = jsonObject.optString("email");
                String mobileNo = jsonObject.optString("mobile_no");

                shareP =new SharedPref();
                shareP.setLoginDone(context,true);

                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("logged_id",loggedID);
                editor.putString("first_name",firstName);
                editor.putString("last_name",lastName);
                editor.putString("user_type",userType);
                editor.putString("mobile_no",mobileNo);
                editor.putString("user_image",userImage);
                editor.putString("user_email",useremail);
                editor.apply();
                editor.commit();

                Intent intent = new Intent(context, SuccessSplash.class);
                context.startActivity(intent);

            }else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
