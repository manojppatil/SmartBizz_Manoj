package com.eduvanzapplication.newUI;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.newUI.fragments.AmortizationFragment;
import com.eduvanzapplication.newUI.fragments.CurrentAddressFragment;
import com.eduvanzapplication.newUI.fragments.DashboardFragmentNew;
import com.eduvanzapplication.newUI.fragments.DetailedInfoFragment;
import com.eduvanzapplication.newUI.fragments.KycDetailFragment;
import com.eduvanzapplication.newUI.fragments.PostApprovalDocFragment;
import com.eduvanzapplication.newUI.fragments.UploadDocumentFragment;
import com.eduvanzapplication.newUI.newViews.BannerActivity;
import com.eduvanzapplication.newUI.newViews.CourseDetailsActivity;
import com.eduvanzapplication.newUI.newViews.DashboardActivity;
import com.eduvanzapplication.newUI.newViews.EditProfile;
import com.eduvanzapplication.newUI.newViews.GetMobileNo;
import com.eduvanzapplication.newUI.newViews.GetMobileNo;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;
//import com.eduvanzapplication.newUI.newViews.Notification;
import com.eduvanzapplication.newUI.newViews.OtpValidation;
import com.eduvanzapplication.newUI.newViews.SplashScreen;
import com.eduvanzapplication.newUI.newViews.TenureSelectionActivity;
import com.eduvanzapplication.newUI.newViews.VideoKYC;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.eduvanzapplication.database.DBAdapter.ExecuteSql;


/**
 * Created by vijay on 23/1/17.
 */

public class VolleyCall {
    String screen, ErrorLogID;
    JSONObject jsonDataO;
    JSONArray jsonArrayData;
    AppCompatActivity mActivity;
    Activity aActivity;
    Fragment mfragment;
    Context context;
    public String auth_token = "";
    String TAG = "VolleyCall";
    String BOUNDARY = "s2retfgsGSRFsERFGHfgdfgw734yhFHW567TYHSrf4yarg"; //This the boundary which is used by the server to split the post parameters.
    String MULTIPART_FORMDATA = "multipart/form-data;boundary=" + BOUNDARY;


    public VolleyCall() {
    }

    public void sendRequest(Context mContext, final String url, AppCompatActivity activity, Fragment fragment, String callingString, final Map<String, String> dataForPost, String mauth_token) {
//        Toast.makeText(activity, "Volley called", Toast.LENGTH_SHORT).show();
        screen = callingString;
        mActivity = activity;
        aActivity = activity;
        mfragment = fragment;
        context = mContext;
        auth_token = mauth_token;
        Log.e(TAG, "sendRequest: " + dataForPost);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, url + " - onResponse: +++++++++++" + response);
                        showJSON(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, url + " - onErrorResponse: 1" + error.getMessage() + error.getLocalizedMessage());
//                        Toast.makeText(VolleyCall.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("user_id","2");
//                params.put("a","Hello");
//                params.put("file", );
                Log.e(TAG, "getParams: Data for this url is " + dataForPost);
                return dataForPost;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "6041c6c1d7c580619c796c25716bf9ed");
                headers.put("Authorization", "Bearer " + MainActivity.auth_token);
