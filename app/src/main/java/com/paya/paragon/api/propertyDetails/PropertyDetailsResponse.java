package com.paya.paragon.api.propertyDetails;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyDetailsResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("code") @Expose private Integer Code;
    @SerializedName("message") @Expose private String Message;
    @SerializedName("data") @Expose private PropertyDetailsData data;
    @SerializedName("suggested_properties") @Expose private SuggestedProperties suggestedProperties;
    public PropertyDetailsData getData() {
        return data;
    }

    public void setData(PropertyDetailsData data) {
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
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public SuggestedProperties getSuggestedProperties() {
        return suggestedProperties;
    }

    public void setSuggestedProperties(SuggestedProperties suggestedProperties) {
        this.suggestedProperties = suggestedProperties;
    }
}
