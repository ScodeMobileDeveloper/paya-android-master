package com.paya.paragon.api.getUserPlanDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class UserDetails {
    @SerializedName("countryName") @Expose private String countryName;
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("userName") @Expose private String userName;
    @SerializedName("userEmail") @Expose private String userEmail;
    @SerializedName("cityID") @Expose private String cityID;
    @SerializedName("cityLocID") @Expose private String cityLocID;
    @SerializedName("countryID") @Expose private String countryID;
    @SerializedName("stateID") @Expose private String stateID;
    @SerializedName("userFirstName") @Expose private String userFirstName;
    @SerializedName("userLastName") @Expose private String userLastName;
    @SerializedName("userPhone") @Expose private String userPhone;
    @SerializedName("userAddress1") @Expose private String userAddress1;
    @SerializedName("userAddress2") @Expose private String userAddress2;
    @SerializedName("userZip") @Expose private String userZip;
    @SerializedName("userPlanID") @Expose private String userPlanID;
    @SerializedName("planID") @Expose private String planID;
    @SerializedName("planPrice") @Expose private String planPrice;
    @SerializedName("countryCode") @Expose private String countryCode;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityLocID() {
        return cityLocID;
    }

    public void setCityLocID(String cityLocID) {
        this.cityLocID = cityLocID;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
