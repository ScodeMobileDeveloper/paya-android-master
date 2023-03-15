package com.paya.paragon.api.builderDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class BuilderDetail implements Serializable {
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("userCompanyLogo") @Expose private String userCompanyLogo;
    @SerializedName("userYearOfInception") @Expose private String userYearOfInception;
    @SerializedName("userUrlKey") @Expose private String userUrlKey;
    @SerializedName("userZip") @Expose private String userZip;
    @SerializedName("userCompanyName") @Expose private String userCompanyName;
    @SerializedName("userAddress1") @Expose private String userAddress1;
    @SerializedName("userAddress2") @Expose private String userAddress2;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("countryName") @Expose private String countryName;
    @SerializedName("countryCode") @Expose private String countryCode;

    @SerializedName("projectCount") @Expose private String projectCount;
    @SerializedName("OnGoingProjectCount") @Expose private String OnGoingProjectCount;
    @SerializedName("userAboutMe") @Expose private String userAboutMe;
    @SerializedName("userSkills") @Expose private String userSkills;
    @SerializedName("userCompanyUrlKey") @Expose private String userCompanyUrlKey;

    public String getUserAboutMe() {
        return userAboutMe;
    }

    public void setUserAboutMe(String userAboutMe) {
        this.userAboutMe = userAboutMe;
    }

    public String getUserSkills() {
        return userSkills;
    }

    public void setUserSkills(String userSkills) {
        this.userSkills = userSkills;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserCompanyLogo() {
        return userCompanyLogo;
    }

    public void setUserCompanyLogo(String userCompanyLogo) {
        this.userCompanyLogo = userCompanyLogo;
    }

    public String getUserYearOfInception() {
        return userYearOfInception;
    }

    public void setUserYearOfInception(String userYearOfInception) {
        this.userYearOfInception = userYearOfInception;
    }

    public String getUserUrlKey() {
        return userUrlKey;
    }

    public void setUserUrlKey(String userUrlKey) {
        this.userUrlKey = userUrlKey;
    }

    public String getUserZip() {
        return userZip;
    }

    public void setUserZip(String userZip) {
        this.userZip = userZip;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(String projectCount) {
        this.projectCount = projectCount;
    }

    public String getOnGoingProjectCount() {
        return OnGoingProjectCount;
    }

    public void setOnGoingProjectCount(String onGoingProjectCount) {
        OnGoingProjectCount = onGoingProjectCount;
    }

    public String getUserAddress1() {
        if (userAddress1 == null || userAddress1.equals("")){
            return "";
        }
        return userAddress1 + ", ";
    }

    public void setUserAddress1(String userAddress1) {
        this.userAddress1 = userAddress1;
    }

    public String getUserAddress2() {
        if (userAddress2 == null || userAddress2.equals("")){
            return "";
        }
        return userAddress2 + ", ";
    }

    public void setUserAddress2(String userAddress2) {
        this.userAddress2 = userAddress2;
    }

    public String getUserCompanyUrlKey() {
        return userCompanyUrlKey;
    }

    public void setUserCompanyUrlKey(String userCompanyUrlKey) {
        this.userCompanyUrlKey = userCompanyUrlKey;
    }
}
