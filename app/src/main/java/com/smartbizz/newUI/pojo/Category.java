package com.smartbizz.newUI.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Category {
    @Expose
    @SerializedName("is_deleted")
    private String isDeleted;
    @Expose
    @SerializedName("is_active")
    private String isActive;
    @Expose
    @SerializedName("created_datetime")
    private String createdDatetime;
    @Expose
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("category_type")
    private String categoryType;
    @Expose
    @SerializedName("id")
    private String id;

    public Category(JSONObject jsonObject) {
        if (jsonObject != null) {
            isDeleted = jsonObject.optString("is_deleted");
            isActive = jsonObject.optString("is_active");
            createdDatetime = jsonObject.optString("created_datetime");
            description = jsonObject.optString("description");
            name = jsonObject.optString("name");
            categoryType = jsonObject.optString("category_type");
            id = jsonObject.optString("id");

        }
    }

    public Category(String isDeleted, String isActive, String createdDatetime, String description, String name, String categoryType, String id) {
        this.isDeleted = isDeleted;
        this.isActive = isActive;
        this.createdDatetime = createdDatetime;
        this.description = description;
        this.name = name;
        this.categoryType = categoryType;
        this.id = id;
    }

    public Category(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
