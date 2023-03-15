package com.paya.paragon.api.updateProfile;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileResponse {
    @SerializedName("response")
    @Expose
    private String Response;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("imgPath")
    @Expose
    private String userProfileImagePath;

    public String getUserProfileImagePath() {
        return userProfileImagePath;
    }

    public void setUserProfileImagePath(String userProfileImagePath) {
        this.userProfileImagePath = userProfileImagePath;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
