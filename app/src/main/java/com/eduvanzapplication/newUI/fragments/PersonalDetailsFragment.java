package com.eduvanzapplication.newUI.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Util.JavaGetFileSize;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;
import com.eduvanzapplication.uploaddocs.PathFile;
import com.eduvanzapplication.uploaddocs.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vijay.createpdf.activity.ImgToPdfActivity;

import static android.content.Context.*;
import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.isProfileEnabled;
import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.student_id;
import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.viewPager;

public class PersonalDetailsFragment extends Fragment {

    //  private static ProgressBar progressbar;
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final String IMAGE_EXTENSION = "jpg";

    public static EditText edtFirstName, edtMiddleName, edtLastName;
    public static LinearLayout linMaleBtn, linFemaleBtn, linOtherBtn, linDobBtn;
    public static Switch switchMarital;
    public static TextView txtMaritalStatus, txtPersonalDetailsErrMsg;

    public static ImageView ivMale, ivFemale, ivOther;
    public static View viewMale, viewFemale, viewOther;
    public static TextView txtMale, txtFemale, txtOther;

    DatePickerDialog dpd;

    LinearLayout linPan, linAadhar, linClose;
    public static Context context;
    public static Fragment mFragment;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;
    public String userChoosenTask;
    public int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_DOC = 2;
    public int GET_MY_PERMISSION = 1, permission;
    String uploadFilePath = "";
    StringBuffer sb;
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

        try {
            sharedPreferences = context.getSharedPreferences("UserData", MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switchMarital.setChecked(false);

        if (!NewLeadActivity.isProfileEnabled) {
            showOCRDialog();
        }
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
                if (isProfileEnabled) {
                    NewLeadActivity.gender = "1";
                    linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                    linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
                    linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));

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
                        bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                        DrawableCompat.setTint(bg, getResources().getColor(R.color.white));
                    }
                    ivMale.setImageDrawable(bg);

                    Drawable bg1;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                        ivFemale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                        DrawableCompat.setTint(bg1, getResources().getColor(R.color.darkblue));
                    }
                    ivFemale.setImageDrawable(bg1);

                    Drawable bg2;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                        ivOther.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
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
                if (isProfileEnabled) {
                    NewLeadActivity.gender = "2";
                    linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
                    linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                    linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));

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
                        bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                        DrawableCompat.setTint(bg, getResources().getColor(R.color.darkblue));
                    }
                    ivMale.setImageDrawable(bg);

                    Drawable bg1;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                        ivFemale.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                        DrawableCompat.setTint(bg1, getResources().getColor(R.color.white));
                    }
                    ivFemale.setImageDrawable(bg1);

                    Drawable bg2;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                        ivOther.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
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
                if (isProfileEnabled) {
                    NewLeadActivity.gender = "3";
                    linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
                    linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_primary));
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
                        bg = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_male);
                        DrawableCompat.setTint(bg, getResources().getColor(R.color.darkblue));
                    }
                    ivMale.setImageDrawable(bg);

                    Drawable bg1;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_female, null);
                        ivFemale.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg1 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_female);
                        DrawableCompat.setTint(bg1, getResources().getColor(R.color.darkblue));
                    }
                    ivFemale.setImageDrawable(bg1);

                    Drawable bg2;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_personal_details_gender, null);
                        ivOther.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                    } else {
                        bg2 = ContextCompat.getDrawable(context, R.drawable.ic_personal_details_gender);
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

                int year0=2019, month0=1, day0=1; // for min. date: 1. feb. 2017
                Calendar cal = Calendar.getInstance();
                cal.set( cal.get((Calendar.YEAR))-18, cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0); //hour=0 min=0 sec=0

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get((Calendar.YEAR));
                dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        if (isProfileEnabled) {
                            NewLeadActivity.dob = dayOfMonth + "-" + (month + 1) + "-" + (year);
                            txtDOB.setText(NewLeadActivity.dob);
                            checkAllFields();
                        }

                    }
                }, year - 18, month, day);
//                dpd.getDatePicker().setMinDate(cal.getTimeInMillis());
                dpd.getDatePicker().setMaxDate(cal.getTimeInMillis());
