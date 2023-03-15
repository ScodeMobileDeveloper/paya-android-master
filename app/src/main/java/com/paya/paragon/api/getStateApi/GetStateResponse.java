package com.paya.paragon.api.getStateApi;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetStateResponse {
    @SerializedName("response")
    @Expose
    private String Response;
    @SerializedName("data")
    @Expose
    private GetStateData data;
    @SerializedName("message") @Expose private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public GetStateData getData() {
        return data;
    }

    public void setData(GetStateData data) {
        this.data = data;
    }

    public class GetStateData {
        @SerializedName("stateList")
        @Expose
        private ArrayList<GetStateList> stateList;

        public ArrayList<GetStateList> getStateList() {
            return stateList;
        }

        public void setStateList(ArrayList<GetStateList> stateList) {
            this.stateList = stateList;
        }
    }
}
