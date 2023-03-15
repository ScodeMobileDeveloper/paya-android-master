package com.paya.paragon.api.langaugeList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguageListResponse {
    @SerializedName("response") @Expose
    private String Response;
    @SerializedName("message") @Expose
    private String Message;
    @SerializedName("data") @Expose
    private LanguageListData data;

    public LanguageListData getData() {
        return data;
    }

    public void setData(LanguageListData data) {
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
