package com.paya.paragon.api.getProfileState;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetProfileStateResponse {
    @SerializedName("response") @Expose private String Response;
    @SerializedName("data") @Expose private ArrayList<GetProfileStateData> stateList;

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public ArrayList<GetProfileStateData> getStateList() {
        return stateList;
    }

    public void setStateList(ArrayList<GetProfileStateData> stateList) {
        this.stateList = stateList;
    }
}
