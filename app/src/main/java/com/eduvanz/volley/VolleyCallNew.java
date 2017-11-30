package com.eduvanz.volley;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.eduvanz.DashBoardFragment;
import com.eduvanz.Login;
import com.eduvanz.MyProfile;
import com.eduvanz.fqform.borrowerdetail.BorrowerEducationFragment;
import com.eduvanz.fqform.borrowerdetail.BorrowerPersonalFragment;
import com.eduvanz.fqform.borrowerdetail.BorrowerProfessionNFinancialFragment;
import com.eduvanz.fqform.coborrowerdetail.CoBorrowerFinancialFragment;
import com.eduvanz.fqform.coborrowerdetail.CoBorrowerPersonalFragment;
import com.eduvanz.newUI.fragments.DashboardFragmentNew;
import com.eduvanz.newUI.fragments.EligibilityCheckFragment_1;
import com.eduvanz.newUI.fragments.EligibilityCheckFragment_2;
import com.eduvanz.newUI.fragments.EligibilityCheckFragment_4;
import com.eduvanz.newUI.fragments.LoanApplicationFragment_1;
import com.eduvanz.newUI.fragments.LoanApplicationFragment_2;
import com.eduvanz.newUI.fragments.LoanApplicationFragment_3;
import com.eduvanz.newUI.fragments.LoanApplicationFragment_4;
import com.eduvanz.newUI.newViews.BannerActivity;
import com.eduvanz.newUI.newViews.DashboardActivity;
import com.eduvanz.newUI.newViews.GetMobileNo;
import com.eduvanz.newUI.newViews.LoanApplication;
import com.eduvanz.newUI.newViews.MyProfileNew;
import com.eduvanz.newUI.newViews.Notification;
import com.eduvanz.newUI.newViews.OtpValidation;
import com.eduvanz.pqformfragments.PqFormFragment1;
import com.eduvanz.pqformfragments.PqFormFragment3;
import com.eduvanz.pqformfragments.SuccessAfterPQForm;
import com.eduvanz.uploaddocs.UploadActivityBorrower;
import com.eduvanz.uploaddocs.UploadActivityCoBorrower;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by nikhil on 23/1/17.
 */

public class VolleyCallNew extends Application {
    String screen;
    JSONObject jsonDataO;
    JSONArray jsonArrayData;
    AppCompatActivity mActivity;
    Activity aActivity;
    Fragment mfragment;
    Context context;
    String TAG="VolleyCall";
    String BOUNDARY = "s2retfgsGSRFsERFGHfgdfgw734yhFHW567TYHSrf4yarg"; //This the boundary which is used by the server to split the post parameters.
    String MULTIPART_FORMDATA = "multipart/form-data;boundary=" + BOUNDARY;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate:" );
    }
    public void sendRequest(Context mContext, String url, AppCompatActivity activity, Fragment fragment, String callingString, final Map<String,String> dataForPost){
//        Toast.makeText(activity, "Volley called", Toast.LENGTH_SHORT).show();
        screen = callingString;
        mActivity=activity;
        aActivity=activity;
        mfragment=fragment;
        context=mContext;
        Log.e(TAG, "sendRequest: "+dataForPost );
//        /*
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: 1"+error.getMessage() +error.getLocalizedMessage() );
//                        Toast.makeText(VolleyCall.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }){
            @Override
            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("user_id","2");
//                params.put("a","Hello");
//                params.put("file", );
                Log.e(TAG, "getParams: Data for this url is "+dataForPost);
                return dataForPost;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError){
                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }
                Log.e(TAG, "parseNetworkError: "+volleyError.getMessage() );
                return volleyError;
            }

            //            @Override
//            public byte[] getBody() throws AuthFailureError {
//                return createPostBody(getParams()).getBytes();
//            }

