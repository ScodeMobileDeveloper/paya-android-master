package com.paya.paragon.api.AgentCityList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentCityLocation {
    @SerializedName("cityKeyID") @Expose private String cityKeyID;
    @SerializedName("cityName") @Expose private String cityName;

    public String getCityKeyID() {
        return cityKeyID;
    }

    public void setCityKeyID(String cityKeyID) {
        this.cityKeyID = cityKeyID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
