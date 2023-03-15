package com.paya.paragon.api.districtList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictListingData {
    @SerializedName("cityName") @Expose private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
