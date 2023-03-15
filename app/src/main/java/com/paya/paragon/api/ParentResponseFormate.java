package com.paya.paragon.api;

import com.google.gson.annotations.SerializedName;

public class ParentResponseFormate {
    @SerializedName("response")
    private String response;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;


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


}
