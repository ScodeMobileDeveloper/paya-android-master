package com.paya.paragon.api.postRequirement.postRequirementLocation;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PRLocationListResponse {
    @SerializedName("response") @Expose private String response;
    @SerializedName("code") @Expose private Integer code;
    @SerializedName("message") @Expose private String message;
    @SerializedName("data") @Expose private ArrayList<PRLocationListData> data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<PRLocationListData> getData() {
        return data;
    }

    public void setData(ArrayList<PRLocationListData> data) {
        this.data = data;
    }
}