//                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }
                Log.e(TAG, "parseNetworkError: " + volleyError.getMessage());
                return volleyError;
            }
        };
        // if volley request is getting send more than once then use this.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 30, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue;
        try {
            if (mfragment == null) {
                requestQueue = Volley.newRequestQueue(mContext);
            } else {
                requestQueue = Volley.newRequestQueue(mContext);
            }
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRequest1(Context mContext, String url, AppCompatActivity activity, Fragment fragment, String callingString, final Map<String, String> dataForPost, String ErrorLogId) {
//        Toast.makeText(activity, "Volley called", Toast.LENGTH_SHORT).show();
        ErrorLogID = ErrorLogId;
        screen = callingString;
        mActivity = activity;
        aActivity = activity;
        mfragment = fragment;
        context = mContext;
        Log.e(TAG, "sendRequest: " + dataForPost);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "onResponse: +++++++++++" + response);
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: 1" + error.getMessage() + error.getLocalizedMessage());
//                        Toast.makeText(VolleyCall.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("user_id","2");
//                params.put("a","Hello");
//                params.put("file", );
                Log.e(TAG, "getParams: Data for this url is " + dataForPost);
                return dataForPost;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//auth_token":"90a876cf5617b74f1e034c6561669803
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("auth_token", "6041c6c1d7c580619c796c25716bf9ed");
                headers.put("Authorization", "Bearer " + auth_token);
//                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }
                Log.e(TAG, "parseNetworkError: " + volleyError.getMessage());
                return volleyError;
            }
        };
        // if volley request is getting send more than once then use this.
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 10, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue;
        try {
            if (mfragment == null) {
                requestQueue = Volley.newRequestQueue(mContext);
            } else {
                requestQueue = Volley.newRequestQueue(mContext);
            }
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showJSON(String s) {
        if (screen.equalsIgnoreCase("checkVersion")) {
            try {
                jsonDataO = new JSONObject(s);
                ((SplashScreen) mActivity).CheckVersion(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getOtp")) {
            try {
                jsonDataO = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ((GetMobileNo) mActivity).getOTPResponse(jsonDataO);
        } else if (screen.equalsIgnoreCase("otpLogin")) {
            try {
                jsonDataO = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ((GetMobileNo) mActivity).otpLoginResponse(jsonDataO);
        } else if (screen.equalsIgnoreCase("bannerDetail")) {
            try {
                jsonDataO = new JSONObject(s);
                ((BannerActivity) mActivity).setBannerDetail(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }//DateFormat.format("dd/MM/yyyy hh:mm a", Calendar.getInstance(Locale.ENGLISH).setTimeInMillis(1531722597904)).toString()
        } else if (screen.equalsIgnoreCase("generateOtpCode")) {
            try {
                jsonDataO = new JSONObject(s);
                ((GetMobileNo) mActivity).generateOtpCodeResponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("verifyOtpCode")) {
            try {
                jsonDataO = new JSONObject(s);
                ((GetMobileNo) mActivity).verifyOtpCodeResponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dashboardBanner")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardFragmentNew) mfragment).setDashboardImages(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("addAlgo360")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardActivity) mActivity).updateAlgo360Res(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getAddressFromPincode")) {
            try {
                jsonDataO = new JSONObject(s);
                ((CurrentAddressFragment) mfragment).setAddressFromPincode(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getAddressFromPincodeKyc")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).setAddressFromPincodeKyc(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getAddressFromPincodeDtl")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DetailedInfoFragment) mfragment).setAddressFromPincodeDtl(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("instituteId")) {
            try {
                jsonDataO = new JSONObject(s);
                ((CourseDetailsActivity) mActivity).instituteName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("courseId")) {
            try {
                jsonDataO = new JSONObject(s);
                ((CourseDetailsActivity) mActivity).courseName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("courseFee")) {
            try {
                jsonDataO = new JSONObject(s);
                ((CourseDetailsActivity) mActivity).courseFee(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("locationName")) {
            try {
                jsonDataO = new JSONObject(s);
                ((CourseDetailsActivity) mActivity).locationName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("eKycDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                ((VideoKYC) mActivity).setKycDetails(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("UploadeKycDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                ((VideoKYC) mActivity).setKycUploadResponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("studentDashbBoardDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardFragmentNew) mfragment).setstudentDashbBoardDetails(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("deletedocument")) {
            try {
                jsonDataO = new JSONObject(s);
                ((UploadDocumentFragment) mfragment).setdeleteFileStatus(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getLoanDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                ((PostApprovalDocFragment) mfragment).setLoanDetails(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("genrateManualAgreement")) {
            try {
                jsonDataO = new JSONObject(s);
                ((PostApprovalDocFragment) mfragment).setgenrateManualAgreement(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("genrateOTPAgreement")) {
            try {
                jsonDataO = new JSONObject(s);
                ((PostApprovalDocFragment) mfragment).setgenrateOTPAgreement(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("signedByOtpAgg")) {
            try {
                jsonDataO = new JSONObject(s);
                ((PostApprovalDocFragment) mfragment).submitOTPResponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("signedByOtpAggCo")) {
            try {
                jsonDataO = new JSONObject(s);
                ((PostApprovalDocFragment) mfragment).submitOTPResponseCo(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("genrateSignedAgreementUrl")) {
            try {
                jsonDataO = new JSONObject(s);
                ((PostApprovalDocFragment) mfragment).setSignedAgreementUrl(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("paytmPaymnetReponse")) {
            try {
                jsonDataO = new JSONObject(s);
                ((PostApprovalDocFragment) mfragment).paytmPaymnetReponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getCountriesCA")) {
            try {
                jsonDataO = new JSONObject(s);
                ((CurrentAddressFragment) mfragment).countryApiResponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getStatesCA")) {
            try {
                jsonDataO = new JSONObject(s);
                ((CurrentAddressFragment) mfragment).getStatesResponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getCityCA")) {
            try {
                jsonDataO = new JSONObject(s);
                ((CurrentAddressFragment) mfragment).getCityResponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("addborrower")) {
            try {
                jsonDataO = new JSONObject(s);
                ((NewLeadActivity) mActivity).addBorrowerResponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getTenureList")) {
            try {
                jsonDataO = new JSONObject(s);
                ((TenureSelectionActivity) mActivity).getTenureList(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("saveTenure")) {
            try {
                jsonDataO = new JSONObject(s);
                ((TenureSelectionActivity) mActivity).saveTenure(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("studentKycDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).setStudentKycDetails(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getDetailedInformation")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DetailedInfoFragment) mfragment).setDetailedInformation(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getCountriesKyc")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).countryApiResponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getStatesKyc")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).getCurrentStates(jsonDataO);
                //EligibilityCheckFragment_3 mfragment).getStates(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getCityKyc")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).getCurrentCities(jsonDataO);
                //EligibilityCheckFragment_3 mfragment).getStates(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("instituteIdkyc")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).instituteName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("courseIdkyc")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).courseName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("courseFeekyc")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).courseFee(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("locationNamekyc")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).locationName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getCountriesDtl")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DetailedInfoFragment) mfragment).countryApiResponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getDocumentsBorrower")) {
            try {
                jsonDataO = new JSONObject(s);
                ((UploadDocumentFragment) mfragment).getBorrowerDocuments(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getAmortDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                ((AmortizationFragment) mfragment).setAmortDetails(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getEmiTransactionDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                ((AmortizationFragment) mfragment).emiHistoryDialog(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("updateOTPData")) {
            try {
                jsonDataO = new JSONObject(s);
                ((OtpValidation) mActivity).UpdateOTPData();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        else if (screen.equalsIgnoreCase("updateTrueData")) {
//            try {
//                jsonDataO = new JSONObject(s);
//                ((SingInWithTruecaller) mActivity).UpdateTrueData(jsonDataO);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        else if (screen.equalsIgnoreCase("getOtpValidation")) {
            try {
                jsonDataO = new JSONObject(s);
                ((OtpValidation) mActivity).getOTPValidation(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("setOtpValidation")) {
            try {
                jsonDataO = new JSONObject(s);
                ((OtpValidation) mActivity).setOTPValidation(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getDeal")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardFragmentNew) mfragment).getDeal(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dealDetail")) {
            try {
                jsonDataO = new JSONObject(s);
                ((BannerActivity) mActivity).setDealDetail(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getAllRelationshipWithCoborrower")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_1 mfragment).getAllRelationshipWithCoborrower(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getAllProfession")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_4 mfragment).getAllProfession(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("saveInstitute")) {
            try {
                jsonDataO = new JSONObject(s);
                ((CourseDetailsActivity) mActivity).setsaveInstitute(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("saveTenure")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_6 mfragment).saveTenure(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getAllProfessionkyc")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).getAllProfessionkyc(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getAllProfessionkycCoBr")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).getAllProfessionkycCoBr(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("editKycDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).editKycDetailsResponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getAllProfessiondetailedinfo")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DetailedInfoFragment) mfragment).getAllProfessiondetailedinfo(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("editDetailedInformation")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DetailedInfoFragment) mfragment).editDetailedInfoResponse(jsonDataO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("instituteNamekyc")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).instituteName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("courseNamekyc")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).courseId(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("courseFeekyc")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).courseFee(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("locationNamekyc")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).locationName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("checkEligiblity")) {
            try {
                jsonDataO = new JSONObject(s);
//                //EligibilityCheckFragment_4 mfragment).checkEligiblity(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("addcoborrower")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_4 mfragment).setaddcoborrower(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("sendborrowerDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).sendBorrowerDetails(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("fromMain")) {
            try {
                jsonDataO = new JSONObject(s);
                //((LoanApplication) mActivity).fromMain(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("autoSave")) {
            try {
                jsonDataO = new JSONObject(s);
                //((LoanApplication) mActivity).fromMain(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        else if (screen.equalsIgnoreCase("coBorrowerLoanDetails")) {
//            try {
//                jsonDataO = new JSONObject(s);
//                //LoanApplicationFragment_2 mfragment).coBorrowerLoanDetails(jsonDataO);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (screen.equalsIgnoreCase("sendcoboorrowerDetails")) {
//            try {
//                jsonDataO = new JSONObject(s);
//                //LoanApplicationFragment_2 mfragment).sendCoborrowerPersonal(jsonDataO);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        else if (screen.equalsIgnoreCase("getDocumentsBorrower")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_3 mfragment).getBorrowerDocuments(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getCoBorrowerDocuments")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_3 mfragment).getCoBorrowerDocuments(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        else if (screen.equalsIgnoreCase("Notifications")) {
//            try {
//                jsonDataO = new JSONObject(s);
//                ((Notification) mActivity).getNotificationContent(jsonDataO);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        else if (screen.equalsIgnoreCase("studentDashbBoardStatus")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardFragmentNew) mfragment).setProfileDashbBoardStatus(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dashbBoardStatusBannerPage")) {
            try {
                jsonDataO = new JSONObject(s);
                ((BannerActivity) mActivity).setProfileDashbBoardStatus(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("fetchpincode")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_3 mfragment).getfetchpincode(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getCurrentStates")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).getCurrentStates(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getCurrentCity")) {
            try {
                jsonDataO = new JSONObject(s);
                ((KycDetailFragment) mfragment).getCurrentCities(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetCurrentStates")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DetailedInfoFragment) mfragment).dtlgetCurrentStates(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetCurrentCity")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DetailedInfoFragment) mfragment).dtlgetCurrentCity(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetPermanentStates")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DetailedInfoFragment) mfragment).dtlgetPermanentStates(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetPermanentCity")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DetailedInfoFragment) mfragment).dtlgetPermanentCities(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetOffStates")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DetailedInfoFragment) mfragment).dtlgetOffStates(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetOffCities")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DetailedInfoFragment) mfragment).dtlgetOffCities(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("editProfileDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                ((EditProfile) mActivity).saveResponse(jsonDataO);
            } catch (JsonIOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("setProfileDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                ((EditProfile) mActivity).setProfileDetails(jsonDataO);
            } catch (JsonIOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getStudentLaf")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_3 mfragment).getStudentLaf(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getDigioDocumentIdForStudent")) {
            try {
                jsonDataO = new JSONObject(s);
                ((PostApprovalDocFragment) mfragment).setDigioDocumentIdForStudent(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("onSuccessfulRegisterStudentESignCase")) {
            try {
                jsonDataO = new JSONObject(s);
                ((PostApprovalDocFragment) mfragment).onSuccessfulRegisterStudentESignCase(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getKycPaymentRelatedInfo")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_4 mfragment).getKycPaymentRelatedInfo(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getSignAndSubmitDetails")) {
            try {//{"result":{"docFinish":1,"lafDownloadPath":"http:\/\/159.89.204.41\/eduvanzbeta\/uploads\/lafdocumentstore\/610\/A180626002_Loan_Application_1530083542.pdf","signedApplicationStatus":0,"docPath":[],"paymentStatus":0,"transactionId":"","transactionAmount":800,"transactionDate":""},"status":0,"message":"failure"}
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_4 mfragment).getSignAndSubmitDetails(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("initializePaytmPayment")) {
            try {
                jsonDataO = new JSONObject(s);
                ((PostApprovalDocFragment) mfragment).initializePaytmPayment(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("initializePaytmPaymentAmort")) {
            try {
                jsonDataO = new JSONObject(s);
                ((AmortizationFragment) mfragment).initializePaytmPaymentAmort(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getUploadErrorLog")) {
            try {//{"result":{"docFinish":1,"lafDownloadPath":"http:\/\/159.89.204.41\/eduvanzbeta\/uploads\/lafdocumentstore\/610\/A180626002_Loan_Application_1530083542.pdf","signedApplicationStatus":0,"docPath":[],"paymentStatus":0,"transactionId":"","transactionAmount":800,"transactionDate":""},"status":0,"message":"failure"}
                String sSql = "Delete from ErrorLog WHERE errorLogID = '" + ErrorLogID + "'";
//                    String sSql = "Update ErrorLog set ISUploaded = '" + true + "' WHERE errorLogID = '"+ErrorLogID+"'" ;
                ExecuteSql(sSql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetOffCitiesCoBr")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetOffCitiesCoBr(jsonDataO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetOffStatesCoBr")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetOffStatesCoBr(jsonDataO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private String createPostBody(Map<String, String> params) {
        StringBuilder sbPost = new StringBuilder();
        if (params != null) {
            for (String key : params.keySet()) {
                if (params.get(key) != null) {
                    sbPost.append("\r\n" + "--" + BOUNDARY + "\r\n");
                    sbPost.append("Content-Disposition: form-data; name=\"" + key + "\"" + "\r\n\r\n");
                    sbPost.append(params.get(key).toString());
                }
            }
        }
        return sbPost.toString();
    }

}