package com.eduvanzapplication.newUI.newViews;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.eduvanzapplication.BuildConfig;
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.adapter.LeadAdaptereKyc;
import com.eduvanzapplication.newUI.pojo.MKycLeads;
import com.facebook.FacebookSdk;
import com.khoslalabs.base.ViKycResults;
import com.khoslalabs.facesdk.FaceSdkModuleFactory;
import com.khoslalabs.ocrsdk.OcrSdkModuleFactory;
import com.khoslalabs.videoidkyc.ui.init.VideoIdKycInitActivity;
import com.khoslalabs.videoidkyc.ui.init.VideoIdKycInitRequest;
import com.khoslalabs.vikycapi.TimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eduvanzapplication.newUI.newViews.DashboardActivity.student_id;

public class VideoKYC extends AppCompatActivity {

    private final String TAG = VideoKYC.class.getSimpleName();
    private static final int VI_KYC_SDK_REQ_CODE = 7879;

    ProgressDialog progressDialog;
    public int GET_MY_PERMISSION = 1, permission;
    public static AppCompatActivity mActivity;

    public static LeadAdaptereKyc adaptereKyc;
    public static RecyclerView rvKYCLeads;
    public static LinearLayout linExperience, linFresher;
    public static String requestId = "", hash = "", ipadd = "", user_idKyc = "",
            transmission_datetime = "", lead_id = "", applicant_id = "";

