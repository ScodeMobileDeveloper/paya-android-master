package com.paya.paragon.api.builderDetails;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuildersDetailResponse {
    @SerializedName("response") @Expose
    private String Response;
    @SerializedName("message") @Expose
    private String Message;
    @SerializedName("data") @Expose
    private BuildersDetailData data;

    public BuildersDetailData getData() {
        return data;
    }

    public void setData(BuildersDetailData data) {
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
