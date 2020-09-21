package com.smartbizz.newUI.network;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.Map;

public abstract class BaseRequest extends Request<NetworkResponse> {
    /**
     * The default socket timeout in milliseconds
     */
    protected static final int DEFAULT_TIMEOUT_MS = 1000 * 60 * 5;

    /**
     * The default number of retries
     */
    protected static final int DEFAULT_MAX_RETRIES = 2;

    protected Response.Listener<NetworkResponse> mListener;

    protected Context mContext;

    protected String mRequestBody;

    protected Map params;

    public BaseRequest(int method, String url, @Nullable Response.ErrorListener listener) {
        super(method, url, listener);
    }

    protected String getErrorMessageForCode(int statusCode) {
        /*String errorMsg = mContext.getResources().getString(R.string.deafult_error);
        switch (statusCode) {
            case 400:
                errorMsg = mContext.getResources().getString(R.string.bad_request);
                break;
            case 401:
                errorMsg = mContext.getResources().getString(R.string.bad_request);
                break;
            case 403:
                errorMsg = mContext.getResources().getString(R.string.bad_request);
                break;
            case 500:
                errorMsg = mContext.getResources().getString(R.string.internal_server_error);
                break;
            case 502:
                errorMsg = mContext.getResources().getString(R.string.bad_gateway);
                break;
            case 503:
                errorMsg = mContext.getResources().getString(R.string.deafult_error);
                break;
            case 504:
                errorMsg = mContext.getResources().getString(R.string.deafult_error);
                break;
        }
        return errorMsg;*/
        return "Something went wrong, please try again";
    }
}
