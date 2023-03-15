package com.paya.paragon.api.BuyAgents;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class BuyAgentsListData {
    @SerializedName("searchAgentCount") @Expose private String searchAgentCount;
    @SerializedName("imageURL") @Expose private String imageURL;
    @SerializedName("propertyLists")  @Expose private List<BuyAgentsModel> agentLists;

    public String getSearchAgentCount() {
        return searchAgentCount;
    }

    public void setSearchAgentCount(String searchAgentCount) {
        this.searchAgentCount = searchAgentCount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<BuyAgentsModel> getAgentLists() {
        return agentLists;
    }

    public void setAgentLists(List<BuyAgentsModel> agentLists) {
        this.agentLists = agentLists;
    }
}
