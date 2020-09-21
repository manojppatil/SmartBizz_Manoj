package com.smartbizz.newUI.pojo;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;


public class MBeats implements Serializable {

    @SerializedName("id")
    String id;

    @SerializedName("imageUri")
    String imageUri;

    @SerializedName("name")
    public String name;

    public MBeats(String name) {
        this.name = name;
    }

    public MBeats(String name, Uri parse) {
        this.name = name;
    }

    public MBeats(JSONObject jsonObject) {
        if (jsonObject != null) {

            id = jsonObject.optString("id");
            imageUri = jsonObject.optString("imageUri");
            name = jsonObject.optString("name");
        }
    }

    public MBeats(String id, String imageUri) {
        this.id = id;
        this.imageUri = imageUri;
        this.name = "name";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
