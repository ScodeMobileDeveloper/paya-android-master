package com.paya.paragon.api.upgradePlanApi;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpgradePlanResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("data") @Expose private UpgradePlanData data;
    @SerializedName("message") @Expose private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public UpgradePlanData getData() {
        return data;
    }

    public void setData(UpgradePlanData data) {
        this.data = data;
    }
}
