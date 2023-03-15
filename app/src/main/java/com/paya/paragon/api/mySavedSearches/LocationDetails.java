package com.paya.paragon.api.mySavedSearches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LocationDetails {
    @SerializedName("cityLocName") @Expose private String cityLocName;
    @SerializedName("cityLocID") @Expose private String cityLocID;

    public String getCityLocName() {
        return cityLocName;
    }

    public void setCityLocName(String cityLocName) {
        this.cityLocName = cityLocName;
    }

    public String getCityLocID() {
        return cityLocID;
    }

    public void setCityLocID(String cityLocID) {
        this.cityLocID = cityLocID;
    }
}
