package com.paya.paragon.api.checkUserEmail;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserEmailData {
    @SerializedName("userId") @Expose private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
