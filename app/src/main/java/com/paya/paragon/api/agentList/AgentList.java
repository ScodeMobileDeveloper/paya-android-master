package com.paya.paragon.api.agentList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AgentList implements Serializable {
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("userEmail") @Expose private String userEmail;
    @SerializedName("userFullName") @Expose private String userFullName;
    @SerializedName("isPremium") @Expose private String isPremium;
    @SerializedName("userProfilePicture") @Expose private String userProfilePicture;
    @SerializedName("userCompanyLogo") @Expose private String userCompanyLogo;
    @SerializedName("userUrlKey") @Expose private String userUrlKey;
    @SerializedName("username") @Expose private String username;
    @SerializedName("userCompanyName") @Expose private String userCompanyName;
    @SerializedName("userDesignation") @Expose private String userDesignation;
    @SerializedName("userCompanyUrlKey") @Expose private String userCompanyUrlKey;
    @SerializedName("userPhone") @Expose private String userPhone;
    @SerializedName("planBusinessLogo") @Expose private String planBusinessLogo;
    @SerializedName("userAddress1") @Expose private String userAddress1;
    @SerializedName("userAddress2") @Expose private String userAddress2;
    @SerializedName("userYearOfInception") @Expose private String userYearOfInception;
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("countryName") @Expose private String countryName;
    @SerializedName("userZip") @Expose private String userZip;
    @SerializedName("dealsIDs") @Expose private String dealsIDs;
    @SerializedName("dealsin") @Expose private String dealsin;
    @SerializedName("rentCount") @Expose private String rentCount;
    @SerializedName("sellCount") @Expose private String sellCount;
    @SerializedName("projectCount") @Expose private String projectCount;

    public String getRentCount() {
        return rentCount;
    }

    public void setRentCount(String rentCount) {
        this.rentCount = rentCount;
    }

    public String getSellCount() {
        return sellCount;
    }

    public void setSellCount(String sellCount) {
        this.sellCount = sellCount;
    }

    public String getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(String projectCount) {
        this.projectCount = projectCount;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getIsPremium() {
        return isPremium;
    }

    public void setIsPremium(String isPremium) {
        this.isPremium = isPremium;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public String getUserCompanyLogo() {
        return userCompanyLogo;
    }

    public void setUserCompanyLogo(String userCompanyLogo) {
        this.userCompanyLogo = userCompanyLogo;
    }

    public String getUserUrlKey() {
        return userUrlKey;
    }

    public void setUserUrlKey(String userUrlKey) {
        this.userUrlKey = userUrlKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserDesignation() {
        return userDesignation;
    }

    public void setUserDesignation(String userDesignation) {
        this.userDesignation = userDesignation;
    }

    public String getUserCompanyUrlKey() {
        return userCompanyUrlKey;
    }

    public void setUserCompanyUrlKey(String userCompanyUrlKey) {
        this.userCompanyUrlKey = userCompanyUrlKey;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getPlanBusinessLogo() {
        return planBusinessLogo;
    }

    public void setPlanBusinessLogo(String planBusinessLogo) {
        this.planBusinessLogo = planBusinessLogo;
    }

    public String getUserAddress1() {
        return userAddress1;
    }

    public void setUserAddress1(String userAddress1) {
        this.userAddress1 = userAddress1;
    }

    public String getUserAddress2() {
        return userAddress2;
    }

    public void setUserAddress2(String userAddress2) {
        this.userAddress2 = userAddress2;
    }

    public String getUserYearOfInception() {
        return userYearOfInception;
    }

    public void setUserYearOfInception(String userYearOfInception) {
        this.userYearOfInception = userYearOfInception;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getUserZip() {
        return userZip;
    }

    public void setUserZip(String userZip) {
        this.userZip = userZip;
    }

    public String getDealsIDs() {
        return dealsIDs;
    }

    public void setDealsIDs(String dealsIDs) {
        this.dealsIDs = dealsIDs;
    }

    public String getDealsin() {
        return dealsin;
    }

    public void setDealsin(String dealsin) {
        this.dealsin = dealsin;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}
