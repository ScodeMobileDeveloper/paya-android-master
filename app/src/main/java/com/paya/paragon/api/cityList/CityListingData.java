package com.paya.paragon.api.cityList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.index.LocationInfo;

import java.util.ArrayList;

public class CityListingData {
    @SerializedName("cityList") @Expose private ArrayList<LocationInfo> cityList;

    public ArrayList<LocationInfo> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<LocationInfo> cityList) {
        this.cityList = cityList;
    }
}
