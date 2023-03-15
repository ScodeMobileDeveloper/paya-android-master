package com.paya.paragon.api.agentList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AgentDataListDATAResponse {
    @SerializedName("totalCount")
    private Integer totalCount;
    @SerializedName("filterdCount")
    private String filterdCount;
    @SerializedName("data")
    private List<AgentDataListDATAItemObject> data;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getFilterdCount() {
        return filterdCount;
    }

    public void setFilterdCount(String filterdCount) {
        this.filterdCount = filterdCount;
    }

    public List<AgentDataListDATAItemObject> getData() {
        return data;
    }

    public void setData(List<AgentDataListDATAItemObject> data) {
        this.data = data;
    }
}