//            @Override
//            public String getBodyContentType() {
//
//                return MULTIPART_FORMDATA;
//            }
        };
        RequestQueue requestQueue;
        try
        {
            if(mfragment == null) {
                requestQueue = Volley.newRequestQueue(mContext);
            }else {
                requestQueue = Volley.newRequestQueue(mContext);
            }
            requestQueue.add(stringRequest);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

//        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    //--------------------------------------FOR ACTIVITY------------------------------------------//
    public void sendRequestActivity(Context mContext, String url, Activity activity, Fragment fragment, String callingString, final Map<String,String> dataForPost){
//        Toast.makeText(activity, "Volley called", Toast.LENGTH_SHORT).show();
        screen = callingString;
        aActivity=activity;
        mfragment=fragment;
        context=mContext;
        Log.e(TAG, "sendRequest: "+dataForPost );
//        /*
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: 1"+error.getMessage() +error.getLocalizedMessage() );
//                        Toast.makeText(VolleyCall.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }){
            @Override
            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("user_id","2");
//                params.put("a","Hello");
//                params.put("file", );
                Log.e(TAG, "getParams: Data for this url is "+dataForPost);
                return dataForPost;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError){
                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }
                Log.e(TAG, "parseNetworkError: "+volleyError.getMessage() );
                return volleyError;
            }

        };

        RequestQueue requestQueue;
        try
        {
            if(mfragment == null) {
                requestQueue = Volley.newRequestQueue(mContext);
            }else {
                requestQueue = Volley.newRequestQueue(mContext);
            }
            requestQueue.add(stringRequest);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void sendRequestforActivity(Context mContext, String url, Activity act, Fragment fragment, String callingString, final Map<String,String> dataForPost){
//        Toast.makeText(activity, "Volley called", Toast.LENGTH_SHORT).show();
        screen = callingString;
//        activity = act;
        mfragment=fragment;
        context=mContext;
        Log.e(TAG, "sendRequest: "+dataForPost );
//        /*
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: 1"+error.getMessage() +error.getLocalizedMessage() );
//                        Toast.makeText(VolleyCall.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }){
            @Override
            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("user_id","2");
//                params.put("a","Hello");
//                params.put("file", );
                Log.e(TAG, "getParams: Data for this url is "+dataForPost);
                return dataForPost;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError){
                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }
                Log.e(TAG, "parseNetworkError: "+volleyError.getMessage() );
                return volleyError;
            }

            //            @Override
//            public byte[] getBody() throws AuthFailureError {
//                return createPostBody(getParams()).getBytes();
//            }

//            @Override
//            public String getBodyContentType() {
//
//                return MULTIPART_FORMDATA;
//            }
        };
//        */

//        we can send json array in POST request i.e. null here
//        JsonArrayRequest req = new JsonArrayRequest(Request.Method.POST,url, null,new Response.Listener<JSONArray> () {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    Log.v("Response:%n %s", response.toString(4));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                VolleyLog.e("Error: ", error.getMessage());
//            }
//        });
        RequestQueue requestQueue;
        try
        {
            if(mfragment == null) {
                requestQueue = Volley.newRequestQueue(mContext);
            }else {
                requestQueue = Volley.newRequestQueue(mContext);
            }
            requestQueue.add(stringRequest);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

//        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showJSON(String s) {
//        Log.e(TAG, "showJSON: "+response );
        if(screen.equalsIgnoreCase("getOtp"))
        {
            try {

                jsonDataO = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ((GetMobileNo)mActivity).getOTP(jsonDataO);
        }

        else if(screen.equalsIgnoreCase("getOtpValidation"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((OtpValidation)mActivity).getOTPValidation(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("setOtpValidation"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((OtpValidation)mActivity).setOTPValidation(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("dashboardBanner"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardFragmentNew)mfragment).setDashboardImages(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("getDeal"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardFragmentNew)mfragment).getDeal(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("bannerDetail"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((BannerActivity)mActivity).setBannerDetail(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("dealDetail"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((BannerActivity)mActivity).setDealDetail(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("instituteName"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((EligibilityCheckFragment_1)mfragment).instituteName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("courseName"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((EligibilityCheckFragment_1)mfragment).courseName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("locationName"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((EligibilityCheckFragment_1)mfragment).locationName(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("courseFee"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((EligibilityCheckFragment_2)mfragment).courseFee(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("checkEligiblity"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((EligibilityCheckFragment_4)mfragment).checkEligiblity(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("borrowerLoanDetails"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((LoanApplicationFragment_1)mfragment).borrowerLoanDetails(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("sendborrowerDetails"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((LoanApplicationFragment_1)mfragment).sendBorrowerDetails(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("fromMain"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((LoanApplication)mActivity).fromMain(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("coBorrowerLoanDetails"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((LoanApplicationFragment_2)mfragment).coBorrowerLoanDetails(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("sendcoboorrowerDetails"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((LoanApplicationFragment_2)mfragment).sendCoborrowerPersonal(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("myProfile"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((MyProfileNew)mActivity).myProfile(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("getDocumentsBorrower"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((LoanApplicationFragment_3)mfragment).getBorrowerDocuments(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("getCoBorrowerDocuments"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((LoanApplicationFragment_3)mfragment).getCoBorrowerDocuments(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(screen.equalsIgnoreCase("Notifications"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((Notification)mActivity).getNotificationContent(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(screen.equalsIgnoreCase("getRecentScrapping"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardActivity)mActivity).getScrappingdates(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(screen.equalsIgnoreCase("StudentDashbBoardStatus"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardActivity)mActivity).setProfileDashbBoardStatus(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(screen.equalsIgnoreCase("ProfileDashbBoardStatusData"))
        {
            try {
                jsonDataO = new JSONObject(s);
                ((MyProfileNew)mActivity).setProfileDashbBoardStatusData(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
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
