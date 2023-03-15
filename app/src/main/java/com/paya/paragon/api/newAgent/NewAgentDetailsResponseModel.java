package com.paya.paragon.api.newAgent;

import com.google.gson.annotations.SerializedName;

public class NewAgentDetailsResponseModel {
    @SerializedName("response")
    private String response;
    @SerializedName("code")
    private Integer code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private NewAgentDetailsDATAResponse data;

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

    public NewAgentDetailsDATAResponse getData() {
        return data;
    }

    public void setData(NewAgentDetailsDATAResponse data) {
        this.data = data;
    }
}
