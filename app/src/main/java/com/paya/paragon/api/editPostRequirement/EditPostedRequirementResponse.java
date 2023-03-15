package com.paya.paragon.api.editPostRequirement;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditPostedRequirementResponse {
    @SerializedName("response") @Expose private String response;
    @SerializedName("code") @Expose private Integer code;
    @SerializedName("message") @Expose private String message;
    @SerializedName("data") @Expose private EditPostedRequirementData data;

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

    public EditPostedRequirementData getData() {
        return data;
    }

    public void setData(EditPostedRequirementData data) {
        this.data = data;
    }
}
