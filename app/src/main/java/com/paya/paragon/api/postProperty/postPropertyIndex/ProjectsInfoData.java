package com.paya.paragon.api.postProperty.postPropertyIndex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectsInfoData {
    @SerializedName("projectID") @Expose private String projectID;
    @SerializedName("projectName") @Expose private String projectName;

    public ProjectsInfoData(String projectID, String projectName){
        this.projectID = projectID;
        this.projectName = projectName;
    }

    public ProjectsInfoData(){
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
