package com.eduvanzapplication.newUI.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.CameraUtils;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;
import com.google.gson.JsonObject;
import com.idfy.rft.RFTSdk;
import com.idfy.rft.RftSdkCallbackInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.*;
import static android.support.v4.content.ContextCompat.getSystemService;
import static com.eduvanzapplication.newUI.MainApplication.TAG;
import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.isProfileEnabled;
import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.student_id;
import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.viewPager;
import static com.facebook.FacebookSdk.getApplicationContext;

public class PersonalDetailsFragment extends Fragment {

    //    private static ProgressBar progressbar;
    private static final String idfy_account_id = "99cde5a9e632/744939bd-4fe2-42e8-94d2-971a79928ee4";
    private static final String idfy_token = "2075c38b-31c3-4fc8-a642-ba7c02697c42";
    public static RFTSdk rftsdk;
    Bitmap bitmapFront = null, bitmapBack = null;
    String doctype = "";
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final String IMAGE_EXTENSION = "jpg";

    public static EditText edtFirstName, edtMiddleName, edtLastName;
    public static LinearLayout linMaleBtn, linFemaleBtn, linOtherBtn, linDobBtn;
    public static Switch switchMarital;
    public static TextView txtMaritalStatus,txtPersonalDetailsErrMsg;

    public static ImageView ivMale, ivFemale, ivOther;
    public static View viewMale, viewFemale,viewOther;
    public static TextView txtMale ,txtFemale,txtOther;

    LinearLayout linPan, linAadhar, linClose, linFooter1, linTakePicture, linQR, linStudentType, linOCR, newLinOcr;
    public static Context context;
    public static Fragment mFragment;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;

    private static OnFragmentInteractionListener mListener;
    private TextView txtDOB;

    private CFAlertDialog cfAlertDialog;

    public PersonalDetailsFragment() {
        // Required empty public constructor
    }

    public static PersonalDetailsFragment newInstance(String param1, String param2) {
        PersonalDetailsFragment fragment = new PersonalDetailsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_details, container, false);
        edtFirstName = view.findViewById(R.id.edtFirstName);
        edtMiddleName = view.findViewById(R.id.edtMiddleName);
        edtLastName = view.findViewById(R.id.edtLastName);
        linMaleBtn = view.findViewById(R.id.linMaleBtn);
        linFemaleBtn = view.findViewById(R.id.linFemaleBtn);
        linOtherBtn = view.findViewById(R.id.linOtherBtn);
        linDobBtn = view.findViewById(R.id.linDobBtn);
        txtDOB = view.findViewById(R.id.txtDOB);
        txtMaritalStatus = view.findViewById(R.id.txtMaritalStatus);
        txtPersonalDetailsErrMsg = view.findViewById(R.id.txtPersonalDetailsErrMsg);
        switchMarital = view.findViewById(R.id.switchMarital);

        ivMale = view.findViewById(R.id.ivMale);
        ivFemale = view.findViewById(R.id.ivFemale);
        ivOther = view.findViewById(R.id.ivOther);
        viewMale = view.findViewById(R.id.viewMale);
        viewFemale = view.findViewById(R.id.viewFemale);
        viewOther = view.findViewById(R.id.viewOther);
        txtMale = view.findViewById(R.id.txtMale);
        txtFemale = view.findViewById(R.id.txtFemale);
        txtOther = view.findViewById(R.id.txtOther);

        context = getContext();
        mFragment = new PersonalDetailsFragment();
        // Checking availability of the camera
        rftsdk = RFTSdk.init((NewLeadActivity) context, idfy_account_id, idfy_token);

