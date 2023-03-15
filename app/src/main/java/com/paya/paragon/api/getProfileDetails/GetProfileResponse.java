package com.paya.paragon.api.getProfileDetails;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProfileResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("data") @Expose private GetProfileData data;

    @SerializedName("message") @Expose private String message;
    @SerializedName("code") @Expose private Integer Code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public GetProfileData getData() {
        return data;
    }

    public void setData(GetProfileData data) {
        this.data = data;
    }
}
