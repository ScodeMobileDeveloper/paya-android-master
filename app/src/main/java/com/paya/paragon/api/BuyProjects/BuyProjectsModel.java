package com.paya.paragon.api.BuyProjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BuyProjectsModel {
    @SerializedName("propertyUnitPriceRange") @Expose private String projectUnitPriceRange;
    @SerializedName("propertyBedRoom") @Expose private String projectBedRoom;
    @SerializedName("propertyTypeName") @Expose private String projectTypeName;
    @SerializedName("projectName") @Expose private String projectName;
    @SerializedName("projectAddedDate") @Expose private String projectAddedDate;
    @SerializedName("projectCoverImage") @Expose private String projectCoverImage;
    @SerializedName("cityLocName") @Expose private String cityLocName;
    @SerializedName("projectBuilderName") @Expose private String projectBuilderName;
    @SerializedName("projectUserCompanyName") @Expose private String projectUserCompanyName;
    @SerializedName("proLatitude") @Expose private String proLatitude;
    @SerializedName("proLongitude") @Expose private String proLongitude;
    @SerializedName("projectID") @Expose private String projectID;
    @SerializedName("projectFeatured") @Expose private String projectFeatured;
    @SerializedName("projectFavourite") @Expose private String projectFavourite;
    @SerializedName("imageCount") @Expose private String imageCount;
    @SerializedName("link") @Expose private String link;
    @SerializedName("exact") @Expose private String exact;

    public String getImageCount() {
        return imageCount;
    }

    public void setImageCount(String imageCount) {
        this.imageCount = imageCount;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProjectFavourite() {
        return projectFavourite;
    }

    public void setProjectFavourite(String projectFavourite) {
        this.projectFavourite = projectFavourite;
    }

    public String getProjectFeatured() {
        return projectFeatured;
    }

    public void setProjectFeatured(String projectFeatured) {
        this.projectFeatured = projectFeatured;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProLatitude() {
        return proLatitude;
    }

    public void setProLatitude(String proLatitude) {
        this.proLatitude = proLatitude;
    }

    public String getProLongitude() {
        return proLongitude;
    }

    public void setProLongitude(String proLongitude) {
        this.proLongitude = proLongitude;
    }

    public String getProjectBuilderName() {
        return projectBuilderName;
    }

    public void setProjectBuilderName(String projectBuilderName) {
        this.projectBuilderName = projectBuilderName;
    }

    public String getCityLocName() {
        return cityLocName;
    }

    public void setCityLocName(String cityLocName) {
        this.cityLocName = cityLocName;
    }

    public String getProjectCoverImage() {
        return projectCoverImage;
    }

    public void setProjectCoverImage(String projectCoverImage) {
        this.projectCoverImage = projectCoverImage;
    }

    public String getProjectUnitPriceRange() {
        return projectUnitPriceRange;
    }

    public void setProjectUnitPriceRange(String projectUnitPriceRange) {
        this.projectUnitPriceRange = projectUnitPriceRange;
    }

    public String getProjectBedRoom() {
        return projectBedRoom;
    }

    public void setProjectBedRoom(String projectBedRoom) {
        this.projectBedRoom = projectBedRoom;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public String getProjectAddedDate() {
        return projectAddedDate;
    }

    public void setProjectAddedDate(String projectAddedDate) {
        this.projectAddedDate = projectAddedDate;
    }

    public String getProjectUserCompanyName() {
        return projectUserCompanyName;
    }

    public void setProjectUserCompanyName(String projectUserCompanyName) {
        this.projectUserCompanyName = projectUserCompanyName;
    }

    public String getExact() {
        return exact;
    }

    public void setExact(String exact) {
        this.exact = exact;
    }
}
