package com.paya.paragon.api.getUserPlanDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PaymentGateway {
    @SerializedName("paySetGroupID") @Expose private String paySetGroupID;
    @SerializedName("paySetGroupLabel") @Expose private String paySetGroupLabel;

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
}
