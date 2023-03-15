package com.paya.paragon.api.buildersList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuildersListingResponse {
    @SerializedName("response") @Expose
    private String Response;
    @SerializedName("message") @Expose
    private String Message;
    @SerializedName("data") @Expose
    private BuildersListingData data;

    public BuildersListingData getData() {
        return data;
    }

    public void setData(BuildersListingData data) {
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
