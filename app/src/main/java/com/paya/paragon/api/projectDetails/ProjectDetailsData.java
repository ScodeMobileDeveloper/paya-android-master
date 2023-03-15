package com.paya.paragon.api.projectDetails;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectDetailsData {
    @SerializedName("imageURL") @Expose private String imageURL;
    @SerializedName("projects")  @Expose private ProjectDetailsModel projects;
    @SerializedName("projectUrl") @Expose private String projectUrl;


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ProjectDetailsModel getProjects() {
        return projects;
    }

    public void setProjects(ProjectDetailsModel projects) {
        this.projects = projects;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }
}
