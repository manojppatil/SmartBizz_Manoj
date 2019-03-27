package com.eduvanzapplication.newUI;

import android.app.Activity;
import android.app.Application;
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
import com.eduvanzapplication.newUI.fragments.DashboardFragmentNew;
import com.eduvanzapplication.newUI.newViews.BannerActivity;
import com.eduvanzapplication.newUI.newViews.DashboardActivity;
import com.eduvanzapplication.newUI.newViews.GetMobileNo;
import com.eduvanzapplication.newUI.newViews.MyProfileNew;
//import com.eduvanzapplication.newUI.newViews.NewTruecallerSignIn;
import com.eduvanzapplication.newUI.newViews.Notification;
import com.eduvanzapplication.newUI.newViews.OtpValidation;
import com.eduvanzapplication.newUI.newViews.SignIn;
import com.eduvanzapplication.newUI.newViews.SplashScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.eduvanzapplication.database.DBAdapter.ExecuteSql;

/**
 * Created by vijay on 23/1/17.
 */

public class VolleyCallNew extends Application {
    String screen,ErrorLogID;
    JSONObject jsonDataO;
    JSONArray jsonArrayData;
    AppCompatActivity mActivity;
    Activity aActivity;
    Fragment mfragment;
    Context context;
    public String auth_token ="";
    String TAG = "VolleyCall";
    String BOUNDARY = "s2retfgsGSRFsERFGHfgdfgw734yhFHW567TYHSrf4yarg"; //This the boundary which is used by the server to split the post parameters.
    String MULTIPART_FORMDATA = "multipart/form-data;boundary=" + BOUNDARY;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate:");
//
    }

    public void sendRequest(Context mContext, String url, AppCompatActivity activity, Fragment fragment, String callingString, final Map<String, String> dataForPost, String mauth_token) {
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
                        Log.e(TAG, "onResponse: +++++++++++"+response );
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
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "6041c6c1d7c580619c796c25716bf9ed");
                headers.put("Authorization","Bearer " + MainActivity.auth_token);
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
                        Log.e(TAG, "onResponse: +++++++++++"+response );
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
//                headers.put("Authorization", "6041c6c1d7c580619c796c25716bf9ed");
                headers.put("Authorization","Bearer " + auth_token);
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
        }

