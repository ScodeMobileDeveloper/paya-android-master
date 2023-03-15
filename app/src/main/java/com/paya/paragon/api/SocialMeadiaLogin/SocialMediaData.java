package com.paya.paragon.api.SocialMeadiaLogin;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialMediaData {
    @SerializedName("userID") @Expose private String userID;
    @SerializedName("userTypeID") @Expose private String userTypeID;
    @SerializedName("userEmail") @Expose private String userEmail;
    @SerializedName("userFirstname") @Expose private String userFirstname;
    @SerializedName("userLastname") @Expose private String userLastname;
    @SerializedName("userPhone") @Expose private String userPhone;
    @SerializedName("userProfilePicture") @Expose private String userProfilePicture;
    @SerializedName("acessToken") @Expose private String acessToken;
    @SerializedName("countryCode") @Expose private String countryCode;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(String userTypeID) {
        this.userTypeID = userTypeID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public String getAcessToken() {
        return acessToken;
    }

    public void setAcessToken(String acessToken) {
        this.acessToken = acessToken;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
