package com.paya.paragon.api.agentProjects;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentProjectModel {
    @SerializedName("projectID") @Expose private String projectID;
    @SerializedName("projectTypeID") @Expose private String projectTypeID;
    @SerializedName("projectKey") @Expose private String projectKey;
    @SerializedName("propertyUnitPriceRange") @Expose private String propertyUnitPriceRange;
    @SerializedName("cityLocName") @Expose private String cityLocName;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("projectBuilderName") @Expose private String projectBuilderName;
    @SerializedName("propertyUnitPricePerSqft") @Expose private String propertyUnitPricePerSqft;
    @SerializedName("projectCoverImage") @Expose private String projectCoverImage;

    public String getPropertyUnitTypeNames() {
        return propertyUnitTypeNames;
    }

    public void setPropertyUnitTypeNames(String propertyUnitTypeNames) {
        this.propertyUnitTypeNames = propertyUnitTypeNames;
    }

    @SerializedName("propertyUnitTypeNames") @Expose private String propertyUnitTypeNames;


    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectTypeID() {
        return projectTypeID;
    }

    public void setProjectTypeID(String projectTypeID) {
        this.projectTypeID = projectTypeID;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getPropertyUnitPriceRange() {
        return propertyUnitPriceRange;
    }

    public void setPropertyUnitPriceRange(String propertyUnitPriceRange) {
        this.propertyUnitPriceRange = propertyUnitPriceRange;
    }

    public String getCityLocName() {
        return cityLocName;
    }

    public void setCityLocName(String cityLocName) {
        this.cityLocName = cityLocName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProjectBuilderName() {
        return projectBuilderName;
    }

    public void setProjectBuilderName(String projectBuilderName) {
        this.projectBuilderName = projectBuilderName;
    }

    public String getPropertyUnitPricePerSqft() {
        return propertyUnitPricePerSqft;
    }

    public void setPropertyUnitPricePerSqft(String propertyUnitPricePerSqft) {
        this.propertyUnitPricePerSqft = propertyUnitPricePerSqft;
    }

    public String getProjectCoverImage() {
        return projectCoverImage;
    }

    public void setProjectCoverImage(String projectCoverImage) {
        this.projectCoverImage = projectCoverImage;
    }
}