//        else if (screen.equalsIgnoreCase("updateTrueData")) {
//            try {
//                jsonDataO = new JSONObject(s);
//                ((NewTruecallerSignIn) mActivity).UpdateTrueData();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

        else if (screen.equalsIgnoreCase("updateOTPData")) {
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
        else if (screen.equalsIgnoreCase("getOtp")) {
            try {
                jsonDataO = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            ((GetMobileNo) mActivity).getOTP(jsonDataO);
        } else if (screen.equalsIgnoreCase("getOtpValidation")) {
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
        } else if (screen.equalsIgnoreCase("dashboardBanner")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardFragmentNew) mfragment).setDashboardImages(jsonDataO);
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
        } else if (screen.equalsIgnoreCase("bannerDetail")) {
            try {
                jsonDataO = new JSONObject(s);
                ((BannerActivity) mActivity).setBannerDetail(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }//DateFormat.format("dd/MM/yyyy hh:mm a", Calendar.getInstance(Locale.ENGLISH).setTimeInMillis(1531722597904)).toString()
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
        }else if (screen.equalsIgnoreCase("getAllProfession")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_4 mfragment).getAllProfession(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("saveInstitute")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_5 mfragment).setsaveInstitute(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("getTenureList")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_6 mfragment).getTenureList(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("saveTenure")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_6 mfragment).saveTenure(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("getAllProfessionkyc")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).getAllProfessionkyc(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("getAllProfessionkycCoBr")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).getAllProfessionkycCoBr(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("editKycDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).editKycDetailsResponse(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("getAllProfessiondetailedinfo")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).getAllProfessiondetailedinfo(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (screen.equalsIgnoreCase("getAllProfessiondetailedinfoCoBr")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).getAllProfessiondetailedinfoCoBr(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("instituteName")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_5 mfragment).instituteName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("courseName")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_5 mfragment).courseName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("courseFee")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_5 mfragment).courseFee(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("locationName")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_5 mfragment).locationName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (screen.equalsIgnoreCase("instituteNamekyc")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).instituteName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("courseNamekyc")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).courseName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("courseFeekyc")) {
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
        }
        else if (screen.equalsIgnoreCase("checkEligiblity")) {
            try {
                jsonDataO = new JSONObject(s);
//                //EligibilityCheckFragment_4 mfragment).checkEligiblity(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (screen.equalsIgnoreCase("addborrower")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_4 mfragment).setaddborrower(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("addcoborrower")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_4 mfragment).setaddcoborrower(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("studentKycDetails")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).setStudentKycDetails(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("getDetailedInformation")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).setDetailedInformation(jsonDataO);
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
        }
        else if (screen.equalsIgnoreCase("fromMain")) {
            try {
                jsonDataO = new JSONObject(s);
                //((LoanApplication) mActivity).fromMain(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (screen.equalsIgnoreCase("autoSave")) {
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
        else if (screen.equalsIgnoreCase("myProfile")) {
            try {
                jsonDataO = new JSONObject(s);
                ((MyProfileNew) mActivity).myProfile(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getDocumentsBorrower")) {
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
        } else if (screen.equalsIgnoreCase("Notifications")) {
            try {
                jsonDataO = new JSONObject(s);
                ((Notification) mActivity).getNotificationContent(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getRecentScrapping")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardActivity) mActivity).getScrappingdates(jsonDataO);
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
        } else if (screen.equalsIgnoreCase("studentDashbBoardStatus")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardFragmentNew) mfragment).setProfileDashbBoardStatus(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  else if (screen.equalsIgnoreCase("dashbBoardStatusBannerPage")) {
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
        }
        else if (screen.equalsIgnoreCase("getStates")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_3 mfragment).getStates(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getCity")) {
            try {
                jsonDataO = new JSONObject(s);
                //EligibilityCheckFragment_3 mfragment).getCity(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("getCurrentStates")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).getCurrentStates(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getCurrentCity")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).getCurrentCities(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (screen.equalsIgnoreCase("getCurrentStatesCoBr")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).getCurrentStatesCoBr(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("getCurrentCitiesCoBr")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_1 mfragment).getCurrentCitiesCoBr(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (screen.equalsIgnoreCase("dtlgetCurrentStates")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetCurrentStates(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetCurrentCity")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetCurrentCities(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (screen.equalsIgnoreCase("dtlgetPermanentStates")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetPermanentStates(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetPermanentCity")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetPermanentCities(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (screen.equalsIgnoreCase("dtlgetCurrentStatesCoBr")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetCurrentStatesCoBr(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetCurrentCitiesCoBr")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetCurrentCitiesCoBr(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (screen.equalsIgnoreCase("dtlgetPermanentStatesCoBr")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetPermanentStatesCoBr(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetPermanentCitiesCoBr")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetPermanentCitiesCoBr(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("dtlgetOffStates")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetOffStates(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetOffCities")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetOffCities(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (screen.equalsIgnoreCase("emailSignIn")) {
            try {
                jsonDataO = new JSONObject(s);
                ((SignIn) mActivity).emailSignin(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else if (screen.equalsIgnoreCase("getStudentLaf")) {
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
                //LoanApplicationFragment_4 mfragment).getDigioDocumentIdForStudent(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("onSuccessfulRegisterStudentESignCase")) {
            try {
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_4 mfragment).onSuccessfulRegisterStudentESignCase(jsonDataO);
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
        }else if (screen.equalsIgnoreCase("initializePaytmPayment")) {
            try {//{"result":{"docFinish":1,"lafDownloadPath":"http:\/\/159.89.204.41\/eduvanzbeta\/uploads\/lafdocumentstore\/610\/A180626002_Loan_Application_1530083542.pdf","signedApplicationStatus":0,"docPath":[],"paymentStatus":0,"transactionId":"","transactionAmount":800,"transactionDate":""},"status":0,"message":"failure"}
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_4 mfragment).initializePaytmPayment(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("getUploadErrorLog")) {
            try {//{"result":{"docFinish":1,"lafDownloadPath":"http:\/\/159.89.204.41\/eduvanzbeta\/uploads\/lafdocumentstore\/610\/A180626002_Loan_Application_1530083542.pdf","signedApplicationStatus":0,"docPath":[],"paymentStatus":0,"transactionId":"","transactionAmount":800,"transactionDate":""},"status":0,"message":"failure"}
                    String sSql = "Delete from ErrorLog WHERE errorLogID = '"+ErrorLogID+"'" ;
//                    String sSql = "Update ErrorLog set ISUploaded = '" + true + "' WHERE errorLogID = '"+ErrorLogID+"'" ;
                    ExecuteSql(sSql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (screen.equalsIgnoreCase("dtlgetOffCitiesCoBr")) {
            try {//{"result":{"docFinish":1,"lafDownloadPath":"http:\/\/159.89.204.41\/eduvanzbeta\/uploads\/lafdocumentstore\/610\/A180626002_Loan_Application_1530083542.pdf","signedApplicationStatus":0,"docPath":[],"paymentStatus":0,"transactionId":"","transactionAmount":800,"transactionDate":""},"status":0,"message":"failure"}
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetOffCitiesCoBr(jsonDataO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("dtlgetOffStatesCoBr")) {
            try {//{"result":{"docFinish":1,"lafDownloadPath":"http:\/\/159.89.204.41\/eduvanzbeta\/uploads\/lafdocumentstore\/610\/A180626002_Loan_Application_1530083542.pdf","signedApplicationStatus":0,"docPath":[],"paymentStatus":0,"transactionId":"","transactionAmount":800,"transactionDate":""},"status":0,"message":"failure"}
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).dtlgetOffStatesCoBr(jsonDataO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("editDetailedInformation")) {
            try {//{"result":{"docFinish":1,"lafDownloadPath":"http:\/\/159.89.204.41\/eduvanzbeta\/uploads\/lafdocumentstore\/610\/A180626002_Loan_Application_1530083542.pdf","signedApplicationStatus":0,"docPath":[],"paymentStatus":0,"transactionId":"","transactionAmount":800,"transactionDate":""},"status":0,"message":"failure"}
                jsonDataO = new JSONObject(s);
                //LoanApplicationFragment_2 mfragment).editDetailedInformation(jsonDataO);
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
