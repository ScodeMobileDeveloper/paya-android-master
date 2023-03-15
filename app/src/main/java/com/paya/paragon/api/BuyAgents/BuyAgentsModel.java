package com.paya.paragon.api.BuyAgents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyAgentsModel {
    @SerializedName("userCompanyName") @Expose private String userCompanyName;
    @SerializedName("userFirstName") @Expose private String userFirstName;
    @SerializedName("userLastName") @Expose private String userLastName;
    @SerializedName("dealsin") @Expose private String dealsin;
    @SerializedName("projectCount") @Expose private String projectCount;
    @SerializedName("sellCount") @Expose private String sellCount;
    @SerializedName("rentCount") @Expose private String rentCount;
    @SerializedName("userCompanyLogo") @Expose private String userCompanyLogo;
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("userUrlKey") @Expose private String userUrlKey;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserUrlKey() {
        return userUrlKey;
    }

    public void setUserUrlKey(String userUrlKey) {
        this.userUrlKey = userUrlKey;
    }

    public String getUserCompanyLogo() {
        return userCompanyLogo;
    }

    public void setUserCompanyLogo(String userCompanyLogo) {
        this.userCompanyLogo = userCompanyLogo;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getDealsin() {
        return dealsin;
    }

    public void setDealsin(String dealsin) {
        this.dealsin = dealsin;
    }

    public String getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(String projectCount) {
        this.projectCount = projectCount;
    }

    public String getSellCount() {
        return sellCount;
    }

    public void setSellCount(String sellCount) {
        this.sellCount = sellCount;
    }

    public String getRentCount() {
        return rentCount;
    }

    public void setRentCount(String rentCount) {
        this.rentCount = rentCount;
    }
}
