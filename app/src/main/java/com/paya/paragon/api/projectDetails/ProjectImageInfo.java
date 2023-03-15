package com.paya.paragon.api.projectDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectImageInfo {
    @SerializedName("projectImageName") @Expose private String projectImageName;
    @SerializedName("projectImageID") @Expose private String projectImageID;

    public String getProjectImageName() {
        return projectImageName;
    }

    public void setProjectImageName(String projectImageName) {
        this.projectImageName = projectImageName;
    }

    public String getProjectImageID() {
        return projectImageID;
    }

    public void setProjectImageID(String projectImageID) {
        this.projectImageID = projectImageID;
    }
}
