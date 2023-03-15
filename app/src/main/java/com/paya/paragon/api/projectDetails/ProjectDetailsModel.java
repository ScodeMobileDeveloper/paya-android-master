package com.paya.paragon.api.projectDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.api.propertyDetails.SpecificationModel;
import com.paya.paragon.model.ProjectModel;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class ProjectDetailsModel {
    @SerializedName("projectID") @Expose private String projectID;
    @SerializedName("propertyTypeName") @Expose private String propertyTypeName;
    @SerializedName("projectLatitude") @Expose private String projectLatitude;
    @SerializedName("projectLongitude") @Expose private String projectLongitude;
    @SerializedName("projectCurrentStatus") @Expose private String projectCurrentStatus;
    @SerializedName("projectAddress1") @Expose private String projectAddress1;
    @SerializedName("projectAddress2") @Expose private String projectAddress2;
    @SerializedName("projectName") @Expose private String projectName;
    @SerializedName("projectDescription") @Expose private String projectDescription;
    @SerializedName("projectStatus") @Expose private String projectStatus;
    @SerializedName("userFullName") @Expose private String userFullName;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("projectCoverImage") @Expose private String projectCoverImage;
    @SerializedName("projectMinPrice") @Expose private String projectMinPrice;
    @SerializedName("projectMaxPrice") @Expose private String projectMaxPrice;
    @SerializedName("propertyUnitPriceRange") @Expose private String propertyUnitPriceRange;
    @SerializedName("link") @Expose private String link;
    @SerializedName("projectFavourite") @Expose private Integer projectFavourite;
    @SerializedName("listAmenties") @Expose private ArrayList<AmenitiesModel> listAmenties;
    @SerializedName("similarProjects") @Expose private ArrayList<ProjectModel> similarProjects;
    @SerializedName("specifications") @Expose private ArrayList<SpecificationModel> specifications;
    @SerializedName("imageCategories") @Expose private ArrayList<ImageCategoryData> imageCategories;

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
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

    public String getProjectAddress1() {
        return projectAddress1;
    }

    public void setProjectAddress1(String projectAddress1) {
        this.projectAddress1 = projectAddress1;
    }

    public String getProjectAddress2() {
        return projectAddress2;
    }

    public void setProjectAddress2(String projectAddress2) {
        this.projectAddress2 = projectAddress2;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
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

    public String getProjectCoverImage() {
        return projectCoverImage;
    }

    public void setProjectCoverImage(String projectCoverImage) {
        this.projectCoverImage = projectCoverImage;
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

    public ArrayList<AmenitiesModel> getListAmenties() {
        return listAmenties;
    }

    public void setListAmenties(ArrayList<AmenitiesModel> listAmenties) {
        this.listAmenties = listAmenties;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ArrayList<ProjectModel> getSimilarProjects() {
        return similarProjects;
    }

    public void setSimilarProjects(ArrayList<ProjectModel> similarProjects) {
        this.similarProjects = similarProjects;
    }

    public String getPropertyUnitPriceRange() {
        return propertyUnitPriceRange;
    }

    public void setPropertyUnitPriceRange(String propertyUnitPriceRange) {
        this.propertyUnitPriceRange = propertyUnitPriceRange;
    }

    public ArrayList<SpecificationModel> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(ArrayList<SpecificationModel> specifications) {
        this.specifications = specifications;
    }

    public Integer getProjectFavourite() {
        return projectFavourite;
    }

    public void setProjectFavourite(Integer projectFavourite) {
        this.projectFavourite = projectFavourite;
    }

    public ArrayList<ImageCategoryData> getImageCategories() {
        return imageCategories;
    }

    public void setImageCategories(ArrayList<ImageCategoryData> imageCategories) {
        this.imageCategories = imageCategories;
    }

}
