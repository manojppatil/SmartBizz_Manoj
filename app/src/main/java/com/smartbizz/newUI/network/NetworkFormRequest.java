package com.smartbizz.newUI.network;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.smartbizz.R;
import com.smartbizz.Util.MyLogger;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES;
import static com.android.volley.DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;

public class NetworkFormRequest extends BaseRequest{
    private static final String TAG = NetworkFormRequest.class.getSimpleName();

    public NetworkFormRequest(Context context, int method, String api, Map params, Response.Listener<NetworkResponse>
            responseListener) {
        super(method, api, null);
        MyLogger.debug("URL:: " + api);
        mContext = context;
        mListener = responseListener;
        this.params = params;
        setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES, DefaultRetryPolicy
                .DEFAULT_BACKOFF_MULT));

    }

    @Override
    protected Map<String, String> getParams() {
        MyLogger.debug("Form PARAMS:: " + params);
        return params != null ? params : new HashMap<>();
    }

    @Override
    public Map<String, String> getHeaders() {
        final Map<String, String> headers = new HashMap<>();
      //  headers.put(ApiConstants.Header.CONTENT_TYPE, ApiConstants.Header.APPLICATION_JSON);
//        String token = PreferenceManager.getString(mContext, Constants.PrefKeys.AUTH_TOKEN);
//        if (!TextUtils.isEmpty(token)) {
//            headers.put(ApiConstants.Header.AUTHORIZATION, "Bearer " + token);

//        }
        MyLogger.debug("HEADER:: " + new JSONObject(headers).toString());
        return headers;
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(com.android.volley.NetworkResponse response) {
        final NetworkResponse networkResponse = new NetworkResponse();
        networkResponse.setSuccess(false);
        networkResponse.setStatusCode(response.statusCode);
        try {
            final String responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            MyLogger.debug("RESPONSE:: " + responseString);
            if (!TextUtils.isEmpty(responseString)) {
                final JSONObject responseObject = new JSONObject(responseString);
                int status = responseObject.optInt(ApiConstants.RootKeys.STATUS);
                networkResponse.setStatus(status);
                networkResponse.setSuccess(status == 1);
                networkResponse.setResponse(responseObject);
                networkResponse.setMessage(responseObject.optString(ApiConstants.RootKeys.MESSAGE));

            }
        } catch (Exception e) {
            MyLogger.error(TAG, e);
        }
        return Response.success(networkResponse, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        final NetworkResponse networkResponse = new NetworkResponse();
        networkResponse.setSuccess(false);
        int statusCode = 0;
        String errorMessage = null;
        if (error != null) {
            if (error instanceof NoConnectionError) {
                errorMessage = mContext.getString(R.string.error_message_unable_to_connect);
            } else if (error instanceof TimeoutError) {
                errorMessage = mContext.getString(R.string.error_message_request_timeout);
            } else if (error.networkResponse != null && error.networkResponse.data != null) {
                String errorData = new String(error.networkResponse.data);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(errorData);
                    errorMessage = jsonObject.optString("errorMessage");
                } catch (Exception e) {
                    MyLogger.error(TAG, e);
                }

                networkResponse.setResponse(jsonObject);
                networkResponse.setMessage(errorMessage);
                statusCode = error.networkResponse.statusCode;
                networkResponse.setStatusCode(statusCode);
            }
        }
        if (TextUtils.isEmpty(errorMessage)) {
            networkResponse.setMessage(getErrorMessageForCode(statusCode));
        } else {
            networkResponse.setMessage(errorMessage);
        }
        mListener.onResponse(networkResponse);
    }
}
