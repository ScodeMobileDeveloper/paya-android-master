package com.paya.paragon.api.getCityListApi;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetCityResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("data") @Expose private ArrayList<GetCityData> cityList;
    @SerializedName("message") @Expose private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public ArrayList<GetCityData> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<GetCityData> cityList) {
        this.cityList = cityList;
    }
}
