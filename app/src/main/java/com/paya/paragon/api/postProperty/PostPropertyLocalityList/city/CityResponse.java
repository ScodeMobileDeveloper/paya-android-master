package com.paya.paragon.api.postProperty.PostPropertyLocalityList.city;



import com.google.gson.annotations.SerializedName;


public class CityResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("data")
    private CityData data;

    @SerializedName("response")
    private String response;

    @SerializedName("message")
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public CityData getData() {
        return data;
    }

    public void setData(CityData data) {
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}