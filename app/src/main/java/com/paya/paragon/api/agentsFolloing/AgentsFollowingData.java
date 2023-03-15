package com.paya.paragon.api.agentsFolloing;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.agentList.AgentList;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class AgentsFollowingData {
    @SerializedName("agencyLists")  @Expose private ArrayList<AgentList> agencyLists;
    @SerializedName("totalCount")  @Expose private String totalCount;

    public ArrayList<AgentList> getAgencyLists() {
        return agencyLists;
    }

    public void setAgencyLists(ArrayList<AgentList> agencyLists) {
        this.agencyLists = agencyLists;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
