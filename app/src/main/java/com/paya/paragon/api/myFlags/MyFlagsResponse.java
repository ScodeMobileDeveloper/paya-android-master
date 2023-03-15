package com.paya.paragon.api.myFlags;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyFlagsResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("code") @Expose private Integer Code;
    @SerializedName("imgUrl") @Expose private Integer imgUrl;
    @SerializedName("message") @Expose private String Message;
    @SerializedName("data") @Expose private ArrayList<MyFlagsData> data;


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

    public ArrayList<MyFlagsData> getData() {
        return data;
    }

    public void setData(ArrayList<MyFlagsData> data) {
        this.data = data;
    }

    public Integer getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(Integer imgUrl) {
        this.imgUrl = imgUrl;
    }
}
