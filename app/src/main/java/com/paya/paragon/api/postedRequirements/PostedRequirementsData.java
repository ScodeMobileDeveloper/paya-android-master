package com.paya.paragon.api.postedRequirements;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class PostedRequirementsData {
    @SerializedName("totalCount") @Expose private Integer totalCount;
    @SerializedName("requirementList") @Expose private ArrayList<PostedRequirementsDataModel> requirementList;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<PostedRequirementsDataModel> getRequirementList() {
        return requirementList;
    }

    public void setRequirementList(ArrayList<PostedRequirementsDataModel> requirementList) {
        this.requirementList = requirementList;
    }
}