    public static List<MKycLeads> mLeadseKycArrayList = new ArrayList<>();
    SharedPref sharedPref;

    Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_video_kyc);
        sharedPref = new SharedPref();
        mActivity = this;
        setViews();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR

        if (Build.VERSION.SDK_INT >= 23) {
            permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_SMS);

            if (permission != PackageManager.PERMISSION_GRANTED) {//Direct Permission without disclaimer dialog
                ActivityCompat.requestPermissions(VideoKYC.this,
                        new String[]{
                                Manifest.permission.CAMERA},
                        GET_MY_PERMISSION);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void setViews() {
        progressDialog = new ProgressDialog(VideoKYC.this);

        rvKYCLeads = findViewById(R.id.rvKYCLeads);

        linExperience = findViewById(R.id.linExperience);
        linFresher = findViewById(R.id.linFresher);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VideoKYC.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvKYCLeads.setLayoutManager(linearLayoutManager);
        rvKYCLeads.setHasFixedSize(true);
        adaptereKyc = new LeadAdaptereKyc(mLeadseKycArrayList, VideoKYC.this);
        rvKYCLeads.setAdapter(adaptereKyc);
//        rvKYCLeads.setNestedScrollingEnabled(true);

        try {
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            if (!mActivity.isFinishing())
                progressDialog.show();

            String url = MainActivity.mainUrl + "dashboard/getKycInstantList";
            Map<String, String> params = new HashMap<String, String>();
            params.put("studentId", student_id);//3303
            if (!Globle.isNetworkAvailable(VideoKYC.this)) {
                Toast.makeText(VideoKYC.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(VideoKYC.this, url, mActivity, null, "eKycDetails", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //this below code for After Entered OTP keyboard is closed
    }

    public void setKycDetails(JSONObject jsonDataO) {
//        Log.e(TAG, "setProfileDashbBoardStatus: " + jsonDataO);
        try {
            progressDialog.dismiss();
            if (jsonDataO.getInt("status") == 1) {

                JSONObject mObject = jsonDataO.optJSONObject("result");
                String message = jsonDataO.getString("message");

                JSONArray jsonArray1 = mObject.getJSONArray("leadsData");
                mLeadseKycArrayList = new ArrayList<>();
                if (jsonArray1.length() == 0) {
                    linExperience.setVisibility(View.GONE);
                    linFresher.setVisibility(View.VISIBLE);
                } else {
                    linExperience.setVisibility(View.VISIBLE);
                    linFresher.setVisibility(View.GONE);
                }
                for (int i = 0; i < jsonArray1.length(); i++) {
                    MKycLeads mLeads = new MKycLeads();
                    JSONObject jsonleadStatus = jsonArray1.getJSONObject(i);

                    try {

                        if (!jsonleadStatus.getString("lead_id").toString().equals("null"))
                            mLeads.lead_id = jsonleadStatus.getString("lead_id");

                        if (!jsonleadStatus.getString("application_id").toString().equals("null"))
                            mLeads.application_id = jsonleadStatus.getString("application_id");

                        if (!jsonleadStatus.getString("has_coborrower").toString().equals("null"))
                            mLeads.has_coborrower = jsonleadStatus.getString("has_coborrower");

                        if (!jsonleadStatus.getString("applicant_id").toString().equals("null"))
                            mLeads.applicant_id = jsonleadStatus.getString("applicant_id");

                        if (!jsonleadStatus.getString("fk_applicant_type_id").toString().equals("null"))
                            mLeads.fk_applicant_type_id = jsonleadStatus.getString("fk_applicant_type_id");

                        if (!jsonleadStatus.getString("first_name").toString().equals("null"))
                            mLeads.first_name = jsonleadStatus.getString("first_name");

                        if (!jsonleadStatus.getString("middle_name").toString().equals("null"))
                            mLeads.middle_name = jsonleadStatus.getString("middle_name");

                        if (!jsonleadStatus.getString("last_name").toString().equals("null"))
                            mLeads.last_name = jsonleadStatus.getString("last_name");

                        if (!jsonleadStatus.getString("student_id").toString().equals("null"))
                            mLeads.student_id = jsonleadStatus.getString("student_id");

                        try {
                            if (!jsonleadStatus.getString("co_instantKyc").toString().equals("null"))
                                mLeads.co_instantKyc = jsonleadStatus.getString("co_instantKyc");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            if (!jsonleadStatus.getString("bo_instantKyc").toString().equals("null"))
                                mLeads.bo_instantKyc = jsonleadStatus.getString("bo_instantKyc");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mLeadseKycArrayList.add(mLeads);
                }
                adaptereKyc = new LeadAdaptereKyc(mLeadseKycArrayList, VideoKYC.this);
                rvKYCLeads.setAdapter(adaptereKyc);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setKycUploadResponse(JSONObject jsonDataO) {
        try {
            progressDialog.dismiss();
            String message = jsonDataO.getString("message");

            if (jsonDataO.getString("status").equals("FAIL")) {

                JSONObject mObject = jsonDataO.optJSONObject("result");
                Toast.makeText(VideoKYC.this, message, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(VideoKYC.this, message, Toast.LENGTH_LONG).show();
                try {
                    progressDialog.setMessage("Loading");
                    progressDialog.setCancelable(false);
                    if (!mActivity.isFinishing())
                        progressDialog.show();

                    String url = MainActivity.mainUrl + "dashboard/getKycInstantList";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("studentId", student_id);//3303
                    if (!Globle.isNetworkAvailable(VideoKYC.this)) {
                        Toast.makeText(VideoKYC.this, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                    } else {
                        VolleyCall volleyCall = new VolleyCall();
                        volleyCall.sendRequest(VideoKYC.this, url, mActivity, null, "eKycDetails", params, MainActivity.auth_token);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public String getOtpType() {
//        if ("".equals(edtMobile.getText().toString())) {
//            if ("".equals(edtEmail.getText().toString())) {
//                return null;
//            }
//            return "EMAIL";
//        }
//        return "MOB_NO";
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == VI_KYC_SDK_REQ_CODE) {

            if (resultCode == ViKycResults.RESULT_OK
                    || resultCode == ViKycResults.RESULT_DOC_COMPLETE) {

                if (data != null) {

//                    String userId = data.getStringExtra(ViKycConstants.KEY_USER_ID);  //here user id is taken, will be used by client to get the KYCinfo block of user
                    String userId = data.getStringExtra("user_id");//771bf850-5c33-483b-9dd4-d167c381ad97  //here user id is taken, will be used by client to get the KYCinfo block of user
                    Log.e("TAG", "userId " + userId);
                    user_idKyc = userId;//fe7d0c14-ca0a-4e50-b4af-b086d5ba181a
                    try {
                        hash = calculateHash(user_idKyc);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    if (userId != null) {
/**
 * here we have are calling one activity method(startkycDataActivity) as demo to process the KYCinfo block
 */
                        try {
                            progressDialog.setMessage("Loading");
                            progressDialog.setCancelable(false);
                            if (!mActivity.isFinishing())
                                progressDialog.show();

                            JSONObject headers = new JSONObject();
                            try {
                                headers.put("client_code", Globle.clientCode);
                                headers.put("sub_client_code", Globle.clientCode);
                                headers.put("actor_type", "CUSTOMER");
                                headers.put("channel_code", "ANDROID_SDK");
                                headers.put("stan", TimeUtil.getCurTimeMilisec() + "");
                                headers.put("user_handle_type", "EMAIL");
                                headers.put("user_handle_value", TimeUtil.getCurTimeMilisec() + "");
                                headers.put("location", "");
                                headers.put("transmission_datetime", transmission_datetime);
                                headers.put("run_mode", "REAL");
                                headers.put("client_ip", ipadd);
                                headers.put("operation_mode", "SELF");
                                headers.put("channel_version", "0.0.1");
                                headers.put("function_code", "REVISED");
                                headers.put("function_sub_code", "DEFAULT");

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            JSONObject request = new JSONObject();
                            try {
                                request.put("api_key", Globle.apiKey);
                                request.put("request_id", requestId);
                                request.put("user_id", user_idKyc);
                                request.put("hash", hash);

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            JSONObject Jreq0 = new JSONObject();
                            Jreq0.put("headers", headers);
                            Jreq0.put("request", request);


                            JSONObject Jreq = new JSONObject();

                            Jreq.put("lead_id", lead_id);
                            Jreq.put("user_id", student_id);
                            Jreq.put("applicant_id", applicant_id);
                            Jreq.put("request", Jreq0);
//                            Jreq.put("headers", headers);
//                            Jreq.put("request", request);

                            //{
                            //  "headers": {
                            //    "client_code": "EDDU2319",
                            //    "sub_client_code": "EDDU2319",
                            //    "actor_type": "CUSTOMER",
                            //    "channel_code": "ANDROID_SDK",
                            //    "stan": "801509769813",
                            //    "user_handle_type": "EMAIL",
                            //    "user_handle_value": "shuklavijay249@gmail.com",
                            //    "location": "",
                            //    "transmission_datetime": "1569419974741",
                            //    "run_mode": "REAL",
                            //    "client_ip": "192.168.1.10",
                            //    "operation_mode": "SELF",
                            //    "channel_version": "0.0.1",
                            //    "function_code": "REVISED",
                            //    "function_sub_code": "DEFAULT"
                            //  },
                            //  "request": {
                            //    "api_key": "gq991xYd",
                            //    "request_id": "EDDU2319-1569421046490",
                            //    "user_id": "3e02d2c0-dbb8-469d-a4ab-dc3c918e4487",
                            //    "hash": "36c17b4c8ce9bf0702f28afab2654cee49c4e20d1a8793a0e1e7e064c41b70e0"
                            //  }
                            //}

                            RequestQueue queue = Volley.newRequestQueue(VideoKYC.this);

                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                    MainActivity.mainUrl + "document/uploadInstantKyc", Jreq,
                                    new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(final JSONObject response) {
//                            Log.d(TAG, response.toString());
                                            setKycUploadResponse(response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    VolleyLog.d(TAG, "Error: " + error.getMessage()); //com.android.volley.ClientError
                                    try {
                                        progressDialog.dismiss();
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
                                    headers.put("Authorization", "Bearer " + MainActivity.auth_token);
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

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    showToast("Video Id KYC was not completed!");
                }

            } else {

                if (data != null) {

//                    int code = data.getIntExtra(ViKycConstants.KEY_ERROR_CODE, 333);
//                    String msg = data.getStringExtra(ViKycConstants.KEY_ERROR_MESSAGE);

                    int code = data.getIntExtra("error_code", 333);//380049
                    String msg = data.getStringExtra("error_message");//Invalid client_code

                    if (msg != null) {
                        showToast(code + " : " + msg);
                    }

                } else {
                    showToast("Video Id KYC was not completed!");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 1:
                if (grantResults.length <= 0) {
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
//                    apiCall();
                } else {
                    //not granted

                    try {
//                            button.setEnabled(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Snackbar.make(
                            findViewById(R.id.rootViews),
                            R.string.permission_denied_explanation,
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.settings, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Build intent that displays the App settings screen.
                                    Intent intent = new Intent();
                                    intent.setAction(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package",
                                            BuildConfig.APPLICATION_ID, null);
                                    intent.setData(uri);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                            .show();
                }
                break;
        }

    }

    /**
     * using userId client can implement their own mechanism to get KYCinfo block
     * here we have just implemented one activity method(startkycDataActivity) as demo
     */

    private void startKycDataActivity(String userId,
                                      String clientCode,
                                      String apiKey,
                                      String salt) {
//        Intent i = new Intent(this, KycDataActivity.class);
//        i.putExtra("user_id", userId);
//        i.putExtra("client_code", clientCode);
//        i.putExtra("api_key", apiKey);
//        i.putExtra("salt", salt);
//        startActivity(i);
    }


    private void showToast(String s) {
        Toast.makeText(VideoKYC.this, s + "", Toast.LENGTH_LONG).show();
    }

    private String calculateHash(String user_idKyc)
            throws NoSuchAlgorithmException {

        MessageDigest digest;

        digest = MessageDigest.getInstance("SHA-256");
        if (digest != null) {

            byte[] hash =
                    digest.digest((Globle.clientCode + "|" + user_idKyc + "|" + Globle.apiKey + "|" + Globle.salt).getBytes());

            return bytesToHex(hash);
        }

        return null;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}

// {
//         "headers":{
//         "client_code":"EDDU2319",
//         "sub_client_code":"EDDU2319",
//         "actor_type":"CUSTOMER",
//         "channel_code":"ANDROID_SDK",
//         "stan":"8983501513",
//         "user_handle_type":"EMAIL",
//         "user_handle_value":"shuklavijay249@gmail.com",
//         "location":"",
//         "transmission_datetime":"1569419974741",
//         "run_mode":"REAL",
//         "client_ip":"192.168.1.10",
//         "operation_mode":"SELF",
//         "channel_version":"0.0.1",
//         "function_code":"REVISED",
//         "function_sub_code":"DEFAULT"
//         },
//         "request":{
//         "api_key":"gq991xYd",
//         "request_id":"EDDU2319-1569421046490",
//         "user_id":"3e02d2c0-dbb8-469d-a4ab-dc3c918e4487"
//         “hash” : “E1D1A6D90ED4EA27B76259D380559F05AC805F114206A1A8576B93D2AB1E5077”
//         }
//         }

//        {
//        "headers":{
//        "client_code":"EDDU2319",
//        "sub_client_code":"EDDU2319",
//        "actor_type":"NA",
//        "channel_code":"ANDROID_SDK",
//        "stan":"123",
//        "user_handle_type":"ANDROID_SDK",
//        "user_handle_value":"ANDROID_SDK",
//        "location":"Mumbai",
//        "transmission_datetime":"1569419974741",
//        "run_mode":"REAL",
//        "client_ip":"192.168.0.1",
//        "operation_mode":"SELF",
//        "channel_version":"0.0.1",
//        "function_code":"REVISED",
//        "function_sub_code":"DEFAULT"
//        },
//        "request":{
//        "api_key":"gq991xYd",
//        "request_id":"EDDU2319-1569419974741",
//        "user_id":"12d590fd-add9-4526-b5e0-4e0b84e91aca"
//        “hash” : “B07A69122859D7A3BD7983CA4AC8179079428204AAA71FE92FDEEB2985BE6939”
//        }
//        }

//{"lead_id":"14217","user_id":"12769","headers":{"client_code":"EDDU2319","sub_client_code":"EDDU2319","actor_type":"CUSTOMER","channel_code":"ANDROID_SDK","stan":"9898989898","user_handle_type":"EMAIL","user_handle_value":"","location":"","transmission_datetime":"1569578400762","run_mode":"REAL","client_ip":"192.168.1.24","operation_mode":"SELF","channel_version":"0.0.1","function_code":"REVISED","function_sub_code":"DEFAULT"},"request":{"api_key":"gq991xYd","request_id":"EDDU2319-1569578400762","user_id":"fe7d0c14-ca0a-4e50-b4af-b086d5ba181a","hash":"C7AA1B0AC756FF2BC36662811F80751F11BA38B668E35AE8598FEA48AAA59E05"}}