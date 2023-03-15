package com.paya.paragon.api.agentList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AgentListingData {
    @SerializedName("agentList")  @Expose private ArrayList<AgentList> agentList;
    @SerializedName("imageURL")  @Expose private String imageURL;
    @SerializedName("totalCount")  @Expose private String totalCount;

    public ArrayList<AgentList> getAgentList() {
        return agentList;
    }

    public void setAgentList(ArrayList<AgentList> agentList) {
        this.agentList = agentList;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
