package com.paya.paragon.api.upgradePlanListing;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class UpgradePlanListResponse {
    @SerializedName("response")
    @Expose
    private String Response;
    @SerializedName("code")
    @Expose
    private Integer Code;
    @SerializedName("message")
    @Expose
    private String Message;
    @SerializedName("data")
    @Expose
    private Data data;

    public class Data {
        @SerializedName("resPlans")
        @Expose
        private ArrayList<UpgradePlanListData> resPlans;
        @SerializedName("currplan")
        @Expose
        private String currplan;

        public ArrayList<UpgradePlanListData> getResPlans() {
            return resPlans;
        }

        public void setResPlans(ArrayList<UpgradePlanListData> resPlans) {
            this.resPlans = resPlans;
        }

        public String getCurrplan() {
            return currplan;
        }

        public void setCurrplan(String currplan) {
            this.currplan = currplan;
        }
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

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


}
