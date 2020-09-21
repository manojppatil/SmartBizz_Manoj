package com.smartbizz.newUI.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Requests {
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
    @SerializedName("created")
    private String created;
    @Expose
    @SerializedName("changed")
    private String changed;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("login_type")
    private String loginType;
    @Expose
    @SerializedName("timezone")
    private String timezone;
    @Expose
    @SerializedName("expiration_date")
    private String expirationDate;
    @SerializedName("img_acceptance_status")
    private String imgAcceptanceStatus;
//    @Expose
//    @SerializedName("package")
//    private String package;//package;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("fullname")
    private String fullname;
    @Expose
    @SerializedName("role")
    private String role;
    @Expose
    @SerializedName("ids")
    private String ids;
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("user_id")
    private String userId;
    @Expose
    @SerializedName("gallery_category_id")
    private String galleryCategoryId;
    @Expose
    @SerializedName("is_img_active")
    private String isImgActive;
    @Expose
    @SerializedName("img_path")
    private String imgPath;
    @Expose
    @SerializedName("comments")
    private String comments;
    @Expose
    @SerializedName("request_id")
    private String requestId;

    @SerializedName("like")
    public int like;

    @SerializedName("share")
    public int share;

    @SerializedName("is_liked")
    public int islike;

    @SerializedName("hex_code")
    public String hexCode;

    @SerializedName("name1")
    public String name1;


    public Requests(String name1) {
        this.name1 = name1;
    }

    public Requests(JSONObject jsonObject) {
        if (jsonObject != null) {
            isDeleted = jsonObject.optString("is_deleted");
            isActive = jsonObject.optString("is_active");
            createdDatetime = jsonObject.optString("created_datetime");
            description = jsonObject.optString("description");
            name = jsonObject.optString("name");
            categoryType = jsonObject.optString("category_type");
            created = jsonObject.optString("created");
            changed = jsonObject.optString("changed");
            status = jsonObject.optString("status");
            loginType = jsonObject.optString("login_type");
            timezone = jsonObject.optString("timezone");
            expirationDate = jsonObject.optString("expiration_date");
//            THIS_IA_AN_INVALID_JAVA_IDENTIFIER_MANUALLY_RESOLVE_THE_ISSUE = jsonObject.optString("package");
            password = jsonObject.optString("password");
            email = jsonObject.optString("email");
            fullname = jsonObject.optString("fullname");
            role = jsonObject.optString("role");
            ids = jsonObject.optString("ids");
            id = jsonObject.optString("id");
            userId = jsonObject.optString("user_id");
//            hasCoBorrower = jsonObject.optString("has_coborrower").equalsIgnoreCase("1");
            galleryCategoryId = jsonObject.optString("gallery_category_id");
            isImgActive = jsonObject.optString("is_img_active");
            imgPath = jsonObject.optString("img_path");
            comments = jsonObject.optString("comments");
            requestId = jsonObject.optString("request_id");
            imgAcceptanceStatus = jsonObject.optString("img_acceptance_status");

            try {
                islike = Integer.parseInt(jsonObject.optString("is_liked"));
            } catch (Exception e) {
                islike = 0;
                e.printStackTrace();
            }

            if(!jsonObject.optString("like").equalsIgnoreCase("null")){
                try {
                    like = Integer.parseInt(jsonObject.optString("like"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            if(!jsonObject.optString("share").equalsIgnoreCase("null")) {
                try {
                    share = Integer.parseInt(jsonObject.optString("share"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            hexCode = jsonObject.optString("hex_code");
            name1 = jsonObject.optString("name1");

        }
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

//    public String getTHIS_IA_AN_INVALID_JAVA_IDENTIFIER_MANUALLY_RESOLVE_THE_ISSUE() {
//        return THIS_IA_AN_INVALID_JAVA_IDENTIFIER_MANUALLY_RESOLVE_THE_ISSUE;
//    }
//
//    public void setTHIS_IA_AN_INVALID_JAVA_IDENTIFIER_MANUALLY_RESOLVE_THE_ISSUE(String THIS_IA_AN_INVALID_JAVA_IDENTIFIER_MANUALLY_RESOLVE_THE_ISSUE) {
//        this.THIS_IA_AN_INVALID_JAVA_IDENTIFIER_MANUALLY_RESOLVE_THE_ISSUE = THIS_IA_AN_INVALID_JAVA_IDENTIFIER_MANUALLY_RESOLVE_THE_ISSUE;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGalleryCategoryId() {
        return galleryCategoryId;
    }

    public void setGalleryCategoryId(String galleryCategoryId) {
        this.galleryCategoryId = galleryCategoryId;
    }

    public String getIsImgActive() {
        return isImgActive;
    }

    public void setIsImgActive(String isImgActive) {
        this.isImgActive = isImgActive;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getImgAcceptanceStatus() {
        return imgAcceptanceStatus;
    }

    public void setImgAcceptanceStatus(String imgAcceptanceStatus) {
        this.imgAcceptanceStatus = imgAcceptanceStatus;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getIslike() {
        return islike;
    }

    public void setIslike(int islike) {
        this.islike = islike;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }

    public String getHexCode() {
        return hexCode;
    }

    public void setHexCode(String hexCode) {
        this.hexCode = hexCode;
    }
}
