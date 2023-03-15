package com.paya.paragon.api.AgentDetail;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentFollowResponse {
    @SerializedName("response") @Expose
    private String Response;
    @SerializedName("message") @Expose
    private String Message;

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
