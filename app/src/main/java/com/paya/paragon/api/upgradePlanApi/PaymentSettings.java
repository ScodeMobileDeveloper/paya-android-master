package com.paya.paragon.api.upgradePlanApi;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentSettings {
    @SerializedName("paySetGroupID") @Expose private String paySetGroupID;
    @SerializedName("paySetGroupLabel") @Expose private String paySetGroupLabel;
    @SerializedName("paySetGroupKey") @Expose private String paySetGroupKey;

    public String getPaySetGroupID() {
        return paySetGroupID;
    }

    public void setPaySetGroupID(String paySetGroupID) {
        this.paySetGroupID = paySetGroupID;
    }

    public String getPaySetGroupLabel() {
        return paySetGroupLabel;
    }

    public void setPaySetGroupLabel(String paySetGroupLabel) {
        this.paySetGroupLabel = paySetGroupLabel;
    }

    public String getPaySetGroupKey() {
        return paySetGroupKey;
    }

    public void setPaySetGroupKey(String paySetGroupKey) {
        this.paySetGroupKey = paySetGroupKey;
    }
}
