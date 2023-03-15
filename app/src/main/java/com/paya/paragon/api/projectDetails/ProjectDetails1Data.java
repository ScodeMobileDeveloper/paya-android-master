package com.paya.paragon.api.projectDetails;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectDetails1Data {
    @SerializedName("imageURL") @Expose private String imageURL;
    @SerializedName("projects")  @Expose private ProjectDetails1Model projects;
    @SerializedName("projectUrl") @Expose private String projectUrl;
    @SerializedName("totalviews")  @Expose private String totalViews;


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ProjectDetails1Model getProjects() {
        return projects;
    }

    public void setProjects(ProjectDetails1Model projects) {
        this.projects = projects;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }


    public String getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(String totalViews) {
        this.totalViews = totalViews;
    }
}
