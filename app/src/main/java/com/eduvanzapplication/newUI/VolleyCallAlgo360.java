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
import com.eduvanzapplication.newUI.newViews.DashboardActivity;
import com.eduvanzapplication.newUI.newViews.FinancialAnalysis;
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

public class VolleyCallAlgo360 {
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


    public VolleyCallAlgo360() {
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 60, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
         if (screen.equalsIgnoreCase("addAlgo360")) {
            try {
                jsonDataO = new JSONObject(s);
                ((DashboardActivity) mActivity).updateAlgo360Res(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (screen.equalsIgnoreCase("addAlgoscore")) {
            try {
                jsonDataO = new JSONObject(s);
                ((FinancialAnalysis) mActivity).updateAlgo360Score(jsonDataO);
            } catch (JSONException e) {
                e.printStackTrace();
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