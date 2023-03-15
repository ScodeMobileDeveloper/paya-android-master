package com.paya.paragon.api.mySavedSearches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateDetails {
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("stateID") @Expose private String stateID;

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }
}
