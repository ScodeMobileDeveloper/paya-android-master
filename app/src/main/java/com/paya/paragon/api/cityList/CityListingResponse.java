package com.paya.paragon.api.cityList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityListingResponse {
    @SerializedName("response") @Expose
    private String Response;
    @SerializedName("message") @Expose
    private String Message;
    @SerializedName("data") @Expose
    private CityListingData data;

    public CityListingData getData() {
        return data;
    }

    public void setData(CityListingData data) {
        this.data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }





}
