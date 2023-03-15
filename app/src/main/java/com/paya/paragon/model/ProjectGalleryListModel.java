package com.paya.paragon.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectGalleryListModel {
    @SerializedName("scheduleUpdatedDate") @Expose private String scheduleUpdatedDate;
    @SerializedName("specialListingID") @Expose private String specialListingID;
    @SerializedName("projectID") @Expose private String projectID;
    @SerializedName("projectName") @Expose private String projectName;
    @SerializedName("projectKey") @Expose private String projectKey;
    @SerializedName("cityID") @Expose private String cityID;
    @SerializedName("cityLocID") @Expose private String cityLocID;
    @SerializedName("projectTypeID") @Expose private String projectTypeID;
    @SerializedName("projectBuilderName") @Expose private String projectBuilderName;
    @SerializedName("userCompanyName") @Expose private String userCompanyName;
    @SerializedName("userCompanyLogo") @Expose private String userCompanyLogo;
    @SerializedName("planBusinessLogo") @Expose private String planBusinessLogo;
    @SerializedName("userTypeID") @Expose private String userTypeID;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("cityLocName") @Expose private String cityLocName;
    @SerializedName("projectCoverImage") @Expose private String projectCoverImage;
    @SerializedName("propertyUnitTypeNames") @Expose private String propertyUnitTypeNames;
    @SerializedName("propertyUnitBedRooms") @Expose private String propertyUnitBedRooms;
    @SerializedName("propertyUnitSqftRange") @Expose private String propertyUnitSqftRange;
    @SerializedName("propertyUnitPriceRange") @Expose private String propertyUnitPriceRange;

    public String getPropertyUnitPriceRange() {
        return propertyUnitPriceRange;
    }

    public void setPropertyUnitPriceRange(String propertyUnitPriceRange) {
        this.propertyUnitPriceRange = propertyUnitPriceRange;
    }

    public String getScheduleUpdatedDate() {
        return scheduleUpdatedDate;
    }

    public void setScheduleUpdatedDate(String scheduleUpdatedDate) {
        this.scheduleUpdatedDate = scheduleUpdatedDate;
    }

    public String getSpecialListingID() {
        return specialListingID;
    }

    public void setSpecialListingID(String specialListingID) {
        this.specialListingID = specialListingID;
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

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityLocID() {
        return cityLocID;
    }

    public void setCityLocID(String cityLocID) {
        this.cityLocID = cityLocID;
    }

    public String getProjectTypeID() {
        return projectTypeID;
    }

    public void setProjectTypeID(String projectTypeID) {
        this.projectTypeID = projectTypeID;
    }

    public String getProjectBuilderName() {
        return projectBuilderName;
    }

    public void setProjectBuilderName(String projectBuilderName) {
        this.projectBuilderName = projectBuilderName;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
    }

    public String getUserCompanyLogo() {
        return userCompanyLogo;
    }

    public void setUserCompanyLogo(String userCompanyLogo) {
        this.userCompanyLogo = userCompanyLogo;
    }

    public String getPlanBusinessLogo() {
        return planBusinessLogo;
    }

    public void setPlanBusinessLogo(String planBusinessLogo) {
        this.planBusinessLogo = planBusinessLogo;
    }

    public String getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(String userTypeID) {
        this.userTypeID = userTypeID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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

    public String getPropertyUnitTypeNames() {
        return propertyUnitTypeNames;
    }

    public void setPropertyUnitTypeNames(String propertyUnitTypeNames) {
        this.propertyUnitTypeNames = propertyUnitTypeNames;
    }

    public String getPropertyUnitBedRooms() {
        return propertyUnitBedRooms;
    }

    public void setPropertyUnitBedRooms(String propertyUnitBedRooms) {
        this.propertyUnitBedRooms = propertyUnitBedRooms;
    }

    public String getPropertyUnitSqftRange() {
        return propertyUnitSqftRange;
    }

    public void setPropertyUnitSqftRange(String propertyUnitSqftRange) {
        this.propertyUnitSqftRange = propertyUnitSqftRange;
    }
}
