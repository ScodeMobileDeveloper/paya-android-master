package com.paya.paragon.api.bankListPropertyPost;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BankListPropertyResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("code") @Expose private Integer Code;
    @SerializedName("message") @Expose private String Message;
    @SerializedName("data") @Expose private ArrayList<BankListData> bankListData;

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

    public ArrayList<BankListData> getBankListData() {
        return bankListData;
    }

    public void setBankListData(ArrayList<BankListData> bankListData) {
        this.bankListData = bankListData;
    }
}
