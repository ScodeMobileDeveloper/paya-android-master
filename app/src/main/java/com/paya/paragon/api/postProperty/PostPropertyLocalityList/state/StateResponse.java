package com.paya.paragon.api.postProperty.PostPropertyLocalityList.state;

import com.google.gson.annotations.SerializedName;


public class StateResponse {

    @SerializedName("code")
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public StateData getData() {
        return data;
    }

    public void setData(StateData data) {
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

    @SerializedName("data")
    private StateData data;

    @SerializedName("response")
    private String response;

    @SerializedName("message")
    private String message;
}