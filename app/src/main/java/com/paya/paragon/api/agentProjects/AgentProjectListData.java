package com.paya.paragon.api.agentProjects;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AgentProjectListData {
    @SerializedName("totalCount") @Expose private String totalCount;
    @SerializedName("imageURL") @Expose private String imageURL;
    @SerializedName("projectLists")  @Expose private ArrayList<AgentProjectModel> projectLists;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ArrayList<AgentProjectModel> getProjectLists() {
        return projectLists;
    }

    public void setProjectLists(ArrayList<AgentProjectModel> projectLists) {
        this.projectLists = projectLists;
    }
}
