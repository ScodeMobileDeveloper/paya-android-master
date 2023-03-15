package com.paya.paragon.api.projectList;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.propertyList.SerializedParameters;
import com.paya.paragon.model.ProjectModel;

import java.util.ArrayList;
import java.util.List;

public class ProjectListData {
    @SerializedName("totalCount") @Expose private String totalCount;
    @SerializedName("imageURL") @Expose private String imageURL;
    @SerializedName("propertyLists")  @Expose private List<ProjectModel> projectLists;
    @SerializedName("serializedParameters")  @Expose private ArrayList<SerializedParameters> serializedParameters;

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

    public List<ProjectModel> getProjectLists() {
        return projectLists;
    }

    public void setProjectLists(List<ProjectModel> projectLists) {
        this.projectLists = projectLists;
    }

    public ArrayList<SerializedParameters> getSerializedParameters() {
        return serializedParameters;
    }

    public void setSerializedParameters(ArrayList<SerializedParameters> serializedParameters) {
        this.serializedParameters = serializedParameters;
    }
}
