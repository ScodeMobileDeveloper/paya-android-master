package com.paya.paragon.api.upgradePlanListing;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class UpgradePlanListData implements Serializable {
    @SerializedName("planID") @Expose private String planID;
    @SerializedName("userTypeID") @Expose private String userTypeID;
    @SerializedName("currencyID") @Expose private String currencyID;
    @SerializedName("planPrice") @Expose private String planPrice;
    @SerializedName("planPropertyCount") @Expose private String planPropertyCount;
    @SerializedName("planProjectCount") @Expose private String planProjectCount;
    @SerializedName("planAgentCount") @Expose private String planAgentCount;
    @SerializedName("planEmployeeCount") @Expose private String planEmployeeCount;
    @SerializedName("planDisplayTime") @Expose private String planDisplayTime;
    @SerializedName("planEnquiry") @Expose private String planEnquiry;
    @SerializedName("planEnquiryCount") @Expose private String planEnquiryCount;
    @SerializedName("planPropertyDetails") @Expose private String planPropertyDetails;
    @SerializedName("planBusinessLogo") @Expose private String planBusinessLogo;
    @SerializedName("planPropertyPerformance") @Expose private String planPropertyPerformance;
    @SerializedName("planFeatured") @Expose private String planFeatured;
    @SerializedName("planFeaturedCount") @Expose private String planFeaturedCount;
    @SerializedName("planProjectFeatured") @Expose private String planProjectFeatured;
    @SerializedName("planProjectFeaturedCount") @Expose private String planProjectFeaturedCount;
    @SerializedName("planStatus") @Expose private String planStatus;
    @SerializedName("planDate") @Expose private String planDate;
    @SerializedName("planUpdateDate") @Expose private String planUpdateDate;
    @SerializedName("isDefault") @Expose private String isDefault;
    @SerializedName("planPropertyDesc") @Expose private String planPropertyDesc;
    @SerializedName("planPhotography") @Expose private String planPhotography;
    @SerializedName("planListOtherPortals") @Expose private String planListOtherPortals;
    @SerializedName("planCommManager") @Expose private String planCommManager;
    @SerializedName("planVirtualTour") @Expose private String planVirtualTour;
    @SerializedName("planFreeHwe") @Expose private String planFreeHwe;
    @SerializedName("planDetailsID") @Expose private String planDetailsID;
    @SerializedName("planTitle") @Expose private String planTitle;
    @SerializedName("languageID") @Expose private String languageID;
    @SerializedName("userTypeName") @Expose private String userTypeName;
    @SerializedName("planVideoUploadingCount") @Expose private String planVideoUploadingCount;
    @SerializedName("planphotouploadCount") @Expose private String planphotouploadCount;
    @SerializedName("featuredPlanDisplayTime") @Expose private String featuredPlanDisplayTime;





    public String getPlanVideoUploadingCount() {
        return planVideoUploadingCount;
    }

    public void setPlanVideoUploadingCount(String planVideoUploadingCount) {
        this.planVideoUploadingCount = planVideoUploadingCount;
    }

    public String getPlanphotouploadCount() {
        return planphotouploadCount;
    }

    public void setPlanphotouploadCount(String planphotouploadCount) {
        this.planphotouploadCount = planphotouploadCount;
    }

    public String getFeaturedPlanDisplayTime() {
        return featuredPlanDisplayTime;
    }

    public void setFeaturedPlanDisplayTime(String featuredPlanDisplayTime) {
        this.featuredPlanDisplayTime = featuredPlanDisplayTime;
    }

    public String getPlanID() {
        return planID;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public String getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(String userTypeID) {
        this.userTypeID = userTypeID;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public String getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(String planPrice) {
        this.planPrice = planPrice;
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

    public String getPlanAgentCount() {
        return planAgentCount;
    }

    public void setPlanAgentCount(String planAgentCount) {
        this.planAgentCount = planAgentCount;
    }

    public String getPlanEmployeeCount() {
        return planEmployeeCount;
    }

    public void setPlanEmployeeCount(String planEmployeeCount) {
        this.planEmployeeCount = planEmployeeCount;
    }

    public String getPlanDisplayTime() {
        return planDisplayTime;
    }

    public void setPlanDisplayTime(String planDisplayTime) {
        this.planDisplayTime = planDisplayTime;
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

    public String getPlanPropertyDetails() {
        return planPropertyDetails;
    }

    public void setPlanPropertyDetails(String planPropertyDetails) {
        this.planPropertyDetails = planPropertyDetails;
    }

    public String getPlanBusinessLogo() {
        return planBusinessLogo;
    }

    public void setPlanBusinessLogo(String planBusinessLogo) {
        this.planBusinessLogo = planBusinessLogo;
    }

    public String getPlanPropertyPerformance() {
        return planPropertyPerformance;
    }

    public void setPlanPropertyPerformance(String planPropertyPerformance) {
        this.planPropertyPerformance = planPropertyPerformance;
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

    public String getPlanProjectFeatured() {
        return planProjectFeatured;
    }

    public void setPlanProjectFeatured(String planProjectFeatured) {
        this.planProjectFeatured = planProjectFeatured;
    }

    public String getPlanProjectFeaturedCount() {
        return planProjectFeaturedCount;
    }

    public void setPlanProjectFeaturedCount(String planProjectFeaturedCount) {
        this.planProjectFeaturedCount = planProjectFeaturedCount;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getPlanUpdateDate() {
        return planUpdateDate;
    }

    public void setPlanUpdateDate(String planUpdateDate) {
        this.planUpdateDate = planUpdateDate;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getPlanPropertyDesc() {
        return planPropertyDesc;
    }

    public void setPlanPropertyDesc(String planPropertyDesc) {
        this.planPropertyDesc = planPropertyDesc;
    }

    public String getPlanPhotography() {
        return planPhotography;
    }

    public void setPlanPhotography(String planPhotography) {
        this.planPhotography = planPhotography;
    }

    public String getPlanListOtherPortals() {
        return planListOtherPortals;
    }

    public void setPlanListOtherPortals(String planListOtherPortals) {
        this.planListOtherPortals = planListOtherPortals;
    }

    public String getPlanCommManager() {
        return planCommManager;
    }

    public void setPlanCommManager(String planCommManager) {
        this.planCommManager = planCommManager;
    }

    public String getPlanVirtualTour() {
        return planVirtualTour;
    }

    public void setPlanVirtualTour(String planVirtualTour) {
        this.planVirtualTour = planVirtualTour;
    }

    public String getPlanFreeHwe() {
        return planFreeHwe;
    }

    public void setPlanFreeHwe(String planFreeHwe) {
        this.planFreeHwe = planFreeHwe;
    }

    public String getPlanDetailsID() {
        return planDetailsID;
    }

    public void setPlanDetailsID(String planDetailsID) {
        this.planDetailsID = planDetailsID;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getLanguageID() {
        return languageID;
    }

    public void setLanguageID(String languageID) {
        this.languageID = languageID;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }
}
