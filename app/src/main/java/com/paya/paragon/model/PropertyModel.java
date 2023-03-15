package com.paya.paragon.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyModel {
    @SerializedName("propertyID") @Expose private String propertyID;
    @SerializedName("propertyFeatured") @Expose private String propertyFeatured;
    @SerializedName("propertyPurpose") @Expose private String propertyPurpose;
    @SerializedName("propertyCoverImage") @Expose private String propertyCoverImage;
    @SerializedName("propertyPlotArea") @Expose private String propertyPlotArea;
    @SerializedName("propertyAddress1") @Expose private String propertyAddress1;
    @SerializedName("propertyAddress2") @Expose private String propertyAddress2;
    @SerializedName("propertyPrice") @Expose private String propertyPrice;
    @SerializedName("currencySymbolLeft") @Expose private String currencySymbolLeft;
    @SerializedName("currencySymbolRight") @Expose private String currencySymbolRight;
    @SerializedName("propertyName") @Expose private String propertyName;
    @SerializedName("type") @Expose private String type;
    @SerializedName("propertyBedRoom") @Expose private String propertyBedRoom;
    @SerializedName("propertyImagecount") @Expose private String propertyImagecount;
    @SerializedName("propertyTypeID") @Expose private String propertyTypeID;
    @SerializedName("favourite") @Expose private Integer favourite;

    public String getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(String propertyID) {
        this.propertyID = propertyID;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyFeatured() {
        return propertyFeatured;
    }

    public void setPropertyFeatured(String propertyFeatured) {
        this.propertyFeatured = propertyFeatured;
    }

    public String getPropertyPurpose() {
        return propertyPurpose;
    }

    public void setPropertyPurpose(String propertyPurpose) {
        this.propertyPurpose = propertyPurpose;
    }

    public String getPropertyAddress1() {
        return propertyAddress1;
    }

    public void setPropertyAddress1(String propertyAddress1) {
        this.propertyAddress1 = propertyAddress1;
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

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getPropertyCoverImage() {
        return propertyCoverImage;
    }

    public void setPropertyCoverImage(String propertyCoverImage) {
        this.propertyCoverImage = propertyCoverImage;
    }

    public String getPropertyBedRoom() {
        return propertyBedRoom;
    }

    public void setPropertyBedRoom(String propertyBedRoom) {
        this.propertyBedRoom = propertyBedRoom;
    }

    public String getPropertyAddress2() {
        return propertyAddress2;
    }

    public void setPropertyAddress2(String propertyAddress2) {
        this.propertyAddress2 = propertyAddress2;
    }

    public String getPropertyPlotArea() {
        return propertyPlotArea;
    }

    public void setPropertyPlotArea(String propertyPlotArea) {
        this.propertyPlotArea = propertyPlotArea;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPropertyImagecount() {
        return propertyImagecount;
    }

    public void setPropertyImagecount(String propertyImagecount) {
        this.propertyImagecount = propertyImagecount;
    }

    public String getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(String propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }

    public Integer getFavourite() {
        return favourite;
    }

    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }
}
