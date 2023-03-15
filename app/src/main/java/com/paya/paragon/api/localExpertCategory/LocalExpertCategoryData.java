package com.paya.paragon.api.localExpertCategory;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LocalExpertCategoryData {
    @SerializedName("userCategoryName") @Expose private String userCategoryName;
    @SerializedName("userCategoryIcon") @Expose private String userCategoryIcon;
    @SerializedName("userCategoryID") @Expose private String userCategoryID;

    public String getUserCategoryName() {
        return userCategoryName;
    }

    public void setUserCategoryName(String userCategoryName) {
        this.userCategoryName = userCategoryName;
    }

    public String getUserCategoryIcon() {
        return userCategoryIcon;
    }

    public void setUserCategoryIcon(String userCategoryIcon) {
        this.userCategoryIcon = userCategoryIcon;
    }

    public String getUserCategoryID() {
        return userCategoryID;
    }

    public void setUserCategoryID(String userCategoryID) {
        this.userCategoryID = userCategoryID;
    }
}
