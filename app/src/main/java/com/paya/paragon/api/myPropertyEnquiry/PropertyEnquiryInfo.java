package com.paya.paragon.api.myPropertyEnquiry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PropertyEnquiryInfo {
    @SerializedName("enquiryName") @Expose private String enquiryName;
    @SerializedName("enquiryEmail") @Expose private String enquiryEmail;
    @SerializedName("enquiryPhone") @Expose private String enquiryPhone;
    @SerializedName("enquiryPostedDate") @Expose private String enquiryPostedDate;
    @SerializedName("enquiryContent") @Expose private String enquiryContent;

    public String getEnquiryContent() {
        return enquiryContent;
    }

    public void setEnquiryContent(String enquiryContent) {
        this.enquiryContent = enquiryContent;
    }

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

    public String getEnquiryPostedDate() {
        return enquiryPostedDate;
    }

    public void setEnquiryPostedDate(String enquiryPostedDate) {
        this.enquiryPostedDate = enquiryPostedDate;
    }
}
