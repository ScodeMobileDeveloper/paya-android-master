package com.paya.paragon.api.agentsFolloing;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentsFollowingResponse {
    @SerializedName("code") @Expose private Integer code;
    @SerializedName("response") @Expose private String Response;
    @SerializedName("message") @Expose private String Message;
    @SerializedName("imagePath") @Expose private String imagePath;
    @SerializedName("data") @Expose private AgentsFollowingData data;

    public AgentsFollowingData getData() {
        return data;
    }

    public void setData(AgentsFollowingData data) {
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
