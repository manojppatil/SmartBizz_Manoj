package com.smartbizz.newUI.pojo;

import com.smartbizz.Util.MyLogger;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends BaseResponse{
    private String mobile;
    private String email;
    private String name;

    public Login(String mobile, String email) {
        this.mobile = mobile;
        this.email = email;
    }

    public Login(String mobile, String email, String name) {
        this.mobile = mobile;
        this.email = email;
        this.name = name;
    }

    public Login(JSONObject jsonObject) {
        this.mobile = jsonObject.optString("mobile");
        this.email = jsonObject.optString("email");
        this.name = jsonObject.optString("name");
        this.status = jsonObject.optInt("status");
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public JSONObject toJson() {
        try {
            return new JSONObject(new GsonBuilder().create().toJson(this, Login.class));
        } catch (JSONException e) {
            MyLogger.error(e);
        }
        return null;
    }
}
