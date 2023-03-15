package com.paya.paragon.api.SocialMeadiaLogin;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocialMediaResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("code") @Expose private Integer Code;
    @SerializedName("message") @Expose private String Message;
    @SerializedName("imagePath") @Expose private String imagePath;
    @SerializedName("data") @Expose private SocialMediaData data;

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

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public SocialMediaData getData() {
        return data;
    }

    public void setData(SocialMediaData data) {
        this.data = data;
    }
}
