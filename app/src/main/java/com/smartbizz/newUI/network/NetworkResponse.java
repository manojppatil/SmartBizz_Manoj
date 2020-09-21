package com.smartbizz.newUI.network;

import org.json.JSONObject;

public class NetworkResponse {
    private String message;
    private int statusCode;
    private int status;
    private boolean success;
    private JSONObject response;
    private boolean isEditable;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean success) {
        this.isEditable = isEditable;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public JSONObject getResponse() {
        return response;
    }

    public void setResponse(JSONObject response) {
        this.response = response;
    }
}