//                dpd.getDatePicker().setMaxDate(System.currentTimeMillis() - 1234564);
                dpd.show();
             /*   DatePickerPopWin datePickerPopWin = new DatePickerPopWin.Builder(getActivity(), new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
//                        Toast.makeText(getContext(), dateDesc, Toast.LENGTH_SHORT).show();
                        if (isProfileEnabled) {
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
                        .colorConfirm(Color.parseColor("#1ac31a"))//color of confirm button
                        .minYear(1900) //min year in loop
                        .maxYear(calendar.get(Calendar.YEAR) - 18) // max year in loop
                        .showDayMonthYear(false) // shows like dd mm yyyy (default is false)
                        .dateChose("2000-01-01") // date chose when init popwindow
                        .build();
//                datePickerPopWin.showPopWin(getActivity());
                datePickerPopWin.showAsDropDown(linDobBtn);*/

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

                if (isProfileEnabled) {
                    if (edtFirstName.getText().toString().equals("")) {
                        NewLeadActivity.firstName = "";
//                        edtFirstName.setError("* Please enter first name");
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

                if (isProfileEnabled) {
                    if (edtLastName.getText().toString().equals("")) {
                        NewLeadActivity.lastName = "";
//                        edtLastName.setError("* Please enter last name");
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

            if (edtFirstName.getText().toString().equals("")) {
                txtPersonalDetailsErrMsg.setText(" *Please enter first name");
//                edtFirstName.requestFocus();
            } else if (edtLastName.getText().toString().equals("")) {
                txtPersonalDetailsErrMsg.setText("*Please enter last name");
//                edtLastName.requestFocus();
            } else if (NewLeadActivity.gender.equals("")) {
                txtPersonalDetailsErrMsg.setText("*Please select gender");
//                linMaleBtn.requestFocus();
            } else if (NewLeadActivity.dob.equals("")) {
                txtPersonalDetailsErrMsg.setText("*Please enter your birthdate");
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
        String[] Name = NewLeadActivity.firstName.split(" ");

        if (Name.length > 1) {
            StringBuilder midname = new StringBuilder();
            for (int i = 1; i <= Name.length - 2; i++) {
                if (i == 1) {
                    midname.append(Name[i]);
                } else {
                    midname.append(" ");
                    midname.append(Name[i]);
                }
            }

            edtFirstName.setText(Name[0]);

            edtMiddleName.setText(midname);

            edtLastName.setText(Name[Name.length - 1]);

        } else {
            if (Name[0].equals("")) {
            } else {
                edtFirstName.setText(Name[0]);
            }
        }

        edtMiddleName.setText(NewLeadActivity.middleName);
        edtLastName.setText(NewLeadActivity.lastName);

        if (NewLeadActivity.gender.equals("1")) {
            linMaleBtn.performClick();
        } else if (NewLeadActivity.gender.equals("2")) {
            linFemaleBtn.performClick();
        } else if (NewLeadActivity.gender.equals("3")) {
            linOtherBtn.performClick();
        }
        String dob = NewLeadActivity.dob;
        //    txtDOB.setText(NewLeadActivity.dob);
        txtDOB.setText(Globle.dateFormaterForKarza(dob));
        NewLeadActivity.dob = txtDOB.getText().toString();

        if (NewLeadActivity.maritalStatus.equals("0") || NewLeadActivity.maritalStatus.equals("1")) {
            txtMaritalStatus.setText("Married");
        } else  //2
        {
            txtMaritalStatus.setText("Unmarried");
        }

        if (isProfileEnabled) {
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

                Intent intent = new Intent(getActivity(), ImgToPdfActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("strapplicantId", "0");
                bundle.putString("strapplicantType", "1");
                bundle.putString("documentTypeNo", "01");
                bundle.putString("toolbarTitle", "PAN OCR");
                bundle.putString("note", getString(R.string.applicant_pan_card));
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2

            }
        });

        linAadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ImgToPdfActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("strapplicantId", "0");
                bundle.putString("strapplicantType", "1");
                bundle.putString("documentTypeNo", "02");
                bundle.putString("toolbarTitle", "Aadhaar OCR");
                bundle.putString("note", getString(R.string.applicant_adhaar_card_front_and_backside));
                intent.putExtras(bundle);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());
                Uri selectedFileUri = data.getData();
                uploadFilePath = PathFile.getPath(context, selectedFileUri);
                Log.e("TAG", "onSelectFromGalleryResult: " + uploadFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static int getQualityNumber(Bitmap bitmap) {
        int size = bitmap.getByteCount();
        int percentage = 0;

        if (size > 500000 && size <= 800000) {
            percentage = 15;
        } else if (size > 800000 && size <= 1000000) {
            percentage = 20;
        } else if (size > 1000000 && size <= 1500000) {
            percentage = 25;
        } else if (size > 1500000 && size <= 2500000) {
            percentage = 27;
        } else if (size > 2500000 && size <= 3500000) {
            percentage = 30;
        } else if (size > 3500000 && size <= 4000000) {
            percentage = 40;
        } else if (size > 4000000 && size <= 5000000) {
            percentage = 50;
        } else if (size > 5000000) {
            percentage = 75;
        }

        return percentage;
    }

    public int uploadFile(String selectedFilePath) {
        String urlup = "https://api.karza.in/v3/ocr/kyc";
//        String urlup = "https://api.karza.in/v2/ocr/kyc";
//        String urlup = "https://testapi.karza.in/v3/ocr/kyc";//UAT

        Log.e(MainActivity.TAG, "urlup++++++: " + urlup);

        int serverResponseCode = 0;
        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);

        String[] parts = selectedFilePath.split("/");
        String fileName = parts[parts.length - 1];
        String[] fileExtn = fileName.split(".");

        if (!selectedFile.isFile()) {
            //dialog.dismiss();
            try {
                progressBar.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG", "run: " + "Source File Doesn't Exist: " + selectedFilePath);
                }
            });
            return 0;
        } else {
            try {

                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(urlup);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setChunkedStreamingMode(1024);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("x-karza-key", "POSJDFLJKNSFNJWD");
//                connection.setRequestProperty("x-karza-key", "RELemGo0j2pZ5rC3");//UAT
                connection.setRequestProperty("document", selectedFilePath);
                Log.e("TAG", "Server property" + connection.getRequestMethod() + ":property " + connection.getRequestProperties());
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dataOutputStream.write(buffer, 0, bufferSize);
                    Log.e("TAG", " here: \n\n" + buffer + "\n" + bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                Log.e("TAG", " here:server response serverResponseCode\n\n" + serverResponseCode);
                String serverResponseMessage = connection.getResponseMessage();
                Log.e("TAG", " here: server message serverResponseMessage \n\n" + serverResponseMessage.toString() + "\n" + bufferSize);
                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                String output = "";
                sb = new StringBuffer();

                while ((output = br.readLine()) != null) {
                    sb.append(output);
                    Log.e("TAG", "uploadFile0: " + br);
                }
                Log.e("TAG", "uploadFile: " + sb.toString());
                try {
                    JSONObject mJson = new JSONObject(sb.toString());
                    String mData = mJson.getString("statusCode");
                    String mData1 = mJson.getString("requestId");
//                    String mData = mJson.getString("status-code");
//                    String mData1 = mJson.getString("request_id");

                    switch (mData) {
                        case "101":
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Successful OCR", Toast.LENGTH_SHORT).show();
                                    try {
                                        cfAlertDialog.dismiss();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            JSONArray jsonOCRArray = mJson.getJSONArray("result");

                            for (int i = 0; i < jsonOCRArray.length(); i++) {

                                JSONObject jsonObject1 = jsonOCRArray.getJSONObject(i);

                                if (jsonObject1.getString("type").equals("Aadhaar Front Top")) {

                                    JSONObject jsonObjectData = jsonObject1.getJSONObject("details");

                                    if (jsonObjectData.getJSONObject("aadhaar").get("isMasked").toString().equals("no")) {
                                        if (!jsonObjectData.getJSONObject("aadhaar").get("value").toString().equals("")) {
                                            NewLeadActivity.Aaadhaarno = jsonObjectData.getJSONObject("aadhaar").get("value").toString();
                                        }
                                    }
                                    NewLeadActivity.Aname = jsonObjectData.getJSONObject("name").get("value").toString();
                                    NewLeadActivity.Aaddress = jsonObjectData.getJSONObject("address").get("value").toString();
                                    NewLeadActivity.Astreet_address = jsonObjectData.getJSONObject("addressSplit").get("line1").toString();
                                    NewLeadActivity.Adistrict = jsonObjectData.getJSONObject("addressSplit").get("city").toString();
                                    if (jsonObjectData.getJSONObject("addressSplit").get("district").toString().length() > 1) {
                                        NewLeadActivity.Adistrict = jsonObjectData.getJSONObject("addressSplit").get("district").toString();
                                    }
                                    NewLeadActivity.Astate = jsonObjectData.getJSONObject("addressSplit").get("state").toString();
                                    NewLeadActivity.Apincode = jsonObjectData.getJSONObject("pin").get("value").toString();
                                    NewLeadActivity.Aisscanned = "true";

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

                                }
                                if (jsonObject1.getString("type").equals("Aadhaar Front Bottom")) {

                                    JSONObject jsonObjectData = jsonObject1.getJSONObject("details");
                                    if (jsonObjectData.getJSONObject("aadhaar").get("isMasked").toString().equals("no")) {
                                        if (!jsonObjectData.getJSONObject("aadhaar").get("value").toString().equals("")) {
                                            NewLeadActivity.Aaadhaarno = jsonObjectData.getJSONObject("aadhaar").get("value").toString();
                                        }
                                    }
                                    NewLeadActivity.Aname = jsonObjectData.getJSONObject("name").get("value").toString();
                                    NewLeadActivity.Adob = jsonObjectData.getJSONObject("dob").get("value").toString();
                                    NewLeadActivity.Agender = jsonObjectData.getJSONObject("gender").get("value").toString();
                                    NewLeadActivity.Ayob = jsonObjectData.getJSONObject("yob").get("value").toString();
                                    NewLeadActivity.Aisscanned = "true";

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
                                }
                                if (jsonObject1.getString("type").equals("Aadhaar Back")) {

                                    JSONObject jsonObjectData = jsonObject1.getJSONObject("details");

                                    if (jsonObjectData.getJSONObject("aadhaar").get("isMasked").toString().equals("no")) {
                                        if (!jsonObjectData.getJSONObject("aadhaar").get("value").toString().equals("")) {
                                            NewLeadActivity.Aaadhaarno = jsonObjectData.getJSONObject("aadhaar").get("value").toString();
                                        }
                                    }
                                    NewLeadActivity.Aaddress = jsonObjectData.getJSONObject("address").get("value").toString();
                                    NewLeadActivity.Astreet_address = jsonObjectData.getJSONObject("addressSplit").get("line1").toString();
                                    NewLeadActivity.Adistrict = jsonObjectData.getJSONObject("addressSplit").get("city").toString();
                                    if (jsonObjectData.getJSONObject("addressSplit").get("district").toString().length() > 1) {
                                        NewLeadActivity.Adistrict = jsonObjectData.getJSONObject("addressSplit").get("district").toString();
                                    }
                                    NewLeadActivity.Astate = jsonObjectData.getJSONObject("addressSplit").get("state").toString();
                                    NewLeadActivity.Apincode = jsonObjectData.getJSONObject("pin").get("value").toString();
                                    NewLeadActivity.Aisscanned = "true";

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
                                }
                                if (jsonObject1.getString("type").equals("Pan")) {

                                    JSONObject jsonObjectData = jsonObject1.getJSONObject("details");

                                    NewLeadActivity.Ppanno = jsonObjectData.getJSONObject("panNo").get("value").toString();
                                    NewLeadActivity.Pname = jsonObjectData.getJSONObject("name").get("value").toString();
                                    NewLeadActivity.Pdob = jsonObjectData.getJSONObject("date").get("value").toString();
                                    NewLeadActivity.Pdoi = jsonObjectData.getJSONObject("dateOfIssue").get("value").toString();
                                    NewLeadActivity.Pisscanned = "true";

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

                                }

                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    saveOCRData(mJson.getString("statusCode").toString(), mJson.getString("requestId").toString(), mJson.getJSONArray("result").toString());
                                                    NewLeadActivity.setOcrData();
                                                    onResume();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, 0);

                                    }
                                });

                            }
                            break;
                        case "102":
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "No KYC Document identified", Toast.LENGTH_LONG).show();
                                }
                            });

                            break;
                        case "103":
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Image Format Not Supported OR Size Exceeds 6MB", Toast.LENGTH_LONG).show();
                                }
                            });

                            break;
                        case "104":
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Max retries exceeded", Toast.LENGTH_LONG).show();
                                }
                            });

                            break;
                        case "105":
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Missing Consent", Toast.LENGTH_LONG).show();
                                }
                            });

                            break;
                        case "106":
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Multiple Records Exist", Toast.LENGTH_LONG).show();
                                }
                            });

                            break;
                        case "107":
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Not Supported", Toast.LENGTH_LONG).show();
                                }
                            });

                            break;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
