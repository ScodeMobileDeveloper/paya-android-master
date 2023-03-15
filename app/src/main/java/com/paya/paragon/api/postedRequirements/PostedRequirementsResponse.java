package com.paya.paragon.api.postedRequirements;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostedRequirementsResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("code") @Expose private Integer Code;
    @SerializedName("message") @Expose private String Message;
    @SerializedName("data") @Expose private ArrayList<PostedRequirementsDataModel> data;


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

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public ArrayList<PostedRequirementsDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<PostedRequirementsDataModel> data) {
        this.data = data;
    }
}
