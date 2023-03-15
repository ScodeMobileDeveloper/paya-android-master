package com.paya.paragon.api.upgradePlanApi;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpgradePlanCountryData {
    @SerializedName("countryID") @Expose private String countryID;
    @SerializedName("countryName") @Expose private String countryName;

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
}
