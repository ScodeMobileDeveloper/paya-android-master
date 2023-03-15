package com.paya.paragon.api.profileInfo;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileInfoResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("message") @Expose private String message;
    @SerializedName("code") @Expose private Integer Code;
    @SerializedName("data") @Expose private ProfileInfoData data;
    @SerializedName("imagePath") @Expose private String imagePath;

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

    public ProfileInfoData getData() {
        return data;
    }

    public void setData(ProfileInfoData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
