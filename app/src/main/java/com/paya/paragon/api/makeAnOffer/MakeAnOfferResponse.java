package com.paya.paragon.api.makeAnOffer;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakeAnOfferResponse {
    @SerializedName("response") @Expose private String result;
    @SerializedName("code") @Expose private Integer Code;
    @SerializedName("message") @Expose private String Message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
}
