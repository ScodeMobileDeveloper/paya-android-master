package com.paya.paragon.api.contactOwner;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactOwnerResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("code") @Expose private Integer Code;
    @SerializedName("message") @Expose private String Message;
    @SerializedName("data") @Expose private ContactOwnerData ContactOwnerData;



    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public com.paya.paragon.api.contactOwner.ContactOwnerData getContactOwnerData() {
        return ContactOwnerData;
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
