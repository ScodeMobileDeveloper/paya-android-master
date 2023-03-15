package com.paya.paragon.api.userSignUp;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserSignUpResponse {
    @SerializedName("response") @Expose
    private String Response;
    @SerializedName("code") @Expose
    private Integer Code;
    @SerializedName("message") @Expose
    private String Message;
    @SerializedName("data") @Expose
    private UserSignUpData data;

    public UserSignUpData getData() {
        return data;
    }

    public void setData(UserSignUpData data) {
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



}
