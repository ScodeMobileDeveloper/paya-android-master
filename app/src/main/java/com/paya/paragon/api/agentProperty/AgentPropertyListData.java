package com.paya.paragon.api.agentProperty;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AgentPropertyListData {
    @SerializedName("totalCount") @Expose private String totalCount;
    @SerializedName("imageURL") @Expose private String imageURL;
    @SerializedName("propertyLists")  @Expose private ArrayList<AgentPropertyModel> propertyLists;

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

    public ArrayList<AgentPropertyModel> getPropertyLists() {
        return propertyLists;
    }

    public void setPropertyLists(ArrayList<AgentPropertyModel> propertyLists) {
        this.propertyLists = propertyLists;
    }
}
