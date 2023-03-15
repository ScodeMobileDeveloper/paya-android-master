package com.paya.paragon.api.projectDetails;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.api.propertyDetails.SpecificationModel;
import com.paya.paragon.model.ProjectModel;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class ProjectDetails1Model {

    @SerializedName("projectID") @Expose private String propertyID;
    @SerializedName("propertyTypeKey") @Expose private String propertyTypeKey;
   // @SerializedName("propertyName") @Expose private String propertyName;
    @SerializedName("projectName") @Expose private String projectName;
    @SerializedName("projectKey") @Expose private String projectKey;
    @SerializedName("amenitiesImgPath") @Expose private String amenitiesImgPath;
    @SerializedName("projectBannerImage") @Expose private String projectBannerImage;
    @SerializedName("planEnquiryCountBalance") @Expose private String planEnquiryCountBalance;
    @SerializedName("projectUserCompanyName") @Expose private String projectUserCompanyName;
    @SerializedName("projectBuilderName") @Expose private String projectBuilderName;
    @SerializedName("userCompanyName") @Expose private String userCompanyName;
    @SerializedName("propertyUnitSqftRange") @Expose private String propertyUnitSqftRange;
    @SerializedName("propertyUnitPricePerSqft") @Expose private String propertyUnitPricePerSqft;
   // @SerializedName("possessionStatus") @Expose private String possessionStatus;
    @SerializedName("projectAddress1") @Expose private String projectAddress1;
    @SerializedName("projectAddress2") @Expose private String projectAddress2;
    @SerializedName("cityName") @Expose private String cityName;
    @SerializedName("userPhone") @Expose private String userPhone;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @SerializedName("cityLocName") @Expose private String cityLocName;
    @SerializedName("projectAddedDate") @Expose private String projectAddedDate;
    @SerializedName("projectDescription") @Expose private String projectDescription;
   // @SerializedName("propertyFeatured") @Expose private String propertyFeatured;
   // @SerializedName("propertySoldOutStatus") @Expose private String propertySoldOutStatus;
    @SerializedName("propertyUnitPriceRange") @Expose private String propertyUnitPriceRange;
    @SerializedName("pricePerSqft") @Expose private String pricePerSqft;
    @SerializedName("projectOffer") @Expose private String projectOffer;
    @SerializedName("propertyTypeName") @Expose private String propertyTypeName;
    @SerializedName("projectLatitude") @Expose private String projectLatitude;
    @SerializedName("projectLongitude") @Expose private String projectLongitude;
    @SerializedName("projectFavourite") @Expose private Integer projectFavourite;
    @SerializedName("projectUserPhone") @Expose private String projectUserPhone;

    @SerializedName("userEmail") @Expose private String userEmail;
    @SerializedName("link") @Expose private String link;
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("projectStatus") @Expose private String projectStatus;
    @SerializedName("projectCurrentStatus") @Expose private String projectCurrentStatus;
    @SerializedName("possessionDate") @Expose private String possessionDate;
    @SerializedName("propertyUnitBedRooms") @Expose private String propertyUnitBedRooms;
    @SerializedName("imagePath") @Expose private String imagePath;
    @SerializedName("unitimagePath") @Expose private String unitimagePath;
    @SerializedName("listAmenties") @Expose private ArrayList<AmenitiesModel> listAmenties;
    @SerializedName("bankPartners") @Expose private ArrayList<AmenitiesModel> bankPartners;
    @SerializedName("similarProjects") @Expose private ArrayList<ProjectModel> similarProjects;
    @SerializedName("specifications") @Expose private ArrayList<SpecificationModel> specifications;
    @SerializedName("imageCategories") @Expose private ArrayList<ImageCategoryData> imageCategories;
    @SerializedName("vidoes") @Expose private ArrayList<ProjectVideoInfo> projectVideos;
    @SerializedName("resUnits") @Expose private ArrayList<ResUnits> resUnits;

    public ArrayList<AmenitiesModel> getBankPartners() {
        return bankPartners;
    }

    public void setBankPartners(ArrayList<AmenitiesModel> bankPartners) {
        this.bankPartners = bankPartners;
    }

    public String getProjectBannerImage() {
        return projectBannerImage;
    }

    public void setProjectBannerImage(String projectBannerImage) {
        this.projectBannerImage = projectBannerImage;
    }


    public String getUnitimagePath() {
        return unitimagePath;
    }

    public void setUnitimagePath(String unitimagePath) {
        this.unitimagePath = unitimagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public ArrayList<ResUnits> getResUnits() {
        return resUnits;
    }

    public void setResUnits(ArrayList<ResUnits> resUnits) {
        this.resUnits = resUnits;
    }

    public class ResUnits
    {

        @SerializedName("title") @Expose private String title;
        @SerializedName("units") @Expose private ArrayList<Units> units;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public ArrayList<Units> getUnits() {
            return units;
        }

        public void setUnits(ArrayList<Units> units) {
            this.units = units;
        }
    }


    public String getProjectOffer() {
        return projectOffer;
    }

    public void setProjectOffer(String projectOffer) {
        this.projectOffer = projectOffer;
    }

    public ArrayList<ProjectVideoInfo> getProjectVideos() {
        return projectVideos;
    }

    public void setProjectVideos(ArrayList<ProjectVideoInfo> projectVideos) {
        this.projectVideos = projectVideos;
    }

    public String getAmenitiesImgPath() {
        return amenitiesImgPath;
    }

    public void setAmenitiesImgPath(String amenitiesImgPath) {
        this.amenitiesImgPath = amenitiesImgPath;
    }
    public String getPlanEnquiryCountBalance() {
        return planEnquiryCountBalance;
    }

    public void setPlanEnquiryCountBalance(String planEnquiryCountBalance) {
        this.planEnquiryCountBalance = planEnquiryCountBalance;
    }
    public String getPropertyUnitPricePerSqft() {
        return propertyUnitPricePerSqft;
    }

    public void setPropertyUnitPricePerSqft(String propertyUnitPricePerSqft) {
        this.propertyUnitPricePerSqft = propertyUnitPricePerSqft;
    }

    public String getProjectCurrentStatus() {
        return projectCurrentStatus;
    }

    public void setProjectCurrentStatus(String projectCurrentStatus) {
        this.projectCurrentStatus = projectCurrentStatus;
    }

    public String getPropertyUnitSqftRange() {
        return propertyUnitSqftRange;
    }

    public void setPropertyUnitSqftRange(String propertyUnitSqftRange) {
        this.propertyUnitSqftRange = propertyUnitSqftRange;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getUserCompanyName() {
        return userCompanyName;
    }

    public void setUserCompanyName(String userCompanyName) {
        this.userCompanyName = userCompanyName;
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

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getPropertyTypeKey() {
        return propertyTypeKey;
    }

    public void setPropertyTypeKey(String propertyTypeKey) {
        this.propertyTypeKey = propertyTypeKey;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectUserCompanyName() {
        return projectUserCompanyName;
    }

    public void setProjectUserCompanyName(String projectUserCompanyName) {
        this.projectUserCompanyName = projectUserCompanyName;
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

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProjectAddedDate() {
        return projectAddedDate;
    }

    public void setProjectAddedDate(String projectAddedDate) {
        this.projectAddedDate = projectAddedDate;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getPropertyUnitPriceRange() {
        return propertyUnitPriceRange;
    }

    public void setPropertyUnitPriceRange(String propertyUnitPriceRange) {
        this.propertyUnitPriceRange = propertyUnitPriceRange;
    }

    public String getPricePerSqft() {
        return pricePerSqft;
    }

    public void setPricePerSqft(String pricePerSqft) {
        this.pricePerSqft = pricePerSqft;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
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

    public Integer getProjectFavourite() {
        return projectFavourite;
    }

    public void setProjectFavourite(Integer projectFavourite) {
        this.projectFavourite = projectFavourite;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getPossessionDate() {
        return possessionDate;
    }

    public void setPossessionDate(String possessionDate) {
        this.possessionDate = possessionDate;
    }

    public String getPropertyUnitBedRooms() {
        return propertyUnitBedRooms;
    }

    public void setPropertyUnitBedRooms(String propertyUnitBedRooms) {
        this.propertyUnitBedRooms = propertyUnitBedRooms;
    }

    public ArrayList<AmenitiesModel> getListAmenties() {
        return listAmenties;
    }

    public void setListAmenties(ArrayList<AmenitiesModel> listAmenties) {
        this.listAmenties = listAmenties;
    }

    public ArrayList<ProjectModel> getSimilarProjects() {
        return similarProjects;
    }

    public void setSimilarProjects(ArrayList<ProjectModel> similarProjects) {
        this.similarProjects = similarProjects;
    }

    public ArrayList<SpecificationModel> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(ArrayList<SpecificationModel> specifications) {
        this.specifications = specifications;
    }

    public ArrayList<ImageCategoryData> getImageCategories() {
        return imageCategories;
    }

    public void setImageCategories(ArrayList<ImageCategoryData> imageCategories) {
        this.imageCategories = imageCategories;
    }

    public String getProjectUserPhone() {
        return projectUserPhone;
    }

    public void setProjectUserPhone(String projectUserPhone) {
        this.projectUserPhone = projectUserPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