        try {
            sharedPreferences = context.getSharedPreferences("UserData", MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switchMarital.setChecked(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtLastName.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.setCursorVisible(false);
                    return true;
                }
                return false;
            }
        });


        linMaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isProfileEnabled) {
                    NewLeadActivity.gender = "1";
                    linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                    linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                    linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));

                    txtMale.setTextColor(getResources().getColor(R.color.white));
                    txtFemale.setTextColor(getResources().getColor(R.color.textcolordark));
                    txtOther.setTextColor(getResources().getColor(R.color.textcolordark));

                    viewMale.setBackgroundColor(getResources().getColor(R.color.white));
                    viewFemale.setBackgroundColor(getResources().getColor(R.color.blue1));
                    viewOther.setBackgroundColor(getResources().getColor(R.color.blue1));

                    Drawable bg;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                        ivMale.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_personal_details_male);
                        DrawableCompat.setTint(bg, getResources().getColor(R.color.white));
                    }
                    ivMale.setImageDrawable(bg);

                    Drawable bg1;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                        ivFemale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg1 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_personal_details_female);
                        DrawableCompat.setTint(bg1, getResources().getColor(R.color.darkblue));
                    }
                    ivFemale.setImageDrawable(bg1);

                    Drawable bg2;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                        ivOther.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg2 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_personal_details_gender);
                        DrawableCompat.setTint(bg2, getResources().getColor(R.color.darkblue));
                    }
                    ivOther.setImageDrawable(bg2);
                    checkAllFields();
                }
            }
        });


        linFemaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isProfileEnabled) {
                    NewLeadActivity.gender = "2";
                    linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                    linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                    linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));

                    txtMale.setTextColor(getResources().getColor(R.color.textcolordark));
                    txtFemale.setTextColor(getResources().getColor(R.color.white));
                    txtOther.setTextColor(getResources().getColor(R.color.textcolordark));

                    viewMale.setBackgroundColor(getResources().getColor(R.color.blue1));
                    viewFemale.setBackgroundColor(getResources().getColor(R.color.white));
                    viewOther.setBackgroundColor(getResources().getColor(R.color.blue1));

                    Drawable bg;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                        ivMale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_personal_details_male);
                        DrawableCompat.setTint(bg, getResources().getColor(R.color.darkblue));
                    }
                    ivMale.setImageDrawable(bg);

                    Drawable bg1;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                        ivFemale.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg1 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_personal_details_female);
                        DrawableCompat.setTint(bg1, getResources().getColor(R.color.white));
                    }
                    ivFemale.setImageDrawable(bg1);

                    Drawable bg2;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                        ivOther.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg2 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_personal_details_gender);
                        DrawableCompat.setTint(bg2, getResources().getColor(R.color.darkblue));
                    }
                    ivOther.setImageDrawable(bg2);

                    checkAllFields();
                }
            }
        });

        linOtherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isProfileEnabled) {
                    NewLeadActivity.gender = "3";
                    linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                    linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                    linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));

                    txtMale.setTextColor(getResources().getColor(R.color.textcolordark));
                    txtFemale.setTextColor(getResources().getColor(R.color.textcolordark));
                    txtOther.setTextColor(getResources().getColor(R.color.white));

                    viewMale.setBackgroundColor(getResources().getColor(R.color.blue1));
                    viewFemale.setBackgroundColor(getResources().getColor(R.color.blue1));
                    viewOther.setBackgroundColor(getResources().getColor(R.color.white));

                    Drawable bg;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_male, null);
                        ivMale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_personal_details_male);
                        DrawableCompat.setTint(bg, getResources().getColor(R.color.darkblue));
                    }
                    ivMale.setImageDrawable(bg);

                    Drawable bg1;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                        ivFemale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg1 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_personal_details_female);
                        DrawableCompat.setTint(bg1, getResources().getColor(R.color.darkblue));
                    }
                    ivFemale.setImageDrawable(bg1);

                    Drawable bg2;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                        ivOther.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg2 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_personal_details_gender);
                        DrawableCompat.setTint(bg2, getResources().getColor(R.color.white));
                    }
                    ivOther.setImageDrawable(bg2);

                    checkAllFields();
                }
            }
        });

        linDobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();

                DatePickerPopWin datePickerPopWin = new DatePickerPopWin.Builder(getActivity(), new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
//                        Toast.makeText(getContext(), dateDesc, Toast.LENGTH_SHORT).show();
                        if(isProfileEnabled) {
                            NewLeadActivity.dob = day + "-" + month + "-" + year;
                            txtDOB.setText(NewLeadActivity.dob);
                            checkAllFields();
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
                        .dateChose("2000-01-01") // date chose when init popwindow
                        .build();
//                datePickerPopWin.showPopWin(getActivity());
                datePickerPopWin.showAsDropDown(linDobBtn);

//                Calendar calendar = Calendar.getInstance();
//                calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)-18);
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity());
//                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                            calendar.set(Calendar.MONTH, month);
//                            NewLeadActivity.dob = String.valueOf(dayOfMonth)+"-"+ (month+1) + "-"+year;
//                            txtDOB.setText(NewLeadActivity.dob);
//                            checkAllFields();
//                        }
//                    });
//                    datePickerDialog.show();
//                }
            }
        });

        switchMarital.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NewLeadActivity.maritalStatus = isChecked ? "1" : "2";
                if (isChecked) {
                    txtMaritalStatus.setText("Married");
                } else {
                    txtMaritalStatus.setText("Unmarried");
                }
                checkAllFields();
            }
        });

        edtFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(isProfileEnabled) {
                    if (edtFirstName.getText().toString().equals("")) {
                        NewLeadActivity.firstName = "";
//                        edtFirstName.setError("Please enter first name");
                    } else {
                        NewLeadActivity.firstName = edtFirstName.getText().toString();
                        edtFirstName.setError(null);
                    }
                    checkAllFields();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtMiddleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NewLeadActivity.middleName = edtMiddleName.getText().toString();
//                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(isProfileEnabled) {
                    if (edtLastName.getText().toString().equals("")) {
                        NewLeadActivity.lastName = "";
//                        edtLastName.setError("Please enter last name");
                    } else {
                        NewLeadActivity.lastName = edtLastName.getText().toString();
                        edtLastName.setError(null);
                    }
                    checkAllFields();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public static void checkAllFields() {
        if (NewLeadActivity.firstName.equals("") || NewLeadActivity.lastName.equals("") || NewLeadActivity.dob.equals("") ||
                NewLeadActivity.gender.equals("") || NewLeadActivity.maritalStatus.equals("")) {
            mListener.onOffButtons(false, false);
            txtPersonalDetailsErrMsg.setVisibility(View.VISIBLE);

            if(edtFirstName.getText().toString().equals("")){
                txtPersonalDetailsErrMsg.setText("Please enter first name");
//                edtFirstName.requestFocus();
            }else if(edtLastName.getText().toString().equals("")){
                txtPersonalDetailsErrMsg.setText("Please enter last name");
//                edtLastName.requestFocus();
            }else if(NewLeadActivity.gender.equals("")){
                txtPersonalDetailsErrMsg.setText("Please select gender");
//                linMaleBtn.requestFocus();
            }else if(NewLeadActivity.dob.equals("")){
                txtPersonalDetailsErrMsg.setText("Please enter your birthdate");
//                linDobBtn.requestFocus();
            }
        } else {
            txtPersonalDetailsErrMsg.setText(null);
            txtPersonalDetailsErrMsg.setVisibility(View.GONE);
            mListener.onOffButtons(true, false);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public static void validate() {
        if (NewLeadActivity.firstName.equals("") || NewLeadActivity.lastName.equals("") || NewLeadActivity.dob.equals("") ||
                NewLeadActivity.gender.equals("") || NewLeadActivity.maritalStatus.equals(""))
            mListener.onFragmentInteraction(false, 0);
        else
            mListener.onFragmentInteraction(true, 1);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        NewLeadActivity.ivOCRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOCRDialog();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

//        if (NewLeadActivity.Aaadhaarno.equals("") && NewLeadActivity.Ppanno.equals("")) {
//            showOCRDialog();
//        } else {
//        }

        edtFirstName.setText(NewLeadActivity.firstName);
        edtMiddleName.setText(NewLeadActivity.middleName);
        edtLastName.setText(NewLeadActivity.lastName);

        if (NewLeadActivity.gender.equals("1")) {
            linMaleBtn.performClick();
        } else if (NewLeadActivity.gender.equals("2")) {
            linFemaleBtn.performClick();
        } else if (NewLeadActivity.gender.equals("3")) {
            linOtherBtn.performClick();
        }

        txtDOB.setText(NewLeadActivity.dob);
        if (NewLeadActivity.maritalStatus.equals("0") || NewLeadActivity.maritalStatus.equals("1")) {
            txtMaritalStatus.setText("Married");
        } else  //2
        {
            txtMaritalStatus.setText("Unmarried");
        }

        if(isProfileEnabled) {
            checkAllFields();
        }
        isProfileEnabled = true;

    }

    private void showOCRDialog() {

        View view = getLayoutInflater().inflate(R.layout.layout_ocr_options, null);

        linPan = view.findViewById(R.id.linPan);
        linAadhar = view.findViewById(R.id.linAadhar);
        linClose = view.findViewById(R.id.linClose);
        progressBar = view.findViewById(R.id.progressBar);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                .setFooterView(view);

        linClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cfAlertDialog.dismiss();
            }
        });

        linPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CameraUtils.checkPermissions((NewLeadActivity) context)) {
                    doctype = "ind_pan";
                    rftsdk.CaptureDocImage((NewLeadActivity) context, "ind_pan", rftSdkCallbackInterface);
                } else {
//                        requestCameraPermission(MEDIA_TYPE_IMAGE);
                }
