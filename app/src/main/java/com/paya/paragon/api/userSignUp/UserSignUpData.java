package com.paya.paragon.api.userSignUp;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSignUpData {
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("acessToken")
    @Expose
    private String acessToken;
    @SerializedName("OTP")
    @Expose
    private String OTP;

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getAcessToken() {
        return acessToken;
    }

    public void setAcessToken(String acessToken) {
        this.acessToken = acessToken;
    }
}
