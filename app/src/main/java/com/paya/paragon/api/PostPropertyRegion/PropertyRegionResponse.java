package com.paya.paragon.api.PostPropertyRegion;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PropertyRegionResponse {
    @SerializedName("response") @Expose private String response;
    @SerializedName("message") @Expose private String message;
    @SerializedName("data") @Expose private ArrayList<RegionData> data;

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

    public ArrayList<RegionData> getData() {
        return data;
    }

    public void setData(ArrayList<RegionData> data) {
        this.data = data;
    }
}
