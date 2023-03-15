package com.paya.paragon.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectModel {
    @SerializedName("projectID") @Expose private String projectID;
    @SerializedName("projectFeatured") @Expose private String projectFeatured;
    @SerializedName("projectCoverImage") @Expose private String projectCoverImage;
    @SerializedName("projectImgCount") @Expose private String projectImgCount;
    @SerializedName("projectName") @Expose private String projectName;
    @SerializedName("projectTypeID") @Expose private String projectTypeID;
    @SerializedName("projectAddress1") @Expose private String projectAddress1;
    @SerializedName("projectAddress2") @Expose private String projectAddress2;
    @SerializedName("projectBuilderName") @Expose private String projectBuilderName;
    @SerializedName("projectCurrentStatus") @Expose private String projectCurrentStatus;
    @SerializedName("propertyUnitSqftRange") @Expose private String propertyUnitSqftRange;
    @SerializedName("propertyUnitPriceRange") @Expose private String propertyUnitPriceRange;
    @SerializedName("projectMinPrice") @Expose private String projectMinPrice;
    @SerializedName("projectMaxPrice") @Expose private String projectMaxPrice;
    @SerializedName("projectFavourite") @Expose private Integer projectFavourite;
    @SerializedName("cityLocName") @Expose private String cityLocName;

    public String getCityLocName() {
        return cityLocName;
    }

    public void setCityLocName(String cityLocName) {
        this.cityLocName = cityLocName;
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

    public String getProjectAddress1() {
        return projectAddress1;
    }

    public void setProjectAddress1(String projectAddress1) {
        this.projectAddress1 = projectAddress1;
    }

    public String getProjectBuilderName() {
        return projectBuilderName;
    }

    public void setProjectBuilderName(String projectBuilderName) {
        this.projectBuilderName = projectBuilderName;
    }

    public String getProjectMinPrice() {
        return projectMinPrice;
    }

    public void setProjectMinPrice(String projectMinPrice) {
        this.projectMinPrice = projectMinPrice;
    }

    public String getProjectMaxPrice() {
        return projectMaxPrice;
    }

    public void setProjectMaxPrice(String projectMaxPrice) {
        this.projectMaxPrice = projectMaxPrice;
    }

    public String getProjectCurrentStatus() {
        return projectCurrentStatus;
    }

    public void setProjectCurrentStatus(String projectCurrentStatus) {
        this.projectCurrentStatus = projectCurrentStatus;
    }

    public String getProjectFeatured() {
        return projectFeatured;
    }

    public void setProjectFeatured(String projectFeatured) {
        this.projectFeatured = projectFeatured;
    }

    public String getProjectImgCount() {
        return projectImgCount;
    }

    public void setProjectImgCount(String projectImgCount) {
        this.projectImgCount = projectImgCount;
    }

    public String getProjectTypeID() {
        return projectTypeID;
    }

    public void setProjectTypeID(String projectTypeID) {
        this.projectTypeID = projectTypeID;
    }

    public String getProjectAddress2() {
        return projectAddress2;
    }

    public void setProjectAddress2(String projectAddress2) {
        this.projectAddress2 = projectAddress2;
    }

    public String getPropertyUnitSqftRange() {
        return propertyUnitSqftRange;
    }

    public void setPropertyUnitSqftRange(String propertyUnitSqftRange) {
        this.propertyUnitSqftRange = propertyUnitSqftRange;
    }

    public String getPropertyUnitPriceRange() {
        return propertyUnitPriceRange;
    }

    public void setPropertyUnitPriceRange(String propertyUnitPriceRange) {
        this.propertyUnitPriceRange = propertyUnitPriceRange;
    }

    public Integer getProjectFavourite() {
        return projectFavourite;
    }

    public void setProjectFavourite(Integer projectFavourite) {
        this.projectFavourite = projectFavourite;
    }

    public String getProjectCoverImage() {
        return projectCoverImage;
    }

    public void setProjectCoverImage(String projectCoverImage) {
        this.projectCoverImage = projectCoverImage;
    }
}
