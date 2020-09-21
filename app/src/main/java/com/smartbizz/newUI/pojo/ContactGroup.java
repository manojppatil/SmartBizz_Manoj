package com.smartbizz.newUI.pojo;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class ContactGroup {
    private String id;

    @SerializedName("group_name")
    private String groupName;

    public ContactGroup(JSONObject jsonObject) {
        if (jsonObject != null) {
            id = jsonObject.optString("id");
            groupName = jsonObject.optString("group_name");
        }
    }

    public ContactGroup(String id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public String getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }
}
