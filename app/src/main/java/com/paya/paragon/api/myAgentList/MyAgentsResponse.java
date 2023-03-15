package com.paya.paragon.api.myAgentList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyAgentsResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("code") @Expose private Integer Code;
    @SerializedName("imgUrl") @Expose private Integer imgUrl;
    @SerializedName("message") @Expose private String Message;
    @SerializedName("data") @Expose private ArrayList<MyAgentssData> data;


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

    public ArrayList<MyAgentssData> getData() {
        return data;
    }

    public void setData(ArrayList<MyAgentssData> data) {
        this.data = data;
    }

    public Integer getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Integer imgUrl) {
        this.imgUrl = imgUrl;
    }
}
