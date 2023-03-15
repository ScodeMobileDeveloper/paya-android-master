package com.paya.paragon.api.shortlistedProjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ShortlistedProjectModel {
    @SerializedName("projectID") @Expose private String projectID;
    @SerializedName("projectKey") @Expose private String projectKey;
    @SerializedName("projectLatitude") @Expose private String projectLatitude;
    @SerializedName("projectLongitude") @Expose private String projectLongitude;
    @SerializedName("projectCurrentStatus") @Expose private String projectCurrentStatus;
    @SerializedName("projectName") @Expose private String projectName;
    @SerializedName("propertyTypeName") @Expose private String propertyTypeName;
    @SerializedName("projectBuilderName") @Expose private String projectBuilderName;
    @SerializedName("userPhone") @Expose private String userPhone;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("projCoverImage") @Expose private String projCoverImage;
    @SerializedName("projectUserCompanyName") @Expose private String projectUserCompanyName;
    @SerializedName("propertyUnitPriceRange") @Expose private String propertyUnitPriceRange;
    @SerializedName("propertyUnitSqftRange") @Expose private String propertyUnitSqftRange;
    @SerializedName("furnishingStatus") @Expose private String furnishingStatus;

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjCoverImage() {
        return projCoverImage;
    }

    public void setProjCoverImage(String projCoverImage) {
        this.projCoverImage = projCoverImage;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getPropertyUnitPriceRange() {
        return propertyUnitPriceRange;
    }

    public void setPropertyUnitPriceRange(String propertyUnitPriceRange) {
        this.propertyUnitPriceRange = propertyUnitPriceRange;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getProjectLatitude() {
        return projectLatitude;
    }

    public void setProjectLatitude(String projectLatitude) {
        this.projectLatitude = projectLatitude;
    }

    public String getProjectLongitude() {
        return projectLongitude;
    }

    public void setProjectLongitude(String projectLongitude) {
        this.projectLongitude = projectLongitude;
    }

    public String getProjectCurrentStatus() {
        return projectCurrentStatus;
    }

    public void setProjectCurrentStatus(String projectCurrentStatus) {
        this.projectCurrentStatus = projectCurrentStatus;
    }

    public String getProjectBuilderName() {
        return projectBuilderName;
    }

    public void setProjectBuilderName(String projectBuilderName) {
        this.projectBuilderName = projectBuilderName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getProjectUserCompanyName() {
        return projectUserCompanyName;
    }

    public void setProjectUserCompanyName(String projectUserCompanyName) {
        this.projectUserCompanyName = projectUserCompanyName;
    }

    public String getPropertyUnitSqftRange() {
        return propertyUnitSqftRange;
    }

    public void setPropertyUnitSqftRange(String propertyUnitSqftRange) {
        this.propertyUnitSqftRange = propertyUnitSqftRange;
    }

    public String getFurnishingStatus() {
        return furnishingStatus;
    }

    public void setFurnishingStatus(String furnishingStatus) {
        this.furnishingStatus = furnishingStatus;
    }
}