//Unable to find explicit activity class {com.eduvanzapplication/com.idfy.rft.CameraView}; have you declared this activity in your AndroidManifest.xml?

            }
        });

        linAadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CameraUtils.checkPermissions((NewLeadActivity) context)) {
//                        progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Capture front-side image of Aadhaar", Toast.LENGTH_LONG).show();

                    doctype = "ind_aadhaar";
//                    doctype = "aadhaar_ocr";
                    rftsdk.CaptureDocImage((NewLeadActivity) context, "ind_aadhaar", rftSdkCallbackInterface);
                } else {
//                        requestCameraPermission(MEDIA_TYPE_IMAGE);
                }
            }
        });

        builder.setCancelable(false);

        cfAlertDialog = builder.show();
        cfAlertDialog.setCancelable(false);
        cfAlertDialog.setCanceledOnTouchOutside(false);

        cfAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                onDialogDismiss();
            }
        });

    }

//    private void showOCRDialog() {
//
//        View view = getLayoutInflater().inflate(R.layout.layout_ocr_options, null);
//
//        linStudentBtn = view.findViewById(R.id.linStudentBtn);
//        linSalariedBtn = view.findViewById(R.id.linSalariedBtn);
//        linSelfEmployedBtn = view.findViewById(R.id.linSelfEmployedBtn);
//
//        linPan = view.findViewById(R.id.linPan);
//        linAadhar = view.findViewById(R.id.linAadhar);
//        linClose = view.findViewById(R.id.linClose);
//        linFooter1 = view.findViewById(R.id.linFooter1);
//        linTakePicture = view.findViewById(R.id.linTakePicture);
//        linQR = view.findViewById(R.id.linQR);
//        linStudentType = view.findViewById(R.id.linStudentType);
//        linOCR = view.findViewById(R.id.linOCR);
//        newLinOcr = view.findViewById(R.id.newLinOcr);
//        progressBar = view.findViewById(R.id.progressBar);
//
//        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context)
//                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
//                .setFooterView(view);
//
//        linFooter1.setVisibility(View.VISIBLE);
//        newLinOcr.setVisibility(View.GONE);
//        linTakePicture.setVisibility(View.GONE);
//        linQR.setVisibility(View.GONE);
//
//        linStudentBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NewLeadActivity.profession = "1";
//                linStudentBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
//                linSalariedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linSelfEmployedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                linOCR.setVisibility(View.VISIBLE);
//                linStudentType.setVisibility(View.GONE);
//                newLinOcr.setVisibility(View.VISIBLE);
//            }
//        });
//
//        linSalariedBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NewLeadActivity.profession = "2";
//                linStudentBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linSalariedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
//                linSelfEmployedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linOCR.setVisibility(View.VISIBLE);
//                linStudentType.setVisibility(View.GONE);
//                newLinOcr.setVisibility(View.VISIBLE);
//            }
//        });
//
//        linSelfEmployedBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NewLeadActivity.profession = "3";
//                linStudentBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linSalariedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
//                linSelfEmployedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
//                linOCR.setVisibility(View.VISIBLE);
//                linStudentType.setVisibility(View.GONE);
//                newLinOcr.setVisibility(View.VISIBLE);
//            }
//        });
//
//        linClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cfAlertDialog.dismiss();
//            }
//        });
//
//        linPan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                linTakePicture.setVisibility(View.VISIBLE);
//                linQR.setVisibility(View.GONE);
//                linFooter1.setVisibility(View.VISIBLE);
//
//            }
//        });
//
//        linAadhar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                linTakePicture.setVisibility(View.VISIBLE);
//                linQR.setVisibility(View.VISIBLE);
//                linFooter1.setVisibility(View.VISIBLE);
//            }
//        });
//
//        linTakePicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (linQR.getVisibility() != View.VISIBLE) {  //pan is selected
//                    if (CameraUtils.checkPermissions((NewLeadActivity) context)) {
//                        doctype = "ind_pan";
//                        rftsdk.CaptureDocImage((NewLeadActivity) context, "ind_pan", rftSdkCallbackInterface);
//                    } else {
////                        requestCameraPermission(MEDIA_TYPE_IMAGE);
//                    }
////Unable to find explicit activity class {com.eduvanzapplication/com.idfy.rft.CameraView}; have you declared this activity in your AndroidManifest.xml?
//                } else {
//                    if (CameraUtils.checkPermissions((NewLeadActivity) context)) {
////                        progressBar.setVisibility(View.VISIBLE);
//                        Toast.makeText(getActivity(), "Capture front-side image of Aadhaar", Toast.LENGTH_LONG).show();
//
//                        doctype = "ind_aadhaar";
////                    doctype = "aadhaar_ocr";
//                        rftsdk.CaptureDocImage((NewLeadActivity) context, "ind_aadhaar", rftSdkCallbackInterface);
//                    } else {
////                        requestCameraPermission(MEDIA_TYPE_IMAGE);
//                    }
//
//                }
//            }
//        });
//
//        builder.setCancelable(false);
//
//        cfAlertDialog = builder.show();
//        cfAlertDialog.setCancelable(false);
//        cfAlertDialog.setCanceledOnTouchOutside(false);
//
//        cfAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
////                onDialogDismiss();
//            }
//        });
//
//    }


    private RftSdkCallbackInterface rftSdkCallbackInterface = new RftSdkCallbackInterface() {
        @Override
        public void onImageCaptureSuccess(final Bitmap image) {
            try {
                progressBar.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (doctype.equals("ind_pan")) {
                PersonalDetailsFragment.AsyncReq bv = new PersonalDetailsFragment.AsyncReq(rftsdk, image);
                bv.execute();

//                runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                AsyncReq bv = new AsyncReq(rftsdk, image);
//                                bv.execute();
//                            }
//                        }, 1000);
//
//                    }
//                });

            } else {
                if (bitmapFront == null) {
                    bitmapFront = image;
                    Toast.makeText(getActivity(), "Capture backside image of Aadhaar", Toast.LENGTH_LONG).show();
                    rftsdk.CaptureDocImage(getActivity(), "ind_aadhaar", rftSdkCallbackInterface);
                } else {
                    bitmapBack = image;
                    PersonalDetailsFragment.AsyncReqAaDhaar av = new PersonalDetailsFragment.AsyncReqAaDhaar(rftsdk, bitmapFront, bitmapBack);
                    av.execute();

//                    runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            final Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    AsyncReqAaDhaar av = new AsyncReqAaDhaar(rftsdk, bitmapFront, bitmapBack);
//                                    av.execute();
//                                }
//                            }, 1000);
//
//                        }
//                    });

                }

            }
        }

        @Override
        public void onImageCaptureFaliure(JSONObject response) {
            JsonObject jsonObject = new JsonObject();
//            Toast.makeText(getActivity(), "Upload Failure, Response-> " + jsonObject.toString(), Toast.LENGTH_LONG).show();
        }

    };

    private class AsyncReq extends AsyncTask<Void, Void, JSONObject> {

        RFTSdk rftsdk;
        Bitmap bitmap;

        public AsyncReq(RFTSdk instance, Bitmap bitmap) {
            this.rftsdk = instance;
            this.bitmap = bitmap;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            JSONObject result = null;
            try {
                String group = "g-" + new SimpleDateFormat("MM dd HH:mm").format(new Date());

//                result = rftsdk.UploadImage("fa5942a5-73d7-4fd7-8158-f87f50c73c82",
//                        "0e48c481-fdc1-4c18-8f86-88aebc5b9a8a",
//                        group,
//                        doctype, bitmap);

//                "token": "2075c38b-31c3-4fc8-a642-ba7c02697c42",
//                "account_id": "99cde5a9e632/744939bd-4fe2-42e8-94d2-971a79928ee4"

                result = rftsdk.UploadImage(idfy_account_id, idfy_token, group, doctype, bitmap);
                // {"url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/17d8011c-9896-4c91-bcc1-1f233fd32505"}
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            JSONObject tasks = new JSONObject();
            try {
                String group = "g-" + new SimpleDateFormat("MM dd HH:mm").format(new Date());
                String task = "t-" + new SimpleDateFormat("MM dd HH:mm").format(new Date());
                tasks.put("type", "pan_ocr");
                tasks.put("group_id", group);
                tasks.put("task_id", task);
                tasks.put("data", new JSONObject().put("doc_url", (String) jsonObject.get("url")));

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            JSONArray jsonArray = new JSONArray();

            jsonArray.put(tasks);

            JSONObject postobject = new JSONObject();
            try {
                postobject.put("tasks", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Test 3
//postobject = {"tasks":[{"type":"pan_ocr","group_id":"g-6","task_id":"t-13","data":{"doc_url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/51184dec-f70b-476a-9a47-269ec9faace8"}}]}
            RequestQueue queue = Volley.newRequestQueue(getContext());

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    "https://api.idfy.com/v2/tasks", postobject,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(final JSONObject response) {
//                            Log.d(TAG, response.toString());
                            try {
                                if (response.getString("request_id") != null) {
                                    getActivity().runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        getPANData((String) response.getString("request_id"));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, 6000);

                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage()); //com.android.volley.ClientError
                    try {
                        progressBar.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }) {

                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                    headers.put("apikey", account_id);
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    return super.parseNetworkResponse(response);
                }
            };

            // Adding request to request queue
            queue.add(jsonObjReq);

            //Test 2

//            RequestQueue queue = Volley.newRequestQueue(context);
//            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
//                    Request.Method.POST,"https://api.idfy.com/v2/tasks", postobject,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//                            BreakIterator msgResponse = null;
//                            msgResponse.setText(response.toString());
//                        }
//                    }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    VolleyLog.d(TAG, "Error: " + error.getMessage());
//                }
//            }) {
//
//                /**
//                 * Passing some request headers
//                 */
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                    headers.put("Content-Type", "application/x-www-form-urlencoded");
//                    return headers;
//                }
//
//                @Override
//                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//                    return super.parseNetworkResponse(response);
//                }
//            };
//            queue.add(jsonObjReq);

            //Test 1
////postobject = {"tasks":[{"type":"pan_ocr","group_id":"g-6","task_id":"t-13","data":{"doc_url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/3778f140-a80f-49cc-964d-f6a9532c1a87"}}]}
//            RequestQueue requestQueue = Volley.newRequestQueue(context);
//
//            final String mRequestBody = postobject.toString();
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.idfy.com/v2/tasks", new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.i("LOG_RESPONSE", response);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("LOG_RESPONSE", error.toString());
//                }
//            }) {
//                // Passing some request headers
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                    headers.put("Content-Type", "application/x-www-form-urlencoded");
//                    return headers;
//                }
//
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//
//                @Override
//                public byte[] getBody() throws AuthFailureError {
//                    try {
//                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                        return null;
//                    }
//                }
//
//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    String responseString = "";
//                    if (response != null) {
//                        responseString = String.valueOf(response.statusCode);
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }
//            };
//
//            requestQueue.add(stringRequest);

//            Use response as required
//            It will contain a selflink for uploaded image

//            Toast.makeText(getActivity(), "Upload succes, Response-> " + jsonObject.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private class AsyncReqAaDhaar extends AsyncTask<Void, Void, JSONArray> {

        RFTSdk rftsdk;
        Bitmap bitmap1, bitmap2;

        public AsyncReqAaDhaar(RFTSdk instance, Bitmap bitmapfrt, Bitmap bitmapbck) {
            this.rftsdk = instance;
            this.bitmap1 = bitmapfrt;
            this.bitmap2 = bitmapbck;
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {

            JSONObject result1 = null;
            JSONObject result2 = null;
            try {
                String group = "g-" + new SimpleDateFormat("MM dd HH:mm").format(new Date());

//                result1 = rftsdk.UploadImage("fa5942a5-73d7-4fd7-8158-f87f50c73c82",
//                        "0e48c481-fdc1-4c18-8f86-88aebc5b9a8a",
//                        group,
//                        doctype, bitmap1);
//
//                result2 = rftsdk.UploadImage("fa5942a5-73d7-4fd7-8158-f87f50c73c82",
//                        "0e48c481-fdc1-4c18-8f86-88aebc5b9a8a",
//                        group,
//                        doctype, bitmap2);

//                "token": "2075c38b-31c3-4fc8-a642-ba7c02697c42",
//                "account_id": "99cde5a9e632/744939bd-4fe2-42e8-94d2-971a79928ee4"
                result1 = rftsdk.UploadImage(idfy_account_id, idfy_token, group, doctype, bitmap1);//Unable to resolve host "signed-urls.idfy.com": No address associated with hostname

                result2 = rftsdk.UploadImage(idfy_account_id, idfy_token, group, doctype, bitmap2);//Unable to resolve host "signed-urls.idfy.com": No address associated with hostname
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(result1);
            jsonArray.put(result2);

            bitmapFront = null;
            bitmapBack = null;

            return jsonArray;
        }

        //{"url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/a5799223-7648-454f-9462-c8d5e9f17eff"}
        //{"url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/8dad48ce-fe07-453a-bcd1-552a43b115d6"}

        @Override
        protected void onPostExecute(JSONArray jsonArrayImg) {
            super.onPostExecute(jsonArrayImg);
            JSONObject tasks = new JSONObject();

            JSONObject data = new JSONObject();
            JSONArray docurl = new JSONArray();
            try {
                docurl.put(jsonArrayImg.getJSONObject(0).get("url"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                docurl.put(jsonArrayImg.getJSONObject(1).get("url"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                data.put("doc_url", docurl);
                data.put("aadhaar_consent", "I, the holder of Aadhaar number, hereby give my consent to Baldor Technologies Private Limited, " +
                        "to obtain my Aadhaar number, name, date of birth, address and demographic data for authentication with UIDAI. " +
                        "Baldor Technologies Private Limited has informed me that my identity information would only be used " +
                        "for a background check or a verification of my identity and has also informed me that " +
                        "my biometrics will not be stored/ shared and will be submitted to CIDR only for the purpose of authentication. " +
                        "I have no objection if reports generated from such background check are shared with relevant third parties.");
            } catch (JSONException e) {
                e.printStackTrace();
            }//{"url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/4ecaa516-35a3-482d-97b0-d531cd417476"}
//            "data": {
//                "doc_url": "https://xyz.com/aadhar-123.jpg" ,
//                        "aadhaar_consent": "I, the holder of Aadhaar number, hereby give my consent to Baldor Technologies Private Limited, to obtain my Aadhaar number, name, date of birth, address and demographic data for authentication with UIDAI. Baldor Technologies Private Limited has informed me that my identity information would only be used for a background check or a verification of my identity and has also informed me that my biometrics will not be stored/ shared and will be submitted to CIDR only for the purpose of authentication. I have no objection if reports generated from such background check are shared with relevant third parties."
//            }
            try {
                String group = "g-" + new SimpleDateFormat("MM dd HH:mm").format(new Date());
                String task = "t-" + new SimpleDateFormat("MM dd HH:mm").format(new Date());
                tasks.put("type", "aadhaar_ocr");
                tasks.put("group_id", group);
                tasks.put("task_id", task);
                tasks.put("data", data);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            JSONArray jsonArray = new JSONArray();

            jsonArray.put(tasks);

            JSONObject postobject = new JSONObject();
            try {
                postobject.put("tasks", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            Test 3
//postobject = {"tasks":[{"type":"pan_ocr","group_id":"g-6","task_id":"t-13","data":{"doc_url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/51184dec-f70b-476a-9a47-269ec9faace8"}}]}
            RequestQueue queue = Volley.newRequestQueue(getContext());

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    "https://api.idfy.com/v2/tasks", postobject,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                            try {
                                if (response.getString("request_id") != null) {
                                    getActivity().runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        getAadhaarData((String) response.getString("request_id"));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, 6000);

                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage()); //com.android.volley.ClientError
                    try {
                        progressBar.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }) {

                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("apikey", account_id);
                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    return super.parseNetworkResponse(response);
                }
            };

            jsonObjReq.setTag(TAG);
            // Adding request to request queue
            queue.add(jsonObjReq);

            //InCorrect Get Request
//            RequestQueue queue = Volley.newRequestQueue(context);
//
//            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,"https://api.idfy.com/v2/tasks?request_id=5128d990-6287-4b3a-a9b8-ea12edc3459e",
//                    null, new Response.Listener<JSONObject>() {
//
//                @Override
//                public void onResponse(JSONObject response) {
//                    Log.d(TAG, response.toString());
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e(TAG, "Site Info Error: " + error.getMessage());
//                }
//            }) {
//
//                /**
//                 * Passing some request headers
//                 */
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                    headers.put("Content-Type", "application/json; charset=utf-8");
//                    return headers;
//                }
//            };
//            queue.add(req);

            //Test 2

//            RequestQueue queue = Volley.newRequestQueue(context);
//            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
//                    Request.Method.POST,"https://api.idfy.com/v2/tasks", postobject,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//                            BreakIterator msgResponse = null;
//                            msgResponse.setText(response.toString());
//                        }
//                    }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    VolleyLog.d(TAG, "Error: " + error.getMessage());
//                }
//            }) {
//
//                /**
//                 * Passing some request headers
//                 */
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                    headers.put("Content-Type", "application/x-www-form-urlencoded");
//                    return headers;
//                }
//
//                @Override
//                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//                    return super.parseNetworkResponse(response);
//                }
//            };
//            queue.add(jsonObjReq);

            //Test 1
////postobject = {"tasks":[{"type":"pan_ocr","group_id":"g-6","task_id":"t-13","data":{"doc_url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/3778f140-a80f-49cc-964d-f6a9532c1a87"}}]}
//            RequestQueue requestQueue = Volley.newRequestQueue(context);
//
//            final String mRequestBody = postobject.toString();
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.idfy.com/v2/tasks", new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.i("LOG_RESPONSE", response);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("LOG_RESPONSE", error.toString());
//                }
//            }) {
//                // Passing some request headers
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                    headers.put("Content-Type", "application/x-www-form-urlencoded");
//                    return headers;
//                }
//
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//
//                @Override
//                public byte[] getBody() throws AuthFailureError {
//                    try {
//                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                        return null;
//                    }
//                }
//
//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    String responseString = "";
//                    if (response != null) {
//                        responseString = String.valueOf(response.statusCode);
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }
//            };
//
//            requestQueue.add(stringRequest);

//            Use response as required
//            It will contain a selflink for uploaded image

        }
    }

    private void getPANData(String Requestid) {

        RequestQueue queue12 = Volley.newRequestQueue(getContext());
        StringRequest getRequest = new StringRequest(Request.Method.GET, "https://api.idfy.com/v2/tasks?request_id=" + Requestid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            progressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Boolean success = false;
                        try {
                            if (new JSONArray(response.toString()).getJSONObject(0).getString("status").toLowerCase().equals("completed")) {
                                success = true;
                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    saveOCRData(new JSONArray(response.toString()).getJSONObject(0).getString("status"), Requestid, response.toString());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, 0);

                                    }
                                });

                            } else if (new JSONArray(response.toString()).getJSONObject(0).getString("status").toLowerCase().equals("in_progress")) {
                                success = false;

                                try {
                                    progressBar.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    getPANData((String) new JSONArray(response.toString()).getJSONObject(0).getString("request_id"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, 3000);

                                    }
                                });

                            } else {
//                                Toast.makeText(getActivity(), "OCR Response-> " + response.toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (success) {
                            try {
                                NewLeadActivity.Ppanno = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("pan_number");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Ppantype = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("pan_type");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Pname = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("name_on_card");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Pdob = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("date_on_card");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Pdoi = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("date_of_issue");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Page = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("age");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Pfathersname = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("fathers_name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Pisminor = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("minor");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Pisscanned = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("is_scanned");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Ppanno", NewLeadActivity.Ppanno);
                                editor.putString("Ppantype", NewLeadActivity.Ppantype);
                                editor.putString("Pname", NewLeadActivity.Pname);
                                editor.putString("Pdob", NewLeadActivity.Pdob);
                                editor.putString("Pdoi", NewLeadActivity.Pdoi);
                                editor.putString("Page", NewLeadActivity.Page);
                                editor.putString("Pfathersname", NewLeadActivity.Pfathersname);
                                editor.putString("Pisminor", NewLeadActivity.Pisminor);
                                editor.putString("Pisscanned", NewLeadActivity.Pisscanned);
                                editor.apply();
                                editor.commit();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            NewLeadActivity.setOcrData();
                            onResume();
                        }
//                        else {
//                            Toast.makeText(context, "Upload Failed, Response-> " + response.toString(), Toast.LENGTH_LONG).show();
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                        try {
                            progressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(getActivity(), "Upload Failed, Response-> " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue12.add(getRequest);
    }

    private void saveOCRData(String strStatus, String strRequestId, String strResponse) {
        /** API CALL **/
        try {//auth_token
//            progressBar.setVisibility(View.VISIBLE);
            String url = MainActivity.mainUrl + "dashboard/saveocr";
            Map<String, String> params = new HashMap<String, String>();

            params.put("request_id", strRequestId);
            params.put("student_id", student_id);
            params.put("response", strResponse);
            params.put("status", strStatus);
            // http://159.89.204.41/eduvanzApi/saveocr
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(context, url, null, mFragment, "addOCR", params, MainActivity.auth_token);
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

    private void getAadhaarData(String Requestid) {
        RequestQueue queue12 = Volley.newRequestQueue(getContext());//https://api.idfy.com/v2/tasks?request_id=d740cbd1-6af1-45f6-a609-8c2170dc3418
        StringRequest getRequest = new StringRequest(Request.Method.GET, "https://api.idfy.com/v2/tasks?request_id=" + Requestid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            progressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Boolean success = false;
                        try {
                            if (new JSONArray(response.toString()).getJSONObject(0).getString("status").toLowerCase().equals("completed")) {
                                success = true;

                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    saveOCRData(new JSONArray(response.toString()).getJSONObject(0).getString("status"), Requestid, response.toString());
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, 0);

                                    }
                                });

                            } else if (new JSONArray(response.toString()).getJSONObject(0).getString("status").toLowerCase().equals("in_progress")) {
                                success = false;

                                try {
                                    progressBar.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    getAadhaarData((String) new JSONArray(response.toString()).getJSONObject(0).getString("request_id"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, 3000);

                                    }
                                });

                            } else {
//                                Toast.makeText(getContext(), "OCR Response-> " + response.toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (success) {

                            try {
                                NewLeadActivity.Aaadhaarno = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("aadhaar_number");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Aname = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("name_on_card");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Adob = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("date_of_birth");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Ayob = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("year_of_birth");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Agender = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("gender");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Aaddress = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("address");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Astreet_address = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("street_address");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Apincode = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("pincode");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                NewLeadActivity.Adistrict = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("district");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                NewLeadActivity.Astate = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("state");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                NewLeadActivity.Aisscanned = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("Aisscanned");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            Log.v(TAG, response);

                            try {
                                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("Aaadhaarno", NewLeadActivity.Aaadhaarno);
                                editor.putString("Aname", NewLeadActivity.Aname);
                                editor.putString("Adob", NewLeadActivity.Adob);
                                editor.putString("Ayob", NewLeadActivity.Ayob);
                                editor.putString("Agender", NewLeadActivity.Agender);
                                editor.putString("Aaddress", NewLeadActivity.Aaddress);
                                editor.putString("Astreet_address", NewLeadActivity.Astreet_address);
                                editor.putString("Adistrict", NewLeadActivity.Adistrict);
                                editor.putString("Apincode", NewLeadActivity.Apincode);
                                editor.putString("Astate", NewLeadActivity.Astate);
                                editor.putString("Aisscanned", NewLeadActivity.Aisscanned);
                                editor.apply();
                                editor.commit();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            NewLeadActivity.setOcrData();
                            onResume();
                        }
//                        else {
//                            Toast.makeText(context, "Upload Failed, Response-> " + response.toString(), Toast.LENGTH_LONG).show();
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                        try {
                            progressBar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(getActivity(), "Upload Failure, Response-> " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                headers.put("apikey", account_id);
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue12.add(getRequest);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(boolean valid, int next);

        void onOffButtons(boolean next, boolean prev);
    }

}