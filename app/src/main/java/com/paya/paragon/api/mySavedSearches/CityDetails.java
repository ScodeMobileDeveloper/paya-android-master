package com.paya.paragon.api.mySavedSearches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityDetails {
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("cityID") @Expose private String cityID;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }
}
