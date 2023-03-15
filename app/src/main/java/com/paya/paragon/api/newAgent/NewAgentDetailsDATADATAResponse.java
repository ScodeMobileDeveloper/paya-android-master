package com.paya.paragon.api.newAgent;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewAgentDetailsDATADATAResponse {

    @SerializedName("userdata")
    private NewAgentDetailsAndPropertyStatus userData;

    @SerializedName("countProperties")
    private int agentPropertyCount;

    @SerializedName("totalProperties")
    private int totalProperties;



    @SerializedName("properties")
    private List<AgentPropertyBasicDetails> agentPropertyBasicDetailsList;

    public NewAgentDetailsAndPropertyStatus getUserData() {
        return userData;
    }

    public void setUserData(NewAgentDetailsAndPropertyStatus userData) {
        this.userData = userData;
    }

    public int getAgentPropertyCount() {
        return agentPropertyCount;
    }

    public void setAgentPropertyCount(int agentPropertyCount) {
        this.agentPropertyCount = agentPropertyCount;
    }
    public int getTotalProperties() {
        return totalProperties;
    }

    public void setTotalProperties(int totalProperties) {
        this.totalProperties = totalProperties;
    }
    public List<AgentPropertyBasicDetails> getAgentPropertyBasicDetailsList() {
        return agentPropertyBasicDetailsList;
    }

    public void setAgentPropertyBasicDetailsList(List<AgentPropertyBasicDetails> agentPropertyBasicDetailsList) {
        this.agentPropertyBasicDetailsList = agentPropertyBasicDetailsList;
    }
}
