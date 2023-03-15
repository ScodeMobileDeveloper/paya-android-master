package com.paya.paragon.api.propertyDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuggestedPropertyDetails {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("propertyName")
    @Expose
    private String propertyName;
    @SerializedName("propertyPrice")
    @Expose
    private String propertyPrice;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("propertyTypeID")
    @Expose
    private String propertyTypeID;
    @SerializedName("propertyTypeKey")
    @Expose
    private String propertyTypeKey;
    @SerializedName("propertyTypeIcon")
    @Expose
    private String propertyTypeIcon;
    @SerializedName("propertyTypeName")
    @Expose
    private String propertyTypeName;
    @SerializedName("CurrentStatus")
    @Expose
    private String currentStatus;
    @SerializedName("Purpose")
    @Expose
    private String purpose;
    @SerializedName("PropertySoldStatus")
    @Expose
    private String propertySoldStatus;
    @SerializedName("IsNegotiable")
    @Expose
    private String isNegotiable;
    @SerializedName("propertyAddress")
    @Expose
    private String propertyAddress;
    @SerializedName("propertyBedRoom")
    @Expose
    private String propertyBedRoom;
    @SerializedName("propertySqrFeet")
    @Expose
    private String propertySqrFeet;
    @SerializedName("propertyBathRoom")
    @Expose
    private String propertyBathRoom;
    @SerializedName("propertyBuiltUpArea")
    @Expose
    private String propertyBuiltUpArea;
    @SerializedName("propCoverImage")
    @Expose
    private String propCoverImage;
    @SerializedName("image_count")
    @Expose
    private String imageCount;
    @SerializedName("propertyImageName")
    @Expose
    private String propertyImageName;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("propertyLink")
    @Expose
    private String propertyLink;
    @SerializedName("propertyPrices")
    @Expose
    private PropertyPrices propertyPrices;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(String propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPropertyTypeID() {
        return propertyTypeID;
    }

    public void setPropertyTypeID(String propertyTypeID) {
        this.propertyTypeID = propertyTypeID;
    }

    public String getPropertyTypeKey() {
        return propertyTypeKey;
    }

    public void setPropertyTypeKey(String propertyTypeKey) {
        this.propertyTypeKey = propertyTypeKey;
    }

    public String getPropertyTypeIcon() {
        return propertyTypeIcon;
    }

    public void setPropertyTypeIcon(String propertyTypeIcon) {
        this.propertyTypeIcon = propertyTypeIcon;
    }

    public String getPropertyTypeName() {
        return propertyTypeName;
    }

    public void setPropertyTypeName(String propertyTypeName) {
        this.propertyTypeName = propertyTypeName;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPropertySoldStatus() {
        return propertySoldStatus;
    }

    public void setPropertySoldStatus(String propertySoldStatus) {
        this.propertySoldStatus = propertySoldStatus;
    }

    public String getIsNegotiable() {
        return isNegotiable;
    }

    public void setIsNegotiable(String isNegotiable) {
        this.isNegotiable = isNegotiable;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getPropertyBedRoom() {
        return propertyBedRoom;
    }

    public void setPropertyBedRoom(String propertyBedRoom) {
        this.propertyBedRoom = propertyBedRoom;
    }

    public String  getPropertySqrFeet() {
        return propertySqrFeet;
    }

    public void setPropertySqrFeet(String propertySqrFeet) {
        this.propertySqrFeet = propertySqrFeet;
    }

    public String getPropertyBathRoom() {
        return propertyBathRoom;
    }

    public void setPropertyBathRoom(String propertyBathRoom) {
        this.propertyBathRoom = propertyBathRoom;
    }

    public String getPropertyBuiltUpArea() {
        return propertyBuiltUpArea;
    }

    public void setPropertyBuiltUpArea(String propertyBuiltUpArea) {
        this.propertyBuiltUpArea = propertyBuiltUpArea;
    }

    public String getPropCoverImage() {
        return propCoverImage;
    }

    public void setPropCoverImage(String propCoverImage) {
        this.propCoverImage = propCoverImage;
    }

    public String getImageCount() {
        return imageCount;
    }

    public void setImageCount(String imageCount) {
        this.imageCount = imageCount;
    }

    public String getPropertyImageName() {
        return propertyImageName;
    }

    public void setPropertyImageName(String propertyImageName) {
        this.propertyImageName = propertyImageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPropertyLink() {
        return propertyLink;
    }

    public void setPropertyLink(String propertyLink) {
        this.propertyLink = propertyLink;
    }

    public PropertyPrices getPropertyPrices() {
        return propertyPrices;
    }

    public void setPropertyPrices(PropertyPrices propertyPrices) {
        this.propertyPrices = propertyPrices;
    }




    public class PropertyPrices {

        @SerializedName("1")
        @Expose
        private String _1;
        @SerializedName("5")
        @Expose
        private String _5;

        public String get1() {
            return _1;
        }

        public void set1(String _1) {
            this._1 = _1;
        }

        public String get5() {
            return _5;
        }

        public void set5(String _5) {
            this._5 = _5;
        }

    }
}
