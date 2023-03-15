package com.paya.paragon.api.FetchLocalInformationData;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocalInformationDataResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("message") @Expose private String message;
    @SerializedName("localInformation") @Expose private List<LocalInformationData> localInformation;

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

    public List<LocalInformationData> getLocalInformation() {
        return localInformation;
    }

    public void setLocalInformation(List<LocalInformationData> localInformation) {
        this.localInformation = localInformation;
    }
}
