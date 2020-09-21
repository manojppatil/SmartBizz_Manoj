package com.smartbizz.newUI.pojo;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;


public class mSMS implements Serializable {


    @SerializedName("mobile")
    String mobile;

    @SerializedName("status")
    String status;

    @SerializedName("status-desc")
    String status_desc;

    public mSMS(JSONObject jsonObject) {
        if (jsonObject != null) {

            if (!jsonObject.optString("mobile").equalsIgnoreCase("null")) {
                mobile = jsonObject.optString("mobile");
            }
            else {
                mobile ="";
            }
            if (!jsonObject.optString("status").equalsIgnoreCase("null")) {
                status = jsonObject.optString("status");
            }else {
                status ="";
            }
            if (!jsonObject.optString("status-desc").equalsIgnoreCase("null")) {

                status_desc = jsonObject.optString("status-desc");
            }else {
                status_desc ="";
            }

        }
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }
}
