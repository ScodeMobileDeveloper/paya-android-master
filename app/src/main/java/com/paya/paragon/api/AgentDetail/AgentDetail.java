package com.paya.paragon.api.AgentDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AgentDetail implements Serializable {
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("userCompanyLogo") @Expose private String userCompanyLogo;
    @SerializedName("userProfilePicture") @Expose private String userProfilePicture;
    @SerializedName("dealsin") @Expose private String dealsin;
    @SerializedName("userFirstName") @Expose private String username;
    @SerializedName("userLastName") @Expose private String userLastName;
    @SerializedName("userYearOfInception") @Expose private String userYearOfInception;
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("countryName") @Expose private String countryName;
    @SerializedName("userAddress1") @Expose private String userAddress1;
    @SerializedName("userAddress2") @Expose private String userAddress2;
    @SerializedName("userZip") @Expose private String userZip;
    @SerializedName("userCompanyName") @Expose private String userCompanyName;
    @SerializedName("userPhone") @Expose private String userPhone;
    @SerializedName("userRegNumber") @Expose private String userRegNumber;
    @SerializedName("userCompanyUrlKey") @Expose private String userCompanyUrlKey;
    @SerializedName("propertyFollowCount") @Expose private String propertyFollowCount;
    @SerializedName("userAboutMe") @Expose private String userAboutMe;
    @SerializedName("userSkills") @Expose private String userSkills;

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

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

    public String getPropertyFollowCount() {
        return propertyFollowCount;
    }

    public void setPropertyFollowCount(String propertyFollowCount) {
        this.propertyFollowCount = propertyFollowCount;
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

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public String getDealsin() {
        return dealsin;
    }

    public void setDealsin(String dealsin) {
        this.dealsin = dealsin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRegNumber() {
        return userRegNumber;
    }

    public void setUserRegNumber(String userRegNumber) {
        this.userRegNumber = userRegNumber;
    }

    public String getUserCompanyUrlKey() {
        return userCompanyUrlKey;
    }

    public void setUserCompanyUrlKey(String userCompanyUrlKey) {
        this.userCompanyUrlKey = userCompanyUrlKey;
    }
}
