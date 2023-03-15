package com.paya.paragon.api.shoertlistedProperties;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class ShortlistedPropertyModel {
    @SerializedName("propertyID") @Expose private String propertyID;
    @SerializedName("propertyKey") @Expose private String propertyKey;
    @SerializedName("propertyFeatured") @Expose private String propertyFeatured;
    @SerializedName("propertyPurpose") @Expose private String propertyPurpose;
    @SerializedName("propCoverImage") @Expose private String propCoverImage;
    @SerializedName("propertyName") @Expose private String propertyName;
    @SerializedName("propertyPrice") @Expose private String propertyPrice;
    @SerializedName("currencySymbolLeft") @Expose private String currencySymbolLeft;
    @SerializedName("currencySymbolRight") @Expose private String currencySymbolRight;
    @SerializedName("propertyTypeName") @Expose private String propertyTypeName;
    @SerializedName("propertyLatitude") @Expose private String propertyLatitude;
    @SerializedName("propertyLongitude") @Expose private String propertyLongitude;
    @SerializedName("projectUserCompanyName") @Expose private String projectUserCompanyName;
    @SerializedName("propertyBedRoom") @Expose private String propertyBedRoom;
    @SerializedName("cityLocName") @Expose private String cityLocName;
    @SerializedName("stateName") @Expose private String stateName;
    @SerializedName("propertyAddedDate") @Expose private String propertyAddedDate;
    @SerializedName("propertyCurrentStatus") @Expose private String propertyCurrentStatus;
    @SerializedName("furnishingStatus") @Expose private String furnishingStatus;
    @SerializedName("propertySqrFeet") @Expose private String propertySqrFeet;
    @SerializedName("possessionDate") @Expose private String possessionDate;
    @SerializedName("projectBuilderName") @Expose private String projectBuilderName;

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getPropCoverImage() {
        return propCoverImage;
    }

    public void setPropCoverImage(String propCoverImage) {
        this.propCoverImage = propCoverImage;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getCurrencySymbolLeft() {
        return currencySymbolLeft;
    }

    public void setCurrencySymbolLeft(String currencySymbolLeft) {
        this.currencySymbolLeft = currencySymbolLeft;
    }

    public String getCurrencySymbolRight() {
        return currencySymbolRight;
    }

    public void setCurrencySymbolRight(String currencySymbolRight) {
        this.currencySymbolRight = currencySymbolRight;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyFeatured() {
        return propertyFeatured;
    }

    public void setPropertyFeatured(String propertyFeatured) {
        this.propertyFeatured = propertyFeatured;
    }

    public String getPropertyLatitude() {
        return propertyLatitude;
    }

    public void setPropertyLatitude(String propertyLatitude) {
        this.propertyLatitude = propertyLatitude;
    }

    public String getPropertyLongitude() {
        return propertyLongitude;
    }

    public void setPropertyLongitude(String propertyLongitude) {
        this.propertyLongitude = propertyLongitude;
    }

    public String getProjectUserCompanyName() {
        return projectUserCompanyName;
    }

    public void setProjectUserCompanyName(String projectUserCompanyName) {
        this.projectUserCompanyName = projectUserCompanyName;
    }

    public String getPropertyBedRoom() {
        return propertyBedRoom;
    }

    public void setPropertyBedRoom(String propertyBedRoom) {
        this.propertyBedRoom = propertyBedRoom;
    }

    public String getCityLocName() {
        return cityLocName;
    }

    public void setCityLocName(String cityLocName) {
        this.cityLocName = cityLocName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getPropertyAddedDate() {
        return propertyAddedDate;
    }

    public void setPropertyAddedDate(String propertyAddedDate) {
        this.propertyAddedDate = propertyAddedDate;
    }

    public String getPropertyCurrentStatus() {
        return propertyCurrentStatus;
    }

    public void setPropertyCurrentStatus(String propertyCurrentStatus) {
        this.propertyCurrentStatus = propertyCurrentStatus;
    }

    public String getFurnishingStatus() {
        return furnishingStatus;
    }

    public void setFurnishingStatus(String furnishingStatus) {
        this.furnishingStatus = furnishingStatus;
    }

    public String getPropertySqrFeet() {
        return propertySqrFeet;
    }

    public void setPropertySqrFeet(String propertySqrFeet) {
        this.propertySqrFeet = propertySqrFeet;
    }

    public String getPossessionDate() {
        return possessionDate;
    }

    public void setPossessionDate(String possessionDate) {
        this.possessionDate = possessionDate;
    }

    public String getProjectBuilderName() {
        return projectBuilderName;
    }

    public void setProjectBuilderName(String projectBuilderName) {
        this.projectBuilderName = projectBuilderName;
    }

    public String getPropertyPurpose() {
        return propertyPurpose;
    }

    public void setPropertyPurpose(String propertyPurpose) {
        this.propertyPurpose = propertyPurpose;
    }
}
