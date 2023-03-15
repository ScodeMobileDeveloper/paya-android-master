package com.paya.paragon.api.getProfileDetails;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GetProfileDetailsData {
    @SerializedName("userFirstName") @Expose private String userFirstName;
    @SerializedName("userLastName") @Expose private String userLastName;
    @SerializedName("userEmail") @Expose private String userEmail;
    @SerializedName("userPhone") @Expose private String userPhone;
    @SerializedName("countryCode") @Expose private String countryCode;
    @SerializedName("userAddress1") @Expose private String userAddress1;
    @SerializedName("userAddress2") @Expose private String userAddress2;
    @SerializedName("countryID") @Expose private String countryID;
    @SerializedName("countryName") @Expose private String countryName;
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("stateID") @Expose private String stateID;
    @SerializedName("cityID") @Expose private String cityID;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("userZip") @Expose private String userZip;



    @SerializedName("userGender") @Expose private String userGender;
    @SerializedName("userProfilePicture") @Expose private String userProfilePicture;
    @SerializedName("googleStateName") @Expose private String googleStateName;
    @SerializedName("googleCityName") @Expose private String googleCityName;
    @SerializedName("googleCountryName") @Expose private String googleCountryName;
    @SerializedName("googleLocality") @Expose private String googleLocality;

    @SerializedName("userTypeID") @Expose private String userTypeID;
    @SerializedName("userCompanyName") @Expose private String userCompanyName;
    @SerializedName("userCompanyLogo") @Expose private String userCompanyLogo;
    @SerializedName("userPhone2") @Expose private String userPhone2;
    @SerializedName("userLocation") @Expose private String userLocation;

    public String getGoogleStateName() {
        return googleStateName;
    }

    public void setGoogleStateName(String googleStateName) {
        this.googleStateName = googleStateName;
    }

    public String getGoogleCityName() {
        return googleCityName;
    }

    public void setGoogleCityName(String googleCityName) {
        this.googleCityName = googleCityName;
    }

    public String getGoogleCountryName() {
        return googleCountryName;
    }

    public void setGoogleCountryName(String googleCountryName) {
        this.googleCountryName = googleCountryName;
    }

    public String getGoogleLocality() {
        return googleLocality;
    }

    public void setGoogleLocality(String googleLocality) {
        this.googleLocality = googleLocality;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserZip() {
        return userZip;
    }

    public void setUserZip(String userZip) {
        this.userZip = userZip;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
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

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(String userTypeID) {
        this.userTypeID = userTypeID;
    }
    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserCompanyLogo() {
        return userCompanyLogo;
    }

    public void setUserCompanyLogo(String userCompanyLogo) {
        this.userCompanyLogo = userCompanyLogo;
    }

    public String getUserPhone2() {
        return userPhone2;
    }

    public void setUserPhone2(String userPhone2) {
        this.userPhone2 = userPhone2;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }
}
