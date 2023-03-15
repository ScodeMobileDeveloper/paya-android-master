package com.paya.paragon.api.agentsFolloing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class FollowingAgentInfo {
    @SerializedName("userFullName")  @Expose private String userFullName;
    @SerializedName("userCompanyName")  @Expose private String userCompanyName;
    @SerializedName("userPhone")  @Expose private String userPhone;
    @SerializedName("userCompanyLogo")  @Expose private String userCompanyLogo;
    @SerializedName("stateName")  @Expose private String stateName;
    @SerializedName("cityName")  @Expose private String cityName;

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
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

    public String getUserCompanyLogo() {
        return userCompanyLogo;
    }

    public void setUserCompanyLogo(String userCompanyLogo) {
        this.userCompanyLogo = userCompanyLogo;
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
}
