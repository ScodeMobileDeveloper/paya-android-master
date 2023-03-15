package com.paya.paragon.api.bankListPropertyPost;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankListData {
    @SerializedName("bankID") @Expose
    private String bankID;
    @SerializedName("bankTitle") @Expose private String bankTitle;

    public String getBankID() {
        return bankID;
    }

    public void setBankID(String bankID) {
        this.bankID = bankID;
    }

    public String getBankTitle() {
        return bankTitle;
    }

    public void setBankTitle(String bankTitle) {
        this.bankTitle = bankTitle;
    }
}
