package com.paya.paragon.api.enquiryList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class EnquiryListItemData {
    @SerializedName("enquiryID") @Expose private String enquiryID;
    @SerializedName("userName") @Expose private String userName;
    @SerializedName("enquiryName") @Expose private String enquiryName;
    @SerializedName("enquiryEmail") @Expose private String enquiryEmail;
    @SerializedName("enquiryPhone") @Expose private String enquiryPhone;
    @SerializedName("userEmail") @Expose private String userEmail;
    @SerializedName("userPhone") @Expose private String userPhone;
    @SerializedName("enquiryContent") @Expose private String enquiryContent;
    @SerializedName("propertyName") @Expose private String propertyName;

    public String getEnquiryName() {
        return enquiryName;
    }

    public void setEnquiryName(String enquiryName) {
        this.enquiryName = enquiryName;
    }

    public String getEnquiryEmail() {
        return enquiryEmail;
    }

    public void setEnquiryEmail(String enquiryEmail) {
        this.enquiryEmail = enquiryEmail;
    }

    public String getEnquiryPhone() {
        return enquiryPhone;
    }

    public void setEnquiryPhone(String enquiryPhone) {
        this.enquiryPhone = enquiryPhone;
    }



    public String getEnquiryID() {
        return enquiryID;
    }

    public void setEnquiryID(String enquiryID) {
        this.enquiryID = enquiryID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getEnquiryContent() {
        return enquiryContent;
    }

    public void setEnquiryContent(String enquiryContent) {
        this.enquiryContent = enquiryContent;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
