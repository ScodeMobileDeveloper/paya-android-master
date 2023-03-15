package com.paya.paragon.api.AgentDetail;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentDetailResponse {
    @SerializedName("response") @Expose
    private String Response;
    @SerializedName("message") @Expose
    private String Message;
    @SerializedName("data") @Expose
    private AgentDetailData data;

    public AgentDetailData getData() {
        return data;
    }

    public void setData(AgentDetailData data) {
        this.data = data;
    }

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





}
