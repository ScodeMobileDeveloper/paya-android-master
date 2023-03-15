package com.paya.paragon.api.getProfileCity;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetProfileCityResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("data") @Expose private ArrayList<GetProfileCityData> cityList;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public ArrayList<GetProfileCityData> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<GetProfileCityData> cityList) {
        this.cityList = cityList;
    }
}
