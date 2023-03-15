package com.paya.paragon.api.getStateApi;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStateList {
    @SerializedName("stateID") @Expose private String stateID;
    @SerializedName("stateName") @Expose private String stateName;

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