//                            Log.e("TAG", " 2285: " + new Date().toLocaleString());//1538546658896.jpg/
                        }
                    });

                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }

                if (serverResponseCode == 200) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
//                            Log.e("TAG", " 2303: " + new Date().toLocaleString());//1538546658896.jpg/
                        }
                    });
                }

                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            return serverResponseCode;
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 2) {
            if (resultCode == 2) {

                String message = data.getStringExtra("PATH");
                String doctypeno = data.getStringExtra("documentTypeNo");
                String strapplicantType = data.getStringExtra("strapplicantType");
                String strapplicantId = data.getStringExtra("strapplicantId");

                String FileExtn = null;
                Double FileSize = null;

                uploadFilePath = message;

                String filesz = JavaGetFileSize.getFileSizeMegaBytes(new File(message)).substring(0, JavaGetFileSize.getFileSizeMegaBytes(new File(message)).length() - 3);
                FileSize = Double.valueOf(filesz);

                FileExtn = uploadFilePath.substring(uploadFilePath.lastIndexOf(".") + 1);// Without dot jpg, png

                if (FileExtn.equals("jpg") || FileExtn.equals("jpeg") || FileExtn.equals("png") || FileExtn.equals("pdf")) {

                    if (FileSize <= 2) {
                        Log.e("TAG", "onActivityResult: DOC PATH " + uploadFilePath);

                        if (uploadFilePath != null) {
                            // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                            progressBar.setVisibility(View.VISIBLE);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        if (!Globle.isNetworkAvailable(context)) {
                                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                                        } else {
                                            uploadFile(uploadFilePath);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else {
                            Toast.makeText(context, R.string.please_choose_a_file_first, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, R.string.file_size_exceeds_limits_of_2_mb, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, R.string.file_is_not_in_supported_format, Toast.LENGTH_LONG).show();
                }

            }
        }
    }

    //2019-06-22 18:07:43.928 10443-13853/com.eduvanzapplication E/TAG: uploadFile: {"statusCode":101,"requestId":"8696ab40-94ea-11e9-98cb-cdeb9eba58a2","result":[{"type":"Aadhaar Front Bottom","details":{"qr":{"value":"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<PrintLetterBarcodeData uid=\"855065682173\" name=\"Vijaykumar Baijnath Shukla\" gender=\"M\" yob=\"1989\" house=\"306, tulshi complex\" street=\"kalyan road\" lm=\"near murlidhar temple\" loc=\"murlidhar compound\" vtc=\"Bhiwandi\" po=\"Dandekarwadi\" dist=\"Thane\" subdist=\"Bhiwandi\" state=\"Maharashtra\" pc=\"421302\" dob=\"17/12/1989\"/>"},"name":{"value":"Vijaykumar Baijnath Shukla"},"dob":{"value":"17/12/1989"},"gender":{"value":"MALE"},"father":{"value":""},"yob":{"value":""},"aadhaar":{"isMasked":"no","value":"855065682173"},"mother":{"value":""}}},{"type":"Aadhaar Back","details":{"qr":{"value":""},"pin":{"value":"421302"},"addressSplit":{"city":"Dandekarwadi","district":"Thane","pin":"421302","locality":"murlidhar compound, Bhiwandi","line2":"murlidhar compound, Bhiwandi, Dandekarwadi","line1":"306, tulshi complex, Kalyan road, near muridhar temple","state":"Maharashtra","street":"Kalyan road","landmark":"near muridhar temple","careOf":"","houseNumber":"306, tulshi complex"},"father":{"value":""},"aadhaar":{"isMasked":"no","value":"855065682173"},"address":{"value":"306, tulshi complex, Kalyan road, near muridhar temple, murlidhar compound, Bhiwandi, Dandekarwadi, Thane, Maharashtra, 421302 "},"husband":{"value":""}}}]}
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(boolean valid, int next);

        void onOffButtons(boolean next, boolean prev);
    }

}
