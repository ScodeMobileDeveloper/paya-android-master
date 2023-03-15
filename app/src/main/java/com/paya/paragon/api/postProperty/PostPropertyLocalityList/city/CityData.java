package com.paya.paragon.api.postProperty.PostPropertyLocalityList.city;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;


public class CityData {

    @SerializedName("cityList")
    private ArrayList<CityListItem> cityList;

    public ArrayList<CityListItem> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<CityListItem> cityList) {
        this.cityList = cityList;
    }
}