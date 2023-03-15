package com.paya.paragon.api.offerList;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OfferListItemData {
    @SerializedName("enquiryID") @Expose private String enquiryID;
    @SerializedName("enquiryName") @Expose private String enquiryName;
    @SerializedName("enquiryEmail") @Expose private String enquiryEmail;
    @SerializedName("enquiryPhone") @Expose private String enquiryPhone;
    @SerializedName("enquiryContent") @Expose private String enquiryContent;
    @SerializedName("userOfferPrice") @Expose private String userOfferPrice;
    @SerializedName("propertyName") @Expose private String propertyName;

    public String getEnquiryID() {
        return enquiryID;
    }

    public void setEnquiryID(String enquiryID) {
        this.enquiryID = enquiryID;
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

    public String getEnquiryContent() {
        return enquiryContent;
    }

    public void setEnquiryContent(String enquiryContent) {
        this.enquiryContent = enquiryContent;
    }

    public String getUserOfferPrice() {
        return userOfferPrice;
    }

    public void setUserOfferPrice(String userOfferPrice) {
        this.userOfferPrice = userOfferPrice;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
