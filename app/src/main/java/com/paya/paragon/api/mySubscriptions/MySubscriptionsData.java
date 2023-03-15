package com.paya.paragon.api.mySubscriptions;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class MySubscriptionsData {
    @SerializedName("userPlanID") @Expose private String userPlanID;
    @SerializedName("planID") @Expose private String planID;
    @SerializedName("planPrice") @Expose private String planPrice;
    @SerializedName("planPurchaseDate") @Expose private String planPurchaseDate;
    @SerializedName("planExpiryDate") @Expose private String planExpiryDate;
    @SerializedName("planUpdateDate") @Expose private String planUpdateDate;
    @SerializedName("planPropertyCount") @Expose private String planPropertyCount;
    @SerializedName("planProjectCount") @Expose private String planProjectCount;
    @SerializedName("planEnquiry") @Expose private String planEnquiry;
    @SerializedName("planEnquiryCount") @Expose private String planEnquiryCount;
    @SerializedName("planFeatured") @Expose private String planFeatured;
    @SerializedName("planFeaturedCount") @Expose private String planFeaturedCount;
    @SerializedName("planPropertyDetails") @Expose private String planPropertyDetails;
    @SerializedName("planPropertyCountBalance") @Expose private String planPropertyCountBalance;
    @SerializedName("planStatus") @Expose private String planStatus;
    @SerializedName("planEnquiryCountBalance") @Expose private String planEnquiryCountBalance;
    @SerializedName("planphotouploadCount") @Expose private String planphotouploadCount;
    @SerializedName("planVideoUploadingCount") @Expose private String planVideoUploadingCount;
    @SerializedName("planTitle") @Expose private String planTitle;
    @SerializedName("isexpired") @Expose private String isexpired;

    public String getIsexpired() {
        return isexpired;
    }

    public void setIsexpired(String isexpired) {
        this.isexpired = isexpired;
    }

    public String getUserPlanID() {
        return userPlanID;
    }

    public void setUserPlanID(String userPlanID) {
        this.userPlanID = userPlanID;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public String getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(String planPrice) {
        this.planPrice = planPrice;
    }

    public String getPlanPurchaseDate() {
        return planPurchaseDate;
    }

    public void setPlanPurchaseDate(String planPurchaseDate) {
        this.planPurchaseDate = planPurchaseDate;
    }

    public String getPlanExpiryDate() {
        return planExpiryDate;
    }

    public void setPlanExpiryDate(String planExpiryDate) {
        this.planExpiryDate = planExpiryDate;
    }

    public String getPlanUpdateDate() {
        return planUpdateDate;
    }

    public void setPlanUpdateDate(String planUpdateDate) {
        this.planUpdateDate = planUpdateDate;
    }

    public String getPlanPropertyCount() {
        return planPropertyCount;
    }

    public void setPlanPropertyCount(String planPropertyCount) {
        this.planPropertyCount = planPropertyCount;
    }

    public String getPlanProjectCount() {
        return planProjectCount;
    }

    public void setPlanProjectCount(String planProjectCount) {
        this.planProjectCount = planProjectCount;
    }

    public String getPlanEnquiry() {
        return planEnquiry;
    }

    public void setPlanEnquiry(String planEnquiry) {
        this.planEnquiry = planEnquiry;
    }

    public String getPlanEnquiryCount() {
        return planEnquiryCount;
    }

    public void setPlanEnquiryCount(String planEnquiryCount) {
        this.planEnquiryCount = planEnquiryCount;
    }

    public String getPlanFeatured() {
        return planFeatured;
    }

    public void setPlanFeatured(String planFeatured) {
        this.planFeatured = planFeatured;
    }

    public String getPlanFeaturedCount() {
        return planFeaturedCount;
    }

    public void setPlanFeaturedCount(String planFeaturedCount) {
        this.planFeaturedCount = planFeaturedCount;
    }

    public String getPlanPropertyDetails() {
        return planPropertyDetails;
    }

    public void setPlanPropertyDetails(String planPropertyDetails) {
        this.planPropertyDetails = planPropertyDetails;
    }

    public String getPlanPropertyCountBalance() {
        return planPropertyCountBalance;
    }

    public void setPlanPropertyCountBalance(String planPropertyCountBalance) {
        this.planPropertyCountBalance = planPropertyCountBalance;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlanEnquiryCountBalance() {
        return planEnquiryCountBalance;
    }

    public void setPlanEnquiryCountBalance(String planEnquiryCountBalance) {
        this.planEnquiryCountBalance = planEnquiryCountBalance;
    }

    public String getPlanphotouploadCount() {
        return planphotouploadCount;
    }

    public void setPlanphotouploadCount(String planphotouploadCount) {
        this.planphotouploadCount = planphotouploadCount;
    }

    public String getPlanVideoUploadingCount() {
        return planVideoUploadingCount;
    }

    public void setPlanVideoUploadingCount(String planVideoUploadingCount) {
        this.planVideoUploadingCount = planVideoUploadingCount;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }
}
