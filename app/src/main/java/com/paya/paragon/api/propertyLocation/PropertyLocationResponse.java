package com.paya.paragon.api.propertyLocation;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyLocationResponse {
    @SerializedName("response") @Expose private String response;
    @SerializedName("message") @Expose private String message;
    @SerializedName("data") @Expose private PropertyLocationData data;

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

    public PropertyLocationData getData() {
        return data;
    }

    public void setData(PropertyLocationData data) {
        this.data = data;
    }
}
