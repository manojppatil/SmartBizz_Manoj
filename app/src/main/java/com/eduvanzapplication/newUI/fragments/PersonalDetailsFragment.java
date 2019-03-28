package com.eduvanzapplication.newUI.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.CameraUtils;
import com.eduvanzapplication.newUI.newViews.DashboardActivity;
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

import static com.eduvanzapplication.newUI.MainApplication.TAG;
import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.viewPager;
public class PersonalDetailsFragment extends Fragment {

    private static ProgressBar progressbar;
    private static final String idfy_account_id = "99cde5a9e632/744939bd-4fe2-42e8-94d2-971a79928ee4";
    private static final String idfy_token = "2075c38b-31c3-4fc8-a642-ba7c02697c42";
    RFTSdk rftsdk;
    Bitmap bitmapFront = null, bitmapBack = null;
    String doctype = "";
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final String IMAGE_EXTENSION = "jpg";

    private TextInputLayout tilFirstName,tilMiddleName, tilLastName;
    private LinearLayout linMaleBtn, linFemaleBtn, linOtherBtn, linDobBtn;
    private Switch switchMarital;
    private TextView txtMaritalStatus;
    private LinearLayout linStudentBtn,linSalariedBtn, linSelfEmployedBtn;

    private static OnFragmentInteractionListener mListener;
    private TextView txtDOB;


    public PersonalDetailsFragment() {
        // Required empty public constructor
    }

    public static PersonalDetailsFragment newInstance(String param1, String param2) {
        PersonalDetailsFragment fragment = new PersonalDetailsFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);

        View professionView = getLayoutInflater().inflate(R.layout.layout_profession,null);
        linStudentBtn = professionView.findViewById(R.id.linStudentBtn);
        linSalariedBtn = professionView.findViewById(R.id.linSalariedBtn);
        linSelfEmployedBtn = professionView.findViewById(R.id.linSelfEmployedBtn);
        builder.setView(professionView);

        AlertDialog dialog = builder.create();
        dialog.show();

//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        wlp.gravity = Gravity.BOTTOM;
//        window.setAttributes(wlp);

        linStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.profession = "1";
                linStudentBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linSalariedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linSelfEmployedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        linSalariedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.profession = "2";
                linStudentBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linSalariedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linSelfEmployedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                dialog.dismiss();
            }
        });

        linSelfEmployedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.profession  ="3";
                linStudentBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linSalariedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linSelfEmployedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                dialog.dismiss();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_details, container, false);
        tilFirstName = view.findViewById(R.id.first_name);
        tilMiddleName =view.findViewById(R.id.middle_name);
        tilLastName =view.findViewById(R.id.last_name);
        linMaleBtn = view.findViewById(R.id.linMaleBtn);
        linFemaleBtn = view.findViewById(R.id.linFemaleBtn);
        linOtherBtn = view.findViewById(R.id.linOtherBtn);
        linDobBtn = view.findViewById(R.id.linDobBtn);
        txtDOB = view.findViewById(R.id.txtDOB);
        txtMaritalStatus = view.findViewById(R.id.txtMaritalStatus);
        switchMarital = view.findViewById(R.id.switchMarital);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linMaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.gender = "1";
                linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            }
        });

        linFemaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               NewLeadActivity.gender = "2";
                linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            }
        });

        linOtherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.gender = "2";
                linMaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linFemaleBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linOtherBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
            }
        });

        linDobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)-18);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity());
                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(Calendar.MONTH, month);
                            NewLeadActivity.dob = String.valueOf(dayOfMonth)+"-"+ (month+1) + "-"+year;
                            txtDOB.setText(NewLeadActivity.dob);
                            checkAllFields();
                        }
                    });
                    datePickerDialog.show();
                }
            }
        });

        switchMarital.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NewLeadActivity.maritalStatus = isChecked ? "1" : "0";
                if (isChecked)
                    txtMaritalStatus.setText("Married");
                else
                    txtMaritalStatus.setText("Unmarried");
                checkAllFields();
            }
        });

        tilFirstName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NewLeadActivity.firstName = tilFirstName.getEditText().getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tilMiddleName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NewLeadActivity.middleName = tilMiddleName.getEditText().getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tilLastName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NewLeadActivity.lastName = tilLastName.getEditText().getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void checkAllFields() {
        if (NewLeadActivity.firstName.equals("") || NewLeadActivity.lastName.equals("") || NewLeadActivity.middleName.equals("") || NewLeadActivity.dob.equals("")){
            mListener.onOffButtons(false, false);
        }else
            mListener.onOffButtons(true, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public static void validate() {
        if (NewLeadActivity.firstName.equals("") || NewLeadActivity.middleName.equals("") || NewLeadActivity.lastName.equals("") || NewLeadActivity.dob.equals(""))
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
        tilFirstName.getEditText().setText(NewLeadActivity.firstName);
        tilMiddleName.getEditText().setText(NewLeadActivity.middleName);
        tilLastName.getEditText().setText(NewLeadActivity.lastName);

        if (NewLeadActivity.gender.equals("1"))
            linMaleBtn.performClick();
        else if (NewLeadActivity.gender.equals("3"))
            linOtherBtn.performClick();
        else
            linFemaleBtn.performClick();

        txtDOB.setText(NewLeadActivity.dob);
        if (NewLeadActivity.maritalStatus.equals("0"))
            txtMaritalStatus.setText("Unmarried");
        else  //1
            txtMaritalStatus.setText("Married");
    }

    private void showOCRDialog() {

        View view = getLayoutInflater().inflate(R.layout.layout_ocr_options,null);
        LinearLayout linPan = view.findViewById(R.id.linPan);
        LinearLayout linAadhar = view.findViewById(R.id.linAadhar);
        LinearLayout linClose =view.findViewById(R.id.linClose);
        LinearLayout linFooter1 = view.findViewById(R.id.linFooter1);
        LinearLayout linTakePicture = view.findViewById(R.id.linTakePicture);
        LinearLayout linQR = view.findViewById(R.id.linQR);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getActivity())
                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                .setFooterView(view);

        CFAlertDialog cfAlertDialog = builder.show();
        cfAlertDialog.setCancelable(false);
        cfAlertDialog.setCanceledOnTouchOutside(false);
        linFooter1.setVisibility(View.VISIBLE);
        linTakePicture.setVisibility(View.GONE);
        linQR.setVisibility(View.GONE);


        linClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cfAlertDialog.dismiss();
            }
        });

        linPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linTakePicture.setVisibility(View.VISIBLE);
                linQR.setVisibility(View.GONE);
                linFooter1.setVisibility(View.GONE);




            }
        });

        linAadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linTakePicture.setVisibility(View.VISIBLE);
                linQR.setVisibility(View.VISIBLE);
                linFooter1.setVisibility(View.GONE);
            }
        });

        linTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linQR.getVisibility() != View.VISIBLE){  //pan is selected
                    if (CameraUtils.checkPermissions(getContext())) {
                        doctype = "ind_pan";
                        rftsdk.CaptureDocImage(getActivity(), "ind_pan", rftSdkCallbackInterface);
                    } else {
//                        requestCameraPermission(MEDIA_TYPE_IMAGE);
                    }

                }else{
                    if (CameraUtils.checkPermissions(getContext())) {
                        Toast.makeText(getActivity(), "Capture front-side image of Aadhaar", Toast.LENGTH_LONG).show();

                        doctype = "ind_aadhaar";
//                    doctype = "aadhaar_ocr";
                        rftsdk.CaptureDocImage(getActivity(), "ind_aadhaar", rftSdkCallbackInterface);
                    } else {
//                        requestCameraPermission(MEDIA_TYPE_IMAGE);
                    }

                }
            }
        });

    }

    private RftSdkCallbackInterface rftSdkCallbackInterface = new RftSdkCallbackInterface() {
        @Override
        public void onImageCaptureSuccess(final Bitmap image) {
            try {
                progressbar.setVisibility(View.VISIBLE);
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
            Toast.makeText(getActivity(), "Upload Failure, Response-> " + jsonObject.toString(), Toast.LENGTH_LONG).show();
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
                        progressbar.setVisibility(View.VISIBLE);
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

            Toast.makeText(getActivity(), "Upload succes, Response-> " + jsonObject.toString(), Toast.LENGTH_LONG).show();
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
                        progressbar.setVisibility(View.GONE);
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
                            progressbar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Boolean success = false;
                        try {
                            if (new JSONArray(response.toString()).getJSONObject(0).getString("status").toLowerCase().equals("completed")) {
                                success = true;
                            } else if (new JSONArray(response.toString()).getJSONObject(0).getString("status").toLowerCase().equals("in_progress")) {
                                success = false;

                                try {
                                    progressbar.setVisibility(View.VISIBLE);
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
                                Toast.makeText(getActivity(), "OCR Response-> " + response.toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (success) {
                            String panno = "";
                            try {
                                panno = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("pan_number");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String pantype = "";
                            try {
                                pantype = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("pan_type");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String name = "";
                            try {
                                name = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("name_on_card");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String dob = "";
                            try {
                                dob = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("date_on_card");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String doi = "";
                            try {
                                doi = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("date_of_issue");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String age = "";
                            try {
                                age = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("age");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String fathersname = "";
                            try {
                                fathersname = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("fathers_name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String isminor = "";
                            try {
                                isminor = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("minor");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String isscanned = "";
                            try {
                                isscanned = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("is_scanned");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


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
                            progressbar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getActivity(), "Upload Failed, Response-> " + error.toString(), Toast.LENGTH_LONG).show();
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

    private void getAadhaarData(String Requestid) {
        RequestQueue queue12 = Volley.newRequestQueue(getContext());//https://api.idfy.com/v2/tasks?request_id=d740cbd1-6af1-45f6-a609-8c2170dc3418
        StringRequest getRequest = new StringRequest(Request.Method.GET, "https://api.idfy.com/v2/tasks?request_id=" + Requestid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            progressbar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Boolean success = false;
                        try {
                            if (new JSONArray(response.toString()).getJSONObject(0).getString("status").toLowerCase().equals("completed")) {
                                success = true;
                            } else if (new JSONArray(response.toString()).getJSONObject(0).getString("status").toLowerCase().equals("in_progress")) {
                                success = false;

                                try {
                                    progressbar.setVisibility(View.VISIBLE);
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
                                Toast.makeText(getContext(), "OCR Response-> " + response.toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (success) {
                            String aadhaarno = "";
                            try {
                                aadhaarno = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("aadhaar_number");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String name = "";
                            try {
                                name = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("name_on_card");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String dob = "";
                            try {
                                dob = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("date_of_birth");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String yob = "";
                            try {
                                yob = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("year_of_birth");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String gender = "";
                            try {
                                gender = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("gender");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String address = "";
                            try {
                                address = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("address");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String street_address = "";
                            try {
                                street_address = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("street_address");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String district = "";
                            try {
                                district = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("gender");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String pincode = "";
                            try {
                                pincode = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("pincode");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String state = "";
                            try {
                                state = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("district");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String isscanned = "";
                            try {
                                isscanned = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("state");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Log.v(TAG, response);

//                            Toast.makeText(context, "Upload succes " + aadhaarno + "\n" + name + "\n" + dob + "\n" + yob + "\n" + gender, Toast.LENGTH_LONG).show();
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
                            progressbar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getActivity(), "Upload Failure, Response-> " + error.toString(), Toast.LENGTH_LONG).show();

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
