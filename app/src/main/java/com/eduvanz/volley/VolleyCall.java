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
import com.eduvanz.pqformfragments.PqFormFragment1;
import com.eduvanz.pqformfragments.PqFormFragment2;
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

public class VolleyCall extends Application {
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
        if(screen.equalsIgnoreCase("PrefillInstitutesFragment1"))
        {
            try {

                jsonDataO = new JSONObject(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("CallForData", "LOGIN PAGE " + s);
            ((PqFormFragment1)mfragment).prefillInstitutesFragment1(jsonDataO);
        }

        else if(screen.equalsIgnoreCase("PrefillCourseFragment1"))
        {
            try {
                Log.e("CallForData", "PrefillCourseFragment1"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "PrefillCourseFragment1"+ s);
                ((PqFormFragment1)mfragment).PrefillCourseFragment1(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

//        else if(screen.equalsIgnoreCase("prefillLocationsFragment2"))
//        {
//            try {
//                Log.e("CallForData", "prefillLocationsFragment2"+ s);
//                jsonDataO = new JSONObject(s);
//                Log.e("CallForData", "prefillLocationsFragment2"+ s);
//                ((PqFormFragment2)mfragment).prefillLocationsFragment2(jsonDataO);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }

        else if(screen.equalsIgnoreCase("prefillLocationsFragment2"))
        {
            try {
                Log.e("CallForData", "prefillLocationsFragment2"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "prefillLocationsFragment2"+ s);
                ((PqFormFragment1)mfragment).prefillLocationsFragment2(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("prefillLoanAmountFragment3"))
        {
            try {
                Log.e("CallForData", "prefillLoanAmountFragment3"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "prefillLoanAmountFragment3"+ s);
                ((PqFormFragment3)mfragment).prefillLoanAmountFragment3(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("sendOtp"))
        {
            try {
                Log.e("CallForData", "sendOtp"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "sendOtp"+ s);
                ((SuccessAfterPQForm)mActivity).sendOtp(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("verifyOtp"))
        {
            try {
                Log.e("CallForData", "verifyOtp"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "verifyOtp"+ s);
                ((SuccessAfterPQForm)mActivity).verifyOtp(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("borrowerEducationFragment"))
        {
            try {
                Log.e("CallForData", "borrowerEducationFragment"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "borrowerEducationFragment"+ s);
                ((BorrowerEducationFragment)mfragment).borrowerEducationFragment(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("borrowerProfessionalNFinance"))
        {
            try {
                Log.e("CallForData", "borrowerProfessionalNFinance"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "borrowerProfessionalNFinance"+ s);
                ((BorrowerProfessionNFinancialFragment)mfragment).borrowerProfessionalNFinance(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("borrowerPersonal"))
        {
            try {
                Log.e("CallForData", "borrowerProfessionalNFinance"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "borrowerProfessionalNFinance"+ s);
                ((BorrowerPersonalFragment)mfragment).borrowerPersonal(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("coBorrowerPersonal"))
        {
            try {
                Log.e("CallForData", "coBorrowerPersonal"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "coBorrowerPersonal"+ s);
                ((CoBorrowerPersonalFragment)mfragment).coBorrowerPersonal(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("coBorrowerProfessionalNFinance"))
        {
            try {
                Log.e("CallForData", "coBorrowerProfessionalNFinance"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "coBorrowerProfessionalNFinance"+ s);
                ((CoBorrowerFinancialFragment)mfragment).coBorrowerProfessionalNFinance(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("setborrowerPersonal"))
        {
            try {
                Log.e("CallForData", "setborrowerPersonal"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "setborrowerPersonal"+ s);
                ((BorrowerPersonalFragment)mfragment).setborrowerPersonal(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("setborrowerEducation"))
        {
            try {
                Log.e("CallForData", "setborrowerEducation"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "setborrowerEducation"+ s);
                ((BorrowerEducationFragment)mfragment).setborrowerEducation(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("setborrowerFinance"))
        {
            try {
                Log.e("CallForData", "setborrowerFinance"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "setborrowerFinance"+ s);
                ((BorrowerProfessionNFinancialFragment)mfragment).setborrowerFinance(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("setcoborrowerPersonal"))
        {
            try {
                Log.e("CallForData", "setcoborrowerPersonal"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "setcoborrowerPersonal"+ s);
                ((CoBorrowerPersonalFragment)mfragment).setcoborrowerPersonal(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("setcoborrowerFinance"))
        {
            try {
                Log.e("CallForData", "setcoborrowerFinance"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "setcoborrowerFinance"+ s);
                ((CoBorrowerFinancialFragment)mfragment).setcoborrowerFinance(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("getDocuments"))
        {
            try {
                Log.e("CallForData", "getDocuments"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "getDocuments"+ s);
                ((UploadActivityBorrower)mActivity).getDocuments(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("logInPage"))
        {
            try {
                Log.e("CallForData", "logInPage"+ s);
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "logInPage"+ s);
                ((Login)mActivity).logIn(jsonDataO);
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
                Log.e("CallForData", "getCoBorrowerDocuments"+ s);
                ((UploadActivityCoBorrower)mActivity).getCoBorrowerDocuments(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("getDashBoard"))
        {
            try {
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "getDashBoard"+ s);
                ((DashBoardFragment)mfragment).getDashBoard(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if(screen.equalsIgnoreCase("changeSettings"))
        {
            try {
                jsonDataO = new JSONObject(s);
                Log.e("CallForData", "changeSettings"+ s);
                ((MyProfile)mActivity).changeSettings(jsonDataO);
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
