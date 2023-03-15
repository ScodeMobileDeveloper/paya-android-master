package com.paya.paragon.api.newAgent;

import com.google.gson.annotations.SerializedName;

public class NewAgentDetailsDATAResponse {
    @SerializedName("userID")
    private String userID;
    @SerializedName("data")
    private NewAgentDetailsDATADATAResponse data;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public NewAgentDetailsDATADATAResponse getData() {
        return data;
    }

    public void setData(NewAgentDetailsDATADATAResponse data) {
        this.data = data;
    }
}
