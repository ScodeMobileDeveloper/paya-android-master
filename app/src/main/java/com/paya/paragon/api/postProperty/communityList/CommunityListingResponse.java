package com.paya.paragon.api.postProperty.communityList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CommunityListingResponse {
    @SerializedName("response") @Expose private String response;
    @SerializedName("code") @Expose private Integer code;
    @SerializedName("message") @Expose private String message;
    @SerializedName("data") @Expose private CommunityListingData data;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CommunityListingData getData() {
        return data;
    }

    public void setData(CommunityListingData data) {
        this.data = data;
    }
}
