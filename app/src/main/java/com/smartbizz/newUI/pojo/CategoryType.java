package com.smartbizz.newUI.pojo;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class CategoryType {

    private String id;

    @SerializedName("category_type")
    private String categoryType;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;


    public CategoryType(JSONObject jsonObject) {
        if (jsonObject != null) {
            id = jsonObject.optString("id");
            name = jsonObject.optString("name");
//            categoryType = jsonObject.optString("category_type");
        }
    }

    public CategoryType(String id, String name) {
        this.id = id;
        this.name = name;
    }

//    public CategoryType(String id, String categoryType) {
//        this.id = id;
//        this.categoryType = categoryType;
//    }

    public String getId() {
        return id;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
